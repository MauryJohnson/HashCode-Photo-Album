import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Photo {
	
	//
	String Type;
	//
	ArrayList<String> Tags;
	
	//Each photo has interest value for all other photos
	Hashtable<Integer,Integer> Interests = new Hashtable<Integer,Integer>();
	
	//Compute distance between all interests in order to find min Interests Distance for each
	//int InterestsDistance = 0;
	
	public Photo(String Type, ArrayList<String> Tags) {
		this.Type=Type;
		this.Tags=Tags;
	}

	//Read from file
	public static void ParseFile() {
		
	}
	
	/**
	 * Try next best approach
	 * Choose first picture then next picture will be one that satisfies
	 * Interest the best!
	 * Continue until final pic used
	 * @param A
	 * @return
	 */
	public static ArrayList<Photo> Interests(ArrayList<Photo> A){
		
		ArrayList<Photo> CorrectOrder = new ArrayList<Photo>();
		
		//CorrectOrder.add(A.get(0));
	
		A.sort((B,C)->{return B.Type.compareTo(C.Type);});
		
		ArrayList<Integer> Randoms = new ArrayList<Integer>();
		for(int i=0; i<A.size();i+=1) {
			Randoms.add(i);
		}
		
		Collections.shuffle(Randoms);
		
		//Random R = new Random();
		
		int MAGIC = 1;
		
		Hashtable<Integer,Photo> Visited = new Hashtable<Integer,Photo>();
		
		for(int i=0; i<A.size();i+=1) {
			
			if(Visited.get(i)==null) {
				CorrectOrder.add(A.get(i));
				Visited.put(i, A.get(i));
			}
			
			int MAX = 0;
			int IDX = -1;
	
			//Choose a random picture and Switch to absolute picture matching every so often
			//int R = Randoms.get(i);
			/*int R = MAGIC;
			if(R==0)
				R=1;
			*/
			/////////////////////////////////////////////////////////////////////// MAGIC NUMBER!!!!!!!!
			if(i%(A.size()/MAGIC)==0) {
			/////////////////////////////////////////////////////////////////////////////////////////
			
			//See if InterestFactor is ideal
			if(i!=A.size()-1) {
				
			int k = Randoms.get(i+1);
			
			if(k<A.size() && k!=i && Visited.get(k)==null) {
			
			int I = InterestFactor(A.get(i),A.get(k));
			
			if(I>MAX) {
				MAX=I;
				IDX = k;
				
			}
			
			}
			
			}
			
			}
			
			else {
			for(int k=0;k<A.size();k+=1) {
				if(k!=i) {
					
					int I = InterestFactor(A.get(i),A.get(k));
					if(I>MAX && I!=0) {
						MAX=I;
						IDX = k;
					}
					
				}
			}
		}
			if(IDX!=-1)
			if(Visited.get(IDX)==null) {
				CorrectOrder.add(A.get(IDX));
				Visited.put(IDX, A.get(IDX));
			}	
			
		}
		
		//System.out.println("CORRECT ORDER:"+CorrectOrder);
		
		return CorrectOrder;
	}

	private static int SizeAll(ArrayList<Hashtable<Photo, Photo>> allVisited) {
		// TODO Auto-generated method stub
		int SIZE=  0;
		
		for(Hashtable<Photo,Photo> h:allVisited)
			SIZE+=h.size();
		
		return SIZE;
	}

	private static int GRNR(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	
	/**
	 * Size of all hashes in ht
	 * @param vI
	 * @return
	 */
	private static int SizeAll(Hashtable<Integer, Hashtable<Photo, Photo>> vI,Hashtable<Photo,Photo> AP) {
		// TODO Auto-generated method stub
		
		int SIZE = 0;
		
		for(Hashtable<Photo,Photo>H:vI.values()) {
			for(Photo P:H.values())
				if(AP.get(P)!=null)
					SIZE+=H.size();
		}
		
		return SIZE;
	}

	public static int InterestFactor(Photo P1, Photo P2) {
		//IIF VERTICAL AND HORIZONTAL PHOTOS COMPARED
		if(P1.Type.compareTo(P2.Type)!=0) {
			//System.out.println("\nINTEREST FACTOR FOR "+P1 +" AND "+P2 +":"+-1+"\n");
			return 0;
		}
		
		int FACTOR = P1.Tags.size()>P2.Tags.size()? P2.Tags.size():P1.Tags.size();
		//int FACTOR = P1.Tags.size()+P2.Tags.size();
		
		int MATCHING = 0;
		
		int[] S = {
		0,
		0,
		0
		};
		
		int i=0;
		
		Hashtable<String,String> H = new Hashtable<String,String>();
		
		for(;i<P1.Tags.size();i+=1) {
			H.put(P1.Tags.get(i), P1.Tags.get(i));
		}
		
		//Look for any similar tags and add those to count s3
		for(i=0;i<P2.Tags.size();i+=1) {
			if(H.get(P2.Tags.get(i))!=null) {
				S[2]+=1;
				//MATCHING+=1;
			}
			else {
				S[0]+=1;
				MATCHING+=1;
			}
		}
		
		Hashtable<String,String> H2 = new Hashtable<String,String>();
		
		int MATCHING2 = 0;
		
		for(;i<P2.Tags.size();i+=1) {
			H2.put(P2.Tags.get(i), P2.Tags.get(i));
		}
		
		//Look for any similar tags and add those to count s3
		for(i=0;i<P1.Tags.size();i+=1) {
			if(H2.get(P1.Tags.get(i))!=null) {
				//S[2]+=1;
			}
			else {
				S[1]+=1;
				MATCHING+=1;
			}
		}
		
		/*
		if(MATCHING2>MATCHING)
			MATCHING=MATCHING2;
		*/
		
		int MIN = Integer.MAX_VALUE;
	
		i=0;
		
		for(;i<S.length;i+=1) {
			if(S[i]<MIN)
				MIN = S[i];
		}
		
		//System.out.println("\nINTEREST FACTOR FOR "+P1 +" AND "+P2 +":"+(MIN)+"\n");
		
		return MIN;
	}
	
	//WHERE WE CAN TEST CODE
	public static void main(String[] args) {
		
		
		ArrayList<Photo> Photos = new ArrayList<Photo>();
		
		InputStream inputstream = null;
		try {
			inputstream = new FileInputStream(System.getProperty("java.class.path")+"/"+"B.txt");
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
				//System.out.println("SIZE:"+Size);
			}
				
			while ((line = r.readLine()) != null) {
			   //Do stuff with the array here, i.e. construct the index.
				
				
			   //Iterate through line
			   for(int i=0; i<line.length();i+=1) {
				   
				   int TagCount = 0;
				   ArrayList<String> Tags;
				   
				   if(line.charAt(i)=='H') {
					  
					  //System.out.print("H");
					  
					  //GET TAG COUNT
					  Tuple<Integer,Integer> T = GetInt(line,i);
					  i = T.Second;
					  TagCount = T.First;
					  
					  //System.out.println(TagCount);
					  //GET TAGS
					  Tuple<ArrayList<String>, Integer> T2 = GetTags(line,TagCount,i);
					  i = T2.Second;
					  Tags = T2.First;
					  
					  
					  Photos.add(new Photo("H",Tags));
					  
				   }
				  
				   else if(line.charAt(i)=='V') {
					  
					  // System.out.print("V");
					   	  //GET TAG COUNT
						  Tuple<Integer,Integer> T = GetInt(line,i);
						  i = T.Second;
						  TagCount = T.First;
						  
						  //System.out.println(TagCount);
						  //GET TAGS
						  Tuple<ArrayList<String>, Integer> T2 = GetTags(line,TagCount,i);
						  i = T2.Second;
						  Tags = T2.First;
						  

						  Photos.add(new Photo("V",Tags));
						  
				   }
				   
				   
				   
				   
			   }
			   
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("ALL PHOTOS:"+Photos);
		
		ArrayList<Photo> Res = Photo.Interests(Photos);
		
		PrintWriter writer = null;
		try {
			writer = new PrintWriter("./src/AnswerB.txt", "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		writer.println(Res.size());
		
		for(int i=0 ;i<Res.size();i+=1) {
			String s = "";
			for(int j=0; j<Res.get(i).Tags.size();j+=1) {
				s+=Res.get(i).Tags.get(j)+ " ";
			}
			writer.print("\n"+Res.get(i).Type + Res.get(i).Tags.size()+" "+s+"\n");
			
		}
		
		writer.close();
		
	}

	/**
	 * Get Tags for as far as tagcount counts all
	 * @param line
	 * @param tagCount
	 * @param i
	 * @return
	 */
	private static Tuple<ArrayList<String>,Integer> GetTags(String line, int tagCount, int i) {
		// TODO Auto-generated method stub
		ArrayList<String> Tags = new ArrayList<String>();
		
		int TagsGot = 0;
		
		System.out.println();
		
		while(TagsGot<tagCount) {
			
			Tuple<String,Integer> T =  GetString(line,i);
			Tags.add(T.First);
			i = T.Second;
			
			//System.out.print("TAG GOT "+(TagsGot+1)+":"+Tags[TagsGot]+"\n");
			TagsGot+=1;
		}
			
		System.out.println();
		
		Tuple<ArrayList<String>,Integer> T = new Tuple<ArrayList<String>,Integer>(Tags,i);
		
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
	
	public String toString() {
		String s = "";
		for(int i=0; i<this.Tags.size();i+=1)
			s+=this.Tags.get(i)+" ";
		
		return this.Type +"\n"+ s+"\n"+this.Interests + "\n";
	}
	
}
