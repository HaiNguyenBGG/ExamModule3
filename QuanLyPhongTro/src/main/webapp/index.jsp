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
    <a href="them-moi.jsp" class="btn btn-success ms-2">Thêm mới</a>
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
              <fmt:formatDate value="${phongTro.ngayBatDau}" pattern="dd/MM/yyyy" var="ngayBatDauFormat"/>
              <td>${ngayBatDauFormat}</td>
              <td>${phongTro.hinhThucThanhToan}</td>
              <td>${phongTro.ghiChu}</td>
              <td>
                <a href="phongtro?action=delete&id=${phongTro.id}" class="btn btn-danger btn-sm" onclick="return confirm('Bạn có chắc muốn xóa phòng trọ này?')">Xóa</a>
                <a href="chinh-sua.jsp?id=${phongTro.id}" class="btn btn-warning btn-sm">Sửa</a>
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
