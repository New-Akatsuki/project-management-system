$(document).ready(function () {
    // Make an AJAX request to fetch data from the REST API endpoint
    $.ajax({
        url: '/get-projects', // Replace with your actual API endpoint
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            renderProjectTable(data);
        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });

    /*===================================================
    Build the Project table
    ===================================================*/
    function renderProjectTable(items) {
        if ($.fn.DataTable.isDataTable('#project')) {
            $('#project').DataTable().destroy();
        }

        return $('#project').DataTable({
            data: items,
            columns: [
                { data: 'name' },
                { data: 'department' },
                {
                    data: 'status',
                    render: function (data, type, row) {

                        return `
                        <div id="toggleBtnGp" class="btn-group" role="group" aria-label="Client Status">
                           ${buildToggleUserBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
                { data: 'client' },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditClientModal(${row.id})">Edit</button>
                    `;
                    }
                },
            ]
        });
    }



});