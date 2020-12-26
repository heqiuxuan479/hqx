package com.example.musicplayer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton play,pause,next,pre;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private TextView bottomSong,bottomSinger,gang;
    ListView mylist;
    private List<Song> list;
    private int currentPosition;
    private SeekBar seekBar;
    private MyAdapter myAdapter;

    //使用Handler类，进行SeekBar的更新(进度条)
    private static final int UPDATE_SEEKBAR = 0;
    private Handler  handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_SEEKBAR:
                    // 将SeekBar位置设置到当前播放位置
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Apply.verifyStoragePermissions(this);//动态获取权限
        setContentView(R.layout.activity_main);
        initTitle();//沉浸式状态栏方法
        initViews();//初始化控件

        //给ListView设置适配器
        mylist = (ListView) findViewById(R.id.mylist);
        list = new ArrayList<>();
        list = Utils.getmusic(this);//获取音乐
        myAdapter = new MyAdapter(this, list);
        mylist.setAdapter(myAdapter);
        //ListView初始效果，没有被选中时的状态
        currentPosition=-1;
        myAdapter.changeSelected(currentPosition);
       //ListView点击事件
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentPosition=position;
                musicPlay(currentPosition);//播放音乐
                myAdapter.changeSelected(currentPosition);//设置字体变色
                play.setVisibility(View.INVISIBLE);//播放按钮消失
                pause.setVisibility(View.VISIBLE);//暂停按钮出现
                String song=list.get(currentPosition).song;//获取音乐名
                String singer=list.get(currentPosition).singer;//获取歌手名
                bottomSong.setText(song);//底栏显示音乐名
                bottomSinger.setText(singer);//底栏显示歌手名
                gang.setVisibility(View.VISIBLE);//横杠

            }
        });
        playClick();

    };

     private void initTitle(){
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN//全屏显示
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    |View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;//因为背景为浅色所以将状态栏字体设置为黑色
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
    /**
     * 初始化控件
     */
    private void initViews() {
        pause=(ImageButton)findViewById(R.id.pause);
        play=(ImageButton)findViewById(R.id.play);
        next=(ImageButton)findViewById(R.id.next);
        pre=(ImageButton)findViewById(R.id.pre);
        seekBar=(SeekBar)findViewById(R.id.seekbar);
        seekBar.getThumb().setColorFilter(Color.parseColor("#22D59D"), PorterDuff.Mode.SRC_ATOP);
        bottomSong=(TextView)findViewById(R.id.bottom_song);
        bottomSinger=(TextView)findViewById(R.id.bottom_singer);
        gang=(TextView)findViewById(R.id.gang);
    }
    //点击方法
    private void playClick(){
        //暂停按钮
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mediaPlayer.pause();//暂停音乐
                    pause.setVisibility(View.INVISIBLE);
                    play.setVisibility(View.VISIBLE);
            }
        });
        //播放按钮
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.VISIBLE);
                mediaPlayer.start();//播放音乐
            }
        });
        /**
         * 下一曲
         */
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition++;
                if (currentPosition > list.size() - 1) {
                    currentPosition = 0;
                }
                musicPlay(currentPosition);
                myAdapter.changeSelected(currentPosition);//ListView颜色变化
                //底栏设置正在播放的歌曲信息
                String song=list.get(currentPosition).song;
                String singer=list.get(currentPosition).singer;
                bottomSong.setText(song);
                bottomSinger.setText(singer);
                gang.setVisibility(View.VISIBLE);
            }
        });
        /**
         * 上一曲
         */
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentPosition--;
                if (currentPosition < 0) {
                    currentPosition = list.size() - 1;
                }
                musicPlay(currentPosition);
                myAdapter.changeSelected(currentPosition);//ListView颜色变化
                //底栏设置正在播放的歌曲信息
                String song=list.get(currentPosition).song;
                String singer=list.get(currentPosition).singer;
                bottomSong.setText(song);
                bottomSinger.setText(singer);
                gang.setVisibility(View.VISIBLE);
            }
        });

        //给mediaPlayer添加一个监听事件，实现当前音乐播放完成后自动播放下一首的功能
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                currentPosition++;
                if (currentPosition > list.size() - 1) {
                    currentPosition = 0;
                }
                musicPlay(currentPosition);
                myAdapter.changeSelected(currentPosition);
                //底栏设置正在播放的歌曲信息
                String song=list.get(currentPosition).song;
                String singer=list.get(currentPosition).singer;
                bottomSong.setText(song);
                bottomSinger.setText(singer);
                gang.setVisibility(View.VISIBLE);
            }
        });

        //给SeekBar添加监听事件，实现拖动进度条改变音乐进度
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
              if(fromUser==true){
                  mediaPlayer.seekTo(progress);
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
    //适配器
    class MyAdapter extends BaseAdapter {
        Context context;
        List<Song> list;
        private int mSelect;   //选中项
        public MyAdapter(MainActivity mainActivity, List<Song> list) {
            this.context = mainActivity;
            this.list = list;
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public Object getItem(int i) {
            return list.get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Myholder myholder;
            if (view == null) {
                myholder = new Myholder();
                view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.text, null);
               // myholder.t_position = view.findViewById(R.id.t_postion);
                myholder.t_song = view.findViewById(R.id.t_song);
                myholder.t_singer = view.findViewById(R.id.t_singer);
                myholder.t_blank=view.findViewById(R.id.t_blank);
                myholder.t_albumn=view.findViewById(R.id.t_albumn);
                myholder.t_point=view.findViewById(R.id.t_point);
                myholder.t_duration = view.findViewById(R.id.t_duration);
                view.setTag(myholder);
            } else {
                myholder = (Myholder) view.getTag();
            }
            myholder.t_song.setText(list.get(i).song.toString());
            myholder.t_singer.setText(list.get(i).singer.toString());
            String time = Utils.formatTime(list.get(i).duration);
            myholder.t_albumn.setText(list.get(i).album.toString());
            myholder.t_duration.setText(time);
            //myholder.t_position.setText(i + 1 + "");
            //设置相关数据
            if (mSelect==-1){
                myholder.t_song.setEnabled(true);
                myholder.t_singer.setEnabled(true);
                myholder.t_blank.setVisibility(View.INVISIBLE);
                //选中项背景
                myholder.t_song.setTextColor(getResources().getColor(R.color.tilte_color));
                myholder.t_singer.setTextColor(getResources().getColor(R.color.tilte_color));
                myholder.t_albumn.setTextColor(getResources().getColor(R.color.tilte_color));
                myholder.t_point.setTextColor(getResources().getColor(R.color.tilte_color));
            }
            if (mSelect == i) {
                myholder.t_song.setEnabled(true);
                myholder.t_singer.setEnabled(true);
                myholder.t_blank.setVisibility(View.VISIBLE);
                //选中项背景
                myholder.t_song.setTextColor(getResources().getColor(R.color.theme));
                myholder.t_singer.setTextColor(getResources().getColor(R.color.theme));
                myholder.t_albumn.setTextColor(getResources().getColor(R.color.theme));
                myholder.t_point.setTextColor(getResources().getColor(R.color.theme));
                myholder.t_duration.setTextColor(getResources().getColor(R.color.theme));
            } else {
                myholder.t_song.setEnabled(false);
                myholder.t_singer.setEnabled(false);
                myholder.t_blank.setVisibility(View.INVISIBLE);
                //其他项背景
                myholder.t_song.setTextColor(getResources().getColor(R.color.tilte_color));
                myholder.t_singer.setTextColor(getResources().getColor(R.color.song));
                myholder.t_albumn.setTextColor(getResources().getColor(R.color.song));
                myholder.t_point.setTextColor(getResources().getColor(R.color.song));
                myholder.t_duration.setTextColor(getResources().getColor(R.color.song));
            }
            return view;
        }
        class Myholder {
            ImageView t_blank;
            TextView t_position, t_song, t_singer, t_duration,t_albumn,t_point;
        }
        //刷新方法
        public void changeSelected(int positon){
            if (positon != mSelect) {
                mSelect = positon;
                notifyDataSetChanged();
            }
        }
    }
   private void musicPlay(int currentPosition) {
           seekBar.setMax(list.get(currentPosition).getDuration());
           try {
               // 重置音频文件，防止多次点击会报错
               mediaPlayer.reset();
               //调用方法传进播放地址
               mediaPlayer.setDataSource(list.get(currentPosition).getPath());
               //异步准备资源，防止卡顿
               mediaPlayer.prepareAsync();
               //调用音频的监听方法，音频准备完毕后响应该方法进行音乐播放
               mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                   @Override
                   public void onPrepared(MediaPlayer mediaPlayer) {
                       mediaPlayer.start();//开始播放
                       Thread thread = new Thread(new SeekBarThread());//更新SeekBar的线程
                       thread.start();
                   }
               });
           } catch (Exception e) {
           e.printStackTrace();
       }
   }

   //实现Runnable接口进行SeekBar状态的刷新
   class SeekBarThread implements Runnable {
        @Override
        public void run() {
            while (mediaPlayer.isPlaying()) {
                try {
                    // 每500毫秒更新一次位置
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message=new Message();
                message.what=UPDATE_SEEKBAR;
                handler.sendMessage(message);
            }
        }
    }
}
