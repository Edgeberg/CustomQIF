/*
 * frmCustomiseQIF.java
 *
 * Created on 20 March 2007, 01:09
 */

package customqif;

import java.awt.Component;
import java.awt.FileDialog;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author  eldon_r
 */
public class frmCustomiseQIF extends javax.swing.JFrame {
    
    /** Creates new form frmCustomiseQIF */
    public frmCustomiseQIF() {
        initComponents();

        // Make the WindowListener our only way out of this app.:
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                doTheCloseThing();
            }            
        });

        loadGrid();
        loadState();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        stringTable = new javax.swing.JTable();
        btnAdd = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        btnUp = new javax.swing.JButton();
        btnDown = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        btnSave = new javax.swing.JButton();
        btnLoad = new javax.swing.JButton();
        btnExecute = new javax.swing.JButton();
        btnLearn = new javax.swing.JButton();
        btnInputFile = new javax.swing.JButton();
        ctlInputFile = new javax.swing.JTextField();
        btnOutputFile = new javax.swing.JButton();
        ctlOutputFile = new javax.swing.JTextField();
        mb = new javax.swing.JMenuBar();
        mFile = new javax.swing.JMenu();
        miOpen = new javax.swing.JMenuItem();
        miSave = new javax.swing.JMenuItem();
        miQuit = new javax.swing.JMenuItem();
        mOptions = new javax.swing.JMenu();
        miCombineNarrations = new javax.swing.JCheckBoxMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Customise QIF files downloaded from the bank");

        stringTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"MINTEREST (INCLUDES BONUS.*", "LINT", "LSavings Interest"},
                {"MPERIODICAL PAYMENT FROM MR ELDON ROSENBE BS DEPOSIT", "LDEP", "LClassic Account"}
            },
            new String [] {
                "Description Pattern", "Transaction Type Pattern", "Transaction Type Replacement"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(stringTable);

        btnAdd.setMnemonic('A');
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnEdit.setMnemonic('E');
        btnEdit.setText("Edit");

        btnRemove.setMnemonic('R');
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        btnUp.setMnemonic('U');
        btnUp.setText("Up");

        btnDown.setMnemonic('D');
        btnDown.setText("Down");

        btnSave.setMnemonic('S');
        btnSave.setText("Save");
        btnSave.setToolTipText("Save this setup");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveActionPerformed(evt);
            }
        });

        btnLoad.setMnemonic('L');
        btnLoad.setText("Load");
        btnLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOpenActionPerformed(evt);
            }
        });

        btnExecute.setMnemonic('x');
        btnExecute.setText("Execute");
        btnExecute.setToolTipText("Do the operation");
        btnExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExecuteActionPerformed(evt);
            }
        });

        btnLearn.setMnemonic('N');
        btnLearn.setText("Learn");
        btnLearn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLearnActionPerformed(evt);
            }
        });

        btnInputFile.setMnemonic('I');
        btnInputFile.setText("Input File");
        btnInputFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInputFileActionPerformed(evt);
            }
        });

        ctlInputFile.setText("/home/eldon_r/InputFile.QIF");

        btnOutputFile.setMnemonic('O');
        btnOutputFile.setText("Output File");
        btnOutputFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOutputFileActionPerformed(evt);
            }
        });

        ctlOutputFile.setText("/home/eldon_r/OutputFile.QIF");

        mFile.setMnemonic('F');
        mFile.setText("File");

        miOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        miOpen.setMnemonic('L');
        miOpen.setText("Load");
        miOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miOpenActionPerformed(evt);
            }
        });
        mFile.add(miOpen);

        miSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        miSave.setMnemonic('S');
        miSave.setText("Save");
        miSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miSaveActionPerformed(evt);
            }
        });
        mFile.add(miSave);

        miQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        miQuit.setMnemonic('Q');
        miQuit.setText("Quit");
        miQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                miQuitActionPerformed(evt);
            }
        });
        mFile.add(miQuit);

        mb.add(mFile);

        mOptions.setMnemonic('O');
        mOptions.setText("Options");

        miCombineNarrations.setSelected(true);
        miCombineNarrations.setText("Combine Narrations");
        mOptions.add(miCombineNarrations);

        mb.add(mOptions);

        setJMenuBar(mb);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false)
                            .add(btnOutputFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(btnInputFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(ctlOutputFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, ctlInputFile, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 558, Short.MAX_VALUE))))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                    .add(btnLoad, 0, 0, Short.MAX_VALUE)
                    .add(btnUp, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btnAdd, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btnEdit, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btnRemove, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jSeparator1)
                    .add(jSeparator2)
                    .add(btnDown, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btnSave, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btnExecute, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(btnLearn, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(btnInputFile)
                            .add(ctlInputFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(btnOutputFile)
                            .add(ctlOutputFile, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(btnAdd)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnEdit)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnRemove)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnUp)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnDown)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnSave)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnLoad)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 153, Short.MAX_VALUE)
                        .add(btnExecute)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnLearn)))
                .addContainerGap())
        );

        getAccessibleContext().setAccessibleName("Customise QIF");
        getAccessibleContext().setAccessibleDescription("Customise QIF files downloaded from the bank");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnOutputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOutputFileActionPerformed
        openFile(evt.getActionCommand());
    }//GEN-LAST:event_btnOutputFileActionPerformed

    private void btnInputFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInputFileActionPerformed
        openFile(evt.getActionCommand());
    }//GEN-LAST:event_btnInputFileActionPerformed

    private void btnLearnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLearnActionPerformed
        doTranslation(true);
    }//GEN-LAST:event_btnLearnActionPerformed

    private void miOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miOpenActionPerformed
        loadGrid();
    }//GEN-LAST:event_miOpenActionPerformed

    private void btnExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExecuteActionPerformed
        doTranslation(false);
    }//GEN-LAST:event_btnExecuteActionPerformed

    private void miSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miSaveActionPerformed
        saveGrid();
    }//GEN-LAST:event_miSaveActionPerformed

    private void miQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_miQuitActionPerformed
        doTheCloseThing();
    }//GEN-LAST:event_miQuitActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        if((stringTable.getRowCount() > 1) &&
                (stringTable.getSelectedRow() >= 0)) {
            DefaultTableModel tableModel = (DefaultTableModel) stringTable.getModel();
            tableModel.removeRow(stringTable.getSelectedRow());
            stringTable.setModel(tableModel);
        }
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        DefaultTableModel tableModel = (DefaultTableModel) stringTable.getModel();
        tableModel.addRow(new Object[] {"","",""});
        stringTable.setModel(tableModel);
    }//GEN-LAST:event_btnAddActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCustomiseQIF().setVisible(true);
            }
        });
    }

    public void handleException(Component window, String errorText, String dialogTitle) {
        //if (getOption("HitAuthor", false).equals("Yes")) {
        //    System.err.println("Author email not implemented...");
        //}
        //if (getOption("ErrorsToStdout", false).equalsIgnoreCase("Yes")) {
            System.out.print(dialogTitle + ": " + errorText);
        //}
        JOptionPane.showMessageDialog(window, errorText, dialogTitle, JOptionPane.ERROR_MESSAGE);
    }

    private void doTranslation(boolean learn) {
        boolean blnCancel = false;
        boolean blnMatch = false;
        String strLine = "";
        String strDesc = "";
        String strSearchDesc = "";
        String strTypeCode = "";
        String strReplaceTypeWith = "";
        int i = 0;
        DefaultTableModel tableModel = (DefaultTableModel) stringTable.getModel();
        int rows = tableModel.getRowCount();
        
        //status1.setText("Processing...");
        BufferedReader in = null;
        BufferedWriter out = null;
        try {
            in = new BufferedReader(new FileReader(ctlInputFile.getText()));
            try {
                if (!learn) out = new BufferedWriter(new FileWriter(ctlOutputFile.getText()));
                try {
                    while (((strLine = in.readLine()) != null) && !blnCancel) {
                        if (strLine.length() > 0) {
                            if (strLine.matches("M.*")) {
                                strDesc = strLine;
                                for (i=0; (!blnMatch) && (i<rows); i++) {
                                    strSearchDesc = tableModel.getValueAt(i,0).toString();
                                    strSearchDesc = strSearchDesc.replaceAll("[(]", "\\[\\(\\]");
                                    strSearchDesc = strSearchDesc.replaceAll("[)]", "\\[\\)\\]");
                                    strTypeCode = tableModel.getValueAt(i,1).toString();
                                    strTypeCode = strTypeCode.replaceAll("[(]", "\\[\\(\\]");
                                    strTypeCode = strTypeCode.replaceAll("[)]", "\\[\\)\\]");
                                    strReplaceTypeWith = tableModel.getValueAt(i,2).toString();
                                    if (strDesc.matches(strSearchDesc)) {
                                        blnMatch = true;
                                        //JOptionPane.showMessageDialog(this, "'" + strLine + "' matches '" + strSearchDesc + "'", "Eureka!", JOptionPane.INFORMATION_MESSAGE);
                                    }
                                }
                            } else if (strLine.matches("L.*")) {
                                if (blnMatch) {
                                    if (strLine.matches(strTypeCode)) {
                                        //JOptionPane.showMessageDialog(this, "And also '" + strLine + "' matches '" + strTypeCode + "'", "Eureka again!", JOptionPane.INFORMATION_MESSAGE);
                                        //JOptionPane.showMessageDialog(this, "So I'll replace it with '" + strReplaceTypeWith + "'", "My response to a match", JOptionPane.INFORMATION_MESSAGE);
                                        strLine = strReplaceTypeWith;
                                    }
                                } else if (learn){
                                    strSearchDesc = JOptionPane.showInputDialog(this, "Unmatched pair:\n" + strDesc + "\n" + strLine +
                                                    "\n\nPlease enter a pattern for matching the description:", strDesc);
                                    blnCancel = strSearchDesc == null;
                                    if (!blnCancel) {
                                        strTypeCode = JOptionPane.showInputDialog(this, "Unmatched pair:\n" + strDesc + "\n" + strLine +
                                                        "\n\nNow please enter a pattern for matching the account name:", strLine);
                                        blnCancel = strTypeCode == null;
                                        if (!blnCancel) {
                                            strReplaceTypeWith = JOptionPane.showInputDialog(this, "Unmatched pair:\n" + strDesc + "\n" + strLine +
                                                            "\n\nFinally, please enter a replacement string for the account name:", strLine);
                                            blnCancel = strReplaceTypeWith == null;
                                            if (!blnCancel) {
                                                tableModel.addRow(new Object [] {strSearchDesc, strTypeCode, strReplaceTypeWith});
                                                rows++;
                                            }
                                        }
                                    }
                                }
                                blnMatch = false;
                            }
                        }
                        if (!learn) out.write(strLine + "\n");
                    }
                    in.close();
                } catch (IOException exTransRead) {
                    handleException(this, "Error while reading " + ctlInputFile.getText() + ": " + exTransRead.toString(), "btnExecuteActionPerformed subroutine");
                }
                if (!learn) out.close();
            } catch (IOException exTransWrite) {
            }
        } catch (FileNotFoundException exFNF) {
            handleException(this, "Error while opening " + ctlInputFile.getText() + ": " + exFNF.toString(), "btnExecuteActionPerformed subroutine");
        }
    }

    private void loadGrid() {
        int r, rows;
        String strLine;
        String [] ary;
        
        // First see if the .CustomQIF directory exists and create it if not
        File myProgramDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".CustomQIF");
        if (!myProgramDir.mkdirs()) {
            File myStartupFile = new File(myProgramDir, "Patterns");
            if (myStartupFile.exists()) {
                // Get a copy of the table structure
                DefaultTableModel tableModel = (DefaultTableModel) stringTable.getModel();
                // Clear table first
                rows = tableModel.getRowCount();
                for (r=(rows - 1); r>=0; r--) {
                    tableModel.removeRow(r);
                    rows--;
                }
                try {
                    BufferedReader in = new BufferedReader(new FileReader(myStartupFile));
                     while ((strLine = in.readLine()) != null) {
                        ary = strLine.split("\t", 3);
                        tableModel.addRow(new Object[] {ary[0], ary[1], ary[2]});
                     }
                    in.close();
                    stringTable.setModel(tableModel);
                } catch (IOException ex) {
                    handleException(this, "Error while reading '" + myStartupFile.getAbsolutePath() + "': " + ex.toString(), "miOpenActionPerformed subroutine");
                }
            }
        }
    }

    private void saveGrid() {
        int r, c, rows, columns;
        boolean mkdirStatus;
        File myProgramDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".CustomQIF");
        if (!myProgramDir.exists()) myProgramDir.mkdirs();
        if (myProgramDir.exists()) {
            DefaultTableModel tableModel = (DefaultTableModel) stringTable.getModel();
            rows = tableModel.getRowCount();
            columns = tableModel.getColumnCount();
            File myStartupFile = new File(myProgramDir, "Patterns");
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(myStartupFile));
                for (r = 0 ; r < rows; r++) {
                    for (c = 0; c < columns; c++) {
                        out.write(tableModel.getValueAt(r, c).toString());
                        if (c < (columns - 1)) {
                            out.write("\t");
                        }
                    }
                    out.write("\n");
                }
                out.close();
            } catch (IOException ex) {
                handleException(this, "Error while writing '" + myStartupFile.getAbsolutePath() + "': " + ex.toString(), "miSaveActionPerformed subroutine");
            }
        } else {
            handleException(this, "Unable to create directory '" + myProgramDir.getAbsolutePath() + "'", "miSaveActionPerformed subroutine");
        }
    }

    private void doTheCloseThing() {
        int selection = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to quit?", "CustomiseQIF",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if( selection == JOptionPane.YES_OPTION ) {
            saveGrid();
            saveState();
            dispose();      // Closes the frame
            System.exit(0); // Terminates the application
        }
        // If the user chose "No" or "Cancel" we do nothing
    }

    private void saveState() {
        boolean mkdirStatus;
        File myProgramDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".CustomQIF");
        if (!myProgramDir.exists()) myProgramDir.mkdirs();
        if (myProgramDir.exists()) {
            File myStateFile = new File(myProgramDir, "State");
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(myStateFile));
                out.write(ctlInputFile.getText());
                out.newLine();
                out.write(ctlOutputFile.getText());
                out.newLine();
                if (miCombineNarrations.getState()) {
                    out.write("CombineNarrations=True");
                } else {
                    out.write("CombineNarrations=False");
                }
                out.close();
            } catch (IOException ex) {
                handleException(this, "Error while writing '" + myStateFile.getAbsolutePath() + "': " + ex.toString(), "miSaveActionPerformed subroutine");
            }
        } else {
            handleException(this, "Unable to create directory '" + myProgramDir.getAbsolutePath() + "'", "miSaveActionPerformed subroutine");
        }
    }

    private void loadState() {
        String strLine;
        
        // First see if the .CustomQIF directory exists and create it if not
        File myProgramDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + ".CustomQIF");
        if (!myProgramDir.mkdirs()) {
            File myStateFile = new File(myProgramDir, "State");
            if (myStateFile.exists()) {
                try {
                    BufferedReader in = new BufferedReader(new FileReader(myStateFile));
                     strLine = in.readLine();
                     if (strLine != null) {
                        ctlInputFile.setText(strLine);
                     }
                     strLine = in.readLine();
                     if (strLine != null) {
                        ctlOutputFile.setText(strLine);
                     }
                     strLine = in.readLine();
                     if (strLine != null) {
                         miCombineNarrations.setState(strLine.equals("CombineNarrations=True"));
                     }
                    in.close();
                } catch (IOException ex) {
                    handleException(this, "Error while reading '" + myStateFile.getAbsolutePath() + "': " + ex.toString(), "miOpenActionPerformed subroutine");
                }
            }
        }
    }

    private void openFile(String string) {
        FileDialog dlg = new FileDialog(this);
        dlg.setFile(ctlInputFile.getText().substring(
                ctlInputFile.getText().lastIndexOf(System.getProperty("file.separator"))+1));
        dlg.setDirectory(ctlInputFile.getText().substring(
                0,ctlInputFile.getText().lastIndexOf(System.getProperty("file.separator"))));
        if (string.matches(".*Input.*")) {
            dlg.setMode(FileDialog.LOAD);
        } else {
            dlg.setMode(FileDialog.SAVE);
        }
        dlg.setVisible(true);
        if (dlg.getFile() != null) {
            ctlInputFile.setText(dlg.getDirectory() + dlg.getFile());
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDown;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExecute;
    private javax.swing.JButton btnInputFile;
    private javax.swing.JButton btnLearn;
    private javax.swing.JButton btnLoad;
    private javax.swing.JButton btnOutputFile;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUp;
    private javax.swing.JTextField ctlInputFile;
    private javax.swing.JTextField ctlOutputFile;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JMenu mFile;
    private javax.swing.JMenu mOptions;
    private javax.swing.JMenuBar mb;
    private javax.swing.JCheckBoxMenuItem miCombineNarrations;
    private javax.swing.JMenuItem miOpen;
    private javax.swing.JMenuItem miQuit;
    private javax.swing.JMenuItem miSave;
    private javax.swing.JTable stringTable;
    // End of variables declaration//GEN-END:variables
    
}
