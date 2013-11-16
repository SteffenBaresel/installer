/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.siv.ksc.profiles;

import de.siv.ksc.modules.Basics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author sbaresel
 */
public class Functions {
    static Properties props = null;
    /**
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    static public void CreateTableStructure() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        /*
         * Definition Tables
         */
        st.execute("CREATE TABLE profiles_user ( UUID BIGSERIAL UNIQUE, USNM varchar(100), USDC varchar(100), UMAI varchar(100), UPIC varchar(100) )");
        st.execute("CREATE TABLE profiles_role ( RLID BIGSERIAL UNIQUE, RLNM varchar(100), RLDE varchar(500) )");
        st.execute("CREATE TABLE profiles_privilege ( PRID BIGSERIAL UNIQUE, PRNM varchar(25),	PRDC varchar(500) )");
        st.execute("CREATE TABLE profiles_group ( GRID BIGSERIAL UNIQUE, GRNM varchar(100), GRDC varchar(500) )");
        st.execute("CREATE TABLE profiles_task ( TKID BIGSERIAL UNIQUE, TKNM varchar(100), TKDC varchar(5000) )");
        /*
         * Mapping Tables
         */
        st.execute("CREATE TABLE profiles_user_group_mapping ( UUID BIGSERIAL, GRID BIGSERIAL )");
        st.execute("CREATE TABLE profiles_group_role_mapping ( GRID BIGSERIAL, RLID BIGSERIAL )");
        st.execute("CREATE TABLE profiles_user_role_mapping ( UUID BIGSERIAL, RLID BIGSERIAL )");
        st.execute("CREATE TABLE profiles_role_priv_mapping ( RLID BIGSERIAL, PRID BIGSERIAL )");
        st.execute("CREATE TABLE profiles_user_task_mapping ( UUID BIGSERIAL, TKID BIGSERIAL )");
        st.execute("CREATE TABLE profiles_customer_task_mapping ( CUID BIGSERIAL, TKID BIGSERIAL )");
        /*
         * Info Tables
         */
        st.execute("CREATE TABLE info_system ( SYSID BIGSERIAL UNIQUE, KEY varchar(100), VAL varchar(500) )");
        st.execute("CREATE TABLE info_contracts ( COTRID SERIAL UNIQUE, CUSTID integer, CTTYID integer, COTRNO decimal )");
        st.execute("CREATE TABLE info_maintenance ( MATNID BIGSERIAL UNIQUE, COTRNO decimal, AUTHOR varchar(20), COMTID integer, COMMENT varchar(5000), DELAY decimal, ESCALATION decimal, UTIME decimal )");
        /*
         * Config Tables
         */
        st.execute("CREATE TABLE config_portal ( CPID BIGSERIAL UNIQUE, USR varchar(100), MOD varchar(100), KEY varchar(100), VAL1 varchar(500), VAL2 varchar(500), VAL3 varchar(500) )");
        st.execute("CREATE TABLE config_gateway ( CGID BIGSERIAL UNIQUE, MOD varchar(100), KEY varchar(100), VAL varchar(100) )");
        /*
         * Class Tables
         */
        st.execute("CREATE TABLE class_contracttypes ( CTTYID SERIAL UNIQUE, COTRSN varchar(20), COTRLN varchar(500), MACTIONS varchar(5000) )");
        st.execute("CREATE TABLE class_commenttypes ( COMTID SERIAL UNIQUE, COMTSN varchar(20), COMTLN varchar(500) )");
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void FillDefaultValues() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement();
        /*
         * System Information
         */
        st.execute("INSERT INTO info_system (KEY,VAL) values (encode('MAIN_VERSION','base64'),encode('2','base64'))");
        st.execute("INSERT INTO info_system (KEY,VAL) values (encode('UPDATE_VERSION','base64'),encode('3','base64'))");
        st.execute("INSERT INTO info_system (KEY,VAL) values (encode('BUILD_VERSION','base64'),encode('131116','base64'))");
        st.execute("INSERT INTO info_system (KEY,VAL) values (encode('FIRST_RUN','base64'),encode('1','base64'))");
        /*
         * Default Group
         */
        st.execute("INSERT INTO profiles_group (GRNM,GRDC) values (encode('admin','base64'),encode('Administrator','base64'))");
        st.execute("INSERT INTO profiles_group (GRNM,GRDC) values (encode('operator','base64'),encode('Operator','base64'))");
        /*
         * Default Role
         */
        st.execute("INSERT INTO profiles_role (RLNM,RLDE) values (encode('full','base64'),encode('Vollzugriff','base64'))");
        st.execute("INSERT INTO profiles_role (RLNM,RLDE) values (encode('operating','base64'),encode('Teilzugriff','base64'))");
        /*
         * Default Privileges
         */
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('all','base64'),encode('Zugriff auf Alles','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('dashboard','base64'),encode('Anzeige des Dashbaord Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('gateway','base64'),encode('Zugriff auf das Gateway Modul','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('liveticker','base64'),encode('Zugriff auf den Liveticker','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('sidebarsearch','base64'),encode('Zugriff auf das Sidebar Suche Modul','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('sidebarfunctions','base64'),encode('Zugriff auf die Sidebar Funktionen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('host','base64'),encode('Anzeige des Hosts Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('service','base64'),encode('Anzeige des Service Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('database','base64'),encode('Anzeige des Database Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('middleware','base64'),encode('Anzeige des Middleware Moduls','base64'))");
        /*
         * Group Role Mapping
         */
        st.execute("INSERT INTO profiles_group_role_mapping (GRID,RLID) values ('1','1')");
        st.execute("INSERT INTO profiles_group_role_mapping (GRID,RLID) values ('2','2')");
        /*
         * Role Privilege Mapping
         */
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','1')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','2')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','3')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','4')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','5')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','7')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','8')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','9')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','10')");
        /*
         * Contract types
         */
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('IH','base64'),encode('InHouse','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('BF','base64'),encode('Betriebsführung','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('SB','base64'),encode('Systembetreuung','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('DBB','base64'),encode('Datenbank Bronze Wartungsvertrag','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('DBS','base64'),encode('Datenbank Silber Wartungsvertrag','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('IASB','base64'),encode('IAS Bronze Wartungsvertrag','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('IASS','base64'),encode('IAS Silber Wartungsvertrag','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('WLFB','base64'),encode('Weblogic Server Forms/Reports Bronze Wartung','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('WLFS','base64'),encode('Weblogic Server Forms/Reports Silber Wartung','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('SBB','base64'),encode('SOA/BAM Server Bronze Wartung','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('SBS','base64'),encode('SOA/BAM Server Silber Wartung','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('PG','base64'),encode('Progov Supportvertrag','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('NW','base64'),encode('Networker Supportvertrag','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('WS','base64'),encode('Webservice Monitoring','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('ASP','base64'),encode('Application Service Provider','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('BSP','base64'),encode('Business Service Provider','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('HTG','base64'),encode('Hosting Rechenzentrum Stralsund','base64'),'')");
        st.execute("INSERT INTO class_contracttypes (COTRSN,COTRLN,MACTIONS) VALUES (encode('KPM','base64'),encode('Kundenportal Monitoring','base64'),'')");
        /*
         * Comment types
         */
        st.execute("INSERT INTO class_commenttypes (COMTSN,COMTLN) VALUES (encode('TEL','base64'),encode('Telefonat','base64'))");
        st.execute("INSERT INTO class_commenttypes (COMTSN,COMTLN) VALUES (encode('MAI','base64'),encode('Mail','base64'))");
        st.execute("INSERT INTO class_commenttypes (COMTSN,COMTLN) VALUES (encode('INF','base64'),encode('Information','base64'))");
        st.execute("INSERT INTO class_commenttypes (COMTSN,COMTLN) VALUES (encode('INT','base64'),encode('Intern','base64'))");
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void CreateAdminUser(String ShortName, String LongName) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        /*
         * Create User
         */
        st.execute("DELETE FROM profiles_user WHERE uuid = 1");
        st.execute("INSERT INTO profiles_user(UUID,USNM,USDC) values ('1','" + ShortName + "','" + LongName + "')");
        /*
         * add to Group
         */
        st.execute("INSERT INTO profiles_user_group_mapping(UUID,GRID) values ( '1', '1' )");
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void ConfigureMailApi(String Host, String Port, String User, String Pass) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        /*
         * Create Configuration entries
         */
        st.execute("DELETE FROM config_gateway WHERE MOD like encode('MAILAPI','base64')");
        st.execute("INSERT INTO config_gateway(MOD,KEY,VAL) VALUES (encode('MAILAPI','base64'), encode('HOST','base64'), '" + Host + "')");
        st.execute("INSERT INTO config_gateway(MOD,KEY,VAL) VALUES (encode('MAILAPI','base64'), encode('PORT','base64'), '" + Port + "')");
        st.execute("INSERT INTO config_gateway(MOD,KEY,VAL) VALUES (encode('MAILAPI','base64'), encode('USER','base64'), '" + User + "')");
        st.execute("INSERT INTO config_gateway(MOD,KEY,VAL) VALUES (encode('MAILAPI','base64'), encode('PASS','base64'), '" + Pass + "')");
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void DropTableStructure() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        /*
         * Definition Tables
         */
        st.execute("DROP TABLE profiles_user");
        st.execute("DROP TABLE profiles_role");
        st.execute("DROP TABLE profiles_privilege");
        st.execute("DROP TABLE profiles_group");
        st.execute("DROP TABLE profiles_task");
        /*
         * Mapping Tables
         */
        st.execute("DROP TABLE profiles_user_group_mapping");
        st.execute("DROP TABLE profiles_group_role_mapping");
        st.execute("DROP TABLE profiles_user_role_mapping");
        st.execute("DROP TABLE profiles_role_priv_mapping");
        st.execute("DROP TABLE profiles_user_task_mapping");
        st.execute("DROP TABLE profiles_customer_task_mapping");
        /*
         * Info Tables
         */
        st.execute("DROP TABLE info_system");
        st.execute("DROP TABLE info_contracts");
        st.execute("DROP TABLE info_maintenance");
        /*
         * Config Tables
         */
        st.execute("DROP TABLE config_portal");
        st.execute("DROP TABLE config_gateway");
        /*
         * Class Tables
         */
        st.execute("DROP TABLE class_contracttypes");
        st.execute("DROP TABLE class_commenttypes");
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public String IsAlreadyInstalled() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        String line = "0";
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        ResultSet rs  = st.executeQuery("SELECT * FROM info_system where KEY like encode('MAIN_VERSION','base64')");
        while ( rs.next() ) { 
           line = "1";
        }
        
        /*
         * Close Connection
         */
        cn.close();
        return line;
    }
    
    static public String MailApiIsAlreadyConfigured() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        String line = "0";
        String out = "";
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        ResultSet rs  = st.executeQuery("select decode(key,'base64'),val FROM config_gateway where MOD like encode('MAILAPI','base64')");
        while ( rs.next() ) {
           line = "1";
           out += "\""+ rs.getString( 1 ) + "\":\"" + rs.getString( 2 ) + "\",";
        }
        if ( "1".equals(line) ) {
            out = out.substring(0, out.length()-1);
            line += "\"," + out;
        } else {
            line += "\"";
        }
        /*
         * Close Connection
         */
        cn.close();
        return line;
    }
    
    static public String AdminUserIsAlreadyConfigured() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        String line = "0";
        String out = "";
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        ResultSet rs  = st.executeQuery("select usnm,usdc FROM profiles_user where UUID = 1");
        while ( rs.next() ) {
           line = "1";
           out += "\"SN\":\""+ rs.getString( 1 ) + "\",\"LN\":\"" + rs.getString( 2 ) + "\",";
        }
        if ( "1".equals(line) ) {
            out = out.substring(0, out.length()-1);
            line += "\"," + out;
        } else {
            line += "\"";
        }
        /*
         * Close Connection
         */
        cn.close();
        return line;
    }
}
