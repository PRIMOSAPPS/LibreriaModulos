package com.modulos.libreria.radiolibreria;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;


public class StreamPlayerActivity extends AppCompatActivity {
    private final static String TAG = "StreamPlayerActivity";
    //private final static String STR_URL = "http://94.75.206.136/start/radiomonesterio/";//"http://94.75.206.136:8040/";
    private final static String STR_URL = "http://emisoras.dip-badajoz.es:8040";//http://94.75.206.136:8040";
    public final static String URL_RADIO = "urlRadio";
    private final static float MAX_VOLUMEN = 1.0f;
    private final static float MIN_VOLUMEN = 0.0f;
    private final static float DEFAULT_VOLUMEN = 0.5f;
    private final static float INCREMENTO_VOLUMEN = 0.01f;
    private String urlRadio;
    private MediaPlayer mediaPlayer;
    private float volumenActual = DEFAULT_VOLUMEN;
    private SeekBar seekBarVolumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_player);

        urlRadio = null;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            urlRadio = (String) extras.get(URL_RADIO);
        }

        seekBarVolumen = (SeekBar)findViewById(R.id.libRadioSeekBarVolumen);
        seekBarVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                volumenActual = ((float)progress)/100;
                setVolumen();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "Nuevo volumen: " + volumenActual);
            }
        });

        initMediaPlayer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stream_player, menu);
        return true;
    }

    protected void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        setVolumen();
        try {
            mediaPlayer.setDataSource(urlRadio);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    play();
                }
            });
        } catch (IOException e) {
            Log.d(TAG, "Error al preparar media player", e);
            Log.e(TAG, "Error inicializando MediaPlayer para la radio.", e);
            TextView textoAviso = (TextView)findViewById(R.id.libRadioTextoAviso);
            textoAviso.setText(R.string.lib_radio_error_conexion);
        }
    }

    public void reset(View view) {
        mediaPlayer.stop();
        mediaPlayer.release();
        initMediaPlayer();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void setVolumen() {
        Log.d(TAG, "Asignando el volumen: " + volumenActual);
        mediaPlayer.setVolume(volumenActual, volumenActual);
    }

    private void play() {

        mediaPlayer.start();
    }

    private void pause() {

        mediaPlayer.pause();
    }

    private void stop() {

        mediaPlayer.stop();
    }

    public void playPause(View view) {
        ToggleButton playPauseButton = (ToggleButton)findViewById(R.id.libRadioPlayPauseButton);
        if(!playPauseButton.isChecked()) {
            pause();
        } else {
            play();
        }
    }

    public void play(View view) {

        play();
    }

    public void pause(View view) {

        pause();
    }

    public void stop(View view) {

        stop();
    }

    private void updateProgressBar() {
        int progreso = (int)(volumenActual * 100);
        seekBarVolumen.setProgress(progreso);
    }

    public void masVolumen(View view) {

        if(volumenActual < MAX_VOLUMEN) {
            volumenActual += INCREMENTO_VOLUMEN;
            setVolumen();
            updateProgressBar();
        }
    }

    public void menosVolumen(View view) {

        if(volumenActual > MIN_VOLUMEN) {
            volumenActual -= INCREMENTO_VOLUMEN;
            setVolumen();
            updateProgressBar();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        ToggleButton playPauseButton = (ToggleButton)findViewById(R.id.libRadioPlayPauseButton);
        playPauseButton.setChecked(false);
    }
}

