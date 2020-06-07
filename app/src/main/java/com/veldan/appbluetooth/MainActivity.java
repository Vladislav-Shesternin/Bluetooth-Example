package com.veldan.appbluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,
        AdapterView.OnItemClickListener {

    private static final int REQ_ENABLE_BT = 1;// любое [число/символ] служит как ключ

    private Switch mSwitchBluetooth;
    private Button mButtonSearchDevice;
    private ProgressBar mProgressBarSearchDevice;
    private TextView mTextViewStateBluetooth;
    private ListView mListViewConnectedDevice;

    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwitchBluetooth = findViewById(R.id.switch___activity_main___bluetooth);
        mButtonSearchDevice = findViewById(R.id.button___activity_main___searchDevice);
        mProgressBarSearchDevice = findViewById(R.id.progressBar___activity_main___searchDevice);
        mTextViewStateBluetooth = findViewById(R.id.textView___activity_main___stateBluetooth);
        mListViewConnectedDevice = findViewById(R.id.listView___activity_main___listConnectedDevice);

        mSwitchBluetooth.setOnCheckedChangeListener(this);
        mButtonSearchDevice.setOnClickListener(this);
        mListViewConnectedDevice.setOnItemClickListener(this);

        checkBluetoothAdapterExist();
    }

    public void checkBluetoothAdapterExist() {
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Устройство не поддерживает Bluetooth", Toast.LENGTH_LONG).show();
            finish();
        } else {
            checkBluetoothAdapterState();
        }
    }

    public void checkBluetoothAdapterState() {
        if (bluetoothAdapter.isEnabled()) {
            mSwitchBluetooth.setChecked(true);
            mTextViewStateBluetooth.setText(R.string.Bluetooth_ON);
        } else {
            mTextViewStateBluetooth.setText(R.string.Bluetooth_Off);
        }
    }

    public void enableBluetooth(boolean flag) {
        if (flag) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQ_ENABLE_BT);
        } else {
            bluetoothAdapter.disable();
            mButtonSearchDevice.setVisibility(View.GONE);
            mTextViewStateBluetooth.setText(R.string.Bluetooth_Off);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ENABLE_BT) {
            if (resultCode == RESULT_OK && bluetoothAdapter.isEnabled()) {
                mButtonSearchDevice.setVisibility(View.VISIBLE);
                mTextViewStateBluetooth.setText(R.string.Bluetooth_ON);
            } else {
                mSwitchBluetooth.setChecked(false);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.equals(mSwitchBluetooth)) {
            enableBluetooth(isChecked);
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
