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
		int[] S = {
		P1.Tags.length,
		P2.Tags.length
		};
		
		int i=0;
		
		Hashtable<String,String> H = new Hashtable<String,String>();
		
		for(;i<P1.Tags.length;i+=1) {
			H.put(P1.Tags[i], P1.Tags[i]);
		}
		
		i=0;
		
		//Look for any similar tags and add those to count s3
		for(;i<P2.Tags.length;i+=1) {
			if(H.get(P2.Tags[i])!=null) {
				S[2]+=1;
			}
		}
		
		int MIN = Integer.MAX_VALUE;
	
		i=0;
		
		for(;i<S.length;i+=1) {
			if(S[i]<MIN)
				MIN = S[i];
		}
		
		return MIN;
	}
	
	//WHERE WE CAN TEST CODE
	public static void main(String[] args) {
		
		
		
	}
	
}
