package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.plan.DcfDao;
import co.com.expertla.training.model.dto.DcfDTO;
import co.com.expertla.training.model.entities.Dcf;
import co.com.expertla.training.service.configuration.DcfService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Dcf Service Impl <br>
* Info. Creación: <br>
* fecha Sep 6, 2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("dcfService")
@Transactional
public class DcfServiceImpl implements DcfService {

    @Autowired
    private DcfDao dcfDao;

    @Override
    public Dcf create(Dcf dcf) throws Exception {
        return dcfDao.create(dcf);
    }

    @Override
    public Dcf store(Dcf dcf) throws Exception {
       return dcfDao.merge(dcf);
    }

    @Override
    public void remove(Dcf dcf) throws Exception {
        dcfDao.remove(dcf, dcf.getDcfId());
    }

    @Override
    public List<Dcf> findAll() throws Exception {
        return dcfDao.findAll();
    }

    @Override
    public List<Dcf> findAllActive() throws Exception {
        return dcfDao.findAllActive();
    }

    @Override
    public List<DcfDTO> findPaginate(int first, int max, String order) throws Exception {
        return dcfDao.findPaginate(first, max, order);
    }

    @Override
    public List<Dcf> findByDcf(Dcf dcf) throws Exception {
        return dcfDao.findByDcf(dcf);
    }

    @Override
    public List<Dcf> findByFiltro(Dcf dcf) throws Exception {
        return dcfDao.findByFiltro(dcf);
    }

}