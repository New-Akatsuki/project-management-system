function checkInputValidation(element_id, element_name, min_length) {
    const nameInput = document.getElementById(element_id);
    const msg_div = document.getElementById(`${element_id}_error`);

    const newPasswordInput = document.getElementById("newPassword");
    const retypePasswordInput = document.getElementById("retypePassword");

    const newPasswordError = document.getElementById("newPassword_error");
    const retypePasswordError = document.getElementById("retypePassword_error");

    const PasswordInput = document.getElementById("password");
    const passwordError = document.getElementById("password_error");


    const pattern = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$/;



    newPasswordInput.addEventListener("input", function () {
        if (!newPasswordInput.value.match(pattern)) {
            newPasswordInput.setCustomValidity(`Invalid ${element_name}. Must have at least one digit,lowercase letter, uppercase letter, and be at least 8 characters long.`);
            newPasswordInput.classList.add("is-invalid");
            newPassword_error.textContent = `Invalid ${element_name}. Must have at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long.`;
        } else {
            newPasswordInput.setCustomValidity("");
            newPasswordInput.classList.remove("is-invalid");
            newPasswordInput.classList.add("is-valid");
            newPassword_error.textContent = "";
        }
    });

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


    retypePasswordInput.addEventListener("input", function () {
        if (newPasswordInput.value !== retypePasswordInput.value) {
            retypePasswordInput.setCustomValidity("Passwords do not match.");
            retypePasswordInput.classList.add("is-invalid");
            retypePasswordError.textContent = "Passwords do not match.";
        } else {
            retypePasswordInput.setCustomValidity("");
            retypePasswordInput.classList.remove("is-invalid");
            retypePasswordError.textContent = "";
        }
    });


    PasswordInput.addEventListener("input", function () {
        if (!PasswordInput.value.match(pattern)) {
            PasswordInput.setCustomValidity(`Invalid ${element_name}. Must have at least one digit,lowercase letter, uppercase letter, and be at least 8 characters long.`);
            PasswordInput.classList.add("is-invalid");
            password_error.textContent = `Invalid ${element_name}. Must have at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long.`;
        } else {
            PasswordInput.setCustomValidity("");
            PasswordInput.classList.remove("is-invalid");
            PasswordInput.classList.add("is-valid");
            password_error.textContent = "";
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
