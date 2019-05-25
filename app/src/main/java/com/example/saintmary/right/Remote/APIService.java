package com.example.saintmary.right.Remote;

//import android.telecom.Call;

import com.example.saintmary.right.Model.MyResponse;
import com.example.saintmary.right.Model.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Header;
import retrofit2.http.POST;
//import retrofit2.http.Post;
//the below long key is server key from firebase cloud messaging tab copied

public interface APIService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAzN_OtkQ:APA91bFHzJ8IvmsJqwRkeyoHwTeTFSQfPPCc_nsDjW6u5rnHE4lCu12uLJG1HVn-kEToFQ1QYMZ01X1kNVzfy1Q8E0comJu6yas4NnyfIS5uluaa_CR30GXnv3rEd6R440sS6N75ZCUX"
    }
    )
    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
