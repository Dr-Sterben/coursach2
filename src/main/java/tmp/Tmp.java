package tmp;
import model.*;
import view.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Tmp {
    public static void main( String[] args ) {
        int mode = 3;
        if (mode == 0) {
            Scanner scanner = new Scanner(System.in);
            GameBoard gameBoard = new GameBoard();
            int firstNumber = 0;
            int secondNumber = 0;
            int curPl = 0;

            while (!(gameBoard.checkFull() || gameBoard.checkWin('x') || gameBoard.checkWin('o'))) {
                gameBoard.printBoard();
                firstNumber = scanner.nextInt();
                secondNumber = scanner.nextInt();
                if (curPl == 0) {
                    gameBoard.makeMove(firstNumber - 1, secondNumber - 1);
                    curPl = 1;
                }
                else {
                    gameBoard.makeMove(firstNumber - 1, secondNumber - 1);
                    curPl = 0;
                }
            }
            scanner.close();
            gameBoard.printBoard();
            if (gameBoard.checkWin('x')) {
                System.out.println('x' + " won!!!");
            }
            else if (gameBoard.checkWin('o')) {
                System.out.println('o' + " won!!!");
            }
            else {
                System.out.println("Draw!!!");
            }
        }
        else if (mode == 1) {
            GameBoard gameBoard = new GameBoard();
            ConsoleView consoleView = new ConsoleView(gameBoard);
            consoleView.consoleTest();
        }
        else if (mode == 2) {
            Map<GameBoard, String> gameBoardName = new HashMap<>();
            List<String> names = new ArrayList<>();
            // Добавьте значения в список names
            for (int i = 0; i < 5; i++) {
                GameBoard gameBoard = new GameBoard();
                gameBoardName.put(gameBoard, "Valueee " + i);
                //names.add("Value " + i);
            }

            for (GameBoard key : gameBoardName.keySet()) {
                String value = gameBoardName.get(key);
                names.add(value);
            }

            String result = String.join(", ", names);
            System.out.println(result);
        }
        else if (mode == 3) {
            GameBoard gameBoard = new GameBoard();
            ConsoleView consoleView = new ConsoleView(gameBoard);
            if (consoleView.rematchDecision()) {
                System.out.println("Rematch");
            }
            else 
                System.out.println("No rematch");
        }
    }
}
