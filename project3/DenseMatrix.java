// import statement for Random, which is used in a constructor
import java.util.Random;

// begin DenseMatrix class
class DenseMatrix implements Matrix {
	
	// create variables used in constructors and methods
	private int size=0; // size of the matrix- number of rows/columns 
	private int nnz=0; // number of non-zero elements 
	private double[][] data; // row, column
	private double[] diag;
	
	// Constructor used to initialize the matrix (all elements to zero) 
	DenseMatrix(int size){ 
		this.size=size;
		// initializes matrix
		data=new double[size][size]; // all elements take the values 0.0d 
	} // end DenseMatrix
	
	// Constructor with Random generator (using nnz random non-zero numbers between 0.01<= x < 1.01 
	// on any random row,column coordinates) 
	DenseMatrix(int size,int nnz){
		this.size = size;
		this.nnz = nnz;
		// initializes matrix 
		data=new double[size][size];
		Random rnd = new Random();
		for(int x = 0; x <= nnz; x++){ 
				this.set(rnd.nextInt(size), rnd.nextInt(size), rnd.nextDouble());
		} 
	} // end DenseMatrix
	
	// Constructor from any other matrix storage using the interface to Dense storage 
	DenseMatrix(Matrix A){ 
		this.size=A.getSize(); 
		this.nnz=A.getNnz();
		// initializes matrix
		data=new double[size][size];
		for (int i = 0; i < size; i++) 
			for (int j=0;j<size;j++) data[i][j]=A.get(i,j); 
	} // end DenseMatrix
	
	// get the size of the matrix 
	public int getSize(){return size;}
	
	// get the number of non-zero elements 
	public int getNnz(){return nnz;}
	
	// Assign the value x to element i,j- change size matrix and nnz dynamically
	public void set(int i,int j, double x){ 
		if ((data[i][j]==0)&&(x!=0.0)) nnz++; 
		if ((data[i][j]!=0)&&(x==0.0)) nnz--; 
		data[i][j]=x; 
	} // end set method
	
	// get the value of the element i,j 
	public double get(int i, int j){return(data[i][j]);}
	
	// Print matrix using a specific format, provided for us in skeleton
	public void display(){ 
		System.out.println(); 
		System.out.println("Display in dense format"); 
		for (int i = 0; i < size; i++){ 
			for (int j=0;j<size;j++) 
				System.out.format("%.4f ",get(i,j)); 
			System.out.println(); 
		} 
		System.out.println(); 
	} // end of display method
	
	// get the elements of the diagonal 
	public double[] getDiagonal(){
		// creates an array to return as the diagonal values in matrix
		diag=new double[size];
		// loop finds the diagonal in each row and saves it to the diagonal array
		for(int i = 0; i<size; i++){
			diag[i] = data[i][i];
		} // end of for loop
		// returns the array holding all elements in the diagonal
		return (diag);
	} // end of getDiagonal method
	
	// Multiply a matrix by a vector (multiply using only the non-zero elements)
	public Vector multiply(Vector B){
		Vector result = new Vector(B.getSize());
	    double sum = 0;
	    // loop counts up rows
	    for(int row=0; row<size; row++){
	    	// loop counts up columns and adds all of row together
	    	for(int column=0; column<size; column++){
	    		sum += data[row][column]*B.get(column);
	    	} // ends column and adding loop
	    	// stores the sum for the row into the resulting vector
	    	result.set(row, sum);
	    	// clears sum to zero for the next row 
	    	sum = 0;
	    } // end of row loop
	// returns resulting vector
	return result;	
	} // end of multiply method

} // end DenseMatrix class