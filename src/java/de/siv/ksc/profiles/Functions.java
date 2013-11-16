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
        st.execute("CREATE TABLE info_system ( ID BIGSERIAL UNIQUE, KEY varchar(100), VAL varchar(500) )");
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
        st.execute("INSERT INTO info_system (KEY,VAL) values ('MAIN_VERSION','2')");
        st.execute("INSERT INTO info_system (KEY,VAL) values ('UPDATE_VERSION','3')");
        st.execute("INSERT INTO info_system (KEY,VAL) values ('BUILD_VERSION','131114')");
        /*
         * Default Group
         */
        st.execute("INSERT INTO profiles_group (GRNM,GRDC) values ('admin','Administrator')");
        st.execute("INSERT INTO profiles_group (GRNM,GRDC) values ('operator','Operator')");
        /*
         * Default Role
         */
        st.execute("INSERT INTO profiles_role (RLNM,RLDE) values ('full','Vollzugriff')");
        st.execute("INSERT INTO profiles_role (RLNM,RLDE) values ('operating','Teilzugriff')");
        /*
         * Default Privileges
         */
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('all','Zugriff auf Alles')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('dashboard','Anzeige des Dashbaord Moduls')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('gateway','Zugriff auf das Gateway Modul')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('liveticker','Zugriff auf den Liveticker')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('sidebarsearch','Zugriff auf das Sidebar Suche Modul')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('sidebarfunctions','Zugriff auf die Sidebar Funktionen')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('host','Anzeige des Hosts Moduls')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('service','Anzeige des Service Moduls')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('database','Anzeige des Database Moduls')");
        st.execute("INSERT INTO profiles_privilege (PRNM,PRDC) values ('middleware','Anzeige des Middleware Moduls')");
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
        st.execute("INSERT INTO profiles_user(USNM,USDC) values ( '" + ShortName + "', '" + LongName + "')");
        /*
         * add to Group
         */
        st.execute("INSERT INTO profiles_user_group_mapping(UUID,GRID) values ( '1', '1' )");
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
         * Close Connection
         */
        cn.close();
    }
    
    static public String IsAlreadyInstalled() throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        String line = null;
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        Statement st = cn.createStatement(); 
        ResultSet rs  = st.executeQuery("SELECT * FROM info_system where KEY like 'MAIN_VERSION'");
        while ( rs.next() ) { 
           line = "1";
        }
        
        /*
         * Close Connection
         */
        cn.close();
        return line;
    }
}
