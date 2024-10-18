document.addEventListener("DOMContentLoaded", function() {
    // Khi nhấn nút "Thêm ngày"
    document.getElementById("add-day-btn").addEventListener("click", function() {
        // Lấy phần tbody của bảng
        const tbody = document.getElementById("claims-body");

        // Tạo một hàng mới
        const newRow = document.createElement("tr");

        // Nội dung của hàng mới
        newRow.innerHTML = `
            <td>
                <input id="day" type="date" class="day" name="day">
            </td>
            <td>
                <input type="text" id="day-of-week" class="day-of-week" name="dayOfWeek" readonly>
            </td>
            <td>
                <input type="time" id="start-time" class="start-time" name="startTime">
            </td>
            <td>
                <input type="time" id="end-time" class="end-time" name="endTime">
            </td>
            <td>
                <input type="text" id="total-hours" class="total-hours" name="totalHours" readonly>
            </td>
            <td>
                <textarea class="remarks" id="remarks" name="remarks" rows="2"></textarea>
            </td>
            <td>
                <button type="button" class="btn btn-danger remove-btn">Cancel</button>
            </td>
        `;

        // Thêm hàng mới vào tbody
        tbody.appendChild(newRow);

        // Thêm sự kiện xóa cho nút Xóa của hàng mới
        newRow.querySelector(".remove-btn").addEventListener("click", function() {
            newRow.remove();
        });

    });

    // Thêm sự kiện xóa cho nút Xóa của hàng đầu tiên
    document.querySelectorAll(".remove-btn").forEach(function(button) {
        button.addEventListener("click", function() {
            button.closest("tr").remove();
        });
    });
});
