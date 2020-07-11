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
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public class FrmStoreMypj extends JDialog implements ActionListener {
	private JPanel toolBar = new JPanel();
	private Button btnok = new Button("确定");
	private Object tblpjTitle[]=BeanSppj.tableTitles1;
	private Object tblpjData[][];
	DefaultTableModel tabpjModel=new DefaultTableModel();
	private JTable dataTablepj=new JTable(tabpjModel);
	
	BeanSppj curpj;
	List<BeanSppj> pj=null;
	private void reloadpjTable(){
		try {
			pj=takeawayUtil.sppjManager.loadbystore();
		} catch (BaseException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "错误",JOptionPane.ERROR_MESSAGE);
			return;
		}
		tblpjData = new Object[pj.size()][BeanSppj.tableTitles1.length];
		for(int i=0;i<pj.size();i++){
			for(int j=0;j<BeanSppj.tableTitles1.length;j++)
				tblpjData[i][j]=pj.get(i).getCell1(j);
		}
		tabpjModel.setDataVector(tblpjData,tblpjTitle);
		this.dataTablepj.validate();
		this.dataTablepj.repaint();
	}
	public FrmStoreMypj(JFrame f, String s, boolean b) {
		super(f, s, b);
		this.setTitle("用户评价");
		toolBar.setLayout(new FlowLayout(FlowLayout.RIGHT));
		toolBar.add(btnok);
		this.getContentPane().add(toolBar, BorderLayout.SOUTH);
	    this.getContentPane().add(new JScrollPane(this.dataTablepj), BorderLayout.WEST);
	    this.dataTablepj.addMouseListener(new MouseAdapter (){
			@Override
			public void mouseClicked(MouseEvent e) {
				int i=FrmStoreMypj.this.dataTablepj.getSelectedRow();
				if(i<0) {
					return;
				}
				curpj=pj.get(i);
			}
	    });
	    this.reloadpjTable();
		this.setSize(460, 250);
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
