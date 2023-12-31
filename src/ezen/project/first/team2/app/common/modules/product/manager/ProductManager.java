////////////////////////////////////////////////////////////////////////////////
//
// [SGLEE:20231120MON_172400] Created
//
////////////////////////////////////////////////////////////////////////////////

package ezen.project.first.team2.app.common.modules.product.manager;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.List;

import ezen.project.first.team2.app.common.modules.base.ListManagerDb;

public class ProductManager extends ListManagerDb<ProductItem> {

	private static final String TABLE_NAME = "PRODUCTS";

	// -------------------------------------------------------------------------

	private static ProductManager mInstance = null;
	private static ProductManager mTmpInstance = new ProductManager();

	// -------------------------------------------------------------------------

	// 생성자
	private ProductManager() {
		super(TABLE_NAME);
	}

	// 인스턴스 얻기
	public static ProductManager getInstance() {
		if (mInstance == null) {
			mInstance = new ProductManager();
		}

		return mInstance;
	}

	public static ProductManager getTmpInstance() {
		return mTmpInstance;
	}

	// -------------------------------------------------------------------------

	public ProductItem findByProductCode(ProductCode prodCode) {
		return this.find((pi, idx) -> pi.getProdCode().equals(prodCode));
	}

	public List<ProductItem> findByRegDate(LocalDate date) {
		return this.findItems((pi, idx) -> pi.getRegDate().equals(date));
	}

	public List<ProductItem> findByName(String name) {
		return this.findItems((pi, idx) -> pi.getName().contains(name));
	}

	public List<ProductItem> findByPrice(int price) {
		return this.findItems((pi, idx) -> pi.getPrice() == price);
	}

	public int getNextProdCodeSnFromDbByType(ProductCode.Type type) throws Exception {
		int nextId = -1;

		// 액션 리스너 비활성화
		var oldStatus = this.enableActionListener(false);
		{
			var mngr = this.onGetTmpInstance();

			mngr.deleteAllItems();

			String fieldset = "prod_id, prod_code";
			String where = String.format("prod_code like '%s%%'", ProductCode.typeToStr(type));
			String orderBy = "prod_code desc";
			int rCnt = mngr.doSelectQuery(null, fieldset, where, orderBy);
			if (rCnt == 0)
				return 1;

			var item = mngr.getFirstItem();
			if (item == null) {
				// 익셉션 발생시킬까?

				return -1;
			}

			var pci = item.getProdCode();
			nextId = pci.getSn() + 1;
		}
		// 액션 리스너 활성화 상태 복구
		this.enableActionListener(oldStatus);

		return nextId;
	}

	// -------------------------------------------------------------------------

	// -> 성공: 빈 문자열 리턴, 실패: 예외 에러 메시지 리턴
	@Override
	protected String onAdd(ProductItem item) {
		if (this.findByProductCode(item.getProdCode()) != null) {
			String msg = String.format("[ProductManager.onAdd()]" +
					" You have same product code(%s)!", item.getProdCode().toString());

			return msg;
		}

		return super.onAdd(item);
	}

	// -> 성공: 빈 문자열 리턴, 실패: 예외 에러 메시지 리턴
	@Override
	protected String onUpdateById(int id, ProductItem oldItem, ProductItem newItem) {
		try {
			// Product Code 복사
			newItem.setProdCode((ProductCode) oldItem.getProdCode().clone());

			return super.onUpdateById(id, oldItem, newItem);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	// -> 성공: 빈 문자열 리턴, 실패: 예외 에러 메시지 리턴
	@Override
	protected String onDeleteById(int id) {
		return super.onDeleteById(id);
	}

	@Override
	protected ListManagerDb<ProductItem> onGetTmpInstance() {
		return mTmpInstance;
	}

	// create 테이블이 있다면 예외 발생
	@Override
	protected String onMakeCreateTableQuery(String tableName) {
		return String.format("create table %s(" +
				"prod_id number(10) primary key, " +
				"prod_code char(4) not null, " +
				"reg_date date not null, " +
				"name varchar2(100), " +
				"price number, " +
				"prod_desc varchar2(100)" +
				")",
				tableName);
	}

	// select
	@Override
	protected ProductItem onResultSetToItem(ResultSet rs) {
		try {

			return new ProductItem(
					this.getInt(rs, "prod_id"),
					new ProductCode(this.getString(rs, "prod_code")),
					this.getDate(rs, "reg_date").toLocalDate(),
					this.getString(rs, "name"),
					this.getInt(rs, "price"),
					this.getString(rs, "prod_desc"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// insert
	@Override
	protected String[] onMakeFieldsetAndValues(ProductItem item) {

		String fieldset = "prod_id, prod_code, reg_date, name, price, prod_desc";
		String values = String.format("%d, '%s', '%s', '%s', %d, '%s'",
				item.getId(), item.getProdCodeStr(), item.getSqlRegDateStr(),
				item.getName(), item.getPrice(), item.getDesc());

		return new String[] { fieldset, values };
	}

	@Override
	protected String onMakeSetAll(ProductItem item) throws Exception {
		String s = "";

		// s += String.format("prod_id = %d, ", item.getId());
		s += String.format("prod_code = '%s', ", item.getProdCodeStr());
		s += String.format("reg_date = '%s', ", item.getSqlRegDateStr());
		s += String.format("name = '%s', ", item.getName());
		s += String.format("price = %d, ", item.getPrice());
		s += String.format("prod_desc = '%s'", item.getDesc());

		return s;

	}

	// update
	@Override
	protected String onMakeSet(ProductItem item, String[] fieldset) throws Exception {
		String s = "";

		for (var f : fieldset) {
			if (f.equals("prod_id")) {
				s += String.format("prod_id = %d, ", item.getId());
			} else if (f.equals("prod_code")) {
				s += String.format("prod_code = '%s', ", item.getProdCodeStr());
			} else if (f.equals("reg_date")) {
				s += String.format("reg_date = '%s', ", item.getSqlRegDateStr());
			} else if (f.equals("name")) {
				s += String.format("name = '%s', ", item.getName());
			} else if (f.equals("price")) {
				s += String.format("price = %d, ", item.getPrice());
			} else if (f.equals("prod_desc")) {
				s += String.format("prod_desc = '%s', ", item.getDesc());
			} else {
				String msg = String.format("[ProductManager.onMakeSet()]" +
						" Invalid field name('%s')!", f);
				throw new Exception(msg);
			}
		}

		s = s.substring(0, s.length() - 2);

		return s;
	}
}
