package server;

import model.Deposit;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Server {

    public static void main(String[] args) throws Exception{

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.load("core.json");
        List<Deposit> deposits = serverConfig.getDeposits();
        DepositManager.getInstance().loadInitialData(deposits);

        int port = serverConfig.getPort();
        ServerSocket serverSocket = new ServerSocket(port);
        while (true){
            Socket socket = serverSocket.accept();
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }

}
