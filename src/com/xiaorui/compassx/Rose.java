package com.xiaorui.compassx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class Rose extends ImageView {
	int direction;
	//Paint paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔    
	
	public Rose(Context context) {
		super(context);
		direction = 0;
	}
	  
	public Rose(Context context, AttributeSet attrs) {
		super(context, attrs); 
		direction = 0;  
	}
	
	public Rose(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		direction = 0;
	}	
	
	
	// Called when component is to be drawn
	@Override
	public void onDraw(Canvas canvas) {
			  
	  int height = this.getHeight();
	  int width = this.getWidth();
	  
	  canvas.rotate(direction, width/2, height/2);
	  
      //paint.setColor(Color.GREEN);
	  //canvas.drawText("TEST", 100, 100, paint);
	  
	  super.onDraw(canvas);
	}
	
	// Called by Compass to update the orientation
	public void setDirection(int direction) { //
	  this.direction = direction;
	  this.invalidate(); // request to be redrawn 
	}
	
	
	  
}
