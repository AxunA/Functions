package com.digital.functions;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;

import com.digital.common_util.ToastUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by fangzhengyou on 16/11/1.
 * 选择照片
 */

public class SelectPicUtil {

    public static final int PHOTO_REQUEST_CUT = 4;
    public static final int PHOTO_REQUEST_GALLERY = 2;
    public static final int PHOTO_REQUEST_CAMERA = 3;

    private static Uri mCutTempFile; //剪切图片后输出的路径
    private static File mCameraTempFile; //拍照后的临时文件

    public static void selectPic(final Activity activity) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            ToastUtil.showText(activity, activity.getString(R.string.no_card), ToastUtil.ONE_SECOND);
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(R.array.img_select, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        openCamera(activity);
                        break;
                    case 1:
                        openGallery(activity);
                        break;
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /*
   * 从相册获取
   */
    public static void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        activity.startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 从相机获取
     */
    public static void openCamera(Activity activity) {
        mCameraTempFile = new File(Environment.getExternalStorageDirectory(), "photo_file");
        Uri uri = Uri.fromFile(mCameraTempFile);

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    /*
    * 剪切图片
    */
    public static void openCropActivity(Activity activity,Uri uri) {
        mCutTempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mCutTempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        // 裁剪框的比例，1：1
        //intent.putExtra("aspectX", 1);
        //intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        //intent.putExtra("outputX", 250);
        //intent.putExtra("outputY", 250);

        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public static Bitmap getAfterCropBitmap(Activity activity){
        File cameraTempFile2=SelectPicUtil.getCameraTempFile();
        if (cameraTempFile2 != null) {
            cameraTempFile2.delete();
        }

        Uri mCutTempFile=SelectPicUtil.getCutTempFile();
        Bitmap bitmap;
        try {
            bitmap = BitmapFactory.decodeStream(activity.getContentResolver().openInputStream(mCutTempFile));
        } catch (FileNotFoundException e) {
            return null;
        }
        return bitmap;
    }

    public static File getCameraTempFile() {
        return mCameraTempFile;
    }

    public static Uri getCutTempFile() {
        return mCutTempFile;
    }

}
