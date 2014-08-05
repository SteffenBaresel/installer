/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function ActivateButton(state,text) {
    if(state == 0) {
        $(':button:contains(\'' + text + '\')').prop('disabled', true).addClass('ui-state-disabled');
    } else {
        $(':button:contains(\'' + text + '\')').prop('disabled', false).removeClass('ui-state-disabled');
    }
}

function GetMonitoringInstances() {
    $.ajax({
        url: '/admin/exec/GetMonitoringInstances',
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            $.each(json, function() {
                $('#MonitoringInstance').append('<option value=\"' + this.ID + '\">' + base64_decode( this.NAME ) + ' (' + base64_decode( this.IDTF ) + ')</option>');
            });
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

function OpenMIConfig(id) {
    $.ajax({
        url: '/admin/exec/GetConfigFiles?instid=' + $('#' + id).val(),
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.length > 0) {
                $.each(json, function() {
                    window.open('/admin/mcfg.jsp?mcfgid=' + this.MCFGID + '&instid=' + $('#' + id).val(),'Konfiguration: ' + base64_decode( this.NAME ) + ' Zuletzt bearbeitet: ' + this.CREATED,'menubar=0,location=0,top=0,left=0,toolbar=0,personalbar=0,width=1250,height=800,resizable=no');
                });
            } else {
                alert("Keine Konfigurationsdateien hinterlegt.");
            }
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

function GetContent(id) {    
    $.ajax({
        url: '/admin/exec/GetContent?mcfgid=' + id,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            $('#McfgName').html(base64_decode( json.NAME ));
            $('#McfgText').val(base64_decode( json.CONTENT ));
        },
        error: function (xhr, thrownError) {
            alert("Keine Konfigurationsdateien hinterlegt.");
        }
    });
}

function GetConfigHosts(id) {
    $.ajax({
        url: '/admin/exec/GetConfigHosts?mcfgid=' + id,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            $('#GetConfigHostsSelect').html('<option value="0000" selected>Bitte einen Host w&auml;hlen!</option>');
            for(var i=0;i<json.length;i++) {
                $('#GetConfigHostsSelect').append('<option value="' + json[i] + '">' + json[i] + '</option>');
            };
        },
        error: function (xhr, thrownError) {
            alert("Fehler bei der Abfrage der konfigurierten Hosts.");
        }
    });
}

function UpdateConfig(id,mcfgid) {    
    var text = base64_encode( $('#' + id).val() ).replace(/\+/g,'78');
    $.ajax({
        url: '/admin/exec/UpdateConfig?text=' + text + '&mcfgid=' + mcfgid,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if(json.EXEC == 1) {
                alert("Konfiguration wurde gespeichert. Damit die neue Konfiguration aktiv wird, starten Sie bitte das Monitoring Backend einmal neu.");
                GetContent(mcfgid);
                GetConfigHosts(mcfgid);
            } else {
                alert("Konfiguration konnte nicht gespeichert werden.");
            }
        },
        error: function (xhr, thrownError) {
            alert("Keine Konfigurationsdateien hinterlegt.");
        }
    });
}

function InsertTask(taskid,comment,instid) {
    $.ajax({
        url: '/admin/exec/InsertTask?taskid=' + taskid + '&comment=' + base64_encode( comment ) + '&instid=' + instid,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            var tid = json.TID;
            if(json.EXEC == 1) {
                $('#ldialog').remove();
                $('#dialog').html('<div id="ldialog" title="' + comment + '"><div id="ldialogContent"><div id="ldialogTaskOutput"></div></div></div>');
                $('#ldialog').dialog({
                    autoOpen: true,
               	    height: 300,
               	    width: 800,
               	    draggable: false,
               	    resizable: false,
               	    modal: true,
                    open: function() {
                        ActivateButton(0,'OK');
                        $('button').addClass('ui-input-hofo');
                        $('#ldialogTaskOutput').append('<div id="OutputDiv"><span>' + comment + '</span><span>Bitte warten...</span></div>');
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',5000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',15000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',30000);
                    },
                    buttons: {
                        OK: function() {
                            $(this).dialog('close');
                            $('#ldialog').remove();
                        }
                    }
                }).parent().find('.ui-dialog-titlebar-close').hide();
                
            } else {
                alert("Die Aktion konnte nicht eingeplant werden.");
            }
        },
        error: function (xhr, thrownError) {
            alert("Die Aktion konnte nicht eingeplant werden.");
        }
    });
}

function InsertTaskH(taskid,id,comment,instid) {
    $.ajax({
        url: '/admin/exec/InsertTask?taskid=' + taskid + '&comment=' + base64_encode( $('#' + id).val() ) + '&instid=' + instid,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            var tid = json.TID;
            if(json.EXEC == 1) {
                $('#ldialog').remove();
                $('#dialog').html('<div id="ldialog" title="' + comment + '"><div id="ldialogContent"><div id="ldialogTaskOutput"></div></div></div>');
                $('#ldialog').dialog({
                    autoOpen: true,
               	    height: 300,
               	    width: 800,
               	    draggable: false,
               	    resizable: false,
               	    modal: true,
                    open: function() {
                        ActivateButton(0,'OK');
                        $('button').addClass('ui-input-hofo');
                        $('#ldialogTaskOutput').append('<div id="OutputDiv"><span>' + comment + '</span><span>Bitte warten...</span></div>');
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',5000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',15000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',30000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',45000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',60000);
                        setTimeout('GetTaskOuput(' + tid + ',\'ldialogTaskOutput\');',90000);
                    },
                    buttons: {
                        OK: function() {
                            $(this).dialog('close');
                            $('#ldialog').remove();
                        }
                    }
                }).parent().find('.ui-dialog-titlebar-close').hide();
                
            } else {
                alert("Die Aktion konnte nicht eingeplant werden.");
            }
        },
        error: function (xhr, thrownError) {
            alert("Die Aktion konnte nicht eingeplant werden.");
        }
    });
}

function GetTaskOuput(tid,id) {
    $.ajax({
        url: '/admin/exec/GetTaskOutput?tid=' + tid,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.length > 0) {
                $('#' + id).html('<div id="TaskOutput"></div>');
                $.each(json, function() {
                    $('#TaskOutput').append('<div id="OutputDiv"><span>' + base64_decode( this.LINE ) + '</span><span>' + this.CREATED + '</span></div>');
                });
                ActivateButton(1,'OK');
            }
        },
        error: function (xhr, thrownError) {
            alert(xhr + " :: " + thrownError);
        }
    });
}