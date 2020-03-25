import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMemoryValue {

	public static int memoryValue() {
		int count=0, mainmemory=0;
		String vmargs = null;
		RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		List<String> jvmArgs = runtimeMXBean.getInputArguments();
		for (String arg : jvmArgs) {
			if(count==0) {
				vmargs = arg;
			}
		    count++;
		}
		System.out.println(vmargs);
		Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(vmargs);
        while(m.find()) {
        	switch (Integer.parseInt(m.group())) {
			case 5: {
				mainmemory = 2* 1048576;
				break;
			}
			case 10: {
				mainmemory = 3* 1048576;
				break;
			}
			case 20: {
				mainmemory = 5* 1048576;
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + Integer.parseInt(m.group()));
			}
        }
		return mainmemory;
	}
}
