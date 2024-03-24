package org.example.LogTransformation;

import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class LoggerToPrint implements Logger {
    @Override
    public void log(String message)
    {
        System.out.println( new Date() +": " + message );
    }
}

