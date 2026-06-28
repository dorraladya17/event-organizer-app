package eventorganizer;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 * Kelas Koneksi
 * Berfungsi untuk menghubungkan aplikasi Java dengan database MySQL (XAMPP).
 * Pastikan service Apache & MySQL pada XAMPP Control Panel sudah berstatus "Running".
 */
public class Koneksi {

    // ====== KONFIGURASI DATABASE (sesuaikan jika perlu) ======
    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "db_event_organizer";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // default XAMPP kosong

    private static final String URL =
            "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB_NAME
            + "?useSSL=false&serverTimezone=Asia/Jakarta";

    public static Connection getKoneksi() {
        Connection konek = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            konek = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                    "Driver MySQL (Connector/J) tidak ditemukan!\n"
                    + "Pastikan file mysql-connector-j-x.x.x.jar sudah ditambahkan ke Library project.\n"
                    + "Detail: " + e.getMessage(),
                    "Error Driver", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Gagal terhubung ke database!\n"
                    + "Pastikan XAMPP (Apache & MySQL) sudah running dan database 'db_event_organizer' sudah dibuat.\n"
                    + "Detail: " + e.getMessage(),
                    "Error Koneksi", JOptionPane.ERROR_MESSAGE);
        }
        return konek;
    }
}
