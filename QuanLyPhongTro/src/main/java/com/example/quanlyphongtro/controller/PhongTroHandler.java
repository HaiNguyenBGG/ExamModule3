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
        String keyword = request.getParameter("search");
        List<PhongTro> danhSachPhongTro = phongTroService.searchPhongTro(keyword);
        request.setAttribute("danhSachPhongTro", danhSachPhongTro);
        request.getRequestDispatcher("quanly.jsp").forward(request, response);
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
        String tenNguoiThue = request.getParameter("ten_nguoi_thue");
        String soDienThoai = request.getParameter("so_dien_thoai");
        String ngayBatDau = request.getParameter("ngay_bat_dau");
        String hinhThucThanhToan = request.getParameter("ten_hinh_thuc");
        String ghiChu = request.getParameter("ghi_chu");

        PhongTro phongTro = new PhongTro();
        phongTro.setTenNguoiThue(tenNguoiThue);
        phongTro.setSoDienThoai(soDienThoai);
        phongTro.setNgayBatDau(java.sql.Date.valueOf(ngayBatDau));
        phongTro.setHinhThucThanhToan(hinhThucThanhToan);
        phongTro.setGhiChu(ghiChu);

        phongTroService.addPhongTro(phongTro);

        response.sendRedirect(request.getContextPath() + "/phongtro?action=list");
    }
}