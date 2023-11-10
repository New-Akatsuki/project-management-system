    <!--For Member List Table-->
    let userList = [];
    $(document).ready(function () {
    // Make an AJAX request to fetch data from the REST API endpoint
    $.ajax({
        url: '/pm/users', // Replace with your actual API endpoint
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log('All user list:', data);
            userList = data;
            // Render the data into the DataTable
            renderMemberListTable(data);
        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });
});
    function renderMemberListTable(items) {
        //check if deliverable table is already initialized, if so, refresh the table with new data
        if ($.fn.DataTable.isDataTable('#user-list-table')) {
            $('#user-list-table').DataTable().destroy();
        }
        return $('#user-list-table').DataTable({
            data: items,
            columns: [
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },
                {data: 'name'},
                {data:'email'},
                {data: 'department'},
                {data: 'role'},
                {
                    data: 'active',
                    render: function (data, type, row) {
                        console.log('data', data ,row.id)
                        return `
                            <button type="button" onclick="toggleMemberStatus(${row.id}, ${!data})" class="btn btn-sm btn-${data ? 'secondary' : 'success'}">${data ? 'Disabled' : 'Active'}</button>

                    `;
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row) {
                        return `
                    <button class="btn btn-sm btn-primary mx-2" onclick="displayEditUserModal(${row.id})">Edit</button>

                                `;
                    }
                },

            ]
        });
    }

    // For Create New Member
     function addNewMember() {
        let addMember = {
            name: $("#name").val(),
            email: $("#email").val(),
            department: parseInt($("#department").val()),
            role: $("#newUserRole").val(),
        };
        console.log(addMember);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/pm/member-create",
            data: JSON.stringify(addMember),
            dataType: 'json',
            success: function (data) {
                userList.push(data)
                console.log(userList);
                renderMemberListTable(userList);
            },
            error: function (error) {
                console.log('Error deleting user:', error);
            }
        });
    }

    <!--Build Toggle Member Btn-->
    function toggleMemberStatus(id, newStatus) {
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
        url: `/pm/member/status/${id}?newStatus=${newStatus}`,
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
