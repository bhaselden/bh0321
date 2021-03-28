package toolRentalApplication;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
	
public class tests 
{	
	toolRental tr = new toolRental();
	
	@Test
	public void printRentalAgreementTest() throws Exception
	{
		try
		{
			tr.printRentalAgreement(Tool.JAKR, 5, 101, "9/3/15");
			fail("Percentage not in range");
		}
		catch(Exception e)
		{
			assertEquals(e.getLocalizedMessage(), "Discount percent is not in range of 0-100.");
		}
		
		tr.printRentalAgreement(Tool.LADW, 3, 10, "7/2/20");
		tr.printRentalAgreement(Tool.CHNS, 5, 25, "7/2/15");
		tr.printRentalAgreement(Tool.JAKD, 6, 0, "9/3/15");
		tr.printRentalAgreement(Tool.JAKR, 9, 0, "7/2/15");
		tr.printRentalAgreement(Tool.JAKR, 4, 50, "7/2/20");
	}
	
	@Test
	public void getDueDateTest()
	{
		assertEquals(tr.getDueDate(Tool.LADW, "7/2/20", 3), "7/5/20");
		assertEquals(tr.getDueDate(Tool.CHNS, "7/2/15", 5), "7/7/15");
		assertEquals(tr.getDueDate(Tool.JAKD, "9/3/15", 6), "9/9/15");
		assertEquals(tr.getDueDate(Tool.JAKR, "7/2/15", 9), "7/11/15");
		assertEquals(tr.getDueDate(Tool.JAKR, "7/2/20", 4), "7/6/20");
	}
	
	@Test
	public void getChargeDaysTest()
	{
		int chargeDays1 = tr.getChargeDays(Tool.LADW, "7/2/20", 3);
		assertEquals(chargeDays1, 3);

		int chargeDays2 = tr.getChargeDays(Tool.CHNS, "7/2/15", 5);
		assertEquals(chargeDays2, 3);
		
		int chargeDays3 = tr.getChargeDays(Tool.JAKD, "9/3/15", 6);
		assertEquals(chargeDays3, 4);
		
		int chargeDays4 = tr.getChargeDays(Tool.JAKR, "7/2/15", 9);
		assertEquals(chargeDays4, 7);
		
		int chargeDays5 = tr.getChargeDays(Tool.JAKR, "7/2/20", 4);
		assertEquals(chargeDays5, 2);
	}
	
	@Test
	public void getPreDisountChargeTest()
	{
		double preDiscountTotal1 = tr.getPreDisountCharge(Tool.LADW, 3);
		assertEquals(String.format("%.2f",preDiscountTotal1), "5.97");

		double preDiscountTotal2 = tr.getPreDisountCharge(Tool.CHNS, 3);
		assertEquals(String.format("%.2f",preDiscountTotal2), "4.47");
		
		double preDiscountTotal3 = tr.getPreDisountCharge(Tool.JAKD, 4);
		assertEquals(String.format("%.2f",preDiscountTotal3), "11.96");
		
		double preDiscountTotal4 = tr.getPreDisountCharge(Tool.JAKR, 7);
		assertEquals(String.format("%.2f",preDiscountTotal4), "20.93");
		
		double preDiscountTotal5 = tr.getPreDisountCharge(Tool.JAKR, 2);
		assertEquals(String.format("%.2f",preDiscountTotal5), "5.98");
	}
	
	@Test
	public void isFourthOfJulyTest() throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		Date date1 = formatter.parse("7/4/21");//Sunday July 4th = true
		Date date2 = formatter.parse("7/5/21");//Monday July 5th = true
		Date date3 = formatter.parse("7/3/21");//Saturday July 3rd = false
		Date date4 = formatter.parse("7/3/15");//Friday July 4th = true
		assertTrue(tr.isFourthOfJuly(date1));
		assertTrue(tr.isFourthOfJuly(date2));
		assertFalse(tr.isFourthOfJuly(date3));
		assertTrue(tr.isFourthOfJuly(date4));
	}
	
	@Test
	public void isFirstMondayOfSeptemberTest() throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		Date date1 = formatter.parse("9/6/21");//true
		Date date2 = formatter.parse("9/13/21");//false
		assertTrue(tr.isFirstMondayOfSeptember(date1));
		assertFalse(tr.isFirstMondayOfSeptember(date2));
	}
	
	@Test
	public void getDiscountAmountTest() throws Exception
	{
		double discountAmount1 = tr.getDiscountAmount(5.97, 10);
		assertEquals(String.format("%.2f",discountAmount1), "0.60");

		double discountAmount2 = tr.getDiscountAmount(4.47, 25);
		assertEquals(String.format("%.2f",discountAmount2), "1.12");
		
		double discountAmount3 = tr.getDiscountAmount(11.96, 0);
		assertEquals(String.format("%.2f",discountAmount3), "0.00");
		
		double discountAmount4 = tr.getDiscountAmount(20.93, 0);
		assertEquals(String.format("%.2f",discountAmount4), "0.00");
		
		double discountAmount5 = tr.getDiscountAmount(5.98, 50);
		assertEquals(String.format("%.2f",discountAmount5), "2.99");
	}
	
	@Test
	public void getFinalChargeTest() throws Exception
	{
		double preDiscountTotal1 = tr.getPreDisountCharge(Tool.LADW, 3);
		double preDiscountTotal2 = tr.getPreDisountCharge(Tool.CHNS, 3);
		double preDiscountTotal3 = tr.getPreDisountCharge(Tool.JAKD, 4);
		double preDiscountTotal4 = tr.getPreDisountCharge(Tool.JAKR, 7);
		double preDiscountTotal5 = tr.getPreDisountCharge(Tool.JAKR, 2);
		
		double discountAmount1 = tr.getDiscountAmount(5.97, 10);
		double discountAmount2 = tr.getDiscountAmount(4.47, 25);
		double discountAmount3 = tr.getDiscountAmount(11.96, 0);
		double discountAmount4 = tr.getDiscountAmount(20.93, 0);
		double discountAmount5 = tr.getDiscountAmount(5.98, 50);
		
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal1,discountAmount1)),"5.37");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal2,discountAmount2)),"3.35");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal3,discountAmount3)),"11.96");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal4,discountAmount4)),"20.93");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal5,discountAmount5)),"2.99");
	}
}
