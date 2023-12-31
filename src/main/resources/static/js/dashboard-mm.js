const ctxManMonth = document.getElementById('manMonthChart').getContext('2d');
const ctxManMonthProductivity = document.getElementById('manMonthProductivityChart').getContext('2d');

let manMonthChart;
let manMonthProductivityChart;
let projectList = []
let currentSelectedProject;


function updateYearSelector(projects) {
    projectList = projects || projectList;
    $("#yearSelector").empty();
    const selectedProjectId = $('#projectSelector').val();
    const selectedDepartmentId = $("#tor").val();
    //set Year
    const currentYear = new Date().getFullYear();
    let html = "";

    if (((currentUserRole === "PMO" || currentUserRole === "SDQC") && (selectedDepartmentId === null || selectedDepartmentId === "")) || projectList === null || projectList.length === 0) {
        //set year option from 2010 to current year
        for (let year = 2010; year <= currentYear; year++) {
            html += `<option value="${year}" ${currentYear === year ? 'selected' : ''}>${year}</option>`;
        }
    } else {
        let sDate,eDate;
        if (selectedProjectId) {
            let selectedProject = projectList.find(p => p.id === parseInt(selectedProjectId, 10));
            sDate = new Date(selectedProject.startDate)
            eDate = new Date(selectedProject.endDate)
        }else {
            const {earliestStartDate, latestEndDate} = findEarliestAndLatestDates(projectList);
            sDate = new Date(earliestStartDate)
            eDate = new Date(latestEndDate)
        }

        const yearRange = getYearRange(sDate, eDate)
        console.log(sDate, eDate, yearRange)

        yearRange.forEach(year => {
            html += `<option value="${year}" ${currentYear === year ? 'selected' : ''}>${year}</option>`;
        });

    }

    $("#yearSelector").html(html);
}


function updateMonthSelector(projects) {

    $("#monthSelector").empty();
    projectList = projects || projectList;
    const selectedProjectId = $('#projectSelector').val();
    const selectedDepartmentId = $("#departmentSelector").val();
    let html = '';
    if (((currentUserRole === "PMO" || currentUserRole === "SDQC") && (selectedDepartmentId === null || selectedDepartmentId === "")) || projectList === null || projectList.length === 0) {
        //generate month option from Jan to Dec
        const monthRange = generateUniqueMonthArray(new Date('2021-01-01'), new Date('2021-12-31'))
        let prevMonth = new Date().getMonth();
        monthRange.forEach(month => {
            html += `<option value="${month.id}" ${month.id === prevMonth ? 'selected' : ''}>${month.name}</option>`;

        });
    } else {
        let startMonth, endMonth;
        if (selectedProjectId) {
            let selectedProject = projectList.filter(p => p.id === parseInt(selectedProjectId, 10))[0];
            startMonth = new Date(selectedProject.startDate)
            endMonth = new Date(selectedProject.endDate)
        } else {
            const {earliestStartDate, latestEndDate} = findEarliestAndLatestDates(projectList);
            startMonth = new Date(earliestStartDate)
            endMonth = new Date(latestEndDate)
        }
        console.log('daaate', startMonth, endMonth)
        const monthRange = generateUniqueMonthArray(startMonth, endMonth)
        let prevMonth = new Date().getMonth();
        monthRange.forEach(month => {
            html += `<option value="${month.id}" ${month.id === prevMonth ? 'selected' : ''}>${month.name}</option>`;

        });
    }

    $("#monthSelector").html(html)
}

function findEarliestAndLatestDates(projects) {
    let earliestStartDate = null;
    let latestEndDate = null;

    projects.forEach(project => {
        // Check for the earliest start date
        if (!earliestStartDate || project.startDate < earliestStartDate) {
            earliestStartDate = project.startDate;
        }

        // Check for the latest end date
        if (!latestEndDate || project.endDate > latestEndDate) {
            latestEndDate = project.endDate;
        }
    });

    return {earliestStartDate, latestEndDate};
}

function updateDepartmentSelector(data) {
    $("#departmentSelector").empty();

    let html = "";
    let isSet = false;
    if (currentUserRole === "DH" || currentUserRole === "PM") {
        let department = data.filter(val => val.name === currentDepartmentName)[0];
        html += `<option value="${department.id}" selected>${department.name}</option>`;
        isSet = true;
    } else if (currentUserRole === "PMO" || currentUserRole === "SDQC") {
        html += `<option value="" id="allDepartmentOption" selected>All</option>`;
        const isAllSelected = $("#departmentSelector option:selected").attr("id") === "allDepartmentOption";
        if (isAllSelected) {
            // Hide the kpiRatioHeadingContainer when "All" is selected
            $('#kpiRatioHeadingContainer').hide();
        } else {
            // Show the kpiRatioHeadingContainer for other department selections
            $('#kpiRatioHeadingContainer').show();
        }
        data.forEach((department) => {
            html += `<option value="${department.id}">${department.name}</option>`;
        });
    } else {
        data.forEach((department, index) => {
            html += `<option value="${department.id}"  ${index === 0 ? 'selected' : ''}>${department.name}</option>`;
        })
    }
    $("#departmentSelector").html(html);
}

function getDepartmentAndRender() {
    $.get("/api/get-department-data", function (data) {
        updateDepartmentSelector(data);
    });
}


$("#yearSelector").on("change", function () {
    updateCharts();
});

function getYearRange(startDate, endDate) {
    const startYear = startDate.getFullYear();
    const endYear = endDate.getFullYear();

    const years = [];
    for (let year = startYear; year <= endYear; year++) {
        years.push(year);
    }

    return years;
}

function generateUniqueMonthArray(startDate, endDate) {
    const start = new Date(startDate);
    const end = new Date(endDate);

    const uniqueMonths = [];
    let currentMonth = start;

    while (currentMonth <= end) {
        const monthName = new Intl.DateTimeFormat('en-US', {month: 'long'}).format(currentMonth);
        const id = currentMonth.getMonth() + 1; // Adding 1 to make it 1-based
        const paddedId = id.toString().padStart(2, '0'); // Ensure two-digit string

        // Check if the month is not already in the array
        if (!uniqueMonths.some(month => month.name === monthName)) {
            uniqueMonths.push({id: paddedId, name: monthName});
        }

        currentMonth.setMonth(currentMonth.getMonth() + 1);
    }

    return uniqueMonths;
}

function getProjectByDepartment() {
    let selectedDepartmentId = $("#departmentSelector").val();

    if (currentUserRole !== "PMO" && currentUserRole !== "SDQC") {
        $("#departmentSelector").val(currentDepartmentName)
        selectedDepartmentId = parseInt(currentDepartmentId, 10);
    }

    if (selectedDepartmentId && selectedDepartmentId!=='') {
        console.log('id', selectedDepartmentId)
        $.get(`/api/get-projects?departmentId=${selectedDepartmentId}`, function (projects) {
            console.log('Received projects:', projects);
            if (projects.length === 0) {
                updateChartsWithNoProjects();
            }
            updateProjectSelector(projects);
            updateYearSelector(projects)
            updateMonthSelector(projects)
            updateCharts();
            let projectId = $("#projectSelector").val();
            if(projectId&&projectId!==''){
                $('#kpiRatioHeadingContainer').removeClass('d-none')
               calculateAllKPIs(projectId);
            } else {
                $('#kpiRatioHeadingContainer').addClass('d-none')
            }
        });
    } else {
        console.log('inll')
        $('#kpiRatioHeadingContainer').addClass('d-none')
        $("#projectSelector").empty();
        updateYearSelector(null)
        updateMonthSelector(null)
        updateCharts();
    }
}

$("#departmentSelector").on("change", function () {
    const isAllSelected = $("#departmentSelector option:selected").attr("id") === "allDepartmentOption";
    if (isAllSelected) {
        $('#kpiRatioHeadingContainer').hide();
    } else {
        $('#kpiRatioHeadingContainer').show();
    }
    getProjectByDepartment()

});

function updateProjectSelector(projects) {
    let html = "";
    html += `<option value="">All</option>`;
    projects.forEach((project, index) => {
        html += `<option value="${project.id}">${project.name}</option>`;
    });
    $("#projectSelector").html(html);
}

$("#monthSelector").on("change", function () {
    updateCharts();
});

$("#projectSelector").on("change", function () {
    const selectedProjectId = $("#projectSelector").val();
    const selectedDepartmentId = $("#departmentSelector").val();
    $.get(`/api/get-projects?departmentId=${selectedDepartmentId}`, function (projects) {
        console.log('Received projects:', projects);
        updateYearSelector(projects)
        updateMonthSelector(projects)
        updateCharts();
        if(selectedProjectId&&selectedProjectId!==''){
            $('#kpiRatioHeadingContainer').removeClass('d-none')
            calculateAllKPIs(selectedProjectId);
        }else {
            $('#kpiRatioHeadingContainer').addClass('d-none')
        }
    });

});

function updateChartsWithNoProjects() {
    // You can update the charts with default data or an empty state
    // For example, display a message in the chart or set default values

    const defaultLabels = ['No Projects'];
    const defaultData = [0];

    // Update both charts with default data
    updateManMonthChart(defaultLabels, defaultData, defaultData);
    updateManMonthProductivity(defaultLabels, defaultData);
}


function updateCharts() {
    const selectedYear = $("#yearSelector").val();
    const selectedProjectId = $("#projectSelector").val();
    const selectedDepartmentId = $("#departmentSelector").val();
    const selectedMonth = $("#monthSelector").val();
    if (selectedProjectId) {
        console.log('in selected project')
        $.get(`/api/get-tasks?projectId=${selectedProjectId}&month=${selectedMonth}&year=${selectedYear}`, function (tasks) {
            const planManMonths = calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear);
            const actualManMonths = calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear);
            updateManMonthChart([selectedProjectId], [planManMonths], [actualManMonths]);

            const actualProductivityRatio = calculateManMonthProductivity(actualManMonths, planManMonths);
            updateManMonthProductivity([selectedProjectId], [actualProductivityRatio]);
        });
    } else if (selectedDepartmentId && selectedMonth) {
        console.log('in selcted department and month')
        $.get(`/api/get-projects?departmentId=${selectedDepartmentId}`, function (projects) {
            const projectLabels = [];
            const planManMonths = [];
            const actualManMonths = [];

            if (projects.length === 0) {
                // If the department has no projects, show default or empty data
                updateManMonthChart(projectLabels, planManMonths, actualManMonths);
                updateManMonthProductivity(projectLabels, []);
            }
            let projectsProcessed = 0;

            function processProject(project) {
                const projectId = project.id;
                $.get(`/api/get-tasks?projectId=${projectId}&month=${selectedMonth}&year=${selectedYear}`, function (tasks) {
                    const projectPlanManMonth = calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear);
                    const projectActualManMonth = calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear);
                    const truncatedTitle = project.name.length > 10 ? project.name.substring(0, 10) + '...' : project.name;
                    projectLabels.push(truncatedTitle);
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
                }).fail(function (error) {
                    console.error("Error fetching tasks:", error);
                });
            }

            projects.forEach(processProject);
        });
    } else if (selectedMonth) {
        console.log('in selected month')
        $.get(`/api/get-department-data`, function (departments) {
            const departmentLabels = [];
            const planManMonths = [];
            const actualManMonths = [];

            let departmentsProcessed = 0;

            function processDepartment(department) {
                const departmentId = department.id;

                // Check if there are projects for the department
                $.get(`/api/get-projects?departmentId=${departmentId}`, function (projects) {
                    if (projects.length > 0) {
                        const projectLabels = [];
                        const projectPlanManMonths = [];
                        const projectActualManMonths = [];

                        let projectsProcessed = 0;

                        function processProject(project) {
                            const projectId = project.id;
                            $.get(`/api/get-tasks?projectId=${projectId}&month=${selectedMonth}&year=${selectedYear}`, function (tasks) {
                                const projectPlanManMonth = calculatePlanManMonthForTasks(tasks, selectedMonth, selectedYear);
                                const projectActualManMonth = calculateActualManMonthForTasks(tasks, selectedMonth, selectedYear);
                                const truncatedTitle = project.name.length > 20 ? project.name.substring(0, 20) + '...' : project.name;
                                projectLabels.push(truncatedTitle);
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
                    } else {
                        // No projects for the department, handle this case as needed
                        departmentLabels.push(department.name);
                        planManMonths.push(0);
                        actualManMonths.push(0);
                        departmentsProcessed++;

                        if (departmentsProcessed === departments.length) {
                            updateManMonthChart(departmentLabels, planManMonths, actualManMonths);

                            const allDepartmentsProductivityRatios = departments.map(() => 0);
                            updateManMonthProductivity(departmentLabels, allDepartmentsProductivityRatios);
                        }
                    }
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
    return (planManMonths / actualManMonths) * 100;
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
                backgroundColor: 'rgba(75, 192, 192, 1)', // Turquoise background color
                borderColor: 'rgba(75, 192, 192, 1)', // Turquoise border color
                borderWidth: 1
            },
            {
                label: 'Actual Man Months',
                data: actualManMonths,
                backgroundColor: 'rgba(255, 99, 132, 1)', // Red background color
                borderColor: 'rgba(255, 99, 132, 1)', // Red border color
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
                backgroundColor: 'rgb(74,197,0)', // Blue background color
                borderColor: 'rgb(74,197,0)', // Blue border color
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


