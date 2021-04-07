package user_interface;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Help extends SuperWindow {
    private Stage beneathStage;
    private Pane beneathPane;

    public Help(Stage aboveStage, Stage beneathStage, Pane beneathPane) {
        super(aboveStage, "Hướng dẫn");
        this.beneathStage = beneathStage;
        this.beneathPane = beneathPane;
    }

    @Override
    protected Pane setupPane() {
        String message = "Hướng dẫn sử dụng phần mềm gửi e-Mail tự động:\n" +
                "+ Bước 1: khởi tạo danh sách người gửi.\n" +
                "Danh sách người gửi có thể được khởi tạo bằng cách thêm từng người hoặc đọc dữ liệu từ tệp tin \n" +
                "excel (*.xls hoặc *.xlsx) dưới định dạng: \n" +
                "_________________________________________________________________________\n" +
                "Tên người nhận | Địa chỉ e-Mail người nhận | Tên tệp tin dữ liệu đính kèm\n" +
                "---------------|---------------------------|-----------------------------\n" +
                "Người số 1     |tendiachi1@homthu.com      |teptincangui1.pdf            \n" +
                "Người số 2     |tendiachi2@homthu.com      |teptincangui2.jpeg           \n" +
                ". . .\n" +
                "\n+ Bước 2: Lựa chọn thư mục chứa tệp tin đính kèm\n" +
                "Tất cả các tệp tin đính kèm cần được gom lại vào một thứ mục, chỉ dường dẫn đễn thư mục này.\n" +
                "\n+ Bước 3: Tiêu đề của thư\n" +
                "Tiêu đề của thư là bắt buộc, không thể bỏ trống.\n" +
                "\n+ Bước 4: Nội dung thư\n" +
                "Nội dung của thư có thể bỏ trống\n" +
                "\n+ Bước 5: Gửi\n" +
                "sau khi bấm gửi, người dùng sẽ được đưa đến cửa sổ đăng nhập dành cho GMail, hãy đăng nhập và chấp\n" +
                "nhận các thao tác như bình thường.\n" +
                "Mọi thắc mắc xin vui lòng liên hệ:\n" +
                "Điện thoại : 04 3 633-6645\n" +
                "Hòm thư: eiehuong@yahoo.com.vn\n" +
                "\nChân thành cảm ơn vì đã tin tưởng / lựa chọn sản phẩm của công ty chúng tôi.";

        Label displayMessage = new Label(message);
        super.setupControl(super.LABEL_STYLE, "normal", displayMessage);
        displayMessage.getStyleClass().add("non-title");

        Button closeButton = new Button("CLOSE");
        closeButton.setStyle("-fx-background-color: transparent; -fx-font-family: Courier");

        closeButton.setOnAction(event -> {
            stage.close();
            beneathPane.setEffect(null);
        });
        VBox helpPage = new VBox(displayMessage, closeButton);
        helpPage.setAlignment(Pos.CENTER);
        helpPage.setStyle("-fx-background-color: transparent");
        return helpPage;
    }

    @Override
    public void openWindow() {
        beneathPane.setEffect(new GaussianBlur());
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(beneathStage);
        stage.setScene(new Scene(setupPane(), Color.TRANSPARENT));
        stage.show();
    }
}
