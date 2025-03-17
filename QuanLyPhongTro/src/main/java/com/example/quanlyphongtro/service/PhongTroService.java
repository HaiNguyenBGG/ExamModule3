package com.example.quanlyphongtro.service;

import com.example.quanlyphongtro.dao.PhongTroDAO;
import com.example.quanlyphongtro.model.PhongTro;

import java.util.List;

public class PhongTroService implements IPhongTroService {
    private PhongTroDAO phongTroDAO;

    public PhongTroService() {
        this.phongTroDAO = new PhongTroDAO();
    }

    public List<PhongTro> searchPhongTro(String keyword) {
        return phongTroDAO.searchPhongTro(keyword);
    }

    @Override
    public List<PhongTro> getAllPhongTro() {
        return phongTroDAO.getAllPhongTro();
    }

    public PhongTro getPhongTroById(int id) {
        return phongTroDAO.getPhongTroById(id);
    }

    public void deletePhongTro(int id) {
        phongTroDAO.deletePhongTro(id);
    }

}