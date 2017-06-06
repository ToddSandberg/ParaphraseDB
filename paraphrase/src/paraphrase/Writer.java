package paraphrase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

public class Writer {
    static HashMap<String, Paraphrase> store = new HashMap<String, Paraphrase>();

    public static void main(String[] args) {
        try {
            ObjectInputStream in;
            try {
                in = new ObjectInputStream(
                        new FileInputStream("paraphraseHash.txt"));
                store = (HashMap<String, Paraphrase>) in.readObject();
                in.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            Scanner scan = new Scanner(new File("shortenedList.txt"));
            while (scan.hasNextLine()) {
                String p = scan.nextLine();
                String[] split = p.split("");

                int i = 0;
                while (!split[i].equals("|")) {

                    i++;
                }
                i += 3;

                String term1unsplit = "";
                while (!split[i].equals("|")) {
                    term1unsplit += split[i];
                    i++;
                }
                System.out.print(term1unsplit);
                if (!store.containsKey(splitTerms(term1unsplit)[0])) {
                    i += 3;
                    String term2unsplit = "";
                    while (!split[i].equals("|")) {
                        term2unsplit += split[i];
                        i++;
                    }
                    System.out.print(term2unsplit);
                    i += 3;
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
                    store.put(splitTerms(term1unsplit)[0],
                            new Paraphrase(splitTerms(term2unsplit)[0],
                                    ppdbscore, splitTerms(term1unsplit)[1]));
                    
                }
                System.out.println("");
            }
            FileOutputStream fout = new FileOutputStream("paraphraseHash.txt");
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

    public static String[] splitTerms(String s) {
        String[] temp = new String[2];
        String term = "";
        String syntax = "";
        if (s.startsWith("[") && s.endsWith("]")) {
            syntax += s.substring(0, s.indexOf(']') + 1);
            s = s.substring(s.indexOf(']') + 1, s.length());
            term = s.substring(0, s.indexOf('['));
            s = s.substring(s.indexOf('['), s.length());
            syntax += s;
        }
        else if (s.startsWith("[")) {
            syntax += s.substring(0, s.indexOf(']') + 1);
            s = s.substring(s.indexOf(']') + 1, s.length());
            syntax += s.substring(0, s.indexOf(']') + 1);
            s = s.substring(s.indexOf(']') + 1, s.length());
            term = s;
        }
        else if (s.endsWith("]")) {
            term = s.substring(0, s.indexOf('['));
            s = s.substring(s.indexOf('['), s.length());
            syntax += s;
        }
        temp[0] = term;
        temp[1] = syntax;
        return temp;
    }
}
