public class Algorithm {

    public static int[][] solveWithBacktracking(int[][] board) {
        if (backtrackingALG(board)) {
            return board;
        } else {
            return null; // No solution found
        }
    }

    private static boolean backtrackingALG(int[][] board) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (board[row][col] == 0) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(board, row, col, num)) {
                            board[row][col] = num;
                            if (backtrackingALG(board)) {
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

    private static boolean isValid(int[][] board, int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }
        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }
        // Check 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[][] solveWithDancingLinks(int[][] board) {
        // Implement the dancing links algorithm here
        return board; // Placeholder return
    }
}