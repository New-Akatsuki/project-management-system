$(document).ready(function () {
    $.ajax({
        url: '/get-task-data',
        method: 'GET',
        success: function (data) {
            console.log('data', data);

            /*================= set config ==================*/
            gantt.config.columns = [
                {name: "name", label: "Task name", tree: true},
                {name: "start_date", label: "Start time", align: "center"},
                {name: "duration", label: "Duration", align: "center"},
                {name: "add", label: "", width: 44},
            ];

            /*================= set config task modal ==================*/
            gantt.config.lightbox.sections = [
                {name: "name", height: 50, map_to: "name", type: "input_text", focus: true},
                {name: "description", height: 75, map_to: "description", type: "textarea", focus: true},
                {name: "time", height: 50, type: "duration", map_to: "auto"},
                {
                    name: "type",
                    type: "select",
                    height: 50,
                    options: [
                        {key: "task", label: "Task"},
                        {key: "milestone", label: "Milestone"},
                        {key: "project", label: "Phase"}],
                    map_to: "type"
                },
                {
                    name: "priority",
                    type: "select",
                    height: 50,
                    options: [
                        {key: "high", label: "High"},
                        {key: "normal", label: "Normal"},
                        {key: "low", label: "Low"}
                    ],
                    map_to: "priority",
                },{name:"owner",height:60, type:"multiselect", options:[
                    {key: "1", label: "Mg Mg"},
                    {key: "2", label: "Aung Aung"},
                    {key: "3", label: "Aye Aye"}
                    ],
                    map_to:"assignees", unassigned_value:5 },
                {
                    name: "assignees",
                    type: "checkbox",
                    height: "fit-content",
                    vertical: true, // To stack checkboxes vertically
                    options: [
                        {key: "1", label: "Mg Mg"},
                        {key: "2", label: "Aung Aung"},
                        {key: "3", label: "Aye Aye"}
                    ],
                    map_to: "assignees",
                },
                {name: "plan_hour", height: 50, map_to: "plan_hours", type: "input_number", focus: true},

            ];

            /*================= set config of project ==================*/
            gantt.config.lightbox.project_sections = [
                { name: "name", height: 50, map_to: "name", type: "text", focus: true },
                { name: "description", height: 70, map_to: "description", type: "textarea", focus: true },
                {
                    name: "type",
                    type: "select",
                    height: 50,
                    options: [
                        {key: "task", label: "Task"},
                        {key: "milestone", label: "Milestone"},
                        {key: "project", label: "Phase"}],
                    map_to: "type"
                },
                { name: "time", type: "duration", readonly: true, map_to: "auto" }
            ];
            /*================= set label name of add task modal  ==================*/
            gantt.locale.labels.section_name = "Name";
            gantt.locale.labels.section_description = "Description"
            gantt.locale.labels.section_priority = "Priority";
            gantt.locale.labels.section_assignees = "Assignees";
            gantt.locale.labels.section_plan_hour = "Plan Hours";

            /*================= when click link, show dependencies ==================*/
            gantt.attachEvent("onLinkClick", function (id) {
                let link = this.getLink(id),
                    src = this.getTask(link.source),
                    trg = this.getTask(link.target),
                    types = this.config.links;

                let first = "", second = "";
                switch (link.type) {
                    case types.finish_to_start:
                        first = "finish";
                        second = "start";
                        break;
                    case types.start_to_start:
                        first = "start";
                        second = "start";
                        break;
                    case types.finish_to_finish:
                        first = "finish";
                        second = "finish";
                        break;
                }

                gantt.message("Must " + first + " <b>" + src.name + "</b> to " + second + " <b>" + trg.name + "</b>");
            });


           /*================= create new task ==================*/
            gantt.attachEvent("onAfterTaskAdd", function (id, item) {
                gantt.changeTaskId(id, 124);
                //send data to server
                $.ajax({
                    url: '/add-task',
                    method: 'POST',
                    data: JSON.stringify(item),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (response) {
                        gantt.changeTaskId(124, response.id);
                        gantt.render();
                        console.log('back data -> ', response);
                    }
                });
            });

            /*================= update task ==================*/
            gantt.attachEvent("onAfterTaskUpdate", function (id, item) {
                console.log('onAfterTaskUpdate clicked ', id, item);
                //send data to server
                $.ajax({
                    url: '/update-task',
                    method: 'PUT',
                    data: JSON.stringify(item),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (response) {
                        gantt.render();
                        console.log('back update data -> ', response);
                    }
                });
            });

            /*================= delete task ==================*/
            gantt.attachEvent("onAfterTaskDelete", function (id, item) {
                console.log('onAfterTaskDelete clicked ', id, item);
                //send data to server
                $.ajax({
                    url: '/delete-task',
                    method: 'DELETE',
                    data: JSON.stringify(item),
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (response) {
                        console.log('back delete data -> ', response)
                        gantt.render();
                    }
                });
            });

            gantt.templates.task_text = function (start, end, task) {
                return task.name;
            };
            gantt.templates.lightbox_header = function (start, end, task) {
                return 'Create New Task';
            }
            gantt.templates.date_format = "%Y-%m-%d";


            gantt.config.xml_date = "%Y-%m-%d";

            gantt.init('gantt');

            gantt.parse({data: data, links: []});

        }
    });


    $(".add-task-button").click(function () {
        gantt.createTask({id: "4", name: "New Task", duration: 1, start_date: null, type: "task"});
        gantt.showLightbox();
    });
});