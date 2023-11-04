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
                                <button class="btn btn-sm btn-primary mx-2" onclick="$.fn.openCli(${row.id})">Edit</button>
                                <button class="btn btn-sm btn-danger mx-2" onclick="$.fn.deleCli(${row.id})">Delete</button>
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
                                <button class="btn btn-sm btn-danger mx-2" onclick="$.fn.deleteDeliverable(${row.id})">Delete</button>
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
            delete deliverable to database
    ===================================================*/
    function deleteDeliverable(deliverableId) {
        // Make a DELETE request to delete the deliverable data
        $.ajax({
            type: "DELETE",
            url: `/pm/deliverable/delete/${deliverableId}`, // Endpoint to delete deliverable data by ID
            contentType: "application/json",
            dataType: 'json',
            success: function (data) {
                // Update the deliverable in the contractInfo object
                contractInfo.deliverables = contractInfo.deliverables.filter(deliverable => deliverable.id !== data);

                //reload Table
                renderDeliverableTable(contractInfo.deliverables);

            },
            error: function () {
            }
        });
    }

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
        export deliverable functions to global scope
    ===================================================*/
    $.fn.addDeliverable = addDeliverable;
    $.fn.openEditDeliverableModal = openEditDeliverableModal;
    $.fn.updateDeliverable = updateDeliverable;
    $.fn.deleteDeliverable = deleteDeliverable;

    /*===================================================
       export client functions to global scope
   ===================================================*/

});
