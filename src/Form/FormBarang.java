package Form;

import Barang.LihatBarang;
import Barang.TambahBarang;
import Barang.UbahHapusBarang;
import Dashboard.DashboardAdmin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormBarang {
    private JButton btnTambahBarang;
    private JButton btnLihatBarang;
    private JButton btnHapusUbahBarang;
    private JPanel FormBarang;

    public FormBarang() {
        btnTambahBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TambahBarang tb = new TambahBarang();
                tb.TB();
            }
        });
        btnLihatBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatBarang lb = new LihatBarang();
                lb.LB();
            }
        });
        btnHapusUbahBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UbahHapusBarang UHB = new UbahHapusBarang();
                UHB.UHB();
            }
        });

    }

    public void formbarang() {
        JFrame frame = new JFrame("FormBarang");
        frame.setContentPane(new FormBarang().FormBarang);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
    }

}
