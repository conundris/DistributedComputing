package sample.SSL;

import sample.SSLStuff;

import javax.net.ssl.SSLEngine;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class SSLClient {

    private SSLEngine engine;
    private DatagramSocket mySocket;

    public SSLClient() {
        try {
            engine = SSLStuff.createSSLEngine(true);
            this.mySocket = new DatagramSocket(3001);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] send(ByteBuffer message, String hostName, int portNum ) {
        try {
            InetSocketAddress serverSocketAddr = new InetSocketAddress(
                    InetAddress.getByName(hostName), portNum);
            SSLStuff.handshake(engine, mySocket, serverSocketAddr, false);

            SSLStuff.sendAppData(engine, mySocket, message.duplicate(), serverSocketAddr, "Client");

            /*ByteBuffer receivedData = SSLStuff.receiveAppData(engine, mySocket, "Client");

            if (receivedData == null) {
                System.out.println("No Application data received on client side.");
            } else {
                System.out.println("GOT MESSAGE");
                System.out.println(new String(receivedData.array()));
            }*/

            //return receivedData.array();
            return null;

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
