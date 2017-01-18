package co.expertic.training.model.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
* ColourIndicator <br>
* Creation Date : <br>
* date 14/09/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "colour_indicator")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ColourIndicator.findAll", query = "SELECT c FROM ColourIndicator c"),
    @NamedQuery(name = "ColourIndicator.findByColourIndicatorId", query = "SELECT c FROM ColourIndicator c WHERE c.colourIndicatorId = :colourIndicatorId"),
    @NamedQuery(name = "ColourIndicator.findByName", query = "SELECT c FROM ColourIndicator c WHERE c.name = :name"),
    @NamedQuery(name = "ColourIndicator.findByColour", query = "SELECT c FROM ColourIndicator c WHERE c.colour = :colour"),
    @NamedQuery(name = "ColourIndicator.findByHoursSpent", query = "SELECT c FROM ColourIndicator c WHERE c.hoursSpent = :hoursSpent"),
    @NamedQuery(name = "ColourIndicator.findByStateId", query = "SELECT c FROM ColourIndicator c WHERE c.stateId = :stateId"),
    @NamedQuery(name = "ColourIndicator.findByCreationDate", query = "SELECT c FROM ColourIndicator c WHERE c.creationDate = :creationDate"),
    @NamedQuery(name = "ColourIndicator.findByColourOrder", query = "SELECT c FROM ColourIndicator c WHERE c.colourOrder = :colourOrder")})
public class ColourIndicator implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "colour_indicator_id")
    private Integer colourIndicatorId;
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "colour")
    private String colour;
    @Basic(optional = false)
    @Column(name = "hours_spent")
    private int hoursSpent;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "colour_order")
    private Integer colourOrder;

    public ColourIndicator() {
    }

    public ColourIndicator(Integer colourIndicatorId) {
        this.colourIndicatorId = colourIndicatorId;
    }

    public ColourIndicator(Integer colourIndicatorId, String colour, int hoursSpent) {
        this.colourIndicatorId = colourIndicatorId;
        this.colour = colour;
        this.hoursSpent = hoursSpent;
    }

    public Integer getColourIndicatorId() {
        return colourIndicatorId;
    }

    public void setColourIndicatorId(Integer colourIndicatorId) {
        this.colourIndicatorId = colourIndicatorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public int getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(int hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getColourOrder() {
        return colourOrder;
    }

    public void setColourOrder(Integer colourOrder) {
        this.colourOrder = colourOrder;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (colourIndicatorId != null ? colourIndicatorId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColourIndicator)) {
            return false;
        }
        ColourIndicator other = (ColourIndicator) object;
        if ((this.colourIndicatorId == null && other.colourIndicatorId != null) || (this.colourIndicatorId != null && !this.colourIndicatorId.equals(other.colourIndicatorId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.ColourIndicator[ colourIndicatorId=" + colourIndicatorId + " ]";
    }

}
