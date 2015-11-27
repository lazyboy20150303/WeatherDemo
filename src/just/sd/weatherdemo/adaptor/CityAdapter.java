package just.sd.weatherdemo.adaptor;

import java.util.List;
import java.util.Map;

import just.sd.weatherdemo.db.DB;

import com.example.weatherdemo.R;

import android.content.Context;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AbsListView.OnScrollListener;

/**
 * 配置ListView显示城市信息(根据页面位置动态加载数据)
 * @author sunqing
 *日期:2015.11.25
 */
public class CityAdapter{

	Context context;
	ListView citysview;
	SimpleAdapter simpleAdapter;
	DB db=null;
	private int lastItem=0;//保存最后一个记录点
	private int currentPage=1;//当前页码
	private int pageSize=1;//总页数
	private int lineSize=30;//每一页记录数
	private int allRecorders=0;//保存全部记录数
	private List<Map<String, String>> list;
	public CityAdapter(Context context, DB db, ListView citysview) {
		this.context=context;
		this.citysview=citysview;
		this.db=db;
	}

	/**
	 * 增加数据
	 */
	private void appendData() {
		db.open();
		List<Map<String, String>> citypage=db.findPage(this.currentPage, this.lineSize);//查询最新列
		this.list.addAll(citypage);
		this.simpleAdapter.notifyDataSetChanged();
		db.close();
	}

	/**
	 * 显示数据列
	 */
	public void showAllData(){
		db.open();
		this.allRecorders=db.getCount();
		this.list=db.findPage(this.currentPage, this.lineSize);
		db.close();
		simpleAdapter=new SimpleAdapter(context,
				this.list, R.layout.cityitem, 
				new String[]{"db_cityname"}, 
				new int[]{R.id.db_cityname});

		this.citysview.setAdapter(simpleAdapter);
		
		this.citysview.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if(lastItem==simpleAdapter.getCount()//当前记录已经在最底部
						&&currentPage<pageSize//当前页小于总页数
						&&scrollState==OnScrollListener.SCROLL_STATE_IDLE){//屏幕不再滚动
					currentPage++;
					citysview.setSelection(lastItem);
					appendData();
				}
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				lastItem=firstVisibleItem+visibleItemCount;
			}
		});
		this.pageSize=(this.allRecorders+this.currentPage-1)/this.lineSize;
	}
}
