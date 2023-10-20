document.getElementById("issue_id").addEventListener("input", function () {
    validateInput("issue_id");
});

document.getElementById("title").addEventListener("input", function () {
    validateInput("title");
});

document.getElementById("date").addEventListener("input", function () {
    validateInput("date");
});

document.getElementById("create-issue").addEventListener("input", function () {
    // Enable or disable the submit button based on input validity
    var issue_id = document.getElementById("issue_id").value;
    var title = document.getElementById("title").value;
    var date = document.getElementById("date").value;

    // Validate issue_id and issue_title
    var isIssue_idValid = issue_id !== "";
    var isTitleValid = title !== "";
    var isDateValid = date !== "";

    // Enable or disable the submit button
    var submitButton = document.getElementById("submitButton");
    submitButton.disabled = !(isIssue_idValid && isTitleValid && isDateValid);
});

function validateInput(inputId) {
    var input = document.getElementById(inputId);
    var errorSpan = document.getElementById(inputId + "_error");

    // Reset error message
    errorSpan.innerHTML = "";

    // Validate input value
    if (input.value === "") {
        errorSpan.innerHTML = inputId.replace(/_/g, ' ').charAt(0).toUpperCase() + inputId.slice(1).replace(/_/g, ' ')+" is required!";
    }
}

document.getElementById("create-issue").addEventListener("submit", function (event) {
    // Perform validation on form submission
    validateInput("issue_id");
    validateInput("title");
    validateInput("date");

    // Prevent form submission if there are errors
    if (document.getElementsByClassName("error-message")[0].innerHTML !== "" || document.getElementsByClassName("error-message")[1].innerHTML !== "") {
        event.preventDefault();
    }
});