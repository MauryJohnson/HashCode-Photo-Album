import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Photo {
	
	//
	String Type;
	//
	String[] Tags;
	//Each photo has interest value for all other photos
	ArrayList<Integer> Interests = new ArrayList<Integer>();
	
	public Photo(String Type, String[] Tags) {
		this.Type=Type;
		this.Tags=Tags;
	}

	//Read from file
	public static void ParseFile() {
		
	}
	
	public static ArrayList<Photo> Interests(ArrayList<Photo> A){
		//ArrayList<Photo> I = new ArrayList<Photo>();
		
		A.sort((C,D)->{return InterestFactor(C,D);});

		//System.out.println("A SORTED:\n"+A);
		
		//HASHTABLE maps a tag to some photo
		Hashtable<String,ArrayList<Photo>> H = new Hashtable<String,ArrayList<Photo>>();	
		
		//Map ALL photos
		for(int i=0; i<A.size();i+=1) {
			for(int j=0; j<A.get(i).Tags.length;j+=1) {
				if(H.get(A.get(i).Tags[j])==null) {
				ArrayList<Photo> B = new ArrayList<Photo>();
				B.add(A.get(i));
				H.put(A.get(i).Tags[j],B);
				}
				else {
					H.get(A.get(i).Tags[j]).add(A.get(i));			
				}
			}
		}
		
		for(int i=0; i<A.size();i+=1) {

			for(int j=0; j<A.get(i).Tags.length;j+=1) {
				H.get(A.get(i).Tags[j]).sort((C,D)->Photo.InterestFactor(C, D));
			}
			
		}
		
		
		ArrayList<Hashtable<Photo,Photo>> AllVisited = new ArrayList<Hashtable<Photo,Photo>>();
		Hashtable<Photo,Photo> Visited = new Hashtable<Photo,Photo>();
		
		ArrayList<Photo> Res = new ArrayList<Photo>();
		
		int MAXSize = Integer.MIN_VALUE;
		ArrayList<Photo> MAXA = null;
		//Maximize from entries of H Table
		for(int i=0; i<A.size();i+=1) {
			
			Visited = new Hashtable<Photo,Photo>();
			
			//MAXA = null;
			
			//Get Maximum possible array
			for(int j=0; j<A.get(i).Tags.length;j+=1) {
				if(H.get(A.get(i).Tags[j])!=null) {
					if(H.get(A.get(i).Tags[j]).size()>MAXSize) {
						MAXSize = H.get(A.get(i).Tags[j]).size();
						MAXA = H.get(A.get(i).Tags[j]);
						//System.out.println("MAX ARR\n"+ MAXA);
					}
				}
			}
			
			
			//Once have maximum possible array, push all elements
			for(int k=0 ;k<MAXA.size();k+=1) {
				
				for(int l=0; l<MAXA.get(k).Tags.length;l+=1) {
					
					if(H.get(MAXA.get(k).Tags[l])!=null)
					for(int I=0; I<H.get(MAXA.get(k).Tags[l]).size();I+=1)
						
						if(l<MAXA.get(k).Tags.length) {
						if(Visited.get(H.get(MAXA.get(k).Tags[l]).get(I))==null) {
							
							System.out.println("FOUND!" + (H.get(MAXA.get(k).Tags[l]).get(I)));
							System.out.println("TAGS LENGTH:"+MAXA.get(k).Tags.length+ " L:" +l)
							;
							
							try {
							Visited.put(H.get(MAXA.get(k).Tags[l]).get(I), H.get(A.get(k).Tags[l]).get(I));
							}
							catch(Exception e) {
								System.err.println("CAUGHT");
							}
						}
					}
				}
			}
			
			
			AllVisited.add(Visited);
			
		}
		
		int MAX = Integer.MIN_VALUE;
		int IDX = 0;
		
		for(int i=0; i<AllVisited.size();i+=1) {
			if(AllVisited.get(i).size()>MAX) {
				MAX = AllVisited.get(i).size();
				IDX = i;
			}
		}
		
		
		
		for(Photo P:AllVisited.get(IDX).values()) {
			Res.add(P);
		}
		
	
		System.out.println("RESULT:\n"+Res);
		
		return Res;
		
	}

	public static int InterestFactor(Photo P1, Photo P2) {
		//IIF VERTICAL AND HORIZONTAL PHOTOS COMPARED
		if(P1.Type.compareTo(P2.Type)!=0) {
			//System.out.println("\nINTEREST FACTOR FOR "+P1 +" AND "+P2 +":"+-1+"\n");
			
			return 0;
		}
		
		int[] S = {
		P1.Tags.length,
		P2.Tags.length,
		0
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
		
		//System.out.println("\nINTEREST FACTOR FOR "+P1 +" AND "+P2 +":"+MIN+"\n");
		
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
				   String[] Tags;
				   
				   if(line.charAt(i)=='H') {
					  
					  //System.out.print("H");
					  
					  //GET TAG COUNT
					  Tuple<Integer,Integer> T = GetInt(line,i);
					  i = T.Second;
					  TagCount = T.First;
					  
					  //System.out.println(TagCount);
					  //GET TAGS
					  Tuple<String[], Integer> T2 = GetTags(line,TagCount,i);
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
						  Tuple<String[], Integer> T2 = GetTags(line,TagCount,i);
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
		
		Photo.Interests(Photos);
		
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
			
			//System.out.print("TAG GOT "+(TagsGot+1)+":"+Tags[TagsGot]+"\n");
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
	
	public String toString() {
		String s = "";
		for(int i=0; i<this.Tags.length;i+=1)
			s+=this.Tags[i]+" ";
		
		return this.Type +"\n"+ s+"\n"+this.Interests + "\n";
	}
	
}
