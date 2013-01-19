/*
 * 
 */
package tr.edu.ankara.blm489.controls;

import java.io.IOException;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;

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
	private Admin newAdmin = new Admin();
	private Manager newManager = new Manager();
	private Employee newEmployee = new Employee();
	private Manager selectedManager = new Manager();
	private List<Admin> admins;
	private List<Manager> managers;
	private List<Employee> employees;
	private List<Employee> selectedTeam;
	private Admin[] selectedAdmins;
	private Manager[] selectedManagers;
	private Employee[] selectedEmployees;

	public void login() {
        FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		Session session = entityManager.unwrap(Session.class);
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("username", this.username));
        loggedUser = (User) criteria.uniqueResult();

        entityManager.getTransaction().commit();
        entityManager.close();
		if ( loggedUser == null ) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid username. Please enter valid username.", "");
	        context.addMessage(null, message);
		} else {
			context.getExternalContext().getSessionMap().put("sessUser", loggedUser);
	        try {
	        	String page = "";
	        	if (loggedUser.getRole().getType().equals("Employee")) {
	        		page = "task/index.xhtml";
	        	} else {
	        		page = "project/index.xhtml";
	        	}
	        	context.getExternalContext().redirect(page);
			} catch (IOException e) {
				System.out.println("Error redirecting.");
			}
		}
	}

	public void logout() {
		FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        session.invalidate();
        try {
			context.getExternalContext().redirect("admin/logout.xhtml");
		} catch (IOException e) {
			System.out.println("Error redirecting.");
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
	        newAdmin = new Admin();
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
	        newManager = new Manager();
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
	        newEmployee = new Employee();
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		} finally {
	        entityManager.close();
		}

		return "/admin/index.xhtml";
	}

	public String deleteSelectedAdmins() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			for(Admin itr : selectedAdmins) {
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

	public String deleteSelectedManagers() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			for(Manager itr : selectedManagers) {
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

	public String deleteSelectedEmployees() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			for(Employee  itr : selectedEmployees) {
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
		if (selectedAdmins.length > 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select only one row to edit!", "");
	        context.addMessage(null, message);
	        return null;
		}
		return "/admin/editAdmin.xhtml"; 
	}
	
	public String controlSelectedManagers() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedManagers.length > 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select only one row to edit!", "");
	        context.addMessage(null, message);
	        return null;
		}
		return "/admin/editManager.xhtml"; 
	}
	
	public String controlSelectedEmployees() {
		FacesContext context = FacesContext.getCurrentInstance();
		if (selectedEmployees.length > 1) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select only one row to edit!", "");
	        context.addMessage(null, message);
	        return null;
		}
		return "/admin/editEmployee.xhtml"; 
	}
	
	public String editSelectedAdmin() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			Admin merged = entityManager.merge(selectedAdmins[0]);
			entityManager.persist(merged);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		}

		return "/admin/index.xhtml";
	}
	
	public String editSelectedManager() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			Manager merged = entityManager.merge(selectedManagers[0]);
			entityManager.persist(merged);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		}

		return "/admin/index.xhtml";
	}
	
	public String editSelectedEmployee() {
		FacesContext context = FacesContext.getCurrentInstance();
		EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			Employee merged = entityManager.merge(selectedEmployees[0]);
			entityManager.persist(merged);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return null;
		}

		return "/admin/index.xhtml";
	}

	public void updateSelectedTeam() {
		System.out.println("Hi!");
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
	 * @return the admins
	 */
	public List<Admin> getAdmins() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		admins = entityManager.createQuery("from Admin", Admin.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
		return admins;
	}

	/**
	 * @param admins the admins to set
	 */
	public void setAdmins(List<Admin> admins) {
		this.admins = admins;
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
	 * @return the employees
	 */
	public List<Employee> getEmployees() {
		EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		employees = entityManager.createQuery("from Employee", Employee.class).getResultList();
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
	 * @return the newAdmin
	 */
	public Admin getNewAdmin() {
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

	/**
	 * @return the selectedTeam
	 */
	public List<Employee> getSelectedTeam() {
		return selectedTeam;
	}

	/**
	 * @param selectedTeam the selectedTeam to set
	 */
	public void setSelectedTeam(List<Employee> selectedTeam) {
		this.selectedTeam = selectedTeam;
	}

	/**
	 * @return the selectedManager
	 */
	public Manager getSelectedManager() {
		return selectedManager;
	}

	/**
	 * @param selectedManager the selectedManager to set
	 */
	public void setSelectedManager(Manager selectedManager) {
		this.selectedManager = selectedManager;
	}

	/**
	 * @return the selectedAdmins
	 */
	public Admin[] getSelectedAdmins() {
		return selectedAdmins;
	}

	/**
	 * @param selectedAdmins the selectedAdmins to set
	 */
	public void setSelectedAdmins(Admin[] selectedAdmins) {
		this.selectedAdmins = selectedAdmins;
	}

	/**
	 * @return the selectedManagers
	 */
	public Manager[] getSelectedManagers() {
		return selectedManagers;
	}

	/**
	 * @param selectedManagers the selectedManagers to set
	 */
	public void setSelectedManagers(Manager[] selectedManagers) {
		this.selectedManagers = selectedManagers;
	}

	/**
	 * @return the selectedEmployees
	 */
	public Employee [] getSelectedEmployees() {
		return selectedEmployees;
	}

	/**
	 * @param selectedEmployees the selectedEmployees to set
	 */
	public void setSelectedEmployees(Employee [] selectedEmployees) {
		this.selectedEmployees = selectedEmployees;
	}
}
