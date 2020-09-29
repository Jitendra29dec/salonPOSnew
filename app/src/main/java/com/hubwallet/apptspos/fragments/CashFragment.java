package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hubwallet.apptspos.R;
import com.starmicronics.stario.PortInfo;
import com.starmicronics.stario.StarIOPort;
import com.starmicronics.stario.StarIOPortException;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CashFragment extends Fragment {
    String value = "";
    boolean isFirstTime = true;
    ImageButton imageButton;
    private EditText editText;

    public static byte[] createCommandsImage() {
        ICommandBuilder builder = StarIoExt.createCommandBuilder(StarIoExt.Emulation.StarPRNT);
        builder.beginDocument();
        builder.appendPeripheral(ICommandBuilder.PeripheralChannel.No1);
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.append("Book N PAY".getBytes());
        builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed);
        builder.endDocument();
        return builder.getCommands();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.cash_view_fragment, null, false);
        editText = v.findViewById(R.id.priceEditTextCashFragment);
        imageButton = v.findViewById(R.id.imageButtonCashFragment);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value = "0.00";
                editText.setText("$" + value);
                imageButton.setVisibility(View.GONE);
                isFirstTime = true;
            }
        });
        v.findViewById(R.id.collectButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick: ", "Cliccked");
                try {
                    List<PortInfo> portList = StarIOPort.searchPrinter("BT:");
                    for (PortInfo portInfo : portList) {
                        Log.e("onClick: ", portInfo.getPortName());
                        StarIOPort port = StarIOPort.getPort(portInfo.getPortName(), "", 10000, getContext());
                        port.writePort(createCommandsImage(), 0, createCommandsImage().length);
                    }
                } catch (StarIOPortException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    public void UpdatePrice(View view) {
        String dollar = "$";
        TextView edit = (TextView) view;
        if (view instanceof Button) {
            value = "";
            editText.setText(((Button) view).getText().toString());
        } else {
            if (isFirstTime) {
                imageButton.setVisibility(View.VISIBLE);
                isFirstTime = false;
                value = edit.getText().toString();
                editText.setText(dollar + value);
            } else {
                if (value.contains(".") && edit.getText().toString().equalsIgnoreCase(".")) {
                } else {
                    value += edit.getText().toString();

                }
                editText.setText(dollar + value);

            }
        }

    }
}
