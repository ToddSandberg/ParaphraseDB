package paraphrase;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;

public class Reader {
    HashMap<String,Paraphrase> store = new HashMap<String,Paraphrase>();
    public Reader(){
        try {
            ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("paraphraseHash.txt"));
            store = (HashMap<String, Paraphrase>) in.readObject();
            in.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String termToString(String s){
        if(s.contains(" ")){
            String [] split = s.split(" ");
            s="";
            for(int i=0;i<split.length;i++){
                s+=split[i];
            }
        }
        if(store.containsKey(s)){
        Paraphrase p = store.get(s);
        return "Term1="+s + " Term2="+p.term2 +" PPDB2.0Score="+p.ppdb2score +" Term1SyntacticalUsage="+p.Term1SyntacticUsage;
        }
        else{
            return "not included";
        }
    }
}
