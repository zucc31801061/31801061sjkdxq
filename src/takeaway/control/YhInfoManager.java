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
import takeaway.model.BeanOrder;
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
			String sql = "select yh_money,sj_name,yh_use.dd_no\r\n" + 
					"from yh_info,sj_info,yh_use,sp_dd\r\n" + 
					"where yh_info.yh_no=yh_use.yh_no\r\n" + 
					"and yh_info.sj_no=sj_info.sj_no\r\n" + 
					"and sp_dd.dd_no=yh_use.dd_no\r\n" + 
					"and sp_dd.user_no=?";
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
		
		Calendar calendar = new GregorianCalendar();
		Date date=new Date(calendar.getTimeInMillis());
		
		Calendar calendar1 = Calendar.getInstance();
        Date now = new Date(calendar1.getTimeInMillis());
		
		String userno = BeanUser.currentLoginUser.getUserid();
		int yhno=0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select endtime,own.yh_no\r\n" + 
					"from own,yh_info\r\n" + 
					"where user_no=?\r\n" + 
					"and yh_info.yh_no=own.yh_no\r\n" + 
					"and jd_num=already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	java.util.Date date1 = rs.getDate(1);
		        calendar.setTime(date1);
		        date=new Date(calendar.getTimeInMillis());
		        yhno=rs.getInt(2);
		        if(date.before(now)) {
		        	String sql1 = "delete from own where yh_no=?";
		        	java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
		        	pst1.setInt(1, yhno);
		        	pst1.execute();
		        	pst1.close();
		        }
		    }
		    rs.close();
		    pst.close();
		    
			sql = "select yh_money,yh_startdate,yh_enddate,sj_name,num\r\n" + 
					"from yh_info,sj_info,own\r\n" + 
					"where yh_info.sj_no=sj_info.sj_no\r\n" + 
					"and yh_info.yh_no=own.yh_no\r\n" + 
					"and jd_num=already\r\n" + 
					"and own.user_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYhInfo p=new BeanYhInfo();
		        p.setyhmoney(rs.getInt(1));
		        p.setstartdate(rs.getDate(2));
		        p.setenddate(rs.getDate(3));
		        p.setsjname(rs.getString(4));
		        p.setnum(rs.getInt(5));
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
		
		Calendar calendar = new GregorianCalendar();
		Date date=new Date(calendar.getTimeInMillis());
		
		Calendar calendar1 = Calendar.getInstance();
        Date now = new Date(calendar1.getTimeInMillis());
		
		String userno = BeanUser.currentLoginUser.getUserid();
		int yhno=0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select endtime,own.yh_no\r\n" + 
					"from own,yh_info\r\n" + 
					"where user_no=?\r\n" + 
					"and yh_info.yh_no=own.yh_no\r\n" + 
					"and jd_num>already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	java.util.Date date1 = rs.getDate(1);
		        calendar.setTime(date1);
		        date=new Date(calendar.getTimeInMillis());
		        yhno=rs.getInt(2);
		        if(date.before(now)) {
		        	String sql1 = "delete from own where yh_no=?";
		        	java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
		        	pst1.setInt(1, yhno);
		        	pst1.execute();
		        	pst1.close();
		        }
		    }
		    rs.close();
		    pst.close();
			
			sql = "select yh_money,sj_name,jd_num,already\r\n" + 
					"from yh_info,sj_info,own\r\n" + 
					"where yh_info.sj_no=sj_info.sj_no\r\n" + 
					"and yh_info.yh_no=own.yh_no\r\n" + 
					"and jd_num>already\r\n" + 
					"and own.user_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    rs = pst.executeQuery();
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
		
		Calendar calendar = new GregorianCalendar();
		Date date=new Date(calendar.getTimeInMillis());
		
		Calendar calendar1 = Calendar.getInstance();
        Date now = new Date(calendar1.getTimeInMillis());
		
		String sjno=BeanStore.currentLoginstore.getsjno();
		int yhno=0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select yh_enddate,yh_no\r\n" + 
					"from yh_info\r\n" + 
					"where sj_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	java.util.Date date1 = rs.getDate(1);
		        calendar.setTime(date1);
		        date=new Date(calendar.getTimeInMillis());
		        yhno=rs.getInt(2);
		        if(date.before(now)) {
		        	String sql1 = "delete from yh_use where yh_no=?";
		        	java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
		        	pst1.setInt(1, yhno);
		        	pst1.execute();
		        	pst1.close();
		        	sql1 = "delete from own where yh_no=?";
		        	pst1 = conn.prepareStatement(sql1);
		        	pst1.setInt(1, yhno);
		        	pst1.execute();
		        	pst1.close();
		        	sql1 = "delete from yh_info where yh_no=?";
		        	pst1 = conn.prepareStatement(sql1);
		        	pst1.setInt(1, yhno);
		        	pst1.execute();
		        	pst1.close();
		        }
		    }
		    rs.close();
		    pst.close();
			sql = "select yh_no,jd_num,yh_startdate,yh_enddate,yh_money from yh_info where sj_no=? order by yh_no";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    rs = pst.executeQuery();
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
			sql = "select user_no\r\n" + 
					"from user_info\r\n" + 
					"where user_no=0";
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			if(!rs.next()) {
				String sql1 = "insert into user_info(user_no) values(0)";
				java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
				pst1.execute();
				pst1.close();
			}
			pst.execute();
			pst.close();
			sql = "select qs_no\r\n" + 
					"from qs_info\r\n" + 
					"where qs_no=0";
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			if(!rs.next()) {
				String sql1 = "insert into qs_info(qs_no,qs_name) values(0,'µ»¥˝≈‰ÀÕ')";
				java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
				pst1.execute();
				pst1.close();
			}
			pst.execute();
			pst.close();
			sql = "select dd_no\r\n" + 
					"from sp_dd\r\n" + 
					"where dd_no=0";
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			if(!rs.next()) {
				String sql1 = "insert into sp_dd(qs_no,dd_no,user_no) values(0,0,0)";
				java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
				pst1.execute();
				pst1.close();
			}
			pst.execute();
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
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from yh_use where yh_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, yh.getyhno());
		    pst.execute();
		    pst.close();
		    sql = "delete from own where yh_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, yh.getyhno());
		    pst.execute();
		    pst.close();
		    sql = "delete from yh_info where yh_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, yh.getyhno());
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
	public void Updyh(BeanYhInfo yh,int yhmoney,int jdyq,Date start,Date end)throws BaseException{
		java.sql.Date start1 = new java.sql.Date(start.getTime());
		java.sql.Date end1 = new java.sql.Date(end.getTime());
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update yh_info\r\n" + 
		    		"set yh_money=?,jd_num=?,yh_startdate=?,yh_enddate=?\r\n" + 
		    		"where yh_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, yhmoney);
		    pst.setInt(2, jdyq);
		    pst.setDate(3, start1);
		    pst.setDate(4, end1);
		    pst.setInt(5, yh.getyhno());
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
	public List<BeanYhInfo> loadnotusebystore()throws BaseException{
		List<BeanYhInfo> result=new ArrayList<BeanYhInfo>();
		
		Calendar calendar = new GregorianCalendar();
		Date date=new Date(calendar.getTimeInMillis());
		
		Calendar calendar1 = Calendar.getInstance();
        Date now = new Date(calendar1.getTimeInMillis());
		
		String userno = BeanUser.currentLoginUser.getUserid();
		int ddno=BeanOrder.currentLoginOrder.getddno();
		int yhno=0;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select endtime,own.yh_no\r\n" + 
					"from own,yh_info\r\n" + 
					"where user_no=?\r\n" + 
					"and yh_info.yh_no=own.yh_no\r\n" + 
					"and jd_num=already";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	java.util.Date date1 = rs.getDate(1);
		        calendar.setTime(date1);
		        date=new Date(calendar.getTimeInMillis());
		        yhno=rs.getInt(2);
		        if(date.before(now)) {
		        	String sql1 = "delete from own where yh_no=?";
		        	java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
		        	pst1.setInt(1, yhno);
		        	pst1.execute();
		        	pst1.close();
		        }
		    }
		    rs.close();
		    pst.close();
			sql = "select yh_info.yh_no,yh_money,num,endtime,sj_name\r\n" + 
					"from own,yh_info,sp_dd,sj_info\r\n" + 
					"where already=jd_num\r\n" + 
					"and own.yh_no=yh_info.yh_no\r\n" + 
					"and own.user_no=?\r\n" + 
					"and yh_info.sj_no\r\n" + 
					"and sp_dd.sj_no=yh_info.sj_no\r\n" + 
					"and sp_dd.sj_no=sj_info.sj_no\r\n" + 
					"and sp_dd.dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    pst.setInt(2, ddno);
		    rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanYhInfo p=new BeanYhInfo();
		        p.setyhno(rs.getInt(1));
		        p.setyhmoney(rs.getInt(2));
		        p.setnum(rs.getInt(3));
		        p.setenddate(rs.getDate(4));
		        p.setsjname(rs.getString(5));
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
