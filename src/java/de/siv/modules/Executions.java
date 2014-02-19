/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.siv.modules;

import de.siv.web.Base64Coder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

/**
 *
 * @author sbaresel
 */
public class Executions {
    static Properties props = null;
    
    static public Boolean UserExist(String Uid) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Boolean exist = false;
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement("select decode(usdc,'base64') FROM profiles_user where usnm=? AND uact=1");
        ps.setString(1,Base64Coder.encodeString( Uid ));
        ResultSet rs = ps.executeQuery();
        if ( rs.next() ) {
            exist = true;
        }
        /*
         * Close Connection
         */
        cn.close();
        return exist;
    }
    
    static public Boolean UserMailEmpty(String Uid) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Boolean empty = true;
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection();
        PreparedStatement ps = cn.prepareStatement("select decode(usdc,'base64') FROM profiles_user where usnm=? AND bit_length(umai) > 35");
        ps.setString(1,Base64Coder.encodeString( Uid ));
        ResultSet rs = ps.executeQuery();
        if ( rs.next() ) {
            empty = false;
        }
        /*
         * Close Connection
         */
        cn.close();
        return empty;
    }
    
    static public Boolean UserIsPermitted(String Uid, String Mod) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Boolean empty = false;
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        PreparedStatement ps = cn.prepareStatement("select decode(e.prnm, 'base64') from profiles_user a, profiles_user_group_mapping b, profiles_group_role_mapping c, profiles_role_priv_mapping d, profiles_privilege e where a.uuid = b.uuid and b.grid = c.grid and c.rlid = d.rlid and d.prid = e.prid and a.usnm=? and e.prnm=?");
        ps.setString(1,Base64Coder.encodeString( Uid ));
        ps.setString(2,Base64Coder.encodeString( Mod ));
        ResultSet rs = ps.executeQuery();
        if ( rs.next() ) {
            empty = true;
        }
        /*
         * Close Connection
         */
        cn.close();
        return empty;
    }
    
    static public void UpdateLastLogin(String Uid) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        PreparedStatement psD = cn.prepareStatement("UPDATE profiles_user SET ULAL=? WHERE usnm=?");
        psD.setLong(1,System.currentTimeMillis()/1000);
        psD.setString(2,Base64Coder.encodeString( Uid ));
        psD.executeUpdate(); 
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public Boolean IsURL(HttpServletRequest request, String uri) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        String url = null;
        Boolean value = false;
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        PreparedStatement ps = cn.prepareStatement("SELECT decode(val,'base64') from info_system where key = encode('PORTAL_PATH','base64')");
        ResultSet rs = ps.executeQuery();
        if ( rs.next() ) {
            url = rs.getString( 1 );
        }
        /*
         * Build Requested URL Path
         */
        url += uri;
        if (request.getRequestURI().equals(url)) {
            value = true;
        }
        /*
         * Close Connection
         */
        cn.close();
        /*
         * Return 
         */
        return value;
    }
    
    static public void UpdateUserIsLoggedIn(String Uid) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        PreparedStatement psD = cn.prepareStatement("UPDATE profiles_user SET UILI=? WHERE usnm=?");
        psD.setBoolean(1,true);
        psD.setString(2,Base64Coder.encodeString( Uid ));
        psD.executeUpdate(); 
        /*
         * Close Connection
         */
        cn.close();
    }
    
    static public void UpdateUserIsLoggedOut(String Uid) throws FileNotFoundException, IOException, NamingException, SQLException {
        if (props == null) {
            props = Basics.getConfiguration();
        }
        Context ctx = new InitialContext(); 
        DataSource ds  = (DataSource) ctx.lookup("jdbc/repository"); 
        Connection cn = ds.getConnection(); 
        PreparedStatement psD = cn.prepareStatement("UPDATE profiles_user SET UILI=? WHERE usnm=?");
        psD.setBoolean(1,false);
        psD.setString(2,Base64Coder.encodeString( Uid ));
        psD.executeUpdate(); 
        /*
         * Close Connection
         */
        cn.close();
    }
}
