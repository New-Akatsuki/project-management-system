function updateSystemOutlineUI(systemOutline) {
    const systemOutlineTableBody = $('#systemOutlineTableBody');

    const newRow = `
        <tr>
          <td>${systemOutline.id}</td>
          <td>${systemOutline.name}</td>
          <td>
           <button class="btn btn-sm btn-primary mx-2" onclick="openEditSystemOutlineModal('${systemOutline.id}', '${systemOutline.name}')">Edit</button>
          </td>
        </tr>
      `;

    systemOutlineTableBody.append(newRow);
    console.log("System Outline Object: " + JSON.stringify(systemOutline));
}

function addSystemOutline() {
    let newSystemOutline = {
        id: null,
        name: $('#outlineName').val()
    };

    $.ajax({
        type: "POST",
        url: "/pm/add-system-outline", // Replace with your server endpoint for adding system outlines
        contentType: "application/json",
        data: JSON.stringify(newSystemOutline),
        dataType: 'json',
        success: function (data) {

            $("#addSystemOutlineModal").modal('hide');
            console.log("SUCCESS: ", data);
            updateSystemOutlineUI(data);
        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
        }
    });
}

$.ajax({
    type: "GET",
    url: "/pm/system-outlines",
    success: function (data) {

        console.log("SUCCESS : ", data);
        $("#addSystemOutlineModal").modal('hide');
    },
    error: function (e) {
        console.log("ERROR : ", e);

    }
})

function updateDeliverableUI(deliverable) {
    const deliverableTableBody = $('#deliverableTableBody');

    const newRow = `
        <tr>
          <td>${deliverable.id}</td>
          <td>${deliverable.name}</td>
          <td>
    <button class="btn btn-sm btn-primary mx-2" onclick="openEditDeliverableModal('${deliverable.id}', '${deliverable.name}')">Edit</button>
          </td>
        </tr>
      `;

    deliverableTableBody.append(newRow);
    console.log("Deliverable Object: " + JSON.stringify(deliverable));
}

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
            console.log("SUCCESS: ", data);
            updateDeliverableUI(data); // Use the data returned from the server response
        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
        }
    });
}

$.ajax({
    type: "GET",
    url: "/pm/deliverables",
    success: function (data) {

        console.log("SUCCESS : ", data);
        $("#addDeliverablesModal").modal('hide');
    },
    error: function (e) {
        console.log("ERROR : ", e);

    }
})

// Function to update the UI with client data
function updateClientUI(client) {
    const clientTableBody = $('#clientTableBody');
    const newRow = `
    <tr>
      <td>${client.id}</td>
      <td>${client.name}</td>
      <td>${client.email}</td>
      <td>${client.phoneNumber}</td>
      <td>
           <button class="btn btn-sm btn-primary mx-2" onclick="openEditClientModal(${client.id}, '${client.name}', '${client.email}', '${client.phoneNumber}')">Edit</button>
      </td>
    </tr>
  `;
    clientTableBody.append(newRow);
    console.log("Client Object: " + JSON.stringify(client));
}

function addClient() {
    // Function to add a new client using AJAX
    let newClient = {
        id: null,
        name: $('#clientName').val(),
        email: $('#clientEmail').val(),
        phoneNumber: $('#clientPhoneNumber').val()
    };

    $.ajax({
        type: "POST",
        url: "/pm/add-client", // Replace with your server endpoint for adding clients
        contentType: "application/json",
        data: JSON.stringify(newClient),
        dataType: 'json',
        success: function (data) {
            $("#addClientModal").modal('hide');
            console.log("SUCCESS: ", data);
            updateClientUI(data);
        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
        }
    });
}

// Fetch existing clients when the page is loaded
$.ajax({
    type: "GET",
    url: "/pm/clients", // Replace with your server endpoint for fetching clients
    success: function (data) {
        console.log("SUCCESS:", data);
        // Update the UI with existing clients if needed
        data.forEach(function (client) {
            updateClientUI(client);
        });
    },
    error: function (e) {
        console.log("ERROR:", e);
    }
});


<!--Add Architecture Outline-->
// Function to update the UI with architecture data
//     function updateArchitectureUI(architecture) {
//     const architectureTableBody = $('#architectureTableBody');
//     const newRow = `
//     <tr>
//       <td>${architecture.id}</td>
//       <td>${architecture.name}</td>
//       <td>${architecture.type}</td>
//        <td>
//        <button class="btn btn-sm btn-primary mx-2" onclick="openEditArchitectureModal('${architecture.id}', '${architecture.name}', '${architecture.type}')">Edit</button>
//        </td>
//     </tr>
//   `;
//     architectureTableBody.append(newRow);
//     console.log("Architecture Object: " + JSON.stringify(architecture));
// }

// Function to add a new architecture
function addArchitecture() {
    let newArchitecture = {
        id: null,
        name: $('#architectureName').val(),
        type: $('#architectureType').val()
    };

    // AJAX POST request to add new architecture
    $.ajax({
        type: 'POST',
        url: '/pm/add-architecture',
        contentType: 'application/json',
        data: JSON.stringify(newArchitecture),
        dataType: 'json',
        success: function (data) {
            $("#addArchitectureModal").modal('hide');
            console.log('SUCCESS:', data);
            updateArchitectureUI(data); // Call the updateArchitectureUI function to update the UI with new data
        },
        error: function (xhr, status, error) {
            console.log('ERROR:', xhr.responseText);
            alert('Error! Please try again.');
        }
    });
}

// AJAX GET request to fetch architecture data when the page loads
$.ajax({
    type: "GET",
    url: "/pm/architectures",
    success: function (data) {
        console.log("SUCCESS:", data);
        data.forEach(function (architecture) {
            updateArchitectureUI(architecture);
        });
    },
    error: function (e) {
        console.log("ERROR:", e);
    }
});

//edit client
function openEditClientModal(clientId) {
    $.ajax({
        type: "GET",
        url: "/pm/client/" + clientId, // Endpoint to fetch client data by ID
        success: function (client) {
            // Populate modal fields with client data
            $('#editClientId').val(client.id);
            $('#editClientName').val(client.name);
            $('#editClientEmail').val(client.email);
            $('#editClientPhoneNumber').val(client.phoneNumber);
            // Show the edit client modal
            $('#editClientModal').modal('show');
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}

// Function to update the client
// Function to update the client
function updateClient() {
    // Get updated client information from modal fields
    let id = $('#editClientId').val();
    let name = $('#editClientName').val();
    let email = $('#editClientEmail').val();
    let phoneNumber = $('#editClientPhoneNumber').val();

    // Prepare updated client object
    let updatedClient = {
        id: id,
        name: name,
        email: email,
        phoneNumber: phoneNumber
    };

    console.log("Updated Client: ", updatedClient)

    // Make a PUT request to update the client data
    $.ajax({
        type: "PUT",
        url: "/pm/client/update/" + id, // Endpoint to update client data by ID
        contentType: "application/json",
        data: JSON.stringify(updatedClient),
        dataType: 'json',
        success: function (data) {
            // Handle success response if needed
            console.log("SUCCESS: ", data);
            // Optionally, update the client data in your UI
        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
        }
    });
    // Close the modal after updating
    $('#editClientModal').modal('hide');
}

//edit deliverable
//     function openEditDeliverableModal(deliverableId) {
//     $.ajax({
//         type: "GET",
//         url: "/pm/deliverable/" + deliverableId, // Endpoint to fetch deliverable data by ID
//         success: function(deliverable) {
//             // Populate modal fields with deliverable data
//             $('#editDeliverableId').val(deliverable.id);
//             $('#editDeliverableName').val(deliverable.name);
//             // Show the edit deliverable modal
//             $('#editDeliverableModal').modal('show');
//         },
//         error: function(e) {
//             console.log("ERROR:", e);
//         }
//     });
// }

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
        url: "/pm/deliverable/update/" + id, // Endpoint to update deliverable data by ID
        contentType: "application/json",
        data: JSON.stringify(updatedDeliverable),
        dataType: 'json',
        success: function (data) {
            // Handle success response if needed
            console.log("SUCCESS: ", data);

            console.log("contractINfo", contractInfo)
            data.deliverables.filter(deliverable => deliverable.id === id)[0] = data;
            renderDeliverableTable(data.deliverables);

            // Close the modal after updating
            $('#editDeliverableModal').modal('hide');

        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
            // Optionally, close the modal on error if desired
            $('#editDeliverableModal').modal('hide');
        }
    });
}


<!--Edit Architecture-->

function openEditArchitectureModal(architectureId) {
    $.ajax({
        type: "GET",
        url: "/pm/architecture/" + architectureId, // Endpoint to fetch architecture data by ID
        success: function (architecture) {
            // Populate modal fields with architecture data
            $('#editArchitectureId').val(architecture.id);
            $('#editArchitectureName').val(architecture.name);
            $('#editArchitectureType').val(architecture.type);
            // Show the edit architecture modal
            $('#editArchitectureModal').modal('show');
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}

function updateArchitecture() {
    // Get updated architecture information from modal fields
    let id = $('#editArchitectureId').val();
    let name = $('#editArchitectureName').val();
    let type = $('#editArchitectureType').val();

    // Prepare updated architecture object
    let updatedArchitecture = {
        id: id,
        name: name,
        type: type
    };

    // Make a PUT request to update the architecture data
    $.ajax({
        type: "PUT",
        url: "/pm/architecture/update/" + id, // Endpoint to update architecture data by ID
        contentType: "application/json",
        data: JSON.stringify(updatedArchitecture),
        dataType: 'json',
        success: function (data) {
            // Handle success response if needed
            console.log("SUCCESS: ", data);
            // Optionally, update the architecture data in your UI
            updateArchitectureUI(data); // Update the UI with the updated architecture data
            // Close the modal after updating
            $('#editArchitectureModal').modal('hide');
        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
            // Optionally, close the modal on error if desired
            $('#editArchitectureModal').modal('hide');
        }
    });
}


<!--Edit System Outline-->
function openEditSystemOutlineModal(systemOutlineId) {
    $.ajax({
        type: "GET",
        url: "/pm/system-outline/" + systemOutlineId, // Endpoint to fetch system outline data by ID
        success: function (systemOutline) {
            // Populate modal fields with system outline data
            $('#editOutlineId').val(systemOutline.id);
            $('#editOutlineName').val(systemOutline.name);
            // Show the edit system outline modal
            $('#editSystemOutlineModal').modal('show');
        },
        error: function (e) {
            console.log("ERROR:", e);
        }
    });
}

function updateSystemOutline() {
    // Get updated system outline information from modal fields
    let id = $('#editOutlineId').val();
    let name = $('#editOutlineName').val();

    // Prepare updated system outline object
    let updatedSystemOutline = {
        id: id,
        name: name
    };

    // Make a PUT request to update the system outline data
    $.ajax({
        type: "PUT",
        url: "/pm/system-outline/update/" + id, // Endpoint to update system outline data by ID
        contentType: "application/json",
        data: JSON.stringify(updatedSystemOutline),
        dataType: 'json',
        success: function (data) {
            // Handle success response if needed
            console.log("SUCCESS: ", data);
            // Optionally, update the system outline data in your UI
            updateSystemOutlineUI(data); // Update the UI with the updated system outline data
            // Close the modal after updating
            $('#editSystemOutlineModal').modal('hide');
        },
        error: function (xhr, status, error) {
            console.log("ERROR: ", xhr.responseText);
            alert('Error! Please try again.', error);
            // Optionally, close the modal on error if desired
            $('#editSystemOutlineModal').modal('hide');
        }
    });
}
