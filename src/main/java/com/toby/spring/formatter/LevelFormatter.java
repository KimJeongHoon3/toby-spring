package com.toby.spring.formatter;

import com.toby.spring.domain.Level;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class LevelFormatter implements Formatter<Level> {
    @Override
    public Level parse(String text, Locale locale) throws ParseException {
        return Level.valueOf(Integer.parseInt(text));
    }

    @Override
    public String print(Level object, Locale locale) {
        return object.intValue()+"";
    }
}
