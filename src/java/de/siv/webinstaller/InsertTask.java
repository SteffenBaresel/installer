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
public class InsertTask extends HttpServlet {
    Properties props = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String type, String comment, String instid)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String tid = "";
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json; charset=utf-8");
            /*
             * Execution
             * 
             * 1. Create Table Structure
             */
            
            tid = Functions.InsertTask(type,comment,instid);
        } catch (FileNotFoundException ex) {
            out.println("{\"EXEC\":\"0\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
            Logger.getLogger(InsertTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            out.println("{\"EXEC\":\"0\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
            Logger.getLogger(InsertTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch(SQLException ex) {
            out.println("{\"EXEC\":\"0\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
            Logger.getLogger(InsertTask.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
        }
        
        if (tid.length() > 0) {
            out.println("{\"EXEC\":\"1\",\"TID\":\"" + tid + "\"}");
        }
       
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, request.getParameter("taskid"), request.getParameter("comment"), request.getParameter("instid"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
    }
}
