package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

public interface RSFutureAllocation {
  Allocation getFutureAllocation(Context context, RenderScript rs);
}
