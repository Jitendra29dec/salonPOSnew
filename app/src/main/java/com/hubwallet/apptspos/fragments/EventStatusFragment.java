package com.hubwallet.apptspos.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hubwallet.apptspos.R;
import com.hubwallet.apptspos.Utils.PrefManager;
import com.hubwallet.apptspos.calander.change_Status.ChangeStatusViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class EventStatusFragment extends Fragment implements View.OnClickListener {

    private View rootView;
    private ChangeStatusViewModel changeStatusViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.evetent_status_list_fragment, container, false);
        changeStatusViewModel = new ViewModelProvider(this).get(ChangeStatusViewModel.class);

        return rootView;
    }
private void getOptions(){
        changeStatusViewModel.getOptionsStatus(new PrefManager(requireContext()).getVendorId());

}

    @Override
    public void onClick(View v) {
        int id = v.getId();
//        if (id == R.id.acceptLinearLayout) {
//            CalenderFragment.FCommunicator.message("accept");
//        }
//        if (id == R.id.denyLinearLayout) {
//            CalenderFragment.FCommunicator.message("deny");
//        }
//        if (id == R.id.showLinearLayout) {
//            CalenderFragment.FCommunicator.message("shows");
//        }
//        if (id == R.id.serviceInProgressRelativeView) {
//            CalenderFragment.FCommunicator.message("service_in_process");
//        }
//        if (id == R.id.noShowLinearLayout) {
//            CalenderFragment.FCommunicator.message("no_show");
//        }
//        if (id == R.id.confirmRelativeView) {
//            CalenderFragment.FCommunicator.message("confirm");
//        }
//        if (id == R.id.cancelRelativeView) {
//            CalenderFragment.FCommunicator.message("cancel");
//        }
//        if (id == R.id.deleteRelativeView) {
//            CalenderFragment.FCommunicator.message("delete");
//        }
//        if (id == R.id.serviceInProgressRelativeView) {
//            //    CalenderFragment.FCommunicator.message("checkin");
//        }
    }
}
