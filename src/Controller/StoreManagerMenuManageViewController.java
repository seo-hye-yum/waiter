package Controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import DB.StoreDB;
import DB.loginDB;
import List.MenuList;
import List.StoreList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author �㼼��
 */
public class StoreManagerMenuManageViewController implements Initializable {

	@FXML
	private Button btn_menu_registor;
	@FXML
	private Button btn_menu_modify;
	@FXML
	private Button btn_menu_delete;
	@FXML
	private Button btn_store_modify;
	@FXML
	private Button btn_store_register;
	@FXML
	private ListView<String> list_menuname;
	@FXML
	private ListView<String> list_price;
	@FXML
	private TextField field_storename;
	@FXML
	private TextField field_storetel;
	@FXML
	private TextField field_storeaddress;
	@FXML
	private TextField field_max;
	@FXML
	private TextField field_open_time;
	@FXML
	private TextField field_end_time;
	@FXML
	private TextField field_registor_menuname;
	@FXML
	private TextField field_registor_price;

	private List<StoreList> Store_List = new ArrayList<>(); // ���� ���� ��� : String (�����̸�, ����, ����Ÿ��, ���Կ���ó )

	

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
		StoreDB sd = new StoreDB();
		loginDB ld = new loginDB();
		System.out.print("���?");
		String id = IntroViewController.getField; // �α����� ����� id�� ����
		System.out.print("id" + id);
		int s_key = ld.storekey_val(id); // ����� ������ ����� Ű���� �ʱ�ȭ
		System.out.print(s_key);

		// �α����� ID�� store_manger�� ���ٸ�
		if (!(sd.checkStore(s_key))) {
			btn_store_modify.setVisible(false); // ���� ���� ��ư�� �������

			btn_store_register.setOnMouseClicked(new EventHandler<MouseEvent>() { // ���콺 ��Ϲ�ư Ŭ���� �߻� �̺�Ʈ ���� ���� ���� ���
				@Override
				public void handle(MouseEvent event) {
					// �ؽ�Ʈ �ʵ� ������ �ʱ�ȭ
					String s_name = field_storename.getText();
					String s_addr = field_storeaddress.getText();
					String s_tel = field_storetel.getText();
					int s_open = Integer.parseInt(field_open_time.getText());
					int s_close = Integer.parseInt(field_end_time.getText());
					int timemax = Integer.parseInt(field_max.getText());

					if (!(sd.checkStore(s_key))) {

						btn_store_register.setVisible(false);// ���� ��� ��ư�� �������
						btn_store_modify.setVisible(true); // ���� ��ư ����
						sd.store_signup_call(s_key, s_name, s_addr, s_open, s_close, s_tel, timemax);// DB�� ������
					}

				}

			});
		} else {// �α����� ID�� store�� ������
			Store_List = sd.getStringStoreInformaiton(s_key);
			btn_store_register.setVisible(false); // ���� ��� ��ư�� �������

			// �ؽ�Ʈ �ʵ� ������ �ʱ�ȭ
			String s_name = Store_List.get(0).getS_name();
			String s_addr = Store_List.get(0).getS_addr();
			int s_open = Store_List.get(0).getS_open();
			int s_close = Store_List.get(0).getS_close();
			String s_tel = Store_List.get(0).getS_tel();
			int timemax = Store_List.get(0).getTimemax();

			// DB�� �־�� ���� fieldtext�� ���
			field_storename.setText(s_name);
			field_storeaddress.setText(s_addr);
			field_storetel.setText(s_tel);
			field_open_time.setText(Integer.toString(s_open));
			field_end_time.setText(Integer.toString(s_close));
			field_max.setText(Integer.toString(timemax));
			
			//�޴� ���
			sub.Menu_Print(list_menuname, list_price, s_key);

		}

		btn_store_modify.setOnMouseClicked(new EventHandler<MouseEvent>() { // ���� ��ư Ŭ���� ���� ���� ���� ����
			@Override
			public void handle(MouseEvent event) {
				// ��� Textfield�� Null�� �ƴϸ�
				if (!(field_end_time.getText().equals("") & !(field_storename.getText().equals(""))
						& !(field_storetel.getText().equals("")) & !(field_storeaddress.getText().equals(""))
						& !(field_max.getText().equals(""))) & !(field_open_time.getText().equals(""))) {

					// �ؽ�Ʈ �ʵ� ������ �ʱ�ȭ
					String s_name = field_storename.getText();
					String s_addr = field_storeaddress.getText();
					String s_tel = field_storetel.getText();
					int s_open = Integer.parseInt(field_open_time.getText());
					int s_close = Integer.parseInt(field_end_time.getText());
					int timemax = Integer.parseInt(field_max.getText());

					sd.ChangeStoreinfo(s_name, s_addr, s_open, s_close, s_tel, timemax, s_key);// s_key���� ���� ���� ����

					sub.setWindow("������������");
				} else {
					sub.setWindow("��� ĭ�� ä���ּ���");
				}
			}
		});

		btn_menu_registor.setOnMouseClicked(new EventHandler<MouseEvent>() {// �޴� ��� ��ư Ŭ���� ���� ���� �޴� ���
			@Override
			public void handle(MouseEvent event) {
				// ��� Textfield�� Null�� �ƴϸ�
				if (!(field_registor_menuname.getText().equals("") & !(field_registor_price.getText().equals("")))) {

					// �ؽ�Ʈ �ʵ� ������ �ʱ�ȭ
					String m_name = field_registor_menuname.getText();
					int m_price = Integer.parseInt(field_registor_price.getText());

					sd.menu_signup_call(m_name, m_price, s_key); // DB�� �޴� ���
					sub.setWindow(m_name + "�޴��� �߰� �߽��ϴ�");
					
					//�޴� ���
					sub.Menu_Print(list_menuname, list_price,  s_key);

				}
			}

		});

		btn_menu_modify.setOnMouseClicked(new EventHandler<MouseEvent>() {// �޴� ���� ��ư Ŭ���� ���� ���� �޴� ���� ����
			@Override
			public void handle(MouseEvent event) {

				String A = field_registor_menuname.getText();
				if (!(field_registor_menuname.getText().equals("") & !(field_registor_price.getText().equals("")))) {
					
					// �ؽ�Ʈ �ʵ� ������ �ʱ�ȭ
					String m_name = field_registor_menuname.getText();
					int m_price = Integer.parseInt(field_registor_price.getText());

					sd.Modifymenu(s_key, m_name, m_price);// DB�� �޴� ����
					sub.setWindow(m_name + " �޴��� ���� �߽��ϴ�");
					
					//�޴� ���
					sub.Menu_Print(list_menuname, list_price,  s_key);

				}

			}
		});

		btn_menu_delete.setOnMouseClicked(new EventHandler<MouseEvent>() {// �޴� ���� ��ư Ŭ���� ���� ���� �޴� ����
			@Override
			public void handle(MouseEvent event) {

				if (!(field_registor_menuname.getText().equals(""))) {
					
					// �ؽ�Ʈ �ʵ� ������ �ʱ�ȭ
					String m_name = field_registor_menuname.getText();
					
					sd.Delmenu(m_name, s_key); // DB�� �޴� ����
					sub.setWindow(m_name + " �޴��� �����߽��ϴ�");
					
					//�޴� ���
					sub.Menu_Print(list_menuname, list_price, s_key);
				}
			}

		});

		list_menuname.setOnMouseClicked(new EventHandler<MouseEvent>() { // ����Ʈ�� Ŭ�� �� �޴��̸� �ؽ�Ʈ�ڽ��� ���
			@Override
			public void handle(MouseEvent event) {
				Object obj = list_menuname.getSelectionModel().getSelectedItem();
				field_registor_menuname.setText(obj.toString());

			}
		});

	}

}