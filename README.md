# Welcome to the Movie Tracker Frontend



---

## How to implement  a Recylerview

### 1. Setting up the dependencies and configurations

##### 1. Update the manifests

1. add permission for internet access and network security configuration

```xml
<manifest ... >
	<uses-permission android:name="android.permission.INTERNET" />
    <application
		android:networkSecurityConfig="@xml/network_security_config"
        ...>
    	...
    </application>
</manifest>
```

##### 2. Add the network_security_config file

1. under `app/src/main/res/xml/` create the `network_security_config.xml` file and add the following code:

```xml 
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="true">
        <domain includeSubdomains="true">10.0.2.2</domain>
    </domain-config>
</network-security-config>
```

##### 3. Add dependencies to Gradle

1. open `app/build.gradle` *(Module :app)* and add the following dependencies:

   ```gradle
   android {
   	 buildFeatures{
           dataBinding true
       }
   }
   
   dependencies {
       implementation('com.squareup.retrofit2:retrofit:(insert latest version)')
       implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
       implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
   }
   ```

   - allows for databinding
   - implements retrofit for back-end calls and http status code  

2. synchronise / update gradle

---

### 2. Overview

There are 7 elements needed:

 	1. the **model**: *contains the classes for the fetched data*
 	2. the **service**:
     - **...ApiService** (*interface*): contains the get requests
     - `RetrofitInstance` (*class*): has the code to make the requests

3. the **...ViewModel**: 
4. the **...Adapter**: binds your data to the views. The adapter is responsible for inflating the item layout and binding the data to each individual item
5. the **...Activity** / **...Fragment**: set up the `RecyclerView` with a `LayoutManager` and set the adapter
6. the (*layout*) **..._item.xml**: the design for the individual "list item" in the recyclerview
7. the (*layout*) **activity_...xml** / **fragment_...xml**: contains the recyclerview widget

In the following section describes how to set up a basic "watched history" recyclerview.



##### 1. the Model

1. create a the `model` package

2. in there create the `WatchHistory` class, it will hold the fetched data: 

   ```java
   import androidx.databinding.BaseObservable;
   import androidx.databinding.Bindable;
   
       public class WatchHistory extends BaseObservable {
           // fields for what ever data you going to need
           String title;
           String info;
           int rating;
   
           // empty constructor
           public WatchHistory(){}
   
           // consturctor 
           public WatchHistory(String title, String info, int rating) {
               this.title = title;
               this.info = info;
               this.rating = rating;
           }
           
           // there needs to be a getter and setter for each field
   
           // convert int to string for the layout later
           @Bindable
           public String getRating() {
               return Integer.toString(rating);
           }
   
           // make sure can get only an string which can be parsed to an int
           public void setRating(String rating) {
               this.rating = Integer.parseInt(rating);
           }
   
           @Bindable
           public String getTitle() {
               return title;
           }
   
           public void setTitle(String title) {
               this.title = title;
           }
   
           @Bindable
           public String getInfo() {
               return info;
           }
   
           public void setInfo(String info) {
               this.info = info;
           }
       }
   ```

   

2. in the same package, create the `WatchHistoryRepository` class 

```java
import android.app.Application;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.northcoders.media_tracker_front.service.MovieApiService;
import com.northcoders.media_tracker_front.service.RetrofitInstance;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WatchHistoryRepository {
    private MutableLiveData<List<WatchHistory>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public WatchHistoryRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<WatchHistory>> getMutableLiveData(){
        MovieApiService movieApiService = RetrofitInstance.getService();
		
        // getHistory() fetches the data
        Call<List<WatchHistory>> call = movieApiService.getHistory();
        call.enqueue(new Callback<List<WatchHistory>>(){
            @Override
            public void onResponse(Call<List<WatchHistory>> call, Response<List<WatchHistory>> response) {
                List<WatchHistory> albums = response.body();
                mutableLiveData.setValue(albums);
            }

            @Override
            public void onFailure(Call<List<WatchHistory>> call, Throwable t) {
                Log.i("GET request", t.getMessage());
            }
        });
        return mutableLiveData;
    }
```

