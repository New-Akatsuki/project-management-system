function checkInputValidation(element_id, element_name, min_length) {
    const nameInput = document.getElementById(element_id);
    const msg_div = document.getElementById(`${element_id}_error`);
    if(!nameInput){
        return;
    }
    nameInput.addEventListener("blur", function () {
        if (!nameInput.validity.valid) {
            if (nameInput.validity.valueMissing) {
                nameInput.setCustomValidity(`${element_name||""} is required.`);
            }else if(nameInput.validity.patternMismatch){
                nameInput.setCustomValidity(`Please enter valid ${element_name||""}`)
            } else if (nameInput.validity.tooShort) {
                nameInput.setCustomValidity(`${element_name} must be at least ${min_length||0} characters.`);
            }
            nameInput.classList.add("is-invalid");
            msg_div.innerText = nameInput.validationMessage;
        }
    });

    nameInput.addEventListener("input", function () {
        nameInput.setCustomValidity(""); // Clear custom validation message
        if (nameInput.validity.valid) {
            nameInput.classList.remove("is-invalid");
        }else{
            nameInput.classList.add("is-valid");
        }
    });

}

function formValidate(form_id){
    const nameForm = document.getElementById(form_id);

    nameForm.addEventListener("submit", function (event) {
        if (!nameForm.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }
        nameForm.classList.add("was-validated");
    });
}
