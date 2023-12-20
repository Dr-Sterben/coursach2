package network;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.List;

import model.GameBoard;
import view.*;


public class GameClient {
    private static String SERVER_IP = "127.0.0.1"; // IP адрес сервера
    private static int SERVER_PORT = 12345; // Порт сервера
    private static String PLAYER_NAME = "None";

     public static void main(String[] args) throws IOException {
        if (args.length == 2) {
            try {
                SERVER_IP = args[0];
                SERVER_PORT = Integer.parseInt(args[1]);
            } catch (Exception e) {
                System.out.println("Wrong cmd parameters");
            }
        }

        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in))) {
                //socket start
                GameBoard gameBoard = new GameBoard();
                ConsoleView consoleView = new ConsoleView(gameBoard);

                PLAYER_NAME = consoleView.welcomeStart();
                out.println(PLAYER_NAME);

                char gameMode = consoleView.gameModeSelect();
                out.println(gameMode);

                if (gameMode == 'c') {

                    out.println(consoleView.enterBoardName());

                    String inString = in.readLine();
                    System.out.println("Your opponent: " + inString);

                    boolean rematchFlag = true;

                    while (rematchFlag) {
                        gameBoard = new GameBoard();
                        consoleView = new ConsoleView(gameBoard);
                        playMatch(in, out, gameBoard, consoleView);

                        if (consoleView.rematchDecision()) {
                            System.out.println("Waiting for opponent decision...");
                            out.println("r");
                            String isRematch = " ";

                            while (isRematch != "no" && isRematch != "yes") {
                                try {
                                    Thread.sleep(3000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                isRematch = in.readLine();
                            }
                            if (isRematch == "yes") {
                                System.out.println("Starting rematch...");
                            }
                            else
                                rematchFlag = false;
                        }
                        else
                            rematchFlag = false;
                    }

                    return;

                    // char yourChar = in.readLine().charAt(0);
                    // System.out.println("Your sign: " + yourChar);
                    // char opponentChar = ' ';
                    // if (yourChar == 'x') opponentChar ='o';
                    // else opponentChar = 'x';
                    // int x = 0, y = 0;
                    // int oponentX = 0, oponentY = 0;
                    // if (yourChar == 'x') {
                    //     int[] tmp = consoleView.turnInput();
                    //     x = tmp[0];
                    //     y = tmp[1];
                    //     out.println(x + " " + y);
                    // }
                    // else {
                    //     System.out.println("Waiting for opponent...");
                    // }
                    // while (!gameBoard.checkFull() && !gameBoard.checkWin('x') && !gameBoard.checkWin('o')) {
                    //     String[] tmpIn = (in.readLine()).split(" ");
                    //     if (tmpIn.length != 2)
                    //         continue;
                    //     oponentX = Integer.parseInt(tmpIn[0]);
                    //     oponentY = Integer.parseInt(tmpIn[1]);
                    //     consoleView.turnWait(oponentX, oponentY);
                    //     if (gameBoard.checkFull() || gameBoard.checkWin('x') || gameBoard.checkWin('o')) {
                    //         gameBoard.printBoard();
                    //         break;
                    //     }
                    //     int[] tmp = consoleView.turnInput();
                    //     x = tmp[0];
                    //     y = tmp[1];
                    //     out.println(x + " " + y);
                    // }
                    // out.println("-1");
                    // if (gameBoard.checkWin(yourChar)) {
                    //     System.out.println("You've won!!!");
                    // }
                    // else if (gameBoard.checkWin(opponentChar)) {
                    //     System.out.println("You've lost!!!");
                    // }
                    // else System.out.println("Draw!!!");                    

                }
                else {
                    String inString = in.readLine();
                    List<String> gameBoards = Arrays.asList(inString.split(", ")); //Server: Name1, Name2, Name3...

                    int boardNumber = consoleView.boardNumber(gameBoards);
                    out.println(boardNumber);

                    // String boardName = consoleView.boardName(gameBoards);
                    // out.println(boardName);
                    
                    System.out.println("Connecting...");
                    inString = in.readLine();
                    System.out.println("Your opponent: " + inString);

                    boolean rematchFlag = true;

                    while (rematchFlag) {
                        gameBoard = new GameBoard();
                        consoleView = new ConsoleView(gameBoard);
                        playMatch(in, out, gameBoard, consoleView);

                        if (consoleView.rematchDecision()) {
                            System.out.println("Waiting for opponent decision...");
                            out.println("r");
                            String isRematch = " ";

                            while (isRematch != "no" && isRematch != "yes") {
                                try {
                                    Thread.sleep(3000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                isRematch = in.readLine();
                            }
                            if (isRematch == "yes") {
                                System.out.println("Starting rematch...");
                            }
                            else
                                rematchFlag = false;
                        }
                        else
                            rematchFlag = false;
                    }

                    return;

                    // inString = in.readLine();
                    // System.out.println("Your sign: " + inString);
                    // char yourChar = inString.charAt(0);
                    // char opponentChar = ' ';
                    // if (yourChar == 'x') opponentChar ='o';
                    // else opponentChar = 'x';
                    // int x = 0, y = 0;
                    // int oponentX = 0, oponentY = 0;
                    // if (yourChar == 'x') {
                    //     int[] tmp = consoleView.turnInput();
                    //     x = tmp[0];
                    //     y = tmp[1];
                    //     out.println(x + " " + y);
                    // }
                    // else {
                    //     System.out.println("Waiting for opponent...");
                    // }
                    // while (!gameBoard.checkFull() && !gameBoard.checkWin('x') && !gameBoard.checkWin('o')) {
                    //     String[] tmpIn = (in.readLine()).split(" ");
                    //     if (tmpIn.length != 2)
                    //         continue;
                    //     oponentX = Integer.parseInt(tmpIn[0]);
                    //     oponentY = Integer.parseInt(tmpIn[1]);
                    //     consoleView.turnWait(oponentX, oponentY);
                    //     if (gameBoard.checkFull() || gameBoard.checkWin('x') || gameBoard.checkWin('o')) {
                    //         gameBoard.printBoard();
                    //         break;
                    //     }
                    //     int[] tmp = consoleView.turnInput();
                    //     x = tmp[0];
                    //     y = tmp[1];
                    //     out.println(x + " " + y);
                    // }
                    // out.println("-1");
                    // if (gameBoard.checkWin(yourChar)) {
                    //     System.out.println("You've won!!!");
                    // }
                    // else if (gameBoard.checkWin(opponentChar)) {
                    //     System.out.println("You've lost!!!");
                    // }
                    // else System.out.println("Draw!!!");
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void playMatch(BufferedReader in, PrintWriter out, GameBoard gameBoard, ConsoleView consoleView) throws IOException {
        String inString = in.readLine();
        System.out.println("Your sign: " + inString);
        char yourChar = inString.charAt(0);
        char opponentChar = ' ';
        if (yourChar == 'x') opponentChar ='o';
        else opponentChar = 'x';
        int x = 0, y = 0;
        int oponentX = 0, oponentY = 0;
        if (yourChar == 'x') {
            int[] tmp = consoleView.turnInput();
            x = tmp[0];
            y = tmp[1];
            out.println(x + " " + y);
        }
        else {
            System.out.println("Waiting for opponent to start...");
        }
        while (!gameBoard.checkFull() && !gameBoard.checkWin('x') && !gameBoard.checkWin('o')) {
            String[] tmpIn = (in.readLine()).split(" ");
            if (tmpIn.length != 2)
                continue;
            oponentX = Integer.parseInt(tmpIn[0]);
            oponentY = Integer.parseInt(tmpIn[1]);
            consoleView.turnWait(oponentX, oponentY);
            if (gameBoard.checkFull() || gameBoard.checkWin('x') || gameBoard.checkWin('o')) {
                gameBoard.printBoard();
                break;
            }
            int[] tmp = consoleView.turnInput();
            x = tmp[0];
            y = tmp[1];
            out.println(x + " " + y);
        }
        out.println("-1");

        if (gameBoard.checkWin(yourChar)) {
            System.out.println("You've won!!!");
        }
        else if (gameBoard.checkWin(opponentChar)) {
            System.out.println("You've lost!!!");
        }
        else System.out.println("Draw!!!");
    }
}
