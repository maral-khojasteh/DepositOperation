package client;

import model.Transaction;
import org.jdom.JDOMException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Client {
    public static void main(String[] args) throws ClassNotFoundException, JDOMException, IOException {

        TerminalConfig terminalConfig = new TerminalConfig();
        terminalConfig.load("C:\\m.khojasteh\\workspace\\DepositOperation\\src\\main\\resources\\terminal.xml");
        int port = terminalConfig.getPort();
        String address = terminalConfig.getServerIP();
        try {
            Socket socket = new Socket(address, port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            List<Transaction> transactions = terminalConfig.getTransactions();
            for(Transaction transaction: transactions){
                out.writeObject(transaction);
                out.flush();
                Transaction result = (Transaction) in.readObject();//maybe it needs to a TransactionResult object instead of Transaction object
                System.out.println("client result" + result.getDepositId());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
