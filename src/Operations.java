import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.Map.Entry;

public class Operations {
	private ArrayList<MonitoredData> activity=new ArrayList<MonitoredData>();

	public void readData() throws IOException {
		//ArrayList<MonitoredData> list=(Stream<String>)Files.lines(Paths.get("Activiy.txt"));

		Stream<String> acti=Files.lines(Paths.get("Activity.txt"));
		activity=(ArrayList<MonitoredData>) acti
				.map(x->x.split("		"))
				.map(x->new MonitoredData(x[0],x[1],x[2]))
				.collect(Collectors.toList());
		acti.close();
	}	


	public void print() {
		for(MonitoredData a:activity)
			System.out.println(a.getStartTime()+" "+a.getEndTime()+" "+a.getActivity());
	}

	public void daysCount() {
		int days=(int) activity.stream()
				.map(x->x.getStartTime())
				.map(x->x.split(" "))
				.map(x->x[0].split("-"))
				.map(x->x[2])
				.distinct()
				.count();
		System.out.println("Zile in Activity.txt: "+days);
	}

	public void activityCount() {
		HashMap<String,Integer> list=new HashMap<String,Integer>();
		list=(HashMap<String, Integer>) activity.stream()
				.map(x->x.getActivity())
				.distinct()
				.collect(Collectors.toMap(x->x, x->countAct(x)));
		
		
		for (Entry<String, Integer> entry : list.entrySet())  
			System.out.println("Key = " + entry.getKey() + 
					", Value = " + entry.getValue());

	}
	public Integer countAct(String s) {
		int count=0;
		count=(int) activity.stream()
				.filter(x->x.getActivity().equals(s))
				.count();
		return (Integer)count;
	}

	public void activityDay() {
		activity.stream()
		.map(x->x.getActivity())
		.distinct()
		.forEach(x->activity.stream()
				.filter(y->y.getActivity().equals(x))
				.map(y->y.getStartTime().split(" "))
				.map(y->y[0].split("-"))
				.map(y->y[2])
				.distinct()
				.forEach(y->System.out.println(y+" "+x+" "+activityCountDay(x,y))));
		//.forEach(x->activityCountDay(x));
		//.map(x->activityCountDay(x));
		//.map()
	}

	public int activityCountDay(String s, String day) {
		int count=0;
		count=(int) activity.stream()
				.filter(x->x.getActivity().equals(s))
				.map(x->x.getStartTime().split(" "))
				.map(x->x[0].split("-"))
				.map(x->x[2])
				.filter(x->x.equals(day))
				.count();
		return count;
	}

	public void lineTime(){
		activity.stream()
			.forEach(x->x.setDuration(lineTime1(x)));
		
		activity.stream()
			.forEach(x->System.out.println(Math.floorMod(((x.getDureation()/(60*60*1000))),12)+":"+Math.abs((x.getDureation()-x.getDureation()/(60*60*1000)*(60*60*1000))/(60*1000))+":"+Math.abs((x.getDureation()/1000-x.getDureation()/(60*1000)*(60)))+" "+x.getActivity()));
		
		/*activity.stream()
			.map(x->new String[] {x.getStartTime(),x.getEndTime()})
			.map(x->new String[][] {x[0].split(" "),x[1].split(" ")})
			.map(x->{
				try {
					return new Date[] {sdf.parse(x[0][1]),sdf.parse(x[1][1])};
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			})
			.map(x->x[1].getTime()-x[0].getTime())
			.forEach(x->System.out.println(Math.floorMod(((x/(60*60*1000))),12)+":"+Math.abs((x-x/(60*60*1000)*(60*60*1000))/(60*1000))+":"+Math.abs((x/1000-x/(60*1000)*(60)))));


		/*time=activity.stream()
			.map(x->new String[] {x.getStartTime(),x.getEndTime()})
			.map(x->new String[][] {x[0].split(" "),x[1].split(" ")})
			.map(x->new String[][] {x[0][1].split(":"),x[1][1].split(":")})
			.map(x->new Integer[] {Math.floorMod(Integer.parseInt(x[1][0])-Integer.parseInt(x[0][0]),24),Math.floorMod(Integer.parseInt(x[1][1])-Integer.parseInt(x[0][1]),60),Math.floorMod(Integer.parseInt(x[1][2])-Integer.parseInt(x[0][2]),60)})
			//.forEach(x->System.out.println(x[0]+":"+x[1]+":"+x[2]));
			.collect(Collectors.toList());

		time.stream()
			.forEach(x->System.out.println(x[0]+":"+x[1]+":"+x[2]));*/
	}
	
	public long lineTime1(MonitoredData a) {
		String format="kk:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		long dur=0;
		dur=activity.stream()
			.filter(x->x.equals(a))
			.map(x->new String[] {x.getStartTime(),x.getEndTime()})
			.map(x->new String[][] {x[0].split(" "),x[1].split(" ")})
			.map(x->{
				try {
					return new Date[] {sdf.parse(x[0][1]),sdf.parse(x[1][1])};
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			})
			.map(x->x[1].getTime()-x[0].getTime())
			.reduce((long)0, Long::sum);
		return dur;
			
	}

	public void activityDuration(){
		activity.stream()
		.map(x->x.getActivity())
		.distinct()
		.forEach(x->System.out.println(x+" "+Math.abs(((duration(x)/(60*60*1000))))+":"+Math.abs((duration(x)-duration(x)/(60*60*1000)*(60*60*1000))/(60*1000))+":"+Math.abs((duration(x)/1000-duration(x)/(60*1000)*(60)))));
		
		
		/*String format="kk:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		activity.stream()
			.map(x->x.getActivity())
			.distinct()
		.forEach(x->activity.stream()
				.filter(y->y.getActivity().equals(x))
				.map(y->new String[] {y.getStartTime(),y.getEndTime()})
				.map(y->new String[][] {y[0].split(" "),y[1].split(" ")})
				.map(y->{
					try {
						return new Date[] {sdf.parse(y[0][1]),sdf.parse(y[1][1])};
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return null;
				})
				.map(y->y[1].getTime()-y[0].getTime())
				.forEach(y->System.out.println(x+" "+Math.floorMod(((y/(60*60*1000))),12)+":"+Math.abs((y-y/(60*60*1000)*(60*60*1000))/(60*1000))+":"+Math.abs((y/1000-y/(60*1000)*(60))))));
				*/
	}

	public long duration(String x) {
		String format="kk:mm:ss";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		long a=0;
		a=activity.stream()
			.filter(y->y.getActivity().equals(x))
			.map(y->new String[] {y.getStartTime(),y.getEndTime()})
			.map(y->new String[][] {y[0].split(" "),y[1].split(" ")})
			.map(y->{
				try {
					return new Date[] {sdf.parse(y[0][1]),sdf.parse(y[1][1])};
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			})
			.map(y->y[1].getTime()-y[0].getTime())
			.reduce((long)0, Long::sum);
		return a;
	}
	
	public void activityFilter() {
		activity.stream()
			.map(x->x.getActivity())
			.distinct()
			.forEach(x->System.out.println((this.activityC(x)*0.9>=this.activityLess5(x))?x:" "));
						
					
					/*.filter(y->y.getActivity().equals(x))
						.filter(y->y.getDureation()<300000)
						//.count();
						.forEach(y->System.out.println(y.getActivity()))
					);*/
	}
	
	public int activityLess5(String x) {
		int a=0;
		a=(int) activity.stream()
			.filter(y->y.getActivity().equals(x))
			.filter(y->y.getDureation()<300000)
			.count();
		System.out.println(a);
		return a;
	}
	
	public int activityC(String a) {
		int nr=0;
		nr=(int) activity.stream()
				.filter(x->x.getActivity().equals(a))
				.count();
		System.out.println(nr);
		return nr;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Operations op=new Operations();
		try {
			op.readData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		op.print();
		op.daysCount();
		op.activityCount();

		op.activityDay();
		op.lineTime();
		op.activityDuration();
		
		op.activityFilter();

		

	}

}
