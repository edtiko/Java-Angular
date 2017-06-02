/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.expertic.training.service.impl.user;

import co.expertic.training.dao.user.UserFittingDao;
import co.expertic.training.dao.user.UserFittingHistoryDao;
import co.expertic.training.enums.StateEnum;
import co.expertic.training.model.dto.PaginateDto;
import co.expertic.training.model.dto.UserFittingVideoDTO;
import co.expertic.training.model.dto.UserResumeDTO;
import co.expertic.training.model.entities.ColourIndicator;
import co.expertic.training.model.util.UtilDate;
import co.expertic.training.service.configuration.ColourIndicatorService;
import co.expertic.training.service.user.MechanicService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Edwin G
 */
@Service
@Transactional
public class MechanicServiceImpl implements MechanicService {

    @Autowired
    UserFittingDao userFittingDao;

    @Autowired
    UserFittingHistoryDao userFittingHistoryDao;

    @Autowired
    ColourIndicatorService colourIndicatorService;

    @Override
    public List<UserResumeDTO> findAthletesByUserPaginate(Integer userId, PaginateDto paginateDto) throws Exception {
        List<UserResumeDTO> athletes = userFittingDao.findAthletesByUserPaginate(userId, paginateDto);

        List<ColourIndicator> colours = colourIndicatorService.findAll();

        int firstOrder = 0;
        int secondOrder = 0;
        int thirdOrder = 0;
        String firstColour = "{'background-color':'white'}";
        String secondColour = "{'background-color':'white'}";
        String thirdColour = "{'background-color':'white'}";
        for (ColourIndicator colour : colours) {
            if (colour.getColourOrder().equals(1)) {
                firstOrder = colour.getHoursSpent();
                firstColour = colour.getColour();
            }
            if (colour.getColourOrder().equals(2)) {
                secondOrder = colour.getHoursSpent();
                secondColour = colour.getColour();
            }
            if (colour.getColourOrder().equals(3)) {
                thirdOrder = colour.getHoursSpent();
                thirdColour = colour.getColour();
            }
        }

        for (UserResumeDTO athlete : athletes) {
            int countFirstColour = 0;
            int countSecondColour = 0;
            int countThirdColour = 0;
            List<UserFittingVideoDTO> videos = userFittingHistoryDao.getUserFittingHistory(athlete.getUserId());

            for (UserFittingVideoDTO video : videos) {
                if (Objects.equals(video.getStateId(), StateEnum.PENDING.getId())) {
                    video.setHoursSpent(UtilDate.calculateHourDifference(video.getCreationDate()));
                    if (video.getHoursSpent() >= 0 && video.getHoursSpent() <= firstOrder) {
                        countFirstColour++;
                    } else if (video.getHoursSpent() > firstOrder && video.getHoursSpent() <= secondOrder) {
                        countSecondColour++;
                    } else {
                        countThirdColour++;
                    }
                }
            }

            if (countThirdColour > 0) {
                athlete.setColor(thirdColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            } else if (countSecondColour > 0) {
                athlete.setColor(secondColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            } else if (countFirstColour > 0) {
                athlete.setColor(firstColour.replaceAll("\\{", "").replaceAll("}", "").replaceAll("'", ""));
            }
        }

        return athletes;
    }

    @Override
    public List<UserResumeDTO> getAthletes() throws Exception {
        return userFittingDao.getAthletes();
    }

}
