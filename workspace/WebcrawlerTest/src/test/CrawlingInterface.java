/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author zayda
 */

import javax.swing.JOptionPane;

public class CrawlingInterface extends javax.swing.JFrame {

    
    Controller controller;
    public CrawlingInterface() {
        controller = new Controller();
        initComponents();
        labelDocumentMax.setVisible(false);
        textDocumentMax.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelDocumentLimit = new javax.swing.JLabel();
        textCrawlerCount = new javax.swing.JTextField();
        radioLimitNo = new javax.swing.JRadioButton();
        radioLimitYes = new javax.swing.JRadioButton();
        buttonCancel = new javax.swing.JButton();
        labelDocumentMax = new javax.swing.JLabel();
        textURLDomain = new javax.swing.JTextField();
        textDocumentMax = new javax.swing.JTextField();
        labelCrawlerCount = new javax.swing.JLabel();
        labelDirectory = new javax.swing.JLabel();
        buttonCrawl = new javax.swing.JButton();
        textDirectory = new javax.swing.JTextField();
        labelURLdomain = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        labelDocumentLimit.setText("Limit the amount of crawled sites?");

        textCrawlerCount.setText("1");

        radioLimitNo.setSelected(true);
        radioLimitNo.setText("No");
        radioLimitNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioLimitNoActionPerformed(evt);
            }
        });

        radioLimitYes.setText("Yes");
        radioLimitYes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioLimitYesActionPerformed(evt);
            }
        });

        buttonCancel.setText("Cancel");
        buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelActionPerformed(evt);
            }
        });

        labelDocumentMax.setText("Maximum amount of sites  to crawl");

        textDocumentMax.setText("1");

        labelCrawlerCount.setText("Number of crawlers to set up");

        labelDirectory.setText("Name of the folder to create");

        buttonCrawl.setText("Start crawling");
        buttonCrawl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCrawlActionPerformed(evt);
            }
        });

        textDirectory.setText("default");

        labelURLdomain.setText("URL domain");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelDirectory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelCrawlerCount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textCrawlerCount, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelURLdomain)
                        .addGap(42, 42, 42)
                        .addComponent(textURLDomain, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelDocumentMax)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textDocumentMax, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonCrawl)
                                .addGap(68, 68, 68)
                                .addComponent(buttonCancel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelDocumentLimit)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(radioLimitYes)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radioLimitNo)))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelURLdomain)
                    .addComponent(textURLDomain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelCrawlerCount)
                    .addComponent(textCrawlerCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDocumentLimit)
                    .addComponent(radioLimitYes)
                    .addComponent(radioLimitNo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDocumentMax)
                    .addComponent(textDocumentMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelDirectory)
                    .addComponent(textDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCrawl)
                    .addComponent(buttonCancel))
                .addGap(55, 55, 55))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void radioLimitNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioLimitNoActionPerformed
        radioLimitYes.setSelected(false);
        labelDocumentMax.setVisible(false);
        textDocumentMax.setVisible(false);
    }//GEN-LAST:event_radioLimitNoActionPerformed

    private void radioLimitYesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioLimitYesActionPerformed
        radioLimitNo.setSelected(false);
        labelDocumentMax.setVisible(true);
        textDocumentMax.setVisible(true);
    }//GEN-LAST:event_radioLimitYesActionPerformed

    private void buttonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCancelActionPerformed
        super.dispose();
    }//GEN-LAST:event_buttonCancelActionPerformed

    private void buttonCrawlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCrawlActionPerformed
        try{
            String seed = textURLDomain.getText();
            int crawlerCount = Integer.parseInt(textCrawlerCount.getText());
            if(crawlerCount<=0){
                JOptionPane.showMessageDialog(null,"Make sure to enter a valid crawler count (i.d. a positive integer)");
            }
            else{
                int documentMax = Integer.parseInt(textDocumentMax.getText());
                if(documentMax<=0){
                    JOptionPane.showMessageDialog(null,"Make sure to enter a valid document max (i.d. a positive integer)");
                }
                else{
                    if(radioLimitYes.isSelected()){
                        controller.startCrawling(seed,crawlerCount,true,documentMax,textDirectory.getText());
                        JOptionPane.showMessageDialog(null,"Crawling done!");
                        IndexerControler.run();
                        super.dispose();
                    }
                    else{
                        int confirmation = JOptionPane.showConfirmDialog( null, "Are you sure you don't want to limit the crawling?\nIf you don't limit it, the crawler will download all\nand every single file it finds, which could lead to a really\nlong process, and an inmense use of disk space.\nDo you really want to continue?",
                            "Confirm crawling without limit", JOptionPane.YES_NO_OPTION);
                        if(confirmation==JOptionPane.YES_OPTION){
                            JOptionPane.showMessageDialog(null,"The crawling will begin. Please be patient. The crawling\nwindow will close itself when the process is done. Please wait.");
                            controller.startCrawling(seed,crawlerCount,false,documentMax,textDirectory.getText());
                            JOptionPane.showMessageDialog(null,"Crawling done! Processing will begin.\nThe window will close itself when done.");
                            IndexerControler.run();
                            super.dispose();
                        }
                    }
                }
            }
        }
        catch(Exception e){
            if(e.getClass().getName().equals("NumberFormatException")){
                JOptionPane.showMessageDialog(null,"Make sure to enter a valid crawler count (i.d. a positive integer)");
            }
            else{
                JOptionPane.showMessageDialog(null,"Not controlled error! Please try again");
                System.err.println(e);
            }
        }
    }//GEN-LAST:event_buttonCrawlActionPerformed

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
            java.util.logging.Logger.getLogger(CrawlingInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrawlingInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrawlingInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrawlingInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrawlingInterface().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonCrawl;
    private javax.swing.JLabel labelCrawlerCount;
    private javax.swing.JLabel labelDirectory;
    private javax.swing.JLabel labelDocumentLimit;
    private javax.swing.JLabel labelDocumentMax;
    private javax.swing.JLabel labelURLdomain;
    private javax.swing.JRadioButton radioLimitNo;
    private javax.swing.JRadioButton radioLimitYes;
    private javax.swing.JTextField textCrawlerCount;
    private javax.swing.JTextField textDirectory;
    private javax.swing.JTextField textDocumentMax;
    private javax.swing.JTextField textURLDomain;
    // End of variables declaration//GEN-END:variables
}
