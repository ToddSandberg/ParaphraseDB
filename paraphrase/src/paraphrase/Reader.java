package paraphrase;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Read's the HashMap of PPDB and allows for an application to access Term1's
 * info
 * 
 * @author ToddSandberg
 * @version 6/7/2017
 */
public class Reader {
    /**
     * HashMap containing Term1 and the key and all of its info as its value
     */
    HashMap<String, ArrayList<Paraphrase>> store = new HashMap<String, ArrayList<Paraphrase>>();

    /**
     * Initializes the paraphrase HashMap
     */
    public Reader() {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("paraphraseHash.ser"));
            store = (HashMap<String, ArrayList<Paraphrase>>) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Looks up Term1 in the PPDB
     * 
     * @param s
     *            = Term1 , partofspeech
     * @return String containing Term1, Term2, PPDB2.0Score,
     *         Term1SyntacticalUsage
     */
    public String termToString(String s, String partofspeech) {
        s = s.toLowerCase();
        /* Check if PPDB contains Term1 */
        if (store.containsKey(s)) {
            ArrayList<Paraphrase> temp = store.get(s);
            String thing = "Term1=" + s + "(" + "\n";
            for (int x = 0; x < temp.size(); x++) {
                Paraphrase p = temp.get(x);

                if (p.Term1SyntacticUsage.contains(convertPOS(partofspeech))) {
                    thing += "-Term2=" + p.term2 + " PPDB2.0Score="
                            + p.ppdb2score + " Term1SyntacticalUsage="
                            + p.Term1SyntacticUsage + "\n";
                }
            }
            return thing + ")";
        }
        else {
            return s + "not included";
        }
    }

    /**
     * Calls termToString for each word in provided sentence
     * 
     * 
     * 
     * @param s sentence to paraphrased, example: I-noun~ like-verb~ it-noun~
     * @return paraphrased sentence
     */
    public String sentenceToString(String s) {
        String temp = "";
        String[] split = s.split("~");
        for (int i = 0; i < split.length; i++) {
            if (split[i].startsWith(" ")) {
                split[i] = split[i].substring(1, split[i].length());
            }
            temp += "{" + termToString(split[i].split("-")[0],
                    split[i].split("-")[1]) + "}";
        }
        return temp;
    }

    private String convertPOS(String s) {
        if (s.equals("noun") || s.equals("NN")||s.equals("pronoun")) {
            return "NP";
        }
        else if (s.equals("verb") || s.equals("VB")) {
            return "VP";
        }
        else if (s.equals("preposition") || s.equals("prep")) {
            return "PP";
        }
        else if(s.equals("adjecive")){
            return "JJ";
        }
        else {
            return "nope";
        }
    }
}
