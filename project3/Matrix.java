import java.util.Vector;

interface Matrix { 
	// Assign the value x to element i,j 
	void set(int i,int j, double x); 
	// get the value of the element i,j 
	double get(int i, int j); 
	// Extract the diagonal of the matrix 
	double[] getDiagonal(); 
	// get the size of the matrix-- number of rows 
	int getSize(); 
	// get the number of non-zero elements 
	int getNnz(); 
	// Multiply a matrix by a vector 
	Vector multiply(Vector B); 
	// Print matrix using a specific format 
	void display();

}
