package ezen.project.first.team2.app.common.z_test.modules.customer;

import java.time.LocalDate;
import java.util.List;

import ezen.project.first.team2.app.common.modules.base.ListActionListener;
import ezen.project.first.team2.app.common.modules.base.ListManager;
import ezen.project.first.team2.app.common.modules.customer.CustomerItem;
import ezen.project.first.team2.app.common.modules.customer.CustomerManager;

public class TestCustomerManager {

	static void printTitle(String text) {
		System.out.println("=".repeat(70));
		System.out.println("= " + text);
		System.out.println("=".repeat(70));
		System.out.println();
	}

	static void printSection(String text) {
		System.out.println("-".repeat(60));
		System.out.println("- " + text);
		System.out.println("-".repeat(60));
		System.out.println();
	}

	static void printList() {
		CustomerManager custMngr = CustomerManager.getInstance();

		try {
			// 고객 리스트
			{
				printSection("고객 리스트");

				custMngr.iterate((item, idx) -> {
					System.out.println("  " + item.toString());

					return true;
				});

				System.out.println();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		CustomerManager custMngr = CustomerManager.getInstance();

		try {
			custMngr.setActionListener(new ListActionListener<CustomerItem>() {
				@Override
				public void onAdded(ListManager<CustomerItem> mngr, CustomerItem item) {
					System.out.println("[TestCustomerManagerMem] onAdded()");
					System.out.println("  -> item: " + item);
					System.out.println();
				}

				@Override
				public void onUpdated(ListManager<CustomerItem> mngr,
						CustomerItem oldItem, CustomerItem newItem) {
					System.out.println("[TestCustomerManagerMem] onUpdated()");
					System.out.println("  -> oldItem: " + oldItem);
					System.out.println("  -> newItem: " + newItem);
					System.out.println();
				}

				@Override
				public void onDeleted(ListManager<CustomerItem> mngr, CustomerItem item) {
					System.out.println("[TestCustomerManagerMem] onDeleted()");
					System.out.println("  -> item: " + item);
					System.out.println();
				}

				@Override
				public void onDeleteItems(ListManager<CustomerItem> mngr, List<Integer> idList) {
				}

				@Override
				public void onDeletedItems(ListManager<CustomerItem> mngr, List<Integer> idList) {
				}

				@Override
				public void onDeleteAllItems(ListManager<CustomerItem> mngr) {
				}

				@Override
				public void onDeletedAllItems(ListManager<CustomerItem> mngr) {
				}
			});

			//
			// custMngr.init();
			//

			// 고객 추가
			{
				printTitle("고객 추가");

				for (var ci : CustomerItem.getPredefinedData()) {
					custMngr.add(ci);
					// System.out.println(" -> " + ci.getName());
				}

				var ci = new CustomerItem();
				// ID 값에 -1을 넣거나 매니저를 통해 다음 ID 값을 얻을 수 있다
				ci.setValues(custMngr.getNextID(), LocalDate.now(), "BLUECNT",
						LocalDate.of(1983, 5, 9), "010-0000-8087", 10000, "");

				custMngr.add(ci);
				System.out.println(" -> " + ci.getName());

				printList();
			}

			// 고객 수정
			{
				printTitle("고객 수정");

				var ci = new CustomerItem(-1, LocalDate.of(2023, 1, 1), "홍길동",
						LocalDate.of(2023, 1, 2), "010-9999-8086", 1234, "비고");

				custMngr.updateById(0, ci);

				printList();
			}

			// 고객 삭제
			{
				printTitle("고객 삭제");

				var ci = custMngr.findById(0);
				System.out.println("  -> " + ci);

				custMngr.deleteById(ci.getId());

				printList();
			}

			// 고객 조회
			{
				printTitle("고객 조회");

				custMngr.add(CustomerItem.getPredefinedData()[0]);
				printList();

				CustomerItem ci = null;
				// List<CustomerItem> ciList = null;

				System.out.println("- findById()");
				ci = custMngr.findById(6);
				System.out.println(ci);

				System.out.println("- findByName()");
				ci = custMngr.findByName("BLUECNT");
				System.out.println(ci);

				System.out.println("- findByBirthday()");
				ci = custMngr.findByBirthday(LocalDate.of(1983, 5, 9));
				System.out.println(ci);

				System.out.println("- findByPhoneNumber()");
				ci = custMngr.findByPhoneNumber("010-0000-8086");
				System.out.println(ci);

				System.out.println();
			}

			//
			// custMngr.deinit();
			//
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
