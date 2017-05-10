package vn.edu.hust.set.dungdao.sharephotofb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ShareDialog shareDialog;
    private CallbackManager callbackManager;
    //private LoginManager loginManager;
    private LoginManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState){

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            FacebookSdk.sdkInitialize(getApplicationContext());
            shareDialog = new ShareDialog(this);
            callbackManager = CallbackManager.Factory.create();

            List<String> permissionNeeds = Arrays.asList("publish_actions");

            //this loginManager helps you eliminate adding a LoginButton to your UI
            manager = LoginManager.getInstance();

            manager.logInWithPublishPermissions(this, permissionNeeds);

            manager.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
            {
                @Override
                public void onSuccess(LoginResult loginResult)
                {
                    sharePhotoToFacebook();
                    Log.d("myLog","it is ok");
                }

                @Override
                public void onCancel()
                {
                    System.out.println("onCancel");
                    Log.d("myLog", "cancel");
                }

                @Override
                public void onError(FacebookException exception)
                {
                    Log.d("myLog", "error");
                    System.out.println("onError");
                }
            });
        }

    private void sharePhotoToFacebook(){
        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.mirandakerr);
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(image)
                .setCaption("Đào Khánh Dũng")
                .build();

        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();

        //ShareApi.share(content, null);
        //ShareApi.share(content, null);
        shareDialog.show(content);

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data)
    {
        super.onActivityResult(requestCode, responseCode, data);
        callbackManager.onActivityResult(requestCode, responseCode, data);
    }

}
