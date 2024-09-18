package Promo;

import DBConnection_06.DBConnection_06;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;


public class MasterPromo extends JFrame {
    private JTextField txtNamaPromo;
    private JTextField txtIdPromo;
    private JButton ubahButton;
    private JButton hapusButton;
    private JButton clearButton;
    private JTextField txtDiscPromo;
    private JButton CariButton;
    private JPanel CaldAwal;
    private JPanel CaldAkhir;
    private JPanel UbahHapusPromo;


    DBConnection_06 connection = new DBConnection_06();
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    JDateChooser datechos = new JDateChooser();
    JDateChooser datechos2 = new JDateChooser();

    String idpromo;
    String nama;
    String awal;
    String akhir;
    public MasterPromo() {
        CaldAwal.add(datechos);
        CaldAkhir.add(datechos2);

        idpromo = txtIdPromo.getText();
        nama =txtNamaPromo.getText();
        CariButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Search();
            }
        });
        ubahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                        try {

                            String query = "EXEC sp_UpdatePromo @id_promo=?,@nama_promo=?,@discount=?,@tanggal_mulai=?,@tanggal_berakhir=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, txtIdPromo.getText());
                            connection.pstat.setString(2, txtNamaPromo.getText());
                            connection.pstat.setString(3, txtDiscPromo.getText());
                            connection.pstat.setString(4, formatter.format(datechos.getDate()));
                            connection.pstat.setString(5, formatter.format(datechos2.getDate()));


                            connection.pstat.executeUpdate();
                            connection.pstat.close();
                            JOptionPane.showMessageDialog(null, "Update Data Berhasil");
                        } catch (Exception ex) {
                            System.out.println("Terjadi error pada saat Update data" + ex);
                        }


                }
            });
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    String query = "EXEC sp_DeletePromo @id_promo=?";
                    connection.pstat = connection.conn.prepareStatement(query);
                    connection.pstat.setString(1, txtIdPromo.getText());


                    connection.pstat.executeUpdate();
                    connection.pstat.close();
                    JOptionPane.showMessageDialog(null, "Delete Data Berhasil");
                } catch (Exception ex) {
                    System.out.println("Terjadi error pada saat Delete data" + ex);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    public void clear(){
        txtIdPromo.setText("");
        txtNamaPromo.setText("");
        txtDiscPromo.setText("");
        datechos.setDate(null);
        datechos2.setDate(null);
    }
    public boolean validasinull(){
        if(idpromo.isEmpty()||nama.isEmpty()||awal.isEmpty()|akhir.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }
    public void Search() {

        try {
            String query = "SELECT id_promo,nama_promo,discount,tanggal_mulai,tanggal_berakhir from Promo where id_promo = ? ";
            connection.pstat = connection.conn.prepareStatement(query);
            connection.pstat.setString(1, txtIdPromo.getText());
            ResultSet result1 = connection.pstat.executeQuery();
            if (result1.next() == false) {
                JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                txtNamaPromo.setText("");
                txtDiscPromo.setText("");
            } else {
                txtNamaPromo.setText(result1.getString("nama_promo"));
                txtDiscPromo.setText(result1.getString("discount"));
                datechos.setDate(result1.getDate("tanggal_mulai"));
                datechos2.setDate(result1.getDate("tanggal_berakhir"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Terjadi error pada saat Cari data Promo: " + ex);
        }
    }

    public void UHP() {
        JFrame frame = new JFrame("Master Promo");
        frame.setContentPane(new MasterPromo().UbahHapusPromo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
