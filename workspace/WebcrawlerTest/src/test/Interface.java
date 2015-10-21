package test;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import test.PostingsList.PostingsListElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author zayda
 */
public class Interface extends javax.swing.JFrame {

    /**
     * Creates new form Interface
     */
    public Interface() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        labelMainTitle = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuCrawler = new javax.swing.JMenu();
        actionNewCrawling = new javax.swing.JMenuItem();
        actionSeeCrawledSites = new javax.swing.JMenuItem();
        actionDeleteCrawledFiles = new javax.swing.JMenuItem();
        actionSearch = new javax.swing.JMenu();
        actionNewSearch = new javax.swing.JMenuItem();
        actionAdvancedSearch = new javax.swing.JMenuItem();
        menuHelp = new javax.swing.JMenu();
        actionAbout = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        labelMainTitle.setFont(new java.awt.Font("Tahoma", 3, 24)); // NOI18N
        labelMainTitle.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logo.png"))); // NOI18N

        jButton1.setText("Test");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        menuCrawler.setText("Crawler");

        actionNewCrawling.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        actionNewCrawling.setText("New crawling");
        actionNewCrawling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionNewCrawlingActionPerformed(evt);
            }
        });
        menuCrawler.add(actionNewCrawling);

        actionSeeCrawledSites.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        actionSeeCrawledSites.setText("View crawled domains");
        actionSeeCrawledSites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionSeeCrawledSitesActionPerformed(evt);
            }
        });
        menuCrawler.add(actionSeeCrawledSites);

        actionDeleteCrawledFiles.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        actionDeleteCrawledFiles.setText("Delete generated files");
        actionDeleteCrawledFiles.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionDeleteCrawledFilesActionPerformed(evt);
            }
        });
        menuCrawler.add(actionDeleteCrawledFiles);

        jMenuBar1.add(menuCrawler);

        actionSearch.setText("Search engine");

        actionNewSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        actionNewSearch.setText("New search");
        actionNewSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionNewSearchActionPerformed(evt);
            }
        });
        actionSearch.add(actionNewSearch);

        actionAdvancedSearch.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        actionAdvancedSearch.setText("Advanced search");
        actionAdvancedSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionAdvancedSearchActionPerformed(evt);
            }
        });
        actionSearch.add(actionAdvancedSearch);

        jMenuBar1.add(actionSearch);

        menuHelp.setText("Help");

        actionAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        actionAbout.setText("About");
        actionAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionAboutActionPerformed(evt);
            }
        });
        menuHelp.add(actionAbout);

        jMenuBar1.add(menuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(118, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(labelMainTitle)
                        .addGap(106, 106, 106))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(88, 88, 88))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addComponent(labelMainTitle)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void actionSeeCrawledSitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionSeeCrawledSitesActionPerformed
        new CrawledSitesInterface().setVisible(true);
    }//GEN-LAST:event_actionSeeCrawledSitesActionPerformed

    private void actionNewSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionNewSearchActionPerformed
        new SearchInterface().setVisible(true);
    }//GEN-LAST:event_actionNewSearchActionPerformed

    private void actionNewCrawlingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionNewCrawlingActionPerformed
        new CrawlingInterface().setVisible(true);
    }//GEN-LAST:event_actionNewCrawlingActionPerformed

    private void actionDeleteCrawledFilesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionDeleteCrawledFilesActionPerformed
        new DeleteCrawledFilesInterface().setVisible(true);
    }//GEN-LAST:event_actionDeleteCrawledFilesActionPerformed

    private void actionAdvancedSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionAdvancedSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actionAdvancedSearchActionPerformed

    private void actionAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionAboutActionPerformed
        JOptionPane.showMessageDialog(null,"Créditos a Lyon y Juank :3");
    }//GEN-LAST:event_actionAboutActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        PostingsList x = new PostingsList();
        PostingsList.PostingsListElement[] a = new PostingsList.PostingsListElement[3];
        PostingsList.PostingsListElement[] b = new PostingsList.PostingsListElement[5];
        PostingsList.PostingsListElement[] c = new PostingsList.PostingsListElement[8];
        
        a[0] = new PostingsList.PostingsListElement(5,10,15);
        a[1] = new PostingsList.PostingsListElement(8,10,15);
        a[2] = new PostingsList.PostingsListElement(13,10,15);
        
        
        b[0] = new PostingsList.PostingsListElement(1,48,15);
        b[1] = new PostingsList.PostingsListElement(8,48,15);
        b[2] = new PostingsList.PostingsListElement(147,48,15);
        b[3] = new PostingsList.PostingsListElement(5,48,15);
        b[4] = new PostingsList.PostingsListElement(13,48,15);
        
        
        c[0] = new PostingsList.PostingsListElement(56,79,15);
        c[1] = new PostingsList.PostingsListElement(8,79,15);
        c[2] = new PostingsList.PostingsListElement(97,79,15);
        c[3] = new PostingsList.PostingsListElement(7,79,15);
        c[4] = new PostingsList.PostingsListElement(5,79,15);
        c[5] = new PostingsList.PostingsListElement(66,79,15);
        c[6] = new PostingsList.PostingsListElement(24,79,15);
        c[7] = new PostingsList.PostingsListElement(100,79,15);
        
        ArrayList <PostingsList.PostingsListElement[]> lista = new ArrayList<PostingsList.PostingsListElement[]>();
        lista.add(a);
        lista.add(b);
        lista.add(c);
        
        PostingsList.PostingsListElement[] results = Indexer.findMatches(lista);
        
        for(int i=0;i<results.length;i++){
            System.out.println(results[i].docID);
        }
        
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
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Interface().setVisible(true);
            }
        });
        
        IndexerControler.run();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem actionAbout;
    private javax.swing.JMenuItem actionAdvancedSearch;
    private javax.swing.JMenuItem actionDeleteCrawledFiles;
    private javax.swing.JMenuItem actionNewCrawling;
    private javax.swing.JMenuItem actionNewSearch;
    private javax.swing.JMenu actionSearch;
    private javax.swing.JMenuItem actionSeeCrawledSites;
    private javax.swing.JButton jButton1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel labelMainTitle;
    private javax.swing.JMenu menuCrawler;
    private javax.swing.JMenu menuHelp;
    // End of variables declaration//GEN-END:variables
}
