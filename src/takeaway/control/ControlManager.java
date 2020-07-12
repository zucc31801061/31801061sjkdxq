package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;

import takeaway.itf.IControlManager;
import takeaway.model.BeanControl;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class ControlManager implements IControlManager{
	@Override
	public BeanControl reg(String userid, String pwd,String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		BeanControl control = new BeanControl();
		if(userid.isEmpty())
			throw new BaseException("�û���Ϊ��");
		if(!pwd.equals(pwd2)||pwd.isEmpty()||pwd2.isEmpty())
			throw new BaseException("��������ͬ������");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from gly_info where yg_no = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,control.getygno());
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("�û��Ѵ���");
			rs.close();
			pst.close();
			sql="insert into gly_info(yg_no,yg_mm) values(?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
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
		return control;
	}
	@Override
	public BeanControl login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		if(userid.isEmpty())
			throw new BaseException("�û���Ϊ��");
		if(pwd.isEmpty())
			throw new BaseException("����Ϊ��");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select yg_no,yg_mm from gly_info where yg_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("��½�˺Ų�����");
			BeanControl control=new BeanControl();
			control.setygno(rs.getString(1));
			control.setPwd(rs.getString(2));
			rs.close();
			pst.close();
			return control;
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
	public void changePwd(BeanControl control, String oldPwd, String newPwd,
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
			String sql="select yg_mm from gly_info where yg_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,control.getygno());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("�û�������");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("ԭ�������");
			rs.close();
			pst.close();
			sql="update gly_info set yg_mm=? where yg_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1, newPwd);
			pst.setString(2, control.getygno());
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
	public BeanControl SearchInfo() throws BaseException {
		String ygno = BeanControl.currentLoginControl.getygno();
    	BeanControl p=new BeanControl();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yg_no,yg_name from gly_info where yg_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, ygno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		        p.setygno(rs.getString(1));
		        p.setygname(rs.getString(2));
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
		return p;
	}
	public void updateinfo(String name) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
	    String ygno = BeanControl.currentLoginControl.getygno();
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "update gly_info set yg_name=? where yg_no=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, name);
	    	pst.setString(2, ygno);
	    	pst.execute();
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
}
