function toggleLoadingSpinner(show) {
    const dimOverlay = $('#dim-overlay');
    const spinner = $('#loadingSpinner');

    if (show) {
        dimOverlay.show();
        spinner.show();
    } else {
        dimOverlay.hide();
        spinner.hide();
    }
}


$(document).ready(function () {
    // Function to populate options from JSON data
    // Initialize Select2 for both select boxes
    $('.js-example-basic-single').select2();

    $(document).ready(function () {
        $.ajax({
            url: '/clients',
            type: 'GET',
            success: function (data) {
                populateSelect('.js-example-basic-single.select-box', data);
            },
            error: function (data) {
                console.log(data);
            }
        });
    });

});

function populateSelect(selector, options) {
    let select = $(selector);
    $.each(options, function (index, value) {
        select.append('<option value="' + value.id + '">' + value.name + '</option>');
    })
}

function updateSelect(selector, option) {
    let select = $(selector);
    select.append('<option value="' + option.id + '">' + option.name + '</option>');
}

$.ajax({
    url: '/get-users',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
        console.log('data', data)
        $.each(data, function (index, value) {
            $('#member,#foc').append($('<option>', {
                value: value.id,
                text: value.name
            }));
        });

        $('#member').select2({
            placeholder: 'Choose Member',
            closeOnSelect: true,
            width: '100%'
        });

        $('#foc').select2({
            placeholder: 'Choose FOC Member',
            closeOnSelect: true,
            width: '100%'
        });

        // When an option is selected in #member, disable it in #foc
        $('#member').on('select2:select', function (e) {
            var selectedOption = e.params.data.id;
            $('#foc option[value="' + selectedOption + '"]').prop('disabled', true);
            $('#foc').trigger('change');
        });

        // When an option is unselected in #member, enable it in #foc
        $('#member').on('select2:unselect', function (e) {
            var unselectedOption = e.params.data.id;
            $('#foc option[value="' + unselectedOption + '"]').prop('disabled', false);
            $('#foc').trigger('change');
        });

        // When an option is selected in #foc, disable it in #member
        $('#foc').on('select2:select', function (e) {
            var selectedOption = e.params.data.id;
            $('#member option[value="' + selectedOption + '"]').prop('disabled', true);
            $('#member').trigger('change');
        });

        // When an option is unselected in #foc, enable it in #member
        $('#foc').on('select2:unselect', function (e) {
            var unselectedOption = e.params.data.id;
            $('#member option[value="' + unselectedOption + '"]').prop('disabled', false);
            $('#member').trigger('change');
        });
    },
    error: function (error) {
        console.error('Error fetching data:', error);
    }
});

$.ajax({
    url: '/deliverables',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
        console.log('data', data)
        $.each(data, function (index, value) {
            $('#deli').append($('<option>', {
                value: value.id,
                text: value.name
            }));
        });
    },
    error: function (error) {
        console.error('Error fetching data:', error);
    }
});

$.ajax({
    url: '/architectures',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
        console.log('data', data)
        $.each(data, function (index, value) {
            $('#ao').append($('<option>', {
                value: value.id,
                text: value.name
            }));
        });
    },
    error: function (error) {
        console.error('Error fetching data:', error);
    }
});

$.ajax({
    url: '/system-outlines',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
        console.log('data', data)
        $.each(data, function (index, value) {
            $('#so').append($('<option>', {
                value: value.id,
                text: value.name
            }));
        });
    },
    error: function (error) {
        console.error('Error fetching data:', error);
    }
});


$('#deli').select2({
    placeholder: 'Choose Deliverables ',
    closeOnSelect: true,
    width: '100%'
});

$('#ao').select2({
    placeholder: 'Choose Outlines ',
    closeOnSelect: true,
    width: '100%'
});

$('#so').select2({
    placeholder: 'Choose Outlines ',
    closeOnSelect: true,
    width: '100%'
});

$('.text-danger').hide();
$('#create-project').submit(function (e) {
    console.log("Submit button clicked")
    e.preventDefault();
    // Reset errors
    // $('.text-danger').hide();
    let name = $('#title').val();
    let startDate = new Date($('#start-date').val());
    let endDate = new Date($('#end-date').val());
    let so = $('#so').val().map(val => parseInt(val, 10));
    let ao = $('#ao').val().map(val => parseInt(val, 10));
    let deli = $('#deli').val().map(val => parseInt(val, 10));
    let member = $('#member').val().map(val => parseInt(val, 10));
    let foc = $('#foc').val().map(val => parseInt(val, 10));
    let client = parseInt($('#client').val(), 10);
    let objective = $('#objective').val();
    let background = $('#background').val();
    console.log("Start Date : " + startDate);
    console.log("End Date : " + endDate);
    let errors = [];


    if (name === '') {
        errors.push('#titleError');
    }
    if (isNaN(startDate) || startDate === '' || startDate === null || startDate === 'Invalid Date') {
        errors.push('#startDateError');
    }
    if (isNaN(endDate) || endDate === '' || endDate === null || endDate === 'Invalid Date') {
        errors.push('#endDateError');
        $('#duration').focus();
    }
    if (isNaN(client) || client === 'default' || client === null) {
        errors.push('#clientError');
    }
    if (!so || so.length === 0) {
        errors.push('#sysOutlinesError');
    }
    if (!ao || ao.length === 0) {
        errors.push('#arcOutlinesError');
    }
    if (!deli || deli.length === 0) {
        errors.push('#deliverablesError');
    }
    if (!member || member.length === 0) {
        errors.push('#contractMemberError');
    }
    if (objective === '') {
        errors.push('#objectiveError');
    }
    if (background === '') {
        errors.push('#backgroundError');
    }

    // Display all errors at once
    for (let error of errors) {
        $(error).show();
    }

    // If there are errors, stop further processing
    if (errors.length > 0) {
        return false;
    }

    // If no errors, proceed with the AJAX request
    const projObj = {
        id: null,
        name: name,
        startDate: startDate,
        endDate: endDate,
        background: background,
        objective: objective,
        systemOutlines: so,
        architectureOutlines: ao,
        deliverables: deli,
        contractMembers: member,
        focMembers: foc,
        client: client,
    };
    // Convert to JSON string
    const jsonString = JSON.stringify(projObj);
    // console.log(projObj);
    toggleLoadingSpinner(true);
    $.ajax({
        url: '/add-project',
        type: 'POST',
        contentType: "application/json",
        data: jsonString,
        success: function (response) {
            toggleLoadingSpinner(false);
            displaySuccessModal();
        },
        error: function (response) {
            alert("Project creation failed");
            toggleLoadingSpinner(false);
        }
    });
});

function buildToast(data = {
    title: 'Error!',
    body: 'something went wrong :(',
    icon: 'bx bx-error-circle',
    color: 'text-danger'
}) {

    $('#toastModal').remove();
    const toast = `<div id="toastModal" class="toast-container position-fixed toast-place">
                                  <div id="liveToast" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
                                    <div class="toast-header">
                                       <i class="${data.icon} ${data.color} fs-5 me-2"></i>
                                       <strong class="me-auto ${data.color}">${data.title}</strong>
                                      <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                                    </div>
                                    <div class="toast-body">
                                      ${data.body}
                                    </div>
                                  </div>
                                </div>`;
    $('body').append(toast);
}

$('.text-danger').hide();

// Function to hide the relevant error message when user interacts with input/select
function hideErrorOnInteraction(input, error) {
    input.on('input', function () {
        error.hide();
    });

    input.on('change', function () {
        error.hide();
    });
}

// Event listeners for input fields
hideErrorOnInteraction($('#title'), $('#titleError'));
hideErrorOnInteraction($('#member'), $('#contractMemberError'));
hideErrorOnInteraction($('#client'), $('#clientError'));
hideErrorOnInteraction($('#start-date'), $('#startDateError'));
hideErrorOnInteraction($('#duration'), $('#endDateError'));
hideErrorOnInteraction($('#so'), $('#sysOutlinesError'));
hideErrorOnInteraction($('#ao'), $('#arcOutlinesError'));
hideErrorOnInteraction($('#deli'), $('#deliverablesError'));
hideErrorOnInteraction($('#objective'), $('#objectiveError'));
hideErrorOnInteraction($('#background'), $('#backgroundError'));

$(document).ready(function () {
    $("#start-date").datepicker({
        dateFormat: "yy-mm-dd",
        showOtherMonths: true,
        selectOtherMonths: true,
        changeMonth: true,
        changeYear: true,
        onSelect: (dateText, inst) => $('#start-date').val(dateText).trigger('change'),
    });

    $("#end-date").datepicker({
        dateFormat: "yy-mm-dd",
        showOtherMonths: true,
        selectOtherMonths: true,
        changeMonth: true,
        changeYear: true,
        onSelect: (dateText, inst) => $('#end-date').val(dateText).trigger('change'),
    });
    //check name input
    checkDatesValidation("start-date", "end-date");
    //form validate
    formValidate("form-wizard1")
});


let department = null;
$(document).ready(function () {
    $.ajax({
        url: '/get-department',
        type: 'GET',
        success: function (data) {
            department = data;
            console.log(department);
        },
        error: function (data) {
            console.log(data);
        }
    });
});

$('#addDeliverablesButton').click(function () {
    let modalID = $('#addDeliverablesModal');
    $('#deliverablesName').val('');
    $('#deliverablesNameError').addClass('d-none');
    $('#deliverablesSameNameError').addClass('d-none');
    modalID.off('shown.bs.modal');
    modalID.modal('show');
});

function addDeliverable() {
    let name = $('#deliverablesName').val();
    if (name === '') {
        $('#deliverablesNameError').removeClass('d-none');
        $('#deliverablesName').focus();
    } else {
        let newDeliverable = {
            id: null,
            name: name
        };
        $.ajax({
            type: "POST",
            url: "/add-deliverable", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newDeliverable),
            dataType: 'json',
            success: function (data) {
                updateSelect('.deliverable-select-box', data);
                $("#addDeliverablesModal").modal('hide').data('bs.modal', null);
                $('#deliverablesSameNameError').addClass('d-none');
            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                $('#deliverablesSameNameError').removeClass('d-none');
            }
        });
    }
}

$('#addSystemOutlineButton').click(function () {
    let modalID = $('#addSystemOutlineModal');
    $('#OutlineName').val('');
    $('#systemOutlineNameError').addClass('d-none');
    $('#systemOutlineSameNameError').addClass('d-none');
    modalID.modal('show');
    // Unbind previous event handlers
    modalID.off('shown.bs.modal');
    // Use the 'shown.bs.modal' event to focus on the input
});

function addSystemOutline() {
    let name = $('#OutlineName').val();
    if (name === '') {
        $('#systemOutlineNameError').removeClass('d-none');
        $('#OutlineName').focus();

    } else {
        let newSystemOutline = {
            id: null,
            name: name,
        };
        $.ajax({
            type: "POST",
            url: "/add-system-outline", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newSystemOutline),
            dataType: 'json',
            success: function (data) {
                updateSelect('.sysOutline-select-box', data);
                $("#addSystemOutlineModal").modal('hide').data('bs.modal', null);
                $('#systemOutlineSameNameError').addClass('d-none');

            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                $('#systemOutlineSameNameError').removeClass('d-none');
            }
        });
    }
}

$('#addArchitectureButton').click(function () {
    let modalID = $('#architectureAddModal');
    $('#architectureName').val('');
    $('#architectureTypeError').hide();
    $('#architectureNameError').addClass('d-none');
    $('#architectureSameNameError').addClass('d-none');
    modalID.modal('show');
    // Unbind previous event handlers
    modalID.off('shown.bs.modal');
    // Use the 'shown.bs.modal' event to focus on the input
});

function addArchitecture() {
    let name = $('#architectureName').val();
    let type = $('#architectureType').val();
    if (name === '' || name == '' || name == null) {
        $('#architectureNameError').removeClass('d-none');
        $('#architectureName').focus();
    } else {
        let newArchitecture = {
            id: null,
            name: name,
            type: type
        };
        $.ajax({
            type: "POST",
            url: "/add-architecture", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newArchitecture),
            dataType: 'json',
            success: function (data) {
                $('#architectureAddModal').modal('toggle');
                updateSelect('.arc-select-box', data);
            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                $('#architectureSameNameError').removeClass('d-none');
            }
        });
    }

}

$('#addClientButton').click(function () {
    let modalID = $('#addClientModal');
    $('#clientName').val('').removeClass("is-invalid").removeClass("is-valid");
    $('#clientEmail').val('').removeClass("is-invalid").removeClass("is-valid");
    $('#clientPhoneNumber').val('').removeClass("is-invalid").removeClass("is-valid");
    // $('#clientSameNameError').addClass('d-none');
    $('#clientModalError').text("");
    modalID.modal('show');
    // Unbind previous event handlers
    modalID.off('shown.bs.modal');
    // Use the 'shown.bs.modal' event to focus on the input
});

function addClient() {
    addClientPhoneNumber=phoneInput.getNumber();
    let addName = $('#clientName').val();
    let addEmail = $('#clientEmail').val();
    let addPhoneNumber = $('#clientPhoneNumber').val();
    let nameError = $('#nameError');
    let emailError = $('#emailError');
    let phoneError = $('#phoneError');

    if (emailError.text() === '' && nameError.text() === '' && phoneError.text() === '' && addName !== '' && addEmail !== '' && addPhoneNumber !== '') {
        let newClient = {
            name: addName,
            email: addEmail,
            phoneNumber: addClientPhoneNumber,
        };
        $.ajax({
            type: "POST",
            url: "/add-client",
            contentType: "application/json",
            data: JSON.stringify(newClient),
            dataType: 'json',
            success: function (data) {
                $("#addClientModal").modal('toggle');
                updateSelect('.js-example-basic-single.select-box', data);
                $('#clientSameNameError').addClass('d-none');
            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                // $('#clientSameNameError').removeClass('d-none');
                $('#clientModalError').text("This client already exists");
            }
        });
    }else{
        $('#clientModalError').text("Please fill all the fields");
    }
}

hideErrorOnInteraction($('#deliverablesName'), $('#deliverablesNameError'));
hideErrorOnInteraction($('#clientName'), $('#clientNameError'));
hideErrorOnInteraction($('#clientEmail'), $('#clientEmailError'));
hideErrorOnInteraction($('#clientPhoneNumber'), $('#clientPhoneNumberError'));
hideErrorOnInteraction($('#architectureName'), $('#architectureNameError'));
hideErrorOnInteraction($('#OutlineName'), $('#systemOutlineNameError'));

function displaySuccessModal() {
    let modalBox = $('#successModal')
    // Show the modal
    modalBox.modal('show');
    // Handle close event of the modal
    modalBox.on('hidden.bs.modal', function () {
        window.location.href = "/projects";
    });
}

function clean() {
    $('#duration').val("")
    $('#title').val("");
    $('#client').val("default").trigger('change');
    $('#member').val(null).trigger('change');
    $('#foc').val(null).trigger('change');
    $('#department').val("default").trigger('change');
    $('#start-date').val("");
    $('#end-date').val("");
    $('#deli').val(null).trigger('change');
    $('#ao').val(null).trigger('change');
    $('#so').val(null).trigger('change');
    $('#objective').val("");
    $('#background').val("");
    $('.text-danger').hide();
}

$(document).ready(function () {
    // Initialize datepicker with a specific format
    var dateFormat = 'mm/dd/yyyy';

    $('#start-date').datepicker({
        format: dateFormat,
        autoclose: true
    });

    $('#end-date').datepicker({
        format: dateFormat,
        autoclose: true
    });

    // Update end date on change of start date or duration
    $('#start-date, #duration').on('change input', function () {
        updateEndDate();
    });

    function updateEndDate() {
        var startDate = $('#start-date').datepicker('getDate');
        var duration = parseInt($('#duration').val());

        if (!isNaN(startDate) && !isNaN(duration)) {
            var endDate = new Date(startDate);
            endDate.setMonth(endDate.getMonth() + duration);

            // Update the end date input
            $('#end-date').datepicker('setDate', endDate);
        }
    }
});

function notify(notification) {
    showToast(notification);
}

$('#clientName, #clientEmail, #clientPhoneNumber').on('input', function () {
    $('#clientSameNameError').addClass('d-none');
});





