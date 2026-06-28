🎫 Event Organizer — Aplikasi Pendaftaran Peserta Event
Aplikasi desktop berbasis Java Swing untuk mengelola pendaftaran peserta sebuah event/kegiatan, terintegrasi dengan basis data MySQL melalui XAMPP. Dibangun menggunakan NetBeans IDE.

Aplikasi ini membantu panitia/Event Organizer mencatat data peserta secara digital — mulai dari nama peserta, event yang diikuti, hingga status pembayaran — tanpa perlu mencatat manual di buku atau spreadsheet.

Fitur
Login Admin — autentikasi sebelum mengakses sistem
Pendaftaran Peserta — input nama peserta, pilih event, dan status pembayaran
CRUD Lengkap — Tambah, Lihat, Ubah, dan Hapus data peserta
Manajemen Event — data event (nama, tanggal, lokasi, biaya) tersimpan di database
Status Pembayaran — pantau peserta yang Sudah Bayar / Belum Bayar secara real-time
Database Terelasi — peserta terhubung ke event lewat foreign key, tidak ada data ganda
eknologi yang Digunakan
Komponen	Teknologi
Bahasa Program	Java (Swing GUI)
IDE	NetBeans
Database	MySQL (via XAMPP)
Konektor DB	JDBC — MySQL Connector/J

📂 Struktur Project
EventOrganizer/
├── src/eventorganizer/
│   ├── Main.java              # Entry point aplikasi
│   ├── Koneksi.java           # Koneksi ke database MySQL
│   ├── FormLogin.java         # Form login admin
│   ├── FormUtama.java         # Dashboard / menu utama
│   └── FormPendaftaran.java   # Form CRUD pendaftaran peserta
├── database_event_organizer.sql   # Script pembuatan database
├── lib/                        # Tempat driver MySQL Connector/J
├── nbproject/                  # Konfigurasi project NetBeans
└── BACA_SAYA.txt               # Panduan instalasi singkat (Bahasa Indonesia)
Struktur Database
Database db_event_organizer terdiri dari 3 tabel:

Tabel	Keterangan
tb_user	Akun login admin (username, password)
tb_event	Data event (nama, tanggal, lokasi, biaya)
tb_peserta	Data peserta — no, nama, event (FK), status_bayar
Relasi: satu tb_event dapat memiliki banyak tb_peserta (one-to-many).

Cara Menjalankan
1. Siapkan Database
Nyalakan Apache dan MySQL di XAMPP Control Panel
Buka http://localhost/phpmyadmin → tab SQL
Jalankan isi file database_event_organizer.sql
2. Siapkan Driver JDBC
Download MySQL Connector/J dari dev.mysql.com/downloads/connector/j
Taruh file .jar hasil download ke folder lib/
3. Buka di NetBeans
File → Open Project → pilih folder EventOrganizer
Klik kanan project → Properties → Libraries → Add JAR/Folder → pilih jar di folder lib/
Klik kanan project → Clean and Build
4. Jalankan
Tekan F6 atau klik kanan project → Run
Login dengan:
Username: admin
Password: admin123
Panduan lengkap dan detail tersedia di BACA_SAYA.txt.

Form Login	Form Pendaftaran Peserta
Input username & password dengan validasi ke database	Input nama, pilih event, pilih status bayar, lengkap dengan tabel data peserta
(Tambahkan screenshot aplikasi kamu di sini setelah dijalankan, contoh: ![Form Login](screenshots/login.png))




