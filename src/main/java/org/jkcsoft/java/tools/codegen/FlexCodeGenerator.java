/*
 * Copyright (c) Jim Coles (jameskcoles@gmail.com) 2016. through present.
 *
 * Licensed under the following license agreement:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Also see the LICENSE file in the repository root directory.
 */
package org.jkcsoft.java.tools.codegen;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.jkcsoft.java.util.CommandLine;
import org.jkcsoft.java.util.LogHelper;
import org.jkcsoft.java.util.Strings;

import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * @author coles
 */
public class FlexCodeGenerator {

    private static final String FILE_CHARSET = "ISO-8859-1";

    public static void main(String[] args) {
        LogHelper.init();
        try {
            FlexCodeGenerator vcg = new FlexCodeGenerator();
            vcg.run(args);
        } catch (Throwable e) {
            LogHelper.getLogger(FlexCodeGenerator.class).fatal("Last ditch", e);
        }
    }

    private void run(String[] args) throws Exception {
        CommandLine cl = new CommandLine(args, new String[]{"outDir", "templateDir"});

        Properties p = new Properties();
        p.setProperty("file.resource.loader.path", cl.value("tempateDir", "./code-gen-templates"));
        Velocity.init(p);

        /* lets make a Context and put data into it */

//        DriverManager.registerDriver(new oracle.jdbc.OracleDriver());

        Connection conn =
                DriverManager.getConnection(
                        "jdbc:oracle:thin:@tstux03:1540:DEV1",
                        "basecamp",
                        "basecamp");

        LogHelper.info(this, "Connected to db: " + conn.getMetaData().getURL());
/*
 * See java.sql.DatabaseMetaData javadoc.
 */
        DatabaseMetaData dmd = conn.getMetaData();
        File outDir = new File(cl.value("outDir", "./gen-out/"));

        File fOjbReposXml = new File(outDir, "repository_user.xml");
        FileOutputStream fosOjbReposXml = new FileOutputStream(fOjbReposXml);
        StringWriter swOjbReposXml = new StringWriter();

        File fXsd = new File(outDir, "FlexElements.xsd");
        FileOutputStream fosXsd = new FileOutputStream(fXsd);
        StringWriter swXsd = new StringWriter();

        File fFlexModel = new File(outDir, "FlexModel-gen.xml");
        FileOutputStream fosFlexModel = new FileOutputStream(fFlexModel);
        StringWriter swFlexModel = new StringWriter();

        // Start process of getting RDB meta data
        ResultSet rsTables = dmd.getTables(null, null, "BC_%", null);

        LogHelper.info(this, "Finding BC_ tables...");
        List allTables = new Vector();
        while (rsTables.next()) {
            String tableName = rsTables.getString("TABLE_NAME");
            allTables.add(tableName);
            LogHelper.info(this, "Found Table: " + tableName);
        }

        // Meta tables only
        List metaTables = new Vector();
        metaTables.add("BC_PRIMITIVE_TYPE");
        metaTables.add("BC_RELATIONSHIP_TYPE");
        metaTables.add("BC_PROCESS_NODE_TYPE");
        metaTables.add("BC_CONSTRAINT_TYPE");

        metaTables.add("BC_ENTITY_TYPE");
        metaTables.add("BC_ATTRIBUTE");
        metaTables.add("BC_TYPE_RELATIONSHIP");
        metaTables.add("BC_PROCEDURE");

        metaTables.add("BC_BUSINESS_PROCESS");
        metaTables.add("BC_PROCESS_NODE");
        metaTables.add("BC_NODE_CONSTRAINT");
        metaTables.add("BC_ACTIVITY_ATTR_CONSTRAINT");

        metaTables.add("BC_TYPE_ENUMERATION");

        // Other system tables (other than meta tables)
        List sysTables = new Vector();
        sysTables.add("BC_ACCESS_GRANT");
        sysTables.add("BC_ATTRIBUTE_CHANGE_HISTORY");
        sysTables.add("BC_BUSINESS_ROLE");
        sysTables.add("BC_DATE_ATTR_VALUE");
        sysTables.add("BC_ENTITY_PROCESS_STATUS");
        sysTables.add("BC_FLEX_MODEL");
        sysTables.add("BC_NUMBER_ATTR_VALUE");
        sysTables.add("BC_PROCESS_CHANGE_HISTORY");
        sysTables.add("BC_STRING_ATTR_VALUE");

        sysTables.add("BC_USER");


        // Primary Entity tables
        List entityTables = new Vector();
        entityTables.add("BC_WORK_REQUEST");
        entityTables.add("BC_GENERAL_WORK_REQUEST");
        entityTables.add("BC_PERSON");
        entityTables.add("BC_SERVICEABLE_ITEM");
//        entityTables.add("BC_GROUP");

        List metaCols = new Vector();
        metaCols.add("OID");
        metaCols.add("CODE_NAME");
        metaCols.add("DESCRIPTION");
        metaCols.add("ACTIVE_FLAG");
        metaCols.add("DISPLAY_NAME");
        metaCols.add("CREATION_DATE");
        metaCols.add("LAST_UPDATED_DATE");

        Collections.sort(allTables, String.CASE_INSENSITIVE_ORDER);

        Iterator iTables = allTables.iterator();
        while (iTables != null && iTables.hasNext()) {
            String tableName = (String) iTables.next();

            boolean isMetaTable = metaTables.contains(tableName);
            boolean isEntity = entityTables.contains(tableName);
            boolean isSystem = sysTables.contains(tableName);

            String className = rdbToJava(tableName, "BC_", false);

            File fClassJavaFile = null;
            File classOutDir = outDir;
            if (isMetaTable || isSystem) {
                classOutDir = new File(outDir, "repos");
                classOutDir.mkdir();
            }
            fClassJavaFile = new File(classOutDir, className + ".java");
            FileOutputStream fosClassJavaFile = new FileOutputStream(fClassJavaFile);

            ResultSet rsColumns = dmd.getColumns(null, null, tableName, null);

//            ResultSet rsPks = dmd.getPrimaryKeys(null, null, tableName);

            ResultSet rsFks = dmd.getImportedKeys(null, null, tableName);
            Map colToTable = new TreeMap();
            while (rsFks.next()) {
                String fkToTable = rsFks.getString("PKTABLE_NAME");
                String colName = rsFks.getString("FKCOLUMN_NAME");
                LogHelper.debug(this, "Found FK: " + tableName + " " + colName + " " + fkToTable);
                colToTable.put(colName, fkToTable);
            }

            List columns = new Vector();
            while (rsColumns.next()) {
                String colName = rsColumns.getString("COLUMN_NAME");
                String fkToTable = (String) colToTable.get(colName);
                if (fkToTable != null) LogHelper.debug(this, "Setting FK in ColumnInfo");

                boolean isMetaCol = metaCols.contains(colName);
                columns.add(new ColumnInfo(rsColumns.getString("COLUMN_NAME"),
                        rsColumns.getInt("DATA_TYPE"),  // java.sql.Types
                        rsColumns.getInt("COLUMN_SIZE"),
                        rsColumns.getString("IS_NULLABLE").equalsIgnoreCase("yes"),
                        fkToTable,
                        isMetaCol));
            }

            // do template thing
            VelocityContext velocityContext = new VelocityContext();
            velocityContext.put("date", new java.util.Date());
            velocityContext.put("classname", className);
            velocityContext.put("tablename", tableName);
            velocityContext.put("columns", columns);
            velocityContext.put("mapper", this);
            String packageName = "com.sbc.labs.basecamp.model.entities";
            if (isMetaTable || isSystem) packageName = "com.sbc.labs.basecamp.flex.repos";
            velocityContext.put("packagename", packageName);

            StringWriter swJavaClass = new StringWriter();

            if (isMetaTable)
                Velocity.mergeTemplate("table-2-meta-class.vm", FILE_CHARSET, velocityContext, swJavaClass);
            else
                Velocity.mergeTemplate("table-2-class.vm", FILE_CHARSET, velocityContext, swJavaClass);

            Velocity.mergeTemplate("table-2-ojb-decl.vm", FILE_CHARSET, velocityContext, swOjbReposXml);

            if (isMetaTable) {
                Velocity.mergeTemplate("table-2-xsd.vm", FILE_CHARSET, velocityContext, swXsd);
            }

            if (isEntity) {
                Velocity.mergeTemplate("table-2-flex-meta.vm", FILE_CHARSET, velocityContext, swFlexModel);
            }

            saveAndClose(fClassJavaFile, fosClassJavaFile, swJavaClass);

//            LogHelper.debug(this, "Output => " + swJavaClass);
        }

        conn.close();

//        LogHelper.debug(this, "Output => " + swOjbReposXml);

        saveAndClose(fOjbReposXml, fosOjbReposXml, swOjbReposXml);

        saveAndClose(fXsd, fosXsd, swXsd);

        saveAndClose(fFlexModel, fosFlexModel, swFlexModel);

//        context.put("project", "Jakarta");

//        /* lets make our own string to render */

//        String s = "We are using $project $name to render this.";
//        w = new StringWriter();
//        Velocity.evaluate( context, w, "mystring", s );
//        LogHelper.debug(this, " Output 2 => " + w );
    }

    /**
     * @param file
     * @param fos
     * @param sw
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    private void saveAndClose(File file, FileOutputStream fos, StringWriter sw) throws IOException, UnsupportedEncodingException {
        fos.write(sw.toString().getBytes(FILE_CHARSET));
        fos.close();
        LogHelper.info(this, "Wrote: " + file.getAbsolutePath());
    }

    /**
     * XYZ_OID --> XYZ_NAME
     *
     * @param rdbColName
     * @return
     */
    public String toByNameIdentifier(String rdbColName) {
        return Strings.replaceAll(rdbColName, "_OID", "_NAME");
    }

    /**
     * ABC_XYZ --> AbcXyz
     *
     * @param rdbName
     * @return
     */
    public String rdbToJava(String rdbName, String rdbPrefix, boolean propName) {
        String javaName = "";
        if (Strings.isNotEmpty(rdbPrefix)) {
            rdbName = Strings.restAfterFirst(rdbName, rdbPrefix);
        }

        char[] rdbChars = rdbName.toCharArray();
        char prevChar = ' ';
        boolean isPrevCharWhitespace = true;
        for (int idxChar = 0; idxChar < rdbChars.length; idxChar++) {
            char rdbChar = rdbChars[idxChar];
            boolean isWhitespace = rdbChar == ' ' || rdbChar == '_';

            if (!isWhitespace) {
                if (isPrevCharWhitespace && (idxChar != 0 || !propName)) {
                    javaName += Character.toUpperCase(rdbChar);
                } else {
                    javaName += Character.toLowerCase(rdbChar);
                }
            }

            //
            isPrevCharWhitespace = isWhitespace;
            prevChar = rdbChar;
        }


        return javaName;
    }

    public String rdbToJava(String rdbName, boolean isPropName) {
        return rdbToJava(rdbName, null, isPropName);
    }

    public Class getJavaType(int sqlType) {
        Class clazz = null;
        switch (sqlType) {
            case Types.INTEGER:
                clazz = Integer.class;
                break;
            case Types.DECIMAL:
                clazz = Long.class;
                break;
            case Types.BIGINT:
                clazz = Long.class;
                break;
            case Types.VARCHAR:
                clazz = String.class;
                break;
            case Types.TIMESTAMP:
            case Types.DATE:
            case Types.TIME:
                clazz = Date.class;
                break;
            default:
                clazz = String.class;
        }

        return clazz;
    }

    public String getJavaTypeId(int sqlType) {
        Class clazz = getJavaType(sqlType);

        if (clazz.getName().startsWith("java.lang")) {
            return Strings.restAfterFirst(clazz.getName(), "java.lang.");
        } else {
            clazz.isPrimitive();
            return clazz.getName();
        }
    }

    /**
     * @param wrapperClass
     * @return The primitive java.lang.Class associate with the wrapper.
     * @see java.lang.Boolean#TYPE
     * @see java.lang.Character#TYPE
     * @see java.lang.Byte#TYPE
     * @see java.lang.Short#TYPE
     * @see java.lang.Integer#TYPE
     * @see java.lang.Long#TYPE
     * @see java.lang.Float#TYPE
     * @see java.lang.Double#TYPE
     * @see java.lang.Void#TYPE
     */
    public Class getPrimitiveClass(Class wrapperClass) {
        Class primClass = null;

        if (Boolean.class.equals(wrapperClass)) {
            primClass = boolean.class;
        } else if (Character.class.equals(wrapperClass)) {
            primClass = char.class;
        } else if (Byte.class.equals(wrapperClass)) {
            primClass = byte.class;
        } else if (Short.class.equals(wrapperClass)) {
            primClass = short.class;
        } else if (Integer.class.equals(wrapperClass)) {
            primClass = int.class;
        } else if (Long.class.equals(wrapperClass)) {
            primClass = long.class;
        } else if (Float.class.equals(wrapperClass)) {
            primClass = float.class;
        } else if (Double.class.equals(wrapperClass)) {
            primClass = double.class;
        } else if (Void.class.equals(wrapperClass)) {
            primClass = void.class;
        } else {
            // return null
        }


        return primClass;
    }

    public boolean isPrimitiveClass(Class clazz) {
        return getPrimitiveClass(clazz) != null;
    }

    public String getXsdType(int sqlType) {
        String xsdType = null;
        switch (sqlType) {
            case Types.INTEGER:
                xsdType = "xsd:integer";
                break;
            case Types.DECIMAL:
                xsdType = "xsd:long";
                break;
            case Types.BIGINT:
                xsdType = "xsd:long";
                break;
            case Types.VARCHAR:
                xsdType = "xsd:string";
                break;
            case Types.TIMESTAMP:
            case Types.DATE:
            case Types.TIME:
                xsdType = "xsd:date";
                break;
            default:
                xsdType = "xsd:string";
        }

        return xsdType;
    }

}
