package com.usuario.mariana_pc.evaluacionfinal;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.gms.ads.AdView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private AccessToken accessToken;
    private TextView textView;
    private ProfilePictureView profilePicture;
    private CallbackManager callbackManager;
    private AdView adview;

    @Override
    public void onDestroy() {
        if (adview!=null){
            adview.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        if (adview!=null){
            adview.resume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (adview!=null){
            adview.pause();
        }
        super.onPause();
    }

    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if (profile != null){
                textView.setText("¡Bienvenido " + profile.getName() + "!");
                profilePicture.setProfileId(profile.getId());
            }

        }
        @Override
        public void onCancel() {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "No se aprobó el login", Toast.LENGTH_SHORT);
            toast.show();
        }

        @Override
        public void onError(FacebookException error) {
            Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Error en el login", Toast.LENGTH_SHORT);
            toast.show();
        }
    };
    public MainActivityFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //3
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        //16
        callbackManager = CallbackManager.Factory.create();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //14
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        //18
        loginButton.registerCallback(callbackManager, callback);

        textView = (TextView) view.findViewById(R.id.text_details);
        profilePicture = (ProfilePictureView) view.findViewById(R.id.profilePicture);

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null){
                    textView.setText("Sesión no iniciada");
                    profilePicture.setProfileId("");
                }
            }
        };

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
