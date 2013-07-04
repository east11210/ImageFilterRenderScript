/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.example.hellocompute;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.util.Log;
import android.widget.ImageView;

public class HelloCompute extends Activity {
  private Bitmap mBitmapIn;
  private Bitmap mBitmapOut;

  private RenderScript mRS;
  private Allocation mInAllocation;
  private Allocation mOutAllocation;
  private ScriptC_mono mScript;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    mBitmapIn = loadBitmap(R.raw.data);
    Bitmap table = loadBitmap(R.raw.valencia_table);
    Log.d("size-----", String.valueOf(mBitmapIn.getWidth()));
    // mBitmapOut = Bitmap.createBitmap(mBitmapIn.getWidth(),
    // mBitmapIn.getHeight(), mBitmapIn.getConfig());
    long time = System.currentTimeMillis();
    ImageView in = (ImageView) findViewById(R.id.displayin);
    in.setImageBitmap(mBitmapIn);
    // mBitmapOut = GuassianBlurRS.blur(this, mBitmapIn);
    // mBitmapOut = FindTableRS.blur(this, mBitmapIn, table);
    mBitmapOut = FastGuassianBlur.blur(this, mBitmapIn);
    // mBitmapOut = FastGuassianBlur.blur(this, mBitmapOut);
    // mBitmapOut = FastGuassianBlur.blur(this, mBitmapOut);
    // mBitmapOut = FastGuassianBlur.blur(this, mBitmapOut);
    // mBitmapOut = FastGuassianBlur.blur(this, mBitmapOut);
    ImageView out = (ImageView) findViewById(R.id.displayout);
    out.setImageBitmap(mBitmapOut);
    Log.d("time---", Long.toString(System.currentTimeMillis() - time));
    // createScript();
    // in.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // v.invalidate();
    // }
    // });
  }

  private void createScript() {
    mRS = RenderScript.create(this);

    long time = System.nanoTime();
    mInAllocation = Allocation.createFromBitmap(mRS, mBitmapIn,
        Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);
    mOutAllocation = Allocation.createTyped(mRS, mInAllocation.getType());

    mScript = new ScriptC_mono(mRS, getResources(), R.raw.mono);
    mScript.set_gIn(mInAllocation);
    mScript.set_gOut(mOutAllocation);
    mScript.set_gScript(mScript);
    mScript.invoke_filter();
    mOutAllocation.copyTo(mBitmapOut);
    Log.d("time-----", Long.toString((System.nanoTime() - time) / 1000000));
  }

  private Bitmap loadBitmap(int resource) {
    Resources res = getResources();
    InputStream is = null;
    try {
      final BitmapFactory.Options options = new BitmapFactory.Options();
      options.inPreferredConfig = Bitmap.Config.ARGB_8888;
      is = res.openRawResource(resource);
      return BitmapFactory.decodeStream(is);
    } finally {
      if (is != null) {
        try {
          is.close();
        } catch (IOException e) {
        }
      }
    }
  }
}
