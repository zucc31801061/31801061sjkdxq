package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IUserManager;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class UserManager implements IUserManager {
	@Override
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		BeanUser user = new BeanUser();
		if(userid.isEmpty())
			throw new BusinessException("�û���Ϊ��");
		if(!pwd.equals(pwd2)||pwd.isEmpty()||pwd2.isEmpty())
			throw new BusinessException("��������ͬ������");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from user_info where user_no = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("�û��Ѵ���");
			rs.close();
			pst.close();
			sql="insert into user_info(user_no,user_mm,user_starttime) values(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			pst.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
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
		return user;
	}
	
	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		if(userid.isEmpty())
			throw new BaseException("�û���Ϊ��");
		if(pwd.isEmpty())
			throw new BaseException("����Ϊ��");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_no,user_mm from user_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų�����");
			BeanUser user=new BeanUser();
			user.setUserid(rs.getString(1));
			user.setPwd(rs.getString(2));
			rs.close();
			pst.close();
			return user;
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


	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		if(oldPwd.isEmpty())
			throw new BusinessException("ԭ����Ϊ��");
		if(!newPwd.equals(newPwd2)||newPwd.isEmpty()||newPwd2.isEmpty())
			throw new BaseException("��������ͬ������");
		if(newPwd.equals(oldPwd))
			throw new BaseException("��������ԭ������ͬ");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_mm from user_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�û�������");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("ԭ�������");
			rs.close();
			pst.close();
			sql="update user_info set user_mm=? where user_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, user.getUserid());
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
	}
	
	@Override
	public void updateinfo(String name,String sex,String phnum,String email,String city) throws BaseException {
		// TODO Auto-generated method stub
		if(!sex.equals("��")&&!sex.equals("Ů"))
			throw new BaseException("�����Ա������롰�С���Ů��");
		Connection conn = null;
	    String userid = BeanUser.currentLoginUser.getUserid();
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "update user_info set user_name=?,user_sex=?,user_phnum=?,user_email=?,user_city=? where user_no=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, name);
	    	pst.setString(2, sex);
	    	pst.setString(3, phnum);
	    	pst.setString(4, email);
	    	pst.setString(5, city);
	    	pst.setString(6, userid);
	    	pst.close();
	    	} catch (SQLException e) {
	    		e.printStackTrace();
	    		throw new DbException(e);
	    	} finally {
	    		if (conn != null)
	    			try {
	    				conn.close();
	    			} catch (SQLException e) {
	    				e.printStackTrace();
	    			}
	    	}
	}
	
	/*public String getinfo() throws BaseException {
		Connection conn = null;
		String userid = BeanUser.currentLoginUser.getUserid();
		String name=null,sex=null,phnum=null,email=null,city=null;
		String result;
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,user_sex,user_phnum,user_email,user_city from user_info where user_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userid);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		        name = rs.getString(1);
		        sex = rs.getString(2);
		        phnum = rs.getString(3);
		        email = rs.getString(4);
		        city = rs.getString(5);
		    }
		    result ="ԭ�û�����"+name+"\nԭ�Ա�"+sex+"\nԭ�绰�ţ�"+phnum+"\nԭ�����ַ��"+email+"\nԭ���У�"+city;
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
	}*/

}
