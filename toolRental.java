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
		sb.append("Charge days: " + rentalDayCount + "\n");
		double preDiscountTotal = getPreDisountCharge(tool, checkoutDate, rentalDayCount);
		sb.append("Pre-discount charge: $" + String.format("%.2f", preDiscountTotal) + "\n");
		sb.append("Discount percent: " + discountPerecent + "%\n");
		double discountAmount = getDiscountAmount(preDiscountTotal, discountPerecent);
		sb.append("Discount amount: $" + String.format("%.2f",discountAmount) + "\n");
		sb.append("Final charge: $" + String.format("%.2f",getFinalCharge(preDiscountTotal, discountAmount)) + "\n\n");
		
		System.out.print(sb.toString());
	}
	
	public String getDueDate(Tool tool, String checkoutDate, int rentalDays) 
	{
		//Calculated from checkout date and rental days.
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		try{
			c.setTime(formatter.parse(checkoutDate));
		}catch(ParseException pe){pe.printStackTrace();}
		
		c.add(Calendar.DATE, rentalDays);
		String dueDateStr = formatter.format(c.getTime());
		return dueDateStr;
	}
	
	public double getPreDisountCharge(Tool tool, String checkoutDate, int rentalDays)
	{
		//Calculated from checkout date and rental days. charge days X daily charge
		//Get rental date information
		boolean includeWeekdays = tool.weekdayCharge();
		boolean includeWeekends = tool.weekendCharge();
		boolean includeHolidays = tool.holidayCharge();
		double dailyCharge = tool.getDailyCharge();
		double total = 0;
		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		Date date = null;
		
		try{
			date = formatter.parse(checkoutDate);
			c.setTime(date);
		}catch(ParseException pe){pe.printStackTrace();}
		
		int days = 0;
		while(days != rentalDays)
		{
			int day = c.get(Calendar.DAY_OF_WEEK);
			if(
				(includeHolidays && isFirstMondayOfSeptember(date) && isFourthOfJuly(date)) ||
				(includeWeekdays && (day != Calendar.SATURDAY && day != Calendar.SUNDAY)) ||
				(includeWeekends && (day == Calendar.SATURDAY || day == Calendar.SUNDAY)))
			{
				total = total + tool.getDailyCharge();
				days++;
			}
			
			c.add(Calendar.DATE, 1);
		}
		return total;
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
