package com.xiaorui.compassx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.ImageView;

public class Rose extends ImageView {
	int direction = 0;
	int backgroundcolor = Color.BLACK;
	//Paint paint = new Paint(); //设置一个笔刷大小是3的黄色的画笔    
	
	public Rose(Context context) {
	  super(context);
	  this.setImageResource(R.drawable.compassrose);
	}
	
	// Called when component is to be drawn
	@Override
	public void onDraw(Canvas canvas) {
	  int height = this.getHeight();
	  int width = this.getWidth();

      //paint.setColor(Color.YELLOW);
	  //canvas.drawText("TEST", 100, 100, paint);
	  
	  canvas.rotate(direction, width/2, height/2);
	  canvas.drawColor(backgroundcolor);

	  super.onDraw(canvas);
	}
	
	// Called by Compass to update the orientation
	public void setDirection(int direction) { //
	  this.direction = direction;
	  this.invalidate(); // request to be redrawn 
	}
	
	public void setBackgroundColor(int color) {
		this.backgroundcolor = color;
		this.invalidate();
	}
	
	  
}
