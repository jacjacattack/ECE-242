// Creates MediaLibraryApplication Class
public class MediaLibraryApplication {

	// runs methods created in other classes
    public static void main(String[] args) {
        
    	// creates MediaCollection object and EasyIn object to read user input
        MediaCollection myStore;
        myStore = new MediaCollection("Amazing Media",20,100.0);
        EasyIn easy = new EasyIn();
        
        // method initializes data from bestmedia.txt file to be automatically in library
        myStore.InitializeCollection();
        
        // prints out welcome statement and prints out option menu
        System.out.println("Welcome to " + myStore.GetName());
        System.out.println("========================");
        System.out.println("");
        myStore.PrintMenu();
        
        // sets setting option to variable not used in options menu
        int setting = 7;
        
        // loop continues asking user for new commands until 0 is entered to terminate session
        while(setting != 0){
            System.out.println("Enter Command: ");
            setting = easy.readInt();
                if(setting == 1){ myStore.PrintCollection(); myStore.PrintMenu(); }
                if(setting == 2){ myStore.DetailedItem(); myStore.PrintMenu(); }
                if(setting == 3){ myStore.AddItem(); myStore.PrintMenu(); }
                if(setting == 4){ myStore.RemoveItem(); myStore.PrintMenu(); }
                if(setting == 5){ myStore.MaximumPrice(); myStore.PrintMenu(); }
                if(setting == 6){ myStore.SortCollection(); myStore.PrintMenu(); }
        }
        
        // prints out goodbye statement when user ends library session
        System.out.println("Goodbye! Please come again!"); 
        
    }

// end class    
}
