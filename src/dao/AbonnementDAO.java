package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

import model.Abonnement;
import model.Client;
import model.TypeAbonnement;
import util.DBconnection;

public class AbonnementDAO {

    // ================= INSERT =================
    public boolean insert(Abonnement a) {
        String sql = "INSERT INTO abonnement (date_debut, date_fin, statut, id_client, id_type) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(a.getDateDebut()));
            ps.setDate(2, Date.valueOf(a.getDateFin()));
            ps.setString(3, a.getStatut());
            ps.setInt(4, a.getClient().getIdClient());
            ps.setInt(5, a.getTypeAbonnement().getIdType());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur INSERT abonnement");
            e.printStackTrace();
            return false;
        }
    }

    // ================= UPDATE =================
    public boolean update(Abonnement a) {
        String sql = "UPDATE abonnement SET date_debut=?, date_fin=?, statut=?, id_client=?, id_type=? " +
                     "WHERE id_abonnement=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(a.getDateDebut()));
            ps.setDate(2, Date.valueOf(a.getDateFin()));
            ps.setString(3, a.getStatut());
            ps.setInt(4, a.getClient().getIdClient());
            ps.setInt(5, a.getTypeAbonnement().getIdType());
            ps.setInt(6, a.getIdAbonnement());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur UPDATE abonnement");
            e.printStackTrace();
            return false;
        }
    }

    // ================= DELETE =================
    public boolean delete(int id) {
        String sql = "DELETE FROM abonnement WHERE id_abonnement=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur DELETE abonnement");
            e.printStackTrace();
            return false;
        }
    }

    // ================= FIND ALL =================
    public List<Abonnement> findAll() {
        List<Abonnement> list = new ArrayList<>();

        String sql = """
            SELECT a.*, 
                   c.nom, c.prenom,
                   t.libelle
            FROM abonnement a
            JOIN client c ON a.id_client = c.id_client
            JOIN type_abonnement t ON a.id_type = t.id_type
        """;

        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Abonnement a = new Abonnement();
                a.setIdAbonnement(rs.getInt("id_abonnement"));
                a.setDateDebut(rs.getDate("date_debut").toLocalDate());
                a.setDateFin(rs.getDate("date_fin").toLocalDate());
                a.setStatut(rs.getString("statut"));

                Client c = new Client();
                c.setIdClient(rs.getInt("id_client"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));

                TypeAbonnement t = new TypeAbonnement();
                t.setIdType(rs.getInt("id_type"));
                t.setLibelle(rs.getString("libelle"));

                a.setClient(c);
                a.setTypeAbonnement(t);

                list.add(a);
            }

        } catch (SQLException e) {
            System.out.println("Erreur FIND ALL abonnement");
            e.printStackTrace();
        }

        return list;
    }

    // ================= FIND BY ID =================
    public Abonnement findById(int id) {
        String sql = "SELECT * FROM abonnement WHERE id_abonnement=?";
        Abonnement a = null;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                a = new Abonnement();
                a.setIdAbonnement(rs.getInt("id_abonnement"));
                a.setDateDebut(rs.getDate("date_debut").toLocalDate());
                a.setDateFin(rs.getDate("date_fin").toLocalDate());
                a.setStatut(rs.getString("statut"));
            }

        } catch (SQLException e) {
            System.out.println("Erreur FIND abonnement");
            e.printStackTrace();
        }

        return a;
    }
}
