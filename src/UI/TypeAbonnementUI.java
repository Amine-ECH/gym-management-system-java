package UI;

import dao.TypeAbonnementDAO;
import model.TypeAbonnement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TypeAbonnementUI extends JFrame {

    private JTextField txtLibelle, txtDuree, txtPrix;
    private JTable table;
    private DefaultTableModel model;

    private TypeAbonnementDAO dao = new TypeAbonnementDAO();
    private int selectedId = -1;

    public TypeAbonnementUI() {
        setTitle("Gestion des Types d’Abonnement");
        setSize(700, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadData();
    }

    private void initUI() {

        // ===== FORMULAIRE =====
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Type Abonnement"));

        txtLibelle = new JTextField();
        txtDuree = new JTextField();
        txtPrix = new JTextField();

        formPanel.add(new JLabel("Libellé :"));
        formPanel.add(txtLibelle);
        formPanel.add(new JLabel("Durée (mois) :"));
        formPanel.add(txtDuree);
        formPanel.add(new JLabel("Prix :"));
        formPanel.add(txtPrix);

        // ===== BOUTONS =====
        JPanel buttonPanel = new JPanel();

        JButton btnAjouter = new JButton("Ajouter");
        JButton btnModifier = new JButton("Modifier");
        JButton btnSupprimer = new JButton("Supprimer");
        JButton btnAnnuler = new JButton("Annuler");
        JButton btnRetour = new JButton("⬅ Retour au menu");
        buttonPanel.add(btnRetour);

        buttonPanel.add(btnAjouter);
        buttonPanel.add(btnModifier);
        buttonPanel.add(btnSupprimer);
        buttonPanel.add(btnAnnuler);

        // ===== TABLE =====
        model = new DefaultTableModel(
                new Object[]{"ID", "Libellé", "Durée (mois)", "Prix"}, 0
        );
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // ===== LAYOUT =====
        setLayout(new BorderLayout(10, 10));
        add(formPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ===== EVENTS =====
        btnAjouter.addActionListener(e -> ajouter());
        btnModifier.addActionListener(e -> modifier());
        btnSupprimer.addActionListener(e -> supprimer());
        btnAnnuler.addActionListener(e -> clear());
        btnRetour.addActionListener(e -> {
            dispose();
            new MainMenuUI().setVisible(true);
        });

        
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                remplirForm();
            }
        });
    }

    // ================= CRUD =================

    private void ajouter() {
        TypeAbonnement t = new TypeAbonnement();
        t.setLibelle(txtLibelle.getText());
        t.setDureeMois(Integer.parseInt(txtDuree.getText()));
        t.setPrix(Double.parseDouble(txtPrix.getText()));

        dao.insert(t);
        loadData();
        clear();
    }

    private void modifier() {
        if (selectedId == -1) return;

        TypeAbonnement t = new TypeAbonnement();
        t.setIdType(selectedId);
        t.setLibelle(txtLibelle.getText());
        t.setDureeMois(Integer.parseInt(txtDuree.getText()));
        t.setPrix(Double.parseDouble(txtPrix.getText()));

        dao.update(t);
        loadData();
        clear();
    }

    private void supprimer() {
        if (selectedId == -1) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Supprimer ce type d’abonnement ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            dao.delete(selectedId);
            loadData();
            clear();
        }
    }

    // ================= UTIL =================

    private void loadData() {
        model.setRowCount(0);
        List<TypeAbonnement> list = dao.findAll();

        for (TypeAbonnement t : list) {
            model.addRow(new Object[]{
                    t.getIdType(),
                    t.getLibelle(),
                    t.getDureeMois(),
                    t.getPrix()
            });
        }
    }

    private void remplirForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            selectedId = (int) model.getValueAt(row, 0);
            txtLibelle.setText(model.getValueAt(row, 1).toString());
            txtDuree.setText(model.getValueAt(row, 2).toString());
            txtPrix.setText(model.getValueAt(row, 3).toString());
        }
    }

    private void clear() {
        txtLibelle.setText("");
        txtDuree.setText("");
        txtPrix.setText("");
        selectedId = -1;
        table.clearSelection();
    }
}
