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
import takeaway.model.BeanKind;
import takeaway.util.BaseException;

public class FrmProKind extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private Object tblProKindTitle[]=BeanKind.tableTitles;
	private Object tblProKindData[][];
	DefaultTableModel tabProKindModel=new DefaultTableModel();
	private JTable dataTableProKind=new JTable(tabProKindModel);
	List<BeanKind> ProKind=null;
	private void reloadProKind(){
		try {
			ProKind=takeawayUtil.kindManager.loadAll();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblProKindData = new Object[ProKind.size()][BeanKind.tableTitles.length];
		for(int i=0;i<ProKind.size();i++){
			for(int j=0;j<BeanKind.tableTitles.length;j++)
				tblProKindData[i][j]=ProKind.get(i).getCell(j);
		}
		tabProKindModel.setDataVector(tblProKindData,tblProKindTitle);
		this.dataTableProKind.validate();
		this.dataTableProKind.repaint();
	}
	public FrmProKind(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("查看类别");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
		//加入一个显示dataTableProKind的滚动条到页面的左边
	    this.getContentPane().add(new JScrollPane(this.dataTableProKind), BorderLayout.WEST);
		this.reloadProKind();
		this.setSize(460, 150);
		// 屏幕居中显示
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
