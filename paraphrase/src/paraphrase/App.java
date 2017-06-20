package paraphrase;
/**
 * Shows a few examples of the usage of paraphrase
 * @author ToddSandberg
 * @version 6/7/2017
 */
public class App {
    public static void main(String [] args){
        Reader pp = new Reader();
        //System.out.println(pp.sentenceToString("let's-verb~ go-verb~ over-preposition~ there-noun~"));
        //System.out.println(pp.termToString("break","noun"));
        //System.out.println(pp.termToString("break","verb"));
        //System.out.println(pp.termToString("to contribute to"));
        //System.out.println(pp.termToString("how is"));
        //System.out.println(pp.termToString("how's","verb"));
        //System.out.println(pp.termToString("the council to","noun"));
        //System.out.println(pp.termToString("i ate it"));
        //System.out.println(pp.termToString("stop"));
        //System.out.println(pp.termToString("to prevent"));
        //System.out.println(pp.termToString("all", "noun"));
        System.out.println(pp.sentenceToString("tell-verb~ everybody-noun~"));
        //System.out.println(pp.termToString("everybody"));
        //System.out.println(pp.termToString("the whole world"));
        //System.out.println(pp.termToString("the entire world"));*/
    }
}
