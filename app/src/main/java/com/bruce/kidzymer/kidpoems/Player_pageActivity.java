package com.bruce.kidzymer.kidpoems;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;



public class Player_pageActivity extends AppCompatActivity implements Runnable, View.OnClickListener,MediaPlayer.OnCompletionListener {
    // MediaPlayer mediaPlayer;
    private Button leftpoem;
    private Button rightpoem;
    private ImageView playpoem;
    private Button resumepoem;
    private MediaPlayer soundplayer;
    private SeekBar soundseekbar;
    private Thread soundthread;
    private TextView fullpoem;
    private PhoneStateListener phoneStateListener;


    private int[] english_songs = {R.raw.babablacksheep, R.raw.bowwow, R.raw.boysandgirls, R.raw.dingdongbel, R.raw.donkeydonkey,
            R.raw.earlytobed, R.raw.fivelittlemonkeys, R.raw.godblessmummy, R.raw.jackandjill, R.raw.jhonnyjhonny,
            R.raw.jinglebells, R.raw.oldmacdonald, R.raw.rainrain, R.raw.twinkletwinkle, R.raw.twolittlehands};

    private int[] telugu_songs = {R.raw.aakesipappesi, R.raw.bavabavapanneru, R.raw.burrupitta, R.raw.chalchalgurram,
            R.raw.chandamamarave, R.raw.chemmachekka, R.raw.chittichilakamma, R.raw.chittichittimiriyalu, R.raw.chukchukrailu,
            R.raw.dagudumoothalu, R.raw.dodobasawanna, R.raw.enugammaenugu, R.raw.matataandam, R.raw.poddunnemanamulevali,
            R.raw.vanavanavallappa};

    //sar
    private int[] hindi_songs = {R.raw.aajmangalwar, R.raw.aaobhaiaao, R.raw.akkadbakkad, R.raw.baarishaayicham, R.raw.bandarmama,
            R.raw.billigayedilli, R.raw.chandamamaaaona, R.raw.chandamamagolgol, R.raw.dhobiaaya, R.raw.ekchota,
            R.raw.haathiraja, R.raw.machiljalkirani, R.raw.merigayiaati, R.raw.naninani, R.raw.prarthana};

    private String languge;
    private int position;

    private String[] poems;
    private int[] songs;

    TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_page);


        leftpoem = (Button) findViewById(R.id.left_song_button);
        rightpoem = (Button) findViewById(R.id.right_song_button);

        leftpoem.setOnClickListener(this);
        rightpoem.setOnClickListener(this);

        telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);

        playpoem = (ImageView) findViewById(R.id.play_song_button);

        fullpoem = (TextView) findViewById(R.id.Tx_poem);

        soundseekbar = (SeekBar) findViewById(R.id.seekbar);

        //based on selected song, we have create the corresponding media player object
        if (getIntent() != null) {
            languge = getIntent().getStringExtra("language");
            //getting the position here,...
            position = getIntent().getIntExtra("position", 0);
            createMediaPlayer(languge, position);
            setPoem(position);
        }

        setupListeners();

        soundthread = new Thread(this);
        soundthread.start();
    }


    private void setPoem(int position) {
        //here we will set the poem based on position
        fullpoem.setText(poems[position]);
    }

    private void changeSong(int position) {
        if (soundplayer != null) {

            if (soundplayer.isPlaying()) {
                soundplayer.stop();
            }
            soundthread.interrupt();

            soundplayer = MediaPlayer.create(this, songs[position]);
            soundplayer.setOnCompletionListener(this);

            soundplayer.start();
            playpoem.setImageResource(R.drawable.resume_button_blue);

            soundthread = null;
            soundthread = new Thread(this);
            soundthread.start();

        }
    }

    private void createMediaPlayer(String language, int position) {

        //creating corresoindg media player object
        if (language.equalsIgnoreCase("english")) {
            //english selected
            soundplayer = MediaPlayer.create(this.getBaseContext(), english_songs[position]);

            poems = getResources().getStringArray(R.array.english_poem_name);

            songs = english_songs;
        } else if (language.equalsIgnoreCase("telugu")) {

            //telugu
            soundplayer = MediaPlayer.create(this.getBaseContext(), telugu_songs[position]);

            poems = getResources().getStringArray(R.array.telugu_poem_name);

            songs = telugu_songs;
        } else if (language.equalsIgnoreCase("hindi")) {

            //hindi
            //hindi

            soundplayer = MediaPlayer.create(this.getBaseContext(), hindi_songs[position]);

            poems = getResources().getStringArray(R.array.hindi_poem_name);

            songs = hindi_songs;
        }

        if (soundplayer != null) {
            soundplayer.setOnCompletionListener(this);
//            soundplayer.setLooping(true);
            soundplayer.start();
        }

    }

    private void setupListeners() {

        playpoem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (soundplayer != null) {
                    if (soundplayer.isPlaying()) {
                        soundplayer.pause();
                        playpoem.setImageResource(R.drawable.pause);
                    } else {
                        soundplayer.start();
                        playpoem.setImageResource(R.drawable.resume_button_blue);
                    }
                }
            }
        });


        soundseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    soundplayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = soundplayer.getDuration();

        soundseekbar.setMax(soundTotal);

        while (soundplayer != null && currentPosition < soundTotal) {
            try {
                Thread.sleep(300);
                currentPosition = soundplayer.getCurrentPosition();
            } catch (InterruptedException soundException) {
                return;
            } catch (Exception otherException) {
                return;
            }
            soundseekbar.setProgress(currentPosition);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (telephonyManager != null) {
            soundplayer.pause();
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
            soundplayer.start();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (soundplayer != null) {
            if (soundplayer.isPlaying()) {
                soundplayer.stop();
            }
            soundplayer = null;
        }

        if (soundthread != null) {
            soundthread = null;
        }

        if (telephonyManager != null) {
            telephonyManager.listen(phoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        }

        //k check cheyu...
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_song_button:


                if (position == 0) {
                    Toast.makeText(getApplicationContext(), "No more Previous Songs ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (position > 0) {
                    position--;

                    //it will set the poem
                    setPoem(position);
                    changeSong(position);
                }

                break;
            case R.id.right_song_button:

                if (position < poems.length - 1) {

                    position++;

                    setPoem(position);
                    changeSong(position);

                } else {
                    Toast.makeText(getApplicationContext(), "No more Songs ", Toast.LENGTH_SHORT).show();
                    return;
                }

                break;
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {


        if (mp != null) {

            playpoem.setImageResource(R.drawable.pause);
        }

    }

}
