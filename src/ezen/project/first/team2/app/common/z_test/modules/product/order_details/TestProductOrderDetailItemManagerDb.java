package ezen.project.first.team2.app.common.z_test.modules.product.order_details;

import ezen.project.first.team2.app.common.modules.database.DBConnector;
import ezen.project.first.team2.app.common.modules.product.order_details.ProductOrderDetailItem;
import ezen.project.first.team2.app.common.modules.product.order_details.ProductOrderDetailsManager;
import ezen.project.first.team2.app.common.utils.TimeUtils;

public class TestProductOrderDetailItemManagerDb {
	static void printTitle(String text) {
		System.out.println("-".repeat(60));
		System.out.printf("[%s] # %s \n", TimeUtils.currTimeStr(), text);
		System.out.println("-".repeat(60));
	}

	public static void main(String[] args) {
		try {
			var dbConn = DBConnector.getInstance();
			dbConn.loadJdbcDriver();

			dbConn.connect("192.168.0.64", DBConnector.DEFAULT_PORT_NUM, "hr", "1234");

			final boolean CREATE_TABLE = !true;
			final boolean DROP_TABLE = true;
			final boolean TRUNCATE_TABLE = !true;

			final boolean DO_SELECT_QUERY = !true;
			final boolean DO_INSERT_QUERY = !true;
			final boolean DO_UPDATE_QUERY = !true;
			final boolean DO_DELETE_QUERY = !true;

			var mngr = ProductOrderDetailsManager.getInstance();

			try {
				// 테이블 생성
				if (CREATE_TABLE) {
					printTitle("테이블 생성");

					mngr.createTable();

					System.out.println();
				}

				// 테이블 삭제
				if (DROP_TABLE) {
					printTitle("테이블 삭제");

					mngr.dropTable();

					System.out.println();
				}
				// 테이블 초기화
				if (TRUNCATE_TABLE) {
					printTitle("테이블 초기화");

					mngr.truncateTable();

					System.out.println();
				}

				// 레코드셋 리스팅
				if (DO_SELECT_QUERY) {
					printTitle("레코드셋 리스팅");

					int rCnt = mngr.doSelectQuery();
					System.out.printf("rCnt: %d \n", rCnt);

					mngr.iterate();

					System.out.println();
				}

				// 레코드 추가
				if (DO_INSERT_QUERY) {
					printTitle("레코드 추가");
					int id = mngr.getNextIdFromDb("prod_order_detail_id");
					var item = new ProductOrderDetailItem(id, 1, 1, 1, 1);
					mngr.doInsertQuery(item);

					mngr.doSelectQuery();
					mngr.iterate();

					System.out.println();
				}

				// 레코드 수정
				if (DO_UPDATE_QUERY) {
					printTitle("레코드 수정");

					var item = mngr.getFirstItem();
					var fieldset = new String[] { "quantity" };
					var _where = String.format("prod_order_detail_id = %d", item.getId());

					item.setQuantity(2);
					int rows = mngr.doUpdateQuery(item, fieldset, _where);
					System.out.printf("rows: %d \n", rows);

					mngr.doSelectQuery();
					mngr.iterate();

					System.out.println();
				}

				// 레코드 삭제
				if (DO_DELETE_QUERY) {
					printTitle("레코드 삭제");

					String _where = "quantity = 2";
					int rows = mngr.doDeleteQuery(_where);
					System.out.printf("rows: %d \n", rows);

					mngr.doSelectQuery();
					mngr.iterate();

					System.out.println();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			dbConn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
