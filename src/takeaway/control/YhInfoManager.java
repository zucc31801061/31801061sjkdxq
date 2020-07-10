package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import takeaway.itf.IYhInfoManager;
import takeaway.model.BeanYhInfo;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class YhInfoManager implements IYhInfoManager{
	public List<BeanYhInfo> loadused()throws BaseException{
		List<BeanYhInfo> result=new ArrayList<BeanYhInfo>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_money,sj_name,dd_no from yh_info,sj_info where yh_info.sj_no=sj_info.sj_no and yh_no in(select yh_no from own where user_no=?) and dd_no!=0 and jd_num=already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYhInfo p=new BeanYhInfo();
		        p.setyhmoney(rs.getInt(1));
		        p.setsjname(rs.getString(2));
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
	public List<BeanYhInfo> loadnotused()throws BaseException{
		List<BeanYhInfo> result=new ArrayList<BeanYhInfo>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_money,yh_startdate,yh_enddate,sj_name from yh_info,sj_info where yh_info.sj_no=sj_info.sj_no and yh_no in(select yh_no from own where user_no=?) and dd_no=0 and jd_num=already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYhInfo p=new BeanYhInfo();
		        p.setyhmoney(rs.getInt(1));
		        p.setstartdate(rs.getDate(2));
		        p.setenddate(rs.getDate(3));
		        p.setsjname(rs.getString(4));
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
	public List<BeanYhInfo> loadnothave()throws BaseException{
		List<BeanYhInfo> result=new ArrayList<BeanYhInfo>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_money,sj_name,jd_num,already from yh_info,sj_info where yh_info.sj_no=sj_info.sj_no and yh_no in(select yh_no from own where user_no=?) and dd_no=0 and jd_num>already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYhInfo p=new BeanYhInfo();
		        p.setyhmoney(rs.getInt(1));
		        p.setsjname(rs.getString(2));
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
	public List<BeanYhInfo> loadbystore()throws BaseException{
		List<BeanYhInfo> result=new ArrayList<BeanYhInfo>();
		String sjno=BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_no,jd_num,yh_startdate,yh_enddate,yh_money from yh_info where sj_no=? order by yh_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYhInfo p=new BeanYhInfo();
		        p.setyhno(rs.getInt(1));
		        p.setjdnum(rs.getInt(2));
		        p.setstartdate(rs.getDate(3));
		        p.setenddate(rs.getDate(4));
		        p.setyhmoney(rs.getInt(5));
		        p.setsjno(sjno);
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
	public BeanYhInfo Addyh(int yhmoney,int jdyq,Date start,Date end)throws BaseException{
		String sjno = BeanStore.currentLoginstore.getsjno();
		BeanYhInfo method=new BeanYhInfo();
		int yhno;
		java.sql.Date start1 = new java.sql.Date(start.getTime());
		java.sql.Date end1 = new java.sql.Date(end.getTime());
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select yh_no from yh_info where sj_no=? order by yh_no desc limit 0,1";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		    	yhno=rs.getInt(1) + 1;
		    } 
		    else {
		    	yhno=1;
		    }
		    rs.close();
			pst.close();
		    sql = "insert into yh_info(yh_no,sj_no,jd_num,yh_startdate,yh_enddate,yh_money,dd_no) values(?,?,?,?,?,?,0)";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, yhno);
		    pst.setString(2, sjno);
		    pst.setInt(3, jdyq);
		    pst.setDate(4, start1);
		    pst.setDate(5, end1);
		    pst.setInt(6, yhmoney);
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
		return method;
	}
	public void Delyh(BeanYhInfo yh)throws BaseException{
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from yh_info where sj_no=? and yh_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanStore.currentLoginstore.getsjno());
		    pst.setInt(2, yh.getyhno());
		    pst.executeUpdate();
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
