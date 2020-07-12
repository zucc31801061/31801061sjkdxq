package takeaway.itf;

import java.util.List;

import takeaway.model.BeanOrderInfo;
import takeaway.model.BeanProduct;
import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public interface ISppjManager {
	/**
	 * �������
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @throws BaseException
	 */
	public void addSppj(BeanSppj sppj,String pjnr,int pjstar,boolean photo) throws BaseException;
	/**
	 * ��ȡ��ǰ�û���������
	 * @return
	 * @throws BaseException
	 */
	public List<BeanSppj> loadbyuser()throws BaseException;
	/**
	 * ��ȡ��ǰ�̼���������
	 * @return
	 * @throws BaseException
	 */
	public List<BeanSppj> loadbystore()throws BaseException;
	public List<BeanSppj> loadpropj(BeanProduct product)throws BaseException;
	public List<BeanSppj> loadpropj(BeanOrderInfo orderinfo)throws BaseException;
	/**
	 * ɾ���̼�
	 * @param address
	 * @throws BaseException
	 */
	public void deleteSppj(BeanSppj sppj)throws BaseException;
}
