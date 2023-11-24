package ezen.project.first.team2.app.payment.pages.main.views.popup;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import ezen.project.first.team2.app.common.framework.View;
import ezen.project.first.team2.app.payment.pages.main.MainPage;
import ezen.project.first.team2.app.payment.pages.main.views.MainView;

public class PopUpView0_FruitsSelector extends View {
	
	private static final int PADDING = 10;
	
	private static final String CANCEL_BTN_TEXT = "취소";
	
	ImageIcon mImg0;
	ImageIcon mImg1;
	ImageIcon mImg2;
	ImageIcon mImg3;
	ImageIcon mImg4;
	ImageIcon mImg5;
	
	JButton mFruits_btn0;
	JButton mFruits_btn1;
	JButton mFruits_btn2;
	JButton mFruits_btn3;
	JButton mFruits_btn4;
	JButton mFruits_btn5;
	
	JButton mCancel_btn;

	public PopUpView0_FruitsSelector() {
		super(MainPage.POPUP_VIEW_FRUITS_SELECTOR_NUM);
	}

	@Override
	protected void onInit() {

		mFruits_btn0 = new JButton();
		mFruits_btn1 = new JButton();
		mFruits_btn2 = new JButton();
		mFruits_btn3 = new JButton();
		mFruits_btn4 = new JButton();
		mFruits_btn5 = new JButton();
		mCancel_btn = new JButton(CANCEL_BTN_TEXT);
		
		try {
			BufferedImage buffImg0 = ImageIO.read(new File("resources/images/fruits&vegetables/banana.png"));
			BufferedImage buffImg1 = ImageIO.read(new File("resources/images/fruits&vegetables/apple.png"));
			BufferedImage buffImg2 = ImageIO.read(new File("resources/images/fruits&vegetables/orange.png"));
			BufferedImage buffImg3 = ImageIO.read(new File("resources/images/fruits&vegetables/lettuce.png"));
			BufferedImage buffImg4 = ImageIO.read(new File("resources/images/fruits&vegetables/persimmon.png"));
			BufferedImage buffImg5 = ImageIO.read(new File("resources/images/fruits&vegetables/pepper.png"));
			Image scaledImg0 = buffImg0.getScaledInstance(117, 149, Image.SCALE_SMOOTH);
			Image scaledImg1 = buffImg1.getScaledInstance(117, 149, Image.SCALE_SMOOTH);
			Image scaledImg2 = buffImg2.getScaledInstance(117, 149, Image.SCALE_SMOOTH);
			Image scaledImg3 = buffImg3.getScaledInstance(117, 149, Image.SCALE_SMOOTH);
			Image scaledImg4 = buffImg4.getScaledInstance(117, 149, Image.SCALE_SMOOTH);
			Image scaledImg5 = buffImg5.getScaledInstance(117, 149, Image.SCALE_SMOOTH);
			mImg0 = new ImageIcon(scaledImg0);
			mImg1 = new ImageIcon(scaledImg1);
			mImg2 = new ImageIcon(scaledImg2);
			mImg3 = new ImageIcon(scaledImg3);
			mImg4 = new ImageIcon(scaledImg4);
			mImg5 = new ImageIcon(scaledImg5);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		mFruits_btn0.setIcon(mImg0);
		mFruits_btn1.setIcon(mImg1);
		mFruits_btn2.setIcon(mImg2);
		mFruits_btn3.setIcon(mImg3);
		mFruits_btn4.setIcon(mImg4);
		mFruits_btn5.setIcon(mImg5);
	}

	@Override
	protected void onSetLayout() {
		this.setBorder(BorderFactory.createEmptyBorder(
				PADDING, PADDING, PADDING, PADDING));
		
		GridLayout gridLayout = new GridLayout(2, 4);
		gridLayout.setHgap(10);
		gridLayout.setVgap(10);
		this.setLayout(gridLayout);
	}

	@Override
	protected void onAddCtrls() {
		
		this.add(mFruits_btn0);
		this.add(mFruits_btn1);
		this.add(mFruits_btn2);
		this.add(mFruits_btn3);
		this.add(mFruits_btn4);
		this.add(mFruits_btn5);
		this.add(mCancel_btn);
		
	}

	@Override
	protected void onAddEventListeners() {
		mCancel_btn.addActionListener(e -> {
			try {
				MainView mainView = (MainView) this.getPage().getViewByNum(MainPage.VIEW_NUM_MAIN);
				mainView.setSelectedRightViewByNum(MainPage.RIGHT_VIEW_ORDER_LIST_NUM);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		});
	}

	@Override
	protected void onShow(boolean firstTime) {}

	@Override
	protected void onHide() {}

	@Override
	protected void onSetResources() {}
	
}
