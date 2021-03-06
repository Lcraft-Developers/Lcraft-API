package de.lcraft.api.java_utils;

import java.io.File;
import java.util.*;

public class CodeHelper {

    public static final String[] makeArrayListToStringArray(ArrayList<String> arrayList) {
        String[] all = new String[arrayList.size()];
        for(int i = 0; i < arrayList.size(); i++) {
            all[i] = arrayList.get(i);
        }
        return all;
    }
    public static final ArrayList<String> makeStringArrayToArrayList(String... array) {
        ArrayList<String> all = new ArrayList<>();
        for(String c : array) {
            all.add(c);
        }
        return all;
    }
    public static final boolean containsFromStringArray(String[] array, String cont) {
        for(String c : array) {
            if(c.equalsIgnoreCase(cont)) {
                return true;
            }
            continue;
        }
        return false;
    }
    public static final int getRandom(int min, int max) {
        Random r = new Random();
        return r.nextInt(max + 1 - min) + min;
    }
    public static final List<File> getAllFilesFromADirectory(String path) {
        return Arrays.stream(new File(path).listFiles()).toList();
    }
    public static final int lenghtAllLowerCaseLetters(String word) {
        int amount = 0;
        for(String c : word.split("")) {
            if(Character.isLowerCase(c.charAt(0))) {
                amount++;
            }
            if(!Character.isAlphabetic(c.charAt(0))) {
                amount++;
            }
        }
        return amount;
    }
    public static final int lenghtAllUpperCaseLetters(String word) {
        int amount = 0;
        for(String c : word.split("")) {
            if(Character.isUpperCase(c.charAt(0))) {
                amount++;
            }
            if(!Character.isAlphabetic(c.charAt(0))) {
                amount++;
            }
        }
        return amount;
    }
    public static final int lenghtAllSpaces(String word) {
        int amount = 0;
        for(String c : word.split("")) {
            if(Character.isSpaceChar(c.charAt(0))) {
                amount++;
            }
        }
        return amount;
    }
    public static boolean isEnding(String str, String word) {
        return Objects.nonNull(replaceEnd(str, word, ""));
    }
    public static String replaceEnd(String str, String replaceWord, String newWord) {
        String w = "";
        boolean foundWord = false;
        for(int i = (str.length() - 1); i > 0; i--) {
            if(!w.equals(replaceWord)) {
                w = w + str.split("")[i];
            } else {
                foundWord = true;
                w = w + newWord;
            }
        }
        if(foundWord) {
            return w;
        }
        return null;
    }

}