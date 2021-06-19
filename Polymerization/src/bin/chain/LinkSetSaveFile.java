package bin.chain;

import chain_of_responsibility.Link;

public class LinkSetSaveFile extends Link {
    private final String configFileName;

    public LinkSetSaveFile(String configFileName) {
        this.configFileName = configFileName;
    }

    @Override
    protected boolean handler(Object o) {
        System.setProperty("configFileName", configFileName);
        return true;
    }
}
