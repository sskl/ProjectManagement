/**
 * 
 */
package tr.edu.ankara.blm489.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author sskl
 *
 */
@Entity
@Table(name = "task")
public class Task {

	/**
	 * Task id
	 */
	private int id;
	
	/**
	 * Task brief
	 */
	private String taskBrief;
	
	/**
	 * Task creation date
	 */
	private Date createdDate;
	
	/**
	 * Task finished date
	 */
	private Date finishedDate;
	
	/**
	 * Task deadline date
	 */
	private Date deadLineDate;
	
	/**
	 * Task employees
	 */
	private Team team;
	
	/**
	 * Task Mapping Id
	 */
	private String tmId = null;

	/**
	 * @return the id
	 */
	@Id()
	@GeneratedValue()
	public int getId() {
		return id;
	}

	/**
	 * @return the taskBrief
	 */
	public String getTaskBrief() {
		return taskBrief;
	}

	/**
	 * @return the createdDate
	 */
	public Date getCreatedDate() {
		return createdDate;
	}

	/**
	 * @return the finishedDate
	 */
	public Date getFinishedDate() {
		return finishedDate;
	}

	/**
	 * @return the deadLineDate
	 */
	public Date getDeadLineDate() {
		return deadLineDate;
	}

	/**
	 * @param deadLineDate the deadLineDate to set
	 */
	public void setDeadLineDate(Date deadLineDate) {
		this.deadLineDate = deadLineDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @param finishedDate the finishedDate to set
	 */
	public void setFinishedDate(Date finishedDate) {
		this.finishedDate = finishedDate;
	}

	/**
	 * @param name the name to set
	 */
	public void setTaskBrief(String taskBrief) {
		this.taskBrief = taskBrief;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the team
	 */
	@ManyToOne(targetEntity=Team.class)
	@JoinColumn(name="teamId")
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	@Transient
	public String getTmId() {
		return tmId;
	}

	public void setTmId(String tmId) {
		this.tmId = tmId;
	}

}
