package com.veldan.appbluetooth;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,
        AdapterView.OnItemClickListener {

    private static final int REQ_ENABLE_BT = 1;// любое [число/символ] служит как ключ
    private static final int LINKED = 21;// любое [число/символ] служит как ключ
    private static final int NOT_LINKED = 22;// любое [число/символ] служит как ключ

    private Switch mSwitchBluetooth;
    private Button mButtonSearchDevice;
    private ProgressBar mProgressBarSearchDevice;
    private TextView mTextViewStateBluetooth;
    private ListView mListViewConnectedDevice;

    private BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothListAdapter listAdapter;
    private List<BluetoothDevice> bluetoothDevices;

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
            mListViewConnectedDevice.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ENABLE_BT) {
            if (resultCode == RESULT_OK && bluetoothAdapter.isEnabled()) {
                mButtonSearchDevice.setVisibility(View.VISIBLE);
                mTextViewStateBluetooth.setVisibility(View.GONE);
                mListViewConnectedDevice.setVisibility(View.VISIBLE);
                setListAdapter(LINKED);
            } else {
                mSwitchBluetooth.setChecked(false);
            }
        }
    }

    private void setListAdapter(int type) {
        switch (type) {
            case LINKED:
                bluetoothDevices = getDevices();
                listAdapter = new BluetoothListAdapter(this, bluetoothDevices, R.drawable.ic_bluetooth_not_linced);
                break;
            case NOT_LINKED:
                listAdapter = new BluetoothListAdapter(this, bluetoothDevices, R.drawable.ic_bluetooth_linked);
                break;
        }
        mListViewConnectedDevice.setAdapter(listAdapter);
    }

    private ArrayList<BluetoothDevice> getDevices() {
        Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();
        ArrayList<BluetoothDevice> devices = new ArrayList<>();
        if (!(deviceSet.isEmpty())) {
            for (BluetoothDevice device : deviceSet) {
                devices.add(device);
            }
        }
        return devices;
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
