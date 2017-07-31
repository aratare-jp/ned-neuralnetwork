$(document).ready(function () {
    askServer();
});

/**
 * Ask the server to see if it's running at the moment.
 */
function askServer() {
    $.ajax({
        url: "http://deco3800-deathbydeco.uqcloud.net/behind/dataset/status",
        // url: 'http://localhost:1337/dataset/status',
        type: 'GET',
        success: function (data) {
            handler(data);
        },
        error: function () {
            $('#server-status').html("<p>Server is currently down. Please" +
                " visit later.</p>");
        }
    });
}

/**
 * Used the data received from the server to populate the chart.
 * @param data the data returned by the server
 */
function handler(data) {
    if (data.status === 'available') {
        $('#server-status').html("<p>Neural network has been trained</p>");
        displayChart(data.meanErrors);
        var $serverInfo = $('#server-info');
        $serverInfo.append("<p>Error threshold: " + data.errorThreshold + "</p>");
        $serverInfo.append("<p>Learning rate: " + data.learningRate + "</p>");
        $serverInfo.append("<p>Max epoch: " + data.maxEpoch + "</p>");
        $serverInfo.append("<p>Number of hidden layers: " + data.hiddenLayers + "</p>");
    } else {
        $('#server-status').html("<p>Neural network is currently in" +
            " training. Please comeback later.</p>");
    }
}

function displayChart(data) {
    Highcharts.chart('mean-chart', {
        animation: false,

        title: {
            text: 'Mean errors while training'
        },

        yAxis: {
            title: {
                text: 'Percentage'
            },
            min: 0,
            max: 1
        },
        xAxis: {
            title: {
                text: 'Iterations'
            }
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle'
        },

        plotOptions: {
            series: {
                pointStart: 0
            }
        },

        series: [{
            name: 'Mean error',
            data: data
        }]
    });
}
