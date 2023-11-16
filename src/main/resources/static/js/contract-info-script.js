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
                { data: 'name' },
                { data: 'email' },
                { data: 'phoneNumber' },
                {
                    data: 'status',
                    render: function (data, type, row) {

                        return `
                        <div id="toggleBtnGp" class="btn-group" role="group" aria-label="Client Status">
                           ${buildToggleUserBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditClientModal(${row.id})">Edit</button>
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
                { data: 'name' },

                {
                    data: 'status',
                    render: function (data, type, row) {

                        return `
                        <div id="toggleBtndeliverableGp" class="btn-group" role="group" aria-label="Deliverable Status">
                           ${buildToggleDeliverableBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditDeliverableModal(${row.id})">Edit</button>
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
                    { data: 'name' },
                    { data: 'type' },

                    {
                        data: 'status',
                        render: function (data, type, row) {

                            return `
                        <div id="toggleBtnArchitectureGp" class="btn-group" role="group" aria-label="Architecture Status">
                           ${buildToggleArchitectureBtn(data, row.id)}
                        </div>
                    `;
                        }
                    },
                    {
                        data: 'id',
                        render: function (data, type, row, meta) {
                            return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditArchitectureModal(${row.id})">Edit</button>
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
                { data: 'name' },

                {
                    data: 'status',
                    render: function (data, type, row) {

                        return `
                        <div id="toggleBtnSystemOutlineGp" class="btn-group" role="group" aria-label="SystemOutline Status">
                           ${buildToggleSystemOutlineBtn(data, row.id)}
                        </div>
                    `;
                    }
                },
                {
                    data: 'id',
                    render: function (data, type, row, meta) {
                        return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openEditSystemOutlineModal(${row.id})">Edit</button>
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
            url: "/pm/add-deliverable", // Replace with your server endpoint for adding deliverables
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
                alert('Error! Please try again.', error);
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
            phoneNumber: $('#clientPhoneNumber').val(),
        };

        $.ajax({
            type: "POST",
            url: "/pm/add-client", // Replace with your server endpoint for adding deliverables
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
                console.log("ERROR: ", xhr.responseText);
                alert('Error! Please try again.', error);
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
            url: "/pm/add-architecture", // Replace with your server endpoint for adding deliverables
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
                console.log("ERROR: ", xhr.responseText);
                alert('Error! Please try again.', error);
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
            url: "/pm/add-system-outline", // Replace with your server endpoint for adding deliverables
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
                alert('Error! Please try again.', error);
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

        // Prepare updated deliverable object
        let updatedDeliverable = {
            id: id,
            name: name
        };

        console.log("Updated Deliverable: ", updatedDeliverable)
        // Make a PUT request to update the deliverable data
        $.ajax({
            type: "PUT",
            url: "/pm/deliverable/update", // Endpoint to update deliverable data by ID
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
                $('#editDeliverableModal').modal('hide');
            }
        });
    }

    /*===================================================
           update client to database
    ===================================================*/
    function updateClient() {
        // Get updated deliverable information from modal fields
        let id = $('#editClientId').val();
        let name = $('#editClientName').val();
        let email = $('#editClientEmail').val();
        let phoneNumber = $('#editClientPhoneNumber').val();

        // Prepare updated deliverable object
        let updatedClient = {
            id: id,
            name: name,
            email: email,
            phoneNumber: phoneNumber
        };

        console.log("Updated Client: ", updatedClient)
        // Make a PUT request to update the deliverable data
        $.ajax({
            type: "PUT",
            url: "/pm/client/update", // Endpoint to update deliverable data by ID
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
                console.log("ERROR: ", xhr.responseText);
                // Optionally, close the modal on error if desired
                $('#editClientModal').modal('hide');
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
            url: "/pm/architecture/update", // Endpoint to update deliverable data by ID
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
                // Optionally, close the modal on error if desired
                $('#editArchitectureModal').modal('hide');
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

        // Prepare updated deliverable object
        let updatedSystemOutline = {
            id: id,
            name: name,

        };

        console.log("Updated SystemOutline: ", updatedSystemOutline)
        // Make a PUT request to update the deliverable data
        $.ajax({
            type: "PUT",
            url: "/pm/system-outline/update", // Endpoint to update deliverable data by ID
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
                console.log("ERROR: ", xhr.responseText);
                // Optionally, close the modal on error if desired
                $('#editSystemOutlineModal').modal('hide');
            }
        });
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

        const deliverable = contractInfo.deliverables.filter(deliverable => deliverable.id === deliverableId)[0];

        // Populate modal fields with deliverable data
        $('#editDeliverableId').val(deliverable.id);
        $('#editDeliverableName').val(deliverable.name);
        // Show the edit deliverable modal
        $('#editDeliverableModal').modal('show');
    }

    /*===================================================
            open edit Client modal
    ===================================================*/
   function openEditClientModal(clientId) {

        const client = contractInfo.clients.filter(client => client.id === clientId)[0];

        // Populate modal fields with deliverable data
        $('#editClientId').val(client.id);
        $('#editClientName').val(client.name);
        $('#editClientEmail').val(client.email);
        $('#editClientPhoneNumber').val(client.phoneNumber);

        // Show the edit deliverable modal
        $('#editClientModal').modal('show');
    }




    /*===================================================
           open edit Architecture modal
   ===================================================*/
    function openEditArchitectureModal(architectureId) {

        const architecture = contractInfo.architectures.filter(architecture => architecture.id === architectureId)[0];

        // Populate modal fields with deliverable data
        $('#editArchitectureId').val(architecture.id);
        $('#editArchitectureName').val(architecture.name);
        $('#editArchitectureType').val(architecture.type);
        // Show the edit deliverable modal
        $('#editArchitectureModal').modal('show');
    }

    /*===================================================
           open edit systemOutline modal
   ===================================================*/
    function openEditSystemOutlineModal(systemOutlineId) {
        console.log("systemOutlineId", systemOutlineId);
        const systemOutline = contractInfo.systems.filter(systemOutline => systemOutline.id === systemOutlineId)[0];
        // Populate modal fields with deliverable data
        $('#editOutlineId').val(systemOutline.id);
        $('#editOutlineName').val(systemOutline.name);
        // Show the edit deliverable modal
        $('#editSystemOutlineModal').modal('show');
    }

    /*===================================================
        export deliverable functions to global scope
    ===================================================*/
    $.fn.addDeliverable = addDeliverable;
    $.fn.openEditDeliverableModal = openEditDeliverableModal;
    $.fn.updateDeliverable = updateDeliverable;
    $.fn.buildToggleDeliverable= buildToggleDeliverableBtn;


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


    $.fn.addSystemOutline= addSystemOutline;
    $.fn.openEditSystemOutlineModal = openEditSystemOutlineModal;
    $.fn.updateSystemOutline = updateSystemOutline;
    $.fn.buildToggleSystemOutlineBtn = buildToggleSystemOutlineBtn;


});


function toggleClientStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'disable' : 'enable';
    $('#confirmationClientAction').text(actionText);

    $('#confirmationClientModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmClientButton').on('click', function(event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationClientModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/pm/client/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function(contractInfo) {
                // Handle success response, update UI if necessary
                console.log('Client status updated successfully:', contractInfo);
                buildToggleUserBtn(contractInfo.status, contractInfo.id);
                // Assuming 'renderClientTable' updates the client table with new data
            },
            error: function(error) {
                // Handle error response
                console.error('Error updating client status:', error);
            }
        });
        $('#confirmClientButton').off('click');
    });
}


function toggleDeliverableStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'disable' : 'enable';
    $('#confirmationDeliverableAction').text(actionText);

    $('#confirmationDeliverableModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmDeliverableButton').on('click', function(event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationDeliverableModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/pm/deliverable/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function(contractInfo) {
                // Handle success response, update UI if necessary
                console.log('Deliverable status updated successfully:', contractInfo);
                buildToggleDeliverableBtn(contractInfo.status, contractInfo.id);
                // Assuming 'renderClientTable' updates the client table with new data
            },
            error: function(error) {
                // Handle error response
                console.error('Error updating deliverable status:', error);
            }
        });
        $('#confirmDeliverableButton').off('click');
    });
}

function toggleArchitectureStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'disable' : 'enable';
    $('#confirmationArchitectureAction').text(actionText);

    $('#confirmationArchitectureModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmArchitectureButton').on('click', function(event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationArchitectureModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/pm/architecture/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function(contractInfo) {
                // Handle success response, update UI if necessary
                console.log('Architecture status updated successfully:', contractInfo);
                buildToggleArchitectureBtn(contractInfo.status, contractInfo.id);
                // Assuming 'renderClientTable' updates the client table with new data
            },
            error: function(error) {
                // Handle error response
                console.error('Error updating architecture status:', error);
            }
        });
        $('#confirmArchitectureButton').off('click');
    });
}



function toggleSystemOutlineStatus(id, newStatus) {
    // Show Bootstrap modal for confirmation
    const actionText = newStatus ? 'disable' : 'enable';
    $('#confirmationSystemOutlineAction').text(actionText);

    $('#confirmationSystemOutlineModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmSystemOutlineButton').on('click', function(event) {
        // Prevent form submission and page refresh
        event.preventDefault();

        // Close the modal
        $('#confirmationSystemOutlineModal').modal('hide');

        // Make the AJAX request
        $.ajax({
            url: `/pm/system-outline/status/${id}?newStatus=${newStatus}`,
            type: 'PUT',
            success: function(contractInfo) {
                // Handle success response, update UI if necessary
                console.log('SystemOutline status updated successfully:', contractInfo);
                buildToggleSystemOutlineBtn(contractInfo.status, contractInfo.id);
                // Assuming 'renderClientTable' updates the client table with new data
            },
            error: function(error) {
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
function buildToggleUserBtn(status,id){
    $('#toggleUserBtn').remove();
    const statusText = status ? 'Disabled' : 'Active';
    const statusClass = status ? 'secondary' : 'success';
    const btn = `<button id="toggleUserBtn" type="button" onclick="toggleClientStatus(${id}, ${!status})" class="btn btn-sm btn-${statusClass}">${statusText}</button>`;
    $('#toggleBtnGp').append(btn)
    return btn;
}
function buildToggleDeliverableBtn(status,id){
    $('#toggleDeliverableBtn').remove();
    const statusText = status ? 'Disabled' : 'Active';
    const statusClass = status ? 'secondary' : 'success';
    const btn = `<button id="toggleDeliverableBtn" type="button" onclick="toggleDeliverableStatus(${id}, ${!status})" class="btn btn-sm btn-${statusClass}">${statusText}</button>`;
    $('#toggleBtndeliverableGp').append(btn)
    return btn;
}
function buildToggleArchitectureBtn(status,id){
    $('#toggleArchitectureBtn').remove();
    const statusText = status ? 'Disabled' : 'Active';
    const statusClass = status ? 'secondary' : 'success';
    const btn = `<button id="toggleArchitectureBtn" type="button" onclick="toggleArchitectureStatus(${id}, ${!status})" class="btn btn-sm btn-${statusClass}">${statusText}</button>`;
    $('#toggleBtnArchitectureGp').append(btn)
    return btn;
}
function buildToggleSystemOutlineBtn(status,id){
    $('#toggleSystemOutlineBtn').remove();
    const statusText = status ? 'Disabled' : 'Active';
    const statusClass = status ? 'secondary' : 'success';
    const btn = `<button id="toggleSystemOutlineBtn" type="button" onclick="toggleSystemOutlineStatus(${id}, ${!status})" class="btn btn-sm btn-${statusClass}">${statusText}</button>`;
    $('#toggleBtnSystemOutlineGp').append(btn)
    return btn;
}

