package takeaway.model;

public class BeanControl {
	public static final String[] tableTitles={"’À∫≈","–’√˚"};
	private String ygno;
	private String pwd;
	private String ygname;
	public String getygno() {
		return ygno;
	}
	public void setygno(String ygno) {
		this.ygno = ygno;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getygname() {
		return ygname;
	}
	public void setygname(String ygname) {
		this.ygname = ygname;
	}
	public static BeanControl currentLoginControl=null;
	public String getCell(int col){
		if (col == 0)
			return this.ygno;
		else if (col == 1)
			return this.ygname;
		    return "";
	}
}
