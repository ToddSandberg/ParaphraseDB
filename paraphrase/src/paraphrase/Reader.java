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
 * @version 6/20/2017
 */
public class Reader {
    /**
     * HashMap containing Term1 and the key and all of its info as its value
     */
    HashMap<String, String> store = new HashMap<String, String>();
    /**
     * Initializes the paraphrase HashMap
     */
    public Reader() {
        try {
            System.out.println("loaded wordnet");
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("Inputs//paraphraseHash.ser"));
            PPDB p = (PPDB) in.readObject();
            store = p.ppdbHash;
            in.close();
            System.out.println("loaded PPDB");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    
    
}
