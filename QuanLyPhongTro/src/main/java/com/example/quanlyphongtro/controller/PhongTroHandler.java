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
        System.out.println("Lấy danh sách phòng trọ: " + danhSachPhongTro.size());

        request.setAttribute("danhSachPhongTro", danhSachPhongTro);
        forward(request, response, "index.jsp");
    }

    public void searchPhongTro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<PhongTro> danhSachPhongTro = phongTroService.searchPhongTro(keyword);

        System.out.println("Tìm kiếm với từ khóa: " + keyword + " | Kết quả: " + danhSachPhongTro.size());

        request.setAttribute("danhSachPhongTro", danhSachPhongTro);
        forward(request, response, "index.jsp");
    }

    public void showPhongTroDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        PhongTro phongTro = phongTroService.getPhongTroById(id);

        if (phongTro != null) {
            request.setAttribute("phongTro", phongTro);
            forward(request, response, "detail.jsp");
        } else {
            response.sendRedirect("phongtro?action=list");
        }
    }

    private void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
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
            for (String id : selectedIds) {
                phongTroService.deletePhongTro(Integer.parseInt(id));
            }
        }
        response.sendRedirect("phongtro?action=list");
    }

}