/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.model.dto;

import co.com.expertla.training.enums.RoleEnum;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andres Lopez
 */
public class RoleEnumMovilDTO {
    private String codigo;
    private String name;

    public RoleEnumMovilDTO(String codigo, String name) {
        this.codigo = codigo;
        this.name = name;
    }

    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static List<RoleEnumMovilDTO> getRoleEnum() {
        List<RoleEnumMovilDTO> list = new ArrayList<>();
        list.add(new RoleEnumMovilDTO(RoleEnum.ATLETA.getId().toString(), "ATLETA"));
        list.add(new RoleEnumMovilDTO(RoleEnum.COACH.getId().toString(), "COACH"));
        list.add(new RoleEnumMovilDTO(RoleEnum.COACH_INTERNO.getId().toString(), "COACH_INTERNO"));
        list.add(new RoleEnumMovilDTO(RoleEnum.ESTRELLA.getId().toString(), "ESTRELLA"));
        list.add(new RoleEnumMovilDTO(RoleEnum.SUPERVISOR.getId().toString(), "SUPERVISOR"));
        list.add(new RoleEnumMovilDTO(RoleEnum.ADMIN.getId().toString(), "ADMIN"));
        return list;
    }
}
