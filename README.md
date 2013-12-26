ComparisonOfDifferentImplementationOfMST
========================================

/** Name: Renkai Ji
* CS6301, Project 6
* Section 014
*/


I. Purpose
----------

Compare the performance of the following minimum spanning tree algorithms:

1. Prim's algorithm (implementation 1 )
2. Prim's algorithm (implementation 2) - I using two different types of
heap(pairing heap and binary heap with array recording indices) for implementation 2
3. Kruskal's algorithm



II. File list
--------------
ComparisonStatistics.txt

Readme.txt

src/WeightedGraph2.java 

src/DisjSets.java           //Disjointed set data structure used for implementation of Kruskal's algorithm

src/PairingHeap.java            //Heap structure for implementation 2 of Prim's algorithm   

src/performanceComparison.java      
//Main funcion to handle input of test file, running times calculation of different implementations and output the result. 

    



III. Compiling and Executing on command line
---------------------------------------------
on command line,

To compile, run:
change your current working directory to Project6_Renkai_Ji_and_Zhuoyi_Wang\src:
javac -d . *.java


To execute, run:
change your current working directory to Project6_Renkai_Ji_and_Zhuoyi_Wang\src:
java performanceComparison

//To change the test file:
//First, you need to copy your target file into the directory Project6_Renkai_Ji_and_Zhuoyi_Wang\src. 
//Second, you need change the file name to your target file name in the main function of src/performanceComparison.java     



