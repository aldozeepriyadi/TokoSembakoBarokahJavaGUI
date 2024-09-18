package Transaksi;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FromBayarTransaksiPenjualan {
        private JPanel FromBayarTransaksiPenjualan;
        private JTable TableTransaksi;
        private JTextField TxtTransaksiID;
        private JButton BtnSearch;
        private JTextField TxtMemberID;
        private JTextField TxtUangBayar;
        private JTextField TxtKembalian;
        private JTextField TxtTotalBayar;
        private JButton BtnBayar;
        private JButton BtnHapus;
        private JButton BtnCancel;
        private JTextField TxtSearchTransaksi;

        private DefaultTableModel modeltransaksi;

        DBConnection_06 connection = new DBConnection_06();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        String trsid;
        String csrid;
        int statustrs;
        String uangbayar;


        public FromBayarTransaksiPenjualan() {
            modeltransaksi = new DefaultTableModel();
            TableTransaksi.setModel(modeltransaksi);

            addColomn();
            clear();


            BtnSearch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    modeltransaksi.getDataVector().removeAllElements(); //menghapus semua data ditamp
                    modeltransaksi.fireTableDataChanged(); //memberitahu data telah kosong


                    try{
                        DBConnection_06 connection = new DBConnection_06();
                        connection.stat = connection.conn.createStatement();
                        String query = "SELECT TOP (1) * FROM tbTransaksiPenjualan WHERE trs_id = '" + TxtSearchTransaksi.getText()+"' AND trs_statusbayar=0";

                        connection.result = connection.stat.executeQuery(query);
                        //lakukan perbaris data
                        while(connection.result.next()){
                            Object[] obj = new Object[5];
                            obj[0] = connection.result.getString(1);
                            obj[1] = connection.result.getString(2);
                            obj[2] = connection.result.getString(3);
                            obj[3] = connection.result.getInt(4);
                            if(connection.result.getInt(5)==0){
                                obj[4] = "Unpaid";
                            }else{
                                obj[4] = "Paid";
                            }

                            modeltransaksi.addRow(obj);
                        }
                        //jika di tabel tidak ada data yang dicari
                        if (modeltransaksi.getRowCount() == 0){
                            JOptionPane.showMessageDialog(null,
                                    "Data tidak ditemukan!", "Failed", JOptionPane.ERROR_MESSAGE);
                            loaddata();
                        }
                        connection.stat.close();
                        connection.result.close();
                    } catch (Exception ex){
                        System.out.println("Terjadi error saat cari data" + ex);
                    }
                }
            });

            TableTransaksi.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    int i = TableTransaksi.getSelectedRow();
                    TxtTransaksiID.setText((String) modeltransaksi.getValueAt(i,0));
                    TxtMemberID.setText((String) modeltransaksi.getValueAt(i,2));
                    TxtTotalBayar.setText(String.valueOf( modeltransaksi.getValueAt(i,3)));
                    TxtTransaksiID.setEnabled(false);
                    TxtMemberID.setEnabled(false);
                    TxtTotalBayar.setEnabled(false);
                }
            });
            TxtUangBayar.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    super.keyTyped(e);
                    if(TxtUangBayar.getText().length() >= 13){
                        e.consume();
                    }
                    if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyChar() == '\b') {
                        TxtUangBayar.setEditable(true);
                    }else {
                        TxtUangBayar.setEditable(false);
                    }
                }
            });
            BtnBayar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {


                    try {
                        if (validasinull()) {
                            throw new Exception("Harap Isi Semua Data!");
                        }else if (validasibayar()){
                            JOptionPane.showMessageDialog(null,
                                    "Uang yang anda masukkan kurang!", "Failed", JOptionPane.ERROR_MESSAGE);
                        }
                        else{
                            try {
                                DBConnection_06 connection = new DBConnection_06();
                                connection.stat = connection.conn.createStatement();
                                String query = "SELECT csr_id FROM tbTransaksiPenjualan WHERE trs_id = '" + TxtTransaksiID.getText() + "'";

                                connection.result = connection.stat.executeQuery(query);

                                while(connection.result.next()) {
                                    trsid = connection.result.getString(1);
                                    statustrs = 1;
                                }

                                connection.stat.close();
                                connection.result.close();
                            }catch (Exception ex){
                                System.out.println("Terjadi error saat cari transaksi"+ex);
                            }

                            try{

                                String query3 = "UPDATE tbTransaksiPenjualan SET trs_statusbayar=? WHERE trs_id=?";
                                connection.pstat = connection.conn.prepareStatement(query3);
                                connection.pstat.setInt(1,statustrs);
                                connection.pstat.setString(2, TxtTransaksiID.getText());
                                connection.pstat.executeUpdate();
                                connection.pstat.close();
                            }
                            catch(Exception ex) {
                                System.out.println("Terjadi error pada saat update data"+ex);
                            }
                            JOptionPane.showMessageDialog(null,"Pembayaran Berhasil");
                            clear();
                        }
                    }catch (Exception ex){
                        JOptionPane.showMessageDialog(null,
                                "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            BtnHapus.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try{
                        String query = "DELETE FROM tbTransaksiPenjualan WHERE trs_id=?";
                        connection.pstat = connection.conn.prepareStatement(query);
                        connection.pstat.setString(1,TxtTransaksiID.getText());

                        connection.pstat.executeUpdate();
                        connection.pstat.close();
                    }
                    catch(Exception ex) {
                        System.out.println("Terjadi error pada saat delete data"+ex);
                    }
                    JOptionPane.showMessageDialog(null,"Delete Data Berhasil");
                    clear();
                }
            });
            TxtKembalian.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    super.mouseClicked(e);
                    //Menghitung total nilai jumlah
                    double temp, total = 0.0;
                    int i = TableTransaksi.getSelectedRow();
                    if(i == -1){//jika tidak ada baris terseleksi
                        return;
                    }
                    //mengetahui berapa banyak baris tabelBuku di layar
                    int j = TableTransaksi.getModel().getRowCount();

                    //menghitung Total = sum of (harga*jumlah)

                        //menghitung nilai harga*jumlah setiap baris
                        temp = (Double.parseDouble((String) TxtUangBayar.getText())) - (Double.parseDouble((String) TxtTotalBayar.getText()));
                        //total = total + temp;
                        TxtKembalian.setText(String.valueOf(temp));

                }
            });
            BtnCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    clear();
                }
            });
        }

        public void apk(){
            JFrame frame = new JFrame("PEMBAYARAN");
            frame.setContentPane(new FromBayarTransaksiPenjualan().FromBayarTransaksiPenjualan);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setSize(800,600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }

        public static void main(String[] args) {
            JFrame frame = new JFrame("PEMBAYARAN");
            frame.setContentPane(new FromBayarTransaksiPenjualan().FromBayarTransaksiPenjualan);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setSize(650,500);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }



        public void addColomn(){
            modeltransaksi.addColumn("Transaksi ID");
            modeltransaksi.addColumn("Tgl Transaksi");
            modeltransaksi.addColumn("Customer ID");
            modeltransaksi.addColumn("Total Bayar");
            modeltransaksi.addColumn("Status");
        }

        public void clear(){
            TxtTransaksiID.setText(null);
            TxtMemberID.setText(null);
            TxtTotalBayar.setText(null);
            TxtUangBayar.setText(null);
            TxtKembalian.setText(null);
            loaddata();
            //kembalian();
        }

        public void loaddata(){
            modeltransaksi.getDataVector().removeAllElements();
            modeltransaksi.fireTableDataChanged();
            try{
                DBConnection_06 connection3 = new DBConnection_06();
                connection3.stat = connection3.conn.createStatement();
                String query3 = "SELECT * FROM tbTransaksiPenjualan";
                connection3.result = connection.stat.executeQuery(query3);
                while(connection3.result.next()){
                    Object[] obj = new Object[5];
                    obj[0] = connection3.result.getString(1);
                    obj[1] = connection3.result.getString(2);
                    obj[2] = connection3.result.getString(3);
                    obj[3] = connection3.result.getInt(4);
                    if(connection3.result.getInt(5)==0){
                        obj[4] = "Unpaid";
                    }else{
                        obj[4] = "Paid";
                    }
                    modeltransaksi.addRow(obj);
                }
                connection3.stat.close();
                connection3.result.close();
            }catch(Exception ex){
                System.out.println("Terjadi error saat load data transaksi "+ex);
            }
        }

        public boolean validasinull(){
            trsid = TxtTransaksiID.getText();
            uangbayar = TxtUangBayar.getText();
            csrid = TxtMemberID.getText();

            if(trsid.isEmpty() || uangbayar.isEmpty() || TxtMemberID.getText() == null){
                return true;
            }else{
                return false;
            }
        }

        public boolean validasibayar(){
            int totalbayar = Integer.parseInt(TxtTotalBayar.getText());
            int uangbayar = Integer.parseInt(TxtUangBayar.getText());
            if (uangbayar < totalbayar){
                return true;
            }else{
                return false;
            }
        }

    }
