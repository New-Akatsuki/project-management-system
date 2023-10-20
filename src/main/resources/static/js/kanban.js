(function ($) {

    $.fn.kanban = function (options) {



        var $this = $(this);

        var settings = $.extend({
            phase: [],
            tasks: [],
            onChange: function (e, ui) {
            },
            onReceive: function (e, ui) {
            }
        }, options)

        var classes = {
            kanban_board_class: "cd_kanban_board",
            kanban_board_titles_class: "cd_kanban_board_titles",
            kanban_board_title_class: "cd_kanban_board_title",
            kanban_board_blocks_class: "cd_kanban_board_blocks",
            kanban_board_block_class: "cd_kanban_board_block",
            kanban_board_item_class: "cd_kanban_board_block_item",
            kanban_board_item_placeholder_class: "cd_kanban_board_block_item_placeholder",
            kanban_board_item_title_class: "cd_kanban_board_block_item_title",
            kanban_board_item_footer_class: "cd_kanban_board_block_item_footer",
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
            var modalId = $(this).data('bs-target'); // Get the modal target ID
            var $modal = $(modalId);

            if ($modal.length) {
                $modal.addClass('show'); // Add the 'show' class to trigger the animation
            }
        });

        function build_kanban() {

            $this.addClass(classes.kanban_board_class);
            $this.append('<div class="vh-100 ' + classes.kanban_board_blocks_class + '"></div>');

            build_section();
            build_tasks();
            build_subtask();
        }

        function build_section() {
            settings.phase.forEach((item, index, array) => {
                const section_container = `
                    <div id="${item.id}" class="${classes.kb_section_class}">
                        <div class="kb-section-header">
                            <span class="kb-section-header-name">${item.name}</span>
                            <button class="kb-section-header-btn" data-bs-toggle="modal" id="${item.id}-btn" data-bs-target="#exampleModal">+</button>
                        </div>
                        <div id="${item.id}" class="kb-section-body"></div>
                    </div>
                `;
                $this.find('.' + classes.kanban_board_blocks_class).append(section_container);
            });
        }





        function build_tasks() {
            settings.tasks.filter(data => data.parent===null).forEach((item, index, array) => {
                console.log(item)
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
                                <span class="kb-task-due-date">${item.end_date}</span>
                            </div>
                            <div class="kb-task-subtask-layout">
                                <span class="kb-task-subtask"><i class="ri-git-merge-line"></i></span>
                            </div>
                            </div>
                        </div>
                            <div>
                        </div>
                    </div>
            `;
                $this.find(`#${item.phase}`).append(task_container);
                const subtaskIcon = $this.find(`#${item.id} .kb-task-subtask`);
                subtaskIcon.click(function () {
                    const subtaskInfo = $this.find(`#${item.id} .kb-subtask-body`);
                    subtaskInfo.toggle();
                });
            });
        }

        function build_subtask() {
            settings.tasks.filter(data => data.parent!==null).forEach((item, index, array) => {
                console.log(item)
                const sub_task_container = `
                    <div class="kb-subtask-body">
                        <div class="kb-subtask-body-layout">
                        <div class="kb-subtask-status-layout">
                              <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" style="fill: rgba(0, 0, 0, 1);transform: ;msFilter:;"><path d="M12 2C6.486 2 2 6.486 2 12s4.486 10 10 10 10-4.486 10-10S17.514 2 12 2zm0 18c-4.411 0-8-3.589-8-8s3.589-8 8-8 8 3.589 8 8-3.589 8-8 8z"></path><path d="M9.999 13.587 7.7 11.292l-1.412 1.416 3.713 3.705 6.706-6.706-1.414-1.414z"></path></svg>
                        </div>
                        <div class="kb-subtask-name-layout">
                         <span class="kb-subtask-name">${item.name}</span>
                        </div>
                         <div class="kb-subtask-due-date-layout">
                         <span class="kb-subtask-due-date">${item.end_date}</span>
                         </div>             
                         </div>
                        </div>
                    
                `;

                $this.find(`#${item.parent}`).append(sub_task_container);
            });
        }

        function create_task() {
            //Todo:: create task
        }
        function update_task(){
            //Todo:: update task
        }
        function delete_task(){
            //Todo:: delete task
        }

        function kanban_render() {
            build_kanban();
        }

        function build_blocks() {
            settings.titles.forEach(function (item, index, array) {
                var item = '<div class="card-width card-height ' + classes.kanban_board_block_class + '" data-block="' + item + '"></div>';
                $this.find('.' + classes.kanban_board_blocks_class).append(item);
            });
            $("." + classes.kanban_board_block_class).sortable({
                connectWith: "." + classes.kanban_board_block_class,
                containment: "." + classes.kanban_board_blocks_class,
                placeholder: classes.kanban_board_item_placeholder_class,
                scroll: true,
                cursor: "move",
                change: settings.onChange,
                receive: settings.onReceive,
            }).disableSelection();

        }




        build_kanban();



    }

}(jQuery));