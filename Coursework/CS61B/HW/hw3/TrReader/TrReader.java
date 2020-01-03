import java.io.Reader;
import java.io.IOException;

/** Translating Reader: a stream that is a translation of an
 *  existing reader.
 *
 *  Daniel Martin
 */
public class TrReader extends Reader {

    public Reader str_;
    public String from_;
    public String to_;
    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     *  in STR unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String from, String to) {
        str_ = str;
        from_ = from;
        to_ = to;
    }

    /** Reads a single character. */
    public char read(char a) throws IOException{
        int x =  from_.length();
        for(int i = 0; i < x; i++) {
            if (a == from_.charAt(i)) {
                return to_.charAt(i);
            }
        }
        return a;
    }

    /** Reads characters into a portion of an array. */
    public int read(char[] cbuf, int off, int len) throws IOException{
        int var = str_.read(cbuf, off, len);
        for (int i = off; i < off + var; i++) {
            cbuf[i] = read(cbuf[i]);
        }
        return var;
   }

   /** Closes the stream and releases any system resources associated with it. **/
   public void close(){
       try{
           str_.close();
       } catch (IOException e) {
           System.out.println("IOException Error");
           //throw e;
           return;
       } finally {
           System.out.println("Finally Block");
       }
   }

    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!
}


