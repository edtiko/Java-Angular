package co.expertic.training.enums;

/**
 * Enum for a record role <br>
 * Creation Info:  <br>
 * date 02/09/2016  <br>
 * @author Andres Felipe Lopez
 */
public enum RoleEnum {
    ATLETA(1), COACH(2), ADMIN(3), COACH_INTERNO(4), ESTRELLA(5), SUPERVISOR(6);
    private Integer id;
    
    private RoleEnum(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    
    
}
