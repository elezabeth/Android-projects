package com.lightcone.playingvideo;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.VideoView;

public class PlayingVideo extends Activity implements OnCompletionListener, OnPreparedListener {
    
    static private final String pathToFile = "vimeovideo.mp4";  // Video source file
    private VideoView videoPlayer;
       
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.main);
       
       // Find the root of the external storage file system.  We assume the file system is
       // mounted and writable (see the project WriteSDCard for ways to check this).
       
       File root = Environment.getExternalStorageDirectory(); 
       
       // Assign a VideoView object to the video player and set its properties.  It
       // will be started by the onPrepared(MediaPlayer vp) callback below when the
       // file is ready to play.
       
       videoPlayer = (VideoView) findViewById(R.id.videoPlayer);   
       videoPlayer.setOnPreparedListener(this);
       videoPlayer.setOnCompletionListener(this);
       videoPlayer.setKeepScreenOn(true);    
       videoPlayer.setVideoPath(root + "/" + pathToFile);
    }
 
    /** This callback will be invoked when the file is ready to play */
    @Override
    public void onPrepared(MediaPlayer vp) {
             
       // Don't start until ready to play.  The arg of seekTo(arg) is the start point in
       // milliseconds from the beginning. In this example we start playing 1/5 of
       // the way through the video if the player can do forward seeks on the video.
       
       if(videoPlayer.canSeekForward()) videoPlayer.seekTo(videoPlayer.getDuration()/5);
       videoPlayer.start();
       Log.i("IS PLAYING", Boolean.toString(videoPlayer.isPlaying()));
    }
    
    /** This callback will be invoked when the file is finished playing */
    @Override
    public void onCompletion(MediaPlayer  mp) {
       // Statements to be executed when the video finishes.
       this.finish();	
    }
    
    /**  Use screen touches to toggle the video between playing and paused. */
    @Override
    public boolean onTouchEvent (MotionEvent ev){
    	Log.i("InSIDE ","TOUCH");
       if(ev.getAction() == MotionEvent.ACTION_DOWN){
          if(videoPlayer.isPlaying()){
          	Log.i("InSIDE ","PAUSED");

                   videoPlayer.pause();
          } else {
          	Log.i("InSIDE ","START");

                   videoPlayer.start();
          }
          return true;		
       } else {
          return false;
       }
    }
 }
