package takeaway.model;

import java.sql.Date;

public class BeanYh {
	public static final String[] tableTitles={"�Żݽ��","�̼ұ��","�������"};
	public static final String[] tableTitles1={"�Żݽ��","��ʼ����","��������","�̼ұ��"};
	public static final String[] tableTitles2={"�Żݽ��","�̼ұ��","����Ҫ��","�Ѷ�����"};
	private int yhmoney;
	private int jdnum;
	private Date startdate;
	private Date enddate;
	private int yhno;
	private String sjno;
	private int ddno;
	private int already;
	public int getyhmoney() {
		return yhmoney;
	}
	public void setyhmoney(int yhmoney) {
		this.yhmoney = yhmoney;
	}
	public int getjdnum() {
		return jdnum;
	}
	public void setjdnum(int jdnum) {
		this.jdnum = jdnum;
	}
	public Date getstartdate() {
		return startdate;
	}
	public void setstartdate(Date startdate) {
		this.startdate = startdate;
	}public Date getenddate() {
		return enddate;
	}
	public void setenddate(Date enddate) {
		this.enddate = enddate;
	}
	public int getyhno() {
		return yhno;
	}
	public void setyhno(int yhno) {
		this.yhno = yhno;
	}
	public void setsjno(String sjno) {
		this.sjno = sjno;
	}
	public String getsjno() {
		return sjno;
	}
	public int getddno() {
		return ddno;
	}
	public void setddno(int ddno) {
		this.ddno = ddno;
	}
	public int getalready() {
		return already;
	}
	public void setalready(int already) {
		this.already = already;
	}
	public String getCell(int col){
		if (col == 0)
			return this.yhmoney+"";
		else if (col == 1)
			return this.sjno;
		else if (col == 2)
			return this.ddno+"";
		else
		    return "";
	}
	public String getCell1(int col){
		if (col == 0)
			return this.yhmoney+"";
		else if (col == 1)
			return this.startdate+"";
		else if (col == 2)
			return this.enddate+"";
		else if (col == 3)
			return this.sjno;
		else
		    return "";
	}
	public String getCell2(int col){
		if (col == 0)
			return this.yhmoney+"";
		else if (col == 1)
			return this.sjno;
		else if (col == 2)
			return this.jdnum+"";
		else if (col == 3)
			return this.already+"";
		else
		    return "";
	}
}
