import java.util.*;

public class WeightedGraph2<V> {
  // Priority adjacency lists
  private int numberOfVertices;
  private List<List<WeightedEdge>> weightedneighbors;
  /** Construct a WeightedGraph from edges and vertices in arrays */
 

  /** Construct a WeightedGraph from edges and vertices in List */
  public WeightedGraph2(int[][] edges, int numberOfVertices) {
	  this.numberOfVertices = numberOfVertices;
	  createAdjacencyLists(edges, numberOfVertices);
  }
  /** Create priority adjacency lists from edge arrays */
  private void createAdjacencyLists(int[][] edges, int numberOfVertices) {
	weightedneighbors = new ArrayList<>(); 
    for (int i = 0; i < numberOfVertices; i++) {
    	weightedneighbors.add(new ArrayList<WeightedEdge>()); // Create a queue
    }

    for (int i = 0; i < edges.length; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      int w = edges[i][2];
      
      // Insert an edge into the queue
      weightedneighbors.get(u).add(new WeightedEdge(u, v, w));
    }
  }

  /** Create priority adjacency lists from edge lists */
  private void createAdjacencyLists(List<WeightedEdge> edges,
      int numberOfVertices) {
	weightedneighbors = new ArrayList<>(); 
	for (int i = 0; i < numberOfVertices; i++) {
		 weightedneighbors.add(new ArrayList<WeightedEdge>()); // Create a queue
	}

    for (WeightedEdge edge: edges) {
    	weightedneighbors.get(edge.u).add(edge); // Insert an edge into the queue
    }
  }

  /** Display edges with weights */
  public void printWeightedEdges() {
    for (int i = 0; i < weightedneighbors.size(); i++) {
      System.out.print("Vertex " + i + ": ");
      for (WeightedEdge edge : weightedneighbors.get(i)) {
        System.out.print("(" + edge.u +
          ", " + edge.v + ", " + edge.weight + ") ");
      }
      System.out.println();
    }
  }

  /** Get a minimum spanning tree rooted at vertex 0 */
  public MST getMinimumSpanningTreeByPrim1() {
    return getMinimumSpanningTreeByPrim1(0);
  }

  /** Get a minimum spanning tree rooted at a specified vertex */
  public MST getMinimumSpanningTreeByPrim1(int startingIndex) {
	PriorityQueue<WeightedEdge> minHeap = new PriorityQueue<>();
    int numberOfVertices = this.numberOfVertices; // Number of vertices
    int[] parent = new int[numberOfVertices]; // Parent of a vertex
    // Initially set the parent of all vertices to -1
    for (int i = 0; i < parent.length; i++)
      parent[i] = -1;
    boolean[] inS = new boolean[numberOfVertices];
    for (int i = 0; i < parent.length; i++)
       inS[i] = false;
    inS[startingIndex] = true;
    List<Integer> T = new ArrayList<Integer>();
    // T initially contains the startingVertex;
    T.add(startingIndex);
    int totalWeight = 0; // Total weight of the tree thus far
    for(WeightedEdge e: weightedneighbors.get(startingIndex)) {
    	minHeap.add(e);
    }
    while(!minHeap.isEmpty()) {
    	WeightedEdge e = minHeap.remove();
    	if(inS[e.u] && inS[e.v]) 
    		continue;
    	else if(inS[e.v] && !inS[e.u]) {
    		T.add(e.u);
    		totalWeight += e.weight;
    		parent[e.u] = e.v;
    		inS[e.u] = true;
    		for(WeightedEdge we: weightedneighbors.get(e.u)) {
    	    	minHeap.add(we);
    	    }
    	}
    	else if (!inS[e.v] && inS[e.u]) {
    		T.add(e.v);
    		totalWeight += e.weight;
    		parent[e.v] = e.u;
    		inS[e.v] = true;
    		for(WeightedEdge we: weightedneighbors.get(e.v)) {
    	    	minHeap.add(we);
    	    }
    	}
    	else
    		System.err.println("Oops");
    }

    return new MST(startingIndex, parent, T, totalWeight);
  }
  public MST getMinimumSpanningTreeByPrim2() {
	  return getMinimumSpanningTreeByPrim2(0);
  }

  /** Get a minimum spanning tree rooted at a specified vertex */
  public MST getMinimumSpanningTreeByPrim2(int startingIndex) {
	  
	  PairingHeap<Vertex> minHeap = new  PairingHeap<>();
	  int numberOfVertices = this.numberOfVertices; // Number of vertices
	 
	  int totalWeight = 0; // Total weight of the tree thus far
	  List<Integer> T = new ArrayList<Integer>();
	  int[] parent = new int[numberOfVertices]; // Parent of a vertex
	    // Initially set the parent of all vertices to -1
	  for (int i = 0; i < parent.length; i++)
	      parent[i] = -1;
	  Vertex[] vts = new Vertex[numberOfVertices];
	  vts[0] = new Vertex(0);
	  vts[0].dist = 0;
	  minHeap.insert(vts[0]);
	  for(int i = 1; i< numberOfVertices; i++) {
		  vts[i] = new Vertex(i);
		  minHeap.insert(vts[i]);
	  }

	  while(!minHeap.isEmpty()) {
		  Vertex u = minHeap.deleteMin( );
		  if (u.known) 
			  continue;
		  totalWeight += u.dist;
		  T.add(u.index);
		  u.known = true;
		  for(WeightedEdge edge:  weightedneighbors.get(u.index)) {
			  
			  if(!vts[edge.v].known && edge.weight < vts[edge.v].dist) {
				  parent[vts[edge.v].index] = u.index;
				  vts[edge.v].dist = edge.weight;				  
				  minHeap.decreaseKey(minHeap.insert(vts[edge.v]), vts[edge.v]);
			  }
		  }
	  }
	  return new MST(startingIndex, parent, T, totalWeight);
  }
  public MST getMinimumSpanningTreeByPrim3() {
	  return getMinimumSpanningTreeByPrim3(0);
  }

 public MST getMinimumSpanningTreeByPrim3(int startingIndex) {
	  
	  BinaryHeap minHeap = new  BinaryHeap();
	  int numberOfVertices = this.numberOfVertices; // Number of vertices
	  int totalWeight = 0; // Total weight of the tree thus far
	  List<Integer> T = new ArrayList<Integer>();
	  int[] parent = new int[numberOfVertices]; // Parent of a vertex
	    // Initially set the parent of all vertices to -1
	  for (int i = 0; i < parent.length; i++)
	      parent[i] = -1;
	  Vertex[] vts = new Vertex[numberOfVertices];
	  vts[0] = new Vertex(0);
	  vts[0].dist = 0;
	  minHeap.insert(vts[0]);
	  for(int i = 1; i< numberOfVertices; i++) {
		  vts[i] = new Vertex(i);
		  minHeap.insert(vts[i]);
	  }

	  while(!minHeap.isEmpty()) {
		  Vertex u = minHeap.deleteMin( );
		  totalWeight += u.dist;
		  T.add(u.index);
		  u.known = true;
		  for(WeightedEdge edge:  weightedneighbors.get(u.index)) {
			  if(!vts[edge.v].known && edge.weight < vts[edge.v].dist) {
				  parent[vts[edge.v].index] = u.index;
				  vts[edge.v].dist = edge.weight;				  
				  minHeap.decreaseKey(vts[edge.v]);
			  }
		  }
	  }
	  return new MST(startingIndex, parent, T, totalWeight);
  }
  public int getMinimumSpanningTreeByKruskal() {
	  int totalWeight = 0;
	  int numberOfVertices = this.numberOfVertices; // Number of vertices
	  List<WeightedEdge> T = new ArrayList<>();
	  DisjSets ds = new DisjSets(numberOfVertices);
	  PriorityQueue<WeightedEdge> pq = new PriorityQueue<>();
	  for(List<WeightedEdge> list: weightedneighbors) 
		  for(WeightedEdge we: list)
			  pq.add(we);
	  while(T.size() != numberOfVertices-1) {
		  WeightedEdge e = pq.remove();
		  int ru = ds.find(e.u);
		  int rv = ds.find(e.v);
		  if(ru != rv) {
			  //Accept the edge
			  T.add(e);
			  totalWeight += e.weight;
			  ds.union(ru, rv);
		  }
		  
	  }
	  
	  
	  return totalWeight;
	
  }
  
  /** MST is an inner class in WeightedGraph */
  public class MST{
    private int totalWeight; // Total weight of all edges in the tree
    private int root; // The root of the tree
    private int[] parent; // Store the parent of each vertex
    private List<Integer> searchOrders; // Store the search order

    /** Construct a tree with root, parent, and searchOrder */
    public MST(int root, int[] parent, List<Integer> searchOrder, int totalWeight) {
    		this.root = root;
    		this.parent = parent;
    		this.searchOrders = searchOrder;
    		this.totalWeight = totalWeight;
        }

    /** Construct a tree with root and parent without a
     *  particular order */
    public MST(int root, int[] parent) {
      this.root = root;
      this.parent = parent;
    }
    public MST(int totalWeight) {
    	this.totalWeight = totalWeight;
      }

    /** Return the root of the tree */
    public int getRoot() {
      return root;
    }

    /** Return the parent of vertex v */
    public int getParent(int v) {
      return parent[v];
    }

    /** Return an array representing search order */
    public List<Integer> getSearchOrders() {
      return searchOrders;
    }

    /** Return number of vertices found */
    public int getNumberOfVerticesFound() {
      return searchOrders.size();
    }
       
    public int getTotalWeight() {
      return totalWeight;
    }
  }
  public List<List<WeightedEdge>> getWeightedEdges() {
    return weightedneighbors;
  }

  public void addEdge(int u, int v, int weight) {
    weightedneighbors.get(u).add(new WeightedEdge(u, v, weight));
    weightedneighbors.get(v).add(new WeightedEdge(v, u, weight));
  }
  class WeightedEdge
  			implements Comparable<WeightedEdge> {
	  public int weight; // The weight on edge (u, v)
	  public int u; // Starting vertex of the edge
	  public int v; // Ending vertex of the edge

	  /** Create a weighted edge on (u, v) */
	  public WeightedEdge(int u, int v, int weight) {
		  this.u = u;
	      this.v = v;
		  this.weight = weight;
	  }

	  /** Compare two edges on weights */
	  public int compareTo(WeightedEdge edge) {
		  if (weight > edge.weight) 
			  return 1;
		  else if (weight == edge.weight) {
			  return 0;
		  }
		  else {
			  return -1;
		  }
	  }
  }
  static class Vertex implements Comparable<Vertex> {

	  int dist = Integer.MAX_VALUE;
	  boolean known = false;
	  int index;
	  public Vertex(int ind) {
		  index = ind;
	  }
	  /** Compare two edges on weights */
	  
	  public int compareTo(Vertex u) {
		  if (dist > u.dist) 
			  return 1;
		  else if (dist == u.dist) {
			  return 0;
		  }
		  else {
			  return -1;
		  }
	  }
  }
  public class BinaryHeap
  {
      /**
       * Construct the binary heap.
       */
      public BinaryHeap( )
      {
          this( DEFAULT_CAPACITY );
      }

      /**
       * Construct the binary heap.
       * @param capacity the capacity of the binary heap.
       */
      public BinaryHeap( int capacity )
      {
          currentSize = 0;
          array = new Vertex[ capacity + 1 ];
          indexMap = new int[ capacity + 1 ];
          
      }
      
     
      /**
       * Insert into the priority queue, maintaining heap order.
       * Duplicates are allowed.
       * @param x the item to insert.
       */
      public void insert( Vertex x )
      {
          if( currentSize == array.length - 1 ) 
              enlargeArray( array.length * 2 + 1 );

              // Percolate up
          int hole = ++currentSize;
          for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 ) {
              array[ hole ] = array[ hole / 2 ];
              indexMap[array[hole].index] = hole;
          }
          array[ hole ] = x;
          indexMap[array[hole].index] = hole;
      }
      


      private void enlargeArray( int newSize )
      {
    	  	  Vertex [] old = array;
    	  	  int[] oldMap = indexMap;
    	  	  indexMap = new int[ newSize ];
              array = new Vertex[ newSize ];
              
              for( int i = 0; i < old.length; i++ ) {
                  array[ i ] = old[ i ];  
                  indexMap[i] = oldMap[i];
              }
      }
      
      /**
       * Find the smallest item in the priority queue.
       * @return the smallest item, or throw an UnderflowException if empty.
       */
      public Vertex findMin( )
      {
      	if( isEmpty( ) )
          	System.err.println("Find min failure");
          return array[ 1 ];
      }

      /**
       * Remove the smallest item from the priority queue.
       * @return the smallest item, or throw an UnderflowException if empty.
       */
      public Vertex deleteMin( )
      {
      	if( isEmpty( ) )
          	System.err.println("Find min failure");

      	  Vertex minItem = findMin( );
          array[ 1 ] = array[ currentSize-- ];
          percolateDown( 1 );

          return minItem;
      }

      /**
       * Establish heap order property from an arbitrary
       * arrangement of items. Runs in linear time.
       */
      private void buildHeap( )
      {
          for( int i = currentSize / 2; i > 0; i-- )
              percolateDown( i );
      }

      /**
       * Test if the priority queue is logically empty.
       * @return true if empty, false otherwise.
       */
      public boolean isEmpty( )
      {
          return currentSize == 0;
      }

      /**
       * Make the priority queue logically empty.
       */
      public void makeEmpty( )
      {
          currentSize = 0;
      }

      private static final int DEFAULT_CAPACITY = 10;

      private int currentSize;      // Number of elements in heap
      private Vertex [] array; 		// The heap array
      private int[] indexMap;

      /**
       * Internal method to percolate down in the heap.
       * @param hole the index at which the percolate begins.
       */
      private void percolateDown( int hole )
      {
          int child;
          Vertex tmp = array[ hole ];

          for( ; hole * 2 <= currentSize; hole = child )
          {
              child = hole * 2;
              if( child != currentSize &&
                      array[ child + 1 ].compareTo( array[ child ] ) < 0 )
                  child++;
              if( array[ child ].compareTo( tmp ) < 0 ) {
                  array[ hole ] = array[ child ];
                  indexMap[array[hole].index] = hole;
              }
              
              else
                  break;
          }
          array[ hole ] = tmp;
          indexMap[array[hole].index] = hole;
      }
      public void decreaseKey(Vertex x) {
      	int hole = indexMap[x.index];
      	for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 ) {
              array[ hole ] = array[ hole / 2 ];
              indexMap[array[hole].index] = hole;
          }
          array[ hole ] = x;
          indexMap[array[hole].index] = hole;
      	
      }
  }
}
