/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("初始化SessionFactory失败！" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}
	public static final ThreadLocal session = new ThreadLocal();

	public static Session getCurrentSession() throws HibernateException {
		Session s = (Session) session.get();
		if (s == null || !s.isOpen()) {
			s = sessionFactory.openSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() throws HibernateException {
		Session s = (Session) session.get();
		session.set(null);
		if (s != null) {
			s.close();
		}
	}
}