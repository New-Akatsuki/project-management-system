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
                data: 'On going',
                render: function (data, type, row) {
                    return `
                            <button type="button" onclick="toggleProjectStatus(${row.id}, ${!data})" class="btn btn-sm btn-${data ? 'secondary' : 'success'}">${data ? 'Disabled' : 'Active'}</button>

                    `;
                }
            },


            {
                data: 'id',
                render: function (data, type, row) {
                    return `
                    <button class="btn btn-sm btn-primary mx-2" onclick="displayEditProjectModal(${row.id})">Edit</button>

                                `;
                }
            },

        ]
    });
}


<!--Build Toggle Project Btn-->
function toggleProjectStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'disable' : 'enable';
    $('#confirmationMemberAction').text(actionText);

    $('#confirmationMemberModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmMemberButton').on('click', function(event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationMemberModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/member/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function(memberInfo) {
                // Handle success response, update UI if necessary
                console.log('Member status updated successfully:', memberInfo);
                userList.filter(user=>user.id===memberInfo.id)[0].active = memberInfo.active;
                renderMemberListTable(userList)
            },
            error: function(error) {
                // Handle error response
                console.error('Error updating member status:', error);
            }
        });
        $('#confirmMemberButton').off('click');
    });
}

// For get department



