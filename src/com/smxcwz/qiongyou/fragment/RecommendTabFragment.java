package com.smxcwz.qiongyou.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.smxcwz.qiongyou.R;
import com.smxcwz.qiongyou.common.QYApi;
import com.smxcwz.qiongyou.cycleview.ADInfo;
import com.smxcwz.qiongyou.cycleview.CycleViewPager;
import com.smxcwz.qiongyou.cycleview.CycleViewPager.ImageCycleViewListener;
import com.smxcwz.qiongyou.cycleview.ViewFactory;
import com.smxcwz.qiongyou.interfaces.MyOnClickLinsenner;
import com.smxcwz.qiongyou.utils.HttpURlUtils;
import com.smxcwz.qiongyou.view.IconAndTextView;

;

public class RecommendTabFragment<V> extends Fragment implements
		MyOnClickLinsenner, OnClickListener {
	private List<ImageView> views = new ArrayList<ImageView>();
	private List<ADInfo> infos = new ArrayList<ADInfo>();
	private CycleViewPager cycleViewPager;
	private SharedPreferences sharedPreferences;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
		initData();
		configImageLoader();

	}

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_recommend, null);
		initView(view);
		if (!(sharedPreferences.getString("json", "").equals(""))) {

			try {
				getData(sharedPreferences.getString("json", ""));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		initEvent();
		return view;
	}

	/**
	 * 数据下载完回调
	 */
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			lordViewData();
		};
	};

	private void initEvent() {
		// TODO Auto-generated method stub
		iconAndText_one.setMyOnClickListener(this);
		iconAndText_two.setMyOnClickListener(this);
		iconAndText_three.setMyOnClickListener(this);
		iconAndText_four.setOnClickListener(this);

		img01_find.setOnClickListener(this);
		img02_find.setOnClickListener(this);
		img03_find.setOnClickListener(this);
		text_moreFind.setOnClickListener(this);

	}

	private IconAndTextView iconAndText_one;
	private IconAndTextView iconAndText_two;
	private IconAndTextView iconAndText_three;
	private ImageView iconAndText_four;
	private AnimationDrawable animationDrawable;
	private ImageView img01_find;
	private ImageView img02_find;
	private ImageView img03_find;
	private TextView text_moreFind;
	private List<ImageView> subjectViews;

	private List<ImageView> discountViews;
	private ImageView img01_discount;
	private ImageView img02_discount;
	private ImageView img03_discount;
	private ImageView img04_discount;
	private ImageView img05_discount;
	private TextView img02_text_discounTtilt;
	private TextView img03_text_discounTtilt;
	private TextView img04_text_discounTtilt;
	private TextView img05_text_discounTtilt;
	private TextView img02_text_discounNum;
	private TextView img03_text_discounNum;
	private TextView img04_text_discounNum;
	private TextView img05_text_discounNum;
	private TextView img02_text_discounPrice;
	private TextView img03_text_discounPrice;
	private TextView img04_text_discounPrice;
	private TextView img05_text_discounPrice;

	private void initView(View view) {
		// TODO Auto-generated method stub
		iconAndText_one = (IconAndTextView) view
				.findViewById(R.id.iconAndText_one);
		iconAndText_two = (IconAndTextView) view
				.findViewById(R.id.iconAndText_two);
		iconAndText_three = (IconAndTextView) view
				.findViewById(R.id.iconAndText_three);
		iconAndText_four = (ImageView) view.findViewById(R.id.iconAndText_four);
		animationDrawable = (AnimationDrawable) iconAndText_four
				.getBackground();

		img01_find = (ImageView) view.findViewById(R.id.img01_find);
		img02_find = (ImageView) view.findViewById(R.id.img02_find);
		img03_find = (ImageView) view.findViewById(R.id.img03_find);
		subjectViews = new ArrayList<ImageView>();
		subjectViews.add(img01_find);
		subjectViews.add(img02_find);
		subjectViews.add(img03_find);
		text_moreFind = (TextView) view.findViewById(R.id.text_moreFind);

		img01_discount = (ImageView) view.findViewById(R.id.img01_discount);
		img02_discount = (ImageView) view.findViewById(R.id.img02_discount);
		img03_discount = (ImageView) view.findViewById(R.id.img03_discount);
		img04_discount = (ImageView) view.findViewById(R.id.img04_discount);
		img05_discount = (ImageView) view.findViewById(R.id.img05_discount);
		discountViews = new ArrayList<ImageView>();

		discountViews.add(img01_discount);
		discountViews.add(img02_discount);
		discountViews.add(img03_discount);
		discountViews.add(img04_discount);
		discountViews.add(img05_discount);
		img02_text_discounTtilt = (TextView) view
				.findViewById(R.id.img02_text_discounTtilt);
		img03_text_discounTtilt = (TextView) view
				.findViewById(R.id.img03_text_discounTtilt);
		img04_text_discounTtilt = (TextView) view
				.findViewById(R.id.img04_text_discounTtilt);
		img05_text_discounTtilt = (TextView) view
				.findViewById(R.id.img05_text_discounTtilt);
		img02_text_discounNum = (TextView) view
				.findViewById(R.id.img02_text_discounNum);
		img03_text_discounNum = (TextView) view
				.findViewById(R.id.img03_text_discounNum);
		img04_text_discounNum = (TextView) view
				.findViewById(R.id.img04_text_discounNum);
		img05_text_discounNum = (TextView) view
				.findViewById(R.id.img05_text_discounNum);
		img02_text_discounPrice = (TextView) view
				.findViewById(R.id.img02_text_discounPrice);
		img03_text_discounPrice = (TextView) view
				.findViewById(R.id.img03_text_discounPrice);
		img04_text_discounPrice = (TextView) view
				.findViewById(R.id.img04_text_discounPrice);
		img05_text_discounPrice = (TextView) view
				.findViewById(R.id.img05_text_discounPrice);
	}

	private List<Map<String, String>> slideData;
	private List<Map<String, String>> discountData;
	private List<Map<String, String>> subjectData;
	private List<Map<String, String>> discount_subjectData;
	private List<Map<String, String>> checkinData;

	private void initData() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String json = HttpURlUtils.getJson(QYApi.RECOMMENDPATH);

					getData(json);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();

	}

	private void getData(String json) throws JSONException {
		if (json != null) {
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("json", json);
			editor.commit();

			JSONObject obj = new JSONObject(json).optJSONObject("data");
			JSONArray slide = obj.optJSONArray("slide");
			slideData = new ArrayList<Map<String, String>>();
			for (int i = 0; i < slide.length(); i++) {
				JSONObject optJSONObject = slide.optJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("url", optJSONObject.optString("url"));
				map.put("photo", optJSONObject.optString("photo"));
				slideData.add(map);

			}

			JSONArray discount = obj.optJSONArray("discount");
			discountData = new ArrayList<Map<String, String>>();
			for (int i = 0; i < discount.length(); i++) {
				JSONObject optJSONObject = discount.optJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("price", optJSONObject.optString("price"));
				map.put("photo", optJSONObject.optString("photo"));
				map.put("priceoff", optJSONObject.optString("priceoff"));
				map.put("id", optJSONObject.optString("id"));
				map.put("title", optJSONObject.optString("title"));
				map.put("end_date", optJSONObject.optString("end_date"));
				discountData.add(map);
			}

			JSONArray subject = obj.optJSONArray("subject");
			subjectData = new ArrayList<Map<String, String>>();
			for (int i = 0; i < subject.length(); i++) {
				JSONObject optJSONObject = subject.optJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("url", optJSONObject.optString("url"));
				map.put("photo", optJSONObject.optString("photo"));
				map.put("title", optJSONObject.optString("title"));
				subjectData.add(map);
			}

			JSONArray discount_subject = obj.optJSONArray("discount_subject");
			discount_subjectData = new ArrayList<Map<String, String>>();
			for (int i = 0; i < discount_subject.length(); i++) {
				JSONObject optJSONObject = discount_subject.optJSONObject(i);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("url", optJSONObject.optString("url"));
				map.put("photo", optJSONObject.optString("photo"));
				discount_subjectData.add(map);
			}

			JSONObject checkin = obj.optJSONObject("checkin");
			checkinData = new ArrayList<Map<String, String>>();
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("status", checkin.optString("status"));
			map.put("url", checkin.optString("url"));
			checkinData.add(map);

			handler.sendEmptyMessage(1);
		}
	}

	/**
	 * 加载数据到控件，lord图片和文字
	 */
	@SuppressLint("NewApi")
	private void lordViewData() {
		lordSlide();
		lordsubject();
		lordDiscount();

	}

	/**
	 * 加载抢折扣数据
	 */
	private void lordDiscount() {
		// TODO Auto-generated method stub
		LayoutParams para;
		if (discountData != null) {
			for (int i = 0; i < discountViews.size(); i++) {

				ImageView img = discountViews.get(i);
				para = img.getLayoutParams();
				float parentWidth = (float) ((View) img.getParent()).getWidth();
				if (img01_discount == img) {
					ImageLoader.getInstance().displayImage(
							discount_subjectData.get(i).get("photo"), img);
					para.height = (int) (parentWidth * 375 / 912);
				} else {
					ImageLoader.getInstance().displayImage(
							discountData.get(i - 1).get("photo"), img);
					para.height = (int) (parentWidth * 350 / 533);
				}
				discountViews.get(i).setLayoutParams(para);
				switch (i) {
				case 1:
					img02_text_discounTtilt.setText(discountData.get(i - 1)
							.get("title"));
					img02_text_discounNum.setText(discountData.get(i - 1).get(
							"priceoff"));
					img02_text_discounPrice.setText(getNum(discountData.get(
							i - 1).get("price")));
					break;
				case 2:
					img03_text_discounTtilt.setText(discountData.get(i - 1)
							.get("title"));
					img03_text_discounNum.setText(discountData.get(i - 1).get(
							"priceoff"));
					img03_text_discounPrice.setText(getNum(discountData.get(
							i - 1).get("price")));
					break;
				case 3:
					img04_text_discounTtilt.setText(discountData.get(i - 1)
							.get("title"));
					img04_text_discounNum.setText(discountData.get(i - 1).get(
							"priceoff"));
					img04_text_discounPrice.setText(getNum(discountData.get(
							i - 1).get("price")));
					break;
				case 4:
					img05_text_discounTtilt.setText(discountData.get(i - 1)
							.get("title"));
					img05_text_discounNum.setText(discountData.get(i - 1).get(
							"priceoff"));
					img05_text_discounPrice.setText(getNum(discountData.get(
							i - 1).get("price")));
					break;

				default:
					break;
				}

			}
		}

	}

	/**
	 * 获取字符串中的数字
	 * 
	 * @param string
	 * @return
	 */
	private String getNum(String string) {
		// TODO Auto-generated method stub
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(string);
		return m.replaceAll("").trim();
	}

	/**
	 * 加载发现下一站数据
	 */
	private void lordsubject() {
		// TODO Auto-generated method stub
		LayoutParams para;
		if (subjectData != null) {
			for (int i = 0; i < subjectData.size(); i++) {
				ImageView img = subjectViews.get(i);
				para = img.getLayoutParams();
				ImageLoader.getInstance().displayImage(
						subjectData.get(i).get("photo"), img);
				float parentWidth = (float) ((LinearLayout) img.getParent())
						.getWidth();
				if (img01_find == img) {
					para.height = (int) (parentWidth * 375 / 912);
				} else {
					para.height = (int) (parentWidth * 350 / 533 / 2);
				}
				subjectViews.get(i).setLayoutParams(para);
				subjectData.get(i).get("url");
				subjectData.get(i).get("title");
			}
		}

	}

	/**
	 * 加载slide
	 */
	private void lordSlide() {
		if (slideData != null) {

			cycleViewPager = (CycleViewPager) (getFragmentManager()
					.findFragmentById(R.id.fragment_cycle_viewpager_content));

			for (int i = 0; i < slideData.size(); i++) {
				ADInfo info = new ADInfo();
				info.setUrl(slideData.get(i).get("photo"));
				info.setContent("图片-->" + i);
				infos.add(info);
			}
			// 将最后一个ImageView添加进来
			views.add(ViewFactory.getImageView(getActivity(),
					infos.get(infos.size() - 1).getUrl()));
			for (int i = 0; i < infos.size(); i++) {
				views.add(ViewFactory.getImageView(getActivity(), infos.get(i)
						.getUrl()));
			}
			// 将第一个ImageView添加进来
			views.add(ViewFactory.getImageView(getActivity(), infos.get(0)
					.getUrl()));

			// 设置循环，在调用setData方法前调用
			cycleViewPager.setCycle(true);

			// 在加载数据前设置是否循环
			cycleViewPager.setData(views, infos, mAdCycleViewListener);
			// 设置轮播
			cycleViewPager.setWheel(true);

			// 设置轮播时间，默认5000ms
			cycleViewPager.setTime(2000);
			// 设置圆点指示图标组居中显示，默认靠右
			cycleViewPager.setIndicatorCenter();
		}
	}

	/**
	 * slide监听回调
	 */
	private ImageCycleViewListener mAdCycleViewListener = new ImageCycleViewListener() {

		@Override
		public void onImageClick(ADInfo info, int position, View imageView) {
			if (cycleViewPager.isCycle()) {
				position = position - 1;
				Toast.makeText(getActivity(),
						"position-->" + info.getContent(), Toast.LENGTH_SHORT)
						.show();
			}

		}

	};

	/**
	 * 配置ImageLoder
	 */
	private void configImageLoader() {
		// 初始化ImageLoader
		@SuppressWarnings("deprecation")
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
				.showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
				.showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
				.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
				.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
				// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
				.build(); // 创建配置过得DisplayImageOption对象

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getActivity().getApplicationContext())
				.defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}

	private boolean isDown;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iconAndText_one:
			if (isDown) {

				iconAndText_one.setIcon(R.drawable.ic_home_jinnang_normal);
				isDown = !isDown;
			} else {
				iconAndText_one.setIcon(R.drawable.ic_home_jinnang_pressed);
				isDown = !isDown;
			}

			break;
		case R.id.iconAndText_two:
			if (isDown) {

				iconAndText_two.setIcon(R.drawable.ic_home_sale_normal);
				isDown = !isDown;
			} else {
				iconAndText_two.setIcon(R.drawable.ic_home_sale_pressed);
				isDown = !isDown;
			}

			break;
		case R.id.iconAndText_three:
			if (isDown) {

				iconAndText_three.setIcon(R.drawable.ic_home_hotel_normal);
				isDown = !isDown;
			} else {
				iconAndText_three.setIcon(R.drawable.ic_home_hotel_pressed);
				isDown = !isDown;
			}

			break;
		case R.id.iconAndText_four:
			startAnml();

			break;

		default:
			break;
		}

	}

	/**
	 * start动画
	 */
	private void startAnml() {
		// TODO Auto-generated method stub
		animationDrawable.start();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				animationDrawable.stop();
			}
		}, 7000);

	}

}
