package raj.com.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    List<Itemobject>allitem=getAllItemObject();
    Custom_Adapter custom_adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView=findViewById(R.id.gridview);
        custom_adapter=new Custom_Adapter(this.getLayoutInflater(),MainActivity.this,allitem);
        gridView.setAdapter(custom_adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(MainActivity.this,GameActivity.class);
                intent.putExtra("itemNumber",position);
               // intent.putExtra("title",)
                startActivity(intent);

            }
        });

    }

    private List<Itemobject> getAllItemObject() {
        List<Itemobject>items=new ArrayList<>();
        items.add(new Itemobject("اماکن دیدنی","item1"));
        items.add(new Itemobject("پرچم کشورها","item2"));
        items.add(new Itemobject("طبیعت و منظره","item3"));
        items.add(new Itemobject("خودرو","item4"));
        items.add(new Itemobject("موتور","item5"));
        items.add(new Itemobject("گل","item6"));
        items.add(new Itemobject("شخصیتهای کارتونی","item7"));
        items.add(new Itemobject("حیوانات","item8"));
        items.add(new Itemobject("اعداد","item9"));
        items.add(new Itemobject("حروف الفبا","item10"));
        items.add(new Itemobject("وسایل خانگی","item11"));
        items.add(new Itemobject("هواپیما","item12"));

        return  items;
    }
}
