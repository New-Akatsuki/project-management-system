(function ($) {
    $.fn.ganttChart = function (options, phases = []) {
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
            parent: null,
            phase: null,
            group: "",
            type: "task",
            assignees: [],
            open: true,
        };

        let vendor = $.extend(
            {
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


        function mapForGantt(phases = []) {

            //get all task that parent is null and set parent value to phaseId of task
            vendor.tasks.filter(task => task.parent === null && task.type !== 'project').map(task => {
                task.parent = task.phase + 'p'
            })
            phases.forEach(phase => {
                let task = {
                    id: phase.id + 'p',
                    name: phase.name,
                    description: "",
                    priority: "",
                    start_date: "",
                    end_date: "",
                    actual_due_date: "",
                    duration: null,
                    plan_hours: 0.0,
                    actual_hours: 0.0,
                    status: false,
                    parent: null,
                    phase: null,
                    group: "",
                    type: "project",
                    assignees: [],
                    open: true,
                };

                // const child = vendor.tasks.filter(val=>val.parent === task.id)[0];
                // if(child){
                //     task.start_date = child.start_date
                //     task.end_date = child.start_date
                // }
                vendor.tasks.push(task)
            })
        }


        function initGantt() {


            mapForGantt(phases)
            //config gantt chart
            gantt.templates.task_text = function (start, end, task) {
                return task.name + ' : ' + end.toLocaleString('en-US', {
                    month: 'short',
                    day: 'numeric'
                });
            };
            gantt.config.bar_height = 30;
            gantt.templates.date_format = "%Y-%m-%d";
            gantt.config.xml_date = "%Y-%m-%d";
            // gantt.config.resize_rows = true;
            gantt.config.sort = true;
            gantt.config.columns[1].sort = false;
            gantt.config.columns[2].sort = false;

            //render gantt chart
            gantt.init('gantt');
            gantt.parse({data: vendor.tasks, links: []});

            //create phase modal
            var colHeader = '<a data-bs-toggle="modal" data-bs-target="#addPhaseModal"><i class="bx bx-plus align-middle fs-5"></i></a>';

            gantt.config.columns = [
                {name:"wbs", label:"WBS", width:40, template:gantt.getWBSCode },
                {name: "name", label: 'Task Name', tree: true, width: '*', resize: true},
                {name: "priority", label: 'Pri', width: 50, align: "center",template: function (task) {
                        const priorityClass = task.priority === 'high' ? 'priority-high' :
                            task.priority === 'medium' ? 'priority-medium' : 'priority-low';
                        return task.priority===''?'':`<span class="col-3"><span class="${priorityClass} text-block fs-sx min-w-50px">${task.priority}</span></span>`;
                    },
                },
                {name: "duration", label: 'Dur', width: 50, align: "center"},
                {
                    name: "buttons", label: colHeader, width: 44, align: "center", template: function (task) {
                        return (
                            `<a id="${task.id}-add-task" onclick="$.fn.ganttChart.showAddTaskModal(this)"><i class="bx bx-plus fs-5 align-middle"></i></a>`
                        );
                    }
                }
            ];

            gantt.attachEvent("onTaskDblClick", function (id, e) {
                let task = gantt.getTask(id)
                if (id.includes('p')) {
                    console.log('it is phase')
                    $(`#editPhaseName`).val(task.name)
                    $.fn.kanban.setPhaseAndParentIds(task.id.replace("p", ""), null)
                    //show modal
                    $(`#editPhaseModal`).modal('show');
                } else {
                    $('#taskViewModal').offcanvas('hide');
                    $.fn.kanban.buildTaskViewModal(parseInt(id));
                    $('#taskViewModal').offcanvas('show');
                }
            });

            gantt.attachEvent("onAfterTaskUpdate", function (id, item) {
                console.log('onAfterTaskUpdate clicked ', id, item);
            });
            gantt.attachEvent("onRowDragEnd", function (id, item) {
                console.log('onRowDragEnd clicked ', id, item);
            });
            gantt.attachEvent("onRowDragEnd", function (id, item) {
                console.log('onRowDragEnd clicked ', id, item);
            });


        }

        function showAddTaskModal(taskElement) {


            let taskId = taskElement.id.split('-')[0]
            let task = gantt.getTask(taskId)
            let minDate = vendor.startDate;
            let maxDate = vendor.endDate;


            if (task.phase && task.type !== 'project') {
                console.log('it is task')
                $.fn.kanban.setPhaseAndParentIds(task.phase, task.id)
                minDate = task.start_date
                maxDate = task.end_date
            } else {
                console.log('it is phase')
                $.fn.kanban.setPhaseAndParentIds(task.id.replace("p", ""), null)
            }

            $.fn.kanban.buildAddPhaseModal(minDate,maxDate);

            //show modal
            $('#exampleModal').modal('show');
        }

        initGantt()

        //export function
        $.fn.ganttChart.showAddTaskModal = showAddTaskModal
    };
})(jQuery);

