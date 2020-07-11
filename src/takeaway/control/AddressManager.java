package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IAddressManager;
import takeaway.model.BeanAddress;
import takeaway.model.BeanOrder;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class AddressManager implements IAddressManager{
	@Override
	public BeanAddress addAddress(String sheng,String shi,String qu,String address,String name,String phnum) throws BaseException {
		// TODO Auto-generated method stub
		if(sheng.isEmpty()||shi.isEmpty()||qu.isEmpty()||address.isEmpty()||name.isEmpty()||phnum.isEmpty())
			throw new BusinessException("请填写完整的地址信息");
		BeanAddress add=new BeanAddress();
		String userno = BeanUser.currentLoginUser.getUserid();
		int addno = 1;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select add_no from add_info order by add_no desc limit 0,1";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if (rs.next()) {
				addno = rs.getInt(1) + 1;
		    } 
		    else {
		    	addno = 1;
		    }
			rs.close();
			pst.close();
			sql = "insert into add_info(user_no,sheng,shi,qu,address,user_name,user_phnum,add_no) values(?,?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1,userno);
			pst.setString(2,sheng);
			pst.setString(3,shi);
			pst.setString(4,qu);
			pst.setString(5,address);
			pst.setString(6,name);
			pst.setString(7,phnum);
			pst.setInt(8, addno);
			pst.execute();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return add;
	}

	@Override
	public List<BeanAddress> loadAll() throws BaseException {
		List<BeanAddress> result=new ArrayList<BeanAddress>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sheng,shi,qu,address,user_name,user_phnum,add_no from add_info where user_no=? order by add_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanAddress p=new BeanAddress();
		        p.setsheng(rs.getString(1));
		        p.setshi(rs.getString(2));
		        p.setqu(rs.getString(3));
		        p.setaddress(rs.getString(4));
		        p.setusername(rs.getString(5));
		        p.setuserphnum(rs.getString(6));
		        p.setaddno(rs.getInt(7));
		        result.add(p);
		    }
		    rs.close();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		    throw new DbException(e);
		} 
		finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		        	e.printStackTrace();
		        }
		}
		return result;
	}

	@Override
	public void deleteAddress(BeanAddress address) throws BaseException {
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from add_info where user_no=? and add_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    pst.setInt(2, address.getaddno());
		    pst.executeUpdate();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	public List<BeanAddress> loadselect(BeanOrder order)throws BaseException{
		List<BeanAddress> result=new ArrayList<BeanAddress>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sheng,shi,qu,address,user_name,user_phnum\r\n" + 
					"from add_info,sp_dd\r\n" + 
					"where sp_dd.dd_no=? and sp_dd.add_no=add_info.add_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, order.getaddno());
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanAddress p=new BeanAddress();
		        p.setsheng(rs.getString(1));
		        p.setshi(rs.getString(2));
		        p.setqu(rs.getString(3));
		        p.setaddress(rs.getString(4));
		        p.setusername(rs.getString(5));
		        p.setuserphnum(rs.getString(6));
		        result.add(p);
		    }
		    rs.close();
		    pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		    throw new DbException(e);
		} 
		finally {
		    if (conn != null)
		    	try {
		    		conn.close();
		        } catch (SQLException e) {
		        	e.printStackTrace();
		        }
		}
		return result;
	}
}
