/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.web.configuration;

import co.expertic.training.dao.plan.TrainingPlanRenovationDao;
import co.expertic.training.model.entities.PlanWorkoutObjective;
import co.expertic.training.model.entities.TrainingPlanRenovation;
import co.expertic.training.model.entities.TrainingPlanUser;
import co.expertic.training.service.configuration.ObjectiveService;
import co.expertic.training.service.plan.PlanWorkoutObjectiveService;
import co.expertic.training.service.plan.TrainingPlanUserService;
import co.expertic.training.service.plan.TrainingPlanWorkoutService;
import co.expertic.training.service.plan.UserTrainingOrderService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Edwin G
 */
@Component
public class ScheduledTasks {

    private static final Logger LOGGER = Logger.getLogger(ScheduledTasks.class);

    @Autowired
    TrainingPlanWorkoutService workoutService;

    @Autowired
    TrainingPlanUserService trainingPlanUserService;

    @Autowired
    ObjectiveService ObjectiveService;

    @Autowired
    PlanWorkoutObjectiveService planWorkoutObjectiveService;

    @Autowired
    UserTrainingOrderService userTrainingOrderService;
    
    @Autowired
    TrainingPlanRenovationDao trainingPlanRenovationDao;

    @Scheduled(fixedRate = 5000) // cada 5 segundos
    public void planEvaluate() {
        //1. generar plan si ha pagado y generar el siguiente mes según el objetivo hijo que siga
        try {
            List<TrainingPlanUser> list = trainingPlanUserService.findAll();

            for (TrainingPlanUser e : list) {

                PlanWorkoutObjective current = planWorkoutObjectiveService.findCurrentObjective(e.getTrainingPlanUserId());
                boolean subscribed = userTrainingOrderService.isSuscribed(e.getUserId().getUserId());
                Date now = Calendar.getInstance().getTime();
                if (current != null && subscribed) {
                    Calendar startCal = Calendar.getInstance();
                    startCal.setTime(current.getToDate());
                    startCal.add(Calendar.MONTH, 1);
                    startCal.add(Calendar.DAY_OF_MONTH, 1);
                    Date startDate = startCal.getTime();
                    startCal.add(Calendar.DAY_OF_MONTH, 29);
                    Date endDate = startCal.getTime();

                    boolean approved = workoutService.isApproved(e.getTrainingPlanUserId(), current.getFromDate(), current.getToDate(), current.getObjectiveId().getObjectiveId());

                  /*  if (approved) {
                        workoutService.generatePlan(e.getUserId().getUserId(), startDate, endDate, true);
                    } else {
                        workoutService.generatePlan(e.getUserId().getUserId(), startDate, endDate, false);
                    }*/
                    
                    TrainingPlanRenovation renovation = new TrainingPlanRenovation();
                    renovation.setTrainingPlanUserId(new TrainingPlanUser(e.getTrainingPlanUserId()));
                    renovation.setCreationDate(Calendar.getInstance().getTime());
                    trainingPlanRenovationDao.create(renovation);
                    
                }

            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
