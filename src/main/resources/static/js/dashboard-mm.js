
    const ctxManMonth = document.getElementById('manMonthChart').getContext('2d');
    const ctxManMonthProductivity = document.getElementById('manMonthProductivityChart').getContext('2d');

    let manMonthChart;
    let manMonthProductivityChart;


    function updateYearSelector() {
    const currentYear = new Date().getFullYear();
    let html = "<option value=''>Select a year</option>";
    for (let year = 2010; year <= currentYear; year++) {
    html += `<option value="${year}">${year}</option>`;
}
    $("#yearSelector").html(html);
}
    updateYearSelector();

    function updateDepartmentSelector(data) {
    let html = "<option value=''>Select a department</option>";
    for (const department of data) {
    html += `<option value="${department.id}">${department.name}</option>`;
}
    $("#departmentSelector").html(html);
}

    $.get("/api/get-department-data", function (data) {
    updateDepartmentSelector(data);
});


    $("#yearSelector").on("change", function () {
    updateCharts();
});


    $("#departmentSelector").on("change", function () {
    const selectedDepartmentId = $(this).val();
    if (selectedDepartmentId) {
    $.get(`/api/get-projects?departmentId=${selectedDepartmentId}`, function (projects) {
    updateProjectSelector(projects);
    updateCharts();
});
} else {
    updateProjectSelector([]);
    updateCharts();
}
});

    function updateProjectSelector(projects) {
    let html = "<option value=''>Select a project</option>";
    for (const project of projects) {
    html += `<option value="${project.id}">${project.name}</option>`;
}
    $("#projectSelector").html(html);
}

    $("#monthSelector").on("change", function () {
    updateCharts();
});

    $("#projectSelector").on("change", function () {
    updateCharts();
});

    function updateCharts() {
    const selectedYear = $("#yearSelector").val();
    const selectedProjectId = $("#projectSelector").val();
    const selectedDepartmentId = $("#departmentSelector").val();
    const selectedMonth = $("#monthSelector").val();



    if (selectedProjectId) {
    $.get(`/api/get-tasks?projectId=${selectedProjectId}&month=${selectedMonth}&year=${selectedYear}`, function (tasks) {
    const planManMonths = calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear);
    const actualManMonths = calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear);
    updateManMonthChart([selectedProjectId], [planManMonths], [actualManMonths]);

    const actualProductivityRatio = calculateManMonthProductivity(actualManMonths, planManMonths);
    updateManMonthProductivity([selectedProjectId], [actualProductivityRatio]);
});
} else if (selectedDepartmentId && selectedMonth) {
    $.get(`/api/get-projects?departmentId=${selectedDepartmentId}`, function (projects) {
    const projectLabels = [];
    const planManMonths = [];
    const actualManMonths = [];

    let projectsProcessed = 0;

    function processProject(project) {
    const projectId = project.id;
    $.get(`/api/get-tasks?projectId=${projectId}&month=${selectedMonth}&year=${selectedYear}`, function (tasks) {
    const projectPlanManMonth = calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear);
    const projectActualManMonth = calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear);
    projectLabels.push(project.name);
    planManMonths.push(projectPlanManMonth);
    actualManMonths.push(projectActualManMonth);

    projectsProcessed++;

    if (projectsProcessed === projects.length) {
    console.log("All Projects Processed. Updating Chart.");
    updateManMonthChart(projectLabels, planManMonths, actualManMonths);

    const departmentProductivityRatios = projects.map((proj, index) => {
    const projActualManMonths = actualManMonths[index];
    const projPlanManMonths = planManMonths[index];
    return calculateManMonthProductivity(projActualManMonths, projPlanManMonths);
});

    updateManMonthProductivity(projectLabels, departmentProductivityRatios);
}
});
}

    projects.forEach(processProject);
});
} else if (selectedMonth) {
    $.get(`/api/get-department-data`, function (departments) {
    const departmentLabels = [];
    const planManMonths = [];
    const actualManMonths = [];

    let departmentsProcessed = 0;

    function processDepartment(department) {
    const departmentId = department.id;
    $.get(`/api/get-projects?departmentId=${departmentId}`, function (projects) {
    const projectLabels = [];
    const projectPlanManMonths = [];
    const projectActualManMonths = [];

    let projectsProcessed = 0;

    function processProject(project) {
    const projectId = project.id;
    $.get(`/api/get-tasks?projectId=${projectId}&month=${selectedMonth}&year=${selectedYear}`, function (tasks) {
    const projectPlanManMonth = calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear);
    const projectActualManMonth = calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear);
    projectLabels.push(project.name);
    projectPlanManMonths.push(projectPlanManMonth);
    projectActualManMonths.push(projectActualManMonth);

    projectsProcessed++;

    if (projectsProcessed === projects.length) {
    const departmentPlanManMonth = projectPlanManMonths.reduce((sum, val) => sum + val, 0);
    const departmentActualManMonth = projectActualManMonths.reduce((sum, val) => sum + val, 0);

    departmentLabels.push(department.name);
    planManMonths.push(departmentPlanManMonth);
    actualManMonths.push(departmentActualManMonth);

    departmentsProcessed++;

    if (departmentsProcessed === departments.length) {
    updateManMonthChart(departmentLabels, planManMonths, actualManMonths);

    const allDepartmentsProductivityRatios = departments.map((dept, index) => {
    const deptActualManMonths = actualManMonths[index];
    const deptPlanManMonths = planManMonths[index];
    return calculateManMonthProductivity(deptActualManMonths, deptPlanManMonths);
});

    updateManMonthProductivity(departmentLabels, allDepartmentsProductivityRatios);
}
}
});
}

    projects.forEach(processProject);
});
}

    departments.forEach(processDepartment);
});
}
}
    let workingDayPerHour = 7.5;
    let workingDayPerMonth = 20;

    function calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear) {
    let planManMonth = 0;
    for (const task of tasks) {
    if (
    (task.start_date && task.start_date.startsWith(`${selectedYear}-${selectedMonth}`)) ||
    (task.end_date && task.end_date.startsWith(`${selectedYear}-${selectedMonth}`))
    ) {
    planManMonth += task.plan_hours / (workingDayPerHour * workingDayPerMonth);
}
}
    return planManMonth;
}

    function calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear) {
    let actualManMonth = 0;
    for (const task of tasks) {
    if (
    (task.start_date && task.start_date.startsWith(`${selectedYear}-${selectedMonth}`)) ||
    (task.actual_due_date && task.actual_due_date.startsWith(`${selectedYear}-${selectedMonth}`))
    ) {
    actualManMonth += task.actual_hours / (workingDayPerHour * workingDayPerMonth);
}
}
    return actualManMonth;
}

    function calculateManMonthProductivity(actualManMonths, planManMonths) {
    if (planManMonths === 0) {
    return 0;
}
    return (actualManMonths / planManMonths) * 100;
}
    function updateManMonthChart(labels, planManMonths, actualManMonths) {
    if (manMonthChart) {
    manMonthChart.destroy();
}

    const chartData = {
    labels: labels,
    datasets: [
{
    label: 'Plan Man Months',
    data: planManMonths,
    backgroundColor: 'rgba(54, 162, 235, 0.2)',
    borderColor: 'rgba(54, 162, 235, 1)',
    borderWidth: 1
},
{
    label: 'Actual Man Months',
    data: actualManMonths,
    backgroundColor: 'rgba(255, 99, 132, 0.2)',
    borderColor: 'rgba(255, 99, 132, 1)',
    borderWidth: 1
}
    ]
};

    manMonthChart = new Chart(ctxManMonth, {
    type: 'bar',
    data: chartData,
    options: {
    scales: {
    y: {
    beginAtZero: true
}
}
}
});
}

    function updateManMonthProductivity(labels, actualProductivityRatio) {
    if (manMonthProductivityChart) {
    manMonthProductivityChart.destroy();
}

    const chartData = {
    labels: labels,
    datasets: [
{
    label: 'Actual Productivity Ratio',
    data: actualProductivityRatio,
    backgroundColor: 'rgba(255, 99, 132, 0.2)',
    borderColor: 'rgba(255, 99, 132, 1)',
    borderWidth: 1,
},
    ],
};

    manMonthProductivityChart = new Chart(ctxManMonthProductivity, {
    type: 'bar',
    data: chartData,
    options: {
    scales: {
    y: {
    beginAtZero: true,
},
},
},
});
}

    updateCharts();

    $(document).ready(function() {
    $('.year-select').select2();
    $('.month-select').select2();
    $('.department-select').select2();
    $('.project-select').select2();
});

