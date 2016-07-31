/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.util;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.logging.Log;
import org.jkcsoft.java.systems.components.Constants;
import org.jkcsoft.java.systems.contexts.BehavioralContext;

import javax.naming.*;
import javax.naming.directory.*;
import java.util.*;

/**
 * @author Jim Coles
 * @version 1.0
 */
public class JndiHelper {

    //----------------------------------------------------------------------------
    // Constants
    //----------------------------------------------------------------------------
    public static final String LDAP_DC = "dc";
    public static final String LDAP_DISTINGUISHEDNAME = "distinguishedName";
    public static final String LDAP_USER_LASTNAME = "sn";
    public static final String LDAP_USER_GIVENNAME = "givenName";
    public static final String LDAP_USER_SAMACCOUNTNAME = "sAMAccountName";
    public static final String LDAP_USER_DISPLAYNAME = "displayName";
    public static final String LDAP_USER_MAIL = "mail";
    //
    public static final String KEY_JNDI_CONN = "jndi-connection";
    public static final String KEY_JNDI_CONN_HOST = KEY_JNDI_CONN + "."
            + "host";
    public static final String KEY_JNDI_CONN_PORT = KEY_JNDI_CONN + "."
            + "port";
    public static final String KEY_JNDI_CONN_MODE = KEY_JNDI_CONN + "."
            + "mode";

    //----------------------------------------------------------------------------
    // Static members
    //----------------------------------------------------------------------------
    private static Log log = LogHelper.getLogger(JndiHelper.class);

    public static Context getContextFromProps(Configuration config)
            throws Exception {
        String url = getJndiUrlFromProps(config);

        return getContext(url, ""); // TODO: get icf class name from config..
    }

    public static String getJndiUrlFromProps(Configuration config)
            throws IllegalArgumentException {
        String scheme = config.getString(KEY_JNDI_CONN_MODE, "tcp");
        String host = config.getString(KEY_JNDI_CONN_HOST, "localhost");
        String port = "";

        if (scheme.equals("tcp") || scheme.equals("tcps")) {
            port = config.getString(KEY_JNDI_CONN_PORT, "3035");
        } else if (scheme.equals("http")) {
            throw new IllegalArgumentException(
                    "http jndi connection not supported");
        } else if (scheme.equals("https")) {
            throw new IllegalArgumentException(
                    "https jndi connection not supported");
        } else if (scheme.equals("rmi")) {
            port = config.getString(KEY_JNDI_CONN_PORT, "1099");
        }

        String name = "";
        if (scheme.equals("rmi")) {
            name = config.getString("jndiname", "");
        }

        String url = scheme + "://" + host + ":" + port + "/" + name;
        return url;
    }

    public static Context getJmsContext(String url) throws NamingException {
        return getContext(url, ""); // TODO: get icf name from config...
    }

    public static Context getContext(String url, String contextFactoryName)
            throws NamingException {
        log.debug("Getting initial context from jndi url [" + url + "]");
        Context ctx = null;
        Hashtable properties = new Hashtable();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, contextFactoryName);

        properties.put(Context.PROVIDER_URL, url);


        try {
            // Creating the JNDI directory context (with LDAP context
            // factory), performs an LDAP bind to the LDAP provider thereby
            // authenticating the username/pw.
            ctx = new InitialContext(properties);
        } catch (NamingException ex) {
            throw ex;
        }

        return ctx;
    }

    public static Map getUserInfo(BehavioralContext ctx, String userName) throws NamingException {
        Map infoMap = null;


        Configuration cfg = ctx.getConfig();
        // 
        String searchRelativeDc = cfg.getString(Constants.KEY_AD_USER_NODE_DN);
        String theFilter = LDAP_USER_SAMACCOUNTNAME + "=" + userName;
        List theAttrsList = new Vector(Arrays.asList(ldapUserAttrs));
        theAttrsList.addAll(Arrays.asList(ldapTopAttrs));

        int countLimit = 1000;
        int timeLimitMillis = 30000;
        boolean returnObject = false;
        boolean derefObj = true;

        SearchControls scs =
                new SearchControls(SearchControls.SUBTREE_SCOPE,
                        countLimit, timeLimitMillis,
                        (String[]) theAttrsList.toArray(new String[0]),
                        returnObject, derefObj);

        DirContext rootCtx = getTsessAccountContext(ctx);

        try {
            log.debug("Search params name[" + searchRelativeDc + "] " +
                    "filter[" + theFilter + "] controls[" + scs + "]");

            NamingEnumeration results = rootCtx.search(searchRelativeDc,
                    theFilter, scs);

            if (results == null || !results.hasMore()) throw new NamingException("User LDAP entry not found");

            SearchResult searchResult = ((SearchResult) results.next());
            if (searchResult == null) throw new NamingException("User LDAP entry not found");

            if (log.isTraceEnabled()) {
                logLdap(log, 0, 0, searchResult);
            }

            Attributes userLdapAttrs = searchResult.getAttributes();
            infoMap = new HashMap();
            for (Iterator attrIter = theAttrsList.iterator(); attrIter.hasNext(); ) {
                loadMap(infoMap, userLdapAttrs, (String) attrIter.next());
            }
        } finally {
            safeClose(rootCtx);
        }

        return infoMap;
    }

    private static void loadMap(Map info, Attributes userLdapAttrs, String key) throws NamingException {
        Attribute attribute = userLdapAttrs.get(key);
        Object value = null;
        if (attribute != null) {
            value = attribute.get().toString();
        } else {
            log.warn("Null value of AD param [" + key + "]");
        }
        info.put(key, value);
    }

    public static DirContext getTsessAccountContext(BehavioralContext bctx)
            throws NamingException {
        Configuration cfg = bctx.getConfig();
        return getDirContext(bctx,
                cfg.getString(Constants.KEY_AD_SERVICE_USER_NAME),
                cfg.getString(Constants.KEY_AD_SERVICE_USER_PW));
    }

    public static DirContext getDirContext(BehavioralContext bctx, Object principal, Object credentials)
            throws NamingException {
        DirContext ctx = null;

        Configuration tconfig = bctx.getConfig();
        String ldapProvider = "ldap"
                + "://" + tconfig.getString(Constants.KEY_AD_HOST)
                + ":" + tconfig.getString(Constants.KEY_AD_PORT)
                + "/" + tconfig.getString(Constants.KEY_AD_ROOT_DN);

        log.info("Using LDAP url: [" + ldapProvider + "]");

//        String url, String contextFactoryName,

        Hashtable jndiEnv = new Hashtable();

        jndiEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        jndiEnv.put(Context.PROVIDER_URL, ldapProvider);
        jndiEnv.put(Context.REFERRAL, "follow");

        if (tconfig.getBoolean(Constants.KEY_AD_SSL)) {
            log.info("Using SSL for LDAP");
            jndiEnv.put(Context.SECURITY_PROTOCOL, "ssl");
        }
        jndiEnv.put(Context.SECURITY_AUTHENTICATION, "simple");

        if (principal != null)
            jndiEnv.put(Context.SECURITY_PRINCIPAL, principal);

        if (credentials != null)
            jndiEnv.put(Context.SECURITY_CREDENTIALS, credentials);

        try {
            // Creating the JNDI directory context (with LDAP context
            // factory), performs an LDAP bind to the LDAP provider thereby
            // authenticating the username/pw.
            ctx = new InitialDirContext(jndiEnv);
        } catch (NamingException ex) {
            log.error("Directory context init failed", ex);
            throw ex;
        }

        return ctx;
    }

    public static void safeClose(Context context) {
        if (context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                log.error("safeClose()", e);
            }
        } else {

        }
    }

    public static Object lookup(Context jndi, String name) throws NamingException {
//        String l_name = cleanName(p_name);

        Object retObj = jndi.lookup(name);

        if (log.isDebugEnabled()) {
            try {
                StringBuilder sbLog = new StringBuilder();

                NameParser parser = jndi.getNameParser(name);
                Name jndiName = parser.parse(name);
                List lNames = Collections.list(jndiName.getAll());
                if (lNames != null) {
                    Iterator iNames = lNames.iterator();
                    while (iNames.hasNext()) {
                        String elemName = (String) iNames.next();
                        Strings.appendLine(sbLog, "name [" + elemName + "]");
                    }
                }
                log.debug("Jndi parse of [" + name + "] => " + sbLog);
            } catch (NamingException e) {
                log.warn("Error in getting JNDI name info", e);
            }
        }

        return retObj;
    }

    private static CharReplacer dnsToJndi = new CharReplacer(
            new String[][]{{".", "_"}});
    private static final String ldapTopAttrs[] = new String[]{LDAP_DISTINGUISHEDNAME, LDAP_DC};
    private static final String ldapUserAttrs[] = new String[]
            {LDAP_USER_MAIL, LDAP_USER_DISPLAYNAME, LDAP_USER_SAMACCOUNTNAME,
                    LDAP_USER_GIVENNAME, LDAP_USER_LASTNAME};

    public static String cleanName(String inName) {
//        return dnsToJndi.replace(inName);
        return Strings.replaceAll(inName, ".", "_");
    }

    public static void logLdap(Log plog, int level, int nth, Object dirEntry)
            throws NamingException {
        try {
            if (dirEntry instanceof NamingEnumeration) {
                NamingEnumeration nameEnum = (NamingEnumeration) dirEntry;
                JndiHelper.logLevel(plog, level, nth, "Naming Enumeration: " + nameEnum);
                try {
                    int nthThis = 0;
                    List nameList = new Vector(Collections.list(nameEnum));
                    Collections.sort(nameList, new Comparator() {
                        public int compare(Object o1, Object o2) {
                            if (o1 instanceof Attribute) {
                                return String.CASE_INSENSITIVE_ORDER.compare(
                                        ((Attribute) o1).getID(),
                                        ((Attribute) o2).getID());
                            }
                            return 0;
                        }
                    });
                    Iterator nameIter = nameList.iterator();
                    while (nameIter.hasNext()) {
                        logLdap(plog, level + 1, nthThis++, nameIter.next());
                    }
                } catch (NamingException ex) {
                    plog.error("Exception iterating thru NamingEnumeration: "
                            + ex.getMessage());
                }
            } else if (dirEntry instanceof Attribute) {
                Attribute dirAttr = (Attribute) dirEntry;
                JndiHelper.logLevel(plog, level, nth, "Attribute: [" + dirAttr + "]");
            } else if (dirEntry instanceof DirContext) {
                DirContext lctx = (DirContext) dirEntry;
                JndiHelper.logLevel(plog, level,
                        nth, "LDAP Context: DN [" + lctx.getNameInNamespace() + "]" +
                                " Attributes ==>");
                logLdap(plog, level, nth, lctx.getAttributes("").getAll());
            } else if (dirEntry instanceof SearchResult) {
                SearchResult sr = (SearchResult) dirEntry;
                JndiHelper.logLevel(plog, level,
                        nth, "SearchResult: ClassName of Bound Object [" + sr.getClassName() + "]" +
                                " Name: [" + sr.getName() + "]" +
                                " Bound Object ==>");
//                sr.s
                logLdap(plog, level, nth, sr.getObject());
                logLdap(plog, level, nth, sr.getAttributes().getAll());
            } else {
                JndiHelper.logLevel(plog, level,
                        nth, "(?) class of entry: [" + dirEntry + "]");
            }
            nth++;
        } catch (NamingException e1) {
            plog.error("Naming Exception (will try to continue): "
                    + e1.getMessage());
        }
    }

    public static void logLevel(Log plog, int level, int nth, String msg) {
        plog.info(Strings.multiplyString("\t", level)
                + "Dir Entry [" + nth + "]: "
                + msg);
    }

    //----------------------------------------------------------------------------
    // Private instance vars
    //----------------------------------------------------------------------------

    //----------------------------------------------------------------------------
    // Constructor(s)
    //----------------------------------------------------------------------------

    //----------------------------------------------------------------------------
    // Instance methods
    //----------------------------------------------------------------------------

}
