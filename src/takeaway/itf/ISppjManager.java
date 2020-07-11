package takeaway.itf;

import java.util.List;

import takeaway.model.BeanSppj;
import takeaway.util.BaseException;

public interface ISppjManager {
	/**
	 * �������
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @throws BaseException
	 */
	public BeanSppj addSppj(String pjnr,int pjstar,boolean photo,int ddno) throws BaseException;
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
	/**
	 * ɾ���̼�
	 * @param address
	 * @throws BaseException
	 */
	public void deleteSppj(BeanSppj sppj)throws BaseException;
}
