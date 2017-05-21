package com.ks.hihi.haha.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.iid.FirebaseInstanceId;
import com.ks.hihi.haha.R;
import com.ks.hihi.haha.items.Users;
import com.ks.hihi.haha.main.MainActivity;
import com.ks.hihi.haha.request.RequestOne;
import com.ks.hihi.haha.utill.Preferences;
import com.tsengvn.typekit.TypekitContextWrapper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener{

    private SignInButton googleBtn = null;
    private CardView startBtn = null;
    private GoogleApiClient mGoogleApiClient;
    // [START declare_auth]
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mFirebaseAuthListener;
    // [END declare_auth]
    public Preferences sp;

    static final String TAG = LoginActivity.class.getName();
    static final int RC_GOOGLE_SIGN_IN = 9001;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = new Preferences(this);
        //로그아웃
        FirebaseAuth.getInstance().signOut();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if ( user != null ) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {

                }

            }
        };

        googleBtn = (SignInButton) findViewById(R.id.google_login_button);
        googleBtn.setSize(SignInButton.SIZE_STANDARD);
        googleBtn.setColorScheme(SignInButton.COLOR_DARK);
        googleBtn.setOnClickListener(this);

        startBtn  = (CardView) findViewById(R.id.start_button);
        startBtn.setOnClickListener(this);

        // [START config_signin]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        // [END config_signin]

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == googleBtn.getId()){
            Intent loginIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
            startActivityForResult(loginIntent, RC_GOOGLE_SIGN_IN);
        } else if (v.getId() == startBtn.getId()) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( mFirebaseAuthListener != null )
            mFirebaseAuth.removeAuthStateListener(mFirebaseAuthListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode == RC_GOOGLE_SIGN_IN ) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if ( result.isSuccess() ) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else {
                Log.d(TAG, "Google Login Failed." + result.getStatus());
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mFirebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        //hideProgressDialog();
        if (user != null) {
            user = mFirebaseAuth.getCurrentUser();

            sp.setUserId(user.getUid());
            sp.setUserName(user.getDisplayName());
            sp.setUserToken(FirebaseInstanceId.getInstance().getToken());

            final Users users = new Users();
            users.setUser_id(user.getUid());
            users.setUser_name(user.getDisplayName());
            users.setUser_token(FirebaseInstanceId.getInstance().getToken());

            //enqueue 방식
            RequestOne.selectOne service = RequestOne.retrofitHttp.create(RequestOne.selectOne.class);
            Call<Users> call = null;

            call = service.getUser("users", user.getUid());
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    // user등록이 안되있을떄 insert, 되어있으면 token update
                    if (response.body().getUser_id() == null || response.body().getUser_id().equals("")){
                        RequestOne.selectOne service = RequestOne.retrofitHttp.create(RequestOne.selectOne.class);
                        call = null;

                        call = service.insertUser("users", users);
                        call.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {

                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {

                            }
                        });
                    } else if(response.body().getUser_id().equals(users.getUser_id())){
                        RequestOne.selectOne service = RequestOne.retrofitHttp.create(RequestOne.selectOne.class);
                        call = null;

                        call = service.updateUser("users", users.getUser_id(), users);
                        call.enqueue(new Callback<Users>() {
                            @Override
                            public void onResponse(Call<Users> call, Response<Users> response) {

                            }

                            @Override
                            public void onFailure(Call<Users> call, Throwable t) {

                            }
                        });
                    } else {
                        Log.e("getusererror", "getusererror");
                    }
                }
                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });

            /*new AsyncTask<Void, Void, Users>() {
                @Override
                protected Users doInBackground(Void... params) {
                    Users result = null;
                    try {
                        RequestOne.selectOne service = RequestList.retrofitHttp.create(RequestOne.selectOne.class);
                        Call<Users> call = null;

                        call = service.insertTask("users", users);

                        result = call.execute().body();
                    } catch (Exception e) {
                        Log.d("Request Error", e.toString());
                    }
                    return result;
                }

                @Override
                protected void onPostExecute(Users result) {

                }
            }.execute();*/

        } else {


        }
    }
}
