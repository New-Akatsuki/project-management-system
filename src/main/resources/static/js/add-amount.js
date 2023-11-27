function prepareModal(action) {
    // Reset modal inputs
    $('#basicDesignScreenCount').val('');
    $('#detailedDesignScreenCount').val('');
    $('#codeLineCount').val('');
    $('#unitTestCount').val('');
    $('#integrationTestCount').val('');

    // Change modal title and button label
    if (action === 'add') {
        $('.modal-title').text('Add Project\'s Data');
        $('.btn-primary').text('Add');
    } else if (action === 'update') {
        $('.modal-title').text('Update Project\'s Data');
        $('.btn-primary').text('Update');
        // Fetch existing data and populate the modal inputs
        fetchExistingData();
    }
}

function fetchExistingData() {
    let projectId = document.getElementById('projectId').value;

    console.log()

    // Check if a project is selected
    if (!projectId || projectId === '#') {
        alert('Please select a project.');
        return;
    }

    // Make an AJAX request to fetch existing data
    $.ajax({
        url: '/api/amount/get-amount/' + projectId,
        type: 'GET',
        success: function (data) {
            // Populate the modal inputs with existing data
            data.forEach(function (amount) {
                switch (amount.developmentPhase) {
                    case 'BASIC_DESIGN':
                        $('#basicDesignScreenCount').val(amount.amount);
                        break;
                    case 'DETAILED_DESIGN':
                        $('#detailedDesignScreenCount').val(amount.amount);
                        break;
                    case 'CODING':
                        $('#codeLineCount').val(amount.amount);
                        break;
                    case 'UNIT_TESTING':
                        $('#unitTestCount').val(amount.amount);
                        break;
                    case 'INTEGRATION_TESTING':
                        $('#integrationTestCount').val(amount.amount);
                        break;
                    // Add more cases for other development phases if needed
                }
            });
        },
        error: function (error) {
            console.error('Error fetching existing data:', error);
        }
    });
}


function addOrUpdateAmount() {
    // Retrieve values from input fields
    let projectId = $('#projectId').val();

    // Check if a project is selected
    if (!projectId || projectId === '#') {
        alert('Please select a project.');
        return;
    }

    // Create an array to store AmountDto objects
    let addAmounts = [];

    // Helper function to check if the development phase already exists
    function isDevelopmentPhaseExists(developmentPhase) {
        return addAmounts.some(amount => amount.developmentPhase === developmentPhase);
    }

    // Add BASIC DESIGN amount if it's not null and doesn't already exist
    let basicDesignScreenCount = $('#basicDesignScreenCount').val();
    if (basicDesignScreenCount !== null && basicDesignScreenCount !== '' && !isDevelopmentPhaseExists('BASIC_DESIGN')) {
        addAmounts.push({ id: null, projectId: projectId, developmentPhase: 'BASIC_DESIGN', amount: basicDesignScreenCount });
    }

    // Add DETAILED DESIGN amount if it's not null and doesn't already exist
    let detailedDesignScreenCount = $('#detailedDesignScreenCount').val();
    if (detailedDesignScreenCount !== null && detailedDesignScreenCount !== '' && !isDevelopmentPhaseExists('DETAILED_DESIGN')) {
        addAmounts.push({ id: null, projectId: projectId, developmentPhase: 'DETAILED_DESIGN', amount: detailedDesignScreenCount });
    }

    // Add CODING amount if it's not null and doesn't already exist
    let codeLineCount = $('#codeLineCount').val();
    if (codeLineCount !== null && codeLineCount !== '' && !isDevelopmentPhaseExists('CODING')) {
        addAmounts.push({ id: null, projectId: projectId, developmentPhase: 'CODING', amount: codeLineCount });
    }

    // Add UNIT TESTING amount if it's not null and doesn't already exist
    let unitTestCount = $('#unitTestCount').val();
    if (unitTestCount !== null && unitTestCount !== '' && !isDevelopmentPhaseExists('UNIT_TESTING')) {
        addAmounts.push({ id: null, projectId: projectId, developmentPhase: 'UNIT_TESTING', amount: unitTestCount });
    }

    // Add INTEGRATION TESTING amount if it's not null and doesn't already exist
    let integrationTestCount = $('#integrationTestCount').val();
    if (integrationTestCount !== null && integrationTestCount !== '' && !isDevelopmentPhaseExists('INTEGRATION_TESTING')) {
        addAmounts.push({ id: null, projectId: projectId, developmentPhase: 'INTEGRATION_TESTING', amount: integrationTestCount });
    }

    // Check if addAmounts is empty (no new developments to add)
    if (addAmounts.length === 0) {
        alert('No new developments to add.');
        return;
    }

    // AJAX request to the backend to add the amounts
    $.ajax({
        url: '/api/amount/add-or-update-amount',
        method: 'POST',
        data: JSON.stringify(addAmounts),
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        success: function (data) {
            // Close the modal on success
            $("#staticBackdrop").modal('hide');
            console.log(data);
            location.reload();
        },
        error: function (xhr, status, error) {
            console.log(status);
            console.log(xhr.responseText);
            console.error('Error adding/updating amounts:', error);
        }
    });

    // Clear the modal inputs
    $('#basicDesignScreenCount').val('');
    $('#detailedDesignScreenCount').val('');
    $('#codeLineCount').val('');
    $('#unitTestCount').val('');
    $('#integrationTestCount').val('');
}