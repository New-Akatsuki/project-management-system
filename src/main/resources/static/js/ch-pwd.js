$(document).ready(function () {
    // Add your event handlers or other initialization code here
    $('#currentPassword').on('input', function () {
        if ($('#currentPassword').val() === '') {
            $('#currentPasswordError').text("Current Password cannot be blank!");
        } else {
            checkCurrentPassword();
            $('#currentPasswordError').text('');
        }
    });
    $('#confirmPassword').prop('disabled', true);
    $('#newPassword').prop('disabled', true);
    $('#changePasswordBtn').prop('disabled', true);
});
$(document).ready(function () {
    $('#successModal').on('show.bs.modal', function () {
        $('#modalOverlay').show();
    });
    $('#successModal').on('hidden.bs.modal', function () {
        $('#modalOverlay').hide();
    });
});


let currentConfirmPassword;
function checkCurrentPassword() {
    let currentPassword = $('#currentPassword').val();
    // Make an AJAX request to check if the current password is correct
    $.ajax({
        type: 'POST',
        url: '/users/check-current-password', // Replace with the actual server endpoint
        contentType: 'application/json',
        data: JSON.stringify({currentPassword: currentPassword}),
        success: function (data) {
            $('#errorMessage').text('');
            if (data === false) {
                $('#currentPassword').removeClass('is-valid').addClass('is-invalid');
            } else {
                $('#currentPassword').removeClass('is-invalid').addClass('is-valid').prop('disabled', true);
                currentConfirmPassword = currentPassword;
                $('#newPassword').prop('disabled', false).focus();
                $('#confirmPassword').prop('disabled', false);
            }
        },
        error: function (xhr, error) {
            console.error('Error checking current password:', error.responseText);
            $('#errorMessage').text('Error checking current password. Please try again.');
        }
    });
}


$('#newPassword').on('input', function () {
    let pattern = /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d).{8,}$/;
    if ($('#newPassword').val() == currentConfirmPassword) {;
        $('#newPasswordError').text("New Password cannot be the same as Current Password!");
    } else if (!pattern.test($('#newPassword').val())) {

        $('#newPasswordError').text("New Password must include at least one uppercase letter, one lowercase letter, and one digit, and be at least 8 characters long!");
    }else if ($('#newPassword').val() === '') {
        $('#newPasswordError').text("New Password cannot be blank!");
    }
    else {
        $('#newPasswordError').text('');
    }
    if ($('#confirmPassword').val() !== '') {
        if ($('#newPassword').val() === $('#confirmPassword').val()) {
            $('#changePasswordBtn').prop('disabled', false);
            $('#newPassword').addClass('is-valid').removeClass('is-invalid');
            $('#confirmPassword').addClass('is-valid').removeClass('is-invalid');
        } else {
            $('#changePasswordBtn').prop('disabled', true);
            $('#newPassword').addClass('is-invalid').removeClass('is-valid');
            $('#confirmPassword').addClass('is-invalid').removeClass('is-valid');
        }
    }
});

$('#confirmPassword').on('input', function () {
    if ($('#confirmPassword').val() === '') {
        $('#confirmPasswordError').text("Confirm Password cannot be blank!");
    } else {
        $('#confirmPasswordError').text('');
    }
    if ($('#newPassword').val() === $('#confirmPassword').val() && $('#newPasswordError').text() === '' && $('#confirmPasswordError').text() === '') {
        $('#changePasswordBtn').prop('disabled', false);
        $('#newPassword').addClass('is-valid').removeClass('is-invalid');
        $('#confirmPassword').addClass('is-valid').removeClass('is-invalid');
    } else {
        $('#changePasswordBtn').prop('disabled', true);
        $('#newPassword').addClass('is-invalid').removeClass('is-valid');
        $('#confirmPassword').addClass('is-invalid').removeClass('is-valid');
    }
});

//eg for change pwd

function changePassword() {
    $('#errorMessage').empty();
    let newPassword = $('#newPassword').val();
    let confirmPassword = $('#confirmPassword').val();
    let currentPassword = $('#currentPassword').val();
    let changePassword = {
        currentPassword: currentPassword,
        newPassword: newPassword,
        confirmPassword: confirmPassword
    }
    // Serialize form data
    $.ajax({
        type: 'POST',
        url: '/users/change-password',
        contentType: 'application/json',
        data: JSON.stringify(changePassword),
        success: function (data) {
            // $('#profile-overview').tab('show');
            $('#currentPassword').val('').prop('disabled', false)
                .removeClass('is-valid').removeClass('is-invalid');
            $('#newPassword').val('').prop('disabled', true)
                .removeClass('is-valid').removeClass('is-invalid');
            $('#confirmPassword').val('').prop('disabled', true)
                .removeClass('is-valid').removeClass('is-invalid');
            displaySuccessModal();
        },
        error: function (xhr, error) {
            console.error('Error changing password:', error.responseText);
            $('#errorMessage').text('Error changing password: ' + error.responseText);
        }
    });

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

function displaySuccessModal() {
    let modalBox = $('#successModal')
    // Show the modal
    modalBox.modal('show');
    // Hide the modal after 3 seconds
    setTimeout(function () {
        modalBox.modal('hide');
    }, 2000);
}











