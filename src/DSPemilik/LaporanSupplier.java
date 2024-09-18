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

public class LaporanSupplier extends JFrame{
    private JPanel MainPanel;
    private JTextField txttahun;
    private JComboBox CmbBulan;
    private JButton BtnExportPenjualan;
    private JTable tblSupplier;
    private JButton btnTampil;
    private JButton btnBersihkan;
    private JPanel LaporanSupplier;
    private DefaultTableModel model;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
     DBConnection_06 connection = new DBConnection_06();
     int bulan;
     String Tahun="2022";


    public LaporanSupplier(){
       // txttahun.setText("2022");
        model = new DefaultTableModel();
        tblSupplier.setModel(model);
        loaddata(bulan,Tahun);
        addColumn();
        btnTampil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bulan = CmbBulan.getSelectedIndex() + 1;
                Tahun = String.valueOf(Integer.parseInt(txttahun.getText()));
                loaddata(bulan,Tahun);
            }
        });
        BtnExportPenjualan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(model.getRowCount() == 0){
                    JOptionPane.showMessageDialog(null,"Data Kosong","Information",JOptionPane.INFORMATION_MESSAGE);
                }else {
                    bulan = CmbBulan.getSelectedIndex() + 1;
                    Tahun = String.valueOf(Integer.parseInt(txttahun.getText()));
                    Map<String, Object> param = new HashMap<String, Object>();
                    try {
                        param.put("tahun", Tahun);
                        param.put("bulan", bulan);

                        JRDataSource dataSource = new JRTableModelDataSource(tblSupplier.getModel());
                        JasperDesign jd = JRXmlLoader.load("C:\\Users\\RIAN SURYANA\\Documents\\Kuliah\\SEMESTER 2\\PRG3\\TokoSembako Barokah_KEL06\\src\\Jasper\\MyReports\\TransaksiSupplier.jrxml");
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
    }

    public void clear(){
        txttahun.setText("");
    }




    public void loaddata(int bulan, String tahun){
        //menghapus seluruh data ditampilkan (jika ada) untuk tampilan pertama
        model.getDataVector().removeAllElements();

        //memberi tahu data telah kosong
        model.fireTableDataChanged();
        try{
            //connection.stat = connection.conn.createStatement();
            String query ="EXEC sp_LaporanSupplier @tahun=?,@bulan=?";
            connection.pstat=connection.conn.prepareStatement(query);
            connection.pstat.setInt(1,Integer.parseInt(tahun));
            connection.pstat.setInt(2,bulan);
            connection.result = connection.pstat.executeQuery();
            if(!connection.result.isBeforeFirst()){
                JOptionPane.showMessageDialog(MainPanel,"Data Yang Anda Cari Tidak Ada!","Informasi",JOptionPane.INFORMATION_MESSAGE);

            }else{
              while(connection.result.next()) {
                  Object[] obj =new Object[5];
                  obj[0] = connection.result.getString(1);
                  obj[1] = connection.result.getString(2);
                  obj[2] = connection.result.getString(3);
                  obj[3] = connection.result.getString(4);
                  obj[4] = connection.result.getString(5);

                  model.addRow(obj);

              }
            }
            connection.stat.close();
            connection.result.close();
        }catch(Exception ex){
            System.out.println("Error Saat load Data:"+ex);
        }

    }
    private void addColumn() {
        CmbBulan.setSelectedIndex(-1);
        model.addColumn("ID Transaksi Supplier");
        model.addColumn("ID Pengguna");
        model.addColumn("ID Supplier");
        model.addColumn("Total");
        model.addColumn("Tanggal Transaksi");
    }


    public void app() {
        JFrame frame = new JFrame("LaporanSupplier");
        frame.setContentPane(new LaporanSupplier().LaporanSupplier);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
