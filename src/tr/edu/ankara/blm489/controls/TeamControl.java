/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tr.edu.ankara.blm489.models.Team;
import tr.edu.ankara.blm489.models.User;

/**
 * @author sskl
 *
 */
public class TeamControl extends MainControl {

	private List<Team> teams;

	/**
	 * @return the teams
	 */
	public List<Team> getTeams() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().put("sessUser", loggedUser);
		context.getExternalContext().getSessionMap().get
		HttpSession session = req.getSession();

        User user = (User) session.getAttribute("sessUser");
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		teams = entityManager.createQuery("from Team", Team.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

		return teams;
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
}
