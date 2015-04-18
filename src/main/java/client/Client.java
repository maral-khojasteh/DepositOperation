package client;

import model.Transaction;
import org.jdom.JDOMException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * @author Maral Khojasteh
 */
public class Client {

    private static Logger logger = Logger.getLogger(Client.class.getName());
    static {
        try {
            LogManager.getLogManager().readConfiguration(Client.class.getResourceAsStream("/clientLogging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ClassNotFoundException, JDOMException, IOException {
        Thread thread = new Thread(){
            @Override
            public void run() {
                TerminalConfig terminalConfig = new TerminalConfig();
                try {
                    terminalConfig.load("src/main/resources/terminal.xml");
                } catch (JDOMException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int port = terminalConfig.getPort();
                String terminalId = terminalConfig.getTerminalId();
                String address = terminalConfig.getServerIP();
                try {
                    Socket socket = new Socket(address, port);
                    logger.log(Level.INFO, "Terminal {0} is connected on port {1}", new Object[]{terminalId, port});
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    List<Transaction> transactions = terminalConfig.getTransactions();
                    File file = new File("src/main/resources/response.xml");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    ArrayList<Transaction> results = new ArrayList<>();
                    for (Transaction transaction: transactions){
                        out.writeObject(transaction);
                        out.flush();
                        Transaction result = null; // maybe it needs to a ResultCode object instead of Transaction object
                        try {
                            result = (Transaction) in.readObject();
                        } catch (ClassNotFoundException e) {
                            logger.log(Level.SEVERE, "Error in reading result at terminal {0} with depositId {1}!", new Object[]{terminalId , transaction.getDepositId()});
                        }
                        results.add(result);
                    }
                    terminalConfig.write(results, fileWriter);
                    logger.log(Level.INFO, "Result is received at terminal {0}...", new Object[]{terminalId});
                } catch (IOException e) {
                    logger.log(Level.WARNING, "terminal {0}: File is not found to write result", new Object[]{terminalId});
                }
            }
        };
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                TerminalConfig terminalConfig = new TerminalConfig();
                try {
                    terminalConfig.load("src/main/resources/terminal1.xml");
                } catch (JDOMException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int port = terminalConfig.getPort();
                String terminalId = terminalConfig.getTerminalId();
                String address = terminalConfig.getServerIP();
                try {
                    Socket socket = new Socket(address, port);
                    logger.log(Level.INFO, "Terminal {0} is connected on port {1}", new Object[]{terminalId, String.valueOf(port)});
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                    List<Transaction> transactions = terminalConfig.getTransactions();
                    File file = new File("src/main/resources/response1.xml");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    ArrayList<Transaction> results = new ArrayList<>();
                    for (Transaction transaction: transactions){
                        out.writeObject(transaction);
                        out.flush();
                        Transaction result = null; // maybe it needs to a ResultCode object instead of Transaction object
                        try {
                            result = (Transaction) in.readObject();
                        } catch (ClassNotFoundException e) {
                            logger.log(Level.SEVERE, "Error in reading result at terminal {0} with depositId {1}!", new Object[]{terminalId , transaction.getDepositId()});
                        }
                        results.add(result);
                    }
                    terminalConfig.write(results, fileWriter);
                    logger.log(Level.INFO, "Result is received at terminal {0}...", new Object[]{terminalId});
                } catch (IOException e) {
                    logger.log(Level.WARNING, "terminal {0}: File is not found to write result", new Object[]{terminalId});
                }
            }
        };
        thread.start();
        thread1.start();
    }
}
