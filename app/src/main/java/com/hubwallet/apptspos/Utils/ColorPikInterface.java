package com.hubwallet.apptspos.Utils;

import android.view.View;

public interface ColorPikInterface {
   void onClick(View view, int position);
   void updateColor(View view, int position,String colorId);
}
