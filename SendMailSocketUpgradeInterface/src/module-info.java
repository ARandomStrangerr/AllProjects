module SendMail {
    requires javafx.graphics;
    requires javafx.controls;
    requires java.mail;
    requires org.apache.poi.poi;
    requires org.slf4j;
    exports SendMailSocketUpgradeInterface;
    exports SendMailSocketUpgradeInterface.bin.command;
    exports SendMailSocketUpgradeInterface.bin.smtp;
    exports SendMailSocketUpgradeInterface.ui;
}