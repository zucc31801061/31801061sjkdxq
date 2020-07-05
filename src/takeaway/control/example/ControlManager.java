package takeaway.control.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IControlManager;
import takeaway.model.BeanControl;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class ControlManager implements IControlManager {
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
	public void changePwd(BeanControl user, String oldPwd, String newPwd,
			String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
	}
}
