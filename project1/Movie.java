// Create Movie Class
class Movie extends MediaItem {
   
	// Additional variables (all others are inherited from MediaItem)
	private String director;
    private String mainActor;
    
    // Create Movie object, inherits MediaItem 
    public Movie(String t, String r, double p, String d, String m){
        super(t,r,p); director = d; mainActor = m;
    }

    // Overrides WhoAmI() method to add book specific data
    @ Override
    public String WhoAmI(){
        String result; 
        result = new String(" Title : " + title + " (Ref : " + reference + ", Price : " + price + ");\n" + " Director : " + director + "; Main Actor: " + mainActor); 
        return result;
    }

    // Methods to retrieve director & actor data
    public String GetDirector(){ return(director); }
    public String GetActor(){ return(mainActor); }
    
    // Methods to change director & actor data
    public void SetDirector(String newDirector){ director = newDirector; }
    public void SetActor(String newActor){ mainActor = newActor; }

// end class    
}
