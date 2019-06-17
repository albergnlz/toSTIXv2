import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONException;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.simpleflatmapper.csv.CsvParser;
import org.simpleflatmapper.csv.CsvReader;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;

public class RiskIQ_CSV {
	org.json.JSONArray artifacts = new org.json.JSONArray();
	
	public RiskIQ_CSV() throws IOException, JSONException, ParseException {
		super();
		Scanner scanner = new Scanner(new File("C:\\Users\\alberto.a.iglesias\\Downloads\\passivetotal_[object-Object]_artifacts.csv"));
        String csv = "";
        scanner.useDelimiter(",");
        
        while (scanner.hasNext()) {
        	String line = scanner.nextLine();
        	csv += line + "\n";
        }
//        System.out.println(csv);
        scanner.close();
        
        CsvReader reader = CsvParser.reader(csv);
        JsonFactory jsonFactory = new JsonFactory();

        Iterator<String[]> iterator = reader.iterator();
        String[] headers = iterator.next();

        StringWriter writer = new StringWriter();
        try (JsonGenerator jsonGenerator = jsonFactory.createGenerator(writer)) {
    
            jsonGenerator.writeStartArray();
    
            while (iterator.hasNext()) {
                jsonGenerator.writeStartObject();
                String[] values = iterator.next();
                int nbCells = Math.min(values.length, headers.length);
                for(int i = 0; i < nbCells; i++) {
                    jsonGenerator.writeFieldName(headers[i]);
                    jsonGenerator.writeString(values[i]);
                }
                
                jsonGenerator.writeEndObject();
            }
            
            jsonGenerator.writeEndArray();
            
        }
        String j = writer.getBuffer().toString();
        JSONParser parser = new JSONParser();
        org.json.simple.JSONArray json = (org.json.simple.JSONArray) parser.parse(j);
        print(json.toJSONString());
        org.json.JSONArray ja = new org.json.JSONArray(json.toJSONString());
       artifacts = ja;
	}   

		
	public org.json.JSONArray getArtifacts() {
		return artifacts;
	}


	public void setArtifacts(org.json.JSONArray artifacts) {
		this.artifacts = artifacts;
	}


	public static void print (String s) {
		System.out.println(s);
	}
}