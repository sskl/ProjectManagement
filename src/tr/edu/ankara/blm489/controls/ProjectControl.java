/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import tr.edu.ankara.blm489.models.Project;

/**
 * @author sskl
 *
 */
public class ProjectControl extends MainControl {

	//private String projectName;
	//private Manager manager;
	private List<Project> projects;
	private Project newProject = new Project();

	/**
	 * @return the projects
	 */
	public List<Project> getProjects() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		projects = entityManager.createQuery("from Project", Project.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	/**
	 * @return the newProject
	 */
	public Project getNewProject() {
		return newProject;
	}

	/**
	 * @param newProject the newProject to set
	 */
	public void setNewProject(Project newProject) {
		this.newProject = newProject;
	}
	
	public String saveNewProject() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			//System.out.println(newProject.getManager().getName());
			entityManager.getTransaction().begin();
			entityManager.persist(newProject);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		} finally {
	        entityManager.close();
		}

		return "/project/index.xhtml";
	}
}