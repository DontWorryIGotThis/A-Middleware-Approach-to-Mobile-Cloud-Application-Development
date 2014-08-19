import java.io.Serializable;

import org.opencv.core.Mat;


public class MatWrapperClass extends Mat implements Serializable {
	Mat matObject;
	
	public MatWrapperClass (Mat TempMatObject){
		super();
		matObject = TempMatObject;
	}

}
