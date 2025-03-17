package com.example.quanlyphongtro.controller;

import com.example.quanlyphongtro.model.PhongTro;
import com.example.quanlyphongtro.service.PhongTroService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PhongTroHandler {
    private PhongTroService phongTroService = new PhongTroService();

    public void listPhongTro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<PhongTro> danhSachPhongTro = phongTroService.getAllPhongTro();

        request.setAttribute("danhSachPhongTro", danhSachPhongTro);
        request.getRequestDispatcher("quanly.jsp").forward(request, response);
    }

    public void searchPhongTro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<PhongTro> listPhongTro = phongTroService.searchPhongTro(keyword);
        request.setAttribute("danhSachPhongTro", listPhongTro);
        RequestDispatcher dispatcher = request.getRequestDispatcher("quanly.jsp");
        dispatcher.forward(request, response);
    }

    public void deletePhongTro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        phongTroService.deletePhongTro(id);
        response.sendRedirect("phongtro?action=list");
    }

    public void deleteMultiplePhongTro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] selectedIds = request.getParameterValues("selectedIds");
        if (selectedIds != null) {
            int[] ids = new int[selectedIds.length];
            for (int i = 0; i < selectedIds.length; i++) {
                ids[i] = Integer.parseInt(selectedIds[i]);
            }
            phongTroService.deleteMultiplePhongTro(ids);
        }
        response.sendRedirect("phongtro?action=list");
    }

    public void addPhongTro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Lấy dữ liệu từ form
            String tenNguoiThue = request.getParameter("ten_nguoi_thue");
            String soDienThoai = request.getParameter("so_dien_thoai");
            String ngayBatDauStr = request.getParameter("ngay_bat_dau");
            String hinhThucThanhToan = request.getParameter("hinh_thuc_thanh_toan");
            String ghiChu = request.getParameter("ghi_chu");

            // Kiểm tra các trường dữ liệu không được trống
            if (tenNguoiThue == null || soDienThoai == null || ngayBatDauStr == null) {
                request.setAttribute("error", "Các trường thông tin không được để trống!");
                request.getRequestDispatcher("quanly.jsp").forward(request, response);
                return;
            }

            // Chuyển đổi chuỗi ngày bắt đầu thành java.sql.Date
            java.sql.Date ngayBatDau = java.sql.Date.valueOf(ngayBatDauStr);

            // Tạo đối tượng PhongTro và set dữ liệu
            PhongTro phongTro = new PhongTro();
            phongTro.setTenNguoiThue(tenNguoiThue);
            phongTro.setSoDienThoai(soDienThoai);
            phongTro.setNgayBatDau(ngayBatDau);
            phongTro.setHinhThucThanhToan(hinhThucThanhToan);
            phongTro.setGhiChu(ghiChu);

            // Thêm phòng trọ vào cơ sở dữ liệu
            phongTroService.addPhongTro(phongTro);

            // Lấy lại danh sách phòng trọ và hiển thị
            List<PhongTro> danhSachPhongTro = phongTroService.getAllPhongTro();
            request.setAttribute("danhSachPhongTro", danhSachPhongTro);

            // Chuyển hướng về trang quản lý
            request.getRequestDispatcher("quanly.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi thêm phòng trọ!");
            request.getRequestDispatcher("quanly.jsp").forward(request, response);
        }
    }
}