package takeaway.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import takeaway.itf.IRiderManager;
import takeaway.model.BeanRider;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class RiderManager implements IRiderManager {
	@Override
	public BeanRider addRider(String name) throws BaseException {
		// TODO Auto-generated method stub
		if(name.isEmpty()) 
			throw new BusinessException("姓名为空");
		BeanRider rider=new BeanRider();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from qs_info where qs_no = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("您已是骑手，请点击“已是骑手”");
			rs.close();
			pst.close();
			sql = "insert into qs_info(qs_name,qs_no,qs_date,qs_id) values(?,?,?,'新人')";
			pst = conn.prepareStatement(sql);
			pst.setString(1,name);
			pst.setString(2,userno);
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
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
		return rider;
	}

	@Override
	public BeanRider login() throws BaseException {
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select qs_no,qs_id,qs_date from qs_info where qs_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("您还不是骑手");
			BeanRider rider=new BeanRider();
			rider.setqsno(rs.getString(1));
			rider.setqsid(rs.getString(2));
			rider.setqsdate(rs.getDate(3));
			rs.close();
			pst.close();
			return rider;
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
	public void updateinfo(String name) throws BaseException {
		// TODO Auto-generated method stub
		if(name.isEmpty())
			throw new BaseException("骑手名为空");
		Connection conn = null;
	    String qsno = BeanRider.currentLoginrider.getqsno();
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "update qs_info set qs_name=? where qs_no=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, name);
	    	pst.setString(2, qsno);
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
	public List<BeanRider> loadbyrider()throws BaseException{
		List<BeanRider> result=new ArrayList<BeanRider>();
		
		Calendar calendar1 = new GregorianCalendar();
		Date date=new Date(calendar1.getTimeInMillis());
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, 1);
        Date now = new Date(calendar.getTimeInMillis());
        
		String qsno=BeanRider.currentLoginrider.getqsno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select qs_date from qs_info where qs_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, qsno);
			java.sql.ResultSet rs=pst.executeQuery();
			if (rs.next()) {
		        java.util.Date date1 = rs.getDate(1);
		        calendar1.setTime(date1);
		        calendar1.add(Calendar.MONTH, 3);
		        date=new Date(calendar1.getTimeInMillis());
		    }
			rs.close();
		    pst.close();
		    if(date.before(now)){
		    	sql = "update qs_info set qs_id='正式员工' where qs_no=?";
		    	pst = conn.prepareStatement(sql);
				pst.setString(1, qsno);
				pst.execute();
		    	pst.close();
		    }
			sql = "select qs_no,qs_name,qs_date,qs_id from qs_info where qs_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, qsno);
			rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanRider p=new BeanRider();
		        p.setqsno(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setqsdate(rs.getDate(3));
		        p.setqsid(rs.getString(4));
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
	public List<BeanRider> loadAll() throws BaseException {
		List<BeanRider> result=new ArrayList<BeanRider>();
		
		Calendar calendar1 = new GregorianCalendar();
		Date date=new Date(calendar1.getTimeInMillis());
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, 1);
        Date now = new Date(calendar.getTimeInMillis());
        
        String qsno=null;
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select qs_date,qs_no from qs_info where qs_no!='0' group by qs_no";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			while (rs.next()) {
		        java.util.Date date1 = rs.getDate(1);
		        calendar1.setTime(date1);
		        calendar1.add(Calendar.MONTH, 3);
		        date=new Date(calendar1.getTimeInMillis());
		        qsno=rs.getString(2);
		        if(date.before(now)){
		        	String sql1 = "update qs_info set qs_id='正式员工' where qs_no=?";
		        	java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
		        	pst1.setString(1, qsno);
		        	pst1.execute();
		        	pst1.close();
		        }
		    }
			rs.close();
		    pst.close();
		    sql = "select qs_no,qs_name,qs_date,qs_id from qs_info where qs_no!='0' order by qs_date desc";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanRider p=new BeanRider();
		        p.setqsno(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setqsdate(rs.getDate(3));
		        p.setqsid(rs.getString(4));
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
	public List<BeanRider> searchrider(String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("账号为空");
		List<BeanRider> result=new ArrayList<BeanRider>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select qs_no from qs_info where qs_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,name);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("骑手不存在");
			rs.close();
			pst.close();
			sql = "select qs_no,qs_name,qs_date,qs_id from qs_info where qs_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanRider p=new BeanRider();
		        p.setqsno(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setqsdate(rs.getDate(3));
		        p.setqsid(rs.getString(4));
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
	public void changeid(String rider,String id) throws BaseException{
		if(rider.isEmpty())
			throw new BaseException("账号为空");
		Connection conn = null;
	    try {
	    	conn = DBUtil.getConnection();
	    	String sql = "update qs_info set qs_id=? where qs_no=?";
	    	java.sql.PreparedStatement pst = conn.prepareStatement(sql);
	    	pst.setString(1, id);
	    	pst.setString(2, rider);
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
	@Override
	public void deleteRider(BeanRider Rider) throws BaseException {
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from dd_info\r\n" + 
		    		"where dd_no in(\r\n" + 
		    		"select dd_no\r\n" + 
		    		"from sp_dd\r\n" + 
		    		"where qs_no=?)";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, Rider.getqsno());
		    pst.executeUpdate();
		    pst.close();
		    sql = "delete from yh_use\r\n" + 
		    		"where dd_no in(\r\n" + 
		    		"select dd_no\r\n" + 
		    		"from sp_dd\r\n" + 
		    		"where qs_no=?)";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, Rider.getqsno());
		    pst.executeUpdate();
		    pst.close();
		    sql = "delete from yh_info\r\n" + 
		    		"where dd_no in(\r\n" + 
		    		"select dd_no\r\n" + 
		    		"from sp_dd\r\n" + 
		    		"where qs_no=?)";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, Rider.getqsno());
		    pst.executeUpdate();
		    pst.close();
		    sql = "delete from sp_dd\r\n" + 
		    		"where qs_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, Rider.getqsno());
		    pst.executeUpdate();
		    pst.close();
		    sql = "delete from qs_info\r\n" + 
		    		"where qs_no=1";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, Rider.getqsno());
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
}
