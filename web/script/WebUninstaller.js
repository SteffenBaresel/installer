/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function WebUninstaller(id) {
    $.ajax({
        url: '/admin/exec/IsAlreadyInstalled',
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.INSTALLED == "0") {
                alert("Es ist keine Version von kVASy System Control installiert.");
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

function eraseText(id) {
    document.getElementById(id).value = "";
    document.getElementById(id).style.color="#000";
}

function execUninstaller() {
    // Check Input Username
    $('#AdminUserHeader').css('border', '1px solid green');
    $('#Output').show();
    $('#ShowStatus').show();
    $('#ProgressBar').show();
    $('#n').show();
    $('#h').show();
    $('#Start').hide();
    Execute("33","Repository: Unprepare","UnprepareRepository","UnprepRep");
    Execute("66","Monitoring: Unprepare","UnprepareMonitoring","UnprepMoni");
    $('#ProgressBar').progressbar( "option", { value: 100 });
    //$('#OutputTable').append('<tr><td>Prepare Repository</td><td><div id="PrepRep"></div></td></tr>');
    $('#Abbrechen').hide();
    alert('Deinstallation erfolgreich abgeschlossen. Bitte schließen Sie die Seite.')
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