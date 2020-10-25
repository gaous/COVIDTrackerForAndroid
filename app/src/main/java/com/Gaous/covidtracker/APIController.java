package com.Gaous.covidtracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class APIController {
    public void setCollectedDataFromAPI(String collectedDataFromAPI) {
        this.collectedDataFromAPI = collectedDataFromAPI;
    }

    private String collectedDataFromAPI;


    public Model getDataFromAPI(String countryCategory, boolean isGlobal, String countryName) throws JSONException {
        Model model = new Model();
        String data_obj = collectedDataFromAPI;
        JSONObject obj = new JSONObject(data_obj);
        if(countryCategory.equals("Global") && isGlobal && countryName.equals("")){
            JSONObject tempObj = obj.getJSONObject("Global");
            model.setTotalConfirmed(tempObj.get("TotalConfirmed").toString());
            model.setTotalDeaths(tempObj.get("TotalDeaths").toString());
            model.setTotalRecovered(tempObj.get("TotalRecovered").toString());
        }
        else if(countryCategory.equals("Countries") && !isGlobal && !countryName.equals("")){
            JSONArray tempObj = obj.getJSONArray("Countries");

            for (int i = 0; i < tempObj.length(); i++) {
                Object o = tempObj.get(i);
                JSONObject new_obj = (JSONObject) o;
                if (new_obj.get("Country").toString().toLowerCase().equals(countryName.toLowerCase())) {
                    model.setSlug_name(new_obj.get("Slug").toString());
                    model.setCountryName(new_obj.get("Country").toString());
                    model.setNewConfirmed(new_obj.get("NewConfirmed").toString());
                    model.setTotalConfirmed(new_obj.get("TotalConfirmed").toString());
                    model.setTotalDeaths(new_obj.get("TotalDeaths").toString());
                    model.setTotalRecovered(new_obj.get("TotalRecovered").toString());
                    model.setNewDeaths(new_obj.get("NewDeaths").toString());
                    model.setNewRecovered(new_obj.get("NewRecovered").toString());
                }
            }
        }
        return model;
    }
}

