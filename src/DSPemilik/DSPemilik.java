package DSPemilik;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPemilik {
    private JButton btnpenjualan;
    private JButton btnsupplier;
    private JButton btnpenukaran;
    public JPanel dspemilik;

    public DSPemilik() {
        btnpenukaran.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaporanPenukaran lp = new LaporanPenukaran();
                lp.app();
            }
        });
        btnsupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaporanSupplier ls = new LaporanSupplier();
                ls.app();
            }
        });
        btnpenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LaporanPenjualan lp2 = new LaporanPenjualan();
                lp2.app();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DSPemilik");
        frame.setContentPane(new DSPemilik().dspemilik);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
