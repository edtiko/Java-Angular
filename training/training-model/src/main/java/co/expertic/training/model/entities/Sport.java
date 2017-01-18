/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "sport")
@NamedQueries({
    @NamedQuery(name = "Sport.findAll", query = "SELECT s FROM Sport s"),
    @NamedQuery(name = "Sport.findBySportId", query = "SELECT s FROM Sport s WHERE s.sportId = :sportId"),
    @NamedQuery(name = "Sport.findByName", query = "SELECT s FROM Sport s WHERE s.name = :name")})
public class Sport implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "sport_id")
    private Integer sportId;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "icon")
    private String icon;
    @OneToMany(mappedBy = "sportId")
    private Collection<UserSport> userSportCollection;
    @OneToMany(mappedBy = "sportId")
    private Collection<Activity> activityCollection;
    @Column(name = "ind_discipline_sport")
    private String indDisciplineSport;

    public Sport() {
    }

    public Sport(Integer sportId) {
        this.sportId = sportId;
    }

    public Sport(Integer sportId, String name) {
        this.sportId = sportId;
        this.name = name;
    }

    public Integer getSportId() {
        return sportId;
    }

    public void setSportId(Integer sportId) {
        this.sportId = sportId;
    }

    public String getIndDisciplineSport() {
        return indDisciplineSport;
    }

    public void setIndDisciplineSport(String indDisciplineSport) {
        this.indDisciplineSport = indDisciplineSport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @JsonIgnore
    public Collection<UserSport> getUserSportCollection() {
        return userSportCollection;
    }

    public void setUserSportCollection(Collection<UserSport> userSportCollection) {
        this.userSportCollection = userSportCollection;
    }
    @JsonIgnore
    public Collection<Activity> getActivityCollection() {
        return activityCollection;
    }

    public void setActivityCollection(Collection<Activity> activityCollection) {
        this.activityCollection = activityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sportId != null ? sportId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sport)) {
            return false;
        }
        Sport other = (Sport) object;
        if ((this.sportId == null && other.sportId != null) || (this.sportId != null && !this.sportId.equals(other.sportId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.Sport[ sportId=" + sportId + " ]";
    }
    
}
