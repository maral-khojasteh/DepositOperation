package server;

import model.Deposit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Maral Khojasteh
 */
public class ServerConfig {

    private static Logger logger = Logger.getLogger(ServerConfig.class.getName());
    private long port;
    //private List<Deposit> deposits = new ArrayList<>();
    private String logFile;

    public List<Deposit> load(String input){
        List<Deposit> deposits = new ArrayList<Deposit>();
        try {
            FileReader reader = new FileReader(input);
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            port = (long) jsonObject.get("port");
            //logFile = (String) jsonObject.get("outLog");
            JSONArray jsonArray = (JSONArray) jsonObject.get("deposits");
            Iterator<JSONObject> jsonObjectIterator = jsonArray.iterator();
            while (jsonObjectIterator.hasNext()){
                JSONObject depositJsonObject = jsonObjectIterator.next();
                Deposit deposit = new Deposit();
                deposit.setCustomerName((String) depositJsonObject.get("customer"));
                deposit.setId((String) depositJsonObject.get("id"));
                deposit.setBalance(new BigDecimal(depositJsonObject.get("initialBalance").toString()));
                deposit.setUpperBound(new BigDecimal(depositJsonObject.get("upperBound").toString()));
                deposits.add(deposit);
            }
            logger.log(Level.INFO, "Server configuration is loaded..");

        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Server config file is not found!", e);
        }catch (IOException e) {
            logger.log(Level.WARNING, "File reader left!", e);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error in parsing server config file...", e);
        }
        return deposits;
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
            //System.out.println(jsonObject.toJSONString());
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
            logger.log(Level.INFO, "Deposits were updated..");

        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Server config file is not found for update!", e);
        }catch (IOException e) {
            logger.log(Level.WARNING, "File reader left!", e);
        } catch (ParseException e) {
            logger.log(Level.SEVERE, "Error in parsing server config file...", e);
        }
    }

    public long getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

//    public List<Deposit> getDeposits() {
//        return deposits;
//    }
//
//    public void setDeposits(List<Deposit> deposits) {
//        this.deposits = deposits;
//    }

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }
}
