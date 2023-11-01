(function ($) {
    $.fn.kanban = function (options) {
        let $this = $(this);
        //modal data object
        let addPhaseModalData = {title:'Add',modalId:'addPhaseModal',};
        let editPhaseModalData = {title:'Edit',modalId:'editPhaseModal'};

        let dataFromUser = {
            phases: [],
            tasks: [],
            onChange: function (e, ui) {
            },
            onReceive: function (e, ui) {
            },
        }
        let phaseId = null;
        let parentId = null;

        let settings = $.extend(
            {
                phases: [],
                tasks: [],
                onChange: function (e, ui) {
                },
                onReceive: function (e, ui) {
                },
            },
            dataFromUser,
            options
        );

        console.log(settings)

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
            $this.append( `<button type="submit" id="add-phase-button" class="btn btn-primary" 
                            data-bs-toggle="modal" data-bs-target="#${addPhaseModalData.title.toLowerCase()}PhaseModal">
                                Add Phase +
                           </button>`);
            //check if element exist, make empty
            if ($this.find('.' + classes.kanban_board_blocks_class).length) {
                $this.find('.' + classes.kanban_board_blocks_class).empty();
            }
            $this.append('<div class="' + classes.kanban_board_blocks_class + '"></div>');
            build_section();
            build_tasks();
            build_subtask();
            enableDragAndDrop();
            buildPhaseModal(addPhaseModalData);
            buildPhaseModal(editPhaseModalData);
            buildConfirmModal();
            buildTaskViewModal();
        }

        /* ===========================================================
       *  Rebuild UI Function
       * ============================================================*/
        function render() {
            // Clear the kanban board and rebuild the sections and tasks
            $this.addClass(classes.kanban_board_class);
            //check if element exist, make empty
            if ($this.find('.' + classes.kanban_board_blocks_class).length) {
                $this.find('.' + classes.kanban_board_blocks_class).empty();
            }
            //$this.append('<div class="vh-100 ' + classes.kanban_board_blocks_class + '"></div>');
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
                                        <li><button class="dropdown-item d-flex align-items-center" id="${item.id}-delete-btn" type="button">
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
                    resetTaskModal();
                    //show modal
                    $('#exampleModal').modal('show');
                });
                //rename phase
                $(`#${item.id}-rename-btn`).on('click',()=>{
                    phaseId = item.id;
                    resetPhaseModal(editPhaseModalData);
                    //show modal
                    $(`#${editPhaseModalData.title.toLowerCase()}PhaseModal`).modal('show');
                });
                $(`#${item.id}-delete-btn`).on('click',()=>{
                    phaseId = item.id;
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
                const doneIcon = item.status ? '<i class="bx bxs-check-circle"></i>':'<i class="bx bx-check-circle"></i>' ;
                const task_container = `
                    <div id="${item.id}" class="kb-task mb-2">
                        <div class="kb-task-body">
                            <div class="kb-task-body-layout">
                                <div class="kb-task-name-layout">
                                    <span class="kb-task-name fs-6 fw-bold">${item.name}</span>
                                </div>
                                <div class="kb-task-status-layout">
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
                            <button id="${item.id}-add-subtask-btn" class="add-subtask-btn d-flex align-items-center justify-content-center gap-2"><i class='bx bx-plus fs-4'></i>Add Subtask</button>
                        </div>  
                    </div>
            `;
                //insert task into phase body
                $this.find(`#${item.phase}-phase-body`).append(task_container);

                // Add a click event handler to add subtask btn
                $(`#${item.id}-add-subtask-btn`).on('click', () => {
                    parentId = item.id;
                    phaseId = item.phase;
                    console.log(item.id, parentId, phaseId)
                    resetTaskModal();
                    //show modal
                    $('#exampleModal').modal('show');
                });
                //toggle subtask
                const task = document.getElementById(`${item.id}`);

                $(`#${item.id} .kb-task-name-layout`).on('click', () => {
                    //show modal
                    // $('#exampleModal').modal('show');
                    //show offcanvas
                    $('#taskViewModal').offcanvas('show');
                    $('#task-name').val(item.name);
                    $('#task-description').val(item.description);
                    $('#task-priority').val(item.priority);
                    $('#task-start-date').val(item.start_date);
                    $('#task-end-date').val(item.end_date);
                    $('#task-plan-hours').val(item.plan_hours);
                    $('#task-group').val(item.group);
                    $('#task-type').val(item.type);
                });

                const subtaskIcon = document.getElementById(`${item.id}-toggle-subtask-btn`);
                if(subtaskIcon) {
                    subtaskIcon.addEventListener('click', () => {
                        const subtaskBlock = document.getElementById(`${item.id}-subtask-block`);
                        subtaskBlock.classList.toggle('hide');
                        //for animation
                        task.style.height = subtaskBlock.classList.contains('hide') ? '155px' : `${height + 145}px`;
                        const addSubtaskBtn = document.getElementById(`${item.id}-add-subtask-btn`);
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
                item.phase = settings.tasks.filter(data => data.id === item.parent)[0].phase;
                const doneIcon = item.status ? '<i class="bx bxs-check-circle"></i>':'<i class="bx bx-check-circle"></i>' ;
                const sub_task_container = `
                    <div id="${item.id}" class="kb-subtask-body">
                            <span class="flex-1">
                                ${doneIcon}
                            </span>
                            <span class="kb-subtask-name flex-6">${item.name}</span>
                            <span class="kb-subtask-due-date flex-3 d-flex flex-column subtask-date-font-size text-end">
                                <span style="font-size: 9px">Due date</span>
                                <span>${new Date(item.end_date).toLocaleString('en-US', {month: 'short', day: 'numeric'})}</span>
                            </span>                                                             
                        </div>                   
                `;


                $this.find(`#${item.parent}-subtask-block`).append(sub_task_container);

                // Add a click event handler to each subtask
                const subtask = document.getElementById(`${item.id}`);
                subtask.addEventListener('click', () => {
                    // Show the modal and populate it with subtask data
                    $('#exampleModal').modal('show');

                    // Populate the modal fields with subtask data
                    $('#task-name').val(item.name);
                    $('#task-description').val(item.description);
                    $('#task-priority').val(item.priority);
                    $('#task-start-date').val(item.start_date);
                    $('#task-end-date').val(item.end_date);
                    $('#task-plan-hours').val(item.plan_hours);
                    $('#task-group').val(item.group);
                    $('#task-type').val(item.type);
                });
            });
        }

        /* ===========================================================
        *  Build Drag and Drop Function
        * ============================================================*/
        function enableDragAndDrop() {
            $('.' + classes.kb_section_body_class).sortable({
                connectWith: '.' + classes.kb_section_body_class,
                placeholder: '.'+ classes.kanban_board_item_placeholder_class,
                items: '.' + classes.kb_task_class,
                start: function (e, ui) {
                    ui.item.data('originalSection', ui.item.closest('.' + classes.kb_section_class));
                },
                update: function (e, ui) {
                    const task = ui.item;
                    const originalSection = ui.item.data('originalSection');
                    const newSection = task.closest('.' + classes.kb_section_class);

                    const taskId = parseInt(task.attr('id'),10);
                    const originalSectionId = originalSection.attr('id');
                    const newSectionId = newSection.attr('id');

                    let taskData = settings.tasks.filter(data => data.id === taskId).at(0);
                    taskData.phase = newSectionId.split('-')[0];
                    taskData.parent = null;
                    updateTask(taskData)
                },
            }).disableSelection();
        }

        /* ===========================================================
       *  Build phase modals Function
       * ============================================================*/
        function buildConfirmModal(){
            const modal = `
              <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="confirmModalLabel">Are you sure?</h1>
                            <button type="button" class="btn btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <label for="phaseName" class="form-label">Enter <span class="text-danger">"CONFIRM'</span> to delete</label>
                            <input type="text" id="confirmInput" class="form-control">
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
            $this.append(modal);
            $('#confirmButton').on('click', ()=>{
                if($('#confirmInput').val() === 'CONFIRM'){
                    deletePhase();
                }else {
                    $('#errorConfirmText').removeClass('d-none');
                    //focus input
                    $('#confirmInput').focus();
                }
            });
            $('#confirmInput').on('input', ()=>{
                if($('#confirmInput').val() === 'CONFIRM'||$('#confirmInput').val() === ''){
                    $('#errorConfirmText').addClass('d-none');
                }
            })

        }


        /* ===========================================================
        *  Build phase modals Function
        * ============================================================*/
        function buildPhaseModal(phaseData={title:'Add',modalId:'addPhaseModal'}){
            const phaseModal = `
              <div class="modal fade" id="${phaseData.modalId}" tabindex="-1" aria-labelledby="${phaseData.modalId}Label" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5" id="${phaseData.modalId}Label">${phaseData.title==="Edit"?'Rename':'Add New'} Phase</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <label for="phaseName">Phase Name:</label>
                            <input type="text" id="${phaseData.title.toLowerCase()}PhaseName" class="form-control" placeholder="Enter Phase Name">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" id="${phaseData.title.toLowerCase()}PhaseButton" data-bs-dismiss="modal">Save
                                Phase
                            </button>
                        </div>
                    </div>
                </div>
            </div>`;
            $this.append(phaseModal);
            $(`#${phaseData.title.toLowerCase()}PhaseButton`).on('click', phaseData.title==='Add'?savePhase:updatePhase);

        }


        /* ===========================================================
      *  Save Phase to Database
      * ============================================================*/

        //Define savePhase function outside the kanban plugin
        function savePhase() {
            console.log("save phase")
            const phaseData = {
                id: null,
                name: $(`#${addPhaseModalData.title.toLowerCase()}PhaseName`).val(),
                projectId: 1
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
            let phaseData = settings.phases.filter(data => data.id === parseInt(phaseId,10))[0]
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
            console.log("update phase")
            let phaseData = settings.phases.filter(data => data.id === parseInt(phaseId,10))[0]
            $.ajax({
                url: '/delete-phase',
                method: 'DELETE',
                data: JSON.stringify(phaseData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    settings.tasks = settings.tasks.filter(val => val.phase !== phaseData.id);
                    settings.phases = settings.phases.filter(val => val.id !== phaseData.id);
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
        function saveTask() {
            // Initialize the task object
            const task = {
                id: null,
                name: $('#task-name').val(),
                description: $('#task-description').val(),
                priority: $('#task-priority').val(),
                start_date: $('#task-start-date').val(),
                end_date: $('#task-end-date').val(),
                actual_due_date: null,
                duration: null,
                plan_hours: parseFloat($('#task-plan-hours').val()),
                actual_hours: null,
                progress: 0,
                status: false,
                phase: phaseId,
                parent: parentId,
                group: $('#task-group').val(),
                type: $('#task-type').val(),
                assignees: [],
                open: true,
            };

            $.ajax({
                url: '/add-task',
                method: 'POST',
                data: JSON.stringify(task),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    settings.tasks.push(data)
                    resetTaskModal()
                    render()
                },
                error: function (xhr, status, error) {
                    // Handle errors, e.g., display them in the console or an alert
                    console.log(xhr.responseText);
                },
            });
        }

        /* ===========================================================
        *  update Task to Database
        * ============================================================*/
        function updateTask(task){
            console.log(task)
            $.ajax({
                url: '/update-task',
                method: 'PUT',
                data: JSON.stringify(task),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                },
                error: function (xhr, status, error) {
                    // Handle errors, e.g., display them in the console or an alert
                    console.log(xhr.responseText);
                },
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
        }

        /*
        ================================================
        Build Task View Modal
        ================================================
         */
        function buildTaskViewModal(task){
            const view = `
            <div class="offcanvas offcanvas-end" tabindex="-1" id="taskViewModal" aria-labelledby="taskViewModalLabel">
              <div class="offcanvas-header">
                <h5 class="offcanvas-title" id="taskViewModalLabel">Offcanvas right</h5>
                <button type="button" class="btn-close" data-bs-dismiss="offcanvas" aria-label="Close"></button>
              </div>
              <div class="offcanvas-body">
              
              </div>
            </div>
            `;
            $this.append(view);
        }


        //reset function for phase modal
        function resetPhaseModal(data) {
            $(`#${data.title.toLowerCase()}PhaseName`).val("")
        }


        // Export your saveTask function for usage elsewhere
        $.fn.kanban.savePhase = savePhase;
        $.fn.kanban.saveTask = saveTask;

        initKanban();
        return {
            render: render,
        };
    };
})(jQuery);
