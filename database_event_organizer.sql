-- =====================================================
-- DATABASE: db_event_organizer
-- Aplikasi: Pendaftaran Peserta Event Organizer
-- Tools: XAMPP (MySQL/MariaDB) + phpMyAdmin
-- =====================================================

CREATE DATABASE IF NOT EXISTS db_event_organizer;
USE db_event_organizer;

-- =====================================================
-- TABEL 1: tb_user
-- Untuk menyimpan akun login admin/operator
-- =====================================================
CREATE TABLE tb_user (
    id_user INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    level VARCHAR(20) NOT NULL DEFAULT 'admin',
    PRIMARY KEY (id_user),
    UNIQUE KEY username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data default untuk login (username: admin, password: admin123)
INSERT INTO tb_user (username, password, level) VALUES
('admin', 'admin123', 'admin');

-- =====================================================
-- TABEL 2: tb_event
-- Untuk menyimpan data event yang diselenggarakan
-- =====================================================
CREATE TABLE tb_event (
    id_event INT(11) NOT NULL AUTO_INCREMENT,
    nama_event VARCHAR(100) NOT NULL,
    tanggal_event DATE NOT NULL,
    lokasi VARCHAR(100) NOT NULL,
    biaya DECIMAL(10,2) NOT NULL DEFAULT 0,
    PRIMARY KEY (id_event)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Contoh data event
INSERT INTO tb_event (nama_event, tanggal_event, lokasi, biaya) VALUES
('Seminar Teknologi AI', '2026-07-10', 'Gedung Serbaguna A', 150000),
('Workshop Web Development', '2026-07-20', 'Aula Kampus', 100000),
('Konser Musik Amal', '2026-08-05', 'Lapangan Utama', 75000);

-- =====================================================
-- TABEL 3: tb_peserta
-- Tabel utama: no, nama, event, status bayar
-- =====================================================
CREATE TABLE tb_peserta (
    no INT(11) NOT NULL AUTO_INCREMENT,
    nama VARCHAR(100) NOT NULL,
    id_event INT(11) NOT NULL,
    status_bayar ENUM('Sudah Bayar','Belum Bayar') NOT NULL DEFAULT 'Belum Bayar',
    tanggal_daftar DATE NOT NULL DEFAULT CURRENT_DATE,
    PRIMARY KEY (no),
    KEY fk_event (id_event),
    CONSTRAINT fk_peserta_event FOREIGN KEY (id_event)
        REFERENCES tb_event (id_event)
        ON DELETE CASCADE
        ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Contoh data peserta
INSERT INTO tb_peserta (nama, id_event, status_bayar) VALUES
('Andi Saputra', 1, 'Sudah Bayar'),
('Budi Hartono', 2, 'Belum Bayar'),
('Citra Dewi', 1, 'Sudah Bayar');

-- =====================================================
-- VIEW (opsional, untuk mempermudah tampilan JOIN)
-- Menampilkan: no, nama, nama_event, status_bayar
-- =====================================================
CREATE OR REPLACE VIEW v_peserta_event AS
SELECT
    p.no,
    p.nama,
    e.nama_event AS event,
    p.status_bayar,
    p.tanggal_daftar,
    p.id_event
FROM tb_peserta p
JOIN tb_event e ON p.id_event = e.id_event;
