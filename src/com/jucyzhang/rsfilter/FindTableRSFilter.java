package com.jucyzhang.rsfilter;

import android.content.Context;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

class FindTableRSFilter extends BaseRSFilter<ScriptC_findtable> {

  @Override
  protected ScriptC_findtable onCreateScript(Context context, RenderScript rs) {
    return new ScriptC_findtable(rs);
  }

  @Override
  protected Allocation onProcessImage(Context context, RenderScript rs) {
    ScriptC_findtable table = getScript();
    Allocation inBitmap = getAllocation(context, rs, ORIGINAL);
    Allocation tableBitmap = getAllocation(context, rs, TABLE);
    Allocation outBitmap = Allocation.createTyped(rs, inBitmap.getType());
    table.bind_table(tableBitmap);
    table.invoke_filter(table, inBitmap, outBitmap);
    return outBitmap;
  }

}
