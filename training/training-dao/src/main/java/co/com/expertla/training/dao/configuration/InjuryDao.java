/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.dao.configuration;

import co.com.expertla.base.jpa.BaseDAO;
import co.com.expertla.base.jpa.DAOException;
import co.com.expertla.training.model.entities.Injury;
import java.util.List;

/**
* Brand Dao <br>
* Info. Creación: <br>
* fecha Oct 25, 2016 <br>
* @author Edwin Gómez
**/
public interface InjuryDao extends BaseDAO<Injury>{
    
   public Injury findById(Integer id) throws DAOException;
   
   public List<Injury> findAll() throws DAOException;
}
