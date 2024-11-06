package cozer.ribeiro.fantin.henrique.fabio.lista.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import cozer.ribeiro.fantin.henrique.fabio.lista.R;
import cozer.ribeiro.fantin.henrique.fabio.lista.model.NewItemActivityViewModel;

public class NewItemActivity extends AppCompatActivity {

    static int PHOTO_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_item);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()); /*Insets do tipo Barras do sistema*/
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom); /*Seta padrões*/
            return insets;
        });
        NewItemActivityViewModel vm = new ViewModelProvider( this ).get(NewItemActivityViewModel.class );
        Uri selectPhotoLocation = vm.getSelectPhotoLocation();
        if(selectPhotoLocation != null) {
            ImageView imvfotoPreview = findViewById(R.id.imvPhotoPreview);
            imvfotoPreview.setImageURI(selectPhotoLocation);
        }

        ImageButton imgCl = findViewById(R.id.imbCl);
        imgCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT); /*Cria Intent*/
                photoPickerIntent.setType("image/*"); /*Seta o tipo*/
                startActivityForResult(photoPickerIntent, PHOTO_PICKER_REQUEST);
            }
        });
        Button btnAddItem = findViewById(R.id.btnAddItem);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri photoSelected = vm.getSelectPhotoLocation();
                if (photoSelected == null) {
                    Toast.makeText(NewItemActivity.this, "É necessário selecionar uma imagem!", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etTitle = findViewById(R.id.etTitle);
                String title = etTitle.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir um título", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText etDesc = findViewById(R.id.etDesc);
                String description = etDesc.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(NewItemActivity.this, "É necessário inserir uma descrição", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent i = new Intent(); /*Cria instância de Intent*/
                i.setData(photoSelected); /*Seta a foto selecionada*/
                i.putExtra("title", title); /*Seta o título*/
                i.putExtra("description", description); /*Seta a descrição*/
                setResult(Activity.RESULT_OK, i); /*Resultado da activity*/
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PHOTO_PICKER_REQUEST) {
            if(resultCode == Activity.RESULT_OK) {
                Uri photoSelected = data.getData();
                ImageView imvfotoPreview = findViewById( R.id.imvPhotoPreview);
                imvfotoPreview.setImageURI(photoSelected);
                NewItemActivityViewModel vm = new ViewModelProvider( this).get( NewItemActivityViewModel.class );
                vm.setSelectPhotoLocation(photoSelected);
            }
        }
    }
}

