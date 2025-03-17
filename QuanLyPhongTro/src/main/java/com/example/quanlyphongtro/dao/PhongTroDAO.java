package com.example.quanlyphongtro.dao;

import com.example.quanlyphongtro.config.DBConfig;
import com.example.quanlyphongtro.model.PhongTro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class PhongTroDAO {
    public List<PhongTro> getAllPhongTro() {
        List<PhongTro> danhSach = new ArrayList<>();
        String sql = "SELECT pt.id, pt.ten_nguoi_thue, pt.so_dien_thoai, pt.ngay_bat_dau, htt.ten_hinh_thuc AS hinh_thuc_tt, pt.ghi_chu\n" +
                "FROM phong_tro pt\n" +
                "JOIN hinh_thuc_tt htt ON pt.hinh_thuc_tt_id = htt.id;\n";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhongTro phongTro = new PhongTro();
                phongTro.setId(rs.getInt("id"));
                phongTro.setTenNguoiThue(rs.getString("ten_nguoi_thue"));
                phongTro.setSoDienThoai(rs.getString("so_dien_thoai"));
                phongTro.setNgayBatDau(rs.getDate("ngay_bat_dau"));
                phongTro.setHinhThucThanhToan(rs.getString("hinh_thuc_tt"));
                phongTro.setGhiChu(rs.getString("ghi_chu"));

                danhSach.add(phongTro);

//                System.out.println("Lấy dữ liệu: " + phongTro.getId() + " - " + phongTro.getTenNguoiThue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public List<PhongTro> searchPhongTro(String keyword) {
        List<PhongTro> danhSach = new ArrayList<>();
        String sql = "SELECT pt.id, pt.ten_nguoi_thue, pt.so_dien_thoai, pt.ngay_bat_dau, ht.ten_hinh_thuc AS hinh_thuc_tt, pt.ghi_chu " +
                "FROM phong_tro pt JOIN hinh_thuc_tt ht ON pt.hinh_thuc_tt_id = ht.id " +
                "WHERE pt.id LIKE ? OR pt.ten_nguoi_thue LIKE ? OR pt.so_dien_thoai LIKE ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String searchParam = "%" + keyword + "%";
            stmt.setString(1, searchParam);
            stmt.setString(2, searchParam);
            stmt.setString(3, searchParam);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                danhSach.add(new PhongTro(
                        rs.getInt("id"),
                        rs.getString("ten_nguoi_thue"),
                        rs.getString("so_dien_thoai"),
                        rs.getDate("ngay_bat_dau"),
                        rs.getString("hinh_thuc_tt"),
                        rs.getString("ghi_chu")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSach;
    }

    public PhongTro getPhongTroById(int id) {
        PhongTro phongTro = null;
        String sql = "SELECT pt.id, pt.ten_nguoi_thue, pt.so_dien_thoai, pt.ngay_bat_dau, ht.ten_hinh_thuc, pt.ghi_chu " +
                "FROM phong_tro pt JOIN hinh_thuc_tt ht ON pt.hinh_thuc_tt_id = ht.id " +
                "WHERE pt.id = ?";

        try (PreparedStatement statement = DBConfig.getConnection().prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                phongTro = new PhongTro();
                phongTro.setId(resultSet.getInt("id"));
                phongTro.setTenNguoiThue(resultSet.getString("ten_nguoi_thue"));
                phongTro.setSoDienThoai(resultSet.getString("so_dien_thoai"));
                phongTro.setNgayBatDau(resultSet.getDate("ngay_bat_dau"));
                phongTro.setHinhThucThanhToan(resultSet.getString("ten_hinh_thuc"));
                phongTro.setGhiChu(resultSet.getString("ghi_chu"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return phongTro;
    }

    public void deletePhongTro(int id) {
        String sql = "DELETE FROM phong_tro WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMultiplePhongTro(int[] ids) {
        String sql = "DELETE FROM phong_tro WHERE id = ?";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int id : ids) {
                ps.setInt(1, id);
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPhongTro(PhongTro phongTro) {
        String sql = "INSERT INTO phong_tro (ten_nguoi_thue, so_dien_thoai, ngay_bat_dau, hinh_thuc_thanh_toan, ghi_chu) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection(); PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, phongTro.getTenNguoiThue());
            statement.setString(2, phongTro.getSoDienThoai());
            statement.setDate(3, new java.sql.Date(phongTro.getNgayBatDau().getTime()));
            statement.setString(4, phongTro.getHinhThucThanhToan());
            statement.setString(5, phongTro.getGhiChu());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Phòng trọ đã được thêm thành công!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
