function checkInputValidation(element_id, element_name, pattern, min_length) {
    const nameInput = document.getElementById(element_id);
    const msg_div = document.getElementById(`${element_id}_error`);
    const patternMismatchMsg = element_id === "email" ? "Please enter a valid email address." : `Invalid ${element_name}. Must have at least one digit,lowercase letter, uppercase letter, and be at least 8 characters long.`;

    if (!element_id) {
        return;
    }

    if(nameInput){
        nameInput.addEventListener("blur", function () {
            if (pattern) {
                if (!nameInput.value.match(pattern)) {
                    if (!nameInput.value.match(pattern)) {
                        nameInput.setCustomValidity(patternMismatchMsg);
                        nameInput.classList.add("is-invalid");
                        msg_div.textContent = patternMismatchMsg;
                    }else {
                        nameInput.setCustomValidity("");
                        nameInput.classList.remove("is-invalid");
                        nameInput.classList.add("is-valid");
                        msg_div.textContent = "";
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
            if (pattern){
                if (!nameInput.value.match(pattern)) {
                    showPatternInvalidMsg(nameInput,msg_div,element_name);
                }else {
                    nameInput.setCustomValidity("");
                    nameInput.classList.remove("is-invalid");
                    nameInput.classList.add("is-valid");
                    msg_div.textContent = "";
                }
            }else{
                if (nameInput.validity.valid) {
                    nameInput.classList.remove("is-invalid");
                } else {
                    nameInput.setCustomValidity("");
                    nameInput.classList.remove("is-invalid");
                    nameInput.classList.add("is-valid");
                    msg_div.textContent = "";
                }
            }
        });
    }

    return {
        element: nameInput,
        msg: msg_div,
    };
}

function showPatternInvalidMsg(element,msg_div,element_name) {
    element.setCustomValidity(`Invalid ${element_name}. Must have at least one digit,lowercase letter, uppercase letter, and be at least 8 characters long.`);
    element.classList.add("is-invalid");
    msg_div.textContent = `Invalid ${element_name}. Must have at least one digit, one lowercase letter, one uppercase letter, and be at least 8 characters long.`;
}


/*function checkPasswordsMatch(passwordElement, confirmPassElement) {
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
}*/

function formValidate(form_id, action=()=>{}) {
    const nameForm = document.getElementById(form_id);

    nameForm.addEventListener("submit", function (event) {
        if (!nameForm.checkValidity()) {
            event.preventDefault();
            event.stopPropagation();
        }else {
            event.preventDefault();
            event.stopPropagation();
            console.log('in form validation');
            action();
        }
        nameForm.classList.add("was-validated");
    });
}

let startDate,endDate,startDateID,endDateID;
function checkDatesValidation(date_id1, date_id2){
    startDateID = date_id1;
    endDateID = date_id2
    $("#"+date_id1).on('change', function() {
        startDate = $(this).val();
        _checkDate()
        console.log('Value changed:', startDate);
    });
    $("#"+date_id2).on('change', function() {
        endDate = $(this).val();
        _checkDate()
        console.log('Value changed:', endDate);
    });
}
function _checkDate() {
    const start_element = document.getElementById(startDateID);
    const end_element = document.getElementById(endDateID);

    const start_msg_div =document.getElementById(`${startDateID}_error`);
    const end_msg_div =document.getElementById(`${endDateID}_error`);

    if (startDate && endDate) {
        const startDateValue = new Date(startDate);
        const endDateValue = new Date(endDate);

        if ((startDateValue > endDateValue)
            ||
            (startDateValue.getFullYear()===endDateValue.getFullYear()&&
                startDateValue.getMonth()===endDateValue.getMonth()&&
                startDateValue.getDate()===endDateValue.getDate())
        ) {
            // Show an error message
            start_element.setCustomValidity("invalid date");
            end_element.setCustomValidity("invalid date");
            start_element.classList.add("is-invalid");
            end_element.classList.add("is-invalid");

            start_msg_div.textContent = "Invalid date";
            end_msg_div.textContent = "Invalid date";
        } else {
            // Clear the error message
            start_element.setCustomValidity("");
            start_element.classList.remove("is-invalid");

            end_element.setCustomValidity("");
            end_element.classList.remove("is-invalid");

            start_msg_div.textContent = "";
            end_msg_div.textContent="";
        }
    }
}