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
                        Transaction result = null; // maybe it needs to a TransactionResult object instead of Transaction object
                        try {
                            result = (Transaction) in.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        results.add(result);
                    }
                    terminalConfig.write(results, fileWriter);
                } catch (IOException e) {
                    e.printStackTrace();
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
                String address = terminalConfig.getServerIP();
                try {
                    Socket socket = new Socket(address, port);
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
                        Transaction result = null; // maybe it needs to a TransactionResult object instead of Transaction object
                        try {
                            result = (Transaction) in.readObject();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        results.add(result);
                    }
                    terminalConfig.write(results, fileWriter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        thread1.start();
    }
}
