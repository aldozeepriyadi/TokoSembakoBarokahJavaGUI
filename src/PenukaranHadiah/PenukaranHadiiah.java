package PenukaranHadiah;

import DBConnection_06.DBConnection_06;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PenukaranHadiiah {
    private JPanel penukaranhadiah;
    private JTextField txtidmember;
    private JTextField txtnama;
    private JTextField txtpoinmember;
    private JButton btncarimember;
    private JTextField txtidpengguna;
    private JTextField txtidpenukaran;
    private JTable tblKeranjang;
    private JTextField txtidhadiah;
    private JTextField txthadiah;
    private JTextField txtpoin;
    private JTextField txtjumlah;
    private JTextField txtTotal;
    private JTextField txtsisapoin;
    private JButton SImpanTransaksiButton;
    private JButton btncarihadiah;
    private JButton btntambahkeranjang;
    private JButton bersihkanButton;
    private JPanel tukarhadiah;
    private JPanel PanelTanggalPenukaran;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06();
    String idpenukaran;
    String idpengguna;
    String idmember;
    String namamember;
    String poinmember;
    String idhadiah;
    String hadiah;
    String poinhadiah;
    String jumlah;
    String total;
    String sisapoin;

    JDateChooser datechos = new JDateChooser();

    public PenukaranHadiiah() {
        model = new DefaultTableModel();

        tblKeranjang.setModel(model);
        addcolumn();

        autoidpenukaran();
        autoidpengguna();

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

    }

    public void addcolumn(){
        model.addColumn("ID Penukaran");
        model.addColumn("ID Hadiah");
        model.addColumn("Nama Hadiah");
        model.addColumn("Poin");
        model.addColumn("Jumlah");
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
    public void autoidpengguna() {
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
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("PenukaranHadiiah");
        frame.setContentPane(new PenukaranHadiiah().tukarhadiah);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
