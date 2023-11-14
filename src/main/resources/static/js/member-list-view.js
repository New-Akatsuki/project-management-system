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
            role: $("#newUserRole").val(),
            departmentId: $("#department").val(),
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


    <!--Build Edit Member Modal-->
    function editUserRoleAndDepartment(){
        //Get updated user information form modal fields
        let id = $('#editId').val();
        let name = $('#editName').val();
        let email = $('#editEmail').val();
        let department= $('#editDepartment').val();
        let role= $('#editUserRole').val();

        // Prepare updated user object
        let updatedUser = {
            id:id,
            name: name,
            email: email,
            department : department,
            role: role,
        };

    }
        console.log("updatedUser", updatedUser);
        //Make a PUT request to update the user data
        $.ajax({
            url: `/pm/user-edit/${id}`, // Replace with your actual API endpoint
            method: 'PUT',
            data: JSON.stringify(updatedUser),
            dataType: 'json',
            contentType: 'application/json',
            success: function (data) {
                console.log('Data received:', data);
                // Hide the modal
                $('#userEditModal').modal('hide');
                // Reload the DataTable with new data
                // renderMemberListTable(data.updatedUser);
            },
            error: function (xhr,error) {
                console.log(xhr.responseText)
                console.log('Error fetching data:', error);
            }
        });
    //For display edit user modal
    function displayEditUserModal(userId) {
        const user = userList.find(user => user.id === userId);
        // Populate modal fields with user data
        $('#editId').val(user.id);
        $('#editName').val(user.name);
        $('#editEmail').val(user.email);
        $('#editDepartment').val(user.department);
        $('#editUserRole').val(user.role);
        // Disable the "name" field
        $('#editName').prop('disabled', true);
        // Show the modal
        $('#userEditModal').modal('show');
    }



