package com.example.stackfarm.myapplication.trail;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.stackfarm.myapplication.R;
import com.example.stackfarm.myapplication.adapter.PluginViewAdapter;
import com.example.stackfarm.myapplication.bean.PluginBean;
import com.example.stackfarm.myapplication.utils.FileUtilcll;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrailHairstyle extends AppCompatActivity {

    private String imgName;
    private Uri imageUri;
    private Uri imgUriOri;
    private String imgPathOri;
    private File file;

    final static int CAMERA_RESULT_CODE=1;
    final static int CROP_RESULT_CODE=2;
    final static int ALBUM_RESULT_CODE=3;
    final static int REQUEST_PERMISSIONS=4;

    RecyclerView recyclerView;
    PluginViewAdapter adapter;
    List<PluginBean> list=new ArrayList<>();

    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail_hairstyle);

        initPermission();

        imageView=(ImageView) findViewById(R.id.image_big);

        Intent intent=getIntent();
        judge(intent.getStringExtra("TRAIL"));

        initPlugins();
        recyclerView=findViewById(R.id.pluginView);
        adapter=new PluginViewAdapter(list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void judge(String s){
        switch (s){
            case "camera":
               openSysCamera();
               break;
            case "album":
                openSysAlbum();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSIONS:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //成功
                    Toast.makeText(this, "用户授权相机权限", Toast.LENGTH_SHORT).show();
                } else {
                    // 勾选了不再询问
                    Toast.makeText(this, "用户拒绝相机权限", Toast.LENGTH_SHORT).show();
                    /**
                     * 跳转到 APP 详情的权限设置页
                     *
                     * 可根据自己的需求定制对话框，点击某个按钮在执行下面的代码
                     */
                    Intent intent = getAppDetailSettingIntent(TrailHairstyle.this);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode){
            case CAMERA_RESULT_CODE:
                File tempFile = new File(Environment.getExternalStorageDirectory(), "output_image.jpg");
                cropPic(getImageContentUri(file));
                break;
            case ALBUM_RESULT_CODE:
                // 相册
                cropPic(data.getData());
                break;
            case CROP_RESULT_CODE:
                // 裁剪时,这样设置 cropIntent.putExtra("return-data", true); 处理方案如下
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        imageView.setImageBitmap(bitmap);
                        // 把裁剪后的图片保存至本地 返回路径
                        String urlpath = FileUtilcll.saveFile(this, "crop.jpg", bitmap);
                        Log.e("TAG","裁剪图片地址->" + urlpath);
                    }
                }

                // 裁剪时,这样设置 cropIntent.putExtra("return-data", false); 处理方案如下
//                try {
//                    ivHead.setImageBitmap(BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(imageUri)));
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
                break;
        }
    }

    /**
     * 裁剪图片
     *
     * @param data
     */
    private void cropPic(Uri data) {
        if (data == null) {
            return;
        }
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        cropIntent.setDataAndType(data, "image/*");

        // 开启裁剪：打开的Intent所显示的View可裁剪
        cropIntent.putExtra("crop", "true");
        // 裁剪宽高比
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        // 裁剪输出大小
        cropIntent.putExtra("outputX", 320);
        cropIntent.putExtra("outputY", 320);
        cropIntent.putExtra("scale", true);
        /**
         * return-data
         * 这个属性决定我们在 onActivityResult 中接收到的是什么数据，
         * 如果设置为true 那么data将会返回一个bitmap
         * 如果设置为false，则会将图片保存到本地并将对应的uri返回，当然这个uri得有我们自己设定。
         * 系统裁剪完成后将会将裁剪完成的图片保存在我们所这设定这个uri地址上。我们只需要在裁剪完成后直接调用该uri来设置图片，就可以了。
         */
        cropIntent.putExtra("return-data", true);
        // 当 return-data 为 false 的时候需要设置这句
//        cropIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        // 图片输出格式
//        cropIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // 头像识别 会启动系统的拍照时人脸识别
//        cropIntent.putExtra("noFaceDetection", true);
        startActivityForResult(cropIntent, CROP_RESULT_CODE);
    }

    /**
     * 7.0以上获取裁剪 Uri
     *
     * @param imageFile
     * @return
     */
    private Uri getImageContentUri(File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * 初始化相机相关权限
     * 适配6.0+手机的运行时权限
     */
    private void initPermission() {
        String[] permissions = new String[]{Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE};
        //检查权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // 之前拒绝了权限，但没有点击 不再询问 这个时候让它继续请求权限
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {
                Toast.makeText(this, "用户曾拒绝打开相机权限", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            } else {
                //注册相机权限
                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
            }
        }
    }

    /**
     * 获取 APP 详情页面intent
     *
     * @return
     */
    public static Intent getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        return localIntent;
    }


    private void openSysCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
//                new File(Environment.getExternalStorageDirectory(), imgName)));
//        File file = new File(Environment.getExternalStorageDirectory(), imgName);
        try {
            file = createOriImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (file != null) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                imgUriOri = Uri.fromFile(file);
            } else {
                imgUriOri = FileProvider.getUriForFile(TrailHairstyle.this, getPackageName() + ".fileprovider", file);
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUriOri);
            startActivityForResult(cameraIntent, CAMERA_RESULT_CODE);
        }
    }

    /**
     * 创建原图像保存的文件
     *
     * @return
     * @throws IOException
     */
    private File createOriImageFile() throws IOException {
        String imgNameOri = "HomePic_" + new SimpleDateFormat(
                "yyyyMMdd_HHmmss").format(new Date());
        File pictureDirOri = new File(getExternalFilesDir(
                Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/OriPicture");
        if (!pictureDirOri.exists()) {
            pictureDirOri.mkdirs();
        }
        File image = File.createTempFile(
                imgNameOri,         /* prefix */
                ".jpg",             /* suffix */
                pictureDirOri       /* directory */
        );
        imgPathOri = image.getAbsolutePath();
        return image;
    }

    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, ALBUM_RESULT_CODE);
    }

    private void initPlugins(){
        PluginBean one=new PluginBean("One",R.mipmap.one);
        PluginBean two=new PluginBean("Two",R.mipmap.two);
        PluginBean three=new PluginBean("Three",R.mipmap.three);

        list.add(one);
        list.add(two);
        list.add(three);
    }
}
