package UI;

import dao.AbonnementDAO;
import dao.ClientDAO;
import dao.TypeAbonnementDAO;
import model.Abonnement;
import model.Client;
import model.TypeAbonnement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

public class AbonnementUI extends JFrame {

    private JComboBox<Client> cbClient;
    private JComboBox<TypeAbonnement> cbType;
    private JTextField txtDateDebut, txtDateFin;
    private JComboBox<String> cbStatut;

    private JTable table;
    private DefaultTableModel model;

    private AbonnementDAO abonnementDAO = new AbonnementDAO();
    private ClientDAO clientDAO = new ClientDAO();
    private TypeAbonnementDAO typeDAO = new TypeAbonnementDAO();

    private int selectedId = -1;

    // ===== AJOUT =====
    private JButton btnModifier;
    private JButton btnSupprimer;

    public AbonnementUI() {
        setTitle("Gestion des Abonnements");
        setSize(900, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadComboData();
        loadTable();
    }

    private void initUI() {

        // ===== FORM =====
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));
        form.setBorder(BorderFactory.createTitledBorder("Abonnement"));

        cbClient = new JComboBox<>();
        cbType = new JComboBox<>();
        txtDateDebut = new JTextField("2026-01-01");
        txtDateFin = new JTextField();
        txtDateFin.setEditable(false);

        cbStatut = new JComboBox<>(new String[]{"ACTIF", "EXPIRE"}); // 

        form.add(new JLabel("Client :"));
        form.add(cbClient);
        form.add(new JLabel("Type Abonnement :"));
        form.add(cbType);
        form.add(new JLabel("Date début (YYYY-MM-DD) :"));
        form.add(txtDateDebut);
        form.add(new JLabel("Date fin :"));
        form.add(txtDateFin);
        form.add(new JLabel("Statut :"));
        form.add(cbStatut);

        // ===== BUTTONS =====
        JPanel buttons = new JPanel();

        JButton btnAjouter = new JButton("Ajouter");
        btnModifier = new JButton("Modifier");   // ===== AJOUT =====
        btnSupprimer = new JButton("Supprimer"); // ===== AJOUT =====
        JButton btnAnnuler = new JButton("Annuler");
        JButton btnRetour = new JButton("⬅ Retour au menu");
        buttons.add(btnRetour);

        buttons.add(btnAjouter);
        buttons.add(btnModifier);
        buttons.add(btnSupprimer);
        buttons.add(btnAnnuler);

        // ===== AJOUT : désactiver au départ =====
        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID", "Client", "Type", "Début", "Fin", "Statut"}, 0
        );
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        setLayout(new BorderLayout(10, 10));
        add(form, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        // ===== EVENTS =====
        cbType.addActionListener(e -> calculerDateFin()); // calculer date de fin 

        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnAnnuler.addActionListener(e -> clear());
        btnRetour.addActionListener(e -> {
            dispose();
            new MainMenuUI().setVisible(true);
        });

        table.addMouseListener(new MouseAdapter() { // rempliree les champs avec data selectionne
            public void mouseClicked(MouseEvent e) {
                remplirForm();
            }
        });
    }

    // ================= LOGIC =================

    private void calculerDateFin() {
        try {
            TypeAbonnement t = (TypeAbonnement) cbType.getSelectedItem();
            if (t == null) return;

            LocalDate debut = LocalDate.parse(txtDateDebut.getText());
            LocalDate fin = debut.plusMonths(t.getDureeMois());
            txtDateFin.setText(fin.toString());

        } catch (Exception ignored) {
        }
    }

    private void ajouter() {
        Abonnement a = new Abonnement();
        a.setClient((Client) cbClient.getSelectedItem());
        a.setTypeAbonnement((TypeAbonnement) cbType.getSelectedItem());
        a.setDateDebut(LocalDate.parse(txtDateDebut.getText()));
        a.setDateFin(LocalDate.parse(txtDateFin.getText()));
        a.setStatut(cbStatut.getSelectedItem().toString());

        abonnementDAO.insert(a);
        loadTable();
        clear();
    }

    private void modifier() {
        if (selectedId == -1) return;

        Abonnement a = new Abonnement();
        a.setIdAbonnement(selectedId);
        a.setClient((Client) cbClient.getSelectedItem());
        a.setTypeAbonnement((TypeAbonnement) cbType.getSelectedItem());
        a.setDateDebut(LocalDate.parse(txtDateDebut.getText()));
        a.setDateFin(LocalDate.parse(txtDateFin.getText()));
        a.setStatut(cbStatut.getSelectedItem().toString());

        abonnementDAO.update(a);
        loadTable();
        clear();
    }

    private void supprimer() {
        if (selectedId == -1) return;

        int confirm = JOptionPane.showConfirmDialog(
                this, "Supprimer cet abonnement ?", "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            abonnementDAO.delete(selectedId);
            loadTable();
            clear();
        }
    }

    // ================= DATA =================

    private void loadComboData() {
        cbClient.removeAllItems();
        cbType.removeAllItems();

        for (Client c : clientDAO.findAll()) {
            cbClient.addItem(c);
        }

        for (TypeAbonnement t : typeDAO.findAll()) {
            cbType.addItem(t);
        }
    }

    private void loadTable() {
        model.setRowCount(0);

        for (Abonnement a : abonnementDAO.findAll()) {
            model.addRow(new Object[]{
                    a.getIdAbonnement(),
                    a.getClient().getNom() + " " + a.getClient().getPrenom(),
                    a.getTypeAbonnement().getLibelle(),
                    a.getDateDebut(),
                    a.getDateFin(),
                    a.getStatut()
            });
        }
    }

    private void remplirForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) model.getValueAt(row, 0);
            txtDateDebut.setText(model.getValueAt(row, 3).toString());
            txtDateFin.setText(model.getValueAt(row, 4).toString());
            cbStatut.setSelectedItem(model.getValueAt(row, 5).toString());

            // ===== AJOUT =====
            btnModifier.setEnabled(true);
            btnSupprimer.setEnabled(true);
        }
    }

    private void clear() {
        txtDateDebut.setText("");
        txtDateFin.setText(""); 
        selectedId = -1;
        table.clearSelection();

        // ===== AJOUT =====
        btnModifier.setEnabled(false);
        btnSupprimer.setEnabled(false);
    }
}
