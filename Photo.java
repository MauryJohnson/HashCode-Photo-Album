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
			inputstream = new FileInputStream(System.getProperty("java.class.path")+"/"+"A.txt");
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
				   
				   int TagCount = 0;
				   String[] Tags;
				   
				   if(line.charAt(i)=='H') {
					  
					  System.out.print("H");
					  
					  //GET TAG COUNT
					  Tuple<Integer,Integer> T = GetInt(line,i);
					  i = T.Second;
					  TagCount = T.First;
					  
					  System.out.println(TagCount);
					  //GET TAGS
					  Tuple<String[], Integer> T2 = GetTags(line,TagCount,i);
					  i = T2.Second;
					  Tags = T2.First;
					  
					  
				   }
				  
				   else if(line.charAt(i)=='V') {
					  
					   System.out.print("V");
					   	  //GET TAG COUNT
						  Tuple<Integer,Integer> T = GetInt(line,i);
						  i = T.Second;
						  TagCount = T.First;
						  
						  System.out.println(TagCount);
						  //GET TAGS
						  Tuple<String[], Integer> T2 = GetTags(line,TagCount,i);
						  i = T2.Second;
						  Tags = T2.First;
						  
						  
						  
						  
				   }
				   
				   
				   
				   
			   }
			   
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * Get Tags for as far as tagcount counts all
	 * @param line
	 * @param tagCount
	 * @param i
	 * @return
	 */
	private static Tuple<String[],Integer> GetTags(String line, int tagCount, int i) {
		// TODO Auto-generated method stub
		String[] Tags = new String[tagCount];
		
		int TagsGot = 0;
		
		System.out.println();
		
		while(TagsGot<tagCount) {
			
			Tuple<String,Integer> T =  GetString(line,i);
			Tags[TagsGot] = T.First;
			i = T.Second;
			
			System.out.print("TAG GOT "+(TagsGot+1)+":"+Tags[TagsGot]+"\n");
			TagsGot+=1;
		}
			
		System.out.println();
		
		Tuple<String[],Integer> T = new Tuple<String[],Integer>(Tags,i);
		
		return T;
	}

	/**
	 * Get String 
	 */
	private static Tuple<String,Integer> GetString(String line, int i) {
		// TODO Auto-generated method stub
		for(;i<line.length();i+=1) {
			if(line.charAt(i)!=' ') {
				break;
			}
		}
		
		String res = "";
		
		for(;i<line.length();i+=1) {
			if(line.charAt(i)==' ' || line.charAt(i)=='\n') {
				break;
			}
			res+=line.charAt(i);
		}
		
		Tuple<String,Integer> T = new Tuple<String,Integer>(res,i);
		
		return T;
	}

	private static Tuple<Integer,Integer> GetInt(String line, int i) {
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
		
		Tuple<Integer,Integer> T = new Tuple<Integer,Integer>(Integer.parseInt(I),i);
		
		return T;
	}
	
}
