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
        List<PhongTro> phongTros = new ArrayList<>();
        try (Connection connection = DBConfig.getConnection(); CallableStatement stmt = connection.prepareCall("{CALL TimKiemPhongTro(?)}")) {

            stmt.setString(1, keyword);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String ten_nguoi_thue = rs.getString("ten_nguoi_thue");
                String so_dien_thoai = rs.getString("so_dien_thoai");
                Date ngay_bat_dau = rs.getDate("ngay_bat_dau");
                String hinh_thuc_tt = String.valueOf(rs.getInt("hinh_thuc_tt_id"));
                String ghi_chu = rs.getString("ghi_chu");
                PhongTro phongTro = new PhongTro(id, ten_nguoi_thue, so_dien_thoai,
                        ngay_bat_dau, hinh_thuc_tt, ghi_chu);
                phongTros.add(phongTro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phongTros;
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

        String query = "INSERT INTO phong_tro (ten_nguoi_thue, so_dien_thoai, ngay_bat_dau, hinh_thuc_tt_id, ghi_chu) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // Gán các giá trị vào PreparedStatement
            stmt.setString(1, phongTro.getTenNguoiThue());
            stmt.setString(2, phongTro.getSoDienThoai());
            stmt.setDate(3, phongTro.getNgayBatDau());
            stmt.setInt(4, Integer.parseInt(phongTro.getHinhThucThanhToan()));
            stmt.setString(5, phongTro.getGhiChu());

           int affectedRows = stmt.executeUpdate();


            if (affectedRows > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    long idPhongTro = generatedKeys.getLong(1);
                    String tenHinhThucQuery = "SELECT ten_hinh_thuc FROM hinh_thuc_tt WHERE id = ?";
                    try (PreparedStatement stmt2 = conn.prepareStatement(tenHinhThucQuery)) {
                        stmt2.setInt(1, Integer.parseInt(phongTro.getHinhThucThanhToan()));
                        ResultSet rs = stmt2.executeQuery();
                        if (rs.next()) {
                            String tenHinhThuc = rs.getString("ten_hinh_thuc");

                            String updateQuery = "UPDATE phong_tro SET ten_hinh_thuc = ? WHERE id = ?";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.setString(1, tenHinhThuc);
                                updateStmt.setLong(2, idPhongTro);
                                updateStmt.executeUpdate();
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
