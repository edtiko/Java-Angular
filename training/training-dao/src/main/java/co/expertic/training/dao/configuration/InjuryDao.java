/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.dao.configuration;

import co.expertic.base.jpa.BaseDAO;
import co.expertic.base.jpa.DAOException;
import co.expertic.training.model.entities.Injury;
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
