package com.android.example.hellocompute;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

public interface FutureAllocation {
  Allocation getFutureAllocation(Context context, RenderScript rs);
}