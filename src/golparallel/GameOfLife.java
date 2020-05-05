package golparallel;
import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Image;
import java.awt.Graphics;
import javax.swing.SwingUtilities;

public class GameOfLife extends javax.swing.JFrame {
    
    final int wid = 100, hei = 100; //map size
    int speed = 100; // ms per round
    boolean[][] currentMove = new boolean[hei][wid], nextMove = new boolean[hei][wid];
    boolean play = false;
    Image offScrImg;
    Graphics offScrGraph; 
    int cont = 0; //counting rounds
    long startTime,endTime; //counting time
    int startx,endx,starty,endy; //to seperate the map


    public GameOfLife() {
        initComponents();
        offScrImg = createImage(jScrollPane1.getWidth(), jScrollPane1.getHeight());
        offScrGraph = offScrImg.getGraphics();
        Timer time = new Timer();
        TimerTask task = new TimerTask(){
            public void run(){
                if(play){                    
                    Thread thread1 = new Thread(new gameThread(0,(hei/2),0,(wid/2)));
                    Thread thread2 = new Thread(new gameThread((hei/2),hei,0,(wid/2)));
                    Thread thread3 = new Thread(new gameThread(0,(hei/2),(wid/2),wid));
                    Thread thread4 = new Thread(new gameThread((hei/2),hei,(wid/2),wid));
                    thread1.start();
                    thread2.start();
                    thread3.start();
                    thread4.start();
                    try {
                        thread1.join();
                        thread2.join();
                        thread3.join();
                        thread4.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for(int i = 0; i < hei; i++){
                        for(int j = 0; j < wid; j++){
                            currentMove[i][j] = nextMove[i][j];
                        }
                    } 
                                   
                    cont = cont+1; //counting rounds
                    jLabel1.setText(String.valueOf(cont));
                    repain();
                    //counting the time every 1000 rounds
                    if(cont%1000==0){
                        endTime=System.currentTimeMillis();
                        jLabel2.setText("("+String.valueOf(endTime-startTime)+" ms)");
                    }
                }
            }
        };
        time.scheduleAtFixedRate(task, 0, speed);
        repain();
    }
    
    private boolean decide(int i, int j){
        int neighbors = 0;
        //left 3 cells
        if(j > 0){
            if(currentMove[i][j-1]) neighbors++;
            if(i>0) if(currentMove[i-1][j-1]) neighbors++;
            if(i<hei-1) if(currentMove[i+1][j-1]) neighbors++;
        }
        //right 3 cells
        if(j < wid-1){
            if(currentMove[i][j+1]) neighbors++;
            if(i>0) if(currentMove[i-1][j+1]) neighbors++;
            if(i<hei-1) if(currentMove[i+1][j+1]) neighbors++;
        }
        //up cell
        if(i>0) if(currentMove[i-1][j]) neighbors++;
        //down cell
        if(i<hei-1) if(currentMove[i+1][j]) neighbors++;
        //total live cells around the current cell
        if(neighbors == 3) return true;
        if(currentMove[i][j] && neighbors == 2) return true;
        return false;
    }
    class gameThread implements Runnable{
        int startx, endx,starty,endy;
        gameThread(int startx, int endx, int starty, int endy){
            this.startx = startx;
            this.endx = endx;
            this.starty = starty;
            this.endy = endy;
        }
        public void run(){
            for(int i = startx; i < endx; i++){
                for(int j = starty; j < endy; j++){
                    nextMove[i][j] = decide(i,j);
                }
            }            
        }
    }
        /*repaint*/
    private void repain(){
        offScrGraph.setColor(jScrollPane1.getBackground());
        offScrGraph.fillRect(0, 0, jScrollPane1.getWidth(), jScrollPane1.getHeight()); //draw a blank rectangle
        for(int i = 0 ; i < hei ; i++){
            for(int j = 0 ; j < wid; j++){
                if(currentMove[i][j]){
                    offScrGraph.setColor(Color.YELLOW);
                    int x = j * jScrollPane1.getWidth()/wid;
                    int y = i * jScrollPane1.getHeight()/hei;
                    offScrGraph.fillRect(x, y, jScrollPane1.getWidth()/wid, jScrollPane1.getHeight()/hei);
                }
            }
        }
        //gridding
        offScrGraph.setColor(Color.BLACK);
        for(int i = 1; i < hei;i++){
            int y = i * jScrollPane1.getHeight()/hei;
            offScrGraph.drawLine(0, y, jScrollPane1.getWidth(), y);
        }
        for(int j = 1; j < wid;j++){
            int x = j * jScrollPane1.getWidth()/wid;
            offScrGraph.drawLine(x, 0, x, jScrollPane1.getHeight());
        }
        jScrollPane1.getGraphics().drawImage(offScrImg, 0, 0, jScrollPane1);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Conway's game of life");

        jScrollPane1.setBackground(new java.awt.Color(102, 102, 102));
        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseDragged(evt);
            }
        });
        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });
        jScrollPane1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jScrollPane1ComponentResized(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton1.setText("Play");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jButton2.setText("Restart");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("0");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("(-- ms)");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("Round:");

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None", "Glider", "Beacon", "Light-weight spaceship", "10 Cell Row" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 235, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addComponent(jComboBox1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        int j = wid * evt.getX() / jScrollPane1.getWidth();
        int i = hei * evt.getY() / jScrollPane1.getHeight();
        currentMove[i][j] = !currentMove[i][j];
        repain();        
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void jScrollPane1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jScrollPane1ComponentResized
        offScrImg = createImage(jScrollPane1.getWidth(), jScrollPane1.getHeight());
        offScrGraph = offScrImg.getGraphics();
        repain();
    }//GEN-LAST:event_jScrollPane1ComponentResized

    //play/pause button
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        startTime = System.currentTimeMillis(); //count time from click the play button
        play = !play;
        if(play) jButton1.setText("Pause");
        else jButton1.setText("Play");
        repain();
    }//GEN-LAST:event_jButton1ActionPerformed

    //restart button
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        play = false;
        jButton1.setText("Play");
        currentMove = new boolean[hei][wid];
        repain();
        cont = 0; //reset the round counter
        jLabel1.setText(String.valueOf(cont));
        jComboBox1.setSelectedIndex(0);
        jLabel2.setText("(-- ms)"); //reset the time
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jScrollPane1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseDragged
        int j = wid * evt.getX() / jScrollPane1.getWidth();
        int i = hei * evt.getY() / jScrollPane1.getHeight();
        if(SwingUtilities.isLeftMouseButton(evt)){
            currentMove[i][j] = true;
        }else currentMove[i][j] = false;
        repain();
    }//GEN-LAST:event_jScrollPane1MouseDragged

    //initial pattern
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if(jComboBox1.getSelectedItem() == "None"){
            currentMove = new boolean[hei][wid];
            repain();
        }
        else if(jComboBox1.getSelectedItem() == "Glider"){
            currentMove = new boolean[hei][wid];
            currentMove[hei/2-1][wid/2] = true;
            currentMove[hei/2+1][wid/2] = true;
            currentMove[hei/2][wid/2+1] = true;
            currentMove[hei/2+1][wid/2-1] = true;
            currentMove[hei/2+1][wid/2+1] = true;
            repain();
        }
        else if(jComboBox1.getSelectedItem() == "Beacon"){
            currentMove = new boolean[hei][wid];
            currentMove[hei/2-1][wid/2-2] = true;
            currentMove[hei/2-1][wid/2-1] = true;
            currentMove[hei/2][wid/2-2] = true;
            currentMove[hei/2][wid/2-1] = true;
            currentMove[hei/2+1][wid/2] = true;
            currentMove[hei/2+1][wid/2+1] = true;
            currentMove[hei/2+2][wid/2] = true;
            currentMove[hei/2+2][wid/2+1] = true;
            repain();
        }
        else if(jComboBox1.getSelectedItem() == "Light-weight spaceship"){
            currentMove = new boolean[hei][wid];
            currentMove[hei/2-2][wid/2-2] = true;
            currentMove[hei/2-2][wid/2+1] = true;
            currentMove[hei/2-1][wid/2+2] = true;
            currentMove[hei/2][wid/2-2] = true;
            currentMove[hei/2][wid/2+2] = true;
            currentMove[hei/2+1][wid/2-1] = true;
            currentMove[hei/2+1][wid/2] = true;
            currentMove[hei/2+1][wid/2+1] = true;
            currentMove[hei/2+1][wid/2+2] = true;
            repain();            
        }
        else if(jComboBox1.getSelectedItem() == "10 Cell Row"){
            currentMove = new boolean[hei][wid];
            currentMove[hei/2][wid/2] = true;
            currentMove[hei/2][wid/2-5] = true;
            currentMove[hei/2][wid/2-4] = true;
            currentMove[hei/2][wid/2-3] = true;
            currentMove[hei/2][wid/2-2] = true;
            currentMove[hei/2][wid/2-1] = true;
            currentMove[hei/2][wid/2+1] = true;
            currentMove[hei/2][wid/2+2] = true;
            currentMove[hei/2][wid/2+3] = true;
            currentMove[hei/2][wid/2+4] = true;
            repain();            
        }        
    }//GEN-LAST:event_jComboBox1ActionPerformed


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameOfLife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameOfLife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameOfLife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameOfLife.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GameOfLife().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
