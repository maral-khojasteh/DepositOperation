package server;

import model.Deposit;
import model.Transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Maral Khojasteh
 */
public class ClientHandler extends Thread{

    private static Logger logger = Logger.getLogger(ServerConfig.class.getName());
    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.log(Level.INFO, "Ready to handle the client...");
        try {
            while (true){
                Transaction transaction = (Transaction) in.readObject();
                logger.log(Level.INFO, "Transaction received from client: {0}, {1}", new Object[] {transaction.getDepositId(), transaction.getType()});
                Transaction result = DepositManager.getInstance().process(transaction);
                logger.log(Level.INFO, "Transaction processed");
                out.writeObject(result);
                out.flush();
            }
        } catch (IOException e) {
            logger.log(Level.WARNING, "Client left!");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Exception occurred", e);
        }

    }

}
