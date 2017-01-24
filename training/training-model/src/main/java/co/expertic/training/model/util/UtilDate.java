package co.expertic.training.model.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Util used for handling dates
 * Creation Info: <br>
 * date 4/11/2015 <br>
 * @author Angela Ramirez
 */
public class UtilDate {

    /**
     * Method used for converting dates in String in the format sent
     * @param date
     * @param format
     * @return 
     */
    public String dateToStringByFormat(Date date,String format) {
        if(date != null){
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            String formatDate = dateFormatter.format(date);
            return formatDate;
        } else {
            return null;
        }
    }
}
