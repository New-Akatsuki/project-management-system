
    <!--For Member List Table-->
    let userList = [];
    $(document).ready(function () {
    // Make an AJAX request to fetch data from the REST API endpoint
    $.ajax({
        url: '/get-users', // Replace with your actual API endpoint
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
            role: $("#newUserRole").val(),
            department: parseInt($("#department").val()),
        };

        console.log(addMember);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/member-create",
            data: JSON.stringify(addMember),
            dataType: 'json',
            success: function (data) {
                $("#name").val('')
                $("#email").val('')
                $("#newUserRole").val('')
                $("#department").val('')
                userList.push(data)
                console.log(userList);
                renderMemberListTable(userList);
            },
            error: function (error) {
                console.log('Error adding new member:', error);
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


    function editUserRoleAndDepartment(){
        //Get updated user information form modal fields
        let id = $('#editId').val();
        let name = $('#editName').val();
        let email = $('#editEmail').val();
        let department = $('#editDepartment').val();
        let role= $('#editUserRole').val();
        // Prepare updated user object
        let updatedUser = {
            id:id,
            name: name,
            email: email,
            department: department,
            role: role,
        };
        console.log("updatedUser", updatedUser);
        //Make a PUT request to update the user data
        $.ajax({
            url: `/user-edit/${id}`, // Replace with your actual API endpoint
            method: 'PUT',
            data: JSON.stringify(updatedUser),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log('Data received:', data);
                location.reload();
                // // Hide the modal
                // $('#userEditModal').modal('hide');
                // // Reload the DataTable with new data
                // renderMemberListTable(updatedUser);
            },
            error: function (xhr,error) {
                console.log(xhr.responseText)
                console.log('Error fetching data:', error);
            }

        });
    }

        //Make a PUT request to update the user data

    //For display edit user modal
    function displayEditUserModal(userId) {
        const user = userList.find(user => user.id === userId);
        console.log("Edit Modal User  ",user)
        // Populate modal fields with user data
        $('#editId').val(user.id);
        $('#editName').val(user.name);
        $('#editEmail').val(user.email);
        $('#editDepartment').val(user.departmentId);
        $('#editUserRole').val(user.role);
        // Disable the "name" field
        $('#editName').prop('disabled', true);
        // Show the modal
        $('#userEditModal').modal('show');
    }
    // For get department
    $(document).ready(function(){
        $.ajax({
            url: '/get-department',
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                console.log("Returned Data : ",data);
                // Clear existing options
                $('#department').empty();

                // Append new options
                $.each(data, function (index, value) {
                    $('#department').append($('<option>', {
                        value: value.id,
                        text: value.name
                    }));
                });

                $('#editDepartment').empty();

                // Append new options
                $.each(data, function (index, value) {
                    $('#editDepartment').append($('<option>', {
                        value: value.id,
                        text: value.name
                    }));
                });
            },
            error: function (xhr, status, error) {
                // Handle errors
                console.error('Error fetching department data:', error);
            }
        });
    })



