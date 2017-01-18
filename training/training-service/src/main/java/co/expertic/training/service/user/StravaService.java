package co.expertic.training.service.user;

import co.expertic.training.model.dto.UserDTO;
import co.expertic.training.model.entities.UserActivityPerformance;
import co.expertic.training.model.util.CurlService;
import java.io.IOException;
import java.util.List;

/**
 * Service for Strava <br>
 * Creation Date : <br>
 * date 16/11/2016 <br>
 *
 * @author Andres Felipe Lopez
 */
public interface StravaService {
    public static final String CLIENT_ID = "14512";
    public static final String CLIENT_SECRET = "aa52ba6596961f92a9c03b79b484e31e4b88af2c";
    public static final String STRAVA_URL = "https://www.strava.com/";
    
    public static final Integer ACTIVIDAD_META = 1;
    public static final Integer CALORIAS_META = 2;
    public static final Integer DISTANCIA_TOTAL_META = 3;
    public static final Integer FRECUENCIA_CARDIACA_MAXIMA_META = 4;
    public static final Integer FRECUENCIA_CARDIACA_MEDIA_META = 5;
    public static final Integer RITMO_MEDIO_META = 6;
    public static final Integer POTENCIA_MAXIMA_META = 7;
    public static final Integer POTENCIA_MEDIA_META = 8;
    public static final Integer RIDE = 1;
    public static final Integer RUN = 3;
    
    
    
    /**
     * Importa los datos de strava a la aplicación por usuario <br>
     * Creation Date : <br>
     * date 16/11/2016 <br>
     * @author Andres Felipe Lopez
     * @param userId
     * @throws Exception
     */
    public void importStravaByUser(Integer userId) throws Exception;
    
    /**
     * 
     * @param curlService
     * @param code
     * @return
     * @throws IOException 
     */
    public String getToken(CurlService curlService, String code) throws IOException;
    
    /**
     * 
     * @param curlService
     * @param token
     * @param activityId
     * @param userId
     * @return
     * @throws Exception 
     */
    public UserActivityPerformance getCaloriesMetaField(CurlService curlService,
            String token, String activityId, Integer userId)throws Exception;
    
    /**
     * 
     * @param curlService
     * @param token
     * @param response
     * @param objUser
     * @return
     * @throws Exception 
     */
    public List<UserActivityPerformance> getMetaField(CurlService curlService,
            String token,
            String response, UserDTO objUser) throws Exception;
}
