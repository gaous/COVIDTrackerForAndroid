package com.Gaous.covidtracker;

import java.io.Serializable;

public class Model implements Serializable {
    private String countryName;
    private String newConfirmed;
    private String totalConfirmed;
    private String newDeaths;
    private String totalDeaths;
    private String newRecovered;
    private String totalRecovered;
    private String slug_name;

    public String getSlug_name() {
        return slug_name;
    }

    public void setSlug_name(String slug_name) {
        this.slug_name = slug_name;
    }



    public String getDetails(boolean isCountryBaseText) {
        if(isCountryBaseText)
            return "As of Today, there are " +
                    this.getTotalConfirmed() + " Total Confirmed cases | " +
                    this.getNewConfirmed() + " Newly Confirmed cases | " +
                    this.getTotalDeaths() + " Total Deaths | " +
                    this.getNewDeaths() + " New Deaths | " +
                    this.getTotalRecovered() + " Total Recovered cases | " +
                    this.getNewRecovered() + " New Recovered cases in " +
                    this.getCountryName();
        else
            return "As of Today, there are " +
                    this.getTotalConfirmed() + " Total Confirmed cases with " +
                    this.getTotalDeaths() + " Total Deaths and " +
                    this.getTotalRecovered() + " Total Recovered cases Globally";
    }

    public void setDetails(String details) {
        this.details = details;
    }

    private String details;

    public Model() {
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public String getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public String getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        this.newDeaths = newDeaths;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public String getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        this.newRecovered = newRecovered;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

}
