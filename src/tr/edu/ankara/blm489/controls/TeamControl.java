/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import tr.edu.ankara.blm489.models.Employee;
import tr.edu.ankara.blm489.models.Manager;
import tr.edu.ankara.blm489.models.Team;
import tr.edu.ankara.blm489.models.User;

/**
 * @author sskl
 *
 */
public class TeamControl extends MainControl {

	private List<Team> teams;
	private List<Employee> employees;
	private Team[] selectedTeams;
	private Team newTeam = new Team();

	/**
	 * @return the teams
	 */
	public List<Team> getTeams() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        
        User user =  (User)session.getAttribute("sessUser");
        if (user instanceof Manager) {
        	Manager m = (Manager) user;
			EntityManager entityManager = emf.createEntityManager();
			entityManager.getTransaction().begin();
			teams = entityManager.createQuery("from Team where managerId = " + m.getId(), Team.class).getResultList();
	        entityManager.getTransaction().commit();
	        entityManager.close();
        } else {
        	EntityManager entityManager = emf.createEntityManager();
			entityManager.getTransaction().begin();
			teams = entityManager.createQuery("from Team", Team.class).getResultList();
	        entityManager.getTransaction().commit();
	        entityManager.close();
        }

		return teams;
	}

	public String saveNewTeam() {
		FacesContext context = FacesContext.getCurrentInstance();

		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newTeam);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		} finally {
	        entityManager.close();
		}

		return "/team/index.xhtml";
	}


	public String deleteSelectedTeams() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			for(Team  itr : selectedTeams) {
				itr = entityManager.merge(itr);
				entityManager.remove(itr);
			}
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		}

		return "/admin/index.xhtml";
	}
	
	public String controlSelectedAdmins() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedTeams.length > 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select only one row to edit!", "");
	        context.addMessage(null, message);
	        return null;
		}
		System.out.println(selectedTeams.length);
		return "/team/editTeam.xhtml"; 
	}

	public String editSelectedTeam() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			Team merged = entityManager.merge(selectedTeams[0]);
			entityManager.persist(merged);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		}

		return "/team/index.xhtml";
	}

	/**
	 * @param teams the teams to set
	 */
	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	/**
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Manager user = (Manager) session.getAttribute("sessUser");

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		employees = entityManager.createQuery("from User where managerId = " + user.getId(), Employee.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();

		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	/**
	 * @return the selectedTeams
	 */
	public Team[] getSelectedTeams() {
		return selectedTeams;
	}

	/**
	 * @param selectedTeams the selectedTeams to set
	 */
	public void setSelectedTeams(Team[] selectedTeams) {
		this.selectedTeams = selectedTeams;
	}


	/**
	 * @return the newTeam
	 */
	public Team getNewTeam() {
		return newTeam;
	}


	/**
	 * @param newTeam the newTeam to set
	 */
	public void setNewTeam(Team newTeam) {
		this.newTeam = newTeam;
	}
	
}
