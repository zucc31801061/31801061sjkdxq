package takeaway.model;

public class BeanControl {
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
}
