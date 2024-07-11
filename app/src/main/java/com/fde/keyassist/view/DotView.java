package com.fde.keyassist.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class DotView extends View {

    private Paint paint;
    private float centerX;
    private float centerY;

    public DotView(Context context,float centerX,float centerY){
        super(context);
        this.centerX = centerX;
        this.centerY = centerY;
        init();
    }

    public DotView(Context context) {
        super(context);
        init();
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // 初始化画笔
        paint = new Paint();
        paint.setColor(Color.RED); // 设置圆点的颜色
        paint.setAntiAlias(true); // 设置抗锯齿
        paint.setStyle(Paint.Style.FILL); // 填充模式
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 绘制圆点
        // 这里假设我们想在视图的中心绘制一个半径为50dp的圆点
        // 注意：由于我们使用的是dp单位，所以需要将其转换为px
        float radius = dpToPx(10);
        canvas.drawCircle(1069f, 271f, radius, paint);
    }

    // dp转px
    private float dpToPx(float dp) {
        return dp * (getResources().getDisplayMetrics().densityDpi / 160f);
    }
}
