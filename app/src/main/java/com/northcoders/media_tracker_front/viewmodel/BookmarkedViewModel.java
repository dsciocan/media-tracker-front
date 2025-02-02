package com.northcoders.media_tracker_front.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.model.BookmarkedRepository;

import java.util.List;

public class BookmarkedViewModel extends AndroidViewModel {

    BookmarkedRepository bookmarkedRepository;

    public BookmarkedViewModel(@NonNull Application application) {
        super(application);
        this.bookmarkedRepository = new BookmarkedRepository(application);
    }

    public LiveData<List<UserFilm>> getBookmarked() {
        return bookmarkedRepository.getMutableLiveData();
    }
}
