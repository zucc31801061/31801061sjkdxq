package takeaway.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import takeaway.takeawayUtil;
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmMyxfInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	private Object tblxfTitle[]=BeanOrder.tableTitles6;
	private Object tblxfData[][];
	DefaultTableModel tabxfModel=new DefaultTableModel();
	private JTable dataTablexf=new JTable(tabxfModel);
	
	List<BeanOrder> xf=null;
	private void reloadxfTable(){
		try {
			xf=takeawayUtil.orderManager.loaduserxfinfo();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblxfData = new Object[xf.size()][BeanOrder.tableTitles6.length];
		for(int i=0;i<xf.size();i++){
			for(int j=0;j<BeanOrder.tableTitles6.length;j++)
				tblxfData[i][j]=xf.get(i).getCell6(j);
		}
		tabxfModel.setDataVector(tblxfData,tblxfTitle);
		this.dataTablexf.validate();
		this.dataTablexf.repaint();
	}
	public FrmMyxfInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("�������");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
	    this.getContentPane().add(new JScrollPane(this.dataTablexf), BorderLayout.WEST);
	    this.reloadxfTable();
		this.setSize(460, 250);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.btnok.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
	}
}
