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
        //check if deliverable table is already initialized, if so, refresh the table with new data
        if ($.fn.DataTable.isDataTable('#client')) {
            $('#client').DataTable().destroy();
        }
        console.log('contractInfo', contractInfo);
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

        //check if deliverable table is already initialized, if so, refresh the table with new data
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

        //check if deliverable table is already initialized, if so, refresh the table with new data
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

        //check if deliverable table is already initialized, if so, refresh the table with new data
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
                $("#addClientModal").modal('hide');

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

    function updateDeliverableStatus(deliverableId, isActive) {
        // Set the deliverableId to a data attribute of the confirmation button
        $("#confirmDeleteDeliverableButton").data("deliverable-id", deliverableId);

        // Set the isActive flag to a data attribute of the confirmation button
        $("#confirmDeleteDeliverableButton").data("is-active", isActive);

        // Open the confirmation modal
        $("#confirmDeleteDeliverableModal").modal("show");
    }


// When the user confirms deletion in the modal, proceed with the deletion
    $("#confirmDeleteDeliverableButton").click(function() {
        // Disable the delete button to prevent multiple clicks
        $(this).prop("disabled", true);

        var deliverableId = $(this).data("deliverable-id");
        var isActive = $(this).data("is-active");

        var url = isActive ? `/pm/deliverable/active/${deliverableId}` : `/pm/deliverable/disable/${deliverableId}`;

        $.ajax({
            type: "POST",
            url: url, // Endpoint to update deliverable status by ID
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                // Update the deliverables in the contractInfo object
                contractInfo.deliverables = contractInfo.deliverables.filter(deliverable => deliverable.id !== data.id);
                contractInfo.deliverables.push(data); // Add the updated deliverable

                // Reload Table
                renderDeliverableTable(contractInfo.deliverables);
            },
            error: function () {
                // Handle error if necessary
            },
            complete: function() {
                // Enable the delete button after the request is completed (success or error)
                $("#confirmDeleteDeliverableButton").prop("disabled", false);
            }
        });

        // Close the confirmation modal
        $("#confirmDeleteDeliverableModal").modal("hide");
    });




    // Function to initiate action confirmation for a client
    function confirmClientAction(clientId) {
        $("#activateClientButton").data("client-id", clientId);
        $("#disableClientButton").data("client-id", clientId);
        $("#confirmClientActionModal").modal("show");
    }


// Event handler for activating client button click
    $("#activateClientButton").click(function() {
        var clientId = $(this).data("client-id");
        // Perform logic to activate client using AJAX request
        $.ajax({
            type: "POST",
            url: `/pm/client/activate/${clientId}`, // Endpoint to activate client data by ID
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                console.log("Client activated successfully!");
                // Perform any additional UI updates if needed
            },
            error: function () {
                console.error("Error activating client.");
            }
        });

        // Close the confirmation modal
        $("#confirmClientActionModal").modal("hide");
    });

// Event handler for disabling client button click
    $("#disableClientButton").click(function() {
        var clientId = $(this).data("client-id");
        // Perform logic to disable client using AJAX request
        $.ajax({
            type: "POST",
            url: `/pm/client/disable/${clientId}`, // Endpoint to disable client data by ID
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                console.log("Client disabled successfully!");
                // Perform any additional UI updates if needed
            },
            error: function () {
                console.error("Error disabling client.");
            }
        });

        // Close the confirmation modal
        $("#confirmClientActionModal").modal("hide");
    });
    /*===================================================
         delete architecture to database
 ===================================================*/
    // Function to initiate delete confirmation
    function deleteArchitecture(architectureId) {
        $("#confirmDeleteArchitectureButton").data("architecture-id", architectureId);
        $("#confirmDeleteArchitectureModal").modal("show");
    }
    $("#confirmDeleteArchitectureButton").click(function() {
        var architectureId = $(this).data("architecture-id");
        $.ajax({
            type: "DELETE",
            url: `/pm/architecture/delete/${architectureId}`, // Endpoint to delete architecture data by ID
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
               contractInfo.architectures = contractInfo.architectures.filter(architecture => architecture.id !== data);
                renderArchitectureTable(contractInfo.architectures);
            },
            error: function () {
               }
        });
        $("#confirmDeleteArchitectureModal").modal("hide");
    });


    /*===================================================
         delete systemOutline to database
     ===================================================*/
    // Function to initiate delete confirmation
    function deleteSystemOutline(systemOutlineId) {
        $("#confirmDeleteButton").data("system-outline-id", systemOutlineId);
        // Open the confirmation modal
        $("#confirmDeleteModal").modal("show");
    }

// When user confirms deletion, perform the deletion and update UI
    $("#confirmDeleteButton").click(function() {
        var systemOutlineId = $(this).data("system-outline-id");
        $.ajax({
            type: "DELETE",
            url: `/pm/system-outline/delete/${systemOutlineId}`, // Endpoint to delete system outline by ID
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                // Update the system outlines in the contractInfo object
                contractInfo.systems = contractInfo.systems.filter(systemOutline => systemOutline.id !== data);

                // Reload the table
                renderSystemOutlineTable(contractInfo.systems);
            },
            error: function () {
                // Handle error if necessary
            }
        });

        // Close the confirmation modal
        $("#confirmDeleteModal").modal("hide");
    });

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
    $.fn.updateDeliverableStatus = updateDeliverableStatus;

    /*===================================================
       export client functions to global scope
   ===================================================*/


    $.fn.addClient = addClient;
    $.fn.openEditClientModal = openEditClientModal;
    $.fn.updateClient = updateClient;



    /*===================================================
      export architecture functions to global scope
  ===================================================*/


    $.fn.addArchitecture = addArchitecture;
    $.fn.openEditArchitectureModal = openEditArchitectureModal;
    $.fn.updateArchitecture = updateArchitecture;
    $.fn.deleteArchitecture = deleteArchitecture;


    /*===================================================
      export systemOutline functions to global scope
  ===================================================*/


    $.fn.addSystemOutline= addSystemOutline;
    $.fn.openEditSystemOutlineModal = openEditSystemOutlineModal;
    $.fn.updateSystemOutline = updateSystemOutline;
    $.fn.deleteSystemOutline = deleteSystemOutline;

});
