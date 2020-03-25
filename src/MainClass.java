import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainClass {
	public static double blocksize;
	private static BufferedReader br;
	private static String filename;
	public static FileReader fr;
	private static List<File> fileList=new ArrayList<File>();
	private static List<List<File>> tempList=new ArrayList<List<File>>();
	public static List<String> obtainedArrFromFile;
	public static int counter = 0;
	public static void main(String args[]) throws IOException {
		double start = System.currentTimeMillis();
		int s = 0;
		double noofblocks=0;
		long mainmemory = MainMemoryValue.memoryValue();
		blocksize = Math.floorDiv(mainmemory, 4096);
		long tuples = (long)(blocksize * 4096)/100;
		double result = 0,rounds=0;
		System.out.println("Block size: "+blocksize);
		System.out.println("No. of tuples in one fill of memory: "+tuples);
		String path=System.getProperty("user.home") + "/Desktop/COMP6521/";
		filename = "T2.txt";
		try {			
			FileReader input = new FileReader(path+filename);
			LineNumberReader count = new LineNumberReader(input);			
			{
				while (count.skip(Long.MAX_VALUE) > 0)
				{

				}
				result = count.getLineNumber() + 1;                                    
			}
			br = new BufferedReader(new FileReader(path+filename));
		} catch (FileNotFoundException e) 
		{ // TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ProcessedTuples.setProcessedtuples(result);
		noofblocks = (int) Math.ceil((result*100)/4096);
		System.out.println("No of blocks: "+noofblocks);
		if(noofblocks>blocksize) {
			rounds = Math.ceil(noofblocks/blocksize);
		}else {
			rounds = 1;
		}
		System.out.println("\nNumber of rounds for T1: "+rounds);
		double start_t1 = System.currentTimeMillis();
		for(int k=0;k<rounds;k++) { 			
			try {
				if(rounds==1) {
					obtainedArrFromFile = TPMMSFileReader.readFile(br,result);
					Collections.sort(obtainedArrFromFile);
					fileList=TPMMSFileWriter.writeListToFile(obtainedArrFromFile, counter, filename, fileList, result, noofblocks);
					tempList.add(fileList);					
				} else {
					obtainedArrFromFile = TPMMSFileReader.readFile(br,tuples);
					Collections.sort(obtainedArrFromFile);					
					fileList=TPMMSFileWriter.writeListToFile(obtainedArrFromFile, counter, filename, fileList, tuples, noofblocks);
					tempList.add(fileList);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}
		int diskIO_T1 = ((TPMMSFileReader.getIOoperations()*100)/4096)+((TPMMSFileWriter.getOutOperations()*100)/4096);
		System.out.println("Phase 1 DISK I/O for T1: "+diskIO_T1);
		double stop_t1 = System.currentTimeMillis();
		System.out.println("Phase 1 time for T1: "+ ((stop_t1 - start_t1)/1000) +" seconds");
		//System.out.println("Counter: "+counter);
		start_t1 = System.currentTimeMillis();
		s = Merge.mergeFiles(0,counter);
		stop_t1 = System.currentTimeMillis();
		System.out.println("Phase 2 time for T1: "+((stop_t1 - start_t1)/1000) + " seconds");
		counter = s;
		System.out.println("Phase 2 DISK I/O for T1: "+(int)(Merge.getIOcount()*blocksize));
		/**
		 * T2 data
		 */
		
		filename = "T1.txt";
		try {			
			FileReader input = new FileReader(path+filename);
			LineNumberReader count = new LineNumberReader(input);			
			{
				while (count.skip(Long.MAX_VALUE) > 0)
				{

				}
				result = count.getLineNumber() + 1;                                    
			}
			br = new BufferedReader(new FileReader(path+filename));
		} catch (FileNotFoundException e) 
		{ // TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double t2_pt = ProcessedTuples.getProcessedtuples() + result;
		ProcessedTuples.setProcessedtuples(t2_pt);
		noofblocks = (int) Math.ceil((result*100)/4096);
		if(noofblocks>blocksize) {
			rounds = Math.ceil(noofblocks/blocksize);
		}else {
			rounds = 1;
		}
		System.out.println("\nNumber of rounds for T2: "+rounds);
		double start_t2 = System.currentTimeMillis();
		for(int k=0;k<rounds;k++) { 			
			try {
				if(rounds==1) {
					obtainedArrFromFile = TPMMSFileReader.readFile(br,result);
					Collections.sort(obtainedArrFromFile);
					fileList=TPMMSFileWriter.writeListToFile(obtainedArrFromFile, counter, filename, fileList, result, noofblocks);
					tempList.add(fileList);					
				} else {
					obtainedArrFromFile = TPMMSFileReader.readFile(br,tuples);
					Collections.sort(obtainedArrFromFile);					
					fileList=TPMMSFileWriter.writeListToFile(obtainedArrFromFile, counter, filename, fileList, tuples, noofblocks);
					tempList.add(fileList);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counter++;
		}
		int diskIO_T2 = ((TPMMSFileReader.getIOoperations()*100)/4096)+((TPMMSFileWriter.getOutOperations()*100)/4096);
		System.out.println("Phase 1 DISK I/O for T2: "+diskIO_T2);
		double stop_t2 = System.currentTimeMillis();
		System.out.println("Phase 1 time for T2: "+ ((stop_t2 - start_t2)/1000)+" seconds");
		//System.out.println("Counter: "+counter);
		start_t2 = System.currentTimeMillis();
		counter = Merge.mergeFiles(s,counter);
		stop_t2 = System.currentTimeMillis();
		System.out.println("Phase 2 time for T2: "+((stop_t2 - start_t2)/1000)+" seconds");
		System.out.println("Phase 2 DISK I/O for T2: "+(int)(Merge.getIOcount()*blocksize));
		try {
			Comparator.comparator(s, counter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double stop = System.currentTimeMillis();
		System.out.println("\nProcessing Time: "+ ((stop-start)/1000)+" seconds");
		System.out.println("Initial Tuples: "+(int)ProcessedTuples.getProcessedtuples());
		System.out.println("TPMMS processed tuples: "+(int)ProcessedTuples.getFinaltuples());
		System.out.println("Number of blocks holding the output tuples: "+ (int)Math.ceil((ProcessedTuples.getFinaltuples()*100)/4096));

	}		
}


