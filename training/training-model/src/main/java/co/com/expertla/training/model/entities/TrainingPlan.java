package co.com.expertla.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "training_plan")
@NamedQueries({
    @NamedQuery(name = "TrainingPlan.findAll", query = "SELECT t FROM TrainingPlan t"),
    @NamedQuery(name = "TrainingPlan.findByTrainingPlanId", query = "SELECT t FROM TrainingPlan t WHERE t.trainingPlanId = :trainingPlanId"),
    @NamedQuery(name = "TrainingPlan.findByName", query = "SELECT t FROM TrainingPlan t WHERE t.name = :name"),
    @NamedQuery(name = "TrainingPlan.findByDescription", query = "SELECT t FROM TrainingPlan t WHERE t.description = :description"),
    @NamedQuery(name = "TrainingPlan.findByDuration", query = "SELECT t FROM TrainingPlan t WHERE t.duration = :duration"),
    @NamedQuery(name = "TrainingPlan.findByCreationDate", query = "SELECT t FROM TrainingPlan t WHERE t.creationDate = :creationDate"),
    @NamedQuery(name = "TrainingPlan.findByEndDate", query = "SELECT t FROM TrainingPlan t WHERE t.endDate = :endDate")})
public class TrainingPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)@SequenceGenerator(name = "training_plan_seq", sequenceName = "training_plan_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_plan_seq")
    @Column(name = "training_plan_id")
    private Integer trainingPlanId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "duration")
    private BigDecimal duration;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "state_id")
    private Short stateId;
    @OneToMany(mappedBy = "trainingPlanId")
    private Collection<TrainingPlanUser> trainingPlanUserCollection;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;
    @Column(name = "price")
    private Double price;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trainingPlanId")
    private Collection<TrainingPlanCharact> trainingPlanCharactCollection;
    @Column(name = "type_plan")
    private String typePlan;//1= plan de entrenamiento, 2= plataforma
    

    public TrainingPlan() {
    }

    public TrainingPlan(Integer trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public TrainingPlan(Integer trainingPlanId, String name, BigDecimal duration, Date creationDate, Date endDate) {
        this.trainingPlanId = trainingPlanId;
        this.name = name;
        this.duration = duration;
        this.creationDate = creationDate;
        this.endDate = endDate;
    }

    public Integer getTrainingPlanId() {
        return trainingPlanId;
    }

    public void setTrainingPlanId(Integer trainingPlanId) {
        this.trainingPlanId = trainingPlanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Integer getUserCreate() {
        return userCreate;
    }

    public void setUserCreate(Integer userCreate) {
        this.userCreate = userCreate;
    }

    public Integer getUserUpdate() {
        return userUpdate;
    }

    public void setUserUpdate(Integer userUpdate) {
        this.userUpdate = userUpdate;
    }
    @JsonIgnore
    public Collection<TrainingPlanUser> getTrainingPlanUserCollection() {
        return trainingPlanUserCollection;
    }

    public void setTrainingPlanUserCollection(Collection<TrainingPlanUser> trainingPlanUserCollection) {
        this.trainingPlanUserCollection = trainingPlanUserCollection;
    }
    @JsonIgnore
    public Collection<TrainingPlanCharact> getTrainingPlanCharactCollection() {
        return trainingPlanCharactCollection;
    }

    public void setTrainingPlanCharactCollection(Collection<TrainingPlanCharact> trainingPlanCharactCollection) {
        this.trainingPlanCharactCollection = trainingPlanCharactCollection;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getTypePlan() {
        return typePlan;
    }

    public void setTypePlan(String typePlan) {
        this.typePlan = typePlan;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (trainingPlanId != null ? trainingPlanId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TrainingPlan)) {
            return false;
        }
        TrainingPlan other = (TrainingPlan) object;
        if ((this.trainingPlanId == null && other.trainingPlanId != null) || (this.trainingPlanId != null && !this.trainingPlanId.equals(other.trainingPlanId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.TrainingPlan[ trainingPlanId=" + trainingPlanId + " ]";
    }

  
}
