// begin DictionarryApplication
class DictionaryApplication{

	// begin main method
    public static void main(String args[]){

    // variables used	
    String myFileName = "N/A";  // name of file being uploaded
    int myFileLength = 0;  // how large the array is
    boolean myFileStatus = false;  // if the file has been sorted
    long startTime, endTime, reportedTime;  // measure of time in ms
    boolean initialized = false; // will only allow other methods to run if data has been initialized
    	
	// declare instance of EasyIn
	EasyIn easy = new EasyIn();
	
	// declare instance of FastDictionary class
	FastDictionary dict = new FastDictionary(myFileName, myFileLength, myFileStatus);
	
	// welcome message
	System.out.println();
	System.out.println("Welcome to the Dictionary App");
	System.out.println("=============================");

    String pause;     
	int command = -1;
	
	// while loop that allows program to run until user enters 0 to quit application
	while (command != 0) {
	    if (command>0) {pause = easy.readString();} // just to create a pause before printing the menu again
	    System.out.println(); 
	    System.out.println("Current Dictionary is '"+dict.getName()+"' of size "+dict.getSize()+" and "+dict.getStatus());  
	    System.out.println();
            
        // print menu
	    dict.ToolsMenu();
	    command = easy.readInt();


	    // switch statement which allows different options to be accepted
	    switch(command)
		{
		
			
		// load dictionary from a text file
		case  1:
			System.out.println("Enter dictionary filename:");
			myFileName = easy.readString();
			dict.loadDictionary(myFileName);
			initialized = true;
			System.out.println("<< OKAY, dictionary " + myFileName + " has loaded >>");
		    break;
		    
		    
		// sort dictionary using insertion sort, calculates time elapsed
		case  2:
			if(initialized == true){
				startTime = System.currentTimeMillis();
				dict.insertionSort();
				endTime = System.currentTimeMillis();
				reportedTime = endTime - startTime;
				myFileStatus = true;
				System.out.println("<<OK dictionary sorted in " + reportedTime + "ms and save in file ’" + dict.getSaveFile() + "’ >>");
			}
			else{
				System.out.println(">>>>>>>>> Dictionary must be loaded first! <<<<<<<<<<");
				System.out.println("Selection Invalid! Try again.");
			}
		    break;
		    
		    
		// sort dictionary using enhanced insertion sort, calculates time elapsed
        case  3:
        	if(initialized == true){
				startTime = System.currentTimeMillis();
				dict.insertionSortEnhanced();
				endTime = System.currentTimeMillis();
				reportedTime = endTime - startTime;
				myFileStatus = true;
				System.out.println("<<OK dictionary sorted in " + reportedTime + "ms and save in file ’" + dict.getSaveFile() + "’ >>");
			}
			else{
				System.out.println(">>>>>>>>> Dictionary must be loaded first! <<<<<<<<<<");
				System.out.println("Selection Invalid! Try again.");
			}
		    break;
		
		
		// shuffle dictionary randomly, calculates time elapsed
        case 4:
        	if(initialized == true){
				startTime = System.currentTimeMillis();
				dict.shuffleDictionary();
				endTime = System.currentTimeMillis();
				reportedTime = endTime - startTime;
				myFileStatus = false;
				System.out.println("<<OK dictionary sorted in " + reportedTime + "ms and save in file ’" + dict.getSaveFile() + "’ >>");
			}
			else{
				System.out.println(">>>>>>>>> Dictionary must be loaded first! <<<<<<<<<<");
				System.out.println("Selection Invalid! Try again.");
			}
		    break;


		// searches for word in dictionary, reports if it has been found
		case  5:
			if(initialized == true){
				if(myFileStatus == true){
					dict.binarySearch();
				}
				else{
					System.out.println(">>>>>>>>> Dictionary must be sorted first! <<<<<<<<<<");
					System.out.println("Selection Invalid! Try again.");
				}
			}
			else{
				System.out.println(">>>>>>>>> Dictionary must be loaded first! <<<<<<<<<<");
				System.out.println("Selection Invalid! Try again.");
			}
		    break;


		// spell checker takes a text file as input, and sees how many words in text file are in dictionary, reprints file
		case 6:
			if(initialized == true){
				if(myFileStatus == true){
					dict.spellChecker();
				}
				else{
					System.out.println(">>>>>>>>> Dictionary must be sorted first! <<<<<<<<<<");
					System.out.println("Selection Invalid! Try again.");
				}
			}
			else{
				System.out.println(">>>>>>>>> Dictionary must be loaded first! <<<<<<<<<<");
				System.out.println("Selection Invalid! Try again.");
			}
		    break;
		     
		      
		// anagram takes in a word and checks if any of the words in the dictionary are anagrams
		case 7:
			if(initialized == true){
				if(myFileStatus == true){
					System.out.println("Enter a word to analyze:");
					String inputWord = easy.readString();
					dict.anagram(inputWord);
				}
				else{
					System.out.println(">>>>>>>>> Dictionary must be sorted first! <<<<<<<<<<");
					System.out.println("Selection Invalid! Try again.");
				}
			}
			else{
				System.out.println(">>>>>>>>> Dictionary must be loaded first! <<<<<<<<<<");
				System.out.println("Selection Invalid! Try again.");
			}
		    break;
        
    
        // exit program         
		case 0:
		    break;


		// if a wrong number is typed in on accident, throws error    
		default:
		    System.out.println("Selection Invalid!- Try Again");
		}
	}

	// goodbye message
	System.out.println("Goodbye!");
	
    } // end of main method
    
} // end of DictionaryApplication