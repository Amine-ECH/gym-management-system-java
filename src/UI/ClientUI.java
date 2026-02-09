package UI;

import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ClientUI extends JFrame {

    private JTextField txtNom, txtPrenom, txtTelephone;
    private JTable tableClients;
    private DefaultTableModel tableModel;

    private ClientDAO clientDAO = new ClientDAO();
    private int selectedClientId = -1;

    // ===== AJOUT =====
    private JButton btnModifier;
    private JButton btnSupprimer;

    public ClientUI() {
        setTitle("Gestion des Clients");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadClients();
    }

    private void initUI() {
        // ====== FORMULAIRE ======
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations Client"));

        txtNom = new JTextField();
        txtPrenom = new JTextField();
        txtTelephone = new JTextField();

        formPanel.add(new JLabel("Nom :"));
        formPanel.add(txtNom);
        formPanel.add(new JLabel("Prénom :"));
        formPanel.add(txtPrenom);
        formPanel.add(new JLabel("Téléphone :"));
        formPanel.add(txtTelephone);

        // ====== BOUTONS ======
        JPanel buttonPanel = new JPanel();

        JButton btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");     // ===== AJOUT =====
        btnSupprimer = new JButton("Supprimer");   // ===== AJOUT =====
        JButton btnAnnuler = new JButton("Annuler");
        JButton btnRetour = new JButton("⬅ Retour au menu");
        buttonPanel.add(btnRetour);

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnAnnuler);

        // ===== AJOUT : désactiver au départ =====
        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);

        // ====== TABLE ======
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nom", "Prénom", "Téléphone"}, 0
        );
        tableClients = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableClients);

        // ====== LAYOUT ======
        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ====== EVENTS ======
        btnAjouter.addActionListener(e -> ajouterClient());
        btnModifier.addActionListener(e -> modifierClient());
        btnSupprimer.addActionListener(e -> supprimerClient());
        btnAnnuler.addActionListener(e -> clearForm());

        btnRetour.addActionListener(e -> {
            dispose();
            new MainMenuUI().setVisible(true);
        });

        tableClients.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                remplirFormDepuisTable();
            }
        });
    }

    // ================= CRUD =================

    private void ajouterClient() {
        Client c = new Client();
        c.setNom(txtNom.getText());
        c.setPrenom(txtPrenom.getText());
        c.setTelephone(txtTelephone.getText());

        clientDAO.insert(c);
        loadClients();
        clearForm();
    }

    private void modifierClient() {
        if (selectedClientId == -1) return;

        Client c = new Client();
        c.setIdClient(selectedClientId);
        c.setNom(txtNom.getText());
        c.setPrenom(txtPrenom.getText());
        c.setTelephone(txtTelephone.getText());

        clientDAO.update(c);
        loadClients();
        clearForm();
    }

    private void supprimerClient() {
        if (selectedClientId == -1) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Supprimer ce client ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            clientDAO.delete(selectedClientId);
            loadClients();
            clearForm();
        }
    }

    // ================= UTIL =================

    private void loadClients() {
        tableModel.setRowCount(0);
        List<Client> clients = clientDAO.findAll();

        for (Client c : clients) {
            tableModel.addRow(new Object[]{
                    c.getIdClient(),
                    c.getNom(),
                    c.getPrenom(),
                    c.getTelephone()
            });
        }
    }

    private void remplirFormDepuisTable() {
        int row = tableClients.getSelectedRow();
        if (row >= 0) {
            selectedClientId = (int) tableModel.getValueAt(row, 0);
            txtNom.setText(tableModel.getValueAt(row, 1).toString());
            txtPrenom.setText(tableModel.getValueAt(row, 2).toString());
            txtTelephone.setText(tableModel.getValueAt(row, 3).toString());

            // ===== AJOUT =====
            btnModifier.setEnabled(true);
            btnSupprimer.setEnabled(true);
        }
    }

    private void clearForm() {
        txtNom.setText("");
        txtPrenom.setText("");
        txtTelephone.setText("");
        selectedClientId = -1;
        tableClients.clearSelection();

        // ===== AJOUT =====
        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);
    }
}
