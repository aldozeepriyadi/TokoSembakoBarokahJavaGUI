package Form;

import Promo.AddPromo;
import Promo.MasterPromo;
import Promo.ViewPromo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormPromo {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormPromo;

    public FormPromo() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddPromo ap = new AddPromo();
                ap.TP();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MasterPromo mp = new MasterPromo();
                mp.UHP();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewPromo vp = new ViewPromo();
                vp.LP();
            }
        });
    }

    public void formpromo() {
        JFrame frame = new JFrame("FormPromo");
        frame.setContentPane(new FormPromo().FormPromo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}

