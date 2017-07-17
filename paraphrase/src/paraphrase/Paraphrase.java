package paraphrase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.extjwnl.data.IndexWord;
import net.sf.extjwnl.data.POS;
import net.sf.extjwnl.data.PointerUtils;
import net.sf.extjwnl.data.list.PointerTargetNodeList;
import net.sf.extjwnl.data.list.PointerTargetTree;
import net.sf.extjwnl.dictionary.Dictionary;

public class Paraphrase {
    static HashMap<String, String> ppdb = new HashMap<String, String>();
    /**
     * dictionary to access wordnet
     */
    static Dictionary dictionary;
    /**
     * The correct formatting for part of speech in paraphraseDB
     */
    static String paraphrasepos = "";
    /**
     * The index word of wordnet
     */
    static IndexWord wordnetpos;

    /**
     * construct a paraphrase access
     */
    public Paraphrase() {
        try {
            dictionary = Dictionary.getDefaultResourceInstance();
            Reader reader = new Reader();
            ppdb = reader.store;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Looks up Term1 in the PPDB
     * 
     * @param s
     *            = Term1 , partofspeech = the part of speech
     * @return String containing all term2's
     */
    public static List termToString(String s, String partofspeech) {
        s = s.toLowerCase();
        String temp = "";
        ArrayList<String> words = new ArrayList<String>();
        convertPOS(partofspeech, s);
        String lemma = wordnetpos.getLemma();
        /* Check if PPDB contains Term1 */
        try {
            if (ppdb.containsKey(lemma)) {
                String[] tempor = ppdb.get(lemma).split("\n");
                for (int x = 0; x < tempor.length; x++) {
                    // System.out.println(tempor[x]);
                    String[] split = tempor[x].split("\\$");
                    if (split[1].contains(paraphrasepos)
                            && !split[0].equals(" ") && !split[0].equals("")
                            && !words.contains(split[0])) {
                        words.add(split[0]);
                    }
                }
            }
            else {
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        boolean write = false;
        /* Get hypernyms from Wordnet */
        try {
            PointerTargetTree hypernyms = PointerUtils
                    .getHypernymTree(wordnetpos.getSenses().get(0));
            String hypos = hypernyms.toList().toString();
            for (int x = 0; x < hypos.length() - 6; x++) {
                if (hypos.substring(x, x + 6).equals("Words:")) {
                    x = x + 7;
                    write = true;
                }
                else if (hypos.charAt(x) == '(') {
                    write = false;
                    if (temp.length() > 1) {
                        while (temp.charAt(temp.length() - 1) == ' ') {
                            temp = temp.substring(0, temp.length() - 1);
                        }
                    }
                    String [] split = temp.split(", ");
                    for(int i=0;i<split.length;i++){
                        if (!words.contains(split[i])&& !temp.equals("")) {
                            words.add(split[i]);
                        }
                    }
                    temp = "";
                }
                if (write && hypos.charAt(x) != '\n'
                        && hypos.charAt(x) != '-') {
                    temp += hypos.charAt(x);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        /* get synonyms from wordnet */
        try {
            PointerTargetNodeList synonyms = PointerUtils
                    .getSynonyms(wordnetpos.getSenses().get(0));
            String synonym = synonyms.toString();
            for (int x = 0; x < synonym.length() - 6; x++) {
                if (synonym.substring(x, x + 6).equals("Words:")) {
                    x = x + 7;
                    write = true;
                }
                else if (synonym.charAt(x) == '(') {
                    write = false;
                    if (temp.length() > 1) {
                        while (temp.charAt(temp.length() - 1) == ' ') {
                            temp = temp.substring(0, temp.length() - 1);
                        }
                    }
                    String [] split = temp.split(", ");
                    for(int i=0;i<split.length;i++){
                    if (!words.contains(temp) && !temp.equals("")) {
                        words.add(temp);
                    }
                    }
                    temp = "";
                }
                if (write && synonym.charAt(x) != '\n'
                        && synonym.charAt(x) != '-') {
                    temp += synonym.charAt(x);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        if(words.isEmpty()){
            System.out.println("empty list");
        }
        return words;
    }

    /**
     * gets the DDDB score of the returned words
     * 
     * @param word
     * @param pos
     * @return the word, the term1 syntactical usage, and the PPDB score
     */
    public static HashMap<String, Double> getPPDBScore(String word,
            String pos) {
        HashMap<String, Double> temp = new HashMap<String, Double>();
        convertPOS(pos, word);
        String lemma = wordnetpos.getLemma();
        try {
            /* Check if PPDB contains Term1 */
            if (ppdb.containsKey(lemma)) {
                String[] tempor = ppdb.get(lemma).split("\n");
                for (int x = 0; x < tempor.length; x++) {
                    String[] split = tempor[x].split("\\$");
                    if (split[1].contains(paraphrasepos)
                            && !split[0].equals(" ") && !split[0].equals("")
                            && !temp.containsKey(split[0])) {
                        temp.put(split[0] + split[1],
                                Double.parseDouble(split[2]));
                    }
                }
            }
            else {
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
        }
        return temp;
    }

    /**
     * Calls termToString for each word in provided sentence
     * 
     * @param s
     *            sentence to paraphrased, example: I-noun~ like-verb~ it-noun~
     * @return paraphrased sentence
     */
    public String sentenceToString(String s) {
        String temp = "";
        String[] split = s.split("~");
        for (int i = 0; i < split.length; i++) {
            if (split[i].startsWith(" ")) {
                split[i] = split[i].substring(1, split[i].length());
            }
            temp += "{" + split[i].split("-")[0] + ": " + termToString(
                    split[i].split("-")[0], split[i].split("-")[1]) + "}";
        }
        return temp;
    }

    /**
     * convert part of speech into the correct formats
     * 
     * @param s
     * @param s2
     */
    private static void convertPOS(String s, String s2) {
        s = s.toLowerCase();
        try {
            if (s.equals("noun") || s.equals("nn") || s.equals("pronoun")) {
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
            else if (s.equals("adjective") || s.equals("adj")) {
                paraphrasepos = "JJ";
                wordnetpos = dictionary.lookupIndexWord(POS.ADJECTIVE, s2);
            }
            else if (s.equals("adverb") || s.equals("adv")) {
                wordnetpos = dictionary.lookupIndexWord(POS.ADVERB, s2);
                paraphrasepos = "nope";
            }
            else {
                paraphrasepos = "nope";
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check's if word1 and word2 are synonyms
     * 
     * @param word1
     * @param pos1
     * @param word2
     * @param pos2
     * @return true if synonyms, false if not
     */
    public static boolean checkSynonyms(String word1, String pos1,
            String word2, String pos2) {
        System.out.println("\nChecking if synonyms: " + word1 + "--" + word2);
        List<String> s1 = termToString(word1, pos1);
        List<String> s2 = termToString(word2, pos2);
        List<String> common = new ArrayList<String>(s1);
        common.retainAll(s2);
        System.out.println("Common: " + common);
        return common.size() > 0;
    }
}
