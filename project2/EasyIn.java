
import java.io.*;
import java.util.*;

class EasyIn {
    static InputStreamReader is = new InputStreamReader( System.in );
    static BufferedReader br = new BufferedReader( is );
    StringTokenizer st;

    StringTokenizer getToken() throws IOException {
       String s = br.readLine();
       return new StringTokenizer(s);
    }

    boolean readBoolean() {
       try {
          st = getToken();
          return new Boolean(st.nextToken()).booleanValue();
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readBoolean");
          return false;
       }
    }

    byte readByte() {
       try {
         st = getToken();
         return Byte.parseByte(st.nextToken());
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readByte");
          return 0;
       }
    }

    short readShort() {
       try {
         st = getToken();
         return Short.parseShort(st.nextToken());
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readShort");
          return 0;
       }
    }

    int readInt() {
       try {
         st = getToken();
         return Integer.parseInt(st.nextToken());
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readInt");
          return -1;
       }
    }

    long readLong() {
       try {
         st = getToken();
         return Long.parseLong(st.nextToken());
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readLong");
          return -1L;
       }
    }

    float readFloat() {
       try {
         st = getToken();
         return new Float(st.nextToken()).floatValue();
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readFloat");
          return 0.0F;
       }
    }

    double readDouble() {
       try {
         st = getToken();
         return new Double(st.nextToken()).doubleValue();
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readDouble");
          return 0.0;
       }
    }

    char readChar() {
       try {
         String s = br.readLine();
         return s.charAt(0);  
       } catch (IOException ioe) {
          System.err.println("IO Exception in EasyIn.readChar");
          return 0;
       }
    }

    String readString() {
       try {
         return br.readLine(); 
       } catch (IOException ioe) {
          System.err.println("IO shortException in EasyIn.readString");
          return "";
       }
    }

}

