package net.laserdiamond.ventureplugin.util;

import java.util.Random;

public class VentureMath {

    /**
     * Generates a random number between 0 and the size of the max (inclusive)
     * @param max The max size of the number
     * @return A random number as an Integer between 0 and the max
     */
    public static Integer getRandomInteger(Integer max)
    {
        Random random = new Random();
        return random.nextInt(max + 1);
    }

    /**
     * Gets the guaranteed chance percent as an integer.
     * Double values less than 0 will be set to 0 and return 0.
     * Double values less than 100 will also return 0. Every 100 adds +1 to the return value.
     * Typically used for stats that have a chance of an event happening.
     * @param statValue The value of the stat
     * @return An int representing the guaranteed chance of an event happening
     */
    public static int getGuaranteedFromChance(double statValue)
    {
        return (int) Math.floor(Math.max(statValue, 0) / 100);
    }

    /**
     * Gets the chance of an event happening from the last two digits of the double value.
     * Double values less than 0 will be set to 0 and return 0.
     * Double values less than 100, yet greater than 0, will return themselves.
     * Double values with 3 or more digits will return the last two digits.
     * Typically used for stats that have a chance of an event happening.
     * @param statValue The value of the stat
     * @return The last two digits of the double value passed through as an int
     */
    public static int getLastTwoDigitsChanceFromChance(double statValue)
    {
        return (int) (Math.max(0, statValue) % 100);
    }
}
