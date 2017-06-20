package paraphrase;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.dictionary.Dictionary;

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
    HashMap<String, String> store = new HashMap<String, String>();
    /**
     * dictionary to access wordnet
     */
    Dictionary dictionary;
    /**
     * The correct formatting for part of speech in paraphraseDB
     */
    String paraphrasepos = "";
    /**
     * The index word of wordnet
     */
    IndexWord wordnetpos;
    /**
     * Initializes the paraphrase HashMap
     */
    public Reader() {
        try {
            dictionary = Dictionary.getDefaultResourceInstance();
            System.out.println("loaded wordnet");
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("paraphraseHash.ser"));
            store = (HashMap<String, String>) in.readObject();
            in.close();
            System.out.println("loaded PPDB");
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
        String temp = "";
        ArrayList<String> words = new ArrayList<String>();
        /* Check if PPDB contains Term1 */
        if (store.containsKey(s)) {
            String[] tempor = store.get(s).split("\n");
            //String thing = "Term1=" + s + "(" + "\n";
            for (int x = 0; x < tempor.length; x++) {
                //System.out.println("unsplit:" +tempor[x]);
                String[] split = tempor[x].split("\\$");
                //System.out.println("split:" +split[0]+"and"+split[1]);
                convertPOS(partofspeech,s);
                if (split[1].contains(paraphrasepos) && !split[0].equals(" ") && !split[0].equals("") && !words.contains(split[0])) {
                    words.add(split[0]);
                }
            }
        }
        else {
        }
        boolean write = false;
        try {
            PointerTargetTree hyponyms = PointerUtils
                    .getHyponymTree(wordnetpos.getSenses().get(0));
            String hypos = hyponyms.toList().toString();
            for (int x = 0; x < hypos.length() - 6; x++) {
                if (hypos.substring(x, x + 6).equals("Words:")) {
                    x = x + 7;
                    write = true;
                }
                else if (hypos.charAt(x) == '(') {
                    write = false;
                    if(!words.contains(temp)){
                        words.add(temp);
                    }
                    temp = "";
                }
                if (write && hypos.charAt(x) != '\n' && hypos.charAt(x)!='-') {
                    temp += hypos.charAt(x);
                }
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        try {
            PointerTargetNodeList seealso = PointerUtils
                    .getAlsoSees(wordnetpos.getSenses().get(0));
            String seealsos = seealso.toString();
            for (int x = 0; x < seealsos.length() - 6; x++) {
                if (seealsos.substring(x, x + 6).equals("Words:")) {
                    x = x + 7;
                    write = true;
                }
                else if (seealsos.charAt(x) == '(') {
                    write = false;
                    if(!words.contains(temp)){
                        words.add(temp);
                    }
                    temp = "";
                }
                if (write && seealsos.charAt(x) != '\n' && seealsos.charAt(x)!='-') {
                    temp += seealsos.charAt(x);
                }
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        temp = "";
        for(int i=0;i<words.size();i++){
            temp += words.get(i)+"\n";
        }
        return temp;
    }

    /**
     * Calls termToString for each word in provided sentence
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
            temp += "{" + split[i].split("-")[0] +": "+termToString(split[i].split("-")[0],
                    split[i].split("-")[1]) + "}";
        }
        return temp;
    }
    /**
     * convert part of speech into the correct formats
     * @param s
     * @param s2 
     */
    private void convertPOS(String s, String s2) {
        s=s.toLowerCase();
        try{
        if (s.equals("noun") || s.equals("nn")||s.equals("pronoun")) {
            paraphrasepos = "NP";
            wordnetpos = dictionary.lookupIndexWord(POS.NOUN, s2);
        }
        else if (s.equals("verb") || s.equals("vb")) {
            paraphrasepos = "VP";
            wordnetpos = dictionary.lookupIndexWord(POS.VERB, s2);
        }
        else if (s.equals("preposition") || s.equals("prep")) {
            paraphrasepos = "PP";
        }
        else if(s.equals("adjective") || s.equals("adj")){
            paraphrasepos = "JJ";
            wordnetpos = dictionary.lookupIndexWord(POS.ADJECTIVE, s2);
        }
        else if(s.equals("adverb")||s.equals("adv")){
            wordnetpos = dictionary.lookupIndexWord(POS.ADVERB, s2);
            paraphrasepos = "nope";
        }
        else {
            paraphrasepos = "nope";
        }
        }
        catch(Exception e){
            
        }
    }
}
