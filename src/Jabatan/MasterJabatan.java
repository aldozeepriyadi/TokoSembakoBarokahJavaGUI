package Jabatan;

import DBConnection_06.DBConnection_06;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class MasterJabatan {
    private JTextField txtNamaJabatan;
    private JTextField txtIdJabatan;
    private JButton ubahButton;
    private JButton hapusButton;
    private JPanel UpdateHapusJabatan;
    private JButton CariButton;
    private JButton btnClear;
    DBConnection_06 connect = new DBConnection_06();

    public void UHJ() {
        JFrame frame = new JFrame("MasterJabatan");
        frame.setContentPane(new MasterJabatan().UpdateHapusJabatan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public MasterJabatan() {
        ubahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    try {
                        String query = "EXEC sp_Updatejabatan @id_jabatan=?,@nama=?";
                        connect.pstat = connect.conn.prepareStatement(query);
                        connect.pstat.setString(1, txtIdJabatan.getText());
                        connect.pstat.setString(2, txtNamaJabatan.getText());
                        connect.pstat.executeUpdate();
                        connect.pstat.close();
                        JOptionPane.showMessageDialog(null, "Update Data Berhasil");
                        clear();
                    } catch (Exception ex) {
                        System.out.println("Terjadi error pada saat Update data" + ex);
                    }


                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        });
        hapusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "EXEC sp_DeleteJabatan @id_jabatan=?";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1, txtIdJabatan.getText());

                    connect.pstat.executeUpdate();
                    connect.pstat.close();
                    clear();
                    JOptionPane.showMessageDialog(null, "Delete Data Berhasil;");

                } catch (Exception ex) {
                    System.out.println("Terjadi error pada saat delete data" + ex);
                }


            }
        });
        CariButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "SELECT id_jabatan,nama from Jabatan where id_jabatan = ? ";
                    connect.pstat = connect.conn.prepareStatement(query);
                    connect.pstat.setString(1, txtIdJabatan.getText());
                    ResultSet result1 = connect.pstat.executeQuery();
                    if (result1.next() == false) {
                        JOptionPane.showMessageDialog(null, "Sorry Record Not Found");
                        txtNamaJabatan.setText("");
                    } else {
                        txtNamaJabatan.setText(result1.getString("nama"));
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Terjadi error pada saat Cari data Jabatan: " + ex);
                }

            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }
    public void clear(){
        txtIdJabatan.setText(null);
        txtNamaJabatan.setText(null);
    }
}
