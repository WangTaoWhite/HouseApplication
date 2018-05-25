package mvp.com.neteaseapp.content.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import mvp.com.neteaseapp.R;
import mvp.com.neteaseapp.util.DimensionUtil;


public class ColorTextView extends View {
    private Paint mPaint;
    private String mText;
    private float mTextSize;
    private int mOriginColor, mChangeColor;
    private int mProgress;
    private int mViewWidth, mViewHeight, mTextWidth, mTextHeight;
    private float mTextStartX, mTextStartY;
    private Context mContext;

    public ColorTextView(Context context) {
        this(context, null);
    }

    public ColorTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ColorTextView);
        mTextSize = array.getDimensionPixelSize(R.styleable.ColorTextView_text_size, DimensionUtil.pxFromSp(context, 13));
        mOriginColor = array.getColor(R.styleable.ColorTextView_text_origin_color, Color.BLACK);
        mChangeColor = array.getColor(R.styleable.ColorTextView_text_change_color, Color.RED);
        mText = (String) array.getText(R.styleable.ColorTextView_text);
        array.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mTextSize);
        measureText();
    }

    private void measureText() {
        Rect mTextRect = new Rect();
        mTextWidth = (int) mPaint.measureText(mText);
        mPaint.getTextBounds(mText, 0, mText.length(), mTextRect);
        mTextHeight = mTextRect.height();
        mTextWidth = mTextRect.width();
    }

    public void setProgress(int progress) {
        mProgress = progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
        mViewHeight = getMeasuredHeight();
        mViewWidth = getMeasuredWidth();
        mTextStartX = mViewWidth - mTextWidth - getPaddingEnd();
        mTextStartY = mViewHeight - getPaddingBottom();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private int measureWidth(int widthMeasureSpec) {
        int spec = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int width = 0;
        if (spec == MeasureSpec.EXACTLY) {
            width = size;
        } else if (spec == MeasureSpec.AT_MOST) {
            width = Math.min(size, mTextWidth);
        }

        return width + getPaddingStart() + getPaddingEnd();
    }

    private int measureHeight(int heightMeasureSpec) {
        int spec = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        int height = 0;
        if (spec == MeasureSpec.EXACTLY) {
            height = size;
        } else if (spec == MeasureSpec.AT_MOST) {
            height = Math.min(size, mTextHeight);
        }
        return height + getPaddingTop() + getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.GREEN);
        canvas.save();
        canvas.clipRect(0, 0, mViewWidth / 2, mViewHeight);
        canvas.drawText(mText, mTextStartX, mTextStartY, mPaint);
        canvas.restore();

        canvas.save();
        canvas.clipRect(mViewWidth / 2, 0, mViewWidth, mViewHeight);
        mPaint.setColor(mChangeColor);
        canvas.drawText(mText, mTextStartX, mTextStartY, mPaint);
        canvas.restore();
    }
}
