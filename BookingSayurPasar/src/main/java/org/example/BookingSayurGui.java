package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class BookingSayurGUI {
    private static final String FILE_NAME = "pesanan.txt";
    private static DefaultTableModel tableModel;
    private static final List<Pesanan> daftarPesanan = new ArrayList<>();  // List to store orders

    public static void main(String[] args) {
        JFrame frame = new JFrame("Booking Sayur App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // Center the frame
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Input Form
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 20, 5, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel namaLabel = new JLabel("Nama Pembeli:");
        JTextField namaField = new JTextField(15);
        JLabel alamatLabel = new JLabel("Alamat:");
        JTextField alamatField = new JTextField(15);
        JLabel pesananLabel = new JLabel("Pesanan:");
        JTextField pesananField = new JTextField(15);
        JLabel jumlahLabel = new JLabel("Jumlah (kg):");
        JTextField jumlahField = new JTextField(15);

        // Buttons
        JButton addButton = new JButton("Tambah Pesanan");
        addButton.setBackground(new Color(0, 153, 255));
        addButton.setForeground(Color.WHITE);

        JButton deleteButton = new JButton("Hapus Pesanan");
        deleteButton.setBackground(new Color(229, 4, 58));
        deleteButton.setForeground(Color.WHITE);

        JButton updateButton = new JButton("Update Pesanan");
        updateButton.setBackground(new Color(50, 97, 182));
        updateButton.setForeground(Color.WHITE);

        JButton saveButton = new JButton("Simpan Pesanan");
        saveButton.setBackground(new Color(41, 217, 41));
        saveButton.setForeground(Color.WHITE);


        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(namaLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(namaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(alamatLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(alamatField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(pesananLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(pesananField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(jumlahLabel, gbc);
        gbc.gridx = 1;
        inputPanel.add(jumlahField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(addButton, gbc);
        gbc.gridy = 5;
        inputPanel.add(updateButton, gbc);
        gbc.gridy = 6;
        inputPanel.add(deleteButton, gbc);
        gbc.gridy = 7;
        inputPanel.add(saveButton, gbc);

        String[] columns = {"Nama Pembeli", "Alamat", "Pesanan", "Jumlah (kg)"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setPreferredSize(new Dimension(400, 300));

        addButton.addActionListener(_ -> {
            String nama = namaField.getText();
            String alamat = alamatField.getText();
            String pesanan = pesananField.getText();
            int jumlah;

            try {
                jumlah = Integer.parseInt(jumlahField.getText());
                if (nama.isEmpty() || alamat.isEmpty() || pesanan.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Pesanan newPesanan = new Pesanan(nama, alamat, pesanan, jumlah);
                    daftarPesanan.add(newPesanan);
                    tableModel.addRow(new Object[]{nama, alamat, pesanan, jumlah});
                    clearFields(namaField, alamatField, pesananField, jumlahField);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                tableModel.removeRow(selectedRow);
                daftarPesanan.remove(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang akan dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        updateButton.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                Pesanan pesanan = daftarPesanan.get(selectedRow);

                String newNama = JOptionPane.showInputDialog(frame, "Masukkan nama baru:", pesanan.getNamaPembeli());
                String newAlamat = JOptionPane.showInputDialog(frame, "Masukkan alamat baru:", pesanan.getAlamat());
                String newPesanan = JOptionPane.showInputDialog(frame, "Masukkan pesanan baru:", pesanan.getPesanan());
                String newJumlahStr = JOptionPane.showInputDialog(frame, "Masukkan jumlah baru (kg):", pesanan.getJumlah());

                try {
                    int newJumlah = Integer.parseInt(newJumlahStr);

                    if (!newNama.isEmpty() && !newAlamat.isEmpty() && !newPesanan.isEmpty()) {
                        pesanan.setNamaPembeli(newNama);
                        pesanan.setAlamat(newAlamat);
                        pesanan.setPesanan(newPesanan);
                        pesanan.setJumlah(newJumlah);

                        tableModel.setValueAt(newNama, selectedRow, 0);
                        tableModel.setValueAt(newAlamat, selectedRow, 1);
                        tableModel.setValueAt(newPesanan, selectedRow, 2);
                        tableModel.setValueAt(newJumlah, selectedRow, 3);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Data tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang akan diupdate!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        saveButton.addActionListener(_ -> savePesananToFile());

        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private static void savePesananToFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(FILE_NAME))) {
            for (Pesanan pesanan : daftarPesanan) {
                writer.write(STR."\{pesanan.getNamaPembeli()},\{pesanan.getAlamat()},\{pesanan.getPesanan()},\{pesanan.getJumlah()}");
                writer.newLine();
            }
            JOptionPane.showMessageDialog(null, "Pesanan berhasil disimpan!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Gagal menyimpan pesanan!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class Pesanan {
        private String namaPembeli;
        private String alamat;
        private String pesanan;
        private int jumlah;

        public Pesanan(String namaPembeli, String alamat, String pesanan, int jumlah) {
            this.namaPembeli = namaPembeli;
            this.alamat = alamat;
            this.pesanan = pesanan;
            this.jumlah = jumlah;
        }

        public String getNamaPembeli() {
            return namaPembeli;
        }

        public void setNamaPembeli(String namaPembeli) {
            this.namaPembeli = namaPembeli;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getPesanan() {
            return pesanan;
        }

        public void setPesanan(String pesanan) {
            this.pesanan = pesanan;
        }

        public int getJumlah() {
            return jumlah;
        }

        public void setJumlah(int jumlah) {
            this.jumlah = jumlah;
        }
    }
}
