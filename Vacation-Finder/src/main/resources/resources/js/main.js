$("#budgetSlider").ionRangeSlider({
    type: "single",
    grid: true,
    values: ["$", "$$", "$$$"]
});

$("#distanceSlider").ionRangeSlider({
    type: "single",
    grid: true,
    min: 0,
    max: 100,
    from: 15,
    from_min: 5,
    to: 1250,
    step: 1,
    force_edges: true,
    postfix: " miles",
    prettify_enabled: true,
    prettify_separator: ","
});

$("#timeOfYearSlider").ionRangeSlider({
    type: "single",
    grid: true,
    values: [
        "Spring", "Summer", "Fall", "Winter"
    ],
    force_edges: true
});

$("#familySlider").ionRangeSlider({
    type: "single",
    values: ["Yes", "No"],
    force_edges: true
});

$("#ageSlider").ionRangeSlider({
    type: "single",
    grid: true,
    min: 0,
    max: 100,
    from: 21,
    to: 60,
    step: 1,
    force_edges: true,
    postfix: " years old"
});

$("#partySizeSlider").ionRangeSlider({
    type: "single",
    grid: true,
    min: 0,
    max: 50,
    from: 1,
    step: 1,
    from_min: 1,
    force_edges: true,
});

$("#partySizeSlider").on("change", function () {

    var $this = $(this),
        value = $this.prop("value");

    var slider = $("#ageSlider").data("ionRangeSlider");
    if (value != "1") {
        slider.update({
            from: 0,
            from_fixed: true
        });
    }
    else {
        slider.update({
            from_fixed: false
        });
    }
});

$('.carousel-control.left').click(function () {
    $('#myCarousel').carousel('prev');
});

var page = 1;
function next() {
    if (fieldsFilled() == true && page < 2) {
        $('#myCarousel').carousel('next');
        page++;
    }
    else if (page == 2) {
        if (radioChecked() == true) {
            $('#myCarousel').carousel('next');
            page++;
        }
        else if (radioChecked() == false) {
            alert("Check yes or no for \"Are you taking a family?\"");
        }
    }
    else {
        alert("Check that all fields have been filled");
        if ($("#input1").val().trim().length === 0) {
            $("#input1").val('');
        }
        if ($("#input2").val().trim().length === 0) {
            $("#input2").val('');
        }
        $("#input1").focus();
    }
};

function done() {
    document.getElementById("theform").submit();
}

function prev() {
    $('#myCarousel').carousel('prev');
    page--;
}



$(document).ready(function () {
    $("#input1").focus();
    // $("#input2").prop('readonly', true);
});

$("#input1").keydown(function (e) {
    if (e.keyCode == 13 || e.keyCode == 9) {
        e.preventDefault()
        $("#input2").focus();
        // $("#input2").prop('readonly', true);
        return false;    //<---- Add this line
    }
});

$("#input2").keypress(function (e) {
    if (e.which == 13) {
        next();
        return false;    //<---- Add this line
    }
});



function fieldsFilled() {
    if ($("#input1").val().trim().length === 0 || $("#input2").val().trim().length === 0) {
        return false;
    }
    else {
        return true;
    }
}

function radioChecked() {
    var yesRadio = document.getElementById("switch_left");
    var noRadio = document.getElementById("switch_right");
    if(yesRadio.checked == true){
        var parent = document.getElementById("social");
        var children = parent.childNodes;
        parent.removeChild(children[15]);
        parent.removeChild(children[15]);
        parent.removeChild(children[15]);
        parent.removeChild(children[15]);

        parent = document.getElementById("entertainment");
        children = parent.childNodes;
        parent.removeChild(children[21]);
        parent.removeChild(children[21]);
        parent.removeChild(children[21]);
        parent.removeChild(children[21]);
        parent.removeChild(children[21]);
        parent.removeChild(children[21]);


    }
    if (yesRadio.checked || noRadio.checked) {
        return true;
    }
    else {
        return false;
    }
}

// function openDropDown(){
//     $("#input2button").click();
//     $("#input2").attr("placeholder", "click arrow ->");
// }

// $("#stateList li").on('click', function () {
//     $("#input2").val($(this).text().substr(0, 2));
// });

[].forEach.call( document.querySelectorAll('.hide-checkbox'), function(element) {
    element.style.display = 'none';
});

var input = document.getElementById("input2");
new Awesomplete(input, {
    list: [
        { label: "Alabama", value: "AL" },
        { label: "Alaska", value: "AK" },
        { label: "Arizona", value: "AZ" },
        { label: "Arkansas", value: "AR" },
        { label: "California", value: "CA" },
        { label: "Colorado", value: "CO" },
        { label: "Connecticut", value: "CT" },
        { label: "Delaware", value: "DE" },
        { label: "Florida", value: "FL" },
        { label: "Georgia", value: "GA" },
        { label: "Hawaii", value: "HI" },
        { label: "Idaho", value: "ID" },
        { label: "Illinois", value: "IL" },
        { label: "Indiana", value: "IN" },
        { label: "Iowa", value: "IA" },
        { label: "Kansas", value: "KS" },
        { label: "Kentucky", value: "KY" },
        { label: "Louisiana", value: "LA" },
        { label: "Maine", value: "ME" },
        { label: "Maryland", value: "MD" },
        { label: "Massachusetts", value: "MA" },
        { label: "Michigan", value: "MI" },
        { label: "Minnesota", value: "MN" },
        { label: "Mississippi", value: "MS" },
        { label: "Missouri", value: "MO" },
        { label: "Montana", value: "MT" },
        { label: "Nebraska", value: "NE" },
        { label: "Nevada", value: "NV" },
        { label: "New Hampshire", value: "NH" },
        { label: "New Jersey", value: "NJ" },
        { label: "New Mexico", value: "NM" },
        { label: "New York", value: "NY" },
        { label: "North Carolina", value: "NC" },
        { label: "North Dakota", value: "ND" },
        { label: "Ohio", value: "OH" },
        { label: "Oklahoma", value: "OK" },
        { label: "Oregon", value: "OR" },
        { label: "Pennsylvania", value: "PA" },
        { label: "Rhode", value: "Island RI" },
        { label: "South Carolina", value: "SC" },
        { label: "South Dakota", value: "SD" },
        { label: "Tennessee", value: "TN" },
        { label: "Texas", value: "TX" },
        { label: "Utah", value: "UT" },
        { label: "Vermont", value: "VT" },
        { label: "Virginia", value: "VA" },
        { label: "Washington", value: "WA" },
        { label: "West Virginia", value: "WV" },
        { label: "Wisconsin", value: "WI" },
        { label: "Wyoming", value: "WY" }
    ],
    minChars: 1,
    maxItems: 3,
    autoFirst: true

});







