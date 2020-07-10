package takeaway.model;

public class BeanKind {
	public static final String[] tableTitles={"分类编号","类别","所含商品数"};
	private int flno;
	private String flname;
	private int num;
	public int getflno() {
		return flno;
	}
	public void setflno(int flno) {
		this.flno = flno;
	}
	public String getflname() {
		return flname;
	}
	public void setflname(String flname) {
		this.flname = flname;
	}
	public int getnum() {
		return num;
	}
	public void setnum(int num) {
		this.num = num;
	}
	public String getCell(int col){
		if (col == 0)
			return this.flno+"";
		else if (col == 1)
			return flname;
		else if (col == 2)
			return this.num+"";
		else
		    return "";
	}
}
