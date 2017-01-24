package co.expertic.training.model.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
* VisibleFieldsUser <br>
* Creation Date : <br>
* date 10/08/2016 <br>
* @author Angela Ramírez
**/
@Entity
@Table(name = "visible_fields_user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VisibleFieldsUser.findAll", query = "SELECT v FROM VisibleFieldsUser v"),
    @NamedQuery(name = "VisibleFieldsUser.findByVisibleFieldsUserId", query = "SELECT v FROM VisibleFieldsUser v WHERE v.visibleFieldsUserId = :visibleFieldsUserId"),
    @NamedQuery(name = "VisibleFieldsUser.findByTableName", query = "SELECT v FROM VisibleFieldsUser v WHERE v.tableName = :tableName"),
    @NamedQuery(name = "VisibleFieldsUser.findByColumnName", query = "SELECT v FROM VisibleFieldsUser v WHERE v.columnName = :columnName"),
    @NamedQuery(name = "VisibleFieldsUser.findByUserId", query = "SELECT v FROM VisibleFieldsUser v WHERE v.userId = :userId")})
public class VisibleFieldsUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "visible_fields_user_id")
    private Integer visibleFieldsUserId;
    @Column(name = "table_name")
    private String tableName;
    @Column(name = "column_name")
    private String columnName;
    @Column(name = "user_id")
    private Integer userId;

    public VisibleFieldsUser() {
    }

    public VisibleFieldsUser(Integer visibleFieldsUserId) {
        this.visibleFieldsUserId = visibleFieldsUserId;
    }

    public Integer getVisibleFieldsUserId() {
        return visibleFieldsUserId;
    }

    public void setVisibleFieldsUserId(Integer visibleFieldsUserId) {
        this.visibleFieldsUserId = visibleFieldsUserId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (visibleFieldsUserId != null ? visibleFieldsUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VisibleFieldsUser)) {
            return false;
        }
        VisibleFieldsUser other = (VisibleFieldsUser) object;
        if ((this.visibleFieldsUserId == null && other.visibleFieldsUserId != null) || (this.visibleFieldsUserId != null && !this.visibleFieldsUserId.equals(other.visibleFieldsUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.com.expertla.training.model.entities.VisibleFieldsUser[ visibleFieldsUserId=" + visibleFieldsUserId + " ]";
    }

}
