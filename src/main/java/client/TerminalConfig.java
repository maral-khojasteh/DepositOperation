package client;

import model.Transaction;
import model.TransactionType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private List<Transaction> transactions = new ArrayList<>();

    public TerminalConfig() {

    }

    public void load(String input) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(input);
        Element rootNode = document.getRootElement();
        List<Element> serverSpec = rootNode.getChildren("server");
        List<Element> outLog = rootNode.getChildren("outLog");
        List<Element> transactionElements = rootNode.getChildren("transactions");
        for(Element e: serverSpec){
            serverIP = e.getAttribute("ip").getValue();
            port = Integer.parseInt(e.getAttribute("port").getValue());
        }
        for (Element e : transactionElements) {
            Transaction transaction = new Transaction();
            transaction.setId(e.getChild("transaction").getAttribute("id").getValue());
            if(e.getChild("transaction").getAttribute("type").getValue() == "deposit")
                transaction.setType(TransactionType.DEPOSIT);
            else if(e.getChild("transaction").getAttribute("type").getValue() == "withdraw")
                transaction.setType(TransactionType.WITHDRAW);
            transaction.setAmount(new BigDecimal(e.getChild("transaction").getAttribute("amount").getValue()));//TODO: test it with string value to handle error
            transaction.setDepositId(e.getChild("transaction").getAttribute("deposit").getValue());
            transactions.add(transaction);
        }
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
