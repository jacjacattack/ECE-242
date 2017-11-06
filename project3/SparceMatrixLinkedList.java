// import statement for Random, which is used in one the the constructors
// to create a linked list of random data
import java.util.Random;

// begin RowNode class, the class that contains the info for linked rows
class RowNode{ 
	public ColNode col; 
	public RowNode next; 
	public int rowindex;
	RowNode(int i){ 
		rowindex=i; 
		col=null;
		next=null;
	}
} // end of RowNode

// begin ColNode class, the class that contains the info for linked rows
class ColNode{ 
	public double entry; 
	public int colindex; 
	public ColNode next;
	ColNode(int j,double x){ 
		colindex=j; 
		entry=x; 
		next=null;
	}
} // end of ColNode

// begin new class SparceMatrixLinkedList
class SparseMatrixLinkedList implements Matrix { 
	
	// creating variables that will be used throughout different methods
	private RowNode top; 
	RowNode firstRow;
	private int size=0; // size matrix 
	private int nnz=0; // number of non-zero elements

	//////////////////////// Constructors //////////////////////
	
	// Basic constructor- no element in the list yet 
	SparseMatrixLinkedList(){
		top=null;
	} // end
	
	
	// Constructor with Random generator (using nnz random non-zero numbers between 0.01<= x < 1.01 on any random row,column coordinates) 
	SparseMatrixLinkedList(int sizeMatrix, int nonZero){
		this.size = sizeMatrix;
		this.nnz = nonZero;
		// create random number generator
		Random rnd = new Random();
		for(int x = 0; x<nnz; x++){
			this.set(rnd.nextInt(size), rnd.nextInt(size), rnd.nextDouble());
		}
		// complete by adding linked list stuff to it
	} // end
	

	// Constructor from any matrix storage using the interface to SparseMatrixLinkedList storage using dynamic fill-in)
	SparseMatrixLinkedList(Matrix A){
		// initialize zero and number of non-zero elements
		size = A.getSize();
		nnz = A.getNnz();
		// set top row node equal to the first row node
		top = firstRow;
		// create necessary variables for storage
		RowNode currentRow = null;
		ColNode currentCol = null;
		RowNode nextRow = null;
		ColNode nextCol = null;
		ColNode firstCol = null;
		// starts from first row, counting down to the last row
		for(int row = 0; row<size; row++){
			// clears the first column when you start a new row
			// starts from the first column, counting down to the last column
			for(int col = 0; col<size; col++){
				// checks to see if that variable is equal to zero
				if(A.get(row, col) != 0){ // proceeds if does not equal zero
					// if a row has not already been created, it establishes it
					if(top == null){
						currentRow = new RowNode(row); // makes top the row at this line, top now has a value
						top = currentRow; // sets the current row as equal to top row
						//currentRow.rowindex = row;
					}
					// if the current row has not been created, it establishes it
					if(currentRow == null){
						currentRow = new RowNode(row);
					}
					// if current row is not the same index as the current row in loop, create new row
					if(currentRow.rowindex != row){
						nextRow = new RowNode(row);
						currentRow.next = nextRow;
						//nextRow = currentRow;
						currentRow = nextRow;
					}
					// if there is already an existing column, it adds a column to the existing column
					if(firstCol != null){
						nextCol = new ColNode(col, A.get(row, col));
						currentCol.next = nextCol;
						nextCol = currentCol;
					}
					// if there is no column attached to the row, adds a column to the row
					if(firstCol == null){
						firstCol = new ColNode(col, A.get(row, col));
						currentRow.col = firstCol;
						currentCol = firstCol;
					}
					// if there is already a first column, adds another column using next
				} // end of if statement
			} // end of column loop
			// sets the first column to null at the end of a row
			firstCol = null;
		} // end of row loop
	} // end
	
	//////////////////////// Methods //////////////////////

	// get the size of the matrix 
	public int getSize(){return size;}

	// get the number of non-zero elements 
	public int getNnz(){return nnz;}

	// Assign the value x to element i,j- change size matrix and nnz dynamically 
		// i = row j = column x = value
		public void set(int i, int j, double x){ 	
			// starts the search at the top, which has been set previously in the constructor
			RowNode tempRow = top;
			ColNode tempCol = null;
			ColNode replacingCol = null;
			RowNode newRow = null;
			//ColNode savedCol = null;
			// checks if the row we are on is null
			if(tempRow !=null){
				// while loop that finds the correct row, skips rows until it finds the row we are looking for,
				// and that row must not have a next row that is null or else it would skip to a row that is null and has not been created
				while(i!=tempRow.rowindex && tempRow.next != null){
					tempRow = tempRow.next;
				}
				// if the row has not already been created, create it now as newRow
				// and link it to the previous row which is tempRow
				if(i>tempRow.rowindex){
					newRow = new RowNode(i);
					tempRow.next = newRow;
					tempRow = newRow;
					// because we added a row, size of square matrix increases
					size++;
				} 
				// we should be on the correct row
				// Begin establishing which column
				// make tempCol the first column on the row
				tempCol = tempRow.col;
				// replaces the value stored with input x
				// if we are on the first column or there is no column already established
				if(j==0 && tempCol != null){
					tempCol.entry = x;
				} 
				// if we are replacing a different column variable that already has data stored in it,
				if(j!=0 && tempCol != null){
					// then while loop counts up until we are at the correct column,
					while(j<tempCol.colindex-1){
						tempCol = tempCol.next;
					} // end of while loop
					// and then sets the value
					tempCol.entry = x;
				} // end of else statement
				// if there is no value in the column we want, we must create a new one
				if(tempCol == null){
					// create replacingCol with the value you want
					replacingCol = new ColNode(j,x);
					// set replacingCol as the first column, and set it as tempCol
					tempRow.col = replacingCol;
					tempCol = replacingCol;
					// because we added a nonzero element, value of nnz increases
					nnz++;
				}
			} // end of if statement
		} // end of set
		
	// get method returns the value of a specific 
	public double get(int i, int j){
		// sets curRow as equal to top, the starting point
		RowNode curRow = top;
		// creates other necessary variables
		ColNode curCol = null;
		double x = 0;
			// while loop skips rows until we are on the correct row index
			while(curRow != null && curRow.rowindex < i){
				curRow = curRow.next;
			}
			// once we are on the correct row, we set current column as the first column in the row
			if(curRow!=null && curRow.rowindex==i){
				curCol = curRow.col;
			}
			// while loop skips columns until we are on the correct column index
			while(curCol != null && curCol.colindex < j){
				curCol = curCol.next;
			}
			// once we are on the correct column, we set the return value x
			if(curCol!=null && curCol.colindex==j){
				x = curCol.entry;
			}
			// returns the value we found
			return x; 
		} // end of get method

	// display method prints out all the linked list information, provided for us in skeleton
	public void display(){ 
		RowNode current = top; //start probe at the beginning 
		System.out.println(); System.out.println("Display in linked-list format"); 
		System.out.println("i"); while (current!=null) { // until the end of the list 
		System.out.print(current.rowindex+" "); 
		ColNode jcurrent = current.col;
		while (jcurrent!=null) { // until the end of the list 
			System.out.format("==> (j=%d, a=%.4f)",jcurrent.colindex,jcurrent.entry); 
			jcurrent = jcurrent.next;
		} 
		System.out.println(); 
		current = current.next; // move to next Link } System.out.println(); }
		}
	} // end of display method
	
	// getDiagonal method get the elements of the diagonal 
	public double[] getDiagonal(){
		double[] diag=new double[size];
		for(int i = 0; i<size; i++){
			diag[i] = this.get(i, i);
		}
		return (diag); 
	} // end of getDiagonal method
	
	// multiply method multiplies a vector to the linked list matrix
	public Vector multiply(Vector B){
		// sets temp row as top
		RowNode tempRow = top;
		// creates other necessary variables
		ColNode tempCol = null;
		double colValue = 0.0;
		double sum = 0.0;
		// creates the vector that will be returned
		Vector result = new Vector(size);
		// checks if the size of the vector is equal to the size of matrix
		if(B.getSize() == size){
			// while loop continues until the row is null
			while(tempRow!=null){
				// sets temp column as the first column in the row we are searching though
				tempCol = tempRow.col;
				// while loop continues until the row is null
				while(tempCol!=null){
					// calculates sum for each row by summing up values in each column
					colValue = tempCol.entry;
					sum = sum + colValue*B.get(tempCol.colindex);
					tempCol = tempCol.next;
				} // end of inner while loop
				result.set(tempRow.rowindex, colValue);
				tempRow = tempRow.next;
			} // end of outer while loop	
		} // end of if statement
		return result;
	} // end of multiply method

} // end SparceMatrixLinkedList class