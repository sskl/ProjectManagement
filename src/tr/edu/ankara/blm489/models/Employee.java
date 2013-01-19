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

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Employee)) return false;

        if (this.getId() == ((Employee)obj).getId()) {
            return true;
        } else {
            return false;
        }
	}
}