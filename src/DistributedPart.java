import mpi.*;

public class DistributedPart{

	public static int n = 8;
	public static int dest;

	public static void main(String[] args){

		MPI.Init(args);
		
		int rank = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();

		//Initialise matrix
		int mat[][] = new int[n][n]; 
		for (int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				if(i==j)
					mat[i][j] = 1;
				else
					mat[i][j] = 0;
			}
		}
		
		if(rank == 0) {
			dest = 1;
			int temp[] = new int[1];
			int test = 0;
			int total = 0;

			long startTime = System.currentTimeMillis();

			heapsAlgorithm(mat,n,n);


			while(test < MPI.COMM_WORLD.Size()-1) {

				//receive number of solutions
				MPI.COMM_WORLD.Recv(temp,0,1,MPI.INT,MPI.ANY_SOURCE,2);
				total+=temp[0];
				test++;
			}
			long endTime = System.currentTimeMillis();
			System.out.println("\nTime elapsed: " + (endTime-startTime) + "ms \nTotal solutions:"+total);

		} else {

			int count = 0;
			int test2 = 0;

			dest=0;

			int multi[][]=new int[n][n];

			while(test2 < (possiblePermutation(n)/(size-1))) {

				// receive matrix
				MPI.COMM_WORLD.Recv(multi,0,multi.length,MPI.OBJECT,dest,1);
				boolean bol=true;

				//check if it is valid
				for(int k=0; k < multi.length; k++) {

					for(int j=0; j < multi.length;j++) {

						if(multi[k][j]==1) {

							if(!isSafe(multi,k,j)) {

								bol=false;

							}

						}

					}    
				}
				//if it is valid print
				if(bol) {
					count++;
					printMatrix(multi);
				}
				test2++;
			}
			// After all permutations from processor are checked, send number of solutions
			int temp2[]=new int[1];
			temp2[0] = count;
			MPI.COMM_WORLD.Send(temp2, 0, temp2.length, MPI.INT, 0, 2);
		}

		MPI.Finalize();
	}
	//prints matrix
	public static void printMatrix(int m[][]) {

		System.out.println("Solution: ");
		for(int i = 0; i < m.length; i++) {
			for(int j = 0; j<m.length; j++) {
				System.out.print(m[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
	}

	//checks if position is safe
	private static boolean isSafe(int[][] mat, int r, int c) {

		// checks upper diagonal to the left
		for (int i = r-1, j = c-1; i >= 0 && j >= 0; i--, j--) {
			if (mat[i][j] == 1) { 
				return false;
			}
		}
		// checks downwards diagonal on the left
		for (int i = r+1, j = c-1; i < n && j >= 0; i++, j--) {
			if (mat[i][j] == 1) {
				return false;
			}
		}
		return true;
	}

	public static int possiblePermutation(int x) {
		if(x==0)
			return 1;
		else 
			return x*possiblePermutation(x-1);
	}

	public static void addPermutation(int m[][]) {
		int temp[][] = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				temp[i][j] = m[i][j];
			}
		}
		if(dest > MPI.COMM_WORLD.Size()-1) {
			dest=1;
		}
		MPI.COMM_WORLD.Send(temp, 0, temp.length, MPI.OBJECT, dest, 1);
		dest++;
	}
	public static void heapsAlgorithm(int m[][], int size, int n) {

		if(size == 1)
			addPermutation(m);

		for(int i = 0; i < size; i++) {
			heapsAlgorithm(m, size - 1, n);

			// if size is odd, swap 0th i.e (first) and
			// (size-1)th i.e (last) element
			if(size % 2 == 1) {
				for (int j = 0; j < n; j++) {
					int temp = m[j][0];
					m[j][0] = m[j][size-1];
					m[j][size-1] = temp;
				}
			} else {
				for (int j = 0; j < n; j++) {
					int temp = m[j][i];
					m[j][i] = m[j][size - 1];
					m[j][size - 1] = temp;
				}
			}
		}
	}





}
