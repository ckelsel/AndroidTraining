package com.example.owncloud.authentication;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owncloud.BuildConfig;
import com.example.owncloud.MainApp;
import com.example.owncloud.R;
import com.owncloud.android.lib.common.accounts.AccountTypeUtils;

/**
 * Created by Administrator on 2016/9/19.
 */
public class AuthenticatorActivity extends AppCompatActivity {
    private static final String TAG = AuthenticatorActivity.class.getSimpleName();

    public static final String EXTRA_ACTION = "ACTION";
    public static final String EXTRA_ACCOUNT = "ACCOUNT";

    private static final String KEY_AUTH_TOKEN_TYPE = "AUTH_TOKEN_TYPE";

    private static final String KEY_HOST_URL_TEXT = "HOST_URL_TEXT";
    private static final String KEY_OC_VERSION = "OC_VERSION";
    private static final String KEY_SERVER_VALID = "SERVER_VALID";
    private static final String KEY_SERVER_CHECKED = "SERVER_CHECKED";
    private static final String KEY_SERVER_STATUS_TEXT = "SERVER_STATUS_TEXT";
    private static final String KEY_SERVER_STATUS_ICON = "SERVER_STATUS_ICON";
    private static final String KEY_IS_SSL_CONN = "IS_SSL_CONN";
    private static final String KEY_PASSWORD_EXPOSED = "PASSWORD_VISIBLE";
    private static final String KEY_AUTH_STATUS_TEXT = "AUTH_STATUS_TEXT";
    private static final String KEY_AUTH_STATUS_ICON = "AUTH_STATUS_ICON";
    private static final String KEY_SERVER_AUTH_METHOD = "SERVER_AUTH_METHOD";
    private static final String KEY_WAITING_FOR_OP_ID = "WAITING_FOR_OP_ID";
    private static final String KEY_AUTH_TOKEN = "AUTH_TOKEN";

    private static final String AUTH_ON = "on";
    private static final String AUTH_OPTIONAL = "optional";

    public static final byte ACTION_CREATE = 0;
    public static final byte ACTION_UPDATE_TOKEN = 1;               // requested by the user
    public static final byte ACTION_UPDATE_EXPIRED_TOKEN = 2;       // detected by the app

    private static final String UNTRUSTED_CERT_DIALOG_TAG = "UNTRUSTED_CERT_DIALOG";
    private static final String SAML_DIALOG_TAG = "SAML_DIALOG";
    private static final String WAIT_DIALOG_TAG = "WAIT_DIALOG";
    private static final String CREDENTIALS_DIALOG_TAG = "CREDENTIALS_DIALOG";
    private static final String KEY_AUTH_IS_FIRST_ATTEMPT_TAG = "KEY_AUTH_IS_FIRST_ATTEMPT";

    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_ASYNC_TASK_IN_PROGRESS = "AUTH_IN_PROGRESS";

    /// parameters from EXTRAs in starter Intent
    private byte mAction;
    private Account mAccount;
    private String mAuthTokenType;


    /// activity-level references / state
    private final Handler mHandler = new Handler();
    private ServiceConnection mOperationsServiceConnection = null;
    //private OperationsServiceBinder mOperationsServiceBinder = null;
    private AccountManager mAccountMgr;
    private Uri mNewCapturedUriFromOAuth2Redirection;


    /// Server PRE-Fragment elements
    private EditText mHostUrlInput;
    private View mRefreshButton;
    private TextView mServerStatusView;

    private TextWatcher mHostUrlInputWatcher;
    private int mServerStatusText = 0, mServerStatusIcon = 0;

    private boolean mServerIsChecked = false;
    private boolean mServerIsValid = false;
    private boolean mPendingAutoCheck = false;

//    private GetServerInfoOperation.ServerInfo mServerInfo =
//            new GetServerInfoOperation.ServerInfo();


    /// Authentication PRE-Fragment elements
    private CheckBox mOAuth2Check;
    private TextView mOAuthAuthEndpointText;
    private TextView mOAuthTokenEndpointText;
    private EditText mUsernameInput;
    private EditText mPasswordInput;
    private View mOkButton;
    private TextView mAuthStatusView;

    private int mAuthStatusText = 0, mAuthStatusIcon = 0;

    private String mAuthToken = "";
//    private AuthenticatorAsyncTask mAsyncTask;

    private boolean mIsFirstAuthAttempt;

    /// Identifier of operation in progress which result shouldn't be lost
    private long mWaitingForOpId = Long.MAX_VALUE;

    private final String BASIC_TOKEN_TYPE = AccountTypeUtils.getAuthTokenTypePass(
            MainApp.getAccountType());
    private final String OAUTH_TOKEN_TYPE = AccountTypeUtils.getAuthTokenTypeAccessToken(
            MainApp.getAccountType());
    private final String SAML_TOKEN_TYPE =
            AccountTypeUtils.getAuthTokenTypeSamlSessionCookie(MainApp.getAccountType());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log_OC.e(TAG,  "onCreate init");
        super.onCreate(savedInstanceState);

        /// protection against screen recording
        if (!BuildConfig.DEBUG) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
        } // else, let it go, or taking screenshots & testing will not be possible

        // Workaround, for fixing a problem with Android Library Suppor v7 19
        //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();

            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        mIsFirstAuthAttempt = true;

        // bind to Operations Service
        mOperationsServiceConnection = new OperationsServiceConnection();
        if (!bindService(new Intent(this, OperationsService.class),
                mOperationsServiceConnection,
                Context.BIND_AUTO_CREATE)) {
            Toast.makeText(this,
                    R.string.error_cant_bind_to_operations_service,
                    Toast.LENGTH_LONG)
                    .show();
            finish();
        }

        /// init activity state
        mAccountMgr = AccountManager.get(this);
        mNewCapturedUriFromOAuth2Redirection = null;

        /// get input values
        mAction = getIntent().getByteExtra(EXTRA_ACTION, ACTION_CREATE);
        mAccount = getIntent().getExtras().getParcelable(EXTRA_ACCOUNT);
        if (savedInstanceState == null) {
            initAuthTokenType();
        } else {
            mAuthTokenType = savedInstanceState.getString(KEY_AUTH_TOKEN_TYPE);
            mWaitingForOpId = savedInstanceState.getLong(KEY_WAITING_FOR_OP_ID);
            mIsFirstAuthAttempt = savedInstanceState.getBoolean(KEY_AUTH_IS_FIRST_ATTEMPT_TAG);
        }

        /// load user interface
        setContentView(R.layout.account_setup);

        /// initialize general UI elements
        initOverallUi();

        mOkButton = findViewById(R.id.buttonOK);
        mOkButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onOkClick();
            }
        });

        findViewById(R.id.centeredRefreshButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkOcServer();
            }
        });

        findViewById(R.id.embeddedRefreshButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                checkOcServer();
            }
        });


        /// initialize block to be moved to single Fragment to check server and get info about it
        initServerPreFragment(savedInstanceState);

        /// initialize block to be moved to single Fragment to retrieve and validate credentials
        initAuthorizationPreFragment(savedInstanceState);

        //Log_OC.e(TAG,  "onCreate end");
}
