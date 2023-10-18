document.addEventListener('DOMContentLoaded', () => {
    const board = document.querySelector('.board');
    const modal = new bootstrap.Modal(document.getElementById('exampleModal'));
    const subtaskModal = new bootstrap.Modal(document.getElementById('subtaskModal'));
    const modalForm = document.getElementById('modal-form');
    const subtaskForm = document.getElementById('subtask-form');
    const addSectionButton = document.getElementById('add-section-button');
    let currentSection = null;
    let currentTaskElement = null;

    addSectionButton.addEventListener('click', () => {
        const sectionName = prompt('Enter section name:');
        if (sectionName) {
            const section = createSection(sectionName);
            board.appendChild(section);
        }
    });

    modalForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const taskName = document.getElementById('modal-task-name').value;
        const dueDate = document.getElementById('modal-due-date').value;
        const description = document.getElementById('modal-description').value;

        if (taskName && dueDate && description) {
            if (currentSection) {
                if (currentTaskElement) {
                    const subtask = createTask(taskName, dueDate, description, true);
                    const subtasksContainer = currentTaskElement.querySelector('.subtasks');
                    if (subtasksContainer) {
                        subtasksContainer.appendChild(subtask);
                    } else {
                        console.log("Error: subtasksContainer is null.");
                    }
                } else {
                    const task = createTask(taskName, dueDate, description, false);
                    currentSection.querySelector('.tasks').appendChild(task);
                }
            }
            document.getElementById('modal-task-name').value = '';
            document.getElementById('modal-due-date').value = '';
            document.getElementById('modal-description').value = '';

            modal.hide();
        }
    });

    board.addEventListener('click', (e) => {
        if (currentTaskElement) {
            // Clear the inputs if a task or subtask is already displayed
            document.getElementById('modal-task-name').value = '';
            document.getElementById('modal-due-date').value = '';
            document.getElementById('modal-description').value = '';
        }

        const taskElement = e.target.closest('.task');
        if (taskElement) {
            currentSection = taskElement.closest('.section');
            displayTaskDetails(taskElement);

            // Check if it's a main task (not a subtask)
            if (!taskElement.classList.contains('subtask')) {
                // Log subtasks if available
                const subtasks = taskElement.querySelectorAll('.subtask');
                if (subtasks.length > 0) {
                    console.log("Subtasks for the selected main task:");
                    subtasks.forEach((subtask, index) => {
                        console.log("Subtask " + (index + 1));
                        console.log("Task Name: " + subtask.querySelector('p').textContent);
                        console.log("Due Date: " + subtask.querySelector('.due-date').textContent);
                        console.log("Description: " + subtask.querySelector('.description').textContent);
                    });
                } else {
                    console.log("No subtasks found for the selected main task.");
                }
            }
        }
    });

    const addSubtaskButton = document.createElement('button');
    addSubtaskButton.id = 'add-subtask-button';
    addSubtaskButton.textContent = 'Add Subtask';
    addSubtaskButton.classList.add('btn', 'btn-primary');
    modalForm.querySelector('.modal-footer').prepend(addSubtaskButton);

    addSubtaskButton.addEventListener('click', () => {
        if (currentSection && currentTaskElement) {
            subtaskModal.show();
        } else if (currentSection && !currentTaskElement) {
            alert("Select a main task first.");
        }
    });

    subtaskForm.addEventListener('submit', (e) => {
        e.preventDefault();
        const subtaskName = document.getElementById('subtask-name').value;
        const subtaskDueDate = document.getElementById('subtask-due-date').value;
        const subtaskDescription = document.getElementById('subtask-description').value;

        if (subtaskName && subtaskDueDate && subtaskDescription) {
            const subtask = createTask(subtaskName, subtaskDueDate, subtaskDescription, true);
            const subtasksContainer = currentTaskElement.querySelector('.subtasks');
            if (subtasksContainer) {
                subtasksContainer.appendChild(subtask);
            }
            document.getElementById('subtask-name').value = '';
            document.getElementById('subtask-due-date').value = '';
            document.getElementById('subtask-description').value = '';

            subtaskModal.hide();
        }
    });

    function createSection(sectionName) {
        const section = document.createElement('div');
        section.classList.add('section');
        section.innerHTML = `
            <div class="section-header">
                <p class="section-name">${sectionName}</p>
                <span id="add-task-button" class="add-task-btn" data-bs-toggle="modal" data-bs-target="#exampleModal">+</span>
            </div>
            <div class="tasks"></div>
        `;

        interact(section).dropzone({
            accept: '.task',
            ondrop: (event) => {
                const taskElement = event.relatedTarget;
                const newSection = section.querySelector('.tasks');
                newSection.appendChild(taskElement);
            },
        });

        const taskButton = section.querySelector('#add-task-button');
        taskButton.addEventListener('click', () => {
            currentTaskElement = null;
            currentSection = section;
            modal.show();
        });

        return section;
    }

    function createTask(taskName, dueDate, description, isSubtask) {
        const task = document.createElement('li');
        task.classList.add(isSubtask ? 'subtask' : 'task');

        task.innerHTML = `
        <p>${taskName}</p>
        <p>Due Date: <span class="due-date">${dueDate}</span></p>
        <p>Description: <span class="description">${description}</span></p>
    `;

        if (!isSubtask) {
            const subtasksContainer = document.createElement('ul');
            subtasksContainer.classList.add('subtasks');
            task.appendChild(subtasksContainer);

            interact(task).draggable({
                onstart: (event) => {
                    event.target.style.zIndex = 9999;
                },
                onmove: (event) => {
                    event.target.style.transform = `translate(${event.dx}px, ${event.dy}px)`;
                },
                onend: (event) => {
                    event.target.style.zIndex = 0;
                    event.target.style.transform = 'translate(0, 0)';
                },
            });
        } else {
            interact(task).dropzone({
                accept: '.subtask',
                ondrop: (event) => {
                    const subtaskElement = event.relatedTarget;
                    const subtasksContainer = task.querySelector('.subtasks');
                    if (subtaskElement && subtasksContainer) {
                        subtasksContainer.appendChild(subtaskElement);
                    }
                },
            });
        }

        return task;
    }

    function displayTaskDetails(taskElement) {
        const taskName = taskElement.querySelector('p').textContent;
        const dueDate = taskElement.querySelector('.due-date').textContent;
        const description = taskElement.querySelector('.description').textContent;

        document.getElementById('modal-task-name').value = taskName;
        document.getElementById('modal-due-date').value = dueDate;
        document.getElementById('modal-description').value = description;

        currentTaskElement = taskElement;
        subtaskModal.hide();
        modal.show();
    }
});
