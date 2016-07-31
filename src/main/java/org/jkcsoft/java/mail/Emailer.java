/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.mail;

import org.apache.commons.logging.Log;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.jkcsoft.java.systems.components.AbstractSystemNode;
import org.jkcsoft.java.systems.components.Application;
import org.jkcsoft.java.systems.components.SystemNode;
import org.jkcsoft.java.systems.components.SystemState;
import org.jkcsoft.java.systems.events.Event;
import org.jkcsoft.java.util.Dates;
import org.jkcsoft.java.util.LogHelper;
import org.jkcsoft.java.util.Strings;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.StringWriter;
import java.util.Date;
import java.util.Properties;

public class Emailer extends AbstractSystemNode {
    //-----------------------------------------------------------------
    // Statics
    //-----------------------------------------------------------------
    private static final String ENC_CP1252 = "Cp1252";

    public static final Log log = LogHelper.getLogger(Emailer.class);

    public static final String MIME_TEXT = "text/plain";
    public static final String MIME_HTML = "text/html";
    public static final String KEY_SMTP_HOST = "mail.smtp.host";

    private static final int MAXTRIES = 2;

    private static Emailer instance;

    public static Emailer constructInstance(SystemNode parent, String smtpHost) throws Exception {
        instance = new Emailer(parent, smtpHost);
        return instance;
    }

    //-----------------------------------------------------------------
    // Instance vars
    //-----------------------------------------------------------------
//    private String smtpHost;
    private Properties javaMailProps = new Properties();
    private Application application;

    //-----------------------------------------------------------------
    // Constructor(s)
    //-----------------------------------------------------------------
    private Emailer(SystemNode parent, String smtpHost) throws Exception {
        super(parent);
        //
        javaMailProps.put(KEY_SMTP_HOST, smtpHost);
        setState(SystemState.CONNECTION_INIT);

        // init velocity template sub-system...
        try {
            Properties velocityProps = new Properties();
            velocityProps.setProperty("file.resource.loader.path",
                    new StringBuilder().append(application.getHomeDir())
                            .append(File.separator).append("email").toString());
            Velocity.init(velocityProps);
        } catch (Throwable ex) {
            LogHelper.warn(this, "Could not init Velocity for email templates", ex);
        }
    }

    //-----------------------------------------------------------------
    // Instance methods
    //-----------------------------------------------------------------

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public String getSmtpHostName() {
        return javaMailProps.getProperty(KEY_SMTP_HOST);
    }

    public void sendTemplateMsg(String to, String toName,
                                String from, String fromName,
                                String subject,
                                String body,
                                String mimeType,
                                VelocityContext velocityContext) throws Exception {
        String subjectFinal = getContent(subject, velocityContext, false);
        String bodyFinal = getContent(body, velocityContext, true);

        sendMsg(to, toName, from, fromName, subjectFinal, bodyFinal, mimeType);
    }

    public void sendTemplateMsg(String to, String toName,
                                String from, String fromName,
                                String subject,
                                String body,
                                VelocityContext velocityContext) throws Exception {
        sendTemplateMsg(to, toName, from, fromName, subject, body, MIME_TEXT, velocityContext);
    }

    public void sendTemplateMsg(String to, String toName,
                                String from, String fromName,
                                String subject,
                                String body) throws Exception {
        sendTemplateMsg(to, toName, from, fromName, subject, body, MIME_TEXT, null);
    }

    private String getContent(String contentInput, VelocityContext velocityContext, boolean addSignature)
            throws Exception {
        String returnContent = null;
        if (contentInput.endsWith(".vm")) {
            StringWriter contentWriter = new StringWriter(100);
            writeContent(contentInput, velocityContext, contentWriter);

            if (addSignature)
                writeContent("system-signature.vm", velocityContext, contentWriter);

            returnContent = contentWriter.toString();
        } else {
            returnContent = contentInput;
        }
        return returnContent;
    }

    private void writeContent(String contentInput, VelocityContext velocityContext, StringWriter contentWriter) throws ResourceNotFoundException, ParseErrorException, MethodInvocationException, Exception {
        if (velocityContext == null) {
            velocityContext = new VelocityContext();
        }
        // Add standard email variables to list...
        velocityContext.put("DATE_LONG", Dates.toDateString(Dates.nowDate(), Dates.FMTR_DEFAULT_LONG_FORMAT));

/* TODO: decide where to put 'meta' data -- fixed, variable, etc.
        velocityContext.put("APP_INSTANCE_NAME",
                            application.getRawConfig()
                            .getString(APP_INSTANCE_NAME, "(not set)"));
        velocityContext.put("TSESS_URL",
                            TsessUpsGateway.getInstance().getRawConfig()
                            .getString(TsessUpsGateway.KEY_TSESS_HOME_URL, "(not set)"));
        velocityContext.put("SYS_ADMIN_EMAIL",
                            TsessUpsGateway.getInstance().getRawConfig()
                            .getString(TsessUpsGateway.KEY_ADMIN_EMAIL, "(not set)"));

*/
        Velocity.mergeTemplate(contentInput, ENC_CP1252, velocityContext, contentWriter);
    }

    private void sendMsg(String to, String toName, String from,
                         String fromName, String subject, String msgBody, String strMimeType)
            throws Exception {
        InternetAddress[] toAddrArr = new InternetAddress[]{new InternetAddress(to, toName)};
        InternetAddress fromAddr = new InternetAddress(from, fromName);
        try {
            _sendMsg(toAddrArr, null, fromAddr, subject, msgBody, strMimeType);
        } catch (MessagingException e) {
            setState(SystemState.LAST_CONNECTION_FAILED);
            setStateMessage(e.getMessage());
            LogHelper.error(this, "Sending email", e);
            throw e;
        }
        setState(SystemState.LAST_CONNECTION_OK);
    }


    /**
     * The final funnel point method that actually uses Java Mail (javax.mail.*)
     * API to send the message.
     *
     * @throws MessagingException
     */
    private void _sendMsg(InternetAddress[] to, InternetAddress[] bccList,
                          InternetAddress from, String subject, String msgBody,
                          String strMimeType) throws MessagingException {
        if (Strings.isEmpty(msgBody)) {
            msgBody = "(no email body; see subject)";
        }
        Session session = Session.getDefaultInstance(javaMailProps, null);
        Message msg = new MimeMessage(session);
        msg.setRecipients(Message.RecipientType.TO, to);
        if (bccList != null) {
            msg.setRecipients(Message.RecipientType.BCC, bccList);
        }
        msg.setFrom(from);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setContent(msgBody, strMimeType);
        boolean doTrySend = true;
        int numTries = 0;
        while (doTrySend) {
            numTries++;
            try {
                Transport.send(msg);
                log.info("Sent email; subject=" + subject + " to " + to[0].getAddress() + " ");
                doTrySend = false;
            } catch (MessagingException me) {
                log.warn("Try " + numTries + " of " + MAXTRIES + " failed:", me);
                if (numTries == MAXTRIES) {
                    log.error("Failed to send email", me);
                    throw me;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    LogHelper.error(this, "Retry sleep interrupted", e1);
                }
                doTrySend = numTries < MAXTRIES;
            }
        }
    }

    public String getDisplayName() {
        return "SMTP (Email) Server [" + getSmtpHostName() + "]";
    }

    public void shutdown(Event context) {

    }

    public Thread getRunnerThread() {
        // n/a; no thread...
        return null;
    }
}
