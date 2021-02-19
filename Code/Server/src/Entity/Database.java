package Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Database {

	private Connection conn;
	private static final String USERNAME = "root";
	private static final String PASSWORD = "gladstone33";
	private String tableName;
	private String schemaName;

	public Database(String schemaName, String tableName) {
		this.schemaName = schemaName;
		this.tableName = tableName;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost/" + schemaName + "?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String showTable(String type) {
		try {
			checkExcist(type);
			String show = "select * from " + tableName + ";";
			Statement stmt2 = conn.createStatement();
			ResultSet showRS = stmt2.executeQuery(show);
			Vector<String> VColumn = getColumnName();
			String result = "";
			while (showRS.next()) {
				for (int i = 0; i < VColumn.size(); i++) {
					result += showRS.getString(VColumn.get(i)) + " ";
				}
				result += "\n";
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public void addRow(Vector<String> selected) {
		try {
			Vector<String> VColumn = getColumnName();
			String insert1 = "insert into " + tableName + "(" + VColumn.get(0);
			String insert2 = ") values(?";
			for (int i = 1; i < VColumn.size(); i++) {
				insert1 += ", " + VColumn.get(i);
				insert2 += ", ?";
			}
			String insertF = insert1 + insert2 + ");";
			PreparedStatement pstmt = conn.prepareStatement(insertF);
			pstmt.setString(1, selected.get(0));
			for (int i = 1; i < selected.size(); i++) {
				pstmt.setString(i + 1, selected.get(i));
			}
			pstmt.executeUpdate();

			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteRow(Vector<String> selected, int compareInt) {
		Vector<String> column = getColumnName();
		String delete = "delete from " + tableName + " where " + column.get(compareInt) + " = '"
				+ selected.get(compareInt) + "';";
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(delete);

			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void rewrite(int compareI, int newI, String compareData, String newData) {
		Vector<String> column = getColumnName();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "update " + tableName + " set " + column.get(newI) + "='" + newData + "' where "
					+ column.get(compareI) + "='" + compareData + "';";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<Object> search(String columnName, String keyword, String day) {
		Vector<Object> result = new Vector<Object>();
		Vector<String> VColumn = getColumnName();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql;
			if (day.equals("notSelected")) {
				sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " LIKE '%" + keyword + "%';";
			} else {
				sql = "SELECT * FROM " + tableName + " WHERE " + columnName + " LIKE '%" + keyword
						+ "%' AND day LIKE '%" + day + "%';";
			}
			stmt.execute(sql);
			ResultSet showRS = stmt.executeQuery(sql);
			while (showRS.next()) {
				for (int i = 0; i < VColumn.size(); i++) {
					result.add(showRS.getString(VColumn.get(i)));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public void checkExcist(String type) {
		StringBuilder sb = new StringBuilder();
		String sql = null;
		switch (type) {
		case "lecture":
			sql = sb.append("create table if not exists " + tableName + "(")
					.append("number varchar(45) not null primary key,").append("name varchar(45),")
					.append("professor varchar(45),").append("credit varchar(45),").append("day varchar(45), ")
					.append("time varchar(45)").append(");").toString();
			break;
			
		case "student":
			sql = sb.append("create table if not exists " + tableName + "(")
					.append("id varchar(45) not null primary key,").append("name varchar(45),")
					.append("pw varchar(45),").append("studentNumber varchar(45),").append("major varchar(45), ")
					.append("phone varchar(45)").append(");").toString();
			break;
			
		case "professor":
			sql = sb.append("create table if not exists " + tableName + "(")
					.append("number varchar(45) not null primary key,").append("name varchar(45),")
					.append("credit varchar(45),").append("day varchar(45),").append("time varchar(45), ")
					.append("studentCount varchar(45)").append(");").toString();
			break;
			
		case "directory":
			sql = sb.append("create table if not exists " + tableName + "(")
					.append("number varchar(45) not null primary key,").append("name varchar(45),")
					.append("hyperlink varchar(45)").append(");").toString();
			break;
			
		case "studentPic":
			sql = sb.append("create table if not exists " + tableName + "(")
					.append("userID varchar(45) not null primary key,").append("picturePath varchar(45)").append(");")
					.toString();
			break;
			
		case "sincheongList":
			sql = sb.append("create table if not exists " + tableName + "(")
					.append("id varchar(45) not null primary key,").append("name varchar(45),")
					.append("studentNumber varchar(45),").append("major varchar(45), ").append("phone varchar(45)")
					.append(");").toString();
			break;
		}
		Statement stmt1;
		try {
			stmt1 = conn.createStatement();
			stmt1.execute(sql);
			stmt1.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Vector<String> getColumnName() {
		String showColumn = "SHOW FULL COLUMNS FROM " + tableName;
		Vector<String> VColumn = new Vector<String>();
		try {
			Statement stmt = conn.createStatement();
			ResultSet column = stmt.executeQuery(showColumn);
			while (column.next()) {
				VColumn.add(column.getString("Field"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return VColumn;
	}

	public void finalize() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
