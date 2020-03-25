import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class TPMMSFileWriter {
	public static int outoperations=0;
	public static List<File> writeListToFile(List<String> records, int counter, String filename, List<File> fileList, double tuples, double noofblocks) throws IOException {
		Writer writer=null;
		String path=System.getProperty("user.home") + "/Desktop/COMP6521/";
		try {
			String b = String.format("%2d", counter);
			File f=new File(path,"SampleOutput"+b+".txt");
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			fileList.add(f);
			for (int k=0;k<records.size();k++) {
				if((k<=records.size()-2)) {
					String data1 = records.get(k).substring(0, 8);
					String data2 = records.get(k+1).substring(0, 8);
					int value = data1.compareTo(data2);
					if(value==0) {
						continue;
					} else {
						writer.write(""+records.get(k));
						writer.write("\r");
						outoperations++;
					}
				}						
				else {
					writer.write(""+records.get(k));
					writer.write("\r");
					outoperations++;
				}
			}
			records.clear();
			writer.flush();
			writer.close();
		} finally {
			//Nothing
		}
		return fileList;
	}
	
	public static int getOutOperations() {
		int out = outoperations;
		outoperations=0;
		return out;
	}

}
