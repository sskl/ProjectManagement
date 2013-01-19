/**
 * 
 */
package tr.edu.ankara.blm489.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author sskl
 *
 */
@Entity
@Table(name = "team")
public class Team {

	/**
	 * Team id
	 */
	private int id;

	/**
	 * Team members
	 */
	private List<Employee> employees;

	/**
	 * Team manager
	 */
	private Manager manager;

	/**
	 * @return the id
	 */
	@Id()
	@GeneratedValue()
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the employees
	 */
	@ManyToMany(targetEntity=Employee.class)
	@JoinTable(name = "team_employee",
	           joinColumns = { @JoinColumn(name = "teamId") },
	           inverseJoinColumns = { @JoinColumn(name = "employeeId") })
	public List<Employee> getEmployees() {
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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