package Controller;

import DB.SignUpDB;

import java.net.URL;
import java.util.ResourceBundle;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class StoreSignUpController implements Initializable {

	@FXML
	private Button btn_store_idcheck;
	@FXML
	private Button btn_store_signup;
	@FXML
	private Button btn_goback;

	@FXML
	private TextField field_store_signupid;
	@FXML
	private PasswordField field_store_signuppw;
	@FXML
	private PasswordField field_store_signuppwcheck;
	@FXML
	private TextField field_store_signupname;
	@FXML
	private TextField field_store_signupaddress;
	@FXML
	private TextField field_store_signuptel;
	@FXML
	private TextField field_store_signupmoney;

	int check_id = 0; // id üũ�� ���� ���, 1�ΰ��� �ߺ��� ���̵� �ִ�.
	SignUpDB signupdb = new SignUpDB();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO �ڵ� ������ �޼ҵ� ����
		
	sub.goback(btn_goback);
		
	btn_store_idcheck.setOnMouseClicked(new EventHandler<MouseEvent>() { // id �ߺ�Ȯ�� üũ
	

			@Override
			public void handle(MouseEvent event) {
				if (signupdb.checkId(field_store_signupid.getText())) { // id�� �ߺ��̸�
					check_id = 2;
					System.out.println("�ߺ��� ID �Դϴ�.");
					sub.setWindow("�̹� �����ϴ� ID �Դϴ�.");
				} else {
					check_id = 1;
					System.out.println("��밡���� ID �Դϴ�.");
					sub.setWindow("��� ������ ID �Դϴ�.");
				}
			}
		});
	
	btn_store_signup.setOnMouseClicked(new EventHandler<MouseEvent>() { // ȸ������
		@Override
		public void handle(MouseEvent event) {
			String id = field_store_signupid.getText();
			String pw = field_store_signuppw.getText();
			String pw_ck = field_store_signuppwcheck.getText();
			String name = field_store_signupname.getText();
			String address = field_store_signupaddress.getText();
			String tel = field_store_signuptel.getText();
			String coin = field_store_signupmoney.getText();
			int i_coin = Integer.parseInt(coin);

			if (check_id == 1 & pw.contains(pw_ck) & !(pw_ck.equals("")) & !(name.equals(""))
					& !(address.equals("")) & !(tel.equals("")) & !(coin.equals(""))) {

				signupdb.signUpStoreManger(id, pw_ck, name, tel, address, i_coin);
				System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
				sub.setWindow("ȸ�������� �Ϸ�Ǿ����ϴ�.");
				Stage stage = (Stage) btn_goback.getScene().getWindow();
				Parent main = null;
				
				try {
					main = FXMLLoader.load(getClass().getResource("/fxml/IntroView.fxml"));
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
				
				Scene scene = new Scene(main);
				stage.setScene(scene);
				stage.show();
			} else if (check_id == 0) {
				sub.setWindow("ID �ߺ� üũ�� ���ּ���");
			} else {
				sub.setWindow("�� ���� ���� �Է����ּ���");
			}
		}
	});
	
	}

}
