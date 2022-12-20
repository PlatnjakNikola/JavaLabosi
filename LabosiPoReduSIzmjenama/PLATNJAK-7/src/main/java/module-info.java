module com.example.platnjak7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;


    opens hr.java.vjezbe.glavna to javafx.fxml;
    exports hr.java.vjezbe.glavna;
    exports hr.java.vjezbe.glavna.search;
    opens hr.java.vjezbe.glavna.search to javafx.fxml;
}