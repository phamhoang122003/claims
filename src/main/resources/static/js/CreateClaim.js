let currentStep = 1;

function showStep(step) {
    document.querySelectorAll('.form-step').forEach(function (element) {
        element.classList.remove('active');
    });
    document.querySelectorAll('.step').forEach(function (element) {
        element.classList.remove('active');
    });
    document.querySelector('.form-step[data-step="' + step + '"]').classList.add('active');
    document.querySelector('.step[data-step="' + step + '"]').classList.add('active');

    const prevButton = document.querySelector('.action-buttons .prev-btn');
    const nextButton = document.querySelector('.action-buttons .next-btn');

    prevButton.style.display = (step === 1) ? 'none' : 'inline-block';
    nextButton.style.display = (step === 3) ? 'none' : 'inline-block';
}

function nextStep() {
    if (currentStep < 3) {
        currentStep++;
        showStep(currentStep);
    }
}

function prevStep() {
    if (currentStep > 1) {
        currentStep--;
        showStep(currentStep);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    showStep(currentStep);
});

function validateTimes() {
    const projectStartTime = document.getElementById("project-start-time");
    const projectEndTime = document.getElementById("project-end-time");

    const startDate = new Date(projectStartTime.value);
    const endDate = new Date(projectEndTime.value);

    if (projectStartTime.value && projectEndTime.value) {
        if (startDate >= endDate) {
            alert("Ngày bắt đầu phải nhỏ hơn ngày kết thúc.");
            projectStartTime.value = "";
            projectEndTime.value = "";
        }
    }
}

function validateTime(startTimeInput, endTimeInput) {
    const startTime = startTimeInput.value;
    const endTime = endTimeInput.value;

    if (startTime && endTime) {
        const start = new Date(`1970-01-01T${startTime}:00`);
        const end = new Date(`1970-01-01T${endTime}:00`);

        if (start >= end) {
            alert("Thời gian bắt đầu phải nhỏ hơn thời gian kết thúc.");
            return false;
        }
    } else {
        alert("Vui lòng nhập cả thời gian bắt đầu và kết thúc.");
        return false;
    }
    return true;
}

function calculateTotalHours(row) {
    const startTimeInput = row.querySelector('.start-time');
    const endTimeInput = row.querySelector('.end-time');
    const totalHoursInput = row.querySelector('.total-hours');

    if (validateTime(startTimeInput, endTimeInput)) {
        const start = new Date(`1970-01-01T${startTimeInput.value}:00`);
        const end = new Date(`1970-01-01T${endTimeInput.value}:00`);
        const diff = (end - start) / 1000 / 60 / 60;

        totalHoursInput.value = diff > 0 ? diff.toFixed(2) : "";
    } else {
        totalHoursInput.value = "";
    }
}

function updateDayOfWeek(dayInput, dayOfWeekInput) {
    if (checkDuplicateDates(dayInput)) {
        dayInput.value = '';
        dayOfWeekInput.value = '';
        return;
    }

    if (dayInput.value) {
        const date = new Date(dayInput.value);
        const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
        dayOfWeekInput.value = daysOfWeek[date.getDay()];
    } else {
        dayOfWeekInput.value = '';
    }
}

// Hàm kiểm tra trùng lặp ngày
function checkDuplicateDates(dayInput) {
    console.log("Gọi hàm kiểm tra trùng lặp"); // Log này
    var dates = [];
    var hasDuplicate = false;

    document.querySelectorAll('.day').forEach(function (input) {
        var dateValue = input.value;
        if (dateValue) { // Kiểm tra nếu trường không rỗng
            if (dates.includes(dateValue)) {
                hasDuplicate = true;
                alert("Ngày đã được chọn, vui lòng chọn ngày khác.");
                input.value = ''; // Xóa giá trị trùng lặp
            } else {
                dates.push(dateValue);
            }
        }
    });
    return hasDuplicate;
}

document.addEventListener('DOMContentLoaded', function() {
    const projectStartTime = document.getElementById("project-start-time");
    const projectEndTime = document.getElementById("project-end-time");

    projectEndTime.addEventListener("change", validateTimes);

    document.querySelectorAll('#claims-body .day').forEach(input => {
        input.addEventListener('change', function() {
            updateDayOfWeek(this, this.closest('tr').querySelector('.day-of-week'));
            checkDuplicateDates(this);
        });
    });

    document.querySelectorAll('#claims-body .start-time, #claims-body .end-time').forEach(input => {
        input.addEventListener('change', function() {
            calculateTotalHours(this.closest('tr'));
        });
    });

    document.getElementById("add-day-btn").addEventListener("click", function() {
        let dayIndex = document.querySelectorAll("#claims-body tr").length; // Lấy số thứ tự tiếp theo cho index
        const tbody = document.getElementById("claims-body");

        const newRow = document.createElement("tr");

        newRow.innerHTML = `
            <td>
                <input type="date" class="day" name="claimDays[${dayIndex}].date">
            </td>
            <td>
                <input type="text" class="day-of-week" name="claimDays[${dayIndex}].day" readonly>
            </td>
            <td>
                <input type="time" class="start-time" name="claimDays[${dayIndex}].fromDate">
            </td>
            <td>
                <input type="time" class="end-time" name="claimDays[${dayIndex}].toDate">
            </td>
            <td>
                <input type="text" class="total-hours" name="claimDays[${dayIndex}].totalOfHours" readonly>
            </td>
            <td>
                <textarea class="remarks" name="claimDays[${dayIndex}].description" rows="2"></textarea>
            </td>
            <td>
                <button type="button" class="btn btn-danger remove-btn">Cancel</button>
            </td>
        `;

        tbody.appendChild(newRow);

        const dayInput = newRow.querySelector('.day');
        const dayOfWeekInput = newRow.querySelector('.day-of-week');
        const startTimeInput = newRow.querySelector('.start-time');
        const endTimeInput = newRow.querySelector('.end-time');
        const removeBtn = newRow.querySelector('.remove-btn');

        // Gán sự kiện cho các phần tử mới
        dayInput.addEventListener('change', function() {
            updateDayOfWeek(this, dayOfWeekInput);
            checkDuplicateDates(this);
        });
        startTimeInput.addEventListener('change', function() {
            calculateTotalHours(newRow);
        });
        endTimeInput.addEventListener('change', function() {
            calculateTotalHours(newRow);
        });
        removeBtn.addEventListener('click', function() {
            newRow.remove();
        });
    });

    document.querySelectorAll(".remove-btn").forEach(function(button) {
        button.addEventListener("click", function() {
            button.closest("tr").remove();
        });
    });
});
