import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cedarsoftware.util.io.JsonWriter;

/**
 * 
 * @author Alberto Gonzalez Iglesias
 * 
 * Extrae el pulso mas reciente de la API de Alienvault
 *
 */
public class Alienvault {

	// URL de la peticion: https://otx.alienvault.com:443/api/v1/pulses/57204e9b3c4c3e015d93cb12?X-OTX-API-KEY=dd92359a50176b3d58e7ceb17ca6caa611698bd49f20a19041f019bfc9b422d4
	// URL BASE: https://otx.alienvault.com:443/api/v1/pulses/
	
	protected static final String API_KEY = "dd92359a50176b3d58e7ceb17ca6caa611698bd49f20a19041f019bfc9b422d4";
	protected static JSONArray Hash_indicators = new JSONArray();
	protected static JSONArray URL_indicators = new JSONArray();
	protected static JSONArray domain_indicators = new JSONArray();
	protected static JSONArray hostname_indicators = new JSONArray();
	protected static JSONArray yara_indicators = new JSONArray();
	protected static JSONArray cve_indicators = new JSONArray();
	protected static String name;
	protected static JSONArray tags;
	protected static String autor;
	protected static String[] references;
	protected static String id;
	protected static String description;
	protected static String creationDate;
	protected static String modifiedDate;
	
	public Alienvault() throws IOException, JSONException, ParseException {
		super();
		// TODO Auto-generated constructor stub
		JSONObject latestsPulses = readJsonFromUrl("https://otx.alienvault.com:443/api/v1/pulses/activity");

	    JSONArray results = latestsPulses.getJSONArray("results");
	    
	    JSONObject pulse = results.getJSONObject(0);
//	    System.out.println(JsonWriter.formatJson(pulse.toString()));
	    
	    autor = pulse.getString("author_name");
	    name = pulse.getString("name");
	    tags = pulse.getJSONArray("tags");
	    id = pulse.get("id").toString();
	    description = pulse.getString("description");
	    
	    String format = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	    
	    java.util.Date date = sdf.parse(pulse.getString("created"));
	    sdf.applyPattern(format);
	    creationDate = sdf.format(date);
	    setCreationDate(creationDate);
//	    print(creationDate);
	    //creationDate = pulse.getString("created");
	    modifiedDate = pulse.getString("modified");
	    
	    JSONArray indicators = pulse.getJSONArray("indicators");
	    
	    int a=0;
	    int b=0;
	    int c=0;
	    int d=0;
	    int e=0;
	    int f=0;
	    
	    for (int i=0; i<indicators.length(); i++) {
	    	if (indicators.getJSONObject(i).get("type").equals("URL")) {
	    		URL_indicators.put(b, indicators.getJSONObject(i));
	    		b++;
	    	} else if (indicators.getJSONObject(i).get("type").equals("FileHash-SHA256") || indicators.getJSONObject(i).get("type").equals("FileHash-MD5")) {
	    		Hash_indicators.put(a, indicators.getJSONObject(i));
	    		a++;
	    	} else if (indicators.getJSONObject(i).get("type").equals("domain")) {
	    		domain_indicators.put(c, indicators.getJSONObject(i));
	    		c++;
	    	} else if (indicators.getJSONObject(i).get("type").equals("hostname")) {
	    		hostname_indicators.put(d, indicators.getJSONObject(i));
	    		d++;
	    	
	    	} else if (indicators.getJSONObject(i).get("type").equals("YARA")) {
	    		yara_indicators.put(e, indicators.getJSONObject(i));
	    		e++;
	    	
	    	} else if (indicators.getJSONObject(i).get("type").equals("CVE")) {
	    		cve_indicators.put(f, indicators.getJSONObject(i));
	    		f++;
	    	
	    	} else {
	    		System.out.println("El indicator con id " + indicators.getJSONObject(i).getLong("id") + " tiene un tipo desconocido.");
	    	}
	    }
	    
//	    System.out.println("Nombre: " + getName());	
	}
	
	private void print(String s) {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		URL myUrl = new URL(url);
	    HttpsURLConnection conn = (HttpsURLConnection)myUrl.openConnection();
	    conn.setRequestProperty("X-OTX-API-KEY", API_KEY);
	    InputStream is = conn.getInputStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      return json;
	    } finally {
	      is.close();
	    }
	  }
	
	public  String getCreationDate() {
		return creationDate;
	}


	public  void setCreationDate(String creationDate) {
		Alienvault.creationDate = creationDate;
	}


	public  String getModifiedDate() {
		return modifiedDate;
	}


	public  void setModifiedDate(String modifiedDate) {
		Alienvault.modifiedDate = modifiedDate;
	}


	public String getDescription() {
		return description.replace("\"", "'").replace("\n", " ");
	}


	public  void setDescription(String description) {
		Alienvault.description = description;
	}


	public  JSONArray getYara_indicators() {
		return yara_indicators;
	}


	public  void setYara_indicators(JSONArray yara_indicators) {
		Alienvault.yara_indicators = yara_indicators;
	}


	public  JSONArray getCve_indicators() {
		return cve_indicators;
	}


	public 
	void setCve_indicators(JSONArray cve_indicators) {
		Alienvault.cve_indicators = cve_indicators;
	}


	public String getId() {
		return id;
	}
	
	public  JSONArray getHash_indicators() {
		return Hash_indicators;
	}

	public  void setHash_indicators(JSONArray hash_indicators) {
		Hash_indicators = hash_indicators;
	}

	public  JSONArray getURL_indicators() {
		return URL_indicators;
	}

	public  void setURL_indicators(JSONArray uRL_indicators) {
		URL_indicators = uRL_indicators;
	}

	public  JSONArray getDomain_indicators() {
		return domain_indicators;
	}

	public  void setDomain_indicators(JSONArray domain_indicators) {
		Alienvault.domain_indicators = domain_indicators;
	}

	public  JSONArray getHostname_indicators() {
		return hostname_indicators;
	}

	public  void setHostname_indicators(JSONArray hostname_indicators) {
		Alienvault.hostname_indicators = hostname_indicators;
	}

	public String getName() {
		return name;
	}

	public  void setName(String name) {
		Alienvault.name = name;
	}

	public  JSONArray getTags() {
		return tags;
	}

	public  void setTags(JSONArray tags) {
		Alienvault.tags = tags;
	}

	public  String getAutor() {
		return autor;
	}

	public  void setAutor(String autor) {
		Alienvault.autor = autor;
	}

	public  String[] getReferences() {
		return references;
	}

	public  void setReferences(String[] references) {
		Alienvault.references = references;
	}

	

	
}


