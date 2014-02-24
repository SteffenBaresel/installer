/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.siv.webinstaller;

import de.siv.modules.Basics;
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
        st.execute("CREATE TABLE profiles_customer_role_mapping ( CUID BIGSERIAL, RLID BIGSERIAL )");
        st.execute("CREATE TABLE profiles_contract_role_mapping ( CCID BIGSERIAL, RLID BIGSERIAL )");
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
         * Monitoring
         */
        st.execute("CREATE TABLE monitoring_host_contract_mapping ( HSTID BIGSERIAL, CCID BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_host_role_mapping ( HSTID BIGSERIAL, RLID BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_host_customer_mapping ( HSTID BIGSERIAL, CUID BIGSERIAL )");
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
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values (encode('monitoring','base64'),encode('Detailed Monitoring','base64'))");
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
        
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','1')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','2')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','3')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','4')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','5')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','6')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','7')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','8')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','9')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','10')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','11')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','12')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','13')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','14')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','15')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','16')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','17')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','22')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','23')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','30')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','31')");
        st.execute("INSERT INTO profiles_role_priv_mapping (RLID,PRID) values ('2','32')");
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
         * Views
         */
        st.execute("DROP VIEW autocompletecustomer CASCADE");
        /*
         * Definition Tables
         */
        st.execute("DROP TABLE profiles_user CASCADE");
        st.execute("DROP TABLE profiles_role CASCADE");
        st.execute("DROP TABLE profiles_privilege CASCADE");
        st.execute("DROP TABLE profiles_group CASCADE");
        st.execute("DROP TABLE profiles_task CASCADE");
        /*
         * Mapping Tables
         */
        st.execute("DROP TABLE profiles_user_group_mapping CASCADE");
        st.execute("DROP TABLE profiles_group_role_mapping CASCADE");
        st.execute("DROP TABLE profiles_user_role_mapping CASCADE");
        st.execute("DROP TABLE profiles_role_priv_mapping CASCADE");
        st.execute("DROP TABLE profiles_user_task_mapping CASCADE");
        st.execute("DROP TABLE profiles_customer_task_mapping CASCADE");
        st.execute("DROP TABLE profiles_customer_role_mapping CASCADE");
        st.execute("DROP TABLE profiles_contract_role_mapping CASCADE");
        /*
         * Info Tables
         */
        st.execute("DROP TABLE info_system CASCADE");
        /*
         * Config Tables
         */
        st.execute("DROP TABLE config_portal CASCADE");
        st.execute("DROP TABLE config_gateway CASCADE");
        /*
         * Class Tables
         */
        st.execute("DROP TABLE class_contracttypes CASCADE");
        st.execute("DROP TABLE class_commenttypes CASCADE");
        st.execute("DROP TABLE class_entrytypes CASCADE");
        /*
         * Manged Service Tables
         */
        st.execute("DROP TABLE managed_service_cinfo CASCADE");
        st.execute("DROP TABLE managed_service_cservices CASCADE");
        st.execute("DROP TABLE managed_service_ccontracts CASCADE");
        /*
         * Monitoring
         */
        st.execute("DROP TABLE monitoring_host_contract_mapping CASCADE");
        st.execute("DROP TABLE monitoring_host_role_mapping CASCADE");
        st.execute("DROP TABLE monitoring_host_customer_mapping CASCADE");
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
        if ( rs.next() ) {
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
    
    /*
     * 
     * Prepare Monitoring
     * 
     */
    
    static public void CreateMonitoringTableStructure() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/monitoring"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        /*
         * Definition Tables
         */
        st.execute("CREATE TABLE class_hosttypes ( HTYPID SERIAL UNIQUE, HTYPSN varchar(20), HTYPLN varchar(1000), HTYPICON varchar(25) )");
        st.execute("CREATE TABLE monitoring_info_instance ( INSTID BIGSERIAL UNIQUE, INSTNA varchar(1000), IDENTIFIER varchar(10), DSC varchar(10000), LAST_ACTIVE BIGSERIAL, STARTUP BIGSERIAL, PID SMALLINT )");
        st.execute("CREATE TABLE monitoring_info_host ( HSTID BIGSERIAL UNIQUE, HSTSN varchar(250), HSTLN varchar(1000), IPADDR varchar(100), HTYPID integer, DSC varchar(10000), CHECK_PERIOD varchar(50), INSTID BIGSERIAL, UPTIME decimal, AGENT_VERSION varchar(50), OS varchar(25), CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_info_service ( SRVID BIGSERIAL UNIQUE, HSTID BIGSERIAL, SRVNA varchar(1000), DSC varchar(10000), INSTID BIGSERIAL, CHECK_PERIOD varchar(50), CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_oracle_database_info ( DBID BIGSERIAL UNIQUE, HSTID BIGSERIAL, SID varchar(10), TYPE varchar(10), DSC varchar(10000), CHECK_PERIOD varchar(50), SRVID BIGSERIAL, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_oracle_database_status ( DBSID BIGSERIAL UNIQUE, DBID BIGSERIAL, STATUS varchar(15), BLOCKED varchar(15), LOGINS varchar(15), STARTMODE varchar(15), DATAFILES smallint, CONTROLFILES smallint, REDOLOGFILES smallint, INV_INDIZES smallint, INV_PART smallint, INV_REG_COMP smallint, INV_OBJECTS smallint, SESSION varchar(10), PROCESS varchar(10), BLOCKKORRUPTION smallint, RMANPROBLEMS smallint, CLUSERS smallint, LSESSIONS smallint, WAALERTS smallint, CRALERTS smallint, UNALERTS smallint, REDOLOGSSWITCH decimal, PRTBPS decimal, OCC decimal, DBG decimal, PRTIOR decimal, DBC decimal, PWTBPS decimal, PWTIOR decimal, CPU_ORA varchar(20), WAIT varchar(20), COMMIT varchar(20), CPU_ORA_WAIT varchar(20), CPU_TOTAL varchar(20), READIO varchar(20), CPU_OS varchar(20), ACK BOOLEAN, ACKID BIGSERIAL, DTM BOOLEAN, DTMID BIGSERIAL, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_oracle_database_status_history ( DBSHID BIGSERIAL UNIQUE, DBID BIGSERIAL, STATUS varchar(15), BLOCKED varchar(15), LOGINS varchar(15), STARTMODE varchar(15), DATAFILES smallint, CONTROLFILES smallint, REDOLOGFILES smallint, INV_INDIZES smallint, INV_PART smallint, INV_REG_COMP smallint, INV_OBJECTS smallint, SESSION varchar(10), PROCESS varchar(10), BLOCKKORRUPTION smallint, RMANPROBLEMS smallint, CLUSERS smallint, LSESSIONS smallint, WAALERTS smallint, CRALERTS smallint, UNALERTS smallint, REDOLOGSSWITCH decimal, PRTBPS decimal, OCC decimal, DBG decimal, PRTIOR decimal, DBC decimal, PWTBPS decimal, PWTIOR decimal, CPU_ORA varchar(20), WAIT varchar(20), COMMIT varchar(20), CPU_ORA_WAIT varchar(20), CPU_TOTAL varchar(20), READIO varchar(20), CPU_OS varchar(20), CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_oracle_database_tablespace ( DBTSID BIGSERIAL UNIQUE, DBID BIGSERIAL, TSNA varchar(25), TSSTATE varchar(25), TSUSAGE varchar(25), TSSIZE varchar(25), TSMAXSIZE varchar(25), TSFRAG varchar(25), CREATED BIGSERIAL)");
        st.execute("CREATE TABLE monitoring_oracle_database_fra ( FRAID BIGSERIAL UNIQUE, DBID BIGSERIAL, PATH varchar(150), SIZE varchar(25), USAGE varchar(25), REC varchar(25), FILES varchar(25), CREATED BIGSERIAL)");
        st.execute("CREATE TABLE monitoring_oracle_middleware_info ( MWID BIGSERIAL UNIQUE, HSTID BIGSERIAL, TYPE varchar(25), PORT varchar(15), DSC varchar(10000), CHECK_PERIOD varchar(50), SRVID bigserial, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_oracle_middleware_status ( MWSID BIGSERIAL UNIQUE, MWID BIGSERIAL, TYPE varchar(25), MODE varchar(25), NAME varchar(50), STATUS varchar(50), ACK BOOLEAN, ACKID BIGSERIAL, DTM BOOLEAN, DTMID BIGSERIAL, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_oracle_middleware_status_history ( MWSHID BIGSERIAL UNIQUE, MWID BIGSERIAL, TYPE varchar(25), MODE varchar(25), NAME varchar(50), STATUS varchar(50), CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_performance ( PID BIGSERIAL UNIQUE, HSTID BIGSERIAL, SRVID BIGSERIAL, APPID BiGSERIAL, CLASS varchar(100), KEY varchar(100), NAME varchar(100), DSC varchar(100), USAGE varchar(100), AVAIL varchar(100), ALLOC varchar(100), CREATED BIGSERIAL)");
        st.execute("CREATE TABLE monitoring_availability ( AID BIGSERIAL UNIQUE, SRVID BIGSERIAL, APPID BiGSERIAL, TIMEOK decimal, TIMEWA decimal, TIMECR decimal, TIMEUN decimal, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_status ( SID BIGSERIAL UNIQUE, HSTID BIGSERIAL, SRVID BIGSERIAL, APPID BIGSERIAL, OUTPUT varchar(100000), LONG_OUTPUT varchar(100000), CURRENT_STATE smallint, LAST_STATE smallint, LAST_CHECK BIGSERIAL, NEXT_CHECK BIGSERIAL, LAST_TIME_OK BIGSERIAL, LAST_TIME_WA BIGSERIAL, LAST_TIME_CR BIGSERIAL, LAST_TIME_UN BIGSERIAL, PERCENT_STATE_CHANGE smallint, PERF_DATA varchar(10000), ACK BOOLEAN, ACKID BIGSERIAL, DTM BOOLEAN, DTMID BIGSERIAL, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_status_history ( SHID BIGSERIAL UNIQUE, HSTID BIGSERIAL, SRVID BIGSERIAL, APPID BIGSERIAL, OUTPUT varchar(100000), LONG_OUTPUT varchar(100000), CURRENT_STATE smallint, LAST_STATE smallint, PERF_DATA varchar(10000), CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_task ( TID BIGSERIAL UNIQUE, TYPE smallint, HSTID BIGSERIAL, SRVID BIGSERIAL, ERROR smallint, DONE BOOLEAN, USR VARCHAR(50), TSSTART BIGSERIAL, TSEND BIGSERIAL, COMMENT VARCHAR(10000), INSTID BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_state_change ( SCID BIGSERIAL, HSTID BIGSERIAL, SRVID BIGSERIAL, STATE SERIAL, LAST_STATE SERIAL, OUTPUT varchar(100000), NEW_PROBLEM SERIAL, MAIL SERIAL, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_acknowledge ( ACKID BIGSERIAL, TS BIGSERIAL, USR VARCHAR(50), COMMENT VARCHAR(10000), HSTID BIGSERIAL, SRVID BIGSERIAL, APPID BIGSERIAL, CREATED BIGSERIAL )");
        st.execute("CREATE TABLE monitoring_downtime ( DTMID BIGSERIAL, TSSTART BIGSERIAL, TSEND BIGSERIAL, USR VARCHAR(50), COMMENT VARCHAR(10000), HSTID BIGSERIAL, SRVID BIGSERIAL, APPID BIGSERIAL, CREATED BIGSERIAL )");
        
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void FillMonitoringDefaultValues() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/monitoring");
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement();
        
        /*
         * Default info
         */
        
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('HOST','Einfacher, nicht klassifizierter Host','server')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KDBS','kVASy(R) Datenbank-Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KFRS','kVASy(R) Forms/Reports WebLogic Server','layers')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KSBS','kVASy(R) SOA/BAM Server','soabam')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KIAS','kVASy(R) Application Server','layers')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KBIS','kVASy(R) Business Intelligence Server','bi')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KDHS','kVASy(R) Data Ware House Datenbank Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KEYS','kVASy(R) Easy Archiv Server','archiv')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KWSV','kVASy(R) WebService','webservice')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('KKPS','kVASy(R) Kundenportal','internet')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('ORDS','Oracle Datenbank Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('MYDS','MySql Datenbank Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('MSDS','MSSql Datenbank Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('DB2S','DB2 Datenbank Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('PGDS','PostgreSQL Datenbank Server','database')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('BAKS','Backupserver','backup')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('PRGV','Progov - Mail Signature und Verschlüsselung','mail')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('HTTP','Webserver','internet')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('DRSE','Druckserver','magazine')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('LOAD','Loadbalancer','loadbalancer')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('DNSS','DNS Server','server')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('LDAP','LDAP Server','server')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('FSRV','Fileserver','server')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('ADSE','AD Server','server')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('MAIL','Mail Server','mail')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('WBSE','WebService','webservice')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('PRSE','Proxy Server','collapse')");
        st.execute("INSERT INTO class_hosttypes (HTYPSN,HTYPLN,HTYPICON) VALUES ('FIWA','Firewall','collapse')");
        
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void DropMonitoringTableStructure() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/monitoring"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        /*
         * Definition Tables
         */

        st.execute("DROP TABLE class_hosttypes CASCADE");
        st.execute("DROP TABLE monitoring_info_instance CASCADE");
        st.execute("DROP TABLE monitoring_info_host CASCADE");
        st.execute("DROP TABLE monitoring_info_service CASCADE");
        st.execute("DROP TABLE monitoring_oracle_database_info CASCADE");
        st.execute("DROP TABLE monitoring_oracle_database_status CASCADE");
        st.execute("DROP TABLE monitoring_oracle_database_status_history CASCADE");
        st.execute("DROP TABLE monitoring_oracle_database_tablespace CASCADE");
        st.execute("DROP TABLE monitoring_oracle_database_fra CASCADE");
        st.execute("DROP TABLE monitoring_oracle_middleware_info CASCADE");
        st.execute("DROP TABLE monitoring_oracle_middleware_status CASCADE");
        st.execute("DROP TABLE monitoring_oracle_middleware_status_history CASCADE");
        st.execute("DROP TABLE monitoring_performance CASCADE");
        st.execute("DROP TABLE monitoring_availability CASCADE");
        st.execute("DROP TABLE monitoring_status CASCADE");
        st.execute("DROP TABLE monitoring_status_history CASCADE");
        st.execute("DROP TABLE monitoring_task CASCADE");
        st.execute("DROP TABLE monitoring_state_change CASCADE");
        st.execute("DROP TABLE monitoring_downtime CASCADE");
        st.execute("DROP TABLE monitoring_acknowledge CASCADE");
        
        /*
         * Close Connection
         */
        cn.close();
    }
}
