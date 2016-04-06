
package makhluk2;

import jcurses.system.*;
import jcurses.event.*;
import jcurses.util.*;
import jcurses.widgets.*;
import net.slashie.libjcsi.wswing.WSwingConsoleInterface;
import net.slashie.libjcsi.jcurses.JCursesConsoleInterface;
import net.slashie.libjcsi.ConsoleSystemInterface;
import java.util.*;
import java.io.*;

class World  {
        
	private int panjang;
	private int lebar;
        ConsoleSystemInterface csi;
        char Dunia[][] = new char[30][30];
        

///Administrator///=========================================================================
	private int size=10; //banyak makhluk maksimal
	private int count=0; //banyak makhluk dalam daftar
	private Vector<MakhlukHidup> daftar = new Vector<MakhlukHidup>(size);//array pointer objek makhluk
        
///Konduktor///=============================================================================
    private int lifeState;
    private int Count;

//private//=======================================================================================
        
	private void resetCursor()
	{
            //
	}
	//=======================================================================================
	private void moveCursor(int x, int y)
	{
		//System call set cursor to (x,y) at console
	}
	//=======================================================================================
	private void cursorSwitch(boolean b)
	{/*
	    HANDLE out = GetStdHandle(STD_OUTPUT_HANDLE);

	    CONSOLE_CURSOR_INFO     cursorInfo;

	    GetConsoleCursorInfo(out, cursorInfo);
	    cursorInfo.bVisible = b; // set the cursor visibility
	    SetConsoleCursorInfo(out, cursorInfo);*/
	}

	private void clear()
	{
		CLS n = new CLS();
		try {
			n.clear();
		} catch (IOException e1) {}
		catch (InterruptedException e2) {}
		finally {}
	}

//public//=============================================================================================
        
//World//==============================================================================================
	public World() {
            csi = new WSwingConsoleInterface("Makhluk2");
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++)  {
                    Dunia[i][j] = '.';
                }
            }
        }
        
        public void printDunia() {
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 30; j++)  {
                    System.out.print(Dunia[i][j]);
                }
                System.out.println();
            }
        }
        
        public void initDisplay() throws ExceptionInInitializerError
	{
            csi.cls();
            size = 10;
            count = 0;
            lifeState = 1;
            Count = 0;
            resetCursor();
	    for(int i=0; i<30; ++i)
	    {
	        for(int j=0; j<30; ++j)
	        {
	            System.out.print('.');
                    csi.print(i, j, Character.toString('.'));
	        }
	        System.out.println();
                csi.print(i,29, "\n");
	    }
	}
        
        
//=======================================================================================
	public void updateDisplay() throws ExceptionInInitializerError
	{
	    for(int i=0; i<get_count(); ++i)
	    {
	        if(get_daftar(i) != null)
	        {
	            if(!get_daftar(i).isMati())
	            {
	                draw(get_daftar(i));
	            }
	            else
	            {
	                endDraw(get_daftar(i));
	                pluck(i);
	            }
	        }
	    }
	}

	public void updateMakhluk(int i) throws ExceptionInInitializerError
	{
		boolean end = false;
		while(!end)
		{
			if(get_daftar(i) != null)
			{
				if(!get_daftar(i).isMati())
				{
					draw(get_daftar(i));
					try {
                                            Thread.sleep(100);
                                        } catch (InterruptedException e) {
                                            
                                        } finally {
                                            
                                        }
				}
				else
				{
					endDraw(get_daftar(i));
					try {
                                            Thread.sleep(3000);
                                        } catch (InterruptedException e1) {
                                            
                                        } finally {
                                            
                                        }
					pluck(i);
					end = true;
				}
			}
		}
	}

	public void draw(Point Px, Point Pc, char display) throws ExceptionInInitializerError
	{
		int ex_X = Px.getAbsis();
		int ex_Y = Px.getOrdinat();
		int x = Pc.getAbsis();
		int y = Pc.getOrdinat();
                    
                Dunia[ex_X][ex_Y] = '.';
                //csi.print(ex_X,ex_Y,".");
                Dunia[x][y] = display;
                
                //csi.print(x, y, Character.toString(display));
                    
	}

	public void draw(Point Pc, char display) throws ExceptionInInitializerError
	{
		int x = Pc.getAbsis();
		int y = Pc.getOrdinat();
                
                
                csi.print(x, y, Character.toString(display));
                Dunia[x][y] = display;
	}

	public void draw(MakhlukHidup m1) throws ExceptionInInitializerError
	{
		Point P  = m1.getPosisi();
		Point PP = m1.getPrecPosisi();
		draw(PP, P, m1.get_DNA());

		m1.setPrecPosisi(P);
	}

	public void initDraw(MakhlukHidup m1) throws ExceptionInInitializerError
	{
		Point P = m1.getPosisi();
		draw(P, m1.get_DNA());

		m1.setPrecPosisi(P);
	}

	public void endDraw(MakhlukHidup m1) throws ExceptionInInitializerError
	{
		draw(m1.getPrecPosisi(), '.');
		draw(m1.getPosisi(), '_');
	}

	public boolean isGameOver()
	{

		if(get_count() <= 0) return true;
		else				 return false;

	}

	public void tangkapLayar() throws IOException, NullPointerException
	{
		boolean found;
		File outfile = new File("out.txt");
		outfile.createNewFile();
		FileWriter out = new FileWriter(outfile);

		for(int i=0; i<30; ++i)
		{
			for(int j=0; j<30; ++j)
			{
				found = false;
				if (!daftar.isEmpty()) {
                                    for(int k=0; k<get_size(); k++)
                                    {
                                            if((get_daftar(k) != null) && (get_daftar(k).getPosisi().getAbsis()==j) && (get_daftar(k).getPosisi().getOrdinat()==i))
                                            {
                                                    out.write(get_daftar(k).get_DNA()+"");
                                                    found = true;
                                            }
                                    }
                                    if(!found) out.write(".");
                                } else {
                                    out.write(".");
                                }
			}
			out.write("\n");
		}
		out.flush();
		out.close();
	}

	public void creation(Point P, char opsi)
	{
		if(get_count() < get_size())
		{
			switch (opsi)
			{
				case '1' :
				{
					Polisi m = new Polisi(P);
					fillDaftar(m);
					break;
				}

				case '2' :
				{
					Herbivora m = new Herbivora();
                                        Factory.makeGajah(m);
					fillDaftar(m);
					break;
				}

				case '3' :
				{
					Karnivora m = new Karnivora();
                                        Factory.makeHyena(m);
					fillDaftar(m);
					break;
				}

				case '4' :
				{
					Omnivora m = new Omnivora();
                                        Factory.makeBeruang(m);
					fillDaftar(m);
					break;
				}

				case '5' :
				{
					Pemburu m = new Pemburu(P);
					fillDaftar(m);
					break;
				}

				case '6' :
				{
					Tumbuhan m = new Tumbuhan();
                                        Factory.makeRumput(m);
					fillDaftar(m);
					break;
				}

				case '7' :
				{
					Tumbuhan m = new Tumbuhan();
                                        Factory.makePohon(m);
					fillDaftar(m);
					break;
				}

				case '8' :
				{
					Herbivora m = new Herbivora();
                                        Factory.makeUnta(m);
					fillDaftar(m);
					break;
				}

				case '9' :
				{
					Karnivora m = new Karnivora();
                                        Factory.makeHarimau(m);
					fillDaftar(m);
					break;
				}

				case '0' :
				{
					Omnivora m = new Omnivora();
                                        Factory.makeMandril(m);
					fillDaftar(m);
					break;
				}
			}
		}
	}

	public void killAll()
	{
		for(int i=0; i<get_count(); i++)
		{
			if(get_daftar(i) != null)
			{
				get_daftar(i).setMati(true);
			}
		}
	}

///Konduktor///=======================================================================
    public void setLifeState(int ls) {
        lifeState = ls;
    }
    public int getLifeState() {
        return lifeState;
    }
    public void aging(MakhlukHidup m1) {
        if((lifeState == 1) && (Count%10 == 0)) {
            m1.menua();
        }
    }
    public void pause() {
    	lifeState = 0;
    }
    public void resume() {
    	lifeState = 1;
    }
    public void setCount(int c) {
    	Count = c;
    }
    public int getCount() {
    	return Count;
    }
    public void hidup(MakhlukHidup m1) {
        if (!(m1 instanceof Tumbuhan)) {
            if(lifeState == 1) //cek pause atau tidak
            {
                if(((Hewan)m1).get_Kecepatan() != 0)
                {
                    if (Count%(10-((Hewan)m1).get_Kecepatan()) == 0){
                        if(((Hewan)m1).getPosisi() == new Point(29,29))//ujung kanan-bawah
                        {
                            ((Hewan)m1).set_Arah(8);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi() == new Point(0,29))//kiri-bawah
                        {
                            ((Hewan)m1).set_Arah(2);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi() == new Point(29,0))//kanan-atas
                        {
                            ((Hewan)m1).set_Arah(6);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi() == new Point(0,0))//kiri-atas
                        {
                            ((Hewan)m1).set_Arah(4);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi().getAbsis() >= 29)//kanan
                        {
                            ((Hewan)m1).set_Arah(7);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi().getAbsis() == 0)//kiri
                        {
                            ((Hewan)m1).set_Arah(3);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi().getOrdinat() >= 29)
                        {
                            ((Hewan)m1).set_Arah(1);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else if(((Hewan)m1).getPosisi().getOrdinat() == 0)
                        {
                            ((Hewan)m1).set_Arah(5);

                            ((Hewan)m1).gerak_berarah();
                        }
                        else
                        {
                            ((Hewan)m1).gerak_berarah();
                        }

                        ((Hewan)m1).setSedangMemburu(false);
                    }
                }
            }
            else if(lifeState == 0)
            {

            }
        }
    }
    ///Administrator///======================================================================================
    public void fillDaftar(MakhlukHidup n)
	{
		daftar.add(count,n);
		count++;
	}

	public void pluck(MakhlukHidup n)
	{
		for(int i=0; i<get_count(); i++)
		{
			if(get_daftar(i) == n)
			{
				daftar.remove(i);
				break;
			}
		}
	}

	public void pluck(int i)
	{
		daftar.remove(i);
		count--;
	}

	public void sinyal()
	{
	    for(int i1=0; i1 < get_count(); i1++){
	        for(int i2=0; i2 < get_count(); i2++)
	        {
	            if(i1 != i2)
	            {
	                    get_daftar(i1).Reaction(get_daftar(i2));
	            }
	        }
	    }
	}

	public int get_size()
	{
		return size;
	}

	public int get_count()
	{
		return daftar.size();
	}

	public Vector<MakhlukHidup> get_daftar()
	{
		return daftar;
	}

	public MakhlukHidup get_daftar(int i)
	{
		return daftar.get(i);
	}

	//setter
	public void set_size(int s)
	{
		size = s;
	}

	public void set_count(int c)
	{
		count = c;
	}
}