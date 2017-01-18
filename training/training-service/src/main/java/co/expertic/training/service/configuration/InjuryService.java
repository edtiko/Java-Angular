/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.configuration;

import co.expertic.training.model.entities.Injury;
import java.util.List;

/**
 *
 * @author Edwin G
 */
public interface InjuryService {

    public Injury findById(Integer id) throws Exception;

    public List<Injury> findAll() throws Exception;
}
