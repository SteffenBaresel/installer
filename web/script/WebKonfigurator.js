/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function MailConfig(id) {
    //$('#ActiveContent').html('<table id="ActiveContentTable"></table>');
    $.ajax({
        url: '/admin/exec/MailApiIsAlreadyConfigured',
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.INSTALLED == "1") {
                $("#" + id).hide();
                $("#ButtonAdminUser").hide();
                $("#ButtonSysInfo").hide();
                $('#body').css('height', '524px');
                $('#Footer').css('top', '475px');
                $('#MailEdit').show();
                $('#hostE').val($.base64.decode( json.HOST ));
                $('#portE').val($.base64.decode( json.PORT ));
                $('#userE').val($.base64.decode( json.USER ));
                $('#passE').val($.base64.decode( json.PASS ));
            } else {
                $("#" + id).hide();
                $('#body').css('height', '524px');
                $('#Footer').css('top', '475px');
                $("#ButtonAdminUser").hide();
                $("#ButtonSysInfo").hide();
                $('#MailConfigure').show();
            }
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

function AdminUser(id) {
    $.ajax({
        url: '/admin/exec/AdminUserIsAlreadyConfigured',
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.INSTALLED == "1") {
                $("#" + id).hide();
                $("#MailConfig").hide();
                $("#ButtonSysInfo").hide();
                $('#body').css('height', '474px');
                $('#Footer').css('top', '425px');
                $('#EdtAdminUser').show();
                $('#unE').val($.base64.decode( json.SN ));
                $('#nmE').val($.base64.decode( json.LN ));
            } else {
                $("#" + id).hide();
                $("#MailConfig").hide();
                $("#ButtonSysInfo").hide();
                $('#body').css('height', '474px');
                $('#Footer').css('top', '425px');
                $('#ConfAdminUser').show();
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

function execMailConfigure(id1,id2,id3,id4) {
    if ( document.getElementById(id1).value == "" ) {
        document.getElementById(id1).style.color="#E5442D";
        alert("Der Host ist nicht korrekt eingetragen.");
    } else {
        if ( document.getElementById(id2).value == "" ) {
            document.getElementById(id2).style.color="#E5442D";
            alert("Der Port ist nicht korrekt eingetragen.");
        } else {
            if ( document.getElementById(id3).value == "" ) {
                document.getElementById(id3).style.color="#E5442D";
                alert("Der Name ist nicht korrekt eingetragen.");
            } else {
                if ( document.getElementById(id4).value == "" ) {
                    document.getElementById(id4).style.color="#E5442D";
                    alert("Das Passwort ist nicht korrekt eingetragen.");
                } else {
                    $('#MailAdminUserHeader').css('border', '1px solid green');
                    $('#ShowStatus').show();
                    $('#ProgressBar').show();
                    $('#n').show();
                    $('#h').show();
                    $('#MailStart').hide();
                    $('.MSE').hide();
                    inputBlur(id1);
                    inputBlur(id2);
                    inputBlur(id3);
                    inputBlur(id4);
                    ConfigureMailApi("50",document.getElementById(id1).value,document.getElementById(id2).value,document.getElementById(id3).value,document.getElementById(id4).value);
                    $('#ProgressBar').progressbar( "option", { value: 100 });
                    $('#MailAbbrechen').hide();
                    $('.MAE').hide();
                    $('#MailZurueck').show();
                    $('.MZE').show();
                    alert("MailApi erfolgreich konfiguriert.");
                }
            }
        }
    }
}

function execAdminUser(id1,id2) {
    // Check Input Username
    if ( document.getElementById(id1).value == "" ) {
        document.getElementById(id1).style.color="#E5442D";
        alert("Der Nutzername ist nicht korrekt eingetragen.");
    } else if ( document.getElementById(id1).value == "mmustermann" ) {
        document.getElementById(id1).style.color="#E5442D";
        alert("Der Nutzername ist nicht korrekt eingetragen.");
    } else {
        // Check Input Name
        if ( document.getElementById(id2).value == "" ) {
            document.getElementById(id2).style.color="#E5442D";
            alert("Der Name ist nicht korrekt eingetragen.");
        } else if ( document.getElementById(id2).value == "Max Mustermann" ) {
            document.getElementById(id2).style.color="#E5442D";
            alert("Der Name ist nicht korrekt eingetragen.");
        } else {
            $('#ConfigureAdminUserHeader').css('border', '1px solid green');
            $('#EditAdminUserHeader').css('border', '1px solid green');
            $('#ShowStatus').show();
            $('#ProgressBar').show();
            $('#n').show();
            $('#h').show();
            $('#AdminStart').hide();
            $('.ASE').hide();
            inputBlur(id1);
            inputBlur(id2);
            CreateAdmin("50",document.getElementById(id1).value,document.getElementById(id2).value);
            $('#ProgressBar').progressbar( "option", { value: 100 });
            $('#AdminAbbrechen').hide();
            $('.AAE').hide();
            $('#AdminZurueck').show();
            $('.AZE').show();
            alert("Admin User erfolgreich bearbeitet.");
        }
    }
}

function inputBlur(id){
    document.getElementById(id).style.color="#666666";
    $("#" + id).attr("disabled",true);
}

function inputAct(id){
    $("#" + id).attr("disabled",false);
}

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

function ConfigureMailApi(pv,host,port,user,pass) {
    $('#ProgressBar').progressbar( "option", { value: parseInt(pv) });
    $.ajax({
        url: '/admin/exec/ConfigureMailApi?host=' + $.base64.encode( host ) + '&port=' + $.base64.encode( port ) + '&user=' + $.base64.encode( user ) + '&pass=' + $.base64.encode( pass ),
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            // nothing
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

function CreateAdmin(pv,sn,ln) {
    $('#ProgressBar').progressbar( "option", { value: parseInt(pv) });
    $.ajax({
        url: '/admin/exec/AdminUser?sn=' + $.base64.encode( sn ) + '&ln=' + $.base64.encode( ln ),
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            //
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

/* SysInfo */

function SysInfo(id) {
    $.ajax({
        url: '/admin/exec/SysInfoIsAlreadyConfigured',
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            if (json.INSTALLED == "1") {
                $("#" + id).hide();
                $("#ButtonSysInfo").hide();
                $("#ButtonAdminUser").hide();
                $("#MailConfig").hide();
                $('#body').css('height', '524px');
                $('#Footer').css('top', '475px');
                $('#SysInfoEdit').show();
                $('#mainvE').val($.base64.decode( json.MAINV ));
                inputAct('mainvE');
                $('#updatevE').val($.base64.decode( json.UPDATEV ));
                inputAct('updatevE');
                $('#buildvE').val($.base64.decode( json.BUILDV ));
                inputAct('buildvE');
                $('#portalpE').val($.base64.decode( json.PORTALP ));
                inputAct('portalpE');
            } else {
                $("#" + id).hide();
                $('#body').css('height', '524px');
                $('#Footer').css('top', '475px');
                $("#ButtonSysInfo").hide();
                $("#ButtonAdminUser").hide();
                $("#MailConfig").hide();
                $('#SysInfoConfigure').show();
                inputAct('mainvE');
                inputAct('updatevE');                
                inputAct('buildvE');
                inputAct('portalpE');
            }
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

function execSysInfoConfigure(id1,id2,id3,id4) {
    if ( document.getElementById(id1).value == "" ) {
        document.getElementById(id1).style.color="#E5442D";
        alert("Der Pfad ist nicht korrekt eingetragen.");
    } else {
        if ( document.getElementById(id2).value == "" ) {
            document.getElementById(id2).style.color="#E5442D";
            alert("Die Release Version ist nicht korrekt eingetragen.");
        } else {
            if ( document.getElementById(id3).value == "" ) {
                document.getElementById(id3).style.color="#E5442D";
                alert("Die Update Version ist nicht korrekt eingetragen.");
            } else {
                if ( document.getElementById(id4).value == "" ) {
                    document.getElementById(id4).style.color="#E5442Ds";
                    alert("Die Build Version ist nicht korrekt eingetragen.");
                } else {
                    $('#SysInfoUserHeader').css('border', '1px solid green');
                    $('#ShowStatus').show();
                    $('#ProgressBar').show();
                    $('#n').show();
                    $('#h').show();
                    $('#SysInfoStart').hide();
                    $('.SISE').hide();
                    inputBlur(id1);
                    inputBlur(id2);
                    inputBlur(id3);
                    inputBlur(id4);
                    ConfigureSysInfo("50",document.getElementById(id1).value,document.getElementById(id2).value,document.getElementById(id3).value,document.getElementById(id4).value);
                    $('#ProgressBar').progressbar( "option", { value: 100 });
                    $('#SysInfoAbbrechen').hide();
                    $('.SIAE').hide();
                    $('#SysInfoZurueck').show();
                    $('.SIZE').show();
                    alert("SysInfo erfolgreich konfiguriert.");
                }
            }
        }
    }
}

function ConfigureSysInfo(pv,mainv,updatev,buildv,portalp) {
    $('#ProgressBar').progressbar( "option", { value: parseInt(pv) });
    $.ajax({
        url: '/admin/exec/ConfigureSysInfo?mainv=' + $.base64.encode( mainv ) + '&updatev=' + $.base64.encode( updatev ) + '&buildv=' + $.base64.encode( buildv ) + '&portalp=' + $.base64.encode( portalp ),
        dataType: 'json',
        cache: false,
        async: false,
        success: function(json) {
            // nothing
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}