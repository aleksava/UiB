import edu.princeton.cs.algs4.LinearRegression;

class LinearReg {
	public static void main(String[] args) {
		
		double[] arr1 = {3.7, 4.0, 4.3, 4.6, 4.9, 5.2, 5.5};
		double[] quickArr = {-1.88, -1.73, -1.53, -1.32, -1.01, -0.85, -0.61};
		double[] quickXarr = {-1.97, -1.84, -1.64, -1.33, -1.15, -0.87, -0.67};
		
		LinearRegression oppg3 = new LinearRegression(arr1, quickXarr);
		
		System.out.println(oppg3.toString());
		
		//QuickArr = 0.00 n + 0.02 /R^2 = 0.985)
		//QuickXArr = 0.00 n + 0.01 /R^2 = 0.986)
		
		//LOG QuickArr = 0.72 n + (-4.61)  (R^2 = 0,994)
		//Y = 0.72X - 4.61 
		//a=0.72, b=.4.61
		//LOG QuickXArr = 0.75 n + (-4.82)  (R^2 = 0.993)
		//Y = 0.75X - 4.82
		//a=0.75, b=-4.82
		
	}
}