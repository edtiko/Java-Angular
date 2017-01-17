package co.expertic.training.service.impl.configuration;

import co.expertic.training.dao.configuration.BrandDao;
import co.expertic.training.model.dto.BrandDTO;
import co.expertic.training.model.entities.Brand;
import co.expertic.training.service.configuration.BrandService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Brand Service Impl <br>
* Info. Creación: <br>
* fecha Sep 9, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandDao brandDao;

    @Override
    public Brand create(Brand brand) throws Exception {
        return brandDao.create(brand);
    }

    @Override
    public Brand store(Brand brand) throws Exception {
       return brandDao.merge(brand);
    }

    @Override
    public void remove(Brand brand) throws Exception {
        brandDao.remove(brand, brand.getBrandId());
    }

    @Override
    public List<Brand> findAll() throws Exception {
        return brandDao.findAll();
    }

    @Override
    public List<Brand> findAllActive() throws Exception {
        return brandDao.findAllActive();
    }

    @Override
    public List<BrandDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return brandDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<Brand> findByBrand(Brand brand) throws Exception {
        return brandDao.findByBrand(brand);
    }

    @Override
    public List<Brand> findByFiltro(Brand brand) throws Exception {
        return brandDao.findByFiltro(brand);
    }

    @Override
    public List<Brand> findByName(Brand brand) throws Exception {
        return brandDao.findByName(brand);
    }
}