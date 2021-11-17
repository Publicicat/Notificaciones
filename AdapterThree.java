package com.publicicat.pgram;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterThree extends RecyclerView.Adapter<AdapterThree.MascotaViewHolder> {

    private static final String TAG = "FIREBASE_TOKEN";

    private static final String GATO_FISICO = "Gato Uno Físico"; //Enviado desde Físico eUHhmKr
    private static final String GATO_VIRTUAL = "Gato Dos Virtual"; //Enviado desde Virtual fHbmq

    ArrayList<ConstructorInternet> mascotaInternet;
    Activity activityThree;

    public AdapterThree(ArrayList<ConstructorInternet> mascotaInternet, Activity activityThree) {
        this.mascotaInternet = mascotaInternet;
        this.activityThree = activityThree;
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_internet, parent, false);
        return new MascotaViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        final ConstructorInternet mascotaItem = mascotaInternet.get(position);

        Picasso.with(activityThree)
                .load(mascotaItem.getPicInternet())
                .placeholder(R.drawable.footprint)
                .into(holder.cvivPicP);
        //DbConstructor dbC = new DbConstructor(activityThree);
        holder.cvtvVotesP.setText(String.valueOf(mascotaItem.getLikesInternet()));

        //[START on click for POST]
        holder.cvivPicP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activityThree, "You clicked " + mascotaItem.getFullNameInternet() + " pic", Toast.LENGTH_SHORT).show();
                // Get token
                // [START log_reg_token]
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(new OnCompleteListener<String>() {
                            @Override
                            public void onComplete(@NonNull Task<String> task) {
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                    return;
                                }

                                // Get new FCM registration token
                                String token = task.getResult();

                                //AFEGIR RESTAPI GCLOUD-HEROKU-FIREBASE
                                MyRestApiAdapter myRestApiAdapter = new MyRestApiAdapter();
                                MyRestApiEndpoints myRestApiEndpoints = myRestApiAdapter.establecerConexionRestAPI();
                                Call<MyRestApiConstructor> myRestApiConstructorCall = myRestApiEndpoints.registrarTokenID(token, GATO_VIRTUAL, GATO_FISICO);

                                myRestApiConstructorCall.enqueue(new Callback<MyRestApiConstructor>() {
                                    @Override
                                    public void onResponse(Call<MyRestApiConstructor> call, Response<MyRestApiConstructor> response) {
                                        //RECUERDA
                                        //nuestro callback y en la respuesta nosotros
                                        //you tenemos una clase que es idéntica a la respuesta que nos está dando.
                                        //Entonces no necesitamos generar un deserializador,
                                        //por lo tanto recuerda que en el objeto response a través del método body
                                        //obtengo la data y esa data la asigno:
                                        /*
                                        MyRestApiConstructor fotoCargadaDesdeFbase = response.body();

                                        //public void toqueFoto(View v){
                                        Log.d("TOQUE_FOTO", "true");
                                        //MyRestApiConstructor fotoCargadaDesdeFbase = new MyRestApiConstructor();
                                        MyRestApiAdapter myRestApiAdapterGET = new MyRestApiAdapter();
                                        MyRestApiEndpoints myRestApiEndpointsGET = myRestApiAdapterGET.establecerConexionRestAPI();
                                        Call<MyRestApiConstructor> myRestApiConstructorCallGET = myRestApiEndpointsGET.toqueFoto(fotoCargadaDesdeFbase.getId(), fotoCargadaDesdeFbase.getOtrogato());
                                        myRestApiConstructorCallGET.enqueue(new Callback<MyRestApiConstructor>() {
                                            @Override
                                            public void onResponse(Call<MyRestApiConstructor> call, Response<MyRestApiConstructor> response) {
                                                MyRestApiConstructor myRestApiConstructorGET = response.body();
                                                Log.d("ID_FIREBASE", myRestApiConstructorGET.getId());
                                                //Log.d("TOKEN_FIREBASE", myRestApiConstructorGET.getToken());
                                                Log.d("GATO_FIREBASE", myRestApiConstructorGET.getOtrogato());
                                            }

                                            @Override
                                            public void onFailure(Call<MyRestApiConstructor> call, Throwable t) {

                                            }
                                        });
                                        //} [End toqueFoto]
                                         */
                                    }

                                    @Override
                                    public void onFailure(Call<MyRestApiConstructor> call, Throwable t) {

                                    }

                                });
                                // Log and toast string formatted to %s characters %floats and %d digits
                                //Gangnus
                                //https://stackoverflow.com/questions/4253328/getstring-outside-of-a-context-or-activity/8765766
                                //String msg;
                                //msg = Resources.getSystem().getString(R.string.msg_token_fmt, token);
                                //Log.d(TAG, msg);
                                Toast.makeText(activityThree, token, Toast.LENGTH_SHORT).show();



                            }

                        });
                // [END log_reg_token]

            }
        }); //[END on click for POST]

    }



    @Override
    public int getItemCount() {
        return mascotaInternet.size();
    }

    //Class inside class
    public static class MascotaViewHolder extends RecyclerView.ViewHolder {

        private final ImageView cvivPicP;
        private final TextView cvtvVotesP;

        public MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            cvivPicP = itemView.findViewById(R.id.cv_iv_picP);
            cvtvVotesP = itemView.findViewById(R.id.cv_tv_votesP);
        }

    }
}

