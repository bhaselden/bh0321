package toolRentalApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class toolRental 
{	
	public void printRentalAgreement(Tool tool, int rentalDayCount, int discountPerecent, String checkoutDate) throws Exception
	{
		if(rentalDayCount < 1)
		{
			throw new Exception ("Rental day count is not 1 or greater.");
		}
		else if(discountPerecent < 0 || discountPerecent > 100)
		{
			throw new Exception ("Discount percent is not in range of 0-100.");
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Tool code: " + tool + "\n");
		sb.append("Tool type: " + tool.getType() + "\n");
		sb.append("Tool brand: " + tool.getBrand() + "\n");
		sb.append("Rental days: " + rentalDayCount + "\n");
		sb.append("Check out date: " + checkoutDate + "\n");
		sb.append("Due date: " + getDueDate(tool, checkoutDate, rentalDayCount) + "\n");
		sb.append("Daily rental charge: " + tool.getDailyCharge() + "\n");
		int chargeDays = getChargeDays(tool, checkoutDate, rentalDayCount);
		sb.append("Charge days: " + chargeDays + "\n");
		double preDiscountTotal = getPreDisountCharge(tool, chargeDays);
		sb.append("Pre-discount charge: $" + String.format("%.2f", preDiscountTotal) + "\n");
		sb.append("Discount percent: " + discountPerecent + "%\n");
		double discountAmount = getDiscountAmount(preDiscountTotal, discountPerecent);
		sb.append("Discount amount: $" + String.format("%.2f",discountAmount) + "\n");
		sb.append("Final charge: $" + String.format("%.2f",getFinalCharge(preDiscountTotal, discountAmount)) + "\n\n");
		
		System.out.print(sb.toString());
	}
	
	public String getDueDate(Tool tool, String checkoutDate, int rentalDays) 
	{
		//convert String checkout date to date format
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		try{
			c.setTime(formatter.parse(checkoutDate));
		}catch(ParseException pe){pe.printStackTrace();}
		
		c.add(Calendar.DATE, rentalDays);
		String dueDateStr = formatter.format(c.getTime());
		return dueDateStr;
	}
	
	public int getChargeDays(Tool tool, String checkoutDate, int rentalDays)
	{
		//Get rental date information
		boolean includeWeekdays = tool.weekdayCharge();
		boolean includeWeekends = tool.weekendCharge();
		boolean includeHolidays = tool.holidayCharge();
		
		//convert String checkout date to date format
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		Date date = null;
		
		try{
			date = formatter.parse(checkoutDate);
			c.setTime(date);
		}catch(ParseException pe){pe.printStackTrace();}
		
		//add days to checkout date to get to due date
		int days = 0;
		while(rentalDays > 0)
		{
			int day = c.get(Calendar.DAY_OF_WEEK);
			if(
				(includeHolidays && isFirstMondayOfSeptember(date) && isFourthOfJuly(date)) ||
				(includeWeekdays && (day != Calendar.SATURDAY && day != Calendar.SUNDAY)) ||
				(includeWeekends && (day == Calendar.SATURDAY || day == Calendar.SUNDAY)))
			{
				days++;
			}
			rentalDays--;
			c.add(Calendar.DATE, 1);
		}
		return days;
	}
	
	public double getPreDisountCharge(Tool tool, int chargeDays)
	{
		double dailyCharge = tool.getDailyCharge();
		return dailyCharge * chargeDays;
	}
	
	public boolean isFourthOfJuly(Date date)
	{
		/*
		 * If falls on weekend, it is observed on the closest weekday 
		 * (if Sat, then Friday before, if Sunday, then Monday after)
		 */
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
		int day = c.get(Calendar.DAY_OF_WEEK);
		int month = c.get(Calendar.MONTH);
		if(month == Calendar.JULY)
		{
			if(dayOfMonth == 4 || 
					(dayOfMonth == 3 && day == Calendar.FRIDAY) || 
					(dayOfMonth == 5 && day == Calendar.MONDAY))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isFirstMondayOfSeptember(Date date)
	{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int day = c.get(Calendar.DAY_OF_WEEK);
		int month = c.get(Calendar.MONTH);
		int week = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	    
	    if(day == Calendar.MONDAY && month == Calendar.SEPTEMBER && week == 1)
	    {
	    	return true;
	    }
	    return false;
	
	}
	public double getDiscountAmount(double preDiscountTotal, int discountPerecent) throws Exception
	{
		//calculated from discount % and pre-discount charge
		double perecent = discountPerecent / 100.0;
		return preDiscountTotal*perecent;
	}
	
	public double getFinalCharge(double preDiscountTotal, double discountAmount)
	{
		return preDiscountTotal - discountAmount;
	}
}
