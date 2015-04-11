package server;

import model.Transaction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class ClientHandler extends Thread{

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                Transaction transaction = (Transaction) in.readObject();
                DepositManager.getInstance().process(transaction);


            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }
}
