package com.northcoders.media_tracker_front.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

public class Watching extends BaseObservable {
  private String title;

  public Watching() {
  }

  public Watching(String title) {
    this.title = title;
  }

  @Bindable
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
