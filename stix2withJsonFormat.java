import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.cedarsoftware.util.io.JsonWriter;
 
public class stix2withJsonFormat {
	protected static String createHeader (Alienvault a) throws java.text.ParseException {
		String header = "        {\r\n" + 
						"            \"type\": \"campaign\",\r\n" + 
						"            \"id\": \"campaign--" + rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) + "\",\r\n" + 
						"            \"created\": \"" + getDate(a.getCreationDate()) + "\",\r\n" + 
						"            \"modified\": \"" + getDate(a.getModifiedDate()) + "\",\r\n" + 
						"            \"object_marking_refs\": [\r\n" + 
						"                \"marking-definition--71f8e024-2c02-4350-a627-a71fa9de3437\"\r\n" + 
						"            ],\r\n" + 
						"            \"name\": \"" + a.getName() + "\",\r\n" + 
						"            \"description\": \"" + a.getDescription() + "\",\r\n" + 
						"            \"first_seen\": \"" + a.getCreationDate() + "\"\r\n" + 
						"        },\r\n";		
		return header;
	}
	
	protected static String getUrlIndicators (Alienvault a) throws JSONException, java.text.ParseException {
		String url_ind = "";
		for(int i=0; i<a.getURL_indicators().length(); i++) {
			JSONObject indicador = a.getURL_indicators().getJSONObject(i);
			String name = "";
			if (indicador.getString("title").isEmpty()) {
				name = "\" " + a.getAutor() + "-" + indicador.getString("type") + "-" + indicador.get("id").toString() + "\"";
			} else {
				name = indicador.getString("title");
			}
			url_ind +=  "        {\r\n" + 
						"            \"created\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
						"            \"id\": \"indicator--"+ rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) +"\",\r\n" + 
						"            \"labels\": [\r\n" + 
						"                \"url-watchlist\"\r\n" + 
						"            ],\r\n" + 
						"            \"modified\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
						"            \"name\":" + name + ",\r\n" + 
						"            \"pattern\": \"[url:value = '" + indicador.getString("indicator").replace("\\","/") + "']\",\r\n" + 
						"            \"type\": \"indicator\",\r\n" + 
						"            \"valid_from\": \"" + getDate(indicador.getString("created")) + "\"\r\n";
			if (i==a.getURL_indicators().length()-1) {
				url_ind += "        }\r\n";
			} else {
				url_ind += "        },\r\n";
			}
		}
		return url_ind;
	}
	
	protected static String getHashIndicators (Alienvault a) throws JSONException, java.text.ParseException {
		String hash_ind = "";
		for(int i=0; i<a.getHash_indicators().length(); i++) {
			JSONObject indicador = a.getHash_indicators().getJSONObject(i);
			String name = "";
			if (indicador.getString("title").isEmpty()) {
				name = "\" " + a.getAutor() + "-" + indicador.getString("type") + "-" + indicador.get("id").toString() + "\"";
			} else {
				name = indicador.getString("title");
			}
			
			hash_ind += "        {\r\n" + 
						"            \"created\": \"" + getDate(getDate(indicador.getString("created"))) + "\",\r\n" + 
						"            \"id\": \"indicator--"+ rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) +"\",\r\n" + 
						"            \"labels\": [\r\n" + 
						"                \"" + indicador.getString("type") + "\"\r\n" + 
						"            ],\r\n" + 
						"            \"modified\": \"" + getDate(getDate(indicador.getString("created"))) + "\",\r\n" + 
						"            \"name\":" + name + ",\r\n" + 
						"            \"pattern\": \"[Hash:value = '" + indicador.getString("indicator") + "']\",\r\n" + 
						"            \"type\": \"indicator\",\r\n" + 
						"            \"valid_from\": \"" + getDate(getDate(indicador.getString("created"))) + "\"\r\n" +
						"        },\r\n";
		}
		return hash_ind;
	}
	
	private static String getDate(String s) throws java.text.ParseException {
		String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		java.util.Date olddate = sdf.parse(s);
	    sdf.applyPattern(format);
	    String date = sdf.format(olddate);
	    return date;
	}

	protected static String getDomainIndicators (Alienvault a) throws JSONException, java.text.ParseException {
		String dom_ind = "";
		for(int i=0; i<a.getDomain_indicators().length(); i++) {
			JSONObject indicador = a.getDomain_indicators().getJSONObject(i);
			String name = "";
			if (indicador.getString("title").isEmpty()) {
				name = "\" " + a.getAutor() + "-" + indicador.getString("type") + "-" + indicador.get("id").toString() + "\"";
			} else {
				name = indicador.getString("title");
			}
			dom_ind +=  "        {\r\n" + 
						"            \"created\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
						"            \"id\": \"indicator--"+ rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) +"\",\r\n" + 
						"            \"labels\": [\r\n" + 
						"                \"" + indicador.getString("type") + "\"\r\n" + 
						"            ],\r\n" + 
						"            \"modified\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
						"            \"name\":" + name + ",\r\n" + 
						"            \"pattern\": \"[Domain:value = '" + indicador.getString("indicator") + "']\",\r\n" + 
						"            \"type\": \"indicator\",\r\n" + 
						"            \"valid_from\": \"" + getDate(indicador.getString("created")) + "\"\r\n" +
						"        },\r\n";
		}
		return dom_ind;
	}
	
	protected static String getHostnameIndicators (Alienvault a) throws JSONException, java.text.ParseException {
		String host_ind = "";
		for(int i=0; i<a.getHostname_indicators().length(); i++) {
			JSONObject indicador = a.getHostname_indicators().getJSONObject(i);
			String name = "";
			if (indicador.getString("title").isEmpty()) {
				name = "\" " + a.getAutor() + "-" + indicador.getString("type") + "-" + indicador.get("id").toString() + "\"";
			} else {
				name = indicador.getString("title");
			}
			host_ind += "        {\r\n" + 
			        	"            \"created\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
			        	"            \"id\": \"indicator--" + rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) +"\",\r\n" + 
			        	"            \"labels\": [\r\n" + 
			        	"                \"" + indicador.getString("type") + "\"\r\n" + 
			        	"            ],\r\n" + 
			        	"            \"modified\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
			        	"            \"name\":" + name + ",\r\n" + 
			        	"            \"pattern\": \"[Hostname:value = '" + indicador.getString("indicator") + "']\",\r\n" + 
			        	"            \"type\": \"indicator\",\r\n" + 
			        	"            \"valid_from\": \"" + getDate(indicador.getString("created")) + "\"\r\n" +
			        	"        },\r\n";
		}
		return host_ind;
	}
	
	protected static String getCVEIndicators (Alienvault a) throws JSONException, java.text.ParseException {
		String cve = "";
		for (int i=0; i<a.getCve_indicators().length(); i++) {
			JSONObject indicador = a.getCve_indicators().getJSONObject(i);
			
			cve +=  "        {\r\n" + 
					"            \"type\": \"vulnerability\",\r\n" + 
					"            \"id\": \"vulnerability--" +rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) + "\",\r\n" + 
					"            \"created\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
					"            \"object_marking_refs\": [\r\n" + 
					"                \"marking-definition--71f8e024-2c02-4350-a627-a71fa9de3437\"\r\n" + 
					"            ],\r\n" + 
					"            \"name\": \"" + indicador.getString("indicator") + "\",\r\n" + 
					"            \"description\": \"" + indicador.getString("description").replace(",", "\\,") + "\",\r\n" + 
					"            \"external_references\": [\r\n" + 
					"             {\r\n" + 
					"                \"source_name\": \"cve\",\r\n" + 
					"                \"external_id\": \"" + indicador.getString("indicator") + "\"\r\n" + 
					"             }\r\n" + 
					"            ]\r\n" + 
					"        },";
		}
		return cve;

	}
	
	protected static String getYARAIndicators (Alienvault a) throws JSONException, java.text.ParseException {
		String yara_ind = "";
		for(int i=0; i<a.getYara_indicators().length(); i++) {
			JSONObject indicador = a.getYara_indicators().getJSONObject(i);
			String name = "";
			if (indicador.getString("title").isEmpty()) {
				name = "\" " + a.getAutor() + "-" + indicador.getString("type") + "-" + indicador.get("id").toString() + "\"";
			} else {
				name = indicador.getString("title");
			}
			yara_ind += "        {\r\n" + 
			        "            \"created\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
					"            \"id\": \"indicator--" + rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) +"\",\r\n" + 
					"            \"labels\": [\r\n" + 
					"                \"" + indicador.getString("type") + "\"\r\n" + 
					"            ],\r\n" + 
					"            \"modified\": \"" + getDate(indicador.getString("created")) + "\",\r\n" + 
					"            \"name\":" + name + ",\r\n" + 
					"            \"pattern\": \"[YARA:value = '" + indicador.getString("indicator") + "']\",\r\n" + 
					"            \"type\": \"indicator\",\r\n" + 
					"            \"valid_from\": \"" + getDate(indicador.getString("created")) + "\"\r\n" +
					"        },\r\n";
		}
		return yara_ind;
	}
	
	protected static void createSTIX2fromAlienvault() throws JSONException, IOException, java.text.ParseException {
		Alienvault a = new Alienvault();
		
		
		String urls = getUrlIndicators(a);
		String hashes = getHashIndicators(a);
		String domains = getDomainIndicators(a);
		String hosts = getHostnameIndicators(a);
		String header = createHeader(a);
		String cve = getCVEIndicators(a);
		String yara = getYARAIndicators(a);
		
		String stix2 = "{\r\n" +
				"    \"type\" : \"bundle\",\r\n" +
				"    \"id\": \"bundle--" + rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+ a.getId().substring(0, 12) + "\",\r\n" +
				"    \"spec_version\": \"2.0\",\r\n" +
				"    \"objects\": [\r\n" + header + cve + yara + hosts + domains + hashes + urls + "    ]\r\n" + 
				"}";
		
		print(stix2);
		writefile(stix2.replace(" ", "").replace("\n", "").replace("\r", ""),"alienvault");
	}
	
	protected static String createArtifacts(RiskIQ_CSV riq) throws JSONException, java.text.ParseException {
		String artifacts = "";
		for(int i=0; i<riq.getArtifacts().length(); i++) {
			JSONObject indicador = riq.getArtifacts().getJSONObject(i);
			
//			print(indicador.toString());
			
			Boolean monitoring; if(indicador.get("").equals("Not Monitoring")) { monitoring = false; } else { monitoring =true; }
			String creator = indicador.getString("creator");
			String artifact_type = indicador.getString("artifact_type");
			
			String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date olddate = sdf.parse(indicador.getString("created"));
		    sdf.applyPattern(format);
		    String created = sdf.format(olddate);

		    String query = indicador.getString("query");
			String extra = indicador.getString("extra");
			String tags = indicador.getString("tags");
			
			
			// Las IDs serán aleatorias ya que no hay manera de conseguir unas ids
			String id = rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+rndID(12).toString();

			
			String t = "";
			
			if (tags.isEmpty() ) {
				t = "";
			} else {
				t = "                \"tags:" +  tags + "\",\r\n";
			}
					
					
					
			artifacts += "        {\r\n" + 
			        "            \"created\": \"" + created + "\",\r\n" + 
					"            \"id\": \"indicator--"+ id +"\",\r\n" + 
					"            \"labels\": [\r\n" + 
					"                \"" + artifact_type + "\",\r\n" + 
					t +
					"                \"" + extra + "\"\r\n" +
					"            ],\r\n" + 
					"            \"creator\": \"" + creator + "\",\r\n" +
					"            \"modified\": \"" + created + "\",\r\n" + 
					"            \"name\": \"" + "indicator" + "-" + artifact_type +"-" + created.substring(5,10) + "\",\r\n" + 
					"            \"pattern\": \"[value = '" + query + "']\",\r\n" + 
					"            \"type\": \"indicator\",\r\n" + 
					"            \"valid_from\": \"" + created + "\"\r\n";
			if (i==riq.getArtifacts().length()-1) {
				artifacts += "        }\r\n";
			} else {
				artifacts += "        },\r\n";
			}
			
		}
		return artifacts;
	}
	
	/**
	 * Main method to create STIX2 content from RiskIQ source
	 * 
	 * @throws JSONException
	 * @throws IOException
	 * @throws ParseException
	 * @throws java.text.ParseException 
	 */
	protected static void createSTIX2fromRiskIQ() throws JSONException, IOException, ParseException, java.text.ParseException {
		RiskIQ_CSV riq = new RiskIQ_CSV();
		
		String artifacts = createArtifacts(riq);
		String id = rndID(8).toString()+"-"+rndID(4).toString()+"-"+rndID(4).toString()+"-"+ rndID(4).toString()+"-"+rndID(12).toString();
		
		String stix2 = "{\r\n" +
				"    \"type\" : \"bundle\",\r\n" +
				"    \"id\": \"bundle--" + id + "\",\r\n" +
				"    \"spec_version\": \"2.0\",\r\n" +
				"    \"objects\": [\r\n" + artifacts + "    ]\r\n" + 
				"}";
		
		print(stix2);
		writefile(stix2,"riskiq");
	}
	
	public static void main (String[] args) throws IOException, JSONException, ParseException, java.text.ParseException {
		createSTIX2fromAlienvault();
//		createSTIX2fromRiskIQ();


	}

	/**
	 * Export stixv2 content to a json file
	 * 
	 * @param stix2
	 * @param type
	 * @throws IOException
	 */
	public static void writefile(String stix2, String type) throws IOException {
	    BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\alberto.a.iglesias\\Desktop\\TFG\\" + type + ".json"));
	    writer.write(stix2); 
	    writer.close();
	}
	
	public static String rndID(int length) {
		  
//	    boolean useLetters = true;
//	    boolean useNumbers = true;
//	    String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
//	 
		// chose a Character random from this String 
        String AlphaNumericString =  "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
  
        // create StringBuffer size of AlphaNumericString 
        StringBuilder sb = new StringBuilder(length); 
  
        for (int i = 0; i < length; i++) { 
  
            // generate a random number between 
            // 0 to AlphaNumericString variable length 
            int index 
                = (int)(AlphaNumericString.length() 
                        * Math.random()); 
  
            // add Character one by one in end of sb 
            sb.append(AlphaNumericString 
                          .charAt(index)); 
        } 
  
        return sb.toString(); 
	}
	
	/**
	 * Print JSON
	 * 
	 * @param o
	 */
	protected static void printJson(JSONObject o) {
		// TODO Auto-generated method stub
		System.out.println(JsonWriter.formatJson(o.toString()));
	}

	/**
	 * Print String
	 * 
	 * @param name
	 */
	protected static void print(String name) {
		// TODO Auto-generated method stub
		System.out.println(name);
	}
}
