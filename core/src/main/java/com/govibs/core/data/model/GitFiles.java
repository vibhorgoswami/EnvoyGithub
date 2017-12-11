package com.govibs.core.data.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.json.JSONObject;

import java.io.Serializable;

/**
 *
 * Created by Vibhor on 12/10/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class GitFiles implements Serializable {

    private JSONObject info;

    public JSONObject getInfo() {
        return info;
    }

    public void setInfo(JSONObject info) {
        this.info = info;
    }

}
