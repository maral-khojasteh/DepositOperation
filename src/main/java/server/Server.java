package server;

import model.Deposit;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Server {

    private static Logger logger = Logger.getLogger("Server");

    public static void main(String[] args) throws Exception{

        FileHandler fileHandler = new FileHandler("test.log");
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.load("src/main/resources/core.json");
        List<Deposit> deposits = serverConfig.getDeposits();
        DepositManager.getInstance().loadInitialData(deposits);


        long port = serverConfig.getPort();
        ServerSocket serverSocket = new ServerSocket(((int) port));

        logger.log(Level.INFO, "Listening to port {0}", port);
        while (true){
            Socket socket = serverSocket.accept();
            logger.log(Level.INFO, "New client received...");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }

}
