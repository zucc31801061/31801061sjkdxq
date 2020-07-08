package takeaway.itf;

import java.util.List;

import takeaway.model.BeanOrder;
import takeaway.util.BaseException;

public interface IOrderManager {
	/**
	 * ��Ӷ���
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @param name  �ƻ�����
	 * @throws BaseException
	 */
	public BeanOrder addOrder(String name) throws BaseException;
	/**
	 * ��ȡ��ǰ�̼����ж���
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrder> loadBysj()throws BaseException;
	public List<BeanOrder> loaduserHisOrder() throws BaseException;
	public List<BeanOrder> loaduserIngOrder() throws BaseException;
	/**
	 * ɾ���̼�
	 * @param plan
	 * @throws BaseException
	 */
	public void deleteOrder(BeanOrder plan)throws BaseException;
}
