package server;

import model.Deposit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Maral Khojasteh
 */
public class Server {

    private static Logger logger = Logger.getLogger(Server.class.getName());

    static {
        try {
            LogManager.getLogManager().readConfiguration(Server.class.getResourceAsStream("/serverLogging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{

        ServerConfig serverConfig = new ServerConfig();
        List<Deposit> deposits = serverConfig.load("src/main/resources/core.json");
        //List<Deposit> deposits = serverConfig.getDeposits();
        DepositManager.getInstance().loadInitialData(deposits);
        long port = serverConfig.getPort();
        ServerSocket serverSocket = new ServerSocket(((int) port));

        logger.log(Level.INFO, "Listening to port {0}", String.valueOf(port));

        while (true){
            Socket socket = serverSocket.accept();
            logger.log(Level.INFO, "New client received...");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }

}
