package paraphrase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Writes a HashMap from a shortened PPDB list
 * 
 * @author ToddSandberg
 *
 */
public class Writer {
    static HashMap<String, ArrayList<Paraphrase>> store = new HashMap<String, ArrayList<Paraphrase>>();

    public static void main(String[] args) {
        try {
            ObjectInputStream in;
            try {
                /* import paraphraseHash into store */
                in = new ObjectInputStream(
                        new FileInputStream("paraphraseHash.ser"));
                store = (HashMap<String, ArrayList<Paraphrase>>) in
                        .readObject();
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            /* Scan shortened list */
            Scanner scan = new Scanner(new File("shortenedList.txt"));
            long linenum = 0;
            while (scan.hasNextLine()) {
                String p = scan.nextLine();
                linenum++;
                System.out.print(linenum);
                
                if (!p.endsWith("P")) {
                    String[] split = p.split("");

                    int i = 0;
                    while (!split[i].equals("|")) {
                        
                        i++;
                    }
                    i += 4;

                    String term1unsplit = "";
                    while (!split[i].equals("|")) {
                        //System.out.print(split[i]);
                        term1unsplit += split[i];
                        i++;
                    }
                    System.out.print(splitTerms(term1unsplit)[0]);

                    i += 4;
                    String term2unsplit = "";
                    while (!split[i].equals("|") && i < split.length) {
                        term2unsplit += split[i];
                        i++;
                    }
                    System.out.print(splitTerms(term2unsplit)[0]);
                    i += 4;
                    while (!split[i].equals("=")) {
                        i++;
                    }
                    i++;
                    String ppdbscore = "";
                    while (i < split.length) {
                        ppdbscore += split[i];
                        i++;
                    }
                    System.out.print(ppdbscore);
                    if (!store.containsKey(splitTerms(term1unsplit)[0])) {
                        ArrayList<Paraphrase> temp = new ArrayList<Paraphrase>();
                        temp.add(new Paraphrase(splitTerms(term2unsplit)[0],
                                ppdbscore, splitTerms(term1unsplit)[1]));
                        store.put(splitTerms(term1unsplit)[0], temp);
                    }
                    else {
                        store.get(splitTerms(term1unsplit)[0])
                                .add(new Paraphrase(
                                        splitTerms(term2unsplit)[0], ppdbscore,
                                        splitTerms(term1unsplit)[1]));
                    }
                    System.out.println("");
                }
                
            }
            FileOutputStream fout = new FileOutputStream(
                    "paraphraseHash.ser");
            ObjectOutputStream out;
            try {
                out = new ObjectOutputStream(fout);
                out.writeObject(store);
                out.flush();
                out.close();
                fout.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("completed");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Splits term from syntax
     * 
     * @param s
     *            syntax
     * @return array containing [1]=term [2]=syntax
     */
    public static String[] splitTerms(String s) {
        
        String[] temp = new String[2];
        String term = "";
        String syntax = "";
        if ((s.startsWith("[")|| s.startsWith(" [")) && (s.endsWith("]") || s.endsWith("] "))) {
            syntax += s.substring(0, s.indexOf(']') + 1);
            s = s.substring(s.indexOf(']') + 1, s.length());
            term = s.substring(0, s.indexOf('[')-1);
            s = s.substring(s.indexOf('['), s.length());
            syntax += s;
        }
        else if (s.startsWith("[")|| s.startsWith(" [")) {
            syntax += s.substring(0, s.indexOf(']') + 1);
            s = s.substring(s.indexOf(']') + 1, s.length());
            syntax += s.substring(0, s.indexOf(']') + 1);
            s = s.substring(s.indexOf(']') + 1, s.length());
            term = s.substring(0,s.length()-1);
        }
        else if (s.endsWith("]") || s.endsWith("] ")) {
            term = s.substring(0, s.indexOf('[')-1);
            s = s.substring(s.indexOf('['), s.length());
            syntax += s;
        }
        if(term.startsWith(" ")){
            term = term.substring(1,term.length());
        }
        if(term.endsWith(" ")){
            term = term.substring(0,term.length()-1);
        }
        temp[0] = term;
        temp[1] = syntax;
        return temp;
    }
}
