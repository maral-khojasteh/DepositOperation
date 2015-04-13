package client;

import model.Transaction;
import model.TransactionType;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.FileWriter;
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
        List<Element> transactionElements = rootNode.getChild("transactions").getChildren("transaction");
        for(Element e: serverSpec){
            serverIP = e.getAttribute("ip").getValue();
            port = Integer.parseInt(e.getAttribute("port").getValue());
        }
        for (Element e : transactionElements) {
            Transaction transaction = new Transaction();
            transaction.setId(e.getAttribute("id").getValue());
            if (e.getAttribute("type").getValue().equals(new String("deposit"))){
                transaction.setType(TransactionType.DEPOSIT);
            }
            else if (e.getAttribute("type").getValue().equals(new String("withdraw"))){
                transaction.setType(TransactionType.WITHDRAW);
            }
            transaction.setAmount(new BigDecimal(e.getAttribute("amount").getValue()));//TODO: test it with string value to handle error
            transaction.setDepositId(e.getAttribute("deposit").getValue());
            transactions.add(transaction);
        }
    }

    public void write(ArrayList<Transaction> results, FileWriter fileWriter){

        Element responses = new Element("responses");
        Document document = new Document(responses);
        for(Transaction result: results){
            Element response = new Element("response");
            response.setAttribute(new Attribute("id", result.getId()));
            //response.setAttribute(new Attribute("type", result.getType().toString()));
            response.setAttribute(new Attribute("amount", String.valueOf(result.getAmount())));
            response.setAttribute(new Attribute("deposit", result.getDepositId()));
            response.setAttribute(new Attribute("depositBalance", String.valueOf(result.getDepositBalance())));
            response.setAttribute(new Attribute("message", result.getResultMessage()));
            document.getRootElement().addContent(response);
        }
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.setFormat(Format.getPrettyFormat());
        try {
            xmlOutputter.output(document, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
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
