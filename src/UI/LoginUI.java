package UI;

import dao.AdminDAO;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame {

    private JTextField txtUser;
    private JPasswordField txtPass;
    private AdminDAO adminDAO = new AdminDAO();

    public LoginUI() {
        setTitle("Login - Gym Management");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtUser = new JTextField();
        txtPass = new JPasswordField();

        JButton btnLogin = new JButton("Connexion");

        panel.add(new JLabel("Username :"));
        panel.add(txtUser);
        panel.add(new JLabel("Password :"));
        panel.add(txtPass);
        panel.add(new JLabel());
        panel.add(btnLogin);

        add(panel);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        String username = txtUser.getText();
        String password = new String(txtPass.getPassword());

        if (adminDAO.login(username, password)) {
            JOptionPane.showMessageDialog(this, "Connexion réussie ✅");
            dispose(); // fermer login

            // ouvrir l'application
            new MainMenuUI().setVisible(true);

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Username ou password incorrect ❌",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
