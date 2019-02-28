import java.util.*;

public class Photo {
	
	String Type;
	String[] Tags;
	
	public Photo(String Type, String[] Tags) {
		this.Type=Type;
		this.Tags=Tags;
	}

	//Read from file
	public static void ParseFile() {
		
	}

	public static int InterestFactor(Photo P1, Photo P2) {
		int s1 = P1.Tags.length;
		int s2 = P2.Tags.length;
		int s3 = 0;
		
		int i=0;
		
		Hashtable<String,String> H = new Hashtable<String,String>();
		
		for(;i<P1.Tags.length;i+=1) {
			H.put(P1.Tags[i], P1.Tags[i]);
		}
		
		i=0;
		
		//Look for any similar tags and add those to count s3
		for(;i<P2.Tags.length;i+=1) {
			if(H.get(P2.Tags[i])!=null) {
				s3+=1;
			}
		}
		
		
		return s3;
	}
	
}
