package com.smxcwz.qiongyou.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

import com.smxcwz.qiongyou.R;
import com.smxcwz.qiongyou.fragment.RecommendTabFragment;
import com.smxcwz.qiongyou.fragment.TabFragment;
import com.smxcwz.qiongyou.view.ChengeColorIconWithText;

public class ContentActivity extends FragmentActivity implements
		OnClickListener {
	private List<Fragment> mTabs = new ArrayList<Fragment>();
	private ViewPager tab_viewPager;
	private String[] mTitles = new String[] { "First Fragment",
			"Second Fragment", "Third Fragment" };
	private PagerAdapter adapter;
	private ChengeColorIconWithText tabNva_one;
	private ChengeColorIconWithText tabNva_two;
	private ChengeColorIconWithText tabNva_three;
	private List<ChengeColorIconWithText> tabNvas = new ArrayList<ChengeColorIconWithText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_content);
		initView();
		initDatas();
		initCtrl();
		initEvent();
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		int n = tabNvas.size();
		for (int i = 0; i < n; i++) {
			tabNvas.get(i).setOnClickListener(this);
			tabNvas.get(i).setIconAlpha(0);
		}
		tabNva_one.setIconAlpha(1.0f);

		tab_viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
				if (positionOffset > 0) {
					ChengeColorIconWithText left = tabNvas.get(position);
					ChengeColorIconWithText right = tabNvas.get(position + 1);
					left.setIconAlpha(1 - positionOffset);
					right.setIconAlpha(positionOffset);
				}
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void initCtrl() {
		// TODO Auto-generated method stub

		tab_viewPager.setAdapter(adapter);
		tab_viewPager.setOffscreenPageLimit(2);  

	}

	private void initDatas() {
		// TODO Auto-generated method stub
		    TabFragment one = new TabFragment();
		    TabFragment two = new TabFragment();
			RecommendTabFragment recommendTabFragment = new RecommendTabFragment();
			mTabs.add(recommendTabFragment);
			mTabs.add(one);
			mTabs.add(two);
		
		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mTabs.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mTabs.get(arg0);
			}
		};

	}

	private void initView() {
		// TODO Auto-generated method stub
		tab_viewPager = (ViewPager) findViewById(R.id.tab_viewPager);
		tabNva_one = (ChengeColorIconWithText) findViewById(R.id.tabNva_one);
		tabNva_two = (ChengeColorIconWithText) findViewById(R.id.tabNva_two);
		tabNva_three = (ChengeColorIconWithText) findViewById(R.id.tabNva_three);
		tabNvas.add(tabNva_one);
		tabNvas.add(tabNva_two);
		tabNvas.add(tabNva_three);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		resetOtherTabs();
		switch (v.getId()) {
		case R.id.tabNva_one:
			tabNvas.get(0).setIconAlpha(1.0f);
			tab_viewPager.setCurrentItem(0, false);

			break;
		case R.id.tabNva_two:
			tabNvas.get(1).setIconAlpha(1.0f);
			tab_viewPager.setCurrentItem(1, false);

			break;
		case R.id.tabNva_three:
			tabNvas.get(2).setIconAlpha(1.0f);
			tab_viewPager.setCurrentItem(2, false);

			break;

		default:
			break;
		}

	}

	private void resetOtherTabs() {
		// TODO Auto-generated method stub
		int n = tabNvas.size();
		for (int i = 0; i < n; i++) {
			tabNvas.get(i).setIconAlpha(0);
		}

	}


}
