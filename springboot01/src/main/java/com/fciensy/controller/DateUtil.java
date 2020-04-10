package com.fciensy.controller;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;



public class DateUtil {
	
	    public static String TERM_YEAR = "00";
	    public static String TERM_HALFYEAR = "01";
	    public static String TERM_QUARTER = "02";
	    public static String TERM_MONTH = "03";
	    public static String TERM_WEEK = "04";
	    public static String TERM_DAY = "05";
	    
	  
	    /**
	     * Get current year.<p>
	     * @return current year.
	     */    
	    public static String getYear()
	    {
	        Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);
	        return String.valueOf(year);
	    }
	 
	    /**
	     * Get current quarter.<p>
	     * @return current quarter.
	     */
	    public static String getQuarter()
	    {
	        Calendar cal = Calendar.getInstance();
	        int month = cal.get(Calendar.MONTH) + 1;
	        String quarter = "01";
	        
	        if (month > 3 && month <= 6) { quarter = "02"; }
	        else if (month > 6 && month <= 9) { quarter = "03"; }
	        else if (month > 9 && month <= 12) { quarter = "04"; }
	        return quarter;
	    }
	    
	    /**
	     * Get current quarter.<p>
	     * @return current quarter.
	     */
	    public static String getQuarter(int month)
	    {
	        String quarter = "01";
	        if (month > 3 && month <= 6) { quarter = "02"; }
	        else if (month > 6 && month <= 9) { quarter = "03"; }
	        else if (month > 9 && month <= 12) { quarter = "04"; }
	        return quarter;
	    }
	    
	    /**
	     * Get current month.<p>
	     * @return current month.
	     */  
	    public static String getMonth()
	    {
	        Calendar cal = Calendar.getInstance();
	        int month = cal.get(Calendar.MONTH) + 1;
	        return StringConvert.formatString(String.valueOf(month),2);
	    }

	    /**
	     * Get current day.<p>
	     * @return current day.
	     */      
	    public static String getDay()
	    {
	        Calendar cal = Calendar.getInstance();
	        int day = cal.get(Calendar.DAY_OF_MONTH);
	        return StringConvert.formatString(String.valueOf(day),2);
	    }

	    /**
	     * Get current date,include year,month and day.<p>
	     * @return current date.
	     */   
	    public static String getDate()
	    {
	        return getYear() + getMonth() + getDay();
	    }
	        
	    /**
	     * Get start date and end date through term.<p>
	     * @param term include year,halfyear,quarter,month.
	     * @param date.
	     * @param way 0 means to get start date and end date;
	     *            1 means only to get start date;
	     *            2 means only to get end date.
	     * @return start date and end date.
	     */
	    public static String getDate(String term,String date,int way)
	    {
	        int pos;
	        int[] day = {31,28,31,30,31,30,31,31,30,31,30,31};
	        String[] month = {"01","02","03","04","05","06","07","08",
	                          "09","10","11","12"};
	        String startDate = null;
	        String endDate = null;
	        String year = date.substring(0,4);
	        GregorianCalendar gc = null;
	        
	        gc = new GregorianCalendar();
	        //判断是否为闰年.
	        if (gc.isLeapYear(Integer.parseInt(year)))
	        {
	            day[1] = 29;
	        }
	        if (term.equals(TERM_YEAR))
	        {
	            startDate = year + "0101";   
	            endDate = year + "1231";
	        }
	        else if (term.equals(TERM_HALFYEAR))
	        {
	            pos = Integer.parseInt(date.substring(4,6)) * 6;
	            startDate = year + month[pos - 6] + "01";
	            endDate = year + month[pos - 1] + day[pos - 1];
	        }
	        else if (term.equals(TERM_QUARTER))
	        {
	            pos = Integer.parseInt(date.substring(4,6)) * 3;
	            startDate = year + month[pos - 3] + "01";
	            endDate = year + month[pos - 1] + day[pos - 1];
	        } 
	        else if (term.equals(TERM_MONTH))
	        {
	            pos = Integer.parseInt(date.substring(4,6));
	            startDate = year + month[pos - 1] + "01";
	            endDate = year + month[pos - 1] + day[pos - 1];
	        }
	        if (way == 0) { return startDate + "," + endDate; }
	        else if (way == 1) { return startDate; }
	        else { return endDate; }
	    }
	    
	    /**
	     * Get date description.<p>
	     * @param term.
	     * @param date.
	     * @param way 0 get current term description;1 get lower term description.
	     * @return description of date.
	     */    
	    public static String getDescription(String term,String date,int way)
	    {
	        if (StringConvert.trimString(date).equals("")) { return "";}
	        term = StringConvert.trimString(term);
	        if (way == 0)
	        {
	            //生成执行时间描述.
	            if (term.equals(TERM_YEAR))
	            {
	                //期限=年.
	                date = date.substring(0,4) + "年";
	            }       
	            else if (term.equals(TERM_HALFYEAR))
	            {
	                //期限=半年.
	                if (date.substring(4,6).equals("01"))
	                {
	                    date = date.substring(0,4) + "年上半年";
	                }
	                else 
	                {
	                    date = date.substring(0,4) + "年下半年";
	                }
	            }
	            else if (term.equals(TERM_QUARTER))
	            {
	                //期限=季度.
	                date = date.substring(0,4) + "年" + 
	                       date.substring(5,6) + "季度";
	            }            
	            else if (term.equals(TERM_MONTH))
	            {
	                //期限=月份.
	                date = date.substring(0,4) + "年" + 
	                       date.substring(4,6) + "月";
	            }            
	            else if (term.equals(TERM_DAY))
	            {
	                //期限=日.
	                date = date.substring(0,4) + "年" + 
	                       date.substring(4,6) + "月" + 
	                       date.substring(6,8) + "日";                
	            }
	        }
	        else if (way == 1)
	        {
	            //生成执行时间描述.
	            if (term.equals(TERM_YEAR) || term.equals(TERM_HALFYEAR))
	            {
	                //期限=年/半年,执行时间为季度.
	                date = date.substring(0,4) + "年" + 
	                       date.substring(5,6) + "季度";
	            }       
	            else if (term.equals(TERM_QUARTER))
	            {
	                //期限=季度,执行时间为月.
	                date = date.substring(0,4) + "年" + 
	                       date.substring(4,6) + "月";
	            }
	        }
	        return date;
	    }
	        
	   
	    
	   
	    
	    /**
	     * Get current timestamp,include year,month,day,hour,minute and 
	     * second.<p>
	     * @return current timestamp.
	     */     
	    public static String getTimeStamp()
	    {
	        return getDate() + getTime();
	    }    

	    /**
	     * Get the previous yearmonth of current yearmonth.<p>
	     * @param yearmonth.
	     * @return the previous yearmonth of current yearmonth.
	    */
	    public static String getLastYearMonth(String yearMonth) 
	    {
	        int year;
	        int month;
	        try 
	        {
	            if (yearMonth.length() != 6) { return ""; }
	            year = Integer.parseInt(yearMonth.substring(0,4));
	            month = Integer.parseInt(yearMonth.substring(4,6));
	            return getLastYearMonth(year,month);
	        } 
	        catch (Exception e) { return ""; }
	    }

	    /**
	     * Get the previous yearmonth of current yearmonth.<p>
	     * @param year.
	     * @param month.
	     * @return the previous yearmonth of current yearmonth.
	    */
	    public static String getLastYearMonth(int year, int month) {
	        if (year < 1900 || year > 9999 || 
	            month < 1 || month > 12) { return ""; }
	        if (month > 1) 
	        { return String.valueOf(year) + 
	                 StringConvert.formatString(
	                 String.valueOf(month - 1),2); }
	        else { return String.valueOf(year - 1) + "12"; }
	    }

	    /**
	     * Get difference between start yearmonth and end yearmonth.
	     * The unit of difference is month.<p>
	     * @param yearMonthStart.
	     * @param yearMonthEnd.
	     * @return difference between start yearmonth and end yearmonth.
	     */
	    public static int getMonthDiff(String yearMonthStart,
	                                   String yearMonthEnd)
	    {
	       int yearStart;
	        int monthStart;
	        int yearEnd;
	        int monthEnd;
	        try
	        {
	            yearStart = Integer.parseInt(yearMonthStart.substring(0,4));
	            monthStart = Integer.parseInt(yearMonthStart.substring(4,6));
	            yearEnd = Integer.parseInt(yearMonthEnd.substring(0,4));
	            monthEnd = Integer.parseInt(yearMonthEnd.substring(4,6));
	            return getMonthDiff(yearStart,monthStart,yearEnd,monthEnd);
	        }
	        catch(Exception e) { return 0; }
	    }

	    /**
	     * Get difference between start yearmonth and end yearmonth.
	     * The unit of difference is month.<p>
	     * @param yearStart.
	     * @param monthStart.
	     * @param yearEnd.
	     * @param monthEnd.
	     * @return difference between start yearmonth and end yearmonth.
	     */
	    public static int getMonthDiff(int yearStart,int monthStart,
	                                   int yearEnd,int monthEnd)
	    {
	        return (yearEnd - yearStart) * 12 + monthEnd - monthStart + 1;
	    }
	    
	    /**
	     * Get standard working days in yearmonth.<p>
	     * @param year.
	     * @param month.
	     * @return standard working days.
	     */
	    public static int getStandardDay(int year, int month)
	    {
	        int dayOfWeek;
	        int i;
	        int restDays = 0;
	        int[] days = {31,28,31,30,31,30,31,31,30,31,30,31};
	        GregorianCalendar gc = null;
	  
	        gc = new GregorianCalendar();
	        //判断是否为闰年.
	        if (gc.isLeapYear(year))
	        {
	            days[1] = 29;
	        }
	        //判断哪几天为双休日.
	        for (i=1; i<=days[month-1]; i++)
	        {
	            gc.set(year,month-1,i);
	            dayOfWeek = gc.get(GregorianCalendar.DAY_OF_WEEK);
	            if (dayOfWeek == GregorianCalendar.SUNDAY || 
	                dayOfWeek == GregorianCalendar.SATURDAY) 
	            {
	                restDays++;
	            }
	        }
	        return days[month-1] - restDays;
	    }
	    
	    /**
	     * Get standard working days in yearmonth.<p>
	     * @param yearmMonth.
	     * @return standard working days.
	     */
	    public static int getStandardDay(String yearmMonth)
	    {
	        return getStandardDay(Integer.parseInt(yearmMonth.substring(0,4)),
	                              Integer.parseInt(yearmMonth.substring(4,6)));
	    }
	    
	    /**
	     * Get year list between current year plus five and current year 
	     * add five.<p>
	     * @return year list between current year plus five and current year 
	     * add five.
	     */         
	    public static ArrayList getYearList()
	    {
	        return getYearList(5);
	    }
	    
	    /**
	     * Get year list between current year plus range and current year 
	     * add range.<p>
	     * @param range.
	     * @return year list between current year plus range and current year 
	     * add range.
	     */        
	    public static ArrayList getYearList(int range)
	    {
	        Calendar cal = Calendar.getInstance();
	        int year = cal.get(Calendar.YEAR);
	        ArrayList list = new ArrayList();
	        for(int i=0-range; i<=range; i++)
	        {
	            list.add(String.valueOf(year + i));
	        }
	        return list;
	    }

	    /**
	     * Get year list from client request.<p>
	     * @param request.
	     * @return year list between current year five range and current year 
	     * add five.
	     */       
	    public static void getYearList(HttpServletRequest request)
	    {
	        request.setAttribute("yearList",getYearList());
	    }

	    /**
	     * Get new yearmonth which is relative month before or after 
	     * crrent yearmonth.<p>
	     * @param yearmonth crrent yearmonth.
	     * @param relativeMonth + means after,- means before
	     */    
	    public static String getRelativeYearMonth(String yearMonth,
	                                              int relativeMonth)
	    {
	        int year = Integer.parseInt(yearMonth.substring(0,4));
	        int month = Integer.parseInt(yearMonth.substring(4,6));
	        GregorianCalendar gc = new GregorianCalendar(year,month-1,1);
	        gc.add(gc.MONTH, (-1)*relativeMonth);
	        return String.valueOf(gc.get(gc.YEAR)) + 
	               StringConvert.formatString(
	               String.valueOf(gc.get(gc.MONTH)+1),2);
	    } 

	    /**
	     * Get new yearmonth which is relative month before or after 
	     * crrent yearmonth.<p>
	     * @param yearmonth crrent yearmonth.
	     * @param spacingMonth.
	     * @param relativeMonth + means after,- means before
	     */    
	    public static String getRelativeYearMonth(String yearMonth,
	                                              int spacingMonth,
	                                              int relativeMonth)
	    {
	        return getRelativeYearMonth(yearMonth,
	                                    spacingMonth + relativeMonth);
	    } 
	    
	    /**
	     * Get quarter range between startdate and enddate.<p>
	     * @param term include year,halfyear and quarter.
	     * @param startDate.
	     * @param endDate.
	     * @return quarter range.
	     */
	    public static String[] getQuarterRange(String term,
	                                           String startDate,
	                                           String endDate)
	    {
	        int i;
	        int year;
	        int quarter;
	        String startQuarter;
	        String endQuarter;
	        ArrayList listQuarter = new ArrayList();
	        if (startDate.compareTo(endDate) > 0) 
	        { return new String[0]; }
	        //期限为年.
	        if (term.equals(TERM_YEAR))
	        {
	            startQuarter = startDate + "01";
	            endQuarter = endDate + "04";
	        }
	        //期限为半年.
	        else if (term.equals(TERM_HALFYEAR))
	        {
	            if (startDate.substring(4,6).equals("01"))
	            {
	                startQuarter = startDate.substring(0,4) + "01";
	            }
	            else 
	            {
	                startQuarter = startDate.substring(0,4) + "03";
	            }
	            if (endDate.substring(4,6).equals("01"))
	            {
	                endQuarter = endDate.substring(0,4) + "02";
	            }
	            else 
	            {
	                endQuarter = endDate.substring(0,4) + "04";
	            }
	        }
	        else 
	        {
	            startQuarter = startDate;
	            endQuarter = endDate;
	        }
	        while (startQuarter.compareTo(endQuarter) <= 0)
	        {
	            listQuarter.add(startQuarter);
	            year = Integer.parseInt(startQuarter.substring(0,4));
	            quarter = Integer.parseInt(startQuarter.substring(4,6));
	            if (quarter < 4) { quarter++; }
	            else 
	            {
	                year++;
	                quarter = 1;
	            }
	            startQuarter = String.valueOf(year) + "0" + 
	                           String.valueOf(quarter);
	        }
	        return (String[])listQuarter.toArray(
	                new String[listQuarter.size()]);
	    }
	    
	    /**
	     * Get yearmonth list between start yearmonth and end yearmonth.<p>
	     * @param startYearMonth.
	     * @param endYearMonth.
	     * @param yearmonth list array.
	     */    
	    public static String[] getYearMonthRange(String startYearMonth,
	                                             String endYearMonth)
	    {
	        int year;
	        int month;
	        ArrayList result = new ArrayList();
	        GregorianCalendar gc = null;
	        while (startYearMonth.compareTo(endYearMonth) <= 0)
	        {
	            result.add(startYearMonth);
	            //获取年份.
	            year = Integer.parseInt(startYearMonth.substring(0,4));
	            //获取月份.
	            month = Integer.parseInt(startYearMonth.substring(4,6));
	            gc = new GregorianCalendar(year,month,1);
	            //获取当前年月的下一个年月.
	            gc.add(gc.MONTH, 1);
	            if(gc.get(gc.MONTH) == 0)
	            {
	                startYearMonth = 
	                    String.valueOf((gc.get(gc.YEAR)-1) * 100 + 12);
	            }
	            else
	           {
	                startYearMonth = 
	                    String.valueOf(gc.get(gc.YEAR) * 100 + 
	                                    gc.get(gc.MONTH));
	            }
	        }
	        return (String[])result.toArray(new String[result.size()]);
	    } 
	    
	    
	    
	    
	    
	    /**
		 * 获得从开始日期到结束日期共多少月（自然月）
		 */
		public static Integer getDuringMonth(String startDate,String endDate)
		{
			int duringMonth = 0;
			startDate = StringConvert.trimString(startDate);
			endDate = StringConvert.trimString(endDate);
			if(checkDate(startDate,endDate).booleanValue())
			{
				int startYear = Integer.parseInt(startDate.substring(0,4));
				int endYear = Integer.parseInt(endDate.substring(0,4));
				int startMonth = Integer.parseInt(startDate.substring(4,6));
				int endMonth = Integer.parseInt(endDate.substring(4,6));
				int startDay = Integer.parseInt(startDate.substring(6));
				int endDay = Integer.parseInt(endDate.substring(6));
				duringMonth = (endYear - startYear)*12 + (endMonth - startMonth) - 1;
	            if(startDay==1)
	            {
	                duringMonth++; 
	                if(endDay==countDay(endYear,endMonth).intValue())
	                {
	                    duringMonth++;    
	                }
	            }
	            else if(endDay==countDay(endYear,endMonth).intValue())
	            {
	                duringMonth++;    
	            }
	            else
	            {
	                if(startDay<=(endDay+1))
	                {
	                    duringMonth++;    
	                }                        
	            }
			}
			else
			{
				duringMonth = -1;
			}
			return new Integer(duringMonth);
		}
	    
	    
	    
	    /**
		 * 检查日期格式(格式为：20060101)
		 */
		public static Boolean checkDate(String startDate,String endDate)
		{
			boolean isSuccess = false;
			startDate = StringConvert.trimString(startDate);
			endDate = StringConvert.trimString(endDate);
			if((checkDate(startDate).booleanValue())&&(checkDate(endDate).booleanValue()))
			{
				int startYear = Integer.parseInt(startDate.substring(0,4));
				int endYear = Integer.parseInt(endDate.substring(0,4));
				int startMonth = Integer.parseInt(startDate.substring(4,6));
				int endMonth = Integer.parseInt(endDate.substring(4,6));
				int startDay = Integer.parseInt(startDate.substring(6));
				int endDay = Integer.parseInt(endDate.substring(6));
				Calendar startCalendar = Calendar.getInstance();
	            Calendar endCalendar = Calendar.getInstance();
	            startCalendar.set(startYear,startMonth-1,startDay);
	            endCalendar.set(endYear,endMonth-1,endDay);
	            if((startCalendar.before(endCalendar))||(startDate.equals(endDate)))
	            {
	                isSuccess = true;
	            }
			}
			return new Boolean(isSuccess);
		}
	    
	    
	    /**
		 * 检查日期格式(格式为：20060101)
		 */
		public static Boolean checkDate(String date)
		{
			boolean isSuccess = true;
			date = StringConvert.trimString(date);
			if((date.length()==4)||(date.length()==6)||(date.length()==8))
			{
				int year = Integer.parseInt(date.substring(0,4));
				if(date.length()>4)
				{
					int month = Integer.parseInt(date.substring(4,6));
					if(date.length()>6)
					{
						int day = Integer.parseInt(date.substring(6));
						if(day>countDay(year,month).intValue())
						{
							isSuccess = false;
						}
					}
				}
			}
			else
			{
				isSuccess = false;
			}
			return new Boolean(isSuccess);
		}
	    
	    
	    /**
		 * 判断某年的某个月份中有几天
		 */
		public static Integer countDay(int year,int month)
		{
			int dayCounts = 0;
			switch(month)
			{
				case 1:
				case 3:
				case 5:
				case 7:
				case 8:
				case 10:
				case 12:
					dayCounts = 31;
					break;
				case 4:
				case 6:
				case 9:
				case 11:
					dayCounts = 30;
					break;	
				case 2:
					if((year%4==0&&year%100!=0)||(year%400==0))
					{
						dayCounts = 29;
					}
					else
					{
						dayCounts = 28;
					}
					break;
				default:
					dayCounts = -1;
			}
			return new Integer(dayCounts);
		}
	    
	     /**
		 * 获得季度（格式为：200701，200702，200703，200704）
		 */
	    public static String[] getQuarterOfYear(String year)
	    {
	        String[] quarterOfYear = new String[4];
	        quarterOfYear[0] = year + "01";
	        quarterOfYear[1] = year + "02";
	        quarterOfYear[2] = year + "03";
	        quarterOfYear[3] = year + "04";
	        return quarterOfYear;
	    }
	    
	    
	    public static String getChinaYearMonth(String date)
	    {
	    	String str1=date.substring(0, 4);
	    	StringBuilder sb= new StringBuilder();
	    	StringBuilder sb1= new StringBuilder();
	    	    for(int i = 0; i < str1.length(); i++){
	    	        char c = str1.charAt(i);
	    	        switch(c){
	    	            case '0':sb.append("○");break;
	    	            case '1':sb.append("一");break;
	    	            case '2':sb.append("二");break;
	    	            case '3':sb.append("三");break;
	    	            case '4':sb.append("四");break;
	    	            case '5':sb.append("五");break;
	    	            case '6':sb.append("六");break;
	    	            case '7':sb.append("七");break;
	    	            case '8':sb.append("八");break;
	    	            case '9':sb.append("九");break;
	    	            default :sb.append(c);
	    	        }
	    	    }
	    	 String str2=date.substring(5, 7); 
	    	   switch(str2){
	            case "01":sb1.append("一");break;
	            case "02":sb1.append("二");break;
	            case "03":sb1.append("三");break;
	            case "04":sb1.append("四");break;
	            case "05":sb1.append("五");break;
	            case "06":sb1.append("六");break;
	            case "07":sb1.append("七");break;
	            case "08":sb1.append("八");break;
	            case "09":sb1.append("九");break;
	            case "10":sb1.append("十");break;
	            case "11":sb1.append("十一");break;
	            case "12":sb1.append("十二");break;
	            default :sb1.append(str2);
	        }
	    	    return sb.toString()+"年"+sb1.toString()+"月";
	    }
	    
	    
	    public static String getBeforeOrAfterDate(String date,int addDay){
	        String beforeOrAfterDate = "";
	        if(checkDate(date).booleanValue()==true){
	            int year = new Integer(date.substring(0,4)).intValue();
	            int month = new Integer(date.substring(4,6)).intValue();
	            int day = new Integer(date.substring(6)).intValue();
	            GregorianCalendar gc = new GregorianCalendar(year,month-1,day);
	            gc.add(GregorianCalendar.DATE, addDay);
	            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	            beforeOrAfterDate = dateFormat.format(gc.getTime());
	        }
	        else{
	            beforeOrAfterDate = "-1";
	        }
	        return beforeOrAfterDate;
	    }
	    
	    /**
		 * 标准日期格式转换为季度日期格式
	     如：20070101转换为200701
		 */
	    public static String dateTOQuarter(String date)
	    {
	        String newDate = "";
	        date = StringConvert.trimString(date);
	        if(checkDate(date).booleanValue()==true)
	        {
	            int year = Integer.parseInt(date.substring(0,4));
	            int month = Integer.parseInt(date.substring(4,6));
	            String quarter = "0";
	            if(month>=1&&month<=3)
	            {
	                quarter = "01";
	            }
	            else if(month>=4&&month<=6)
	            {
	                quarter = "02";
	            }
	            else if(month>=7&&month<=9)
	            {
	                quarter = "03";
	            }
	            else
	            {
	                quarter = "04";
	            }
	            newDate = new Integer(year).toString() + quarter;
	        }
	        else
	        {
	           newDate = "-1"; 
	        }
	        return newDate;
	    }
	    
	    /**
		 * 季度日期格式转换为标准日期格式
	     如：（
	     *当flag==0时：200701转换为20070101
	     *当flag==1时：200701转换为20070331
	     *当flag==2时：200701转换为20070131
	     *否则：返回-1
	     ）
		 */
	    public static String quarterTODate(String quarterDate,int flag)
	    {
	        String date = "";
	        quarterDate = StringConvert.trimString(quarterDate);
	        if(checkDate(quarterDate).booleanValue()==true)
	        {
	            int year = Integer.parseInt(quarterDate.substring(0,4));
	            int quarter = Integer.parseInt(quarterDate.substring(4,6));
	            Integer[] months = getMonthOfQuarter(quarter);
	            if(flag==0)
	            {
	                date = formatDate(year,months[0].intValue(),1);
	            }
	            else if(flag==1)
	            {
	                int endDay = countDay(year,months[1].intValue()).intValue();
	                date = formatDate(year,months[1].intValue(),endDay);
	            }
	            else if(flag==2)
	            {
	                int endDay = countDay(year,months[0].intValue()).intValue();
	                date = formatDate(year,months[0].intValue(),endDay);
	            }
	            else
	            {
	                date = "-1";
	            }
	        }
	        else
	        {
	            date = "-1";
	        }
	        return date;
	    }
	    
	    /**
		 * 根据季度获得月份区间
		 */
	    public static Integer[] getMonthOfQuarter(int quarter)
	    {
	        Integer[] months = new Integer[2];
	        int startMonth = 0;
	        int endMonth = 0;
	        switch(quarter)
	        {
	            case 1:
	                    startMonth = 1;
	                    endMonth = 3;
	                    break;
	            case 2:
	                    startMonth = 4;
	                    endMonth = 6;
	                    break;
	            case 3:
	                    startMonth = 7;
	                    endMonth = 9;
	                    break;
	            case 4:
	                    startMonth = 10;
	                    endMonth = 12;
	                    break;
	            default:
	                    startMonth = -1;
	                    endMonth = -1;
	        }
	        months[0] = new Integer(startMonth);
	        months[1] = new Integer(endMonth);
	        return months;
	    }
	    
	    /**
		 * 格式化日期（标准日期格式）
		 */
	    public static String formatDate(int year,int month,int day)
	    {
	        String formattedDate = "";
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(year,month-1,day);
	        formattedDate = dateFormat.format(calendar.getTime());
	        return formattedDate;
	    }
	    
	    
	    
	    /**
		 * 获得从开始日期到结束日期共多少天
		 */
	    public static int getDuringDays(String startDate,String endDate)
	    {
	        int duringDays = 0;
	        startDate = StringConvert.trimString(startDate);
	        endDate = StringConvert.trimString(endDate);
	        if(checkDate(startDate,endDate).booleanValue()==true)
	        {
	            Calendar startCalendar = Calendar.getInstance();
	            Calendar endCalendar = Calendar.getInstance();
	            int year = Integer.parseInt(startDate.substring(0,4));
	            int month = Integer.parseInt(startDate.substring(4,6));
	            int day = Integer.parseInt(startDate.substring(6));
	            startCalendar.set(year,month-1,day,0,0,0);
	            year = Integer.parseInt(endDate.substring(0,4));
	            month = Integer.parseInt(endDate.substring(4,6));
	            day = Integer.parseInt(endDate.substring(6));
	            endCalendar.set(year,month-1,day,0,0,0);
	            duringDays = new Long((endCalendar.getTimeInMillis()-startCalendar.getTimeInMillis())/(24*3600*1000)).intValue() + 1;
	        }
	        else
	        {
	            duringDays = -1;
	        }
	        return duringDays;
	    }
	    
	    
	    /**
		 * 获得从开始日期到结束日期共间隔多少小时
		 */
	    public static Float getDuringHours(String startDate,String endDate)
	    {
	        float duringHours = 0;
	        startDate = StringConvert.trimString(startDate);
	        endDate = StringConvert.trimString(endDate);
	        Calendar startCalendar = Calendar.getInstance();
	        Calendar endCalendar = Calendar.getInstance();
	        int year = Integer.parseInt(startDate.substring(0,4));
	        int month = Integer.parseInt(startDate.substring(4,6));
	        int day = Integer.parseInt(startDate.substring(6,8));
	        int hour = Integer.parseInt(startDate.substring(8,10));
	        int minute = Integer.parseInt(startDate.substring(10,12));
	        int second = Integer.parseInt(startDate.substring(12,14));
	        startCalendar.set(year,month-1,day,hour,minute,second);
	        year = Integer.parseInt(endDate.substring(0,4));
	        month = Integer.parseInt(endDate.substring(4,6));
	        day = Integer.parseInt(endDate.substring(6,8));
	        hour = Integer.parseInt(endDate.substring(8,10));
	        minute = Integer.parseInt(endDate.substring(10,12));
	        second = Integer.parseInt(endDate.substring(12,14));
	        endCalendar.set(year,month-1,day,hour,minute,second);
	        duringHours = (new Long(endCalendar.getTimeInMillis()-startCalendar.getTimeInMillis()).floatValue())/(3600*1000);
	        return new Float(duringHours);
	    }
	    
	    /**
		 * 检查时间格式（标准时间格式:080000）
		 */
	    public static Boolean checkTime(String time)
	    {
	        boolean isSuccess = false;
	        time = StringConvert.trimString(time);
	        if(time.length()==6)
	        {
	            int hour = Integer.parseInt(time.substring(0,2));
	            int minute = Integer.parseInt(time.substring(2,4));
	            int second = Integer.parseInt(time.substring(4));
	            if((hour>=0&&hour<=23)&&(minute>=0&&minute<=59)&&(second>=0&&second<=59))
	            {
	                isSuccess = true;
	            }
	        }
	        return new Boolean(isSuccess);
	    }
	    
	    /**
		 * 格式化时间（标准时间格式）
		 */
	    public static String formatTime(int hour,int minute,int second)
	    {
	        String formattedTime = "";
	        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(1,1,1,hour,minute,second);
	        formattedTime = dateFormat.format(calendar.getTime());
	        return formattedTime;
	    }
	    
	    /**
	     * 返回月份列表
	     */
	    public static ArrayList getMonthList()
	    {
	        ArrayList list = new ArrayList();
	        list.add("01");
	        list.add("02");
	        list.add("03");
	        list.add("04");
	        list.add("05");
	        list.add("06");
	        list.add("07");
	        list.add("08");
	        list.add("09");
	        list.add("10");
	        list.add("11");
	        list.add("12");
	        return list;
	    }
	    
	    public static String addMonth(String date, int addMonth)
	    {
	        String result = date;
	        Calendar cl = Calendar.getInstance();
	        cl.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
	        cl.set(Calendar.MONTH, Integer.parseInt(date.substring(5, 7)) - 1);
	        cl.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(8)));
	        cl.add(Calendar.MONTH, addMonth);
	        result = String.valueOf(cl.get(Calendar.YEAR)) + "-" + 
	                StringConvert.formatString(String.valueOf(cl.get(Calendar.MONTH) + 1), 2) + "-" + 
	                StringConvert.formatString(String.valueOf(cl.get(Calendar.DAY_OF_MONTH)), 2);
	        return result;
	    }
	
	
	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=formatter.format(currentTime);
		Date date=new Date();
		try {
			  date = formatter.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		ParsePosition pos = new ParsePosition(8);
		Date currentTime_2 = formatter.parse(dateString, pos);
		return currentTime_2;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDateShort() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTimeShort() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateLong(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateToStrLong(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString="";
		if(dateDate!=null){
			dateString = formatter.format(dateDate);	
		}
		
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}
	
	/**
	 * 将短时间格式字符串转换为时间 yyyy年MM月dd日
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToCnDate(String strDate) {
		String year=strDate.substring(0,4);	
		String month=strDate.substring(4,6);	
		String day=strDate.substring(6,8);	
		return year+"年"+month+"月"+day+"日";
	}
	
	/**
	 * 将短时间格式字符串转换为时间 yyyy年MM月dd日
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToCnDateOne(String strDate) {
		//String year=strDate.substring(0,4);	
		String month=strDate.substring(4,6);	
		String day=strDate.substring(6,8);	
		return month+"月"+day+"日";
	}
	
	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToMinusDate(String strDate) {
		String year=strDate.substring(0,4);	
		String month=strDate.substring(4,6);	
		String day=strDate.substring(6,8);	
		return year+"-"+month+"-"+day;
	}
	
	
	/**
	 * 将短时间格式字符串转换为时间 hh:mm
	 * 
	 * @param strDate
	 * @return
	 */
	public static String strToHourMinute(String strDate) {
		String hour=strDate.substring(0,2);	
		String minute=strDate.substring(2,4);		
		return hour+":"+minute;
	}
	
	
	/**
	 * 得到现在时间
	 * 
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 提取一个月中的最后一天
	 * 
	 * @param day
	 * @return
	 */
	public static Date getLastDate(long day) {
		Date date = new Date();
		long date_3_hm = date.getTime() - 3600000 * 34 * day;
		Date date_3_hm_date = new Date(date_3_hm);
		return date_3_hm_date;
	}

	/**
	 * 得到现在时间
	 * 
	 * @return 字符串 yyyyMMdd HHmmss
	 */
	public static String getStringToday() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd HHmmss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 得到现在小时
	 */
	public static String getHour() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String hour;
		hour = dateString.substring(11, 13);
		return hour;
	}

	/**
	 * 得到现在分钟
	 * 
	 * @return
	 */
	public static String getTime() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		String min;
		min = dateString.substring(14, 16);
		return min;
	}

	/**
	 * 根据用户传入的时间表示格式，返回当前时间的格式 如果是yyyyMMdd，注意字母y不能大写。
	 * 
	 * @param sformat
	 *            yyyyMMddhhmmss
	 * @return
	 */
	public static String getUserDate(String sformat) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(sformat);
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/**
	 * 二个小时时间间的差值,必须保证二个时间都是"HH:MM"的格式，返回字符型的分钟
	 */
	public static String getTwoHour(String st1, String st2) {
		String[] kk = null;
		String[] jj = null;
		kk = st1.split(":");
		jj = st2.split(":");
		if (Integer.parseInt(kk[0]) < Integer.parseInt(jj[0]))
			return "0";
		else {
			double y = Double.parseDouble(kk[0]) + Double.parseDouble(kk[1])
					/ 60;
			double u = Double.parseDouble(jj[0]) + Double.parseDouble(jj[1])
					/ 60;
			if ((y - u) > 0)
				return y - u + "";
			else
				return "0";
		}
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟.
	 */
	public static String getPreTime(String sj1, String jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + Integer.parseInt(jj) * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 得到一个时间延后或前移几天的时间,nowdate为时间,delay为前移或后延的天数
	 */
	public static String getNextDay(String nowdate, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String mdate = "";
			Date d = strToDate(nowdate);
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24
					* 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 判断是否润年
	 * 
	 * @param ddate
	 * @return
	 */
	public static boolean isLeapYear(String ddate) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		Date d = strToDate(ddate);
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(d);
		int year = gc.get(Calendar.YEAR);
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 返回美国时间格式 26 Apr 2006
	 * 
	 * @param str
	 * @return
	 */
	public static String getEDate(String str) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(str, pos);
		String j = strtodate.toString();
		String[] k = j.split(" ");
		return k[2] + k[1].toUpperCase() + k[5].substring(2, 4);
	}

	/**
	 * 获取一个月的最后一天
	 * 
	 * @param dat
	 * @return
	 */
	public static String getEndDateOfMonth(String dat) {// yyyy-MM-dd
		String str = dat.substring(0, 8);
		String month = dat.substring(5, 7);
		int mon = Integer.parseInt(month);
		if (mon == 1 || mon == 3 || mon == 5 || mon == 7 || mon == 8
				|| mon == 10 || mon == 12) {
			str += "31";
		} else if (mon == 4 || mon == 6 || mon == 9 || mon == 11) {
			str += "30";
		} else {
			if (isLeapYear(dat)) {
				str += "29";
			} else {
				str += "28";
			}
		}
		return str;
	}

	/**
	 * 判断二个时间是否在同一个周
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 产生周序列,即得到当前时间所在的年度是第几周
	 * 
	 * @return
	 */
	public static String getSeqWeek() {
		Calendar c = Calendar.getInstance(Locale.CHINA);
		String week = Integer.toString(c.get(Calendar.WEEK_OF_YEAR));
		if (week.length() == 1)
			week = "0" + week;
		String year = Integer.toString(c.get(Calendar.YEAR));
		return year + week;
	}

	

	/**
	 * 两个时间之间的天数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long getDays(String date1, String date2) {
		if (date1 == null || date1.equals(""))
			return 0;
		if (date2 == null || date2.equals(""))
			return 0;
		// 转换为标准时间
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		java.util.Date mydate = null;
		try {
			date = myFormatter.parse(date1);
			mydate = myFormatter.parse(date2);
		} catch (Exception e) {
		}
		long day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		return day;
	}

	
	/**
	 * 取得数据库主键 生成格式为yyyymmddhhmmss+k位随机数
	 * 
	 * @param k
	 *            表示是取几位随机数，可以自己定
	 */
	public static String getNo(int k) {
		return getUserDate("yyyyMMddhhmmss") + getRandom(k);
	}

	/**
	 * 返回一个随机数
	 * 
	 * @param i
	 * @return
	 */
	public static String getRandom(int i) {
		Random jjj = new Random();
		// int suiJiShu = jjj.nextInt(9);
		if (i == 0)
			return "";
		String jj = "";
		for (int k = 0; k < i; k++) {
			jj = jj + jjj.nextInt(9);
		}
		return jj;
	}

	/**
	 * 
	 * @param args
	 */
	public static boolean RightDate(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		;
		if (date == null)
			return false;
		if (date.length() > 10) {
			sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		}
		try {
			sdf.parse(date);
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}
	
	/**
	 * 将yyyy-mm-dd格式的日期变为yyyymmdd
	 * @param args
	 */
	
	public static String formatDate(String date){
		StringBuffer sb=new StringBuffer();
		if (date!=null&&!date.equals("")) {
			String str[]=date.split("-");
			for (int i = 0; i < str.length; i++) {
				sb.append(str[i]);
			}
		}
		return sb.toString();
	}

	
	/**
	 * 判断一个时间段是否正确(两个时间格式必须是HHMM)
	 * @param args
	 */
	public static boolean validateTime(String time1,String time2){
		boolean flag=false;
		
		if (time1!=null&&time2!=null) {
			if(time1.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")&&time2.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")){
			if (time1.length()!=4||time2.length()!=4) {
				return flag;
			}
			int time1Hour= Integer.parseInt(time1.substring(0,2));
			int time1Min=Integer.parseInt(time1.substring(2,4));
			int time2Hour=Integer.parseInt(time2.substring(0,2));
			int time2Min=Integer.parseInt(time2.substring(2,4));
			if (time1Hour<24&&time2Hour<24&&time1Min<60&&time2Min<60) {
				flag=true;
			}else {
				return flag;
			}
			int timeone=Integer.parseInt(time1);
			int timetwo=Integer.parseInt(time2);
			if (timeone>timetwo) {
				flag=false;
				return flag;
			}
			}else{
				return flag;
			}
		}
		return flag;
	}
	/**
	 * 将HHMM格式的时间改为HH:MM
	 * @param args
	 */
	public static String changeTimeType(String str){
		String strHour=str.substring(0,2);
		String strMin=str.substring(2,4);
		return strHour+":"+strMin;
	}
	/**
	 * 将yyyyMMdd格式的日期变为yyyy-MM-dd
	 * @param args
	 */
	public static String reFormatDate(String str){
		String year=str.substring(0,4);
		String month=str.substring(4,6);
		String day=str.substring(6,8);
		return year+"-"+month+"-"+day;
	}
	
	/** 
	*字符串的日期格式的计算 
	*/  
	    public static int daysBetween(String smdate,String bdate) throws ParseException{  
	    	String startDate=smdate.substring(6,8);
	    	String endDate=bdate.substring(6,8);
	         int days=(Integer.parseInt(endDate)-Integer.parseInt(startDate))+1;
	       return days;     
	    }
	    
	    public static int daysBetween1(String smdate,String bdate) throws ParseException { 
	    	
	    	
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");  
	        Calendar cal = Calendar.getInstance();    
	        cal.setTime(sdf.parse(smdate.toString()));    
	        long time1 = cal.getTimeInMillis();                 
	        cal.setTime(sdf.parse(bdate.toString()));    
	        long time2 = cal.getTimeInMillis();         
	        long between_days=(time2-time1)/(1000*3600*24); 
	           
	    	
	    	return Integer.parseInt(String.valueOf(between_days));  
	            
	     
	    }  
	
	    
	  /*  public static List<String> getNewList(List<String> li){
			        List<String> list = new ArrayList<String>();
			        for(int i=0; i<li.size(); i++){
			            String str = li.get(i);  //获取传入集合对象的每一个元素
			            if(!list.contains(str)){   //查看新集合中是否有指定的元素，如果没有则加入
			                list.add(str);
			            }
			        }
			        return list;  //返回集合
			    }*/

}
