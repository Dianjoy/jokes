package com.jokes.project;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dlnetwork.AdView;
import com.dlnetwork.Dianle;
import com.dlnetwork.InitStatusListener;
import com.dlnetwork.ViewType;
import com.jokes.net.GetJokesService;
import com.jokes.net.GetListener;
import com.jokes.net.JokesModel;
import com.jokes.net.Utils;
import com.jokes.project.PullDownView.OnPullDownListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class JokesMainActivity extends Activity implements OnPullDownListener,
		OnItemClickListener {

	/** Handler What加载数据完毕 **/
	private static final int WHAT_DID_LOAD_DATA = 0;
	/** Handler What更新数据完毕 **/
	private static final int WHAT_DID_REFRESH = 1;
	/** Handler What更多数据完毕 **/
	private static final int WHAT_DID_MORE = 2;
	/** Handler What加载数据出错 **/
	private static final int WHAT_DID_ERROR = 3;
	private int countPage = 1;
	private int cesPage = 1;
	private ListView mListView;
	private JokesContentAdapter contentAdapter;
	private PullDownView mPullDownView;
	private List<Object> mStrings = new ArrayList<Object>();
	private static ArrayList<AdView> adView = new ArrayList<AdView>();
	public static int margins[] = { 5, 5, 5, 5 };
	private int temp = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jokesmainactivity);
		UmengUpdateAgent.update(this);
		// 注册广告所需的Service
		Dianle.setCustomService(getPackageName() + ".MyService");
		// 初始化广告
		Dianle.initNativeAds(this, "c68e360e38fca0851a3e9d0237c19613",
				new InitStatusListener() {

					@Override
					public void initStatusSuccessed() {
						// 广告初始化成功后,获取广告数据
						adView = Dianle.getShowViews(JokesMainActivity.this,
								10, margins);
					}

					@Override
					public void initStatusFailed(String arg0) {
						// TODO Auto-generated method stub
					}
				});
		/*
		 * 1.使用PullDownView 2.设置OnPullDownListener 3.从mPullDownView里面获取ListView
		 */
		mPullDownView = (PullDownView) findViewById(R.id.pull_down_view);

		mPullDownView.setOnPullDownListener(this);

		mListView = mPullDownView.getListView();

		mListView.setOnItemClickListener(this);
		contentAdapter = new JokesContentAdapter(this, mStrings);
		mListView.setAdapter(contentAdapter);
		mPullDownView.enableAutoFetchMore(true, 1);
		mPullDownView.setHideFooter();
		mPullDownView.setShowFooter();
		mPullDownView.setHideHeader();
		mPullDownView.setShowHeader();

		mListView.setDividerHeight(0);

		String count = Utils.getPreferenceStr(JokesMainActivity.this, "count");
		if (count.equals("") || Integer.valueOf(count) >= 7000) {
			count = "1";
		}
		loadData(count);
	}

	@Override
	public void onRefresh() {

		new Thread(new Runnable() {

			@Override
			public void run() {
				if (temp == 1) {
					mPullDownView.RefreshComplete();
					temp = 0;
					return;
				}
				/** 关闭 刷新完毕 ***/

				String min = null;
				try {
					min = "so="
							+ URLEncoder.encode(Utils.getPreferenceStr(
									JokesMainActivity.this, "min"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("aa", min);
				GetJokesService.GetJokes(cesPage + "", "2", min,
						new GetListener() {

							@Override
							public void getSuccess(List<JokesModel> jokesList) {
								// TODO Auto-generated method stub
								cesPage++;
								Message msg = mUIHandler
										.obtainMessage(WHAT_DID_REFRESH);
								msg.obj = jokesList;
								msg.sendToTarget();
								mPullDownView.RefreshComplete();
							}

							@Override
							public void getFaild(String error) {
								// TODO Auto-generated method stub
								Message msg = mUIHandler
										.obtainMessage(WHAT_DID_ERROR);
								msg.obj = error;
								msg.sendToTarget();
								mPullDownView.RefreshComplete();
							}
						});
			}
		}).start();
	}

	/** 刷新事件接口 这里要注意的是获取更多完 要关闭 更多的进度条 notifyDidMore() **/
	@Override
	public void onMore() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// 告诉它获取更多完毕 这个事线程安全的 可看源代码

				if (countPage >= 7000) {
					countPage = 1;
				}
				Utils.setPreferenceStr(JokesMainActivity.this, "count",
						countPage + "");

				String max = null;
				try {
					max = "so="
							+ URLEncoder.encode(Utils.getPreferenceStr(
									JokesMainActivity.this, "max"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("aa", max);
				GetJokesService.GetJokes(countPage + "", "1", max,
						new GetListener() {

							@Override
							public void getSuccess(List<JokesModel> jokesList) {
								// TODO Auto-generated method stub
								countPage++;
								Message msg = mUIHandler
										.obtainMessage(WHAT_DID_MORE);
								msg.obj = jokesList;
								msg.sendToTarget();
								mPullDownView.notifyDidMore();
							}

							@Override
							public void getFaild(String error) {
								// TODO Auto-generated method stub
								Message msg = mUIHandler
										.obtainMessage(WHAT_DID_ERROR);
								msg.obj = error;
								msg.sendToTarget();
								mPullDownView.notifyDidMore();
							}
						});
			}
		}).start();
	}

	private Handler mUIHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_DID_LOAD_DATA: {
				if (msg.obj != null) {
					List<Object> strings = (List<Object>) msg.obj;
					Utils.setPreferenceStr(JokesMainActivity.this, "min",
							((JokesModel) strings.get(0)).createTime);
					Utils.setPreferenceStr(
							JokesMainActivity.this,
							"max",
							((JokesModel) strings.get(strings.size() - 1)).createTime);
					if (!strings.isEmpty()) {
						mStrings.addAll(strings);
						if (adView.size() > 0) {
							mStrings.add(adView.get(0));
							adView.remove(0);
						}
						contentAdapter.notifyDataSetChanged();
					}
				}
				break;
			}
			case WHAT_DID_REFRESH: {
				List<Object> body = (List<Object>) msg.obj;
				Utils.setPreferenceStr(JokesMainActivity.this, "min",
						((JokesModel) body.get(body.size()-1)).createTime);
				for (Object str : body) {
					mStrings.add(0, str);
				}
				if (adView.size() > 0) {
					mStrings.add(0, adView.get(0));
					adView.remove(0);
				}
				contentAdapter.notifyDataSetChanged();
				break;
			}

			case WHAT_DID_MORE: {
				List<Object> body = (List<Object>) msg.obj;
				Utils.setPreferenceStr(JokesMainActivity.this, "max",
						((JokesModel) body.get(body.size() - 1)).createTime);
				for (Object str : body) {
					mStrings.add(str);
				}
				if (adView.size() > 0) {
					mStrings.add(adView.get(0));
					adView.remove(0);
				}
				contentAdapter.notifyDataSetChanged();
				break;
			}
			case WHAT_DID_ERROR: {
				String body = (String) msg.obj;
				Toast.makeText(getApplicationContext(), body,
						Toast.LENGTH_SHORT).show();
				break;
			}
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	private void loadData(final String count) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				String max = null;
				try {
					max = "so="
							+ URLEncoder.encode(Utils.getPreferenceStr(
									JokesMainActivity.this, "max"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				GetJokesService.GetJokes(count, "1", max, new GetListener() {

					@Override
					public void getSuccess(List<JokesModel> jokesList) {
						// TODO Auto-generated method stub
						Message msg = mUIHandler
								.obtainMessage(WHAT_DID_LOAD_DATA);
						msg.obj = jokesList;
						msg.sendToTarget();
					}

					@Override
					public void getFaild(String error) {
						// TODO Auto-generated method stub
						Message msg = mUIHandler.obtainMessage(WHAT_DID_ERROR);
						msg.obj = error;
						msg.sendToTarget();
					}
				});
			}
		}).start();
	}

}
