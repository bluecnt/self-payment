package ezen.project.first.team2.app.manager.pages.main.views.right.product;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ezen.project.first.team2.app.common.framework.View;
import ezen.project.first.team2.app.common.modules.product.manager.ProductCode;
import ezen.project.first.team2.app.common.modules.product.manager.ProductItem;
import ezen.project.first.team2.app.common.modules.product.manager.ProductManager;
import ezen.project.first.team2.app.common.utils.UiUtils;
import ezen.project.first.team2.app.common.utils.UiUtils.MsgBoxType;
import ezen.project.first.team2.app.manager.Main;
import ezen.project.first.team2.app.manager.pages.main.MainPage;

public class UpdateProductView extends View {
    DecimalFormat df = new DecimalFormat("###,###");

    JLabel mLabelInfo = new JLabel("상품 데이터 수정");

    ProductManager prodMngr = ProductManager.getInstance();

    // 검색, 결과, 수정용 패널
    JPanel mPanelSearchUpdate = new JPanel();
    // 상품검색용 패널
    JPanel mPanelSearch = new JPanel();
    // 검색결과용 패널
    JPanel mPanelResult = new JPanel();
    // 상품속성 수정패널
    JPanel mPanelPropertyUpdate = new JPanel();

    // 검색 컴포넌트
    JComboBox<String> mComboBoxSearchProperty;
    String[] properties = { "상품명", "상품코드" };
    JTextField mTextFieldSearch = new JTextField(10);
    JButton mBtnSearch = new JButton("상품검색");
    // 검색결과 컴포넌트
    JTable mTableResultList;
    JScrollPane mScroll;

    // 상품수정 컴포넌트
    JPanel mPanelPanelInfo = new JPanel();
    JLabel mLabelPanelInfo_1 = new JLabel("상단 리스트에서 더블클릭");
    JLabel mLabelPanelInfo_2 = new JLabel("■ 상품 정보");

    JPanel mPanelUpdateIdCode = new JPanel();
    JPanel mPanelUpdateProdId = new JPanel();
    JLabel mLabelUpdateProdId = new JLabel("상품 번호");
    JTextField mTextFieldUpdateProdId = new JTextField(10);
    JPanel mPanelUpdateProdCod = new JPanel();
    JLabel mLabelUpdateProdCode = new JLabel("상품 코드");
    JTextField mTextFieldUpdateProdCode = new JTextField(10);

    JPanel mPanelUpdateNamePrice = new JPanel();
    JPanel mPanelUpdateProdName = new JPanel();
    JLabel mLabelUpdateProdName = new JLabel("상품명");
    JTextField mTextFieldUpdateName = new JTextField(10);
    JPanel mPanelUpdateProdPrice = new JPanel();
    JLabel mLabelUpdateProdPrice = new JLabel("상품 가격");
    JTextField mTextFieldUpdatePrice = new JTextField(10);

    JPanel mPanelUpdateDesc = new JPanel();
    JLabel mLabelUpdateDesc = new JLabel("비 고");
    JTextField mTextFieldUpdateDesc = new JTextField(20);

    JPanel mPanelUpdateBtn = new JPanel();
    JButton mBtnUpdateComplete = new JButton("수정 확정");

    public UpdateProductView() {
        super(MainPage.VIEW_NUM_PROD_UPDATE);
    }

    @Override
    protected void onInit() {
        // 테이블 설정
        try {
            Object[] mPropertyColumn = {
                    "상품번호", "상품코드", "상품명", "가격", "등록일", "설명"
            };
            Object[][] mProdListRows = new Object[mPropertyColumn.length][10];

            DefaultTableModel model = new DefaultTableModel(mProdListRows, mPropertyColumn) {
                // 셀 내용 수정 불가
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                };
            };

            mTableResultList = new JTable(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onSetLayout() {
        this.setLayout(new BorderLayout());
        this.mPanelPanelInfo.setLayout(new GridLayout(2, 1));
        this.mPanelSearchUpdate.setLayout(new BorderLayout());
        this.mPanelResult.setLayout(new GridLayout(2, 1));
        this.mPanelPropertyUpdate.setLayout(new BoxLayout(
                mPanelPropertyUpdate, BoxLayout.Y_AXIS));

        this.mPanelUpdateIdCode.setLayout(new GridLayout(1, 2));
        this.mPanelUpdateNamePrice.setLayout(new GridLayout(1, 2));

    }

    @Override
    protected void onAddCtrls() {
        this.mLabelInfo.setOpaque(true);
        this.mLabelInfo.setBackground(Color.LIGHT_GRAY);
        this.mLabelInfo.setHorizontalAlignment(JLabel.CENTER);

        // 테이블 설정
        this.mTableResultList.getTableHeader().setReorderingAllowed(false);
        // 스크롤 설정
        this.mScroll = new JScrollPane(mTableResultList);
        this.mScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.mScroll.setBorder(
                BorderFactory.createEmptyBorder(10, 30, 30, 30));
        // 콤보박스 설정
        this.mComboBoxSearchProperty = new JComboBox<String>(properties);

        // 비활성화할 텍스트필드
        this.mTextFieldUpdateProdId.setEnabled(false);
        this.mTextFieldUpdateProdCode.setEnabled(false);

        // 라벨설정
        this.mLabelPanelInfo_1.setHorizontalAlignment(JLabel.CENTER);
        this.mLabelPanelInfo_2.setHorizontalAlignment(JLabel.CENTER);

        // 패널 설정
        this.mPanelPropertyUpdate.setBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20));

        this.mPanelUpdateIdCode.setBorder(
                BorderFactory.createEmptyBorder(10, 200, 10, 200));
        this.mPanelUpdateNamePrice.setBorder(
                BorderFactory.createEmptyBorder(10, 200, 10, 200));
        this.mPanelUpdateDesc.setBorder(
                BorderFactory.createEmptyBorder(10, 200, 10, 200));

        // 버튼 설정
        this.mBtnUpdateComplete.setBorder(
                BorderFactory.createEmptyBorder(10, 20, 10, 20));
        // 페이지에 추가
        this.add(mLabelInfo, BorderLayout.NORTH);
        this.add(mPanelSearchUpdate, BorderLayout.CENTER);

        this.mPanelSearchUpdate.add(mPanelSearch, BorderLayout.NORTH);
        this.mPanelSearchUpdate.add(mPanelResult, BorderLayout.CENTER);

        this.mPanelSearch.add(mComboBoxSearchProperty);
        this.mPanelSearch.add(mTextFieldSearch);
        this.mPanelSearch.add(mBtnSearch);

        this.mPanelResult.add(mScroll);
        this.mPanelResult.add(mPanelPropertyUpdate);

        // 상품수정 패널
        this.mPanelPropertyUpdate.add(mPanelPanelInfo);
        this.mPanelPanelInfo.add(mLabelPanelInfo_1);
        this.mPanelPanelInfo.add(mLabelPanelInfo_2);

        this.mPanelPropertyUpdate.add(mPanelUpdateIdCode);
        this.mPanelPropertyUpdate.add(mPanelUpdateNamePrice);
        this.mPanelPropertyUpdate.add(mPanelUpdateDesc);

        this.mPanelUpdateIdCode.add(mPanelUpdateProdId);
        this.mPanelUpdateProdId.add(mLabelUpdateProdId);
        this.mPanelUpdateProdId.add(mTextFieldUpdateProdId);
        this.mPanelUpdateIdCode.add(mPanelUpdateProdCod);
        this.mPanelUpdateProdCod.add(mLabelUpdateProdCode);
        this.mPanelUpdateProdCod.add(mTextFieldUpdateProdCode);

        this.mPanelUpdateNamePrice.add(mPanelUpdateProdName);
        this.mPanelUpdateProdName.add(mLabelUpdateProdName);
        this.mPanelUpdateProdName.add(mTextFieldUpdateName);
        this.mPanelUpdateNamePrice.add(mPanelUpdateProdPrice);
        this.mPanelUpdateProdPrice.add(mLabelUpdateProdPrice);
        this.mPanelUpdateProdPrice.add(mTextFieldUpdatePrice);

        this.mPanelUpdateDesc.add(mLabelUpdateDesc);
        this.mPanelUpdateDesc.add(mTextFieldUpdateDesc);

        this.mPanelPropertyUpdate.add(mPanelUpdateBtn);
        this.mPanelUpdateBtn.add(mBtnUpdateComplete);
    }

    @Override
    protected void onAddEventListeners() {
        mTextFieldSearch.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    mPanelSearch.getRootPane().setDefaultButton(mBtnSearch);
                }
            }
        });

        mTableResultList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = mTableResultList.getSelectedRow();

                    int idColumn = 0;
                    int findId = (int) mTableResultList.getValueAt(row, idColumn);

                    try {
                        ProductItem findedItem = prodMngr.findById(findId);

                        searchItemAddTextField(findedItem);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }

                }
            }

        });

        ActionListener listener = e -> {
            JButton btn = (JButton) e.getSource();

            if (btn == this.mBtnSearch) { // 상품검색

                // 텍스트 필드를 비운다
                initializeTextField();

                String property = mComboBoxSearchProperty.getSelectedItem().toString();

                switch (property) {
                    case "상품명":
                        try {
                            String searchText = mTextFieldSearch.getText();

                            List<ProductItem> prodItemList = prodMngr.findByName(searchText);

                            searchItemAddtable(prodItemList);

                        } catch (Exception e1) {
                            System.out.println("[findByName()]No Search Result");
                            UiUtils.showMsgBox("검색결과가 없습니다", "");
                            // e1.printStackTrace();
                        }
                        break;

                    case "상품코드":
                        try {
                            String searchText = mTextFieldSearch.getText();

                            // 검색어 앞자리 대문자화
                            char prodType = searchText.charAt(0);
                            StringBuilder sb = new StringBuilder(searchText);
                            if (prodType >= 'a' && prodType <= 'z') {
                                sb.setCharAt(0, (char) (prodType - 32));
                                searchText = sb.toString();
                            }
                            List<ProductItem> prodItemList = new ArrayList<>();

                            // 리스트에 추가
                            ProductCode prodCode = new ProductCode(searchText);
                            ProductItem prodItem = prodMngr.findByProductCode(prodCode);
                            prodItemList.add(prodItem);

                            searchItemAddtable(prodItemList);

                        } catch (Exception e1) {
                            System.out.println("[findByName()]No Search Result");
                            UiUtils.showMsgBox("검색결과가 없습니다", "");
                            // e1.printStackTrace();
                        }
                        break;
                }
            } else if (btn == mBtnUpdateComplete) { // 수정 확정

                try {
                    int updateId = Integer.valueOf(this.mTextFieldUpdateProdId.getText());

                    ProductItem updateItem = prodMngr.findById(updateId);
                    updateItem.setName(mTextFieldUpdateName.getText());
                    updateItem.setPrice(Integer.valueOf(mTextFieldUpdatePrice.getText()));
                    updateItem.setDesc(mTextFieldUpdateDesc.getText());

                    prodMngr.updateById(updateId, updateItem);
                    // DB에 Update 쿼리
                    prodMngr.doUpdateQuery(updateItem, null, "prod_id = " + updateId);

                    // 업데이트를 진행한 아이템 row만 갱신
                    DefaultTableModel m = (DefaultTableModel) mTableResultList.getModel();
                    for (int row = 0; row < mTableResultList.getRowCount(); row++) {
                        if ((int) mTableResultList.getValueAt(row, 0) == updateId) {

                            m.removeRow(row);

                            Object[] item = { updateItem.getId(), updateItem.getProdCodeStr(),
                                    updateItem.getName(), df.format(updateItem.getPrice()),
                                    updateItem.getRegDateStr(), updateItem.getDesc() };
                            m.insertRow(row, item);

                            break;
                        }
                    }

                    // 완료되면 필드를 지운다
                    initializeTextField();

                    UiUtils.showMsgBox("수정 완료", "");
                } catch (Exception e1) {
                    UiUtils.showMsgBox("유효하지 않은 동작입니다.", "", MsgBoxType.Error);
                    // e1.printStackTrace();
                }

            }
        };

        this.mBtnSearch.addActionListener(listener);
        this.mBtnUpdateComplete.addActionListener(listener);

    }

    @Override
    protected void onShow(boolean firstTime) {
        System.out.println("[ListProdStockView.onShow()]");

        // 텍스트필드 비워놓기
        initializeTextField();

        // 상품목록을 테이블에 추가
        insertItemsIntoTable();
    }

    @Override
    protected void onHide() {
        System.out.println("[ListProdStockView.onHide()]");
    }

    @Override
    protected void onSetResources() {
        Main main = (Main) this.getStatusManager();

        JLabel lb = (JLabel) this.getComponents()[0];
        lb.setFont(main.mFont0);

        this.mLabelPanelInfo_1.setFont(main.mFont2);
        this.mLabelPanelInfo_2.setFont(main.mFont2);
        this.mLabelUpdateDesc.setFont(main.mFont2);
        this.mLabelUpdateProdCode.setFont(main.mFont2);
        this.mLabelUpdateProdId.setFont(main.mFont2);
        this.mLabelUpdateProdName.setFont(main.mFont2);
        this.mLabelUpdateProdPrice.setFont(main.mFont2);

        this.mTableResultList.setFont(main.mFont3);

        this.mBtnUpdateComplete.setFont(main.mFont2);
    }

    // 검색한 결과를 테이블에 추가
    private void searchItemAddtable(List<ProductItem> prodItemList) {

        DefaultTableModel m = (DefaultTableModel) mTableResultList.getModel();

        m.setRowCount(0);

        for (ProductItem pi : prodItemList) {
            if (prodItemList.size() == 1) {

                searchItemAddTextField(pi);
            }
            m.addRow(new Object[] {
                    pi.getId(), pi.getProdCodeStr(),
                    pi.getName(), df.format(pi.getPrice()),
                    pi.getRegDateStr(), pi.getDesc()
            });
        }
    }

    // 검색한 결과를 텍스트 필드에 추가
    private void searchItemAddTextField(ProductItem prodItem) {

        UiUtils.showMsgBox(String.format(
                "수정할 항목은\n[ 상품코드[%s] 상품명[%s] ] 입니다\n",
                prodItem.getProdCodeStr(),
                prodItem.getName()), "");

        this.mTextFieldUpdateProdId.setText(String.valueOf(prodItem.getId()));
        this.mTextFieldUpdateProdCode.setText(prodItem.getProdCodeStr());
        this.mTextFieldUpdateName.setText(prodItem.getName());
        this.mTextFieldUpdatePrice.setText(String.valueOf(prodItem.getPrice()));
        this.mTextFieldUpdateDesc.setText(prodItem.getDesc());
    }

    // 텍스트 필드 비우기
    private void initializeTextField() {
        this.mTextFieldUpdateProdId.setText("");
        this.mTextFieldUpdateProdCode.setText("");
        this.mTextFieldUpdateName.setText("");
        this.mTextFieldUpdatePrice.setText("");
        this.mTextFieldUpdateDesc.setText("");
    }

    // 상품목록을 테이블에 추가하는 메소드
    private void insertItemsIntoTable() {
        DefaultTableModel m = (DefaultTableModel) mTableResultList.getModel();
        m.setRowCount(0);
        try {
            prodMngr.iterate((info, idx) -> {
                m.addRow(new Object[] {
                        info.getId(), info.getProdCodeStr(),
                        info.getName(), df.format(info.getPrice()),
                        info.getRegDateStr(), info.getDesc()
                });
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        mTableResultList.updateUI();
    }
}