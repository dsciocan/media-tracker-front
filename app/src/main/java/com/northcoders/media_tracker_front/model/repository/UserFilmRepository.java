package com.northcoders.media_tracker_front.model.repository;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.MutableLiveData;
import com.northcoders.media_tracker_front.model.UserFilm;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import com.northcoders.media_tracker_front.service.UserActionsService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFilmRepository {
    private MutableLiveData<List<UserFilm>> watchedFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<List<UserFilm>> bookmarkedFilmMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<UserFilm> userFilm = new MutableLiveData<>();
    private Application application;

    public UserFilmRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<UserFilm>> getWatchedFilmMutableLiveData(){
        UserActionsService userActionsService = RetrofitInstance.getUserService();
        // getHistory() fetches the data, see 'service'
        Call<List<UserFilm>> call = userActionsService.getHistory();
        call.enqueue(new Callback<List<UserFilm>>(){
            @Override
            public void onResponse(Call<List<UserFilm>> call, Response<List<UserFilm>> response) {
                List<UserFilm> historyList = response.body();
                watchedFilmMutableLiveData.setValue(historyList);
            }

            @Override
            public void onFailure(Call<List<UserFilm>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return watchedFilmMutableLiveData;
    }

    public MutableLiveData<List<UserFilm>> getBookmarkedFilmMutableLiveData() {
        UserActionsService userActionsService = RetrofitInstance.getUserService();

        Call<List<UserFilm>> call = userActionsService.getBookmarked();
        call.enqueue(new Callback<List<UserFilm>>() {
            @Override
            public void onResponse(Call<List<UserFilm>> call, Response<List<UserFilm>> response) {
                List<UserFilm> userFilmList = response.body();
                bookmarkedFilmMutableLiveData.setValue(userFilmList);
            }

            @Override
            public void onFailure(Call<List<UserFilm>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return bookmarkedFilmMutableLiveData;
    }

    public MutableLiveData<UserFilm> getUserFilmDetails(Long id){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<UserFilm> call = service.getUserFilmDetails(id);
        call.enqueue(new Callback<UserFilm>() {
            @Override
            public void onResponse(Call<UserFilm> call, Response<UserFilm> response) {
                Log.i("WatchHistoryRepo Response", String.valueOf(response.code()));
               Log.i("WatchHistoryRepo Response", response.body().toString());
                if(response.code() == 200){
                    UserFilm userCall = response.body();
                    userFilm.setValue(userCall);
                }

            }

            @Override
            public void onFailure(Call<UserFilm> call, Throwable t) {
                Log.e("WatchHistoryRepo Response", t.getMessage());
            }
        });


        return userFilm;
    }


    public void deleteUserFilm(Long id){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.deleteUserFilm(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("Watched History Repo", String.valueOf(response.code()));
                if(response.code() == 200){
                    Toast.makeText(application.getApplicationContext(), "The Movie Has Been Removed", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Watched History Repo", t.getMessage());
            }
        });
    }

    public void updateUserFilm(Long id,UserFilm film){
        UserActionsService service = RetrofitInstance.getUserService();
        Call<Void> call = service.updateUserFilm(id,film);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.i("WatchHistoryRepo Response", String.valueOf(response.code()));
                Toast.makeText(application.getApplicationContext(), "The Movie Has Been Updated", Toast.LENGTH_SHORT);

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }


}
