$(document).ready(function () {
    // Add your event handlers or other initialization code here
    $('#currentPassword').on('blur', function () {
        checkCurrentPassword();
    });
});

function checkCurrentPassword() {
    let currentPassword = $('#currentPassword').val();

    // Make an AJAX request to check if the current password is correct
    $.ajax({
        type: 'POST',
        url: '/users/check-current-password', // Replace with the actual server endpoint
        contentType: 'application/json',
        data: JSON.stringify({currentPassword: currentPassword}),
        success: function (data) {
            console.log('Current password is correct:', data);
            checkCurrentPassword = currentPassword;
            $('#errorMessage').text('');
        },
        error: function (xhr, error) {
            console.error('Error checking current password:', error.responseText);
            // Display an error message or handle it as needed
        }
    });
}


$('#newPassword').on('input', function () {
    checkValidateion()
});

$('#confirmPassword').on('input', function () {
    checkValidateion()
});

$('#currentPassword').on('input', function () {
    checkValidateion()
});

function checkValidateion() {
    $('#errorMessage').text('');
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();
    let currentPassword = $('#currentPassword').val();
    let validationPasses = true;

    let errorMessage = ''; // Initialize an empty error message

    if (currentPassword.trim() === '') {
        errorMessage += 'Please enter current password.\n';
        validationPasses = false;
    }

    if (newPassword.trim() === '') {
        errorMessage += 'Please enter new password.\n';
        validationPasses = false;
    }

    if (confirmPassword.trim() === '') {
        errorMessage += 'Please enter confirm password.\n';
        validationPasses = false;
    }

    if (currentPassword.trim() === '' || newPassword.trim() === '' || confirmPassword.trim() === '') {
        errorMessage += 'Please fill all the fields.\n';
        validationPasses = false;
    }

    if (currentPassword === newPassword) {
        errorMessage += 'Old password and new password must not be the same.\n';
        validationPasses = false;
    }

    if (newPassword !== confirmPassword) {
        errorMessage += 'Passwords do not match.\n';
        validationPasses = false;
    }
    if (checkCurrentPassword !== currentPassword) {
        errorMessage += 'Current password is incorrect.\n';
        validationPasses = false;
    }

    if (errorMessage !== '') {
        $('#errorMessage').text(errorMessage); // Display accumulated error messages
        $('#changePasswordBtn').prop('disabled', true);
        return false;
    }else{
        $('#changePasswordBtn').prop('disabled', false);
    }
    if (currentPassword === newPassword) {
        validationPasses = false;
    }
    return validationPasses;
    return true;
}

//eg for change pwd




function validatePassword() {
    $('#changePasswordForm').preventDefault();
    $('#errorMessage').empty();
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();
    let currentPassword = $('#currentPassword').val();

    let changePassword = {
        currentPassword: currentPassword,
        newPassword: newPassword,
        confirmPassword: confirmPassword
    }

    if (checkValidateion()) {
        // Serialize form data
        $.ajax({
            type: 'POST',
            url: '/users/change-password',
            contentType: 'application/json',
            data: JSON.stringify(changePassword),
            success: function (data) {
                console.log('Password changed successfully:', data);
                // $('#profile-overview').tab('show');
                $('#currentPassword').val('');
                $('#newPassword').val('');
                $('#confirmPassword').val('');
            },
            error: function (xhr, error) {
                console.log(xhr.responseText);
                console.error('Error changing password:', error.responseText);

                $('#errorMessage').text('Error changing password: ' + error.responseText);
            }
        });
    }
    return false;
}


$(document).ready(function () {

    $('#passwordToggleOld').on('click', function () {
        var oldPasswordField = $('#currentPassword');
        var oldPasswordIcon = $('#passwordIconOld');

        // Toggle password visibility
        if (oldPasswordField.attr('type') === 'password') {
            oldPasswordField.attr('type', 'text');
            oldPasswordIcon.removeClass('bi-eye-slash').addClass('bi-eye');
        } else {
            oldPasswordField.attr('type', 'password');
            oldPasswordIcon.removeClass('bi-eye').addClass('bi-eye-slash');
        }
    });

    $('#passwordToggle').on('click', function () {
        var passwordField = $('#newPassword');
        var passwordIcon = $('#passwordIcon');

        // Toggle password visibility
        if (passwordField.attr('type') === 'password') {
            passwordField.attr('type', 'text');
            passwordIcon.removeClass('bi-eye-slash').addClass('bi-eye');
        } else {
            passwordField.attr('type', 'password');
            passwordIcon.removeClass('bi-eye').addClass('bi-eye-slash');
        }
    });

    $('#passwordToggle2').on('click', function () {
        var confirmPasswordField = $('#confirmPassword');
        var confirmPasswordIcon = $('#passwordIcon2');

        // Toggle password visibility
        if (confirmPasswordField.attr('type') === 'password') {
            confirmPasswordField.attr('type', 'text');
            confirmPasswordIcon.removeClass('bi-eye-slash').addClass('bi-eye');
        } else {
            confirmPasswordField.attr('type', 'password');
            confirmPasswordIcon.removeClass('bi-eye').addClass('bi-eye-slash');
        }
    });
});












