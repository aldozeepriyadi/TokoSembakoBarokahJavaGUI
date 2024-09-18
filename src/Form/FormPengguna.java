package Form;

import Pengguna.AddPengguna;
import Pengguna.MasterPengguna;
import Pengguna.ViewPengguna;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPengguna {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormPengguna;

    public FormPengguna() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPengguna tp = new AddPengguna();
                tp.TP();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MasterPengguna uhp = new MasterPengguna();
                uhp.UHP();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPengguna lp = new ViewPengguna();
                lp.LP();
            }
        });
    }

    public void formpengguna() {
        JFrame frame = new JFrame("FormPengguna");
        frame.setContentPane(new FormPengguna().FormPengguna);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
