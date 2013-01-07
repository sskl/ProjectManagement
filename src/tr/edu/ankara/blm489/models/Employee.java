/**
 * 
 */
package tr.edu.ankara.blm489.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * @author sskl
 *
 */
@Entity
@DiscriminatorValue("Employee")
public class Employee extends User {

	private List<Task> tasks;
	private Manager manager;

	/**
	 * @return the projects
	 */
	@ManyToMany(targetEntity=Task.class)
	@JoinTable(name = "Employee_Task",
    		   joinColumns = { @JoinColumn(name = "employeeId") },
    		   inverseJoinColumns = { @JoinColumn(name = "taskId") })	
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	/**
	 * @return the manager
	 */
	@ManyToOne(targetEntity=Manager.class)
	@JoinColumn(name="managerId")
	public Manager getManager() {
		return manager;
	}

	/**
	 * @param manager the manager to set
	 */
	public void setManager(Manager manager) {
		this.manager = manager;
	}
}
