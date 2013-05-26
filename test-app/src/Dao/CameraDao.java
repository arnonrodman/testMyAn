package Dao;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.SliceQuery;

public enum CameraDao {
	INSTANCE;
	
	public final static String   keySpace = "Camera";	
	
	//The string serializer translates the byte[] to and from String using utf-8 encoding
    public final static StringSerializer stringSerializer = StringSerializer.get();
    
	public Mutator<String> initConnection(){
		
		//Create a cluster object from your existing Cassandra cluster
        Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "localhost:9160");
        
        //Create a keyspace object from the existing keyspace we created using CLI
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
        
        //Create a mutator object for this keyspace using utf-8 encoding
        Mutator<String> mutator = HFactory.createMutator(keyspace, stringSerializer);
        
        return mutator;
	}
	
	
	public String validteExitsUserInDB(String androidID){	
		//Create a cluster object from your existing Cassandra cluster
        Cluster cluster = HFactory.getOrCreateCluster("Test Cluster", "localhost:9160");
        
        //Create a Keyspace object from the existing Keyspace we created using CLI
        Keyspace keyspace = HFactory.createKeyspace(keySpace, cluster);
       
		SliceQuery<String, String, String> sliceQuery = HFactory.createSliceQuery(keyspace, stringSerializer, stringSerializer, stringSerializer);
        sliceQuery.setColumnFamily("users").setKey("email");
        sliceQuery.setRange(null, null, false, 100);
        
        QueryResult<ColumnSlice<String, String>> result = sliceQuery.execute(); 
      
		ColumnSlice<String, String> res = result.get();
		boolean isFound = false;
		String albumFolder = null; 
		if(!res.getColumns().isEmpty()){
			 for (HColumn<String, String> column : result.get().getColumns()) {
			        System.out.println(column.getName() +"::"+ column.getValue());
			        if("albumFolder".equals(column.getName()) &&  column.getValue() != null){
			        	albumFolder =  column.getValue();
			        }
			        if("androidId".equals(column.getName()) && androidID.equals(column.getValue())){
			        	isFound = true;
			        	break;
			        }
			    }			
		}
		
		return albumFolder;
	}
	public void insertNewAlbumDB(String... params){
		String userId    	= params[0];
		String password  	= params[1];
		String albumName 	= params[2];
		String albumFolder 	= params[3];
		String latitude		= params[4];
		String altitude 	= params[5];
		String androidId	= params[6];
		String email	    = params[7];
		
		Mutator<String> mutator = initConnection();
		String key = email+"#"+albumName;
			
		if(key!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("emailAlbumName", androidId));
		
		if(androidId!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("androidId", androidId));
		
		if(userId!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("userId", userId));
		
		if(password!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("password", password));
				
		if(albumFolder!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("albumFolder", albumFolder));
		
		if(latitude!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("latitude", latitude));
        
		if(altitude!= null)
			mutator.insert(key, "userAlbums", HFactory.createStringColumn("altitude", altitude));
		
        mutator.execute();
			}
	
	public void insertNewUser(String... params){
		String androidId    	= params[0];
		String emailAddress  	= params[1];
		String password      	= params[2];
		
		Mutator<String> mutator = initConnection();
		
		if(emailAddress!= null)
			mutator.insert(emailAddress, "users", HFactory.createStringColumn("emailAddress", emailAddress));
		
		if(password!= null)
			mutator.insert(emailAddress, "users", HFactory.createStringColumn("password", password));				
		
		 mutator.execute();		
	}
	
	public static void main(String[] args) {
		int year = 1994;
		int month = 12;
		
		System.out.println(Long.valueOf(""+year+""+month));
		
	}
}
