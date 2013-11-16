<%-- 
    Document   : index
    Created on : 14.11.2013, 14:51:48
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
	<title>WebManager</title>
        <script type="text/javascript" src="script/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="script/jquery-ui-1.9.0.custom.min.js"></script>
        <script type="text/javascript" src="script/WebInstaller.js"></script>
        <link rel='stylesheet' href='layout/jquery-ui-1.9.0.custom.css' />
        <!--
            Erweiterungen
        -->
        <style type="text/css">
            @font-face { font-family: SansProLight; src: url(layout/SourceSansPro-Regular.ttf) format("truetype"); }
            body { background-color: #D8D8D8; color: #ffffff; font-size: 16px; font-family: SansProLight; }
            #body { border: 1px solid #004c8a; width: 825px; height: 425px; background-color: #004c8a; -webkit-box-shadow: 0px 0px 10px #292929; /* webkit browser*/ -moz-box-shadow: 0px 0px 10px #292929; /* firefox */ box-shadow: 0px 0px 10px #292929; }
            #Header { width: 825px; height: 225px; }
            #Header img { position: absolute; margin-left: 680px; margin-top: 25px;}
            #Header h2 { position: absolute; margin-left: 50px; margin-top: 35px;}
            #Header h4 { position: absolute; margin-left: 50px; margin-top: 60px;}
            #Header p { position: absolute; margin-left: 50px; margin-top: 125px;}
            #Footer { position: absolute; top: 375px; width: 825px; height: 57px; border-top: 1px solid #004279; background-color: #004279; font-size: 10px; color: #82abcc; text-align: center; }
            #Footer p { margin-top: 20px; }
            #menu a { text-decoration: none; color: #004279; font-size: 12px;}
            li a { text-decoration: none; color: #ffffff; font-size: 18px; font-weight: bold; }
            #Installer { margin-left: 75px; margin-top: 25px; }
            #menu { width: 825px; height: 25px; text-align: center; vertical-align: middle; }
        </style>
        <script type="text/javascript">
            $(function() {
                $('#Installer').button().css('border','1px solid #82abcc');
                $('#Uninstaller').button().css('border','1px solid #82abcc');
                $('#Konfigurator').button().css('border','1px solid #82abcc');
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
                <h2>WebManager</h2>
                <h4>f&uuml;r kVASy&reg; System Control<font color="#82abcc"> - Version 2 Update 3</font></h4>
                <p>Dieser WebManager installiert und konfiguriert, deinstalliert und updated Basis Einstellungen f&uuml;r das<br>
                kVASy&reg; System Control in der Version 2 Update 3. Durch dr&uuml;cken auf den entsprechenden Link gelangen Sie auf<br>
                die entsprechende Oberfl&auml;che um die Aufgabe zu erledigen.</p>
            </div>
                <a id="Installer" href="installer.jsp">Installer</a>
                <a id="Uninstaller" href="uninstaller.jsp">Deinstaller</a>
                <a id="Konfigurator" href="configure.jsp">Konfigurator</a>
                <!--a href="updater.jsp">Updater</a-->
            <div id="Footer"><p>2013</p></div>
        </div>
        <div id="menu"><a href="api/">API</a></div>
    </body>
</html>
