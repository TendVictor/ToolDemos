package com.example.chen.tooldemos.tools2.tools2.music.lyrics;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 16/5/3.
 */
public class LyricView extends TextView {

    private float width;        //歌词视图宽度
    private float height;       //歌词视图高度
    private Paint currentPaint; //当前画笔对象
    private Paint notCurrentPaint;  //非当前画笔对象
    private float textHeight = 60;  //文本高度
    private float textSize = 30;        //文本大小
    private int index = 0;      //list集合下标

    private List<LrcContent> mLrcList = new ArrayList<LrcContent>();

    public void setmLrcList(List<LrcContent> mLrcList) {
        this.mLrcList = mLrcList;
    }

    public LyricView(Context context) {
        super(context);
        init();
    }

    public LyricView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LyricView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //初始化
    public void init(){
        setFocusable(true);
        currentPaint = new Paint();
        currentPaint.setAntiAlias(true);
        currentPaint.setTextAlign(Paint.Align.CENTER);

        notCurrentPaint = new Paint();
        notCurrentPaint.setAntiAlias(true);
        notCurrentPaint.setTextAlign(Paint.Align.CENTER);

    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        if(canvas == null)
            return;

        currentPaint.setColor(Color.parseColor("#1040f8"));
        notCurrentPaint.setColor(Color.parseColor("#0d0f13"));

        currentPaint.setTextSize(40);
        currentPaint.setTypeface(Typeface.SERIF);

        notCurrentPaint.setTextSize(textSize);
        notCurrentPaint.setTypeface(Typeface.DEFAULT);


        try{
            setText("");
            //画当前歌词
            StringBuffer nowlyric = new StringBuffer(mLrcList.get(index).getLrcStr());

            canvas.drawText(nowlyric.toString() , width/2 , height/2, currentPaint);

            float tempY = height/2;

            //画出当前歌词之前的歌词
            for (int i = index -1; i >= 0; i--){
                tempY = tempY - textHeight;
                if(tempY > textHeight){
                    canvas.drawText(mLrcList.get(i).getLrcStr() , width/2 , tempY , notCurrentPaint);
                }
            }

            tempY = height/2;

            //画出当前歌词之后的歌词
            for(int i = index+1; i <= mLrcList.size() ; i++){
                tempY = tempY + textHeight;
                if(tempY < height - textHeight)
                    canvas.drawText(mLrcList.get(i).getLrcStr() , width/2 , tempY , notCurrentPaint);
            }

        }catch(Exception e){
            setText("...木有歌词文件，赶紧去下载...");
            setGravity(Gravity.CENTER_HORIZONTAL);
        }

    }

    //居中
    public void setLayout(){
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.width = w;
        this.height = h;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
