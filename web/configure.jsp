<%-- 
    Document   : configure.jsp
    Created on : 30.10.2013, 15:03:02
    Author     : sbaresel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" href="public/images/favicon.ico" type="image/vnd.microsoft.icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Steffen Baresel">
	<meta name="description" content="WebInstaller">
	<title>WebAdmin - Konfigurator</title>
        <script type="text/javascript" src="public/script/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="public/script/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="public/script/basics.js"></script>
        <script type="text/javascript" src="script/WebKonfigurator.js"></script>
        <link rel='stylesheet' href='public/style/jquery-ui-1.10.4.custom.css' />
        <!--
            Erweiterungen
        -->
        <style type="text/css">
            @font-face { font-family: SansProLight; src: url(public/style/SourceSansPro-Regular.ttf) format("truetype"); }
            body { background-image: url(public/images/bg.png); color: #000; font-size: 16px; font-family: SansProLight; }
            #body { border: 1px solid #423939; width: 825px; height: 424px; background-color: #fff; -webkit-box-shadow: 0px 0px 2px 0px #423939; /* webkit browser*/ -moz-box-shadow: 0px 0px 2px 0px #423939; /* firefox */ box-shadow: 0px 0px 2px 0px #423939; }
            #Header { width: 825px; height: 225px; }
            #Header img { position: absolute; margin-left: 650px; margin-top: 15px;}
            #Header h2 { position: absolute; margin-left: 50px; margin-top: 35px;}
            #Header h4 { position: absolute; margin-left: 50px; margin-top: 60px;}
            #Header p { position: absolute; margin-left: 50px; margin-top: 125px;}
            #Footer { position: absolute; top: 375px; width: 825px; height: 57px; border-top: 1px solid #423939; background-color: #423939; font-size: 10px; color: #fff; text-align: center; }
            #Footer p { margin-top: 20px; }
            #menu a { text-decoration: none; color: #000; font-size: 12px;}
            li a { text-decoration: none; color: #000; font-size: 18px; font-weight: bold; }
            #Installer { margin-left: 75px; margin-top: 0px; }
            #menu { width: 825px; height: 25px; text-align: center; vertical-align: middle; }
            .ui-input-hofo:focus, .ui-input-hofo:hover { -webkit-box-shadow: 0px 0px 2px 0px #423939; -moz-box-shadow: 0px 0px 2px 0px #423939; box-shadow: 0px 0px 2px 0px #423939; }
        
            button#MailStart { position: absolute; margin-left: 500px; margin-top: -60px; }
            button#MailAbbrechen { position: absolute; margin-left: 667px; margin-top: -60px; }
            button#MailZurueck { display: none; position: absolute; margin-left: 667px; margin-top: -60px; }
            
            #ShowStatus, #MailConfigure, #MailEdit, #ConfAdminUser, #EdtAdminUser, #SysInfoConfigure, #SysInfoEdit { display: none; }
            
            #Header button#MailConfig { margin-left: 100px; margin-top: 225px; }
            #MailAdminUser { padding: 10px; }
            #MailAdminUser div#MailAdminUserHeader { margin-top: -22px; background-color: #fff; border: 1px solid #423939; padding: 5px; font-size: 12px; margin-bottom: 20px; text-align: center;}
            #MailAdminUser table { margin-left: 30px; }
            #MailAdminUser table td:first-child { font-size: 12px; text-align: right; }
            #MailAdminUser input { width: 200px; border: 1px solid #423939; background-color: #fff; color: #000; padding: 5px;}
            #MailOutput { position: absolute; width: 790px; height: 50px; border: 1px solid #82abcc; top: 420px;left: 25px; background-color: #004c8a; overflow-y: scroll; display: none; }
            #ProgressBar .ui-progressbar-value { background-color: #82abcc; }
            #ProgressBar { display: none; width: 500px; border: 1px solid #82abcc; position: absolute; left: 150px; margin-top: 15px; height: 15px; padding: 2px; }
            #n { display: none; position: absolute; left: 130px; font-size: 12px; color: #000; margin-top: 17px; }
            #h { display: none; position: absolute; left: 660px; font-size: 12px; color: #000; margin-top: 17px; }
            #MailOutputTable { width: 100%; font-size: 14px; table-layout: fixed; border-spacing:0; border-collapse:collapse; }
            #MailOutputTable td:first-child { width: 200px; text-align: left; }
            #MailOutputTable td:last-child { text-align: right; }
            #MailOutputTable td { border-bottom: 1px solid #82abcc; padding: 5px; }
            
            #Header button#ButtonAdminUser { margin-left: 10px; margin-top: 225px; }
            button#AdminStart { position: absolute; margin-left: 500px; margin-top: -60px; }
            button#AdminAbbrechen { position: absolute; margin-left: 678px; margin-top: -60px; }
            button#AdminZurueck { display: none; position: absolute; margin-left: 667px; margin-top: -60px; }
            #ConfigureAdminUser,#EditAdminUser { padding: 10px; }
            #ConfigureAdminUser div#ConfigureAdminUserHeader,#EditAdminUser div#EditAdminUserHeader { margin-top: -22px; background-color: #fff; border: 1px solid #423939; padding: 5px; font-size: 12px; margin-bottom: 20px; text-align: center;}
            #ConfigureAdminUser table,#EditAdminUser table { margin-left: 30px; }
            #ConfigureAdminUser table td:first-child,#EditAdminUser table td:first-child { font-size: 12px; text-align: right; }
            #ConfigureAdminUser input,#EditAdminUser input { width: 200px; border: 1px solid #423939; background-color: #fff; color: #000; padding: 5px;}
            
            #Header button#ButtonSysInfo { margin-left: 10px; margin-top: 225px; }
            button#SysInfoStart { position: absolute; margin-left: 500px; margin-top: -60px; }
            button#SysInfoAbbrechen { position: absolute; margin-left: 678px; margin-top: -60px; }
            button#SysInfoZurueck { display: none; position: absolute; margin-left: 667px; margin-top: -60px; }
            #SysInfoUser,#SysInfoEditUser { padding: 10px; }
            #SysInfoUser div#SysInfoUserHeader,#SysInfoEditUser div#SysInfoEditUserHeader { margin-top: -22px; background-color: #fff; border: 1px solid #423939; padding: 5px; font-size: 12px; margin-bottom: 20px; text-align: center;}
            #SysInfoUser table,#SysInfoEditUser table { margin-left: 30px; }
            #SysInfoUser table td:first-child,#SysInfoEditUser table td:first-child { font-size: 12px; text-align: right; }
            #SysInfoUser input,#SysInfoEditUser input { width: 200px; border: 1px solid #423939; background-color: #fff; color: #000; padding: 5px;}
        </style>
        <script type="text/javascript">
            $(function() {
                $('#MailStart').button().addClass('ui-input-hofo'); $('#MailAbbrechen').button().addClass('ui-input-hofo'); $('.MSE').button().addClass('ui-input-hofo'); $('.MAE').button().addClass('ui-input-hofo'); $('.MZE').button().addClass('ui-input-hofo'); $('#MailZurueck').button().addClass('ui-input-hofo'); $('#MailConfig').button().addClass('ui-input-hofo');
                $('#ButtonAdminUser').button().addClass('ui-input-hofo'); $('#AdminStart').button().addClass('ui-input-hofo'); $('#AdminAbbrechen').button().addClass('ui-input-hofo'); $('.ASE').button().addClass('ui-input-hofo'); $('.AAE').button().addClass('ui-input-hofo'); $('.AZE').button().addClass('ui-input-hofo'); $('#AdminZurueck').button().addClass('ui-input-hofo');
                $('#ButtonSysInfo').button().addClass('ui-input-hofo'); $('#SysInfoStart').button().addClass('ui-input-hofo'); $('#SysInfoAbbrechen').button().addClass('ui-input-hofo'); $('.SISE').button().addClass('ui-input-hofo'); $('.SIAE').button().addClass('ui-input-hofo'); $('.SIZE').button().addClass('ui-input-hofo'); $('#SysInfoZurueck').button().addClass('ui-input-hofo');
                $('input[type=text]').addClass('ui-input-hofo');
                $('#ProgressBar').progressbar({
                    value: false
                });
            });
        </script>
    </head>
    <body>
        <div id="body">
            <div id="Header">
                <img src="public/images/SIV_AG_Logo_RGB_Web.png" />
                <h2>WebAdmin - Konfigurator</h2>
                <h4>f&uuml;r C&C - Reports<font color="#666"> - Version 1 Build 2015.03</font></h4>
                <p>Dieser WebKonfigurator konfiguriert erweiterte Einstellungen f&uuml;r das C&C - Reports in der <br>Version 1 Build 2015.03.</p>
                <button id="MailConfig" onclick="MailConfig('MailConfig');">Mail Konfiguration</button><button id="ButtonAdminUser" onclick="AdminUser('ButtonAdminUser');">Admin User</button><button id="ButtonSysInfo" onclick="SysInfo('ButtonSysInfo');">SysInfo</button>
            </div>
            <!-- MailApi Konfiguration -->
            <div id="MailConfigure">
                <div id="MailAdminUser">
                <div id="MailAdminUserHeader">Bitte tragen Sie hier die Einstellungen f&uuml;r den Mailserver ein. Diese Angaben m&uuml;ssen gemacht werden.</div>
                    <table>
                        <tr><td>Mailserver (IP/Name):</td><td><input id="host" type="text" value="127.0.0.1" onclick="javascript:eraseText('host');"/></td></tr>
                        <tr><td>Mailserver Port:</td><td><input id="port" type="text" value="25" onclick="javascript:eraseText('port');"/></td></tr>
                        <tr><td>Account Name:</td><td><input id="user" type="text" value="mmustermann" onclick="javascript:eraseText('user');"/></td></tr>
                        <tr><td>Account Password:</td><td><input id="pass" type="text" value="12345" onclick="javascript:eraseText('pass');"/></td></tr>
                    </table>
                </div>
                <button id="MailStart" onclick="execMailConfigure('host','port','user','pass');">MailApi Konfigurieren</button><button id="MailAbbrechen" onclick="javascript:location.reload();">Abbrechen</button><button id="MailZurueck" onclick="javascript:location.reload();">Zur&uuml;ck</button>
            </div>
            <!-- MailApi bearbeiten -->
            <div id="MailEdit">
                <div id="MailAdminUser">
                <div id="MailAdminUserHeader">Bitte &auml;ndern Sie hier gegebenenfalls die Einstellungen f&uuml;r den Mailserver. Diese Angaben m&uuml;ssen gemacht werden.</div>
                    <table>
                        <tr><td>Mailserver (IP/Name):</td><td><input id="hostE" type="text" /></td></tr>
                        <tr><td>Mailserver Port:</td><td><input id="portE" type="text" /></td></tr>
                        <tr><td>Account Name:</td><td><input id="userE" type="text" /></td></tr>
                        <tr><td>Account Password:</td><td><input id="passE" type="text" /></td></tr>
                    </table>
                </div>
                <button class="MSE" id="MailStart" onclick="execMailConfigure('hostE','portE','userE','passE');">MailApi Konfigurieren</button><button class="MAE" id="MailAbbrechen" onclick="javascript:location.reload();">Abbrechen</button><button class="MZE" id="MailZurueck" onclick="javascript:location.reload();">Zur&uuml;ck</button>
            </div>
            <!-- AdminUser -->
            <div id="ConfAdminUser">
                <div id="ConfigureAdminUser">
                <div id="ConfigureAdminUserHeader">Bitte tragen Sie hier einen Admin Nutzer ein. Dieser ist der Nutzer der alle Berechtigungen in der Anwendung hat und andere Nutzer hinzuf&uuml;gen kann. Diese Angabe muss gemacht werden.</div>
                    <table>
                        <tr><td>Admin Username:</td><td><input id="un" type="text" value="mmustermann" onclick="javascript:eraseText('un');"/></td></tr>
                        <tr><td>Admin Fullname:</td><td><input id="nm" type="text" value="Max Mustermann" onclick="javascript:eraseText('nm');"/></td></tr>
                    </table>
                </div>
                <button id="AdminStart" onclick="execAdminUser('un','nm');">Admin User bearbeiten</button><button id="AdminAbbrechen" onclick="javascript:location.reload();">Abbrechen</button><button id="AdminZurueck" onclick="javascript:location.reload();">Zur&uuml;ck</button>
            </div>
            <!-- AdminUser bearbeiten -->
            <div id="EdtAdminUser">
                <div id="EditAdminUser">
                <div id="EditAdminUserHeader">Bitte &auml;ndern Sie hier einen Admin Nutzer. Dieser ist der Nutzer der alle Berechtigungen in der Anwendung hat und andere Nutzer hinzuf&uuml;gen kann. Diese Angabe muss gemacht werden.</div>
                    <table>
                        <tr><td>Admin Username:</td><td><input id="unE" type="text" /></td></tr>
                        <tr><td>Admin Fullname:</td><td><input id="nmE" type="text" /></td></tr>
                    </table>
                </div>
                <button class="ASE" id="AdminStart" onclick="execAdminUser('unE','nmE');">Admin User bearbeiten</button><button class="AAE" id="AdminAbbrechen" onclick="javascript:location.reload();">Abbrechen</button><button class="AZE" id="AdminZurueck" onclick="javascript:location.reload();">Zur&uuml;ck</button>
            </div>
            <!-- SysInfo Konfiguration -->
            <div id="SysInfoConfigure">
                <div id="SysInfoUser">
                <div id="SysInfoUserHeader">Bitte tragen Sie hier die Einstellungen ein. Diese Angaben m&uuml;ssen gemacht werden.</div>
                    <table>
                        <tr><td>Pfad:</td><td><input id="portalp" type="text" value="/webaccess/" onclick="javascript:eraseText('portalp');"/></td></tr>
                        <tr><td>Release:</td><td><input id="mainv" type="text" value="1" onclick="javascript:eraseText('mainv');"/></td></tr>
                        <tr><td>Update:</td><td><input id="updatev" type="text" value="0" onclick="javascript:eraseText('updatev');"/></td></tr>
                        <tr><td>Build:</td><td><input id="buildv" type="text" value="1403" onclick="javascript:eraseText('buildv');"/></td></tr>
                    </table>
                </div>
                <button id="SysInfoStart" onclick="execSysInfoConfigure('mainv','updatev','buildv','portalp');">SysInfo Konfigurieren</button><button id="SysInfoAbbrechen" onclick="javascript:location.reload();">Abbrechen</button><button id="SysInfoZurueck" onclick="javascript:location.reload();">Zur&uuml;ck</button>
            </div>
            <!-- SysInfo bearbeiten -->
            <div id="SysInfoEdit">
                <div id="SysInfoEditUser">
                <div id="SysInfoEditUserHeader">Bitte &auml;ndern Sie hier gegebenenfalls die Einstellungen. Diese Angaben m&uuml;ssen gemacht werden.</div>
                    <table>
                        <tr><td>Pfad:</td><td><input id="portalpE" type="text" /></td></tr>
                        <tr><td>Release:</td><td><input id="mainvE" type="text" /></td></tr>
                        <tr><td>Update:</td><td><input id="updatevE" type="text" /></td></tr>
                        <tr><td>Build:</td><td><input id="buildvE" type="text" /></td></tr>
                    </table>
                </div>
                <button class="SISE" id="SysInfoStart" onclick="execSysInfoConfigure('mainvE','updatevE','buildvE','portalpE');">SysInfo Konfigurieren</button><button class="SIAE" id="SysInfoAbbrechen" onclick="javascript:location.reload();">Abbrechen</button><button class="SIZE" id="SysInfoZurueck" onclick="javascript:location.reload();">Zur&uuml;ck</button>
            </div>
            <!-- -->
            <div id="n">0%</div><div id="ProgressBar"></div><div id="h">100%</div>
            <!-- Ende -->
            <div id="Footer"><p>2015</p></div>
        </div>
        <div id="menu"><a href="/admin">WebAdmin</a></div>
    </body>
</html>
