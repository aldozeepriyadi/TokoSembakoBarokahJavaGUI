package Form;

import Member.LihatMember;
import Member.TambahMember;
import Member.UbahHapusMember;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormMember {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormMember;

    public FormMember() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TambahMember tm = new TambahMember();
                tm.TM();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UbahHapusMember uhmr = new UbahHapusMember();
                uhmr.UHMR();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatMember lm = new LihatMember();
                lm.LM();
            }
        });
    }

    public void formmember() {
        JFrame frame = new JFrame("FormMember");
        frame.setContentPane(new FormMember().FormMember);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
