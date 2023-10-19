function singleSelect(id, input_data){
    const wrapper = document.getElementById(id),
        selectBtn = wrapper.querySelector(`#${id} .select-btn`),
        searchInp = wrapper.querySelector(`#${id} input`),
        options = wrapper.querySelector(`#${id} .options`);
    const data = Array.isArray(input_data) ? input_data : [input_data];
    console.log(wrapper)
    console.log(selectBtn)
    console.log(searchInp)
    console.log(options)
    addCountry(options);

    function addCountry(selectedItem) {
        options.innerHTML = "";
        data.forEach(item => {
            let isSelected = item == selectedItem ? "selected" : "";
            let li = `<li onclick="${id}UpdateName(this)" class="${isSelected}">${item}</li>`;
            options.insertAdjacentHTML("beforeend", li);
        });
    }

    function updateName(selectedLi) {
        searchInp.value = "";
        addCountry(selectedLi.innerText);
        wrapper.classList.remove("active");
        selectBtn.firstElementChild.innerText = selectedLi.innerText;
    }

    searchInp.addEventListener("keyup", () => {
        let arr = [];
        let searchWord = searchInp.value.toLowerCase();
        arr = data.filter(data => {
            return data.toLowerCase().startsWith(searchWord);
        }).map(data => {
            let isSelected = data == selectBtn.firstElementChild.innerText ? "selected" : "";
            return `<li onclick="${id}UpdateName(this)" class="${isSelected}">${data}</li>`;
        }).join("");
        options.innerHTML = arr ? arr : `<p style="margin-top: 10px;">Oops! Country not found</p>`;
    });

    selectBtn.addEventListener("click", () => wrapper.classList.toggle("active"));

    return updateName;
}

function init(id){
    const container = document.getElementById(id);

    let item = `
        <div id="${id}_wrapper" class="s-wrapper col-12">
        <div class="select-btn col-12">
        <span>Select </span>
        <i class="uil uil-angle-down"></i>
        </div>
        <div class="content">
        <div class="search d-flex gap-2">
            <i class="uil uil-search"></i>
            <input spellcheck="false" type="text" placeholder="   Search">
        </div>
        <ul class="options"></ul>
        </div>
        </div>
    `;
    container.innerHTML = item;
    return `${id}_wrapper`;
}