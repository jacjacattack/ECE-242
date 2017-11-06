// insert import statements
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// BookBST class begins
public class BookBST {

////////// VARIABLES //////////	
	
	// create variables used in BookBST object
	public Book root; // root node of the tree
	public String key; // key stores the string being compared
	public int size; // size or number of nodes in the tree
	public String filename; // name of the input file
	public BookBST subtree; // name of the subtree
	public int subsize; //size or number of nodes in the subtree
	public int sortNumber=0; // number that determines if we are sorting via IBSN, Publisher Year, or Publisher
	public String[] array = new String[1023]; // creates an array of size 1023
	public int lvl; // stores the level or "height" of a particular node in the array
	
////////// CONSTRUCTORS //////////
	
	// BookBST object, blank, used for general initialization
	public BookBST(){
		size=0;
		root=null;
	} // end of BookBST
	
	// BookBST object creates a Binary Search Tree from a file
	public BookBST(String filename){
		this.filename = filename;
		sortNumber=0;
		subtree = null;
		int count=0; // variable used to count how many books have been inserted into tree, used primarily for debugging
		//tests the bestmedia.txt file and reports if there are any errors
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            // read the first line of the file, which is the size of the library
            size = scanner.nextInt();
            scanner.nextLine(); // puts scanner on next line
            // this loop continues as long as the file has more lines and the array is not full
            while (scanner.hasNext() && count<size){ // inserts books as long as there are more lines in file and has inserted less than total amount of books
                String isbn = scanner.nextLine(); // read ISBN
                String title = scanner.nextLine(); // read TITLE
                String author = scanner.nextLine(); // read AUTHOR
                String publisher = scanner.nextLine(); // read PUBLISHER
                String year = scanner.nextLine(); // read YEAR
                Book newBook = new Book(isbn,title,author,publisher,year); // creates a new book made up of the following data
                insertNode(newBook);
                count++; // increase the amount of books added to tree, used primarilly for debugging
            }
        scanner.close();
        } // end of try
        catch (FileNotFoundException e) {
	         e.printStackTrace();
        } // end of catch
	} // end of BookBST
	
	
	// BookBST object creates a subtree from main BST
	public BookBST(int select, String input, Book rootOfInputTree){
		if(select==1){ // for PubYear subtree
			sortNumber=1; // makes sure that Morris Traverse checks subtree
			key=input; // sets key to the value typed in by the user
			morrisTraverse(rootOfInputTree);
			
		}
		else if (select==2){ // for Publisher subtree
			sortNumber=2; // makes sure that Morris Traverse checks subtree
			key=input; // sets key to the value typed in by the user
			morrisTraverse(rootOfInputTree);
		}
	} // end of BookBST
	
	
////////// METHODS //////////
	
///// GENERAL METHODS

	// insert method inserts a Book into the BookBST
	public void insertNode(Book insertNode){
		if(root==null){ // if BST is empty, insert first node
			root=insertNode;
		}
		else{ // if BST is not empty, inserts node into tree using recursive insert method
			recInsert(root, insertNode);
		}
	} // end of insert method
	
	// recInsert method performs insertion computation for BST
	public void recInsert(Book cur, Book inp){
		// sort by ISBN number
		if(cur!=null){
			if(sortNumber==0){ // update, make all lower case
				if (inp.getISBN().compareTo(cur.getISBN())<0){ // search left
 					if (cur.left==null){ // node needs to be inserted
 					cur.left = new Book(inp.getISBN(), inp.getTITLE(), inp.getAUTHOR(), inp.getPUBLISHER(), inp.getYEAR());
 					}
 					else{
 					recInsert(cur.left,inp); // keep searching
 					}
				}
				else if (inp.getISBN().compareTo(cur.getISBN())>=0){ // search right
 					if (cur.right==null){ // node needs to be inserted
 					cur.right = new Book(inp.getISBN(), inp.getTITLE(), inp.getAUTHOR(), inp.getPUBLISHER(),  inp.getYEAR());
 					}
 					else{
 					recInsert(cur.right,inp);// keep searching
 					}
 				}
			}
			// sort by Title
 			else if(sortNumber==1  || sortNumber==2){
 				if (inp.getTITLE().compareTo(cur.getTITLE())<0){ // if a match is found
 					if (cur.left==null){ // node needs to be inserted
 					cur.left = new Book(inp.getISBN(), inp.getTITLE(), inp.getAUTHOR(), inp.getPUBLISHER(), inp.getYEAR());
 					}
 					else{
 					recInsert(cur.left,inp); // keep searching
 					}
 				}
 				else if (inp.getTITLE().compareTo(cur.getTITLE())>=0){ // search right
 					if (cur.right==null){ // node needs to be inserted
 					cur.right = new Book(inp.getISBN(), inp.getTITLE(), inp.getAUTHOR(), inp.getPUBLISHER(), inp.getYEAR());
 					}
 					else{
 					recInsert(cur.right,inp);// keep searching
 					}
 				}
 			}
		} // end of cur!=null if statement
	} // end of recInsert method
	

///// METHODS FOR OPTIONS 1 & 2
	
	// morrisTraverse method uses Morris Traverse to traverse main Tree for insertion into the subtree
	public void morrisTraverse(Book current){
		// loop runs until it looks at every item in the tree
		Book temp;
		while(current != null){ // loop runs until it runs out of items
			// first goes down to the next left branch, if one exists
			if(current.left != null){
				temp = current.left;
				while(true){ // go down this node's rightmost branches
					if(temp.right == null) break;
					if(temp.right == current) break;
					temp = temp.right;
				} // end of while loop
				if(temp.right == null){ // if there is no right branch, create link to predecessor
					temp.right = current;
					current = current.left;
				} // end of if temp.right==null
				else{ // if there is a right branch, remove the link to the predecessor
					temp.right = null;
					// check if current item matches key and should be inserted
					if(sortNumber==1){ // sort by year
						if(key.compareTo(current.getYEAR())==0){
							insertNode(current); // insert node
							size++; // increase size of tree
						}
					}
					else if(sortNumber==2){ // sort by publisher
						if(key.compareTo(current.getPUBLISHER())==0){
							insertNode(current); // insert node
							size++; // insert size of tree
						}
					} // end of insertion statements
					current = current.right;
				} // end of temp.right==null's else statement
			}
			else{ // check if current item matches key and should be inserted
				if(sortNumber==1){ // sort by year
					if(key.compareTo(current.getYEAR())==0){
						insertNode(current); // insert node
						size++; // increase size of tree
					}
				}
				else if(sortNumber==2){ // sort by publisher
					if(key.compareTo(current.getPUBLISHER())==0){
						insertNode(current); // insert node
						size++; // insert size of tree
					}
				} // end of insertion statements
				current = current.right;
			}
		} // end of while loop
	} // end of morrisTraverse method 

///// METHODS FOR OPTION 3

	// displayBST method prints out the data in tree format, based off of In-Order Traversal
	public void displayBST(Book curr){ 
		if (curr!=null){
			displayBST(curr.left); // recursive searches left side
			// prints out data info
			System.out.println(curr.getISBN()+" "+curr.getTITLE()+" "+curr.getAUTHOR()+" "+curr.getPUBLISHER()+" "+curr.getYEAR()); 
			displayBST(curr.right); // recursive searches right side
		}
	} // end of display method
	
///// METHODS FOR OPTION 4

	// initializeArray method fills all indexes with N/A
	public void initializeArray(){
		for(int i = 0; i<array.length; i++){ // for all items in the array,
			array[i] = "N/A"; // make string value equal to N/A
		}
	} // end of intitializeArray method

	// fillArray method fills array with data from tree
	public void fillArray(Book current, int index){
		int Left = 2*index+1; // integer that represents the index of a left child relative to the initial index 
		int Right = 2*index+2; // // integer that represents the index of a right child relative to the initial index 
		if(current!=null && index<array.length){ // if there is data in the current index, proceed
			array[index]=current.getISBN(); 
			if(current.left!=null && Left<array.length){array[Left]=current.left.getISBN();}
			if(current.right!=null && Right<array.length){array[Right]=current.right.getISBN();}
			fillArray(current.left, Left); // recursively searches left side of tree to fill array
			fillArray(current.right, Right); // recursively searches right side of tree to fill array
			}
	} // end of fillArrayInOrder method

	// countLevel method
	public int countLevel(int index){ //returns the correct level based on the index of each node in our tree 
		if(index==0){lvl=0;} // root is at level zero
		else if(index>=1 && index<=2){lvl=1;}
		else if(index>=3 && index<=6){lvl=2;}
		else if(index>=7 && index<=14){lvl=3;}
		else if(index>=15 && index<=30){lvl=4;}
		else if(index>=31 && index<=62){lvl=5;}
		else if(index>=63 && index<=126){lvl=6;}
		else if(index>=127 && index<=254){lvl=7;}
		else if(index>=255 && index<=510){lvl=8;}
		else if(index>=511 && index<=1022){lvl=9;}
		return lvl;
	} // end of countLebvel method
		
	// doesArrayExist method is used in BookApp to make sure option 5 only runs if array exists and is not equal to zero
	public boolean doesArrayExist(){
		if(lvl==0){return false;}
		else if(lvl>0 && lvl<10){return true;}
		else return false;
	} // end of zeroFinder method	
		
	int level=-1;	
	// displayArray method prints out the ISBN numbers by index
	public void displayArray(){ 
		int count=0;
		System.out.println("[i] " + "    ISBN"); // prints title header
		for(int j=0; j<array.length; j++){
			if(array[j] != "N/A" && count<subtree.size){
				System.out.println(j + "   " + array[j]); // prints out the index and ISBN of each item in our array that is not equal to "N/A"
				count++;
				lvl = countLevel(j); // calls our countLevel method and assigns the value to our lvl variable
			}
		}
		System.out.println("Number of items " + count +";" + " Number of levels " + lvl); // prints out final count of items and largest level
	} // end of displayArray method
	
///// METHODS FOR OPTION 5

	// recFind method recursively searches through admin tree to find Book that matches the array's ISBN number
	public Book recFind(Book current, String i) {
		if (current==null) return null;
		else if (i.compareTo(current.getISBN())<0){ // search left
			current=recFind(current.left,i);
		}
		else if (i.compareTo(current.getISBN())>0){// search right
			current=recFind(current.right,i);
		}
		return current;
	} // end of recFind method
	
	// displaySortedArray prints out data of Book that matches the array's ISBN numbers
	public void printArray(Book BSTroot){
		for(int i=0;i<array.length;i++){
			if(array[i]!="N/A"){
				Book printMe = recFind(BSTroot, array[i]); // recursively find book that matches array ISBN
				// print out data
				System.out.println(printMe.getISBN()+"; "+printMe.getTITLE()+"; "+printMe.getAUTHOR()+"; "+printMe.getPUBLISHER()+"; "+printMe.getYEAR());
			}
		}
	} // end of displaySortedArray method

///// METHODS FOR OPTION 6

	// getXcoor method calculates the proper x coordinate for each point
	public double getXcoor(int index){
		if(index==0){return 0.5;}
		else{ return ( (double)(index+2-countPerRow(index)) / (double)(countPerRow(index)+1)); }
	} // end of getXcoor method
	
	// countPerRow method counts how many items are in each row of the tree
	public int countPerRow(int index){
		int count=0;
		if(index==0){ return count=0; }
		else if(index>=1 && index<=2){count=2;}
		else if(index>=3 && index<=6){count=4;}
		else if(index>=7 && index<=14){count=8;}
		else if(index>=15 && index<=30){count=16;}
		else if(index>=31 && index<=62){count=32;}
		else if(index>=63 && index<=126){count=64;}
		else if(index>=127 && index<=254){count=128;}
		else if(index>=255 && index<=510){count=256;}
		else if(index>=511 && index<=1022){count=512;}
		return count;
	} // end of countPerRow method
	
	// getYcoordinate method calculates the proper y coordinate for each point
	public int getYcoor(int index){
		int ycor=-1;
		if(countLevel(index)==0){ycor=475;}
		else if(countLevel(index)==1){ycor=425;}
		else if(countLevel(index)==2){ycor=375;}
		else if(countLevel(index)==3){ycor=325;}
		else if(countLevel(index)==4){ycor=275;}
		else if(countLevel(index)==5){ycor=225;}
		else if(countLevel(index)==6){ycor=175;}
		else if(countLevel(index)==7){ycor=125;}
		else if(countLevel(index)==8){ycor=75;}
		else if(countLevel(index)==9){ycor=25;}
		return ycor;
	} // end of getYcoordinate method
	
	// drawTree method draws the tree stored in the array
	public void drawTree(){
		// set initial conditions including canvas size, scale, and color scheme
		StdDraw.setPenColor(StdDraw.WHITE);
		StdDraw.setCanvasSize(1000,500);
        StdDraw.setPenColor(StdDraw.MAGENTA);
        StdDraw.setPenRadius(0.005);
        StdDraw.setXscale(0,1);
        StdDraw.setYscale(0,500);
		for(int i=0;i<array.length;i++){ // goes through each item in array
			if(array[i]!="N/A"){ // if there is data stored in that index, proceed
				StdDraw.setPenColor(StdDraw.MAGENTA);
		        StdDraw.setPenRadius(0.015); // increase pen radius to make a large point
				StdDraw.point(getXcoor(i),getYcoor(i));
				StdDraw.setPenRadius(0.005); // decrease pen radius to reduce line size
				int lChild = 2*i+1; // store value of left child index
				int rChild = 2*i+2; // store value of right child index
				if(lChild<1023 && array[lChild]!="N/A"){ // if there is a left child that would fit in the graph, proceed
					StdDraw.line(getXcoor(i), getYcoor(i), getXcoor(lChild), getYcoor(lChild)); // draw line
				}
				if(rChild<1023 && array[rChild]!="N/A"){ // if there is a right child that would fit in the graph, proceed
					StdDraw.line(getXcoor(i), getYcoor(i), getXcoor(rChild), getYcoor(rChild)); // draw line
				}
			}
		}
        StdDraw.show(); // display tree
	} // end of drawTree
	
} // end of BookBST class