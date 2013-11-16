<%-- 
    Document   : index.jsp
    Created on : 30.10.2013, 15:03:02
    Author     : sbaresel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link rel="shortcut icon" href="layout/images/favicon.ico" type="image/vnd.microsoft.icon" />
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="author" content="Steffen Baresel">
	<meta name="description" content="WebInstaller">
	<title>WebKonfigurator</title>
        <script type="text/javascript" src="script/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="script/jquery-ui-1.9.0.custom.min.js"></script>
        <script type="text/javascript" src="script/WebKonfigurator.js"></script>
        <script type="text/javascript" src="script/kSCbase64.js"></script>
        <link rel='stylesheet' href='layout/jquery-ui-1.9.0.custom.css' />
        <!--
            Erweiterungen
        -->
        <style type="text/css">
            @font-face { font-family: SansProLight; src: url(layout/SourceSansPro-Regular.ttf) format("truetype"); }
            body { background-color: #D8D8D8; color: #ffffff; font-size: 16px; font-family: SansProLight; }
            #body { border: 1px solid #004c8a; width: 825px; height: 375px; background-color: #004c8a; -webkit-box-shadow: 0px 0px 10px #292929; /* webkit browser*/ -moz-box-shadow: 0px 0px 10px #292929; /* firefox */ box-shadow: 0px 0px 10px #292929; }
            #Header { width: 825px; height: 225px; }
            #Header img { position: absolute; margin-left: 680px; margin-top: 25px;}
            #Header h2 { position: absolute; margin-left: 50px; margin-top: 35px;}
            #Header h4 { position: absolute; margin-left: 50px; margin-top: 60px;}
            #Header p { position: absolute; margin-left: 50px; margin-top: 125px;}
            #Header button#MailConfig { position: absolute; margin-left: 75px; margin-top: 225px; }
            #Footer { position: absolute; top: 325px; width: 825px; height: 57px; border-top: 1px solid #004279; background-color: #004279; font-size: 10px; color: #82abcc; text-align: center; }
            #Footer p { margin-top: 20px; }
            body a { text-decoration: none; color: #004279; font-size: 12px;}
            #menu { width: 825px; height: 25px; text-align: center; vertical-align: middle; color: #004279; }
            
            button#MailStart { position: absolute; margin-left: 500px; margin-top: -60px; }
            button#MailAbbrechen { position: absolute; margin-left: 667px; margin-top: -60px; }
            button#MailZurueck { display: none; position: absolute; margin-left: 667px; margin-top: -60px; }
            #ShowStatus, #MailConfigure, #MailEdit, #ConfAdminUser, #EdtAdminUser { display: none; }
            #MailAdminUser { padding: 10px; }
            #MailAdminUser div#MailAdminUserHeader { margin-top: -22px; background-color: #004279; border: 1px solid #FF6969; padding: 5px; font-size: 12px; margin-bottom: 20px; text-align: center;}
            #MailAdminUser table { margin-left: 30px; }
            #MailAdminUser table td:first-child { font-size: 12px; text-align: right; }
            #MailAdminUser input { width: 200px; border: 1px solid #82abcc; background-color: #004279; color: #fff; padding: 5px;}
            #MailOutput { position: absolute; width: 790px; height: 50px; border: 1px solid #82abcc; top: 420px;left: 25px; background-color: #004c8a; overflow-y: scroll; display: none; }
            #ProgressBar .ui-progressbar-value { background-color: #82abcc; }
            #ProgressBar { display: none; width: 500px; border: 1px solid #82abcc; position: absolute; left: 150px; margin-top: 15px; height: 15px; padding: 2px; }
            #n { display: none; position: absolute; left: 130px; font-size: 12px; color: #82abcc; margin-top: 17px; }
            #h { display: none; position: absolute; left: 660px; font-size: 12px; color: #82abcc; margin-top: 17px; }
            #MailOutputTable { width: 100%; font-size: 14px; table-layout: fixed; border-spacing:0; border-collapse:collapse; }
            #MailOutputTable td:first-child { width: 200px; text-align: left; }
            #MailOutputTable td:last-child { text-align: right; }
            #MailOutputTable td { border-bottom: 1px solid #82abcc; padding: 5px; }
            
            #Header button#ButtonAdminUser { position: absolute; margin-left: 225px; margin-top: 225px; }
            button#AdminStart { position: absolute; margin-left: 500px; margin-top: -60px; }
            button#AdminAbbrechen { position: absolute; margin-left: 678px; margin-top: -60px; }
            button#AdminZurueck { display: none; position: absolute; margin-left: 667px; margin-top: -60px; }
            #ConfigureAdminUser,#EditAdminUser { padding: 10px; }
            #ConfigureAdminUser div#ConfigureAdminUserHeader,#EditAdminUser div#EditAdminUserHeader { margin-top: -22px; background-color: #004279; border: 1px solid #FF6969; padding: 5px; font-size: 12px; margin-bottom: 20px; text-align: center;}
            #ConfigureAdminUser table,#EditAdminUser table { margin-left: 30px; }
            #ConfigureAdminUser table td:first-child,#EditAdminUser table td:first-child { font-size: 12px; text-align: right; }
            #ConfigureAdminUser input,#EditAdminUser input { width: 200px; border: 1px solid #82abcc; background-color: #004279; color: #fff; padding: 5px;}
        </style>
        <script type="text/javascript">
            $(function() {
                $('#MailStart').button();
                $('#MailAbbrechen').button();
                $('.MSE').button();
                $('.MAE').button();
                $('.MZE').button();
                $('#MailZurueck').button();
                $('#MailConfig').button();
                $('#ButtonAdminUser').button();
                $('#AdminStart').button();
                $('#AdminAbbrechen').button();
                $('.ASE').button();
                $('.AAE').button();
                $('.AZE').button();
                $('#AdminZurueck').button();
                $('#ProgressBar').progressbar({
                    value: false
                });
            });
        </script>
    </head>
    <body>
        <div id="body">
            <div id="Header">
                <img src="layout/images/logo_backgroundblue_whitetext.png" />
                <h2>WebKonfigurator</h2>
                <h4>f&uuml;r kVASy&reg; System Control<font color="#82abcc"> - Version 2 Update 3</font></h4>
                <p>Dieser WebKonfigurator konfiguriert erweiterte Einstellungen f&uuml;r das kVASy&reg; System Control in der <br>Version 2 Update 3.</p>
                <button id="MailConfig" onclick="MailConfig('MailConfig');">Mail Konfiguration</button><button id="ButtonAdminUser" onclick="AdminUser('ButtonAdminUser');">Admin User</button>
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
            <!-- AdminUser -->
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
            <!-- -->
            <div id="n">0%</div><div id="ProgressBar"></div><div id="h">100%</div>
            <!-- Ende -->
            <div id="Footer"><p>2013</p></div>
        </div>
        <div id="menu"><a href="/installer/">WebManager</a></div>
    </body>
</html>
