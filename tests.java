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
	static toolRental tr = new toolRental();
	
	public static void main(String[] args) throws Exception
	{
		printRentalAgreementTest();
		getDueDateTest();
		getPreDisountChargeTest();
		isFourthOfJulyTest();
		isFirstMondayOfSeptemberTest();
		getDiscountAmountTest();
		getFinalChargeTest();
	}
	
	@Test
	public static void printRentalAgreementTest() throws Exception
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
	public static void getDueDateTest()
	{
		assertEquals(tr.getDueDate(Tool.JAKR, "9/3/15", 5), "9/8/15");
		assertEquals(tr.getDueDate(Tool.LADW, "7/2/20", 3), "7/5/20");
		assertEquals(tr.getDueDate(Tool.CHNS, "7/2/15", 5), "7/7/15");
		assertEquals(tr.getDueDate(Tool.JAKD, "9/3/15", 6), "9/9/15");
		assertEquals(tr.getDueDate(Tool.JAKR, "7/2/15", 9), "7/11/15");
		assertEquals(tr.getDueDate(Tool.JAKR, "7/2/20", 4), "7/6/20");
	}
	
	@Test
	public static void getPreDisountChargeTest()
	{
		double preDiscountTotal1 = tr.getPreDisountCharge(Tool.JAKR, "9/3/15", 5);
		assertEquals(String.format("%.2f",preDiscountTotal1), "14.95");
		
		double preDiscountTotal2 = tr.getPreDisountCharge(Tool.LADW, "7/2/20", 3);
		assertEquals(String.format("%.2f",preDiscountTotal2), "5.97");

		double preDiscountTotal3 = tr.getPreDisountCharge(Tool.CHNS, "7/2/15", 5);
		assertEquals(String.format("%.2f",preDiscountTotal3), "7.45");
		
		double preDiscountTotal4 = tr.getPreDisountCharge(Tool.JAKD, "9/3/15", 6);
		assertEquals(String.format("%.2f",preDiscountTotal4), "17.94");
		
		double preDiscountTotal5 = tr.getPreDisountCharge(Tool.JAKR, "7/2/15", 9);
		assertEquals(String.format("%.2f",preDiscountTotal5), "26.91");
		
		double preDiscountTotal6 = tr.getPreDisountCharge(Tool.JAKR, "7/2/20", 4);
		assertEquals(String.format("%.2f",preDiscountTotal6), "11.96");
	}
	
	@Test
	public static void isFourthOfJulyTest() throws ParseException
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
	public static void isFirstMondayOfSeptemberTest() throws ParseException
	{
		SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
		Date date1 = formatter.parse("9/6/21");//true
		Date date2 = formatter.parse("9/13/21");//false
		assertTrue(tr.isFirstMondayOfSeptember(date1));
		assertFalse(tr.isFirstMondayOfSeptember(date2));
	}
	
	@Test
	public static void getDiscountAmountTest() throws Exception
	{
		double discountAmount1 = tr.getDiscountAmount(14.95, 10);
		assertEquals(String.format("%.2f",discountAmount1), "1.50");
		
		double discountAmount2 = tr.getDiscountAmount(5.97, 10);
		assertEquals(String.format("%.2f",discountAmount2), "0.60");

		double discountAmount3 = tr.getDiscountAmount(7.45, 25);
		assertEquals(String.format("%.2f",discountAmount3), "1.86");
		
		double discountAmount4 = tr.getDiscountAmount(17.94, 0);
		assertEquals(String.format("%.2f",discountAmount4), "0.00");
		
		double discountAmount5 = tr.getDiscountAmount(26.91, 0);
		assertEquals(String.format("%.2f",discountAmount5), "0.00");
		
		double discountAmount6 = tr.getDiscountAmount(11.96, 50);
		assertEquals(String.format("%.2f",discountAmount6), "5.98");
	}
	
	@Test
	public static void getFinalChargeTest() throws Exception
	{
		double preDiscountTotal1 = tr.getPreDisountCharge(Tool.JAKR, "9/3/15", 5);
		double preDiscountTotal2 = tr.getPreDisountCharge(Tool.LADW, "7/2/20", 3);
		double preDiscountTotal3 = tr.getPreDisountCharge(Tool.CHNS, "7/2/15", 5);
		double preDiscountTotal4 = tr.getPreDisountCharge(Tool.JAKD, "9/3/15", 6);
		double preDiscountTotal5 = tr.getPreDisountCharge(Tool.JAKR, "7/2/15", 9);
		double preDiscountTotal6 = tr.getPreDisountCharge(Tool.JAKR, "7/2/20", 4);
		
		double discountAmount1 = tr.getDiscountAmount(14.95, 10);
		double discountAmount2 = tr.getDiscountAmount(5.97, 10);
		double discountAmount3 = tr.getDiscountAmount(7.45, 25);
		double discountAmount4 = tr.getDiscountAmount(17.94, 0);
		double discountAmount5 = tr.getDiscountAmount(26.91, 0);
		double discountAmount6 = tr.getDiscountAmount(11.96, 50);
		
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal1,discountAmount1)),"13.46");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal2,discountAmount2)),"5.37");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal3,discountAmount3)),"5.59");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal4,discountAmount4)),"17.94");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal5,discountAmount5)),"26.91");
		assertEquals(String.format("%.2f",tr.getFinalCharge(preDiscountTotal6,discountAmount6)),"5.98");
	}
}
