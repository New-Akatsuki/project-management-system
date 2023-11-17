(function ($) {
    $.fn.kanban = function (options) {
        let $this = $(this);

        //current phase and parent ids
        let phaseId = null;
        let parentId = null;
        let currentTaskId = null;

        //modal data object
        let addPhaseModalData = {title: 'Add', modalId: 'addPhaseModal',};
        let editPhaseModalData = {title: 'Edit', modalId: 'editPhaseModal'};

        //simple task object
        let simpleTask = {
            id: null,
            name: "Task",
            description: "",
            priority: "",
            start_date: "",
            end_date: "",
            actual_due_date: "",
            duration: null,
            plan_hours: 0.0,
            actual_hours: 0.0,
            status: false,
            phase: phaseId,
            parent: parentId,
            group: "",
            type: "",
            assignees: [],
            open: true,
        };

        let settings = $.extend(
            {
                phases: [],
                tasks: [],
                users: [],
                startDate: null,
                endDate: null,
                projectId: null,
                onChange: function (e, ui) {
                },
                onReceive: function (e, ui) {
                },
            },
            options
        );


        let classes = {
            kanban_board_class: "cd_kanban_board",
            kanban_board_blocks_class: "cd_kanban_board_blocks",
            kanban_board_block_class: "cd_kanban_board_block",
            kanban_board_item_placeholder_class: "cd_kanban_board_block_item_placeholder",
            kb_section_class: "kb-section",
            kb_section_header_class: "kb-section-header",
            kb_section_header_name_class: "kb-section-header-name",
            kb_section_header_btn_class: "kb-section-header-btn",
            kb_section_body_class: "kb-section-body",
            kb_task_class: "kb-task",
            kb_task_header_class: "kb-task-header",
            kb_task_body_class: "kb-task-body",
            kb_task_body_layout_class: "kb-task-body-layout",
            kb_task_name_layout_class: "kb-task-name-layout",
            kb_task_name_class: "kb-task-name",
            kb_task_status_layout_class: "kb-task-status-layout",
            kb_task_due_date_layout_class: "kb-task-due-date-layout",
            kb_task_due_date_class: "kb-task-due-date",
            kb_task_subtask_btn_class: "kb-task-subtask-btn",
            kb_task_subtask_layout_class: "kb-task-subtask-layout",
            kb_task_subtask_class: "kb-task-subtask",
            kb_subtask_body_class: "kb-subtask-body",
            kb_subtask_body_layout_class: "kb-subtask-body-layout",
            kb_subtask_name_layout_class: "kb-subtask-name-layout",
            kb_subtask_name_class: "kb-subtask-name",
            kb_subtask_due_date_layout_class: "kb-subtask-due-date-layout",
            kb_subtask_due_date_class: "kb-subtask-due-date",
            kb_subtask_status_layout_class: "kb-subtask-status-layout",
            kb_subtask_status_class: "kb-subtask-status",
        };

        $this.on('click', '.' + classes.kb_section_header_btn_class, function (e) {
            e.preventDefault();
            let modalId = $(this).data('bs-target');
            let $modal = $(modalId);

            if ($modal.length) {
                $modal.modal('show'); // Use Bootstrap's modal() function to show the modal
            }
        });

        /* ===========================================================
             *  Build Kanban UI Function
             * ============================================================*/
        function initKanban() {
            $this.addClass(classes.kanban_board_class);
            $('#add-phase-button').remove()

            //check if element exist, make empty
            $this.find('.' + classes.kanban_board_blocks_class).remove();

            $this.append('<div class="' + classes.kanban_board_blocks_class + '"></div>');
            if(settings.phases.length === 0){
                $('.'+classes.kanban_board_blocks_class)
                    .addClass('flex-column justify-content-center align-items-center gap-2')
                    .append(
                    `<div class="text-center text-secondary display-6 fs-3 fw-bold">No data found!</div>
                     <button type="submit" id="add-phase-button" class="btn btn-primary" 
                            data-bs-toggle="modal" data-bs-target="#${addPhaseModalData.title.toLowerCase()}PhaseModal">
                                Add Phase +
                           </button>`
                )
            }else{
                $this.append(`<button type="submit" id="add-phase-button" class="btn btn-primary move-top" 
                            data-bs-toggle="modal" data-bs-target="#${addPhaseModalData.title.toLowerCase()}PhaseModal">
                                Add Phase +
                           </button>`);
            }

            build_section();
            build_tasks();
            build_subtask();
            enableDragAndDrop();
            $('#addPhaseModal').remove()
            $('#editPhaseModal').remove()

            buildPhaseModal(addPhaseModalData);
            buildPhaseModal(editPhaseModalData);
        }

        /* ===========================================================
            *  Build toast
            * ============================================================*/
        function buildToast(){
            $('#toastPlace').empty();
            const toast = `<div class="toast-container position-fixed bottom-0 end-0 p-3">
                                      <div id="liveToast" class="toast " role="alert" aria-live="assertive" aria-atomic="true">
                                        <div class="toast-header">
                                          <img src="/images/DAT.png" class="rounded me-2" alt="...">
                                          <strong class="me-auto">Bootstrap</strong>
                                          <small>11 mins ago</small>
                                          <button type="button" class="btn-close" data-bs-dismiss="toast" aria-label="Close"></button>
                                        </div>
                                        <div class="toast-body">
                                          Hello, world! This is a toast message.
                                        </div>
                                      </div>
                                    </div>`;
            $('#toastPlace').append(toast);
        }

        /* ===========================================================
       *  Rebuild UI Function
       * ============================================================*/
        function render() {
            // Clear the kanban board and rebuild the sections and tasks
            $this.addClass(classes.kanban_board_class);
            $('#add-phase-button').remove()
            //check if element exist, make empty
            $this.find('.' + classes.kanban_board_blocks_class).remove();

            $this.append('<div class="' + classes.kanban_board_blocks_class + '"></div>');
            if(settings.phases.length === 0){
                $('.'+classes.kanban_board_blocks_class)
                    .addClass('flex-column justify-content-center align-items-center gap-2')
                    .append(
                    `<div class="text-center text-secondary fs-3 fw-bold">No data found!</div>
                     <button type="submit" id="add-phase-button" class="btn btn-primary" 
                            data-bs-toggle="modal" data-bs-target="#${addPhaseModalData.title.toLowerCase()}PhaseModal">
                                Add Phase +
                           </button>`
                )
            }else{
                $this.append(`<button type="submit" id="add-phase-button" class="btn btn-primary move-top" 
                            data-bs-toggle="modal" data-bs-target="#${addPhaseModalData.title.toLowerCase()}PhaseModal">
                                Add Phase +
                           </button>`);
            }
            build_section();
            build_tasks();
            build_subtask();
            enableDragAndDrop();
        }

        /* ===========================================================
        *  Build Sections UI Function
        * ============================================================*/
        function build_section() {
            settings.phases.forEach((item, index, array) => {
                const section_container = `
                    <div id="${item.id}-phase" class="${classes.kb_section_class}">
                        <div class="kb-section-header px-board border-highlight-left">
                            <span class="kb-section-header-name display-6 fs-5 fw-bold">${item.name}</span>
                            <div class="d-flex gap1">
                                <button class="kb-section-header-btn d-flex align-items-center pt-1" id="${item.id}-btn"><i class='bx bx-plus fs-4'></i></button>
                                <div>
                                      <button class="kb-section-header-btn d-flex align-items-center pt-1 px-0 mx-0" type="button" data-bs-toggle="dropdown" aria-expanded="false">
                                        <i class='bx bx-dots-vertical-rounded fs-4'></i>
                                      </button>
                                      <ul class="dropdown-menu">
                                        <li><button class="dropdown-item d-flex align-items-center" id="${item.id}-rename-btn" type="button">
                                            <i class='bx bx-edit-alt'></i>Rename 
                                        </button></li>
                                        <li><button class="dropdown-item d-flex align-items-center" id="${item.id}-delete-phase-btn" type="button">
                                        <i class='bx bx-trash'></i>Delete</button></li>
                                      </ul>
                               </div>
                            </div>
                        </div>
                        <div id="${item.id}-phase-body" class="kb-section-body px-board"></div>
                    </div>
                `;
                $this.find('.' + classes.kanban_board_blocks_class).append(section_container);
                $(`#${item.id}-btn`).on('click', () => {
                    phaseId = item.id;
                    parentId = null;
                    currentTaskId = null;
                    buildAddTaskModal(settings.startDate, settings.endDate)
                    resetTaskModal();
                    //show modal
                    $('#exampleModal').modal('show');
                });
                //rename phase
                $(`#${item.id}-rename-btn`).on('click', () => {
                    phaseId = item.id;
                    resetPhaseModal(editPhaseModalData);
                    $('#deleteBtnInEditPhaseModal').remove();
                    //show modal
                    $(`#${editPhaseModalData.title.toLowerCase()}PhaseModal`).modal('show');
                });
                $(`#${item.id}-delete-phase-btn`).on('click', () => {
                    phaseId = item.id;
                    renderConfirmModal(deletePhase, "Are you sure to delete this phase?");
                    //show modal
                    $('#confirmModal').modal('show');
                });
            });
        }

        /* ===========================================================
       *  Build Tasks UI Function
       * ============================================================*/
        function build_tasks() {
            settings.tasks.filter(data => data.parent === null).forEach((item, index, array) => {
                const numberOfSubtasks = settings.tasks.filter(data => data.parent === item.id).length;
                const height = numberOfSubtasks * 50;
                const doneIcon = item.status ? '<i class="bx bxs-check-circle"></i>' : '<i class="bx bx-check-circle"></i>';
                const task_container = `
                    <div id="${item.id}" class="kb-task mb-2">
                        <div class="kb-task-body">
                            <div class="kb-task-body-layout">
                                <div class="kb-task-name-layout">
                                    <span class="kb-task-name fs-6 fw-bold">${item.name}</span>
                                </div>
                                <div class="kb-task-status-layout d-flex align-items-center">
                                   ${doneIcon} 
                                </div>
                            </div>
                            <div class="kb-task-body-layout2">
                                <div class="kb-task-due-date-layout">
                                    <span class="kb-task-due-date subtask-date-font-size">Due date : ${new Date(item.end_date).toLocaleString('en-US', {
                    month: 'short',
                    day: 'numeric'
                })}</span>
                                </div>
                                <div class="kb-task-subtask-layout">
                                  <span class="me-2">${numberOfSubtasks}</span>
                                  <button id="${item.id}-toggle-subtask-btn" class="z-2 toggle-subtask-icon toggle-subtask-icon-active" ${numberOfSubtasks > 0 ? '' : 'disabled'}><i class="ri-git-merge-line"></i></button>
                                </div>
                            </div>
                            <div id="${item.id}-subtask-block" class="hide subtask-block"></div>
                        </div>
                        <button id="${item.id}-add-subtask-btn" class="add-subtask-btn d-flex align-items-center justify-content-center gap-2"><i class='bx bx-plus fs-4'></i>Add Subtask</button>
                    </div>
            `;
                //insert task into phase body
                $this.find(`#${item.phase}-phase-body`).append(task_container);

                // Add a click event handler to add subtask btn
                $(`#${item.id}-add-subtask-btn`).on('click', () => {
                    currentTaskId = null
                    parentId = item.id;
                    phaseId = item.phase;
                    buildAddTaskModal(item.start_date, item.end_date)
                    resetTaskModal();
                    //show modal
                    $('#exampleModal').modal('show');
                });

                //toggle subtask
                const task = document.getElementById(`${item.id}`);

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
                        renderConfirmModal(updateTaskStatus, "Are you sure to make this task incomplete?")
                        //show modal
                        $('#confirmModal').modal('show');
                    } else {
                        buildCompleteTaskModal(item.id)
                        $('#completeTaskModal').modal('show');
                    }
                });

                const subtaskIcon = document.getElementById(`${item.id}-toggle-subtask-btn`);
                if (subtaskIcon) {
                    subtaskIcon.addEventListener('click', () => {
                        const subtaskBlock = document.getElementById(`${item.id}-subtask-block`);
                        subtaskBlock.classList.toggle('hide');
                        //for animation
                        task.style.height = subtaskBlock.classList.contains('hide') ? '145' : `${height + 145}px`;
                        subtaskIcon.classList.toggle('toggle-subtask-icon-active');
                    });
                }
            });
        }

        /* ===========================================================
       *  Build SubTasks UI Function
       * ============================================================*/
        function build_subtask() {
            settings.tasks.filter(data => data.parent !== null).forEach((item, index, array) => {
                const parentData = settings.tasks.filter(data => data.id === item.parent)[0];
                if (parentData) {
                    item.phase = parentData.phase;
                }
                const doneIcon = item.status ? '<i class="bx bxs-check-circle"></i>' : '<i class="bx bx-check-circle"></i>';
                const sub_task_container = `
                    <div id="${item.id}" class="kb-subtask-body">
                            <span class="flex-1">
                                ${doneIcon}
                            </span>
                            <span class="kb-subtask-name flex-6">${item.name}</span>
                            <span class="kb-subtask-due-date flex-3 d-flex flex-column subtask-date-font-size text-end">
                                <span style="font-size: 9px">Due date</span>
                                <span>${new Date(item.end_date).toLocaleString('en-US', {
                    month: 'short',
                    day: 'numeric'
                })}</span>
                            </span>                                                             
                        </div>                   
                `;


                $this.find(`#${item.parent}-subtask-block`).append(sub_task_container);

                // Add a click event handler to each subtask
                const subtask = document.getElementById(`${item.id}`);
                if (subtask) {
                    subtask.addEventListener('click', () => {
                        //create offcanvas
                        buildTaskViewModal(item.id);
                        //show offcanvas
                        $('#taskViewModal').offcanvas('show');
                    });
                }
            });
        }

        /* ===========================================================
        *  Build Drag and Drop Function
        * ============================================================*/
        function enableDragAndDrop() {
            $('.' + classes.kb_section_body_class).sortable({
                connectWith: '.' + classes.kb_section_body_class,
                placeholder: classes.kanban_board_item_placeholder_class,
                items: '.' + classes.kb_task_class,
                start: function (e, ui) {
                    ui.item.data('originalSection', ui.item.closest('.' + classes.kb_section_class));
                    placeholderHeight = ui.item.outerHeight();
                    ui.placeholder.height(placeholderHeight + 15);
                    $('<div class="slide-placeholder-animator" data-height="' + placeholderHeight + '"></div>').insertAfter(ui.placeholder);
                },
                change: function (e, ui) {
                    ui.placeholder.stop().height(0).animate({
                        height: ui.item.outerHeight() + 15
                    }, 300);

                    placeholderAnimatorHeight = parseInt($(".slide-placeholder-animator").attr("data-height"));

                    $(".slide-placeholder-animator").stop().height(placeholderAnimatorHeight + 15).animate({
                        height: 0
                    }, 300, function () {
                        $(this).remove();
                        placeholderHeight = ui.item.outerHeight();
                        $('<div class="slide-placeholder-animator" data-height="' + placeholderHeight + '"></div>').insertAfter(ui.placeholder);
                    });
                },
                stop: function (e, ui) {
                    $(".slide-placeholder-animator").remove();

                    const task = ui.item;
                    const originalSection = ui.item.data('originalSection');
                    const newSection = task.closest('.' + classes.kb_section_class);

                    const taskId = parseInt(task.attr('id'), 10);
                    const originalSectionId = originalSection.attr('id');
                    const newSectionId = newSection.attr('id');


                    let taskData = settings.tasks.filter(data => data.id === taskId).at(0);
                    taskData.phase = parseInt(newSectionId.split('-')[0], 10);
                    taskData.parent = null;
                    taskData.assignees = taskData.assignees.map(val => parseInt(val.id, 10));
                    updateTask(taskData)
                },
            }).disableSelection();
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
            $('#phaseModalPlace').append(modal);
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

        /* ===========================================================
              *  render phase modals Function
              * ============================================================*/
        function renderConfirmModal(action = () => {
            console.log('clicked confirm')
        }, title) {
            $('#confirmModal').remove();
            buildConfirmModal(action, title)
        }


        /* ===========================================================
        *  Build phase modals Function
        * ============================================================*/
        function buildPhaseModal(phaseData = {title: 'Add', modalId: 'addPhaseModal'}) {
            $(`#${phaseData.modalId}`).remove();
            const phaseModal = `
              <div class="modal fade" id="${phaseData.modalId}" tabindex="-1" aria-labelledby="${phaseData.modalId}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="${phaseData.modalId}Label">${phaseData.title === "Edit" ? 'Rename' : 'Add New'} Phase</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <label for="phaseName">Phase Name:</label>
                            <input type="text" id="${phaseData.title.toLowerCase()}PhaseName" class="form-control" placeholder="Enter Phase Name">
                        </div>
                        <div id="deleteBtnPlaceInEditPhaseModal" class="modal-footer d-flex flex-row-reverse justify-content-between">
                          
                            <div> 
                            
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit" class="btn btn-primary" id="${phaseData.title.toLowerCase()}PhaseButton" data-bs-dismiss="modal">
                                    Save Phase
                                </button>
                            </div>
                           
                        </div>
                    </div>
                </div>
            </div>`;
            $('#phaseModalPlace').append(phaseModal);
            $(`#${phaseData.title.toLowerCase()}PhaseButton`).on('click', phaseData.title === 'Add' ? savePhase : updatePhase);

        }


        /* ===========================================================
      *  Save Phase to Database
      * ============================================================*/

        //Define savePhase function outside the kanban plugin
        function savePhase() {
            const phaseData = {
                id: null,
                name: $(`#${addPhaseModalData.title.toLowerCase()}PhaseName`).val(),
                projectId: settings.projectId,
            };
            $.ajax({
                url: '/add-phase',
                method: 'POST',
                data: JSON.stringify(phaseData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    resetPhaseModal(addPhaseModalData)
                    settings.phases.push(data)
                    render()
                    $.fn.refreshGantt();
                    buildToast();
                    $('#liveToast').toast('show');

                },
                error: function (xhr, status, error) {
                    console.log(xhr.responseText);
                }
            });
        }

        /* ===========================================================
     *  update Phase to Database
     * ============================================================*/
        function updatePhase() {
            let phaseData = settings.phases.filter(data => data.id === parseInt(phaseId, 10))[0]
            phaseData.name = $(`#${editPhaseModalData.title.toLowerCase()}PhaseName`).val();
            $.ajax({
                url: '/update-phase',
                method: 'PUT',
                data: JSON.stringify(phaseData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    resetPhaseModal(editPhaseModalData)
                    settings.phases.filter(val => val.id === phaseData.id)[0].name = data.name;
                    $.fn.refreshGantt()
                    render()
                },
                error: function (xhr, status, error) {
                    console.log(xhr.responseText);
                }
            });
        }

        /* ===========================================================
          *  delete Phase to Database
          * ============================================================*/
        function deletePhase() {
            let phaseData = settings.phases.filter(data => data.id === parseInt(phaseId, 10))[0]

            $.ajax({
                url: '/delete-phase',
                method: 'DELETE',
                data: JSON.stringify(phaseData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    settings.tasks = settings.tasks.filter(val => val.phase !== phaseData.id);
                    settings.phases = settings.phases.filter(val => val.id !== phaseData.id);
                    $.fn.refreshGantt()
                    render()
                    $('#confirmModal').modal('hide');
                },
                error: function (error) {
                    console.log("Error response text:", error.responseText);
                }
            });
        }


        /* ===========================================================
            Save Task to Database
           ============================================================*/
        function saveOrUpdateTask() {

            const task = {
                id: currentTaskId,
                name: $('#task-name').val(),
                description: $('#task-description').val(),
                priority: $('#task-priority').val(),
                start_date: $('#task-start-date').val(),
                end_date: $('#task-end-date').val(),
                actual_due_date: null,
                duration: null,
                plan_hours: parseFloat($('#task-plan-hours').val()),
                actual_hours: null,
                status: false,
                phase: phaseId,
                parent: parentId,
                group: $('#task-group').val(),
                type: 'task',
                assignees: $('#task-assignees').val().map(val => parseInt(val, 10)),
                open: true,
            };
            //check if task id exist
            if (currentTaskId) {
                let currentTask = settings.tasks.filter(data => data.id === currentTaskId)[0];
                currentTask.name = task.name
                currentTask.description = task.description
                currentTask.priority = task.priority
                currentTask.start_date = task.start_date
                currentTask.end_date = task.end_date
                currentTask.plan_hours = task.plan_hours
                currentTask.group = task.group
                currentTask.type = task.type
                currentTask.assignees = task.assignees


                updateTask(currentTask)
            } else {
                saveTask(task)
            }
            $("#exampleModal").modal("hide")
        }

        function saveTask(task) {
            $.ajax({
                url: '/add-task',
                method: 'POST',
                data: JSON.stringify(task),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    settings.tasks.push(data)
                    resetTaskModal()
                    if (document.getElementById('taskViewModal')) {
                        renderSubtask(parentId);
                    }
                    $.fn.refreshGantt()
                    render()
                },
                error: function (xhr, status, error) {
                    // Handle errors, e.g., display them in the console or an alert
                    console.log(xhr.responseText);
                },
            });
        }

        // recursive function that
        // reset start date and end date of subtasks as parent task's start date and end date
        // if subtask start date or end date is not in range of parent task
        function resetSubtaskDate(parentTask) {
            settings.tasks.filter(data => data.parent === parentTask.id).forEach((item, index, array) => {
                if (item.start_date < parentTask.start_date) {
                    item.start_date = parentTask.start_date
                }
                if (item.end_date > parentTask.end_date) {
                    item.end_date = parentTask.end_date
                }
                settings.tasks.filter(data => data.id === item.id)[0] = item;
                resetSubtaskDate(item)
            });
        }


        /* ===========================================================
        *  update Task to Database
        * ============================================================*/
        function updateTask(task, refresh = true) {

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

                    resetSubtaskDate(data)
                    //reload
                    if (refresh) {
                        $.fn.refreshGantt()
                    }
                    render()
                    //check taskViewModal exist and check class list contain show
                    const modal = document.getElementById('taskViewModal')
                    if (modal && modal.classList.contains('show')) {
                        //close offcanvas
                        $('#taskViewModal').offcanvas('hide');
                        buildTaskViewModal(data.id);
                        $('#taskViewModal').offcanvas('show');
                    }
                    currentTaskId = null
                },
                error: function (xhr, status, error) {
                    // Handle errors, e.g., display them in the console or an alert
                    console.log(xhr.responseText);
                },
            });
        }

        /* ===========================================================
       *  delete Task to Database
       * ============================================================*/
        function deleteTask() {
            let taskData = settings.tasks.filter(data => data.id === parseInt(currentTaskId, 10))[0] || simpleTask
            taskData.assignees = taskData.assignees.map(val => parseInt(val.id, 10))

            $.ajax({
                url: '/delete-task',
                method: 'DELETE',
                data: JSON.stringify(taskData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    settings.tasks = settings.tasks.filter(val => val.id !== taskData.id);
                    settings.tasks = settings.tasks.filter(val => val.parent !== taskData.id);

                    $('#taskViewModal').offcanvas('hide');
                    $('#taskViewModal').remove();
                    $('#confirmModal').modal('hide');
                    currentTaskId = null
                    $.fn.refreshGantt()
                    render()
                },
                error: function (error) {
                    console.log("Error response text:", error.responseText);
                }
            });
        }


        //reset function for task modal
        function resetTaskModal() {
            $('#task-name').val("")
            $('#task-description').val("")
            $('#task-priority').val("")
            $('#task-start-date').val("")
            $('#task-end-date').val("")
            $('#task-plan-hours').val("")
            $('#task-group').val("")
            $('#task-type').val("")
            $('#task-assignees').val("").trigger('change')
        }

        function fillDataTaskModal(taskId) {
            const task = settings.tasks.filter(data => data.id === taskId)[0] || simpleTask;
            $('#task-name').val(task.name)
            $('#task-description').val(task.description)
            $('#task-priority').val(task.priority)
            $('#task-start-date').val(task.start_date)
            $('#task-end-date').val(task.end_date)
            $('#task-plan-hours').val(task.plan_hours)
            $('#task-group').val(task.group)
            $('#task-type').val(task.type)
            $('#task-assignees').val(task.assignees.map(val => val.id)).trigger('change')
        }

        /* ===========================================================
           Build completeTaskModal Function
          ============================================================*/
        function buildCompleteTaskModal(taskId) {
            // Dynamic creation of modal with form
            let today = new Date();
            // Get the month, day, and year
            let month = (today.getMonth() + 1).toString().padStart(2, '0'); // Months are zero-based
            let day = today.getDate().toString().padStart(2, '0');
            let year = today.getFullYear();

            // Format the date as mm-dd-yyyy
            let formattedDate = `${year}-${month}-${day}`;

            const item = settings.tasks.filter(data => data.id === taskId)[0] || simpleTask;
            //check completeTaskModal if already exist, remove
            $('#completeTaskModal').remove();

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
            $('#phaseModalPlace').append(completeTaskModal);
            // checkDatesValidation("task-start-date", "task-end-date");
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


        function buildAddTaskModal(minDate = null, maxDate = null) {
            $('#exampleModal').remove();
            const addTaskModal = `
              <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <form id="addTaskForm" autocomplete="off">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="exampleModalLabel">Task</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <div class="task-body-text-layout">
                                    <div class="form-group d-flex flex-column mb-0">
                                        <label for="task-name" class="form-label">Enter Task Name:</label>
                                        <input type="text" id="task-name" placeholder="Name" class="form-control" minlength="3" maxlength="50" required>
                                    </div>
                                    <div class="form-group d-flex flex-column mb-0">
                                        <label for="task-description" class="form-label">Enter Description:</label>
                                        <textarea id="task-description" placeholder="Description" class="form-control" maxlength="500"></textarea>
                                    </div>
                                    <div class="d-flex gap-2 align-items-start">
                                        <div class="form-group mb-0 flex-3 d-flex flex-column">
                                            <label for="task-start-date" class="form-label">Start Date:</label>
                                            <input type="text" id="task-start-date" placeholder="Enter start date" class="form-control mb-0" required>
                                            <div class="invalid-feedback" id="task-start-date_error"></div>
                                        </div>
                                        <div class="form-group mb-0 flex-3 d-flex flex-column">
                                            <label for="task-end-date" class="form-label">End Date:</label>
                                            <input type="text" id="task-end-date" placeholder="Enter end date" class="form-control mb-0" required>
                                            <div class="invalid-feedback" id="task-end-date_error"></div>
                                        </div>
                                        <div class="form-group mb-0 flex-3 d-flex flex-column">
                                            <label for="task-plan-hours" class="form-label">Plan Hours:</label>
                                            <input type="number" step="0.1" min="0" id="task-plan-hours" placeholder="Enter plan hours" class="form-control mb-0" required>
                                        </div>
                                    </div>
                                    <div class="d-flex gap-2">
                                        <div class="form-group mb-0 flex-3">
                                            <label for="task-priority" class="form-label">Priority:</label>
                                            <select id="task-priority" class="form-select" required>
                                                <option value="" selected>Select task priority</option>
                                                <option value="high">High</option>
                                                <option value="medium">Medium</option>
                                                <option value="low">Low</option>
                                            </select>
                                        </div>
                                        <div class="form-group mb-0 flex-3">
                                            <label for="task-group" class="form-label">Group:</label>
                                            <select id="task-group" class="form-select" required>
                                                <option value="" selected>Select task group</option>
                                                <option value="A">A</option>
                                                <option value="B">B</option>
                                                <option value="C">C</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="task-assignees" class="form-label" >Assignees :</label>
                                        <select class="js-example-basic-multiple form-select" multiple id="task-assignees" style="width: 100%; height: 100%;">
                                        </select>
                                    </div>
                                </div>
                                <input type="hidden" id="task-phase">
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                                <button type="submit"  class="btn btn-primary" id="saveTaskBtn">Save changes</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            `;

            $('#phaseModalPlace').append(addTaskModal);
            $.each(settings.users, function (index, value) {
                $('#task-assignees').append($('<option>', {
                    value: value.id,
                    text: value.name
                }));
            });

            $('#task-start-date').on('input',()=>{
                $('#task-start-date').val('');
            })

            setupDatePicker('#task-start-date', minDate, maxDate);
            setupDatePicker('#task-end-date', minDate, maxDate);
            checkDatesValidation("task-start-date", "task-end-date");
            formValidate("addTaskForm", saveOrUpdateTask);
            // setupSelect2('#task-assignees',settings.users);
            $('#task-assignees').select2({
                placeholder: 'assign users',
                dropdownParent: '#exampleModal',
                closeOnSelect: true,
                width: '100%'
            });
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
            const typeClass = task.type === 'task' ? 'type-task' : 'type-milestone';
            const completeBtnText = task.status ? 'completed' : 'Mark complete';
            const subtaskList = settings.tasks.filter(val => val.parent === task.id);
            const isExpandAccordion = subtaskList.length > 0;

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
                        <button id="${task.id}-edit-task-btn" class="btn btn-primary float-edit-btn"><i class="bx bx-edit-alt me-2"></i>Edit</button>
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
                            <span class="border border-1 px-3 py-1 desc-view">
                                ${task.description || 'No description'}
                            </span>
                        </div>
                        <div class="d-flex flex-column gap-1 col-12">
                            <div class="accordion mb-3" id="subtask-view-offcanvas">
                                <div class="accordion-item">
                                    <h2 class="accordion-header">
                                        <button class="accordion-button${isExpandAccordion ? '' : ' collapsed'}" type="button" data-bs-toggle="collapse"
                                                data-bs-target="#collapseOne" aria-expanded="${isExpandAccordion}"
                                                aria-controls="collapseOne">
                                            <span id="subtask-accordion-header" class="display-6 fs-5 fw-bold">Subtasks <span class="ms-2 badge bg-primary">${subtaskList.length}</span></span>
                                        </button>
                                    </h2>
                                    <div id="collapseOne" class="accordion-collapse collapse${isExpandAccordion ? ' show' : ''}"
                                         data-bs-parent="#subtask-view-offcanvas">
                                        <div class="accordion-body"></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button id="${task.id}-add-subtask" class="add-subtask-btn d-flex align-items-center justify-content-center gap-2"><i class='bx bx-plus fs-4'></i>Add Subtask</button>
                    </div>
              </div>
            </div>
            `;


            let btn = ` <button id="${task.id}-complete-btn" class="btn-sm make-complete-btn py-1 px-2 fs-6 ${task.status ? 'btn-complete' : ''}">
                                     <i class='bx bx-check fs-5 me-1'></i>${completeBtnText} 
                            </button>
                            <button id="${task.id}-delete-task-btn" class="btn btn-danger"><i class="bx bx-trash-alt"></i></button>
                            `;
            $('#phaseModalPlace').append(view);

            //append as first child of offcanvas-header
            $(`.offcanvas-header #btn-area`).prepend(btn);


            //append subtasks
            renderSubtask(task.id)

            const addSubtaskBtn = document.getElementById(`${task.id}-add-subtask`);

            // Add a click event handler to add subtask btn
            addSubtaskBtn.addEventListener('click', () => {
                currentTaskId = null
                parentId = task.id;
                phaseId = task.phase;

                buildAddTaskModal(task.start_date, task.end_date);
                resetTaskModal();
                $('#exampleModal').modal('show');
            });

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
            $(`#${task.id}-edit-task-btn`).on('click', () => {

                currentTaskId = task.id;
                let minDate = settings.startDate;
                let maxDate = settings.endDate;
                if (task.parent && !task.parent.toString().includes('p')) {
                    const parentTask = settings.tasks.filter(val => val.id === task.parent)[0]
                    minDate = parentTask.start_date;
                    maxDate = parentTask.end_date;
                }

                buildAddTaskModal(minDate, maxDate)
                fillDataTaskModal(task.id);
                $('#exampleModal').modal('show');
            });

            $(`#${task.id}-delete-task-btn`).on('click', () => {
                currentTaskId = task.id
                renderConfirmModal(deleteTask, "Are you sure to delete this task?")
                //show modal
                $('#confirmModal').modal('show');
            });
        }

        function renderSubtask(taskId) {
            $('#subtask-accordion-header').empty();
            $(`#subtask-view-offcanvas .accordion-body`).empty();
            const subtaskList = settings.tasks.filter(val => val.parent === taskId);
            //append subtasks
            subtaskList.forEach((item) => {
                const doneIcon = item.status ? '<i class="bx bxs-check-circle"></i>' : '<i class="bx bx-check-circle"></i>';
                const subtask = `
                    <div id="${item.id}-offcanvas-subtask" class="kb-subtask-body">
                            <span class="flex-1 fs-4 d-flex align-items-center justify-content-center">
                                ${doneIcon}
                            </span>
                            <span class="kb-subtask-name flex-7">${item.name}</span>
                            <span class="kb-subtask-due-date flex-3 d-flex flex-column subtask-date-font-size text-end me-3">
                                <span style="font-size: 9px">Due date</span>
                                <span>${new Date(item.end_date).toLocaleString('en-US', {
                    month: 'short',
                    day: 'numeric'
                })}</span>
                            </span>                                                             
                        </div>                   
                `;

                $(`#subtask-view-offcanvas .accordion-body`).append(subtask)

                $(`#${item.id}-offcanvas-subtask`).on('click', () => {
                    $('#taskViewModal').offcanvas('hide');
                    //create offcanvas
                    buildTaskViewModal(item.id);
                    //show offcanvas
                    $('#taskViewModal').offcanvas('show');
                })
            })
            $('#subtask-accordion-header').append(`Subtasks <span class="ms-2 badge bg-primary">${subtaskList.length}</span>`)
        }

        //reset function for phase modal
        function resetPhaseModal(data) {
            $(`#${data.title.toLowerCase()}PhaseName`).val("")
        }

        function setPhaseAndParentIds(phase, parent) {
            parentId = parent;
            phaseId = phase;
        }

        function setCurrentTaskId(id) {
            currentTaskId = id;
        }

        // Export your saveTask function for usage elsewhere
        $.fn.kanban.savePhase = savePhase;
        $.fn.kanban.saveOrUpdateTask = saveOrUpdateTask;
        $.fn.kanban.buildAddPhaseModal = buildAddTaskModal;
        $.fn.kanban.buildTaskViewModal = buildTaskViewModal;
        $.fn.kanban.resetTaskModal = resetTaskModal;
        $.fn.kanban.setPhaseAndParentIds = setPhaseAndParentIds;
        $.fn.kanban.updateTask = updateTask;
        initKanban();
    };
})(jQuery);
