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

public class FrmRiderHisOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("ȷ��");
	//�������
	private Object tblHisOrderTitle[]=BeanOrder.tableTitles2;
	//��ά��洢
	private Object tblHisOrderData[][];
	//�������ģ��
	DefaultTableModel tabHisOrderModel=new DefaultTableModel();
	//��tabHisOrderModelΪģ�͹�����
	private JTable dataTableHisOrder=new JTable(tabHisOrderModel);
	BeanOrder curOrder;
	List<BeanOrder> HisOrder=null;
	private void reloadHisOrder(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰHisOrder
			HisOrder=takeawayUtil.orderManager.loadriderHisOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblHisOrderData = new Object[HisOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<HisOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblHisOrderData[i][j]=HisOrder.get(i).getCell2(j);
		}
		tabHisOrderModel.setDataVector(tblHisOrderData,tblHisOrderTitle);
		this.dataTableHisOrder.validate();
		//��֤�������������
		this.dataTableHisOrder.repaint();
		//�ػ�����
	}
	public FrmRiderHisOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("�ҵ���Ϣ");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableHisOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableHisOrder), BorderLayout.WEST);
		this.reloadHisOrder();
		this.setSize(460, 250);
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
