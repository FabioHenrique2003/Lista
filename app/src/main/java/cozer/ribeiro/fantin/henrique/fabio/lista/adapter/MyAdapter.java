package cozer.ribeiro.fantin.henrique.fabio.lista.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cozer.ribeiro.fantin.henrique.fabio.lista.R;
import cozer.ribeiro.fantin.henrique.fabio.lista.activity.MainActivity;
import cozer.ribeiro.fantin.henrique.fabio.lista.model.MyItem;

/*MyAdapter e suas funções*/


public class MyAdapter extends RecyclerView.Adapter {
    MainActivity mainActivity;
    List<MyItem> itens;

    public MyAdapter(MainActivity mainActivity, List<MyItem> itens) {
        this.mainActivity = mainActivity;
        this.itens = itens;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mainActivity);
        View v = inflater.inflate(R.layout.item_list, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // Obtém o item atual a partir da lista de itens pela posição
        MyItem myItem = itens.get(position);
        View v = holder.itemView;

        // Configura a imagem do item usando a URI armazenado
        ImageView imvfoto = v.findViewById(R.id.imvPhotoPreview);
        imvfoto.setImageBitmap(myItem.photo);

        // Configura o título
        TextView tvTitle = v.findViewById(R.id.tvTitle);
        tvTitle.setText(myItem.title);

        // Configura a descrição
        TextView tvdesc = v.findViewById(R.id.etDesc);
        tvdesc.setText(myItem.description);
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}


