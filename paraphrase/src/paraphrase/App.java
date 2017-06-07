package paraphrase;
/**
 * Shows a few examples of the usage of paraphrase
 * @author ToddSandberg
 * @version 6/7/2017
 */
public class App {
    public static void main(String [] args){
        Reader pp = new Reader();
        System.out.println(pp.termToString("to be"));
        System.out.println(pp.termToString("tocontributeto"));
        System.out.println(pp.termToString("how is"));
        System.out.println(pp.termToString("how's"));
        System.out.println(pp.termToString("the council to"));
        System.out.println(pp.termToString("i ate it"));
        System.out.println(pp.termToString("stop"));
        System.out.println(pp.termToString("toprevent"));
        System.out.println(pp.termToString("all"));
        System.out.println(pp.termToString("everybody"));
        System.out.println(pp.termToString("thewholeworld"));
        System.out.println(pp.termToString("theentireworld"));
    }
}
