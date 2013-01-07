/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author sskl
 *
 */
public class MainControl {

	protected static EntityManagerFactory emf;
	private static int activePage = 0;

	static {
		try {
			FacesContext context = FacesContext.getCurrentInstance();
			emf = (EntityManagerFactory) context.getExternalContext().getSessionMap().get("sessEmf");
			if (emf == null) {
				emf = Persistence.createEntityManagerFactory("ProjectManagementPU");
				context.getExternalContext().getSessionMap().put("sessEmf", emf);
			}
		} catch (Throwable ex) {
			System.err.println("EntityManagerFactory" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * @return the activePage
	 */
	public int getActivePage() {
		return activePage;
	}

	/**
	 * @param activePage the activePage to set
	 */
	public static void setActivePage(int _activePage) {
		activePage = _activePage;
	}
}
