package takeaway.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
			throw new BusinessException("用户名为空");
		if(!pwd.equals(pwd2)||pwd.isEmpty()||pwd2.isEmpty())
			throw new BusinessException("请输入相同的密码");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from user_info where user_no = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("用户已存在");
			rs.close();
			pst.close();
			sql="insert into user_info(user_no,user_mm,user_starttime,vip) values(?,?,?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			pst.setString(2,pwd);
			pst.setTimestamp(3,new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setBoolean(4, false);
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
			throw new BaseException("用户名为空");
		if(pwd.isEmpty())
			throw new BaseException("密码为空");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_no,user_mm,vip from user_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userid);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("登陆账号不存在");
			BeanUser user=new BeanUser();
			user.setUserid(rs.getString(1));
			user.setPwd(rs.getString(2));
			user.setvip(rs.getBoolean(3));
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
			throw new BusinessException("原密码为空");
		if(!newPwd.equals(newPwd2)||newPwd.isEmpty()||newPwd2.isEmpty())
			throw new BaseException("请输入相同的密码");
		if(newPwd.equals(oldPwd))
			throw new BaseException("新密码与原密码相同");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_mm from user_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户不存在");
			if(!oldPwd.equals(rs.getString(1))) throw new BusinessException("原密码错误");
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
		if(!sex.equals("男")&&!sex.equals("女"))
			throw new BaseException("请在性别栏输入“男”或“女”");
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
	public BeanUser SearchInfo() throws BaseException {
		String userno = BeanUser.currentLoginUser.getUserid();
    	BeanUser p=new BeanUser();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,user_sex,user_phnum,user_email,user_city from user_info where user_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		        p.setusername(rs.getString(1));
		        p.setusersex(rs.getString(2));
		        p.setphnum(rs.getString(3));
		        p.setemail(rs.getString(4));
		        p.setcity(rs.getString(5));
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
	@Override
	public BeanUser SearchVIP() throws BaseException{
		String userno = BeanUser.currentLoginUser.getUserid();
    	BeanUser p=new BeanUser();
    	
    	Calendar calendar1 = new GregorianCalendar();
		Date date=new Date(calendar1.getTimeInMillis());
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, 1);
        Date now = new Date(calendar.getTimeInMillis());
        
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select vip_enddate from user_info where user_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userno);
			java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		        java.util.Date date1 = rs.getDate(1);
		        calendar1.setTime(date1);
		        date=new Date(calendar1.getTimeInMillis());
		    }
		    rs.close();
		    pst.close();
		    sql = "update user_info set vip=? where user_no=?";
		    pst = conn.prepareStatement(sql);
		    if(date.before(now)) {
		    	pst.setBoolean(1, false);
		    }
		    else {
		    	pst.setBoolean(1, true);
		    }
		    pst.setString(2, userno);
		    pst.execute();
		    pst.close();
			sql = "select vip,vip_enddate from user_info where user_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    rs = pst.executeQuery();
		    if (rs.next()) {
		        p.setvip(rs.getBoolean(1));
		        p.setvipenddate(rs.getDate(2));
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
	@Override
	public BeanUser PayVIP() throws BaseException{
		String userno = BeanUser.currentLoginUser.getUserid();
		
		Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);//time，是用户充值的月数
        Date date = new Date(calendar.getTimeInMillis());
        
		Calendar calendar1 = new GregorianCalendar();
		
		Date date2=new Date(calendar1.getTimeInMillis());
    	BeanUser p=new BeanUser();
    	
    	Calendar calendar2 = Calendar.getInstance();
        Date now = new Date(calendar2.getTimeInMillis());
        
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select vip_enddate from user_info where user_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userno);
			java.sql.ResultSet rs = pst.executeQuery();
		    if (rs.next()) {
		        java.util.Date date1 = rs.getDate(1);
		        calendar1.setTime(date1);
		        date2=new Date(calendar1.getTimeInMillis());
		    }
		    rs.close();
		    pst.close();
			sql = "update user_info set vip=TRUE,vip_enddate=? where user_no=?";
		    pst = conn.prepareStatement(sql);
		    if(date2.before(now)) {
		    	pst.setDate(1,date);
		    }
		    else {
		        calendar1.add(Calendar.MONTH, 1);
		        date2=new Date(calendar1.getTimeInMillis());
		    	pst.setDate(1,date2);
		    }
		    pst.setString(2, userno);
		    pst.execute();
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
	public List<BeanUser> loadAll()throws BaseException{
		List<BeanUser> result=new ArrayList<BeanUser>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,user_sex,user_phnum,user_email,user_city,user_starttime,vip,vip_enddate,user_no\n" + 
					"from user_info\n" + 
					"order by user_starttime DESC";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanUser p=new BeanUser();
		        p.setusername(rs.getString(1));
		        p.setusersex(rs.getString(2));
		        p.setphnum(rs.getString(3));
		        p.setemail(rs.getString(4));
		        p.setcity(rs.getString(5));
		        p.setCreateDate(rs.getDate(6));
		        p.setvip(rs.getBoolean(7));
		        p.setvipenddate(rs.getDate(8));
		        p.setUserid(rs.getString(9));
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
	public List<BeanUser> searchuser(String name)throws BaseException{
		if(name.isEmpty())
			throw new BusinessException("账号为空");
		List<BeanUser> result=new ArrayList<BeanUser>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql="select user_no from user_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,name);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户不存在");
			rs.close();
			pst.close();
			sql = "select user_name,user_sex,user_phnum,user_email,user_city,vip,vip_enddate,user_no from user_info where user_no=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, name);
			rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanUser p=new BeanUser();
		        p.setusername(rs.getString(1));
		        p.setusersex(rs.getString(2));
		        p.setphnum(rs.getString(3));
		        p.setemail(rs.getString(4));
		        p.setcity(rs.getString(5));
		        p.setvip(rs.getBoolean(6));
		        p.setvipenddate(rs.getDate(7));
		        p.setUserid(rs.getString(8));
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
	public void changevip(String userno,boolean vip,Date date)throws BaseException{
		if(userno.isEmpty())
			throw new BusinessException("账号为空");
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="select user_no from user_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(!rs.next()) throw new BusinessException("用户不存在");
			rs.close();
			pst.close();
			sql="update user_info set vip=?,vip_enddate=? where user_no=?";
			pst=conn.prepareStatement(sql);
			pst.setBoolean(1, vip);
			pst.setDate(2, date);
			pst.setString(3, userno);
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
	public void deluser(BeanUser user)throws BaseException{
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql="delete from add_info where user_no=?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			pst.execute();
			pst.close();
			sql="delete from own where user_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			pst.execute();
			pst.close();
			sql="delete\n" + 
					"from dd_info\n" + 
					"where dd_no in(\n" + 
					"select dd_no\n" + 
					"from sp_dd\n" + 
					"where user_no=?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			pst.execute();
			pst.close();
			sql="delete from yh_use\n" + 
					"where yh_no in(\n" + 
					"select yh_no\n" + 
					"from yh_info\n" + 
					"where user_no=?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			pst.execute();
			pst.close();
			sql="delete from yh_info\n" + 
					"where dd_no in(\n" + 
					"select dd_no\n" + 
					"from sp_dd\n" + 
					"where user_no=?)";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			pst.execute();
			pst.close();
			sql="delete from sp_dd where user_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
			pst.execute();
			pst.close();
			sql="delete from user_info where user_no=?";
			pst=conn.prepareStatement(sql);
			pst.setString(1,user.getUserid());
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
}
