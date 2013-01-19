package tr.edu.ankara.blm489.controls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;

import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import tr.edu.ankara.blm489.models.Manager;
import tr.edu.ankara.blm489.models.Project;
import tr.edu.ankara.blm489.models.Task;
import tr.edu.ankara.blm489.models.Team;

public class TaskControl extends MainControl {
	
	private List<Task> tasks;
	
	private String firstDateOfSelectedRange;
	private String lastDateOfSelectedRange;
	private String projectCriteria;
	
	private ScheduleModel eventModel;
	
	private Project owner = new Project();
	private Manager man = new Manager();
	private Team team = new Team();
  
    private ScheduleEvent event = new DefaultScheduleEvent();   
  
    public TaskControl() {
    	setRangeValues();
        eventModel = new DefaultScheduleModel();
    }  
      
    public void addEvent(ActionEvent actionEvent) { 
    	
        Task tmpTask;
        
        if(event.getId() == null) {
            eventModel.addEvent(event);
            tmpTask = new Task();
            tmpTask.setTmId(event.getId());
            tasks.add(tmpTask);
        }
        else {
            eventModel.updateEvent(event);
            tmpTask = getTaskByTMId(event.getId());
        }
        tmpTask.setTaskBrief(event.getTitle());
        tmpTask.setCreatedDate(event.getStartDate());
        tmpTask.setDeadLineDate(event.getEndDate());
        tmpTask.setOwnerProject(owner);
        tmpTask.setTeam(team);
        
        FacesContext context = FacesContext.getCurrentInstance();
		
        EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			tmpTask = entityManager.merge(tmpTask);
			entityManager.persist(tmpTask);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return;
		} finally {
			entityManager.close();
		}
        
        event = new DefaultScheduleEvent();  
    }  
      
    public void onEventSelect(ScheduleEntrySelectEvent selectEvent) {  
        event = selectEvent.getScheduleEvent();
    }  
      
    public void onDateSelect(DateSelectEvent selectEvent) {  
        event = new DefaultScheduleEvent("", selectEvent.getDate(), selectEvent.getDate());  
    }  
      
    public void onEventMove(ScheduleEntryMoveEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
        this.event = event.getScheduleEvent();
        addEvent(null); 
        addMessage(message);  
    }  
      
    public void onEventResize(ScheduleEntryResizeEvent event) {  
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());  
        
        this.event = event.getScheduleEvent();
        addEvent(null);   
        
        addMessage(message);  
    }  
      
    private void addMessage(FacesMessage message) {  
        FacesContext.getCurrentInstance().addMessage(null, message);  
    }

	public ScheduleModel getEventModel() {
		return eventModel;
	}

	public void setEventModel(ScheduleModel eventModel) {
		this.eventModel = eventModel;
	}

	public ScheduleEvent getEvent() {
		return event;
	}

	public void setEvent(ScheduleEvent event) {
		this.event = event;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	} 
    
	private Task getTaskByTMId(String tmId) {
		for (Task itr : tasks) {
			if (itr.getTmId().equals(tmId))
				return itr;
		}
		return null;
	}
	
	private void setRangeValues() {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Calendar cal = Calendar.getInstance();
    	
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
    	cal.set(Calendar.HOUR_OF_DAY, 0);
    	cal.clear(Calendar.MINUTE);
    	cal.clear(Calendar.SECOND);
    	cal.clear(Calendar.MILLISECOND);

    	Date tmp = cal.getTime();
    	firstDateOfSelectedRange = dateFormat.format(tmp);
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    	tmp = cal.getTime();
    	lastDateOfSelectedRange = dateFormat.format(tmp);
	}
    
	private void deployTasks() {
		eventModel.clear();
		for (Task itr : tasks) {
			ScheduleEvent event = new DefaultScheduleEvent(itr.getTaskBrief(), itr.getCreatedDate(), itr.getDeadLineDate());
			eventModel.addEvent(event);
			itr.setTmId(event.getId());
		}
	}

	public String getProjectCriteria() {
		return projectCriteria;
	}

	public void setProjectCriteria(String projectCriteria) {
		this.projectCriteria = projectCriteria;
	}

	public String getFirstDateOfSelectedRange() {
		return firstDateOfSelectedRange;
	}

	public void setFirstDateOfSelectedRange(String firstDateOfSelectedRange) {
		this.firstDateOfSelectedRange = firstDateOfSelectedRange;
	}

	public String getLastDateOfSelectedRange() {
		return lastDateOfSelectedRange;
	}

	public void setLastDateOfSelectedRange(String lastDateOfSelectedRange) {
		this.lastDateOfSelectedRange = lastDateOfSelectedRange;
	}

	public Project getOwner() {
		return owner;
	}

	public void setOwner(Project owner) {
		this.owner = owner;
	}
	
	public void loadTasks() {
    	projectCriteria = String.valueOf(owner.getId());
        EntityManager entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
		tasks = entityManager.createQuery("from Task where projectId = '" + projectCriteria +"'", Task.class).getResultList();
		//tasks = entityManager.createQuery("from Task where createdDate >= '" + firstDateOfSelectedRange + "' AND createdDate <= '" + lastDateOfSelectedRange + "' AND projectId = '" + projectCriteria +"'", Task.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        deployTasks();
	}

	public Manager getMan() {
		return man;
	}

	public void setMan(Manager man) {
		this.man = man;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	public void deleteTask() {
		Task tmpTask;

        tmpTask = getTaskByTMId(event.getId());
        
        FacesContext context = FacesContext.getCurrentInstance();
		
        EntityManager entityManager = emf.createEntityManager();

		try {
			entityManager.getTransaction().begin();
			tmpTask = entityManager.merge(tmpTask);
			entityManager.remove(tmpTask);
	        entityManager.getTransaction().commit();	
		} catch (Exception e) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), "");
	        context.addMessage(null, message);
	        return;
		} finally {
			entityManager.close();
		}
		
		eventModel.deleteEvent(event);
        
        event = new DefaultScheduleEvent();
	}
	
      
}
