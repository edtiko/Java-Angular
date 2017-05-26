
package co.expertic.training.service.user;

import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserResumeDTO;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface MechanicService {
    
     public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws Exception; 

    public List<UserResumeDTO> getAthletes() throws Exception;
    
}
