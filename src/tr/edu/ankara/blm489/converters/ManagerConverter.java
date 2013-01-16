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

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		if (value == null)
			return null;
		try {
			EntityManager em = MainControl.getEmf().createEntityManager();
			int id = Integer.parseInt(value);
			Manager m = em.find(Manager.class, id);
			em.getTransaction().begin();
			em.getTransaction().commit();
			return m;
		} catch (Exception e) {
			System.out.println(e.getMessage() + value);
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		Manager m = (Manager)value; 
		return m.toString();
		
	}

}
