// Function to toggle the toolbar
function clean() {
    $('#title').val("");
    quillObjective.root.innerHTML = "";
    quillBackground.root.innerHTML = "";
    $('#client').val("default").trigger('change');
    $('#member').val(null).trigger('change');
    $('#foc').val(null).trigger('change');
}

let quillObjective = new Quill("#objective", aa);
let quillBackground = new Quill("#background", aa);
//document element ready
document.addEventListener('DOMContentLoaded', function () {
    const containers = document.querySelectorAll('.ql-container');
    containers.forEach(item => {
        item.classList.add('round-full-border');
    });
    toggleToolbar(0, 'objective');
    toggleToolbar(1, 'background');
})

    $(document).ready(function () {
    $('.js-example-basic-single').select2();
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
    function setupModalHandling(input, error, modalButton, modalSaveButton, getURL, postURL, selectBoxClass, modalId) {
    function toggleError() {
        error.toggleClass('d-none', input.val().trim() !== '');
    }
    function resetModal() {
    let modalID = $('#' + modalId);
    input.val('');
    error.addClass('d-none');
    modalID.modal('show');
    // Use the 'shown.bs.modal' event to focus on the input
    modalID.on('shown.bs.modal', function () {
    input.focus();
});
}
    function handlePostRequest(data) {
    $.ajax({
    url: postURL,
    type: 'POST',
    contentType: "application/json",
    data: JSON.stringify(data),
    success: function (responseData) {
    $('#' + modalId).modal('hide');
    updateSelect(selectBoxClass, responseData);
    console.log(responseData);
},
    error: function (responseData) {
    console.log(responseData);
    alert("Creation failed");
}
});
}
    input.on('input', toggleError);
    modalButton.click(resetModal);



    $(document).ready(function () {
    $.ajax({
    url: '/get-client',
    type: 'GET',
    success: function (data) {
    populateSelect('clientSelectBox', data);
},
    error: function (data) {
    console.log(data);
}
});
});
    modalSaveButton.click(function () {
    let inputValue = input.val().trim();
    if (inputValue !== '') {
    handlePostRequest({id: null, name: inputValue});
} else {
    error.removeClass('d-none');
    input.focus();
}
});
}

    // Usage for addClient button
    setupModalHandling(
    $('#OutlineName'),
    $('#outlineNameError'),
    $('#addSystemOutlineButton'),
    $('#outlineSave'),
    '/get-systemoutlines',
    '/pm/add-system-outline',
    '.selectBox',
    'addSystemOutlineModal'
    );
    setupModalHandling(
    $('#deliverablesName'),
    $('#deliverablesNameError'),
    $('#addDeliverablesButton'),
    $('#deliverableSave'),
    '/get-deliverables',
    '/pm/add-deliverable',
    '.selectBox2',
    'addDeliverablesModal'
    );


    function closeModal(modalId){
    $(modalId).modal('hide');
}

    $.ajax({
    url: '/get-member',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
    populateSelect('.addMember', data);
    $('#member').select2({
    placeholder: 'Choose Contract Member',
    closeOnSelect: true,
    width: '100%'
});
    $('#foc').select2({
    placeholder: 'Choose FOC Member',
    closeOnSelect: true,
    width: '100%'
});
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
    url: '/get-deliverables',
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
    url: '/get-architecture-outlines',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
    console.log('data', data)
    populateSelect('.arcSelectBox', data)

},
    error: function (error) {
    console.error('Error fetching data:', error);
}
});
    $.ajax({
    url: '/get-systemoutlines',
    method: 'GET',
    dataType: 'json',
    success: function (data) {
    console.log('data', data)
    populateSelect('.selectBox2', data)
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
    placeholder: 'Choose Architecture Outlines ',
    closeOnSelect: true,
    width: '100%'
});

    $('#so').select2({
    placeholder: 'Choose System Outlines ',
    closeOnSelect: true,
    width: '100%'
});

    $(document).ready(function () {
    $.ajax({
        url: '/get-department',
        type: 'GET',
        success: function (data) {
            console.log("Department data : ", data);
            populateSelect('.js-example-basic-single.select-box2', data);
        },
        error: function (data) {
            console.log(data);
        }
    });
});

    $('.text-danger').hide();
    $('#create-project').submit(function (e) {
    console.log("Submit button clicked")
    e.preventDefault();
    // Reset errors
    // $('.text-danger').hide();
    let name = $('#title').val();
    let department = parseInt($('#department').val(), 10);
    let startDate = new Date($('#start-date').val());
    let endDate = new Date($('#end-date').val());
    let so = $('#so').val().map(val => parseInt(val, 10));
    let ao = $('#ao').val().map(val => parseInt(val, 10));
    let deli = $('#deli').val().map(val => parseInt(val, 10));
    let member = $('#member').val().map(val => parseInt(val, 10));
    let foc = $('#foc').val().map(val => parseInt(val, 10));
    let client = parseInt($('#client').val(), 10);
    let objective = quillObjective.root.innerHTML;
    let background = quillBackground.root.innerHTML;

    console.log("Name :", name);
    console.log("Department :", department);
    console.log("Start Date :", startDate);
    console.log("End Date :", endDate);
    console.log("System Outlines :", so);
    console.log("Architecture Outlines :", ao);
    console.log("Deliverables :", deli);
    console.log("Contract Members :", member);
    console.log("FOC Members :", foc);
    console.log("Client :", client);
    console.log("Objective :", objective);
    console.log("Background :", background);

    let errors = [];

    if (name === '') {
    errors.push('#titleError');
}
    if (isNaN(department) || department === 'default' || department === null) {
    errors.push('#departmentError');
}
    if (isNaN(client) || client === 'default' || client === null) {
    errors.push('#clientError');
}
    if (isNaN(startDate.getTime()) || startDateString !== startDate.toISOString().split('T')[0]) {
    errors.push('#startDateError');
}
    if (isNaN(endDate.getTime()) || startDateString !== endDate.toISOString().split('T')[0]) {
    errors.push('#endDateError');
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
    if (objective === '' || objective === '<p><br></p>') {
    errors.push('#objectiveError');
}
    if (background === '' || background === '<p><br></p>') {
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
    department: department,
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
    console.log(projObj);

    $.ajax({
    url: '/pm/create-proj',
    type: 'POST',
    contentType: "application/json",
    data: jsonString,
    success: function (response) {
    alert("Project created successfully");
},
    error: function (response) {
    alert("Project creation failed");
}
});
});

    // $('.text-danger').hide();

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
    hideErrorOnInteraction($('#department'), $('#departmentError'));
    hideErrorOnInteraction($('#member'), $('#contractMemberError'));
    hideErrorOnInteraction($('#client'), $('#clientError'));
    hideErrorOnInteraction($('#start-date'), $('#startDateError'));
    hideErrorOnInteraction($('#end-date'), $('#endDateError'));
    hideErrorOnInteraction($('#so'), $('#sysOutlinesError'));
    hideErrorOnInteraction($('#ao'), $('#arcOutlinesError'));
    hideErrorOnInteraction($('#deli'), $('#deliverablesError'));
    // Event listeners for Quill text editors
    quillObjective.on('text-change', function () {
    $('#objectiveError').hide();
});
    quillBackground.on('text-change', function () {
    $('#backgroundError').hide();
});

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
});