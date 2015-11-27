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
 * ����ListView��ʾ������Ϣ(����ҳ��λ�ö�̬��������)
 * @author sunqing
 *����:2015.11.25
 */
public class CityAdapter{

	Context context;
	ListView citysview;
	SimpleAdapter simpleAdapter;
	DB db=null;
	private int lastItem=0;//�������һ����¼��
	private int currentPage=1;//��ǰҳ��
	private int pageSize=1;//��ҳ��
	private int lineSize=30;//ÿһҳ��¼��
	private int allRecorders=0;//����ȫ����¼��
	private List<Map<String, String>> list;
	public CityAdapter(Context context, DB db, ListView citysview) {
		this.context=context;
		this.citysview=citysview;
		this.db=db;
	}

	/**
	 * ��������
	 */
	private void appendData() {
		db.open();
		List<Map<String, String>> citypage=db.findPage(this.currentPage, this.lineSize);//��ѯ������
		this.list.addAll(citypage);
		this.simpleAdapter.notifyDataSetChanged();
		db.close();
	}

	/**
	 * ��ʾ������
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
				if(lastItem==simpleAdapter.getCount()//��ǰ��¼�Ѿ�����ײ�
						&&currentPage<pageSize//��ǰҳС����ҳ��
						&&scrollState==OnScrollListener.SCROLL_STATE_IDLE){//��Ļ���ٹ���
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
