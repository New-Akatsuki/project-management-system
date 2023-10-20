function checkInputValidation(element_id, element_name,pattern, min_length ) {
    const nameInput = document.getElementById(element_id);
    const msg_div = document.getElementById(`${element_id}_error`);
    const patternMismatchMsg = element_id === "email" ? "Please enter a valid email address." : `Invalid ${element_name}. Must have at least one digit,lowercase letter, uppercase letter, and be at least 8 characters long.`;




    if (!element_id) {
        return;
    }

    nameInput.addEventListener("blur", function () {
        if (pattern) {
            if (!nameInput.validity.valid) {
                if (!nameInput.value.match(pattern)) {
                    nameInput.setCustomValidity(patternMismatchMsg);
                    nameInput.classList.add("is-invalid");
                    msg_div.textContent = patternMismatchMsg;

                }

            }
        } else {
            if (!nameInput.validity.valid) {
                if (nameInput.validity.valueMissing) {
                    nameInput.setCustomValidity(`${element_name || ""} is required.`);
                } else if (nameInput.validity.patternMismatch) {
                    nameInput.setCustomValidity(`Please enter valid ${element_name || ""}`)
                } else if (nameInput.validity.tooShort) {
                    nameInput.setCustomValidity(`${element_name} must be at least ${min_length || 0} characters.`);
                }
                nameInput.classList.add("is-invalid");
                msg_div.innerText = nameInput.validationMessage;
            }
        }
    });

    nameInput.addEventListener("input", function () {
        nameInput.setCustomValidity(""); // Clear custom validation message
        if (nameInput.validity.valid) {
            nameInput.classList.remove("is-invalid");
        } else if (!nameInput.value.match(pattern)) {
            nameInput.setCustomValidity(`Invalid ${element_name}. Must have at least one digit,lowercase letter, uppercase letter, and be at least 8 characters long.`);
            nameInput.classList.add("is-invalid");
            msg_div.textContent = `Invalid ${element_name}. Must have at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long.`;
        } else {
            nameInput.setCustomValidity("");
            nameInput.classList.remove("is-invalid");
            nameInput.classList.add("is-valid");
            msg_div.textContent = "";
        }
    });


    return {
        element: nameInput,
        msg: msg_div,
    };


    function checkPasswordsMatch(passwordElement, confirmPassElement) {
        confirmPassElement.element.addEventListener("input", function () {
            if (passwordElement.element.value !== confirmPassElement.element.value) {
                confirmPassElement.element.setCustomValidity("Passwords do not match.");
                confirmPassElement.element.classList.add("is-invalid");
                confirmPassElement.msg.textContent = "Passwords do not match.";
            } else {
                confirmPassElement.element.setCustomValidity("");
                confirmPassElement.element.classList.remove("is-invalid");
                confirmPassElement.msg.textContent = "";
            }
        });
        passwordElement.element.addEventListener("input", function () {
            if (confirmPassElement.element.value !== "") {
                if (passwordElement.element.value !== confirmPassElement.element.value) {
                    confirmPassElement.element.setCustomValidity("Passwords do not match.");
                    confirmPassElement.element.classList.add("is-invalid");
                    confirmPassElement.msg.textContent = "Passwords do not match.";
                } else {
                    confirmPassElement.element.setCustomValidity("");
                    confirmPassElement.element.classList.remove("is-invalid");
                    confirmPassElement.msg.textContent = "";
                }
            }
        });
    }

    function formValidate(form_id) {
        const nameForm = document.getElementById(form_id);

        nameForm.addEventListener("submit", function (event) {
            if (!nameForm.checkValidity()) {
                event.preventDefault();
                event.stopPropagation();
            }
            nameForm.classList.add("was-validated");
        });
    }
}