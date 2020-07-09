package takeaway.itf;

import java.util.List;

import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public interface IUserManager {
	/**
	 * 注册：
	 * 要求用户名不能重复，不能为空
	 * 两次输入的密码必须一致，密码不能为空
	 * 如果注册失败，则抛出异常
	 * @param userid
	 * @param pwd  密码
	 * @param pwd2 重复输入的密码
	 * @return
	 * @throws BaseException
	 */
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException;
	/**
	 * 登陆
	 * 1、如果用户不存在或者密码错误，抛出一个异常
	 * 2、如果认证成功，则返回当前用户信息
	 * @param userid
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanUser login(String userid,String pwd)throws BaseException;
	/**
	 * 修改密码
	 * 如果没有成功修改，则抛出异常
	 * @param user    当前用户
	 * @param oldPwd  原密码
	 * @param newPwd  新密码
	 * @param newPwd2 重复输入的新密码
	 */
	public void changePwd(BeanUser user, String oldPwd,String newPwd, String newPwd2)throws BaseException;
	/**
	 * 修改个人信息
	 * @param name   新用户名
	 * @param sex    新性别
	 * @param phnum  新手机号
	 * @param email  新邮箱地址
	 * @param city   新城市
	 */
	public void updateinfo(String name,String sex,String phnum,String email,String city) throws BaseException;
	/**
	 * 显示当前用户信息
	 * @return
	 * @throws BaseException
	 */
	public BeanUser SearchInfo() throws BaseException;
	/**
	 * 显示当前用户信息
	 * @return
	 * @throws BaseException
	 */
	public BeanUser SearchVIP() throws BaseException;
	/**
	 * 充值VIP
	 * @return
	 * @throws BaseException
	 */
	public BeanUser PayVIP() throws BaseException;
}
