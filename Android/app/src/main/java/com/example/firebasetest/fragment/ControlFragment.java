package com.example.firebasetest.fragment;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.firebasetest.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ControlFragment extends Fragment {
    boolean check = false;

    private boolean nhietdo_high = true;
    private boolean nhietdo_low = true;
    private boolean doamphong_low = true;
    private boolean doamdat_low = true;

    private boolean lampcheck = true;
    private boolean fan1check = true;
    private boolean fan2check = true;
    private boolean pumpcheck = true;
    private boolean mistcheck = true;


    public ControlFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);

        final SeekBar seek_temp_high = view.findViewById(R.id.see1);
        final SeekBar seek_temp_low = view.findViewById(R.id.see2);
        final SeekBar seek_hum = view.findViewById(R.id.see3);
        final SeekBar seek_soil = view.findViewById(R.id.see4);

        //Set alarm pump1
        final EditText hourpum1 = view.findViewById(R.id.pumphourlan1);
        final EditText minpum1 = view.findViewById(R.id.pumpmintuelan1);
        final Button setpump1 = view.findViewById(R.id.btnsetpumplan1);
        final Switch swpump1 = view.findViewById(R.id.swpumplan1);

        //Set alarm lamp1
        final EditText hourlamp1 = view.findViewById(R.id.lamphourlan1);
        final EditText minlamp1 = view.findViewById(R.id.lampmintuelan1);
        final Button setlamp1 = view.findViewById(R.id.btnsetlamplan1);
        final Switch swlamp1 = view.findViewById(R.id.swlamplan1);

        //Set alarm fan1
        final EditText hourfan1 = view.findViewById(R.id.fanhourlan1);
        final EditText minfan1 = view.findViewById(R.id.fanmintuelan1);
        final Button setfan1 = view.findViewById(R.id.btnsetfanlan1);
        final Switch swfan1 = view.findViewById(R.id.swfanlan1);

        //Set alarm fan21
        final EditText hourfan21 = view.findViewById(R.id.fan2hourlan1);
        final EditText minfan21 = view.findViewById(R.id.fan2mintuelan1);
        final Button setfan21 = view.findViewById(R.id.btnsetfan2lan1);
        final Switch swfan21 = view.findViewById(R.id.swfan2lan1);

        //Set alarm mis1
        final EditText hourmis1 = view.findViewById(R.id.mishourlan1);
        final EditText minmis1 = view.findViewById(R.id.mismintuelan1);
        final Button setmis1 = view.findViewById(R.id.btnsetmislan1);
        final Switch swmis1 = view.findViewById(R.id.swmislan1);

        //Set alarm pump2
        final EditText hourpum2 = view.findViewById(R.id.pumphourlan2);
        final EditText minpum2 = view.findViewById(R.id.pumpmintuelan2);
        final Button setpump2 = view.findViewById(R.id.btnsetpumplan2);
        final Switch swpump2 = view.findViewById(R.id.swpumplan2);

        //Set alarm lamp2
        final EditText hourlamp2 = view.findViewById(R.id.lamphourlan2);
        final EditText minlamp2 = view.findViewById(R.id.lampmintuelan2);
        final Button setlamp2 = view.findViewById(R.id.btnsetlamplan2);
        final Switch swlamp2 = view.findViewById(R.id.swlamplan2);

        //Set alarm fan2
        final EditText hourfan2 = view.findViewById(R.id.fanhourlan2);
        final EditText minfan2 = view.findViewById(R.id.fanmintuelan2);
        final Button setfan2 = view.findViewById(R.id.btnsetfanlan2);
        final Switch swfan2 = view.findViewById(R.id.swfanlan2);

        //Set alarm fan22
        final EditText hourfan22 = view.findViewById(R.id.fan2hourlan2);
        final EditText minfan22 = view.findViewById(R.id.fan2mintuelan2);
        final Button setfan22 = view.findViewById(R.id.btnsetfan2lan2);
        final Switch swfan22 = view.findViewById(R.id.swfan2lan2);

        //Set alarm mis2
        final EditText hourmis2 = view.findViewById(R.id.mishourlan2);
        final EditText minmis2 = view.findViewById(R.id.mismintuelan2);
        final Button setmis2 = view.findViewById(R.id.btnsetmislan2);
        final Switch swmis2 = view.findViewById(R.id.swmislan2);


        final TextView nn1 = view.findViewById(R.id.den1);
        final TextView dd1 = view.findViewById(R.id.phunsuong1);
        final TextView dd2 = view.findViewById(R.id.bomnuoc1);


        final ImageView imageView = view.findViewById(R.id.imageView6);
        final ImageView fan11 = view.findViewById(R.id.fan1);
        final ImageView fan22 = view.findViewById(R.id.fan2);
        final ImageView pump = view.findViewById(R.id.pumptr);
        final ImageView mist = view.findViewById(R.id.mist);


        final TextView nhietdo1 = view.findViewById(R.id.text1);
        final TextView nhietdo2 = view.findViewById(R.id.text2);
        final TextView doamphong1 = view.findViewById(R.id.text3);
        final TextView doamdat1 = view.findViewById(R.id.text4);

        final Switch simpleSwitch_autoMode = view.findViewById(R.id.switch_AutoMode);
        final Switch simpleSwitch_manualMode = view.findViewById(R.id.switch_ManualMode);
        final Switch simpleSwitch_alarm = view.findViewById(R.id.swalarm);

        final Switch simpleSwitch2 = view.findViewById(R.id.switch2);
        final Switch simpleSwitch3 = view.findViewById(R.id.switch3);
        final Switch simpleSwitch4 = view.findViewById(R.id.switch4);
        final Switch simpleSwitch5 = view.findViewById(R.id.switch5);
        final Switch simpleSwitch9 = view.findViewById(R.id.switch9);

        final Firebase mRe2 = new Firebase("https://iot-phong-cn.firebaseio.com/S2_Lamp");
        final Firebase mRe3 = new Firebase("https://iot-phong-cn.firebaseio.com/S3_Fan");
        final Firebase mRe4 = new Firebase("https://iot-phong-cn.firebaseio.com/S4_Pump");
        final Firebase mRe5 = new Firebase("https://iot-phong-cn.firebaseio.com/S5_Mis");
        final Firebase fan2 = new Firebase("https://iot-phong-cn.firebaseio.com/S6_Fan2");


        final Firebase SwAuto = new Firebase("https://iot-phong-cn.firebaseio.com/S1_Auto");
        final Firebase SwManual = new Firebase("https://iot-phong-cn.firebaseio.com/S1_Manual");
        final Firebase SwAlarm = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Status");


        final Firebase mRe7 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Auto/Temp_high");
        final Firebase mRe8 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Auto/Temp_low");
        final Firebase mRe9 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Auto/Hum");
        final Firebase mRe10 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Auto/Soil");

        final Firebase mRe11 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/dht/temp");
        final Firebase mRe12 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/dht/hum");
        final Firebase mRe13 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/sensor/soil_hum");

        //Link firebase pump1
        final Firebase mhourpum1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Pump/AmPump1/Hour");
        final Firebase mminutepump1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Pump/AmPump1/Minute");
        final Firebase mstatuspump1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Pump/AmPump1/Status");

        //Link firebase lamp1
        final Firebase mhourlamp1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Lamp/AmLamp1/Hour");
        final Firebase mminutelamp1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Lamp/AmLamp1/Minute");
        final Firebase mstatuslamp1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Lamp/AmLamp1/Status");

        //Link firebase fan1
        final Firebase mhourfan1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan/AmFan1/Hour");
        final Firebase mminutefan1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan/AmFan1/Minute");
        final Firebase mstatusfan1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan/AmFan1/Status");

        //Link firebase fan2
        final Firebase mhourfan21 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan2/AmFan21/Hour");
        final Firebase mminutefan21 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan2/AmFan21/Minute");
        final Firebase mstatusfan21 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan2/AmFan21/Status");

        //Link firebase mis1
        final Firebase mhourmis1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Mis/AmMis1/Hour");
        final Firebase mminutemis1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Mis/AmMis1/Minute");
        final Firebase mstatusmis1 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Mis/AmMis1/Status");

        //Link firebase pump2
        final Firebase mhourpum2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Pump/AmPump2/Hour");
        final Firebase mminutepump2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Pump/AmPump2/Minute");
        final Firebase mstatuspump2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Pump/AmPump2/Status");

        //Link firebase lamp2
        final Firebase mhourlamp2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Lamp/AmLamp2/Hour");
        final Firebase mminutelamp2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Lamp/AmLamp2/Minute");
        final Firebase mstatuslamp2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Lamp/AmLamp2/Status");

        //Link firebase fan2
        final Firebase mhourfan2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan/AmFan2/Hour");
        final Firebase mminutefan2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan/AmFan2/Minute");
        final Firebase mstatusfan2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan/AmFan2/Status");

        //Link firebase fan22
        final Firebase mhourfan22 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan2/AmFan22/Hour");
        final Firebase mminutefan22 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan2/AmFan22/Minute");
        final Firebase mstatusfan22 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Fan2/AmFan22/Status");

        //Link firebase mis2
        final Firebase mhourmis2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Mis/AmMis2/Hour");
        final Firebase mminutemis2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Mis/AmMis2/Minute");
        final Firebase mstatusmis2 = new Firebase("https://iot-phong-cn.firebaseio.com/Smart_Farm1/Alarm/Mis/AmMis2/Status");


        mstatusmis2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swmis2.setChecked(true);
                } else if (value.equals("0")) {
                    swmis2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutemis2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minmis2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourmis2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourmis2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setmis2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Mis/AmMis2/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Mis/AmMis2/Minute");

                pumphour1.setValue(hourmis2.getText().toString().trim());

                pumpminute1.setValue(minmis2.getText().toString().trim());

            }
        });
        swmis2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Mis/AmMis2/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Mis/AmMis2/Status");
                    myref.setValue("0");

                }
            }
        });


        mstatusmis1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swmis1.setChecked(true);
                } else if (value.equals("0")) {
                    swmis1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutemis1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minmis1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourmis1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourmis1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setmis1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Mis/AmMis1/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Mis/AmMis1/Minute");

                pumphour1.setValue(hourmis1.getText().toString().trim());

                pumpminute1.setValue(minmis1.getText().toString().trim());

            }
        });
        swmis1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Mis/AmMis1/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Mis/AmMis1/Status");
                    myref.setValue("0");

                }
            }
        });


        mstatusfan22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swfan22.setChecked(true);
                } else if (value.equals("0")) {
                    swfan22.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutefan22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minfan22.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourfan22.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourfan22.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setfan22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan22/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan22/Minute");

                pumphour1.setValue(hourfan22.getText().toString().trim());

                pumpminute1.setValue(minfan22.getText().toString().trim());

            }
        });
        swfan22.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan22/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan22/Status");
                    myref.setValue("0");

                }
            }
        });

        mstatusfan21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swfan21.setChecked(true);
                } else if (value.equals("0")) {
                    swfan21.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutefan21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minfan21.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourfan21.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourfan21.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setfan21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan21/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan21/Minute");

                pumphour1.setValue(hourfan21.getText().toString().trim());

                pumpminute1.setValue(minfan21.getText().toString().trim());

            }
        });
        swfan21.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan21/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Fan2/AmFan21/Status");
                    myref.setValue("0");

                }
            }
        });

        mstatusfan1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swfan1.setChecked(true);
                } else if (value.equals("0")) {
                    swfan1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutefan1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minfan1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourfan1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourfan1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setfan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Fan/AmFan1/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Fan/AmFan1/Minute");

                pumphour1.setValue(hourfan1.getText().toString().trim());

                pumpminute1.setValue(minfan1.getText().toString().trim());

            }
        });
        swfan1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Fan/AmFan1/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Fan/AmFan1/Status");
                    myref.setValue("0");

                }
            }
        });

        mstatusfan2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swfan2.setChecked(true);
                } else if (value.equals("0")) {
                    swfan2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutefan2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minfan2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourfan2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourfan2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setfan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Fan/AmFan2/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Fan/AmFan2/Minute");

                pumphour1.setValue(hourfan2.getText().toString().trim());

                pumpminute1.setValue(minfan2.getText().toString().trim());

            }
        });
        swfan2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Fan/AmFan2/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Fan/AmFan2/Status");
                    myref.setValue("0");

                }
            }
        });

        mstatuslamp2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swlamp2.setChecked(true);
                } else if (value.equals("0")) {
                    swlamp2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutelamp2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minlamp2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourlamp2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourlamp2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setlamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp2/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp2/Minute");

                pumphour1.setValue(hourlamp2.getText().toString().trim());

                pumpminute1.setValue(minlamp2.getText().toString().trim());

            }
        });
        swlamp2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp2/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp2/Status");
                    myref.setValue("0");

                }
            }
        });
        mstatuslamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swlamp1.setChecked(true);
                } else if (value.equals("0")) {
                    swlamp1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutelamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minlamp1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourlamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourlamp1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setlamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp1/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp1/Minute");

                pumphour1.setValue(hourlamp1.getText().toString().trim());

                pumpminute1.setValue(minlamp1.getText().toString().trim());

            }
        });
        swlamp1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp1/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Lamp/AmLamp1/Status");
                    myref.setValue("0");

                }
            }
        });

        mstatuspump2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swpump2.setChecked(true);
                } else if (value.equals("0")) {
                    swpump2.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutepump2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minpum2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourpum2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourpum2.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setpump2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Pump/AmPump2/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Pump/AmPump2/Minute");

                pumphour1.setValue(hourpum2.getText().toString().trim());

                pumpminute1.setValue(minpum2.getText().toString().trim());

            }
        });
        swpump2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Pump/AmPump2/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Pump/AmPump2/Status");
                    myref.setValue("0");

                }
            }
        });

        mstatuspump1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    swpump1.setChecked(true);
                } else if (value.equals("0")) {
                    swpump1.setChecked(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mminutepump1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                minpum1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mhourpum1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                hourpum1.setText(Value);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        setpump1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference pumphour1 = database.getReference("Smart_Farm1/Alarm/Pump/AmPump1/Hour");
                DatabaseReference pumpminute1 = database.getReference("Smart_Farm1/Alarm/Pump/AmPump1/Minute");

                pumphour1.setValue(hourpum1.getText().toString().trim());

                pumpminute1.setValue(minpum1.getText().toString().trim());

            }
        });
        swpump1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("Smart_Farm1/Alarm/Pump/AmPump1/Status");
                    fan2.setValue("1");
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Pump/AmPump1/Status");
                    myref.setValue("0");

                }
            }
        });


        fan2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                if(fan2check){
                    if (Value.equals("1")) {
                        simpleSwitch9.setChecked(true);
                    } else if (Value.equals("0")) {
                        simpleSwitch9.setChecked(false);
                    }
                    fan2check = false;

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRe11.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                nn1.setText("Temp:" + value + "°C");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRe12.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                dd1.setText("Soil:" + value + "%");
            }


            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mRe13.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                dd2.setText("Humi:" + value + "%");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        mRe7.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = dataSnapshot.getValue(String.class);

                int i = Integer.parseInt(a.replaceAll("[\\D]", ""));
                if (nhietdo_high) {
                    seek_temp_high.setProgress(i);
                    nhietdo_high = false;
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        mRe8.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = dataSnapshot.getValue(String.class);
                int i = Integer.parseInt(a.replaceAll("[\\D]", ""));

                if (nhietdo_low) {
                    seek_temp_low.setProgress(i);
                    nhietdo_low = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        mRe9.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = dataSnapshot.getValue(String.class);
                int i = Integer.parseInt(a.replaceAll("[\\D]", ""));
                if (doamphong_low) {
                    seek_hum.setProgress(i);
                    doamphong_low = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        mRe10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String a = dataSnapshot.getValue(String.class);
                int i = Integer.parseInt(a.replaceAll("[\\D]", ""));
                if (doamdat_low) {
                    seek_soil.setProgress(i);
                    doamdat_low = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

        SwAuto.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    simpleSwitch_autoMode.setChecked(true);
                } else if (value.equals("0")) {
                    simpleSwitch_autoMode.setChecked(false);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        SwManual.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    simpleSwitch_manualMode.setChecked(true);
                } else if (value.equals("0")) {
                    simpleSwitch_manualMode.setChecked(false);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        SwAlarm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (value.equals("1")) {
                    simpleSwitch_alarm.setChecked(true);
                } else if (value.equals("0")) {
                    simpleSwitch_alarm.setChecked(false);

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRe2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                if (lampcheck) {
                    if (Value.equals("1")) {
                        simpleSwitch2.setChecked(true);
                    } else if (Value.equals("0")) {
                        simpleSwitch2.setChecked(false);
                    }
                    lampcheck = false;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRe3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                if (fan1check){
                    if (Value.equals("1")) {
                        simpleSwitch3.setChecked(true);
                    } else if (Value.equals("0")) {
                        simpleSwitch3.setChecked(false);
                    }
                    fan1check = false;

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRe4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                if (pumpcheck){
                    if (Value.equals("1")) {
                        simpleSwitch4.setChecked(true);
                    } else if (Value.equals("0")) {
                        simpleSwitch4.setChecked(false);
                    }
                    pumpcheck = false;

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mRe5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);
                if (mistcheck){
                    if (Value.equals("1")) {
                        simpleSwitch5.setChecked(true);
                    } else if (Value.equals("0")) {
                        simpleSwitch5.setChecked(false);
                    }
                    mistcheck = false;

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        seek_temp_high.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, final int progress, boolean fromUser) {


                nhietdo1.setText(progress + "°C");
                String abc = String.valueOf(progress);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("Smart_Farm1/Auto/Temp_high");
                myref.setValue(abc);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_temp_low.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nhietdo2.setText(progress + "°C");
                String abc = String.valueOf(progress);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("Smart_Farm1/Auto/Temp_low");
                myref.setValue(abc);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_hum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doamphong1.setText(progress + "%");
                String abc = String.valueOf(progress);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("Smart_Farm1/Auto/Hum");
                myref.setValue(abc);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        seek_soil.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                doamdat1.setText(progress + "%");

                String abc = String.valueOf(progress);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref = database.getReference("Smart_Farm1/Auto/Soil");
                myref.setValue(abc);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        simpleSwitch9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransitionDrawable drawable = (TransitionDrawable) fan22.getDrawable();

                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference fan2 = database.getReference("S6_Fan2");
                    fan2.setValue("1");
                    drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S6_Fan2");
                    myref.setValue("0");
                    drawable.reverseTransition(200);

                }
            }
        });
        simpleSwitch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransitionDrawable drawable = (TransitionDrawable) imageView.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S2_Lamp");
                    myref.setValue("1");
                    drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S2_Lamp");
                    myref.setValue("0");

                    drawable.reverseTransition(200);
                }
            }
        });


        simpleSwitch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransitionDrawable drawable = (TransitionDrawable) fan11.getDrawable();

                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S3_Fan");
                    myref.setValue("1");
                    drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S3_Fan");
                    myref.setValue("0");
                    drawable.reverseTransition(200);

                }
            }
        });
        simpleSwitch4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransitionDrawable drawable = (TransitionDrawable) pump.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S4_Pump");
                    myref.setValue("1");
                    drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S4_Pump");
                    myref.setValue("0");
                    drawable.reverseTransition(200);
                }
            }
        });
        simpleSwitch5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                TransitionDrawable drawable = (TransitionDrawable) mist.getDrawable();
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S5_Mis");
                    myref.setValue("1");
                    drawable.startTransition(800);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S5_Mis");
                    myref.setValue("0");
                    drawable.reverseTransition(200);
                }
            }
        });
        simpleSwitch_autoMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S1_Auto");
                    myref.setValue("1");
                    simpleSwitch_alarm.setChecked(false);
                    simpleSwitch_manualMode.setChecked(false);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S1_Auto");
                    myref.setValue("0");
                }
            }
        });

        simpleSwitch_manualMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S1_Manual");
                    myref.setValue("1");
                    simpleSwitch_alarm.setChecked(false);
                    simpleSwitch_autoMode.setChecked(false);
                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("S1_Manual");
                    myref.setValue("0");
                }
            }
        });
        simpleSwitch_alarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Status");
                    myref.setValue("1");
                    simpleSwitch_autoMode.setChecked(false);
                    simpleSwitch_manualMode.setChecked(false);

                } else {
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref = database.getReference("Smart_Farm1/Alarm/Status");
                    myref.setValue("0");
                }
            }
        });


        return view;
    }


}
