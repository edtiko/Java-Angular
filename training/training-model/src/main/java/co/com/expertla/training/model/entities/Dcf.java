package co.com.expertla.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
* Dcf <br>
* Creation Date : <br>
* date 21/07/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "dcf")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dcf.findAll", query = "SELECT d FROM Dcf d"),
    @NamedQuery(name = "Dcf.findByDcfId", query = "SELECT d FROM Dcf d WHERE d.dcfId = :dcfId"),
    @NamedQuery(name = "Dcf.findByPattern", query = "SELECT d FROM Dcf d WHERE d.pattern = :pattern"),
    @NamedQuery(name = "Dcf.findBySessions", query = "SELECT d FROM Dcf d WHERE d.sessions = :sessions")})
public class Dcf implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "dcf_id")
    private Integer dcfId;
    @Basic(optional = false)
    @Column(name = "pattern")
    private String pattern;
    @Basic(optional = false)
    @Column(name = "sessions")
    private int sessions;
    @JoinColumn(name = "modality_id", referencedColumnName = "modality_id")
    @ManyToOne
    private Modality modalityId;
    @JoinColumn(name = "objetive_id", referencedColumnName = "objetive_id")
    @ManyToOne
    private Objetive objetiveId;

    public Dcf() {
    }

    public Dcf(Integer dcfId) {
        this.dcfId = dcfId;
    }

    public Dcf(Integer dcfId, String pattern, int sessions) {
        this.dcfId = dcfId;
        this.pattern = pattern;
        this.sessions = sessions;
    }

    public Integer getDcfId() {
        return dcfId;
    }

    public void setDcfId(Integer dcfId) {
        this.dcfId = dcfId;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public int getSessions() {
        return sessions;
    }

    public void setSessions(int sessions) {
        this.sessions = sessions;
    }

    public Modality getModalityId() {
        return modalityId;
    }

    public void setModalityId(Modality modalityId) {
        this.modalityId = modalityId;
    }

    public Objetive getObjetiveId() {
        return objetiveId;
    }

    public void setObjetiveId(Objetive objetiveId) {
        this.objetiveId = objetiveId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dcfId != null ? dcfId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dcf)) {
            return false;
        }
        Dcf other = (Dcf) object;
        if ((this.dcfId == null && other.dcfId != null) || (this.dcfId != null && !this.dcfId.equals(other.dcfId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Dcf[ dcfId=" + dcfId + " ]";
    }

}
