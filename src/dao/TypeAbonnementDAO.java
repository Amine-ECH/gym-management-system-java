package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.TypeAbonnement;
import util.DBconnection;

public class TypeAbonnementDAO {

    //insert
    public boolean insert(TypeAbonnement t) {
        String sql = "INSERT INTO type_abonnement (libelle, duree_mois, prix) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getLibelle());
            ps.setInt(2, t.getDureeMois());
            ps.setDouble(3, t.getPrix());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur INSERT type_abonnement");
            e.printStackTrace();
            return false;
        }
    }

    // ================= UPDATE 
    public boolean update(TypeAbonnement t) {
        String sql = "UPDATE type_abonnement SET libelle=?, duree_mois=?, prix=? WHERE id_type=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, t.getLibelle());
            ps.setInt(2, t.getDureeMois());
            ps.setDouble(3, t.getPrix());
            ps.setInt(4, t.getIdType());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur UPDATE type_abonnement");
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE =================
    public boolean delete(int idType) {
        String sql = "DELETE FROM type_abonnement WHERE id_type=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idType);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur DELETE type_abonnement");
            e.printStackTrace();
            return false;
        }
    }

    // ================= FIND ALL =================
    public List<TypeAbonnement> findAll() {
        List<TypeAbonnement> list = new ArrayList<>();
        String sql = "SELECT * FROM type_abonnement";

        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                TypeAbonnement t = new TypeAbonnement();
                t.setIdType(rs.getInt("id_type"));
                t.setLibelle(rs.getString("libelle"));
                t.setDureeMois(rs.getInt("duree_mois"));
                t.setPrix(rs.getDouble("prix"));
                list.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erreur FIND ALL type_abonnement");
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY ID =================
    public TypeAbonnement findById(int idType) {
        String sql = "SELECT * FROM type_abonnement WHERE id_type=?";
        TypeAbonnement t = null;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idType);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                t = new TypeAbonnement();
                t.setIdType(rs.getInt("id_type"));
                t.setLibelle(rs.getString("libelle"));
                t.setDureeMois(rs.getInt("duree_mois"));
                t.setPrix(rs.getDouble("prix"));
            }

        } catch (SQLException e) {
            System.out.println("Erreur FIND type_abonnement");
            e.printStackTrace();
        }

        return t;
    }
}
