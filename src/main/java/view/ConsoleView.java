package view;
import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

public class ConsoleView {
    private GameBoard gameBoard;
    private Scanner scanner;

    public ConsoleView(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.scanner = new Scanner(System.in);
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void consoleTest() {
        System.out.println(">" + welcomeStart());
        System.out.println(">" + gameModeSelect());
        List<String> tmpList = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 25; i++) {
            tmpList.add(String.valueOf(random.nextInt()));
        }
        int bd = boardNumber(tmpList);
        System.out.println(">" + bd + "\n>" + tmpList.get(bd));
        scanner.close();
    }

    public String welcomeStart() {
        System.out.println("Welcome to the Tic Tac toe 10x10.");
        System.out.println("Enter your nickname: ");
        //Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        //scanner.close();
        return name;
    }

    public char gameModeSelect() {
        System.out.println("Choose the game mode: ");
        System.out.println("Create a board (c) / select an existing one (e):");
        //Scanner scanner = new Scanner(System.in);
        char gameMode = (scanner.nextLine()).charAt(0);
        
        //scanner.close();
        return gameMode;
    }

    public String enterBoardName() {
        System.out.println("Enter game board name: ");
        String name = scanner.nextLine();
        System.out.println("Creating and waiting for other player...");

        return name;
    }

    public int boardNumber(List<String> gameBoards) {
        if (gameBoards.isEmpty()) {
            System.out.println("No board exists :'(");
            return -1;
        }
        int number = 0;
        int i = 0;
        //Scanner scanner = new Scanner(System.in);
        for (i = 0; (i < 10) && (i < gameBoards.size()); i++) {
            System.out.println((i+1) + ". " + gameBoards.get(i));
        }
        if (i == 10)
            System.out.println("11. Next page->");
        int board = scanner.nextInt();
        //scanner.nextLine();
        if (1 <= board && board <= 10) {
            number+= board - 1;
            //scanner.close();
            return number;
        }
        else if (board == 11 && i == 10) {
            while (true) {
                number+= 10;
                for (i = 0; (i < 10) && (i + number < gameBoards.size()); i++) {
                    System.out.println((i+1) + ". " + gameBoards.get(i + number));
                }
                if (i == 10)
                    System.out.println("11. Next page->");
                board = scanner.nextInt();
                //scanner.nextLine();
                if (1 <= board && board <= 10) {
                    number+= board - 1;
                    //scanner.close();
                    return number;
                }
                else if (i != 10) {
                    //scanner.close();
                    return 0;
                }
            }
        }

        //scanner.close();
        return 0;
    }

    
    public String boardName(List<String> gameBoards) {
        if (gameBoards.isEmpty()) {
            System.out.println("No board exists :'(");
            return null;
        }
        int number = 0;
        int i = 0;
        for (i = 0; (i < 10) && (i < gameBoards.size()); i++) {
            System.out.println((i+1) + ". " + gameBoards.get(i));
        }
        if (i == 10)
            System.out.println("11. Next page->");
        int board = scanner.nextInt();
        if (1 <= board && board <= 10) {
            number+= board - 1;
            return gameBoards.get(number);
        }
        else if (board == 11 && i == 10) {
            while (true) {
                number+= 10;
                for (i = 0; (i < 10) && (i + number < gameBoards.size()); i++) {
                    System.out.println((i+1) + ". " + gameBoards.get(i + number));
                }
                if (i == 10)
                    System.out.println("11. Next page->");
                board = scanner.nextInt();
                //scanner.nextLine();
                if (1 <= board && board <= 10) {
                    number+= board - 1;
                    //scanner.close();
                    return gameBoards.get(number);
                }
                else if (i != 10) {
                    //scanner.close();
                    return gameBoards.get(0);
                }
            }
        }
        
        return gameBoards.get(0);
    }


    public int[] turnInput() {
        int[] xy = {0, 0};
        gameBoard.printBoard();
        System.out.print("Enter your turn (x y): ");
        xy[0] = scanner.nextInt() - 1;
        xy[1] = scanner.nextInt() - 1;
        while (!gameBoard.makeMove(xy[0], xy[1])) {
            System.out.print("Incorrect turn. Enter another (x y): ");
            xy[0] = scanner.nextInt() - 1;
            xy[1] = scanner.nextInt() - 1;
        }
        gameBoard.printBoard();
        System.out.println("Waiting for opponent...");
        return xy;
    }

    public void turnWait(int x,int y) {
        gameBoard.makeMove(x, y);
        //gameBoard.printBoard();
        //System.out.println("That was oponent's turn");
    }

    // public boolean rematchDecision() {
    //     boolean isRematch = false;
    //     System.out.println("That was a good game. Do u want rematch? (y/n) ");
    //     String tmpStr = scanner.nextLine();
    //     char isRematchChar = ' ';
    //     if (tmpStr.length() > 0)
    //         isRematchChar = scanner.nextLine().charAt(0);
    //     while (isRematchChar != 'y' && isRematchChar != 'n') {
    //         if (isRematchChar != ' ')
    //             System.out.println("Incorrect answer. Enter another (y/n): ");
    //         isRematchChar = scanner.nextLine().charAt(0);
    //     }
    //     return isRematch;
    // }

    public boolean rematchDecision() {
        System.out.println("That was a good game. Do u want rematch? (y/n):");
        String userInput = scanner.nextLine().toLowerCase();
        while (!(userInput.equalsIgnoreCase("y") || userInput.equalsIgnoreCase("n"))) {
            System.out.println("Incorrect answer. Enter another (y/n):");
            userInput = scanner.nextLine().toLowerCase();
        }

        if (userInput.equalsIgnoreCase("y")) {
            return true;
        }
        else 
            return false;
    }
}
