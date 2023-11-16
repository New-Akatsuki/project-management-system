
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
                    backgroundColor: 'rgba(255, 99, 132, 0.2)', // Adjust color as needed
                    borderColor: 'rgba(255, 99, 132, 1)', // Adjust color as needed
                    borderWidth: 1,
                },
                {
                    label: 'External Kpi',
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