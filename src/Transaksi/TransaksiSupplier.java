package Transaksi;

import DBConnection_06.DBConnection_06;
import Form.FromSupplier;
import Jabatan.DBConnect;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;
import java.text.Format;


public class TransaksiSupplier extends JFrame {
    private JTextField txtTotal;
    private JScrollPane view;
    private JTable tblDetailTransSupp;
    private JPanel jdCaldTransSup;
    private JPanel TransaksiSupplier;
    private JButton btnCari1;
    private JTextField txtStokBrg;
    private JTextField txtNamaBrg;
    private JTextField txtIDBrg;
    private JButton btnTambah;
    private JButton btnCari2;
    private JTextField txtIDSupplier;
    private JTextField txtNamaSupplier;
    private JTextField txtIDTrans;
    private JTextField txtIDPengguna;
    private JButton btnClearAll;
    private JTextField txtAlamaSupplier;
    private JScrollPane viewBarang;
    private JTable tblBarang;
    private JButton btnDelete;
    private JButton btnSimpan;
    private JButton BtnRefresh;
    private JTextField txtJumlah;
    private JTextField txtStok;
    private JTextField txtTotalPembayaran;
    private JTextField txtKembalian;
    private DefaultTableModel model;
    private DefaultTableModel modelBarang;

    JDateChooser chooser = new JDateChooser();
    DBConnection_06 connection = new DBConnection_06();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    String id_detail;
    String id_barang = txtIDBrg.getText();
    String nama_barang = txtNamaBrg.getText();



    public TransaksiSupplier(){
     model = new DefaultTableModel();
     modelBarang = new DefaultTableModel();
     jdCaldTransSup.add(chooser);
     tblDetailTransSupp.setModel(model);
     modelBarang = new DefaultTableModel();
     tblBarang.setModel(modelBarang);
     txtNamaBrg.setEnabled(false);
     txtNamaSupplier.setEnabled(false);
     txtAlamaSupplier.setEnabled(false);
     txtStok.setEnabled(false);
     txtJumlah.setEnabled(false);
     txtTotal.setEnabled(false);
     txtTotalPembayaran.setText("0");
     addColomn();
     autoid();
     autoid2();
     loaddata();

        btnCari1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search1();

            }
        });
        btnCari2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search2();
            }
        });
        btnClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });


        btnTambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                try
                {
                    String id_barang = txtIDBrg.getText();
                    String nama_barang =txtNamaBrg.getText();
                    int stok = Integer.parseInt(txtStok.getText());
                    int jumlah = Integer.parseInt(txtJumlah.getText());
                    int total =  stok * jumlah;
                    int jumlah2 = 0;


                    modelBarang = (DefaultTableModel) tblBarang.getModel();
                    modelBarang.addRow(new Object[]{id_barang, nama_barang, stok, total});




                    /*connection.stat = connection.conn.createStatement();
                    String sql = "SELECT id_barang,nama,stok,harga_jual FROM Barang WHERE nama = '" + txtNamaBrg.getText() + "'";
                    connection.result = connection.stat.executeQuery(sql);*/
                   /* for(int i =0 ; i < modelBarang.getRowCount();i++) {
                        Object[] obj1 = new Object[5];
                        obj1[0] =  (String) modelBarang.setValueAt(txtIDBrg.);
                        obj1[1] = nama_barang =(String) modelBarang.getValueAt(i,2);
                        obj1[2] = stok = (int) modelBarang.getValueAt(i,3);
                        obj1[3] = total =(int) modelBarang.getValueAt(i,4);

                    }*/
                 /*   while (connection.result.next())
                    {
                        Object[] obj = new Object[5];

                        obj[0] = id_barang = (String) connection.result.getString("id_barang");
                        obj[1] = nama_barang = (String) connection.result.getString("nama");
                        obj[2]= stok = (int) connection.result.getInt("stok");
                        obj[3] = jumlah2 =(int) connection.result.getInt("harga_jual");

                        modelBarang.addRow(obj);
                    }*/
                    int sum =0;
                    for(int i =0 ; i < tblBarang.getRowCount();i++){
                        sum = sum + Integer.parseInt(tblBarang.getValueAt(i,3).toString());

                    }
                    txtTotal.setText(Integer.toString(sum));


                } catch (Exception e1)
                {
                    System.out.println("Terjadi error pada saat penambahan table :" + e1);
                }
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int i = tblBarang.getSelectedRow();
                if(i>=0){
                    int ok = JOptionPane.showConfirmDialog(null, "Yakin Mau Hapus?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                    if(ok==0){
                        modelBarang.removeRow(i);
                        int sum =0;
                        for(int k =0 ; k < tblBarang.getRowCount();i++){
                            sum = sum + Integer.parseInt(tblBarang.getValueAt(i,3).toString());

                        }
                        txtTotal.setText(Integer.toString(sum));
                    }
                }

            }
        });
        btnSimpan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               InputTransSupplier();
               InputDetailTrans();
               TambahStokBRG();
               clear();

            }
        });
        txtTotal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                double temp, total = 0.0;
                int i = tblBarang.getSelectedRow();
                if(i == -1){//jika tidak ada baris terseleksi
                    return;
                }
                //mengetahui berapa banyak baris tabelBuku di layar
                int j = tblBarang.getModel().getRowCount();

                //menghitung Total = sum of (harga*jumlah)
                for(int k = 0; k < j; k++)
                {
                    //menghitung nilai harga*jumlah setiap baris
                    temp = (Double.parseDouble((String) modelBarang.getValueAt(k, 3))) * (Double.parseDouble((String) modelBarang.getValueAt(k, 4)));
                    total = total++;
                    txtTotal.setText(String.valueOf(total));
                }
            }
        });
        BtnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loaddata();
            }
        });
        txtKembalian.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int total = Integer.parseInt(txtTotalPembayaran.getText()) - Integer.parseInt(txtTotal.getText());
                if(total <= 0){
                    JOptionPane.showMessageDialog(null,"Uang Anda Tidak Cukup!","Informasi",JOptionPane.INFORMATION_MESSAGE);
                }else{
                    txtKembalian.setText(String.valueOf(total));
                }

            }
        });
    }


    public void search2(){
        model.getDataVector().removeAllElements(); //menghapus semua data ditamp
        model.fireTableDataChanged(); //memberitahu data telah kosong

        try{


            connection.stat = connection.conn.createStatement();
            String query = "SELECT TOP (1) id_supplier,nama,alamat FROM Supplier WHERE id_supplier ='"+txtIDSupplier.getText()+"'";
            connection.result = connection.stat.executeQuery(query);
            //lakukan perbaris data
            while(connection.result.next()){
                txtNamaSupplier.setText(connection.result.getString("nama"));
                txtAlamaSupplier.setText(connection.result.getString("alamat"));


            }

            txtNamaSupplier.setEnabled(true);
            txtNamaSupplier.setVisible(true);
            txtAlamaSupplier.setEnabled(true);
            txtAlamaSupplier.setVisible(true);

            //jika di tabel tidak ada data yang dicari


            connection.stat.close();
            connection.result.close();
        } catch (Exception ex){
            System.out.println("Terjadi error saat cari data" + ex);
        }
    }
    public void InputDetailTrans(){
        int total2;
        try{
        String sql3 = "INSERT INTO DetailTransSupplier VALUES (?, ?, ?, ?,?)";
        connection.pstat = connection.conn.prepareStatement(sql3);
        for(int i=0 ; i < tblBarang.getRowCount(); i++) {
            total2 = (int) tblBarang.getValueAt(i,3);
            connection.pstat.setString(1, id_detail);
            connection.pstat.setString(2, txtIDTrans.getText());
            connection.pstat.setString(3, txtIDBrg.getText());
            connection.pstat.setString(4, txtNamaBrg.getText());
            connection.pstat.setString(5, txtStok.getText());

            connection.pstat.executeUpdate(); //insert tabel detilBeli
        }
            connection.pstat.close();
            clear();

        }catch (Exception ex){
            System.out.println("Terjadi error saat insert Detail:" +ex);
        }
    }
    public void InputTransSupplier(){
        int harga;
        Format formater = new SimpleDateFormat("yyyy-MM-dd");
        int j = tblBarang.getModel().getRowCount();
        String spid ="";
        String Trs_idSP = txtIDTrans.getText();


        String suppliernama =txtNamaSupplier.getText();
        String alamat = txtAlamaSupplier.getText();
        jdCaldTransSup.add(chooser);

        try{
        connection.stat = connection.conn.createStatement();
        String sql2 = "INSERT INTO TransaksiSupplier VALUES (?, ?, ?, ?, ?)";
        connection.pstat = connection.conn.prepareStatement(sql2);


        connection.pstat.setString(1, Trs_idSP);
        connection.pstat.setString(2, txtIDPengguna.getText());
        connection.pstat.setString(3,txtIDSupplier.getText());
        connection.pstat.setInt(4, Integer.parseInt(txtTotal.getText()));
        connection.pstat.setString(5, formatter.format(chooser.getDate()));
        connection.pstat.executeUpdate();
        connection.pstat.close();
        JOptionPane.showMessageDialog(null, "Data Transaksi Telah diinput");
        }catch (Exception ex){
            System.out.println("Terjadi error saat insert Transaksi:" +ex);
        }
    }
    public void search1(){



        try{

            connection.stat = connection.conn.createStatement();
            String query = "SELECT TOP(1) id_barang, nama,stok,harga_jual FROM Barang WHERE id_barang ='"+txtIDBrg.getText()+"'";
            connection.result = connection.stat.executeQuery(query);
            //lakukan perbaris data

            while(connection.result.next()){
               txtNamaBrg.setText(connection.result.getString("nama"));
               txtStok.setText(connection.result.getString("stok"));
               txtJumlah.setText(connection.result.getString("harga_jual"));



            }


            txtNamaBrg.setEnabled(true);
            txtNamaBrg.setVisible(true);
            //jika di tabel tidak ada data yang dicari


            connection.stat.close();
            connection.result.close();
        } catch (Exception ex){
                JOptionPane.showMessageDialog(null, "Data " +txtIDBrg.getText()+ "tidak ditemukan");

        }
    }

    public void addColomn(){

        modelBarang.addColumn("ID Barang");
        modelBarang.addColumn("Nama Barang");
        modelBarang.addColumn("Stok");
        modelBarang.addColumn("Jumlah");

        model.addColumn("Transaksi Supplier ID");
        model.addColumn("ID Pengguna");
        model.addColumn("ID Supplier");
        model.addColumn("Total");
        model.addColumn("Tanggal Transaksi");



    }
    public void loaddata(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try{
            DBConnect connection = new DBConnect();
            connection.stat = connection.conn.createStatement();
            String query = "SELECT * FROM TransaksiSupplier";
            connection.result = connection.stat.executeQuery(query);
            while(connection.result.next()){
                Object[] obj = new Object[6];
                obj[0] = connection.result.getString(1);
                obj[1] = connection.result.getString(2);
                obj[2] = connection.result.getString(3);
                obj[3] = connection.result.getString(4);
                obj[4] = connection.result.getString(5);




                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Terjadi error saat load data transaksi"+ex);
        }


    }
    public void clear(){

        txtIDSupplier.setText(null);
        txtNamaSupplier.setText(null);
        txtIDBrg.setText(null);
        txtNamaBrg.setText(null);
        txtIDTrans.setText(null);
        txtIDPengguna.setText(null);
        txtNamaBrg.setText(null);
        txtJumlah.setText(null);
        txtAlamaSupplier.setText(null);
        autoid();
        autoid2();
        loaddata();

    }
    public void autoid() {
        try {
            String sql = "SELECT * FROM DetailTransSupplier ORDER BY id_detail desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                id_detail= connection.result.getString("id_detail").substring(4);
                String AN = "" + (Integer.parseInt(id_detail) + 1);
                String nol = "";
                if (AN.length() == 1) {
                    nol = "00";
                } else if (AN.length() == 2) {
                    nol = "0";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                id_detail= "DTL" + nol + AN;


            } else {
                id_detail  ="DTL001";

            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception e1) {
            System.out.println("Terjadi error pada Auto ID: " + e1);
        }
    }
    public void autoid2() {
        try {
            String sql = "SELECT * FROM TransaksiSupplier ORDER BY trs_idSP desc";
            connection.stat = connection.conn.createStatement();
            connection.result = connection.stat.executeQuery(sql);
            if (connection.result.next()) {
                String id_trans= connection.result.getString("trs_idSP").substring(4);
                String AN = "" + (Integer.parseInt(id_trans) + 1);
                String nol = "";
                if (AN.length() == 1) {
                    nol = "00";
                } else if (AN.length() == 2) {
                    nol = "0";
                } else if (AN.length() == 3) {
                    nol = "";
                }
                txtIDTrans.setText("TSP" + nol + AN);


            } else {
                txtIDTrans.setText("TSP001");

            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception e1) {
            System.out.println("Terjadi error pada Auto ID: " + e1);
        }
    }
    public void TambahStokBRG(){
        try{
            String query = "UPDATE Barang SET stok =? where id_barang = ? ";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setInt(1, Integer.parseInt(txtStok.getText()));
            connection.pstat.setString(2, txtIDBrg.getText());
            connection.pstat.executeUpdate(); // insert ke database
            connection.pstat.close(); // menutup koneksi db


        }catch(Exception ex){
            System.out.println("Terjadi error pada saat update data"+ex);
        }
    }


    public void ts() {
        JFrame frame = new JFrame("Transaksi Dupplier");
        frame.setContentPane(new TransaksiSupplier().TransaksiSupplier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}