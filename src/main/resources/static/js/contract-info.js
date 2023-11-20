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
//Edit Client for validation

$(document).ready(function () {
    $('#editClientName').on('blur', function () {
        let name = $('#editClientName').val();
        let editClientNameError = $('#editClientNameError');
        if (name.trim() === '') {
            editClientNameError.removeClass('d-none');
        }
    });

    $('#editClientEmail').on('blur', function () {
        let email = $('#editClientEmail').val();
        let editClientEmailError = $('#editClientEmailError');
        if (email === '') {
            editClientEmailError.removeClass('d-none');
        }
    });

    $('#editClientPhoneNumber').on('blur', function () {
        let phoneNumber = $('#editClientPhoneNumber').val();
        let editClientPhError = $('#editClientPhError');
        if (phoneNumber === '') {
            editClientPhError.removeClass('d-none');
        }
    });

    $('#editClient').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#editClientName').val();
        let email = $('#editClientEmail').val();
        let phoneNumber = $('#editClientPhoneNumber').val();
        let editClient = {
            name: name,
            email: email,
            phoneNumber: phoneNumber
        };

        let editClientNameError = $('#editClientNameError');
        let editClientEmailError = $('#editClientEmailError');
        let editClientPhError = $('#editClientPhError');

        if (name === '') {
            editClientNameError.removeClass('d-none');
        }
        if (email === '') {
            editClientEmailError.removeClass('d-none');
        }
        if (phoneNumber === '') {
            editClientPhError.removeClass('d-none');
        }


        if (name === '' || email === '' || phoneNumber === '') {
            console.log("error");
            return false;
        } else {
            $('#editClientModal').modal('hide');
            $.fn.updateClient();
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

//edit deliverable for validation
$(document).ready(function () {
    $('#editDeliverableName').on('blur', function () {
        let name = $('#editDeliverableName').val();
        let editDeliverableNameError = $('#editDeliverableNameError');
        if (name === '') {
            editDeliverableNameError.removeClass('d-none');
        }
    });

    $('#editDeliverable').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#editDeliverableName').val();

        let deliverable = {
            name: name,

        };

        let editDeliverableNameError = $('#editDeliverableNameError');


        if (name === '') {
            editDeliverableNameError.removeClass('d-none');
        }
        if(name === '')
        {
            console.log("error");
            return false;
        }
        else {
            $('#editDeliverableModal').modal('hide');
            $.fn.updateDeliverable();
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
    $('#editArchitectureName').on('blur', function () {
        let name = $('#editArchitectureName').val();
        let type= $('#edtiArchitectureType').val();
        let edtiArchitectureNameError = $('#editArchitectureNameError');
        let editArchitectureTypeError = $('#editArchitectureTypeError');
        if (name === '') {
            editArchitectureNameError.removeClass('d-none');
        }
        if (type === '') {
            editArchitectureTypeError.removeClass('d-none');
        }
    });

    $('#editArchitecture').click(function (event) {
        event.preventDefault(); // Prevent the default form submission behavior

        let name = $('#editArchitectureName').val();
        let type= $('#editArchitectureType').val();

        let architecture = {
            name: name,
            type: type

        };

        let editArchitectureNameError = $('#editArchitectureNameError');


        if (name === '') {
            editArchitectureNameError.removeClass('d-none');
        }
        if(type=== ''){
            editArchitectureTypeError.removeClass('d-none');
        }
        if(name === '' || type === '')
        {
            console.log("error");
            return false;
        }
        else {
            $('#editArchitectureModal').modal('hide');
            $.fn.updateArchitecture();
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


$(document).ready(function () {
    $('#editOutlineName').on('blur', function () {
        let name = $('#editOutlineName').val();
        let editSystemOutlineNameError = $('#editSystemOutlineNameError');
        if (name === '') {
            editSystemOutlineNameError.removeClass('d-none');
        }
    });

    $('#editOutline').click(function (event) {
        event.preventDefault();

        let name = $('#editOutlineName').val();
        let editSystemOutlineNameError = $('#editSystemOutlineNameError');

        if (name === '') {
            editSystemOutlineNameError.removeClass('d-none');
            console.log("error",name);
        } else {
            console.log("no error", name)
            // Your logic for when the name is not empty
            $('#editSystemOutlineModal').modal('hide');
            $.fn.updateSystemOutline(); // Call the updateSystemOutline() function
        }
    });
});





























