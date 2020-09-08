/*
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

import static java.awt.Color.*;
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
        ActionListener actionListener = this::btnCancelActionPerformed // setVisible(false);
        ;
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
    
    private boolean checkMatch(String strPattern
            , String strDatePattern
            , String strAmountPattern
            , String strChequePattern
            , String strTypePattern
            , String strTxt
            , String strType
            , String strTransactionLines) {
        boolean blnMatches = false;
        String strTL = parent.nvl(strTransactionLines).concat(" \n \n \n \n \n ");
        String[] strTLA = strTL.split("\n");
        String strDate = strTLA[2].trim();
        String strAmount = strTLA[3].trim();
        String strCheque = strTLA[4].trim();
        
        parent.lastPE = null;
        if (parent.matchTransaction(strPattern
                , strDatePattern
                , strAmountPattern
                , strChequePattern
                , strTypePattern
                , strTxt
                , strType
                , strDate
                , strAmount
                , strCheque
                , -1
                , false)) {
            lblMatchIndicator.setText("Pattern currently matches this transaction");
            lblMatchIndicator.setBackground(green);
            blnMatches = true;
        } else {
            lblMatchIndicator.setText("Pattern does not currently match this transaction");
            lblMatchIndicator.setBackground(orange);
            blnMatches = false;
        }
        if (parent.lastPE != null) {
            lblMatchIndicator.setText("Pattern has a syntax error");
            lblMatchIndicator.setBackground(red);
            lblMatchIndicator.setToolTipText("Error text: '" + parent.lastPE.getDescription() + "'");
        } else {
            lblMatchIndicator.setToolTipText("Click here to re-check");
        }
        return blnMatches;
    }

    /** Creates new form dlgEdit
     * @param parentForm
     * Always specify "this"
     * @param modal
     * Should this be modal or not?
     * @param strTransactionLines
     * New-line-delimited lines of transaction data
     * @param strNarration
     * Initial value of Narration field
     * @param strDate
     * Initial value of Date field
     * @param strAmount
     * Initial value of Amount field
     * @param strCheque
     * Initial value of Cheque field
     * @param strTransactionType
     * Initial value of Type (account) field
     * @param strReplacement
     * Inital value of Replacement field (replacement account)
     * @param strAnnotation
     * Initial value of text string to add to matching transactions
     * @param scbModel
     * SortedComboBox of accounts already listed for this pattern file
     * @param editingRow
     * If editing existing row data, specify row, otherwise -1
     **/
    @SuppressWarnings("unchecked")
    public dlgEdit(frmCustomiseQIF parentForm, boolean modal,
            String strTransactionLines,
            String strNarration,
            String strDate,
            String strAmount,
            String strCheque,
            String strTransactionType,
            String strReplacement,
            String strAnnotation,
            SortedComboBoxModel scbModel,
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
            btnSkip.setVisible(false);
            blnNewTransaction = false;
            if (strDate.isEmpty() && strAmount.isEmpty() && strCheque.isEmpty()) {
                cbMatchOther.setSelected(false);
                jpOtherFields.setVisible(false);
            } else {
                cbMatchOther.setSelected(true);
                jpOtherFields.setVisible(true);
            }
        } else {
            jtaTransaction.setText(strTransactionLines);
            blnNewTransaction = true;
            cbMatchOther.setSelected(false);
            jpOtherFields.setVisible(false);
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
        jcbAccount.setModel(scbModel);
        if (scbModel.getIndexOf(strReplacement) >= 0) {
            jcbAccount.setSelectedItem(strReplacement);
        }
        jtfAnnotation.setText(strAnnotation);
        if (blnNewTransaction) {
            if (!checkMatch(jtfNarration.getText()
                    , strDate
                    , strAmount
                    , strCheque
                    , strTransactionType
                    , strTransactionNarrationToMatch
                    , strTransactionTypeToMatch
                    , strTransactionLines)) {
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
        jpOtherFields = new javax.swing.JPanel();
        lblDate = new javax.swing.JLabel();
        jtfDate = new javax.swing.JTextField();
        lblAmount = new javax.swing.JLabel();
        jtfAmount = new javax.swing.JTextField();
        lblCheque = new javax.swing.JLabel();
        jtfCheque = new javax.swing.JTextField();
        jpReplacementAccount = new javax.swing.JPanel();
        lblReplacementAccount = new javax.swing.JLabel();
        jcbAccount = new javax.swing.JComboBox();
        lblNewAccount = new javax.swing.JLabel();
        jtfAccount = new javax.swing.JTextField();
        jtfAnnotation = new javax.swing.JTextField();
        lblAnnotation = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnOK = new javax.swing.JButton();
        btnSkip = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        jpType = new javax.swing.JPanel();
        jtfTransactionType = new javax.swing.JTextField();
        lblTransactionType = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Rule Editor");

        lblTransaction.setLabelFor(jspTransaction);
        lblTransaction.setText("Unmatched Record:");

        jtaTransaction.setEditable(false);
        jtaTransaction.setColumns(20);
        jtaTransaction.setRows(5);
        jtaTransaction.setMinimumSize(new java.awt.Dimension(0, 300));
        jspTransaction.setViewportView(jtaTransaction);

        lblMatchIndicator.setBackground(java.awt.SystemColor.info);
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
                    .addComponent(jspTransaction)
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
                .addComponent(jspTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblPattern.setLabelFor(jtfNarration);
        lblPattern.setText("Pattern for matching the description / narration*");
        lblPattern.setToolTipText("<html>To also match on transaction date and/or amount and/or cheque number,<br />\ncheck the <b>Also <u>m</u>atch other fields</b> option.<br />\nLeave any of these optional text fields empty if you don't want to match <br />on them; they will be ignored.");

        jtfNarration.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });

        cbMatchOther.setMnemonic('m');
        cbMatchOther.setText("Also match other fields");
        cbMatchOther.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });
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
                    .addComponent(jtfNarration)
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
                .addComponent(jtfNarration, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblDate.setText("Date");

        jtfDate.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });

        lblAmount.setText("Amount");

        jtfAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });

        lblCheque.setText("Cheque #");

        jtfCheque.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });

        javax.swing.GroupLayout jpOtherFieldsLayout = new javax.swing.GroupLayout(jpOtherFields);
        jpOtherFields.setLayout(jpOtherFieldsLayout);
        jpOtherFieldsLayout.setHorizontalGroup(
            jpOtherFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOtherFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDate, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfAmount)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCheque, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfCheque)
                .addContainerGap())
        );
        jpOtherFieldsLayout.setVerticalGroup(
            jpOtherFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpOtherFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jtfDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblDate)
                .addComponent(lblAmount)
                .addComponent(jtfAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblCheque)
                .addComponent(jtfCheque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblReplacementAccount.setText("Replacement account name (to match a GnuCash account)");

        jcbAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbAccountActionPerformed(evt);
            }
        });

        lblNewAccount.setText("Type a new account here if not in the list:");

        lblAnnotation.setText("Annotation to add to the narration (e.g. description of the goods)");

        btnOK.setMnemonic('O');
        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
            }
        });

        btnSkip.setMnemonic('S');
        btnSkip.setText("Skip");
        btnSkip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSkipActionPerformed(evt);
            }
        });

        btnCancel.setMnemonic('C');
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSkip)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(btnOK)
                .addComponent(btnCancel)
                .addComponent(btnSkip))
        );

        javax.swing.GroupLayout jpReplacementAccountLayout = new javax.swing.GroupLayout(jpReplacementAccount);
        jpReplacementAccount.setLayout(jpReplacementAccountLayout);
        jpReplacementAccountLayout.setHorizontalGroup(
            jpReplacementAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpReplacementAccountLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpReplacementAccountLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jcbAccount, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblReplacementAccount, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNewAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfAccount)
                    .addComponent(lblAnnotation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jtfAnnotation))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblAnnotation)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfAnnotation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtfTransactionType.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jtfNarrationFocusLost(evt);
            }
        });

        lblTransactionType.setText("Pattern for matching the transaction type (usually leave as default)");

        javax.swing.GroupLayout jpTypeLayout = new javax.swing.GroupLayout(jpType);
        jpType.setLayout(jpTypeLayout);
        jpTypeLayout.setHorizontalGroup(
            jpTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpTypeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jpTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtfTransactionType)
                    .addComponent(lblTransactionType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jpTypeLayout.setVerticalGroup(
            jpTypeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTypeLayout.createSequentialGroup()
                .addGap(0, 6, Short.MAX_VALUE)
                .addComponent(lblTransactionType)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtfTransactionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpEditLayout = new javax.swing.GroupLayout(jpEdit);
        jpEdit.setLayout(jpEditLayout);
        jpEditLayout.setHorizontalGroup(
            jpEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpType, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpOtherFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpNarration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpReplacementAccount, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpEditLayout.setVerticalGroup(
            jpEditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpEditLayout.createSequentialGroup()
                .addComponent(jpTransaction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpNarration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpOtherFields, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jpReplacementAccount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        if (blnCanClose) {
            if (blnNewTransaction) {
                parent.tableModelInProgress.addRow(new Object[]{parent.tableModelInProgress.getRowCount()+1
                        , jtfNarration.getText()
                        , jtfDate.getText()
                        , jtfAmount.getText()
                        , jtfCheque.getText()
                        , jtfTransactionType.getText()
                        , jtfAccount.getText()
                        , jtfAnnotation.getText()});
            } else {
                parent.replaceRow(rowBeingEdited
                        , jtfNarration.getText()
                        , jtfDate.getText()
                        , jtfAmount.getText()
                        , jtfCheque.getText()
                        , jtfTransactionType.getText()
                        , jtfAccount.getText()
                        , jtfAnnotation.getText());
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
            if (checkMatch(jtfNarration.getText()
                    , jtfDate.getText()
                    , jtfAmount.getText()
                    , jtfCheque.getText()
                    , jtfTransactionType.getText()
                    , strTransactionNarrationToMatch
                    , strTransactionTypeToMatch
                    , jtaTransaction.getText())) {
                btnOK.setEnabled(true);
            } else {
                btnOK.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jtfNarrationKeyTyped

    private void lblMatchIndicatorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMatchIndicatorMouseClicked
        if (checkMatch(jtfNarration.getText(), jtfDate.getText(), jtfAmount.getText(), jtfCheque.getText(), jtfTransactionType.getText(), strTransactionNarrationToMatch, strTransactionTypeToMatch, jtaTransaction.getText())) {
            btnOK.setEnabled(true);
        } else {
            btnOK.setEnabled(false);
        }
    }//GEN-LAST:event_lblMatchIndicatorMouseClicked

    private void jtfNarrationFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jtfNarrationFocusLost
        if (blnNewTransaction) {
            if (checkMatch(jtfNarration.getText(), jtfDate.getText(), jtfAmount.getText(), jtfCheque.getText(), jtfTransactionType.getText(), strTransactionNarrationToMatch, strTransactionTypeToMatch, jtaTransaction.getText())) {
                btnOK.setEnabled(true);
            } else {
                btnOK.setEnabled(false);
            }
        }
    }//GEN-LAST:event_jtfNarrationFocusLost

    private void cbMatchOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbMatchOtherActionPerformed
        // Note that this event fires before the state changes, so we reverse te logic:
        if (!cbMatchOther.isSelected()) {
            jtfDate.setText("");
            jtfAmount.setText("");
            jtfCheque.setText("");
            jpOtherFields.setVisible(false);
        } else {
            jtfDate.setText(strDt);
            jtfAmount.setText(strAmt);
            jtfCheque.setText(strChq);
            jpOtherFields.setVisible(true);
        }
    }//GEN-LAST:event_cbMatchOtherActionPerformed

    private void btnSkipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSkipActionPerformed
        parent.blnSkipModalDialog = true;
        parent.blnCancelModalDialog = false;
        this.dispose();
    }//GEN-LAST:event_btnSkipActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnOK;
    private javax.swing.JButton btnSkip;
    private javax.swing.JCheckBox cbMatchOther;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JComboBox jcbAccount;
    private javax.swing.JPanel jpEdit;
    private javax.swing.JPanel jpNarration;
    private javax.swing.JPanel jpOtherFields;
    private javax.swing.JPanel jpReplacementAccount;
    private javax.swing.JPanel jpTransaction;
    private javax.swing.JPanel jpType;
    private javax.swing.JScrollPane jspTransaction;
    private javax.swing.JTextArea jtaTransaction;
    private javax.swing.JTextField jtfAccount;
    private javax.swing.JTextField jtfAmount;
    private javax.swing.JTextField jtfAnnotation;
    private javax.swing.JTextField jtfCheque;
    private javax.swing.JTextField jtfDate;
    private javax.swing.JTextField jtfNarration;
    private javax.swing.JTextField jtfTransactionType;
    private javax.swing.JLabel lblAmount;
    private javax.swing.JLabel lblAnnotation;
    private javax.swing.JLabel lblCheque;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblMatchIndicator;
    private javax.swing.JLabel lblNewAccount;
    private javax.swing.JLabel lblPattern;
    private javax.swing.JLabel lblReplacementAccount;
    private javax.swing.JLabel lblTransaction;
    private javax.swing.JLabel lblTransactionType;
    // End of variables declaration//GEN-END:variables

}
