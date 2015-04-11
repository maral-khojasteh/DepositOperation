package client;

import model.Transaction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Maral ito
 * Date: 4/10/15
 * Time: 4:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class TerminalConfig {

    private String terminalId;
    private String terminalType;
    private String serverIP;
    private String logFile;
    private int port;
    private List<Transaction> transactions;

    public TerminalConfig() {

    }

    public void load(String input) {
        //TODO: load from XML
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(String terminalType) {
        this.terminalType = terminalType;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

}
