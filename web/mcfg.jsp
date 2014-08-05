<%-- 
    Document   : mcfg
    Created on : 09.03.2014, 17:47:45
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
	<title>WebAdmin</title>
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
            body { background-image: url(public/images/bg.png); color: #000; font-size: 16px; font-family: SansProLight; cursor: default; }
            #body { border: 1px solid #423939; width: 1231px; height: 782px; background-color: #fff; -webkit-box-shadow: 0px 0px 2px 0px #423939; /* webkit browser*/ -moz-box-shadow: 0px 0px 2px 0px #423939; /* firefox */ box-shadow: 0px 0px 2px 0px #423939; }
            #Header { width: 825px; height: 170px; }
            #Header img { position: absolute; right: 30px; margin-top: 15px;}
            #Header h2 { position: absolute; margin-left: 50px; margin-top: 35px;}
            #Header h4 { position: absolute; margin-left: 50px; margin-top: 60px;}
            #Header p { position: absolute; margin-left: 50px; margin-top: 125px;}
            #Footer { position: absolute; left: 12px; top: 740px; right: 12px; height: 50px; border-top: 1px solid #423939; background-color: #fff; font-size: 10px; color: #423939; text-align: center; }
            #Footer p { margin-top: 20px; }
            #menu a { text-decoration: none; color: #000; font-size: 12px;}
            li a { text-decoration: none; color: #000; font-size: 12px; font-weight: normal; }
            #Installer { margin-left: 75px; margin-top: 0px; }
            #menu { width: 825px; height: 25px; text-align: center; vertical-align: middle; }
            .ui-input-hofo:focus, .ui-input-hofo:hover { -webkit-box-shadow: 0px 0px 2px 0px #423939; -moz-box-shadow: 0px 0px 2px 0px #423939; box-shadow: 0px 0px 2px 0px #423939; }
            #body textarea { border: 1px solid #423939; resize: none; width: 1205px; height: 450px; margin-left: -10px; font-family: monospace; }
            #McfgButton { position: absolute; right: 20px; bottom: 20px; z-index: 55;}
            #McfgButton4, #McfgButton6 { margin-top: -4px; height: 33px;}
            #tabs-1, #tabs-2 { height: 500px; }
            #body .ui-widget-content { border: 0; }
            #ConfigurationSection { width: 97%; padding: 10px; margin-top: 10px; font-size: 14px; }
            #ConfigurationSectionTitle { margin-top: -24px; padding: 5px; background-color: #fff; border-bottom: 1px solid #423939; font-size: 14px; font-weight: bold; margin-bottom: 10px; }
            .ConfigurationSectionPoint { margin-top: 10px; }
            #body select { border: 1px solid #423939; width: 387px; padding: 5px; }
            #ldialogTaskOutput { width: 770px; height: 165px; overflow-y: auto; }
            #ldialogTaskOutput div#OutputDiv { border: 1px solid #423939; height: 20px; margin: 2px; padding: 7px; background-color: #fff; color: #000; }
            #ldialogTaskOutput div#OutputDiv:hover { background-color: #423939; color: #fff; }
            #ldialogTaskOutput span:first-child { float: left; width: 550px; }
            #ldialogTaskOutput span:last-child { float: right; width: 130px; }
        </style>
        <script type="text/javascript">
            $(function() {
                $('#tabs').tabs();
                $('#Installer').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#Uninstaller').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#Konfigurator').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#Monitoring').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#McfgButton').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#McfgButton2').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#McfgButton3').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#McfgButton4').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#McfgButton5').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#McfgButton6').button().css('border','1px solid #423939').addClass('ui-input-hofo');
                $('#ProgressBar').progressbar({
                    value: false
                });
                GetContent('<%= request.getParameter("mcfgid") %>');
                GetConfigHosts('<%= request.getParameter("mcfgid") %>');
            });
        </script>
    </head>
    <body>
        <div id="dialog"></div>
        <div id="body">
            <div id="Header">
                <img src="public/images/SIV_AG_Logo_RGB_Web.png" />
                <h2>WebAdmin</h2>
                <h4>f&uuml;r kVASy&reg; System Control<font color="#666"> - Version 3 Build 2014.02</font></h4>
                <p>Hier können Sie die Konfiguration bearbeiten und basis Aktionen durchf&uuml;hren.</p>
            </div>
            <div id="tabs">
                <ul>
                    <li><a href="#tabs-1">Konfigurationsdatei: <span style="font-weight: bold;" id="McfgName"></span></a></li>
                    <li><a href="#tabs-2">Servicemen&uuml;</a></li>
                </ul>
                <div id="tabs-1">                    
                    <textarea id="McfgText" class="ui-input-hofo"></textarea>
                    <button id="McfgButton" onclick="UpdateConfig('McfgText','<%= request.getParameter("mcfgid") %>');">Speichern</button>
                </div>
                <div id="tabs-2">
                    <div id="ConfigurationSection">
                        <div id="ConfigurationSectionTitle">Inventory aller Hosts</div>
                        <button id="McfgButton3" onclick="InsertTask('102','++ L&ouml;sche Cache und suche nach Services ++ wurde initiiert.','<%= request.getParameter("instid") %>');" title="L&ouml;sche Cache und suche nach Services">Inventory</button>
                        <button id="McfgButton5" onclick="InsertTask('101','++ Suche nach neuen Services ++ wurde initiiert.','<%= request.getParameter("instid") %>');" title="Suche nach neuen Services">Scan</button>
                    </div>
                    <div id="ConfigurationSection">
                        <div id="ConfigurationSectionTitle">Inventory eines neuen Hosts</div>
                        <select id="GetConfigHostsSelect"></select>
                        <button id="McfgButton4" onclick="InsertTaskH('104','GetConfigHostsSelect','++ L&ouml;sche Cache und suche nach Services auf dem Host ++ wurde initiiert.','<%= request.getParameter("instid") %>');" title="L&ouml;sche Cache und suche nach Services auf dem Host">Inventory</button>
                        <button id="McfgButton6" onclick="InsertTaskH('103','GetConfigHostsSelect','++ Suche nach neuen Services auf dem Host ++ wurde initiiert.','<%= request.getParameter("instid") %>');" title="Suche nach neuen Services auf dem Host">Scan</button>
                    </div>
                    <div id="ConfigurationSection">
                        <div id="ConfigurationSectionTitle">Neustarten des Monitoring Backends</div>
                        <button id="McfgButton2" onclick="InsertTask('100','Neustart wurde initiiert.','<%= request.getParameter("instid") %>');">Neustarten</button>
                    </div>
                </div>
            </div>
            <div id="Footer"><p>2014</p></div>
        </div>
    </body>
</html>
