// BookApp class begins
public class BookApp {

	// ToolMenu method prints the options menu
	public static void ToolsMenu(){
		System.out.println("Menu");
		System.out.println("====");
		System.out.println("1-From admin BST database, extract user custom BST database using PubYear");
		System.out.println("2-From admin BST database, extract user custom BST database using Publisher");
		System.out.println("3-Display in-order user BST database");
		System.out.println("4-Convert user BST database into array of ISBN");
		System.out.println("5-Search admin database using ISBN array");
		System.out.println("6-Plot user BST database (via array)");
		System.out.println("0-Exit");
	} // end of ToolsMenu
	
	// main method begins
	public static void main(String[] args){
		
		// create BookBST object and initialize the data into BST by ISBN number
		BookBST mainBookBST = new BookBST("BestDB.txt");
		BookBST sub; // creates a name for subtree
		
		// print out welcome statement
		System.out.println("Welcome to Book Collection App ("+mainBookBST.size+" items)");
		System.out.println("============================================================");
		System.out.println();
		
		// this application continues to perform operations until user exits the application
		boolean go = true; // sets a boolean value to true, will run code until go is false
		boolean array=false; // this boolean is false until option 4 runs, this prevents users from doing option 5 prematurely
		EasyIn easy = new EasyIn(); // inserts an instance of Easy In to read users input
		while(go){ //this while loop initializes each method that corresponds to the user's input
			ToolsMenu(); // prints menu
			System.out.print("\nCommand: ");
			int b = easy.readInt(); //integer that represents the user's command input
			if(b==1){ // runs option 1
				System.out.println("Which year are you interested in?");
				int d = easy.readInt(); // reads integer from keyboard
				String s = Integer.toString(d); // converts integer to string to be used in methods
				System.out.println("Begin subtree extraction for PubYear = " + d +  " using in-order traversal");
				sub=null; // set subtree to null if one already exists
				sub=new BookBST(1,s,mainBookBST.root); // create the subtree
				mainBookBST.subtree = sub; // links subtree to main tree
				System.out.println("Number of Items Found: "+mainBookBST.subtree.size); // prints items in subtree
				System.out.println();
			}
			else if(b==2){ // runs option 2
				System.out.println("Which publisher are you interested in?");
				String s = easy.readString();
				System.out.println("Begin subtree extraction for Publisher = " + s + " using in-order traversal");
				sub=null; // set subtree to null if one already exists
				sub=new BookBST(2,s,mainBookBST.root); // create the subtree
				mainBookBST.subtree = sub; // links subtree to main tree
				System.out.println("Number of Items Found: "+mainBookBST.subtree.size); // prints items in subtree
				System.out.println();
			}
			else if(b==3){ // runs option 3
				// throws statements if certain conditions are not met
				if(mainBookBST.subtree==null){
					System.out.println("Please create a user custom BST. Select option 1 or 2.");
					System.out.println();
				}
				// runs code
				else{
					System.out.println("List in-order "+mainBookBST.subtree.size+" items sorted by Title.");
					mainBookBST.subtree.displayBST(mainBookBST.subtree.root); // display the subtree
					System.out.println();
				}
			}
			else if(b==4){ // runs option 4
				// throws statements if certain conditions are not met
				if(mainBookBST.subtree==null){
					System.out.println("Please create a user custom BST. Select option 1 or 2.");
					System.out.println();
				}
				// runs code
				else{
					mainBookBST.initializeArray(); // initializes array to N/A
					mainBookBST.fillArray(mainBookBST.subtree.root,0); // fills array with values from the subtree
					mainBookBST.displayArray(); // displays array
					array = mainBookBST.doesArrayExist(); // sets boolean array to true if the array holds values
					System.out.println();
				}
			}
			else if(b==5){ // runs option 5
				// throws statements if certain conditions are not met
				if(mainBookBST.subtree==null && array==false){System.out.println("Please create a user custom BST. Select option 1 or 2.");}
				else if(mainBookBST.subtree!=null && array==false){System.out.println("Please create an array from user custom BST. Select option 4.");}
				// runs code
				else if(mainBookBST.subtree!=null && array==true){
					Long startTime = System.currentTimeMillis(); // starts counting time
					mainBookBST.printArray(mainBookBST.root); // prints array
					Long endTime = System.currentTimeMillis(); // stops counting time
					System.out.println(endTime-startTime+"ms"); // reports total time elapsed
					System.out.println();
				}	
			}
			else if(b==6){ // runs option 6
				// throws statements if certain conditions are not met
				if(mainBookBST.subtree==null && array==false){System.out.println("Please create a user custom BST. Select option 1 or 2.");}
				else if(mainBookBST.subtree!=null && array==false){System.out.println("Please create an array from user custom BST. Select option 4.");}
				// runs code
				else if(mainBookBST.subtree!=null && array==true){
			        mainBookBST.drawTree(); // draws tree in GUI
				}
			}
			else if(b==0){ // runs option 0
				System.out.println("Good-bye!"); //closes the scanner and prints out "Goodbye!" when the user's input is 0
				go = false; // sets go as false and cancels application
			}
		} // end of while loop		
	} // end of main method
} // end of BookApp class