import java.io.*;
import java.util.*;

// mstw.java
// demonstrates minimum spanning tree with weighted graphs
// to run this program: C>java MSTWApp

////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////


class Edge{
    
    public int start;   // index of a vertex starting edge
    public int end;     // index of a vertex ending edge
    public int weight;  // weight from start to end
    // -------------------------------------------------------------
    public Edge(int sv, int dv, int d){  // constructor
	    start = sv;
	    end = dv;
	    weight = d;
    }
    public int getStart(){ // returns index of start vertex
    	return start;
    }
    public int getEnd(){  // returns index of end vertex
    	return end;
    }
    public int getWeight(){ // return weight value
    	return weight;
    }

}  // end class Edge


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////

class Heap{
    
    private Edge[] heapArray; 
    public int N; // current size of heapArray
    private int MaxSize;
    
    public Heap(int MaxSize){ // constructor
	    this.MaxSize = MaxSize; // MaxSize is the number of vertexes
	    N = 0; // initially there are no edges in the heap, so size is zero
	    heapArray = new Edge[MaxSize*MaxSize]; // array that stores MST
    }
     
    public void insert(Edge item){ // insert item in heap (assumed not full)
            heapArray[N] = item; // insert new edge as last item in tree
            trickleUp(N++); // reorder array now that a new 
    }
   
    public Edge remove(){ // method to remove root, item with smallest weight
        Edge root = heapArray[0];
        heapArray[0] = heapArray[--N]; // replace root with last item in tree
        heapArray[N] = null; // make sure the index of the item you swapped is null
        trickleDown(0); // reorder tree now that the root is different
        return root;
    }
   
    public int peekWeight(int i){ // checks the weight of an item in the heapArray
    	int weight=0; // set default weight as zero
    	if(heapArray[i]==null){weight=-1;} // if there is no edge in the array, return -1
    	else{weight = heapArray[i].getWeight();} // else return the actual weight
		return weight;
    } // end of peekWeight method
    
    private void trickleUp(int i){ // swaps weights of nodes all the way up the tree
        if (i==0) return; // base case is when you reach the top
        // check if parent is smaller
        int parent=(i-1)/2; // compute index of parent node
        if (peekWeight(i)<peekWeight(parent)){
            swap(i,parent); //swap
            trickleUp(parent); //recursion
        }
    } // end trickleUp method
    
    private void trickleDown(int i){ // swaps weights of nodes all the way down the tree
        int Left = 2*i+1; // compute index of left child
        int Right = 2*i+2; // compute index of right child
        // if it does not have any children, base case
        if(heapArray[Left]==null) return; // base case is when there are no more children
        // if it only has a right child
        else if(heapArray[Right]==null){
        	if(peekWeight(i)<peekWeight(Left)){
        		swap(i,Left); // swap
        		trickleDown(Left); //recursion
        	}
        	return;
        }
        // if it has two children
        else if(peekWeight(i)>peekWeight(Left) || peekWeight(i)>peekWeight(Right)){ // if either child needs to be swapped
            if(peekWeight(Left) < peekWeight(Right)){
                swap(i,Left); //swap
                trickleDown(Left); // recursion 
            }
            else{
                swap(i,Right); //swap
                trickleDown(Right); // recursion
            }
        }
    } // end of trickleDown method

    private void swap(int i,int j){ // swaps the position of two edges in the heapArray
    	Edge temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
     // (needed by trickle methods) 
    }
   
    // -------------------------------------------------------------
    public int size(){           // return number of items
        return N; 
    }
    // -------------------------------------------------------------
    public boolean isEmpty(){    // true if queue is empty
        return (N==0); 
    }
    // -------------------------------------------------------------
    
} // end class Heap


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////


class Vertex{
    
    public int i,j; // points coordinate (i,j)
    public boolean isInTree; // boolean keeps track of which vertexes have been used in MST
    // -------------------------------------------------------------
    public Vertex(int i,int j){  // constructor
	    this.i = i;
	    this.j=j;
	    isInTree = false;
    }
    // -------------------------------------------------------------
    public void display(){ // prints out vertex data in coordinate format
	    System.out.print("("+i+","+j+")");
    }

}  // end class Vertex


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////


class Graph{
    
    private Vertex vertexList[]; // list of vertices
    private int adjMat[][];      // adjacency matrix
    private int Nvertex;         // Number of vertices/nodes
    private int Nedges;          // Number of vertices/nodes
    
    // -------------------------------------------------------------   
    public Graph(int maxVertex){               // basic constructor
	vertexList = new Vertex[maxVertex];
	// adjacency matrix initialization
	adjMat = new int[maxVertex][maxVertex];
	Nvertex = 0;
	for(int j=0; j<maxVertex; j++)      // set adjacency
	    for(int k=0; k<maxVertex; k++)   // matrix to 0 by default
		adjMat[j][k] = 0;
    }  // end constructor

    // -------------------------------------------------------------
    public Graph(String name){               // constructor- load Graph
    	try {
            File file1 = new File(name+".v"); // v file contains nodes aka vertices
            Scanner scanner = new Scanner(file1);
            // read the first line of the file, which is the size of the library
            int vtx = scanner.nextInt(); // reads number of vertexes from file
            vertexList = new Vertex[vtx]; // creates a vertex array of the size of the first int in the file
            adjMat = new int[vtx][vtx]; // initializes the adjacency matrix
            scanner.nextLine(); // puts scanner on next line
            // this loop continues as long as the file has more lines and the array is not full
            while (scanner.hasNext()){ // inserts books as long as there are more lines in file and has inserted less than total amount of books
                int x = scanner.nextInt(); // read x coordinate of vertex
                int y = scanner.nextInt(); // read y coordinate of vertex
                addVertex(x,y); // adds vertex read by file to array
            } // end of while loop
        scanner.close();
        } // end of try
        catch (FileNotFoundException e) {
	         e.printStackTrace();
        } // end of catch
    	try {
            File file2 = new File(name+".e"); // v file contains edges aka
            Scanner scanner = new Scanner(file2);
            // read the first line of the file, which is the size of the library
            scanner.nextInt(); // scans number of edges
            scanner.nextLine(); // puts scanner on next line
            // this loop continues as long as the file has more lines and the array is not full
            while (scanner.hasNext()){ // inserts books as long as there are more lines in file and has inserted less than total amount of books
                int x = scanner.nextInt(); // read x coordinate of vertex
                int y = scanner.nextInt(); // read y coordinate of vertex
                int w = scanner.nextInt(); // reads weight of edge
                addEdge(x,y,w); // adds vertex read by file to array
            } // end of while loop
        scanner.close();
        } // end of try
        catch (FileNotFoundException e) {
	         e.printStackTrace();
        } // end of catch
    }// end constructor


    // -------------------------------------------------------------
    public Graph(int nx,int ny){        // constructor with random weight grid generator
        vertexList = new Vertex[nx*ny]; // creates an array that can hold every vertex
    	adjMat = new int[nx*ny][nx*ny]; // creates a matrix that can hold every edge
    	// populate vertexList
    	for(int y=0; y<ny; y++){ // counts every y level
    		for(int x=0; x<nx; x++){ // counts every x coordinate
    			addVertex(x,y); // adds a vertex to array for each coordinate
    		}
    	}
    	// populate adjMat with edges of random weight
    	int count=0; // start count at zero
    	for(int i=0; i<(nx*ny); i++){ // loop counts up every item in the vertex array
    		count++; // increases one every
    		if(count%nx!=0){ //
    			addEdge(i,i+1,(int)(1+Math.random()*(nx+ny))); // adds an edge to matrix horizontally
    		}
    		if(i<(nx*ny-nx)){
    			addEdge(i,i+nx,(int)(1+Math.random()*(nx+ny))); // adds an edge to matrix vertically
    		}
    	}
    }  // end constructor
              
    // -------------------------------------------------------------
    public void addVertex(int i, int j){ //add vertex method adds a vertex to the vertex array
	    vertexList[Nvertex++] = new Vertex(i,j);
    }
    
    // -------------------------------------------------------------
    public void addEdge(int start, int end, int weight){ // add edge method inputs edge info into matrix
	    adjMat[start][end] = weight;
	    adjMat[end][start] = weight;
	    Nedges++; // increase number of edges in matrix
    }

    public int[][] getAdjMat(){ // return the matrix of adjacent edges
	    return(adjMat);
    }

    public Vertex[] getVertexList(){ // return the array of vertexes
	    return(vertexList);
    }

    public int getNvertex(){ // return number of vertex/nodes
	    return(Nvertex);
    }

    public int getNedges(){ // return number of edges (connected to the number of non-zero elements in matrix)
	    return(Nedges);
    }
    
       
    // -------------------------------------------------------------
    public Graph mstw(){ // method returns the graph of the minimum spanning tree
    	Graph mst=new Graph(Nvertex);       // mst returns as a graph
    	//initialize the nodes (same numbering than graphs)
    	for (int i=0;i<Nvertex;i++){
    		mst.addVertex(vertexList[i].i,vertexList[i].j);
    	} // now verticies should be the same as original graph
        //initialize Heap
    	Heap theHeap = new Heap(Nedges); //large enough to contain all edges
    	int y=0; // start at row zero
    	int count=0; // 
    	vertexList[y].isInTree = true; // set the top node as being in the tree
	    while(count<Nvertex-1){ // keep going until you use every y row
	    	for(int x=0; x<Nvertex; x++){ // look at all x is this row
	    		if(adjMat[y][x]!=0 && vertexList[x].isInTree==false){ // if weight is not null and x has not been added yet
	    			Edge e = new Edge(y,x,adjMat[y][x]); // create a new node
	    			theHeap.insert(e); // insert new node into the heap
	    		}
	    	}
	    	Edge addEdge = theHeap.remove(); // remove top item in tree, use it to retrieve data
	    	if (vertexList[addEdge.getEnd()].isInTree == false) {
	    	mst.addEdge(addEdge.getStart(),addEdge.getEnd(),addEdge.getWeight()); // retrieve data, where end is y
	    	vertexList[addEdge.getEnd()].isInTree = true; // set y as true and as being in the tree
	    	count++; // increase the count
	    	y =  addEdge.getEnd(); // set the y as the ending vertex for the edge previously inserted
	    	}
    	}
    	return mst; // return mst graph
    }  // end of mstw method

    // -------------------------------------------------------------
    public void plot(){  //// Plot the Graph using the StdDraw.java library
    	int xmin = vertexList[0].i; int xmax = vertexList[vertexList.length-1].i;
    	int ymin = vertexList[0].j; int ymax = vertexList[vertexList.length-1].j;
    	StdDraw.setPenColor(StdDraw.WHITE); //
    	StdDraw.setCanvasSize(600,600); // size canvas	
	    StdDraw.setXscale(xmin-5, xmax+5);    //  x scale
	    StdDraw.setYscale(ymin-5, ymax+5);   //  y scale
	    StdDraw.setPenColor(StdDraw.BLUE);  // change pen color
	    for(int i=0; i<vertexList.length; i++){
	    	StdDraw.setPenRadius(0.03);
	    	StdDraw.point(vertexList[i].i, vertexList[i].j);
	    }
	    for(int i=0; i<vertexList.length; i++){
	    	StdDraw.setPenRadius(0.003);
	    	for(int y=0; y<vertexList.length; y++){
	    		for(int x=0; x<vertexList.length; x++){
	    			if(adjMat[x][y]!=0){
	    				StdDraw.line(vertexList[x].i, vertexList[x].j, vertexList[y].i, vertexList[y].j);
	    			}
	    		} // end x loop
	    	} // end y loop
	    }
    } // end of plot method
    
    public void plot2(){  //// Plot the Graph using the StdDraw.java library
    	int xmin = vertexList[0].i; int xmax = vertexList[vertexList.length-1].i;
    	int ymin = vertexList[0].j; int ymax = vertexList[vertexList.length-1].j;
    	StdDraw.setPenColor(StdDraw.WHITE); //
    	StdDraw.setCanvasSize(600,600); // size canvas	
	    StdDraw.setXscale(xmin-5, xmax+5);    //  x scale
	    StdDraw.setYscale(ymin-5, ymax+5);   //  y scale
	    StdDraw.setPenRadius(0.03);
	    StdDraw.setPenColor(StdDraw.BLACK);  // change pen color
	    for(int i=0; i<vertexList.length; i++){
	    	for(int y=0; y<vertexList.length; y++){
	    		for(int x=0; x<vertexList.length; x++){
	    			if(adjMat[x][y]!=0){
	    				StdDraw.line(vertexList[x].i, vertexList[x].j, vertexList[y].i, vertexList[y].j);
	    			}
	    		} // end x loop
	    	} // end y loop
	    }   
    } // end of plot2 method
    
}  // end class Graph


////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////
class MSTWApp{
    
    public static void ToolsMenu(){ // prints out option menu
	    System.out.println("Menu");
	    System.out.println("====");
	    System.out.println("1- Read Graph from File");
	    System.out.println("2- Generate a Graph using a Grid with Random weights");
	    System.out.println("3- Compute the Minimum Spanning Tree");
	    System.out.println("4- Plot the Maze");
	    System.out.println("5- Plot the Maze IN STYLE");
	    System.out.println("0-Exit");
	    System.out.println("");
	    System.out.print("Command: ");
    } // end of ToolsMenu method

    public static void main(String[] args){
	    Graph theGraph=null; // original graph
	    Graph mst = null;   // MST stored as graph
        EasyIn easy = new EasyIn();
        int N; // current amount of items in heap
	    int mat[][]; // initialize matrix
	    Vertex nodes[]; // initialize vertex array
	    // print out welcome statement
	    System.out.println("\nWelcome to Maze Generator App");
	    System.out.println("===============================\n");
	    int command = -1;
	    while (command != 0) {
	        if (command!=-1) easy.readString(); //just a pause
	        ToolsMenu();         // Print Option Menu
	        command = easy.readInt();
	        switch(command){
		    case  1: // Read Graph from File
		        System.out.println("Enter File name: ");
		        theGraph=new Graph(easy.readString()); // creates new graph
		        System.out.println("List of edges + weights: ");
		        N=theGraph.getNvertex();
		        nodes=new Vertex[N];
		        nodes=theGraph.getVertexList();
		        // Obtain Matrix
		        mat=new int[N][N];
		        mat=theGraph.getAdjMat();
		        // loops check over every cell in the adjMat matrix and print out non-empty cells
		        for (int i=0;i<N;i++){
			        for (int j=0;j<=i;j++) if(mat[i][j]!=0){
				        nodes[i].display();
				        System.out.print("<-->");
				        nodes[j].display();
				        System.out.println("  "+mat[i][j]);
			        }
			    }
		        break;
		    case  2: // Generate a Graph using a Grid with Random weights
                System.out.println("Enter Total Grid Size x: ");
		        int nx=easy.readInt();
                System.out.println("Enter Total Grid Size y: ");
		        int ny=easy.readInt(); 
		        theGraph=new Graph(nx,ny); // creates new graph
		        break;
		    case  3: // Compute the Minimum Spanning Tree
		        if (theGraph==null){ System.out.println("Graph not defined"); break;} // print error statement if conditions are not met
		        System.out.println("Minimum spanning tree: ");
		        N=theGraph.getNvertex();
		        mst=theGraph.mstw(); // create minimum spanning tree
		        System.out.println("Number of vertices: "+N);
		        System.out.println("Number of edges: "+mst.getNedges());
		        System.out.println("List of edges + weights: ");
		        N = theGraph.getNvertex();
		        nodes = new Vertex[N];
		        nodes = theGraph.getVertexList();
		        mat = new int[N][N];
		        mat = mst.getAdjMat();
		        int i =0;
		        int weightTotal = 0; // integer that will be used to print out the total weight of the MST
		        // loops check over every cell in the adjMat matrix and print out non-empty cells
		        for (i=0;i<N;i++){
			        for (int j=0;j<=i;j++) if(mat[i][j]!=0){
				        nodes[i].display();
				        System.out.print("<-->");
				        nodes[j].display();
				        System.out.println("  "+mat[i][j]);
				        weightTotal = weightTotal + mat[i][j]; // computes total weight
			        }
			    }
		        System.out.println("MST Total Weight: " + weightTotal); // returns added weight
		        break;
	        case 4: // Plot the maze
		        if (mst==null){ // print error statement if conditions are not met
		            System.out.println("MST not defined"); 
		            break;
		        }
		        mst.plot(); // prints plot
		        break;
		    case 5: // Plot the maze in a more appealing format
		    	if (mst==null){ // print error statement if conditions are not met 
		            System.out.println("MST not defined"); 
		            break;
		        }
		        mst.plot2(); // prints plot2
		        break;
		    case 0: // Terminate application 
		        break;
		    default: // default return statement for invalid input
		        System.out.println("Selection Invalid!- Try Again");
		    } // end of switch
	    } // end of while loop
	System.out.println("Goodbye!"); // print out goodbye statement
    } // end of main method
    
} // end class MSTWApp