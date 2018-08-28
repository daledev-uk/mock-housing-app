package com.daledev.mockhousingapp.service;

import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * @author dale.ellis
 * @since 19/12/2017
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {
    private static final String[] TITLE = new String[]{"Mr", "Mrs", "Miss", "Ms", "Dr", "Lord", "Sir"};

    private static final String[] BEGINNING = {"Kr", "Ca", "Ra", "Mrok", "Cru",
            "Ray", "Bre", "Zed", "Drak", "Mor", "Jag", "Mer", "Jar", "Mjol",
            "Zork", "Mad", "Cry", "Zur", "Creo", "Azak", "Azur", "Rei", "Cro",
            "Mar", "Luk"};
    private static final String[] MIDDLE = {"air", "ir", "mi", "sor", "mee", "clo",
            "red", "cra", "ark", "arc", "miri", "lori", "cres", "mur", "zer",
            "marac", "zoir", "slamar", "salmar", "urak"};
    private static final String[] END = {"d", "ed", "ark", "arc", "es", "er", "der",
            "tron", "med", "ure", "zur", "cred", "mur"};

    private Random rand = new Random();


    @Override
    public String generateTitle() {
        return TITLE[rand.nextInt(TITLE.length)];
    }

    @Override
    public String generateName() {
        return BEGINNING[rand.nextInt(BEGINNING.length)] +
                MIDDLE[rand.nextInt(MIDDLE.length)] +
                END[rand.nextInt(END.length)];
    }


    @Override
    public Date generateRandomDate(int yearStart, int yearEnd){
        Calendar cal = Calendar.getInstance();

        Random r = new Random();

        int range = yearEnd- yearStart +1;
        cal.set(Calendar.YEAR, yearStart + r.nextInt(range));

        // 0 - 11 months, but nextInt() upper bound is exclusive
        cal.set(Calendar.MONTH, r.nextInt(12));
        cal.set(Calendar.DAY_OF_MONTH, r.nextInt(26));

        return cal.getTime();
    }
}
