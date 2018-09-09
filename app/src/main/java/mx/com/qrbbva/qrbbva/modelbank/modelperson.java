package mx.com.qrbbva.qrbbva.modelbank;

import org.json.JSONException;
import org.json.JSONObject;

public class modelperson {

    public JSONObject persona (String nameClass, String value){
        JSONObject object = new JSONObject();
        try {
            object.put(nameClass,value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }








}
