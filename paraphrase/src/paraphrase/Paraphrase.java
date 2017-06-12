package paraphrase;

import java.io.Serializable;
/**
 * Contains the information about Term1
 * @author ToddSandberg
 * @version 6/7/2017
 */
public class Paraphrase implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -3404053570019014715L;
    /**
     * related term for Term1
     */
    public String term2;
    /**
     * Score from PPDB
     */
    public String ppdb2score;
    /**
     * Term1's Syntactic Usage
     */
    public String Term1SyntacticUsage;
    
    public Paraphrase(String t2,String pp2,String syn){
        term2 = t2;
        ppdb2score = pp2;
        Term1SyntacticUsage = syn;
    }
}
