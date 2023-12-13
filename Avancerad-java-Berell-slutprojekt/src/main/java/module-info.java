module org.example.testfirebase {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens org.example.testfirebase to javafx.fxml;
    exports org.example.testfirebase;
}