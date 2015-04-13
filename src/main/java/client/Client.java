package client;

import model.Transaction;
import org.jdom.JDOMException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Client {

    public static void main(String[] args) throws ClassNotFoundException, JDOMException, IOException {

        TerminalConfig terminalConfig = new TerminalConfig();
        terminalConfig.load("src/main/resources/terminal.xml");
        int port = terminalConfig.getPort();
        String address = terminalConfig.getServerIP();
        try {
            Socket socket = new Socket(address, port);
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
                Transaction result = (Transaction) in.readObject(); // maybe it needs to a TransactionResult object instead of Transaction object
                results.add(result);
            }
            terminalConfig.write(results, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
