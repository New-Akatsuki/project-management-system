
var myChart;

// Listen for changes in project or reviewer type
$('#projectSelector, #reviewerType').change(function() {
    const id = $('#projectSelector').val();
    calculateAllKPIs(id);
});

function calculateAllKPIs(id) {
    let projectId = id;
    let reviewerType = $('#reviewerType').val();
    console.log('Reviewer Type:', reviewerType,projectId); // Log the reviewerType value

    if(myChart){
        myChart.destroy();
    }
    // Use $.ajax for more control
    $.ajax({
        url: `/calculateAllKPIs/${projectId}`,
        method: 'GET',
        data: {
            reviewerType: reviewerType
        },
        success: function (results) {
            console.log(results)
            $('#kpiRatioHeadingContainer').show();

            updateChart(results);
        },
        error: function (xhr) {
            console.error(xhr.responseText);
        }
    });

}

function updateChart(results) {
    let ctx = document.getElementById('kpiChart').getContext('2d');
    let labels = Object.keys(results.EXTERNAL); // Assuming both INTERNAL and EXTERNAL have the same development phases
    let internalData = Object.values(results.INTERNAL);
    let externalData = Object.values(results.EXTERNAL);

    if (myChart) {
        myChart.destroy();
    }

    myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [
                {
                    label: 'Internal Kpi',
                    data: internalData,
                    backgroundColor: 'rgba(54, 162, 235, 1)', // Blue background color
                    borderColor: 'rgba(54, 162, 235, 1)', // Blue border color
                    borderWidth: 1,
                },
                {
                    label: 'External Kpi',
                    data: externalData,
                    backgroundColor: 'rgba(255, 205, 86, 1)', // Yellow background color
                    borderColor: 'rgba(255, 205, 86, 1)', // Yellow border color
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