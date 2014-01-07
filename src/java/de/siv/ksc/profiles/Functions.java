/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.siv.ksc.profiles;

import de.siv.ksc.modules.Basics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        st.execute("CREATE TABLE profiles_user ( UUID BIGSERIAL UNIQUE, USNM varchar(100), USDC varchar(100), UMAI varchar(100), UCRT BIGSERIAL, ULAL BIGSERIAL, UACT SERIAL, UPIC bytea, UILI boolean)");
        st.execute("CREATE TABLE profiles_role ( RLID BIGSERIAL UNIQUE, RLNM varchar(100), RLDE varchar(500) )");
        st.execute("CREATE TABLE profiles_privilege ( PRID BIGSERIAL UNIQUE, PRNM varchar(100),	PRDC varchar(500) )");
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
        /*
         * Config Tables
         */
        st.execute("CREATE TABLE config_portal ( CPID BIGSERIAL UNIQUE, UUID BIGSERIAL, MOD varchar(100), KEY varchar(100), VAL1 varchar(100000), VAL2 varchar(100000), VAL3 varchar(100000) )");
        st.execute("CREATE TABLE config_gateway ( CGID BIGSERIAL UNIQUE, MOD varchar(100), KEY varchar(100), VAL varchar(100000) )");
        /*
         * Class Tables
         */
        st.execute("CREATE TABLE class_contracttypes ( CTTYID SERIAL UNIQUE, COTRSN varchar(20), COTRLN varchar(500), MACTIONS varchar(5000) )");
        st.execute("CREATE TABLE class_commenttypes ( COMTID SERIAL UNIQUE, COMTSN varchar(20), COMTLN varchar(500) )");
        st.execute("CREATE TABLE class_entrytypes ( ENID SERIAL UNIQUE, ENSN varchar(20), ENLN varchar(500) )");
        /*
         * Manged Service Tables
         */
        st.execute("CREATE TABLE managed_service_cinfo ( CUID SERIAL UNIQUE, CUNR BIGSERIAL, CUNM varchar(10000), CUADDR varchar(1000), CUMAIL varchar(1000), CUESKMAIL varchar(1000), CUCOMM varchar(100000) )");
        st.execute("CREATE TABLE managed_service_cservices ( MSID BIGSERIAL UNIQUE, CUID BIGSERIAL, CCID BIGSERIAL, ENID BIGSERIAL, COMTID BIGSERIAL, UUID BIGSERIAL, COMT varchar(100000), DELAY BIGSERIAL, UTIM BIGSERIAL, ESK SERIAL )");
        st.execute("CREATE TABLE managed_service_ccontracts ( CCID BIGSERIAL UNIQUE, CUID SERIAL, CCNR BIGSERIAL, CCPRVE varchar(1000), CCPRDC varchar(1000), CTTYID SERIAL )");
        /*
         * Views
         */
        st.execute("CREATE VIEW autocompletecustomer AS SELECT cuid,cunr,decode(CUNM,'base64') as cunm FROM managed_service_cinfo");
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
         * Default info
         */
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
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('dashboard','base64'),encode('Anzeige des Dashbaord Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('gateway','base64'),encode('Zugriff auf das Gateway Modul','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('liveticker','base64'),encode('Zugriff auf den Liveticker','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('sidebarsearch','base64'),encode('Zugriff auf das Sidebar Suche Modul','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('sidebarfunctions','base64'),encode('Zugriff auf die Sidebar Funktionen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('host','base64'),encode('Anzeige des Hosts Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('service','base64'),encode('Anzeige des Service Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('database','base64'),encode('Anzeige des Database Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('middleware','base64'),encode('Anzeige des Middleware Moduls','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('addlink','base64'),encode('Weiterer Link im Dashboard','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config','base64'),encode('Konfgurieren der Anwendung','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_web','base64'),encode('Webeinstellungen konfigurieren','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_web_dashboard','base64'),encode('Webeinstellungen User Dashboard','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_web_properties','base64'),encode('User Einstellungen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_web_reset','base64'),encode('Webeinstellungen User zuruecksetzen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('sidebar','base64'),encode('Zeige Side Bar','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('bottombar','base64'),encode('Zeige Bottom Bar','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('profile','base64'),encode('Bearbeiten der Profileinstellungen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_usermanagement','base64'),encode('Zugang Nutzerverwaltung','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_usermanagement_user_edit','base64'),encode('Nutzerverwaltung: User bearbeiten','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_usermanagement_group_edit','base64'),encode('Nutzerverwaltung: Gruppen bearbeiten','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_usermanagement_privilege_edit','base64'),encode('Nutzerverwaltung: Privilegien bearbeiten','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services','base64'),encode('Zugang: Management Service','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_ki','base64'),encode('Kunden Information anzeigen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_nka','base64'),encode('Kunden erstellen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_kb','base64'),encode('Kunden bearbeiten','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_kl','base64'),encode('Kunden l&ouml;schen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_vae','base64'),encode('Vertragsarbeiten erstellen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_vab','base64'),encode('Vertragsarbeiten bearbeiten','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_val','base64'),encode('Vertragsarbeiten l&ouml;schen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_csr','base64'),encode('Servicearbeiten anzeigen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_csw','base64'),encode('Servicesarbeiten eintragen','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('managed_services_wili','base64'),encode('Wer ist angemeldet','base64'))");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('config_mail','base64'),encode('Mail Format Einstellungen','base64'))");
        
        /*
         * Group Role Mapping
         */
        st.execute("INSERT INTO profiles_group_role_mapping (GRID,RLID) values ('1','1')");
        st.execute("INSERT INTO profiles_group_role_mapping (GRID,RLID) values ('2','2')");
        /*
         * Role Privilege Mapping
         */
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','1')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','2')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','3')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','4')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','5')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','6')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','7')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','8')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','9')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','10')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','11')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','12')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','13')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','14')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','15')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','16')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','17')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','18')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','19')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','20')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','21')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','22')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','23')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','24')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','25')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','26')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','27')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','28')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','29')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','30')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','31')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','32')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','33')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','34')");
        
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','2')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','3')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','4')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','6')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','7')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','8')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','9')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','11')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','12')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','13')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','14')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','15')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','16')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','17')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','18')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','23')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','24')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','25')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','26')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','27')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','28')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','29')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','30')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','31')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','32')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','33')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','24')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','31')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','32')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('1','33')");
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
         * Entry types
         */
        st.execute("INSERT INTO class_entrytypes (ENSN,ENLN) VALUES (encode('UserEntry','base64'),encode('Nutzer','base64'))");
        st.execute("INSERT INTO class_entrytypes (ENSN,ENLN) VALUES (encode('WApplStatMail','base64'),encode('Wartungsapplikation','base64'))");
        st.execute("INSERT INTO class_entrytypes (ENSN,ENLN) VALUES (encode('ProfilesTask','base64'),encode('Aufgabenkorb','base64'))");
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
        st.execute("INSERT INTO profiles_user(USNM,USDC,UCRT,ULAL,UPIC,UILI) values ('" + ShortName + "','" + LongName + "','" + System.currentTimeMillis()/1000 + "','0','',FALSE)");
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
        st.execute("INSERT INTO config_gateway(MOD,KEY,VAL) VALUES (encode('MAILAPI','base64'), encode('FOOTER','base64'), encode('<b>initial</b>','base64'))");
        st.execute("INSERT INTO config_gateway(MOD,KEY,VAL) VALUES (encode('MAILAPI','base64'), encode('HEADER','base64'), encode('<b>initial</b>','base64'))");
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
        st.execute("DROP TABLE class_entrytypes");
        /*
         * Manged Service Tables
         */
        st.execute("DROP TABLE managed_service_cinfo");
        st.execute("DROP TABLE managed_service_cservices");
        st.execute("DROP TABLE managed_service_ccontracts");
        /*
         * Views
         */
        st.execute("DROP VIEW autocompletecustomer");
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
    
    static public String SysInfoIsAlreadyConfigured() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        String line = "0";
        String out = "";
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement("select decode(key,'base64'),val from info_system where key = encode('MAIN_VERSION','base64') OR key = encode('UPDATE_VERSION','base64') OR key = encode('BUILD_VERSION','base64') OR key = encode('PORTAL_PATH','base64')");
        ResultSet rs = ps.executeQuery();
        while ( rs.next() ) { 
            if ("MAIN_VERSION".equals(rs.getString( 1 ))) {
                out += "\"MAINV\":\"" + rs.getString( 2 ) + "\","; line = "1";
            } else if ("UPDATE_VERSION".equals(rs.getString( 1 ))) {
                out += "\"UPDATEV\":\"" + rs.getString( 2 ) + "\","; line = "1";
            } else if ("BUILD_VERSION".equals(rs.getString( 1 ))) {
                out += "\"BUILDV\":\"" + rs.getString( 2 ) + "\","; line = "1";
            } else if ("PORTAL_PATH".equals(rs.getString( 1 ))) {
                out += "\"PORTALP\":\"" + rs.getString( 2 ) + "\","; line = "1";
            } else {
                line = "0";
            }
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
    
    static public void ConfigureSysInfo(String mainv, String updatev, String buildv, String portalp) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        /*
         * Delete old Configuration
         */
        PreparedStatement ps1 = cn.prepareStatement("DELETE FROM info_system WHERE KEY like encode('MAIN_VERSION','base64')");
        ps1.executeUpdate();
        PreparedStatement ps2 = cn.prepareStatement("DELETE FROM info_system WHERE KEY like encode('UPDATE_VERSION','base64')");
        ps2.executeUpdate();
        PreparedStatement ps3 = cn.prepareStatement("DELETE FROM info_system WHERE KEY like encode('BUILD_VERSION','base64')");
        ps3.executeUpdate();
        PreparedStatement ps4 = cn.prepareStatement("DELETE FROM info_system WHERE KEY like encode('PORTAL_PATH','base64')");
        ps4.executeUpdate();
        /*
         * Add new Configuration
         */
        PreparedStatement ps5 = cn.prepareStatement("INSERT INTO info_system (KEY,VAL) values (encode('MAIN_VERSION','base64'),?)");
        ps5.setString(1,mainv);
        ps5.executeUpdate();
        PreparedStatement ps6 = cn.prepareStatement("INSERT INTO info_system (KEY,VAL) values (encode('UPDATE_VERSION','base64'),?)");
        ps6.setString(1,updatev);
        ps6.executeUpdate();
        PreparedStatement ps7 = cn.prepareStatement("INSERT INTO info_system (KEY,VAL) values (encode('BUILD_VERSION','base64'),?)");
        ps7.setString(1,buildv);
        ps7.executeUpdate();
        PreparedStatement ps8 = cn.prepareStatement("INSERT INTO info_system (KEY,VAL) values (encode('PORTAL_PATH','base64'),?)");
        ps8.setString(1,portalp);
        ps8.executeUpdate();
        /*
         * Close Connection
         */
        cn.close();
    }
}
