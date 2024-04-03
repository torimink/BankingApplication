package com.example.Bank;

import com.sun.tools.javac.Main;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    public static final Logger logger = LogManager.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        logger.info("Application started");

        Main.main(args);

        logger.info("Application finished");
    }
}

