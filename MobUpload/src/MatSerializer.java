import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.opencv.core.Mat;


public class MatSerializer {
	private MatWrapperClass MatObj;
	
	public MatSerializer (MatWrapperClass TempMat){
		MatObj = TempMat;
	}
	
	public byte[] Serialize(){
		try {
			ByteArrayOutputStream ByteArrOut = new ByteArrayOutputStream();
			ObjectOutputStream ObjOut = new ObjectOutputStream(ByteArrOut);
			ObjOut.writeObject(MatObj);
			return ByteArrOut.toByteArray();
		} catch (IOException e) {
			System.err.println("Custom Error Message: Error in connecting the byte and object output Streams and returning NUll");
			e.printStackTrace();
			return null;
		}
	}

}
