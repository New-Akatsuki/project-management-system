(function ($) {
    $.fn.ganttChart = function (options, phases = []) {
        let $this = $(this);

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

            vendor.tasks.filter(task => task.type !== 'project' && task.id === task.parent).map(task => {
                task.parent = null;
            })
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
                vendor.tasks.push(task)
            })
        }


        function initGantt() {

            gantt.clearAll()

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

            gantt.config.sort = true;
            gantt.config.work_time = true;
            gantt.config.skip_off_time = true;

            gantt.config.project_start = vendor.startDate;
            gantt.config.project_end = vendor.endDate;

            //render gantt chart
            gantt.init('gantt');
            gantt.parse({data: vendor.tasks, links: []});

            //create phase modal
            var colHeader = '<a data-bs-toggle="modal" data-bs-target="#addPhaseModal"><i class="bx bx-plus align-middle fs-5"></i></a>';

            gantt.config.columns = [
                {name: "wbs", label: "WBS", width: 40, template: gantt.getWBSCode},
                {name: "name", label: 'Task Name', tree: true, width: '*', resize: true},
                {
                    name: "priority", label: 'Pri', width: 50, align: "center", template: function (task) {
                        const priorityClass = task.priority === 'high' ? 'priority-high' :
                            task.priority === 'medium' ? 'priority-medium' : 'priority-low';
                        return task.priority === '' ? '' : `<span class="col-3"><span class="${priorityClass} text-block fs-sx min-w-50px">${task.priority}</span></span>`;
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
        }


        gantt.attachEvent("onTaskDblClick", function (id, e) {
            let task = gantt.getTask(id)
            if (id.includes('p')) {
                console.log('it is phase')
                // $('#deleteBtnPlaceInEditPhaseModal').append(`<button id="deleteBtnInEditPhaseModal" type="button" class="btn btn-danger" data-bs-dismiss="modal"><i class="bx bx-trash-alt"></i></button>`);
                $(`#editPhaseName`).val(task.name)
                $.fn.kanban.setPhaseAndParentIds(task.id.replace("p", ""), null)
                //show modal
                $(`#editPhaseModal`).modal('show');
            } else {
                $('#taskViewModal').offcanvas('hide');
                $.fn.kanban.buildTaskViewModal(parseInt(id), task);
                $('#taskViewModal').offcanvas('show');
            }
        });

        let els = document.querySelectorAll("input[name='scale']");
        for (let i = 0; i < els.length; i++) {
            els[i].onclick = function (e) {
                let el = e.target;
                let value = el.value;
                setScaleConfig(value);
                gantt.render();
            };
        }


        /*================= update task ==================*/
        gantt.attachEvent("onAfterTaskUpdate", function (id, item) {
            let task = {...item};
            task.assignees = task.assignees.map(val => parseInt(val.id))
            task.parent = task.parent.toString().includes('p') ? null : task.parent;
            task.parent = parseInt(task.parent)
            task.start_date = formatDateToYYYYMMDD(task.start_date)
            task.end_date = formatDateToYYYYMMDD(task.end_date)
            $.fn.kanban.updateTask(task, false);
            gantt.render()
        });


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

            $.fn.kanban.buildAddPhaseModal(minDate, maxDate);

            //show modal
            $('#exampleModal').modal('show');
        }

        function formatDateToYYYYMMDD(date) {
            const year = date.getFullYear();
            const month = (date.getMonth() + 1).toString().padStart(2, '0');
            const day = date.getDate().toString().padStart(2, '0');
            return `${year}-${month}-${day}`;
        }

        initGantt()

        //export function
        $.fn.ganttChart.showAddTaskModal = showAddTaskModal
    };
})(jQuery);

function setScaleConfig(level) {
    switch (level) {
        case "day":
            gantt.config.scales = [
                {unit: "day", step: 1, format: "%d %M"}
            ];
            gantt.config.scale_height = 27;
            break;
        case "week":
            var weekScaleTemplate = function (date) {
                var dateToStr = gantt.date.date_to_str("%d %M");
                var endDate = gantt.date.add(gantt.date.add(date, 1, "week"), -1, "day");
                return dateToStr(date) + " - " + dateToStr(endDate);
            };
            gantt.config.scales = [
                {unit: "week", step: 1, format: weekScaleTemplate},
                {unit: "day", step: 1, format: "%D"}
            ];
            gantt.config.scale_height = 50;
            break;
        case "month":
            gantt.config.scales = [
                {unit: "month", step: 1, format: "%F, %Y"},
                {unit: "day", step: 1, format: "%j, %D"}
            ];
            gantt.config.scale_height = 50;
            break;
        case "year":
            gantt.config.scales = [
                {unit: "year", step: 1, format: "%Y"},
                {unit: "month", step: 1, format: "%M"}
            ];
            gantt.config.scale_height = 90;
            break;
    }
}