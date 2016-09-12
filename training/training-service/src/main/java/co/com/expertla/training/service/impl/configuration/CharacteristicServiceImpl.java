package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.CharacteristicDao;
import co.com.expertla.training.model.dto.CharacteristicDTO;
import co.com.expertla.training.model.entities.Characteristic;
import co.com.expertla.training.service.configuration.CharacteristicService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Characteristic Service Impl <br>
* Info. Creación: <br>
* fecha 8/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("characteristicService")
@Transactional
public class CharacteristicServiceImpl implements CharacteristicService {

    @Autowired
    private CharacteristicDao characteristicDao;

    @Override
    public Characteristic create(Characteristic characteristic) throws Exception {
        return characteristicDao.create(characteristic);
    }

    @Override
    public Characteristic store(Characteristic characteristic) throws Exception {
       return characteristicDao.merge(characteristic);
    }

    @Override
    public void remove(Characteristic characteristic) throws Exception {
        characteristicDao.remove(characteristic, characteristic.getCharacteristicId());
    }

    @Override
    public List<Characteristic> findAll() throws Exception {
        return characteristicDao.findAll();
    }

    @Override
    public List<Characteristic> findAllActive() throws Exception {
        return characteristicDao.findAllActive();
    }

    @Override
    public List<CharacteristicDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return characteristicDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<Characteristic> findByCharacteristic(Characteristic characteristic) throws Exception {
        return characteristicDao.findByCharacteristic(characteristic);
    }

    @Override
    public List<Characteristic> findByFiltro(Characteristic characteristic) throws Exception {
        return characteristicDao.findByFiltro(characteristic);
    }

    @Override
    public List<Characteristic> findByName(Characteristic characteristic) throws Exception {
        return characteristicDao.findByName(characteristic);
    }
}