import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;




public class Upload implements Serializable{
	
	private String FilePath;
	private String CloudDaemonIp;
	private Mat MatImageToBeSent;
	private MatWrapperClass WMatImageToBeSent;
	private Integer CloudDaemonPort;
	private Integer ClientSidePort;
	
	
	public Upload(String TempFilePath, String TempCloudDaemonIP){
		CloudDaemonIp = TempCloudDaemonIP;
		//FilePath = TempFilePath;
		FilePath= "D:/Research/Eclipse j2ee/workspace/OpenCVAttempt1/src/lena.png";
		System.out.println(FilePath);
		CloudDaemonPort = 4;
		ClientSidePort = 4;
	}
	
	public void ExtractImage (){
		MatImageToBeSent =  Highgui.imread(FilePath);
		try{
			
			//MatImageToBeSent = Highgui.imread("D:/Research/Eclipse j2ee/workspace/OpenCVAttempt1/src/lena.png");
			WMatImageToBeSent = new MatWrapperClass(MatImageToBeSent);
			
		}
		catch(Exception e){ 
				System.err.println("Custom Error Message: Couldn't extract Image");
				e.printStackTrace();
			
		}
	}
	
	public void UploadImage(){
		MatSerializer MatObjectSerializer = new MatSerializer(WMatImageToBeSent);
		try {
			InetAddress CloudInetAddress = InetAddress.getByName(CloudDaemonIp);
			DatagramSocket DgSocket = new DatagramSocket(ClientSidePort);
			byte[] PktBuffer = MatObjectSerializer.Serialize();
			DatagramPacket DgPacket = new DatagramPacket(PktBuffer, PktBuffer.length, CloudInetAddress, CloudDaemonPort);
			DgSocket.send(DgPacket);
			DgSocket.close();
			
		} catch (SocketException e) {
			System.err.println("Custom Error Message:Socket Creation Error");
			e.printStackTrace();
			System.out.println("Returning Null");
			
		} catch (UnknownHostException e) {
			System.err.println("Custom Error Message: Error in resolving InetAddress");
			e.printStackTrace();
			System.out.println("Returning Null");
			
		} catch (IOException e) {
			System.err.println("Custom Error Message: Error in sending packet through the Datagram Socket through port "+ ClientSidePort);
			e.printStackTrace();
			System.out.println("Returning Null");
		}
	}
	
	public static void main(String[] args) {
		Upload upObjectUpload; 
		if(args.length != 2){
			System.out.println("Custom Error Message: Invalid usage, Provide the First Argument as the FilePath of the Image and the 2nd Argument as the CloudDaemonIp");
			System.exit(0);
		}
		else{
			upObjectUpload = new Upload(args[0], args[1]);
			upObjectUpload.ExtractImage();
			upObjectUpload.UploadImage();
		}

	}
	
	public String getFilePath() {
		return FilePath;
	}

	public void setFilePath(String filePath) {
		FilePath = filePath;
	}

	public String getCloudDaemonIp() {
		return CloudDaemonIp;
	}

	public void setCloudDaemonIp(String cloudDaemonIp) {
		CloudDaemonIp = cloudDaemonIp;
	}

	public MatWrapperClass getWMatImageToBeSent() {
		return WMatImageToBeSent;
	}

	public void setWMatImageToBeSent(MatWrapperClass wMatImageToBeSent) {
		WMatImageToBeSent = wMatImageToBeSent;
	}

}
