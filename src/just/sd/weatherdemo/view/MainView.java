package just.sd.weatherdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 主布局：
 * main_content:显示天气
 * main_menu:侧边栏放置常用列表
 * @author sunqing
 *日期：2015.11.27
 */
public class MainView extends ViewGroup{

	private View main_content;//内容页
	private View main_menu;//侧栏
	
	private boolean isOpen=false;//侧栏状态 
	private int menu_width=0;//侧栏宽度
	
	Context context=null;
	public MainView(Context context) {
		super(context);
		this.context=context;
	}
	
	public MainView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}
	
	public MainView(Context context, View main_content,View main_menu) {
		super(context);
		this.context=context;
		init(main_content,main_menu);
	}
	
	private void init(View main_content, View main_menu) {
		this.main_content=main_content;
		addView(main_content);
		this.main_menu=main_menu;
		addView(main_menu);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		this.main_content.layout(l, t, r, b);
		this.main_menu.layout(-this.main_menu.getMeasuredWidth(), t, 0, b);
		menu_width=(int) (this.main_menu.getMeasuredWidth()*0.4);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		this.main_content.measure(widthMeasureSpec, heightMeasureSpec);//设置内容页大小
		this.main_menu.measure(widthMeasureSpec, heightMeasureSpec);//设置菜单页大小
	}
	
	int startX,touchX;
	boolean direction=false;//移动方向,向左为false,向右为true;(即startX-touchX<0为true)
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startX=(int)event.getX();
			touchX=(int)event.getX();
		case MotionEvent.ACTION_MOVE:
			touchX=(int)event.getX();
			if(startX-touchX<0){
				direction=true;
			}else {
				direction=false;
			}
			if (!isOpen&&direction&&-getScrollX()<menu_width||//如果侧栏处于关闭状态，向右滑动，并且主页面没有超出可视区域
					isOpen&&!direction&&getScrollX()<0) {//如果侧栏处于打开状态，向左滑动，并且主页面没有偏移出窗口
				scrollBy(startX-touchX, 0);
			}
			startX=touchX;
			break;
		case MotionEvent.ACTION_UP:
			if(-getScrollX()<menu_width/2){//如果偏移宽度没有达到侧边栏宽度的一半
				scrollTo(0, 0);
				isOpen=false;
			}else {
				scrollTo(-menu_width, 0);
				isOpen=true;
			}
			break;
		default:
			break;
		}
		return true;
	}
}
