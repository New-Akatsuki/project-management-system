$(document).ready(function () {
    let contractInfo = {
        clients: [],
        deliverables: [],
        architectures: [],
        systems: [],
    };
    // Make an AJAX request to fetch data from the REST API endpoint
    $.ajax({
        url: '/get-contract-info', // Replace with your actual API endpoint
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log(data);
            contractInfo = data;
            renderClientTable(data.clients);
            renderDeliverableTable(data.deliverables);
            renderArchitectureTable(data.architectures);
            renderSystemOutlineTable(data.systems);
        },
        error: function (error) {
            console.log('Error fetching data:', error);
        }
    });

    /*===================================================
    Build the client table
    ===================================================*/
    function renderClientTable(items) {
        if ($.fn.DataTable.isDataTable('#client')) {
            $('#client').DataTable().destroy();
        }

        return $('#client').DataTable({
            data: items,
            columns: [
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },
                {data: 'name'},
                {data: 'email'},
                {data: 'phoneNumber'},
                {
                    data: 'status',
                    render: function (data, type, row) {
                        return `
                      <span class="badge rounded-pill text-bg-${data ? 'success':'danger'}" style="width: 60px">${data ? 'Active' : 'Disabled'}</span>
                    `;
                    }
                },
                {
                    data: 'status',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditClientModal(${row.id})">Edit</button>
                        <div id="toggleBtnGp${row.id}" class="btn-group" role="group" aria-label="Client Status">
                           ${buildToggleUserBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
            ]
        });
    }


    /*===================================================
    Build the deliverable table
     ===================================================*/
    function renderDeliverableTable(items) {
        if ($.fn.DataTable.isDataTable('#deliverable')) {
            $('#deliverable').DataTable().destroy();
        }

        return $('#deliverable').DataTable({
            data: items,
            columns: [
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },
                {data: 'name'},
                {
                    data: 'status',
                    render: function (data, type, row) {
                        return `
                      <span class="badge rounded-pill text-bg-${data ? 'success':'danger'}" style="width: 60px">${data ? 'Active' : 'Disabled'}</span>
                    `;
                    }
                },
                {
                    data: 'status',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditDeliverableModal(${row.id})">Edit</button>
                        <div id="toggleBtndeliverableGp${row.id}" class="btn-group" role="group" aria-label="Deliverable Status">
                           ${buildToggleDeliverableBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
            ]
        });
    }


    /*===================================================
    Build the architecture table
     ===================================================*/


    function renderArchitectureTable(items) {
        if ($.fn.DataTable.isDataTable('#architecture')) {
            $('#architecture').DataTable().destroy();
        }

        return $('#architecture').DataTable({
            data: items,
            columns: [
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },
                {data: 'name'},
                {data: 'type'},

                {
                    data: 'status',
                    render: function (data, type, row) {
                        return `
                      <span class="badge rounded-pill text-bg-${data ? 'success':'danger'}" style="width: 60px">${data ? 'Active' : 'Disabled'}</span>
                    `;
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditArchitectureModal(${row.id})">Edit</button>
                        <div id="toggleBtnArchitectureGp${row.id}" class="btn-group" role="group" aria-label="Architecture Status">
                           ${buildToggleArchitectureBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
            ]
        });
    }


    /*===================================================
   Build the SystemOutline table
    ===================================================*/

    function renderSystemOutlineTable(items) {
        if ($.fn.DataTable.isDataTable('#systemOutline')) {
            $('#systemOutline').DataTable().destroy();
        }

        return $('#systemOutline').DataTable({
            data: items,
            columns: [
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return meta.row + 1;
                    }
                },
                {data: 'name'},

                {
                    data: 'status',
                    render: function (data, type, row) {
                        return `
                      <span class="badge rounded-pill text-bg-${data ? 'success':'danger'}" style="width: 60px">${data ? 'Active' : 'Disabled'}</span>
                    `;
                    }
                },
                {
                    data: 'status',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditSystemOutlineModal(${row.id})">Edit</button>
                        <div id="toggleBtnSystemOutlineGp${row.id}" class="btn-group" role="group" aria-label="SystemOutline Status">
                           ${buildToggleSystemOutlineBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
            ]
        });
    }

    /*===================================================
            save deliverable to database
     ===================================================*/
    function addDeliverable() {
        let newDeliverable = {
            id: null,
            name: $('#deliverablesName').val()
        };

        $.ajax({
            type: "POST",
            url: "/add-deliverable", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newDeliverable),
            dataType: 'json',
            success: function (data) {
                $("#addDeliverablesModal").modal('hide');
                // Update the deliverable in the contractInfo object
                contractInfo.deliverables.push(data);
                //reload Table
                renderDeliverableTable(contractInfo.deliverables);

            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                $('#addDeliverablesNameError').text("This deliverable already exists")
                $('#deliverablesName').addClass('is-invalid').removeClass('is-valid');
            }
        });
    }

    /*===================================================
           save Client to database
    ===================================================*/
    function addClient() {
        let newClient = {
            id: null,
            name: $('#clientName').val(),
            email: $('#clientEmail').val(),
            phoneNumber: phoneInput1.getNumber()
        };
        $.ajax({
            type: "POST",
            url: "/add-client", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newClient),
            dataType: 'json',
            success: function (data) {
                $('#addClientModal').modal('hide');
                // Update the deliverable in the contractInfo object
                contractInfo.clients.push(data);
                //reload Table
                renderClientTable(contractInfo.clients);
            },
            error: function (xhr, status, error) {
                $("#addClientError").text("This client already exists");
            }
        });
    }

    /*===================================================
          save Architecture to database
      ===================================================*/
    function addArchitecture() {
        let newArchitecture = {
            id: null,
            name: $('#architectureName').val(),
            type: $('#architectureType').val(),
        };

        $.ajax({
            type: "POST",
            url: "/add-architecture", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newArchitecture),
            dataType: 'json',
            success: function (data) {
                $("#addArchitectureModal").modal('hide');

                // Update the deliverable in the contractInfo object
                contractInfo.architectures.push(data);
                //reload Table
                renderArchitectureTable(contractInfo.architectures);

            },
            error: function (xhr, status, error) {
                $("#addArchitectureSameNameError").text("This architecture already exists");
                $('#architectureName').addClass('is-invalid').removeClass('is-valid');
            }
        });
    }


    /*===================================================
         save SystemOutline to database
     ===================================================*/
    function addSystemOutline() {

        console.log("addSystemOutline function called");
        let newSystemOutline = {
            id: null,
            name: $('#OutlineName').val(),
        };

        $.ajax({
            type: "POST",
            url: "/add-system-outline", // Replace with your server endpoint for adding deliverables
            contentType: "application/json",
            data: JSON.stringify(newSystemOutline),
            dataType: 'json',
            success: function (data) {
                $("#addSystemOutlineModal").modal('hide');
                // Update the deliverable in the contractInfo object
                contractInfo.systems.push(data);
                //reload Table
                renderSystemOutlineTable(contractInfo.systems);

            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                $('#addSystemOutlineNameError').text('This system outline already exists');
                $('#OutlineName').addClass('is-invalid').removeClass('is-valid');
            }
        });
    }

    /*===================================================
            update deliverable to database
     ===================================================*/
    function updateDeliverable() {
        // Get updated deliverable information from modal fields
        let id = $('#editDeliverableId').val();
        let name = $('#editDeliverableName').val();
        if (name === '') {
            return;
        }

        // Prepare updated deliverable object
        let updatedDeliverable = {
            id: id,
            name: name
        };

        console.log("Updated Deliverable: ", updatedDeliverable)
        // Make a PUT request to update the deliverable data
        $.ajax({
            type: "PUT",
            url: "/deliverable/update", // Endpoint to update deliverable data by ID
            contentType: "application/json",
            data: JSON.stringify(updatedDeliverable),
            dataType: 'json',
            success: function (data) {
                // Handle success response if needed
                console.log("SUCCESS: ", data);
                console.log("contractINfo", contractInfo);
                // Update the deliverable in the contractInfo object
                contractInfo.deliverables = contractInfo.deliverables.map(deliverable => {
                    if (deliverable.id === data.id) {
                        return data;
                    }
                    return deliverable;
                })

                //reload Table
                renderDeliverableTable(contractInfo.deliverables);
                // Close the modal after updating
                $('#editDeliverableModal').modal('hide');

            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                // Optionally, close the modal on error if desired
                $('#editDeliverableNameError').text("This deliverable already exists");
                $('#editDeliverableName').addClass('is-invalid').removeClass('is-valid');
            }
        });
    }

    /*===================================================
           update client to database
    ===================================================*/
    function updateClient() {
        // Get updated deliverable information from modal fields
        let editClientPhoneNumber=phoneInput.getNumber();
        console.log("editClientPhoneNumber",editClientPhoneNumber);
        let id = $('#editClientId').val();
        let name = $('#editClientName').val();
        let email = $('#editClientEmail').val();
        let phoneNumber = $('#editClientPhoneNumber').val();

        if (name === '' || email === '' || phoneNumber === '') {
            return;
        }

        // Prepare updated deliverable object
        let updatedClient = {
            id: id,
            name: name,
            email: email,
            phoneNumber: editClientPhoneNumber
        };

        console.log("Updated Client: ", updatedClient)
        // Make a PUT request to update the deliverable data
        $.ajax({
            type: "PUT",
            url: "/client/update", // Endpoint to update deliverable data by ID
            contentType: "application/json",
            data: JSON.stringify(updatedClient),
            dataType: 'json',
            success: function (data) {
                // Handle success response if needed
                console.log("SUCCESS: ", data);

                console.log("contractINfo", contractInfo);
                // Update the deliverable in the contractInfo object
                contractInfo.clients = contractInfo.clients.map(client => {
                    if (client.id === data.id) {
                        return data;
                    }
                    return client;
                })
                //reload Table
                renderClientTable(contractInfo.clients);
                // Close the modal after updating
                $('#editClientModal').modal('hide');
            },
            error: function (xhr, status, error) {
                $('#editClientSameNameError').text("This client already exists");
            }
        });
    }


    /*===================================================
          update architecture to database
   ===================================================*/
    function updateArchitecture() {
        // Get updated architecture information from modal fields
        let id = $('#editArchitectureId').val();
        let name = $('#editArchitectureName').val();
        let type = $('#editArchitectureType').val();

        if (name === '' || type === '') {
            return;
        }
        // Prepare updated deliverable object
        let updatedArchitecture = {
            id: id,
            name: name,
            type: type,
        };

        console.log("Updated Architecture: ", updatedArchitecture)
        // Make a PUT request to update the deliverable data
        $.ajax({
            type: "PUT",
            url: "/architecture/update", // Endpoint to update deliverable data by ID
            contentType: "application/json",
            data: JSON.stringify(updatedArchitecture),
            dataType: 'json',
            success: function (data) {
                // Handle success response if needed
                console.log("SUCCESS: ", data);

                console.log("contractINfo", contractInfo);
                // Update the architectures in the contractInfo object
                contractInfo.architectures = contractInfo.architectures.map(architecture => {
                    if (architecture.id === data.id) {
                        return data;
                    }
                    return architecture;
                })
                //reload Table
                renderArchitectureTable(contractInfo.architectures);
                // Close the modal after updating
                $('#editArchitectureModal').modal('hide');
            },
            error: function (xhr, status, error) {
                console.log("ERROR: ", xhr.responseText);
                $('#editArchitectureSameNameError').text('This architecture already exists');
                $('#editArchitectureName').addClass('is-invalid').removeClass('is-valid');
            }
        });
    }
    /*===================================================
          update systemOutline to database
   ===================================================*/
    function updateSystemOutline() {
        // Get updated deliverable information from modal fields
        let id = $('#editOutlineId').val();
        let name = $('#editOutlineName').val();
        if (name !== '') {
            // Prepare updated deliverable object
            let updatedSystemOutline = {
                id: id,
                name: name,

            };
            console.log("Updated SystemOutline: ", updatedSystemOutline)
            // Make a PUT request to update the deliverable data
            $.ajax({
                type: "PUT",
                url: "/system-outline/update", // Endpoint to update deliverable data by ID
                contentType: "application/json",
                data: JSON.stringify(updatedSystemOutline),
                dataType: 'json',
                success: function (data) {
                    // Handle success response if needed
                    console.log("SUCCESS: ", data);
                    console.log("contractINfo", contractInfo);
                    // Update the systemOutline in the contractInfo object
                    contractInfo.systems = contractInfo.systems.map(systemOutline => {
                        if (systemOutline.id === data.id) {
                            return data;
                        }
                        return systemOutline;
                    })
                    //reload Table
                    renderSystemOutlineTable(contractInfo.systems);
                    // Close the modal after updating
                    $('#editSystemOutlineModal').modal('hide');
                },
                error: function (xhr, status, error) {
                    $('#editSystemOutlineNameError').text('This system outline already exists');
                    $('#editOutlineName').addClass('is-invalid').removeClass('is-valid');
                }
            });
        }
    }


    /*===================================================
            delete deliverable to database
    ===================================================*/


    /*===================================================
         delete architecture to database
 ===================================================*/


    /*===================================================
            open edit deliverable modal
    ===================================================*/
    function openEditDeliverableModal(deliverableId) {
        $('#editDeliverableNameError').text('');
        const deliverable = contractInfo.deliverables.filter(deliverable => deliverable.id === deliverableId)[0];

        // Populate modal fields with deliverable data
        $('#editDeliverableId').val(deliverable.id);
        $('#editDeliverableName').val(deliverable.name).removeClass('is-valid').removeClass('is-invalid');
        // Show the edit deliverable modal
        $('#editDeliverableModal').modal('show');
    }

    /*===================================================
            open edit Client modal
    ===================================================*/
    let phoneInput;
    function openEditClientModal(clientId) {
        $('#editClientSameNameError').text("");
        $('#editClientNameError').text("");
        $('#editClientEmailError').text("");
        $('#editClientPhError').text("");
        const client = contractInfo.clients.filter(client => client.id === clientId)[0];
        // Populate modal fields with deliverable data
        $('#editClientId').val(client.id);
        $('#editClientName').val(client.name).removeClass('is-valid').removeClass('is-invalid');
        $('#editClientEmail').val(client.email).removeClass('is-valid').removeClass('is-invalid');
        $('#editClientPhoneNumber').val(client.phoneNumber).removeClass('is-valid').removeClass('is-invalid');
        $('.iti__flag-container').remove();
        // Initialize the intlTelInput with the detected country code
        const phoneInputField = document.querySelector("#editClientPhoneNumber");

        // Extract the country code from the client's phone number
        const countryCode = client.phoneNumber ? client.phoneNumber.substring(1, 3) : "";
        // Initialize the intlTelInput with the detected country code
        phoneInput = window.intlTelInput(phoneInputField, {
            utilsScript: "https://cdnjs.cloudflare.com/ajax/libs/intl-tel-input/17.0.8/js/utils.js",
            separateDialCode: true,
            autoHideDialCode: false,
            initialCountry: countryCode || 'us',
            geoIpLookup: function (callback) {
                // You can implement a geoIpLookup function here if needed
            },
        });
        // Show the edit deliverable modal
        $('#editClientModal').modal('show');
    }

    /*===================================================
           open edit Architecture modal
   ===================================================*/
    function openEditArchitectureModal(architectureId) {
        $('#editArchitectureSameNameError').text('');
        const architecture = contractInfo.architectures.filter(architecture => architecture.id === architectureId)[0];

        // Populate modal fields with deliverable data
        $('#editArchitectureId').val(architecture.id);
        $('#editArchitectureName').val(architecture.name).removeClass('is-valid').removeClass('is-invalid');
        $('#editArchitectureType').val(architecture.type).removeClass('is-valid').removeClass('is-invalid');
        // Show the edit deliverable modal
        $('#editArchitectureModal').modal('show');
    }

    /*===================================================
           open edit systemOutline modal
   ===================================================*/
    function openEditSystemOutlineModal(systemOutlineId) {
        $('#editSystemOutlineNameError').text('');
        console.log("systemOutlineId", systemOutlineId);
        const systemOutline = contractInfo.systems.filter(systemOutline => systemOutline.id === systemOutlineId)[0];
        // Populate modal fields with deliverable data
        $('#editOutlineId').val(systemOutline.id);
        $('#editOutlineName').val(systemOutline.name).removeClass('is-valid').removeClass('is-invalid');
        // Show the edit deliverable modal
        $('#editSystemOutlineModal').modal('show');
    }

    /*===================================================
        export deliverable functions to global scope
    ===================================================*/
    $.fn.addDeliverable = addDeliverable;
    $.fn.openEditDeliverableModal = openEditDeliverableModal;
    $.fn.updateDeliverable = updateDeliverable;
    $.fn.buildToggleDeliverable = buildToggleDeliverableBtn;


    /*===================================================
       export client functions to global scope
   ===================================================*/


    $.fn.addClient = addClient;
    $.fn.openEditClientModal = openEditClientModal;
    $.fn.updateClient = updateClient;
    // $.fn.toggleClientStatus= toggleClientStatus;
    $.fn.buildToggleUserBtn = buildToggleUserBtn;


    /*===================================================
      export architecture functions to global scope
  ===================================================*/


    $.fn.addArchitecture = addArchitecture;
    $.fn.openEditArchitectureModal = openEditArchitectureModal;
    $.fn.updateArchitecture = updateArchitecture;
    $.fn.buildToggleArchitectureBtn = buildToggleArchitectureBtn;

    /*===================================================
      export systemOutline functions to global scope
  ===================================================*/
    $.fn.addSystemOutline = addSystemOutline;
    $.fn.openEditSystemOutlineModal = openEditSystemOutlineModal;
    $.fn.updateSystemOutline = updateSystemOutline;
    $.fn.buildToggleSystemOutlineBtn = buildToggleSystemOutlineBtn;
    $.fn.toggleClientStatus = toggleClientStatus;
function toggleClientStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ?  'enable' : 'disable';
    $('#confirmationClientAction').text(actionText);

    $('#confirmationClientModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmClientButton').on('click', function (event) {
        // Prevent form submission and page refresh
        event.preventDefault();
        // Close the modal
        $('#confirmationClientModal').modal('hide');
        // Make the AJAX request
        $.ajax({
            url: `/client/status/`+id,
            type: 'PUT',
            success: function (data) {
                // Handle success response, update UI if necessary
                console.log('Client status updated successfully:', contractInfo);
                contractInfo.clients.find(client => client.id === data.id).status = data.status;
                renderClientTable(contractInfo.clients);
                // Assuming 'renderClientTable' updates the client table with new data
            },
            error: function (error) {
                // Handle error response
                console.error('Error updating client status:', error);
            }
        });
        $('#confirmClientButton').off('click');
    });
}

    $.fn.toggleDeliverableStatus = toggleDeliverableStatus;
function toggleDeliverableStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'enable' : 'disable';
    $('#confirmationDeliverableAction').text(actionText);

    $('#confirmationDeliverableModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmDeliverableButton').on('click', function (event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationDeliverableModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/deliverable/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function (data) {
                // Handle success response, update UI if necessary
                contractInfo.deliverables.find(deliverable => deliverable.id === data.id).status = data.status;
                renderDeliverableTable(contractInfo.deliverables);
            },
            error: function (error) {
                // Handle error response
                console.error('Error updating deliverable status:', error);
            }
        });
        $('#confirmDeliverableButton').off('click');
    });
}

$.fn.toggleArchitectureStatus = toggleArchitectureStatus;
function toggleArchitectureStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'enable' : 'disable';
    $('#confirmationArchitectureAction').text(actionText);

    $('#confirmationArchitectureModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmArchitectureButton').on('click', function (event) {
        // Prevent form submission and page refresh
        event.preventDefault();
        // Close the modal
        $('#confirmationArchitectureModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/architecture/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function (data) {
                // Handle success response, update UI if necessary
                console.log('Architecture status updated successfully:', data);
                contractInfo.architectures.find(architecture => architecture.id === data.id).status = data.status;
                renderArchitectureTable(contractInfo.architectures);
                // Assuming 'renderClientTable' updates the client table with new data
            },
            error: function (error) {
                // Handle error response
                console.error('Error updating architecture status:', error);
            }
        });
        $('#confirmArchitectureButton').off('click');
    });
}

$.fn.toggleSystemOutlineStatus = toggleSystemOutlineStatus;
function toggleSystemOutlineStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'enable' : 'disable';
    $('#confirmationSystemOutlineAction').text(actionText);

    $('#confirmationSystemOutlineModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmSystemOutlineButton').on('click', function (event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationSystemOutlineModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/system-outline/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function (data) {
                // Handle success response, update UI if necessary
                console.log('SystemOutline status updated successfully:', data);
                contractInfo.systems.find(system => system.id === data.id).status = data.status;
                renderSystemOutlineTable(contractInfo.systems);
            },
            error: function (error) {
                // Handle error response
                console.error('Error updating SystemOutline status:', error);
            }
        });
        $('#confirmSystemOutlineButton').off('click');
    });
}


/*===================================================
        build toggle button
  ====================================================*/
function buildToggleUserBtn(status, id) {
    $(`#toggleUserBtn${id}`).remove();
    const statusText = status ? 'Disable':'Enable';
    const btn = `<button id="toggleUserBtn${id}" type="button" onclick="$.fn.toggleClientStatus(${id}, ${!status})" class="btn btn-sm btn-secondary" style="width:65px">${statusText}</button>`;
    $(`#toggleBtnGp${id}`).append(btn)
    return btn;
}

function buildToggleDeliverableBtn(status, id) {
    $(`#toggleDeliverableBtn${id}`).remove();
    const statusText = status ? 'Disable':'Enable';
    const btn = `<button id="toggleDeliverableBtn${id}" type="button" onclick="$.fn.toggleDeliverableStatus(${id}, ${!status})" class="btn btn-sm btn-secondary" style="width:65px">${statusText}</button>`;
    $(`#toggleBtndeliverableGp${id}`).append(btn);
    return btn;
}

function buildToggleArchitectureBtn(status, id) {
    $(`#toggleArchitectureBtn${id}`).remove();
    const statusText = status ? 'Disable':'Enable';
    const btn = `<button id="toggleArchitectureBtn${id}" type="button" onclick="$.fn.toggleArchitectureStatus(${id}, ${!status})" class="btn btn-sm btn-secondary" style="width:65px">${statusText}</button>`;
    $(`#toggleBtnArchitectureGp${id}`).append(btn)
    return btn;
}

function buildToggleSystemOutlineBtn(status, id) {
    $(`#toggleSystemOutlineBtn${id}`).remove();
    const statusText = status ? 'Disable':'Enable';
    const btn = `<button id="toggleSystemOutlineBtn${id}" type="button" onclick="$.fn.toggleSystemOutlineStatus(${id}, ${!status})" class="btn btn-sm btn-secondary" style="width:65px">${statusText}</button>`;
    $(`#toggleBtnSystemOutlineGp${id}`).append(btn)
    return btn;
}


});
