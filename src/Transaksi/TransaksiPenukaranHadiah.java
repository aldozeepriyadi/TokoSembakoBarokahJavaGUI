package Transaksi;

import DBConnection_06.DBConnection_06;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;

public class TransaksiPenukaranHadiah {
    private JTable tblkeranjang;
    private JTextField txtidpengguna;
    private JButton btncarimember;
    private JTextField txtidpenukaran;
    private JTextField txtidmember;
    private JTextField txtnama;
    private JTextField txtpoinmember;
    private JTextField txtidhadiah;
    private JTextField txthadiah;
    private JTextField txtpoin;
    private JTextField txtTotal;
    private JTextField txtsisapoin;
    private JButton btncarihadiah;
    private JButton SImpanTransaksiButton;
    private JButton bersihkanButton;
    private JButton btntambahkeranjang;
    private JPanel jpCald;
    private JPanel penukaranhadiah;
    private JScrollPane view;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06();
    String idpenukaran;
    String idpengguna;
    String idmember;
    String namamember;
    String poinmember;
    String idhadiah;

    public static void main(String[] args) {
        JFrame frame = new JFrame("TransaksiPenukaranHadiah");
        frame.setContentPane(new TransaksiPenukaranHadiah().penukaranhadiah);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    String hadiah;
    String poinhadiah;
    String jumlah;
    String total;
    String sisapoin;
    int poin1, total1;
    JDateChooser datechos = new JDateChooser();

    public void addcolumn(){
        //model.addColumn("ID Penukaran");
        model.addColumn("ID Hadiah");
        model.addColumn("Nama Hadiah");
        model.addColumn("Poin");
        model.addColumn("Jumlah");
    }

    public TransaksiPenukaranHadiah() {

        model = new DefaultTableModel();
        autoidpenukaran();
        //autoidpengguna();
        tblkeranjang.setModel(model);
        addcolumn();
        jpCald.add(datechos);

        btntambahkeranjang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[]{"", "", "", "",""});
            }
        });
        bersihkanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            txtidhadiah.getText();

            }
        });
        SImpanTransaksiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //membuat statement untuk input ke database
                String idpenukaran, idpengguna, idmember, nama, totalpoin,tanggal;
                int stock = 0, stock2 = 0;
                int poinhadiah;
                int poinmember;
                Format formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                int j = tblkeranjang.getModel().getRowCount(); //mengecek total tabelBuku yang tampil dilayar

                idpenukaran = txtidpenukaran.getText();
                idpengguna = txtidpengguna.getText();
                idmember = txtidmember.getText();
                poinmember = Integer.parseInt(txtpoinmember.getText());
                tanggal = formatter.format(datechos.getDate());
                totalpoin = txtTotal.getText();

                try {

                    //INSERT ke tabel master
                    String sql2 = "INSERT INTO PenukaranHadiah VALUES (?,?, ?, ?, ?)";
                    connection.pstat = connection.conn.prepareStatement(sql2);
                    connection.pstat.setString(1, idpenukaran);
                    connection.pstat.setString(2, tanggal);
                    connection.pstat.setString(3, idpengguna);
                    connection.pstat.setString(4, idmember);
                    connection.pstat.setString(5, totalpoin);

                    connection.pstat.executeUpdate(); //insert ke tabel master

                    //insert ke tabel detail, looping sebanyak row yang ada di layar
                    for(int k = 0; k < j; k++) {
                        poinhadiah = (Integer.parseInt((String) model.getValueAt(k, 2))) *
                                (Integer.parseInt((String) model.getValueAt(k, 3)));
                        String sql3 = "INSERT INTO DetailPenukaranHadiah VALUES (?, ?, ?, ?)";
                        connection.pstat = connection.conn.prepareStatement(sql3);
                        connection.pstat.setString(1, idpenukaran);
                        connection.pstat.setString(2, (String) model.getValueAt(k, 0)); //kodebuku
                        connection.pstat.setString(3, (String) model.getValueAt(k, 3)); //jumlah beli
                        connection.pstat.setString(4, (String.valueOf(poinhadiah))); //jumlah * harga
                        connection.pstat.executeUpdate(); //insert tabel detilBeli

                        //mencari nilai stock ditabel buku saat ini dan menabmahkan dengan nilai di inputan
                       /* String sql4 = "SELECT jumlah FROM tbProduk WHERE id_produk = '" + (String) model.getValueAt(k, 0) + "'";
                        connection.result = connection.stat.executeQuery(sql4);
                        while (connection.result.next()) {
                            stock = connection.result.getInt("jumlah");
                            stock2 = stock + Integer.parseInt((String) model.getValueAt(k, 3));
                        }

                        //update stack di tabel buku
                        System.out.println("Nilai stock setelah ditambah : " + String.valueOf(stock2));
                        String sql5 = "UPDATE tbProduk SET jumlah =? WHERE id_produk =?";
                        connection.pstat = connection.conn.prepareStatement(sql5);
                        connection.pstat.setString(1, String.valueOf(stock2));
                        connection.pstat.setString(2, (String) model.getValueAt(k, 0));
                        connection.pstat.executeUpdate(); //update tabel buku*/
                    }

                    connection.pstat.close(); //close connection

                    JOptionPane.showMessageDialog(null, "Insert data pembelian berhasil");

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Terjadi error pada saat insert " +ex);
                }
            }
        });
        tblkeranjang.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                String id;
                String namaHadiah;
                String poin;
                String jumlah;
                //idpenukaran = txtidpenukaran.getText();
                int i = tblkeranjang.getSelectedRow();
                if (i == -1) { //jika tidak ada baris terseleksi
                    return;
                }
                // ambil nilai kode buku yang diketikkan method getValue()
                // kemudian tempatkan di dtabel
                id = (String) model.getValueAt(i, 0);
                try {
                    connection.stat = connection.conn.createStatement();
                    //mengambil nilai judul dan harga sesuai kode
                    String sql = "SELECT hadiah, poin FROM HadiahMember where id_hadiah = '" + id + "'";
                    connection.result = connection.stat.executeQuery(sql);
                    while (connection.result.next()) {
                        namaHadiah= connection.result.getString("hadiah"); //mengambil nilai judul buku
                        poin = connection.result.getString("poin"); //mengambil nilai judul buku
                        //jumlah = txtjumlah.getText();
                        model.setValueAt(namaHadiah, i, 1); //set judul di tabel
                        model.setValueAt(poin, i, 2); //set harga di tabel
                        //); //set harga di tabel
                    }
                    connection.stat.close();
                    connection.result.close();

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error saat mengambil data judul dan harga buku" + ex);
                }
            }
        });
        txtTotal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //Menghitung total nilai jumlah
                int temp, total = 0;
                int i = tblkeranjang.getSelectedRow();
                if(i == -1) { //jika tidak ada baris terseleksi
                    return;
                }
                //mengetahui berapa banyak baris tabelBuku di layar
                int j = tblkeranjang.getModel().getRowCount();

                //menghitung Total = sum of (harga*jumlah)
                for(int k = 0; k < j; k++) {
                    //menghitung nilai harga*jumlah setiap baris
                    temp = (Integer.parseInt((String) model.getValueAt(k, 2))) * (Integer.parseInt((String) model.getValueAt(k, 3)));
                    total = total + temp;
                }
                txtTotal.setText(String.valueOf(total));
            }
        });

        btncarimember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                idmember = txtidmember.getText();
                namamember = txtnama.getText();
                poinmember = txtpoinmember.getText();

                try {
                    String query = "SELECT id_member,nama,poin from Member where id_member = ? ";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, idmember);
                    ResultSet result1 = connection.pstat.executeQuery();
                    if(result1.next()==false)
                    {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtnama.setText("");
                        txtpoinmember.setText("");
                    }
                    else
                    {
                        txtnama.setText(result1.getString("nama"));
                        txtpoinmember.setText(result1.getString("poin"));
                    }
                } catch ( SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat insert data Jabatan: " + ex);
                }
            }
        });

        txtsisapoin.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                poin1 = Integer.parseInt(txtpoinmember.getText());
                total1 = Integer.parseInt(txtTotal.getText());

                sisapoin = String.valueOf(poin1 - total1);
                txtsisapoin.setText(sisapoin);

            }
        });
    }

    public void autoidpenukaran() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (trs_idPH,3))+1 FROM PenukaranHadiah";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.result = connection.pstat.executeQuery();
            int autoid = 0;
            while(connection.result.next()){
                if(connection.result.getString(1)==null){
                    autoid = 1;
                }else{
                    autoid =Integer.parseInt(connection.result.getString(1));
                }
            }
            String kode;
            if(autoid<10){
                kode = "PNH00"+autoid;
            }else if(autoid<100){
                kode = "PNH0"+autoid;
            }else{
                kode = "PNH"+autoid;
            }
            txtidpenukaran.setText(kode);
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }
    /*public void autoidpengguna() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_pengguna,3))+1 FROM Pengguna";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.result = connection.pstat.executeQuery();
            int autoid = 0;
            while(connection.result.next()){
                if(connection.result.getString(1)==null){
                    autoid = 1;
                }else{
                    autoid =Integer.parseInt(connection.result.getString(1));
                }
            }
            String kode;
            if(autoid<10){
                kode = "PGN00"+autoid;
            }else if(autoid<100){
                kode = "PGN0"+autoid;
            }else{
                kode = "PGN"+autoid;
            }
            txtidpengguna.setText(kode);
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }*/


    public void app() {
        JFrame frame = new JFrame("TransaksiPenukaranHadiah");
        frame.setContentPane(new TransaksiPenukaranHadiah().penukaranhadiah);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
