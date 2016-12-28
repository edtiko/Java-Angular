/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.expertla.training.web.configuration;

import co.com.expertla.training.model.entities.PlanWorkoutObjective;
import co.com.expertla.training.model.entities.TrainingPlanUser;
import co.com.expertla.training.service.configuration.ObjectiveService;
import co.com.expertla.training.service.plan.PlanWorkoutObjectiveService;
import co.com.expertla.training.service.plan.TrainingPlanUserService;
import co.com.expertla.training.service.plan.TrainingPlanWorkoutService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edwin G
 */
@Component
public class ScheduledTasks {

    @Autowired
    TrainingPlanWorkoutService workoutService;

    @Autowired
    TrainingPlanUserService trainingPlanUserService;

    @Autowired
    ObjectiveService ObjectiveService;
    
    @Autowired
    PlanWorkoutObjectiveService planWorkoutObjectiveService;
            
    @Scheduled(fixedRate = 5000) // cada 5 segundos
    public void planEvaluate() {
        //1. generar plan si ha pagado y generar el siguiente mes según el objetivo hijo que siga
        //
        try {
            List<TrainingPlanUser> list = trainingPlanUserService.findAll();
            Calendar startCal = Calendar.getInstance();
            startCal.setTime(new Date());
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            Date startDate = startCal.getTime();
            startCal.add(Calendar.DAY_OF_MONTH, 29);
            Date endDate = startCal.getTime();
            startCal.
            Date nextStartDate = 

            for (TrainingPlanUser e : list) {

                PlanWorkoutObjective current = planWorkoutObjectiveService.findByTrainingPlanUserId(e.getTrainingPlanUserId(), startDate, endDate);
                if (workoutService.isApproved(e.getTrainingPlanUserId(), startDate, endDate, current.getObjectiveId().getObjectiveId())) {
                    workoutService.generatePlan(e.getUserId().getUserId(), fromDate, toDate, true);
                } else {
                    workoutService.generatePlan(e.getUserId().getUserId(), fromDate, toDate, false);
                }

            }

        } catch (Exception e) {

        }
    }

}
