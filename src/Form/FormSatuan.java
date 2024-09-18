package Form;

import Satuan.LihatSatuan;
import Satuan.TambahSatuan;
import Satuan.UbahHapusSatuan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormSatuan {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormSatuan;

    public FormSatuan() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TambahSatuan ts = new TambahSatuan();
                ts.ts();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UbahHapusSatuan uhs = new UbahHapusSatuan();
                uhs.uhs();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatSatuan ls = new LihatSatuan();
                ls.ls();
            }
        });

    }

    public void formsupplier() {
        JFrame frame = new JFrame("FormSatuan");
        frame.setContentPane(new FormSatuan().FormSatuan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
