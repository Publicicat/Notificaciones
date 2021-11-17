package com.publicicat.pgram;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentThreePres implements FragmentThreeIntPres {

    private FragmentThreeInt fragmentThreeInt;
    private Context context;

    private ArrayList<ConstructorInternet> mascotasInternet;
    private String nombreMascota;

    public FragmentThreePres(FragmentThreeInt fragmentThreeInt, Context context){
        this.fragmentThreeInt = fragmentThreeInt;
        this.context = context;
        obtenerMascotaInternet();
    }


    /*Aquí hi ha d'haver una funció que al carregar-se el Fragment
    aguanti un valor string per passar-lo a obtenerMascotaInternet
    I que es pugui modificar des de l'activitat changeUser

    getNameFromActivity

    Aquesta ha de portar el valor des de la base de dades
    està declarada a la interfícice FragmentThreeIntPres
    public void getTokenId(int tknIdRetrieved) {
        DbConstructor dbC = new DbConstructor(context);
        tokenIdFromDb = dbC.getTokenId(tknIdRetrieved);
    }*/

    /*
    @Override
    public void getNameFromActivity(String val) {
        Constructor queryName = new Constructor();
        val = queryName.getName();
        obtenerMascotaInternet(val);
    }*/


    @Override
    public void obtenerMascotaInternet() {
        DbConstructor nameReturned = new DbConstructor(context);
        nombreMascota = nameReturned.obtenerNombreString();
        if ( nombreMascota != null) {
            switch(nombreMascota){
                case "Publicicat_":
                    //Definim un nou objecte de RestApiAdapter, que establirà connexió amb l'Api d'Instagram
                    //també des de l'arxiu RestApiAdapter es generarà una consulta
                    RestApiAdapter restApiAdapter = new RestApiAdapter();
                    //Construim aquí com volem serialitzades les dades del RestApiAdapter,
                    //les deserialitzem de JSON i serialitzem segons volem (tot configurat a l'arxiu RestApiAdapter
                    Gson gsonMascotaInternet = restApiAdapter.construyeGsonDesSerializadorMascotaInternet();
                    //Amb l'adaptador configurat, demanem que es connecti
                    RestApiEndpoints restApiEndpoints = restApiAdapter.establecerConexionRestApiInstragram(gsonMascotaInternet);
                    Call<RestApiMascotaResponse> restApiMascotaResponseCall = restApiEndpoints.getRecentMedia(); //demana tots els endpoints

                    restApiMascotaResponseCall.enqueue(new Callback<RestApiMascotaResponse>() {
                        @Override
                        public void onResponse(Call<RestApiMascotaResponse> call, Response<RestApiMascotaResponse> response) {
                            RestApiMascotaResponse restApiMascotaResponse = response.body();
                            mascotasInternet = restApiMascotaResponse.getMascotasInternet();
                            mostrarMascotaRV();
                        }

                        @Override
                        public void onFailure(Call<RestApiMascotaResponse> call, Throwable t) {
                            Toast.makeText(context, "Algo passó en la conexion, intenta de nuevo", Toast.LENGTH_SHORT).show();
                            Log.e("Falló la conexion", t.toString());
                        }
                    });
                    break;
                case "Publicicat_2":


                    break;
                default:

                    break;
            }
        } else {

        }

    }

    @Override
    public void mostrarMascotaRV() {
        fragmentThreeInt.initAdapterRV(fragmentThreeInt.crearAdaptador(mascotasInternet));
        fragmentThreeInt.generarGridLayour();
    }
}

