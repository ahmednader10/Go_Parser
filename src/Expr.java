
public class Expr {
	public String addr="";
	public String code="";
	public static int counter=0;
	
	public String genCount() {
		return "t"+counter++;
	}

}
