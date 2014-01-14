package com.jokes.net;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GetJokesService {
	private final static String PRE_URL = "http://z.turbopush.com/";
	private final static String GET_JOKES = "jokelist.php?p=";

	public static void GetJokes(String page,String sort,String so, GetListener jokesListener) {
		List<JokesModel> modelList = new ArrayList<JokesModel>();
		String jsonJokes = NetRequest.connectToURL(PRE_URL + GET_JOKES,page+"&o="+sort+"&"+so);
		try {
			JSONObject jsonList = new JSONObject(jsonJokes);
			JSONArray jsonArray = jsonList.getJSONArray("data");
			JokesModel modelJokes=null;
			for (int i = 0; i < jsonArray.length(); i++) {
				modelJokes=new JokesModel();
				JSONObject model = (JSONObject) jsonArray.getJSONObject(i);
				modelJokes.content=model.getString("con");
				modelJokes.author=model.getString("author");
				modelJokes.createTime=model.getString("date");
				modelList.add(modelJokes);
				//modelList.add(model.getString("con"));
			}
			jokesListener.getSuccess(modelList);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			jokesListener.getFaild("网络异常!");
		}
	}
}
