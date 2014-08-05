/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.siv.webinstaller;

import de.siv.modules.Basics;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sbaresel
 */
public class UpdateConfig extends HttpServlet {
    Properties props = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String text, String mcfgid)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        boolean ctsSuccess = true;
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json; charset=utf-8");
            /*
             * Execution
             * 
             * 1. Create Table Structure
             */
            
            Functions.UpdateConfig(text,mcfgid);
        } catch (FileNotFoundException ex) {
            ctsSuccess = false;
            out.println("{\"EXEC\":\"0\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
            Logger.getLogger(UpdateConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            ctsSuccess = false;
            out.println("{\"EXEC\":\"0\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
            Logger.getLogger(UpdateConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex) {
            ctsSuccess = false;
            out.println("{\"EXEC\":\"0\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
            Logger.getLogger(UpdateConfig.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ctsSuccess = true;
            out.println("{\"EXEC\":\"1\",\"MESSAGE\":\"-\"}");
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, request.getParameter("text"), request.getParameter("mcfgid"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
    }
}
