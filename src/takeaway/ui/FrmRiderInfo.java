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
import takeaway.model.BeanRider;
import takeaway.util.BaseException;

public class FrmRiderInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	private Object tblriderTitle[]=BeanRider.tableTitles;
	private Object tblriderData[][];
	DefaultTableModel tabriderModel=new DefaultTableModel();
	private JTable dataTablerider=new JTable(tabriderModel);
	List<BeanRider> rider=null;
	private void reloadrider(){
		try {
			rider=takeawayUtil.riderManager.loadbyrider();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblriderData = new Object[rider.size()][BeanRider.tableTitles.length];
		for(int i=0;i<rider.size();i++){
			for(int j=0;j<BeanRider.tableTitles.length;j++)
				tblriderData[i][j]=rider.get(i).getCell(j);
		}
		tabriderModel.setDataVector(tblriderData,tblriderTitle);
		this.dataTablerider.validate();
		this.dataTablerider.repaint();
	}
	public FrmRiderInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("�鿴��Ϣ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTablerider�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTablerider), BorderLayout.WEST);
		this.reloadrider();
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
