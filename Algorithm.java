public class Algorithm {

    public static int[][] solveWithBacktracking(int[][] board) {
        if (solve(board)) {
            return board;
        } else {
            return null; // No solution found
        }
    }

    private static boolean solve(int[][] board) {

        GUI gui = new GUI();

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (!gui.isValid(board)) {
                    return false;
                }
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (gui.isValid(board)) {
                            board[row][col] = num;
                            if (solve(board)) {
                                return true;
                            }
                            board[row][col] = 0; // Reset on backtrack
                        }
                    }
                    return false; // Trigger backtracking
                }
            }
        }
        return true; // Solved
    }



    public static int[][] solveWithDancingLinks(int[][] board) {

        // Implement the dancing links algorithm here

        return board; // Placeholder return

    }

}