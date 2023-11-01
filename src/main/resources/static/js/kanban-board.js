(function ($) {
    $.fn.kanban = function (options) {
        let $this = $(this);
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

        function initKanban() {
            $this.addClass(classes.kanban_board_class);
            //check if element exist, make empty
            if ($this.find('.' + classes.kanban_board_blocks_class).length) {
                $this.find('.' + classes.kanban_board_blocks_class).empty();
            }
            $this.append('<div class="' + classes.kanban_board_blocks_class + '"></div>');
            build_section();
            build_tasks();
            build_subtask();
            // enableDragAndDrop();
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
        }

        /* ===========================================================
        *  Build Sections UI Function
        * ============================================================*/
        function build_section() {
            settings.phases.forEach((item, index, array) => {
                const section_container = `
                    <div id="${item.id}-phase" class="${classes.kb_section_class}">
                        <div class="kb-section-header">
                            <span class="kb-section-header-name">${item.name}</span>
                            <button class="kb-section-header-btn" id="${item.id}-btn">+</button>
                        </div>
                        <div id="${item.id}-phase-body" class="kb-section-body"></div>
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
            });
        }

        /* ===========================================================
       *  Build Tasks UI Function
       * ============================================================*/
        function build_tasks() {
            settings.tasks.filter(data => data.parent === null).forEach((item, index, array) => {
                const numberOfSubtasks = settings.tasks.filter(data => data.parent === item.id).length;
                const height = numberOfSubtasks * 50;
                const task_container = `
                    <div id="${item.id}" class="kb-task mb-2">
                        <div class="kb-task-body">
                            <div class="kb-task-body-layout">
                                <div class="kb-task-name-layout">
                                    <span class="kb-task-name">${item.name}</span>
                                </div>
                                <div class="kb-task-status-layout">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M9.999 13.587 7.7 11.292l-1.412 1.416 3.713 3.705 6.706-6.706-1.414-1.414z"></path></svg>
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
                                  <button id="${item.id}-toggle-subtask-btn" class="toggle-subtask-icon toggle-subtask-icon-active" ${numberOfSubtasks > 0 ? '' : 'disabled'}><i class="ri-git-merge-line"></i></button>
                                </div>
                            </div>
                            <div id="${item.id}-subtask-block" class="hide subtask-block"></div>
                            <button id="${item.id}-add-subtask-btn" class="add-subtask-btn">Add Subtask</button>
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

                $(`#${item.id} .kb-task-body-layout`).on('click', () => {
                    //show modal
                    $('#exampleModal').modal('show');
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
                subtaskIcon.addEventListener('click', () => {
                    const subtaskBlock = document.getElementById(`${item.id}-subtask-block`);
                    subtaskBlock.classList.toggle('hide');
                    //for animation
                    task.style.height = subtaskBlock.classList.contains('hide') ? '165px' : `${height + 165}px`;
                    const addSubtaskBtn = document.getElementById(`${item.id}-add-subtask-btn`);
                    subtaskIcon.classList.toggle('toggle-subtask-icon-active');
                });
            });
        }

        /* ===========================================================
       *  Build SubTasks UI Function
       * ============================================================*/
        function build_subtask() {
            settings.tasks.filter(data => data.parent !== null).forEach((item, index, array) => {
                item.phase = settings.tasks.filter(data => data.id === item.parent)[0].phase;
                const sub_task_container = `
                    <div id="${item.id}" class="kb-subtask-body">
                                <span class="flex-1">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M9.999 13.587 7.7 11.292l-1.412 1.416 3.713 3.705 6.706-6.706-1.414-1.414z"></path></svg>
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
        *  Build SubTasks UI Function
        * ============================================================*/
        function enableDragAndDrop() {
            $('.' + classes.kb_section_body_class).sortable({
                connectWith: '.' + classes.kb_section_body_class,
                placeholder: 'ui-state-highlight',
                items: '.' + classes.kb_task_class,
                start: function (e, ui) {
                    ui.item.data('originalSection', ui.item.closest('.' + classes.kb_section_class));
                },
                update: function (e, ui) {
                    const task = ui.item;
                    const originalSection = ui.item.data('originalSection');
                    const newSection = task.closest('.' + classes.kb_section_class);

                    const taskId = task.attr('id');
                    const originalSectionId = originalSection.attr('id');
                    const newSectionId = newSection.attr('id');

                    // Call your custom change and receive functions here
                    settings.onChange({
                        task,
                        originalSection,
                        newSection,
                        taskId,
                        originalSectionId,
                        newSectionId
                    });
                    settings.onReceive({
                        task,
                        originalSection,
                        newSection,
                        taskId,
                        originalSectionId,
                        newSectionId
                    });
                },
            }).disableSelection();
        }


        /* ===========================================================
      *  Save Phase to Database
      * ============================================================*/

        //Define savePhase function outside the kanban plugin
        function savePhase() {
            console.log("save phase")
            const phaseData = {
                id: null,
                name: $('#phaseName').val(),
                projectId: 1
            };

            $.ajax({
                url: '/add-phase',
                method: 'POST',
                data: JSON.stringify(phaseData),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    $('#phaseName').val("")
                    settings.phases.push(data)
                    render()
                },
                error: function (xhr, status, error) {
                    console.log(xhr.responseText);
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
            console.log(task)

            $.ajax({
                url: '/add-task',
                method: 'POST',
                data: JSON.stringify(task),
                contentType: 'application/json; charset=utf-8',
                dataType: 'json',
                success: function (data) {
                    console.log(data)
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


        // Export your saveTask function for usage elsewhere
        $.fn.kanban.savePhase = savePhase;
        $.fn.kanban.saveTask = saveTask;

        initKanban();
        return {
            render: render,
        };
    };
})(jQuery);
