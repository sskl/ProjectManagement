/**
 * 
 */
package tr.edu.ankara.blm489.models;

import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * @author sskl
 *
 */
@Entity
@DiscriminatorValue("Manager")
public class Manager extends User {

	private List<Employee> team;
	
	private List<Project> projects;

	/**
	 * @return the team
	 */
	@OneToMany(targetEntity=Employee.class, mappedBy="manager")
	public List<Employee> getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(List<Employee> team) {
		this.team = team;
	}

	/**
	 * @return the projects
	 */
	@OneToMany(targetEntity=Project.class, mappedBy="manager")
	public List<Project> getProjects() {
		return projects;
	}

	/**
	 * @param projects the projects to set
	 */
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Manager)) return false;

        if (this.getId() == ((Manager)obj).getId()) {
            return true;
        } else {
            return false;
        }
	}
}