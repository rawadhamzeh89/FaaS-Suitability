package parsing;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.ServerAddress;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;

import org.bson.Document;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

import com.mongodb.Block;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCursor;
import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.result.DeleteResult;
import static com.mongodb.client.model.Updates.*;
import com.mongodb.client.result.UpdateResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;


public class DBManager {
	MongoClient mongoClient;
	 MongoDatabase database;
	 MongoCollection<Document> collection;
	 LinkedList<operation> allUseCaseType;
	 public DBManager(){
	   mongoClient = new MongoClient( "localhost" , 27017 );
	   database = mongoClient.getDatabase("FaaS-useCase");
	   collection = database.getCollection("FaaSUseCase");
	   allUseCaseType=new LinkedList<operation>();
   }
	 public DBManager(String collectionName) {
		 mongoClient = new MongoClient( "localhost" , 27017 );
		   database = mongoClient.getDatabase("FaaS-useCase");
		   collection = database.getCollection(collectionName);
		 
	 }
  public LinkedList<operation> returnAllUseCaseType() {
	  LinkedList<operation> dbt= this.returnDBUseCase();
	  LinkedList<operation> mst= this.returnMessagingUseCase();
	  LinkedList<operation> aut= this.returnAuthMessagingUseCase();
	  dbt.addAll(mst);
	  dbt.addAll(aut);
	  return dbt;
  }
  public LinkedList<operation> returnDBUseCase() {
	  LinkedList<operation> result=new LinkedList<operation>();
	  collection.find(eq("id" , "2")).projection(new Document("Operations", 1)
              ).forEach(printBlock);
              System.out.println(collection.find(eq("id" , "1")).projection(new Document("Operations", 1)
    	              ).first().toJson());
	  ObjectMapper mapper = new ObjectMapper();
	  try {
		operations ops= mapper.readValue(collection.find(eq("id" , "1")).projection(new Document("Operations", 1)
	              ).first().toJson(),operations.class);
		System.out.println(ops.Operations2.size());
		result=ops.Operations2;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  return result;
  }
  public LinkedList<operation> returnMessagingUseCase() {
	  LinkedList<operation> result=new LinkedList<operation>();
	  ObjectMapper mapper = new ObjectMapper();
	  try {
		operations ops= mapper.readValue(collection.find(eq("id" , "2")).projection(new Document("Operations", 1)
	              ).first().toJson(),operations.class);
		System.out.println(ops.Operations2.size());
		result=ops.Operations2;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  return result;
  }
  
  public LinkedList<operation> returnAuthMessagingUseCase() {
	  LinkedList<operation> result=new LinkedList<operation>();
	  ObjectMapper mapper = new ObjectMapper();
	  try {
		operations ops= mapper.readValue(collection.find(eq("id" , "3")).projection(new Document("Operations", 1)
	              ).first().toJson(),operations.class);
		System.out.println(ops.Operations2.size());
		result=ops.Operations2;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  return result;
  }
  public LinkedList<metric> returnCodeMR() {
	  LinkedList<metric> result=new LinkedList<metric>();
	  ObjectMapper mapper = new ObjectMapper();
	  try {
		  retrievedMetrics ops= mapper.readValue(collection.find().projection(new Document("metrics", 1) 
	              ).first().toJson(),retrievedMetrics.class);
		System.out.println(ops.Operations2.size());
		;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  return result;
  }
  public LinkedList<metric> returnMeasurement() {
	  LinkedList<metric> result=new LinkedList<metric>();
	  ObjectMapper mapper = new ObjectMapper();
	  try {
		  retrievedMetrics ops= mapper.readValue(collection.find().projection(new Document("metrics", 1) 
	              ).first().toJson(),retrievedMetrics.class);
		System.out.println(ops.Operations2.size());
		;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} 
	  return result;
  }
  Block<Document> printBlock = new Block<Document>() {
      @Override
      public void apply(final Document document) {
    	  System.out.println("test");
    	  System.out.println(document.isEmpty());
          System.out.println(document.toJson());
      }
};


}

class operations{
	@JsonIgnore
	String _id;
	@JsonIgnore
	String $oid;

	Map<String, Object> Operations ;
	LinkedList<operation> Operations2=new LinkedList<operation>() ;
	operations(){
		
		//Operations2= new LinkedList<operation>();
		Operations= new HashMap<String, Object>();
	}
	@JsonAnySetter
	public void addop(String propertyKey, Object value) {
		Operations.put(propertyKey,value);
		System.out.println("value "+value.toString());
		if(propertyKey=="Operations") {
			String str=value.toString();
			str=str.replace('=', ':');
		JSONArray jsonarray = new JSONArray(str);
		for(int i=0;i< jsonarray.length();i++) {
			JSONObject jsonobject = jsonarray.getJSONObject(i);
		    String name = jsonobject.getString("name");
		    String text = jsonobject.getString("text");
		   
			Operations2.addFirst(new operation(name,text));
			
		}
		}
		//Operations2.addFirst(new operation(propertyKey,value));
	}
}
class operation{
	String name;
	Object text;
	int type;
	operation(String name, Object text){
		this.name=name;
		this.text=text;
	}
}