package takeaway.itf;

import java.util.List;

import takeaway.model.BeanOrder;
import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanProduct;
import takeaway.util.BaseException;

public interface IOrderInfoManager {
	/**
	 * ����̼�
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @param name  �ƻ�����
	 * @throws BaseException
	 */
	public BeanOrderInfo addOrderInfo(String name) throws BaseException;
	/**
	 * ��ȡ�����̼�
	 * @return
	 * @throws BaseException
	 */
	public List<BeanOrderInfo> loadOrderInfo(BeanOrder order)throws BaseException;
	/**
	 * ɾ���̼�
	 * @param plan
	 * @throws BaseException
	 */
	public List<BeanOrderInfo> loadretj()throws BaseException;
	public void addproduct(BeanProduct product,int num)throws BaseException;
}
