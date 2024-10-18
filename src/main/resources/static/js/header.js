$(document).ready(function() {

    $('#user').click(function (){
        let userList =$('.user-list')[0]
        if(userList.style.display === 'block'){
            userList.style.display = 'none'
        }else {
            userList.style.display = 'block'
        }
    });

    function updateSelectOptions() {
        const staffSelects = $("select[name='staffId']");  // Sử dụng jQuery để chọn tất cả các select với name là 'staffName'
        const selectedStaff = Array.from(staffSelects).map(s => s.value);

        staffSelects.each(function() {
            const select = $(this);  // `this` là phần tử hiện tại trong vòng lặp
            const options = select.find("option");
            options.each(function() {
                const option = $(this);  // `this` là option hiện tại
                if (selectedStaff.includes(option.val()) && option.val() !== select.val()) {
                    option.prop('disabled', true);  // Ẩn nhân viên đã được chọn
                } else {
                    option.prop('disabled', false);
                }
            });
        });
    }


    // Cập nhật vị trí Position, cho phép PM và QA chỉ được chọn một lần
    function updatePositionOptions() {
        const positionSelects = $("select[name='position']");
        const selectedPositions = Array.from(positionSelects).map(s => s.value);

        positionSelects.each(function() {
            const select = $(this);  // `this` là phần tử hiện tại trong vòng lặp
            const options = select.find("option");
            options.each(function() {
                const option = $(this);  // `this` là option hiện tại
                if (["PM", "QA"].includes(option.val()) && selectedPositions.includes(option.val()) && option.val() !== select.val()) {
                    option.prop('disabled', true);  // Ẩn PM và QA sau khi đã chọn
                } else {
                    option.prop('disabled', false);
                }
            });
        });
    }



    $("select[name='position']").on('change', function() {
        updatePositionOptions();
    });

    $("select[name='staffId']").on('change', function() {
        updateSelectOptions();
    });

    $('.addRow').click(function() {
        const table = document.querySelector(".table");
        const rowCount = table.rows.length;
        const row = table.insertRow(rowCount);

        // Tạo ô cho Position
        const cell1 = row.insertCell(0);
        const positionSelect = document.createElement("select");
        positionSelect.name = "position";
        positionSelect.classList.add("border-none");
        positionSelect.onchange = updatePositionOptions;

        const positionOptions = ["Select-position", "BA", "PM", "QA", "DEVELOPER", "TESTERS", "TECHNICAL_LEAD", "TECHNICAL_CONSULTANCY"];
        positionOptions.forEach(pos => {
            const option = document.createElement("option");
            option.value = pos === "Select-position" ? "0" : pos;
            option.text = pos;
            positionSelect.appendChild(option);
        });

        cell1.appendChild(positionSelect);

        // Tạo ô cho Staff Name
        const cell2 = row.insertCell(1);
        const staffSelect = document.createElement("select");
        staffSelect.name = "staffId";
        staffSelect.classList.add("border-none");
        staffSelect.onchange = updateSelectOptions;

        const defaultOption = document.createElement("option");
        defaultOption.value = "0";
        defaultOption.text = "Select-name";
        staffSelect.appendChild(defaultOption);

        // Tải dữ liệu nhân viên từ API
        let domContextPath= document.querySelector("input[name='contextPath']").value;
        let url = domContextPath + 'rest/staff';

        //1. create XMLHttp
        let xmlHttp = new XMLHttpRequest();
        xmlHttp.onload = function() {
            //2. render staff
            let textJSON = xmlHttp.responseText;
            let staffList = JSON.parse(textJSON);
            staffList.forEach(staff => {
                const option = document.createElement("option");
                option.value = staff.staffId;
                option.text = staff.staffName;
                staffSelect.appendChild(option);
            });
            cell2.appendChild(staffSelect);

            // Cập nhật các tùy chọn sau khi thêm dòng mới
            updateSelectOptions();
            updatePositionOptions();
        }
        xmlHttp.open('get', url, true);
        xmlHttp.send();  //3. send request
    });

    let domContextPath= document.querySelector("input[name='contextPath']").value;
    let url = domContextPath + 'rest/project/' + $('input[name="id"]').val();

    //1. create XMLHttp
    let xmlHttp = new XMLHttpRequest();
    xmlHttp.onload= function(){
        //2, render book
        let textJSON = xmlHttp.responseText;
        let staffList2 = JSON.parse(textJSON);
        // let staffWithoutPosition = staffList2.filter(staff => staff.position === null || staff.position === '');
        let html = '';
        staffList2.forEach(function(staff){

            html = html + ` <tr>
                                <td>
                                   <select class="border-none" name="position" id="position">
                                        <option value="0" ${staff.position == null ? 'selected' : ''}>Select-position</option>
                                        <option value="BA" ${staff.position === 'BA' ? 'selected' : ''}>BA</option>
                                        <option value="PM" ${staff.position === 'PM' ? 'selected' : ''}>PM</option>
                                        <option value="QA" ${staff.position === 'QA' ? 'selected' : ''}>QA</option>
                                        <option value="DEVELOPER" ${staff.position === 'DEVELOPER' ? 'selected' : ''}>DEVELOPER</option>
                                        <option value="TESTERS" ${staff.position === 'TESTERS' ? 'selected' : ''}>TESTERS</option>
                                        <option value="TECHNICAL_LEAD" ${staff.position === 'TECHNICAL_LEAD' ? 'selected' : ''}>TECHNICAL_LEAD</option>
                                        <option value="TECHNICAL_CONSULTANCY" ${staff.position === 'TECHNICAL_CONSULTANCY' ? 'selected' : ''}>TECHNICAL_CONSULTANCY</option>
                                    </select>
                                </td>
                                <td>
                                    <select class="border-none" name="staffId" id="staffName">
             
                                        ${staffList2.map(otherStaff => `
                                            <option value="${otherStaff.staffId}" ${staff.staffId === otherStaff.staffId ? 'selected' : ''}>${otherStaff.staffName}</option>
                                        `).join('')}  
                                                             
                                    </select>
                                </td>
                            </tr>`

        });
        document.querySelector("tbody").innerHTML = html;
        updateSelectOptions();
        updatePositionOptions();

    }
    xmlHttp.open('get', url, true);
    xmlHttp.send();  //3. send request

    updateSelectOptions();
    updatePositionOptions();

});