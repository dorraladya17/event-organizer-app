package eventorganizer;

import javax.swing.SwingUtilities;

/**
 * Main
 * Kelas utama (entry point) untuk menjalankan aplikasi.
 * Menjalankan FormLogin sebagai tampilan pertama.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FormLogin().setVisible(true);
        });
    }
}
