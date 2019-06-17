import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.vocabulary.XSD;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

public class STIXContentToOWL {

	
    public static void main( String[] args ) throws IOException, JSONException, ParseException, java.text.ParseException {
    	//Creamos nuestro modelo
    	OntModel model = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);

    	//Establecemos el NameSpace por defecto para nuestra ontología
    	String NS = "stix2";
    	model.setNsPrefix(NS, "http://www.owl-ontologies.com/OntologySTIX.owl");
    	
    	//Clases. Hay que crearlas con el NameSpace definido
    	OntClass Bundle = model.createClass(NS+":"+"Bundle");
    	OntClass Indicator = model.createClass(NS+":"+"Indicator");
    	OntClass Artifact = model.createClass(NS+":"+"Artifact");

    	Indicator.setDisjointWith(Artifact);
    	Bundle.addSubClass(Indicator);
    	Bundle.addSubClass(Artifact);

    	// PROPIEDADES DE INDICATOR (name, type, validFrom & pattern)
    	DatatypeProperty name = model.createDatatypeProperty(NS+":"+"Name");
    	name.addDomain(Indicator);												//Clase a la que pertenece
    	name.addRange(XSD.xint);												//Tipo de la propiedad
    	name.convertToFunctionalProperty();										//Para que solo acepte un valor.
    	
    	DatatypeProperty type = model.createDatatypeProperty(NS+":"+"Type");
    	type.addDomain(Indicator);												//Clase a la que pertenece
    	type.addRange(XSD.xint);												//Tipo de la propiedad
    	type.convertToFunctionalProperty();										//Para que solo acepte un valor.
    	
    	DatatypeProperty validFrom = model.createDatatypeProperty(NS+":"+"validFrom");
    	validFrom.addDomain(Indicator);											//Clase a la que pertenece
    	validFrom.addRange(XSD.xint);											//Tipo de la propiedad
    	validFrom.convertToFunctionalProperty();								//Para que solo acepte un valor.
    	
    	DatatypeProperty pattern = model.createDatatypeProperty(NS+":"+"Pattern");
    	pattern.addDomain(Indicator);											//Clase a la que pertenece
    	pattern.addRange(XSD.xint);												//Tipo de la propiedad
    	pattern.convertToFunctionalProperty();									//Para que solo acepte un valor.

    	
    	// PROPIEDADES DE ARTIFACT (name, createdByRef, validFrom & pattern)
    	name.addDomain(Artifact);												//Clase a la que pertenece
    	name.addRange(XSD.xint);												//Tipo de la propiedad
    	name.convertToFunctionalProperty();										//Para que solo acepte un valor.
    	
    	DatatypeProperty createdByRef = model.createDatatypeProperty(NS+":"+"createdByRef");
    	createdByRef.addDomain(Artifact);										//Clase a la que pertenece
    	createdByRef.addRange(XSD.xint);										//Tipo de la propiedad
    	createdByRef.convertToFunctionalProperty();								//Para que solo acepte un valor.
    	
    	validFrom.addDomain(Artifact);											//Clase a la que pertenece
    	validFrom.addRange(XSD.xint);											//Tipo de la propiedad
    	validFrom.convertToFunctionalProperty();								//Para que solo acepte un valor.
    	
    	pattern.addDomain(Artifact);											//Clase a la que pertenece
    	pattern.addRange(XSD.xint);												//Tipo de la propiedad
    	pattern.convertToFunctionalProperty();									//Para que solo acepte un valor.
    	
    	
    	/**
    	 *  INSTANCIAS -- Se da valor a las propiedades
    	 */
    	
    	/**
    	 * INDICADORES DE ALIENVAULT
    	 */
    	Alienvault a = new Alienvault();
		
		// CVE
		JSONArray cves = toJSON(stix2withJsonFormat.getCVEIndicators(a));
		try {
			JSONObject cve = cves.getJSONObject(0); 
			Individual indicatorCVE = model.createIndividual(NS+":"+"indicatorCVE",Indicator);
			indicatorCVE.setPropertyValue(name, model.createTypedLiteral(cve.getString("name")));
			indicatorCVE.setPropertyValue(type, model.createTypedLiteral(cve.getString("type")));
			indicatorCVE.setPropertyValue(validFrom, model.createTypedLiteral(cve.getString("valid_from")));
			indicatorCVE.setPropertyValue(pattern, model.createTypedLiteral(cve.getString("pattern")));
		} catch (Exception e) {
			print("Array de CVEs vacio");
		}

		// YARA
		JSONArray yaras = toJSON(stix2withJsonFormat.getYARAIndicators(a));
		try {
			JSONObject yara = yaras.getJSONObject(0); 
			Individual indicatorYARA = model.createIndividual(NS+":"+"indicatorYARA",Indicator);
			indicatorYARA.setPropertyValue(name, model.createTypedLiteral(yara.getString("name")));
			indicatorYARA.setPropertyValue(type, model.createTypedLiteral(yara.getString("type")));
			indicatorYARA.setPropertyValue(validFrom, model.createTypedLiteral(yara.getString("valid_from")));
			indicatorYARA.setPropertyValue(pattern, model.createTypedLiteral(yara.getString("pattern")));
		} catch (Exception e) {
			print("Array de YARAs vacio");
		}
				
				
		// URLs
		JSONArray urls = toJSON(stix2withJsonFormat.getUrlIndicators(a));
		try {
			JSONObject url = urls.getJSONObject(0); 
			Individual indicatorUrl = model.createIndividual(NS+":"+"indicatorUrl",Indicator);
			indicatorUrl.setPropertyValue(name, model.createTypedLiteral(url.getString("name")));
			indicatorUrl.setPropertyValue(type, model.createTypedLiteral(url.getString("type")));
			indicatorUrl.setPropertyValue(validFrom, model.createTypedLiteral(url.getString("valid_from")));
			indicatorUrl.setPropertyValue(pattern, model.createTypedLiteral(url.getString("pattern")));
		} catch (Exception e) {
			print("Array de urls vacio");
		}
				
		// HASHES
		JSONArray hashes = toJSON(stix2withJsonFormat.getHashIndicators(a));
		try {
			JSONObject hash = hashes.getJSONObject(0); 
			Individual indicatorHash = model.createIndividual(NS+":"+"indicatorHash",Indicator);
			indicatorHash.setPropertyValue(name, model.createTypedLiteral(hash.getString("name")));
			indicatorHash.setPropertyValue(type, model.createTypedLiteral(hash.getString("type")));
			indicatorHash.setPropertyValue(validFrom, model.createTypedLiteral(hash.getString("valid_from")));
			indicatorHash.setPropertyValue(pattern, model.createTypedLiteral(hash.getString("pattern")));
		} catch (Exception e) {
			print("Array de hashes vacio");
		}
		
		// DOMAINS
		JSONArray domains = toJSON(stix2withJsonFormat.getDomainIndicators(a));
		try {
			JSONObject domain = domains.getJSONObject(0); 
	    	Individual indicatorDomain = model.createIndividual(NS+":"+"indicatorDomain",Indicator);
	    	indicatorDomain.setPropertyValue(name, model.createTypedLiteral(domain.getString("name")));
	    	indicatorDomain.setPropertyValue(type, model.createTypedLiteral(domain.getString("type")));
	    	indicatorDomain.setPropertyValue(validFrom, model.createTypedLiteral(domain.getString("valid_from")));
	    	indicatorDomain.setPropertyValue(pattern, model.createTypedLiteral(domain.getString("pattern")));
		} catch (Exception e) {
			print("Array de domains vacio");
		}

		// HOSTS
		JSONArray hosts = toJSON(stix2withJsonFormat.getHostnameIndicators(a));
		try {
			JSONObject host = hosts.getJSONObject(0); 
	    	Individual indicatorDomain = model.createIndividual(NS+":"+"indicatorHostname",Indicator);
	    	indicatorDomain.setPropertyValue(name, model.createTypedLiteral(host.getString("name")));
	    	indicatorDomain.setPropertyValue(type, model.createTypedLiteral(host.getString("type")));
	    	indicatorDomain.setPropertyValue(validFrom, model.createTypedLiteral(host.getString("valid_from")));
	    	indicatorDomain.setPropertyValue(pattern, model.createTypedLiteral(host.getString("pattern")));
		} catch (Exception e) {
			print("Array de hosts vacio");
		}
		
    	/**
    	 * ARTIFACTS DE RISKIQ
    	 */
		RiskIQ_CSV riq = new RiskIQ_CSV();
    	
		// Artifact 1
		JSONObject indicador1 = riq.getArtifacts().getJSONObject(0);
    	String creator1 = indicador1.getString("creator");
		String artifact_type1 = indicador1.getString("artifact_type");
		String format1 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date olddate1 = sdf1.parse(indicador1.getString("created"));
	    sdf1.applyPattern(format1);
	    String created1 = sdf1.format(olddate1);
	    String query1 = indicador1.getString("query");
		String nameArtifact1 = "indicator" + "-" + artifact_type1 +"-" + created1.substring(5,10);
    	
    	Individual artifact1 = model.createIndividual(NS+":"+"Artifact1", Artifact);
    	artifact1.setPropertyValue(name, model.createTypedLiteral(nameArtifact1));
    	artifact1.setPropertyValue(createdByRef, model.createTypedLiteral(creator1));
    	artifact1.setPropertyValue(validFrom, model.createTypedLiteral(created1));
    	artifact1.setPropertyValue(pattern, model.createTypedLiteral("[value = '" + query1 + "']"));

    	// Artifact 2
    	JSONObject indicador2 = riq.getArtifacts().getJSONObject(1);
    	String creator2 = indicador2.getString("creator");
		String artifact_type2 = indicador2.getString("artifact_type");
		String format2 = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date olddate2 = sdf2.parse(indicador2.getString("created"));
	    sdf2.applyPattern(format2);
	    String created2 = sdf2.format(olddate2);
	    String query2 = indicador2.getString("query");
		String nameArtifact2 = "indicator" + "-" + artifact_type2 +"-" + created2.substring(5,10);
    	
    	Individual artifact2 = model.createIndividual(NS+":"+"Artifact2", Artifact);
    	artifact2.setPropertyValue(name, model.createTypedLiteral(nameArtifact2));
    	artifact2.setPropertyValue(createdByRef, model.createTypedLiteral(creator2));
    	artifact2.setPropertyValue(validFrom, model.createTypedLiteral(created2));
    	artifact2.setPropertyValue(pattern, model.createTypedLiteral("[value = '" + query2 + "']"));
    	
    	
    	// Guardar la ontología en un fichero OWL
    	File file = new File("C:\\Users\\alberto.a.iglesias\\Desktop\\TFG\\newSTIX2.owl");
    	if (!file.exists()) {
    	     file.createNewFile();
    	}
    	model.write(new PrintWriter(file));
    	
    	// Leer los datos de la ontología creada
    	for (Iterator<OntClass> i = model.listClasses();i.hasNext();){
    		OntClass cls = i.next();
    		System.out.print(cls.getLocalName()+": ");
    		for(Iterator it = cls.listInstances(true);it.hasNext();){
    			Individual ind = (Individual)it.next();
    			if(ind.isIndividual()){
    				System.out.print(ind.getLocalName()+" ");
    			}
    		}
    		System.out.println();
    	}   	
    }

	private static void print(String s) {
		System.out.println(s);
	}
	
	protected static JSONArray toJSON(String s) throws JSONException {
		String r = "[";
		r += s.replace("\n", "").replace("\r", "").replace(" ", "");
		r += "]";
    	print(r.toString());
    	JSONArray j = new JSONArray(r);
    	return j;
	}
}
