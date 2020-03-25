import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TPMMSFileReader {
	BufferedReader bufferedreader;
	static int iooperations;

	public TPMMSFileReader(BufferedReader br) {
		/*
		 * super(); this.br = br;
		 */
		bufferedreader = br;
	}

	public static List<String> readFile(BufferedReader bufferedreader , double size) throws IOException{
		List<String> dataBlock = new ArrayList<String>();
		String data = null,tupleData = null;
		String line = "";
		for(long i=0;i<size && (line = bufferedreader.readLine())!= null;i++) {
			data = line.trim();
			if(!data.isEmpty()) {				
				dataBlock.add(data);
			}
			iooperations++;
		}
		return dataBlock;
	}
	public static int getIOoperations() {
		int operations = iooperations;
		iooperations=0;
		return operations;
	}
}
