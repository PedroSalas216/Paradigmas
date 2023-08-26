package parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import subscription.SingleSubscription;
import subscription.Subscription;

/*
 * Esta clase implementa el parser del  archivo de suscripcion (json)
 * Leer https://www.w3docs.com/snippets/java/how-to-parse-json-in-java.html
 * */
public class SubscriptionParser extends GeneralParser implements Serializable {

    Path filePath;

    public SubscriptionParser(Path filePath) {
        this.filePath = filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFilePath() {
        return filePath;
    }

    /**
     * @return Lista de subscripciones
     */
    public Subscription parse(){
        Subscription result = new Subscription(null);
        try (
            FileReader fileReader = new FileReader(filePath.toString())) {
            // leemos el array del json
            JSONArray jsonArray = new JSONArray(new JSONTokener(fileReader));
            
            // por cada elemento
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    
                    // Parseamos el json y creamos variables para despues crear la subscripcion
                    String url = jsonObject.getString("url");
                    String urlType = jsonObject.getString("urlType");
                    JSONArray urlParamsRaw = jsonObject.getJSONArray("urlParams");
                    List<String> urlParams = new ArrayList<>();
                    
                    if (urlParamsRaw!=null) {
                        for (int j = 0; j < urlParamsRaw.length(); j++) {
                            urlParams.add(urlParamsRaw.getString(j));
                        }
                    }

                    // Creamos la subscripcion y la agregamos a la lista
                    SingleSubscription subscription = new SingleSubscription(url, urlParams, urlType);
                    result.addSingleSubscription(subscription);

                } catch (JSONException e) {
                    System.out.printf("Problema parseando el archivo json: %s\n", e.getCause().toString());
                }
            }
        } catch(FileNotFoundException e) {
			System.out.printf("No se encontro el archivo: %s\n", e.getCause().toString());
        } catch (IOException e) {
			System.out.printf("Problema en la conexión con el servidor: %s\n", e.getCause().toString());
        } catch (JSONException e) {
            System.out.printf("Problema parseando el archivo json: %s\n", e.getCause().toString());
        } catch (Exception e) {
            System.out.printf("error: %s\n", e.getCause().toString());
        }

        return result;
    }

	public static void main(String[] args) {
		System.out.println("SubscriptionParserClass");
        SubscriptionParser subscriptionParser = new SubscriptionParser(Paths.get("config/subscriptions.json"));
		Subscription subscriptionList = subscriptionParser.parse();

        SingleSubscription subscription = subscriptionList.getSingleSubscription(0);

        System.out.println(subscription.toString());
    }
}
