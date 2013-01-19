/**
 * 
 */
package tr.edu.ankara.blm489.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author sskl
 *
 */
@Entity
public class Project {

	private int id;
	private String name;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	@Override
	public String toString() {
		return String.valueOf(id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Project)) 
        	return false;

        if (this.getId() == ((Project)obj).getId()) {
            return true;
        } else {
            return false;
        }
	}
	
	
	
}
