package com.billy.searchsidebar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.billy.searchsidebar.R;

import java.util.Arrays;

/**
 * Created by Billy_Cui on 2018/7/18
 * Describe:
 */

public class WaveSideBar extends View {

    private final static int DEFAULT_TEXT_SIZE = 14; // sp
    private final static int DEFAULT_MAX_OFFSET = 60; //dp


    private final static String[] DEFAULT_INDEX_ITEMS = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    private String[] mIndexItems;

    private int mCurrentIndex = -1;

    private float mCurrentY = -1;

    private Paint mPaint;
    private int mTextColor;
    private float mTextSize;


    private float mIndexItemHeight;
    private float mMaxOffset;

    private RectF mStartTouchingArea = new RectF();
    private float mBarHeight;
    private float mBarWidth;

    private boolean mStartTouching = false;

    private boolean mLazyRespond = false;

    private int mSideBarPosition;
    public static final int POSITION_RIGHT = 0;
    public static final int POSITION_LEFT = 1;


    private int mTextAlignment;
    public static final int TEXT_ALIGN_CENTER = 0;
    public static final int TEXT_ALIGN_LEFT = 1;
    public static final int TEXT_ALIGN_RIGHT = 2;


    private OnSelectIndexItemListener onSelectIndexItemListener;


    private float mFirstItemBaseLineY;


    private DisplayMetrics mDisplayMetrics;


    public WaveSideBar(Context context) {
        this(context, null);
    }

    public WaveSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDisplayMetrics = context.getResources().getDisplayMetrics();

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.sideBar);
        mLazyRespond = typedArray.getBoolean(R.styleable.sideBar_sidebar_lazy_respond, false);
        mTextColor = typedArray.getColor(R.styleable.sideBar_sidebar_text_color, Color.GRAY);
        mTextSize = typedArray.getDimension(R.styleable.sideBar_sidebar_text_size, sp2px(DEFAULT_TEXT_SIZE));
        mMaxOffset = typedArray.getDimension(R.styleable.sideBar_sidebar_max_offset, dp2px(DEFAULT_MAX_OFFSET));
        mSideBarPosition = typedArray.getInt(R.styleable.sideBar_sidebar_position, POSITION_RIGHT);
        mTextAlignment = typedArray.getInt(R.styleable.sideBar_sidebar_text_alignment, TEXT_ALIGN_CENTER);
        typedArray.recycle();

        mIndexItems = DEFAULT_INDEX_ITEMS;

        initPaint();

    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mTextColor);
        mPaint.setTextSize(mTextSize);
        switch (mTextAlignment) {
            case TEXT_ALIGN_CENTER:
                mPaint.setTextAlign(Paint.Align.CENTER);
                break;
            case TEXT_ALIGN_LEFT:
                mPaint.setTextAlign(Paint.Align.LEFT);
                break;
            case TEXT_ALIGN_RIGHT:
                mPaint.setTextAlign(Paint.Align.RIGHT);
                break;
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //测量控件宽高
        mIndexItemHeight = fontMetrics.bottom - fontMetrics.top;
        mBarHeight = mIndexItems.length * mIndexItemHeight;
        for (String indexItem : mIndexItems) {
            mBarWidth = Math.max(mBarWidth, mPaint.measureText(indexItem));
        }

        float areaLeft = (mSideBarPosition == POSITION_LEFT) ? 0 : (width - mBarWidth - getPaddingRight());
        float areaRight = (mSideBarPosition == POSITION_LEFT) ? (getPaddingLeft() + areaLeft + mBarWidth) : width;
        //此处有疑问 ???
        float areaTop = height / 2 - mBarHeight / 2;
        float areaBottom = areaTop + mBarHeight;
        mStartTouchingArea.set(
                areaLeft,
                areaTop,
                areaRight,
                areaBottom
        );
        mFirstItemBaseLineY = (height / 2 - mIndexItems.length * mIndexItemHeight / 2)
                + (mIndexItemHeight / 2 - (fontMetrics.descent - fontMetrics.ascent) / 2)
                - fontMetrics.ascent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0, mIndexItemsLength = mIndexItems.length; i < mIndexItemsLength; i++) {
            float baseLineY = mFirstItemBaseLineY + mIndexItemHeight * i;

            float scale = getItemScale(i);

            int alphaScale = (i == mCurrentIndex) ? (255) : (int) (255 * (1 - scale));
            mPaint.setAlpha(alphaScale);

            mPaint.setTextSize(mTextSize + mTextSize * scale);

            float baseLineX = 0f;

            if (mSideBarPosition == POSITION_LEFT) {
                switch (mTextAlignment) {
                    case TEXT_ALIGN_CENTER:
                        baseLineX = getPaddingLeft() + mBarWidth / 2 + mMaxOffset * scale;
                        break;
                    case TEXT_ALIGN_LEFT:
                        baseLineX = getPaddingLeft() + mMaxOffset * scale;
                        break;
                    case TEXT_ALIGN_RIGHT:
                        baseLineX = getPaddingLeft() + mBarWidth + mMaxOffset * scale;
                        break;
                }
            } else {
                switch (mTextAlignment) {
                    case TEXT_ALIGN_CENTER:
                        baseLineX = getWidth() - getPaddingRight() - mBarWidth / 2 - mMaxOffset * scale;
                        break;
                    case TEXT_ALIGN_LEFT:
                        baseLineX = getWidth() - getPaddingRight() - mMaxOffset * scale - mBarWidth;
                        break;
                    case TEXT_ALIGN_RIGHT:
                        baseLineX = getWidth() - getPaddingRight() - mMaxOffset * scale;
                        break;
                }
            }

            canvas.drawText(
                    mIndexItems[i],
                    baseLineX,
                    baseLineY,
                    mPaint);
        }
        mPaint.setAlpha(255);
        mPaint.setTextSize(mTextSize);
    }

    private float getItemScale(int index) {
        float scale = 0;
        if (mCurrentIndex != -1) {
            float distance = Math.abs(mCurrentY - (mIndexItemHeight * index + mIndexItemHeight / 2)) / mIndexItemHeight;
            scale = 1 - distance * distance / 16;
            scale = Math.max(scale, 0);
        }
        return scale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mIndexItems.length == 0) {
            return super.onTouchEvent(event);
        }
        float eventY = event.getY();
        float eventX = event.getX();

        mCurrentIndex = getSelectedIndex(eventY);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mStartTouchingArea.contains(eventX, eventY)) {
                    mStartTouching = true;
                    if (!mLazyRespond && onSelectIndexItemListener != null) {
                        onSelectIndexItemListener.onSelectIndexItem(mIndexItems[mCurrentIndex]);
                    }
                    invalidate();
                    return true;
                } else {
                    mCurrentIndex = -1;
                    return false;
                }
            case MotionEvent.ACTION_MOVE:
                if (mStartTouching && !mLazyRespond && onSelectIndexItemListener != null) {
                    onSelectIndexItemListener.onSelectIndexItem(mIndexItems[mCurrentIndex]);
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
//                if (!mLazyRespond && onSelectIndexItemListener != null) {
//                    onSelectIndexItemListener.onSelectIndexItem(mIndexItems[mCurrentIndex]);
//                }
                mCurrentIndex = -1;
                mStartTouching = false;
                invalidate();
                return true;
        }

        return super.onTouchEvent(event);
    }

    private int getSelectedIndex(float eventY) {
        mCurrentY = eventY - (getHeight() / 2 - mBarHeight / 2);
        if (mCurrentY <= 0) {
            return 0;
        }
        int index = (int) (mCurrentY / this.mIndexItemHeight);
        if (index >= this.mIndexItems.length) {
            index = this.mIndexItems.length - 1;
        }
        return index;
    }

    private float dp2px(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, this.mDisplayMetrics);
    }

    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, this.mDisplayMetrics);
    }

    public void setIndexItems(String... indexItems) {
        mIndexItems = Arrays.copyOf(indexItems, indexItems.length);
        requestLayout();
    }

    public void setTextColor(int color) {
        mTextColor = color;
        mPaint.setColor(color);
        invalidate();
    }

    public void setPosition(int position) {
        if (position != POSITION_RIGHT && position != POSITION_LEFT) {
            throw new IllegalArgumentException("the position must be POSITION_RIGHT or POSITION_LEFT");
        }

        mSideBarPosition = position;
        requestLayout();
    }

    public void setMaxOffset(int offset) {
        mMaxOffset = offset;
        invalidate();
    }

    public void setLazyRespond(boolean lazyRespond) {
        mLazyRespond = lazyRespond;
    }

    public void setTextAlign(int align) {
        if (mTextAlignment == align) {
            return;
        }
        switch (align) {
            case TEXT_ALIGN_CENTER: mPaint.setTextAlign(Paint.Align.CENTER); break;
            case TEXT_ALIGN_LEFT:   mPaint.setTextAlign(Paint.Align.LEFT); break;
            case TEXT_ALIGN_RIGHT:  mPaint.setTextAlign(Paint.Align.RIGHT); break;
            default:
                throw new IllegalArgumentException(
                        "the alignment must be TEXT_ALIGN_CENTER, TEXT_ALIGN_LEFT or TEXT_ALIGN_RIGHT");
        }
        mTextAlignment = align;
        invalidate();
    }

    public void setTextSize(float size) {
        if (mTextSize == size) {
            return;
        }
        mTextSize = size;
        mPaint.setTextSize(size);
        invalidate();
    }

    public void setOnSelectIndexItemListener(OnSelectIndexItemListener onSelectIndexItemListener) {
        this.onSelectIndexItemListener = onSelectIndexItemListener;
    }

    public interface OnSelectIndexItemListener {
        void onSelectIndexItem(String index);
    }
}
