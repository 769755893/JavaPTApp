package myapp.example.myfinalproject;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import myapp.example.myfinalproject.myapp.game.project.ImagePiece;
import myapp.example.myfinalproject.myapp.game.project.ImageSplit;
public class MyGamesLayout extends RelativeLayout implements View.OnClickListener {

    private ImageView MyEmptyImageView;
    private int mColumn = 3;
    private int mPadding;//容器内边距

    //每张小图之间的横纵距离。
    private int mMargin = 3;

    private ImageView[] mGamePintuItems;
    private int mIteamWidth;//每张图片宽高一致。

    private Bitmap mBitmap, blackBitmap;
    private List<ImagePiece> mIteamBitmaps;//所有切后的图
    private ImagePiece BlackImagePiece;//黑色小块图。

    private boolean once;
    private int mWidth;//游戏面板的宽度和高度。
    //游戏接口
    private int Level = 1;
    private boolean isGameSuccess=false;
    private boolean isGameOver=false;


    interface GamePintuListener {
        void nextLevel(int Level);

        void timechanged(int currentTime);

        void gameover();
    }

    public GamePintuListener mListener;

    //设置接口回调函数
    public void setmListener(GamePintuListener mListener) {
        this.mListener = mListener;
    }

    public static final int TIME_CHANGED = 0x100;
    private static final int NEXT_LEVEL = 0x123;
    private int mTime;//游戏时间。
    //时间限制。
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case TIME_CHANGED:
                    if (isGameSuccess || isGameOver||isPause)
                        return;
                    if (mListener != null) {
                        mListener.timechanged(mTime);//传入时间。
                        if (mTime == 0) {
                            isGameOver = true;//游戏失败。
                            mListener.gameover();
                            return;
                        }
                    }
                    mTime--;
                    mHandler.sendEmptyMessageDelayed(TIME_CHANGED, 1000);//延迟一秒发送形成计时循环循环
                    break;
                case NEXT_LEVEL:
                    Level++;
                    if (mListener != null) {
                        mListener.nextLevel(Level);
                    } else {
                        nextLevel();
                    }
                    break;
            }
        }
        ;
    };

    public void restart() {
        isGameOver = false;
        mColumn--;//重新开始当前失败关卡。
        nextLevel();
    }

    public boolean isPause=true;

    public void pause() {
        isPause = true;
        mHandler.removeMessages(TIME_CHANGED);
    }

    public void resume() {
        if (isPause) {
            isPause = false;
            mHandler.sendEmptyMessage(TIME_CHANGED);
        }
    }

    public void nextLevel() {
        this.removeAllViews();
        mAnimLayout = null;
        mColumn++;
        isGameSuccess = false;
        checkTimeEnable();
        initBitmap();
        initItem();
    }

    private boolean isTimeEnabled = false;//默认无时间限制。
    //可设置是否有时间限制。
    public void setTimeEnabled(boolean timeEnabled) {
        isTimeEnabled = timeEnabled;
    }
    public MyGamesLayout(Context context) {
        this(context, null);
    }
    public MyGamesLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyGamesLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init() {
        mMargin = 3;
        mPadding = min(getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom());
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredHeight(), getMeasuredWidth());
        if (!once) {
            initBitmap();
            //设置ImageView(Iteam)的宽高等属性.
            initItem();//内容初始化
//            //判断是否开启时间限制。
            checkTimeEnable();
            once = true;
        }
        setMeasuredDimension(mWidth, mWidth);//容器初始化。
    }
    private void checkTimeEnable() {
        if (isTimeEnabled) {
            //根据当前游戏等级设置时间。
            countTimeBaseLevel();
            mHandler.sendEmptyMessage(TIME_CHANGED);//告诉主界面游戏时间发生了变化！
        }
    }
    private void countTimeBaseLevel() {
        mTime = (int) Math.pow(2, Level) * 60;
    }
    //设置每个小图片的宽高等属性。
    private void initItem(){
        mIteamWidth = (mWidth - mPadding * 2 - mMargin * (mColumn - 1)) / mColumn;//每个小图片的宽高度。
        mGamePintuItems = new ImageView[mColumn * mColumn];//创建所有小图片集合。
        //设置每个item的属性和规则。
        for (int i = 0; i < mGamePintuItems.length; i++){
            ImageView item = new ImageView(getContext());
            if (i == mGamePintuItems.length - 1) {
                item.setImageBitmap(BlackImagePiece.getBitmap());
            }
            else
                item.setImageBitmap(mIteamBitmaps.get(i).getBitmap());//得到集合中的某一图片。
            item.setOnClickListener(this);
            mGamePintuItems[i] = item;
            item.setId(i + 1);
            //在item的tag中储存了index;index为图片的正确顺序，用来判断是否拼图成功。
            if (i == mGamePintuItems.length - 1)
                item.setTag(i + "_" + BlackImagePiece.getIndex());
            else if (i != mGamePintuItems.length - 1)
                item.setTag(i + "_" + mIteamBitmaps.get(i).getIndex());
            RelativeLayout.LayoutParams LP = new RelativeLayout.LayoutParams(mIteamWidth, mIteamWidth);
            //设置每个item横向间隙，最后一列不设置。
            if ((i + 1) % mColumn != 0) {
                LP.rightMargin = mMargin;
            }
            //设置每个item的right of,第一列不设置。
            if (i % mColumn != 0) {
                LP.addRule(RelativeLayout.RIGHT_OF, mGamePintuItems[i - 1].getId());
            }
            //设置topMargin 第一行不设置。
            if ((i + 1) > mColumn) {
                LP.topMargin = mMargin;
                LP.addRule(RelativeLayout.BELOW, mGamePintuItems[i - mColumn].getId());
            }
            addView(item, LP);//每一个item加入Relativelayout进行显示。
        }
    }
    private void initBitmap() {
        if(mColumn==3)
        mBitmap = BitmapFactory.decodeResource
                (getResources(), R.drawable.xx1);//传入基本图片
        else if(mColumn==4)
            mBitmap=BitmapFactory.decodeResource
                    (getResources(),R.drawable.xx2);
        mIteamBitmaps = ImageSplit.splitImage(mBitmap, mColumn);
        Collections.sort(mIteamBitmaps, new Comparator<ImagePiece>() {
            @Override
            public int compare(ImagePiece a, ImagePiece b) {
                return Math.random() > 0.5 ? 1 : -1;//使用sort完成乱序。
            }
        });
        //设置乱序后的坐标。
        if(mColumn==3)
            UPDataXYColmn3();
        else if(mColumn==4){
            UPDataXYColmn4();
        }
        //右下角小黑块初始化。
        blackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        int width = blackBitmap.getWidth();
        int height =blackBitmap.getHeight();
        int pieceWidth = Math.min(width, height) / mColumn;
        int x = (mColumn - 1) * pieceWidth;
        int y = (mColumn - 1) * pieceWidth;
        BlackImagePiece = new ImagePiece();
        BlackImagePiece.setBitmap(Bitmap.createBitmap(blackBitmap, x, y, pieceWidth, pieceWidth));
        BlackImagePiece.setIndex(mColumn * mColumn - 1);
        BlackImagePiece.setCurrentX(mColumn);
        BlackImagePiece.setCurrentY(mColumn);//坐标3,3
    }
    private void UPDataXYColmn3() {
        int pox=0;
        int poy=0;
        for(int i=0;i<mColumn*mColumn-1;i++){
            //0 1 2
            //3 4 5
            //6 7 8
            if(i<mColumn){
                pox=1;
                poy=i+1;
            }
            else if(i>=mColumn&&i<mColumn*2){
                pox=2;
                poy=i-2;
            }
            else if(i>=mColumn*2){
                pox=3;
                poy=i-5;
            }
            mIteamBitmaps.get(i).setCurrentX(pox);
            mIteamBitmaps.get(i).setCurrentY(poy);
        }
    }
    private void UPDataXYColmn4(){
        int pox=0;
        int poy=0;
        for(int i=0;i<mColumn*mColumn-1;i++){
            //0 1 2 3
            //4 5 6 7
            //8 9 10 11
            //12 13 14 15
            if(i<mColumn){
                pox=1;
                poy=i+1;
            }
            else if(i>=mColumn&&i<mColumn*2){
                pox=2;
                poy=i-3;
            }
            else if(i>=mColumn*2&&i<mColumn*3){
                pox=3;
                poy=i-7;
            }
            else if(i>=mColumn*3){
                pox=4;
                poy=i-11;
            }
            mIteamBitmaps.get(i).setCurrentX(pox);
            mIteamBitmaps.get(i).setCurrentY(poy);
        }
    }
    private int min(int... params) {
        int min = params[0];
        for (int param : params) {
            if (min > param) min = param;
        }
        return min;
    }
    private boolean isAniming;//防止交换动画还未完成的时候的用户过度操作。
    private ImageView mFirst;//
    private ImageView mSecond;
    private Point pointFirst, pointSecond;
    public void onClick(View v) {
        //游戏开始后才能交换图片！
        if(!isPause) {
            if (isAniming) return;//防止点击过快卡退！
            //找到First item 即小黑块。
            for (int i = 0; i < mGamePintuItems.length; i++) {
                if (getImageIndex((String) mGamePintuItems[i].getTag()) == BlackImagePiece.getIndex()) {
                    mFirst = mGamePintuItems[i];
                }
            }
            //点击一次判定这块的位置是否与小黑块相邻。
            ImageView imageView = (ImageView) v;
            //找到Second item.
            for (int i = 0; i < mGamePintuItems.length; i++) {
                if (mGamePintuItems[i] == imageView) {
                    //判断是否相邻。
                    //点小黑块以外的其他块块
                    if (getImageIndex((String) mGamePintuItems[i].getTag()) != BlackImagePiece.getIndex()) {
                        if (isNear(mIteamBitmaps.get(getImageIdByTag((String) mGamePintuItems[i].getTag())).getCurrentX(),
                                mIteamBitmaps.get(getImageIdByTag((String) mGamePintuItems[i].getTag())).getCurrentY())) {
                            //相邻就交换。
                            exchangeXY(mGamePintuItems[i]);//更新坐标。
                            mSecond = mGamePintuItems[i];
                            exchangeView();
                        }
                    }
                }
            }
        }
    }
    private void exchangeXY(ImageView imagePiece) {
                int X=mIteamBitmaps.get(getImageIdByTag((String)imagePiece.getTag())).getCurrentX();
                int Y=mIteamBitmaps.get(getImageIdByTag((String)imagePiece.getTag())).getCurrentY();
                mIteamBitmaps.get(getImageIdByTag((String)imagePiece.getTag())).setCurrentX(BlackImagePiece.getCurrentX());
                mIteamBitmaps.get(getImageIdByTag((String)imagePiece.getTag())).setCurrentY(BlackImagePiece.getCurrentY());
                BlackImagePiece.setCurrentX(X);
                BlackImagePiece.setCurrentY(Y);
        }
    private boolean isNear(int cX, int cY) {
        int X=BlackImagePiece.getCurrentX();
        int Y=BlackImagePiece.getCurrentY();
        if((X+1)==cX&&cY==Y)return true;
        else if((X-1)==cX&&cY==Y)return true;
        else if(X==cX&&(Y+1)==cY)return true;
        else if(X==cX&&(Y-1)==cY)return true;
        return false;
    }

    //移动动画层
    private RelativeLayout mAnimLayout;

    private void exchangeView() {
        mFirst.setColorFilter(null);
        setUpAnimLayout();//在交换之前构造动画层基本属性

        //复制两个这两个原来view来进行动画，动画开始的时候，先将原来的两个view进行隐藏，然后原来的两个view在动画结束的时候瞬间交换显示即可。
        final ImageView first=new ImageView(getContext());
        final Bitmap firstBitmap=BlackImagePiece.getBitmap();
        first.setImageBitmap(firstBitmap);//要移动的view设置成该图片。

        LayoutParams LP1=new LayoutParams(mIteamWidth,mIteamWidth);//图片宽高
        LP1.leftMargin=mFirst.getLeft()-mPadding;//减去容器的内边距。
        LP1.topMargin=mFirst.getTop()-mPadding;//得到该图片的动画层位置。
        first.setLayoutParams(LP1);
        mAnimLayout.addView(first);

        ImageView second=new ImageView(getContext());
        final Bitmap secondBitmap=mIteamBitmaps.get(getImageIdByTag((String)mSecond.getTag())).getBitmap();
        second.setImageBitmap(secondBitmap);
        LayoutParams LP2=new LayoutParams(mIteamWidth,mIteamWidth);//图片宽高
        LP2.leftMargin=mSecond.getLeft()-mPadding;//减去容器的内边距。
        LP2.topMargin=mSecond.getTop()-mPadding;//得到该图片的动画层位置。
        second.setLayoutParams(LP2);
        mAnimLayout.addView(second);

        //动画层属性设置完毕，//开始设置要交换的两个imageview的动画。
        TranslateAnimation anim=new TranslateAnimation(0,mSecond.getLeft()-mFirst.getLeft(),0,mSecond.getTop()-mFirst.getTop());
        anim.setDuration(300);
        anim.setFillAfter(true);
        first.startAnimation(anim);

        TranslateAnimation animSecond=new TranslateAnimation(0,-mSecond.getLeft()+mFirst.getLeft(),0,-mSecond.getTop()+mFirst.getTop());
        animSecond.setDuration(300);
        animSecond.setFillAfter(true);
        second.startAnimation(animSecond);

        //监听动画，动画开始时，隐藏两个item,动画完成后，并交换两个item,移除动画层view。
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mFirst.setVisibility(View.INVISIBLE);
                mSecond.setVisibility(View.INVISIBLE);//在动画开始的那一刻隐藏两个view.
                isAniming=true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //tag在创建的时候加入了bitmap的i和每张图片的index
                String firstTag=(String)mFirst.getTag();
                String secongTag=(String)mSecond.getTag();

                mFirst.setImageBitmap(secondBitmap);//交换bitmap图。
                mSecond.setImageBitmap(firstBitmap);

                mFirst.setTag(secongTag);//交换item,只交换图和tag.
                mSecond.setTag(firstTag);

                //移动动画结束后再将两交换后的imageview显示出来。
                mFirst.setVisibility(View.VISIBLE);
                mSecond.setVisibility(View.VISIBLE);
                mFirst=mSecond=null;

                mAnimLayout.removeAllViews();//移除动画层。
                checksuccess();//此次交换动作完成后判断是否完成所有交换。
                isAniming=false;
            }

            private void checksuccess() {
                boolean isSuccess=true;
                for(int i=0;i<mGamePintuItems.length;i++){
                    ImageView imageView=mGamePintuItems[i];
                    if(getImageIndex((String)imageView.getTag())!=i) {
                        isSuccess = false;
                    }
                }
                if(isSuccess){
                    isGameSuccess=true;
                    mHandler.removeMessages(TIME_CHANGED);//移除上一级的信息，防止时间变快。
                    Toast.makeText(getContext(),"Suceess!Level UP!",Toast.LENGTH_LONG).show();
                    mHandler.sendEmptyMessage(NEXT_LEVEL);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
//            public void onAnimationRepeat(Animation animation) {
//
//            }
        });

    }

    public int getImageIdByTag(String tag){
        tag.split("_");
        String[] split=tag.split("_");
        return Integer.parseInt(split[0]);
    }

    public int getImageIndex(String tag){
        tag.split("_");
        String[] split=tag.split("_");
        return Integer.parseInt(split[1]);
    }
    private void setUpAnimLayout() {
        if(mAnimLayout==null){
            mAnimLayout=new RelativeLayout(getContext());
            addView(mAnimLayout);//加入面板中。
        }
    }
}
