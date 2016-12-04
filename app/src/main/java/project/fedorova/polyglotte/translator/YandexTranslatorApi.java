package project.fedorova.polyglotte.translator;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import static android.content.ContentValues.TAG;

public class YandexTranslatorApi {
    public static final String ENCODING = "UTF-8";
    private String apiKey;
    private String referrer;
    public static final String PARAM_KEY = "key=";
    public static final String PARAM_LANGS = "&lang=";
    public static final String PARAM_TEXT = "&text=";

    public void setApiKey() {
        apiKey = ApiKey.YANDEX_API_KEY;
    }

    public void setReferrer(final String pReferrer) {
        referrer = pReferrer;
    }

    /**
     * Forms an HTTPS request, sends it using GET method and returns the result of the request as a String.
     *
     * @param url The URL to query for a String response.
     * @return The translated String.
     * @throws Exception on error.
     */
    private  String getResponse(final URL url) throws Exception {
        final HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
        if (referrer != null) {
            uc.setRequestProperty("referrer", referrer);
        }
        uc.setRequestProperty("Content-Type", "text/plain; chrset=" + ENCODING);
        uc.setRequestProperty("Accept-Charset", ENCODING);
        uc.setRequestMethod("GET");

        try {
            final int responseCode = uc.getResponseCode();
            final String result = inputStreamToString(uc.getInputStream());
            if (responseCode != 200) {
                throw new Exception("Error from Yandex API: " + result);
            }
            return result;
        } finally {
            if (uc != null) {
                uc.disconnect();
            }
        }
    }

    /**
     * Reads an InputStream and returns its contents as a String.
     *
     * @param inputStream The InputStream to read from.
     * @return The contents of the InputStream as a String.
     * @throws Exception on error.
     */
    private static String inputStreamToString(final InputStream inputStream) throws Exception {
        final StringBuilder outputBuilder = new StringBuilder();

        try {
            String string;
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                while (null != (string = reader.readLine())) {
                    outputBuilder.append(string.replaceAll("\uFEFF", ""));
                }
            }
        } catch (Exception ex){
            throw new Exception("[yandex-translator-api] Error reading translation stream.", ex);
        }
        return outputBuilder.toString();
    }

    /**
     * Forms a request, sends it using the GET method and returns the contents of the array of strings
     * with the given label, with multiple strings concatenated.
     */
    public String getPropArrString(final URL url, final String jsonValProperty) throws Exception {
        final String[] combinedTranslations = {"", ""};
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String response = getResponse(url);
                    String[] translationArr = jsonObjValToStringArr(response, jsonValProperty);
                    for (String s : translationArr) {
                        combinedTranslations[0] += s;
                    }
                } catch (Exception e) {
                    combinedTranslations[1] = e.getMessage();
                }
            }
        });
        thread.start();
        thread.join();
        if (combinedTranslations[1].length() > 0) {
            throw new Exception(combinedTranslations[1]);
        }
        return combinedTranslations[0].trim();
    }

    // Helper method to parse a JSONArray. Reads an array of JSONObjects and returns a String Array
    // containing the toString() of the desired property. If propertyName is null, just return the String value.
    private static String[] jsonObjValToStringArr(final String inputString, final String propertyName) throws Exception {
        JSONArray jsonArr = new JSONArray();
        Object tmp = JSONValue.parse(inputString);
        try {
            jsonArr.add((JSONObject) tmp);
        } catch (Exception e) {
            jsonArr = (JSONArray) tmp;
        }
        String[] values = new String[jsonArr.size()];
        for (int i = 0; i < jsonArr.size(); i++) {
            Object obj = (JSONObject) jsonArr.get(i);
            if (propertyName != null && propertyName.length() != 0) {
                final JSONObject json = (JSONObject) obj;
                if (json.containsKey(propertyName)) {
                    values[i] = json.get(propertyName).toString();
                }
            } else {
                values[i] = obj.toString();
            }
        }
        return values;
    }

    public void validServiceState() throws Exception {
        if (apiKey == null || apiKey.length() < 27) {
            throw new RuntimeException("INVALID_API_KEY - Please set the API Key with your Yandex API Key");
        }
    }


    public String getApiKey() {
        return apiKey;
    }

    public String getReferrer() {
        return referrer;
    }
}
