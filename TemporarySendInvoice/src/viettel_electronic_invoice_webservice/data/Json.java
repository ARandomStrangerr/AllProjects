package viettel_electronic_invoice_webservice.data;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class Json {
    private StringBuilder json;
    private boolean finalize;

    public Json() {
    }

    public void reset() {
        json = new StringBuilder();
        finalize = false;
    }

    public void addGroup(String name, Map<String, String> group) {
        if (json.length() != 0) json.append(",");
        json.append("\"").append(name).append("\":{");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : group.entrySet()) {
            if (sb.length() != 0) sb.append(",");
            sb.append("\"").append(entry.getKey()).append("\":");
            if (isNumeric(entry.getValue()) || isBoolean(entry.getValue())) sb.append(entry.getValue());
            else sb.append("\"").append(entry.getValue()).append("\"");
        }
        json.append(sb).append("}");
    }

    public final void addGroups(String name, List<Map<String, String>> groups) {
        if (json.length() != 0) json.append(",");
        json.append("\"").append(name).append("\":[");
        StringBuilder sb = new StringBuilder();
        for (Map<String, String> group : groups) {
            if (sb.length() != 0) sb.append(",");
            sb.append("{");
            StringBuilder innerSb = new StringBuilder();
            for (Map.Entry<String, String> entry : group.entrySet()) {
                if (innerSb.length() != 0) innerSb.append(",");
                innerSb.append("\"").append(entry.getKey()).append("\":");
                if (isNumeric(entry.getValue()) || isBoolean(entry.getValue())) innerSb.append(entry.getValue());
                else innerSb.append("\"").append(entry.getValue()).append("\"");
            }

            sb.append(innerSb).append("}");
        }
        json.append(sb).append("]");
    }

    @SafeVarargs
    public final void addGroups(String name, Map<String, String>... groups) {
        addGroups(name, Arrays.asList(groups));
    }

    public String getJson() {
        if (!finalize) {
            json.insert(0, "{").append("}");
            finalize = true;
        }
        return json.toString();
    }

    private boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isBoolean(String value) {
        if (value.equals("true") || value.equals("false")) {
            return true;
        } else {
            return false;
        }
    }
}
