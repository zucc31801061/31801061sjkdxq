package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import takeaway.itf.IYhManager;
import takeaway.model.BeanUser;
import takeaway.model.BeanYh;
import takeaway.util.BaseException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class YhManager implements IYhManager{
	public List<BeanYh> loadused()throws BaseException{
		List<BeanYh> result=new ArrayList<BeanYh>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_money,sj_no,dd_no from yh_info where user_no=? and dd_no!=0 and jdnum=already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYh p=new BeanYh();
		        p.setyhmoney(rs.getInt(1));
		        p.setsjno(rs.getString(2));
		        p.setddno(rs.getInt(3));
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
	public List<BeanYh> loadnotused()throws BaseException{
		List<BeanYh> result=new ArrayList<BeanYh>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_money,yh_startdate,yh_enddate,sj_no from yh_info where user_no=? and dd_no=0 and jdnum=already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYh p=new BeanYh();
		        p.setyhmoney(rs.getInt(1));
		        p.setstartdate(rs.getDate(2));
		        p.setenddate(rs.getDate(3));
		        p.setsjno(rs.getString(4));
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
	public List<BeanYh> loadnothave()throws BaseException{
		List<BeanYh> result=new ArrayList<BeanYh>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_money,sj_no,jd_num,already from yh_info where user_no=? and dd_no=0 and jdnum>already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYh p=new BeanYh();
		        p.setyhmoney(rs.getInt(1));
		        p.setsjno(rs.getString(2));
		        p.setjdnum(rs.getInt(3));
		        p.setalready(rs.getInt(4));
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
