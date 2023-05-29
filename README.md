# NQueensProblem

# Setup
Mpj package: https://sourceforge.net/projects/mpjexpress/ <br />
Setup tutorial: https://www.youtube.com/watch?v=SqJ01eE9tiI&ab_channel=MasurahMasue

# Implementation method

For the goal of implementing the task, we had to take into consideration the master/worker paradigm. For this goal it was imperative to implement a way to find all possible positions of the n queens, in the n*n chessboard. For this goal, Heap’s algorithm was used. Heap’s algorithm is used to generate all permutation of n objects. The idea is to generate each permutation from a previous permutation by interchanging elements without affecting the others. To achieve this the already existing algorithm was adapted to use on matrices. Each permutation is then checked to see if it fits all the desired limitations to constitute a solution, and in the event that it is, the matrix is printed and stored in an ArrayList. The sequential solution followed this algorithm, while the parallel and distributive solutions, took to divide the number of permutations available for testing depending on the systems available processors.

# Sequential method

The sequential method does not divide the work in any way, so in theory it should be the slowest from all of the solution methods. As previously stated, the solution begins by using Heap’s algorithm to generate all possible permutations for the n*n matrix. Using the generated permutations, each one is checked to see if it fits all limitations to constitute a solution. It does this by checking the upper and lower left diagonal of each queen, as well as the row spaces on the left. The reason behind this is that the testing goes through all queens from the left to the right, so it is only necessary to check the positions left of the queen. If the permutation is valid, it is printed and stored in an ArrayList to be used later in the graphical interface.

# Parallel method

This method divides the work between a certain number of Threads, depending on the number of available processors of the system the program is running on. This is done by generating all possible permutation for the n*n matrix, the same way as the sequential method. Later, this number is divided by the number of available processors, presenting each thread with the number of permutations they will have to check. For example, an 8*8 matrix will provide !8=40320 permutations. This number is then divided into the 8 Threads (in my case) such that each will have to deal with 5,040 permutations. The Threads are not executed in any particular way, so the printed solutions will be presented in a different order each execution of the program. This solution uses a Thread Pool, so after the Threads have finished their respective tasks, the Executor Service is shut down, such that no other tasks will be accepted.

# Distributive method

This method is similar to the parallel method, such as the work is again divided between available processors, again depending on the system the program is running on. The system follows the same algorithm for the solution such as, each processor except one handle an equal number of permutations, sending each one to be checked if it fits the description of a solution, printing it if it does. Each processor will handle a different number of solutions, and it will have to send the number of solutions to one select processor, that will count the number of solutions, such as to present it at the end of the program.
