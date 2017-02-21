package co.expertic.training.service.user;

import co.expertic.training.model.dto.ChartDTO;
import co.expertic.training.model.dto.ProgressReportDTO;
import co.expertic.training.model.dto.UserActivityPerformanceDTO;
import co.expertic.training.model.entities.UserActivityPerformance;
import java.util.Date;
import java.util.List;

/**
* UserActivityPerformance Service <br>
* Info. Creación: <br>
* fecha Sep 15, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface UserActivityPerformanceService {
    

    /**
     * Crea userActivityPerformance <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return 
     * @throws Exception 
     */
    public UserActivityPerformance create(UserActivityPerformance userActivityPerformance) throws Exception;
    /**
     * Modifica userActivityPerformance <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return 
     * @throws Exception 
     */
    public UserActivityPerformance store(UserActivityPerformance userActivityPerformance) throws Exception;
    /**
     * Elimina userActivityPerformance<br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @throws Exception 
     */
    public void remove(UserActivityPerformance userActivityPerformance) throws Exception;
    /**
     * Obtiene todos los registros de userActivityPerformance <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformance> findAll() throws Exception;
    
    /**
     * Obtiene todos los registros activos de userActivityPerformance <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformance> findAllActive() throws Exception;

    /**
     * Obtiene todos los registros de userActivityPerformance paginados <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param first
     * @param max
     * @param order
     * @param filter
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformanceDTO> findPaginate(int first, int max, String order, String filter) throws Exception;
    
    /**
     * Obtiene todos los registros de userActivityPerformance paginados <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformance> findByUserActivityPerformance(UserActivityPerformance userActivityPerformance) throws Exception;   

    /**
     * Obtiene todos los registros de userActivityPerformance por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param userActivityPerformance
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformance> findByFiltro(UserActivityPerformance userActivityPerformance) throws Exception; 

    /**
     * Obtiene todos los registros de userActivityPerformance por rango de fechas y user Id <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param fromDate
     * @param toDate
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformanceDTO> findByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception;
    
    /**
     * Obtiene todos los registros de userActivityPerformance por rango de fechas y user Id and metafield<br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param fromDate
     * @param toDate
     * @param userId
     * @param metafieldId
     * @return
     * @throws Exception 
     */
    public List<ChartDTO> findByDateRangeAndUserIdAndMetaField( Date fromDate, Date toDate, Integer userId, Integer metafieldId) throws Exception;

    /**
     * Obtiene todos los registros de userActivityPerformance por rango de fechas y user Id and metafield by days and weekly or monthly <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param fromDate
     * @param toDate
     * @param userId
     * @param metafieldId
     * @param days
     * @param weekly
     * @return
     * @throws Exception 
     */
    public List<ChartDTO> findByDateRangeAndUserIdAndMetaField( Date fromDate, Date toDate, Integer userId, Integer metafieldId, Integer days, boolean weekly) throws Exception;
    
     /**
     * Obtiene todos los registros de userActivityPerformance por rango de fechas y user Id y count activities week <br>
     * Info. Creación: <br>
     * fecha Sep 15, 2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param fromDate
     * @param toDate
     * @param userId
     * @return
     * @throws Exception 
     */
    public List<ChartDTO> findActivitiesByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception;
    
    public List<UserActivityPerformanceDTO> findConsolidationByDateRangeAndUserId( Date fromDate, Date toDate, Integer userId) throws Exception;

    public List<ProgressReportDTO> getProgressReport(Integer date, Integer activity, Integer userId) throws Exception;
    
}