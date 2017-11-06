// import statements
import java.io.*;
import java.util.*; 


// begin FastDictionary
class FastDictionary{

	
///////// Variables to complete Constructors
	
	
	private String fileName; // name of file being used
	private int fileSize; // size of array & number of words in dictionary
	private boolean fileStatus; // whether or not the dictionary has been sorted
	private String[] array; // string array that holds the dictionary data
	private String saveFileName; // name of file being used for a save file
	EasyIn easy = new EasyIn();  // new instance of EasyIn to read file and inputed data
	
	
///////// Creates FastDictionary constructor object that will be used to run other methods
    public FastDictionary(String name, int size, boolean status) {
    		fileName = name; fileSize = size; fileStatus = status;
    }   

    
///////// Methods

    
    // Method that prints menu options
    public void ToolsMenu() {
	System.out.println("Menu");
	System.out.println("====");
	System.out.println("1-Load dictionary file");          
	System.out.println("2-Sort dictionary using Insertion sort");
	System.out.println("3-Sort dictionary using Enhanced Insertion sort");
	System.out.println("4-Shuffle dictionary");
	System.out.println("5-Search word");
	System.out.println("6-SpellChecker");
	System.out.println("7-Anagram");
	System.out.println("0-Exit");
	System.out.println("");
	System.out.print("Command: ");
    }


// Methods for OPTION 1


	// Method that loads a selected dictionary's data into an array
    public void loadDictionary(String fileName) {
    		// sets the inputed fileName as the fileName for the object
    		this.fileName=fileName;
    		// tests if file works, if not throws exception
    		try{
				File file = new File(fileName);
				Scanner scan = new Scanner(file);
				fileSize = scan.nextInt();
				array = new String[fileSize];
				int arrayIndex = 0;
				scan.nextLine(); // skips a line so that it processes data correctly
				// fills us the array with the data, line by line
				while(scan.hasNext() && (arrayIndex<fileSize)){
				    array[arrayIndex] = scan.nextLine();
				    arrayIndex++;
				}
				scan.close();
			}
			catch (FileNotFoundException e) {
	         e.printStackTrace();
        	}
    } // end of loadDictionary


// Methods for OPTION 2


	// Method sorts the array using insertion sort technique, linear
	public void insertionSort(){
		int i,j;
		String temp;
		// outer loop counts through each word in the array
		for(j=1; j<array.length; j++){
 			temp=array[j];
 			i = j-1;
 				// inner loop sorts array word by word
 				while(i >= 0){
 					if(temp.compareTo(array[i]) >= 0){ break;}
					array[i+1] = array[i];
					i--;
				} // end inner loop
			array[i+1] = temp;
		} // end outer loop
		// tells object that data has been sorted, saves the data to a new .txt file per saveDictionary
		fileStatus = true;
		saveFileName = fileName + "-sorted";
		saveDictionary(saveFileName);
	} // end of insertionSort
	

// Methods for OPTION 3


	// Method sorts the array using insertion sort technique, binary, improved speed
	public void insertionSortEnhanced(){
		// outer loop goes through each word in the array
        for (int i=0; i<fileSize; ++i){
            String temp=array[i];
            int left=0;
            int right=i;
            // inner loop sorts data
            while (left<right){
                int middle=(left+right)/2;
                if (temp.compareTo(array[middle]) >= 0){
                	left=middle+1;
                }
                else{
                	right=middle;
                }
            } // end inner loop
            // swaps values
            for (int j=i; j>left; j--){
            	swap(j-1, j);
            }
        } // end outer loop
        // tells object that data has been sorted, saves the data to a new .txt file per saveDictionary
        fileStatus = true;
        saveFileName = fileName + "-sorted-enhanced";
        saveDictionary(saveFileName);
	} // end insertionSortEnhanced

	
	//Method computes the swap between values, used in insertionSortEnhanced
	public void swap(int i, int j){
		String temp = array[i];
		array[i] = array[j];
		array[j] = temp;
	} // end of swap
	

// Methods for OPTION 4


	// Method shuffles dictionary randomly using Fisher-Yate algorithm
	public void shuffleDictionary(){
		Random rnd = new Random();
		int out,index;
		String temp1;
		for(out=fileSize-1; out>0; out--){
			index = rnd.nextInt(out+1);
			temp1 = array[index];
			array[index] = array[out];
			array[out] = temp1;
		}
		// tells object that data has been unsorted, saves the data to a new .txt file per saveDictionary
		fileStatus = false;
		saveFileName = fileName + "-unsorted";
		saveDictionary(saveFileName);
		printArray();
	} // end of shuffleDictionary
    
    
// Methods for OPTION 5


	// Method searches though a sorted file and reports if word is in dictionary, how many steps and position
	public void binarySearch(){
		System.out.println("Enter a word you would like to search for:");
		String inputSearch = easy.readString();
		boolean found  = false;
		int position = 0;
		int steps = 0;
		int first = 0;
		int last = array.length;
		// loop sorts data
		while (first < last) {
			steps++;
	        int mid = (first + last) / 2;
	        if (inputSearch.compareTo(array[mid]) == 0){
	        	found = true; position = mid; break;
	        }
	        else if (inputSearch.compareTo(array[mid]) < 0) {
	            last = mid;
	        } 
	        else if (inputSearch.compareTo(array[mid]) > 0) {
	            first = mid + 1;
	        } 
	    }
		// prints different statements depending on if the word has been found
		// if it has been found, also returns index in array and how many steps needed to find it
		if(found == true){
        	System.out.println("<<OK word found>> using Binary search in "+steps+" steps and at position "+position+"!");
        }
        else{
        	System.out.println("Sorry, word was not found in dictionary!");
        }
	} // end of binarySearch
	
	
// Methods for OPTION 6	
	
	
	// Method reads a file entered into console, and for each word, reports if in dictionary, then prints out file
	public void spellChecker(){
		System.out.println("Enter text filename:");
		String spellFileName = easy.readString();
		boolean correct = true;
		try{
			File spellFile = new File(spellFileName);
			Scanner fileScanner = new Scanner(spellFile);
			while(fileScanner.hasNextLine()){
				String readLine = fileScanner.nextLine();
				Scanner lineScanner = new Scanner(readLine);
				while(lineScanner.hasNext()){
					String readWord = lineScanner.next();
					System.out.print(" ");
					String word1=readWord.replaceAll("[^\\p{L}]", "").toLowerCase();
					correct = findWord(word1);
					if(correct == true){
						System.out.print(readWord + "");
					}
					else					
					System.out.print( "("+readWord+")" );
				}
				System.out.println("");
				lineScanner.close();
			}
			fileScanner.close();
		}
		catch (FileNotFoundException e) {
         e.printStackTrace();
    	}
	}  // end spellChecker
	
	
	// Method runs insertionSortEnhanced specifically for spellChecker
	public boolean findWord(String inputHere){
		boolean found  = false;
		int first = 0;
		int last = array.length;
		while (first < last) {
	        int mid = (first + last) / 2;
	        if (inputHere.compareTo(array[mid]) == 0){
	        	found = true; break;
	        }
	        else if (inputHere.compareTo(array[mid]) < 0) {
	            last = mid;
	        } 
	        else if (inputHere.compareTo(array[mid]) > 0) {
	            first = mid + 1;
	        } 
	    }
		return found;
	} // end of findWord
	

// Methods for OPTION 7
	
	
	// Method prints out all words that have same letter as word in anagramArray
	public void anagram(String word){
		// makes the word into a lower case char array, sets isAnagram to false
		word = word.toLowerCase();
		char[] firstArray = word.toCharArray();
		boolean isAnagram = false;
		// goes through array word by word
		for(int index=0; index<fileSize; index++){
			// creates a char array of second word, which is the specific word at that index in the array
			char[] secondArray = array[index].toCharArray();
			// if the two words have the same length, it proceeds
			if(firstArray.length == secondArray.length){
				// goes though each letter of each char array
				for(int pos=0; pos<firstArray.length; pos++){
					for(int i=0; i<secondArray.length; i++){
						// states that the status of the anagram is false until two letters are equal
						if(firstArray[pos] != secondArray[i]){
							isAnagram = false;
							// if it is still false at the end of the second word, it quits looking at that word
							if(i == secondArray.length-1){
								pos = firstArray.length;
			                	}
						}
						// if two letters are equal, it sets status to true and breaks
						// puts a blank space where old letter was to prevent it from being read twice
						else{
							isAnagram = true;
							secondArray[i] = ' ';
							break;
						}
					}
				}
					// if each letter of the word has been found, then it prints the word
					if(isAnagram == true){
						System.out.println(array[index]);
					}
			}
		}
	} // end of anagram
	
	
// General Methods


	// Method returns the same of the file bring read
    public String getName(){
    	return fileName;
    } // end of getName  
   
   
    // Method returns the size of the array
    public int getSize(){
    	return fileSize;
    } // end of getSize


	// Method returns the status of the array, whether or not it has been sorted
    public String getStatus(){
    	if(fileStatus == true){ return "sorted";}
    	else{ return "unsorted";}
    } // end of getStatus
    
    
    //Method returns name of the current save file name
    public String getSaveFile(){
    	return saveFileName;
    } // end of getSaveFile
    
    
    //Method saves array data to a new file
    public void saveDictionary(String sfn) {
    	// tests if file works, if not throws exception
    	try{
    		File file2 = new File (saveFileName+".txt"); // change save location
    		BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
    		// prints out the fileSize to document, but first converts it to a string for easier insertion into new file
    		String firstLine = Integer.toString(fileSize);
    		writer.write(firstLine);
    		writer.write("\n");
    		// prints out each word in the array in order
    		for(int i=0; i<fileSize; i++){
    			writer.write(array[i]);
    			writer.write("\n");
    		}
    		writer.flush();
    		writer.close();
    	}
    	catch(FileNotFoundException e)
    	{
    		System.out.println("File Not Found");
    		System.exit(1);
    	}
    	catch(IOException e)
    	{
    		System.out.println("Exception found");
    		System.exit(1);
    	}
    }	// end of saveDictionary
    
    
    //Method prints out array, used only for debugging
    public void printArray(){
    	for(int i=0; i<fileSize; i++){
    		System.out.println(array[i]);
    	}
    } // end of printArray


} // end of FastDictionary