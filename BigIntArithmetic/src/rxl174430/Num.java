package rxl174430;

import java.util.ArrayDeque;
import java.util.Deque;

public class Num implements Comparable<Num> {
	static long defaultBase = 10;
	long base;
	long[] arr;
	boolean isNegative;
	int len;

	public Num(String s) {
		base = defaultBase;
		constructStringNum(s);
	}

	private void constructStringNum(String s) {
		// Handle for new Num("-00000000000000000") len is 0 and arrayLength considers "-" and leading zeroes in its calculation 
		// Handle Small bases
		long arrLength = (long) Math.ceil((s.length()+1) * (Math.log(10) / Math.log(base))+1);
		//System.out.println(arrLength);
		arr = new long[(int) arrLength];
		if (s.charAt(0) == '-') {
			isNegative = true;
			recursive(s.substring(1, s.length()), 0);
		} else if (s.charAt(0) == '+') {
			isNegative = false;
			recursive(s.substring(1, s.length()), 0);
		} else
			recursive(s, 0);
	}

	private Num(long[] arr, int size, boolean isNegative) {
		this.arr = arr;
		this.len = size;
		this.isNegative = isNegative;
		this.base = defaultBase;
	}

	private void recursive(String quotient, int index) {
		if (quotient.length() < 19) {
			if (Long.parseLong(quotient) == 0) {
				len = index;
				return;
			}
		}
		// long[] arrTemp;
		long arrLength = 18 - Long.toString(base).length();
		// arrTemp = new long[(int) ((quotient.length() / arrLength) +
		// ((quotient.length() % arrLength) == 0 ? 0 : 1))];
		long temporaryLength = (long) (quotient.length() / arrLength) + ((quotient.length() % arrLength) == 0 ? 0 : 1);
		long temporaryNumber;
		long temporaryNumber2;
		String temp;
		long remainder = 0;
		String quotientString = "";
		// System.out.println(arrLength + " " + arrTemp.length);
		for (long i = 0; i < temporaryLength; i = i + 1) {
			long subLength = i * arrLength;
			temp = Long.toString(remainder).concat(quotient.substring((int) (subLength),
					(int) ((subLength + arrLength) < quotient.length() ? (subLength + arrLength) : quotient.length())));
			temporaryNumber = Long.valueOf(temp);
			// Remainder handling
			if (i != 0) {
				if (i != temporaryLength - 1) {
					if (remainder == 0) {
						int iter = 0;
						temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength),
								(int) (subLength + iter + 1)));
						
						while(iter < arrLength-1 && temporaryNumber2 < base) {
							
							quotientString = quotientString.concat("0");
							iter++;
							temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength),
									(int) (subLength + iter + 1)));
						}
//						if (base <= temporaryNumber2) {
//							for (int z = 0; z < Long.toString(base).length() - 1; z++) {
//								quotientString = quotientString.concat("0");
//							}
//						} else {
//							for (int z = 0; z < Long.toString(base).length(); z++) {
//								quotientString = quotientString.concat("0");
//							}
//
//						}
					} else if (Long.toString(base).length() != Long.toString(remainder).length()) {
					
						temporaryNumber2 = Long.valueOf(Long.toString(remainder).concat(quotient.substring(
								(int) (subLength),
								(int) (subLength + Long.toString(base).length() - Long.toString(remainder).length()))));
						if (base <= temporaryNumber2) {
							for (int z = 0; z < Long.toString(base).length() - Long.toString(remainder).length()
									- 1; z++) {
								quotientString = quotientString.concat("0");
							}
						} else {
							for (int z = 0; z < Long.toString(base).length() - Long.toString(remainder).length(); z++) {
								quotientString = quotientString.concat("0");
							}

						}
					}
				} else if (i == temporaryLength - 1) {

					if (remainder == 0) {

						temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength), (int) (subLength + 1)));
						int iter = (int)subLength + 1;
						while(iter < quotient.length() && temporaryNumber2 < base) {
							quotientString = quotientString.concat("0");
							iter++;
							temporaryNumber2 = Long.valueOf(quotient.substring((int) (subLength),
									(int) (iter)));
						}
//						if (temporaryNumber2 < base) {
//							for (int z = 0; z < Long.toString(temporaryNumber2).length(); z++) {
//								quotientString = quotientString.concat("0");
//							}
//						} else {
//							long var1 = Long.parseLong(
//									Long.toString(temporaryNumber2).substring(0, Long.toString(base).length()));
//							if (var1 < base) {
//								for (int z = 0; z < Long.toString(var1).length(); z++) {
//									quotientString = quotientString.concat("0");
//								}
//							} else {
//								for (int z = 0; z < Long.toString(var1).length() - 1; z++) {
//									quotientString = quotientString.concat("0");
//								}
//							}
//						}
					} else if (Long.toString(base).length() != Long.toString(remainder).length()) {
						
						Long lastPart = Long.parseLong(Long.toString(remainder)
								.concat(quotient.substring((int) subLength, quotient.length())));
						if (lastPart < base) {
							for (int z = 0; z < quotient.substring((int) subLength, quotient.length()).length()-1; z++) {
								//System.out.println("hello"+z);
								quotientString = quotientString.concat("0");
							}
							
						} else {//copy need to test
							temporaryNumber2 = Long.valueOf(Long.toString(remainder)
									.concat(quotient.substring((int) (subLength), (int) (subLength
											+ Long.toString(base).length() - Long.toString(remainder).length()))));
							if (base <= temporaryNumber2) {
								for (int z = 0; z < Long.toString(base).length() - Long.toString(remainder).length()
										- 1; z++) {
									quotientString = quotientString.concat("0");
								}
							} else {
								for (int z = 0; z < Long.toString(base).length()
										- Long.toString(remainder).length(); z++) {
									quotientString = quotientString.concat("0");
								}

							}
						}
					}

				}
			}
			//System.out.print("temporaryNumber" + Long.toString(temporaryNumber));
			/*
			* if (remainder == 0 && i != 0) { for (int z = 0; z <
			* Long.toString(temporaryNumber).length() - 1; z++) { quotientString =
			* quotientString.concat("0"); } }
			*/
			remainder = temporaryNumber % base;
			//System.out.print(" remainder- " + Long.toString(remainder));
			if(!quotientString.isEmpty() && quotientString.equals("0")) {
				quotientString = "";
			}
			quotientString = quotientString.concat(Long.toString(temporaryNumber / base));
			//System.out.println(" quotient" + quotientString);

		}
		//System.out.println("quotient string-" + quotientString);
		//System.out.println("Remainder string-" + remainder);
		//System.out.println(index);
		arr[index] = remainder;
		recursive(quotientString, index + 1);

	}

	public Num(long x) {
		base = defaultBase;
		if (x == Long.MIN_VALUE) {
			constructStringNum(Long.toString(x));
		}
		else {
			if (x == 0) {
				len = 1;
				arr = new long[len];
				arr[len - 1] = x;
			} else {
				this.isNegative = x < 0 ? true : false;
				int i = 0;
				x = Math.abs(x);
				len = getNumberLength(x);
				arr = new long[len];
				i = 0;
				while (x > 0) {
					arr[i] = x % base;
					x = x / base;
					i = i + 1;
				}
			}
		}		
	}

	private Num(String x, long base) {
		this.base = base;
		constructStringNum(x);
	}

	private int getNumberLength(long x) {
		int i = 0;
		while (x > 0) {
			x = x / base;
			i = i + 1;
		}
		return i;
	}
	
	public static Num add(Num a, Num b) {
		if (isNumberZero(a)) {
			return b;
		}
		if (isNumberZero(b)) {
			return a;
		}
		boolean isNegative;
		if (a.isNegative && b.isNegative || !a.isNegative && !b.isNegative) {
			if (a.isNegative && b.isNegative) {
				isNegative = true;
			} else {
				isNegative = false;
			}
			long[] firstNumber = a.arr;
			long[] secondNumber = b.arr;
			int sizeOfNumA = a.len;
			int sizeOfNumB = b.len;
			int sizeOfSum = (sizeOfNumA > sizeOfNumB ? sizeOfNumA : sizeOfNumB) + 1;
			long[] sum = new long[sizeOfSum];
			long carry = 0;
			int i = 0;
			int j = 0;
			int k = 0;
			while (i < sizeOfNumA && j < sizeOfNumB) {
				sum[k] = (firstNumber[i] + secondNumber[j] + carry) % defaultBase;
				carry = (firstNumber[i] + secondNumber[j] + carry) / defaultBase;
				i++;
				j++;
				k++;
			}
			if (i < sizeOfNumA) {
				while (i < sizeOfNumA) {
					sum[k] = (firstNumber[i] + carry) % defaultBase;
					carry = (firstNumber[i] + carry) / defaultBase;
					i++;
					k++;
				}
			} else if (j < sizeOfNumB) {
				while (j < sizeOfNumB) {
					sum[k] = (secondNumber[j] + carry) % defaultBase;
					carry = (secondNumber[j] + carry) / defaultBase;
					j++;
					k++;
				}
			}
			if (carry != 0) {
				sum[k] = carry;
			} else {
				k--;
			}
			return new Num(sum, k + 1, isNegative);
		} else {
			return subtract(a, negateNumber(b));
		}
	}

	public static boolean isSignEqual(Num a, Num b) {
		if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative)) {
			return true;
		} else
			return false;
	}

	public static Num negateNumber(Num a) {
		Num negatedNum = new Num(a.arr, a.len, !a.isNegative);
		return negatedNum;
	}

	public static Num subtract(Num a, Num b) {
		if (isNumberZero(a)) {
			return negateNumber(b);
		}
		if (isNumberZero(b)) {
			return a;
		}

		if (a.compareTo(b) == 0) {
			return new Num(0);
		}

		if (isSignEqual(a, b)) {
			boolean isNegative;
			int sizeOfNumA = a.len;
			int sizeOfNumB = b.len;
			int sizeOfDiff;
			long[] firstNumber;
			long[] secondNumber;
			int firstNumberLength, secondNumberLength;
			if (sizeOfNumA > sizeOfNumB) {
				sizeOfDiff = sizeOfNumA;
				firstNumber = a.arr;
				secondNumber = b.arr;
				firstNumberLength = sizeOfNumA;
				secondNumberLength = sizeOfNumB;
				isNegative = a.isNegative;
			} else if (sizeOfNumA < sizeOfNumB) {
				sizeOfDiff = sizeOfNumB;
				firstNumber = b.arr;
				secondNumber = a.arr;
				firstNumberLength = sizeOfNumB;
				secondNumberLength = sizeOfNumA;
				isNegative = b.isNegative;
			} else {
				int x = sizeOfNumA - 1;
				int isEqual = 0;
				while (x >= 0) {
					if (a.arr[x] < b.arr[x]) {
						isEqual = -1;
						break;
					} else if (a.arr[x] > b.arr[x]) {
						isEqual = 1;
						break;
					}
					x--;
				}
				if (isEqual == -1) {
					sizeOfDiff = sizeOfNumB;
					firstNumber = b.arr;
					secondNumber = a.arr;
					firstNumberLength = sizeOfNumB;
					secondNumberLength = sizeOfNumA;
					isNegative = b.isNegative;
				} else {
					sizeOfDiff = sizeOfNumA;
					firstNumber = a.arr;
					secondNumber = b.arr;
					firstNumberLength = sizeOfNumA;
					secondNumberLength = sizeOfNumB;
					isNegative = a.isNegative;
				}

			}
			int compare = a.compareTo(b);
			if (compare == -1) {
				isNegative = true;
			} else {
				isNegative = false;
			}
			long[] difference = new long[sizeOfDiff];
			int i = 0;
			int j = 0;
			int k = 0;
			long carry = 0;
			while (i < firstNumberLength && j < secondNumberLength) {
				if ((firstNumber[i] - carry) >= secondNumber[j]) {
					difference[k] = firstNumber[i] - secondNumber[j] - carry;
					carry = 0;
				} else {
					difference[k] = (firstNumber[i] + defaultBase) - secondNumber[j] - carry;
					carry = 1;
				}
				i++;
				j++;
				k++;
				
			}
			while (i < firstNumberLength) {
				if ((firstNumber[i] - carry) >= 0) {
					difference[k] = firstNumber[i] - carry;
					carry = 0;
				} else {
					difference[k] = (firstNumber[i] + defaultBase) - carry;
					carry = 1;
				}
				i++;
				k++;
			}
			return new Num(difference, getLengthWithoutLeadingZeros(difference), isNegative);
		} else {
			return add(a, negateNumber(b));
		}

	}

	public static int getLengthWithoutLeadingZeros(long[] a) {
		int len = a.length;
		int i = len - 1;
		int count = 0;
		while (i > 0 && a[i] == 0) {
			count++;
			i--;
		}
		return len - count;
	}

	public static Num product(Num a, Num b) {
		if (isNumberZero(a) || isNumberZero(b)) {
			return new Num(0);
		}
		boolean isNegative;
		int sizeOfLargerNum = 0;
		int sizeOfSmallerNum = 0;
		int compare = a.compareTo(b);
		long[] firstNumber;
		long[] secondNumber;
		if (compare == -1) {
			firstNumber = b.arr;
			sizeOfLargerNum = b.len;
			secondNumber = a.arr;
			sizeOfSmallerNum = a.len;

		} else {
			firstNumber = a.arr;
			sizeOfLargerNum = a.len;
			secondNumber = b.arr;
			sizeOfSmallerNum = b.len;

		}
		int sizeOfProduct = sizeOfLargerNum + sizeOfSmallerNum;
		long[] product = new long[sizeOfProduct];
		long carry = 0;
		int i;
		int j;
		int k = 0;
		for (i = 0; i < sizeOfLargerNum; i++) {
			carry = 0;
			for (j = 0; j < sizeOfSmallerNum; j++) {
				k = i + j;
				long prod = product[k] + (firstNumber[i] * secondNumber[j]) + carry;
				product[k] = prod % defaultBase;
				carry = prod / defaultBase;
			}
			if (carry != 0) {
				product[k + 1] = product[k + 1] + carry;
			}
		}
		if (carry != 0) {
			k++;
			product[k] = carry;
		}
		if ((a.isNegative && b.isNegative) || (!a.isNegative && !b.isNegative)) {
			isNegative = false;
		} else {
			isNegative = true;
		}

		return new Num(product, k + 1, isNegative);
	}

	
	public static Num power(Num a, long n) {
		return power(a, new Num(n));
	}
	
	private static Num power(Num a, Num n) {
		if (isNumberZero(n)) {
			return new Num(1);
		} else {
			Num p = power(Num.product(a, a), n.by2());
			return mod(n,new Num(2)).compareTo(new Num(1)) == 0 ? Num.product(p, a) : p;
		}
	}

	
	public static Num divide(Num a, Num b) {
		Num sign = null;
		if (isSignEqual(a, b)) {
			sign = new Num(1);
		} else {
			sign = new Num(-1);
		}
		a = a.isNegative ? negateNumber(a) : a;
		b = b.isNegative ? negateNumber(b) : b;

		if (b.compareTo(new Num(0)) == 0) {
			throw new IllegalArgumentException("Cannot divide by zero");
		}
		if (b.compareTo(new Num(1)) == 0) {
			return product(a, sign);
		}
		if (b.compareTo(a) == 0) {
			return sign;
		}
		if (b.compareTo(a) > 0) {
			return new Num(0);
		}
		Num low = new Num(0);
		Num high = a;
		while (true) {
			Num mid = add(low, ((subtract(high, low)).by2()));
			Num operation = subtract(product(b, mid), a);
			if (operation.isNegative) {
				if ((negateNumber(operation)).compareTo(b) <= 0) {
					return product(mid, sign);
				}
			} else {
				if ((operation).compareTo(new Num(0)) == 0) {
					return product(mid, sign);
				}
			}

			if (product(b, mid).compareTo(a) == -1) {
				low = mid;
			} else {
				high = mid;
			}
		}
	}

	private static boolean isNumberZero(Num a) {
		return (a.len == 1) && (a.arr[0] == 0);
	}

	
	public static Num mod(Num a, Num b) {
		if (b.compareTo(new Num(0)) == 0) {
			throw new IllegalArgumentException("Cannot divide by zero");
		}
		if (b.compareTo(new Num(1)) == 0) {
			return new Num(0);
		}
		if (b.compareTo(a) == 0) {
			return new Num(0);
		}
		if (b.compareTo(a) > 0) {
			return a;
		}
		Num low = new Num(0);
		Num high = a;
		while (true) {
			Num mid = add(low, ((subtract(high, low)).by2()));
			Num operation = subtract(product(b, mid), a);
			if (operation.isNegative) {
				if ((negateNumber(operation)).compareTo(b) <= 0) {
					return negateNumber(operation);
				}
			} else {
				if ((operation).compareTo(new Num(0)) == 0) {
					return new Num(0);
				}
			}

			if (product(b, mid).compareTo(a) == -1) {
				low = mid;
			} else {
				high = mid;
			}
		}
	}

	
	public static Num squareRoot(Num a) {
		Num low = new Num(0);
		Num high = a;
		Num sqrt = new Num(1);
		while (low.compareTo(high) <= 0) {
			Num mid = add(low, ((subtract(high, low)).by2()));
			Num operation = product(mid, mid);
			int comparison = operation.compareTo(a);
			if (comparison == -1) {
				low = add(mid, new Num(1));
				sqrt = mid;
			} else if (comparison == 0) {
				return mid;
			} else {
				high = subtract(mid, new Num(1));
			}
		}
		return sqrt;
	}

	
	@Override
	public int compareTo(Num other) {
		if ((this.isNegative && other.isNegative) || (!this.isNegative && !other.isNegative)) {
			if (this.len > other.len)
				return (this.isNegative && other.isNegative) ? -1 : 1;
			else if (this.len == other.len) {
				int i = this.len - 1;
				int isEqual = 0;
				while (i >= 0) {
					if (this.arr[i] < other.arr[i]) {
						isEqual = (this.isNegative && other.isNegative) ? 1 : -1;
						break;
					} else if (this.arr[i] > other.arr[i]) {
						isEqual = (this.isNegative && other.isNegative) ? -1 : 1;
						break;
					}
					i--;
				}
				return isEqual;
			} else
				return (this.isNegative && other.isNegative) ? 1 : -1;
		} else if (this.isNegative) {
			return -1;
		} else {
			return 1;
		}
	}

	
	public void printList() {
		System.out.print(base() + ": ");
		for (int i = 0; i < len; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println(isNegative ? "-" : "");
	}


	@Override
	public String toString() {
		Num n = convertNumToBase10(this);
		StringBuilder sb = new StringBuilder();
		if(n.isNegative && !isNumberZero(n)) {
			sb.append('-');
		}
		for(int i = n.len - 1; i >= 0; i--) {
			sb.append(Long.toString(n.arr[i]));
		}
		return sb.toString();
	}

	public long base() {
		return base;
	}
	

	private Num convertNumToBase10(Num num) {
		int i = num.len-1;
		long originalBase = defaultBase;
		defaultBase = 10;
		Num newNum = new Num(num.arr[i]);
		Num baseNum = new Num(base);
		while(i>0) {
			Num prodNum = product(newNum,baseNum);
			newNum = add(prodNum,new Num(num.arr[i-1]));
			i--;
		}
		defaultBase = originalBase;
		newNum.isNegative = num.isNegative;
		return newNum;
	}

	public Num convertBase(int newBase) {
		if (newBase != base) {
			return new Num(toString(), newBase);
		} else
			return this;
	}

	public Num by2() {
		long carry = 0;
		long[] newArr = new long[this.len];
		for (int i = this.len - 1; i >= 0; i--) {
			long remainder = this.arr[i] + carry * base;
			newArr[i] = remainder >> 1;
			carry = remainder - (newArr[i] * 2);
		}

		return new Num(newArr, getLengthWithoutLeadingZeros(newArr), this.isNegative);
	}

	private static boolean isOperator(String str) {
		if (str.equals("*") || str.equals("+") || str.equals("-") || str.equals("/") || str.equals("%")
				|| str.equals("^")) {
			return true;
		} else {
			return false;
		}
	}

	private static Num doOperation(Num operand1, Num operand2, String operator) throws Exception {
		Num result = null;
		switch (operator) {
		case "+":
			result = add(operand1, operand2);
			break;
		case "-":
			result = subtract(operand1, operand2);
			break;
		case "*":
			result = product(operand1, operand2);
			break;
		case "/":
			result = divide(operand1, operand2);
			break;
		case "%":
			result = mod(operand1, operand2);
			break;
		case "^": 
			result = power(operand1,operand2);
			break;
		default:
			throw new Exception("Unsupported Operation");
		}
		return result;
	}

	
	public static Num evaluatePostfix(String[] expr) throws Exception {
		Deque<String> stack = new ArrayDeque<String>();
		for (String exp : expr) {
			if (!isOperator(exp)) {
				stack.push(exp);
			} else {
				String operandString2 = stack.pop();
				String operandString1 = stack.pop();
				Num operand1 = new Num(operandString1);
				Num operand2 = new Num(operandString2);
				Num result = doOperation(operand1, operand2, exp);
				stack.push(result.toString());
			}
		}
		Num answer = new Num(stack.pop());
		return answer;
	}

	private static int getOperatorPriority(String operator) throws Exception {
		int priority = 0;
		switch (operator) {
		case "+":
			priority = 1;
			break;
		case "-":
			priority = 1;
			break;
		case "*":
			priority = 2;
			break;
		case "/":
			priority = 2;
			break;
		case "%":
			priority = 2;
			break;
		case "^":
			priority = 3;
			break;
		default:
			throw new Exception("Unsupported Operator");
		}
		return priority;

	}

	public static Num evaluateInfix(String[] expr) throws Exception {
		Deque<String> stack = new ArrayDeque<String>();
		Deque<String> queue = new ArrayDeque<String>();

		for (String exp : expr) {
			if (!isOperator(exp) && !exp.equals("(") && !exp.equals(")")) {
				queue.addLast(exp);
			} else if (isOperator(exp)) {
				while (stack.peek()!=null && isOperator(stack.peek())&&getOperatorPriority(stack.peek()) >= getOperatorPriority(exp)) {
					queue.addLast(stack.pop());
				}
				stack.push(exp);
			} else if (exp.equals("(")) {
				stack.push(exp);
			} else if (exp.equals(")")) {
				while (!stack.peek().equals("(")) {
					queue.addLast(stack.pop());
				}
				stack.pop();
			}
		}
		while (stack.size() > 0) {
			queue.addLast(stack.pop());
		}
		String[] postfixExpression = queue.toArray(new String[queue.size()]);
		return evaluatePostfix(postfixExpression); //
	}

	public static void main(String[] args) throws Exception {
		Num a = new Num(Long.MIN_VALUE);
		Num b = new Num(Long.MIN_VALUE+1);
		a.printList();
		b.printList();
		Num sub = subtract(a,b);
		sub.printList();
		System.out.println(sub.toString());
	}
}