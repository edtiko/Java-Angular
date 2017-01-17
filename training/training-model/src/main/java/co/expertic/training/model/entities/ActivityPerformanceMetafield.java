package co.expertic.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
* ActivityPerformanceMetafield <br>
* Creation Date : <br>
* date 15/09/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "activity_performance_metafield")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ActivityPerformanceMetafield.findAll", query = "SELECT a FROM ActivityPerformanceMetafield a"),
    @NamedQuery(name = "ActivityPerformanceMetafield.findByActivityPerformanceMetafieldId", query = "SELECT a FROM ActivityPerformanceMetafield a WHERE a.activityPerformanceMetafieldId = :activityPerformanceMetafieldId"),
    @NamedQuery(name = "ActivityPerformanceMetafield.findByName", query = "SELECT a FROM ActivityPerformanceMetafield a WHERE a.name = :name"),
    @NamedQuery(name = "ActivityPerformanceMetafield.findByLabel", query = "SELECT a FROM ActivityPerformanceMetafield a WHERE a.label = :label"),
    @NamedQuery(name = "ActivityPerformanceMetafield.findByDataType", query = "SELECT a FROM ActivityPerformanceMetafield a WHERE a.dataType = :dataType")})
public class ActivityPerformanceMetafield implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "activity_performance_metafiel_activity_performance_metafiel_seq", sequenceName = "activity_performance_metafiel_activity_performance_metafiel_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "activity_performance_metafiel_activity_performance_metafiel_seq")
    @Basic(optional = false)
    @Column(name = "activity_performance_metafield_id")
    private Integer activityPerformanceMetafieldId;
    @Column(name = "name")
    private String name;
    @Column(name = "label")
    private String label;
    @Column(name = "data_type")
    private String dataType;
    @OneToMany(mappedBy = "activityPerformanceMetafieldId")
    private Collection<UserActivityPerformance> userActivityPerformanceCollection;

    public ActivityPerformanceMetafield() {
    }

    public ActivityPerformanceMetafield(Integer activityPerformanceMetafieldId) {
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
    }

    public Integer getActivityPerformanceMetafieldId() {
        return activityPerformanceMetafieldId;
    }

    public void setActivityPerformanceMetafieldId(Integer activityPerformanceMetafieldId) {
        this.activityPerformanceMetafieldId = activityPerformanceMetafieldId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<UserActivityPerformance> getUserActivityPerformanceCollection() {
        return userActivityPerformanceCollection;
    }

    public void setUserActivityPerformanceCollection(Collection<UserActivityPerformance> userActivityPerformanceCollection) {
        this.userActivityPerformanceCollection = userActivityPerformanceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (activityPerformanceMetafieldId != null ? activityPerformanceMetafieldId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActivityPerformanceMetafield)) {
            return false;
        }
        ActivityPerformanceMetafield other = (ActivityPerformanceMetafield) object;
        if ((this.activityPerformanceMetafieldId == null && other.activityPerformanceMetafieldId != null) || (this.activityPerformanceMetafieldId != null && !this.activityPerformanceMetafieldId.equals(other.activityPerformanceMetafieldId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ActivityPerformanceMetafield[ activityPerformanceMetafieldId=" + activityPerformanceMetafieldId + " ]";
    }

}
