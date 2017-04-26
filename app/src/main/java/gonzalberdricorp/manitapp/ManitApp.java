package gonzalberdricorp.manitapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ManitApp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manit_app);
    }
    public void profesional (View v){
        this.startActivity(new Intent(this,Registro.class));
    }

    public void buscar (View v){
        this.startActivity(new Intent(this,Registro.class));
    }
}
