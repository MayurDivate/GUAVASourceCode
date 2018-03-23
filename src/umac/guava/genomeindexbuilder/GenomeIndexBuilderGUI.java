/*
 * Copyright (C) 2018 mayurdivate
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package umac.guava.genomeindexbuilder;

import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author mayurdivate
 */
public class GenomeIndexBuilderGUI extends javax.swing.JFrame {

    /**
     * Creates new form GenomeIndexBuilderGUI
     */
    public GenomeIndexBuilderGUI() {
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

        buttonAlignerGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabelAligner = new javax.swing.JLabel();
        jRadioButtonBowtie = new javax.swing.JRadioButton();
        jRadioButtonBowtie2 = new javax.swing.JRadioButton();
        jButtonGenomeFasta = new javax.swing.JButton();
        jTextFieldGenomeFasta = new javax.swing.JTextField();
        jButtonOutputFolder = new javax.swing.JButton();
        jTextFieldOutputFolder = new javax.swing.JTextField();
        jButtonCancel = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentsMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelAligner.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelAligner.setText("Aligner");

        buttonAlignerGroup.add(jRadioButtonBowtie);
        jRadioButtonBowtie.setSelected(true);
        jRadioButtonBowtie.setText("Bowtie");

        buttonAlignerGroup.add(jRadioButtonBowtie2);
        jRadioButtonBowtie2.setText("Bowtie2");

        jButtonGenomeFasta.setText("Genome Fasta");
        jButtonGenomeFasta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGenomeFastaActionPerformed(evt);
            }
        });

        jTextFieldGenomeFasta.setEditable(false);
        jTextFieldGenomeFasta.setText("/genome.fa");

        jButtonOutputFolder.setText("output folder");
        jButtonOutputFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOutputFolderActionPerformed(evt);
            }
        });

        jTextFieldOutputFolder.setEditable(false);
        jTextFieldOutputFolder.setText("/index/");

        jButtonCancel.setText("Cancel");
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jButtonStart.setText("Start");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(178, 178, 178)
                        .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonOutputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOutputFolder))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButtonGenomeFasta, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabelAligner, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldGenomeFasta)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jRadioButtonBowtie, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jRadioButtonBowtie2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)))))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelAligner, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonBowtie, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonBowtie2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonGenomeFasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldGenomeFasta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOutputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOutputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        fileMenu.setMnemonic('f');
        fileMenu.setText("Tools");

        openMenuItem.setMnemonic('o');
        openMenuItem.setText("GUAVA");
        fileMenu.add(openMenuItem);

        saveMenuItem.setMnemonic('s');
        saveMenuItem.setText("Diff");
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("GenomeIndexBuilder");
        fileMenu.add(saveAsMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentsMenuItem.setMnemonic('c');
        contentsMenuItem.setText("Contents");
        helpMenu.add(contentsMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void jButtonGenomeFastaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGenomeFastaActionPerformed
        // TODO add your handling code here:
        JFileChooser jFileChooserGenomeFasta =  new JFileChooser();
        jFileChooserGenomeFasta.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter fastaFile =  new FileNameExtensionFilter("Fasta File", "fasta","fa");
        jFileChooserGenomeFasta.setFileFilter(fastaFile);
        
        jFileChooserGenomeFasta.setDialogTitle("Select genome fasta file");
        int returnVal = jFileChooserGenomeFasta.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            jTextFieldGenomeFasta.setText(jFileChooserGenomeFasta.getSelectedFile().getAbsolutePath());
        }
        
    }//GEN-LAST:event_jButtonGenomeFastaActionPerformed

    private void jButtonOutputFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOutputFolderActionPerformed
        // TODO add your handling code here:
        
        JFileChooser jFileChooserOutputFolder =  new JFileChooser();
        jFileChooserOutputFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        jFileChooserOutputFolder.setDialogTitle("Select genome fasta file");
        int returnVal = jFileChooserOutputFolder.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            jTextFieldOutputFolder.setText(jFileChooserOutputFolder.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_jButtonOutputFolderActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        // TODO add your handling code here:
        
        // create genome index object
        File genomeFastaFile =  new File(jTextFieldGenomeFasta.getText());
        File outputFolder =  new File(jTextFieldOutputFolder.getText());
        File outputFile = getOutputFfile(outputFolder, genomeFastaFile);
        String aligner =  getAligner();
        
        GenomeIndexBuilder gib = new GenomeIndexBuilder(genomeFastaFile, outputFile, aligner);
        this.dispose();
        WaitFrame wait = new WaitFrame();
        this.setVisible(true);
        
        gib.CreateGenomeIndex();
        
        System.out.println("close");
        System.exit(0);
    }//GEN-LAST:event_jButtonStartActionPerformed

    public File getOutputFfile(File outputFolder, File genomeFasta) {
        String outName =  genomeFasta.getName();
        return new File(outputFolder,outName);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.ButtonGroup buttonAlignerGroup;
    private javax.swing.JMenuItem contentsMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonGenomeFasta;
    private javax.swing.JButton jButtonOutputFolder;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JLabel jLabelAligner;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonBowtie;
    private javax.swing.JRadioButton jRadioButtonBowtie2;
    private javax.swing.JTextField jTextFieldGenomeFasta;
    private javax.swing.JTextField jTextFieldOutputFolder;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    // End of variables declaration//GEN-END:variables

    private String getAligner() {
        
        if(jRadioButtonBowtie.isSelected()){
            return "bowtie-build";
        }
        else if(jRadioButtonBowtie2.isSelected()){
            return "bowtie2-build";
        }
        return null;
    }

}
