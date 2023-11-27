$(document).ready(function () {
    $('#clientName').on('input', function () {
        $('#addClientSameNameError').addClass('d-none');
        let name = $('#clientName').val();
        let clientNameError = $('#addClientNameError');
        if (name === '') {
            clientNameError.removeClass('d-none');
        }else{
            clientNameError.addClass('d-none');
        }
    });

    $('#clientEmail').on('input', function () {
        $('#addClientSameNameError').addClass('d-none');
        let email = $('#clientEmail').val();
        let clientEmailError = $('#addClientEmailError');
        let clientEmailFormatError = $('#addClientEmailFormatError');
        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if(!email.match(emailPattern)) {
            $('#clientEmail').addClass("is-invalid");
            clientEmailFormatError.removeClass('d-none');
            console.log("Invalid email");
        }else{
            $('#clientEmail').removeClass("is-invalid");
            $('#clientEmail').addClass("is-valid");
            clientEmailFormatError.addClass('d-none');
        }
        if (email === '') {
            clientEmailFormatError.addClass('d-none');
            clientEmailError.removeClass('d-none');
        }else{
            clientEmailError.addClass('d-none');
        }
    });

    $('#clientPhoneNumber').on('input', function () {
        $('#addClientSameNameError').addClass('d-none');
        let phoneNumber = $('#clientPhoneNumber').val();
        let clientPhoneError = $('#addClientPhError');
        if (phoneNumber === '') {
            clientPhoneError.removeClass('d-none');
        }else{
            clientPhoneError.addClass('d-none');
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
        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (name === '') {
            clientNameError.removeClass('d-none');
        }
        if (email === '') {
            clientEmailError.removeClass('d-none');
        }
        if (phoneNumber === '') {
            clientPhError.removeClass('d-none');
        }
        if (!email.match(emailPattern)) {
            $('#clientEmail').addClass("is-invalid");
            console.log("Invalid email");
        }
        if(name === '' || email === '' || phoneNumber === '' ||!email.match(emailPattern))
        {
            console.log("error");
            return false;
        }
        else {
            $.fn.addClient();
        }
    });
});
//Edit Client for validation

$(document).ready(function () {
    $('#editClientName').on('input', function () {
        $('#editClientSameNameError').addClass('d-none');
        let name = $('#editClientName').val();
        let editClientNameError = $('#editClientNameError');
        if (name.trim() === '') {
            editClientNameError.removeClass('d-none');
        }else{
            editClientNameError.addClass('d-none');
        }
    });

    $('#editClientEmail').on('input', function () {
        $('#editClientSameNameError').addClass('d-none');
        let email = $('#editClientEmail').val();
        let clientEmailError = $('#editClientEmailError');
        let clientEmailFormatError = $('#editClientEmailFormatError');
        let emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if(!email.match(emailPattern)) {
            $('#editClientEmail').addClass("is-invalid");
            clientEmailFormatError.removeClass('d-none');
            console.log("Invalid email");
        }else{
            $('#editClientEmail').removeClass("is-invalid");
            $('#editClientEmail').addClass("is-valid");
            clientEmailFormatError.addClass('d-none');
        }
        if (email === '') {
            clientEmailFormatError.addClass('d-none');
            clientEmailError.removeClass('d-none');
        }else{
            clientEmailError.addClass('d-none');
        }
    });

    $('#editClientPhoneNumber').on('input', function () {
        $('#editClientSameNameError').addClass('d-none');
        let phoneNumber = $('#editClientPhoneNumber').val();
        let editClientPhError = $('#editClientPhError');
        if (phoneNumber === '') {
            editClientPhError.removeClass('d-none');
        }else{
            editClientPhError.addClass('d-none');
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
        let emailPattern= /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        if (name === '') {
            editClientNameError.removeClass('d-none');
        }
        if (email === '') {
            editClientEmailError.removeClass('d-none');
        }
        if (!email.match(emailPattern)) {
            $('#clientEmail').addClass("is-invalid");
            console.log("Invalid email");
        }
        if (phoneNumber === '') {
            editClientPhError.removeClass('d-none');
        }

        if (name === '' || email === '' || phoneNumber === '' || !email.match(emailPattern)) {
            console.log("error");
            return false;
        } else {
            $.fn.updateClient();
        }
    });
});



$(document).ready(function () {
    $('#deliverablesName').on('input', function () {
        $('#addDeliverableSameNameError').addClass('d-none');
        let name = $('#deliverablesName').val();
        let deliverablesNameError = $('#addDeliverablesNameError');
        if (name === '') {
            deliverablesNameError.removeClass('d-none');
        }else{
            deliverablesNameError.addClass('d-none');
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
            $.fn.addDeliverable();
        }
    });
});

//edit deliverable for validation
$(document).ready(function () {
    $('#editDeliverableName').on('input', function () {
        $('#editDeliverableSameNameError').addClass('d-none');
        let name = $('#editDeliverableName').val();
        let editDeliverableNameError = $('#editDeliverableNameError');
        if (name === '') {
            editDeliverableNameError.removeClass('d-none');
        }else{
            editDeliverableNameError.addClass('d-none');
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
            $.fn.updateDeliverable();
        }
    });
});


$(document).ready(function () {
    $('#architectureName').on('input', function () {
        $('#addArchitectureSameNameError').addClass('d-none');
        let name = $('#architectureName').val();
        let type= $('#architectureType').val();
        let architectureNameError = $('#addArchitectureNameError');
        let architectureTypeError = $('#addArchitectureTypeError');
        if (name === '') {
            architectureNameError.removeClass('d-none');
        }else{
            architectureNameError.addClass('d-none');
        }
        if (type === '') {
            architectureTypeError.removeClass('d-none');
        }else{
            architectureTypeError.addClass('d-none');
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
            $.fn.addArchitecture();
        }
    });
});



$(document).ready(function () {
    $('#editArchitectureName').on('input', function () {
        $('#editArchitectureSameNameError').addClass('d-none');
        let name = $('#editArchitectureName').val();
        let type= $('#edtiArchitectureType').val();
        let editArchitectureNameError = $('#editArchitectureNameError');
        let editArchitectureTypeError = $('#editArchitectureTypeError');
        if (name === '') {
            editArchitectureNameError.removeClass('d-none');
        }else{
            editArchitectureNameError.addClass('d-none');
        }
        if (type === '') {
            editArchitectureTypeError.removeClass('d-none');
        }else{
            editArchitectureTypeError.addClass('d-none');
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
            $.fn.updateArchitecture();
        }
    });
});




$(document).ready(function () {
    $('#OutlineName').on('input', function () {
        $('#addSystemOutlineSameNameError').addClass('d-none');
        let name = $('#OutlineName').val();
        let systemOutlineNameError = $('#addSystemOutlineNameError');
        if (name === '') {
            systemOutlineNameError.removeClass('d-none');
        }else{
            systemOutlineNameError.addClass('d-none');
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
            $.fn.addSystemOutline();
        }
    });
});


$(document).ready(function () {
    $('#editOutlineName').on('input', function () {
        $('#editSystemOutlineSameNameError').addClass('d-none');
        let name = $('#editOutlineName').val();
        let editSystemOutlineNameError = $('#editSystemOutlineNameError');
        if (name === '') {
            editSystemOutlineNameError.removeClass('d-none');
        }else{
            editSystemOutlineNameError.addClass('d-none');
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
            $.fn.updateSystemOutline(); // Call the updateSystemOutline() function
        }
    });
});





























