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
package umac.guava;

import umac.guava.diff.DifferentialInputFrame1;
import umac.guava.genomeindexbuilder.GenomeIndexBuilderGUI;

/**
 *
 * @author mayurdivate
 */
public class HomeFrame extends javax.swing.JFrame {

    /**
     * Creates new form HomeFrame
     */
    public HomeFrame() {
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

        buttonToolGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jRadioGUAVA = new javax.swing.JRadioButton();
        jRadioDifferential = new javax.swing.JRadioButton();
        jRadioGenomeINdexBuilder = new javax.swing.JRadioButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(340, 231));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Select Program", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Andale Mono", 0, 14))); // NOI18N

        buttonToolGroup.add(jRadioGUAVA);
        jRadioGUAVA.setText("ATAC-seq Data Analysis");
        jRadioGUAVA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioGUAVAActionPerformed(evt);
            }
        });

        buttonToolGroup.add(jRadioDifferential);
        jRadioDifferential.setText("ATAC-seq Differential Analysis");
        jRadioDifferential.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioDifferentialActionPerformed(evt);
            }
        });

        buttonToolGroup.add(jRadioGenomeINdexBuilder);
        jRadioGenomeINdexBuilder.setText("Genome Index Builder");
        jRadioGenomeINdexBuilder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioGenomeINdexBuilderActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jRadioDifferential, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioGUAVA, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jRadioGenomeINdexBuilder, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jRadioGUAVA, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioDifferential, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jRadioGenomeINdexBuilder, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jMenu1.setText("About");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioGUAVAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioGUAVAActionPerformed
        
        MainJFrame guava = new MainJFrame();
        CustomChromosomesFrame.mainFrame =  guava;

        if(isWorking(guava)){        
            this.dispose();
            guava.setVisible(true);
        }

    }//GEN-LAST:event_jRadioGUAVAActionPerformed

    private void jRadioDifferentialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioDifferentialActionPerformed
        if(this.isWorking() ){        
            DifferentialInputFrame1.differentialInputFrame = new DifferentialInputFrame1();
            this.dispose();
            DifferentialInputFrame1.differentialInputFrame.setVisible(true);
        }

    }//GEN-LAST:event_jRadioDifferentialActionPerformed

    private void jRadioGenomeINdexBuilderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioGenomeINdexBuilderActionPerformed
        // TODO add your handling code here:
        this.dispose();
        GenomeIndexBuilderGUI gibGui = new GenomeIndexBuilderGUI();
        gibGui.setVisible(true);
    }//GEN-LAST:event_jRadioGenomeINdexBuilderActionPerformed
    
    /********************************************
    * isWorking method to check all dependencies
    ********************************************/
    boolean isWorking(MainJFrame main){
        boolean flag = AnalysisWorkflow.validateToolPaths(true);

        if(!flag){
                System.out.println("\n***There are missing dependancies!");
                System.out.println("***Please use configure.sh to install dependencies.");
                System.out.println("***If you are having difficulties in installing dependencies, please report issue at *GitHub*.");
                System.err.println("");
                System.err.println("***killing GUAVA ... ***");
                System.exit(-1);
        }

        return flag;        
    }

    boolean isWorking(){
        boolean isWorking = false;
        
        isWorking = BedTools.bedtoolsPath();
        if(isWorking){
            isWorking = new UCSCtools().isWorking();
        }
        if(isWorking){
                isWorking =  new IGV().isWorking();
        }
        if(isWorking){
                isWorking = new Samtools().isWorking();
        }
        
        return isWorking;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonToolGroup;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioDifferential;
    private javax.swing.JRadioButton jRadioGUAVA;
    private javax.swing.JRadioButton jRadioGenomeINdexBuilder;
    // End of variables declaration//GEN-END:variables
}
