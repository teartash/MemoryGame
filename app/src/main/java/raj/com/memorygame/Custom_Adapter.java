package raj.com.memorygame;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by raj on 12/21/2020.
 */

public class Custom_Adapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    Context context;
    List<Itemobject>liststorage;

    public Custom_Adapter(LayoutInflater layoutInflater, Context context, List<Itemobject> liststorage) {
        this.layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.liststorage = liststorage;
    }

    @Override
    public int getCount() {
        return liststorage.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder listviewholder;
        if (view==null){
            listviewholder=new ViewHolder();
            view=layoutInflater.inflate(R.layout.listview_with_text_image,parent,false);
            listviewholder.imageinListview=view.findViewById(R.id.imageview);
            listviewholder.textinListview=view.findViewById(R.id.textview);
            view.setTag(listviewholder);

        }
        else {
                listviewholder= (ViewHolder) view.getTag();
        }
        listviewholder.textinListview.setText(liststorage.get(position).getContent());
        int imageResourceId=this.context.getResources().getIdentifier(liststorage.get(position).getImageresource(),"drawable",this.context.getPackageName());
        listviewholder.imageinListview.setImageResource(imageResourceId);
        return view;
    }

    private class ViewHolder {
        TextView textinListview;
        ImageView imageinListview;
    }
}
