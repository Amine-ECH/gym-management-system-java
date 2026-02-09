package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Client;
import util.DBconnection;

public class ClientDAO {

    // INSERT
    public boolean insert(Client client) {
        String sql = "INSERT INTO client (nom, prenom, telephone) VALUES (?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur INSERT client");
            e.printStackTrace();
            return false;
        }
    }

    // UPDATE
    public boolean update(Client client) {
        String sql = "UPDATE client SET nom=?, prenom=?, telephone=? WHERE id_client=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getPrenom());
            ps.setString(3, client.getTelephone());
            ps.setInt(4, client.getIdClient());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur UPDATE client");
            e.printStackTrace();
            return false;
        }
    }

    // DELETE

    public boolean delete(int idClient) {
        String sql = "DELETE FROM client WHERE id_client=?";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erreur DELETE client");
            e.printStackTrace();
            return false;
        }
    }

//======
    // FIND BY ID
    
    public Client findById(int idClient) {
        String sql = "SELECT * FROM client WHERE id_client=?";
        Client client = null;

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setTelephone(rs.getString("telephone"));
            }

        } catch (SQLException e) {
            System.out.println("Erreur FIND client");
            e.printStackTrace();
        }

        return client;
    }

    // ===============================
    // FIND ALL


    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client";

        try (Connection conn = DBconnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Client client = new Client();
                client.setIdClient(rs.getInt("id_client"));
                client.setNom(rs.getString("nom"));
                client.setPrenom(rs.getString("prenom"));
                client.setTelephone(rs.getString("telephone"));
                clients.add(client);
            }

        } catch (SQLException e) {
            System.out.println("Erreur FIND ALL clients");
            e.printStackTrace();
        }

        return clients;
    }
}
