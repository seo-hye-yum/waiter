/*
jdk = javaSE jre1.8.0_202
ide =��Ŭ���� 2021.09
fxml = fxml-17.0.1 
ScenceBuilder - 17.0.0.msi

*/



package Controller;


//�ڹ�fx Ŭ����
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
	
	
	@Override
	public void start(Stage stage) throws Exception {
		
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/IntroView.fxml"));

        Scene scene = new Scene(root);
 
        stage.setTitle("DB���� ��������Ʈ");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
	

	public static void main(String[] args) {
		
		launch(args);
		
	}
}
