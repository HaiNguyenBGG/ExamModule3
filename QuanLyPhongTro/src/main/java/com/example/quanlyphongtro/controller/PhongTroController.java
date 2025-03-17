package com.example.quanlyphongtro.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/phongtro")
public class PhongTroController extends HttpServlet {

    private PhongTroHandler phongTroHandler = new PhongTroHandler();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action == null || action.isEmpty()) {
            action = "list";
        }

        switch (action) {
            case "search":
                phongTroHandler.searchPhongTro(request, response);
                break;
            case "delete":
                phongTroHandler.deletePhongTro(request, response);
                break;
            case "deleteMultiple":
                phongTroHandler.deleteMultiplePhongTro(request, response);
                break;
            case "add":
                phongTroHandler.addPhongTro(request, response);
                break;
            default:
                phongTroHandler.listPhongTro(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            phongTroHandler.addPhongTro(request, response);
        }
        if ("deleteMultiple".equals(action)) {
            phongTroHandler.deleteMultiplePhongTro(request, response);
        }
    }
}
