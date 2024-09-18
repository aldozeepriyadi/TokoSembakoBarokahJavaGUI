package Form;

import Merk.LihatMerk;
import Merk.TambahMerk;
import Merk.UBahHapusMerk;
import Satuan.TambahSatuan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FromMerk {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormMerk;

    public FromMerk() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TambahMerk tm = new TambahMerk();
                tm.tm();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UBahHapusMerk uhm = new UBahHapusMerk();
                uhm.uhm();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatMerk lm = new LihatMerk();
                lm.lm();
            }
        });
    }

    public void formmerk() {
        JFrame frame = new JFrame("From Merk");
        frame.setContentPane(new FromMerk().FormMerk);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
