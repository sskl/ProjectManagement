/**
 * 
 */
package tr.edu.ankara.blm489.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author sskl
 *
 */
@Entity
@DiscriminatorValue("Employee")
public class Employee extends User {

	private Manager manager;

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
