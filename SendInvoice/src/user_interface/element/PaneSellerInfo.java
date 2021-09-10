package user_interface.element;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import user_interface.PaneAbstract;

public final class PaneSellerInfo extends PaneAbstract {
    //singleton
    private static PaneSellerInfo cache;

    public static PaneSellerInfo getInstance() {
        if (cache == null) cache = new PaneSellerInfo();
        return cache;
    }

    //actual class
    private final Label titleLabel,
            nameLabel,
            taxCodeLabel,
            addressLabel;
    private final TextField nameTextField,
            taxCodeTextField,
            addressTextField;
    private final HBox wrapperFirstLine,
            wrapperSecondLine;
    private final VBox mainPane;

    private PaneSellerInfo() {
        super(new VBox());
        titleLabel = new Label("Thông tin bên bán");
        nameLabel = new Label("Tên đơn vị");
        taxCodeLabel = new Label("Mã số thuế");
        addressLabel = new Label("Địa chỉ");
        nameTextField = new TextField();
        taxCodeTextField = new TextField();
        addressTextField = new TextField();
        wrapperFirstLine = new HBox();
        wrapperSecondLine = new HBox();
        mainPane = (VBox) super.getMainPane();
        setup();
    }

    @Override
    protected void setup() {
        HBox.setHgrow(nameTextField, Priority.ALWAYS);
        HBox.setHgrow(addressTextField, Priority.ALWAYS);
        nameTextField.setId("sellerCompanyName");
        taxCodeTextField.setId("sellerTaxCode");
        addressTextField.setId("sellerAddress");
        try {
            nameTextField.setText((String) PaneAbstract.getProperty(nameTextField.getId()));
            taxCodeTextField.setText((String) PaneAbstract.getProperty(taxCodeTextField.getId()));
            addressTextField.setText((String) PaneAbstract.getProperty(addressTextField.getId()));
        } catch (NullPointerException ignore) {
        }

        wrapperFirstLine.getChildren().addAll(nameLabel, nameTextField, taxCodeLabel, taxCodeTextField);
        wrapperSecondLine.getChildren().addAll(addressLabel, addressTextField);
        mainPane.getChildren().addAll(titleLabel, wrapperFirstLine, wrapperSecondLine);

        wrapperFirstLine.getStyleClass().addAll("alignment", "spacing");
        wrapperSecondLine.getStyleClass().addAll("alignment", "spacing");
        mainPane.getStyleClass().add("spacing");
    }

    public String getName() {
        return nameTextField.getText();
    }

    public String getTaxCode() {
        return taxCodeTextField.getText();
    }

    public String getAddress() {
        return taxCodeTextField.getText();
    }
}
