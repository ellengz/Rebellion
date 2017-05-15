package rebellion;

public class test2 
{
	static int x = 0;
	public static void main(String args[])
	{
		Cop cop = new Cop();
		cop.setPositionX(12);
		Cop temp = cop;
		temp.setPositionX(50);
		//System.out.println(cop.getPositionX());
		System.out.println(Math.floor(1/0));
		
		
	}
	public void value(int x)
	{
		x=10;
		
	}

}
