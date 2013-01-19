/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

import org.hibernate.Hibernate;
import org.primefaces.model.DualListModel;

import tr.edu.ankara.blm489.models.Employee;
import tr.edu.ankara.blm489.models.Manager;
import tr.edu.ankara.blm489.models.Team;

/**
 * @author sskl
 *
 */
public class TeamControl extends MainControl {

	private List<Team> teams;
	private Team[] selectedTeams;
	private Team newTeam = new Team();
	private DualListModel<Employee> employees;

	public String saveNewTeam() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Manager user = (Manager) session.getAttribute("sessUser");
		EntityManager entityManager = emf.createEntityManager();
		
	    newTeam.setEmployees(employees.getTarget());
	    newTeam.setManager(user);
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newTeam);
	        entityManager.getTransaction().commit();
	        newTeam = new Team();
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
		return "/team/index.xhtml";
	}
	
	public String controlSelectedTeams() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedTeams.length > 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select only one row to edit!", "");
	        context.addMessage(null, message);
	        return null;
		}
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
	 * @return the teams
	 */
	public List<Team> getTeams() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Manager user = (Manager) session.getAttribute("sessUser");

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		teams = entityManager.createQuery("from Team where managerId = " + user.getId(), Team.class).getResultList();
		for (Team team : teams) {
			Hibernate.initialize(team.getEmployees());
		}
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

	/**
	 * @return the employees
	 */
	public DualListModel<Employee> getEmployees() {
		List<Employee> source;  
        List<Employee> target = new ArrayList<Employee>();  
          
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        Manager user = (Manager) session.getAttribute("sessUser");

		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		source = entityManager.createQuery("from Employee where managerId = " + user.getId(), Employee.class).getResultList();
        entityManager.getTransaction().commit();
        employees = new DualListModel<Employee>(source, target);
        entityManager.close();

		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(DualListModel<Employee> employees) {
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
