package com.digital.functions;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import java.io.File;

import static com.digital.functions.SelectPicUtil.getAfterCropBitmap;

public class SelectPicActivity extends BaseActivity {

    private AppCompatImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pic);

        mImageView=(AppCompatImageView)findViewById(R.id.iv_pic);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select_pic:
                SelectPicUtil.selectPic(this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SelectPicUtil.PHOTO_REQUEST_GALLERY:
                if (data != null)  SelectPicUtil.openCropActivity(this,data.getData());
                break;
            case SelectPicUtil.PHOTO_REQUEST_CAMERA:
                File cameraTempFile=SelectPicUtil.getCameraTempFile();
                if (cameraTempFile != null) SelectPicUtil.openCropActivity(this,Uri.fromFile(cameraTempFile));
                break;
            case SelectPicUtil.PHOTO_REQUEST_CUT:
                mImageView.setImageBitmap(getAfterCropBitmap(this));
                break;
        }
    }


}
