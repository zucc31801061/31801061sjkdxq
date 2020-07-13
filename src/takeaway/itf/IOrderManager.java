package takeaway.itf;

import java.util.List;

import takeaway.model.BeanAddress;
import takeaway.model.BeanMjMethod;
import takeaway.model.BeanOrder;
import takeaway.model.BeanRider;
import takeaway.model.BeanStore;
import takeaway.model.BeanUser;
import takeaway.model.BeanYhInfo;
import takeaway.util.BaseException;

public interface IOrderManager {
	/**
	 * ��Ӷ���
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @param name  �ƻ�����
	 * @throws BaseException
	 */
	public BeanOrder addOrder() throws BaseException;
	/**
	 * ��ȡ��ǰ�̼����ж���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadBysj()throws BaseException;
	/**
	 * ��ȡ��ǰ�������ж���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadByqs()throws BaseException;
	/**
	 * ��ȡ��ǰ�û��������ʹﶩ��
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserHisOrder() throws BaseException;
	/**
	 * ��ȡ��ǰ�̼��������ʹﶩ��
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadstoreHisOrder() throws BaseException;
	/**
	 * ��ȡ��ǰ�����������ʹﶩ��
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadriderHisOrder() throws BaseException;
	/**
	 * ��ȡ��ǰ�û����������ж���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserIngOrder() throws BaseException;
	/**
	 * ��ȡ��ǰ�̼����������ж���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadstoreingOrder() throws BaseException;
	/**
	 * ��ȡ��ǰ�̼����������ж���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadrideringOrder() throws BaseException;
	/**
	 * ��ȡ���пɽӵ��Ķ���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadriderfreeOrder() throws BaseException;
	/**
	 * �ӵ�
	 * @param plan
	 * @throws BaseException
	 */
	public void takeOrder(BeanOrder order)throws BaseException;
	/**
	 * ȷ���ʹ�
	 * @param plan
	 * @throws BaseException
	 */
	public void sendOrder(BeanOrder order)throws BaseException;
	/**
	 * ��ȡ��ǰ�û���������(������)
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserpj() throws BaseException;
	/**
	 * ��ȡ��ǰ����������Ϣ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadridermoney() throws BaseException;
	/**
	 * ��ȡ��ѯ����������Ϣ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> selectridermoney(BeanRider rider) throws BaseException;
	/**
	 * ��ȡ��������������Ϣ
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadallridermoney() throws BaseException;
	/**
	 * ɾ���̼�
	 * @param plan
	 * @throws BaseException
	 */
	public List<BeanOrder> loaduserxfinfo() throws BaseException;
	public void deleteOrder()throws BaseException;
	public void updhppj(BeanOrder order)throws BaseException;
	public void updcppj(BeanOrder order)throws BaseException;
	public void delpj(BeanOrder order)throws BaseException;
	public void addstore(BeanStore store)throws BaseException;
	public List<BeanOrder> loadallxfinfo() throws BaseException;
	public List<BeanOrder> selectxfinfo(BeanUser user) throws BaseException;
	public float pdxd()throws BaseException;
	public void csh()throws BaseException;
	public void addmj(BeanMjMethod mj)throws BaseException;
	public void addyh(BeanYhInfo yh)throws BaseException;
	public void addadd(BeanAddress add)throws BaseException;
	public void order()throws BaseException;
}
