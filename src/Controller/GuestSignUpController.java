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

public class GuestSignUpController implements Initializable {

	@FXML
	private Button btn_idcheck;
	@FXML
	private Button btn_signup;
	@FXML
	private Button btn_goback;
	@FXML
	private TextField field_signupid;
	@FXML
	private PasswordField field_signuppw;
	@FXML
	private PasswordField field_signuppwcheck;
	@FXML
	private TextField field_signupname;
	@FXML
	private TextField field_signupadress;
	@FXML
	private TextField field_signuptel;
	@FXML
	private TextField field_signupmoney;

	int check_id = 0; // id üũ�� ���� ���, 1�ΰ��� �ߺ��� ���̵� �ִ�.
	SignUpDB signupdb = new SignUpDB();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO �ڵ� ������ �޼ҵ� ����
		
		sub.goback(btn_goback);
		
		btn_idcheck.setOnMouseClicked(new EventHandler<MouseEvent>() { // id �ߺ�Ȯ�� üũ
			@Override
			public void handle(MouseEvent event) {
				if (signupdb.checkId(field_signupid.getText())) { // id�� �ߺ��̸�
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
		
		btn_signup.setOnMouseClicked(new EventHandler<MouseEvent>() { // ȸ������
			@Override
			public void handle(MouseEvent event) {
				String id = field_signupid.getText();
				String pw = field_signuppw.getText();
				String pw_ck = field_signuppwcheck.getText();
				String name = field_signupname.getText();
				String address = field_signupadress.getText();
				String tel = field_signuptel.getText();
				String coin = field_signupmoney.getText();
				int i_coin = Integer.parseInt(coin);

				if (check_id == 1 & pw.contains(pw_ck) & !(pw_ck.equals("")) & !(name.equals(""))
						& !(address.equals("")) & !(tel.equals("")) & !(coin.equals(""))) {

					signupdb.signUpGuest(id, pw, name, tel, address, i_coin);
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
