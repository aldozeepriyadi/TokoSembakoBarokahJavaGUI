package Form;

import Hadiah.TambahHadiah;
import Hadiah.LihatHadiah;
import Hadiah.UbahHapusHadiah;
import Member.UbahHapusMember;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormHadiah {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormHadiah;

    public FormHadiah() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TambahHadiah th = new TambahHadiah();
                th.TH();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UbahHapusHadiah uhh = new UbahHapusHadiah();
               uhh.UHH();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatHadiah lh = new LihatHadiah();
                lh.LH();
            }
        });
    }



    public void formhadiah() {
        JFrame frame = new JFrame("FormHadiah");
        frame.setContentPane(new FormHadiah().FormHadiah);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
