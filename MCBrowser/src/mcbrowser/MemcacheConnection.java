package mcbrowser;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 *
 * @author Tom Russell
 */
public class MemcacheConnection {

    private String ip;
    private int port;
    public Socket sock = null;
    private PrintWriter out = null;
    private BufferedReader in = null;


    public MemcacheConnection(String ip, String port) {
        this.ip = ip;
        try {
            this.port = Integer.parseInt(port);
        } catch (NumberFormatException e) {
            //not a valid integer or null
        }
    }
    
    public boolean isAlive() {
        return sock.isConnected();
    }

    public boolean connectToMemcacheServer() {
        try {
            sock = new Socket(this.ip, this.port);
            out = new PrintWriter(sock.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Could not comunicate with host");
            return false;
        } catch (IOException e) {
            System.err.println("Could not open I/O");
            return false;
        }
        return true;
    }

    public void closeConnection() throws IOException {
       System.out.println("Closing connection..");
        out.close();
       in.close();
       sock.close();
    }

    public ArrayList getMemcacheKeys() throws IOException {
        //get slab ID's
        out.println("stats items");
        String line;
        ArrayList<String> slabIds = new ArrayList();
        while ((line = in.readLine()) != null) {
            if (line.equals("END")) break;
            String[] parts = line.split(":");
            if (parts.length > 1) {
                if (!slabIds.contains(parts[1])) {
                    slabIds.add(parts[1]);
                }
            }
        }
        //convert those into keys
        ArrayList<String> memcacheKeys = new ArrayList();
        for (String id : slabIds) {
            out.println("stats cachedump " + id + " 100");
            while ((line = in.readLine()) != null) {
                if (line.equals("END")) break;
                String[] parts = line.split(" ");
                if (parts.length > 1) {
                    memcacheKeys.add(parts[1]);
                }
            }
        }
        Collections.sort(memcacheKeys);
        return memcacheKeys;
    }

    //XXX this method only returns the 'text-equivalent' of thea ctual byte
    //data.
    public String getDataForKey(String key) throws IOException {
        out.println("get " + key);
        String line;
        String output = "";
        while ((line = in.readLine()) != null) {
            if (line.equals("END")) break;
            output = output + line + "\r\n";
        }
        return output;
    }
}
