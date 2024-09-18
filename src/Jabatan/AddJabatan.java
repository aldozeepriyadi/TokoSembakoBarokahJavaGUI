package Jabatan;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddJabatan extends JFrame{
    private JTextField txtNamaJabatan;
    private JTextField txtIdJabatan;
    private JButton simpanButton;
    private JButton batalButton;
    private JPanel SimpanMasterJabatan;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06(); //membuat objek dari class DBConnect
    String idjabatan;
    String jabatan;

    public boolean validasinull(){
        if(idjabatan.isEmpty()||jabatan.isEmpty() ){
            return true;
        }else{
            return false;
        }
    }

    public AddJabatan() {

        model = new DefaultTableModel();
        autoid();
       // validasinull();

        simpanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idjabatan = txtIdJabatan.getText();
                jabatan = txtNamaJabatan.getText();
                try {
                    if (validasinull()) {
                        throw new Exception("Harap Isi Semua Data!");
                    } else
                    {
                        try {
                            String query = "EXEC sp_InsertJabatan @id_jabatan=?,@nama=?";
                            connection.pstat = connection.conn.prepareStatement(query);
                            connection.pstat.setString(1, idjabatan);
                            connection.pstat.setString(2, jabatan);
                            connection.pstat.executeUpdate();
                            connection.pstat.close();

                        } catch (Exception ex) {
                            System.out.println("Terjadi error pada saat insert data" + ex);
                        }
                        JOptionPane.showMessageDialog(null, "Insert Data Berhasil");
                        clear();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null,
                            "Harap Isi Semua Data!", "Failed", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        batalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }
    public void autoid() {
        try{
            String query = "SELECT TOP (1) MAX(RIGHT (id_jabatan,2))+1 FROM Jabatan";
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
                kode = "JBT0"+autoid;
            }else{
                kode = "JBT"+autoid;
            }
            txtIdJabatan.setText(kode);
            txtNamaJabatan.requestFocus();
            connection.pstat.close();
            connection.result.close();
        }
        catch(Exception ex) {
            System.out.println("Terjadi error pada saat generate id data "+ex);
        }
    }

    public void clear(){
        txtIdJabatan.setText("");
        txtNamaJabatan.setText("");
        autoid();
    }

    public void TJ() {
        JFrame frame = new JFrame("AddJabatan");
        frame.setContentPane(new AddJabatan().SimpanMasterJabatan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
