package takeaway.itf;

import java.util.List;

import takeaway.model.BeanRider;
import takeaway.model.BeanUser;
import takeaway.util.BaseException;

public interface IRiderManager {
	/**
	 * ����̼�
	 * ע�⣺��ǰ��½�û���ͨ�� BeanUser.currentLoginUser��ȡ
	 * @param name  �ƻ�����
	 * @throws BaseException
	 */
	public BeanRider addRider(String name) throws BaseException;
	/**
	 *�̼ҵ�¼
	 */
	public BeanRider login() throws BaseException;
	/**
	 * ��ȡ�����̼�
	 * @return
	 * @throws BaseException
	 */
	public List<BeanRider> loadAll()throws BaseException;
	/**
	 * ɾ���̼�
	 * @param Rider
	 * @throws BaseException
	 */
	public void deleteRider(BeanRider Rider)throws BaseException;
	public void updateinfo(String name) throws BaseException;
	public void changeid(String rider,String id) throws BaseException;
	public List<BeanRider> loadbyrider()throws BaseException;
	public List<BeanRider> searchrider(String name)throws BaseException;
}
