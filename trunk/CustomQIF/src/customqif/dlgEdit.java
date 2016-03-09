/*
 * $Id: dlgEdit.java 45 2014-11-17 09:53:47Z eldon_r $
 *
 * dlgEdit.java
 *
 * Created on 13/11/2011, 4:49:40 PM
 *
 * This dialog is for editing of both new and existing rules.
 * If strTransactionLines is empty (note: null not handled) assumed existing.
 * Otherwise, assumed edit of new rule, and editingRow value is ignored.
 * Upon return (assuming it is called modally) blnCancelModalDialog will show
 * whether or not the edit was OK'd (submitted) or canceled. (very cludgy, FIXME)
 */

package customqif;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Edgeberg <eldon_r@users.sf.net>
 */
public class dlgEdit extends javax.swing.JDialog {

    // The following override code was created with assistance from:
    // https://forums.netbeans.org/topic4136.html and (it refers to this):
    // http://www.javaworld.com/javaworld/javatips/javatip72/EscapeDialog.java
    // (URLs valid as at 26/10/2014)
    @Override
    protected JRootPane createRootPane() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                // setVisible(false);
                btnCancelActionPerformed(actionEvent);
            }
        };
        JRootPane rootPane = new JRootPane();
        KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
        rootPane.registerKeyboardAction(actionListener, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
        return rootPane; 
        // return super.createRootPane();
    }

    frmCustomiseQIF parent;
    int rowBeingEdited = -1;
    String strTransactionNarrationToMatch = "";
    String strTransactionTypeToMatch;
    boolean blnNewTransaction = false;
    String strDt;
    String strAmt;
    String strChq;
    
    private boolean checkMatch(String strPattern, String strTypePattern, String strTxt, String strType, String strTransactionLines) {
        boolean blnMatches = false;
        String strTL = parent.nvl(strTransactionLines).concat(" \n \n \n \n \n ");
        String[] strTLA = strTL.split("\n");
        String strDate = strTLA[2].trim();
        String strAmount = strTLA[3].trim();
        String strCheque = strTLA[4].trim();
        try {
            if (parent.matchTransaction(
                    strPattern,
                    strTypePattern,
                    strTxt,
                    strType,
                    strDate,
                    strAmount,
                    strCheque,
                    false)) {
                lblMatchIndicator.setText("Pattern currently matches this transaction");
                lblMatchIndicator.setBackground(Color.green);
                blnMatches = true;
            } else {
                lblMatchIndicator.setText("Pattern does not currently match this transaction");
                lblMatchIndicator.setBackground(Color.orange);
                blnMatches = false;
            }
            lblMatchIndicator.setToolTipText("Click here to re-check");
        } catch (java.util.regex.PatternSyntaxException pe1) {
            lblMatchIndicator.setText("Pattern has a syntax error");
            lblMatchIndicator.setBackground(Color.red);
            lblMatchIndicator.setToolTipText("Error text: '" + pe1.getDescription() + "'");
        }
        return blnMatches;
    }

    /** Creates new form dlgEdit */
    public dlgEdit(frmCustomiseQIF parentForm, boolean modal,
            String strTransactionLines,
            String strNarration,
            String strTransactionType,
            String strReplacement,
            String strAnnotation,
            SortedComboBoxModel model,
            int editingRow) {
        super(parentForm, modal);
        initComponents();
        parent=parentForm;
        rowBeingEdited=editingRow;
        String[] aryTL = strTransactionLines.concat(" \n \n \n \n \n \n ").split("\n");
        strDt = aryTL[2].trim();
        strAmt = aryTL[3].trim();
        strChq = aryTL[4].trim();
        parent.blnCancelModalDialog = true;
        if (strTransactionLines.isEmpty()) {
            jpTransaction.setVisible(false);
            cbMatchOther.setVisible(false);
            setSize(getWidth(), getHeight() - jpTransaction.getHeight());
            blnNewTransaction = false;
        } else {
            jtaTransaction.setText(strTransactionLines);
            blnNewTransaction = true;
        }
        String strNarrPattern = strNarration;
        if (blnNewTransaction) {
            // Escape the narration text for use as a regexp, but only if this is a new transaction, otherwise it's already been done:
            strNarrPattern = java.util.regex.Matcher.quoteReplacement(strNarrPattern);
            strNarrPattern = strNarrPattern.replaceAll("[(]", "[(]").replaceAll("[)]", "[)]").replaceAll("[*]","[*]").replaceAll("[.]", "[.]");
        }
        jtfNarration.setText(strNarrPattern);
        strTransactionNarrationToMatch=strNarration;
        strTransactionTypeToMatch=strTransactionType;
        jtfTransactionType.setText(strTransactionType);
        jtfAccount.setText(strReplacement);
        jcbAccount.setModel(model);
        if (model.getIndexOf(strReplacement) >= 0) {
            jcbAccount.setSelectedItem(strReplacement);
        }
        jtfAnnotation.setText(strAnnotation);
        if (blnNewTransaction) {
            if (!checkMatch(jtfNarration.getText(), strTransactionType, strTransactionNarrationToMatch, strTransactionTypeToMatch, strTransactionLines)) {
                btnOK.setEnabled(false);
            }
        }
        btnOK.getRootPane().setDefaultButton(btnOK);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpEdit = new javax.swing.JPanel();
        jpTransaction = new javax.swing.JPanel();
        lblTransaction = new javax.swing.JLabel();
        jspTransaction = new javax.swing.JScrollPane();
        jtaTransaction = new javax.swing.JTextArea();
        lblMatchIndicator = new javax.swing.JLabel();
        jpNarration = new javax.swing.JPanel();
        lblPattern = new javax.swing.JLabel();
        jtfNarration = new javax.swing.JTextField();
        cbMatchOther = new javax.swing.JCheckBox();
        jpTransactionType = new javax.swing.JPanel();
        lblTransactionType = new javax.swing.JLabel();
        jtfTransactionType = new javax.swing.JTextField();
        jpReplacementAccount = new javax.swing.JPanel();
        lblReplacementAccount = new javax.swing.JLabel();
        jcbAccount = new javax.swing.JComboBox();
        lblNewAccount = new javax.swing.JLabel();
        jtfAccount = new javax.swing.JTextField();
        jtfAnnotation = new javax.swing.JTextField();
        lblAnnotation = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rule Editor");
        setResizable(false);

        lblTransaction.setLabelFor(jspTransaction);
        lblTransaction.setText("Unmatched Record:");

        jtaTransaction.setEditable(false);
        jtaTransaction.setColumns(20);
        jtaTransaction.setRows(5);
        jspTransaction.setViewportView(jtaTransaction);

        lblMatchIndicator.setBackground(java.awt.SystemColor.info);
        lblMatchIndicator.setForeground(java.awt.SystemColor.infoText);
        lblMatchIndicator.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblMatchIndicator.setText("Current pattern does not match");
        lblMatchIndicator.setOpaque(true);
        lblMatchIndicator.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMatchIndicatorMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jpTransactionLayout = new javax.swing.GroupLayout(jpTransaction);
        jpTransaction.setLayout(jpTransactionLayout);
        jpTransactionLayout.setHorizontalGroup(
            jpTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTransactionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jspTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(jpTransactionLayout.createSequentialGroup()
                        .addComponent(lblTransaction)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMatchIndicator)))
                .addContainerGap())
        );
        jpTransactionLayout.setVerticalGroup(
            jpTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTransactionLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTransactionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTransaction)
                    .addComponent(lblMatchIndicator))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
        );

        lblPattern.setLabelFor(jtfNarration);
        lblPattern.setText("Pattern for matching the description / narration*");
        lblPattern.setToolTipText("<html>Optionally, to also match on transaction date and/or amount and/or cheque number, add your<br />\nmatch text at the end of the narration pattern separated by vertical bar (|) (i.e., in that order).<br />\nLeave any of these optional text fields empty if you don't want to match on them; they will be ignored.");

        jtfNarration.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });
        jtfNarration.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jtfNarrationPropertyChange(evt);
            }
        });

        cbMatchOther.setMnemonic('m');
        cbMatchOther.setText("Also match other fields");
        cbMatchOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbMatchOtherActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpNarrationLayout = new javax.swing.GroupLayout(jpNarration);
        jpNarration.setLayout(jpNarrationLayout);
        jpNarrationLayout.setHorizontalGroup(
            jpNarrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpNarrationLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpNarrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfNarration, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addGroup(jpNarrationLayout.createSequentialGroup()
                        .addComponent(lblPattern, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cbMatchOther, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jpNarrationLayout.setVerticalGroup(
            jpNarrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpNarrationLayout.createSequentialGroup()
                .addGroup(jpNarrationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPattern)
                    .addComponent(cbMatchOther))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblTransactionType.setText("Pattern for matching the transaction type (usually leave as default)");

        javax.swing.GroupLayout jpTransactionTypeLayout = new javax.swing.GroupLayout(jpTransactionType);
        jpTransactionType.setLayout(jpTransactionTypeLayout);
        jpTransactionTypeLayout.setHorizontalGroup(
            jpTransactionTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTransactionTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTransactionTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTransactionType, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addComponent(jtfTransactionType))
                .addContainerGap())
        );
        jpTransactionTypeLayout.setVerticalGroup(
            jpTransactionTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTransactionTypeLayout.createSequentialGroup()
                .addComponent(lblTransactionType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfTransactionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblReplacementAccount.setText("Replacement account name (to match a GnuCash account)");

        jcbAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAccountActionPerformed(evt);
            }
        });

        lblNewAccount.setText("Type a new account here if not in the list:");

        lblAnnotation.setText("Annotation to add to the narration (e.g. description of the goods)");

        javax.swing.GroupLayout jpReplacementAccountLayout = new javax.swing.GroupLayout(jpReplacementAccount);
        jpReplacementAccount.setLayout(jpReplacementAccountLayout);
        jpReplacementAccountLayout.setHorizontalGroup(
            jpReplacementAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpReplacementAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpReplacementAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbAccount, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblReplacementAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 634, Short.MAX_VALUE)
                    .addComponent(lblNewAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfAccount)
                    .addComponent(lblAnnotation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfAnnotation))
                .addContainerGap())
        );
        jpReplacementAccountLayout.setVerticalGroup(
            jpReplacementAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpReplacementAccountLayout.createSequentialGroup()
                .addComponent(lblReplacementAccount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jcbAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNewAccount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblAnnotation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfAnnotation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnOK.setMnemonic('O');
        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('C');
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpEditLayout = new javax.swing.GroupLayout(jpEdit);
        jpEdit.setLayout(jpEditLayout);
        jpEditLayout.setHorizontalGroup(
            jpEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpNarration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpTransactionType, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpReplacementAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEditLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(183, 183, 183))
        );
        jpEditLayout.setVerticalGroup(
            jpEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEditLayout.createSequentialGroup()
                .addComponent(jpTransaction, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpTransactionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpReplacementAccount, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        parent.blnCancelModalDialog = true;
        this.dispose();
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        boolean blnCanClose = true;
        if (!blnNewTransaction) {
            
        }
        if (blnCanClose) {
            if (blnNewTransaction) {
                parent.tableModelInProgress.addRow(new Object[]{parent.tableModelInProgress.getRowCount()+1, jtfNarration.getText(), jtfTransactionType.getText(), jtfAccount.getText(), jtfAnnotation.getText()});
            } else {
                parent.replaceRow(rowBeingEdited, jtfNarration.getText(), jtfTransactionType.getText(), jtfAccount.getText(), jtfAnnotation.getText());
            }
            parent.blnCancelModalDialog = false;
            this.dispose();
        }
    }//GEN-LAST:event_btnOKActionPerformed

    private void jcbAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbAccountActionPerformed
        if (evt.getActionCommand().equals("comboBoxChanged")) {
            jtfAccount.setText(jcbAccount.getSelectedItem().toString());
        }
    }//GEN-LAST:event_jcbAccountActionPerformed

    private void jtfNarrationKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jtfNarrationKeyTyped
        //checkMatch(strTransactionNarrationToMatch, jtfNarration.getText());
        if (blnNewTransaction) {
            if (checkMatch(jtfNarration.getText(), jtfTransactionType.getText(), strTransactionNarrationToMatch, strTransactionTypeToMatch, jtaTransaction.getText())) {
                btnOK.setEnabled(true);
            } else {
                btnOK.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jtfNarrationKeyTyped

    private void jtfNarrationPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jtfNarrationPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfNarrationPropertyChange

    private void lblMatchIndicatorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMatchIndicatorMouseClicked
        if (checkMatch(jtfNarration.getText(), jtfTransactionType.getText(), strTransactionNarrationToMatch, strTransactionTypeToMatch, jtaTransaction.getText())) {
            btnOK.setEnabled(true);
        } else {
            btnOK.setEnabled(false);
        }
    }//GEN-LAST:event_lblMatchIndicatorMouseClicked

    private void jtfNarrationFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNarrationFocusLost
        if (blnNewTransaction) {
            if (checkMatch(jtfNarration.getText(), jtfTransactionType.getText(), strTransactionNarrationToMatch, strTransactionTypeToMatch, jtaTransaction.getText())) {
                btnOK.setEnabled(true);
            } else {
                btnOK.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jtfNarrationFocusLost

    private void cbMatchOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMatchOtherActionPerformed
        // Note that this event fires before the state changes, so we reverse te logic:
        if (cbMatchOther.isSelected()) {
            jtfNarration.setText(jtfNarration.getText().replaceAll("[|].*$", "").concat("|" + strDt + "|" + strAmt + "|" + strChq).replaceAll("[|]*$", ""));
        } else {
            jtfNarration.setText(jtfNarration.getText().replaceAll("[|].*$", ""));
        }
    }//GEN-LAST:event_cbMatchOtherActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JCheckBox cbMatchOther;
    private javax.swing.JComboBox jcbAccount;
    private javax.swing.JPanel jpEdit;
    private javax.swing.JPanel jpNarration;
    private javax.swing.JPanel jpReplacementAccount;
    private javax.swing.JPanel jpTransaction;
    private javax.swing.JPanel jpTransactionType;
    private javax.swing.JScrollPane jspTransaction;
    private javax.swing.JTextArea jtaTransaction;
    private javax.swing.JTextField jtfAccount;
    private javax.swing.JTextField jtfAnnotation;
    private javax.swing.JTextField jtfNarration;
    private javax.swing.JTextField jtfTransactionType;
    private javax.swing.JLabel lblAnnotation;
    private javax.swing.JLabel lblMatchIndicator;
    private javax.swing.JLabel lblNewAccount;
    private javax.swing.JLabel lblPattern;
    private javax.swing.JLabel lblReplacementAccount;
    private javax.swing.JLabel lblTransaction;
    private javax.swing.JLabel lblTransactionType;
    // End of variables declaration//GEN-END:variables

}
