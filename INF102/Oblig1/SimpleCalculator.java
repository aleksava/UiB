import java.util.Stack;

public class SimpleCalculator {
	
	int length;
	Stack<String> ops = new Stack<String>(); //Stack for the operators of the equation
	Stack<Integer> nums = new Stack<Integer>(); //Stack for the numbers of the equation
	
	public SimpleCalculator(String s) {
		
		this.length = s.length();
		pushToStack(s);
	}
	
	public void pushToStack(String str) {
		
		boolean isNumber = true;
		boolean finished = false;
		boolean oneEquals = false;
		String pushable = "";
		
		for(int i = 0; i < length; i++) {
			
			//Breaking out in case there are two '=' in a row, as per syntax
			if(finished) {
				break;
			}
			
			isNumber = true;
			
			//Creating the number that is to be pushed by adding the digits until it hits an operator
			//Will set isNumber to false, when the current substring is no longer a number
			try { 
				pushable += Integer.parseInt(str.substring(i,i+1)); 
				}
			catch (NumberFormatException e) { 
				isNumber = false; 
			}
			
			if(isNumber) { continue; }
			else {
				
				//Pushes the full integer to the stack
				try { 
					nums.push(Integer.parseInt(pushable)); 
				}
				catch (NumberFormatException e) {
					//Do nothing
				}
				pushable = "";
				
				//Looks if the current operator is the sign '=' and acts accordingly
				//Uses the operator '=' as a sign that it's time to pop() some fools.
				if(str.substring(i,i+1).equals("=")) {
					
					//If it feels like bad code, and looks like bad code. Then it's probably bad code...
					//I'm sorry :( But I'm really tired, and it's getting late. To be fair, it works
					if(oneEquals) { finished = true; }
					else { oneEquals = true; }
					
					//Holds the calculated number for troubleshooting, and then pushes the result
					int calced = calculate();
					if(calced == -1) { //Handles mistakes in the calculate()
						System.out.println("Something went wrong in calculate!");
						System.exit(0);
					}
					nums.push(calced);
				}
				
				//Pushes the current operator
				//NOTE: It doesn't push '='
				else {
					ops.push(str.substring(i,i+1));
				}
			}
		}
	}
	
	//Calculates the top 2 int's in the stack, using the top of the operand stack
	public int calculate() {
		
		int sum = 0;
		int num1 = 0;
		int num2 = 0;
		String operator = "";
		
		num1 = nums.pop();
		num2 = nums.pop();
		operator = ops.pop();
		
		//Multiplying the operands
		if(operator.equals("*")) {
			sum = num1 * num2;
			return sum;
		}
		
		//Adding together the operands
		if(operator.equals("+")) {
			sum = num1 + num2;
			return sum;
		}
		
		//In case there is a mistake
		return -1;
	}
	
	public static void main(String[] args) {
		
		String input = "10+20=*30+400=="; //Output should be 21
		SimpleCalculator calc = new SimpleCalculator(input);
		
		System.out.println("Here's your calculation: " + input + 
							"\nHere's your result: " + calc.calculate());
	}
}
