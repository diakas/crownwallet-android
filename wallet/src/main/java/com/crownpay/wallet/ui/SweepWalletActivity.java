package com.crownpay.wallet.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.crownpay.core.wallet.SendRequest;
import com.crownpay.wallet.Constants;
import com.crownpay.wallet.ExchangeHistoryProvider;
import com.crownpay.wallet.R;

import javax.annotation.Nullable;


public class SweepWalletActivity extends BaseWalletActivity implements SweepWalletFragment.Listener, MakeTransactionFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_wrapper);

        if (savedInstanceState == null) {
            Fragment fragment = SweepWalletFragment.newInstance();
            fragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onSendTransaction(SendRequest request) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.ARG_SEND_REQUEST, request);
        replaceFragment(MakeTransactionFragment.newInstance(args), R.id.container);
    }

    @Override
    public void onSignResult(@Nullable Exception error, @Nullable ExchangeHistoryProvider.ExchangeEntry exchange) {
        final Intent result = new Intent();
        result.putExtra(Constants.ARG_ERROR, error);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void onBackPressed() {
        SweepWalletFragment.clearTasks();
        super.onBackPressed();
    }
}
