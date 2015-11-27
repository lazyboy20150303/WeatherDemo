package just.sd.weatherdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * �����֣�
 * main_content:��ʾ����
 * main_menu:��������ó����б�
 * @author sunqing
 *���ڣ�2015.11.27
 */
public class MainView extends ViewGroup{

	private View main_content;//����ҳ
	private View main_menu;//����
	
	private boolean isOpen=false;//����״̬ 
	private int menu_width=0;//�������
	
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
		this.main_content.measure(widthMeasureSpec, heightMeasureSpec);//��������ҳ��С
		this.main_menu.measure(widthMeasureSpec, heightMeasureSpec);//���ò˵�ҳ��С
	}
	
	int startX,touchX;
	boolean direction=false;//�ƶ�����,����Ϊfalse,����Ϊtrue;(��startX-touchX<0Ϊtrue)
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
			if (!isOpen&&direction&&-getScrollX()<menu_width||//����������ڹر�״̬�����һ�����������ҳ��û�г�����������
					isOpen&&!direction&&getScrollX()<0) {//����������ڴ�״̬�����󻬶���������ҳ��û��ƫ�Ƴ�����
				scrollBy(startX-touchX, 0);
			}
			startX=touchX;
			break;
		case MotionEvent.ACTION_UP:
			if(-getScrollX()<menu_width/2){//���ƫ�ƿ��û�дﵽ�������ȵ�һ��
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
