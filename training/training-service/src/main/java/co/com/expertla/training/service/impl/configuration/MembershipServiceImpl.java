package co.com.expertla.training.service.impl.configuration;

import co.com.expertla.training.dao.configuration.MembershipDao;
import co.com.expertla.training.model.dto.MembershipDTO;
import co.com.expertla.training.model.entities.Membership;
import co.com.expertla.training.service.configuration.MembershipService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* Membership Service Impl <br>
* Info. Creación: <br>
* fecha 21/09/2016 <br>
* @author Andres Felipe Lopez Rodriguez
**/
@Service("membershipService")
@Transactional
public class MembershipServiceImpl implements MembershipService {

    @Autowired
    private MembershipDao membershipDao;

    @Override
    public Membership create(Membership membership) throws Exception {
        return membershipDao.create(membership);
    }

    @Override
    public Membership store(Membership membership) throws Exception {
       return membershipDao.merge(membership);
    }

    @Override
    public void remove(Membership membership) throws Exception {
        membershipDao.remove(membership, membership.getMembershipId());
    }

    @Override
    public List<Membership> findAll() throws Exception {
        return membershipDao.findAll();
    }

    @Override
    public List<MembershipDTO> findAllActive() throws Exception {
        return membershipDao.findAllActive();
    }

    @Override
    public List<MembershipDTO> findPaginate(int first, int max, String order, String filter) throws Exception {
        return membershipDao.findPaginate(first, max, order, filter);
    }

    @Override
    public List<Membership> findByMembership(Membership membership) throws Exception {
        return membershipDao.findByMembership(membership);
    }

    @Override
    public List<Membership> findByFiltro(Membership membership) throws Exception {
        return membershipDao.findByFiltro(membership);
    }

    @Override
    public List<Membership> findByName(Membership membership) throws Exception {
        return membershipDao.findByName(membership);
    }
}