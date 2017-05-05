
public class Expr {
	public String addr="";
	public String code="";
	public static int counter=0;
	public static int ifCounter = -1;
	public static int elseCounter = 0;
	public static int EndifCounter = -1;
	
	public String genCount() {
		return "t"+counter++;
	}
	
	public String genIfCount() {
		ifCounter++;
		return "L"+ifCounter;
	}
	
	public String genElseCount() {
		ifCounter++;
		return "L"+elseCounter++;
	}
	
	public String genEndIfCount() {
		return "Goto LL"+(ifCounter-1);
	}

}
