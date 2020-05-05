package golparallel;

import javax.swing.JFrame;

public class GOLParallel {

    public static void main(String[] args) {
       GameOfLife life = new GameOfLife();
       life.setLocationRelativeTo(null); //so the window will shown in the middle
       //life.setExtendedState(JFrame.MAXIMIZED_BOTH); 
       life.show();
    }
    
}
