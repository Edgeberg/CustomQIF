/*
 * Main.java
 * Created on 19 March 2007, 23:10
 *
 * This program was created to store and use regular expression strings for
 * matching transaction in Quicken format transaction import files (.QIF
 * extension) to accounts in an accounting package (GnuCash) that I wanted to
 * import these transactions into.
 *
 * This process greatly simplifies the import process if the aim is to split all
 * transactions into meaningful categories (represented by different accounts)
 * but the source .QIF file is a simplified one from a single account at a bank.
 *
 * I also found, once I had switched banks, that my new bank's export files
 * have multi-line transaction narrations that are not gathered into a single
 * transaction in the file.  So I added logic to gather these narrations onto
 * the narration line of the transaction to which they belong.  There was one
 * tricky exception to the rule of "multi-line transaction narrations occur
 * sequentially after the parent transaction without being interrupted by other
 * transaction records" -- and that was for the mortgage interest transactions,
 * where so far one (1) transaction/narration group is interrupted by an
 * unrelated transaction that occurred on the same date.  So I also added logic
 * to recognise these types of transactions and to search for the other part
 * past other transactions if necessary.  I deemed it wouldn't accidentally (all
 * things being normal) come across the next such transaction & try to
 * incorporate it, since these only occur monthly.
 */

package customqif;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgeberg <eldon_r@users.sf.net>
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    public static String AppVersion = "(Unknown version)";
    public static String AppURL = "http://github.com/Edgeberg/CustomQIF.git";
    public static String AppName = "QIF Customiser Application";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Properties prop = new Properties();
        try {
            prop.load(Main.class.getResourceAsStream("/customqif/resources/CustomQIF.properties"));
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error while retrieving application properties from the jar file!", ex);
        }
        AppVersion = prop.getProperty("Application.revision", AppVersion);
        AppName = prop.getProperty("Application.name", AppName);
        AppURL = prop.getProperty("Application.url", AppURL);
        System.out.print("Version " + AppVersion + "\n");
        new frmCustomiseQIF().setVisible(true);
    }
    
}
