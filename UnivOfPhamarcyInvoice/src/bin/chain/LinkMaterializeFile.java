package bin.chain;

import chain_of_responsibility.Chain;
import chain_of_responsibility.Link;
import com.google.gson.JsonObject;
import user_interface.PaneAbstract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

public class LinkMaterializeFile extends Link {

    public LinkMaterializeFile(Chain chain) {
        super(chain);
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean handle() {
        List<JsonObject> jsonObjects;
        File file;
        jsonObjects = (List<JsonObject> )chain.getProcessObject();
        for (JsonObject obj : jsonObjects) {
            byte[] encodedFile = obj.get("fileToBytes").getAsString().getBytes();
            byte[] decodedFile = Base64.getDecoder().decode(encodedFile);
            file = new File( ((String) PaneAbstract.getProperty("saveFolder")) + ((char) 92) + obj.get("fileName").getAsString());
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(decodedFile);
            } catch (IOException e){

            }
        }
        return false;
    }
}
