package thoth_gui.config;

import org.json.simple.JSONObject;

public interface Configuration {

    JSONObject exportJSON();
    void importJSON(JSONObject importJson);

}
