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

public class FrmTakeOrder extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button send = new Button("�ӵ�");
	private Button btnok = new Button("ȷ��");
	//�������
	private Object tblFreeOrderTitle[]=BeanOrder.tableTitles2;
	//��ά��洢
	private Object tblFreeOrderData[][];
	//�������ģ��
	DefaultTableModel tabFreeOrderModel=new DefaultTableModel();
	//��tabFreeOrderModelΪģ�͹�����
	private JTable dataTableFreeOrder=new JTable(tabFreeOrderModel);
	BeanOrder curOrder;
	List<BeanOrder> FreeOrder=null;
	private void reloadFreeOrder(){//���ǲ������ݣ���Ҫ��ʵ�����滻
		try {
			//��ѯ��ǰFreeOrder
			FreeOrder=takeawayUtil.orderManager.loadriderfreeOrder();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblFreeOrderData = new Object[FreeOrder.size()][BeanOrder.tableTitles2.length];
		for(int i=0;i<FreeOrder.size();i++){
			for(int j=0;j<BeanOrder.tableTitles2.length;j++)
				tblFreeOrderData[i][j]=FreeOrder.get(i).getCell2(j);
		}
		tabFreeOrderModel.setDataVector(tblFreeOrderData,tblFreeOrderTitle);
		this.dataTableFreeOrder.validate();
		//��֤�������������
		this.dataTableFreeOrder.repaint();
		//�ػ�����
	}
	public FrmTakeOrder(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("�ɽӵ�");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(send);
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//����һ����ʾdataTableFreeOrder�Ĺ�������ҳ������
	    this.getContentPane().add(new JScrollPane(this.dataTableFreeOrder), BorderLayout.WEST);
	    this.dataTableFreeOrder.addMouseListener(new MouseAdapter (){
			@Override
	    	//������ϵ�����갴ťʱ���ú���
			public void mouseClicked(MouseEvent e) {
				//������ѡ��һ�е�����
				int i=FrmTakeOrder.this.dataTableFreeOrder.getSelectedRow();
				//��û��ѡ�����򷵻�-1
				if(i<0) {
					return;
				}
				curOrder=FreeOrder.get(i);
			}
	    });
	    this.reloadFreeOrder();
		this.setSize(460, 250);
		// ��Ļ������ʾ
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		this.setLocation((int) (width - this.getWidth()) / 2,
				(int) (height - this.getHeight()) / 2);

		this.validate();
		this.send.addActionListener(this);
		this.btnok.addActionListener(this);
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.btnok) {
			this.setVisible(false);
			return;
		}
		else if(e.getSource()==this.send) {
			if(this.curOrder==null) {
				JOptionPane.showMessageDialog(null, "��ѡ�񶩵�", "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			try {
				takeawayUtil.orderManager.takeOrder(this.curOrder);
			} catch (BaseException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "����",JOptionPane.ERROR_MESSAGE);
				return;
			}
			this.setVisible(false);
		}
	}
}
