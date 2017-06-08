package paraphrase;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
     * @param s = Term1 , partofspeech
     * @return String containing Term1, Term2, PPDB2.0Score,
     *         Term1SyntacticalUsage
     */
    public String termToString(String s) {
        s=s.toLowerCase();
        /*Check if PPDB contains Term1*/
        if (store.containsKey(s)) {
            ArrayList<Paraphrase> temp = store.get(s);
            String thing = "Term1=" + s + "(" + "\n";
            for(int x=0;x<temp.size();x++){
                Paraphrase p = temp.get(x);
                thing += "-Term2=" + p.term2 + " PPDB2.0Score="
                    + p.ppdb2score + " Term1SyntacticalUsage="
                    + p.Term1SyntacticUsage + "\n";
            }
            return thing + ")";
        }
        else {
            return "not included";
        }
    }
    /**
     * Calls termToString for each word in provided sentence
     * @param s sentence to paraphrased
     * @return paraphrased sentence
     */
    public String sentenceToString(String s){
        String temp="";
        String [] split = s.split(" ");
        for(int i=0;i<split.length;i++){
            temp += "{" + termToString(split[i]) +"}";
        }
        return temp;
    }
}
