package Form;

import Jabatan.AddJabatan;
import Jabatan.MasterJabatan;
import Jabatan.Viewjabatan;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormJabatan {
    private JButton btntambah;
    private JButton btnubahhapus;
    private JButton btnlihat;
    private JPanel FormJabatan;


    public FormJabatan() {
        btntambah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddJabatan aj = new AddJabatan();
                aj.TJ();
            }
        });
        btnubahhapus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MasterJabatan ms = new MasterJabatan();
                ms.UHJ();
            }
        });
        btnlihat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Viewjabatan vj = new Viewjabatan();
                vj.LJ();
            }
        });
    }

    public void formjabatan() {
        JFrame frame = new JFrame("FormJabatan");
        frame.setContentPane(new FormJabatan().FormJabatan);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
