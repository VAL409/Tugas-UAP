package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

class BookingSayurPasar {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Booking Sayur Pasar");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null); // Posisikan di tengah layar
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.LIGHT_GRAY);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblNama = new JLabel("Nama:");
        JTextField tfNama = new JTextField(15);
        JLabel lblAlamat = new JLabel("Alamat:");
        JTextField tfAlamat = new JTextField(15);
        JLabel lblPesanan = new JLabel("Pesanan:");
        JTextField tfPesanan = new JTextField(15);
        JLabel lblJumlah = new JLabel("Jumlah (kg):");
        JTextField tfJumlah = new JTextField(15);

        JButton btnBooking = new JButton("Tambah Pesanan");
        btnBooking.setBackground(new Color(0, 153, 255));
        btnBooking.setForeground(Color.WHITE);

        JButton btnUpdate = new JButton("Update Pesanan");
        btnUpdate.setBackground(new Color(50, 97, 182));
        btnUpdate.setForeground(Color.WHITE);

        JButton btnDelete = new JButton("Hapus Pesanan");
        btnDelete.setBackground(new Color(229, 4, 58));
        btnDelete.setForeground(Color.WHITE);

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(lblNama, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfNama, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(lblAlamat, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfAlamat, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(lblPesanan, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfPesanan, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(lblJumlah, gbc);
        gbc.gridx = 1;
        inputPanel.add(tfJumlah, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        inputPanel.add(btnBooking, gbc);
        gbc.gridy = 5;
        inputPanel.add(btnUpdate, gbc);
        gbc.gridy = 6;
        inputPanel.add(btnDelete, gbc);

        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Nama", "Alamat", "Pesanan", "Jumlah (kg)"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        btnBooking.addActionListener(_ -> {
            try {
                if (tfNama.getText().isEmpty() || tfAlamat.getText().isEmpty() || tfPesanan.getText().isEmpty() || tfJumlah.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Semua kolom harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                } else {
                    String nama = tfNama.getText();
                    String alamat = tfAlamat.getText();
                    String pesanan = tfPesanan.getText();
                    double jumlah = Double.parseDouble(tfJumlah.getText());

                    tableModel.addRow(new Object[]{nama, alamat, pesanan, jumlah});
                    savePesananToFile(nama, alamat, pesanan, jumlah);

                    tfNama.setText("");
                    tfAlamat.setText("");
                    tfPesanan.setText("");
                    tfJumlah.setText("");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnUpdate.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang ingin diupdate.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String nama = tfNama.getText();
                String alamat = tfAlamat.getText();
                String pesanan = tfPesanan.getText();
                double jumlah = Double.parseDouble(tfJumlah.getText());

                tableModel.setValueAt(nama, selectedRow, 0);
                tableModel.setValueAt(alamat, selectedRow, 1);
                tableModel.setValueAt(pesanan, selectedRow, 2);
                tableModel.setValueAt(jumlah, selectedRow, 3);

                updatePesananInFile(selectedRow, nama, alamat, pesanan, jumlah);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnDelete.addActionListener(_ -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tableModel.removeRow(selectedRow);
            deletePesananFromFile(selectedRow);
        });

        mainPanel.add(inputPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);
        frame.setVisible(true);

        loadPesananFromFile(tableModel);
    }

    private static void savePesananToFile(String nama, String alamat, String pesanan, double jumlah) {
        try {
            Path filePath = Paths.get("pesanan.txt");

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }

            String pesananData = STR."\{nama},\{alamat},\{pesanan},\{jumlah}\n";
            Files.write(filePath, pesananData.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, STR."Terjadi kesalahan saat menyimpan pesanan: \{e.getMessage()}", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void loadPesananFromFile(DefaultTableModel tableModel) {
        try {
            Path filePath = Paths.get("pesanan.txt");
            if (Files.exists(filePath)) {
                List<String> lines = Files.readAllLines(filePath);
                for (String line : lines) {
                    String[] data = line.split(",");
                    tableModel.addRow(new Object[]{data[0], data[1], data[2], Double.parseDouble(data[3])});
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, STR."Terjadi kesalahan saat memuat pesanan: \{e.getMessage()}", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void updatePesananInFile(int rowIndex, String nama, String alamat, String pesanan, double jumlah) {
        try {
            Path filePath = Paths.get("pesanan.txt");
            List<String> lines = Files.readAllLines(filePath);
            lines.set(rowIndex, STR."\{nama},\{alamat},\{pesanan},\{jumlah}");
            Files.write(filePath, lines);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, STR."Terjadi kesalahan saat memperbarui pesanan: \{e.getMessage()}", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void deletePesananFromFile(int rowIndex) {
        try {
            Path filePath = Paths.get("pesanan.txt");
            List<String> lines = Files.readAllLines(filePath);
            lines.remove(rowIndex);
            Files.write(filePath, lines);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, STR."Terjadi kesalahan saat menghapus pesanan: \{e.getMessage()}", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
