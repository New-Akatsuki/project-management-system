//customizing toolbar..
let toolbaroptions = [
    // Font size
    [{ size: ["small", false, "large", "huge"] }],
    // Text style
    ["bold", "italic", "underline"],
    // Bullet point style...
    [{ list: "ordered" }, { list: "bullet" }],
    // Alignment
    [{ align: [] }],
    // Sub and super script
    [{ script: "sub" }, { script: "super" }],
    // Adding text color and background
    [{ color: [] }, { background: [] }],
    // Adding code snippet or blockquote
    ["code-block", "blockquote"],
    // Image
    ["image"]
];

let aa = {
    modules: {
        toolbar: toolbaroptions,
    },
    theme: "snow"
};

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
