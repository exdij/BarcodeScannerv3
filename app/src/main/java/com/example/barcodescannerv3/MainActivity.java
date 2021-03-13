package com.example.barcodescannerv3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


public class MainActivity extends AppCompatActivity {
    public String EAN = null;
    private EditText EAN_editText;
    private EditText name_editText;
    private EditText ip_editText;
    private EditText quantityOld_editText;
    private EditText quantityNew_editText;
    private ItemViewModel model_item;
    RESTMethods rest = new RESTMethods();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        String ip = intent.getStringExtra("ip");
        EAN_editText = findViewById(R.id.EAN_editText);
        name_editText = findViewById(R.id.name_editText);
        quantityOld_editText = findViewById(R.id.quantityOld_editText);
        quantityNew_editText = findViewById(R.id.quantityNew_editText);
        ip_editText = findViewById(R.id.ip_editText);
        model_item = new ViewModelProvider(this).get(ItemViewModel.class);

        final Observer<Item> itemObserver = new Observer<Item>() {
            @Override
            public void onChanged(Item item) {
                EAN_editText.setText(item.getEan());
                name_editText.setText(item.getName());
                quantityOld_editText.setText(item.getOld_amount());
                quantityNew_editText.setText(item.getNew_amount());
            }
        };

        model_item.getMutableItem().observe(this, itemObserver);

        findViewById(R.id.scan_button).setOnClickListener(v -> scanBtnClicked());
        findViewById(R.id.put_button).setOnClickListener(v -> putBtnClicked());
        findViewById(R.id.same_button).setOnClickListener(v -> sameBtnClicked());

    }

        private void scanBtnClicked(){
            IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
            integrator.setPrompt("Skanuj kod kreskowy");
            integrator.setOrientationLocked(false);
            integrator.initiateScan();
        }
        private void putBtnClicked(){
            Item item = new Item();
            item.setEan(EAN_editText.getText().toString());
            item.setName(name_editText.getText().toString());
            item.setNew_amount(quantityNew_editText.getText().toString());
            item.setOld_amount(quantityOld_editText.getText().toString());
            rest.put(item, ip_editText.getText().toString());
            scanBtnClicked();
        }
        private void sameBtnClicked(){
            Item item = new Item();
            item.setEan(EAN_editText.getText().toString());
            item.setName(name_editText.getText().toString());
            item.setNew_amount(quantityOld_editText.getText().toString());
            item.setOld_amount(quantityOld_editText.getText().toString());
            rest.put(item, ip_editText.getText().toString());
            scanBtnClicked();
        }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                EAN = result.getContents();
                rest.request(EAN, model_item, ip_editText.getText().toString());

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }



}