/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.siv.web;

import de.siv.modules.Executions;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author sbaresel
 */
public class Html {
    
    static public String openHtmlAndHead(String mod) {
        String replace ="<!DOCTYPE html>\n<html>\n    <head>\n";
        return replace;
    }
    
    static public String closeHeadOpenBody(String mod) {
        String replace ="    </head>\n    <body>\n";
        return replace;
    }
    
    static public String closeBodyCloseHtml(String mod) {
        String replace ="    </body>\n</html>";
        return replace;
    }
    
    static public String includeMeta(String mod) {
        String replace ="    <link rel='shortcut icon' href='public/images/favicon.ico' type='image/vnd.microsoft.icon' />\n";
        replace+="    <meta name='author' content='Steffen Baresel'>\n";
	replace+="    <meta name='description' content='kVASy(R) System Control'>\n";
	replace+="    <meta name='keywords' content='kVASy, System Control, kVASy System Control'>\n";
	replace+="    <meta name='language' content='it'>\n";
	replace+="    <meta name='charset' content='utf-8'>\n";
        return replace;
    }
    
    static public String includeJs(String mod) {
        String replace = "    <script type='text/javascript' src='public/script/jquery-1.8.2.min.js'></script>\n";
        replace+="    <script type='text/javascript' src='public/script/jquery-ui-1.10.4.custom.min.js'></script>\n";
        replace+="    <script type='text/javascript' src='public/script/jquery.shortcuts.min.js'></script>\n";
        replace+="    <script type='text/javascript' src='public/script/base64.js'></script>\n";
        replace+="    <script type='text/javascript' src='public/script/jquery.cookie.js'></script>\n";
        
        if(mod.equals("Index")) {
            replace+="    <script type='text/javascript' src='public/script/basics.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/UserBasics.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/UserProfile.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/jquery-te-1.4.0.min.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/TacticalOverview.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/charting.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/charts.js'></script>\n";
        } else if (mod.equals("ManagedServices")) {
            replace+="    <script type='text/javascript' src='public/script/basics.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/ManagedServiceBasics.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/UserProfile.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/jquery-te-1.4.0.min.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/TacticalOverview.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/charting.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/charts.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/jquery.simplePagination.js'></script>\n";
        } else if (mod.equals("Monitoring")) {
            replace+="    <script type='text/javascript' src='public/script/basics.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/MonitoringBasics.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/UserProfile.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/jquery-te-1.4.0.min.js'></script>\n";
            replace+="    <script type='text/javascript' src='script/TacticalOverview.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/charting.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/charts.js'></script>\n";
            replace+="    <script type='text/javascript' src='public/script/jquery.simplePagination.js'></script>\n";
        }
        
        return replace;
    }
    
    static public String includeCss(String mod) {
        String replace = "    <link rel='stylesheet' href='public/style/jquery-ui-1.10.4.custom.css' />\n";
        replace+="    <link rel='stylesheet' href='public/style/systemcontrol.css' />\n";
        replace+="    <link rel='stylesheet' href='public/style/jquery-te-1.4.0.css' />\n";
              
        return replace;
    }
    
    static public String printSectionMenu(String uid,String mod) throws FileNotFoundException, IOException, NamingException, SQLException {
        String replace = ""
                + "<section id='menu'><div id='bg-menu' class='ui-opacity'></div><div id='bg-user-menu' class='ui-opacity-med'></div>"
                + "    <div id='logo-title'>"
                + "        <font class='logo-red'>kVASy&reg;</font><font class='logo-blue'>System Control</font>"
                + "    </div>"
                + "    <div id='logo-img'>"
                + "        <img src='public/images/SIV_AG_Logo_RGB_Web.png' title='SIV.AG'/>"
                + "    </div>"
                + "    <div id='logo-subtitle'>"
                + "        Version 3 Build 2014.02"
                + "    </div>"
                + "    <div id='menu-locator'>"
                + "    </div>"
                + "</section>";
        
            if(mod.equals("Index") || mod.equals("ManagedService") || mod.equals("Monitoring")) {
                
                replace += "<section id='user-menu'><div id='UserMenu' class='ui-user-menu'><span class='login_username' style='float: left'></span>";
                
                if(Executions.UserIsPermitted(uid,"profile")) {
                    replace += "<span title='Edit' style='float: left; margin-top: 1px; cursor: pointer;' class='ui-icon ui-icon-wrench' onclick=\"UserProfile('" + Base64Coder.encodeString(uid) + "');\"></span>";
                }
                
                replace += "<a href='logout'><span title='Logout' style='float: left; margin-top: 1px;' class='ui-icon ui-icon-power'></span></a></div></section><section id='user-picture'><img id='UserProfileConfig' src='public/images/DefaultProfile.png' /></section>";
                
                
                if(mod.equals("ManagedService")) {
                    if(Executions.UserIsPermitted(uid,"managed_services")) {
                    
                        replace += "<div id='MenuSidebarSmall'></div><div id='MenuSidebar'><div id='MenuSidebarContent'>\n";
                        replace += "        <div id='MenuSidebarManagedServiceMenus'><div id='MS_CustActions'>\n";
                        replace += "<div class='UserDesc'><span style=\"float: left;\">Arbeiten</span><span style=\"float: left; margin-top: 0px;\" class=\"ui-icon ui-icon-triangle-1-s\"></span></div><br>\n";
                        replace += "<div id='MS_srv'>";
                
                        if(Executions.UserIsPermitted(uid,"managed_services_csw")) {
                            replace += "<span class='icon ui-input-hofo' onclick=\"CreateServiceEntry()\" title='Servicearbeiten eintragen'><img src='public/images/add.png' alt='Add' width='50' height='50'></span>";
                        }
                
                        replace += "</div>";
                        
                        if(Executions.UserIsPermitted(uid,"managed_services_nka")) {
                            replace += "<div class='UserDesc'><span style=\"float: left;\">Kundeninfo</span><span style=\"float: left; margin-top: 0px;\" class=\"ui-icon ui-icon-triangle-1-s\"></span></div><br>";
                            replace += "<div id='MS_cc'>";
                            replace += "<span class='icon ui-input-hofo' onclick=\"CreateCustomer();\" title='Neuen Kunden anlegen'><img src='public/images/add.png' alt='Add' width='50' height='50'></span>";
                    
                            if(Executions.UserIsPermitted(uid,"managed_services_kb")) {
                                replace += "<span class='icon ui-input-hofo' onclick=\"EditCustomer();\" title='Kunden bearbeiten'><img src='public/images/edit.png' alt='Edit' width='50' height='50'></span>";
                            } 
                    
                            if(Executions.UserIsPermitted(uid,"managed_services_kl")) {
                                replace += "<span class='icon ui-input-hofo' onclick=\"DeleteCustomer();\" title='Kunden l&ouml;schen'><img src='public/images/delete.png' alt='Delete' width='50' height='50'></span>";
                            } 
                    
                            replace += "</div>";
                        }
                        
                        if(Executions.UserIsPermitted(uid,"managed_services_vae")) {
                        
                            replace += "<div class='UserDesc'><span style=\"float: left;\">Vertragstypen</span><span style=\"float: left; margin-top: 0px;\" class=\"ui-icon ui-icon-triangle-1-s\"></span></div><br>";
                            replace += "<div id='MS_vt'>";
                            replace += "<span class='icon ui-input-hofo' onclick=\"CreateContractType();\" title='Neuer Vertragstyp'><img src='public/images/add.png' alt='Add' width='50' height='50'></span>";
                        
                            if(Executions.UserIsPermitted(uid,"managed_services_vab")) {
                                replace += "<span class='icon ui-input-hofo' onclick=\"EditContractType();\" title='Vertragstyp bearbeiten'><img src='public/images/edit.png' alt='Edit' width='50' height='50'></span>";
                            } 
                    
                            if(Executions.UserIsPermitted(uid,"managed_services_val")) {
                                replace += "<span class='icon ui-input-hofo' onclick=\"DeleteContractType();\" title='Vertragstyp l&ouml;schen'><img src='public/images/delete.png' alt='Delete' width='50' height='50'></span>";
                            }
                
                            replace += "</div>";
                            
                        }
                            
                        replace += "        </div><div id='MSDialog'></div><div id='MSDialogSuccess'></div>";
                        replace += "</div></div></div>";
                    }
                }
                
                replace += "<section id='content-index'>";
                    
                if(Executions.UserIsPermitted(uid,"liveticker")) {
                    replace += "<section id='big-taov'></section>";
                }
                    
            } else {
                replace += "<section id='content'>";
            }
            
                replace += "";
                
        return replace;
    }
    
    static public String printSectionBottom(String uid) {
        String replace = ""
                + "</section>"
                + "<div id='Dialog'></div><div id='LiveDialog'></div>"
                + "<section id='bottom'>"
                + "</section>"
                + "";
        return replace;
    }
    
    static public String printErrorBox(String message) {
        String replace = "<div id='login-error' class='ui-widget'><div class='ui-state-error ui-corner-all' style='padding: 0 .7em;'><p><span class='ui-icon ui-icon-alert' style='float: left; margin-right: .3em;'></span><strong>Fehler:</strong> " + message + "</p></div></div>";
        return replace;
    }
    
    static public String printInfoBox(String message) {
        String replace = "<div id='login-success' class='ui-widget'><div class='ui-state-highlight ui-corner-all' style='padding: 0 .7em;'><p><span class='ui-icon ui-icon-info' style='float: left; margin-right: .3em;'></span><strong>Information:</strong> " + message + "</p></div></div>";
        return replace;
    }
    
    static public String printTopMenu(String mod, String uid) {
        String replace = "        <div id='TopMenu'></div>\n";
        replace+="        <span id='top'>\n";
        replace+="            <p class='title'><font class='kvasy'>kVASy&reg;</font> System Control</p><div id='logo-div'><img class='logo' src='layout/images/logo_backgroundblue_whitetext.png' title='SIV.AG'/></div>\n";
        replace+="            <p class='subtitle'>Monitoring quite simple!</p>";
        replace+="        </span>\n";
        replace+="        <div id='UserMenu'><table cellpadding=0 cellspacing=0 border=0><tr><td><span class='UserDesc' style='float: left;'>User</span><span style='float: left; margin-top: -1px;' class='ui-icon ui-icon-triangle-1-s'></span></td><td width='10'></td><td colspan=3><span class='UserDesc' style='float: left;'>Session</span><span style='float: left; margin-top: -1px;' class='ui-icon ui-icon-triangle-1-s'></span></td></tr><tr valign=middle><td><p class='login_username'></p></td><td width='10'></td><td><p class='login_shortname'><a href='logout'>Abmelden</a><p></td></tr></table><div id='Liveticker'></div></div>\n";
        if ("mod".equals(mod)) {
        replace+="        <div id='UserView'><table cellpadding=0 cellspacing=0 border=0><tr><td><span class='UserDesc' style='float: left;'>View</span><span style='float: left; margin-top: -1px;' class='ui-icon ui-icon-triangle-1-s'></span></td></tr><tr><td><span class='UserReload' onclick=\"Reload('" + uid + "');\">Reload</span></td></tr></table></div>";
        replace+="        <div id='back-div'></div>\n";
        }
        return replace;
    }
    
    static public String printSidebar(String mod) {
        String replace = "        <div id='SidebarSmall'>\n";
        replace+="            <div id='LivetickerSidebar'></div>\n";
        replace+="        </div>\n";
        replace+="        <div id='Sidebar'>\n";
        replace+="            <div id='SidebarContent'>\n";
        replace+="                <section id='SidebarSearch'></section>\n";
        replace+="                <section id='SidebarSearchFilter'></section>\n";
        replace+="                <section id='SidebarLiveticker'></section>\n";
        replace+="                <section id='SidebarSubmenu'></section>\n";
        replace+="            </div>\n";
        replace+="        </div>\n";
        return replace;
    }
    
    static public String printBottombar(String mod) {
        String replace = "        <div id='SidebarBottomSmall'>\n";
        replace+="            <div id='SlimTaov'></div>\n";
        replace+="        </div>\n";
        replace+="        <div id='SidebarBottom'>\n";
        replace+="            <div id='SidebarBottomContent'>\n";
        replace+="                <table id='TPie' cellpadding='0' cellspacing='0' border='0'>\n";
        replace+="                    <tr>\n";
        replace+="                        <td>\n";
        replace+="                            <div id='HeaderHostPie'><span style=\"float: left;\">Host Status &Uuml;bersicht</span><span style=\"float: left; margin-top: -1px;\" class=\"ui-icon ui-icon-triangle-1-s\"></span></div>\n";
        replace+="                            <div id='HostPie'></div>\n";
        replace+="                        </td>\n";
        replace+="                        <td>\n";
        replace+="                            <div id='HostPer'></div>\n";
        replace+="                        </td>\n";
        /*replace+="                        <td>\n";
        replace+="                            <div id='HeadComments'>Letzte Kommentare</div>\n";
        replace+="                            <div id='Comments'></div>\n";
        replace+="                            <div id='FooterComments'></div>\n";
        replace+="                        </td>\n";*/
        replace+="                        <td>\n";
        replace+="                            <div id='HeaderServicePie'><span style=\"float: left;\">Service Status &Uuml;bersicht</span><span style=\"float: left; margin-top: -1px;\" class=\"ui-icon ui-icon-triangle-1-s\"></span></div>\n";
        replace+="                            <div id='ServicePie'></div>\n";
        replace+="                        </td>\n";
        replace+="                        <td>\n";
        replace+="                            <div id='ServicePer'></div>\n";
        replace+="                        </td>\n";
        replace+="                    </tr>\n";
        replace+="                </table>\n";
        replace+="                <br>\n";
        /*replace+="                <div id='HeadDivShowCritical'>Aktuelle Probleme</div>\n";
        replace+="                <div id='DivShowCritical'></div>\n";
        replace+="                <div id='FooterDivShowCritical'></div>\n";*/
        replace+="            </div>\n";
        replace+="        </div>\n";
        return replace;
    }
}
