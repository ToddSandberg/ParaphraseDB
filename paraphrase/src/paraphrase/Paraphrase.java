package paraphrase;

import java.io.Serializable;

public class Paraphrase implements Serializable{
    public String term2;
    public String ppdb2score;
    public String Term1SyntacticUsage;
    
    public Paraphrase(String t2,String pp2,String syn){
        term2 = t2;
        ppdb2score = pp2;
        Term1SyntacticUsage = syn;
    }
}
