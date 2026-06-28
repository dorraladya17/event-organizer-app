package eventorganizer;

import java.awt.*;
import java.sql.*;
import javax.swing.*;

/**
 * FormLogin
 * Form login untuk masuk ke aplikasi Event Organizer.
 * Validasi username & password ke tabel tb_user.
 */
public class FormLogin extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnKeluar;

    public FormLogin() {
        setTitle("Login - Aplikasi Event Organizer");
        setSize(420, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        initComponents();
    }

    private void initComponents() {
        JPanel panelUtama = new JPanel();
        panelUtama.setBackground(new Color(41, 128, 185));
        panelUtama.setLayout(null);
        setContentPane(panelUtama);

        JLabel lblJudul = new JLabel("EVENT ORGANIZER");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setBounds(80, 20, 300, 30);
        panelUtama.add(lblJudul);

        JLabel lblSub = new JLabel("Silakan login untuk melanjutkan");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSub.setForeground(Color.WHITE);
        lblSub.setBounds(110, 55, 250, 20);
        panelUtama.add(lblSub);

        JPanel panelForm = new JPanel();
        panelForm.setBackground(Color.WHITE);
        panelForm.setBounds(40, 95, 340, 180);
        panelForm.setLayout(null);
        panelUtama.add(panelForm);

        JLabel lblUser = new JLabel("Username");
        lblUser.setBounds(30, 25, 100, 25);
        panelForm.add(lblUser);

        txtUsername = new JTextField();
        txtUsername.setBounds(30, 50, 280, 30);
        panelForm.add(txtUsername);

        JLabel lblPass = new JLabel("Password");
        lblPass.setBounds(30, 85, 100, 25);
        panelForm.add(lblPass);

        txtPassword = new JPasswordField();
        txtPassword.setBounds(30, 110, 280, 30);
        panelForm.add(txtPassword);

        btnLogin = new JButton("LOGIN");
        btnLogin.setBackground(new Color(41, 128, 185));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        btnLogin.setBounds(30, 150, 130, 35);
        panelForm.add(btnLogin);

        btnKeluar = new JButton("KELUAR");
        btnKeluar.setBackground(new Color(192, 57, 43));
        btnKeluar.setForeground(Color.WHITE);
        btnKeluar.setFocusPainted(false);
        btnKeluar.setBounds(180, 150, 130, 35);
        panelForm.add(btnKeluar);

        btnLogin.addActionListener(e -> prosesLogin());
        btnKeluar.addActionListener(e -> System.exit(0));

        // Enter di password langsung trigger login
        txtPassword.addActionListener(e -> prosesLogin());
    }

    private void prosesLogin() {
        String username = txtUsername.getText().trim();
        String password = String.valueOf(txtPassword.getPassword()).trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) {
                return;
            }
            String sql = "SELECT * FROM tb_user WHERE username = ? AND password = ?";
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this,
                        "Login berhasil! Selamat datang, " + username,
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
                new FormUtama().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Username atau password salah!",
                        "Gagal Login", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Terjadi kesalahan database:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormLogin().setVisible(true));
    }
}
