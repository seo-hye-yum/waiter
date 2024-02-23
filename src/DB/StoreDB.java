package DB;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import List.MenuList;
import List.StoreList;

public class StoreDB extends DTO {

	String sql;

	/**
	 * @�޼����̸� : checkStore
	 * @�ۼ���¥ : 21.11.29
	 * @�뵵 : STORE_MANGER�� STOREKEY���� �Ű������� �޾� STORE�� Ű���� ������ T ���� key���� ������ �ִ��� ������
	 *     �˻�
	 * @author �㼼��
	 */
	public boolean checkStore(int key) {
		boolean check = false;
		sql = "select * from store where storekey= ?";
		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
			st = conn.prepareStatement(sql);
			st.setInt(1, key);
			rs = st.executeQuery();
			check = rs.next();
			closeDB();

		} catch (SQLException e) {
			System.out.println("���� �˻� ����" + e);
		}
		return check;
	}

	
	public void store_signup_call(int S_KEY, String S_NAME, String S_ADDR, int S_OPEN, int S_CLOSED, String S_TEL,
			int S_TMAX) { // in out parameter
		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
			cstmt = conn.prepareCall("{call ������(?, ?, ?, ?, ?, ?, ?)}");
			cstmt.setInt(1, S_KEY);
			cstmt.setString(2, S_NAME);
			cstmt.setString(3, S_ADDR);
			cstmt.setInt(4, S_OPEN);
			cstmt.setInt(5, S_CLOSED);
			cstmt.setString(6, S_TEL);
			cstmt.setInt(7, S_TMAX);
			cstmt.executeQuery();

			closeDB();
		} catch (SQLException e) {
			System.out.println("DB �ε� ����" + e);
		}
	}
	
	
	public void menu_signup_call(String m_name, int price, int s_key) { // in out parameter
		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
			cstmt = conn.prepareCall("{call �޴����(?, ?, ?)}");
			cstmt.setString(1, m_name);
			cstmt.setInt(2, price);
			cstmt.setInt(3, s_key);
			cstmt.executeQuery();
			closeDB();
		} catch (SQLException e) {
			System.out.println("DB �ε� ����" + e);
		}
	}
	
	//�޴� ��ȯ
	public ArrayList<MenuList> getMenuList(int s_key) {

        ArrayList<MenuList> list = new ArrayList<>();

        try {
        	conn = DriverManager.getConnection(Uri, Id, Pw);
        	sql = "select * from menu where storekey = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, s_key);

            rs = st.executeQuery();
            String value = "";
            int price = 0;
            
            while (rs.next()) {
                    value = rs.getString(1);
                    price = rs.getInt(2);
                    list.add(new MenuList(price, value));
            }
        
            closeDB();
        } catch (SQLException e) {
			System.out.println("DB �ε� ����" + e);
		}
        return list;
    }
	
	
	/**
	 * @�޼����̸� : getStringStoreInformaiton
	 * @�ۼ���¥ : 21.11.28
	 * @�뵵 : �Ű������� ���� id�� �ʵ���� storelist �迭�� �����ϰ� ��ȯ.
	 * @author �㼼��
	 */
	public ArrayList<StoreList> getStringStoreInformaiton(int s_key) {

		ArrayList<StoreList> list = new ArrayList<>();
		String s_name = null;
		String s_addr = null;
		String s_tel = null; 
		int s_open = 0; 
		int s_close = 0; 
		int timemax = 0;

		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
			sql = "select * from store where storekey = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, s_key);
			rs = st.executeQuery();

			while (rs.next()) {
				s_name = rs.getString(2);
				s_addr = rs.getString(3);
				s_open = rs.getInt(4);
				s_close = rs.getInt(5);
				s_tel = rs.getString(6); 
				timemax = rs.getInt(7);

				list.add(new StoreList(s_key, s_name, s_addr, s_tel, s_open, s_close, timemax));
			}

			closeDB();
		} catch (SQLException e) {
			System.out.println("DB �ε� ����" + e);
		}
		return list;
	}

	
	public void ChangeStoreinfo(String s_name, String s_address, int s_open, int s_close, String s_tel, int timemax, 
			int key) {
		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
			sql = "UPDATE STORE SET storename= ?, storeaddress=?, storeopen=?, storeclose=?, storetel=?, timemax=? WHERE storekey = ?";

			st = conn.prepareStatement(sql);
			st.setString(1, s_name);
			st.setString(2, s_address);
			st.setInt(3, s_open);
			st.setInt(4, s_close);
			st.setString(5, s_tel);
			st.setInt(6, timemax);
			st.setInt(7, key);
			st.executeUpdate();

			closeDB();
		} catch (SQLException e) {
			System.out.println("DB �ε� ����" + e);
		}
	}
	
    /**
    * @�޼����̸� : Modifymenu 
    * @�ۼ���¥ : 21.11.30
    * @�뵵 : menu ���̺� �Ű������� ���� ���Ը�, �޴���, ������ �����Ѵ�.  
    * @author �㼼��
    */
    public void Modifymenu(int s_key, String menu_name, int price){
            try{
                
            	conn = DriverManager.getConnection(Uri, Id, Pw);
 
                sql = "UPDATE menu SET menu_name=?, price=?  WHERE storekey = ? and menu_name= ?";
                  
                  
                st = conn.prepareStatement(sql);
               
                st.setString(1, menu_name);
                st.setInt(2, price);
                st.setInt(3, s_key);
                st.setString(4, menu_name);
               
               
                rs=st.executeQuery();
                
                closeDB();
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("DB �ε� ����");
            }
    }
    
    /**
    * @�޼����̸� : Delmenu 
    * @�ۼ���¥ : 21.11.30
    * @�뵵 : �Ű������� ���� �����̸���, �޴��̸��� �˻��ϰ� �ű⿡ �´� menu ���̺� ����.
    * @author �㼼��
    */
    public void Delmenu(String store_name, int s_key){
            try{
            	conn = DriverManager.getConnection(Uri, Id, Pw);
 
                sql = "delete from menu where storekey = ? and menu_name= ?";
                  
                  
                st = conn.prepareStatement(sql);
             
                st.setInt(1, s_key);
                st.setString(2, store_name);
               
               
                rs= st.executeQuery();
                
               closeDB();
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("DB �ε� ����");
            }
    }
    
	public ArrayList<String> getGuestList(int s_key) {

        ArrayList<String> list = new ArrayList<>();

        try {
        	conn = DriverManager.getConnection(Uri, Id, Pw);
        	sql = "SELECT name FROM order_t ot LEFT OUTER JOIN guest g ON (g.key = ot.guest_key) where sto_key = ? and check_key=1";
            st = conn.prepareStatement(sql);
            st.setInt(1, s_key);
            rs = st.executeQuery();
            String value = "";
            
            while (rs.next()) {
                    value = rs.getString(1);
                    list.add(value);
            }
        
            closeDB();
        } catch (SQLException e) {
			System.out.println("�ֹ� �մ� Ȯ�� ����" + e);
		}
        return list;
    }
	
	public int getguestkey(int s_key, String name) {

	    int value = 0;
	       
        try {
        	conn = DriverManager.getConnection(Uri, Id, Pw);
        	sql = "SELECT guest_key FROM order_t ot LEFT OUTER JOIN guest g on (g.key = ot.guest_key) where sto_key = ? and name = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, s_key);
            st.setString(2, name);
            rs = st.executeQuery();
     
            
            while (rs.next()) {
                    value = rs.getInt(1);

            }
    
            closeDB();
        } catch (SQLException e) {
			System.out.println("StoreDB.java �մ� Ű �������� ����" + e);
		}
        return value;
    }
	
	/**
	 * @�޼����̸� : GetOrderMenuList
	 * @�ۼ���¥ : 21.12.04
	 * @�뵵 : �ֹ� �޴� ���
	 * @author �㼼��
	 * prepare
	 */
	public ArrayList<String> GetOrderMenuList(int g_key, int s_key, int ord_num) {

		ArrayList<String> list = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
			sql = "select menuname from order_menu where guest_key = ? and store_key = ? and ord_num = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, g_key);
			st.setInt(2, s_key);
			st.setInt(3, ord_num);
			rs = st.executeQuery();
			String value = "";
			while (rs.next()) {
				value = rs.getString(1);
				list.add(value);
			}
			closeDB();

		} catch (SQLException e) {
			System.out.println("�޴� ��� ���� " + e);
		}
		return list;

	}
	
	
	public int gettotalprice(int s_key, int g_key, int ordnum) {

	    int value = 0;
	       
        try {
        	conn = DriverManager.getConnection(Uri, Id, Pw);
        	sql = "SELECT total_cost FROM order_t where sto_key = ? and guest_key = ? and ord_num = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, s_key);
            st.setInt(2, g_key);
            st.setInt(3, ordnum);
            rs = st.executeQuery();
     
            
            while (rs.next()) {
                    value = rs.getInt(1);

            }
    
            closeDB();
        } catch (SQLException e) {
			System.out.println("StoreDB.java totalprice �������� ����" + e);
		}
        return value;
    }
	
	public int get_ord_num(int s_key, int g_key) {

	    int value = 0;
	       
        try {
        	conn = DriverManager.getConnection(Uri, Id, Pw);
        	sql = "SELECT ord_num FROM order_t where sto_key = ? and guest_key = ?";
            st = conn.prepareStatement(sql);
            st.setInt(1, s_key);
            st.setInt(2, g_key);
            rs = st.executeQuery();
     
            
            while (rs.next()) {
                    value = rs.getInt(1);

            }
    
            closeDB();
        } catch (SQLException e) {
			System.out.println("StoreDB.java totalprice �������� ����" + e);
		}
        return value;
    }

	
    public void final_payment(int s_key, int g_key, int ordnum){
        try{
        	conn = DriverManager.getConnection(Uri, Id, Pw);

        	sql = "UPDATE order_t SET check_key = 2 WHERE sto_key = ? and guest_key = ? and ord_num = ?";

            st = conn.prepareStatement(sql);
         
            st.setInt(1, s_key);
            st.setInt(2, g_key);
            st.setInt(3, ordnum);
            rs= st.executeQuery();
            
           closeDB();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("DB �ε� ����");
        }
}





}
