package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanStore;
import takeaway.util.BaseException;

public class FrmStoreInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	private Object tblProStoreTitle[]=BeanStore.tableTitles;
	private Object tblProStoreData[][];
	DefaultTableModel tabProStoreModel=new DefaultTableModel();
	private JTable dataTableProStore=new JTable(tabProStoreModel);
	List<BeanStore> ProStore=null;
	private void reloadProStore(){
		try {
			ProStore=takeawayUtil.storeManager.loadbystore();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblProStoreData = new Object[ProStore.size()][BeanStore.tableTitles.length];
		for(int i=0;i<ProStore.size();i++){
			for(int j=0;j<BeanStore.tableTitles.length;j++)
				tblProStoreData[i][j]=ProStore.get(i).getCell(j);
		}
		tabProStoreModel.setDataVector(tblProStoreData,tblProStoreTitle);
		this.dataTableProStore.validate();
		this.dataTableProStore.repaint();
	}
	public FrmStoreInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("�鿴��Ϣ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableProStore�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableProStore), BorderLayout.WEST);
		this.reloadProStore();
		this.setSize(460, 150);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
	}
}
