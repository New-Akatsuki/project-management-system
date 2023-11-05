// app.js
$(document).ready(function () {
    const wrapper = $(".wrapper"),
        selectBtn = wrapper.find(".select-btn"),
        searchInp = wrapper.find("input"),
        options = wrapper.find(".options");

    let countries = [];

    function addCountry(selectedCountry) {
        options.html("");
        countries.forEach(country => {
            let isSelected = country === selectedCountry ? "selected" : "";
            let li = `<li data-country="${country}" class="${isSelected}">${country}</li>`;
            options.append(li);
        });
    }

    addCountry();

    function updateName(selectedLi) {
        searchInp.val("");
        const selectedCountry = $(selectedLi).data("country");
        addCountry(selectedCountry);
        wrapper.removeClass("active");
        selectBtn.find("span").text(selectedCountry);
        console.log(selectedCountry);
    }

    searchInp.on("keyup", () => {
        let arr = [];
        let searchWord = searchInp.val().toLowerCase();
        arr = countries
            .filter(data => data.toLowerCase().startsWith(searchWord))
            .map(data => {
                let isSelected = data == selectBtn.find("span").text() ? "selected" : "";
                return `<li data-country="${data}" onclick="updateName(this)" class="${isSelected}">${data}</li>`;
            })
            .join("");
        options.html(arr ? arr : `<p style="margin-top: 10px;">Oops! Country not found</p>`);
    });

    selectBtn.on("click", () => wrapper.toggleClass("active"));

    // Additional jQuery plugin for making the dropdown reusable
    $.fn.select = function (data) {
        countries = data;
        addCountry();
        return this;
    };
});
