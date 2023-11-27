$('#addClientButton').click(function () {
    $('#addClientError').text('');
    $('#addClientForm').trigger('reset');
    $('#addClientNameError').text('');
    $('#addClientEmailError').text('');
    $('#addClientPhError').text('');
    $('#clientEmail').removeClass('is-invalid').removeClass('is-valid');
    $('#clientName').removeClass('is-invalid').removeClass('is-valid');
    $('#clientPhoneNumber').removeClass('is-invalid').removeClass('is-valid');
    $('#addClientModal').modal('show');
});
$('#addDeliverablesButton').click(function () {
    $('#deliverablesName').val('').removeClass('is-invalid').removeClass('is-valid');
    $('#addDeliverablesNameError').text('');
    $('#addDeliverablesModal').modal('show');
});
$('#addSystemOutlineButton').click(function () {
    $('#OutlineName').val('').removeClass('is-invalid').removeClass('is-valid');
    $('#addSystemOutlineNameError').text('');
    $('#addSystemOutlineModal').modal('show');
});
$('#addArchitectureButton').click(function () {
    $('#architectureName').val('').removeClass('is-invalid').removeClass('is-valid');
    $('#addArchitectureNameError').text('');
    $('#addArchitectureTypeError').text('');
    $('#addArchitectureSameNameError').text('');
    $('#addArchitectureModal').modal('show');
});
function notify(notification) {
    showToast(notification);
}

$('#clientName').on('input', function () {
    $('#addClientError').text("");
    if ($('#clientName').val() === '') {
        $('#clientName').removeClass("is-valid").addClass("is-invalid");
        $('#addClientNameError').text("Client name is required");
    } else {
        $('#addClientNameError').text("");
        $('#clientName').removeClass("is-invalid").addClass("is-valid");
    }
});
$('#clientEmail').on('input', function () {
    $('#addClientError').text("");
    let email = $('#clientEmail').val();
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if ($('#clientEmail').val() === '') {
        $('#clientEmail').addClass('is-invalid');
        $('#addClientEmailError').text('Email is required');
    } else if (!email.match(emailPattern)) {
        $('#clientEmail').addClass('is-invalid');
        $('#addClientEmailError').text('Email must be in email pattern. eg., abc@gmail.com');
    } else {
        $('#clientEmail').removeClass('is-invalid').addClass('is-valid');
        $('#addClientEmailError').text(''); // Clear the error message if the input is valid
    }
});
$('#clientPhoneNumber').on('input', function () {
    $('#addClientError').text("");
    let isValid = phoneInput1.isValidNumber();
    if ($('#clientPhoneNumber').val() === '') {
        $('#clientPhoneNumber').addClass('is-invalid').removeClass('is-valid');
        $('#addClientPhError').text('Phone number is required');
    } else if (!isValid) {
        $('#clientPhoneNumber').addClass('is-invalid').removeClass('is-valid');
        $('#addClientPhError').text('Invalid phone number');
    } else {
        $('#clientPhoneNumber').removeClass('is-invalid').addClass('is-valid');
        $('#addClientPhError').text(''); // Clear the error message if the input is valid
    }
})

$('#saveNewClient').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior
    let name = $('#clientName').val();
    let email = $('#clientEmail').val();
    let phoneNumber = $('#clientPhoneNumber').val();
    if (name !== '' && email !== '' && phoneNumber !== '' && $('#addClientNameError').text() === '' && $('#addClientEmailError').text() === '' && $('#addClientPhError').text() === '') {
        console.log("Ok");
        $.fn.addClient();
    } else {
        $('#addClientError').text('Please fill all the fields.');
        console.log("Not Ok");
    }
});

$('#editClientName').on('input', function () {
    $('#editClientSameNameError').text("");
    if ($('#editClientName').val() === '') {
        $('#editClientName').removeClass("is-valid").addClass("is-invalid");
        $('#editClientNameError').text("Client name is required");
    } else {
        $('#editClientNameError').text("");
        $('#editClientName').removeClass("is-invalid").addClass("is-valid");
    }
});
$('#editClientEmail').on('input', function () {
    $('#editClientSameNameError').text("");
    let email = $('#editClientEmail').val();
    let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if ($('#editClientEmail').val() === '') {
        $('#editClientEmail').addClass('is-invalid');
        $('#editClientEmailError').text('Email is required');
    } else if (!email.match(emailPattern)) {
        $('#editClientEmail').addClass('is-invalid');
        $('#editClientEmailError').text('Email must be in email pattern. eg., abc@gmail.com');
    } else {
        $('#editClientEmail').removeClass('is-invalid').addClass('is-valid');
        $('#editClientEmailError').text(''); // Clear the error message if the input is valid
    }
});
$('#editClientPhoneNumber').on('input', function () {
    $('#editClientSameNameError').text("");
    let isValid = phoneInput1.isValidNumber();
    if ($('#editClientPhoneNumber').val() === '') {
        $('#editClientPhoneNumber').addClass('is-invalid').removeClass('is-valid');
        $('#editClientPhError').text('Phone number is required');
    } else if (!isValid) {
        $('#editClientPhoneNumber').addClass('is-invalid').removeClass('is-valid');
        $('#editClientPhError').text('Invalid phone number');
    } else {
        $('#editClientPhoneNumber').removeClass('is-invalid').addClass('is-valid');
        $('#editClientPhError').text(''); // Clear the error message if the input is valid
    }
})

$('#editClient').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior
    let name = $('#editClientName').val();
    let email = $('#editClientEmail').val();
    let phoneNumber = $('#editClientPhoneNumber').val();
    if (name !== '' && email !== '' && phoneNumber !== '' && $('#addClientNameError').text() === '' && $('#addClientEmailError').text() === '' && $('#addClientPhError').text() === '') {
        console.log("Ok");
        $.fn.updateClient();
    } else {
        $('#editClientSameNameError').text('Please fill all the fields.');
        console.log("Not Ok");
    }
});


$('#deliverablesName').on('input', function () {
    $('#addDeliverablesNameError').text('');
    let name = $('#deliverablesName').val();
    let deliverablesNameError = $('#addDeliverablesNameError');
    if (name === '') {
        $('#deliverablesName').removeClass("is-valid").addClass("is-invalid");
        deliverablesNameError.text('Deliverables name is required');
    } else {
        $('#deliverablesName').removeClass("is-invalid").addClass("is-valid");
        deliverablesNameError.text('');
    }
});

$('#saveNewDeliverable').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior
    let name = $('#deliverablesName').val();
    if (name === '') {
        $('#deliverablesName').removeClass("is-valid").addClass("is-invalid");
        $('#addDeliverablesNameError').text('Deliverables name is required');
    } else {
        $.fn.addDeliverable();
    }
});

$('#editDeliverableName').on('input', function () {
    $('#editDeliverableNameError').text('');
    let name = $('#editDeliverableName').val();
    let deliverablesNameError = $('#editDeliverableNameError');
    if (name === '') {
        $('#editDeliverableName').removeClass("is-valid").addClass("is-invalid");
        deliverablesNameError.text('Deliverables name is required');
    } else {
        $('#editDeliverableName').removeClass("is-invalid").addClass("is-valid");
        deliverablesNameError.text('');
    }
});

$('#editDeliverable').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior
    let name = $('#editDeliverableName').val();
    if (name === '') {
        $('#editDeliverableName').removeClass("is-valid").addClass("is-invalid");
        $('#editDeliverableNameError').text('Deliverables name is required');
    } else {
        $.fn.updateDeliverable();
    }
});


$('#OutlineName').on('input', function () {
    $('#addSystemOutlineNameError').text('');
    let name = $('#OutlineName').val();
    let systemOutlineNameError = $('#addSystemOutlineNameError');
    if (name === '') {
        $('#OutlineName').removeClass("is-valid").addClass("is-invalid");
        systemOutlineNameError.text('System outline name is required');
    } else {
        $('#OutlineName').removeClass("is-invalid").addClass("is-valid");
        systemOutlineNameError.text('');
    }
});

$('#saveNewSystemOutline').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior
    let name = $('#OutlineName').val();
    if (name === '') {
        $('#OutlineName').removeClass("is-valid").addClass("is-invalid");
        $('#addSystemOutlineNameError').text('System outline name is required');
        return false;
    } else {
        $.fn.addSystemOutline();
    }
});

$('#editOutlineName').on('input', function () {
    $('#editSystemOutlineNameError').text('');
    let name = $('#editOutlineName').val();
    let systemOutlineNameError = $('#editSystemOutlineNameError');
    if (name === '') {
        $('#editOutlineName').removeClass("is-valid").addClass("is-invalid");
        systemOutlineNameError.text('System outline name is required');
    } else {
        $('#editOutlineName').removeClass("is-invalid").addClass("is-valid");
        systemOutlineNameError.text('');
    }
});

$('#editOutline').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior
    let name = $('#editOutlineName').val();
    if (name === '') {
        $('#editOutlineName').removeClass("is-valid").addClass("is-invalid");
        $('#editSystemOutlineNameError').text('System outline name is required');
        return false;
    } else {
        $.fn.updateSystemOutline();
    }
});

$('#architectureName').on('input', function () {
    $('#addArchitectureSameNameError').text('');
    let name = $('#architectureName').val();
    let type = $('#architectureType').val();
    let architectureNameError = $('#addArchitectureNameError');
    let architectureTypeError = $('#addArchitectureTypeError');
    if (name === '') {
        $('#architectureName').addClass('is-invalid').removeClass('is-valid');
        architectureNameError.text('Architecture name is required');
    } else {
        $('#architectureName').removeClass('is-invalid').addClass('is-valid');
        architectureNameError.text('');
    }
    if (type === '') {
        $('#architectureType').addClass('is-invalid').removeClass('is-valid');
        architectureTypeError.text('Architecture type is required');
    } else {
        $('#architectureType').removeClass('is-invalid').addClass('is-valid');
        architectureTypeError.text('');
    }
});

$('#saveNewArchitecture').click(function (event) {
    event.preventDefault(); // Prevent the default form submission behavior

    let name = $('#architectureName').val();
    let type = $('#architectureType').val();
    let architectureNameError = $('#addArchitectureNameError');
    let architectureTypeError = $('#addArchitectureTypeError');
    if (name === '') {
        $('#architectureName').addClass('is-invalid').removeClass('is-valid');
        architectureNameError.text('Architecture name is required');
    }
    if (type === '') {
        $('#architectureName').addClass('is-invalid').removeClass('is-valid');
        architectureTypeError.text('Architecture type is required');
    }
    if (name === '' || type === '') {
        $('#addArchitectureSameNameError').text('Please fill all the fields');
        return false;
    } else {
        $.fn.addArchitecture();
    }
});

$('#editArchitectureName').on('input', function () {
    $('#editArchitectureSameNameError').text('');
    let name = $('#editArchitectureName').val();
    let type = $('#editArchitectureType').val();
    let architectureNameError = $('#editArchitectureNameError');
    let architectureTypeError = $('#editArchitectureTypeError');
    if (name === '') {
        $('#editArchitectureName').addClass('is-invalid').removeClass('is-valid');
        architectureNameError.text('Architecture name is required');
    } else {
        $('#editArchitectureName').removeClass('is-invalid').addClass('is-valid');
        architectureNameError.text('');
    }
    if (type === '') {
        $('#editArchitectureType').addClass('is-invalid').removeClass('is-valid');
        architectureTypeError.text('Architecture type is required');
    } else {
        $('#editArchitectureType').removeClass('is-invalid').addClass('is-valid');
        architectureTypeError.text('');
    }
});

$('#editArchitecture').click(function (event) {
    event.preventDefault();
    let name = $('#editArchitectureName').val();
    let type = $('#editArchitectureType').val();
    let architectureNameError = $('#editArchitectureNameError');
    let architectureTypeError = $('#editArchitectureTypeError');
    if (name === '') {
        $('#editArchitectureName').addClass('is-invalid').removeClass('is-valid');
        architectureNameError.text('Architecture name is required');
    }
    if (type === '') {
        $('#editArchitectureType').addClass('is-invalid').removeClass('is-valid');
        architectureTypeError.text('Architecture type is required');
    }
    if (name === '' || type === '') {
        $('#editArchitectureSameNameError').text('Please fill all the fields');
        return false;
    } else {
        $.fn.updateArchitecture();
    }
});

