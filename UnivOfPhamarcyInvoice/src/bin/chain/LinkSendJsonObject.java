package bin.chain;

import bin.constant.API;
import bin.constant.Address;
import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
                System.out.println(input);
                jsonObj = gson.fromJson(input, JsonObject.class);
                responseObjectsCollection.add(jsonObj);
                br.close();
                bw.close();
                con.disconnect();
                int finalIterationNumber = iterationNumber;
                Platform.runLater(() -> BlockMessagePane.getInstance().setMsg(String.format("Thành công\n%d / %d", finalIterationNumber, sendObjectsCollection.size())));
                iterationNumber++;
            }
        } catch (IOException e) {
            chain.setErrorMessage("Không thể thiết lập kết nối tới Internet");
            e.printStackTrace();
            return false;
        }
        chain.setProcessObject(responseObjectsCollection);
        return true;
    }
}
