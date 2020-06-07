package com.veldan.appbluetooth;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BluetoothListAdapter extends BaseAdapter {
    public static final int RESOURCE_LAYOUT = R.layout.list_item;

    private List<BluetoothDevice> bluetoothDevices = new ArrayList<>();
    private LayoutInflater inflater;
    private int iconType;

    public BluetoothListAdapter(Context context, List<BluetoothDevice> bluetoothDevices, int iconType) {
        this.bluetoothDevices = bluetoothDevices;
        this.iconType = iconType;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bluetoothDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(RESOURCE_LAYOUT, parent, false);
        BluetoothDevice device = bluetoothDevices.get(position);
        if (device != null) {
            bind(view, device);
        }
        return view;
    }

    private void bind(View view, BluetoothDevice device) {
        TextView tvName, tvAddress;
        ImageView imgBluetoothLink;

        tvName = view.findViewById(R.id.textView___list_item___nameDevice);
        tvAddress = view.findViewById(R.id.textView___list_item___addressDevice);
        imgBluetoothLink = view.findViewById(R.id.imageView___list_item___bluetoothLink);

        tvName.setText(device.getName());
        tvAddress.setText(device.getAddress());
        imgBluetoothLink.setImageResource(iconType);
    }
}
