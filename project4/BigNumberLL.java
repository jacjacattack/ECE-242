// class Link creates Link objects used to create BigNumberLL
class Link{ 
	public int val = 0; 
	public Link prev = null; 
	public Link next = null;
	
	public Link(int val){ 
		this.val = val; 
		next = null; 
		prev = null; 
	}
} // end of Link class



////////////////////////////////////////////////////////////////////



// class BigNumberLL
class BigNumberLL { 
	// variables used in constructors and methods
	public Link first; // reference to first Link 
	public Link last; // reference to last Link 
	private int size=0; // size of the number (#Links) 
	private boolean positive=true; // sign of the big number
	
///// Constructors /////
	
	// simple constructor 
	BigNumberLL(){ 
		first=null; 
		last=null; 
	} // end of simple BigNumberLL constructor

	// a constructor that takes a string and converts it into a BigNumber 
	BigNumberLL(String s){
		// check if sign is positive or negative, set accordingly
		if(s.charAt(0)=='-'){ setSign(false);} else{setSign(true);}
		// replace all commas and non-integer elements
		s=s.replaceAll("[^\\d.]", "");
		s=s.replace(",",""); s=s.replace(".","");
		// for loop reads each char in string and puts it into linked list
		for(int index=0; index<s.length(); index++){
			// convert char in string to to int
			int temp = Character.getNumericValue(s.charAt(index));
			// insert this number at the end of the BigNumberLL
			insertLast(temp);
		} // end of for loop
	} // end of BigNumberLL constructor
	
///// Basic Methods /////
	
	// insertLast method places a Link in the last position, taken from lecture
	public void insertLast(int input){
		Link newLink = new Link(input);
		if(isEmpty() == true){
			first = newLink;
			last = newLink;
		}
		else{
			last.next = newLink;
			newLink.prev = last;
			last = newLink;
		}
		size++;
	} // end of insertLast method
	
	// insertFirst method places a link in the first position, taken from lecture
	public void insertFirst(int input){
		Link newLink = new Link(input);
		if(isEmpty() == true){
			first = newLink;
			last = newLink;
		}
		else{
			first.prev = newLink;
			newLink.next = first;
			first = newLink;
		}
		size++;
	} // end of insertFirst method
	
	// deleteFirst method removes the first link in the linked list
	public void deleteFirst(){
		// check if the list is empty, if not then proceed
		if(first!=null){
			// if there is only one item in the list, everything becomes null
			if(first.next == null){first=null; last=null;}
			// otherwise if there is stuff in the list, remove just the first link
			else{first.next.prev=null; first = first.next;}
		}
	} // end of deleteFirst method
	
	// trimFront method removes all zeroes at the front of the linked list
	public void trimFront(){
		Link current = first;
		// delete zero as long as there are zeroes to delete
		while(current.val==0 && current!=last){
			deleteFirst();
			current = current.next;
		}
	} // end of trimFront method
	
	// isEmpty method checks if there is anything in the linked list
	public boolean isEmpty(){
		if(first==null && last==null){return true;}
		else{return false;}
	} // end of isEmpty method
	
	// getSize method returns number of digits in BigNumberLL
	public int getSize(){ return size; } // end of getSize method
	
	// setSign method changes the sign of the BigNumberLL
	public void setSign(boolean z){ positive=z; } // end of setSign method
	
	// getSign method returns the sign of the BigNumberLL
	public boolean getSign(){ return positive; } // end of getSign method
	
	// toString method prints out the linked list, adding commas when appropriate
	public String toString(){
		String result = "";
		trimFront();
		int count=0; 
		Link current = last;
		while(current != null){
			count++;
			result = current.val + result;
			if(count%3 == 0 && current.prev != null){result = "," + result;}
			current = current.prev;
			// System.out.println(current.val);
		} // end of for loop
		if(getSign()==false){result = "-" + result;}
		return result;
	} // end of toString method
	
///// Operation Methods //////
	
	// compare method takes two BigNumberLL and checks their sign, returns
	public static int compare(BigNumberLL x, BigNumberLL y){
		int value = -1;
		// takes the difference to see which is larger
		// if the difference is positive, return 1
		BigNumberLL subtract = subtract(x,y);
		subtract.trimFront();
		// if the sum is zero, make it return zero automatically
		if(subtract.first == subtract.last && x.getSize() == y.getSize() && subtract.first.val == 0){value=0;}
		else if(subtract.getSign()==true){value=1;}
		return value;
	} // end of compare method
	
	// add method takes two BigNumberLL and adds them together, returns sum
	public static BigNumberLL add(BigNumberLL x, BigNumberLL y){
		// create return object
		BigNumberLL sumList = new BigNumberLL();
		// create variables
		Link xCurrent = x.last;
		Link yCurrent = y.last;
		int sum = 0;
		// checks if the two numbers are positive or negative, if both same sign, then add
		if(x.getSign() == y.getSign()){
			if(x.getSign() == false){ sumList.setSign(false);}
			// while loop adds as long as there is at least one value remaining
			while(xCurrent!=null || yCurrent!=null){
				// if there are no values left in the top number
				if(xCurrent==null){
					sum = sum + yCurrent.val;
					sumList.insertFirst(sum%10);
					sum=(sum/10);
					yCurrent=yCurrent.prev;
				}
				// if there are no values left in the bottom number
				else if(yCurrent==null){
					sum = sum + xCurrent.val;
					sumList.insertFirst(sum%10);
					sum=(sum/10);
					xCurrent=xCurrent.prev;
				}
				// if both top and bottom numbers are non-null cells, add them together
				else if(xCurrent!=null && yCurrent!=null){
					// add the cells
					sum = sum + xCurrent.val + yCurrent.val;
					// if sum is greater than nine, something must be carried over to the next column
					if(sum>9){
						sumList.insertFirst(sum%10);
						sum=(sum/10);
						xCurrent=xCurrent.prev;
						yCurrent=yCurrent.prev;
					}
					// if sum is less than nine, print and clear the sum
					else{
						sumList.insertFirst(sum);
						sum=0;
						xCurrent=xCurrent.prev;
						yCurrent=yCurrent.prev;
					}
				}
			} // end of while loop
			// if there are no more numbers to add, but there is still a remainder, add it in
			if(xCurrent==null && yCurrent==null && sum!=0){sumList.insertFirst(sum);}
		} // end if addition if statement
		// if both are different signs, they can be subtracted to get the proper answer
		else{
			// must switch one sign before subtracting, so that it is done properly
			if(y.getSign()==false){y.setSign(true); sumList.setSign(true);}
			else{y.setSign(false); sumList.setSign(false);}
			sumList = subtract(x,y);}
		return sumList;
	} // end of add method
	
	// subtract method takes two BigNumberLL and subtracts them, returns difference
	public static BigNumberLL subtract(BigNumberLL x, BigNumberLL y){
		// create return object
		BigNumberLL diffList = new BigNumberLL();
		// create variables
		Link xCurrent;
		Link yCurrent;
		int diff = 0;
		// checks if the two numbers are positive or negative, if both different sign, then subtract
		if(x.getSign() == y.getSign()){
			// make sure difference is the correct sign
			if((x.getSign() && y.getSign()) == false){diffList.setSign(false);}
			// before subtracting, make it so that the top number is always the bigger number
			if(y.getSize()>x.getSize()){ // if bottom has more digits than top, then bottom must be bigger
				BigNumberLL temp = x; x = y; y = temp; // switch so that x is larger number
				diffList.setSign(false); // the resulting difference must be negative
			}
			else if(y.getSize()==x.getSize()){ // if top and bottom number have same number of digits, check which is bigger
				Link xCheck = x.first; Link yCheck = y.first; boolean swap = false;
				// check number by number to see if bottom is larger than top
				while(xCheck!=null){
					// if a bottom value is larger than its respective top value, swap and break
					if(yCheck.val>xCheck.val){swap=true; break;}
					else if(xCheck.val>yCheck.val){break;}
					// keep searching until you reach the end of the number
					xCheck = xCheck.next; yCheck = yCheck.next;
				}
				// if swap is necessary, perform now
				if(swap==true){
					BigNumberLL temp = x; x = y; y = temp; // switch so that x is larger number
					diffList.setSign(false); // the resulting difference must be negative
				}
				
			} // now top should be larger than bottom, and we should know if result going to be positive or negative
			xCurrent = x.last;
			yCurrent = y.last;
			// while loop subtracts as long as there there are values in the top
			while(xCurrent!=null){
				// if there are no values left in the bottom number
				if(yCurrent==null){
					diff = -diff + xCurrent.val;
					if(diff<0){
						diff = 10 + diff; // if you add 10 to the difference, you get the correct value for that cell
						diffList.insertFirst(diff); // put that correct value in the list
						diff = 1; // set the difference to be -1, must remember that there is a carry
						xCurrent = xCurrent.prev;
					}
					// is the difference is positive
					else{
						diffList.insertFirst(diff); // add difference to the list
						diff = 0; // set difference to zero, there is no carry
						xCurrent = xCurrent.prev;
					}	
				}	
				// if there are two values to subtract
				if(xCurrent!=null && yCurrent!=null){
					// subtract the cells
					diff = -diff + xCurrent.val - yCurrent.val;
					// if the difference is negative
					if(diff<0){
						diff = 10 + diff; // if you add 10 to the difference, you get the correct value for that cell
						diffList.insertFirst(diff); // put that correct value in the list
						diff = 1; // set the difference to be -1, must remember that there is a carry
						xCurrent = xCurrent.prev;
						yCurrent = yCurrent.prev;
					}
					// if the difference is positive
					else{
						diffList.insertFirst(diff); // add difference to the list
						diff = 0; // set difference to zero, there is no carry
						xCurrent = xCurrent.prev;
						yCurrent = yCurrent.prev;
					}	
				}
			} // end of while loop
		} // end of sign checking loop
		// if both are different signs, they can be added to get the proper answer
		else{ 
			// must switch one sign before adding, so that it is done properly
			if(y.getSign()==false){y.setSign(true); diffList.setSign(true);}
			else{y.setSign(false); diffList.setSign(false);}
			diffList = add(x, y); 
		}
		return diffList;
	} // end of subtract method
	
	// multiply base method multiplies a base integer to a BigNumberLL
	public static BigNumberLL multiplyBase(BigNumberLL x, int i){
		// create return object
		BigNumberLL base = new BigNumberLL();
		// create necessary variables
		Link current = x.last; int product = 0;
		// multiplies each cell of linked list to a integer
		for(int count=x.getSize(); count>0; count--){
			product = product + (current.val*i);
			// if product is greater than 9, inserts the remainder and carries the carry over
			if(product>9){
				base.insertFirst(product%10);
				product = (product/10);
				current = current.prev;
			}
			// if product is less than nine, inserts as normal multiplication
			else{
				base.insertFirst(product);
				product = 0;
				current = current.prev;
			}
		}
		base.insertFirst(product); // if there is a carry at the end, insert it here
		return base;
	} // end of multiplyBase method
	
	// multiply method takes two BigNumberLL and multiplies them, returns the product
	public static BigNumberLL multiply(BigNumberLL X, BigNumberLL Y){
		// create return object
		BigNumberLL prodList = new BigNumberLL();
		BigNumberLL temporary = new BigNumberLL();
		int count = 0;
		prodList.insertFirst(0);
		// set sign of product to false if one number is negative
		if(X.getSign()!=Y.getSign()){prodList.setSign(false);}
		// switch so that larger length number is on top, actual values are irrelevant
		if(Y.getSize()>X.getSize()){ BigNumberLL temp=X; X=Y; Y=temp;}
		else if(Y.getSize()==X.getSize()){ // if top and bottom number have same number of digits, check which is bigger
			Link xCheck = X.first; Link yCheck = Y.first; boolean swap = false;
			// check number by number to see if bottom is larger than top
			while(xCheck!=null){
				// if a bottom value is larger than its respective top value, swap and break
				if(yCheck.val>xCheck.val){swap=true; break;}
				else if(xCheck.val>yCheck.val){break;}
				// keep searching until you reach the end of the number
				xCheck = xCheck.next; yCheck = yCheck.next;
			}
			// if swap is necessary, perform now
			if(swap==true){
				BigNumberLL temp = X; X = Y; Y = temp; // switch so that x is larger number
			}
		} // now the larger number should be on top
		Link yCurrent = Y.last;
		// for as long as there are values, multiply
		while(yCurrent!=null){
			// multiplication consists of adding up all the bases that have been already multiplied together
			temporary = multiplyBase(X,yCurrent.val);
			for(int j=0; j<count;j++){
				temporary.insertLast(0);
			}
			prodList = add(prodList,temporary);
			yCurrent = yCurrent.prev;
			count++; // every addition adds a value starting in the next "column", must multiply by ten each time to move over a column
		}
		return prodList;
	} // end of multiply method
	
	// divide method takes two BigNumberLL and divides them, returns the quotient
	public static BigNumberLL divide(BigNumberLL X, BigNumberLL Y){
		// create return object
		BigNumberLL quotList = new BigNumberLL();
		// create a quotList with the value of 1 for counting purposes
		BigNumberLL addOne = new BigNumberLL();
		addOne.insertFirst(1);
		// trim zeroes
		X.trimFront(); Y.trimFront();
		// set sign of product to false if one number is negative
		if(X.getSign()!=Y.getSign()){quotList.setSign(false);}
		// set signs of both inputs to positive values to simplify division
		X.setSign(true); Y.setSign(true);
		// division is repeated subtraction, returning how many times you have subtracted
		while(X.getSign()==true){
			X = subtract(X,Y);
			X.toString(); // this is used as a buffer to slow it down, without this is runs infinitely
			quotList = add(quotList,addOne);
		}
		quotList = subtract(quotList,addOne);
		return quotList;
	} // end of divide method
	
} // end of BigNumberLL