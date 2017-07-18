package paraphrase;

import java.util.List;

/**
 * Shows a few examples of the usage of paraphrase
 * @author ToddSandberg
 * @version 6/20/2017
 */
public class App {
    public static void main(String [] args){
        Paraphrase pp = new Paraphrase();
        System.out.println("was: "+Paraphrase.termToString("was", "verb"));
        System.out.println("arrive: "+Paraphrase.termToString("arrive", "verb"));
        System.out.println("was: "+Paraphrase.getPPDBScore("was", "verb"));
        System.out.println("synonyms?: "+Paraphrase.checkSynonyms("arrive", "verb", "food", "noun"));
    }
}
