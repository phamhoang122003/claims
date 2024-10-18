document.addEventListener('DOMContentLoaded', function() {
    const projectSelect = document.getElementById('project-name');
    const startDateInput = document.getElementById('project-start-time');
    const roleInput = document.getElementById('role');
    const endDateInput = document.getElementById('project-end-time');
    const dayInput = document.getElementById('day');


    function updateDateRestrictions() {
        const selectedOption = projectSelect.options[projectSelect.selectedIndex];
        const startDate = selectedOption.getAttribute('data-start-date');
        const role = selectedOption.getAttribute('data-role');
        const endDate = selectedOption.getAttribute('data-end-date');

        if (startDate && endDate) {
            startDateInput.value = startDate;
            endDateInput.value = endDate;
            roleInput.value = role;
            dayInput.setAttribute('min', startDate);
            dayInput.setAttribute('max', endDate);
        } else {
            startDateInput.value = '';
            endDateInput.value = '';
            dayInput.removeAttribute('min');
            dayInput.removeAttribute('max');
        }
    }

    projectSelect.addEventListener('change', updateDateRestrictions);

    dayInput.addEventListener('change', function() {
        const minDate = dayInput.getAttribute('min');
        const maxDate = dayInput.getAttribute('max');
        const selectedDate = dayInput.value;

        if (selectedDate < minDate || selectedDate > maxDate) {
            alert('Ngày nhập vào vượt quá phạm vi cho phép. Vui lòng chọn lại.');
            dayInput.value = ''; // Xóa giá trị ngày không hợp lệ
        }
    });

    // Cập nhật các hạn chế ngày khi tải trang nếu có giá trị chọn trước
    updateDateRestrictions();
});
