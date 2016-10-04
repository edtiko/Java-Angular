package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.BikeTypeDao;
import co.com.expertla.training.model.dto.BikeTypeDTO;
import co.com.expertla.training.model.entities.BikeType;
import co.com.expertla.training.service.configuration.BikeTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* BikeType Service Impl <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("bikeTypeService")
@Transactional
public class BikeTypeServiceImpl implements BikeTypeService {

    @Autowired
    private BikeTypeDao bikeTypeDao;

    @Override
    public BikeType create(BikeType bikeType) throws Exception {
        return bikeTypeDao.create(bikeType);
    }

    @Override
    public BikeType store(BikeType bikeType) throws Exception {
       return bikeTypeDao.merge(bikeType);
    }

    @Override
    public void remove(BikeType bikeType) throws Exception {
        bikeTypeDao.remove(bikeType, bikeType.getBikeTypeId());
    }

    @Override
    public List<BikeType> findAll() throws Exception {
        return bikeTypeDao.findAll();
    }

    @Override
    public List<BikeType> findAllActive() throws Exception {
        return bikeTypeDao.findAllActive();
    }

    @Override
    public List<BikeTypeDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return bikeTypeDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<BikeType> findByBikeType(BikeType bikeType) throws Exception {
        return bikeTypeDao.findByBikeType(bikeType);
    }

    @Override
    public List<BikeType> findByFiltro(BikeType bikeType) throws Exception {
        return bikeTypeDao.findByFiltro(bikeType);
    }

    @Override
    public List<BikeType> findByName(BikeType bikeType) throws Exception {
        return bikeTypeDao.findByName(bikeType);
    }
}