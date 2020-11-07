package cn.itcast.listview;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
public class MainActivity extends Activity {
    private ListView mListView;

    private String[] titles = { "桌子", "苹果", "蛋糕", "线衣", "猕猴桃",
            "围巾"};
    private String[] prices = { "1800元", "10元/kg", "300元", "350元", "10元/kg",
            "280元"};

    private int[] icons = {R.drawable.table,R.drawable.apple,R.drawable.cake,
            R.drawable.wireclothes,R.drawable.kiwifruit,R.drawable.scarf};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化ListView控件
        mListView = (ListView) findViewById(R.id.lv);
        //创建一个Adapter的实例
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        //设置Adapter
        mListView.setAdapter(mAdapter);
    }
    //创建一个类继承BaseAdapter
    class MyBaseAdapter extends BaseAdapter {

        @Override
        public int getCount() {                  //得到item的总数
            return titles.length;//返回ListView Item条目的总数
        }

        @Override
        public Object getItem(int position) {  //得到Item代表的对象
            return titles[position];           //返回ListView Item条目代表的对象
        }

        @Override
        public long getItemId(int position) {        //得到Item的id
            return position; //返回ListView Item的id
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {    //加载list_item.xml布局文件
            View view = View.inflate(MainActivity.this,R.layout.list_item,null);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView price = (TextView) view.findViewById(R.id.price);
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            title.setText(titles[position]);
            price.setText(prices[position]);
            iv.setBackgroundResource(icons[position]);
            return view;
        }
    }

        class ViewHolder{
            TextView title;
            TextView price;
            ImageView iv;
        }

        public View getView(int position, View convertView, ViewGroup parent) {   //得到Item的View视图
            ViewHolder holder = null;
            if(convertView == null){
                //将list_item.xml文件找出来并转换成View对象
                convertView  = View.inflate(MainActivity.this, R.layout.list_item, null);
                //找到list_item.xml中创建的TextView
                holder = new ViewHolder();
                holder.title = (TextView) convertView.findViewById(R.id.title);
                holder.price = (TextView) convertView.findViewById(R.id.price);
                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(titles[position]);
            holder.price.setText(prices[position]);
            holder.iv.setBackgroundResource(icons[position]);
            return convertView;
    }
}
