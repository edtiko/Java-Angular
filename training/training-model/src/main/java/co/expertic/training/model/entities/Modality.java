package co.expertic.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
* Modality <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "modality")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Modality.findAll", query = "SELECT m FROM Modality m"),
    @NamedQuery(name = "Modality.findByModalityId", query = "SELECT m FROM Modality m WHERE m.modalityId = :modalityId"),
    @NamedQuery(name = "Modality.findByName", query = "SELECT m FROM Modality m WHERE m.name = :name")})
public class Modality implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "modality_seq", sequenceName = "modality_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "modality_seq")
    @Column(name = "modality_id")
    private Integer modalityId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "discipline_id", referencedColumnName = "discipline_id")
    @ManyToOne
    private Discipline disciplineId;
    @OneToMany(mappedBy = "modalityId")
    private Collection<Dcf> dcfCollection;

    @Column(name = "state_id")
    private Short stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_update")
    @Temporal(TemporalType.DATE)
    private Date lastUpdate;
    @Column(name = "user_create")
    private Integer userCreate;
    @Column(name = "user_update")
    private Integer userUpdate;

    public Modality() {
    }

    public Modality(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public Modality(Integer modalityId, String name) {
        this.modalityId = modalityId;
        this.name = name;
    }

    public Integer getModalityId() {
        return modalityId;
    }

    public void setModalityId(Integer modalityId) {
        this.modalityId = modalityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Discipline getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Discipline disciplineId) {
        this.disciplineId = disciplineId;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Dcf> getDcfCollection() {
        return dcfCollection;
    }

    public void setDcfCollection(Collection<Dcf> dcfCollection) {
        this.dcfCollection = dcfCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (modalityId != null ? modalityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Modality)) {
            return false;
        }
        Modality other = (Modality) object;
        if ((this.modalityId == null && other.modalityId != null) || (this.modalityId != null && !this.modalityId.equals(other.modalityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Modality[ modalityId=" + modalityId + " ]";
    }


    public Short getStateId() {
        return stateId;
    }

    public void setStateId(Short stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

}
