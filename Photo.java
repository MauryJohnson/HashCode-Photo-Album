import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
		
		InputStream inputstream = null;
		try {
			inputstream = new FileInputStream(System.getProperty("java.class.path")+"/"+"a_example.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader r = new BufferedReader(new InputStreamReader(inputstream));
		
		//Fist number is number of photos total
		
		//HX or VX is horizintal or vertical photo with X tags
		
		String line;
		
		int Size = 0;
		String[] splitString;
		
		try {
			
			if((line = r.readLine()) != null) {
				splitString = line.split("\\s+");
				String res = "";
				for(int i=0; i<splitString.length;i+=1) {
					res+=splitString[i];
				}
				Size = Integer.parseInt(res);
				System.out.println("SIZE:"+Size);
			}
				
			while ((line = r.readLine()) != null) {
			   //Do stuff with the array here, i.e. construct the index.
				
				
			   //Iterate through line
			   for(int i=0; i<line.length();i+=1) {
				   int Tags = 0;
				   if(line.charAt(i)=='H') {
					   System.out.print("H");
					  Tags = GetInt(line,i);
					  System.out.println(Tags);
				   }
				   else if(line.charAt(i)=='V') {
					   System.out.print("V");
					  Tags = GetInt(line,i);
					   System.out.println(Tags);
				   }
			   }
			   
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static int GetInt(String line, int i) {
		// TODO Auto-generated method stub
		
		//Iterate until reach num
		for(;i<line.length();i+=1) {
			if(Character.isDigit(line.charAt(i))){
				break;
			}
		}
		
		String I = "";
		//Store Integer
		for(;i<line.length();i+=1) {
			if(!Character.isDigit(line.charAt(i))){
				break;
			}
			I+=line.charAt(i);
		}
		
		return Integer.parseInt(I);
	}
	
}
