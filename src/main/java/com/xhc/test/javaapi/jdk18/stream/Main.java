package com.xhc.test.javaapi.jdk18.stream;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by xuhuanchao on 2018/5/10.
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void testStream(){
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

    }


    public static void main(String[] args) {
        String path = Main.class.getClassLoader().getResource("./").getPath();
        System.out.println(path);
        PropertyConfigurator.configure(path+ "config/log4j.properties");
        testStream();
    }
}
