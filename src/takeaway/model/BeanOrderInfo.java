package takeaway.model;

public class BeanOrderInfo {
	public static final String[] tblOrderInfoTitle={"商品名","订单编号","商品数量","商品价格","单品优惠"};
	private int spno;
	private String spname;
	private int ddno;
	private int num;
	private Float price;
	private Float discount;
	public int getspno() {
		return spno;
	}
	public void setspno(int spno) {
		this.spno = spno;
	}
	public String getspname() {
		return spname;
	}
	public void setspname(String spname) {
		this.spname = spname;
	}
	public int getddno() {
		return ddno;
	}
	public void setddno(int ddno) {
		this.ddno = ddno;
	}
	public int getnum() {
		return num;
	}
	public void setnum(int num) {
		this.num = num;
	}
	public Float getprice() {
		return price;
	}
	public void setprice(Float price) {
		this.price = price;
	}
	public Float getdiscount() {
		return discount;
	}
	public void setdiscount(Float discount) {
		this.discount = discount;
	}
	public String getCell(int col){
		if (col == 0)
			return this.spname;
		else if (col == 1)
		    return this.ddno + "";
		else if (col == 2)
		    return this.num + "";
		else if (col == 3)
		    return this.price + "";
		else if (col == 4)
		    return this.discount + "";
		else
		    return "";
	}
}
