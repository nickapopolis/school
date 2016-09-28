/**
 * @(#)SpiderGame.java
 *
 * SpiderGame application
 *
 * @author
 * @version 1.00 2011/9/15
 */

import javax.swing.*;

@SuppressWarnings("serial")
public class SpiderGame extends JFrame{

    public static void main(String[] args)
    {
    	Game g1 = new Game("Spider Game");
    	g1.initialize();
    	g1.setVisible(true);
    	
    }
}
