package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.GameBoard;
//do not need?

public class GameServer {
    private static int PORT = 12345;
    private static List<Socket> clients = new ArrayList<>();
    private static Map<Socket, PrintWriter> clientWriters = new HashMap<>();
    private static Map<Socket, BufferedReader> clientReaders = new HashMap<>();
    private static Map<Socket, String> clientNames = new HashMap<>();
    private static List<String[]> gameBoardsNameP1P2 = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length == 1) {
            try {
                PORT = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("Wrong cmd parameters");
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running. Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                // clientWriters.put(clientSocket, out);

                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // private static String allGameBoardNames() {
    //     List<String> names = new ArrayList<>();
    //     for (GameBoard key : gameBoardName.keySet()) {
    //         String value = gameBoardName.get(key);
    //         names.add(value);
    //     }
    //     String result = String.join(", ", names);
    //     return result;
    // }

    private static String allGameBoardNames() {
        List<String> names = new ArrayList<>();

        for (String[] gameBoardItem : gameBoardsNameP1P2) {
            if (gameBoardItem[1] == "null" && gameBoardItem[2] == "null") {
                gameBoardsNameP1P2.remove(gameBoardItem);
            }
            else if (gameBoardItem[1] == "null" || gameBoardItem[2] == "null") {
                names.add(gameBoardItem[0]);
            }
        }

        String result = String.join(", ", names);
        return result;
    }

    private static Socket findSocketByName(String name) {
        for (Socket key : clientNames.keySet()) {
            if (clientNames.get(key) == name)
                return key;
        }
        return null;
    }

    // private static void waitingForPlayer(String gameBoardNameInput, String player1) {
    //     System.out.println(gameBoardNameInput + " is waiting for other player");
    //     String[] tmpStr = gameBoardNameP1P2.get(gameBoardNameInput);
    //     while (tmpStr[0] == player1 && tmpStr[1] == "null") {
    //         try {
    //             Thread.sleep(3000);
    //         } catch (Exception e) {
    //             e.printStackTrace();
    //         }
    //     }
    //     return;
    // }

    private static void waitingForPlayer(int gameBoardIndex) {
        String[] tmpStr = gameBoardsNameP1P2.get(gameBoardIndex);
        System.out.println(tmpStr[0] + " is waiting for other player");
        while (tmpStr[1] == "null" || tmpStr[2] == "null") {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            tmpStr = gameBoardsNameP1P2.get(gameBoardIndex);
        }
        return;
    }

    private static boolean isRematch(Socket socket1, Socket socket2) throws IOException {
        PrintWriter out1 = clientWriters.get(socket1);
        BufferedReader in1 = clientReaders.get(socket1);
        PrintWriter out2 = clientWriters.get(socket2);
        BufferedReader in2 = clientReaders.get(socket2);
        String tmpStr = " ";
        while (tmpStr != "r" && tmpStr != "n") {
            tmpStr = in1.readLine();
        }
        if (tmpStr == "n") {
            out1.println("no");
            out2.println("no");
            System.out.println("No rematch!");
            return false;
        }

        while (tmpStr != "r" && tmpStr != "n") {
            tmpStr = in2.readLine();
        }
        if (tmpStr == "n") {
            out1.println("no");
            out2.println("no");
            System.out.println("No rematch!");
            return false;
        }

        System.out.println("Starting rematch!");
        out1.println("yes");
        out2.println("yes");

        return true;
    }


    private static void gameProcess(Socket socket1, Socket socket2) {
        PrintWriter out1 = clientWriters.get(socket1);
        BufferedReader in1 = clientReaders.get(socket1);
        PrintWriter out2 = clientWriters.get(socket2);
        BufferedReader in2 = clientReaders.get(socket2);

        String name1 = clientNames.get(socket1);
        String name2 = clientNames.get(socket2);

        DataTransferThread thread1;
        DataTransferThread thread2;

        int randomValue = (int) (Math.random() * 2);
        if (randomValue == 0) {
            thread1 = new DataTransferThread(in1, out2, name1, 'x');
            thread2 = new DataTransferThread(in2, out1, name2, 'o');
        }
        else {
            thread1 = new DataTransferThread(in1, out2, name1, 'o');
            thread2 = new DataTransferThread(in2, out1, name2, 'x');
        }

        thread1.start();
        thread2.start();
        while (!(thread1.isStop || thread2.isStop)) {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!thread1.isStop)
            thread1.interrupt();
        if (!thread2.isStop)
            thread2.interrupt();

            return;
    }

    private static class DataTransferThread extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private boolean isStop;

        public DataTransferThread(BufferedReader in, PrintWriter out,String name, char sign) {
            this.in = in;
            this.out = out;
            this.isStop = false;

            out.println(name);
            out.println(sign);
        }

        @Override
        public void run() {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.equals("-1")) {
                        this.isStop = true;
                        break;
                    }
                    if (line.split(" ").length == 2)
                        out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader in;
        private PrintWriter out;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
            try {
                this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                this.out = new PrintWriter(clientSocket.getOutputStream(), true);

                clientReaders.put(socket, in);
                clientWriters.put(socket, out);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {     //name -> game mode select ->     -> c
                                                                //-> e 
            try {
                String userName = in.readLine();
                clientNames.put(this.clientSocket, userName);
                System.out.println("User " + userName + " joined.");
                String gameMode = in.readLine();
                if (gameMode.charAt(0) == 'c') {
                    String boardName = in.readLine();
                    String[] boardP1P2 = {boardName, userName, "null"};

                    gameBoardsNameP1P2.add(boardP1P2);
                    int gameBoardIndex = gameBoardsNameP1P2.indexOf(boardP1P2);

                    waitingForPlayer(gameBoardIndex);

                    String[] gameBoardNameP1P2Current = gameBoardsNameP1P2.get(gameBoardIndex);
                    Socket opponentSocket = findSocketByName(gameBoardNameP1P2Current[2]);

                    if (opponentSocket == null) 
                        return;

                    gameProcess(this.clientSocket, opponentSocket);
                    System.out.println(gameBoardNameP1P2Current[0] + " ended!");

                    boolean isRematch = isRematch(this.clientSocket, opponentSocket);
                    while (isRematch) {
                        System.out.println("Rematch started...");
                        gameProcess(this.clientSocket, opponentSocket);
                        System.out.println(gameBoardNameP1P2Current[0] + " ended!");
                        isRematch = isRematch(this.clientSocket, opponentSocket);
                    }
                    System.out.println("Board deleted");
                    gameBoardsNameP1P2.remove(gameBoardIndex);
                }
                else if (gameMode.charAt(0) == 'e') {
                    out.println(allGameBoardNames());
                    
                    int gameBoardIndex = Integer.parseInt(in.readLine());
                    String[] gameBoardNameP1P2Current = gameBoardsNameP1P2.get(gameBoardIndex);
                    Socket opponentSocket;
                    if (gameBoardNameP1P2Current[1] == "null") {
                        gameBoardNameP1P2Current[1] = userName;
                        opponentSocket = findSocketByName(gameBoardNameP1P2Current[2]);
                    }
                    else {
                        gameBoardNameP1P2Current[2] = userName;
                        opponentSocket = findSocketByName(gameBoardNameP1P2Current[1]);
                    }
                    gameBoardsNameP1P2.set(gameBoardIndex, gameBoardNameP1P2Current);
                    if (opponentSocket == null) 
                        return;
                    gameProcess(this.clientSocket, opponentSocket);
                    System.out.println(gameBoardNameP1P2Current[0] + " ended!");

                    boolean isRematch = isRematch(this.clientSocket, opponentSocket);
                    while (isRematch) {
                        System.out.println("Rematch started...");
                        gameProcess(this.clientSocket, opponentSocket);
                        System.out.println(gameBoardNameP1P2Current[0] + " ended!");
                        isRematch = isRematch(this.clientSocket, opponentSocket);
                    }
                    System.out.println("Board deleted");
                    gameBoardsNameP1P2.remove(gameBoardIndex);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

