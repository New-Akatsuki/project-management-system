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

// Listen for changes in project or reviewer type
$('#projectSelector, #reviewerType').change(function() {
    calculateAllKPIs();
});

function calculateAllKPIs() {
    var projectId = $('#projectSelector').val();
    var reviewerType = $('#reviewerType').val();
    console.log('Reviewer Type:', reviewerType); // Log the reviewerType value

    if(myChart){
        myChart.destroy();
    }
    // Use $.ajax for more control
    $.ajax({
        url: `/calculateAllKPIs/${projectId}
      `,
        method: 'GET',
        data: {
            reviewerType: reviewerType
        },
        success: function (results) {
            console.log(results)
            updateChart(results);
        },
        error: function (xhr) {
            console.error(xhr.responseText);
        }
    });

    // $.ajax({
    //     url: `/calculateAllKPIs/${projectId}/${reviewerType}
    //     `,
    //     method: 'GET',
    //     data: {
    //         reviewerType: reviewerType
    //     },
    //     success: function (results) {
    //         console.log(results)
    //         updateChart(results);
    //     },
    //     error: function (xhr) {
    //         console.error(xhr.responseText);
    //     }
    // });
}

function updateChart(results) {
    var ctx = document.getElementById('kpiChart').getContext('2d');
    var labels = Object.keys(results.EXTERNAL); // Assuming both INTERNAL and EXTERNAL have the same development phases
    var internalData = Object.values(results.INTERNAL);
    var externalData = Object.values(results.EXTERNAL);

    if (myChart) {
        myChart.destroy();
    }

    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Internal Review',
                    data: internalData,
                    backgroundColor: 'rgba(255, 99, 132, 0.2)', // Adjust color as needed
                    borderColor: 'rgba(255, 99, 132, 1)', // Adjust color as needed
                    borderWidth: 1,
                },
                {
                    label: 'External Review',
                    data: externalData,
                    backgroundColor: 'rgba(75, 192, 192, 0.2)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
}