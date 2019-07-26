package com.wl.weatherlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.wl.weatherlib.R;
import com.wl.weatherlib.interfaces.TempEntry;
import com.wl.weatherlib.utils.PixelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Project: WeatherUtils
 * @Package: com.wl.weatherlib.widget
 * @Author: HSL
 * @Time: 2019/07/25 10:36
 * @E-mail: xxx@163.com
 * @Description: 这个人太懒，没留下什么踪迹~
 */
public class WeatherChartView<T extends TempEntry> extends View {

    private static final int CIRCLE = 1;
    private static final int RING = 2;

    public static int mMaxTemp = -1;
    public static int mMinTemp = -1;
    public static int mNowPosition = -1;

    /**
     * 目前支持两种:圆、圆环
     */
    private int mDotModel;
    /**
     * 点的颜色
     */
    private int mDotColor;
    /**
     * 当前时刻之前点的颜色
     */
    private int mPassDotColor;
    /**
     * 点的半径
     */
    private int mDotRadius;
    /**
     * 折线的尺寸
     */
    private int mFoldLineSize;
    /**
     * 折线颜色
     */
    private int mFoldLineColor;
    /**
     * 现在时间点之前的折线颜色
     */
    private int mPassFoldLineColor;

    /**
     * 文本颜色
     */
    private int mNormalTextColor;

    /**
     * 当前时刻文本颜色
     */
    private int mNowTextColor;

    /**
     * 文本尺寸
     */
    private int mTextSize;
    /**
     * 文本距底部距离
     */
    private int mTextMarginBottom;
    /**
     * 中间线颜色
     */
    private int mMiddleLineColor;
    /**
     * 中间线尺寸
     */
    private int mMiddleLineSize;
    /**
     * 显示中间线
     */
    private boolean mShowMiddleLine;
    /**
     * 虚线颜色
     */
    private int mDottedLineColor;
    /**
     * 虚线尺寸
     */
    private int mDottedLineSize;
    /**
     * 显示虚线
     */
    private boolean mShowDottedLine;
    /**
     * 连接点画笔
     */
    private Paint mDotPaint;
    /**
     * 文本画笔
     */
    private Paint mTextPaint;
    /**
     * 折线画笔
     */
    private Paint mFoldLinePaint;
    /**
     * 中间线画笔
     */
    private Paint mMiddlePaint;
    /**
     * 虚线画笔
     */
    private Paint mDottedLinePaint;
    /**
     * 当前Item下标
     */
    private int mItemPosition = -1;
    /**
     * 数据源
     */
    private List<T> mData = new ArrayList<>();


    public WeatherChartView(Context context) {
        this(context, null);
    }

    public WeatherChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeatherChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(context, attrs, defStyleAttr);
        initPaints();
    }

    /**
     * 获取属性值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void initAttributes(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WeatherChartView);
        mDotModel = ta.getInteger(R.styleable.WeatherChartView_DotModel, RING);
        mDotColor = ta.getColor(R.styleable.WeatherChartView_dotColor, Color.parseColor("#ffffff"));
        mPassDotColor = ta.getColor(R.styleable.WeatherChartView_passDotColor, Color.parseColor("#80ffffff"));
        mDotRadius = (int) ta.getDimension(R.styleable.WeatherChartView_dotRadius, 7);
        mFoldLineSize = (int) ta.getDimension(R.styleable.WeatherChartView_foldLineSize, 4);
        mFoldLineColor = ta.getColor(R.styleable.WeatherChartView_foldLineColor, Color.parseColor("#ffffff"));
        mPassFoldLineColor = ta.getColor(R.styleable.WeatherChartView_passFoldLineColor, Color.parseColor("#80ffffff"));
        mTextSize = (int) ta.getDimension(R.styleable.WeatherChartView_textSize, PixelUtils.sp2px(context, 16));
        mNormalTextColor = ta.getColor(R.styleable.WeatherChartView_NormalTextColor, Color.parseColor("#ffffff"));
        mNowTextColor = ta.getColor(R.styleable.WeatherChartView_NowTextColor, Color.parseColor("#ffffff"));
        mTextMarginBottom = (int) ta.getDimension(R.styleable.WeatherChartView_textMarginBottom, PixelUtils.dip2px(context, 6));
        mShowMiddleLine = ta.getBoolean(R.styleable.WeatherChartView_showMiddleLine, false);
        mMiddleLineColor = ta.getColor(R.styleable.WeatherChartView_middleLineColor, Color.parseColor("#ffffff"));
        mMiddleLineSize = (int) ta.getDimension(R.styleable.WeatherChartView_middleLineSize, 2);
        mShowDottedLine = ta.getBoolean(R.styleable.WeatherChartView_showDottedLine, true);
        mDottedLineSize = (int) ta.getDimension(R.styleable.WeatherChartView_dottedLineSize, 2);
        mDottedLineColor = ta.getColor(R.styleable.WeatherChartView_dottedLineColor, Color.parseColor("#ffffff"));
        ta.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaints() {
        //折线连接点
        mDotPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDotPaint.setColor(mDotColor);
        if (mDotModel == CIRCLE) {
            mDotPaint.setStyle(Paint.Style.FILL);
        }
        if (mDotModel == RING) {
            mDotPaint.setStyle(Paint.Style.STROKE);
            mDotPaint.setStrokeWidth(mFoldLineSize);
        }
        //连接点之上的文本
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mNormalTextColor);
        mTextPaint.setTextSize(mTextSize);
        //折线实心
        mFoldLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFoldLinePaint.setStyle(Paint.Style.FILL);
        mFoldLinePaint.setColor(mFoldLineColor);
        mFoldLinePaint.setStrokeWidth(mFoldLineSize);
        //中间线
        mMiddlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMiddlePaint.setStyle(Paint.Style.FILL);
        mMiddlePaint.setColor(mMiddleLineColor);
        mMiddlePaint.setStrokeWidth(mMiddleLineSize);
        //虚线-连接点向下延伸的虚线
        mDottedLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDottedLinePaint.setStyle(Paint.Style.STROKE);
        mDottedLinePaint.setColor(mDottedLineColor);
        mDottedLinePaint.setStrokeWidth(mDottedLineSize);
        PathEffect pathEffect = new DashPathEffect(new float[]{8, 8}, 0);
        mDottedLinePaint.setPathEffect(pathEffect);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthModel = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        //去除padding
        width = widthSize;
        height = heightSize - getPaddingTop() - getPaddingBottom();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMaxTemp == mMinTemp) {
            return;
        }
        //获取测量的宽高
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        //获取温度Entry
        TempEntry tempEntry = mData.get(mItemPosition);
        //获取文字宽高
        Rect textRect = new Rect();
        String tempText = tempEntry.getTempText();
        mTextPaint.getTextBounds(tempText, 0, tempText.length(), textRect);
        int textWidth = textRect.right - textRect.left;
        int textHeight = textRect.bottom - textRect.top;
        //计算折线绘制的空间
        int foldHeight = measuredHeight - textHeight - mTextMarginBottom;
        //计算出每一度的高度
        int degreeHeight = foldHeight / (mMaxTemp - mMinTemp);
        //绘制中间线
        if (mShowMiddleLine) {
            canvas.drawLine(measuredWidth / 2, 0, measuredWidth / 2, measuredHeight, mMiddlePaint);
        }
        //绘制连接点
        if (mNowPosition != -1 && mItemPosition < mNowPosition) {
            mDotPaint.setColor(mPassDotColor);
        } else {
            mDotPaint.setColor(mDotColor);
        }
        int dotY = measuredHeight - (tempEntry.getTempValue() - mMinTemp) * degreeHeight;
        int dotX = measuredWidth / 2;
        canvas.drawCircle(dotX, dotY, mDotRadius, mDotPaint);
        //绘制文本
        if (mItemPosition < mNowPosition) {
            mTextPaint.setColor(mPassFoldLineColor);
        } else if (mItemPosition == mNowPosition) {
            mTextPaint.setColor(mNowTextColor);
        } else {
            mTextPaint.setColor(mNormalTextColor);
        }
        canvas.drawText(tempText, dotX - textWidth / 2, dotY - mDotRadius - mTextMarginBottom, mTextPaint);
        //绘制虚线
        if (mShowDottedLine) {
            Path path = new Path();
            path.moveTo(measuredWidth / 2, dotY + mDotRadius + mFoldLineSize);
            path.lineTo(measuredWidth / 2, measuredHeight);
            canvas.drawPath(path, mDottedLinePaint);
        }
        //绘制折线
        if (mItemPosition > 0) {
            //不是第一个Item,全部绘制左半部折线
            if (mNowPosition != -1 && mItemPosition <= mNowPosition) {
                mFoldLinePaint.setColor(mPassFoldLineColor);
            } else {
                mFoldLinePaint.setColor(mFoldLineColor);
            }
            TempEntry tempEntryBefore = mData.get(mItemPosition - 1);
            int beforeDotY = measuredHeight - (tempEntryBefore.getTempValue() - mMinTemp) * degreeHeight;
            int leftDotY = (dotY + beforeDotY) / 2;
            canvas.drawLine(0, leftDotY, dotX - mDotRadius, dotY, mFoldLinePaint);
        }
        if (mItemPosition < mData.size() - 1) {
            //不是最后一个Item,全部绘制右半部分折线
            if (mNowPosition != -1 && mItemPosition < mNowPosition) {
                mFoldLinePaint.setColor(mPassFoldLineColor);
            } else {
                mFoldLinePaint.setColor(mFoldLineColor);
            }
            TempEntry tempEntryAfter = mData.get(mItemPosition + 1);
            int AfterDotY = measuredHeight - (tempEntryAfter.getTempValue() - mMinTemp) * degreeHeight;
            int rightDotY = (dotY + AfterDotY) / 2;
            canvas.drawLine(dotX + mDotRadius, dotY, measuredWidth, rightDotY, mFoldLinePaint);
        }
    }

    /**
     * 绘制
     *
     * @param data
     * @param position
     */
    public void drawChartView(List<T> data, int position) {
        mData.clear();
        mData.addAll(data);
        mItemPosition = position;
        postInvalidate();
    }
}
