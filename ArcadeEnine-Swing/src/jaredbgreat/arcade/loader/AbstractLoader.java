
package jaredbgreat.arcade.loader;

import jaredbgreat.arcade.util.GameLogger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author jared
 */
public abstract class AbstractLoader {
    protected String loc;
    protected String infoLoc; 
    // Line should be delimited with spaces, but including other stuff
    protected static final String delimeters = " \t\n\r\f\"\';";
    // Block delimeters, showing all included images are frames of one graphic
    protected static final String startBlock = "{";
    protected static final String endBlock   = "}";
    // Lines starting with this will be treated as comments
    protected static final String comment    = "#";
    protected static final String empty      = "";
    
    protected InputStream textStream;
    protected BufferedReader inText;
    
    protected String nextLine;
    protected StringTokenizer tokens;
    protected boolean inBlock;    
        
    protected String name;
    protected String address1;
    protected String address2;
    protected String token1;
    protected String token2;
    protected String token3; 
    
    protected List<String> list;
    
    
    protected AbstractLoader() {        
        inBlock = false;     
    }
    
    
    
    /**
     * Opens the GraphicsData.txt file for reading.
     */
    protected void openInfo() {        
        GameLogger.mainLogger.logInfo("Running OpenInfo() for ImageLoader");
        textStream = getClass().getResourceAsStream(infoLoc);
        if(textStream != null) {
            GameLogger.mainLogger.logInfo("Trying to open file " + infoLoc);
            inText = new BufferedReader(new InputStreamReader(textStream));
        } else {
            GameLogger.mainLogger.logError("ERROR! Could not get stream for " 
                    + infoLoc);            
        } if(inText != null) try {
            parseInfo(inText);
            inText.close();
        } catch (IOException ex) {
            GameLogger.mainLogger.logException(ex);
            System.exit(1);
        }
     }
    
    
    /**
     * Reads and parses the GraphicsData.txt file.
     * 
     * @param in
     * @throws IOException 
     */
    protected void parseInfo(BufferedReader in) throws IOException {
        GameLogger.mainLogger.logInfo("Reading file " + infoLoc);
        while((nextLine = in.readLine()) != null) {
            GameLogger.mainLogger.logInfo("Reading line \"" + nextLine + "\"");
            if(nextLine.startsWith(comment) || nextLine.isEmpty()) continue;
            tokens = new StringTokenizer(nextLine, delimeters);
            token1 = getToken();
            token2 = getToken();
            token3 = getToken();
            if(inBlock) {
                if(token1.equals(endBlock)) {
                    inBlock = false;
                    makeResource();                    
                } else {
                    address2 = token1;
                    list.add(loc + address1 + address2);
                }
            } else {
                if(token1.equals(startBlock)) {
                    inBlock = true;
                } else {
                    list = new ArrayList<>();
                    name = token1;
                    if(token2.equals(startBlock)) {
                        address1 = empty;
                        inBlock = true;
                    } else {
                        address1 = token2;
                    }
                    // This setup allows images to be in sub-directories, 
                    // the names of which is represented by address1.
                    if(token3.equals(startBlock)) {
                        inBlock = true;
                    }
                    if(!inBlock) {
                        address2 = token3;
                        list.add(loc + address1 + address2);
                        makeResource();
                    }
                }
            }
        }
    }
    
    
    protected String getToken() {
        if(tokens.hasMoreTokens()) {
            return tokens.nextToken();
        } else {
            return empty;
        }
    }
    
    
    protected abstract void makeResource();
}
