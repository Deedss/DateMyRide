package com.example.vroomrr;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

final public class ServerConnection {
    // URL information for Server Connections
    private final String user_agent = "";
     //todo Add official master_server
    private final String master_server = "grolink.nl/Vroomrr/";

    // Cookies handler
    private final CookieManager cookieManager;

    /**
     * Constructor
     */
    public ServerConnection(){
        cookieManager = new CookieManager();
        CookieHandler.setDefault(cookieManager);
    }

    /**
     * GET function for grabbing data from the flask webserver.
     * @param file the file/request to send
     * @param context the context
     * @return returns the string value of result
     */
    private String httpsGET(String file, SSLContext context){
        StringBuffer data = new StringBuffer();

        // Setup SSL socket and TrustManager

        try{
//            SharedPreferences SP = context.getDefaultSSLParameters()
//            String cookie = SP.getString("Cookie", "");

            // Setup HTTPS connection
            URL url = new URL(master_server + file);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String result = "";
        
        /// TODO: 11-12-20 Fill in return 
        return result;

    }

    /**
     * This function serves to utilize the certificate defined by the project group or the HTTPS
     * connection.
     * @param context SSLContext
     * @return Returns a SSLContext
     * @throws Exception Can throw exception such as Certificate and IOException.
     */
    private SSLContext setupCertificate(SSLContext context) throws Exception {
        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        // From https://www.washington.edu/itconnect/security/ca/load-der.crt
        InputStream caInput = new BufferedInputStream(new FileInputStream("load-der.crt"));
        Certificate ca;
        try {
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } finally {
            caInput.close();
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", ca);

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
        tmf.init(keyStore);

        // Create an SSLContext that uses our TrustManager
        context = SSLContext.getInstance("TLS");
        context.init(null, tmf.getTrustManagers(), null);

        return context;
    }

    /**
     * Returns user information from the server.
     * @param userId, the userId/username
     * @return User object
     */
    public User getUser(String userId){
        User user = null;

        return user;
    }

    /**
     * returns an arraylist with all chats specific to the user.
     * @param user The User for which to import all chats.
     * @return Returns an arraylist of Chats.
     */
    public ArrayList<Chat> getChats(User user){
        ArrayList<Chat> chats = null;

        return chats;
    }

    /**
     * Returns a list of all messages in a chat.
     * @param chat the chat for which to get the messages
     * @return List<String>
     */
    public List<String> getChatMsgs(Chat chat){
        List<String> list = null;

        return list;
    }

    /**
     * Returns all possible candidates depending on the user search filter.
     * @param filter The filter to define the results by.
     * @return ArrayList<User>
     */
    public ArrayList<User> getCandidates(SearchFilter filter){
        ArrayList<User> users = null;

        return users;
    }

    /**
     * Get the information from your car via the RDW api
     * @param licenseplate the licensplate of the
     * @return returns the
     */
    public Car getCar(String licenseplate){
        Car car = null;

        /// TODO: 11-12-20 Strip licenseplate string
        licenseplate.replaceAll("-", "");

        return car;
    }

    /**
     * Update info on specific car
     * @param car car to update info on
     * @param info info to add in database
     */
    public void updateInfo(Car car, String info){

    }

    /**
     * Returns a list of possible searchfilters.
     * @return ArrayList<SearchFilter>
     */
    public ArrayList<SearchFilter> getFilterOptions(){
        ArrayList<SearchFilter> filters = null;

        return  filters;
    }

    /**
     * Send a message to a specific User
     * @param user User to send message to
     * @param message message to send
     */
    public void sendMessage(User user, String message){
        /// TODO: 11-12-20 Encrypt message with RSAPublicKey
    }

    /**
     * Get the public key from the database for specific user.
     * @param user User to get public key from
     * @return publickey
     */
    private RSAPublicKey getPublicKey(User user){
        RSAPublicKey publicKey = null;

        return publicKey;
    }
}