package parsing;

import java.util.LinkedList;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.json.JSONArray;
import org.json.JSONObject;

public class metric {
	public int type;
	public String Name;
	public double value;
	public double weight;
	public metric(String name,double value) {
		this.Name=name;
		this.value=value;
	}
	@JsonIgnore
	String _id;
	@JsonIgnore
	String $oid;
}

 class retrievedMetrics {
	 @JsonIgnore
		String _id;
		@JsonIgnore
		String $oid;
		
		LinkedList<metric> Operations2=new LinkedList<metric>() ;
		
		@JsonAnySetter
		public void addop(String propertyKey, Object value) {
			if(propertyKey=="metrics") {
				String str=value.toString();
				str=str.replace('=', ':');
			JSONArray jsonarray = new JSONArray(str);
			for(int i=0;i< jsonarray.length();i++) {
				JSONObject jsonobject = jsonarray.getJSONObject(i);
			    String name = jsonobject.getString("name");
			    double text = jsonobject.getDouble("value");
			   
				Operations2.addFirst(new metric(name,text));
				
			}
			}
			//Operations2.addFirst(new operation(propertyKey,value));
		}
}


