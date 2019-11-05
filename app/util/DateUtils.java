package util;

import java.text.DateFormatSymbols;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {
    }

    public static String getMonthName(int month) {
        return getMonthName(month, Locale.getDefault());
    }

    public static String getMonthName(int month, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] monthNames = symbols.getMonths();
        return monthNames[month - 1];
    }

    public static String[] getAllMonths(Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] monthNames = symbols.getMonths();

        String[] monthMayus = new  String[12];
        for(int i = 0; i < monthNames.length; i++){
            if(!monthNames[i].isEmpty()){
                String mes = Character.toUpperCase(monthNames[i].charAt(0)) + monthNames[i].substring(1);
                monthMayus[i] = mes;
            }

        }

        return monthMayus;
    }


    public static String getDayName(int day, Locale locale) {
        DateFormatSymbols symbols = new DateFormatSymbols(locale);
        String[] dayNames = symbols.getWeekdays();
        return dayNames[day];
    }


    public static void main(String[] args) {

        System.out.println(DateUtils.getMonthName(1));
        System.out.println(DateUtils.getMonthName(1, new Locale("it")));

        System.out.println(DateUtils.getDayName(java.util.Calendar.SUNDAY, Locale.getDefault()));
    
    /*
     * output :
     *   january
     *   gennaio  
     *   sunday
     */
    }
}