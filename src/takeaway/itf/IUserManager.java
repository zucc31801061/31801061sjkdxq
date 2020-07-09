package takeaway.itf;

import java.util.List;

import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public interface IUserManager {
	/**
	 * ע�᣺
	 * Ҫ���û��������ظ�������Ϊ��
	 * ����������������һ�£����벻��Ϊ��
	 * ���ע��ʧ�ܣ����׳��쳣
	 * @param userid
	 * @param pwd  ����
	 * @param pwd2 �ظ����������
	 * @return
	 * @throws BaseException
	 */
	public BeanUser reg(String userid, String pwd,String pwd2) throws BaseException;
	/**
	 * ��½
	 * 1������û������ڻ�����������׳�һ���쳣
	 * 2�������֤�ɹ����򷵻ص�ǰ�û���Ϣ
	 * @param userid
	 * @param pwd
	 * @return
	 * @throws BaseException
	 */
	public BeanUser login(String userid,String pwd)throws BaseException;
	/**
	 * �޸�����
	 * ���û�гɹ��޸ģ����׳��쳣
	 * @param user    ��ǰ�û�
	 * @param oldPwd  ԭ����
	 * @param newPwd  ������
	 * @param newPwd2 �ظ������������
	 */
	public void changePwd(BeanUser user, String oldPwd,String newPwd, String newPwd2)throws BaseException;
	/**
	 * �޸ĸ�����Ϣ
	 * @param name   ���û���
	 * @param sex    ���Ա�
	 * @param phnum  ���ֻ���
	 * @param email  �������ַ
	 * @param city   �³���
	 */
	public void updateinfo(String name,String sex,String phnum,String email,String city) throws BaseException;
	/**
	 * ��ʾ��ǰ�û���Ϣ
	 * @return
	 * @throws BaseException
	 */
	public BeanUser SearchInfo() throws BaseException;
	/**
	 * ��ʾ��ǰ�û���Ϣ
	 * @return
	 * @throws BaseException
	 */
	public BeanUser SearchVIP() throws BaseException;
	/**
	 * ��ֵVIP
	 * @return
	 * @throws BaseException
	 */
	public BeanUser PayVIP() throws BaseException;
}
