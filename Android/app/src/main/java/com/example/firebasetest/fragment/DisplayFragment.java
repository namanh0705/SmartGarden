package com.example.firebasetest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.firebasetest.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Objects;

import az.plainpie.PieView;
import az.plainpie.animation.PieAngleAnimation;
import pl.pawelkleczkowski.customgauge.CustomGauge;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {
    private ImageView imageView;
    private ImageView btnfan;
    private ImageView btnpump;
    private ImageView btnlamp;
    private ImageView btnmis;
    private ImageView btnfan2;

    private ImageView btnfan11;
    private ImageView btnfan22;
    private ImageView btnpump11;
    private ImageView btnmist1;
    private ImageView btnlamp11;


    private TextView anhsang;
    private PieView pieView_hum;
    private PieView pieView_soil;
    private CustomGauge customGauge;
    private TextView textView;
    public DisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display, container, false);
        customGauge = view.findViewById(R.id.gauge2);
        textView = view.findViewById(R.id.text1);
        pieView_hum = view.findViewById(R.id.pieView_hum);
        pieView_soil = view.findViewById(R.id.pieView_soil);
        imageView = view.findViewById(R.id.image);
        anhsang = view.findViewById(R.id.anhsang);
        btnfan = view.findViewById(R.id.fan_status);
        btnlamp = view.findViewById(R.id.lamp_status);
        btnpump = view.findViewById(R.id.pump_status);
        btnmis = view.findViewById(R.id.mis_status);
        btnfan2 = view.findViewById(R.id.fan2_status);

        btnfan11 = view.findViewById(R.id.btnfan11);
        btnfan22 = view.findViewById(R.id.btnfan22);
        btnpump11 = view.findViewById(R.id.btnpump1);
        btnmist1 = view.findViewById(R.id.btnmist1);
        btnlamp11 = view.findViewById(R.id.btnlamp1);


        Firebase.setAndroidContext(Objects.requireNonNull(getContext()));

        final Firebase mRef1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/dht/temp");
        Firebase mRef2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/light");
        Firebase mRef3 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/soil_hum");
        Firebase mRef4 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/dht/hum");


        Firebase btnfan1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Status/Fan");
        Firebase btnlamp1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Status/Lamp");
        Firebase btnpump1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Status/Pump");
        Firebase btnmis1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Status/Mis");
        Firebase btnfan21 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Status/Fan2");




        pieView_hum.setPercentageBackgroundColor(getResources().getColor(R.color.bluehigh));
//        pieView_hum.setMainBackgroundColor(getResources().getColor(R.color.white));


        pieView_soil.setPercentageBackgroundColor(getResources().getColor(R.color.browhigh));
//        pieView_soil.setMainBackgroundColor(getResources().getColor(R.color.white));


        btnfan21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnfan22.setImageResource(R.drawable.fan);
                    btnfan2.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnfan22.setImageResource(R.drawable.fanoff);
                    btnfan2.setImageResource(R.drawable.btntat);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        btnfan1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnfan11.setImageResource(R.drawable.fan);
                    btnfan.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnfan11.setImageResource(R.drawable.fanoff);
                    btnfan.setImageResource(R.drawable.btntat);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnlamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnlamp11.setImageResource(R.drawable.lighon2);
                    btnlamp.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnlamp11.setImageResource(R.drawable.light_off1);
                    btnlamp.setImageResource(R.drawable.btntat);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnpump1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnpump11.setImageResource(R.drawable.pump2);
                    btnpump.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnpump11.setImageResource(R.drawable.pumpoff);
                    btnpump.setImageResource(R.drawable.btntat);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnmis1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    btnmist1.setImageResource(R.drawable.miston);
                    btnmis.setImageResource(R.drawable.btnbat);
                } else if (value.equals("0")) {
                    btnmis.setImageResource(R.drawable.btntat);
                    btnmist1.setImageResource(R.drawable.mistoff);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mRef4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                float i = Float.parseFloat(value);
                pieView_hum.setPercentage(i);
                pieView_hum.setPieInnerPadding(30);
                pieView_hum.setPercentageTextSize(20);
                PieAngleAnimation animation = new PieAngleAnimation(pieView_hum);
                animation.setDuration(2000); //This is the duration of the animation in millis
                pieView_hum.startAnimation(animation);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                anhsang.setText(value);
                if (anhsang.getText().toString().equals("Trời sáng")) {
                    imageView.setImageResource(R.drawable.sun);
                } else if (anhsang.getText().toString().equals("Trời tối")) {
                    imageView.setImageResource(R.drawable.moon);
                }


            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRef1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                textView.setText(value+"°C");
                int i=Integer.parseInt(value.replaceAll("[\\D]", ""));
                customGauge.setValue(i*10);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRef3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                float i = Float.parseFloat(value);
                pieView_soil.setPieInnerPadding(30);
                pieView_soil.setPercentageTextSize(20);
                pieView_soil.setPercentage(i);
                PieAngleAnimation animation = new PieAngleAnimation(pieView_soil);
                animation.setDuration(2000); //This is the duration of the animation in millis
                pieView_soil.startAnimation(animation);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        return view;
    }

}
