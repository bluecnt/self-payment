package ezen.project.first.team2.app.common.z_test.modules.database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;

import ezen.project.first.team2.app.common.modules.database.DBConnector;
import ezen.project.first.team2.app.common.utils.MathUtils;
import ezen.project.first.team2.app.common.utils.TimeUtils;

public class TestDBConnector {
	static void printTitle(String text) {
		System.out.println("-".repeat(60));
		System.out.printf("[%s] # %s \n", TimeUtils.currTimeStr(), text);
		System.out.println("-".repeat(60));
	}

	// 레코드셋 리스팅
	static void listRecordset() {
		var dbConn = DBConnector.getInstance();

		printTitle("select 쿼리");

		try {
			String sql = "select * from employees where employee_id > ? order by employee_id desc";
			PreparedStatement pstmt = dbConn.getConnection().prepareStatement(sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			pstmt.setInt(1, 200);

			ResultSet rs = pstmt.executeQuery();

			// 개수 얻기
			int rcnt = 0;
			while (rs.next())
				rcnt++;
			rs.beforeFirst();

			System.out.printf("[%s] 레코드 개수: %d \n", TimeUtils.currTimeStr(), rcnt);

			while (rs.next()) {
				System.out.printf("[%s] %4s | %-12s | %-12s | %s | %6d \n",
						TimeUtils.currTimeStr(),
						rs.getString("employee_id"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("hire_date"),
						rs.getInt("salary"));
			}

			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
	}

	// 레코드 추가
	static void addRecord() {
		var dbConn = DBConnector.getInstance();

		printTitle("insert 쿼리");

		try {
			String sql = "insert into employees(" +
					"employee_id, first_name, last_name, email, " +
					"phone_number, hire_date, job_id, salary, " +
					"commission_pct, manager_id, department_id" +
					") values(employees_seq.nextval,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = dbConn.getConnection().prepareStatement(sql);

			Object[] values = {
					"sigwan", "lee", "id@domain.com" + MathUtils.getRandomNumber(1, 4096),
					"000.000.000", LocalDate.now(), "IT_PROG", 100000,
					0.5, null, 90
			};

			int idx = 1;
			for (var v : values) {
				if (v instanceof String)
					pstmt.setString(idx++, (String) v);
				else if (v instanceof Integer)
					pstmt.setInt(idx++, (Integer) v);
				else if (v instanceof Double)
					pstmt.setDouble(idx++, (Double) v);
				else if (v instanceof LocalDate)
					pstmt.setDate(idx++, Date.valueOf((LocalDate) v));
				else if (v == null)
					pstmt.setNull(idx++, Types.NUMERIC);
			}

			int rows = pstmt.executeUpdate();
			System.out.printf("[%s] pstmt.executeUpdate() => %d \n", TimeUtils.currTimeStr(), rows);

			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();
	}

	// 레코드 수정
	static void updateRecord() {
		var dbConn = DBConnector.getInstance();

		printTitle("update 쿼리");

		try {
			String sql = "update employees set salary=salary+? where first_name=?";
			PreparedStatement pstmt = dbConn.getConnection().prepareStatement(sql);
			pstmt.setInt(1, 10000);
			pstmt.setString(2, "sigwan");

			int rows = pstmt.executeUpdate();
			System.out.printf("pstmt.executeUpdate() => %d \n", rows);

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();

	}

	// 레코드 삭제
	static void deleteRecord() {
		var dbConn = DBConnector.getInstance();

		printTitle("delete 쿼리");

		try {
			String sql = "delete from employees where first_name=?";
			PreparedStatement pstmt = dbConn.getConnection().prepareStatement(sql);
			pstmt.setString(1, "sigwan");

			int rows = pstmt.executeUpdate();
			System.out.printf("[%s] pstmt.executeUpdate() => %d \n", TimeUtils.currTimeStr(), rows);

			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println();

	}

	public static void main(String[] args) {
		System.out.println("Test DBTable class");

		try {
			var dbConn = DBConnector.getInstance();
			dbConn.loadJdbcDriver();

			// DB 연결
			System.out.printf("[%s] DB 연결 중.. \n", TimeUtils.currTimeStr());
			dbConn.connect("localhost", DBConnector.DEFAULT_PORT_NUM, "hr", "1234");
			System.out.printf("[%s] DB 연결됨 \n", TimeUtils.currTimeStr());
			System.out.println();

			// 레코드셋 리스팅
			listRecordset();

			// 레코드 추가
			addRecord();
			listRecordset();

			// 레코드 수정
			updateRecord();
			listRecordset();

			// 레코드 삭제
			deleteRecord();
			listRecordset();

			// DB 연결 해제
			if (dbConn.isConnected()) {
				System.out.printf("[%s] DB 연결 해제 중.. \n", TimeUtils.currTimeStr());
				dbConn.disconnect();
				System.out.printf("[%s] DB 연결 해제됨 \n", TimeUtils.currTimeStr());
				System.out.println();
			}

			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}