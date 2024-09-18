package Transaksi;

import DBConnection_06.DBConnection_06;
import Jabatan.DBConnect;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TransaksiPenjualan{
    private JPanel penjualan;
    private JPanel FormTransaksiPenjualan;
    private JTextField TxtPenjualan;
    private JTextField TxtBarang;
    private JTextField TxtTotal;
    private JTextField TxtMember;
    private JButton BtnCari;
    private JTable TableMember;
    private JComboBox TxtBayar;
    private JTable TableBeliProduct;
    private JButton BtnTambah;
    private JButton BtnHapus;
    private JTextField TxtKembalian;
    private JTable TableTransaksi;
    private JButton BtnSimpan;
    private JButton BtnCancel;
    private JButton BtnDelete;
    private JButton BtnBayar;
    private JTextField TxtHarga;
    private JRadioButton RbNonMember;
    private JRadioButton RbMember;
    private JTextField TxtNamaBarang;
    private JTextField TxtJumlahBarang;
    private JRadioButton RbSatuan;
    private JRadioButton RbGrosir;
    private JTextField TxtNama;
    private JTextField TxtPoin;
    private JTextField TxtPromo;
    private DefaultTableModel modelmember;
    private DefaultTableModel modelproduct;
    private DefaultTableModel modeltransaksi;
    private DefaultTableModel modelbeliproduct;
    private DefaultTableModel modelreset;


    DBConnection_06 connection_06 = new DBConnection_06();
    JDateChooser datechoos  = new JDateChooser();
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");

    String autoid;
    String total;
    int status;
    String trsid;
    String csrid;
    String tgltransaksi;
    String prdct_id;
    String prdct_nama;
    String prdct_hargajual;

    public void apk() {
        JFrame frame = new JFrame("TRANSAKSI PENJUALAN");
        frame.setContentPane(new TransaksiPenjualan().FormTransaksiPenjualan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public boolean validasinull(){
        if(TxtPenjualan.getText() == null || TxtBarang.getText() == null ){
            return true;
        }else{
            return false;
        }
    }

    public TransaksiPenjualan(){
        modelreset = new DefaultTableModel();

        modelmember = new DefaultTableModel();
        TableMember.setModel(modelmember);


        modeltransaksi = new DefaultTableModel();
        TableTransaksi.setModel(modeltransaksi);

        modelbeliproduct = new DefaultTableModel();
        TableBeliProduct.setModel(modelbeliproduct);

        TxtHarga.setText(formatter.format(calendar.getTime()));
        addColomn();
        loaddata();
        autoid();
        //loadimages();

        tampilProduct();
        TableMember.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = TableMember.getSelectedRow();
                TxtBarang.setText((String) modelmember.getValueAt(i,0));
                //TxtMember.setText((String) modelmember.getValueAt(i,1));
            }
        });


        BtnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productid = "";
                int stok = 0;
                try{
                    DBConnection_06.stat = DBConnection_06.conn.createStatement();
                    String sql = "SELECT prdct_id FROM tbProduct WHERE prdct_nama = '" + TxtBayar.getSelectedItem() + "'";
                    DBConnection_06.result = DBConnection_06.stat.executeQuery(sql);
                    while (DBConnection_06.result.next()) {
                        productid = DBConnection_06.result.getString("prdct_id");
                    }
                    DBConnection_06.stat.close();
                    DBConnection_06.result.close();


                    DBConnection_06.stat = DBConnection_06.conn.createStatement();
                    String sql2 = "SELECT dbo.fcStokProduct(prdct_id) AS stok FROM tbProduct WHERE prdct_id = '" + productid + "'";
                    ResultSet a = DBConnection_06.stat.executeQuery(sql2);
                    while (a.next()) {
                        stok = Integer.parseInt(a.getString("stok"));
                    }
                    DBConnection_06.stat.close();
                    a.close();
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null,"Error2"+ex);
                }

                System.out.println(""+ stok +" "+ Integer.parseInt(TxtKembalian.getText()) + " " +productid);

                if (stok < Integer.parseInt(TxtKembalian.getText())){
                    JOptionPane.showMessageDialog(null,
                            "Stok tidak mencukupi!", "Failed", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {

                    DBConnection_06.stat = DBConnection_06.conn.createStatement();
                    String sql = "SELECT * FROM tbProduct WHERE prdct_nama = '" + TxtBayar.getSelectedItem() + "'";
                    DBConnection_06.result = DBConnection_06.stat.executeQuery(sql);

                    while (DBConnection_06.result.next()) {
                        Object[] obj = new Object[4];
                        obj[0] = prdct_id = (String) DBConnection_06.result.getString("prdct_id");
                        obj[1] = prdct_nama = (String) DBConnection_06.result.getString("prdct_nama");
                        obj[2] = prdct_hargajual = (String) DBConnection_06.result.getString("prdct_hargajual");
                        obj[3] = prdct_hargajual = TxtKembalian.getText();

                        modelbeliproduct.addRow(obj);
                    }


                } catch (Exception e1) {
                    System.out.println("Terjadi error pada saat penambahan table :" + e1);
                }
            }
        });
        BtnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (validasinull()){
                        throw new Exception("Harap Isi Semua Data!");
                    }else
                    {
                        //membuat statement untuk input ke database
                        total = TxtTotal.getText();
                        int stock = 0, stock2 = 0;
                        double harga;
                        Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        int j = TableBeliProduct.getModel().getRowCount(); //knowing how many row on tabelBuku dilayar
                        trsid = TxtPenjualan.getText();
                        //tgltransaksi= formatter.format(datechoos.getDate());
                        tgltransaksi= TxtHarga.getText();
                        csrid = "";
                        try {
                            int i = TableMember.getSelectedRow();
                            //mencari kode supplier dari supplier yang ada di tabel, karena yang masuk ke tabel master adalah kode supplier
                            DBConnection_06 connection = null;
                            DBConnection_06.stat = connection.conn.createStatement();
                            String sql = "SELECT csr_id FROM tbCustomer WHERE csr_nama = '" + modelmember.getValueAt(i,1) + "'";
                            DBConnection_06.result = DBConnection_06.stat.executeQuery(sql);

                            while (DBConnection_06.result.next()) {
                                csrid = (String) DBConnection_06.result.getString("csr_id");
                            }

                            //INSERT ke tabel master
                            //String sql2 = "INSERT INTO tbTransaksiPenjualan VALUES (?, ?, ?, ?, ?)";
                            String sql2 = "EXEC sp_InputTPenjualan @trs_id=?,@trs_tanggal=?,@csr_id=?," +
                                    "@trs_totalbayar=?,@trs_statusbayar=?";
                            DBConnection_06.pstat = DBConnection_06.conn.prepareStatement(sql2);
                            DBConnection_06.pstat.setString(1, trsid);
                            DBConnection_06.pstat.setString(2, tgltransaksi);
                            DBConnection_06.pstat.setString(3, csrid);
                            DBConnection_06.pstat.setString(4, total);
                            DBConnection_06.pstat.setInt(5, status);

                            DBConnection_06.pstat.executeUpdate(); //insert ke tabel
                            //insert ke tabel detail, looping sebanyak row yang ada di layar
                            for(int k = 0; k < j; k++) {
                                //String sql3 = "INSERT INTO tbDetailPenjualan VALUES (?, ?, ?)";
                                String sql3 = "EXEC sp_InputDetailPenjualan @trs_id=?,@prdct_id=?,@trs_qty=?";
                                DBConnection_06.pstat = DBConnection_06.conn.prepareStatement(sql3);
                                DBConnection_06.pstat.setString(1, trsid);
                                DBConnection_06.pstat.setString(2, (String) modelbeliproduct.getValueAt(k, 0)); //idproduct
                                DBConnection_06.pstat.setString(3, (String) modelbeliproduct.getValueAt(k, 3)); //quantity
                                DBConnection_06.pstat.executeUpdate(); //insert tabel detilBeli

                                //mencari nilai stock ditabel buku saat ini dan menabmahkan dengan nilai di inputan
                                String sql4 = "SELECT prdct_qty FROM tbProduct WHERE prdct_id = '" + (String) modelbeliproduct.getValueAt(k, 0) + "'";
                                DBConnection_06.result = DBConnection_06.stat.executeQuery(sql4);
                                while (DBConnection_06.result.next()) {
                                    stock  = DBConnection_06.result.getInt("prdct_qty");
                                    stock2 = stock - Integer.parseInt((String) modelbeliproduct.getValueAt(k, 3));
                                }

                                //update stock di tabel buku
                                System.out.println("Nilai quantity setelah dikurang = " + String.valueOf(stock2));
                                String sql5 = "UPDATE tbProduct SET prdct_qty = ? WHERE prdct_id =?";
                                DBConnection_06.pstat = DBConnection_06.conn.prepareStatement(sql5);
                                DBConnection_06.pstat.setString(1, String.valueOf(stock2));
                                DBConnection_06.pstat.setString(2, (String) modelbeliproduct.getValueAt(k, 0));
                                DBConnection_06.pstat.executeUpdate(); //update tabel product
                            }

                            DBConnection_06.pstat.close(); //close connection
                            //clear();
                            //JOptionPane.showMessageDialog(null, "Insert data Buku Berhasil");
                        } catch (SQLException ex) {
                            System.out.println("Terjadi error saat insert " +ex);
                        }

                        JOptionPane.showMessageDialog(null,"Insert Transaksi berhasil!");
                        clear();

                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,
                            "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        BtnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        TableTransaksi.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = TableTransaksi.getSelectedRow();
                TxtPenjualan.setText((String) modeltransaksi.getValueAt(i,0));
                TxtBarang.setText((String) modeltransaksi.getValueAt(i,2));
                TxtTotal.setText(String.valueOf( modeltransaksi.getValueAt(i,3)));
                TxtHarga.setText(String.valueOf( modeltransaksi.getValueAt(i,1)));
                TxtPenjualan.setEnabled(false);
                TxtTotal.setEnabled(false);

            }
        });

        BtnCari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelmember.getDataVector().removeAllElements(); //menghapus semua data ditamp
                modelmember.fireTableDataChanged(); //memberitahu data telah kosong


                try{
                    DBConnection_06 connection = new DBConnection_06();
                    connection.stat = connection.conn.createStatement();
                    String query = "SELECT TOP (1) * FROM tbCustomer WHERE csr_id = '" + TxtMember.getText()+"'";

                    connection.result = connection.stat.executeQuery(query);
                    //lakukan perbaris data
                    while(connection.result.next()){
                        Object[] obj = new Object[5];
                        obj[0] = connection.result.getString(1);
                        obj[1] = connection.result.getString(2);
                        obj[2] = connection.result.getString(3);
                        obj[3] = connection.result.getString(4);


                        modelmember.addRow(obj);
                    }
                    //jika di tabel tidak ada data yang dicari
                    if (modelmember.getRowCount() == 0){
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
        BtnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FromBayarTransaksiPenjualan p = new FromBayarTransaksiPenjualan();
                p.apk();
            }
        });
        BtnHapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = TableBeliProduct.getSelectedRow();
                if(i>=0){
                    int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if(ok==0){
                        modelbeliproduct.removeRow(i);
                    }
                }
            }
        });
        BtnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = TxtPenjualan.getText();
                try{
                    String query = "DELETE FROM tbTransaksiPenjualan WHERE trs_id=?";
                    DBConnection_06.pstat = DBConnection_06.conn.prepareStatement(query);
                    DBConnection_06.pstat.setString(1,id);

                    DBConnection_06.pstat.executeUpdate();
                    DBConnection_06.pstat.close();

                }
                catch(Exception ex) {
                    System.out.println("Terjadi error pada saat delete data"+ex);
                }
                JOptionPane.showMessageDialog(null,"Delete Data Berhasil");
                clear();
                loaddata();
            }
        });
        TableBeliProduct.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int i = TableBeliProduct.getSelectedRow();
            }
        });
        TxtTotal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //Menghitung total nilai jumlah
                double temp, total = 0.0;
                int i = TableBeliProduct.getSelectedRow();
                if(i == -1){//jika tidak ada baris terseleksi
                    return;
                }
                //mengetahui berapa banyak baris tabelBuku di layar
                int j = TableBeliProduct.getModel().getRowCount();

                //menghitung Total = sum of (harga*jumlah)
                for(int k = 0; k < j; k++){
                    //menghitung nilai harga*jumlah setiap baris
                    temp = (Double.parseDouble((String) modelbeliproduct.getValueAt(k, 2))) * (Double.parseDouble((String) modelbeliproduct.getValueAt(k, 3)));
                    total = total + temp;
                    TxtTotal.setText(String.valueOf(total));
                }
            }
        });
        TxtBarang.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
        TxtMember.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }

    public void addColomn(){
        modelmember.addColumn("ID");
        modelmember.addColumn("Nama Customer");
        modelmember.addColumn("Alamat");
       modelmember.addColumn("No Telepon");


        modeltransaksi.addColumn("Transaksi ID");
        modeltransaksi.addColumn("Tgl Transaksi");
        modeltransaksi.addColumn("Customer ID");
        modeltransaksi.addColumn("Total Bayar");
        modeltransaksi.addColumn("Status");

        modelbeliproduct.addColumn("Product ID");
        modelbeliproduct.addColumn("Nama Product");
        modelbeliproduct.addColumn("Harga");
        modelbeliproduct.addColumn("Jumlah");
    }

    public void clear(){
        datechoos.setEnabled(true);
        TxtTotal.setEnabled(true);
        TxtPenjualan.setText(null);
        TxtBarang.setText(null);
        datechoos.setDate(null);
        TxtTotal.setText(null);
        TxtMember.setText(null);
        TxtHarga.setText(null);
        TxtHarga.setText(formatter.format(calendar.getTime()));
        autoid();
        loaddata();
    }

    public void loaddata() {
        modelmember.getDataVector().removeAllElements();
        modelmember.fireTableDataChanged();
        try {
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM tbCustomer";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[5];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                obj[2] = connection.result.getString(3);
                obj[3] = connection.result.getString(4);

                modelmember.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception ex) {
            System.out.println("Terjadi error saat load data customer" + ex);
        }

        modeltransaksi.getDataVector().removeAllElements();
        modeltransaksi.fireTableDataChanged();
        try{
            DBConnect connection3 = new DBConnect();
            connection3.stat = connection3.conn.createStatement();
            String query3 = "SELECT * FROM tbTransaksiPenjualan";
            connection3.result = connection3.stat.executeQuery(query3);
            while(connection3.result.next()){
                Object[] obj = new Object[6];
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

    public void autoid(){
        try{
            String sql = "SELECT * FROM tbTransaksiPenjualan ORDER BY trs_id desc";
            DBConnection_06.stat = DBConnection_06.conn.createStatement();
            DBConnection_06.result = DBConnection_06.stat.executeQuery(sql);
            if (DBConnection_06.result.next()) {
                trsid = DBConnection_06.result.getString("trs_id").substring(4);
                String AN = "" + (Integer.parseInt(trsid) + 1);
                String nol = "";

                if (AN.length() == 1) {
                    nol = "00";
                } else if (AN.length() == 2) {
                    nol = "0";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                TxtPenjualan.setText("TRS" + nol + AN);
                TxtBarang.requestFocus();
                TxtPenjualan.setEnabled(false);

            }else {
                TxtPenjualan.setText("TRS001");
                TxtBarang.requestFocus();
                TxtPenjualan.setEnabled(false);
            }
            DBConnection_06.stat.close();
            DBConnection_06.result.close();
        }catch (Exception e1){
            System.out.println("Terjadi error pada kode User: " + e1);
        }
    }


    public void tampilProduct() {
        try {
            DBConnection_06.stat = DBConnection_06.conn.createStatement();
            String sql = "SELECT prdct_id, prdct_nama, prdct_qty, prdct_hargajual, prdct_images FROM tbProduct";
            DBConnection_06.result = DBConnection_06.stat.executeQuery(sql);
            while (DBConnection_06.result.next()) {
                TxtBayar.addItem(DBConnection_06.result.getString("prdct_nama"));
            }
            DBConnection_06.stat.close();
            DBConnection_06.result.close();
        } catch (SQLException ex) {
            System.out.println("Terjadi error saat load data product" + ex);
        }
    }

    public void tp() {
        JFrame frame = new JFrame("Transaksi Penjualan");
        frame.setContentPane(new TransaksiPenjualan().penjualan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}

