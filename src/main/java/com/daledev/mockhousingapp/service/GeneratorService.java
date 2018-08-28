package com.daledev.mockhousingapp.service;

import java.util.Date;

/**
 * @author dale.ellis
 * @since 22/08/2018
 */
public interface GeneratorService {

    /**
     * @return
     */
    String generateTitle();

    /**
     * @return
     */
    String generateName();

    /**
     * Generates a random date between two years
     *
     * @param yearStart
     * @param yearEnd
     * @return
     */
    Date generateRandomDate(int yearStart, int yearEnd);
}
