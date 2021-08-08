import java.io.*;
import static java.lang.Integer.parseInt;
import static javafx.scene.input.KeyCode.S;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author noufsf19
 */
public class Schedule extends javax.swing.JFrame {

    /**
     * Creates new form Schedule
     */
    public Schedule(File f) throws IOException{
      initComponents();
        
      int ts =1;
      int counter=1;
      List<Transaction> transaction= new LinkedList();//for all transactions
      List<Transaction> abortedList= new LinkedList();//for aborted transactions
      MultiVersion mv = new MultiVersion();
      List<tObj> Ilist = new LinkedList();
        
        
      if(f.exists()){
         FileReader fr=new FileReader(f);
         BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
        // StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters  
         String line;
         Transaction t = new Transaction() ;
           // tObj item = new tObj();
           // item.VL= new LinkedList<ItemVersion>();
         char name;
            
         while((line=br.readLine())!=null)    
         { 
             if (line.substring(0,line.indexOf("_")).equals("commit")){
                  System.out.println((line.substring(0,line.indexOf("_"))));
                //  t.status = "commit";
               } 
             else if (line.substring(0,line.indexOf("_")).equals("begin")) { 
               Transaction newT = new Transaction(); 
               newT.name= parseInt(line.substring(line.indexOf("_")+1)); 
               newT.timestamp = ts++;
               System.out.println("T ts: "+ newT.timestamp);
               System.out.println((line.substring(0,line.indexOf("_"))));
               //System.out.println(t.name);
               newT.status = "begin";
               transaction.insert(newT);
               System.out.println("T insert "+transaction.retrieve().name);
               System.out.println("-------------------------------------------");
               if(!transaction.empty()){
                   transaction.findFirst();
                   while (!transaction.last()){
                        System.out.println("T name:" + transaction.retrieve().name);
                        
                        transaction.findNext();
                   }
                    System.out.println("T name:" + transaction.retrieve().name);
               }
               System.out.println("-------------------------------------------");
               
            }
            
            
            else if (line.substring(0,line.indexOf("(")).equals("read")){ 
                System.out.println(line.substring(0,line.indexOf("(")));
                
                 System.out.println(" i will print T name  "+parseInt(line.substring(line.indexOf("_")+1)));
                 
                 int ts2= parseInt(line.substring(line.indexOf("_")+1));
                 Transaction tr = new Transaction() ;
                 
               //  tr.name= parseInt(line.substring(line.indexOf("_")+1)); 
               //  tr.timestamp = ts++;
                 Transaction newT = new Transaction(); 
                if(!transaction.empty()){
                    transaction.findFirst();
                    System.out.println("T is not empty i'm in write operation");
                    while (!transaction.last()){
                        if (transaction.retrieve().name == parseInt(line.substring(line.indexOf("_")+1))){
                            newT = transaction.retrieve();
                             System.out.println("T name after correct if statement"+newT.name);
                        }
                        transaction.findNext();
                    }
                    if (transaction.retrieve().name == parseInt(line.substring(line.indexOf("_")+1))){
                            newT = transaction.retrieve();
                            System.out.println(newT.name);
                }
                }
               System.out.println("T name after find correct T"+newT.name); 
               
               newT.status = "read";
                    
               name = (line.charAt(line.indexOf('(')+1));
               System.out.println(name);
               mv.read(newT, name);
            }
              
            
            
            
            else if  (line.substring(0,line.indexOf("(")).equals("write")) {
               //System.out.println(line.substring(0,line.indexOf("(")));
               Transaction newT = new Transaction(); 
                if(!transaction.empty()){
                    transaction.findFirst();
                    System.out.println("T is not empty i'm in write operation");
                    while (!transaction.last()){
                        if (transaction.retrieve().name == parseInt(line.substring(line.indexOf("_")+1))){
                            newT = transaction.retrieve();
                             System.out.println("T name after correct if statement"+newT.name);
                        }
                        transaction.findNext();
                    }
                    if (transaction.retrieve().name == parseInt(line.substring(line.indexOf("_")+1))){
                            newT = transaction.retrieve();
                            System.out.println(newT.name);
                }
                }
               System.out.println("T name after find correct T "+newT.name); 
               newT.status = "write";
               
               name = (line.charAt(line.indexOf('(')+1));
              // System.out.println(name);
              
               if( mv.write(newT, name ) == false){
                  System.out.println("abort");
                  newT.timestamp = counter;
                  System.out.println("T ts: "+ newT.timestamp);
                  newT.status= "Abort";
                  abortedList.insert(newT);
               }
               else{
                  System.out.println(line.substring(0,line.indexOf("(")));
                  newT.status = "write";
                  System.out.println( "T ts: "+newT.timestamp);
               }
            }  
            
               else {
                System.out.println("finish read file");
            } 
            
            System.out.println("counter : "+counter);   
            counter++;
         }
         if(!abortedList.empty()){
            abortedList.findFirst();
            Transaction t2 = null;
            textField1.setText("Aborted Transaction: \n");
            String text ="";
            while (!abortedList.last()){
               t2= abortedList.retrieve();
               text += "T"+t2.name+" at time: "+t2.timestamp+" - "; 
               abortedList.findNext();
              // jTextField1.setText(text+"\n");
            }  
           t2= abortedList.retrieve();
           text += " T"+t2.name+" at time: "+t2.timestamp; 
           jTextField1.setText(text);
           
         }
        else{
            String text="";
            textField1.setText("Serial schedule: \n");
            transaction.findFirst();
            Transaction t2 = null;
            while(!transaction.last()) {
               t2= transaction.retrieve();
               text += "T"+t2.name+" , ";
               transaction.findNext();
            }
             t2= transaction.retrieve();
             text += "T"+t2.name;

            jTextField1.setText(text);
         } 
      }
   }



    private Schedule() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        viewTrans = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        textField1 = new java.awt.TextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(219, 218, 218));

        jPanel2.setBackground(new java.awt.Color(30, 60, 91));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/big-data (1).png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Multi-version Timestamp Ordering System ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        viewTrans.setBackground(new java.awt.Color(255, 255, 255));
        viewTrans.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jTextField1.setSelectedTextColor(new java.awt.Color(96, 110, 124));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        viewTrans.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 320, 170));
        viewTrans.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 30, -1, -1));

        textField1.setBackground(new java.awt.Color(15, 55, 91));
        textField1.setFont(new java.awt.Font("Lucida Grande", 1, 24)); // NOI18N
        textField1.setForeground(new java.awt.Color(255, 255, 255));
        textField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textField1ActionPerformed(evt);
            }
        });
        viewTrans.add(textField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, 320, 60));

        jButton1.setForeground(new java.awt.Color(55, 76, 96));
        jButton1.setText("Home Page");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        viewTrans.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(viewTrans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void textField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
                // TODO add your handling code here:
                 this.setVisible(false);
                 HomePage hp = new HomePage();
                 hp.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(Schedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Schedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Schedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Schedule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Schedule().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField jTextField1;
    private java.awt.TextField textField1;
    private javax.swing.JPanel viewTrans;
    // End of variables declaration//GEN-END:variables
}
