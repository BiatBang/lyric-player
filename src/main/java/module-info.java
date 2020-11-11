module org.junbang {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jsoup;
    requires spotify.web.api.java;
    requires org.apache.httpcomponents.core5.httpcore5;
    requires com.google.gson;

    opens org.junbang to javafx.fxml;
    exports org.junbang;
}