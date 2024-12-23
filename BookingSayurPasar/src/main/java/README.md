# Booking Sayur App

## Code Review

### Struktur dan Organisasi
- **Penamaan**: Nama kelas, metode, dan variabel sudah cukup deskriptif, namun perlu pemisahan logika dan tampilan dengan penerapan pola **Model-View-Controller (MVC)**.
- **Fungsi yang Terlalu Panjang**: Beberapa logika dalam tombol seperti tambah, update, dan hapus pesanan dapat diekstrak menjadi metode terpisah untuk menghindari duplikasi.
- **Dokumentasi**: Tambahkan komentar atau gunakan **JavaDoc** untuk metode penting agar lebih mudah dipahami.

### Fungsi dan Logika
- **Validasi Input**: Validasi input untuk kolom "Jumlah" sudah baik, tetapi tambahkan batasan untuk jumlah minimum dan maksimum.
- **Error Handling**: Penanganan kesalahan seperti file I/O perlu ditingkatkan, misalnya menampilkan pesan error lebih rinci jika terjadi kegagalan.

### Standar Coding
- **Format Kode**: Gunakan alat seperti **CheckStyle** atau **Google Java Format** untuk memastikan konsistensi kode.
- **Penghapusan Duplikasi**: Refaktor logika tombol untuk mengurangi pengulangan kode serupa.

### Efisiensi
- **Penyimpanan Data**: Pertimbangkan untuk menggunakan format JSON dengan library seperti **Gson** untuk menyimpan data pesanan agar lebih fleksibel.
- **Optimasi Tampilan**: Pastikan antarmuka responsif di berbagai resolusi layar.

### Keamanan
- **Validasi Data**: Tambahkan sanitasi input untuk mencegah potensi masalah keamanan, seperti karakter khusus dalam input pengguna.
- **Akses File**: Pastikan hanya file yang diizinkan dapat ditulis/dibaca oleh aplikasi.

## Testing

### Pengujian Fungsional
1. **Tambah Pesanan**:
   - Input valid: Data ditambahkan ke tabel dan kolom input dikosongkan.
   - Input tidak valid (misalnya, jumlah bukan angka): Pesan error muncul dan data tidak ditambahkan.

2. **Update Pesanan**:
   - Pilih pesanan, ubah data, dan simpan: Data di tabel diperbarui sesuai input baru.
   - Tidak ada baris yang dipilih: Pesan error muncul.

3. **Hapus Pesanan**:
   - Pilih pesanan dan hapus: Baris di tabel dihapus dan daftar diperbarui.
   - Tidak ada baris yang dipilih: Pesan error muncul.

4. **Simpan Pesanan**:
   - Data di tabel disimpan ke file `pesanan.txt` dalam format CSV.
   - Tambahkan data baru setelah menyimpan, lalu simpan ulang: File diperbarui tanpa kehilangan data lama.

### Pengujian Batasan
1. **Batas Input**:
   - Jumlah maksimum: Tambahkan data dengan jumlah besar (misalnya, 9999 kg).
   - Input kosong: Semua kolom kosong memunculkan pesan error.

2. **Jumlah Data Besar**:
   - Tambahkan 100–200 pesanan untuk memastikan aplikasi tetap responsif.

### Pengujian File
1. **Format File**:
   - Periksa file `pesanan.txt` apakah sesuai format CSV.
   - Edit file secara manual dan jalankan kembali aplikasi untuk memastikan tidak ada error.

### Pengujian Tampilan
1. **Resolusi Layar**:
   - Jalankan aplikasi pada berbagai resolusi (800x600, 1920x1080) untuk memastikan tata letak tetap konsisten.

## Rekomendasi Pengembangan
- Implementasi pengujian unit menggunakan **JUnit** untuk mengotomatiskan pengujian fitur utama.
- Pisahkan logika menjadi tiga komponen utama (MVC) untuk meningkatkan skalabilitas dan pemeliharaan kode.
- Tambahkan log untuk mencatat aktivitas aplikasi seperti penambahan dan penyimpanan pesanan.