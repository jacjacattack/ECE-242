// Creates MediaItem Class
public class MediaItem{
    
	// Variables for construction of object
    public String title;
    public String reference;
    public double price;
    
    // Creates MediaItem object
    public MediaItem(String t, String r, double p){
        title = t; reference = r; price = p;
    }
    
    // Methods to retrieve title & reference & price data
    public String GetTitle(){ return(title); }
    public String GetRef(){ return(reference); }
    public double GetPrice(){ return(price); }
    
    // Methods to change title & reference & price data
    protected void SetTitle(String newTitle){ title = newTitle; }
    protected void SetRef(String newRef){ reference = newRef; }
    protected void SetPrice(double newPrice){ price = newPrice; }

    // Creates WhoAmI method which prints out a string of data about the MediaItem
    public String WhoAmI() { 
        String result; 
        result = new String(" Title : " + title + " (Ref : " + reference + ", Price : " + price + ");\n"); 
        return result; 
    }

// end class   
}