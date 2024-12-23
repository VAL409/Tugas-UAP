package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingSayurGUITest {

    private List<BookingSayurGUI.Pesanan> daftarPesanan;

    @BeforeEach
    void setUp() {
        daftarPesanan = new ArrayList<>();
    }

    @Test
    void testTambahPesanan() {
        BookingSayurGUI.Pesanan pesanan = new BookingSayurGUI.Pesanan("John Doe", "Jl. Mawar", "Bayam", 5);
        daftarPesanan.add(pesanan);

        assertEquals(1, daftarPesanan.size());
        assertEquals("John Doe", daftarPesanan.get(0).getNamaPembeli());
        assertEquals("Bayam", daftarPesanan.get(0).getPesanan());
    }

    @Test
    void testHapusPesanan() {
        BookingSayurGUI.Pesanan pesanan1 = new BookingSayurGUI.Pesanan("John Doe", "Jl. Mawar", "Bayam", 5);
        BookingSayurGUI.Pesanan pesanan2 = new BookingSayurGUI.Pesanan("Jane Doe", "Jl. Melati", "Kangkung", 3);
        daftarPesanan.add(pesanan1);
        daftarPesanan.add(pesanan2);

        daftarPesanan.remove(pesanan1);

        assertEquals(1, daftarPesanan.size());
        assertEquals("Jane Doe", daftarPesanan.get(0).getNamaPembeli());
    }

    @Test
    void testUpdatePesanan() {
        BookingSayurGUI.Pesanan pesanan = new BookingSayurGUI.Pesanan("John Doe", "Jl. Mawar", "Bayam", 5);
        daftarPesanan.add(pesanan);

        pesanan.setNamaPembeli("Jane Doe");
        pesanan.setAlamat("Jl. Melati");
        pesanan.setPesanan("Kangkung");
        pesanan.setJumlah(10);

        assertEquals("Jane Doe", daftarPesanan.get(0).getNamaPembeli());
        assertEquals("Kangkung", daftarPesanan.get(0).getPesanan());
        assertEquals(10, daftarPesanan.get(0).getJumlah());
    }

    @Test
    void testSimpanPesananToFile() throws IOException {
        String fileName = "test_pesanan.txt";
        BookingSayurGUI.Pesanan pesanan1 = new BookingSayurGUI.Pesanan("John Doe", "Jl. Mawar", "Bayam", 5);
        BookingSayurGUI.Pesanan pesanan2 = new BookingSayurGUI.Pesanan("Jane Doe", "Jl. Melati", "Kangkung", 3);
        daftarPesanan.add(pesanan1);
        daftarPesanan.add(pesanan2);

        try (var writer = Files.newBufferedWriter(Paths.get(fileName))) {
            for (BookingSayurGUI.Pesanan pesanan : daftarPesanan) {
                writer.write(pesanan.getNamaPembeli() + "," + pesanan.getAlamat() + "," + pesanan.getPesanan() + "," + pesanan.getJumlah());
                writer.newLine();
            }
        }

        List<String> lines;
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            lines = reader.lines().toList();
        }

        assertEquals(2, lines.size());
        assertEquals("John Doe,Jl. Mawar,Bayam,5", lines.get(0));
        assertEquals("Jane Doe,Jl. Melati,Kangkung,3", lines.get(1));

        Files.deleteIfExists(Paths.get(fileName)); // Clean up test file
    }
}
