var rows = 0;

function FileDragHover(e) {
    e.stopPropagation();
    e.preventDefault();
    e.target.className = (e.type == "dragover" ? "filedrag hover" : "filedrag");
}

function FileSelectHandler(e) {
    FileDragHover(e);
    var files = e.target.files || e.dataTransfer.files;

    if (files.length > 1) {
        alert('Only one file can be dragged onto the upload area');
    } else if (files.length === 1) {
        if (files[0].type.indexOf("image") == 0) {
            var reader = new FileReader();
            reader.onload = function(e) {
                var img = document.getElementById("filedrag" + rows);
                img.src = e.target.result;
                img.className = "";
                var hidden = document.createElement("input");
                hidden.type = "hidden";
                hidden.name = "img" + rows;
                hidden.value = e.target.result;
                hidden.innerHTML = e.target.result;
                img.appendChild(hidden);
            }
            reader.readAsDataURL(files[0]);
        }
    }
}

function addData() {
    if (window.File && window.FileList && window.FileReader) {
        rows++;
        var dropper = document.getElementById("droppertable");
        var newtr = document.createElement("tr");
        var newtd = document.createElement("td");
        newtd.width = 150;
        newtd.height = 150;
        var newimg = document.createElement("img");
        newimg.id = "filedrag" + rows;
        newimg.name = "filedrag" + rows;
        newimg.className = "filedrag";
        newimg.style.width = "100%";
        newimg.style.height = "100%";
        var xhr = new XMLHttpRequest();
        if (xhr.upload) {
            newimg.addEventListener("dragover", FileDragHover, false);
            newimg.addEventListener("dragleave", FileDragHover, false);
            newimg.addEventListener("drop", FileSelectHandler, false);
        }
        newtd.appendChild(newimg);
        newtr.appendChild(newtd);
        newtd = document.createElement("td");
        newtd.width = 500;
        newtd.height = 150;
        var newtextarea = document.createElement("textarea");
        newtextarea.id = "info" + rows;
        newtextarea.name = "info" + rows;
        newtextarea.style.width = "100%";
        newtextarea.style.height = "100%";
        newtd.appendChild(newtextarea);
        newtr.appendChild(newtd);
        dropper.appendChild(newtr);
    }
}

function addPerformance() {
    p1 = document.getElementById('performancedate').value;
    d1 = document.getElementById('fromdate').value;
    d2 = document.getElementById('todate').value;
    showtime = document.getElementById('showtime').value;

    performancedate = new Date(p1);
    date1 = new Date(d1);
    date2 = new Date(d2);
    if (d1.length == 0 || d2.length == 0) {
        alert('You must set start and end season dates');
        return true;
    }
    if (p1.length == 0 || performancedate > date2 || performancedate < date1) {
        alert('Invalid date for performance');
        return true;
    }
    performances = document.getElementById('performances');
    if (performances.value.length == 0) {
        performances.value = p1 + ', ' + showtime + ';';
    } else {
        performances.value += ' ' + p1 + ', ' + showtime + ';';
    }
}

function doCheckDates() {
    // alert("checking dates");
    d1 = document.getElementById('fromdate').value;
    d2 = document.getElementById('todate').value;

    date1 = new Date(d1);
    date2 = new Date(d2);
    ok = date1 <= date2;
    if (!ok) {
        alert('Invalid date range');
    }

    return ok;
}