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
        url: '/pm/check-current-password', // Replace with the actual server endpoint
        contentType: 'application/json',
        data: JSON.stringify({ currentPassword: currentPassword }),
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
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();
    let currentPassword = $('#currentPassword').val();

    let errorMessage = ''; // Initialize an empty error message

    if (currentPassword.trim() === '') {
        errorMessage += 'Please enter current password.\n';
    }

    if (newPassword.trim() === '') {
        errorMessage += 'Please enter new password.\n';
    }

    if (confirmPassword.trim() === '') {
        errorMessage += 'Please enter confirm password.\n';
    }

    if (currentPassword.trim() === '' || newPassword.trim() === '' || confirmPassword.trim() === '') {
        errorMessage += 'Please fill all the fields.\n';
    }

    if (currentPassword === newPassword) {
        errorMessage += 'Old password and new password must not be the same.\n';
    }

    if (newPassword !== confirmPassword) {
        errorMessage += 'Passwords do not match.\n';
    }
    if (checkCurrentPassword !== currentPassword) {
        errorMessage += 'Current password is incorrect.\n';
    }

    if (errorMessage !== '') {
        $('#errorMessage').text(errorMessage); // Display accumulated error messages
        validationPasses = false;
    }
}


function validatePassword() {
    $('#errorMessage').empty();
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();
    let currentPassword = $('#currentPassword').val();
    let validationPasses = true;

    let changePassword = {
        currentPassword: currentPassword,
        newPassword: newPassword,
        confirmPassword: confirmPassword
    }

    checkValidateion();

    if (validationPasses) {
            // Serialize form data
            $.ajax({
                type: 'POST',
                url: '/pm/change-password',
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












