import java.util.Stack;

public class SimpleCalculator <E> {
	
	//private String[] str;
	int length;
	Stack<String> ops = new Stack<String>(); //Stack for the operators of the equation
	Stack<Integer> nums = new Stack<Integer>(); //Stack for the numbers of the equation
	
	public SimpleCalculator(String s) throws NumberFormatException {
		
		//str = new String[s.length()];
		System.out.println("Length of str: " + s.length());
		System.out.println(s);
		this.length = s.length();
		pushToStack(s);
	}
	
	public void pushToStack(String str) {
		
		boolean isNumber = true;
		String pushable = "";
		
		for(int i = 0; i < length; i++) {
			isNumber = true;
			System.out.println("Now looking at: " + str.substring(i,i+1));
			
			try { 
				pushable += Integer.parseInt(str.substring(i,i+1)); 
				System.out.println("Pushable: " + pushable);
				}
			catch (NumberFormatException e) { 
				System.out.println("Not a number: " + str.substring(i, i+1)); 
				isNumber = false; 
			}
			
			if(isNumber) { continue; }
			else {
				System.out.println("Current sub: " + str.substring(i,i+1));
				try { nums.push(Integer.parseInt(pushable)); 
				System.out.println("Pushed as int: " + pushable);}
				catch (NumberFormatException e) {
					System.out.println("Failed to push as int: " + pushable);
					//e.printStackTrace();
				}
				ops.push(str.substring(i,i+1));
				System.out.println("Pushed as operator: " + str.substring(i, i+1));
				pushable = "";
			}
			
		}
	}
	
	public int calculate() {
		
		int sum = 0;
		int num1 = 0;
		int num2 = 0;
		String operator = "";
		
		for(int i = 0; i < this.length; i++) {
			num1 = nums.pop();
			num2 = nums.pop();
			operator = ops.pop();
			if(operator.equals("+")) {
				sum = num1 + num2;
			}
			else if(operator.equals("*")) {
				sum = num1*num2;
			}
		}
		
		return sum;
	}
	
	public static void main(String[] args) {
		
		String input = "10+2=*3+4=="; //Output should be 21
		
		SimpleCalculator calc = new SimpleCalculator(input);
		
		System.out.println(calc.calculate());
	}
}
