package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Date formatDate(String dateInString){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Date formatDateTime(String dateInString){
        // 2016-12-02T10:10
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
            date = formatter.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String parseDateToString(Date date){
        //DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        return df.format(date);
    }

}
