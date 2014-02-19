/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Host Tacticalo Overview
 */

function ShowHostPie() {
    Charting.setOptions({
        colors: ['#088A08', '#E5442D', '#C800C8']
    });
    hostpie = new Charting.Chart({
	chart: {
	    renderTo: 'HostPie',
	    type: 'pie',
	    borderRadius: 0,
            borderWidth: 0,
            borderColor: '#423939',
            width: 350,
            height: 300,
            backgroundColor: null,
            animation: false
        },
        credits: {
            enabled: false
	},
        exporting: {
            enabled: false
	},
	title: {
            text: ' ',
            style: {
		color: '#000',
		fontFamily: 'SansProLight',
                fontSize: '14px'
            }
	},
	yAxis: {
            enabled: false
	},
        plotOptions: {
            pie: {
                borderWidth: 0,
                shadow: false,
		animation: false,
		dataLabels: {
                    enabled: true,
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px',
                    connectorWidth: 0,
                    distance: -15,
                    formatter: function() {
			// display only if larger than 1
                        return this.y > 0 ? ''+ this.y +''  : null;
                    }
                },
                showInLegend: true
            }
	},
	tooltip: {
            enabled: false
	},
	legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
            itemStyle: {
		cursor: 'pointer',
		color: '#000',
                fontFamily: 'SansProLight',
                fontSize: '14px'
            },
            itemHoverStyle: {
		color: '#423939',
		fontFamily: 'SansProLight',
                fontSize: '14px'
            }
	}
    });
}

function ShowHostPer(uid) {
    Charting.setOptions({
        colors: ['#088A08', '#E5442D', '#C800C8']
    });
    charthostper = new Charting.Chart({
        chart: {
            renderTo: 'HostPer',
            type: 'column',
            width: 150,
            height: 300,
            borderRadius: 0,
            borderWidth: 0,
            plotBackgroundColor: null,
            backgroundColor: null,
            animation: false
        },
	credits: {
            enabled: false
	},
	legend: {
            enabled: false
	},
	exporting: {
            enabled: false
	},
	title: {
            text: ' ',
            style: {
		color: '#000',
		fontSize: '14px',
                fontFamily: 'SansProLight'
            }
	},
	xAxis: {
            gridLineWidth: 0,
            lineWidth: 0,
            tickWidth: 0,
            labels: {
                enabled: false
            }
	},
	yAxis: {
            gridLineWidth: 1,
            gridLineColor: '#423939',
            offset: 15,
            lineColor: '#423939',
            lineWidth: 1,
            tickColor: '#423939',
            tickWidth: 1,
            tickLength: 4,
            tickPosition: 'inside',
            labels: {
                enabled: true,
                style: {
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px'
                },
                formatter: function() {
                    return this.value + "%";
                }
            },
            min: 0,
            max: 100,
            title: {
                text: ' '
            },
            stackLabels: {
		enabled: false,
		style: {
                    fontWeight: 'bold',
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px'
		},
		formatter: function() {
                    return this.total + " MB";
                }
            }
	},
	tooltip: {
            enabled: false
	},
	plotOptions: {
            column: {
                borderWidth: 0,
                shadow: false,
		animation: false,                        
		stacking: 'normal',
		dataLabels: {
                    enabled: true,
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px',
                    connectorWidth: 0,
                    distance: -15,
                    formatter: function() {
			// display only if larger than 1
                        return this.y > 0 ? ''+ Math.round(this.y) +'%'  : null;
                    }
                }
            }
	}
    });
}

/*
 * Service Tactical Overview
 */

function ShowServicePie() {
    Charting.setOptions({
        colors: ['#088A08', 'orange', '#E5442D', '#C800C8']
    });
    servicepie = new Charting.Chart({
	chart: {
	    renderTo: 'ServicePie',
	    type: 'pie',
	    borderRadius: 0,
            borderWidth: 0,
            borderColor: '#423939',
            width: 350,
            height: 300,
            backgroundColor: null,
            animation: false
        },
        credits: {
            enabled: false
	},
        exporting: {
            enabled: false
	},
	title: {
            text: ' ',
            style: {
		color: '#000',
		fontFamily: 'SansProLight',
                fontSize: '14px'
            }
	},
	yAxis: {
            enabled: false
	},
        plotOptions: {
            pie: {
                borderWidth: 0,
                shadow: false,
		animation: false,
		dataLabels: {
                    enabled: true,
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px',
                    connectorWidth: 0,
                    distance: -13,
                    formatter: function() {
			// display only if larger than 1
                        return this.y > 0 ? ''+ this.y +''  : null;
                    }
                },
                showInLegend: true
            }
	},
	tooltip: {
            enabled: false
	},
	legend: {
            layout: 'horizontal',
            align: 'center',
            verticalAlign: 'bottom',
            borderWidth: 0,
            itemStyle: {
		cursor: 'pointer',
		color: '#000',
                fontFamily: 'SansProLight',
                fontSize: '14px'
            },
            itemHoverStyle: {
		color: '#000',
		fontFamily: 'SansProLight',
                fontSize: '14px'
            }
	}
    });
}

function ShowServicePer() {
    Charting.setOptions({
        colors: ['#088A08', 'orange', '#E5442D', '#C800C8']
    });
    chartserviceper = new Charting.Chart({
        chart: {
            renderTo: 'ServicePer',
            type: 'column',
            width: 150,
            height: 300,
            borderRadius: 0,
            borderWidth: 0,
            plotBackgroundColor: null,
            backgroundColor: null,
            animation: false
        },
	credits: {
            enabled: false
	},
	legend: {
            enabled: false
	},
	exporting: {
            enabled: false
	},
	title: {
            text: ' ',
            style: {
		color: '#000',
		fontSize: '14px',
                fontFamily: 'SansProLight'
            }
	},
	xAxis: {
            gridLineWidth: 0,
            lineWidth: 0,
            tickWidth: 0,
            labels: {
                enabled: false
            }
	},
	yAxis: {
            gridLineWidth: 1,
            gridLineColor: '#423939',
            offset: 15,
            lineColor: '#423939',
            lineWidth: 1,
            tickColor: '#423939',
            tickWidth: 1,
            tickLength: 4,
            tickPosition: 'inside',
            labels: {
                enabled: true,
                style: {
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px'
                },
                formatter: function() {
                    return this.value + "%";
                }
            },
            min: 0,
            max: 100,
            title: {
                text: ' '
            },
            stackLabels: {
		enabled: false,
		style: {
                    fontWeight: 'bold',
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px'
		},
		formatter: function() {
                    return this.total + " MB";
                }
            }
	},
	tooltip: {
            enabled: false
	},
	plotOptions: {
            column: {
                borderWidth: 0,
                shadow: false,
		animation: false,                        
		stacking: 'normal',
		dataLabels: {
                    enabled: true,
                    color: '#000',
                    fontFamily: 'SansProLight',
                    fontSize: '14px',
                    connectorWidth: 0,
                    distance: -15,
                    formatter: function() {
			// display only if larger than 1
                        return this.y > 0 ? ''+ Math.round(this.y) +'%'  : null;
                    }
                }
            }
	}
    });
}


