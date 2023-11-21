let userList = [];
$(document).ready(function () {
    $('.text-danger').hide();
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
            {data: 'email'},
            {data: 'department'},
            {data: 'role'},
            {
                data: 'active',
                render: function (data, type, row) {
                    return `
                    <button class="btn btn-sm btn-primary mx-2" onclick="displayEditUserModal(${row.id})">Edit</button>
                    <button type="button" onclick="toggleMemberStatus(${row.id}, ${!data})" class="btn btn-sm btn-${data ? 'secondary' : 'success'}">${data ? 'Disabled' : 'Active'}</button>
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
            resetInput();
        },
        error: function (error) {
            console.log('Error adding new member:', error);
        }
    });
}

function resetInput() {
    $("#name").removeClass("is-valid is-invalid");
    $("#email").removeClass("is-valid is-invalid");
    $("#newUserRole").removeClass("is-valid is-invalid");
    $("#department").removeClass("is-valid is-invalid");
    //remove validation error of form
    $("#name_error").removeClass("is-invalid");
    $("#email_error").removeClass("is-invalid");
    $("#newUserRole_error").removeClass("is-invalid");
    $("#department_error").removeClass("is-invalid");
    $("#addNewMemberForm").removeClass("was-validated").trigger('reset');
}

function resetEditInput() {
    $("#editName, #editEmail, #editUserRole, #editDepartment").val('').removeClass("is-valid is-invalid");
    $("#editName, #editEmail, #editUserRole, #editDepartment").each(function() {
        this.setCustomValidity("");
    });

    //remove validation error of form
    $("#editName_error").removeClass("is-invalid");
    $("#editEmail_error").removeClass("is-invalid");
    $("#editUserRole_error").removeClass("is-invalid");
    $("#editDepartment_error").removeClass("is-invalid");
    $("#addEditMemberForm").removeClass("was-validated");
}

<!--Build Toggle Member Btn-->
function toggleMemberStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'disable' : 'enable';
    $('#confirmationMemberAction').text(actionText);

    $('#confirmationMemberModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmMemberButton').on('click', function (event) {

        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationMemberModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/member/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function (memberInfo) {
                // Handle success response, update UI if necessary
                console.log('Member status updated successfully:', memberInfo);
                userList.filter(user => user.id === memberInfo.id)[0].active = memberInfo.active;
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
        }

    });
}


//For display edit user modal
function displayEditUserModal(userId) {
    resetEditInput();
    const user = userList.find(user => user.id === userId);
    console.log("Edit Modal User  ", user)
    // Populate modal fields with user data
    $('#editId').val(user.id);
    $('#editName').val(user.name);
    $('#editEmail').val(user.email);
    $('#editDepartment').val(user.departmentId);
    $('#editUserRole').val(user.role);
    $('#userEditModal').modal('show');
}


// For get department
$(document).ready(function () {
    const currentRole = $('#currentRole').val();
    const currentDepartment = $('#currentDepartment').val();

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


            // Append new options

        },
        error: function (xhr, status, error) {
            // Handle errors
            console.error('Error fetching department data:', error);
        }
    });
})
// For Add Member
let nameInput = $("#name");
let emailInput = $("#email");

let nameError = $("#nameError");
let emailError = $("#emailError");
let departmentInput = $('#department')
let departmentError = $('#departmentError')
let roleInput = $('#newUserRole')
let roleError = $('#roleError')

function validateAndAddMember() {
    //Validation
    let isValid = true;
    if (nameInput.val().trim() === '') {
        nameError.show()
        isValid = false;
    }
    if (emailInput.val().trim() === '') {
        emailError.show()
        isValid = false;
    }
    if (departmentInput.val() == null) {
        departmentError.show()
        isValid = false;
    }
    if (roleInput.val() == null) {
        roleError.show()
        isValid = false;
    }
    if (isValid) {
        addNewMember();
    }

}

$('#addMemberModalButton').click(function () {
    $('.text-danger').hide();
    $("#name").val('')
    $("#email").val('')
    $("#newUserRole").val('')
    $("#department").val('')
    $('#addMemberModal').modal('show');
})

nameInput.on('input', () => {
    toggleError(nameError, nameInput)
})

emailInput.on('input', () => {
    toggleError(emailError, emailInput)
})

departmentInput.on('change', function () {
    departmentError.hide();
})
roleInput.on('change', function () {
    roleError.hide();
})


//For Edit Member

let editNameInput = $("#editName");
let editEmailInput = $("#editEmail");
let editDepartmentInput = $('#editDepartment');
let editRoleInput = $('#editUserRole');
let editNameError = $("#editNameError");
let editEmailError = $("#editEmailError");
let editDepartmentError = $('#editDepartmentError');
let editRoleError = $('#editRoleError');

function validateAndEditMember() {
    let isValid = true;
    if (editNameInput.val().trim() === '') {
        editNameError.show();
        isValid = false;
    }
    if (editEmailInput.val().trim() === '') {
        editEmailError.show();
        isValid = false;
    }
    if (editDepartmentInput.val() == null) {
        editDepartmentError.show();
        isValid = false;
    }
    if (editRoleInput.val() == null) {
        editRoleError.show();
        isValid = false;
    }
    if (isValid) {
        editUserRoleAndDepartment();
    }
}

$('#editMemberModalButton').click(function () {
    $('.edit-text-danger').hide();
    $("#editName").val('');
    $("#editEmail").val('');
    $("#editUserRole").val('');
    $("#editDepartment").val('');
    $('#editMemberModal').modal('show');
})

editNameInput.on('input', () => {
    toggleError(editNameError, editNameInput);
})

editEmailInput.on('input', () => {
    toggleError(editEmailError, editEmailInput);
})

editDepartmentInput.on('change', function () {
    editDepartmentError.hide();
})

editRoleInput.on('change', function () {
    editRoleError.hide();
})

function toggleError(error, input) {
    if (error && input) {
        error.toggleClass('d-none', input.val().trim() !== '');
    }
}











