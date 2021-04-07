package user_interface;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Browser extends SuperWindow{
    private final WebView webView = new WebView();
    private final WebEngine webEngine = webView.getEngine();
    private final HBox primePane = new HBox(webView);
    private final String url;

    public Browser(Stage stage, String url) {
        super(stage, "Đăng nhập");
        this.url = url;
    }

    @Override
    protected Pane setupPane() {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");
        webEngine.load(url);
        return primePane;
    }
}
