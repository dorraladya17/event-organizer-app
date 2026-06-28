package eventorganizer;

import java.awt.*;
import javax.swing.*;

/**
 * FormUtama
 * Dashboard / menu utama setelah login berhasil.
 * Menyediakan akses ke Form Pendaftaran Peserta Event.
 */
public class FormUtama extends JFrame {

    public FormUtama() {
        setTitle("Dashboard - Aplikasi Event Organizer");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(41, 128, 185));
        panelHeader.setPreferredSize(new Dimension(600, 70));
        panelHeader.setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel("  SELAMAT DATANG DI APLIKASI EVENT ORGANIZER");
        lblHeader.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblHeader.setForeground(Color.WHITE);
        panelHeader.add(lblHeader, BorderLayout.CENTER);

        JPanel panelMenu = new JPanel();
        panelMenu.setLayout(new GridLayout(2, 1, 20, 20));
        panelMenu.setBorder(BorderFactory.createEmptyBorder(60, 100, 60, 100));

        JButton btnPendaftaran = new JButton("FORM PENDAFTARAN PESERTA EVENT");
        btnPendaftaran.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnPendaftaran.setBackground(new Color(39, 174, 96));
        btnPendaftaran.setForeground(Color.WHITE);
        btnPendaftaran.setFocusPainted(false);

        JButton btnLogout = new JButton("LOGOUT");
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(new Color(192, 57, 43));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);

        panelMenu.add(btnPendaftaran);
        panelMenu.add(btnLogout);

        btnPendaftaran.addActionListener(e -> {
            new FormPendaftaran().setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            int konfirmasi = JOptionPane.showConfirmDialog(this,
                    "Apakah Anda yakin ingin logout?",
                    "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (konfirmasi == JOptionPane.YES_OPTION) {
                new FormLogin().setVisible(true);
                this.dispose();
            }
        });

        setLayout(new BorderLayout());
        add(panelHeader, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.CENTER);
    }
}
