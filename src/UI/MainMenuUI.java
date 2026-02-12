package UI;

import javax.swing.*;
import java.awt.*;

public class MainMenuUI extends JFrame {

    public MainMenuUI() {
        setTitle("Gym Management - Menu Principal");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initUI();
    }

    private void initUI() {

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        JButton btnClients = new JButton("Gestion des Clients ");
        JButton btnTypes = new JButton("Types d’Abonnement ");
        JButton btnAbonnements = new JButton("Abonnements");
        JButton btnLogout = new JButton("Déconnexion");

        panel.add(btnClients);
        panel.add(btnTypes);
        panel.add(btnAbonnements);
        panel.add(btnLogout);

        add(panel);

        // ===== ACTIONS =====
        btnClients.addActionListener(e -> new ClientUI().setVisible(true));
        btnTypes.addActionListener(e -> new TypeAbonnementUI().setVisible(true));
        btnAbonnements.addActionListener(e -> new AbonnementUI().setVisible(true));

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginUI().setVisible(true);
        });
    }
}
