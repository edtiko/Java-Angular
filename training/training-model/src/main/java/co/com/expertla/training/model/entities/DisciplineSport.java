package co.com.expertla.training.model.entities;

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
@Table(name = "discipline_sport")
public class DisciplineSport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)@SequenceGenerator(name = "discipline_sport_discipline_sport_id_seq", sequenceName = "discipline_sport_discipline_sport_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "discipline_sport_discipline_sport_id_seq")
    @Column(name = "discipline_sport_id")
    private Integer disciplineSportId;
    @JoinColumn(name = "sport_id", referencedColumnName = "sport_id")
    @ManyToOne
    private Sport sportId;
    @JoinColumn(name = "discipine_id", referencedColumnName = "discipine_id")
    @ManyToOne
    private Discipline disciplineId;

    public DisciplineSport() {
    }

    public Integer getDisciplineSportId() {
        return disciplineSportId;
    }

    public void setDisciplineSportId(Integer disciplineSportId) {
        this.disciplineSportId = disciplineSportId;
    }

    public Discipline getDisciplineId() {
        return disciplineId;
    }

    public void setDisciplineId(Discipline disciplineId) {
        this.disciplineId = disciplineId;
    }


    public Sport getSportId() {
        return sportId;
    }

    public void setSportId(Sport sportId) {
        this.sportId = sportId;
    }
}
