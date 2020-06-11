package com.huanzong.property.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 图片压缩工具
 */
public class ImgUtils {

    /**
     * 按比例缩小图片的像素以达到压缩的目的
     * @param imgPath 图片路径
     * @param sizeKB  图片大小kb
     * @throws FileNotFoundException
     */
    public static Bitmap compressImageByPixel(String imgPath, int sizeKB) {
        if(imgPath == null) return null;
        File file = new File(imgPath);
        if(!file.exists()){
            return null;
        }
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        BitmapFactory.decodeFile(imgPath, newOpts);
        newOpts.inJustDecodeBounds = false;
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;
        float maxSize = 1200;//最大的高宽像素
        int be = 1;
        if (width >= height && width > maxSize) {//缩放比,用高或者宽其中较大的一个数据进行计算
            be = (int) (newOpts.outWidth / maxSize);
            be++;
        } else if (width < height && height > maxSize) {
            be = (int) (newOpts.outHeight / maxSize);
            be++;
        }
        newOpts.inSampleSize =be;//设置采样率
        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        newOpts.inPurgeable = true;// 同时设置才会有效
        newOpts.inInputShareable = true;//当系统内存不够时候图片自动被回收
        Bitmap bitmap = BitmapFactory.decodeFile(imgPath, newOpts);
        return compressImageByQuality(bitmap,sizeKB);//压缩好比例大小后再进行质量压缩
    }
    /**
     * 返回base64位
     * @author JPH
     * @param bitmap 内存中的图片
     * @date 2014-12-5下午11:30:43
     */
    public static Bitmap compressImageByQuality(Bitmap bitmap, int sizeKB){
        if(bitmap==null) return null;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int options = 100;
                bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//质量压缩方法，把压缩后的数据存放到baos中 (100表示不压缩，0表示压缩到最小)
//                System.out.println(baos.toByteArray().length/1024+"------------kb-----------");
                while (baos.toByteArray().length >sizeKB*1024) {//循环判断如果压缩后图片是否大于指定大小,大于继续压缩
                    baos.reset();//重置baos即让下一次的写入覆盖之前的内容
                    options -= 5;//图片质量每次减少5
                    if(options<=5)options=5;//如果图片质量小于5，为保证压缩后的图片质量，图片最底压缩质量为5
                    bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//将压缩后的图片保存到baos中
                    if(options==5) break;//如果图片的质量已降到最低则，不再进行压缩
                }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        return BitmapFactory.decodeStream(isBm, null, null);
    }


    /**
     * 通过Base32将Bitmap转换成Base64字符串
     * @param bit
     * @return
     */
    public static String Bitmap2StrByBase64(Bitmap bit){
        if(bit==null)return null;
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        byte[] bytes=bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 将文件转化为base64
     * @param file
     * @return
     */
    public static String fileStrByBase64(File file){
        FileInputStream inputFile = null;
        try {
            inputFile = new FileInputStream(file);
            byte[] buffer = new byte[(int)file.length()];
            inputFile.read(buffer);
            inputFile.close();
            return Base64.encodeToString(buffer, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle , Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }
    /**
     * 将图片转换成Base64编码的字符串
     * @param path
     * @return base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }


    /**
     * 弹出对话框,可以选择从相册或者拍照获取图片
     * @param activity    上下文
     * @param cameraCode 跳转到拍照activity的请求码
     * @param albumCode 跳转到相册activity的请求码
     * @param tempPath 拍出的照片的临时文件路径
     * @return
     */
    public static void getPhoto(final Activity activity,
                                final int cameraCode, final int albumCode, final String tempPath) {
        final CharSequence[] items = { "相册", "拍照" };
        AlertDialog dlg = new AlertDialog.Builder(activity).setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 1) {
                            getPhotoFromCamera(activity,cameraCode,tempPath);
                        } else {
                            getPhotoFromAlbum(activity,albumCode);
                        }
                    }
                }).create();
        dlg.show();
    }

    /**
     * 拍照获取图片
     * @param activity 上下文
     * @param cameraCode 跳转到拍照activity的请求码
     * @param tempPath 拍出的照片的临时文件路径
     */
    public static void getPhotoFromCamera(Activity activity, int cameraCode,
                                          String tempPath) {
        File file = createImagePathUri(activity,tempPath);
        Intent getImageByCamera = new Intent(
                "android.media.action.IMAGE_CAPTURE");
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            Uri uri = FileProvider.getUriForFile(activity, AppContext.getContext().getPackageName()+".fileprovider",file);
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,uri);
            //将存储图片的uri读写权限授权给剪裁工具应用
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(getImageByCamera, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }else{
            getImageByCamera.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(tempPath)));
        }
        activity.startActivityForResult(getImageByCamera, cameraCode);
    }

    /**
     * 拍照
     * @param activity
     * @param cameraCode 请求码
     */
    public static void getPhotoFromCamera(Activity activity, int cameraCode) {
        Intent getImageByCamera = new Intent(
                "android.media.action.IMAGE_CAPTURE");
        activity.startActivityForResult(getImageByCamera, cameraCode);
    }

    /**
     * 相册获取图片
     * @param activity 上下文
     * @param albumCode 跳转到相册activity的请求码
     */
    public static void getPhotoFromAlbum(Activity activity, int albumCode) {
//            Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
//            getImage.addCategory(Intent.CATEGORY_OPENABLE);
//            getImage.setType("image/jpeg");
        Intent getImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(getImage, albumCode);
    }

    /**
     * 裁剪图片
     * @param uri 需要剪裁图片的uri
     * @param path 裁剪后的保存的图片路径
     * @param zoomCode 跳转系统裁剪activity的请求码
     * @param aspectX 宽高比
     * @param aspectY 宽高比
     */
    public static void photoZoom(Activity activity, Uri uri, String path, int zoomCode, int aspectX, int aspectY) {
        File file = createImagePathUri(activity,path);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");//从所有图片中进行选择
        intent.putExtra("crop", "true");//设置为裁切
        // aspectX aspectY 是宽高的比例
        if (aspectY > 0) {
            intent.putExtra("aspectX", aspectX);//裁切的宽比例
            intent.putExtra("aspectY", aspectY);//裁切的高比例
        }
        intent.putExtra("scale", aspectX == aspectY);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());//裁切成的图片的格式
        Uri mUri;
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            mUri = FileProvider.getUriForFile(activity, AppContext.getContext().getPackageName()+".fileprovider",file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);//将裁切的结果输出到指定的Uri
            //将存储图片的uri读写权限授权给剪裁工具应用
            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, mUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
        }else{
            mUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);//将裁切的结果输出到指定的Uri
        }
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, zoomCode);
    }

    /**
     * 将SD卡图片路径转为Bitmap对象
     * @param path 图片路径
     * @param w    转为bitmap后图片的宽
     * @param h 转为bitmap后图片的高
     * @return bitmap对象
     */
    public static Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
        if(bitmap==null){
            return null;
        }

        WeakReference<Bitmap> weak = new WeakReference<>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }


    /**
     * 比例缩放
     * 将SD卡图片路径转为Bitmap对象
     * @param path 图片路径
     * @param w    转为bitmap后图片的宽
     * @return bitmap对象
     */
    public static Bitmap convertToBitmap(String path, int w) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(path, opts);

        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        int width = opts.outWidth;
        int height = opts.outHeight;

        float scaleWidth = 0.f, scaleHeight = 0.f;
            scaleWidth = ((float) w)/width;
            scaleHeight = scaleWidth;
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;

        if(bitmap==null){
            return null;
        }
//        System.out.println(w);
//        System.out.println( (int)(bitmap.getHeight()*scaleHeight));
        return Bitmap.createScaledBitmap(bitmap, w, (int)(bitmap.getHeight()*scaleHeight), true);
    }


    /**
     * 创建一条图片地址uri,用于保存拍照后的照片
     *
     * @param context
     * @return
     */

    private static File createImagePathUri(Context context, String path) {
        File file = new File(path);
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
            if(!file.getParentFile().exists())file.getParentFile().mkdirs();
        } else {
            Toast.makeText(context, "没有SD卡", Toast.LENGTH_SHORT).show();
        }
        return file;
    }



//    /**
//     * 图片圆角规则 eg. TOP：上半部分
//     */
//    public enum HalfType {
//        LEFT, // 左上角 + 左下角
//        RIGHT, // 右上角 + 右下角
//        TOP, // 左上角 + 右上角
//        BOTTOM, // 左下角 + 右下角
//        ALL // 四角
//    }

    /**
     * 将图片的四角圆弧化
     * @param bitmap 原图
     * @param roundPixels 弧度
     * @return
     */
    public static Bitmap getRoundCornerImage(Bitmap bitmap, int roundPixels) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap roundConcerImage = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);//创建一个和原始图片一样大小的位图
        Canvas canvas = new Canvas(roundConcerImage);//创建位图画布
        Paint paint = new Paint();//创建画笔

        Rect rect = new Rect(0, 0, width, height);//创建一个和原始图片一样大小的矩形
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);// 抗锯齿

        canvas.drawRoundRect(rectF, roundPixels, roundPixels, paint);//画一个基于前面创建的矩形大小的圆角矩形
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//设置相交模式
        canvas.drawBitmap(bitmap, null, rect, paint);//把图片画到矩形去
//
//        switch (half) {
//            case LEFT:
//                return Bitmap.createBitmap(roundConcerImage, 0, 0, width - roundPixels, height);
//            case RIGHT:
//                return Bitmap.createBitmap(roundConcerImage, width - roundPixels, 0, width - roundPixels, height);
//            case TOP: // 上半部分圆角化 “- roundPixels”实际上为了保证底部没有圆角，采用截掉一部分的方式，就是截掉和弧度一样大小的长度
//                return Bitmap.createBitmap(roundConcerImage, 0, 0, width, height - roundPixels);
//            case BOTTOM:
//                return Bitmap.createBitmap(roundConcerImage, 0, height - roundPixels, width, height - roundPixels);
//            case ALL:
        return roundConcerImage;
//            default:
        //               return roundConcerImage;
        //       }
    }

//    /**
//     * 上传多张图片
//     * @param imageList
//     * @param order
//     */
//    public static void loadImages( final List<String> imageList, final int order) {
//        final List<String> images = new ArrayList<>();
//        for (int i = 0; i < imageList.size(); i++) {
//            final String key = String.valueOf(System.currentTimeMillis()) + ((int) Math.random() * 1000000) + ".png";
//            final String objectKey = "image/" + key;
//            Log.e("x_log", "key:" + key);
//            // 构造上传请求
//            PutObjectRequest put = new PutObjectRequest("pandaouba", objectKey, imageList.get(i));
//            // 异步上传时可以设置进度回调
//            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//                @Override
//                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//                }
//            });
//            MyApplication.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//                @Override
//                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                    images.add(key);
//                    Log.e("x_log", "key:" + key);
//                }
//
//                @Override
//                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                    if (clientExcepion != null) {// 请求异常
//                        clientExcepion.printStackTrace(); // 本地异常如网络异常等
//                    }
//                    if (serviceException != null) {// 服务异常
//                        Log.e("ErrorCode", serviceException.getErrorCode());
//                        Log.e("RequestId", serviceException.getRequestId());
//                        Log.e("HostId", serviceException.getHostId());
//                        Log.e("RawMessage", serviceException.getRawMessage());
//                    }
//                }
//            });
//        }
//
//    }
//
//    /**
//     * 单图上传
//     */
//    public static void loadImage(String imagePath, final Handler handler) {
//        final String key = String.valueOf(System.currentTimeMillis()) + ((int) Math.random() * 1000000) + ".png";
//
//        final String objectKey =  key;
//        Log.e("x_log", "key:" + key);
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest("hlgx", objectKey, imagePath);
//        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//
//            }
//        });
//        MyApplication.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                Message message = new Message();
//                message.what=1;
//                message.obj = key;
//                handler.sendMessage(message);
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                Message message = new Message();
//                message.what=-1;
//                message.obj = "上传失败,请检测网络连接";
//                handler.sendMessage(message);
//                if (clientExcepion != null) {// 请求异常
//                    clientExcepion.printStackTrace(); // 本地异常如网络异常等
//                }
//                if (serviceException != null) {// 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });
//    }

    /**
     * 视频上传
     */
    public static void loadVideo(final Activity activity, String imagePath, final int order) {
//        DialogUtils.showWithStatus("正在上传视频", activity);
//        final String key = String.valueOf(System.currentTimeMillis()) + ((int) Math.random() * 1000000) + ".mp4";
//        final String objectKey = "video/" + key;
//        // 构造上传请求
//        PutObjectRequest put = new PutObjectRequest("pandaouba", objectKey, imagePath);
//        // 异步上传时可以设置进度回调
//        put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
//            @Override
//            public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
//            }
//        });
//        MyApplication.getOss().asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
//            @Override
//            public void onSuccess(PutObjectRequest request, PutObjectResult result) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        callBack.dataBack(key, order);
//                        DialogUtils.disMissDialog();
//                    }
//                });
//            }
//
//            @Override
//            public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DialogUtils.disMissDialog();
//                    }
//                });
//                if (clientExcepion != null) {// 请求异常
//                    clientExcepion.printStackTrace(); // 本地异常如网络异常等
//                }
//                if (serviceException != null) {// 服务异常
//                    Log.e("ErrorCode", serviceException.getErrorCode());
//                    Log.e("RequestId", serviceException.getRequestId());
//                    Log.e("HostId", serviceException.getHostId());
//                    Log.e("RawMessage", serviceException.getRawMessage());
//                }
//            }
//        });
    }
}
