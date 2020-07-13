package takeaway.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import takeaway.itf.IOrderManager;
import takeaway.model.BeanOrder;
import takeaway.model.BeanRider;
import takeaway.model.BeanSppj;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;
import takeaway.util.BusinessException;
import takeaway.util.DBUtil;
import takeaway.util.DbException;

public class OrderManager implements IOrderManager{
	@Override
	public BeanOrder addOrder() throws BaseException {
		// TODO Auto-generated method stub
		String userno = BeanUser.currentLoginUser.getUserid();
		int ddno=1;
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select dd_no\r\n" + 
					"from sp_dd\r\n" + 
					"order by dd_no\r\n" + 
					"desc limit 0,1";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs=pst.executeQuery();
			if (rs.next()) {
				ddno = rs.getInt(1) + 1;
		    } 
		    else {
		    	ddno = 1;
		    }
			BeanOrder order=new BeanOrder();
			order.setddno(ddno);
			rs.close();
			pst.close();
			sql = "select qs_no\r\n" + 
					"from qs_info\r\n" + 
					"where qs_no=0";
			pst = conn.prepareStatement(sql);
			rs=pst.executeQuery();
			if(!rs.next()) {
				String sql1 = "insert into qs_info(qs_no,qs_name) values(0,'等待配送')";
				java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
				pst1.execute();
				pst1.close();
			}
			pst.execute();
			pst.close();
			sql = "insert into sp_dd(user_no,dd_no,qs_no) values(?,?,0)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userno);
			pst.setInt(2, ddno);
			pst.execute();
			pst.close();
			return order;
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
	public List<BeanOrder> loadBysj() throws BaseException {
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no,dd_starttime from sp_dd,qs_info,add_info where sj_no=? and add_info.add_no=sp_dd.add_no and qs_info.qs_no=sp_dd.qs_no order by dd_starttime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setusername(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
		        p.setddno(rs.getInt(8));
		        p.setddstarttime(rs.getTimestamp(9));
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
	public List<BeanOrder> loadByqs()throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String qsno = BeanRider.currentLoginrider.getqsno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,dd_zt,dd_starttime,dd_endtime,dd_no,add_no\r\n" + 
					"from sp_dd,sj_info\r\n" + 
					"where qs_no=?\r\n" + 
					"and sj_info.sj_no=sp_dd.sj_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setddzt(rs.getString(2));
		        p.setddstarttime(rs.getTimestamp(3));
		        p.setddendtime(rs.getTimestamp(4));
		        p.setddno(rs.getInt(5));
		        p.setaddno(rs.getInt(6));
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
	public List<BeanOrder> loaduserHisOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no from sp_dd,sj_info,qs_info where user_no=? and dd_zt=? and sp_dd.sj_no=sj_info.sj_no and sp_dd.qs_no=qs_info.qs_no order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    pst.setString(2, "已送达");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
		        p.setddno(rs.getInt(8));
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
	public List<BeanOrder> loadstoreHisOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_starttime\r\n" + 
					"from sp_dd,add_info,qs_info\r\n" + 
					"where sj_no=? and dd_zt=?\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"order by dd_starttime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    pst.setString(2, "已送达");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setqsname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setmjno(rs.getInt(5));
		        p.setyhno(rs.getInt(6));
		        p.setddstartmoney(rs.getFloat(7));
		        p.setddendmoney(rs.getFloat(8));
		        p.setddstarttime(rs.getTimestamp(9));
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
	public List<BeanOrder> loadriderHisOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String qsno=BeanRider.currentLoginrider.getqsno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,user_name,sj_name,dd_zt,dd_starttime,dd_endtime,sp_dd.add_no\r\n" + 
					"from sp_dd,add_info,sj_info\r\n" + 
					"where qs_no=? and dd_zt='已送达'\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.sj_no=sj_info.sj_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setsjname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setddstarttime(rs.getTimestamp(5));
		        p.setddendtime(rs.getTimestamp(6));
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
	public List<BeanOrder> loadstoreingOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_starttime\r\n" + 
					"from sp_dd,qs_info,user_info\r\n" + 
					"where sj_no=? and (dd_zt='已超时' or dd_zt='配送中')\r\n" + 
					"and user_info.user_no=sp_dd.user_no\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setqsname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setmjno(rs.getInt(5));
		        p.setyhno(rs.getInt(6));
		        p.setddstartmoney(rs.getFloat(7));
		        p.setddendmoney(rs.getFloat(8));
		        p.setddstarttime(rs.getTimestamp(9));
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
	public List<BeanOrder> loaduserIngOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no from sp_dd,sj_info,qs_info where user_no=? and sj_info.sj_no=sp_dd.sj_no and qs_info.qs_no=sp_dd.qs_no and (dd_zt=? or dd_zt=?)";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    pst.setString(2, "配送中");
		    pst.setString(3, "已超时");
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmjno(rs.getInt(4));
		        p.setyhno(rs.getInt(5));
		        p.setddstartmoney(rs.getFloat(6));
		        p.setddendmoney(rs.getFloat(7));
		        p.setddno(rs.getInt(8));
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
	public List<BeanOrder> loadrideringOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String qsno=BeanRider.currentLoginrider.getqsno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,user_name,sj_name,dd_zt,dd_starttime,dd_endtime,sp_dd.add_no\r\n" + 
					"from sp_dd,add_info,sj_info\r\n" + 
					"where qs_no=? and (dd_zt='配送中' or dd_zt='已超时')\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.sj_no=sj_info.sj_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setsjname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setddstarttime(rs.getTimestamp(5));
		        p.setddendtime(rs.getTimestamp(6));
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
	public List<BeanOrder> loadriderfreeOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,user_name,dd_zt,dd_starttime,dd_endtime,dd_no\r\n" + 
					"from sp_dd,sj_info,add_info\r\n" + 
					"where qs_no=0\r\n" + 
					"and sj_info.sj_no=sp_dd.sj_no\r\n" + 
					"and add_info.add_no=sp_dd.add_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setusername(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setddstarttime(rs.getTimestamp(4));
		        p.setddendtime(rs.getTimestamp(5));
		        p.setddno(rs.getInt(6));
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
	public void takeOrder(BeanOrder order)throws BaseException{
		Connection conn=null;
		String qsno=BeanRider.currentLoginrider.getqsno();
		try {
			conn = DBUtil.getConnection();
		    String sql = "update sp_dd set qs_no=? where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    pst.setInt(2, order.getddno());
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
	public void sendOrder(BeanOrder order)throws BaseException{
		String qsno=BeanRider.currentLoginrider.getqsno();
		
		Calendar calendar1 = new GregorianCalendar();
		Date date=new Date(calendar1.getTimeInMillis());
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.add(Calendar.DATE, 1);
        Date now = new Date(calendar.getTimeInMillis());
        
		Connection conn=null;
		int num=0;
		float money=0;
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
			if(date.before(now)){
				String sql1 = "update qs_info set qs_id='正式员工' where qs_no=?";
				java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
				pst1.setString(1, qsno);
				pst1.execute();
				pst1.close();
			}
			rs.close();
		    pst.close();
		    String id=BeanRider.currentLoginrider.getqsid();
			sql = "select count(dd_no)\r\n" + 
					"from sp_dd\r\n" + 
					"where qs_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    rs = pst.executeQuery();
		    if(rs.next())
		    	num=rs.getInt(1);
		    rs.close();
		    pst.close();
		    if(num<100)
		    	money=2;
		    else if(num>=100 && num<300)
		    	money=3;
		    else if(num>=300 && num<450)
		    	money=5;
		    else if(num>=450 && num<550)
		    	money=6;
		    else if(num>=550 && num<650)
		    	money=7;
		    else if(num>=650)
		    	money=8;
		    if(id.equals("新人"))
		    	money=money+0.5f;
		    sql = "update sp_dd set dd_zt='已送达',money=?,time=? where dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setFloat(1, money);
		    pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
		    pst.setInt(3, order.getddno());
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
	public List<BeanOrder> loadridermoney() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String qsno=BeanRider.currentLoginrider.getqsno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,user_name,dd_zt,money,pj,dd_no,time\r\n" + 
					"from sp_dd,sj_info,add_info\r\n" + 
					"where qs_no=? and dd_zt='已送达'\r\n" + 
					"and sj_info.sj_no=sp_dd.sj_no\r\n" + 
					"and add_info.add_no=sp_dd.add_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setusername(rs.getString(2));
		        p.setddzt(rs.getString(3));
		        p.setmoney(rs.getFloat(4));
		        p.setpj(rs.getString(5));
		        p.setddno(rs.getInt(6));
		        p.settime(rs.getTimestamp(7));
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
	public List<BeanOrder> loadallridermoney() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select qs_info.qs_no,qs_name,sum(money),count(dd_no)\r\n" + 
					"from sp_dd,qs_info\r\n" + 
					"where dd_zt='已送达'\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"group by qs_no\r\n" + 
					"order by sum(money),count(dd_no)";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setqsno(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setsummoney(rs.getFloat(3));
		        p.setddcount(rs.getInt(4));
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
	public List<BeanOrder> loaduserpj() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select dd_no,sj_name,qs_name,dd_starttime,dd_endtime,dd_zt,pj,time\r\n" + 
					"from sp_dd,sj_info,qs_info\r\n" + 
					"where user_no=?\r\n" + 
					"and sj_info.sj_no=sp_dd.sj_no\r\n" + 
					"and qs_info.qs_no=sp_dd.qs_no\r\n" + 
					"and dd_zt='已送达'";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setddno(rs.getInt(1));
		        p.setsjname(rs.getString(2));
		        p.setqsname(rs.getString(3));
		        p.setddstarttime(rs.getTimestamp(4));
		        p.setddendtime(rs.getTimestamp(5));
		        p.setddzt(rs.getString(6));
		        p.setpj(rs.getString(7));
		        p.settime(rs.getTimestamp(8));
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
	public List<BeanOrder> loaduserxfinfo() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(dd_no),sum(dd_endmoney),sum(dd_startmoney-dd_endmoney)\r\n" + 
					"from sp_dd\r\n" + 
					"where user_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, userno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setddcount(rs.getInt(1));
		        p.setsumddendmoney(rs.getFloat(2));
		        p.setsumddyh(rs.getFloat(3));
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
	public List<BeanOrder> loadallxfinfo() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(dd_no),sum(dd_endmoney),sum(dd_startmoney-dd_endmoney),sp_dd.user_no,user_name\r\n" + 
					"from sp_dd,user_info\r\n" + 
					"where sp_dd.user_no=user_info.user_no\r\n" + 
					"group by sp_dd.user_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setddcount(rs.getInt(1));
		        p.setsumddendmoney(rs.getFloat(2));
		        p.setsumddyh(rs.getFloat(3));
		        p.setuserno(rs.getString(4));
		        p.setusername(rs.getString(5));
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
	public void updhppj(BeanOrder order)throws BaseException {
		float money=0.5f;
		String pj=null;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pj\r\n" + 
					"from sp_dd\r\n" + 
					"where dd_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, order.getddno());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				pj=rs.getString(1);
			rs.close();
			pst.close();
			if(pj.equals("好评"))
				money=0;
			else if(pj.equals("差评"))
				money=20.5f;
		    sql = "update sp_dd\r\n" + 
		    		"set pj='好评',money=money+?,time=?\r\n" + 
		    		"where dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setFloat(1, money);
		    pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
		    pst.setInt(3, order.getddno());
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
	public void updcppj(BeanOrder order)throws BaseException {
		float money=-20;
		String pj=null;
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pj\r\n" + 
					"from sp_dd\r\n" + 
					"where dd_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, order.getddno());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				pj=rs.getString(1);
			rs.close();
			pst.close();
			if(pj.equals("好评"))
				money=-20.5f;
			else if(pj.equals("差评"))
				money=0;
		    sql = "update sp_dd\r\n" + 
		    		"set pj='差评',money=money+?,time=?\r\n" + 
		    		"where dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setFloat(1,money);
		    pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
		    pst.setInt(3, order.getddno());
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
	public void delpj(BeanOrder order)throws BaseException {
		Connection conn=null;
		String pj=null;
		float money=0;
		try {
			conn = DBUtil.getConnection();
			String sql = "select pj\r\n" + 
					"from sp_dd\r\n" + 
					"where dd_no=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, order.getddno());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next())
				pj=rs.getString(1);
			rs.close();
			pst.close();
			if(pj.equals("好评"))
				money=-0.5f;
			else if(pj.equals("差评"))
				money=20;
		    sql = "update sp_dd\r\n" + 
		    		"set pj=null,money=money+?,time=?\r\n" + 
		    		"where dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setFloat(1, money);
		    pst.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
		    pst.setInt(3, order.getddno());
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
	public List<BeanOrder> selectxfinfo(BeanUser user) throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select count(dd_no),sum(dd_endmoney),sum(dd_startmoney-dd_endmoney)\r\n" + 
					"from sp_dd\r\n" + 
					"where user_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, user.getUserid());
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setddcount(rs.getInt(1));
		        p.setsumddendmoney(rs.getFloat(2));
		        p.setsumddyh(rs.getFloat(3));
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
	public List<BeanOrder> selectridermoney(BeanRider rider) throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select qs_info.qs_no,qs_name,sum(money),count(dd_no)\r\n" + 
					"from sp_dd,qs_info\r\n" + 
					"where dd_zt='已送达'\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"and qs_info.qs_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, rider.getqsno());
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setqsno(rs.getString(1));
		        p.setqsname(rs.getString(2));
		        p.setsummoney(rs.getFloat(3));
		        p.setddcount(rs.getInt(4));
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
	public void deleteOrder() throws BaseException {
		int ddno=BeanOrder.currentLoginOrder.getddno();
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "delete from dd_info where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, ddno);
		    pst.execute();
			pst.close();
			sql = "delete from sp_dd where dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setInt(1, ddno);
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
	public void addstore(BeanStore store)throws BaseException{
		int ddno=BeanOrder.currentLoginOrder.getddno();
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select sj_no\r\n" + 
		    		"from sp_dd\r\n" + 
		    		"where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setInt(1, ddno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    if(rs.next()) {
		    	if(!store.getsjno().equals(rs.getString(1))) {
		    		String sql1 = "delete from dd_info\r\n" + 
		    				"where dd_no=?";
				    java.sql.PreparedStatement pst1 = conn.prepareStatement(sql1);
				    pst1.setInt(1, ddno);
				    pst1.execute();
					pst1.close();
					sql1 = "update sp_dd\r\n" + 
							"set dd_startmoney=0,dd_endmoney=0\r\n" + 
							"where dd_no=?";
				    pst1 = conn.prepareStatement(sql1);
				    pst1.setInt(1, ddno);
				    pst1.execute();
					pst1.close();
		    	}
		    }
		    rs.close();
			pst.close();
			sql = "update sp_dd\r\n" + 
					"set sj_no=?\r\n" + 
					"where dd_no=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, store.getsjno());
		    pst.setInt(2, ddno);
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
