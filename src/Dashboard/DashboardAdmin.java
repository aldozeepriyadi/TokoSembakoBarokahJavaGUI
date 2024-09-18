package Dashboard;

import Form.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardAdmin {

    public JPanel  dsadmin;
    private JButton btnFormMember;
    private JButton btnFormMerk;
    private JButton btnFormSupplier;
    private JButton btnFormBarang;
    private JButton btnFormSatuan;
    private JButton btnFormPengguna;
    private JButton btnFormJabatan;
    private JButton btnFormPromo;
    private JButton btnFormHadiah;

    public DashboardAdmin() {
        btnFormMember.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormMember fmr = new FormMember();
                fmr.formmember();
            }
        });
        btnFormMerk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              FromMerk fmk = new FromMerk();
              fmk.formmerk();
            }
        });
        btnFormSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FromSupplier fs = new FromSupplier();
                fs.formsupllier();
            }
        });
        btnFormJabatan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormJabatan fj = new FormJabatan();
                fj.formjabatan();
            }
        });
        btnFormPengguna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormPengguna fp = new FormPengguna();
                fp.formpengguna();
            }
        });
        btnFormSatuan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormSatuan fs = new FormSatuan();
                fs.formsupplier();
            }
        });
        btnFormPromo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormPromo fpm = new FormPromo();
                fpm.formpromo();
            }
        });
        btnFormHadiah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormHadiah fh = new FormHadiah();
                fh.formhadiah();
            }
        });
        btnFormBarang.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormBarang fb = new FormBarang();
                fb.formbarang();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DashboardAdmin");
        frame.setContentPane(new DashboardAdmin().dsadmin);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void admin() {
        JFrame frame = new JFrame("DashboardAdmin");
        frame.setContentPane(new DashboardAdmin().dsadmin);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
