function taskBoard(options) {
    let settings = $.extend(
        {
            tasks: [],
        },
        options
    );

    function init() {
        build_tasks()
        buildBadge()
    }


    function buildBadge() {
        const badge = document.getElementById('overdueBadge');
        const overDueCount = settings.tasks.filter(item => !item.status && new Date(item.end_date) < new Date()).length;
        const pendingCount = settings.tasks.filter(item => !item.status).length;
        const completeCount = settings.tasks.filter(item => item.status).length;
        if (overDueCount > 0) {
            badge.classList.remove('d-none');
            badge.innerText = overDueCount;
        } else {
            badge.classList.add('d-none');
            $('#overdueContainer').append('<div class="d-flex align-items-center justify-content-center" style="height: 65vh"><span class="fs-4 text-secondary">No overdue task</span></div>')
        }
        if(pendingCount===0){
            $('#pendingContainer').append('<div class="d-flex align-items-center justify-content-center" style="height: 65vh"><span class="fs-4 text-secondary">No pending task</span></div>')
        }
        if(completeCount===0){
            $('#completedContainer').append('<div class="d-flex align-items-center justify-content-center" style="height: 65vh"><span class="fs-4 text-secondary">No complete task</span></div>')
        }
    }


    function buildCompleteTaskModal(taskId) {
        // Dynamic creation of modal with form
        let today = new Date();
        // Get the month, day, and year
        let month = (today.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
        let day = today.getDate().toString().padStart(2, '0');
        let year = today.getFullYear();

        // Format the date as mm-dd-yyyy
        let formattedDate = `${year}-${month}-${day}`;

        //check completeTaskModal if already exist, remove
        $('#completeTaskModal').remove();

        const item = settings.tasks.filter(data => data.id === taskId)[0];

        const completeTaskModal = `
              <div class="modal fade" id="completeTaskModal" tabindex="-1" aria-labelledby="completeTaskModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                  <form id="complete-task-form" autocomplete="off">
                    <div class="modal-content">
                        <div class="modal-header border-0">
                            <h1 class="modal-title fs-5" id="completeTaskModalLabel">Make Complete Task</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row" style="padding:10px">
                              <div class="col-6">
                                <label for="planHour">Plan Hour :</label>
                                <input type="number" id="planHour" class="inputmodalbox"
                                placeholder="Enter plan hour" value="${item.plan_hours}" disabled>
                              </div>
                              <div class="col-6">
                                <label for="planHour">Due Date :</label>
                                <input type="text" class="dateInputBox" placeholder="Select due date" value="${item.end_date}" disabled/>
                              </div>
                            </div>

                            <div class="row" style="padding:10px">
                               <div class="col-6">
                                <label for="actualHour">Actual Hour :</label>
                                <input type="number" id="actualHour" class="inputmodalbox" min="0" autocomplete="off" step="0.1"
                                placeholder="Enter actual hour" value="${item.actual_hours !== null ? item.actual_hours : '0.0'}">
                              </div>
                              <div class="col-6">
                                <label for="actualHour">Actual Date :</label>
                                <input type="text" id="${taskId}-actual-date" placeholder="Select actual date" value="${formattedDate}"/>
                              </div>
                            </div>
                        </div>
                        <div class="modal-footer border-0">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                            <button type="submit" id="completeTaskButton" class="btn btn-primary">Confirm</button>
                        </div>
                    </div>
                  </form>
                </div>
            </div>`;
        $('body').append(completeTaskModal);

        const updateStatusInfo = () => {
            let task = item;
            task.actual_hours = parseFloat($('#actualHour').val());
            task.actual_due_date = $(`#${taskId}-actual-date`).val();
            task.status = task.actual_hours > 0;
            task.assignees = item.assignees.map(val => parseInt(val.id, 10));
            updateTask(task);
            $("#completeTaskModal").modal("hide");
        }
        setupDatePicker(`#${taskId}-actual-date`);
        formValidate('complete-task-form', updateStatusInfo)
    }

    function updateTask(task) {

        $.ajax({
            url: '/update-task',
            method: 'PUT',
            data: JSON.stringify(task),
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            success: function (data) {
                //update settings.task data
                settings.tasks.filter(val => val.id === data.id)[0] = data;
                settings.tasks.filter(val => val.id === data.id)[0].assignees = data.assignees;
                $('#taskViewModal').offcanvas('hide');
                //update view
                init();
            },
            error: function (xhr, status, error) {
                // Handle errors, e.g., display them in the console or an alert
                console.log(xhr.responseText);
            },
        });
    }

    function build_tasks() {
        //clear all container
        $('#pendingContainer').empty();
        $('#overdueContainer').empty();
        $('#completedContainer').empty();

        settings.tasks.filter(data => data.parent === null).forEach((item, index, array) => {
            const doneIcon = item.status ? '<i class="bx bxs-check-circle"></i>' : '<i class="bx bx-check-circle"></i>';
            const priClass = item.priority === 'high' ? 'danger' : item.priority === 'medium' ? 'warning' : 'success';
            const task_container = `
                    <div id="${item.id}" class="p-3 mb-2 border border-2 rounded-2">
                        <div class="kb-task-body">
                            <div class="kb-task-body-layout mb-2">
                                <div class="kb-task-name-layout">
                                    <span class="kb-task-name fs-5 fw-bold">${item.name}</span>
                                </div>
                                <div class="kb-task-status-layout d-flex align-items-center">
                                   ${doneIcon} 
                                </div>
                            </div>
                            <div class="kb-task-body-layout2 p-0">
                                <span class="badge text-bg-${priClass}">${item.priority}</span>
                                <div class="kb-task-due-date-layout">
                                    <span class="kb-task-due-date subtask-date-font-size">Due date : ${new Date(item.end_date).toLocaleString('en-US', {
                                        month: 'short',
                                        day: 'numeric'
                                    })}</span>
                                </div>
                            </div>
                        </div>
                      </div>
            `;

            const today = new Date();
            const itemEndDate = new Date(item.end_date);
            itemEndDate.setHours(0, 0, 0, 0);
            today.setHours(0, 0, 0, 0);

            if (item.status) {
                $('#completedContainer').append(task_container);
                $(`#${item.id}`).addClass('bd-success');
                $(`#${item.id}`).addClass('border-success');

            } else if (!item.status && itemEndDate < new Date()) {
               $('#overdueContainer').append(task_container);
                $(`#${item.id}`).addClass('bd-danger');
                $(`#${item.id}`).addClass('border-danger');

            } else {
                $('#pendingContainer').append(task_container);
                $(`#${item.id}`).addClass('bod-success');
            }

            $(`#${item.id}editTask`).on('click',()=>{
                buildCompleteTaskModal(item.id)
                $('#completeTaskModal').modal('show');
            })


            $(`#${item.id} .kb-task-name-layout`).on('click', () => {
                //create offcanvas
                buildTaskViewModal(item.id);
                //show offcanvas
                $('#taskViewModal').offcanvas('show');
            });

            function updateTaskStatus() {
                let taskData = item;
                taskData.status = !taskData.status;
                taskData.assignees = taskData.assignees.map(val => parseInt(val.id, 10));
                taskData.actual_due_date = null;
                taskData.actual_hours = null;
                updateTask(taskData)
                $('#confirmModal').modal('hide');
            }

            $(`#${item.id} .kb-task-status-layout`).on('click', () => {
                if (item.status) {
                    console.log('clicked complete')
                    renderConfirmModal(updateTaskStatus, "Are you sure to make this task incomplete?")
                    //show modal
                    $('#confirmModal').modal('show');
                } else {
                    buildCompleteTaskModal(item.id)
                    $('#completeTaskModal').modal('show');
                }
            });


        });
    }

    function renderConfirmModal(action = () => {
        console.log('clicked confirm')
    }, title) {
        $('#confirmModal').remove();
        buildConfirmModal(action, title)
    }

    /* ===========================================================
    *  Build phase modals Function
    * ============================================================*/
    function buildConfirmModal(action = () => {
    }, title = 'Are you sure?') {
        const modal = `
              <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="confirmModalLabel">${title}</h1>
                            <button type="button" class="btn btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <label for="phaseName" class="form-label">Enter <span class="text-danger fw-bold">CONFIRM</span> to delete</label>
                            <input type="text" id="confirmInput" class="form-control" autocomplete="off">
                            
                            <div id="errorConfirmText" class="d-flex d-none text-danger align-items-center gap-1"><i class="bx bx-info-circle"></i>Please check your input</div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="confirmButton">Confirm
                            </button>
                        </div>
                    </div>
                </div>
            </div>`;
        $('#modalPlaceholder').append(modal);
        $('#confirmButton').on('click', () => {
            if ($('#confirmInput').val() === 'CONFIRM') {
                action();
            } else {
                $('#errorConfirmText').removeClass('d-none');
                //focus input
                $('#confirmInput').focus();
            }
        });
        $('#confirmInput').on('input', () => {
            if ($('#confirmInput').val() === 'CONFIRM' || $('#confirmInput').val() === '') {
                $('#errorConfirmText').addClass('d-none');
            }
        })

    }


    /*
     ================================================
     Build Task View Modal
     ================================================
      */
    function buildTaskViewModal(taskId, item = null) {
        $('#taskViewModal').remove();
        const task = item ? item : settings.tasks.filter(data => data.id === taskId)[0] || simpleTask;
        const priorityClass = task.priority === 'high' ? 'priority-high' : task.priority === 'medium' ? 'priority-medium' : 'priority-low';
        const completeBtnText = task.status ? 'completed' : 'Mark complete';

        const view = `
            <div class="offcanvas offcanvas-end" tabindex="-1" id="taskViewModal" aria-labelledby="taskViewModalLabel">
              <div class="offcanvas-header">
                   <div id="btn-area" class="d-flex gap-2">
                   </div>
                   <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
              </div>
              <div class="offcanvas-body">
                    <div class="d-flex flex-column gap-3 col-12 px-3 y-scroll">
                        <h5 class="offcanvas-title mb-2 col-12">${task.name}</h5>
                          <div class="d-flex col-12">
                            <span class="col-3 fw-bold">Assignee</span>
                            <span class="col-9">${task.assignees.map(val => val.name).join(", ") || 'No assignee'}</span>
                        </div>
                        <div class="d-flex col-12">
                            <span class="col-3 fw-bold">Start date</span>
                            <span class="col-3">
                                ${new Date(task.start_date).toLocaleString('en-US', {
                                    year: 'numeric',
                                    month: 'short',
                                    day: 'numeric'
                                })}
                            </span>
                            <span class="col-3 fw-bold">Due date</span>
                            <span class="col-3">
                                ${new Date(task.end_date).toLocaleString('en-US', {
                                    year: 'numeric',
                                    month: 'short',
                                    day: 'numeric'
                                })}
                            </span>
                        </div>
                        <div class="d-flex col-12">
                            <span class="col-3 fw-bold">Priority</span>
                            <span class="col-3"><span class="${priorityClass} text-block">${task.priority}</span></span>
                       
                            <span class="col-3 fw-bold">Task Group</span>
                            <span class="col-3">${task.group}</span>
                        </div>
                        <div class="d-flex col-12">
                            <span class="col-3 fw-bold">Duration</span>
                            <span class="col-3">${task.duration + (task.duration > 1 ? ' days' : ' day')}</span>
                        
                            <span class="col-3 fw-bold">Plan Hour</span>
                            <span class="col-3">${task.plan_hours} hours</span>
                        </div>
                        <div class="d-flex flex-column gap-1 col-12">
                            <span class="fw-bold">Description</span>
                            <span class="border border-1 px-3 py-1 rounded-1 text-justify">
                                ${task.description || 'No description'}
                            </span>
                        </div>
                     
                      </div>
              </div>
            </div>
            `;


        let btn = ` <button id="${task.id}-complete-btn" class="btn-sm make-complete-btn py-1 px-2 fs-6 ${task.status ? 'btn-complete' : ''}">
                                     <i class='bx bx-check fs-5 me-1'></i>${completeBtnText} 
                            </button>
                              `;
        $('#modalPlaceholder').append(view);

        //append as first child of offcanvas-header
        $(`.offcanvas-header #btn-area`).prepend(btn);


        function updateTaskStatus() {
            let taskData = task;
            taskData.status = !taskData.status;
            taskData.assignees = taskData.assignees.map(val => parseInt(val.id, 10));
            taskData.actual_due_date = null;
            taskData.actual_hours = null;
            updateTask(taskData)
            $('#confirmModal').modal('hide');
        }

        const completeBtn = document.getElementById(`${task.id}-complete-btn`)
        if (completeBtn) {
            completeBtn.addEventListener('click', () => {
                if (task.status) {
                    renderConfirmModal(updateTaskStatus, "Are you sure to make this task incomplete?")
                    //show modal
                    $('#confirmModal').modal('show');
                } else {
                    buildCompleteTaskModal(task.id)
                    $('#completeTaskModal').modal('show');
                }
            });
        }

    }


    init();
    //end
}


//
