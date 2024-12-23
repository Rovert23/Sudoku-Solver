public class Algorithm {



    public static int[][] solveWithBacktracking(int[][] board) {

        GUI gui = new GUI();

        // Implement the backtracking algorithm here
        while (!gui.isSolved(board)) {
            for (int row = 0; row < 9; row++) {
                for (int col = 0; col < 9; col++) {
                    if (board[row][col] == 0) {
                        for (int guess = 1; guess < 10; guess++) {
                            board[row][col] = guess;
                            if (gui.isValid(board)) {
                                break;
                            }
                        }
                        if (gui.isValid(board)) {
                            continue;
                        }
                        else {
                            board[row][col] = 0;
                            if (col > 0) {
                                col--;
                            } else {
                                row--;
                                col = 8;
                            }
                        }
                    }
                }
            }
        }
        return board;

    }



    public static int[][] solveWithDancingLinks(int[][] board) {

        // Implement the dancing links algorithm here

        return board; // Placeholder return

    }

}