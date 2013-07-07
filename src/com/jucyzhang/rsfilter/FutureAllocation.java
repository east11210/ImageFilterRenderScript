package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

abstract class FutureAllocation {
  abstract Allocation getFutureAllocation(Context context, RenderScript rs);
}
