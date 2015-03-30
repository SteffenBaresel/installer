<%-- 
    Document   : monitoring
    Created on : 07.03.2014, 12:56:33
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
	<title>WebAdmin - Monitoring</title>
        <script type="text/javascript" src="public/script/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="public/script/jquery-ui-1.10.4.custom.min.js"></script>
        <script type="text/javascript" src="public/script/basics.js"></script>
        <script type="text/javascript" src="script/WebMonitoring.js"></script>
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
            #MonitoringInstance { margin-left: 200px; border: 1px solid #423939; background-color: #fff; color: #000; padding: 4px; width: 387px; cursor: pointer; }
        </style>
        <script type="text/javascript">
            $(function() {
                $('#Installer').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#Uninstaller').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#Konfigurator').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#ProgressBar').progressbar({
                    value: false
                });
                GetMonitoringInstances();
            });
        </script>
    </head>
    <body>
        <div id="body">
            <div id="Header">
                <img src="public/images/SIV_AG_Logo_RGB_Web.png" />
                <h2>WebAdmin</h2>
                <h4>f&uuml;r C&C - Reports<font color="#666"> - Version 1 Build 2015.03</font></h4>
                <p>In diesem Men&uuml; ist es m&ouml;glich die Konfiguration des Monitoring im Backend anzupassen.<br>
                Bitte w&auml;hlen Sie die Instanz aus, bei der Sie eine Konfigurations&auml;nderung vornehmen wollen.</p>
            </div>
                <select id="MonitoringInstance" class="ui-input-hofo" onchange="OpenMIConfig('MonitoringInstance');"><option value="0000" selected>Bitte w&auml;hlen Sie eine Monitoring Instanz</option></select>
                <!--a href="updater.jsp">Updater</a-->
            <div id="Footer"><p>2015</p></div>
        </div>
        <div id="menu"><a href="/admin">WebAdmin</a></div>
    </body>
</html>
