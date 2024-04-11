package net.laserdiamond.ventureplugin.util;

import java.util.Random;

public class Randomizer {

    public static Integer getRandomInteger(Integer max) {
        Random random = new Random();
        max += 1;
        return random.nextInt(max);
    }
}
