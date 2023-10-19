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

