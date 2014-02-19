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
public class UnprepareRepository extends HttpServlet {
    Properties props = null;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter(); 
        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "*");
            response.setContentType("application/json; charset=utf-8");
            /*
             * Execution
             * 
             * 1. Drop Table Structure
             */
            boolean ctsSuccess = true;
            try {
                Functions.DropTableStructure();
            } catch(SQLException ex) {
                ctsSuccess = false;
                out.println("{\"EXIT\":\"1\",\"MESSAGE\":\"ERROR - " + Basics.encodeHtml(ex.toString()) + "\"}");
                Logger.getLogger(UnprepareRepository.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (ctsSuccess) {
                    out.println("{\"EXIT\":\"0\",\"MESSAGE\":\"OK\"}");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UnprepareRepository.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NamingException ex) {
            Logger.getLogger(UnprepareRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //
    }
}
