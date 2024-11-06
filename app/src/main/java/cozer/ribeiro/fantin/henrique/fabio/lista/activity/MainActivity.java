package cozer.ribeiro.fantin.henrique.fabio.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.NinePatch;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import cozer.ribeiro.fantin.henrique.fabio.lista.R;
import cozer.ribeiro.fantin.henrique.fabio.lista.adapter.MyAdapter;
import cozer.ribeiro.fantin.henrique.fabio.lista.model.MainActivityViewModel;
import cozer.ribeiro.fantin.henrique.fabio.lista.model.MyItem;
import cozer.ribeiro.fantin.henrique.fabio.lista.util.Util;

public class MainActivity extends AppCompatActivity {

    static int NEW_ITEM_REQUEST = 1;
    MyAdapter myAdapter; /*Cria objeto de myAdapter*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        FloatingActionButton fabAddItem = findViewById(R.id.fabAddNewItem);
        fabAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewItemActivity.class); /*Criação de um Intent*/
                startActivityForResult(i, NEW_ITEM_REQUEST);
            }
        });


        MainActivityViewModel vm = new ViewModelProvider(this).get(MainActivityViewModel.class);
        List<MyItem> itens = vm.getItens();
        RecyclerView rvItens = findViewById(R.id.rvItens);

        myAdapter = new MyAdapter(this, itens); /*Cria instância de myAdapter*/
        rvItens.setAdapter(myAdapter); /*Seta o adapter com a instância que criou*/

        rvItens.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this); /*Cria instância de Linear Layout Manager*/
        rvItens.setLayoutManager(layoutManager); /*Seta a instância de LLM Criada*/
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rvItens.getContext(), DividerItemDecoration.VERTICAL); /*Cria instância de Divider Item Decoration*/
        rvItens.addItemDecoration(dividerItemDecoration); /*Adiciona a instância criada*/
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ITEM_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                MyItem myItem = new MyItem(); /*Cria a instância de item*/
                myItem.title = data.getStringExtra("title"); /*Pega a título do item*/
                myItem.description = data.getStringExtra("description"); /*Pega a descrição do item*/
                Uri selectedPhotoURI = data.getData();
                try {
                    Bitmap photo = Util.getBitmap( MainActivity.this, selectedPhotoURI, 100, 100 );
                    myItem.photo = photo;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                // Possível retirada
                MainActivityViewModel vm = new ViewModelProvider( this ).get(MainActivityViewModel.class );
                List<MyItem> itens = vm.getItens();
                //Possível retirada
                itens.add(myItem);
                myAdapter.notifyItemInserted(itens.size()-1);
            }
        }
    }
}