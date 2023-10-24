//customizing toolbar..
let toolbaroptions = [
    //header for text for eg h1, h2,...
    [{header: [1, 2, 3, 4, 5, 6, false]}],
    //text style
    ["bold", "italic", "underline"],
    //bullet point style...
    [{list: "ordered"}, {list: "bullet"}],
    //alignment
    [{align: []}],
    //sub and super script
    [{script: "sub"}, {script: "super"}],
    //adding text color and background
    [{color: []}, {background: []}],
    //adding code snippet or blockquote
    ["code-block", "blockquote"],
    //adding image, link, video, or formula
    ["image", "link"]
]

let aa = {
    modules: {
        toolbar: toolbaroptions,
    },
    theme: "snow"
}


function toggleToolbar(index, editor_id) {
    const containers = document.querySelectorAll('.ql-container');
    const toolbars = document.querySelectorAll('.ql-toolbar');
    const textEditorContainer = document.getElementById(editor_id);

    const showToolbar = () => {
        toolbars[index].style.display = 'block';
        toolbars[index].style.border = '1px solid #ccc';
        containers[index].classList.remove('round-full-border');
    };

    const hideToolbar = () => {
        toolbars[index].style.display = 'none';
        containers[index].classList.add('round-full-border');
    };

    const hidePreviousToolbars = () => {
        toolbars.forEach((toolbar, i) => {
            if (i !== index) {
                toolbar.style.display = 'none';
                containers[i].classList.add('round-full-border');
            }
        });
    };

    textEditorContainer.addEventListener('click', (event) => {
        hidePreviousToolbars();
        showToolbar();
        event.stopPropagation(); // Prevent the event from reaching the document click handler immediately
    });
    window.addEventListener('click', (event) => {
        // Check if the clicked element is outside the text editor and toolbar
        if (!textEditorContainer.contains(event.target) && !toolbars[index].contains(event.target)) {
            hideToolbar();
        }
    });

    // Update hidden inputs or perform other actions when clicking inside the text editor
    // document.addEventListener('click', updateHiddenInputs);

    // If you want to stop propagation when clicking inside the toolbar
    toolbars[index].addEventListener('click', (event) => {
        event.stopPropagation();
    });
}
