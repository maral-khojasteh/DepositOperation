package server;

import model.Deposit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by Dotin school 5 on 4/11/2015.
 */
public class ServerConfig {

    private long port;
    private List<Deposit> deposits = new ArrayList<>();
    private String logFile;

    public void load(String input){
        try {
            FileReader reader = new FileReader(input);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            port = (long) jsonObject.get("port");
            logFile = (String) jsonObject.get("outLog");
            JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");
            Iterator<JSONObject> jsonObjectIterator = jsonArray.iterator();
            while (jsonObjectIterator.hasNext()){
                JSONObject depositJsonObject = jsonObjectIterator.next();
                Deposit deposit = new Deposit();
                deposit.setCustomerName((String) depositJsonObject.get("customer"));
                deposit.setId((String) depositJsonObject.get("id"));
                deposit.setInitialBalance(new BigDecimal(depositJsonObject.get("initialBalance").toString()));
                deposit.setUpperBound(new BigDecimal(depositJsonObject.get("upperBound").toString()));
                deposits.add(deposit);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void update(String input, String depositId, BigDecimal initialBalance){
        try {
            FileReader reader = new FileReader(input);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");
            Iterator<JSONObject> jsonObjectIterator = jsonArray.iterator();
            while (jsonObjectIterator.hasNext()){
                JSONObject depositJsonObject = jsonObjectIterator.next();
                if(depositJsonObject.get("id").equals(new String(depositId))){
                    depositJsonObject.put("initialBalance", initialBalance);
                }
            }
            FileWriter fileWriter = new FileWriter(input);
            System.out.println(jsonObject.toJSONString());
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public long getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public List<Deposit> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposit> deposits) {
        this.deposits = deposits;
    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
}
