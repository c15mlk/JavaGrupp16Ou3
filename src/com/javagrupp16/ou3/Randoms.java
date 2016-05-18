package com.javagrupp16.ou3;

import java.util.List;
import java.util.Random;

/**
 * A utility class for Random methods.
 * Created by Mirrepirre on 2016-05-18.
 */
public class Randoms {

    private static Random random = new Random();


    /*Tar ett nummer mellan 0 till 10000
     *10000 kan man tänka sig som 100% och 1 som 0.01%
     *Gjorde det så eftersom testprogrammet behöver ha 0.01% chans per tidssteg
     *double procent som man stoppar in gångras med 100 så att man ska skriva 0.01% om man vill ha det.
     *Eftersom 0.01% = 1 i detta fall.*/
    public static boolean chanceOf(double procent){
        int rng = random.nextInt(10000 + 1);
        return (procent * 100) >= rng;
    }


    public static <T> T randomItem(List<T> list){
        int randomInt = random.nextInt(list.size());
        return list.get(randomInt);
    }

}
