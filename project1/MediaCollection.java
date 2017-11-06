// import statements to use scanner, file reader, and more
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

// Creates MediaCollection Class
public class MediaCollection {
    
	// Variables used in method
    private String name;
    private int maxItems = 0;
    private double maxItemPrice;
    private MediaItem[] array;
    private int arrayIndex = 0;
    EasyIn easy = new EasyIn();
    
    // Creates MediaCollection object
    public MediaCollection(String n, int items, double price){
        name = n;  maxItems = items; maxItemPrice = price;
    }
   
    // Method to print library option menu
    public void PrintMenu(){
        System.out.println("Menu");
        System.out.println("====");
        System.out.println("1-See List of Items");
        System.out.println("2-See Item Description");
        System.out.println("3-Add One Item");
        System.out.println("4-Remove One Item");
        System.out.println("5-Set Maximum Price");
        System.out.println("6-Sort Library");
        System.out.println("0-Exit");
        System.out.println();
    }
    
    // Methods to retrieve MediaCollection object data
    public String GetName(){ return(name); }
    public int GetItemLimit(){ return(maxItems); }
    public double GetPriceLimit(){ return(maxItemPrice); }
    
    // Methods to edit MediaCollection object data
    public void SetName(String newName){ name = newName; }
    public void SetItemLimit(int newLimit){ maxItems = newLimit; }
    public void SetPriceLimit(double newPrice){ maxItemPrice = newPrice; }
    
    // Method to initialize data from "bestmedia.txt" file into array
    public void InitializeCollection(){
    	// initializes the array used in all other methods
        array = new MediaItem[maxItems];
        // saves the name of the file to be scanned: bestmedia.txt
        String fileName = "bestmedia.txt";
        //tests the bestmedia.txt file and reports if there are any errors
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            // this loop continues as long as the file has more lines and the array is not full
            while (scanner.hasNext() && arrayIndex < maxItems) {
                String type = scanner.nextLine();  // read entire line into string and go to next line   
                    // scans for if it is a movie or book, then reads corresponding data and adds to array
                	if(type.equals("Movie")){
                        String title = scanner.nextLine();
                        String ref = scanner.nextLine();
                        double price = scanner.nextDouble();
                        String blank1 = scanner.nextLine(); // added in to solve a scanner error, prevents scanner from skipping next input
                        String director = scanner.nextLine();
                        String actor = scanner.nextLine();
                            array[arrayIndex] = new Movie(title, ref, price, director, actor);
                            arrayIndex++;
                        // String blank2 = scanner.nextLine();
                    }
                    if(type.equals("Book")){
                        String title = scanner.nextLine();
                        String ref = scanner.nextLine();
                        double price = scanner.nextDouble();
                        String blank1 = scanner.nextLine(); // added in to solve a scanner error, prevents scanner from skipping next input
                        String author = scanner.nextLine();
                            Book newBook = new Book(title, ref, price, author);
                            array[arrayIndex] = newBook;
                            arrayIndex++;
                        // String blank2 = scanner.nextLine();
                    }
            }
        }
        catch (FileNotFoundException e) {
	         e.printStackTrace();
        }
    }
    
    // Method to print the library data in the array - Option 1
    public void PrintCollection(){
    	// this loop prints each item in the array from 0 to index
        for(int i=0; i<arrayIndex; i++){
        	// this loop will not print items that cost more than maxItemPrice
            if(array[i].GetPrice() <= maxItemPrice){
               // tests if it is a Movie or Book to adjust print statement
               if(array[i].getClass() == Movie.class){
                  System.out.println((i+1) + " " + "Movie" + " '" + array[i].GetTitle() + "' $" + array[i].GetPrice());
               }
               if(array[i].getClass() == Book.class){
                  System.out.println((i+1) + " " + "Book" + " '" + array[i].GetTitle() + "' $" + array[i].GetPrice());
               }
            }
        }
        System.out.println("");
    }
    
    // Method to see more details of an item in the library - Option 2
    public void DetailedItem(){
    	// prompts user to enter in item number of specific item
        System.out.println("Enter Item:");
        int itemSelected = easy.readInt();
        	// finds if item selected is in array, if it exists it prints out details
        	if((itemSelected-1) <= arrayIndex){
        		System.out.println(array[itemSelected-1].WhoAmI());
        		System.out.println("");
        	}
        	else{
        		System.out.println("This item is not in the Library!");
        	}
        }
    
    // Method to add item to array - Option 3
    public void AddItem(){
        System.out.println("Menu");
        System.out.println("====");
        System.out.println("1-Book");
        System.out.println("2-Movie");
        // prompts user to enter if it is a book or movie
        int option = easy.readInt();
        // checks if integer entered is an option, if not then reports error
        if ((option == 1) || (option == 2)){
        	// reads data entered by user and adds Book object to array
            if (option == 1){
                System.out.println("Enter Book Title:");
                String title = easy.readString();
                System.out.println("Enter Book Reference:");
                String ref = easy.readString();
                System.out.println("Enter Author Name:");
                String author = easy.readString();
                System.out.println("Enter Book Price:");
                double price = easy.readDouble();
                array[arrayIndex] = new Book(title, ref, price, author);
                arrayIndex++;
            }
            // reads data entered by user and adds Movie object to array
            if (option == 2){
                System.out.println("Enter Movie Title:");
                String title = easy.readString();
                System.out.println("Enter Movie Reference:");
                String ref = easy.readString();
                System.out.println("Enter Director Name:");
                String director = easy.readString();
                System.out.println("Enter Main Actor Name:");
                String actor = easy.readString();
                System.out.println("Enter Movie Price:");
                double price = easy.readDouble();
                array[arrayIndex] = new Movie(title, ref, price, director, actor);
                arrayIndex++;  
            }  
        }
        else{
        	System.out.println("This is not an available option!\n");
        }
    }
    
    // Method to remove item from array - Option 4
    public void RemoveItem(){
    	// prompts user enter in item in array, if array at that number is empty, returns error
        System.out.println("Which item do you want to remove?");
        int itemEntered = easy.readInt();
        int itemInArray = itemEntered-1;
        // checks if item entered is in array, then shifts array up to write over deleted item
        if((itemEntered < arrayIndex) && (itemInArray >= 0)){
            for(int i=itemInArray; i<=arrayIndex; i++){
                array[i] = array[i+1];
        	}
        arrayIndex--;
        }
        else{ 
        	System.out.println("This item is not in the library!\n");
        }
	}
        
    // Method to set max price of objects in array - Option 5
    public void MaximumPrice(){
    	// prompts user to enter in a new price, then writes over maxPrice value
        System.out.println("Which Maximum Price? (currently " + maxItemPrice + ")");
        double n = easy.readDouble();
        maxItemPrice = n;
        System.out.println("New Price Is: " + maxItemPrice + "\n");
    }
    
    // Method to sort library items in array - Option 6
    public void SortCollection(){
    	int numBook = 0;
    	MediaItem[] temp = new MediaItem[1];
    	// counts how many Movies and Books are in array
    	for(int i=0; i<arrayIndex; i++){
    		if(array[i].getClass() == Book.class){ numBook++; }
    	}
    	// sorts array into books first then movies via bubble method
    	for(int backwards=arrayIndex-1; backwards>0; backwards--){
    		for(int forwards=0; forwards<backwards; forwards++){
    			if(array[forwards].getClass() == Movie.class){
    				temp[0] = array[forwards];
    				array[forwards] = array[forwards+1];
    				array[forwards+1] = temp[0];
    			}
    		}
    	}
    	// sorts books by price via bubble method
    	for(int back=numBook-1; back>0; back--){
    		for(int front=0; front<back; front++){
    			if(array[front].GetPrice() > array[front+1].GetPrice()){
    				temp[0] = array[front];
    				array[front] = array[front+1];
    				array[front+1] = temp[0];
    			}
    		}	
    	}
    	// sorts movies by price via bubble method
    	for(int b=arrayIndex-1; b>numBook; b--){
    		for(int f=numBook; f<b ; f++){
    			if(array[f].GetPrice() > array[f+1].GetPrice()){
    				temp[0] = array[f];
    				array[f] = array[f+1];
    				array[f+1] = temp[0];
    			}
    		}
    	}
   		
    }

// end of class
}