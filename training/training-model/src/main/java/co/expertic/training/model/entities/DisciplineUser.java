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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "discipline_user")
@NamedQueries({
    @NamedQuery(name = "DisciplineUser.findAll", query = "SELECT d FROM DisciplineUser d"),
    @NamedQuery(name = "DisciplineUser.findByDisciplineUserId", query = "SELECT d FROM DisciplineUser d WHERE d.disciplineUserId = :disciplineUserId")})
public class DisciplineUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)@SequenceGenerator(name = "discipline_user_seq", sequenceName = "discipline_user_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discipline_user_seq")
    @Column(name = "discipline_user_id")
    private Integer disciplineUserId;
    @JoinColumn(name = "discipline", referencedColumnName = "discipline_id")
    @ManyToOne
    private Discipline discipline;
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @ManyToOne
    private User userId;

    public DisciplineUser() {
    }

    public DisciplineUser(Integer disciplineUserId) {
        this.disciplineUserId = disciplineUserId;
    }

    public Integer getDisciplineUserId() {
        return disciplineUserId;
    }

    public void setDisciplineUserId(Integer disciplineUserId) {
        this.disciplineUserId = disciplineUserId;
    }

    public Discipline getDiscipline() {
        return discipline;
    }

    public void setDiscipline(Discipline discipline) {
        this.discipline = discipline;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (disciplineUserId != null ? disciplineUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DisciplineUser)) {
            return false;
        }
        DisciplineUser other = (DisciplineUser) object;
        if ((this.disciplineUserId == null && other.disciplineUserId != null) || (this.disciplineUserId != null && !this.disciplineUserId.equals(other.disciplineUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.DisciplineUser[ disciplineUserId=" + disciplineUserId + " ]";
    }
    
}
