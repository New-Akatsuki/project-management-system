<!--For Project List Table-->
let projectList = [];
$(document).ready(function () {
    // Make an AJAX request to fetch data from the REST API endpoint
    $.ajax({
        url: '/get-projects', // Replace with your actual API endpoint
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log('All project list:', data);
            projectList = data;
            // Render the data into the DataTable
            renderProjectListTable(data);
        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });
});
function renderProjectListTable(items) {
    //check if deliverable table is already initialized, if so, refresh the table with new data
    if ($.fn.DataTable.isDataTable('#project')) {
        $('#project').DataTable().destroy();
    }
    return $('#project').DataTable({
        data: items,
        columns: [
            {data: 'name'},
            {data: 'department'},
            {data: 'endDate'},
            {data: 'client'},
            {
                data: 'status',
                render: function (data, type, row) {
                    return `
                         <span class="badge bg-${data==='ONGOING'?'success': data==='FINISHED'?'secondary':'danger'}">${data}</span>
                    `;
                }
            },
            {
                data: 'id',
                render: function (data, type, row) {

                    return `
                        <a class="btn btn-sm btn-primary mx-2" href="/projects/${data}/working-area">More</a>
                    `;
                }
            },

        ]
    });
}


// For get department



