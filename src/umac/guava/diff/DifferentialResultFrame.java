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

import umac.guava.Pathway;
import java.awt.Desktop;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import umac.guava.GeneOntology;
import umac.guava.Genome;
import umac.guava.IGV;
import umac.guava.IGVdataTrack;

/**
 *
 * @author mayurdivate
 */
public class DifferentialResultFrame extends javax.swing.JFrame {
    
    public static Genome genome;

    /**
     * Creates new form DifferentialResultFrame
     */
    public DifferentialResultFrame() {
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

        jPanelMain = new javax.swing.JPanel();
        jOutputTabs = new javax.swing.JTabbedPane();
        jPanelSummary = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableSummary = new javax.swing.JTable();
        jPanelVolcanoPlot = new javax.swing.JPanel();
        jLabelVplot = new javax.swing.JLabel();
        jPanelPCA = new javax.swing.JPanel();
        jLabelPCAplot = new javax.swing.JLabel();
        jPanelAnnotatedPeaks = new javax.swing.JPanel();
        jScrollPaneAnnotatedPeaks = new javax.swing.JScrollPane();
        jTableDifferentialPeaks = new javax.swing.JTable();
        jPanelBarChart = new javax.swing.JPanel();
        jLabelBarChart = new javax.swing.JLabel();
        jPanelGO = new javax.swing.JPanel();
        jScrollPaneGoTable = new javax.swing.JScrollPane();
        jTableGO = new javax.swing.JTable();
        jPanelPathway = new javax.swing.JPanel();
        jScrollPanePathwayTable = new javax.swing.JScrollPane();
        jTablePathway = new javax.swing.JTable();
        jPanelFooter = new javax.swing.JPanel();
        jButtonOuputFolder = new javax.swing.JButton();
        jLabelGeneSearch = new javax.swing.JLabel();
        jTextFieldSerachKey = new javax.swing.JTextField();
        jButtonIGV = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(800, 800));
        setMinimumSize(new java.awt.Dimension(800, 565));
        setPreferredSize(new java.awt.Dimension(800, 590));
        setResizable(false);

        jOutputTabs.setBackground(new java.awt.Color(153, 153, 153));
        jOutputTabs.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.gray, java.awt.Color.gray));
        jOutputTabs.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jOutputTabs.setPreferredSize(new java.awt.Dimension(780, 480));
        jOutputTabs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jOutputTabsMouseClicked(evt);
            }
        });

        jPanelSummary.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTableSummary.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTableSummary.setFont(new java.awt.Font("Andale Mono", 0, 14)); // NOI18N
        jTableSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Parameter", "value"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableSummary.setRowHeight(25);
        jTableSummary.setShowGrid(false);
        jScrollPane1.setViewportView(jTableSummary);
        if (jTableSummary.getColumnModel().getColumnCount() > 0) {
            jTableSummary.getColumnModel().getColumn(0).setMinWidth(200);
            jTableSummary.getColumnModel().getColumn(0).setPreferredWidth(250);
            jTableSummary.getColumnModel().getColumn(0).setMaxWidth(250);
        }

        javax.swing.GroupLayout jPanelSummaryLayout = new javax.swing.GroupLayout(jPanelSummary);
        jPanelSummary.setLayout(jPanelSummaryLayout);
        jPanelSummaryLayout.setHorizontalGroup(
            jPanelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSummaryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelSummaryLayout.setVerticalGroup(
            jPanelSummaryLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSummaryLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );

        jOutputTabs.addTab("Summary", jPanelSummary);

        jPanelVolcanoPlot.setBackground(new java.awt.Color(255, 255, 255));
        jPanelVolcanoPlot.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        javax.swing.GroupLayout jPanelVolcanoPlotLayout = new javax.swing.GroupLayout(jPanelVolcanoPlot);
        jPanelVolcanoPlot.setLayout(jPanelVolcanoPlotLayout);
        jPanelVolcanoPlotLayout.setHorizontalGroup(
            jPanelVolcanoPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelVolcanoPlotLayout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jLabelVplot, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelVolcanoPlotLayout.setVerticalGroup(
            jPanelVolcanoPlotLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelVolcanoPlotLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelVplot, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );

        jOutputTabs.addTab("Volcano Plot", jPanelVolcanoPlot);

        jPanelPCA.setBackground(new java.awt.Color(255, 255, 255));
        jPanelPCA.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        jLabelPCAplot.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanelPCALayout = new javax.swing.GroupLayout(jPanelPCA);
        jPanelPCA.setLayout(jPanelPCALayout);
        jPanelPCALayout.setHorizontalGroup(
            jPanelPCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPCALayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jLabelPCAplot, javax.swing.GroupLayout.PREFERRED_SIZE, 650, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanelPCALayout.setVerticalGroup(
            jPanelPCALayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelPCALayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelPCAplot, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );

        jOutputTabs.addTab("PCA Plot", jPanelPCA);

        jPanelAnnotatedPeaks.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTableDifferentialPeaks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Chr", "Start", "End", "Length", "log2(FC)", "P value", "adj. P value", "Regulation", "Gene Symbol", "Distance"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableDifferentialPeaks.setGridColor(new java.awt.Color(153, 153, 153));
        jTableDifferentialPeaks.setShowGrid(true);
        jTableDifferentialPeaks.getTableHeader().setReorderingAllowed(false);
        jTableDifferentialPeaks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableDifferentialPeaksMouseClicked(evt);
            }
        });
        jScrollPaneAnnotatedPeaks.setViewportView(jTableDifferentialPeaks);

        javax.swing.GroupLayout jPanelAnnotatedPeaksLayout = new javax.swing.GroupLayout(jPanelAnnotatedPeaks);
        jPanelAnnotatedPeaks.setLayout(jPanelAnnotatedPeaksLayout);
        jPanelAnnotatedPeaksLayout.setHorizontalGroup(
            jPanelAnnotatedPeaksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneAnnotatedPeaks, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );
        jPanelAnnotatedPeaksLayout.setVerticalGroup(
            jPanelAnnotatedPeaksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneAnnotatedPeaks, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );

        jOutputTabs.addTab("Annotated Peaks", jPanelAnnotatedPeaks);

        jPanelBarChart.setBackground(new java.awt.Color(255, 255, 255));
        jPanelBarChart.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        javax.swing.GroupLayout jPanelBarChartLayout = new javax.swing.GroupLayout(jPanelBarChart);
        jPanelBarChart.setLayout(jPanelBarChartLayout);
        jPanelBarChartLayout.setHorizontalGroup(
            jPanelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBarChartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelBarChartLayout.setVerticalGroup(
            jPanelBarChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBarChartLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );

        jOutputTabs.addTab("Bar Chart", jPanelBarChart);

        jPanelGO.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTableGO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "GO ID", "GO Term", "Type", "Pvalue", "adj. P value", "Gene Symbol"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableGO.setGridColor(new java.awt.Color(153, 153, 153));
        jTableGO.setShowGrid(true);
        jTableGO.getTableHeader().setReorderingAllowed(false);
        jScrollPaneGoTable.setViewportView(jTableGO);
        if (jTableGO.getColumnModel().getColumnCount() > 0) {
            jTableGO.getColumnModel().getColumn(0).setMinWidth(100);
            jTableGO.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableGO.getColumnModel().getColumn(0).setMaxWidth(200);
            jTableGO.getColumnModel().getColumn(1).setMinWidth(50);
            jTableGO.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableGO.getColumnModel().getColumn(2).setMinWidth(60);
            jTableGO.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTableGO.getColumnModel().getColumn(2).setMaxWidth(80);
            jTableGO.getColumnModel().getColumn(3).setMinWidth(80);
            jTableGO.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTableGO.getColumnModel().getColumn(3).setMaxWidth(200);
            jTableGO.getColumnModel().getColumn(4).setMinWidth(80);
            jTableGO.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTableGO.getColumnModel().getColumn(4).setMaxWidth(200);
            jTableGO.getColumnModel().getColumn(5).setMinWidth(100);
            jTableGO.getColumnModel().getColumn(5).setPreferredWidth(100);
        }

        javax.swing.GroupLayout jPanelGOLayout = new javax.swing.GroupLayout(jPanelGO);
        jPanelGO.setLayout(jPanelGOLayout);
        jPanelGOLayout.setHorizontalGroup(
            jPanelGOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneGoTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );
        jPanelGOLayout.setVerticalGroup(
            jPanelGOLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneGoTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );

        jOutputTabs.addTab("Gene Ontology", jPanelGO);

        jPanelPathway.setBorder(javax.swing.BorderFactory.createEtchedBorder(java.awt.Color.lightGray, java.awt.Color.lightGray));

        jTablePathway.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "KEGG ID", "Pathway Name", "P value", "adj. P value", "Gene Symbol"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTablePathway.setGridColor(new java.awt.Color(153, 153, 153));
        jTablePathway.setShowGrid(true);
        jTablePathway.getTableHeader().setReorderingAllowed(false);
        jScrollPanePathwayTable.setViewportView(jTablePathway);
        if (jTablePathway.getColumnModel().getColumnCount() > 0) {
            jTablePathway.getColumnModel().getColumn(0).setPreferredWidth(90);
            jTablePathway.getColumnModel().getColumn(0).setMaxWidth(80);
            jTablePathway.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTablePathway.getColumnModel().getColumn(2).setMaxWidth(80);
            jTablePathway.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTablePathway.getColumnModel().getColumn(3).setMaxWidth(80);
            jTablePathway.getColumnModel().getColumn(4).setPreferredWidth(130);
        }

        javax.swing.GroupLayout jPanelPathwayLayout = new javax.swing.GroupLayout(jPanelPathway);
        jPanelPathway.setLayout(jPanelPathwayLayout);
        jPanelPathwayLayout.setHorizontalGroup(
            jPanelPathwayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanePathwayTable, javax.swing.GroupLayout.DEFAULT_SIZE, 751, Short.MAX_VALUE)
        );
        jPanelPathwayLayout.setVerticalGroup(
            jPanelPathwayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPanePathwayTable, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
        );

        jOutputTabs.addTab("Pathway", jPanelPathway);

        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addComponent(jOutputTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 780, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelMainLayout.setVerticalGroup(
            jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelMainLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jOutputTabs, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelFooter.setMaximumSize(new java.awt.Dimension(780, 45));
        jPanelFooter.setMinimumSize(new java.awt.Dimension(780, 45));
        jPanelFooter.setPreferredSize(new java.awt.Dimension(780, 45));

        jButtonOuputFolder.setText("Output Folder");
        jButtonOuputFolder.setPreferredSize(new java.awt.Dimension(150, 30));
        jButtonOuputFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOuputFolderActionPerformed(evt);
            }
        });

        jLabelGeneSearch.setText("Gene Symbol");
        jLabelGeneSearch.setPreferredSize(new java.awt.Dimension(81, 30));
        jLabelGeneSearch.setEnabled(false);

        jTextFieldSerachKey.setEnabled(false);
        jTextFieldSerachKey.setPreferredSize(new java.awt.Dimension(14, 30));
        jTextFieldSerachKey.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSerachKeyKeyReleased(evt);
            }
        });

        jButtonIGV.setText("View in IGV");
        jButtonIGV.setEnabled(false);
        jButtonIGV.setPreferredSize(new java.awt.Dimension(114, 30));
        jButtonIGV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIGVActionPerformed(evt);
            }
        });

        jButton2.setText("Close");
        jButton2.setPreferredSize(new java.awt.Dimension(79, 30));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelFooterLayout = new javax.swing.GroupLayout(jPanelFooter);
        jPanelFooter.setLayout(jPanelFooterLayout);
        jPanelFooterLayout.setHorizontalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFooterLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jButtonOuputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelGeneSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jTextFieldSerachKey, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jButtonIGV, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanelFooterLayout.setVerticalGroup(
            jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelFooterLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanelFooterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonOuputFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelGeneSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSerachKey, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIGV, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelFooter, javax.swing.GroupLayout.DEFAULT_SIZE, 786, Short.MAX_VALUE))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelMain, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jPanelFooter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonIGVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIGVActionPerformed
        
        if(jTableDifferentialPeaks.getSelectedRow() > -1){
            int rowIndex = jTableDifferentialPeaks.convertRowIndexToModel(jTableDifferentialPeaks.getSelectedRow());
            TableModel tableModel = jTableDifferentialPeaks.getModel();
            
            String chr = tableModel.getValueAt(rowIndex, 0).toString();
            int start = Integer.parseInt(tableModel.getValueAt(rowIndex, 1).toString());
            int end = Integer.parseInt(tableModel.getValueAt(rowIndex, 2).toString());
            int distance = 100;
            
            File[] tracks =  IGVdataTrack.getDifferentialTracks(DifferentialInputFrame1.dfInputList, DifferentialResultFrame.genome);
            tracks[tracks.length - 1] = DifferentialOutputFiles.getDifferentialOutputFiles(outputFolder,DifferentialResultFrame.genome).getControlTreatmentCommonPeakBed();
            
            IGV.genome = DifferentialResultFrame.genome;
            IGV igv =  new IGV(tracks, chr, start, end, distance);
            
            Thread t1 = new Thread(igv);
            t1.start();
        }
        
        jButtonIGV.setEnabled(false);
    }//GEN-LAST:event_jButtonIGVActionPerformed

    private void jButtonOuputFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOuputFolderActionPerformed
        // TODO add your handling code here:
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(DifferentialOutputFiles.getOutFolder().getAbsoluteFile());
        } catch (IOException ex) {
            Logger.getLogger(DifferentialResultFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonOuputFolderActionPerformed

    private void jTextFieldSerachKeyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSerachKeyKeyReleased
        jButtonIGV.setEnabled(false);
        String query =  jTextFieldSerachKey.getText().trim();
        filterTableEntries(query);
    }//GEN-LAST:event_jTextFieldSerachKeyKeyReleased

    private void jTableDifferentialPeaksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableDifferentialPeaksMouseClicked
        // TODO add your handling code here:
        jButtonIGV.setEnabled(true);
        jTextFieldSerachKey.setEnabled(true);
        jTextFieldSerachKey.setEditable(true);
        
    }//GEN-LAST:event_jTableDifferentialPeaksMouseClicked

    private void jOutputTabsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jOutputTabsMouseClicked
        // TODO add your handling code here:
        
        if(jOutputTabs.getSelectedIndex() == 3){
            jTextFieldSerachKey.setEnabled(true);
            jTextFieldSerachKey.setEditable(true);
            jLabelGeneSearch.setEnabled(true);
            jTextFieldSerachKey.setVisible(true);
            jLabelGeneSearch.setVisible(true);
            jButtonIGV.setVisible(true);
            
        }else{
            jButtonIGV.setEnabled(false);
            jTextFieldSerachKey.setEnabled(false);
            jTextFieldSerachKey.setEditable(false);
            jLabelGeneSearch.setEnabled(false);
            jTextFieldSerachKey.setVisible(false);
            jLabelGeneSearch.setVisible(false);
            jButtonIGV.setVisible(false);
        }
    }//GEN-LAST:event_jOutputTabsMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_jButton2ActionPerformed

    private void filterTableEntries(String query){
        
        int geneColumnIndex = 8;
        DefaultTableModel dfModel =  (DefaultTableModel) jTableDifferentialPeaks.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(dfModel);
        jTableDifferentialPeaks.setRowSorter(tableRowSorter);
        tableRowSorter.setRowFilter(RowFilter.regexFilter("(?i)"+query, geneColumnIndex));
        
    }
    
    public void displayVplot(File plot){
        ImageIcon imageIconVplot  = new ImageIcon(plot.getAbsolutePath());
        jLabelVplot.setIcon(imageIconVplot);
    }
    
    public void displayPCAplot(File plot){
        ImageIcon imageIconVplot  = new ImageIcon(plot.getAbsolutePath());
        jLabelPCAplot.setIcon(imageIconVplot);
    }
    
    public void displayBarChart(File plot){
        ImageIcon imageIconVplot  = new ImageIcon(plot.getAbsolutePath());
        jLabelBarChart.setIcon(imageIconVplot);
    }
    
    public boolean displayResultTable(File deseqResults){
        if(!deseqResults.exists()){
             return false;
        }

        ArrayList<DESeq2AnnotatedPeaks> resultList = DESeq2AnnotatedPeaks.getDESeq2ResultList(deseqResults);
        DefaultTableModel dfModel =  (DefaultTableModel) jTableDifferentialPeaks.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(dfModel);
        jTableDifferentialPeaks.setRowSorter(tableRowSorter);
        
               
        if(resultList.size() == 0){
            return false;
        }
        Object rowData[] = new Object[jTableDifferentialPeaks.getColumnCount()];
        for(int index=0; index < resultList.size(); index++){
            //0     1       2   3       4       5       6           7           8           9      
            //chr   start   end length  log2fc  pvalue  adjPvalue   regulation  genesymbol  distance
            
            rowData[0] =  resultList.get(index).getChromosome();
            rowData[1] =  resultList.get(index).getStart();
            rowData[2] =  resultList.get(index).getEnd();
            rowData[3] =  resultList.get(index).getLength();
            
            NumberFormat formatter = new DecimalFormat("0.0000");
            rowData[4] = Double.parseDouble(formatter.format(resultList.get(index).getFoldchange()));
            
            formatter = new DecimalFormat("0.00E00");
            rowData[5] =  formatter.format(resultList.get(index).getPvalue());
            rowData[6] =  formatter.format(resultList.get(index).getAdjPvalue());
            
            rowData[7] =  resultList.get(index).getRegulation();
            rowData[8] =  resultList.get(index).getGeneSymbol();
            rowData[9] =  resultList.get(index).getDistance();
            dfModel.addRow(rowData);
            
        }
        
        return true;
    
    }
    
    public boolean displayGO(File goResults){
        
     if(!goResults.exists()){
         return false;
     }   
        DefaultTableModel dfModel = (DefaultTableModel) jTableGO.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<>(dfModel);
        jTableGO.setRowSorter(tableRowSorter);
        
        Object[] rowData = new Object[dfModel.getColumnCount()];
        
        HashMap<GeneOntology, GeneOntology> goHashMap = GeneOntology.parseGOAnalysisOutputFile(goResults);
        
        for(GeneOntology go : goHashMap.keySet()){
            //0     1       2       3       4           5
            //GOid  GoTerm  Gotype  pvalue adjPvalue    GeneSymbol  
            rowData[0] = go.getGoID();
            rowData[1] = go.getGoTerm();
            rowData[2] = go.getGoCategory();
            
            NumberFormat formatter = new DecimalFormat("0.00E00");
            rowData[3] =  formatter.format(go.getPvalue());
            rowData[4] =  formatter.format(go.getAdjustedPvalue());
                    
            rowData[5] = go.getGeneSymbols();
            dfModel.addRow(rowData);
        }
        
        
        return true;

        
    }
    
    public boolean displayPathways(File pathwayResults){
        if(!pathwayResults.exists()){
             return false;
         }   
         
         
        DefaultTableModel dfModel =  (DefaultTableModel) jTablePathway.getModel();
        TableRowSorter<DefaultTableModel> tableRowSorter = new TableRowSorter<DefaultTableModel>(dfModel);
        jTablePathway.setRowSorter(tableRowSorter);
        
        HashMap<Pathway, Pathway> goHashMap =  Pathway.parsePathwayAnalysisOutputFile(pathwayResults);
        
        Object rowData[] = new Object[jTablePathway.getColumnCount()];
        
        for(Pathway pathway : goHashMap.keySet() ){
            //  0        1          2       3           4
            //  KeggID  PathwayName Pvalue  adjPvalue   GeneSymbols
            rowData[0] = pathway.getKeggID();
            rowData[1] = pathway.getPathwayname();
            NumberFormat formatter = new DecimalFormat("0.00E00");
            rowData[2] = formatter.format(pathway.getPvalue());
            rowData[3] = formatter.format(pathway.getAdjPvalue());
            rowData[4] = pathway.getGeneSymbol();
            dfModel.addRow(rowData);
        }
        
        
        return true;
        
    }
    
    void addSummary(GdiffInput input){
        DefaultTableModel dfModel =  (DefaultTableModel) jTableSummary.getModel();
        Object rowData[] = new Object[jTableSummary.getColumnCount()];
        
        rowData[0] = "Project Name";
        rowData[1] = ""+input.getProjectName();
        dfModel.addRow(rowData);

        rowData[0] = "Genome build";
        rowData[1] = ""+input.getGenome().getGenomeName();
        dfModel.addRow(rowData);
        
        rowData[0] = "Analysis Method";
        rowData[1] = "DESeq2";
        dfModel.addRow(rowData);
        
        rowData[0] = "P value";
        rowData[1] = ""+input.getPvalue();
        dfModel.addRow(rowData);
        
        rowData[0] = "Fold change";
        rowData[1] = ""+input.getFoldChange();
        dfModel.addRow(rowData);
        
        rowData[0] = "Upstream";
        rowData[1] = ""+input.getUpstream();
        dfModel.addRow(rowData);

        rowData[0] = "Downstream";
        rowData[1] = ""+input.getDownstream();
        dfModel.addRow(rowData);
        
        for(int index=0; index < input.getDiffInputfiles().size(); index++){
        rowData[0] = ""+input.getDiffInputfiles().get(index).getCondition()+"_REP_"+input.getDiffInputfiles().get(index).getReplicateNumber();
        rowData[1] = ""+input.getDiffInputfiles().get(index).getDiifInputFile().getName();
        dfModel.addRow(rowData);
        }
    
    }
    
    static File outputFolder;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonIGV;
    private javax.swing.JButton jButtonOuputFolder;
    private javax.swing.JLabel jLabelBarChart;
    private javax.swing.JLabel jLabelGeneSearch;
    private javax.swing.JLabel jLabelPCAplot;
    private javax.swing.JLabel jLabelVplot;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JTabbedPane jOutputTabs;
    private javax.swing.JPanel jPanelAnnotatedPeaks;
    private javax.swing.JPanel jPanelBarChart;
    private javax.swing.JPanel jPanelFooter;
    private javax.swing.JPanel jPanelGO;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JPanel jPanelPCA;
    private javax.swing.JPanel jPanelPathway;
    private javax.swing.JPanel jPanelSummary;
    private javax.swing.JPanel jPanelVolcanoPlot;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneAnnotatedPeaks;
    private javax.swing.JScrollPane jScrollPaneGoTable;
    private javax.swing.JScrollPane jScrollPanePathwayTable;
    private javax.swing.JTable jTableDifferentialPeaks;
    private javax.swing.JTable jTableGO;
    private javax.swing.JTable jTablePathway;
    private javax.swing.JTable jTableSummary;
    private javax.swing.JTextField jTextFieldSerachKey;
    // End of variables declaration//GEN-END:variables
}
