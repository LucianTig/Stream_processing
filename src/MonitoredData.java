
public class MonitoredData {
	private String startTime;
	private String endTime;
	private String activity;
	private long duration;
	
	public MonitoredData(String s, String e, String a) {
		startTime=s;
		endTime=e;
		activity=a;
	}
	
	public String getStartTime() {
		return this.startTime;
	}
	
	public String getEndTime() {
		return this.endTime;
	}
	
	public String getActivity() {
		return this.activity;
	}
	
	public void setDuration(long dur) {
		duration=dur;
	}
	
	public long getDureation() {
		return this.duration;
	}
	
	public static void main(String args[]) {
		
	}
	
	/*public int equals(MonitoredData a) {
		int i=0;
		if()
	}*/

}
