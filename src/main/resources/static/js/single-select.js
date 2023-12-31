let searchbar;

function singleSelect(id, input_data, create_new_option={btn_id:'',modal_id:null,btn_name:'Add new'}) {
    const wrapper = document.getElementById(id),
        selectBtn = wrapper.querySelector(`#${id} .select-btn`),
        searchInp = wrapper.querySelector(`#${id} input`),
        options = wrapper.querySelector(`#${id} .options`);
    let data = Array.isArray(input_data) ? input_data : [input_data];
    addCountry(options);
    function addCountry(selectedItem) {
        options.innerHTML = "";
        data.forEach(item => {
            let isSelected = item === selectedItem ? "selected" : "";
            let li = `<li onclick="${id}UpdateName(this)" class="s-li ${isSelected}">${item}</li>`;
            options.insertAdjacentHTML("beforeend", li);
        });
    }

    function updateName(selectedLi) {
        searchInp.value = "";
        if(selectedLi){
            addCountry(selectedLi.innerText);
            wrapper.classList.remove("active");
            selectBtn.firstElementChild.innerText = selectedLi.innerText;
        }
        return selectedLi.innerText;
    }

    window.addEventListener('click', (e) => {
        if (!selectBtn.contains(e.target)&&!searchInp.contains(e.target)){
            wrapper.classList.remove('active')
        }
    });


    searchInp.addEventListener("keyup", () => {
        searchbar = searchInp;
        let arr = [];
        let searchWord = searchInp.value.toLowerCase();
        arr = data.filter(data => {
            return data.toLowerCase().startsWith(searchWord);
        }).map(data => {
            let isSelected = data === selectBtn.firstElementChild.innerText ? "selected" : "";
            return `<li onclick="${id}UpdateName(this)" class="s-li ${isSelected}">${data}</li>`;
        }).join("");
        if(create_new_option.modal_id){
            options.innerHTML = arr ? arr :
                `<p style="margin-top: 10px;">
                     <a id="${create_new_option.btn_id}" class="icon-add-btn" data-toggle="modal" data-target="#${create_new_option.modal_id}">
                        <i class='bx bx-plus m-0 p-0'></i> ${create_new_option.btn_name||'Add new'}
                     </a>
                </p>`;
        }
        else {
            options.innerHTML = arr ? arr : `<p style="margin-top: 10px;">Not found.</p>`;
        }
    });

    selectBtn.addEventListener("click", () => wrapper.classList.toggle("active"));
    return updateName;
}

function addNewOption(new_option){
    searchbar.value = null
    if(new_option){

    }
}

function init(id,placeholder){
    const container = document.getElementById(id);
    container.innerHTML = `
        <div id="${id}_wrapper" class="s-wrapper form-control">
        <div class="select-btn">
        <span class="p-0 m-0">${placeholder || 'Select ' + id}</span>
        <i class="uil uil-angle-down"></i>
        </div>
        <div class="container s-content">
        <div class="search d-flex gap-2">
            <i class="uil uil-search"></i>
            <input spellcheck="false" type="text" placeholder="Search">
        </div>
        <ul class="options"></ul>
        </div>
        </div>
    `;
    return `${id}_wrapper`;
}