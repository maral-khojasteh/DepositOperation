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
        serverConfig.load("C:\\m.khojasteh\\workspace\\DepositOperation\\src\\main\\resources\\core.json");
        List<Deposit> deposits = serverConfig.getDeposits();
        DepositManager.getInstance().loadInitialData(deposits);

        long port = serverConfig.getPort();
        ServerSocket serverSocket = new ServerSocket(((int) port));
        while (true){
            System.out.println("before");
            Socket socket = serverSocket.accept();
            System.out.println("after");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }

}
