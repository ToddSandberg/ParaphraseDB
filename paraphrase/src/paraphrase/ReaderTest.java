package paraphrase;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests for the Reader class
 * @author ToddSandberg
 * @version 6/7/2017
 */
public class ReaderTest {
    Reader pp = new Reader();
    /**
     * Tests the phrase "to be" with its expected input
     */
    @Test
    public void toBeTest() {
        String expected = "Term1=tobe Term2=toparticipatein PPDB2.0Score=6.49978 Term1SyntacticalUsage=[NP/NP,1][NP/NP,2]";
        String actual = pp.termToString("tobe");
        assertEquals(expected,actual);
    }
    /**
     * Tests the phrase "to be" with its expected input tests removal of spaces
     */
    @Test
    public void toBeWithSpacesTest() {
        String expected = "Term1=tobe Term2=toparticipatein PPDB2.0Score=6.49978 Term1SyntacticalUsage=[NP/NP,1][NP/NP,2]";
        String actual = pp.termToString("to be");
        assertEquals(expected,actual);
    }
    /**
     * tests "I ate it" which is not in PPDB
     */
    @Test
    public void IAteItTest() {
        String expected = "not included";
        String actual = pp.termToString("I ate it");
        assertEquals(expected,actual);
    }
    /**
     * tests "to participate in" which is not in PPDB
     */
    @Test
    public void ToParticipateInTest() {
        String expected = "Term1=toparticipatein Term2=tocontributeto PPDB2.0Score=6.76072 Term1SyntacticalUsage=[NP/NP,1][NP/NP,2]";
        String actual = pp.termToString("toparticipatein");
        assertEquals(expected,actual);
    }
    /**
     * tests "to contribute to" which is not in PPDB
     */
    @Test
    public void ToContributeToTest() {
        String expected = "Term1=tocontributeto Term2=toparticipatein PPDB2.0Score=6.72540 Term1SyntacticalUsage=[NP/NP,1][NP/NP,2]";
        String actual = pp.termToString("tocontributeto");
        assertEquals(expected,actual);
    }
}
