package takeaway.model;

import java.util.Date;

public class BeanStore {
	public static final String[] tableTitles={"�̼�","�Ǽ�","�˾�����","������"};
	/**
	 * �����и���javabean������޸ı��������룬col��ʾ�������е�����ţ�0��ʼ
	 */
	private String sjname;
	private int sjstar;
	private Float sjavgxf;
	private Float sjsumxl;
	private String sjno;
	public int getsjstar() {
		return sjstar;
	}
	public void setsjstar(int sjstar) {
		this.sjstar = sjstar;
	}
	public String getsjname() {
		return sjname;
	}
	public void setsjname(String sjname) {
		this.sjname = sjname;
	}
	public Float getsjavgxf() {
		return sjavgxf;
	}
	public void setsjavgxf(Float sjavgxf) {
		this.sjavgxf = sjavgxf;
	}
	public Float getsjsumxl() {
		return sjsumxl;
	}
	public void setsjsumxl(Float sjsumxl) {
		this.sjsumxl = sjsumxl;
	}
	public String getsjno() {
		return sjno;
	}
	public void setsjno(String sjno) {
		this.sjno = sjno;
	}
	public String getCell(int col){
		if (col == 0)
			return this.sjname;
		else if (col == 1)
			return (this.sjstar + "");
		else if (col == 2)
			return (this.sjavgxf + "");
		else if (col == 3)
			return (this.sjsumxl + "");
		else
		    return "";
	}

}
