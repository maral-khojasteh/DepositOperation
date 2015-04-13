package model;

import server.ServerConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.*;

/**
 * Created by Dotin school 5 on 4/13/2015.
 */
public class CustomLogger {

    private static final LogManager logManager = LogManager.getLogManager();
    private static final Logger logger = Logger.getLogger("confLogger");
    static{
        try {
            logManager.readConfiguration(new FileInputStream("./logConfig.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error in loading configuration", e);

        }
    }
    public CustomLogger() throws IOException {
        ServerConfig serverConfig = new ServerConfig();
        String logFile = serverConfig.getLogFile();
        System.out.println(logFile);// TODO: why logFile is null?
        FileHandler fileHandler = new FileHandler("server.out", true);
        SimpleFormatter formatter = new SimpleFormatter();
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
    }

    public void log(Level level, String msg, Object obj){
        logger.log(level, msg, obj);
    }
    public void log(Level level, String msg){
        logger.log(level, msg);
    }

    public void log(Level level, String msg, Exception e){
        logger.log(level, msg, e);
    }

}
