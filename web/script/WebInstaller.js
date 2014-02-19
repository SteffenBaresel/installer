/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function WebInstaller(id) {
    //$('#ActiveContent').html('<table id="ActiveContentTable"></table>');
    $.ajax({
        url: '/admin/exec/IsAlreadyInstalled',
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.INSTALLED == "1") {
                alert("Eine Version von kVASy System Control ist bereits installiert.");
            } else {
                $("#" + id).hide();
                $('#body').css('height', '824px');
                $('#Footer').css('top', '775px');
                $('#Installer').show();
            }
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

function TestFunction() {
    for (var i=0; i<100; i++) {
        $('#ActiveContentTable').append('<tr><td>' + i + '</td></tr>');
    }
}

function eraseText(id) {
    document.getElementById(id).value = "";
    document.getElementById(id).style.color="#000";
}

function execInstaller(id1,id2) {
    // Check Input Username
    $('#AdminUserHeader').css('border', '1px solid green');
    $('#Output').show();
    $('#ShowStatus').show();
    $('#ProgressBar').show();
    $('#n').show();
    $('#h').show();
    $('#Start').hide();
    Execute("20","Repository: Prepare Repository","PrepareRepository","PrepRep");
    Execute("40","Repository: Fill Default","FillDefault","FillDef");
    Execute("60","Monitoring: Prepare Data Source","PrepareMonitoring","PrepMoni");
    Execute("80","Monitoring: Fill Default","FillMonitoringDefault","FillMoniDef");
    $('#ProgressBar').progressbar( "option", { value: 100 });
    $('#Abbrechen').hide();
    alert('Installation erfolgreich abgeschlossen. Bitte konfigurieren Sie jetzt noch die fehlenden Einstellungen. Sie werden automatisch weitergeleitet.')
    location.href = 'configure.jsp';
}

function inputBlur(id){
    document.getElementById(id).style.color="#666";
    $("#" + id).attr("disabled",true);
}

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

function Execute(pv,name,fnct,id) {
    $('#ProgressBar').progressbar( "option", { value: parseInt(pv) });
    $('#OutputTable').append('<tr><td>' + name + '</td><td><div id="' + id + '"></div></td></tr>');
    $.ajax({
        url: '/admin/exec/' + fnct,
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            $('#' + id).html(json.MESSAGE);
            if (json.EXIT == "1") { $('#' + id).css('color', '#E5442D'); } else { $('#' + id).css('color', '#55E553'); }
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}