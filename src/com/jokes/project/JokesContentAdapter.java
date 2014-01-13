package com.jokes.project;

import java.util.List;
import java.util.Random;
import com.dlnetwork.AdView;
import com.jokes.net.JokesModel;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class JokesContentAdapter extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<Object> list;
	private int[] images={R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six,R.drawable.seven,R.drawable.eight,R.drawable.nine,R.drawable.ten,R.drawable.eleven,R.drawable.twelve,R.drawable.thirteen,
			R.drawable.fourteen,R.drawable.fifteen,R.drawable.sixteen,R.drawable.seventeen,R.drawable.eighteen,R.drawable.nineteen,R.drawable.twenty,R.drawable.twenty_one,R.drawable.twenty_two,R.drawable.twenty_three,R.drawable.twenty_four,R.drawable.twenty_five,R.drawable.twenty_six,R.drawable.twenty_seven,R.drawable.twenty_eight,R.drawable.twenty_nine,R.drawable.thirty,R.drawable.thirty_one,R.drawable.thirty_two,R.drawable.thirty_three,R.drawable.thirty_four,R.drawable.thirty_five,R.drawable.thirty_six,R.drawable.thirty_seven,R.drawable.thirty_eight,
			R.drawable.thirty_nine,R.drawable.forty,R.drawable.forty_one,R.drawable.forty_two,R.drawable.forty_three,R.drawable.forty_four,R.drawable.forty_five,R.drawable.forty_six,R.drawable.forty_seven,R.drawable.forty_eight,R.drawable.forty_nine,R.drawable.fifty};

	// 构造方法，参数list传递的就是这一组数据的信息
	public JokesContentAdapter(Context context, List<Object> list) {
		layoutInflater = LayoutInflater.from(context);
		this.list = list;
	}

	// 得到总的数量
	public int getCount() {
		// TODO Auto-generated method stub
		return this.list != null ? this.list.size() : 0;
	}

	// 根据ListView位置返回View
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.list.get(position);
	}

	// 根据ListView位置得到List中的ID
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	// 根据位置得到View对象
	public View getView(int position, View convertView, ViewGroup parent) {
		if(list.get(position) instanceof AdView)
		{
			AdView adView=(AdView)list.get(position);
			adView.setBackgroundResource(R.drawable.listview_item);
			return adView;	
		}
		if (convertView == null||convertView instanceof AdView) {
			convertView = layoutInflater.inflate(R.layout.pulldown_item, null);
		}
		TextView tv = (TextView) convertView.findViewById(R.id.message);
		tv.setText(Html.fromHtml(((JokesModel)list.get(position)).content));
		
		TextView userNameTv=(TextView) convertView.findViewById(R.id.userName);
		userNameTv.setText(((JokesModel)list.get(position)).author.trim());
		
		TextView tvDate = (TextView) convertView.findViewById(R.id.date);
		tvDate.setText(((JokesModel)list.get(position)).createTime);

		
		Random randomImage=new Random();
		ImageView image=(ImageView) convertView.findViewById(R.id.image);
		image.setBackgroundResource(images[randomImage.nextInt(images.length-1)]);
		return convertView;
	}
}
