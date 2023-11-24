<!--   For Department Table-->
let departmentList =[];
$(document).ready(function () {

    //Make an AJAX request to fetch data from the Rest API point
    $.ajax({
        url: '/departments',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            console.log('All department list: ', data);
            departmentList = data;
            //Render the data into the DataTable
            renderDepartmentListTable(data);
        },
        error: function (xhr, status, error) {
            console.log('Error: ', error);
        }
    });

    checkInputValidation('name','Department name',null,null);
    checkInputValidation('editDepartmentName','Department name',null,null);
    formValidate('addDepartmentForm',addDepartment);
    formValidate('editDepartmentForm',editDepartment);
});

function renderDepartmentListTable(items) {
    if ($.fn.DataTable.isDataTable('#department-table')) {
        $('#department-table').DataTable().destroy();
    }
    return $('#department-table').DataTable({
        data: items,
        columns: [
            {data: 'id'},
            {data: 'name'},
            {
                data: 'active',
                render: function (data, type, row) {
                    return `
                       <span class="badge rounded-pill text-bg-${data?'success':'danger'}">${data?'opened':'closed'}</span>
                    `;
                }
            },
            {
                data: 'active',
                render: function (data, type, row) {
                    return `
                        <button class="btn btn-sm btn-primary mx-2" onclick="displayEditDepartmentModal(${row.id})">Edit</button>
                        <button class="btn btn-sm btn-${data?'danger':'success'} mx-2" onclick="toggleDepartment(${row.id})">${data?'Close':'Open'}</button>
                    `;
                }
            }
        ]
    });
}
function toggleDepartment(departmentId) {
   //Find the department in departmentList
    const department = departmentList.find(department => department.id === departmentId);
    if(!department){
        console.log("Department not found for ID:", departmentId);
        return;
    }
    // Show Bootstrap modal for confirmation
    const actionText = department.active ? 'close' : 'open';
    $('#confirmationDepartmentAction').text(actionText);

    $('#confirmationDepartmentModal').modal('show');

    // Set event listener for modal confirm button
    $('#confirmDepartmentButton').off().on('click', function () {
        // Close the modal
        $('#confirmationDepartmentModal').modal('hide');

        // Determine the new active status (toggle it)
        const newActiveStatus = !department.active;

        //Make a PUT request to update the department data
    $.ajax({
        url: `/department-toggle/${departmentId}`,
        method: 'PUT',
        data: JSON.stringify({ active: newActiveStatus }),
        dataType: 'text',
        contentType: 'application/json', // Corrected contentType value
        success: function (data) {
            console.log('Department toggled successfully:', data);
            //Update the departmentList with the new active status value
            department.active = !department.active;
            //Update the button text based on the new active status
            const buttonText = department.active ? 'Close' : 'Open';
            $(`#department-table button[data-id=${departmentId}]`).text(buttonText);
            location.reload(); // Reload the page
        },
        error: function (xhr, error) {
            console.log('Error toggling department:', error);
        }
    });
        $('#confirmDepartmentButton').off('click');
    });
}

// For add Department
function addDepartment() {
    let addDepartment= {
        id:$("#id").val(),
        name:$("#name").val(),};
    console.log(addDepartment);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/department-create",
        data: JSON.stringify(addDepartment),
        dataType: 'json',
        success: function (data) {
            // departmentList.push(data)
            console.log(departmentList);
            location.reload();
            // renderDepartmentListTable(departmentList);
        },
        error: function (error) {
            console.log('Error adding new department:', error);
        }
    });
}
//For Display Edit Department Modal
function displayEditDepartmentModal(id) {
    const department = departmentList.find(department => department.id === id);
    console.log("Department Edit ", department);
    // Check if department is found
    if (department) {
        //populate modal fields with department data
        $('#editId').val(department.id);
        $('#editDepartmentName').val(department.name);
        //show the modal
        $("#departmentEditModal").modal('show');
    } else {
        console.log("Department not found for ID:", id);
    }
}

function editDepartment() {
    let id = $('#editId').val();
    let name = $('#editDepartmentName').val();
    let updatedDepartment = {
        id: id,
        name: name,
    };
    console.log("updatedDepartment", updatedDepartment);
    // Make a PUT request to update the department data
    $.ajax({
        url: `/department-edit/${id}`,
        method: 'PUT',
        data: JSON.stringify(updatedDepartment),
        dataType: 'json',
        contentType: 'application/json', // Corrected contentType value
        success: function (data) {
            console.log('Data received:', data);
            location.reload();
        },
        error: function (xhr, error) {
            console.log(xhr.responseText)
            console.log('Error fetching data:', error);
        }
    });
}



