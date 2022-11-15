/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tallertcpclient;

/**
 *
 * @author josep
 */
public class TallerTCPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        var client=new Client();
        client.start();
        while(true)
            client.leer();
            
    }
    
}
