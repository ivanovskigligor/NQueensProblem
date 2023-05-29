import java.util.*;


public class SequentialPart {

	protected static List<int[][]> tempList = new ArrayList<int[][]>();
	protected static List<int[][]> solutionsList = new ArrayList<int[][]>();
	public static int n = 8;
	public static int solNum = 1;
	public static void main(String[] args) {

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

		long startTime = System.currentTimeMillis();
		heapsAlgorithm(mat,n,n);
		findSolutions();
		long endTime = System.currentTimeMillis();
		System.out.println("Total Solutions: " + solutionsList.size() + "\nTime elapsed: " + (endTime-startTime) + "ms");
	}

	//prints matrix
	public static void printMatrix(int m[][]) {

		System.out.println("Solution: "+solNum);
		for(int i = 0; i < m.length; i++) {
			for(int j = 0; j<m.length; j++) {
				System.out.print(m[i][j] + "  ");
			}
			System.out.println();
		}
		System.out.println();
		solNum++;
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

	public static void findSolutions() {

		boolean test = true;		
		for(int i = 0; i < tempList.size(); i++) {			
			for(int j = 0; j < n; j++) {				
				for(int k = 0; k < n; k++) {
					if(tempList.get(i)[j][k] == 1) {
						if(!isSafe(tempList.get(i),j,k))
							test = false;
					}
				}
			}
			if(test) {
				solutionsList.add(tempList.get(i));
				printMatrix(tempList.get(i));
			}
			test = true;
		}
	}

	public static void addPermutation(int m[][]) {
		int temp[][] = new int[n][n];
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				temp[i][j] = m[i][j];
			}
		}
		tempList.add(temp);
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
