// Create Book Class
class Book extends MediaItem {
    
	// Additional variable (all others are inherited from MediaItem)
	private String author;

    // Create Book Object, inherits MediaItem
    public Book(String t, String r, double p, String a){
        super(t,r,p); author = a;
    }
    
    // Overrides WhoAmI() method to add book specific data
    @ Override
    public String WhoAmI(){
        String result; 
        result = new String(" Title : " + title + " (Ref : " + reference + ", Price : " + price + ");\n" + " Author : " + author); 
        return result;
    }

    // Methods to change & retrieve author data
    public String GetAuthor(){ return(author); }
    public void SetAuthor(String newAuthor){ author = newAuthor; }

// end class
}
