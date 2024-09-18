package DSPemilik;

import DBConnection_06.DBConnection_06;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class LaporanPenukaran {
    private JPanel MainPanel;
    private JTextField txttahun;
    private JComboBox CmbBulan;
    private JButton BtnExportPenjualan;
    private JTable tblPenukaran;
    private JButton btnTampil;
    private JButton btnBersihkan;
    private JPanel laporanpenukaran;
    private DefaultTableModel model;

    DBConnection_06 connection = new DBConnection_06();
    int tahun;
    int bulan;

    public LaporanPenukaran() {
        //Membuat table model
        model = new DefaultTableModel();
        //menambahkan tabel model
        tblPenukaran.setModel(model);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        addColumn();
        loadData(tahun, bulan);

        btnTampil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tahun = Integer.parseInt(txttahun.getText());
                bulan = CmbBulan.getSelectedIndex() + 1;
                loadData(tahun, bulan);
            }
        });
        BtnExportPenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null,"Data Kosong","Information",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    tahun = Integer.parseInt(txttahun.getText());
                    bulan = CmbBulan.getSelectedIndex() + 1;
                    //JasperPrint jp;
                    Map<String, Object> param = new HashMap<String, Object>();
                    try {
                        param.put("tahun", tahun);
                        param.put("bulan", bulan);

                        JRDataSource dataSource = new JRTableModelDataSource(tblPenukaran.getModel());
                        JasperDesign jd = JRXmlLoader.load("C:\\Users\\RIAN SURYANA\\Documents\\Kuliah\\SEMESTER 2\\PRG3\\TokoSembako\\src\\Jasper\\MyReports\\Penukaran.jrxml");
                        JasperReport jr = JasperCompileManager.compileReport(jd);

                        JasperPrint jp = JasperFillManager.fillReport(jr, param, dataSource);
                        //jp = JasperFillManager.fillReport("C:\\Users\\sraul\\Downloads\\Proyek_PRG3_Kel07 (3)\\Proyek_PRG3_Kel07\\Proyek_PRG3_Kel07\\src\\Jasper\\MyReports\\LaporanPembelian.jrxml", param, connect.conn);
                        JasperViewer viewer = new JasperViewer(jp, false);
                        viewer.setTitle("Laporan");
                        viewer.setVisible(true);
                    } catch (JRException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
            }
        });
        btnBersihkan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
    }

    public void clear(){
        txttahun.setText("");
    }

    private void loadData(int tahun, int bulan) {
        //menghapus seluruh data ditampilkan (jika ada) untuk tampilan pertama
        model.getDataVector().removeAllElements();

        //memberi tahu data telah kosong
        model.fireTableDataChanged();

        try {
            connection.stat = connection.conn.createStatement();
            String query ="SELECT * FROM PenukaranHadiah  WHERE YEAR(trs_tanggal) = " + tahun + "AND MONTH(trs_tanggal) = " + bulan;;
            connection.result = connection.stat.executeQuery(query);

            //lakukan perbaris data
            while (connection.result.next()) {
                Object[] obj = new Object[4];
                obj[0] = connection.result.getString("trs_idPH");
                obj[1] = connection.result.getString("trs_tanggal");
                obj[2] = connection.result.getString("id_member");
                obj[3] = connection.result.getString("trs_poin");
                model.addRow(obj);
            }
            connection.stat.close();
            connection.result.close();
        } catch (Exception e) {
            System.out.println("Terjadi error load data transaksi pelayanan " + e);
        }
    }
    private void addColumn() {
        CmbBulan.setSelectedIndex(-1);
        model.addColumn("ID Penukaran");
        model.addColumn("Tanggal Penukaran");
        model.addColumn("ID Member");
        model.addColumn("Total Poin");
    }



    public void app() {
        JFrame frame = new JFrame("LaporanPenukaran");
        frame.setContentPane(new LaporanPenukaran().laporanpenukaran);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
