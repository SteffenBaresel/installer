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
	<title>WebUninstaller</title>
        <script type="text/javascript" src="script/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="script/jquery-ui-1.9.0.custom.min.js"></script>
        <script type="text/javascript" src="script/WebUninstaller.js"></script>
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
            #Header button#StartF { position: absolute; margin-left: 75px; margin-top: 225px; }
            #Footer { position: absolute; top: 325px; width: 825px; height: 57px; border-top: 1px solid #004279; background-color: #004279; font-size: 10px; color: #82abcc; text-align: center; }
            #Footer p { margin-top: 20px; }
            body a { text-decoration: none; color: #004279; font-size: 12px;}
            #menu { width: 825px; height: 25px; text-align: center; vertical-align: middle; color: #004279; }
            
            button#Start { position: absolute; margin-left: 500px; margin-top: -52px; }
            button#Abbrechen { position: absolute; margin-left: 567px; margin-top: -52px; }
            #ShowStatus, #Installer { display: none; }
            #AdminUser { height: 100px; padding: 10px; }
            #AdminUser div#AdminUserHeader { margin-top: -22px; background-color: #004279; border: 1px solid #FF6969; padding: 5px; font-size: 12px; margin-bottom: 20px; text-align: center;}
            #Output { position: absolute; width: 790px; height: 325px; border: 1px solid #82abcc; top: 420px;left: 25px; background-color: #004c8a; overflow-y: scroll; display: none; }
            #ProgressBar .ui-progressbar-value { background-color: #82abcc; }
            #ProgressBar { display: none; width: 500px; border: 1px solid #82abcc; position: absolute; left: 150px; margin-top: 23px; height: 15px; padding: 2px; }
            #n { display: none; position: absolute; left: 130px; font-size: 12px; color: #82abcc; margin-top: 25px; }
            #h { display: none; position: absolute; left: 660px; font-size: 12px; color: #82abcc; margin-top: 25px; }
            #OutputTable { width: 100%; font-size: 14px; table-layout: fixed; border-spacing:0; border-collapse:collapse; }
            #OutputTable td:first-child { width: 200px; text-align: left; }
            #OutputTable td:last-child { text-align: right; }
            #OutputTable td { border-bottom: 1px solid #82abcc; padding: 5px; }
        </style>
        <script type="text/javascript">
            $(function() {
                $('#StartF').button();
                $('#Start').button();
                $('#Abbrechen').button();
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
                <h2>WebUninstaller</h2>
                <h4>f&uuml;r kVASy&reg; System Control<font color="#82abcc"> - Version 2 Update 3</font></h4>
                <p>Dieser WebUninstaller deinstalliert Basis Einstellungen f&uuml;r das kVASy&reg; System Control in der <br>Version 2 Update 3.
                Durch dr&uuml;cken auf den Start Button werden die notwendigen Prozesse im Hintergrund <br>gestartet und &uuml;berwacht.
                Eventuelle Fehler werden direkt in der GUI angezeigt.</p>
                <button id="StartF" onclick="WebUninstaller('StartF');">Start</button>
            </div>
            <div id="Installer">
                <div id="AdminUser">
                    <div id="AdminUserHeader">Es werden alle Daten gel&ouml;scht!</div>
                </div>
                <div id="n">0%</div><div id="ProgressBar"></div><div id="h">100%</div>
                <div id="Output"><table id="OutputTable"></table></div>
                <button id="Start" onclick="execUninstaller();">Start</button><button id="Abbrechen" onclick="javascript:location.reload();">Abbrechen</button>
            </div>
            <div id="Footer"><p>2013</p></div>
        </div>
        <div id="menu"><a href="/installer/">WebManager</a></div>
    </body>
</html>
