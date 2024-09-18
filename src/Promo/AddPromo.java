package Promo;

import DBConnection_06.DBConnection_06;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class AddPromo extends JFrame{
    private JTextField txtNamaPromo;
    private JTextField txtIdPromo;
    private JButton clearButton;
    private JTextField txtDiscPromo;
    private JButton simpanButton;
    private JPanel AddPromo;
    private JPanel CaldAwal;
    private JPanel CaldAkhir;
    private JPanel jpCald;
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    DBConnection_06 connect = new DBConnection_06();
    JDateChooser datechos = new JDateChooser();
    JDateChooser datechos2 = new JDateChooser();
    String idpromo;
    String nama;
    int promo;
    String awal;
    String akhir;

    public AddPromo() {

        CaldAwal.add(datechos);
        CaldAkhir.add(datechos2);
        autoid();


        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                   try {
                    idpromo = txtIdPromo.getText();
                    nama =txtNamaPromo.getText();
                    promo = Integer.parseInt(txtDiscPromo.getText());
                    awal = formatter.format(datechos.getDate());
                    akhir = formatter.format(datechos2.getDate());


                    String query = "EXEC sp_InsertPromo @id_promo=?,@nama_promo=?,@discount=?,@tanggal_mulai=?,@tanggal_berakhir=?";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1, txtIdPromo.getText());
                    connect.pstat.setString(2, txtNamaPromo.getText());
                    connect.pstat.setString(3, txtDiscPromo.getText());
                    connect.pstat.setString(4, formatter.format(datechos.getDate()));
                    connect.pstat.setString(5, formatter.format(datechos2.getDate()));


                    connect.pstat.executeUpdate();
                    connect.pstat.close();
                    JOptionPane.showMessageDialog(null, "Insert Data Berhasil");
                   } catch (Exception ex) {
                    System.out.println("Terjadi error pada saat insert data" + ex);
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

    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_promo,3))+1 FROM Promo";
            connect.pstat = connect.conn.prepareStatement(query);
            connect.result = connect.pstat.executeQuery();
            int autoid = 0;
            while(connect.result.next()){
                if(connect.result.getString(1)==null){
                    autoid = 1;
                }else{
                    autoid =Integer.parseInt(connect.result.getString(1));
                }
            }
            String kode;
            if(autoid<10){
                kode = "PRM00"+autoid;
            }else if(autoid<100){
                kode = "PRM0"+autoid;
            }else{
                kode = "PRM"+autoid;
            }
            txtIdPromo.setText(kode);
            connect.pstat.close();
            connect.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }
    public boolean validasinull(){
        if(idpromo.isEmpty()||nama.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }
    public void clear(){
        txtIdPromo.setText("");
        txtNamaPromo.setText("");
        txtDiscPromo.setText("");
        datechos.setDate(null);
        datechos2.setDate(null);
    }
    public void TP() {
        JFrame frame = new JFrame("Add Promo");
        frame.setContentPane(new AddPromo().AddPromo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
