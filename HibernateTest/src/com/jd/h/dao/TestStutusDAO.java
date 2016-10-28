package com.jd.h.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jd.h.bean.Master;
import com.jd.h.util.BaseHibernateDAO;

/**
 * 测试Hibernate 三种状态
 * @author asus
 *
 */
public class TestStutusDAO extends BaseHibernateDAO {
	/**
	 * 测试临时状态到持久状态的转变
	 */
	public void testT2P()
	{
		Master master=new Master();
		//临时太
		master.setName("呵呵");
		master.setSex(1);
		Session session=getSession();
		Transaction tran=session.beginTransaction();
		session.save(master);
		tran.commit();
		//持久态
		master.setSex(0);
		session.close();
		//游离态
		master.setName("a");
				
		//临时态
		master.setId(10);
	}
	/**
	 * 测试临时状态到持久状态后，更改属性提交。
	 */
	public void testT2P2Update()
	{
		Master master=new Master();
		master.setName("心");
		master.setSex(0);
		Session session=getSession();
		Transaction tran=session.beginTransaction();
		session.save(master);
		master.setName("苦");
		tran.commit();
		session.close();
		//回滚
		//tran.rollback();
		
	}
	/**
	 * 测试持久状态修改后提交
	 */
	public void testP2Update()
	{
		Session session = getSession();
		Master master=(Master)session.get(Master.class, 2);
		Transaction tran=session.getTransaction();
		//此处保存无意义
		session.save(master);
		session.save(master);
		master.setSex(1);
		master.setName("一个人");
		tran.commit();
		session.close();
		
	}
	/**
	 * 将持久态转换为游离态后更新提交
	 * clear 或 evict 或 session关闭
	 */
	public void testP2D2Update()
	{
		Session session = getSession();
		Master master=(Master)session.get(Master.class, 2);
		//持久转换为游离态
		session.evict(master);
		Transaction tran=session.getTransaction();
		//游离态 （操作无用，不在范围内）
		master.setName("ssssssssssss");
		tran.commit();
		session.close();
	}
	/**
	 * 将持久态转换为临时态
	 */
	/**
	 * 
	 */
	public void testP2T()
	{
		Session session = getSession();
		//持久态
		Master master=(Master)session.get(Master.class, 2);
		Transaction tran=session.getTransaction();
		session.delete(master);
		master.setName("666666");
		tran.commit();
		session.close();
	}
	/**
	 * 同步持久化对象
	 */
	public void testRefresh()
	{
		Session session=getSession();
		Master master=(Master)session.get(Master.class, 1);
		System.out.println("前"+master.getName());
		Transaction tran=session.beginTransaction();
		tran.commit();
		session.refresh(master);
		System.out.println("后"+master.getName());
		session.close();
	}
	/**
	 * 将游离态转换成持久态
	 */
	public void testD2P()
	{
		Session session=getSession();
		
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=new Master();
		//游离态
		master.setId(3);
		master.setName("55555");
		master.setSex(1);
		session.update(master);
		//持久态
		String name=master.getName();
		trans.commit();
		session.close();
		
	}
	/**
	 * 持久状态修改id      （报错）
	 */
	public void testP2EditId()
	{
        Session session=getSession();
		Transaction trans=session.beginTransaction();
		Master master=(Master)session.get(Master.class, 2);
		master.setId(1000);
		trans.commit();
		session.close();
	}
	/**
	 * 游离态转换为暂时状态
	 */
	public void testD2S()
	{
		 Session session=getSession();
		Transaction trans=session.beginTransaction();
		//临时态
		Master master=new Master();
		//游离态
		master.setId(3);
		session.delete(master);
		
		trans.commit();
		session.close();
		
	}
	/**
	 * 测试重复的持久化对象 （报错）
	 */
	public void testDuplicateP()
	{
		 Session session=getSession();
	     Transaction trans=session.beginTransaction();
	     //持久态
	     Master master=(Master)session.get(Master.class, 2);
	     //临时态
	     Master master2=new Master();
	     //游离态
	     master2.setId(2);
	     master2.setName("游离");
	     session.update(master2);
	     //持久态  
	     trans.commit();
	     session.close();
	}
	/**
	 * 消除重复的持久化对象
	 */
	public void testRemoveDupli()
	{
		Session session=getSession();
	     Transaction trans=session.beginTransaction();
	     //持久态
	     Master master=(Master)session.get(Master.class, 2);
	     //临时态
	     Master master2=new Master();
	     //游离态
	     master2.setId(2);
	     master2.setName("游离");
	     session.merge(master2);
	     //持久态  
	     System.out.println(master2);
	     trans.commit();
	     session.close();
	}
public static void main(String[] args) {
	
}

}
