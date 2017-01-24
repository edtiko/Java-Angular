/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Andres Lopez
 */
@Entity
@Table(name = "replace_activity")
@XmlRootElement
public class ReplaceActivity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "replace_activity_id")
    private Integer replaceActivityId;
    @JoinColumn(name = "activity_id", referencedColumnName = "activity_id")
    @ManyToOne
    private Activity activityId;
    @JoinColumn(name = "replace_id", referencedColumnName = "activity_id")
    @ManyToOne
    private Activity replaceId;

    public ReplaceActivity() {
    }

    public ReplaceActivity(Integer replaceActivityId) {
        this.replaceActivityId = replaceActivityId;
    }

    public Integer getReplaceActivityId() {
        return replaceActivityId;
    }

    public void setReplaceActivityId(Integer replaceActivityId) {
        this.replaceActivityId = replaceActivityId;
    }

    public Activity getReplaceId() {
        return replaceId;
    }

    public void setReplaceId(Activity replaceId) {
        this.replaceId = replaceId;
    }


    public Activity getActivityId() {
        return activityId;
    }

    public void setActivityId(Activity activityId) {
        this.activityId = activityId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (replaceActivityId != null ? replaceActivityId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReplaceActivity)) {
            return false;
        }
        ReplaceActivity other = (ReplaceActivity) object;
        if ((this.replaceActivityId == null && other.replaceActivityId != null) || (this.replaceActivityId != null && !this.replaceActivityId.equals(other.replaceActivityId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ReplaceActivity[ replaceActivityId=" + replaceActivityId + " ]";
    }
    
}
