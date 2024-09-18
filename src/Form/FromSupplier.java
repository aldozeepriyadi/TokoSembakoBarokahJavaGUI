package Form;

import Supplier.LihatSupplier;
import Supplier.TambahSupplier;
import Supplier.UbahHapusSupplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FromSupplier {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormSupplier;

    public FromSupplier() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TambahSupplier ts = new TambahSupplier();
                ts.ts();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UbahHapusSupplier uhs = new UbahHapusSupplier();
                uhs.uhs();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LihatSupplier ls = new LihatSupplier();
                ls.ls();
            }
        });
    }
    public void formsupllier() {
        JFrame frame = new JFrame("FromSupplier");
        frame.setContentPane(new FromSupplier().FormSupplier);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
