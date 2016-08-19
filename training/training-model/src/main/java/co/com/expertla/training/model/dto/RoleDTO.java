package co.com.expertla.training.model.dto;

import java.io.Serializable;

public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer roleId;
    private String name;

    public RoleDTO() {
    }

    public RoleDTO(Integer roleId) {
        this.roleId = roleId;
    }

    public RoleDTO(Integer roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
