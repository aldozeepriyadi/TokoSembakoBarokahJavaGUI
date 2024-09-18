package DSPemilik;

import javax.swing.*;

public class LaporanPenjualan {
    private JPanel LaporanPenjualan;


    public void app(){
        JFrame frame = new JFrame("LaporanPenjualan");
        frame.setContentPane(new LaporanPenjualan().LaporanPenjualan);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
