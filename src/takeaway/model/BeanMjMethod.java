package takeaway.model;

public class BeanMjMethod {
	public static final String[] tblMjTitle={"�������","�������","�����Ż�","�Ƿ�����Ż�ȯ"};
	private int mjno;
	private String sjno;
	private Float mjmoney;
	private Float mjyh;
	private boolean dj;
	public int getmjno() {
		return mjno;
	}
	public void setmjno(int mjno) {
	    this.mjno = mjno;
	}
	public String getsjno() {
	    return sjno;
	}
	public void setsjno(String sjno) {
	    this.sjno = sjno;
	}
	public Float getmjmoney() {
	    return mjmoney;
	}
	public void setmjmoney(Float mjmoney) {
	    this.mjmoney = mjmoney;
	}
	public Float getmjyh() {
	    return mjyh;
	}
	public void setmjyh(Float mjyh) {
	    this.mjyh = mjyh;
	}
	public boolean getdj() {
	    return dj;
	}
	public void setdj(boolean dj) {
	    this.dj = dj;
	}
	public String getCell(int col){
		if (col == 0)
			return this.mjno+"";
		else if (col == 1)
		    return this.mjmoney+"";
		else if (col == 2)
		    return this.mjyh+"";
		else if (col == 3) {
			if(this.dj == true) {
				return "�ɵ���";
			}
			else {
				return "���ɵ���";
			}
		}
		else
		    return "";
	}
}
