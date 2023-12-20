package model;

public class GameBoard {
    private char[][] board;
    private char empty = '-';
    private char currentPlayer;

    public GameBoard() {
        // Инициализация игрового поля 10x10
        board = new char[10][10]; //board char = 'x' || 'o' || '_'
        // Заполнение поля пробелами (пустые клетки)
        boardInitializations(); //!!!!! remmember: player: (x, y) --> makeMove (x-1, y-1)
    }

    private void boardInitializations() {
        currentPlayer = 'x';
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = empty;
            }
        }
    }

    public void printBoard() {
        // Вывод игрового поля в консоль
        System.out.print("   ");
        for (int i = 1; i < 11; i++) {
            System.out.print(i + " ");
        }
        System.out.print("(x)\n");
        for (int i = 0; i < 10; i++) {
            if (i != 9)
                System.out.print((i + 1) + "  ");
            else
                System.out.print("10 ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("(y)");
    }

    public char[][] getBoard() {
        return this.board;
    }

    //tmp function
    public boolean makeMove(int x, int y, char symbol) {
        int row = y;
        int col = x;

        // Проверка на корректность хода
        if (row < 0 || row >= 10 || col < 0 || col >= 10 || board[row][col] != empty) {
            return false; // Ход недопустим
        }

        // Выполнение хода
        board[row][col] = symbol;
        return true;
    }

    public boolean makeMove(int x, int y) {
        int row = y;
        int col = x;

        // Проверка на корректность хода
        if (row < 0 || row >= 10 || col < 0 || col >= 10 || board[row][col] != empty) {
            return false; // Ход недопустим
        }

        // Выполнение хода
        board[row][col] = currentPlayer;
        if (currentPlayer == 'x')
            currentPlayer = 'o';
        else
            currentPlayer = 'x';
        return true;
    }
    
    //tmp function
    public boolean checkWin(Player player) {
        boolean win = false;
        for (int i = 0; i < 10; i++) {
            win = win || checkVerticalLine(player, i);
            win = win || checkHorizontalLine(player, i);
        }
        win = win || checkDiagonalLines(player);

        return win;
    }

    public boolean checkWin(char player) {
        if (player != 'x' && player != 'o') {
            return false;
        }
        else {
            boolean win = false;
            for (int i = 0; i < 10; i++) {
                win = win || checkVerticalLine(player, i);
                win = win || checkHorizontalLine(player, i);
            }
            win = win || checkDiagonalLines(player);

            return win;
        }
    }

    //tmp function
    private boolean checkHorizontalLine(Player player, int line) {
        // Проверка линии для определенного игрока
        int inARowMax = 0;
        int inARowCurr = 0;
        for (int i = 0; i < 10; i++) {
            if (board[line][i] == player.getSymbol()) {
                inARowCurr++;
            }
            else {
                inARowMax = Math.max(inARowCurr, inARowMax);
                inARowCurr = 0;
            }
        }
        if (inARowMax >= 5)
            return true;
        else
            return false;
    }

    private boolean checkHorizontalLine(char player, int line) {
        // Проверка линии для определенного игрока
        int inARowMax = 0;
        int inARowCurr = 0;
        for (int i = 0; i < 10; i++) {
            //if (board[line][i] == player) {
            if (board[line][i] == player) {
                inARowCurr++;
            }
            else {
                inARowMax = Math.max(inARowCurr, inARowMax);
                inARowCurr = 0;
            }
        }
        if (inARowMax >= 5)
            return true;
        else
            return false;
    }

    //tmp function
    private boolean checkVerticalLine(Player player, int line) {
        // Проверка линии для определенного игрока
        int inARowMax = 0;
        int inARowCurr = 0;
        for (int i = 0; i < 10; i++) {
            if (board[i][line] == player.getSymbol()) {
                inARowCurr++;
            }
            else {
                inARowMax = Math.max(inARowCurr, inARowMax);
                inARowCurr = 0;
            }
        }
        if (inARowMax >= 5)
            return true;
        else
            return false;
    }

    private boolean checkVerticalLine(char player, int line) {
        // Проверка линии для определенного игрока
        int inARowMax = 0;
        int inARowCurr = 0;
        for (int i = 0; i < 10; i++) {
            if (board[i][line] == player) {
                inARowCurr++;
            }
            else {
                inARowMax = Math.max(inARowCurr, inARowMax);
                inARowCurr = 0;
            }
        }
        if (inARowMax >= 5)
            return true;
        else
            return false;
    }

    // private boolean checkDiagonalLines(Player player) {
    //     char symbol = player.getSymbol();
    //     int count = 0;
    //     int countMax = 0;
    //     // Check diagonal from top-left to bottom-right
    //     for (int i = 0; i <= board.length - 5; i++) {
    //         for (int j = 0; j <= board[i].length - 5; j++) {
    //             count = 0;
    //             for (int k = 0; k < 5; k++) {
    //                 if (board[i + k][j + k] == symbol) {
    //                     count++;
    //                 }
    //                 else {
    //                     countMax = Math.max(countMax, count);
    //                     if (countMax == 5)
    //                         return true;
    //                     count = 0;
    //                 }
    //             }
    //         }
    //     }
    //     count = 0;
    //     countMax = 0;
    //     // Check diagonal from top-right to bottom-left
    //     for (int i = 0; i <= board.length - 5; i++) {
    //         for (int j = 4; j < board[i].length; j++) {
    //             count = 0;
    //             for (int k = 0; k < 5; k++) {
    //                 if (board[i + k][j - k] == symbol) {
    //                     count++;
    //                 }
    //                 else {
    //                     countMax = Math.max(countMax, count);
    //                     if (countMax == 5)
    //                         return true;
    //                     count = 0;
    //                 }
    //             }
    //         }
    //     }
    //     return false;
    // }

    //tmp function
    private boolean checkDiagonalLines(Player player) {
        // Проверка диагоналей слева сверху направо вниз
        for (int i = 0; i <= 10 - 5; i++) {
            for (int j = 0; j <= 10 - 5; j++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] != player.getSymbol()) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }

        // Проверка диагоналей слева снизу направо вверх
        for (int i = 4; i < 10; i++) {
            for (int j = 0; j <= 10 - 5; j++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i - k][j + k] != player.getSymbol()) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }

        // Если не найдено 5 подряд символов на диагоналях
        return false;
    }

    public boolean checkFull() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (board[i][j] == empty)
                    return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalLines(char player) {
        // Проверка диагоналей слева сверху направо вниз
        for (int i = 0; i <= 10 - 5; i++) {
            for (int j = 0; j <= 10 - 5; j++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i + k][j + k] != player) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }

        // Проверка диагоналей слева снизу направо вверх
        for (int i = 4; i < 10; i++) {
            for (int j = 0; j <= 10 - 5; j++) {
                boolean found = true;
                for (int k = 0; k < 5; k++) {
                    if (board[i - k][j + k] != player) {
                        found = false;
                        break;
                    }
                }
                if (found) {
                    return true;
                }
            }
        }

        // Если не найдено 5 подряд символов на диагоналях
        return false;
    }
}