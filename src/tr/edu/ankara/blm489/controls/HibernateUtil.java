/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {
			AnnotationConfiguration config  = new AnnotationConfiguration();
			config.configure();
			//Configuration configuration = new Configuration();
		    //configuration.configure();
		    sessionFactory = config.buildSessionFactory();
		} catch (Throwable ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}