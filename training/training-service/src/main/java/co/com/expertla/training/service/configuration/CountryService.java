package co.com.expertla.training.service.configuration;

import co.com.expertla.training.model.entities.Country;
import java.util.List;

/**
* Country Service <br>
* Info. Creación: <br>
* fecha 19/08/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
public interface CountryService {
    

    /**
     * Crea country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return 
     * @throws Exception 
     */
    public Country create(Country country) throws Exception;
    /**
     * Modifica country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @throws Exception 
     */
    public Country store(Country country) throws Exception;
    /**
     * Elimina country<br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return 
     * @throws Exception 
     */
    public void remove(Country country) throws Exception;
    /**
     * Obtiene todos los registros de country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Country> findAll() throws Exception;

    /**
     * Obtiene todos los registros activos de country <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @return
     * @throws Exception 
     */
    public List<Country> findAllActive() throws Exception;
    /**
     * Obtiene todos los registros de country por su id <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return
     * @throws Exception 
     */
    public List<Country> findByCountry(Country country) throws Exception;   

    /**
     * Obtiene todos los registros de country por el filtro del usuario <br>
     * Info. Creación: <br>
     * fecha 19/08/2016 <br>
     * @author Andres Felipe Lopez Rodriguez
     * @param country
     * @return
     * @throws Exception 
     */
    public List<Country> findByFiltro(Country country) throws Exception; 



    
}