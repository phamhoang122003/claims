document.addEventListener('DOMContentLoaded', function() {
    const dayInput = document.getElementById("day");
    const dayOfWeekInput = document.getElementById("day-of-week");
    const startTimeInput = document.getElementById("start-time");
    const endTimeInput = document.getElementById("end-time");
    const totalHoursInput = document.getElementById("total-hours");
    const projectStartTime = document.getElementById("project-start-time");
    const projectEndTime = document.getElementById("project-end-time");

    // Hàm kiểm tra thời gian dự án
    function validateTimes() {
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

    // Hàm kiểm tra thời gian làm việc
    function validateTime() {
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

    // Hàm tính toán tổng số giờ làm việc
    function calculateTotalHours() {
        if (validateTime()) {
            const start = new Date(`1970-01-01T${startTimeInput.value}:00`);
            const end = new Date(`1970-01-01T${endTimeInput.value}:00`);
            const diff = (end - start) / 1000 / 60 / 60;

            totalHoursInput.value = diff > 0 ? diff.toFixed(2) : "";
        }
    }

    // Cập nhật thứ trong tuần dựa trên ngày
    function updateDayOfWeek() {
        if (dayInput.value) {
            const date = new Date(dayInput.value);
            const daysOfWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
            dayOfWeekInput.value = daysOfWeek[date.getDay()];
        } else {
            dayOfWeekInput.value = "";
        }
    }

    // Gắn sự kiện cho các trường đầu vào
    endTimeInput.addEventListener("change", calculateTotalHours);
    projectEndTime.addEventListener("change", validateTimes);
    dayInput.addEventListener("change", updateDayOfWeek);

    // Xử lý các nút hành động
    const buttons = document.querySelectorAll(".action-buttons button");

    buttons.forEach(button => {
        button.addEventListener("click", function(event) {
            const action = button.textContent.trim();

            switch (action) {
                case "Save":
                    alert("Dữ liệu đã được lưu thành công!");
                    break;
                case "Submit":
                    alert("Yêu cầu đã được gửi đi!");
                    break;
                case "Approve":
                    alert("Yêu cầu đã được duyệt!");
                    break;
                case "Reject":
                    alert("Yêu cầu đã bị từ chối!");
                    break;
                case "Return":
                    alert("Yêu cầu đã được gửi lại để chỉnh sửa!");
                    break;
                case "Print":
                    alert("Đang chuẩn bị để in thông tin!");
                    window.print();
                    break;
                case "Cancel Request":
                    alert("Yêu cầu đã bị hủy!");
                    break;
                case "Cancel":
                    alert("Thao tác đã bị hủy!");
                    break;
                case "Close":
                    alert("Đóng cửa sổ!");
                    window.close();
                    break;
                default:
                    console.warn("Hành động không xác định:", action);
            }
        });
    });
});