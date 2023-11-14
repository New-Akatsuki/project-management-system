// Populate project dropdown options
$.get("/get-project-data", function (projects) {
    var projectDropdown = $('#projectId');
    projects.forEach(function (project) {
        projectDropdown.append($('<option>', {
            value: project.id,
            text: project.name
        }));
    });
});
var myChart;

function calculateAllKPIs() {
    var projectId = $('#projectId').val();



    if(myChart){
        myChart.destroy();
    }
    // Use $.ajax for more control
    $.ajax({
        url: `/calculateAllKPIs/${projectId}`,
        method: 'GET',
        data: {

        },
        success: function (results) {
            updateChart(results);
        },
        error: function (xhr) {
            console.error(xhr.responseText);
        }
    });
}

function updateChart(results) {
    var ctx = document.getElementById('kpiChart').getContext('2d');
    var labels = Object.keys(results);
    var data = Object.values(results);

    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'KPI',
                data: data,
                backgroundColor: 'rgba(75, 192, 192, 0.2)',
                borderColor: 'rgba(75, 192, 192, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
}



$(document).ready(function () {
    $('.project-select').select2();
    $('.reviewer-select').select2();
});