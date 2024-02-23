/**
 * �� Ŭ������ DB���� SQL�� ������ ���� Ŭ������. 
 * ��ȯ�ϴ� ���� ���� ���ϴ� �Լ��� ����Ѵ�.
 * loadDB() DB����
 * changeDB(String query) insert, update.. �� ��ȯ ���� ���� sql�� ����
 * getDBString(String query, String getValue) select �� �˻��Ͽ� string�� ��ȯ
 * getDBInt(String query, String getValue) select�� �˻��Ͽ� int ��ȯ
 * getDBListint(String query, String getValue, String getValue2) select�� �˻��Ͽ� ArrayList��ȯ int Ÿ�Ը� 
 * closeDB() DB��������
 * 
 * �Ű����� 
 * query = sql��,  getValue = ���� ���� ��, getValue1 = �� ���� �� ó�� ,getValue2 = ���� ���� �� ������ 
 * 
 * @version     1.00 21/11/25
 * @author      �㼼��
 * @see         com.javacan.event.SomeEvent
 * @since       Jre1.8.0_202
 */

package DB;

import java.sql.CallableStatement;
//�ڹ� Ŭ����
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DTO {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	PreparedStatement st = null;
	CallableStatement cstmt = null;

	String sql = "";
	String Uri = "jdbc:oracle:thin:@58.239.219.202:1521:XE";
	String Id = "system";
	String Pw = "oracle";
	
	
	public void pstmt() {
		
		
	}
	// DB ���� �Լ�
	public Connection loadDB() {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(Uri, Id, Pw);
			System.out.println("����");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB �ε� ����");
		}
		return null;
	}

	
	// DB�� insert, delete, update �� ���� @������
	public void changeDB(String query) {

		String sql = query;
		try {
			st = conn.prepareStatement(sql);
			st.executeUpdate();
		} catch (SQLException e) {
			// TODO �ڵ� ������ catch ���
			System.out.println("SQL�� ���� ����" + e);
		}

	}
	
	public int order_max(int s_key, int time, Date date) {
		
		int ord_num = 0;

		try {
			conn = DriverManager.getConnection(Uri, Id, Pw);
		    sql = "select max(ord_num) from order_t where store_key = ? and ord_date = ? and where order_time  = ?";
			st = conn.prepareStatement(sql);
			st.setInt(1, s_key);
			st.setDate(2, date);
			st.setInt(3, time);
			rs = st.executeQuery();
			rs.next();
			ord_num = rs.getInt(1);
			closeDB();

		} catch (SQLException e) {
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		}
 
        
        
		return ord_num;
	}	
    /**
    * @�޼����̸� : Change
    * @�ۼ���¥ : 21.06.03
    * @�뵵 : DB ����
    * @author �㼼��
    */
    public void Change(String table, String colum, String change_val, String row,String key){
            try{
            	conn = DriverManager.getConnection(Uri, Id, Pw);
                sql = "UPDATE "+table+" SET "+colum +" = ? WHERE "+row+" = ? ";
                
                st = conn.prepareStatement(sql);
                st.setString(1, change_val);
                st.setString(2, key);
                st.executeUpdate();
                
                closeDB();
            } catch (SQLException e) {
                System.out.println("DB �ε� ����" + e);
            }
    }  

	// DB���� String ���� ��ȯ�ϴ� �Լ�
	public String getDBString(String query, String getValue) {
		String value = "";

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				value = rs.getString(getValue);
			}
			return value;
		} catch (SQLException e) {
			System.out.println("SQL�� ���� ����" + e);
			value = "���� ����";
			return value;
		}

	}

	// DB���� Int ���� ��ȯ�ϴ� �Լ� query = sql / getValue 
	public int getDBInt(String query, String getValue) {

		int value = 0;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				value = rs.getInt(getValue);
			}
			return value;
		} catch (SQLException e) {
			// TODO �ڵ� ������ catch ���
			System.out.println("SQL�� ���� ����" + e);
			return value;
		}

	}

	// DB���� ���� arryalist�� ��ȯ�ϴ� �Լ� @������
	public ArrayList<Integer> getDBList(String query, String getValue, String getValue2) {

		int value = 0;
		int value_2 = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();

		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				value = rs.getInt(getValue);
				value_2 = rs.getInt(getValue2);
				list.add(value * value_2);
			}
			return list;
		} catch (SQLException e) {
			// TODO �ڵ� ������ catch ���
			System.out.println("SQL�� ���� ����" + e);
			return list;
		}

	}
	
	// DB ���� ����
	public void closeDB() { 
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if(cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		}
	}

}
