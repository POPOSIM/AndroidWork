package Android_Examples.AndroidWork;

import java.util.ArrayList;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MydataAdapter extends BaseAdapter {	
	private LayoutInflater mInflater;
	public ArrayList<String[]> data;//�}�C�O�s���}�Τ�r����
	GetWebImg ImgCache;
	public MydataAdapter(Context c,ArrayList<String[]> d,GetWebImg cache) {			
		mInflater = LayoutInflater.from(c);
		data = d;		
		ImgCache=cache;
	}
	public int getCount() {
		return data.size();
	}
	public Object getItem(int position) {
		return data.get(position);
	}
	public long getItemId(int position) {
		return position;
	}
	
	public View getView(final int position, View view, ViewGroup parent) {
		final ViewHolder holder;
		if (view == null) {
			view = mInflater.inflate(R.layout.main_content, null);
			holder = new ViewHolder();
			holder.text = (TextView) view.findViewById(R.id.main_content_text);
			holder.pic = (ImageView) view.findViewById(R.id.main_content_pic);
			holder.wait = (ProgressBar) view.findViewById(R.id.main_content_wait);
			view.setTag(holder);        		
		} else {
			holder = (ViewHolder) view.getTag();
		} 
		holder.text.setText(data.get(position)[0]);//��ܤ�r����
		holder.pic.setVisibility(View.INVISIBLE);
		holder.wait.setVisibility(View.VISIBLE);
		if (ImgCache.IsCache(data.get(position)[1]) == false){//�p�G�Ϥ��S���Ȧs
			ImgCache.LoadUrlPic(data.get(position)[1],h);
		}else if (ImgCache.IsDownLoadFine(data.get(position)[1]) == true){//�p�G�w�g�U�������A�N��ܹϤ��ç�ProgressBar����
			holder.pic.setImageBitmap(ImgCache.getImg(data.get(position)[1]));
			holder.wait.setVisibility(View.GONE);
			holder.pic.setVisibility(View.VISIBLE);	
		}else{
			//�o�̬O�U�����A����Ƴ����ΰ�
		}		
		return view;
	}
	Handler h = new Handler(){//�i�DBaseAdapter��Ƥw�g��s�F
		@Override
		public void handleMessage(Message msg) {
			Log.d("m", "notifyDataSetChanged");
			notifyDataSetChanged();
			super.handleMessage(msg);
		}
	};	
	class ViewHolder{
		TextView text;
		ImageView pic;
		ProgressBar wait;
	}
}