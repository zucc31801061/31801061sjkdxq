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

public class FrmUserXfInfo extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private Object tblxfTitle[]=BeanOrder.tableTitles7;
	private Object tblxfData[][];
	DefaultTableModel tabxfModel=new DefaultTableModel();
	private JTable dataTablexf=new JTable(tabxfModel);
	
	List<BeanOrder> xf=null;
	private void reloadxfTable(){
		try {
			xf=takeawayUtil.orderManager.loadallxfinfo();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblxfData = new Object[xf.size()][BeanOrder.tableTitles7.length];
		for(int i=0;i<xf.size();i++){
			for(int j=0;j<BeanOrder.tableTitles7.length;j++)
				tblxfData[i][j]=xf.get(i).getCell7(j);
		}
		tabxfModel.setDataVector(tblxfData,tblxfTitle);
		this.dataTablexf.validate();
		this.dataTablexf.repaint();
	}
	public FrmUserXfInfo(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("消费情况");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
	    this.getContentPane().add(new JScrollPane(this.dataTablexf), BorderLayout.CENTER);
	    this.reloadxfTable();
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
