var Backend;
var DeleteDomainSuffix;
var state = urlPara('s').replace(/%3D/g,'=');
var searchstring = urlPara('searchstring').replace(/%3D/g,'=').replace(/%20/g,' ').replace(/%22/g,'"').replace(/%25/g,'%').replace(/%3C/g,'<').replace(/%3E/g,'>').replace(/%5B/g,'[').replace(/%5C/g,'\\').replace(/%5D/g,']').replace(/%5E/g,'^').replace(/%60/g,'`').replace(/%7B/g,'{').replace(/%7C/g,'|').replace(/%7D/g,'}').replace(/%7E/g,'~').replace(/%7F/g,'').replace(/%28/g,'(').replace(/%29/g,')').replace(/%2B/g,'+');
var suburl = window.location.pathname.split("/")[1];
var FullName;
var UUID;
var UserID;
var UserMail;
var UsrPctrPath;
var UsrPctrLength;
var UserGroups = [];
var UserPerm = [];
var Mailing = [];

/* Basic Functions */

function urlParam(name) {
    var results = new RegExp('[\\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
    if(typeof(results) !== 'undefined' && results != null) { return results[1]; } else { return 0; }
}

function urlPara(name){
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");  
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );  
    var results = regex.exec( window.location.href ); 
    if( results == null )
        return "";  
    else
        return results[1];
}

function OpenWindow(target,mode) {
    window.open(target,mode);
}

/* Executions */

function GetUserConfig() {
    $.ajax({
        url: '/gateway/exec/GetUserConfig',
        dataType: 'json',
        crossDomain: true,
        cache: false,
        async: false,
        success: function(json) {
            $.each(json, function() {
                Backend = this.LOCAL_BACKEND;
                $.each(this.DASHBOARD, function() {
                    $('#DashboardLinks').append('<span id="ui-tile" class="ui-tile-300px" title="' + base64_decode( this.TITLE ) + '"><img class="ui-tile-cimg" src="public/images/login-128.png" alt="' + base64_decode( this.DESC ) + '" width="96" height="96"><span class="ui-tile-header">' + base64_decode( this.TITLE) + '</span><br></br><span class="ui-tile-content">' + base64_decode( this.DESC ) + '</span></span>');
                });
                $.each(this.USER_CONFIG, function() {
                    if (this.KEY == "DeleteDomainSuffix") {
                        DeleteDomainSuffix = this.ACTION;
                    }
                });
                $('span.login_username').html(base64_decode( this.NAME ));
                UUID = base64_decode( this.UUID );
                UserID = base64_decode( this.UID );
                FullName = base64_decode( this.NAME );
                UserMail = base64_decode( this.MAIL );
                UsrPctrPath = base64_decode( this.PCTR );
                UsrPctrLength = base64_decode( this.PCTRL );
                if ( UsrPctrLength != "0" ) {
                    $('#UserProfileConfig').attr('src',UsrPctrPath);
                    $('#UserPictureP').attr('src',UsrPctrPath);
                }
                UserGroups = this.USER_GROUPS;
                UserPerm = this.USER_PERM;
                Mailing = this.MAILING;
            });
        },
        error: function (xhr, thrownError) {
            alert(xhr.status + "::" + thrownError);
        }
    });
}

/* Complete Functions */

function DialogMailComplete(id,title,message) {
    $(id).html('<div id="SuccessDialog" title="' + title + '"><p><span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>' + message + '</p></div>');
    $('#SuccessDialog').dialog({
        autoOpen: true,
        height: 200,
        width: 600,
        draggable: false,
        resizable: false,
        modal: false,
        buttons: { 
            OK: function() {
                $(this).dialog('close');
                $('#SuccessDialog').remove();
                
                //location.reload();
            }
        }
    }).parent().find('.ui-dialog-titlebar-close').hide();
}

function DialogSuccess(id,message) {
    $(id).append('<div id="SuccessDialog" title="Aktion Erfolgreich durchgef&uuml;hrt."><p><span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 50px 0;"></span>' + message + '</p></div>');
    $('#SuccessDialog').dialog({
        autoOpen: true,
        height: 200,
        width: 600,
        draggable: false,
        resizable: false,
        modal: false,
        buttons: { 
            OK: function() {
                $(this).dialog('close');
                $('#SuccessDialog').remove();
            }
        }
    }).parent().find('.ui-dialog-titlebar-close').hide();
}

function PrepareHtmlPasteJqte(id) {
    $('#' + id + ' div.jqte_editor').bind('paste', function(e) {
        e.preventDefault();
        var text = (e.originalEvent || e).clipboardData.getData('text/plain') || prompt('Paste something..');
        $('#' + id + ' div.jqte_editor').html(text);
    });
}

function PrepareHtmlPasteDiv(id) {
    $('#' + id + '').bind('paste', function(e) {
        e.preventDefault();
        var text = (e.originalEvent || e).clipboardData.getData('text/plain') || prompt('Paste something..');
        $('#' + id + '').html(text);
    });
}

function EscapeChar(s) {
    return s.replace(/\"/g,'\\\"');
}

/* Date Functions */

function sleep(milliseconds) {
  var start = new Date().getTime();
  for (var i = 0; i < 1e7; i++) {
    if ((new Date().getTime() - start) > milliseconds){
      break;
    }
  }
}

function PrintTS() {
    var currentTime = new Date()
    var hours = currentTime.getHours()
    var minutes = currentTime.getMinutes()
    var seconds = currentTime.getSeconds()
    if (minutes < 10){
        minutes = "0" + minutes
    }
    if (seconds < 10){
        seconds = "0" + seconds
    }
    return(hours + ":" + minutes + ":" + seconds)
}

var dateFormat = function () {
	var	token = /d{1,4}|m{1,4}|yy(?:yy)?|([HhMsTt])\1?|[LloSZ]|"[^"]*"|'[^']*'/g,
		timezone = /\b(?:[PMCEA][SDP]T|(?:Pacific|Mountain|Central|Eastern|Atlantic) (?:Standard|Daylight|Prevailing) Time|(?:GMT|UTC)(?:[-+]\d{4})?)\b/g,
		timezoneClip = /[^-+\dA-Z]/g,
		pad = function (val, len) {
			val = String(val);
			len = len || 2;
			while (val.length < len) val = "0" + val;
			return val;
		};

	// Regexes and supporting functions are cached through closure
	return function (date, mask, utc) {
		var dF = dateFormat;

		// You can't provide utc if you skip other args (use the "UTC:" mask prefix)
		if (arguments.length == 1 && Object.prototype.toString.call(date) == "[object String]" && !/\d/.test(date)) {
			mask = date;
			date = undefined;
		}

		// Passing date through Date applies Date.parse, if necessary
		date = date ? new Date(date) : new Date;
		if (isNaN(date)) throw SyntaxError("invalid date");

		mask = String(dF.masks[mask] || mask || dF.masks["default"]);

		// Allow setting the utc argument via the mask
		if (mask.slice(0, 4) == "UTC:") {
			mask = mask.slice(4);
			utc = true;
		}

		var	_ = utc ? "getUTC" : "get",
			d = date[_ + "Date"](),
			D = date[_ + "Day"](),
			m = date[_ + "Month"](),
			y = date[_ + "FullYear"](),
			H = date[_ + "Hours"](),
			M = date[_ + "Minutes"](),
			s = date[_ + "Seconds"](),
			L = date[_ + "Milliseconds"](),
			o = utc ? 0 : date.getTimezoneOffset(),
			flags = {
				d:    d,
				dd:   pad(d),
				ddd:  dF.i18n.dayNames[D],
				dddd: dF.i18n.dayNames[D + 7],
				m:    m + 1,
				mm:   pad(m + 1),
				mmm:  dF.i18n.monthNames[m],
				mmmm: dF.i18n.monthNames[m + 12],
				yy:   String(y).slice(2),
				yyyy: y,
				h:    H % 12 || 12,
				hh:   pad(H % 12 || 12),
				H:    H,
				HH:   pad(H),
				M:    M,
				MM:   pad(M),
				s:    s,
				ss:   pad(s),
				l:    pad(L, 3),
				L:    pad(L > 99 ? Math.round(L / 10) : L),
				t:    H < 12 ? "a"  : "p",
				tt:   H < 12 ? "am" : "pm",
				T:    H < 12 ? "A"  : "P",
				TT:   H < 12 ? "AM" : "PM",
				Z:    utc ? "UTC" : (String(date).match(timezone) || [""]).pop().replace(timezoneClip, ""),
				o:    (o > 0 ? "-" : "+") + pad(Math.floor(Math.abs(o) / 60) * 100 + Math.abs(o) % 60, 4),
				S:    ["th", "st", "nd", "rd"][d % 10 > 3 ? 0 : (d % 100 - d % 10 != 10) * d % 10]
			};

		return mask.replace(token, function ($0) {
			return $0 in flags ? flags[$0] : $0.slice(1, $0.length - 1);
		});
	};
}();

// Some common format strings
dateFormat.masks = {
	"default":      "ddd mmm dd yyyy HH:MM:ss",
	shortDate:      "m/d/yy",
	mediumDate:     "mmm d, yyyy",
	longDate:       "mmmm d, yyyy",
	fullDate:       "dddd, mmmm d, yyyy",
	shortTime:      "h:MM TT",
	mediumTime:     "h:MM:ss TT",
	longTime:       "h:MM:ss TT Z",
	isoDate:        "yyyy-mm-dd",
	isoTime:        "HH:MM:ss",
	isoDateTime:    "yyyy-mm-dd'T'HH:MM:ss",
	isoUtcDateTime: "UTC:yyyy-mm-dd'T'HH:MM:ss'Z'"
};

// Internationalization strings
dateFormat.i18n = {
	dayNames: [
		"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat",
		"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"
	],
	monthNames: [
		"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec",
		"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
	]
};

// For convenience...
Date.prototype.format = function (mask, utc) {
	return dateFormat(this, mask, utc);
};

function base64_encode(data) {
    var b64 = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
    var o1, o2, o3, h1, h2, h3, h4, bits, i = 0,
        ac = 0,
        enc = '',
        tmp_arr = [];

    if (!data) {
        return data;
    }

    data = unescape(encodeURIComponent(data))

    do {
        // pack three octets into four hexets
        o1 = data.charCodeAt(i++);
        o2 = data.charCodeAt(i++);
        o3 = data.charCodeAt(i++);

        bits = o1 << 16 | o2 << 8 | o3;

        h1 = bits >> 18 & 0x3f;
        h2 = bits >> 12 & 0x3f;
        h3 = bits >> 6 & 0x3f;
        h4 = bits & 0x3f;

        // use hexets to index into b64, and append result to encoded string
        tmp_arr[ac++] = b64.charAt(h1) + b64.charAt(h2) + b64.charAt(h3) + b64.charAt(h4);
    } while (i < data.length);

    enc = tmp_arr.join('');

    var r = data.length % 3;

    return (r ? enc.slice(0, r - 3) : enc) + '==='.slice(r || 3);
}

function base64_decode(data) {
    var b64 = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=';
    var o1, o2, o3, h1, h2, h3, h4, bits, i = 0,
        ac = 0,
        dec = '',
        tmp_arr = [];

    if (!data) {
        return data;
    }

    data += '';

    do {
        // unpack four hexets into three octets using index points in b64
        h1 = b64.indexOf(data.charAt(i++));
        h2 = b64.indexOf(data.charAt(i++));
        h3 = b64.indexOf(data.charAt(i++));
        h4 = b64.indexOf(data.charAt(i++));

        bits = h1 << 18 | h2 << 12 | h3 << 6 | h4;

        o1 = bits >> 16 & 0xff;
        o2 = bits >> 8 & 0xff;
        o3 = bits & 0xff;

        if (h3 == 64) {
            tmp_arr[ac++] = String.fromCharCode(o1);
        } else if (h4 == 64) {
            tmp_arr[ac++] = String.fromCharCode(o1, o2);
        } else {
            tmp_arr[ac++] = String.fromCharCode(o1, o2, o3);
        }
    } while (i < data.length);

    dec = tmp_arr.join('');

    return decodeURIComponent(escape(dec.replace(/\0+$/, '')));
}