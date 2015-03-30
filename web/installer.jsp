<%-- 
    Document   : index.jsp
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
	<title>WebAdmin - Installer</title>
        <script type="text/javascript" src="public/script/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="public/script/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="public/script/basics.js"></script>
        <script type="text/javascript" src="script/WebInstaller.js"></script>
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
        
            button#StartF { position: absolute; margin-left: 380px; margin-top: 250px; }
            button#Start { position: absolute; margin-left: 500px; margin-top: -52px; }
            button#Abbrechen { position: absolute; margin-left: 567px; margin-top: -52px; }
            #ShowStatus, #Installer { display: none; }
            #AdminUser { height: 100px; padding: 10px; }
            #AdminUser div#AdminUserHeader { margin-top: -22px; background-color: #fff; border: 1px solid #423939; padding: 5px; font-size: 12px; margin-bottom: 20px; margin-left: -30px; text-align: center; width: 700px;}
            #Output { position: absolute; width: 790px; height: 325px; border: 1px solid #423939; top: 420px;left: 25px; background-color: #fff; overflow-y: scroll; display: none; }
            #ProgressBar .ui-progressbar-value { background-color: #fff; }
            #ProgressBar { display: none; width: 500px; border: 1px solid #423939; position: absolute; left: 150px; margin-top: 23px; height: 15px; padding: 2px; }
            #n { display: none; position: absolute; left: 130px; font-size: 12px; color: #000; margin-top: 25px; }
            #h { display: none; position: absolute; left: 660px; font-size: 12px; color: #000; margin-top: 25px; }
            #OutputTable { width: 100%; font-size: 14px; table-layout: fixed; border-spacing:0; border-collapse:collapse; }
            #OutputTable td:first-child { width: 200px; text-align: left; }
            #OutputTable td:last-child { text-align: right; }
            #OutputTable td { border-bottom: 1px solid #423939; padding: 5px; }
        </style>
        <script type="text/javascript">
            $(function() {
                $('#StartF').button().addClass('ui-input-hofo');
                $('#Start').button().addClass('ui-input-hofo');
                $('#Abbrechen').button().addClass('ui-input-hofo');
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
                <h2>WebAdmin - Installer</h2>
                <h4>f&uuml;r C&C - Reports<font color="#666"> - Version 1 Build 2015.03</font></h4>
                <p>Dieser WebInstaller installiert Basis Einstellungen f&uuml;r das C&C - Reports in der <br>Version 1 Build 2015.03.
                Durch dr&uuml;cken auf den Start Button werden die notwendigen Prozesse im Hintergrund <br>gestartet und &uuml;berwacht.
                Eventuelle Fehler werden direkt in der GUI angezeigt.</p>
                <button id="StartF" onclick="WebInstaller('StartF');">Start</button>
            </div>
            <div id="Installer">
                <div id="AdminUser">
                    <div id="AdminUserHeader">Die SQL Datasource wird jetzt f&uuml;r den Betrieb von kVASy&reg; System Control vorbereitet.</div>
                </div>
                <div id="n">0%</div><div id="ProgressBar"></div><div id="h">100%</div>
                <div id="Output"><table id="OutputTable"></table></div>
                <button id="Start" onclick="execInstaller();">Start</button><button id="Abbrechen" onclick="javascript:location.reload();">Abbrechen</button>
            </div>
            <div id="Footer"><p>2015</p></div>
        </div>
        <div id="menu"><a href="/admin">WebAdmin</a></div>
    </body>
</html>
