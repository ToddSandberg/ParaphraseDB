package paraphrase;

public class App {
    public static void main(String [] args){
        Reader pp = new Reader();
        System.out.println(pp.termToString("tobe"));
        System.out.println(pp.termToString("to be"));
        System.out.println(pp.termToString("how is"));
        System.out.println(pp.termToString("the council to"));
        System.out.println(pp.termToString("I ate it"));
    }
}
