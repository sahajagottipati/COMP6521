import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Comparator {
	static BufferedReader br1;
	static BufferedReader br2;
	static Writer writer;
	public static void comparator(int s, int counter) throws FileNotFoundException {
		double tp_number=0;
		String path=System.getProperty("user.home") + "/Desktop/COMP6521/";
		String b1 = String.format("%2d", (s-1));
		String b2 = String.format("%2d", (counter-1));
		try {
			String b = String.format("%2d", 1);
			File f=new File(path,"FinalOutput"+b+".txt");
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			br1 = new BufferedReader(new FileReader(path+"SampleOutput"+b1+".txt"));
			br2 = new BufferedReader(new FileReader(path+"SampleOutput"+b2+".txt"));
			String line1 = br1.readLine();
			String line2= br2.readLine();
			while(line1!=null && line2!=null) {
				int value = line1.substring(8, 18).compareTo(line2.substring(8,18));
				if(Integer.parseInt(line1.substring(0, 8))>Integer.parseInt(line2.substring(0, 8))) {
					writer.write(line2);
					line2=br2.readLine();
					writer.write("\r");
				}else if(Integer.parseInt(line1.substring(0, 8))<Integer.parseInt(line2.substring(0, 8))) {
					writer.write(line1);
					line1=br1.readLine();
					writer.write("\r");
				}else if (Integer.parseInt(line1.substring(0, 8))==Integer.parseInt(line2.substring(0, 8))){
					if(value<0) {
						writer.write(line2);
						line1=br1.readLine();
						line2=br2.readLine();
						writer.write("\r");
					}else{
						writer.write(line1);
						line2=br2.readLine();
						line1=br1.readLine();
						writer.write("\r");
					} 
				}
				tp_number++;
			} 
			while(line1!=null && line2==null) {
				writer.write(line1);
				writer.write("\r");
				line1=br1.readLine();
				tp_number++;
			}
			while(line2!=null && line1==null) {
				writer.write(line2);
				writer.write("\r");
				line2=br2.readLine();
				tp_number++;
			}
			writer.flush();
			writer.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProcessedTuples.setFinaltuples(tp_number);
	}
}
