/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package makhluk2;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Aditio Pangestu
 */
public class TumbuhanTest {
    
    public TumbuhanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        Tumbuhan pohon;
        Tumbuhan rumput;

        Herbivora unta = new Herbivora();
        Factory.makeUnta(unta);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        pohon = new Tumbuhan();
        Factory.makePohon(pohon);

        rumput = new Tumbuhan();
        Factory.makeRumput(rumput)
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Reaction method, of class Tumbuhan.
     */
    //=============================================================
    @Test
    public void testMakeRumput() {
        System.out.println("Make Rumput");
        assert(
            (rumput.get_batas_umur() == 73) &&
            (rumput.get_DNA() == '^') &&
            (rumput.get_ulang_tahun() == 1)
              )
    }

    @Test
    public void testMakePohon() {
        System.out.println("Make Pohon");
        assert(
            (pohon.get_batas_umur() == 99) &&
            (pohon.get_DNA() == '!') &&
            (rumput.get_ulang_tahun() == 1)
              )
    }

    @Test
    public void testReactionPohon() {
        System.out.println("Reaction Pohon");
        pohon.setPosisi(new Point(2,2));
        unta.setPosisi(new Point(2,2));
        pohon.Reaction(unta);
        // TODO review the generated test code and remove the default call to fail.
        assert(pohon.isMati() == true;)
    }

    @Test
    public void testReactionRumput() {
        System.out.println("Reaction Rumput");
        rumput.setPosisi(new Point(2,2));
        unta.setPosisi(new Point(2,2));
        rumput.Reaction(unta);
        // TODO review the generated test code and remove the default call to fail.
        assert(tumput.isMati() == true;)
    }
    
}
