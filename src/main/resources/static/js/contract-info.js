$(document).ready(function () {
    $('#clientName').on('blur', function () {
        let name = $('#clientName').val();
        let clientNameError = $('#addClientNameError');
        if (name === '') {
            clientNameError.removeClass('d-none');
        }
    });

    $('#clientEmail').on('blur', function () {
        let email = $('#clientEmail').val();
        let clientEmailError = $('#addClientEmailError');
        if (email === '') {
            clientEmailError.removeClass('d-none');
        }
    });

    $('#clientPhoneNumber').on('blur', function () {
        let phoneNumber = $('#clientPhoneNumber').val();
        let clientPhoneError = $('#addClientPhError');
        if (phoneNumber === '') {
            clientPhoneError.removeClass('d-none');
        }
    });

    $('#saveNewClient').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#clientName').val();
        let email = $('#clientEmail').val();
        let phoneNumber = $('#clientPhoneNumber').val();
        let client = {
            name: name,
            email: email,
            phoneNumber: phoneNumber
        };

        let clientNameError = $('#addClientNameError');
        let clientEmailError = $('#addClientEmailError');
        let clientPhError = $('#addClientPhError');

        if (name === '') {
            clientNameError.removeClass('d-none');
        }
        if (email === '') {
            clientEmailError.removeClass('d-none');
        }
        if (phoneNumber === '') {
            clientPhError.removeClass('d-none');
        }
        if(name === '' || email === '' || phoneNumber === '')
        {
            console.log("error");
            return false;
        }
        else {
            $('#addClientModal').modal('hide');
           $.fn.addClient();
        }
    });
});



$(document).ready(function () {
    $('#deliverablesName').on('blur', function () {
        let name = $('#deliverablesName').val();
        let deliverablesNameError = $('#addDeliverablesNameError');
        if (name === '') {
            deliverablesNameError.removeClass('d-none');
        }
    });

    $('#saveNewDeliverable').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#deliverablesName').val();

        let deliverable = {
            name: name,

        };

        let deliverablesNameError = $('#addDeliverablesNameError');


        if (name === '') {
            deliverablesNameError.removeClass('d-none');
        }
        if(name === '')
        {
            console.log("error");
            return false;
        }
        else {
            $('#addDeliverablesModal').modal('hide');
            $.fn.addDeliverable();
        }
    });
});

$(document).ready(function () {
    $('#architectureName').on('blur', function () {
        let name = $('#architectureName').val();
        let type= $('#architectureType').val();
        let architectureNameError = $('#addArchitectureNameError');
        let architectureTypeError = $('#addArchitectureTypeError');
        if (name === '') {
            architectureNameError.removeClass('d-none');
        }
        if (type === '') {
            architectureTypeError.removeClass('d-none');
        }
    });

    $('#saveNewArchitecture').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#architectureName').val();
        let type= $('#architectureType').val();

        let architecture = {
            name: name,
            type: type

        };

        let architectureNameError = $('#addArchitectureNameError');


        if (name === '') {
            architectureNameError.removeClass('d-none');
        }
        if(type=== ''){
            architectureTypeError.removeClass('d-none');
        }
        if(name === '' || type === '')
        {
            console.log("error");
            return false;
        }
        else {
            $('#addArchitectureModal').modal('hide');
            $.fn.addArchitecture();
        }
    });
});


$(document).ready(function () {
    $('#OutlineName').on('blur', function () {
        let name = $('#OutlineName').val();
        let systemOutlineNameError = $('#addSystemOutlineNameError');
        if (name === '') {
            systemOutlineNameError.removeClass('d-none');
        }
    });

    $('#saveNewSystemOutline').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#OutlineName').val();

        let systemOutline = {
            name: name,

        };

        let systemOutlineNameError = $('#addSystemOutlineNameError');


        if (name === '') {
            systemOutlineNameError.removeClass('d-none');
        }
        if(name === '')
        {
            console.log("error");
            return false;
        }
        else {
            $('#addSystemOutlineModal').modal('hide');
            $.fn.addSystemOutline();
        }
    });
});


























