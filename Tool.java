package toolRentalApplication;

public enum Tool 
{
	/*
	 * Each tool instance has the following attributes:
	 * Tool Code - Unique identifier for a tool instance
	 * Brand - The brand of the ladder, chain saw or jackhammer.
	 * Tool Type - The type of tool. The type also specifies the daily rental charge, and the days for which the daily rental charge applies:
	 */
	LADW(ToolType.Ladder,"Werner",1.99,true,true,false),
	CHNS(ToolType.Chainsaw,"Stihl",1.49,true,false,true),
	JAKR(ToolType.Jackhammer,"Ridgid",2.99,true,false,false),
	JAKD(ToolType.Jackhammer,"DeWalt",2.99,true,false,false);
	
	private final ToolType type;
	private final String brand;
	private final double dailyCharge;
	private final boolean weekdayCharge;
	private final boolean weekendCharge;
	private final boolean holidayCharge;
	
	Tool(ToolType type, String brand, double dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) 
	{
		this.type = type; 
		this.brand = brand;
		this.dailyCharge = dailyCharge;
		this.weekdayCharge = weekdayCharge;
		this.weekendCharge = weekendCharge;
		this.holidayCharge = holidayCharge;
	}
	
	public ToolType getType() {return this.type;}
	
	public String getBrand() {return this.brand;}
	
	public double getDailyCharge() {return this.dailyCharge;}
	
	public boolean weekdayCharge() {return this.weekdayCharge;}
	
	public boolean weekendCharge() {return this.weekendCharge;}
	
	public boolean holidayCharge() {return this.holidayCharge;}
}
