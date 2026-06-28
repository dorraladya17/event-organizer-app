package eventorganizer;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * FormPendaftaran
 * Form pendaftaran Peserta Event Organizer.
 * Field sesuai kebutuhan: no, nama, event, status bayar.
 * Mendukung: Tambah, Edit, Hapus, dan tampil data (CRUD).
 */
public class FormPendaftaran extends JFrame {

    private JTextField txtNama;
    private JComboBox<String> cmbEvent;
    private JComboBox<String> cmbStatusBayar;
    private JTable tabelPeserta;
    private DefaultTableModel model;
    private JButton btnSimpan, btnUbah, btnHapus, btnBatal;
    private int idTerpilih = -1; // menyimpan "no" baris yang dipilih dari tabel

    public FormPendaftaran() {
        setTitle("Form Pendaftaran Peserta Event");
        setSize(750, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
        muatComboEvent();
        tampilkanData();
    }

    private void initComponents() {
        setLayout(null);

        JLabel lblJudul = new JLabel("PENDAFTARAN PESERTA EVENT");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblJudul.setBounds(20, 15, 400, 25);
        add(lblJudul);

        // ===== Panel Form Input =====
        JPanel panelForm = new JPanel();
        panelForm.setBorder(BorderFactory.createTitledBorder("Data Peserta"));
        panelForm.setBounds(20, 50, 700, 130);
        panelForm.setLayout(null);
        add(panelForm);

        JLabel lblNama = new JLabel("Nama Peserta");
        lblNama.setBounds(20, 25, 120, 25);
        panelForm.add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(150, 25, 250, 28);
        panelForm.add(txtNama);

        JLabel lblEvent = new JLabel("Nama Event");
        lblEvent.setBounds(20, 60, 120, 25);
        panelForm.add(lblEvent);

        cmbEvent = new JComboBox<>();
        cmbEvent.setBounds(150, 60, 250, 28);
        panelForm.add(cmbEvent);

        JLabel lblStatus = new JLabel("Status Bayar");
        lblStatus.setBounds(420, 25, 120, 25);
        panelForm.add(lblStatus);

        cmbStatusBayar = new JComboBox<>(new String[]{"Sudah Bayar", "Belum Bayar"});
        cmbStatusBayar.setBounds(520, 25, 150, 28);
        panelForm.add(cmbStatusBayar);

        btnSimpan = new JButton("Simpan");
        btnSimpan.setBackground(new Color(39, 174, 96));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setBounds(420, 65, 90, 30);
        panelForm.add(btnSimpan);

        btnUbah = new JButton("Ubah");
        btnUbah.setBackground(new Color(41, 128, 185));
        btnUbah.setForeground(Color.WHITE);
        btnUbah.setBounds(520, 65, 80, 30);
        panelForm.add(btnUbah);

        btnHapus = new JButton("Hapus");
        btnHapus.setBackground(new Color(192, 57, 43));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setBounds(610, 65, 80, 30);
        panelForm.add(btnHapus);

        btnBatal = new JButton("Batal / Bersihkan Form");
        btnBatal.setBounds(150, 95, 180, 28);
        panelForm.add(btnBatal);

        // ===== Tabel Data Peserta =====
        model = new DefaultTableModel(new Object[]{"No", "Nama", "Event", "Status Bayar"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPeserta = new JTable(model);
        tabelPeserta.setRowHeight(24);
        JScrollPane scrollPane = new JScrollPane(tabelPeserta);
        scrollPane.setBounds(20, 195, 700, 290);
        add(scrollPane);

        // ===== Event Listener =====
        btnSimpan.addActionListener(e -> simpanData());
        btnUbah.addActionListener(e -> ubahData());
        btnHapus.addActionListener(e -> hapusData());
        btnBatal.addActionListener(e -> bersihkanForm());

        tabelPeserta.getSelectionModel().addListSelectionListener(e -> isiFormDariTabel());
    }

    /** Mengambil daftar event dari tabel tb_event untuk mengisi combo box */
    private void muatComboEvent() {
        cmbEvent.removeAllItems();
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            String sql = "SELECT id_event, nama_event FROM tb_event ORDER BY nama_event";
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                // format "id - nama" supaya id_event mudah diambil kembali
                cmbEvent.addItem(rs.getInt("id_event") + " - " + rs.getString("nama_event"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat data event: " + e.getMessage());
        }
    }

    /** Menampilkan seluruh data peserta (join dengan tb_event) ke JTable */
    private void tampilkanData() {
        model.setRowCount(0);
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            String sql = "SELECT no, nama, event, status_bayar FROM v_peserta_event ORDER BY no";
            Statement st = konek.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("no"),
                        rs.getString("nama"),
                        rs.getString("event"),
                        rs.getString("status_bayar")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menampilkan data: " + e.getMessage());
        }
    }

    /** Mengambil id_event dari teks combo box (format "id - nama") */
    private int ambilIdEventTerpilih() {
        String terpilih = (String) cmbEvent.getSelectedItem();
        if (terpilih == null) return -1;
        return Integer.parseInt(terpilih.split(" - ")[0]);
    }

    private void simpanData() {
        String nama = txtNama.getText().trim();
        if (nama.isEmpty() || cmbEvent.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Nama dan Event wajib diisi!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            String sql = "INSERT INTO tb_peserta (nama, id_event, status_bayar) VALUES (?, ?, ?)";
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setString(1, nama);
            ps.setInt(2, ambilIdEventTerpilih());
            ps.setString(3, (String) cmbStatusBayar.getSelectedItem());
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data peserta berhasil disimpan!");
            bersihkanForm();
            tampilkanData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data: " + e.getMessage());
        }
    }

    private void ubahData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            String sql = "UPDATE tb_peserta SET nama = ?, id_event = ?, status_bayar = ? WHERE no = ?";
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setString(1, txtNama.getText().trim());
            ps.setInt(2, ambilIdEventTerpilih());
            ps.setString(3, (String) cmbStatusBayar.getSelectedItem());
            ps.setInt(4, idTerpilih);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data peserta berhasil diubah!");
            bersihkanForm();
            tampilkanData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengubah data: " + e.getMessage());
        }
    }

    private void hapusData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data pada tabel terlebih dahulu!",
                    "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int konfirmasi = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data ini?", "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);
        if (konfirmasi != JOptionPane.YES_OPTION) return;

        try (Connection konek = Koneksi.getKoneksi()) {
            if (konek == null) return;
            String sql = "DELETE FROM tb_peserta WHERE no = ?";
            PreparedStatement ps = konek.prepareStatement(sql);
            ps.setInt(1, idTerpilih);
            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data peserta berhasil dihapus!");
            bersihkanForm();
            tampilkanData();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menghapus data: " + e.getMessage());
        }
    }

    /** Mengisi form input otomatis ketika baris tabel dipilih (untuk Edit/Hapus) */
    private void isiFormDariTabel() {
        int baris = tabelPeserta.getSelectedRow();
        if (baris == -1) return;

        idTerpilih = (int) model.getValueAt(baris, 0);
        txtNama.setText((String) model.getValueAt(baris, 1));
        String namaEvent = (String) model.getValueAt(baris, 2);
        cmbStatusBayar.setSelectedItem(model.getValueAt(baris, 3));

        // Cocokkan nama event pada combo box
        for (int i = 0; i < cmbEvent.getItemCount(); i++) {
            if (cmbEvent.getItemAt(i).endsWith(namaEvent)) {
                cmbEvent.setSelectedIndex(i);
                break;
            }
        }
    }

    private void bersihkanForm() {
        txtNama.setText("");
        if (cmbEvent.getItemCount() > 0) cmbEvent.setSelectedIndex(0);
        cmbStatusBayar.setSelectedIndex(0);
        idTerpilih = -1;
        tabelPeserta.clearSelection();
    }
}
