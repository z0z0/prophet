package org.prng.prophet.messages;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: shushumiga
 * Date: Mar 9, 2013
 * Time: 12:20:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Location {

    String country;
    String countryCode;
    String location;
    String shortLocation;
    Double latitude;
    Double longitude;

    public Location() { }

    public static JSONObject getInstance(String str) {
        try {
            return new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShortLocation() {
        return shortLocation;
    }

    public void setShortLocation(String shortLocation) {
        this.shortLocation = shortLocation;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
