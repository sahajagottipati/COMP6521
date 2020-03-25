import java.util.Map;
import java.util.TreeMap;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Array;

/**
 * This class is to merge the sorted lists into one list.
 *
 */
public class Merge {
	static boolean isEvenCount=false;
	static Map<String, BufferedReader> bufferreaders = new HashMap<>();
	static List<String> filesList=new ArrayList<String>();
	static Writer writer = null;
	static int c=0;
	static int Generalsize=0;
	public static int count=0;
	static String path=System.getProperty("user.home") + "/Desktop/COMP6521/";
	public static int mergeFiles(int startfile, int counter){			
		c=counter;
		Generalsize = startfile;
		int round = c-Generalsize;	
		if(startfile==0)
			smallMerge(c, 0);
		while(round>1) {
			smallMerge(c, Generalsize);
			round = c-Generalsize;
		}
		if(round == 1) {
			String b = String.format("%2d", c);
			filesList.add(b);
		}
		return c;
	}

	public static void smallMerge(int v,int generalsize) {
		try { 
			c=v;
			Generalsize = v;
			for(int i=generalsize;i<c ;i++)
			{
				String b = String.format("%2d", i);
				bufferreaders.put("br"+b, new BufferedReader(new FileReader(path+"SampleOutput"+b+".txt")));
				filesList.add(b);
				count++;
				
			}	
			/*
			 * if(filesList.size()%2==0) { isEvenCount=true; //index=2; }
			 */
			if((v-generalsize)%2==0) {
				isEvenCount=true;
				//index=2;
			}else{
				isEvenCount=false;
			}
			for(int i=generalsize;i<filesList.size()-1;i+=2) {
				String line1 = null,line2 = null;
				String b = String.format("%2d", c);
				File f=new File(path,"SampleOutput"+b+".txt");
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
				String b1 = String.format("%2d", i);
				String b2 = String.format("%2d", i+1);
				line1 =bufferreaders.get("br"+b1).readLine();
				line2=bufferreaders.get("br"+b2).readLine();
				while(line1!=null && line2!=null) {
					int value = line1.substring(8, 18).compareTo(line2.substring(8,18));
					if(Integer.parseInt(line1.substring(0, 8))>Integer.parseInt(line2.substring(0, 8))) {
						writer.write(line2);
						line2=bufferreaders.get("br"+b2).readLine();
						writer.write("\r");
					}else if(Integer.parseInt(line1.substring(0, 8))<Integer.parseInt(line2.substring(0, 8))) {
						writer.write(line1);
						line1=bufferreaders.get("br"+b1).readLine();
						writer.write("\r");
					}else if (Integer.parseInt(line1.substring(0, 8))==Integer.parseInt(line2.substring(0, 8))){
						if(value<0) {
							writer.write(line2);
							line1=bufferreaders.get("br"+b1).readLine();
							line2=bufferreaders.get("br"+b2).readLine();
							writer.write("\r");
						}else{
							writer.write(line1);
							line2=bufferreaders.get("br"+b2).readLine();
							line1=bufferreaders.get("br"+b1).readLine();
							writer.write("\r");
						} 
					}
				} 
				while(line1!=null && line2==null) {
					writer.write(line1);
					writer.write("\r");
					line1=bufferreaders.get("br"+b1).readLine();
				}
				while(line2!=null && line1==null) {
					writer.write(line2);
					writer.write("\r");
					line2=bufferreaders.get("br"+b2).readLine();
				}
				writer.flush();
				writer.close();
				//bufferreaders.put("br"+c, new BufferedReader(new FileReader("SampleOutput"+c+".txt")));
				c++;							
			}
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if(!isEvenCount) {
			String b3 = String.format("%2d", filesList.size()-1);
			String b = String.format("%2d", c);
			File f=new File(path,"SampleOutput"+b+".txt");			
			try {
				writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
				String file3=bufferreaders.get("br"+b3).readLine();
				while(file3!=null) {
					writer.write(""+file3);
					writer.write("\r");
					file3=bufferreaders.get("br"+b3).readLine();
				}
				writer.flush();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c++;
		}
	}
	public static int getIOcount() {
		int s = count;
		count=0;
		return s;
	}
}
