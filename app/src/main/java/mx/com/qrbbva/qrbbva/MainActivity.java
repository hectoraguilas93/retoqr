package mx.com.qrbbva.qrbbva;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import mx.com.qrbbva.qrbbva.modelbank.modelperson;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView  imageView;
    Button transfer,backTransaction;
    LinearLayout principal,codeqr;
    EditText Motivodepago,clabe_tarjeta,refbenefi,montoTransfer;
    Spinner claveCuenta;
    public String typeCount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        transfer = findViewById(R.id.transfer);
        imageView = findViewById(R.id.image_view);
        principal = findViewById(R.id.principal);
        codeqr = findViewById(R.id.codeqr);

        // formulario para captura de datos

        Motivodepago = findViewById(R.id.Motivodepago);
        clabe_tarjeta = findViewById(R.id.clabe_tarjeta);
        claveCuenta = findViewById(R.id.claveCuenta);

        refbenefi = findViewById(R.id.refbenefi);
        montoTransfer = findViewById(R.id.montoTransfer);
        backTransaction = findViewById(R.id.backTransaction);
      //  FirebaseApp.initializeApp(MainActivity.this);

        transfer.setOnClickListener(this);
        backTransaction.setOnClickListener(this);

        claveCuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    typeCount  = "CL";
                }else{
                    typeCount =  "TC";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) { } });
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.transfer:
                codeqr.setVisibility(View.VISIBLE);
                principal.setVisibility(View.GONE);

                getInformationandCreateQr();
                break;
            case R.id.backTransaction:
                codeqr.setVisibility(View.GONE);
                principal.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getInformationandCreateQr() {

        try {
            JSONArray persona = new JSONArray();
            persona.put(new modelperson().persona("alias",Motivodepago.getText().toString()));
            persona.put(new modelperson().persona("cl",clabe_tarjeta.getText().toString() ));
            persona.put(new modelperson().persona("type",typeCount));
            persona.put(new modelperson().persona("refn",""));
            persona.put(new modelperson().persona("refa",refbenefi.getText().toString()));
            persona.put(new modelperson().persona("amount",montoTransfer.getText().toString()));
            persona.put(new modelperson().persona("bank","00012"));
            persona.put(new modelperson().persona("country","MX"));
            persona.put(new modelperson().persona("currency", "MXN"));
            JSONObject object = new JSONObject();
            object.put("ot", "0001");
            object.put("dOp", persona );
            BitMatrix bitMatrix = new MultiFormatWriter().encode(object.toString(), BarcodeFormat.QR_CODE,100,100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);


        } catch (JSONException e) {
            e.printStackTrace();
        }catch (WriterException e) {
            e.printStackTrace();
        }

    }
}

