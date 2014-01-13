package com.jokes.net;

import java.util.List;

public interface GetListener {
  public void getSuccess(List<JokesModel> jokesList);
  public void getFaild(String error);
}
