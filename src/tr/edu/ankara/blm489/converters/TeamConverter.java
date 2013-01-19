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
import tr.edu.ankara.blm489.models.Team;

/**
 * @author sskl
 * 
 */

public class TeamConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value)
			throws ConverterException {
		if (value == null)
			return null;
		try {
			EntityManager em = MainControl.getEmf().createEntityManager();
			int id = Integer.parseInt(value);
			Team p = em.find(Team.class, id);
			em.getTransaction().begin();
			em.getTransaction().commit();
			return p;
		} catch (Exception e) {
			System.out.println(e.getMessage() + value);
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value)
			throws ConverterException {
		Team p = (Team)value; 
		return p.toString();
		
	}

}
