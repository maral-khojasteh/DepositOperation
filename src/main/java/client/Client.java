package client;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class Client {
    public static void main(String[] args) {

        TerminalConfig terminalConfig = new TerminalConfig();
        terminalConfig.load("terminal.xml");
        int port = terminalConfig.getPort();
        String address = terminalConfig.getServerIP();
        try {
            Socket socket = new Socket(address, port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
