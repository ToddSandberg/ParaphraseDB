package paraphrase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ListShortener {
    public static void main(String [] args){
        try {
            Scanner scan = new Scanner(new File("ppdb-2.0-s-syntax.txt"));
            PrintWriter print = new PrintWriter(new File("shortenedList.txt"));
            boolean write = true;
            while(scan.hasNext()){
                String s = scan.next();
                if(s.startsWith("PPDB1")){
                    write = false;
                    print.println("");
                    System.out.println("");
                }
                else if(s.startsWith("[")){
                    write = true;
                }
                if(write){
                    print.print(s+" ");
                    System.out.print(s+" ");
                }
            }
            scan.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
