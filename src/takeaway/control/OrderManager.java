package takeaway.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
	public BeanOrder addOrder(String name) throws BaseException {
		// TODO Auto-generated method stub
		/*if(name.isEmpty()) 
			throw new BusinessException("商家名为空");
		BeanOrder store=new BeanOrder();
		String userno = BeanUser.currentLoginUser.getUserid();
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			String sql = "select * from sj_info where sj_no = ?";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			pst.setString(1,userno);
			java.sql.ResultSet rs=pst.executeQuery();
			if(rs.next()) throw new BusinessException("您已是商家，请点击“已是商家”");
			rs.close();
			pst.close();
			sql = "insert into sj_info(sj_name,sj_no) values(?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1,name);
			pst.setString(2,userno);
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
		return store;*/
		return null; 
	}

	@Override
	public List<BeanOrder> loadBysj() throws BaseException {
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		String sjno = BeanStore.currentLoginstore.getsjno();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no from sp_dd,qs_info,add_info where sj_no=? and add_info.add_no=sp_dd.add_no and qs_info.qs_no=sp_dd.qs_no";
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
			String sql = "select sj_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no,add_no\r\n" + 
					"from sp_dd,sj_info\r\n" + 
					"where qs_no=? and sj_info.sj_no=sp_dd.sj_no";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setddzt(rs.getString(2));
		        p.setmjno(rs.getInt(3));
		        p.setyhno(rs.getInt(4));
		        p.setddstartmoney(rs.getFloat(5));
		        p.setddendmoney(rs.getFloat(6));
		        p.setddno(rs.getInt(7));
		        p.setaddno(rs.getInt(8));
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
			String sql = "select dd_no,user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney\r\n" + 
					"from sp_dd,add_info,qs_info\r\n" + 
					"where sj_no=? and dd_zt=?\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"order by dd_endtime desc";
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
			String sql = "select dd_no,user_name,sj_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney\r\n" + 
					"from sp_dd,add_info,sj_info\r\n" + 
					"where qs_no=? and dd_zt='已送达'\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.sj_no=sj_info.sj_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	System.out.println(rs.getString(2));
		    	BeanOrder p=new BeanOrder();
		    	p.setddno(rs.getInt(1));
		    	p.setusername(rs.getString(2));
		        p.setsjname(rs.getString(3));
		        p.setddzt(rs.getString(4));
		        p.setmjno(rs.getInt(5));
		        p.setyhno(rs.getInt(6));
		        p.setddstartmoney(rs.getFloat(7));
		        p.setddendmoney(rs.getFloat(8));
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
			String sql = "select dd_no,user_name,qs_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney\r\n" + 
					"from sp_dd,add_info,qs_info\r\n" + 
					"where sj_no=? and (dd_zt=? or dd_zt=?)\r\n" + 
					"and sp_dd.add_no=add_info.add_no\r\n" + 
					"and sp_dd.qs_no=qs_info.qs_no\r\n" + 
					"order by dd_endtime desc";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, sjno);
		    pst.setString(2, "已超时");
		    pst.setString(3, "配送中");
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
			String sql = "select sj_name,user_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no\r\n" + 
					"from sp_dd,sj_info,add_info\r\n" + 
					"where qs_no=?\r\n" + 
					"and sj_info.sj_no=sp_dd.sj_no\r\n" + 
					"and add_info.add_no=sp_dd.add_no\r\n" + 
					"and (dd_zt='已超时' or dd_zt='配送中')";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, qsno);
		    java.sql.ResultSet rs = pst.executeQuery();
		    while (rs.next()) {
		    	BeanOrder p=new BeanOrder();
		        p.setsjname(rs.getString(1));
		        p.setusername(rs.getString(2));
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
	public List<BeanOrder> loadriderfreeOrder() throws BaseException{
		List<BeanOrder> result=new ArrayList<BeanOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select sj_name,user_name,dd_zt,mj_no,yh_no,dd_startmoney,dd_endmoney,dd_no\r\n" + 
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
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update sp_dd set dd_zt='已送达',money=3,time=? where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
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
		        p.settime(rs.getDate(7));
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
		        p.setddstarttime(rs.getDate(4));
		        p.setddendtime(rs.getDate(5));
		        p.setddzt(rs.getString(6));
		        p.setpj(rs.getString(7));
		        p.settime(rs.getDate(8));
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
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update sp_dd\r\n" + 
		    		"set pj='好评',money=3.5,time=?\r\n" + 
		    		"where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
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
	public void updcppj(BeanOrder order)throws BaseException {
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update sp_dd\r\n" + 
		    		"set pj='差评',money=2.5,time=?\r\n" + 
		    		"where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
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
	public void delpj(BeanOrder order)throws BaseException {
		Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "update sp_dd\r\n" + 
		    		"set pj=null,money=3,time=?\r\n" + 
		    		"where dd_no=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
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
	@Override
	public void deleteOrder(BeanOrder plan) throws BaseException {
		/*Connection conn=null;
		try {
			conn = DBUtil.getConnection();
		    String sql = "select finished_step_count,step_count from tbl_plan where user_id=? and plan_order=?";
		    java.sql.PreparedStatement pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    pst.setInt(2, plan.getplanorder());
		    ResultSet rs = pst.executeQuery();
		    rs.next();
		    if (rs.getInt(1) < rs.getInt(2)) {
		    	throw new BusinessException("计划执行步骤有误");
		    }
		    sql = "delete from tbl_plan where user_id=? and plan_order=?";
		    pst = conn.prepareStatement(sql);
		    pst.setString(1, BeanUser.currentLoginUser.getUserid());
		    pst.setInt(2, plan.getplanorder());
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
		}*/
	}
}
