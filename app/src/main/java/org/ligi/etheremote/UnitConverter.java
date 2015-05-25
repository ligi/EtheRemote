package org.ligi.etheremote;

import java.math.BigInteger;

public class UnitConverter {

    static String[] units = new String[]{"wei", "Kwei", "Mwei", "Gwei", "szabo", "finney", "ether", "Kether", "Mether", "Gether", "Tether"};

    static BigInteger THOUSAND= new BigInteger("1000");

    public static String humanize(BigInteger from) {

        int currentStep=0;

        BigInteger current=from;
        BigInteger rest=BigInteger.ZERO;

        while (true) {
            if (current.toString().length()<4 || currentStep == units.length) {
                return current.toString()+"."+rest.toString()+units[currentStep];
            }

            currentStep++;
            final BigInteger[] bigIntegers = current.divideAndRemainder(THOUSAND);

            current=bigIntegers[0];
            rest=bigIntegers[1];
        }

    }
}
