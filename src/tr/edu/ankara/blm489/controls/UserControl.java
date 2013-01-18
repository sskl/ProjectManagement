/**
 * 
 */
package tr.edu.ankara.blm489.controls;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import tr.edu.ankara.blm489.models.Admin;
import tr.edu.ankara.blm489.models.Employee;
import tr.edu.ankara.blm489.models.Manager;
import tr.edu.ankara.blm489.models.Role;
import tr.edu.ankara.blm489.models.User;

/**
 * @author sskl
 *
 */
public class UserControl extends MainControl {

	private String username = "";
	private User loggedUser = null;
	private User selectedUser;
	private Admin newAdmin = new Admin();
	private Manager newManager = new Manager();
	private Employee newEmployee = new Employee();
	private List<User> users;
	private List<Manager> managers;

	public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Session session = entityManager.unwrap(Session.class);
		//Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", this.username));
        loggedUser = (User) criteria.uniqueResult();

        entityManager.getTransaction().commit();
        entityManager.close();
		if ( loggedUser == null ) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username. Please enter valid username.", "");
	        context.addMessage(null, message);
			return null;
		} else {
			context.getExternalContext().getSessionMap().put("sessUser", loggedUser);
			return "project/index.xhtml";
		}
	}
	
	public String saveNewAdmin() {
		FacesContext context = FacesContext.getCurrentInstance();
		Role role = new Role();
		role.setId(1);
		role.setType("Admin");
		newAdmin.setRole(role);

		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newAdmin);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		} finally {
	        entityManager.close();
		}
        
        newAdmin = null;
		return "/admin/index.xhtml";
	}
	
	public String saveNewManager() {
		FacesContext context = FacesContext.getCurrentInstance();
		Role role = new Role();
		role.setId(2);
		role.setType("Manager");
		newManager.setRole(role);

		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newManager);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		} finally {
	        entityManager.close();
		}

		return "/admin/index.xhtml";
	}
	
	public String saveNewEmployee() {
		FacesContext context = FacesContext.getCurrentInstance();
		Role role = new Role();
		role.setId(3);
		role.setType("Employee");
		newEmployee.setRole(role);

		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(newEmployee);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		} finally {
	        entityManager.close();
		}

		return "/admin/index.xhtml";
	}

	public String deleteUser() {
		
		return "";
	}

	public boolean isUserLogged() {
		if (loggedUser instanceof User)
			return true;
		return false;
	}
	
	public boolean isUserRoleAdmin() {
		if (loggedUser.getRole().getType().equals("Admin"))
			return true;
		return false;
    }
	
	public boolean isUserRoleManager() {
		if (loggedUser.getRole().getType().equals("Manager"))
			return true;
		return false;
    }
	
	public boolean isUserRoleEmployee() {
		if (loggedUser.getRole().getType().equals("Employee"))
			return true;
		return false;
    }

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return the loggedUser
	 */
	public User getLoggedUser() {
		return loggedUser;
	}

	/**
	 * @param loggedUser the loggedUser to set
	 */
	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	/**
	 * @return the selectedUser
	 */
	public User getSelectedUser() {
		return selectedUser;
	}

	/**
	 * @param selectedUser the selectedUser to set
	 */
	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		users = entityManager.createQuery("from User", User.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the managers
	 */
	public List<Manager> getManagers() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		managers = entityManager.createQuery("from Manager", Manager.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
		return managers;
	}

	/**
	 * @param managers the managers to set
	 */
	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

	/**
	 * @return the newAdmin
	 */
	public User getNewAdmin() {
		return newAdmin;
	}

	/**
	 * @param newAdmin the newAdmin to set
	 */
	public void setNewAdmin(Admin newAdmin) {
		this.newAdmin = newAdmin;
	}

	/**
	 * @return the newManager
	 */
	public Manager getNewManager() {
		if(newManager == null){
			newManager = new Manager();
		}
		return newManager;
	}

	/**
	 * @param newManager the newManager to set
	 */
	public void setNewManager(Manager newManager) {
		this.newManager = newManager;
	}

	/**
	 * @return the newEmployee
	 */
	public Employee getNewEmployee() {		
		return newEmployee;
	}

	/**
	 * @param newEmployee the newEmployee to set
	 */
	public void setNewEmployee(Employee newEmployee) {
		this.newEmployee = newEmployee;
	}

}
