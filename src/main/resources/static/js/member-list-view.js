let userList = [];
$(document).ready(function () {
    $.ajax({
        url: '/get-users',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            userList = data;
            renderMemberListTable(data);
        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });
});

function renderMemberListTable(items) {
    console.log("userList : ", userList);
    // Check if user list table is already initialized; if so, refresh the table with new data
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
            {
                data: 'name',
                render: function (data, type, row) {
                    return data.length > 15 ? data.substring(0, 15) + '...' : data;
                }
            },
            {
                data: 'email',
                render: function (data, type, row) {
                    return data.length > 15 ? data.substring(0, 15) + '...' : data;
                }
            },
            {
                data: 'department',
                render: function (data, type, row) {
                    return data.length > 7 ? data.substring(0, 7) + '...' : data;
                }
            },
            {data: 'role'},
            {
                data: 'active',
                render: function (data, type, row) {
                    return `
                      <span class="badge rounded-pill text-bg-${data ? 'success' : 'danger'}" style="width: 60px">${data ? 'Active' : 'Disabled'}</span>
                    `;
                }
            },
            {
                data: 'active',
                render: function (data, type, row) {
                    return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="displayEditUserModal(${row.id})">Edit</button>
                        <button type="button" onclick="toggleMemberStatus(${row.id}, ${!data})" class="btn btn-sm btn-secondary" style="width:90px;margin-right: 5px">${data ? 'Disabled' : 'Enable'}</button>
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
            $('#addMemberModal').modal('hide');
            userList.push(data)
            console.log(userList);
            renderMemberListTable(userList);
        },
        error: function (error) {
            console.log('Error adding new member:', error);
            $('#addMemberError').text('This user already exists!');
        }
    });
}

function toggleMemberStatus(id, newStatus) {
    //Find the member in memberList
    const member = userList.find(user => user.id === id);
    if (!member) {
        console.log('Member not found for ID:', id);
        return;
    }

    // Show Bootstrap modal for confirmation
    const actionText = member.active ? 'disable' : 'enable';
    $('#confirmationMemberAction').text(actionText);

    $('#confirmationMemberModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmMemberButton').on('click', function (event) {

        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationMemberModal').modal('hide');
        //Determine the new active status (toggle it)
        const newActiveStatus = !member.active;

        // Make the AJAX request
        $.ajax({
            url: `/member/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function (memberInfo) {
                // Handle success response, update UI if necessary
                console.log('Member status updated successfully:', memberInfo);
                member.active = !member.active;
                const buttonText = member.active ? 'Disabled' : 'Active';
                $(`#user-list-table button[data-id=${id}]`).text(buttonText);
                // userList.filter(user => user.id === memberInfo.id)[0].active = memberInfo.active;
                renderMemberListTable(userList)
            },
            error: function (error) {
                // Handle error response
                console.error('Error updating member status:', error);
            }
        });
        $('#confirmMemberButton').off('click');
    });
}

// Edit Member
function editUserRoleAndDepartment() {
    //Get updated user information form modal fields
    let id = $('#editId').val();
    let name = $('#editName').val();
    let email = $('#editEmail').val();
    let department = $('#editDepartment').val();
    let role = $('#editUserRole').val();
    // Prepare updated user object
    let updatedUser = {
        id: id,
        name: name,
        email: email,
        department: department,
        role: role,
    };
    console.log("updatedUser", updatedUser);
    //Make a PUT request to update the user data
    $.ajax({
        url: '/user-edit/' + id, // Replace with your actual API endpoint
        method: 'PUT',
        data: JSON.stringify(updatedUser),
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            $('#userEditModal').modal('hide');
            const indexToUpdate = userList.findIndex(val => val.id === data.id);
            if (indexToUpdate !== -1) {
                userList[indexToUpdate] = data;
            }
            renderMemberListTable(userList);
        },
        error: function (xhr, error) {
            console.log(xhr.responseText)
            console.log('Error fetching data:', error);
            $('#editMemberError').text('This user already exists!');
        }

    });
}

// For get department
$(document).ready(function () {
    $.ajax({
        url: '/get-department',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            // Clear existing options
            $('#department').empty();
            $('#editDepartment').empty();
            $('#department').append($('<option>', {
                value: '',
                text: "Select department",
            }));
            // Append new options
            $.each(data, function (index, value) {
                $('#department').append($('<option>', {
                    value: value.id,
                    text: value.name
                }));
            });
            // append edit option
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


$('#addUserModalBtn').click(function () {
    $('#addMemberError').text('');
    $("#name").val('').removeClass('is-invalid').removeClass('is-valid');
    $("#email").val('').removeClass('is-invalid').removeClass('is-valid');
    $("#newUserRole").val('').removeClass('is-invalid').removeClass('is-valid');
    $("#department").val('').removeClass('is-invalid').removeClass('is-valid');
    $('#addMemberModal').modal('show');
})

function emailValidation(emailId, emailErrorId) {
    emailId.on('input', function () {
        let email = emailId.val();
        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (emailId.val() === '') {
            emailId.addClass('is-invalid');
            emailErrorId.text('Email is required');
        } else if (!email.match(emailPattern)) {
            emailId.addClass('is-invalid');
            emailErrorId.text('Email must be in email pattern. eg., abc@gmail.com');
        } else {
            emailId.removeClass('is-invalid').addClass('is-valid');
            emailErrorId.text(''); // Clear the error message if the input is valid
        }
    });
}

$('#addNewMemberForm').submit(function (e) {
    e.preventDefault();
    let name = $('#name').val();
    let email = $('#email').val();
    let department = $('#department').val();
    let role = $('#newUserRole').val();

    if (name === '' || email === '' || department === '' || role === '') {
        if (name === '') {
            $('#name').addClass('is-invalid');
            $('#nameError').text('Name is required');
        }
        if (email === '') {
            $('#email').addClass('is-invalid');
            $('#emailError').text('Email is required');
        }
        if (department === '') {
            $('#department').addClass('is-invalid');
            $('#departmentError').text('Department is required');
        }
        if (role === '') {
            $('#newUserRole').addClass('is-invalid');
            $('#roleError').text('Role is required');
        }
    } else {
        addNewMember();
    }
})

function errorHideOnInteraction(inputId, errorId, sameErrorId) {
    inputId.on('input', function () {
        inputId.removeClass('is-invalid').removeClass('is-valid');
        errorId.text('');
        sameErrorId.text('');
    });
    inputId.on('change', function () {
        inputId.removeClass('is-invalid').removeClass('is-valid');
        errorId.text('');
    });
}

emailValidation($('#email'), $('#emailError'));
emailValidation($('#editEmail'), $('#editEmailError'));
errorHideOnInteraction($('#name'), $('#nameError'), $('#addMemberError'));
errorHideOnInteraction($('#email'), $('#emailError'), $('#addMemberError'));
errorHideOnInteraction($('#newUserRole'), $('#roleError'), $('#addMemberError'));
errorHideOnInteraction($('#department'), $('#departmentError'), $('#addMemberError'));
errorHideOnInteraction($('#editName'), $('#editNameError'), $('#editMemberError'));
errorHideOnInteraction($('#editEmail'), $('#editEmailError'), $('#editMemberError'));
errorHideOnInteraction($('#editUserRole'), $('#editRoleError'), $('#editMemberError'));
errorHideOnInteraction($('#editDepartment'), $('#editDepartmentError'), $('#editMemberError'));

//For display edit user modal
function displayEditUserModal(userId) {
    const user = userList.find(user => user.id === userId);
    console.log("Edit Modal User  ", user)
    // Populate modal fields with user data
    $('#editMemberError').text('');
    $('#editId').val(user.id);
    $('#editName').val(user.name).removeClass('is-invalid').removeClass('is-valid');
    $('#editEmail').val(user.email).removeClass('is-invalid').removeClass('is-valid');
    $('#editDepartment').val(user.departmentId).removeClass('is-invalid').removeClass('is-valid');
    $('#editUserRole').val(user.role).removeClass('is-invalid').removeClass('is-valid');
    $('#userEditModal').modal('show');
}

$('#editMemberForm').submit(function (e) {
    e.preventDefault();
    let name = $('#editName').val();
    let email = $('#editEmail').val();
    let department = $('#editDepartment').val();
    let role = $('#editUserRole').val();

    if (name === '' || email === '' || department === '' || role === '') {
        if (name === '') {
            $('#editName').addClass('is-invalid');
            $('#editNameError').text('Name is required');
        }
        if (email === '') {
            $('#editEmail').addClass('is-invalid');
            $('#editEmailError').text('Email is required');
        }
        if (department === '') {
            $('#editDepartment').addClass('is-invalid');
            $('#editDepartmentError').text('Department is required');
        }
        if (role === '') {
            $('#editUserRole').addClass('is-invalid');
            $('#editRoleError').text('Role is required');
        }
    } else {
        editUserRoleAndDepartment();
    }
});







