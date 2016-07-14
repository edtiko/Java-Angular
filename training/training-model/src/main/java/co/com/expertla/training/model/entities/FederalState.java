/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Edwin G
 */
@Entity
@Table(name = "federal_state")
@NamedQueries({
    @NamedQuery(name = "FederalState.findAll", query = "SELECT f FROM FederalState f"),
    @NamedQuery(name = "FederalState.findByFederalStateId", query = "SELECT f FROM FederalState f WHERE f.federalStateId = :federalStateId"),
    @NamedQuery(name = "FederalState.findByName", query = "SELECT f FROM FederalState f WHERE f.name = :name")})
public class FederalState implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "federal_state_id")
    private Integer federalStateId;
    @Column(name = "name")
    private String name;
    @JoinColumn(name = "country_id", referencedColumnName = "country_id")
    @ManyToOne
    private Country countryId;
    @OneToMany(mappedBy = "federalStateId")
    private Collection<City> cityCollection;

    public FederalState() {
    }

    public FederalState(Integer federalStateId) {
        this.federalStateId = federalStateId;
    }

    public Integer getFederalStateId() {
        return federalStateId;
    }

    public void setFederalStateId(Integer federalStateId) {
        this.federalStateId = federalStateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountryId() {
        return countryId;
    }

    public void setCountryId(Country countryId) {
        this.countryId = countryId;
    }

    public Collection<City> getCityCollection() {
        return cityCollection;
    }

    public void setCityCollection(Collection<City> cityCollection) {
        this.cityCollection = cityCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (federalStateId != null ? federalStateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FederalState)) {
            return false;
        }
        FederalState other = (FederalState) object;
        if ((this.federalStateId == null && other.federalStateId != null) || (this.federalStateId != null && !this.federalStateId.equals(other.federalStateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.FederalState[ federalStateId=" + federalStateId + " ]";
    }
    
}
