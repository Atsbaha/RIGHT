package com.example.saintmary.right.Common;

import com.example.saintmary.right.Model.MusicRequest;
import com.example.saintmary.right.Model.User;
import com.example.saintmary.right.Remote.APIService;
import com.example.saintmary.right.Remote.RetrofitClient;

public class Common {
    public static User currentUser;
    public static MusicRequest musicRequest;

    private static final String BASE_URL="https://fcm.google.com/";
    public static APIService getFCMService()
    {
        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }

    public static final String DELETE = "Delete";
    public static final String USER_KEY = "Delete";
    public static final String PWD_KEY= "Delete";

   public static String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return "Placed";
        else if(status.equals("1"))
            return "Preparing";
        else if(status.equals("2"))
            return "Packaging";
        else
            return "Food Ready";
    }
}
