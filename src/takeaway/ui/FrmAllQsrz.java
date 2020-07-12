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

public class FrmAllQsrz extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private Object tblqsrzTitle[]=BeanOrder.tableTitles8;
	private Object tblqsrzData[][];
	DefaultTableModel tabqsrzModel=new DefaultTableModel();
	private JTable dataTableqsrz=new JTable(tabqsrzModel);
	
	List<BeanOrder> qsrz=null;
	private void reloadqsrzTable(){
		try {
			qsrz=takeawayUtil.orderManager.loadallridermoney();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblqsrzData = new Object[qsrz.size()][BeanOrder.tableTitles8.length];
		for(int i=0;i<qsrz.size();i++){
			for(int j=0;j<BeanOrder.tableTitles8.length;j++)
				tblqsrzData[i][j]=qsrz.get(i).getCell8(j);
		}
		tabqsrzModel.setDataVector(tblqsrzData,tblqsrzTitle);
		this.dataTableqsrz.validate();
		this.dataTableqsrz.repaint();
	}
	public FrmAllQsrz(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("骑手入账");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
	    this.getContentPane().add(new JScrollPane(this.dataTableqsrz), BorderLayout.CENTER);
	    this.reloadqsrzTable();
		this.setSize(500, 500);
		// 屏幕居中显示
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
