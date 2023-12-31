////////////////////////////////////////////////////////////////////////////////
//
// [SGLEE:20231120MON_120600] Created
//
////////////////////////////////////////////////////////////////////////////////

package ezen.project.first.team2.app.common.modules.base;

public class ListItem implements Cloneable {
	// -------------------------------------------------------------------------

	// 기본 값은 -1이며, ListManager.add()에서 내부적으로 this.getNextID()를
	// 호출하여 id를 할당한다
	protected int mId = -1;

	// -------------------------------------------------------------------------

	public ListItem() {
	}

	// -------------------------------------------------------------------------

	// 객체로 값 설정
	public void setValuesFrom(ListItem item) {
		this.onSetValuesFrom(item);
	}

	// -------------------------------------------------------------------------

	// ID 얻기
	public int getId() {
		return this.mId;
	}

	// -------------------------------------------------------------------------

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// -------------------------------------------------------------------------

	protected void onSetValuesFrom(ListItem item) {
	}
}
