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
import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public class FrmIngOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	//�������
	private Object tblIngOrderTitle[]=BeanOrder.tableTitles1;
	//��ά��洢
	private Object tblIngOrderData[][];
	//�������ģ��
	DefaultTableModel tabIngOrderModel=new DefaultTableModel();
	//��tabIngOrderModelΪģ�͹�����
	private JTable dataTableIngOrder=new JTable(tabIngOrderModel);
	List<BeanOrder> IngOrder=null;
	private void reloadIngOrder(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰIngOrder
			IngOrder=takeawayUtil.orderManager.loaduserIngOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblIngOrderData = new Object[IngOrder.size()][BeanOrder.tableTitles1.length];
		for(int i=0;i<IngOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles1.length;j++)
				tblIngOrderData[i][j]=IngOrder.get(i).getCell1(j);
		}
		tabIngOrderModel.setDataVector(tblIngOrderData,tblIngOrderTitle);
		this.dataTableIngOrder.validate();
		//��֤�������������
		this.dataTableIngOrder.repaint();
		//�ػ�����
	}
	public FrmIngOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("�ҵ���Ϣ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableIngOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableIngOrder), BorderLayout.WEST);
		this.reloadIngOrder();
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
