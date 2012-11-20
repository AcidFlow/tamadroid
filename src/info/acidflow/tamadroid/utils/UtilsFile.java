package info.acidflow.tamadroid.utils;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UtilsFile {

	private UtilsFile(){
		
	}
	
	public static String inputStreamtoString(InputStream in) throws IOException {
		DataInputStream stream = new DataInputStream(in);
		StringBuffer ret = new StringBuffer();
		String line = stream.readLine();
		while(line != null) {
			ret.append(line);
			line = stream.readLine();
		}
		return ret.toString();
	}
}
