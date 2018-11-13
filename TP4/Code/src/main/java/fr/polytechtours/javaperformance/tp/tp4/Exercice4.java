package fr.polytechtours.javaperformance.tp.tp4;

import java.util.LinkedList;
import java.util.List;

/**
 * L'objectif de cet exercice est de condenser un nombre indéfini de taleaux de bytes dans un seul et unique tableau. Les tableaux seront concaténés dans l'ordre d'entrée.
 */
public class Exercice4 {

    public static byte[] exercice4(final byte[]... bytes) {
        List<Byte> list = new LinkedList<>();

        for(final byte[] byteArray : bytes) {
            for(final Byte currentByte : byteArray) {
                list.add(currentByte);
            }
        }

        final byte[] result = new byte[list.size()];

        for(Integer i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }
}
