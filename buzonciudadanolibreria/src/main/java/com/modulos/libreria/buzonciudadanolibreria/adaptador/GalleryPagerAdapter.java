package com.modulos.libreria.buzonciudadanolibreria.adaptador;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileInputStream;
import java.util.List;

/**
 * Created by h on 11/10/15.
 */
public class GalleryPagerAdapter extends PagerAdapter {
    private final static String TAG = "GalleryPagerAdapter";

    private Activity activity;
    private List<Bitmap> lstFotos;

    public GalleryPagerAdapter(Activity activity, List<Bitmap> lstFotos){
        this.activity = activity;
        this.lstFotos = lstFotos;
    }

    @Override
    public int getCount() {
        int resul = lstFotos.size();
        Log.d(TAG, "Existen " + resul + " fotos.");
        return resul;
    }

    /**
     * Create the page for the given position. The adapter is responsible
     * for adding the view to the container given here, although it only
     * must ensure this is done by the time it returns from
     * {@link #finishUpdate()}.
     *
     * @param collection The containing View in which the page will be shown.
     * @param position The page position to be instantiated.
     * @return Returns an Object representing the new page. This does not
     * need to be a View, but can be some other container of the page.
     */
    @Override
    public Object instantiateItem(View collection, int position) {

        ImageView imageView = new ImageView(activity);
        FileInputStream fis = null;
        try {

//            File file = new File(lstFotos[position]);
//            fis = new FileInputStream(file);
//        imageView.setBackgroundColor(Color.argb(50, 30, 30, 30));
//            Uri fotoUri = Uri.fromFile(file);
            /*
            Uri uriFoto = lstFotos.get(position);
            File file = new File(uriFoto.getPath());
            Log.e(TAG, "El fichero existe: " + file.exists());
            fis = new FileInputStream(file);
            */

            Bitmap bmp = lstFotos.get(position);//BitmapFactory.decodeStream(fis);
//            int width = bmp.getWidth() / 3;
//            int height = bmp.getHeight() / 3;
//            bmp = Bitmap.createScaledBitmap(bmp, width, height, false);
        //imageView.setImageURI(uriFoto);
            imageView.setImageBitmap(bmp);
        //Log.e(TAG, "Un fichero: " + uriFoto.toString());

//        Bitmap bitmapImagen = BitmapFactory.decodeFile(lstFotos[position]);
//            Bitmap bitmapImagen = BitmapFactory.decodeStream(fis);
//            bitmapImagen = Bitmap.createScaledBitmap(bitmapImagen, 30, 30, false);
//            imageView.setImageBitmap(bitmapImagen);


//        Bitmap bmp = new Bitmap();
//        BitmapDrawable bd = new BitmapDrawable();

//        imageView.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));//URI(fotoUri);
//        imageView.setBackgroundDrawable(Drawable.createFromPath(file.getPath()));
//        imageView.setBackgroundResource();

            ((ViewPager) collection).addView(imageView, 0);
        } catch (Exception e) {
            Log.e(TAG, "Error al leer la imagen.", e);
        } finally {
            if(fis != null) {
                try {
                    fis.close();
                } catch(Exception e) {
                    Log.e(TAG, "Error al cerrar la imagen.", e);
                }
            }
        }

        return imageView;
    }

    /**
     * Remove a page for the given position. The adapter is responsible
     * for removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate()}.
     *
     * @param collection The containing View from which the page will be removed.
     * @param position The page position to be removed.
     * @param view The same object that was returned by
     * {@link #instantiateItem(View, int)}.
     */
    @Override
    public void destroyItem(View collection, int position, Object view) {
        ((ViewPager) collection).removeView((ImageView) view);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==((ImageView)object);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
