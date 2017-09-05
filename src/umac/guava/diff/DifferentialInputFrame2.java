/*
 * Copyright (C) 2017 mayurdivate
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
package umac.guava.diff;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import umac.guava.IGV;

/**
 *
 * @author mayurdivate
 */
public class DifferentialInputFrame2 extends javax.swing.JFrame {

    /**
     * Creates new form DifferentialInputParameters
     */
    
    
    public DifferentialInputFrame2() {
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

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jComboBoxFC = new javax.swing.JComboBox<>();
        jComboBoxMetho = new javax.swing.JComboBox<>();
        jTextFieldOverlap = new javax.swing.JTextField();
        jTextFieldDownstream = new javax.swing.JTextField();
        jTextFieldPvalue = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jTextFieldUpstream = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanelBackResetNextButtons = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButtonReset = new javax.swing.JButton();
        jButtonStart = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jButtonOutputFolder = new javax.swing.JButton();
        jTextFieldOutputFolder = new javax.swing.JTextField();

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Differential Analysis Parameters", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP));

        jLabel1.setText("Analysis Method ");

        jLabel2.setText("log2 ( Fold Change )");

        jLabel3.setText("P value");

        jLabel7.setText("Downstream of TSS");

        jLabel8.setText("Genome");

        jComboBoxFC.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2","1.5","1"}));

        jComboBoxMetho.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DESeq2"}));

        jTextFieldOverlap.setEditable(false);
        jTextFieldOverlap.setText("hg19");
        jTextFieldOverlap.setEnabled(false);
        jTextFieldOverlap.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jTextFieldOverlapComponentMoved(evt);
            }
        });
        jTextFieldOverlap.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldOverlapKeyTyped(evt);
            }
        });

        jTextFieldDownstream.setText("1000");
        jTextFieldDownstream.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldDownstreamKeyTyped(evt);
            }
        });

        jTextFieldPvalue.setText("0.05");
        jTextFieldPvalue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldPvalueActionPerformed(evt);
            }
        });

        jLabel9.setText("Upstream of TSS");

        jTextFieldUpstream.setText("1000");
        jTextFieldUpstream.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldUpstreamKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxMetho, 0, 82, Short.MAX_VALUE)
                    .addComponent(jComboBoxFC, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUpstream))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextFieldPvalue)
                            .addComponent(jTextFieldDownstream, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldOverlap)))
                .addGap(10, 10, 10))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxMetho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOverlap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDownstream, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUpstream, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jLabel4.setText("Please fill the parmeters ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addContainerGap(331, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(5, 5, 5))
        );

        jPanelBackResetNextButtons.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButtonReset.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        jButtonReset.setText("Reset");
        jButtonReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonResetActionPerformed(evt);
            }
        });

        jButtonStart.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        jButtonStart.setText("Start");
        jButtonStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelBackResetNextButtonsLayout = new javax.swing.GroupLayout(jPanelBackResetNextButtons);
        jPanelBackResetNextButtons.setLayout(jPanelBackResetNextButtonsLayout);
        jPanelBackResetNextButtonsLayout.setHorizontalGroup(
            jPanelBackResetNextButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackResetNextButtonsLayout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );
        jPanelBackResetNextButtonsLayout.setVerticalGroup(
            jPanelBackResetNextButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBackResetNextButtonsLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelBackResetNextButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonReset, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonStart, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButtonOutputFolder.setText("output folder");
        jButtonOutputFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOutputFolderActionPerformed(evt);
            }
        });

        jTextFieldOutputFolder.setEditable(false);
        jTextFieldOutputFolder.setDragEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jButtonOutputFolder)
                .addGap(18, 18, 18)
                .addComponent(jTextFieldOutputFolder)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOutputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldOutputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBackResetNextButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(10, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelBackResetNextButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 4, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonStartActionPerformed
        
        File outputFolder =  new File(jTextFieldOutputFolder.getText());
        if(outputFolder.exists()){
            DifferentialResultFrame.outputFolder = outputFolder;
            DifferentialOutputFiles dof = DifferentialOutputFiles.getDifferentialOutputFiles(outputFolder);
        
            double foldchange =  Double.parseDouble(jComboBoxFC.getSelectedItem().toString());
            double pvalue =  Double.parseDouble(jTextFieldPvalue.getText());
            int downstream = Integer.parseInt(jTextFieldDownstream.getText());
            int upstream = Integer.parseInt(jTextFieldUpstream.getText());
            String genome = "hg19";
            
            GdiffInput diffInput = new GdiffInput(outputFolder, dof, foldchange,
                    pvalue, DifferentialInputFrame1.dfInputList, DifferentialInputFrame1.projectName, genome,upstream,downstream);
            this.dispose();
            new DifferentialAnalysisFlow().startDifferentialAnalysis(diffInput);
        }
    }//GEN-LAST:event_jButtonStartActionPerformed
    
    private void jTextFieldPvalueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldPvalueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldPvalueActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        DifferentialInputFrame1.differentialInputFrame.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButtonResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonResetActionPerformed
        jComboBoxMetho.setSelectedIndex(0);
        jComboBoxFC.setSelectedIndex(0);
        jTextFieldOverlap.setText("1");
        jTextFieldDownstream.setText("1000");
        jTextFieldPvalue.setText("0.05");
        
        
    }//GEN-LAST:event_jButtonResetActionPerformed

    private void jButtonOutputFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOutputFolderActionPerformed
        // TODO add your handling code here:
        JFileChooser addFileChooser = new JFileChooser();
        addFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(DifferentialInputFrame1.lastPath != null){
            addFileChooser.setCurrentDirectory(DifferentialInputFrame1.lastPath);
        }

        addFileChooser.setDialogTitle("Select output folder");
        
        int returnVal = addFileChooser.showOpenDialog(null);
        
        if(returnVal == JFileChooser.APPROVE_OPTION){
            File outputDir = addFileChooser.getSelectedFile();
            jTextFieldOutputFolder.setText(outputDir.getAbsolutePath());
            DifferentialOutputFiles.outFolder = outputDir;
//            jTextFieldOutputFolder.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_jButtonOutputFolderActionPerformed

    private void jTextFieldOverlapKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldOverlapKeyTyped
        // TODO add your handling code here:
        char key =  evt.getKeyChar();
        if(!Character.isDigit(key)){
            evt.consume();
        }
        
    }//GEN-LAST:event_jTextFieldOverlapKeyTyped

    private void jTextFieldOverlapComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTextFieldOverlapComponentMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldOverlapComponentMoved

    private void jTextFieldDownstreamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldDownstreamKeyTyped
        // TODO add your handling code here:
        char key =  evt.getKeyChar();
        if(!Character.isDigit(key)){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldDownstreamKeyTyped

    private void jTextFieldUpstreamKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldUpstreamKeyTyped
        // TODO add your handling code here:
        char key =  evt.getKeyChar();
        if(!Character.isDigit(key)){
            evt.consume();
        }
    }//GEN-LAST:event_jTextFieldUpstreamKeyTyped

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonOutputFolder;
    private javax.swing.JButton jButtonReset;
    private javax.swing.JButton jButtonStart;
    private javax.swing.JComboBox<String> jComboBoxFC;
    private javax.swing.JComboBox<String> jComboBoxMetho;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanelBackResetNextButtons;
    private javax.swing.JTextField jTextFieldDownstream;
    private javax.swing.JTextField jTextFieldOutputFolder;
    private javax.swing.JTextField jTextFieldOverlap;
    private javax.swing.JTextField jTextFieldPvalue;
    private javax.swing.JTextField jTextFieldUpstream;
    // End of variables declaration//GEN-END:variables
}
