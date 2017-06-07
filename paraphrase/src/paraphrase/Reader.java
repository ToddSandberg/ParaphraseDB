package paraphrase;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
/**
 * Read's the HashMap of PPDB and allows for an application to access Term1's info
 * @author ToddSandberg
 * @version 6/7/2017
 */
public class Reader {
    /**
     * HashMap containing Term1 and the key and all of its info as its value
     */
    HashMap<String, Paraphrase> store = new HashMap<String, Paraphrase>();

    /**
     * Initializes the paraphrase HashMap
     */
    public Reader() {
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("paraphraseHash.ser"));
            store = (HashMap<String, Paraphrase>) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Looks up Term1 in the PPDB
     * 
     * @param Term1
     * @return String containing Term1, Term2, PPDB2.0Score,
     *         Term1SyntacticalUsage
     */
    public String termToString(String s) {
        s=s.toLowerCase();
        /* Deletes spaces in a phrase */
        if (s.contains(" ")) {
            String[] split = s.split(" ");
            s = "";
            for (int i = 0; i < split.length; i++) {
                s += split[i];
            }
        }
        /*Check if PPDB contains Term1*/
        if (store.containsKey(s)) {
            Paraphrase p = store.get(s);
            return "Term1=" + s + " Term2=" + p.term2 + " PPDB2.0Score="
                    + p.ppdb2score + " Term1SyntacticalUsage="
                    + p.Term1SyntacticUsage;
        }
        else {
            return "not included";
        }
    }
}
