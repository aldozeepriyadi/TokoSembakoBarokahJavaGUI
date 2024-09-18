package Dashboard;

import Transaksi.TransaksiPenjualan;
import Transaksi.TransaksiPenukaranHadiah;
import Transaksi.TransaksiSupplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardKasir {
    public JPanel DSKasir;
    private JButton btnPenjualan;
    private JButton transaksiSupplierButton;
    private JButton btnPenukaranHadiah;
    private JButton btnSupplier;

    public String idpgw;

    public DashboardKasir() {
        btnPenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnPenukaranHadiah.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransaksiPenukaranHadiah tph = new TransaksiPenukaranHadiah();
                tph.app();
            }
        });
        btnPenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransaksiPenjualan tp = new TransaksiPenjualan();
                tp.tp();
            }
        });
        btnSupplier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TransaksiSupplier ts = new TransaksiSupplier();
                ts.ts();
            }
        });
    }

    public void dskasir() {
        JFrame frame = new JFrame("DashboardKasir");
        frame.setContentPane(new DashboardKasir().DSKasir);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("DashboardKasir");
        frame.setContentPane(new DashboardKasir().DSKasir);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
