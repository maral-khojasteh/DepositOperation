package server;

import model.CustomLogger;
import model.Deposit;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Server {

    //private static Logger logger = Logger.getLogger("Server");

    public static void main(String[] args) throws Exception{

        ServerConfig serverConfig = new ServerConfig();
        serverConfig.load("src/main/resources/core.json");
        List<Deposit> deposits = serverConfig.getDeposits();
        DepositManager.getInstance().loadInitialData(deposits);
        long port = serverConfig.getPort();
        ServerSocket serverSocket = new ServerSocket(((int) port));

//        String logFile = serverConfig.getLogFile();
//        FileHandler fileHandler = new FileHandler(logFile, true);
//        SimpleFormatter formatter = new SimpleFormatter();
//        fileHandler.setFormatter(formatter);
//        logger.addHandler(fileHandler);
//        logger.log(Level.INFO, "Listening to port {0}", port);
        CustomLogger customLogger = new CustomLogger();
        customLogger.log(Level.INFO, "Listening to port {0}", port+"");
        while (true){
            Socket socket = serverSocket.accept();
            customLogger.log(Level.INFO, "New client received...");
            //logger.log(Level.INFO, "New client received...");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }

}
