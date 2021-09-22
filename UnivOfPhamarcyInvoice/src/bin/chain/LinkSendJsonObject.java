package bin.chain;

import bin.constant.API;
import bin.constant.Address;
import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import file_operation.TextFile;
import javafx.application.Platform;
import user_interface.BlockMessagePane;
import user_interface.PaneAbstract;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

public final class LinkSendJsonObject extends Link {
    private final String address;

    public LinkSendJsonObject(Chain chain,
                              String address) {
        super(chain);
        this.address = address;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean handle() {
        List<JsonObject> sendObjectsCollection,
                responseObjectsCollection;
        JsonObject jsonObj;
        HttpURLConnection con;
        BufferedWriter bw;
        BufferedReader br;
        Gson gson;
        String accessToken;
        int iterationNumber;
        try {
            sendObjectsCollection = (List<JsonObject>) chain.getProcessObject();
        } catch (ClassCastException e) {
            chain.setErrorMessage("Vui lòng liên hệ với công ty phần mềm để sửa lỗi này");
            e.printStackTrace();
            return false;
        }
        //get token
        jsonObj = new JsonObject();
        jsonObj.addProperty("username", (String) PaneAbstract.getProperty("username"));
        jsonObj.addProperty("password", (String) PaneAbstract.getProperty("password"));
        try {
            con = (HttpURLConnection) new URL(Address.ADDRESS_AUTH.value + API.ACCESS_TOKEN.value).openConnection();
            con.setRequestMethod("POST");
            con.addRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            bw.write(jsonObj.toString());
            bw.newLine();
            bw.flush();
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            gson = new Gson();
            jsonObj = gson.fromJson(br.readLine(), JsonObject.class);
            bw.close();
            br.close();
            con.disconnect();
        } catch (IOException e) {
            chain.setErrorMessage("Không thể thiết lập kết nối tới Internet");
            e.printStackTrace();
            return false;
        }
        accessToken = String.format("access_token=%s", jsonObj.get("access_token").getAsString());
        try {
            TextFile.getInstance().write("debug.txt", false, address);
            TextFile.getInstance().write("debug.txt", true, accessToken);
        } catch (IOException e) {
        }
        //send object
        responseObjectsCollection = new LinkedList<>();
        iterationNumber = 1;
        try {
            for (JsonObject jsonObject : sendObjectsCollection) {
                con = (HttpURLConnection) new URL(address).openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Type", "application/json");
                con.setRequestProperty("Cookie", accessToken);
                con.setDoOutput(true);
                con.setUseCaches(false);

                bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
                bw.write(jsonObject.toString());
                bw.newLine();
                bw.flush();
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input = br.readLine();
                jsonObj = gson.fromJson(input, JsonObject.class);
                try {
                    if (!jsonObj.get("description").getAsString().isEmpty()) {
                        chain.setErrorMessage("Lỗi ở bản ghi " + iterationNumber + " - " + jsonObj.get("description").getAsString());
                        chain.setProcessObject(responseObjectsCollection);
                        return false;
                    }
                } catch (UnsupportedOperationException e) {
                    if (!jsonObj.get("description").isJsonNull()) {
                        chain.setErrorMessage("Lỗi ở bản ghi " + iterationNumber + " - " + jsonObj.get("description").getAsString());
                        chain.setProcessObject(responseObjectsCollection);
                        return false;
                    }
                }

                responseObjectsCollection.add(jsonObj);
                br.close();
                bw.close();
                con.disconnect();
                int finalIterationNumber = iterationNumber;
                Platform.runLater(() -> BlockMessagePane.getInstance().setMsg(String.format("Thành công\n%d / %d", finalIterationNumber, sendObjectsCollection.size())));
                iterationNumber++;
            }
        } catch (IOException e) {
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            try {
                System.out.println(br.readLine());
            } catch (IOException e1) {
                chain.setErrorMessage(e1.getMessage());
                e1.printStackTrace();
                return false;
            }
            chain.setErrorMessage(e.getMessage());
            e.printStackTrace();
            return false;
        }
        chain.setProcessObject(responseObjectsCollection);
        return true;
    }
}