package com.javaunit3.springmvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TitanicMovie implements Movie {
    @Override
    public String getTitle() {
        return "Titanic";
    }

    @Override
    public String getMaturity() {
        return "PG-13";
    }

    @Override
    public String getGenre() {
        return "Romance";
    }
}
