/**
 * 
 */
package tr.edu.ankara.blm489.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.persistence.EntityManager;

import tr.edu.ankara.blm489.controls.MainControl;
import tr.edu.ankara.blm489.models.Manager;

/**
 * @author sskl
 * 
 */
public class ManagerConverter implements Converter {

	/**
	 * 
	 */
	public ManagerConverter() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2)
			throws ConverterException {
		try {
			EntityManager em = MainControl.getEmf().createEntityManager();
			int id = Integer.parseInt(arg2);
			Manager m = em.find(Manager.class, id);
			em.getTransaction().begin();
			em.getTransaction().commit();
			return m;
		} catch (Exception e) {
			System.out.println(e.getMessage() + arg2);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext
	 * , javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ConverterException {
		Manager m = (Manager) arg2;

		return (m.getId() != 0) ? String.valueOf(m.getId()) : null;
	}

}
