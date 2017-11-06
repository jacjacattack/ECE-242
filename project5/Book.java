// Book class begins
public class Book {

	// create variables used in Book object
	public String ISBN; public String TITLE; public String AUTHOR; public String PUBLISHER; public String YEAR;
	public Book left; public Book right;
	
	// Book object takes in many parameters of book data and has the functionality of a linked list
	public Book(String ISBN, String TITLE, String AUTHOR, String PUBLISHER, String YEAR){
		this.ISBN=ISBN; this.TITLE=TITLE; this.AUTHOR=AUTHOR; this.PUBLISHER=PUBLISHER; this.YEAR=YEAR;
		left=null; right=null;
	} // end of Book object

///// Create Return Methods

	// return ISBN
	public String getISBN(){ return ISBN; }

	// return TITLE
	public String getTITLE(){ return TITLE; }

	// return AUTHOR
	public String getAUTHOR(){ return AUTHOR; }

	// return PUBLISHER
	public String getPUBLISHER(){ return PUBLISHER; }

	// return YEAR
	public String getYEAR(){ return YEAR; }

} // end of Book class


