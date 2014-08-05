<%-- 
    Document   : index
    Created on : 30.10.2013, 15:05:07
    Author     : sbaresel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <!--GATEWAY_ENGINE:2.3.131127-->
        <center>
            <h2>kVASy&reg; System Control - Installer Engine 2.3.140308</h2>
            <p>
                <font size=2>Entwicklungslinie 3 Build 2014.02</font>
            </p>
        </center>
        <div>
            <i>
                <font size=3>Fehler an:</font>
            </i>
            <br>
            <b>Mail: kvasysystemcontrol@siv.de</b>
            <br>
            <b>Tel: 0381 - 2524 0</b>
        </div>
        <br></br><br></br>
        <font size=2>
        <!--ul>
            <li>Get Core Version (json) URL: <a href="/?cv=g">/?cv=g</a></li>
            <li>Get Modul Version (json) URL: <a href="/?mv=g">/?mv=g</a></li>
        </ul-->
        </font>
        <hr>
        <div>
            <center>Nachfolgend eine &Uuml;bersicht der Funktionen der REST Schnittstelle:</center>
        </div>
        <hr>
        <br></br>
        <!--INSTALLATION_REPOSITORY_VERSION:2.3.140308-->
        Modul: <i><b>Installation Repository</b></i><br>
        Version: <i><b>2.3.140308</b></i><br>
        Comments: -
        <br></br>
        Format: <i><b>JSON</b></i><br>
        <font size=2>Query:<br>
        <ul>
            <li><b>PrepareRepository</b> - Prepare Database Backend for Profiles usage URL: <a href="/admin/exec/PrepareRepository">/admin/exec/PrepareRepository</a></li>
            <li><b>UnprepareRepository</b> - Unprepare Database Backend for Profiles usage URL: <a href="/admin/exec/UnprepareRepository">/admin/exec/UnprepareRepository</a></li>
            <li><b>FillDefault</b> - Fill Default Values for Profiles usage URL: <a href="/admin/exec/FillDefault">/admin/exec/FillDefault</a></li>
            <li><b>PrepareMonitoring</b> - Prepare Database Backend for Monitoring usage URL: <a href="/admin/exec/PrepareMonitoring">/admin/exec/PrepareMonitoring</a></li>
            <li><b>UnprepareMonitoring</b> - Unprepare Database Backend for Monitoring usage URL: <a href="/admin/exec/UnprepareMonitoring">/admin/exec/UnprepareMonitoring</a></li>
            <li><b>FillMonitoringDefault</b> - Fill Default Values for Monitoring usage URL: <a href="/admin/exec/FillMonitoringDefault">/admin/exec/FillMonitoringDefault</a></li>
        </ul>
        </font>
        <br></br>
        <!--
         Neue Funktionen hier anfügen.
        -->
        <hr>
        <br></br>
        <!--CONFIGURE_REPOSITORY_VERSION:2.3.140308-->
        Modul: <i><b>Konfiguration Repository</b></i><br>
        Version: <i><b>2.3.140308</b></i><br>
        Comments: -
        <br></br>
        Format: <i><b>JSON</b></i><br>
        <font size=2>Query:<br>
        <ul>
            <li><b>MailApiIsAlreadyConfigured</b> - Get configuration for Java Mail Api and check ifs already configured URL: <a href="/admin/exec/MailApiIsAlreadyConfigured">/admin/exec/MailApiIsAlreadyConfigured</a></li>
            <li><b>ConfigureMailApi</b> - Set configuration for Java Mail Api URL: <a href="/admin/exec/ConfigureMailApi?host=&port=&user=&pass=">/admin/exec/ConfigureMailApi?host=&port=&user=&pass=</a></li>
            <li><b>AdminUserIsAlreadyConfigured</b> - Get configured Admin User URL: <a href="/admin/exec/AdminUserIsAlreadyConfigured">/admin/exec/AdminUserIsAlreadyConfigured</a></li>
            <li><b>CreateAdminUser</b> - Set Admin User URL: <a href="/admin/exec/CreateAdminUser?sn=&ln=">/admin/exec/CreateAdminUser?sn=&ln=</a></li>
            <li><b>SysInfoIsAlreadyConfigured</b> - Get system information URL: <a href="/admin/exec/SysInfoIsAlreadyConfigured">/admin/exec/SysInfoIsAlreadyConfigured</a></li>
            <li><b>ConfigureSysInfo</b> - Set set system information URL: <a href="/admin/exec/ConfigureSysInfo?mainv=&updatev=&buildv=&portalp=">/admin/exec/ConfigureSysInfo?mainv=&updatev=&buildv=&portalp=</a></li>
        </ul>
        </font>
        <br></br>
        <hr>
        <br></br>
        <!--:3.0.140308-->
        Modul: <i><b>Monitoring</b></i><br>
        Version: <i><b>3.0.140308</b></i><br>
        Comments: -
        <br></br>
        Format: <i><b>JSON</b></i><br>
        <font size=2>Query:<br>
        <ul>
            <li><b>GetMonitoringInstances</b> - Get configured Monitoring Instances URL: <a href="/admin/exec/GetMonitoringInstances">/admin/exec/GetMonitoringInstances</a></li>
            <li><b>GetConfigFiles</b> - Get Monitoring Config Files for one Instances URL: <a href="/admin/exec/GetConfigFiles?instid=">/admin/exec/GetConfigFiles?instid=</a></li>
            <li><b>GetContent</b> - Get Monitoring Config File for one Instance URL: <a href="/admin/exec/GetContent?mcfgid=">/admin/exec/GetContent?mcfgid=</a></li>
            <li><b>UpdateConfig</b> - Update Monitoring Config File for one Instance URL: <a href="/admin/exec/UpdateConfig?text=&mcfgid=">/admin/exec/UpdateConfig?text=&mcfgid=</a></li>
            <li><b>InsertTask</b> - Insert Monitoring task for one Instance URL: <a href="/admin/exec/InsertTask?comment=&instid=">/admin/exec/InsertTask?comment=&instid=</a></li>
            <li><b>GetConfigHosts</b> - Get Host from Config File for one Instance URL: <a href="/admin/exec/GetConfigHosts?mcfgid=">/admin/exec/GetConfigHosts?mcfgid=</a></li>
            <li><b>GetTaskOutput</b> - Get Output from Inserted Task for one Instance URL: <a href="/admin/exec/GetTaskOutput?tid=">/admin/exec/GetTaskOutput?tid=</a></li>
        </ul>
        </font>
        <br></br>
        <hr>
        <center><font size="1"><b>2014 Steffen Baresel</b></font></center>
    </body>
</html>
