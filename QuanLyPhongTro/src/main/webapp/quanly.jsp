<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Quản lý phòng trọ</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<div class="container">
    <h2 class="text-center text-primary">Danh sách phòng trọ</h2>

    <!-- Tìm kiếm và thêm mới -->
    <form action="phongtro" method="get" class="d-flex mb-3">
        <input type="text" name="search" class="form-control me-2" placeholder="Nhập mã phòng, tên hoặc số điện thoại">
        <button type="submit" class="btn btn-primary">Tìm kiếm</button>
        <button type="button" class="btn btn-success ms-2" data-bs-toggle="modal" data-bs-target="#addPhongTroModal">Thêm mới</button>
    </form>

    <form action="phongtro?action=deleteMultiple" method="post" class="mb-3">
        <table class="table table-striped table-bordered">
            <thead class="table-dark">
            <tr>
                <th><input type="checkbox" id="selectAll"></th>
                <th>Mã phòng</th>
                <th>Tên người thuê</th>
                <th>Số điện thoại</th>
                <th>Ngày bắt đầu</th>
                <th>Hình thức thanh toán</th>
                <th>Ghi chú</th>
                <th>Hành động</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
                <c:when test="${not empty danhSachPhongTro}">
                    <c:forEach var="phongTro" items="${danhSachPhongTro}">
                        <tr>
                            <td><input type="checkbox" name="selectedIds" value="${phongTro.id}"></td>
                            <td>${phongTro.id}</td>
                            <td>${phongTro.tenNguoiThue}</td>
                            <td>${phongTro.soDienThoai}</td>
                            <td>${phongTro.ngayBatDau}</td>
                            <td>${phongTro.hinhThucThanhToan}</td>
                            <td>${phongTro.ghiChu}</td>
                            <td>
                                <a href="phongtro?action=delete&id=${phongTro.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa phòng trọ này?')">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr>
                        <td colspan="8" class="text-center text-danger">Không có dữ liệu</td>
                    </tr>
                </c:otherwise>
            </c:choose>
            </tbody>
        </table>
        <button type="submit" class="btn btn-danger" onclick="return confirm('Bạn có chắc muốn xóa các phòng trọ đã chọn?')">Xóa các phòng trọ đã chọn</button>
    </form>
</div>
<!-- Modal Thêm phòng trọ -->
<div class="modal fade" id="addPhongTroModal" tabindex="-1" aria-labelledby="addPhongTroModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addPhongTroModalLabel">Thêm phòng trọ mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form action="phongtro?action=add" method="post">
                    <div class="mb-3">
                        <label for="ten_nguoi_thue" class="form-label">Tên người thuê</label>
                        <input type="text" class="form-control" id="ten_nguoi_thue" name="ten_nguoi_thue" required>
                    </div>
                    <div class="mb-3">
                        <label for="so_dien_thoai" class="form-label">Số điện thoại</label>
                        <input type="text" class="form-control" id="so_dien_thoai" name="so_dien_thoai" required>
                    </div>
                    <div class="mb-3">
                        <label for="ngay_bat_dau" class="form-label">Ngày bắt đầu</label>
                        <input type="date" class="form-control" id="ngay_bat_dau" name="ngay_bat_dau" required>
                    </div>
                    <div class="mb-3">
                        <label for="hinh_thuc_thanh_toan" class="form-label">Hình thức thanh toán</label>
                        <input type="text" class="form-control" id="hinh_thuc_thanh_toan" name="hinh_thuc_thanh_toan" required>
                    </div>
                    <div class="mb-3">
                        <label for="ghi_chu" class="form-label">Ghi chú</label>
                        <textarea class="form-control" id="ghi_chu" name="ghi_chu"></textarea>
                    </div>
                    <button type="submit" class="btn btn-primary">Thêm phòng trọ</button>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    document.getElementById("selectAll").onclick = function () {
        let checkboxes = document.getElementsByName("selectedIds");
        for (let checkbox of checkboxes) {
            checkbox.checked = this.checked;
        }
    };
</script>
</body>
</html>
