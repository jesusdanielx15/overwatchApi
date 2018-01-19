package cl.idatum.overwatchapi;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import cl.idatum.overwatchapi.api.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RestAdapter.OnRecyclerViewItemClickListener {

    private RecyclerView recyclerView;
    private String TAG = "MainActivity.class";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        makeCall();
    }

    /**
     * Peticion http a la api de Overwatch
     */
    private void makeCall() {
        Call<JsonObject> call = ApiUtils.getClient().getHeros();

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JsonObject object = response.body();
                    JsonArray jsonArray = object.getAsJsonArray("data");
                    ArrayList<OverwatchHeros> arrayList = new ArrayList();

                    //Recorro el arreglo de jsons que es cada Heroe.
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObjectHeros = jsonArray.get(i).getAsJsonObject();
                        OverwatchHeros overwatchHeros = new OverwatchHeros();

                        overwatchHeros.setName(jsonObjectHeros.get("name").toString());
                        overwatchHeros.setDescription(jsonObjectHeros.get("description").toString());
                        overwatchHeros.setHealth(jsonObjectHeros.get("health").toString());
                        overwatchHeros.setArmour(jsonObjectHeros.get("armour").toString());
                        overwatchHeros.setShield(jsonObjectHeros.get("shield").toString());
                        overwatchHeros.setAffiliation(jsonObjectHeros.get("affiliation").toString());
                        overwatchHeros.setAge(jsonObjectHeros.get("age").toString());

                        arrayList.add(overwatchHeros);
                    }
                    RestAdapter adapter = new RestAdapter(arrayList);
                    recyclerView.setAdapter(adapter);
                    adapter.setOnItemClickListener(MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Intente de nuevo", Toast.LENGTH_LONG).show();
                Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void onRecyclerViewItemClicked(String name, String description, String health, String armour, String shield, String age, String affiliation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Details");
        //Vistas
        builder.setMessage("Name: " + name + "\n"
                + "Health: " + health + "\n"
                + "Armour: " + armour + "\n"
                + "Shield: " + shield + "\n"
                + "Age: " + age + "\n"
                + "Affiliation: " + affiliation + "\n" + "\n"
                + "Description: " + description + "\n");

        builder.setPositiveButton("OK", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setLayout(600, 700);
        alertDialog.show();
    }
}
