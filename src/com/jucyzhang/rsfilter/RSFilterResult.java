package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

/**
 * The object holding informations of filter which was just added to
 * RSFilterEngine.
 * 
 * @usage you can pass this object to another filter through
 *        {@code RSFilter.setAllocation()} to set the result of this filter
 *        as the input of another filter.
 * 
 * @usage you can get the result Bitmap through
 *        {@code RSFilterEngine.getBitmapFromResult()}
 * 
 * @author jucyzhang
 * 
 */
public abstract class RSFilterResult extends FutureAllocation {

  /*
   * this class is designed to store the result without ***creating*** any
   * Allocation. so neither context nor rs is provided.
   * 
   * @author jucyzhang
   */
  @Override
  final Allocation getFutureAllocation(Context context, RenderScript rs) {
    return getFutureAllocation();
  }

  abstract Allocation getFutureAllocation();
}
