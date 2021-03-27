/* Sending Sensor Data to Firebase Database by CircuitDigest(www.circuitdigest.com) */

#include <ESP8266WiFi.h>                                                    // esp8266 library
#include <FirebaseArduino.h>                                                // firebase library
#include <DHT.h>                                                            // dht11 temperature and humidity sensor library
#include <WiFiUdp.h>
#include <NTPtimeESP.h>

NTPtime NTPch("ch.pool.ntp.org");   // Server NTP

WiFiUDP Udp;


#define   FIREBASE_HOST "............."                          // the project name address from firebase id
#define   FIREBASE_AUTH "..................."            // the secret key generated from firebase

//#define   WIFI_SSID "............"                                             // input your home or public wifi name 
//#define   WIFI_PASSWORD "..........."                                    //password of wifi ssid
 
#define   DHTPIN        2       // Pin of DHT
#define   Lam_Sensor   16       // Lamp sensor
#define   Soil_Sensor  A0       // doc cam bien do am dat
                                                      
#define   Lamp         13         // Relay 1
#define   PUMPW_PIN    12         // Relay 2
#define   Fan           4           // Relay 3
#define   Mis          14            // Relay 4
#define   Fan2         5
#define   DHTTYPE DHT11         // select dht type as DHT 11 or DHT22


int      temp_high;
int      temp_low;
int      hum_low;
int      soilhum_low;

int     valauto;
int     val;
int     val1;
int     val2;
int     val3;
int     val4;
int     val5;
//Real time o'clock
int     minuterealtime;
int     hourrealtime;

// set variable Alarm
int     alarm;
//set variable Pump1
int     pump1status;
int     pump1hour;
int     pump1mintue;

//set variable Pump2
int     pump2status;
int     pump2hour;
int     pump2mintue;

//set variable Lamp1
int     lamp1status;
int     lamp1hour;
int     lamp1mintue;

//set variable Lamp2
int     lamp2status;
int     lamp2hour;
int     lamp2mintue;

//set variable Mis1
int     mis1status;
int     mis1hour;
int     mis1mintue;

//set variable Mis2
int     mis2status;
int     mis2hour;
int     mis2mintue;

//set variable Fan1
int     fan1status;
int     fan1hour;
int     fan1mintue;

//set variable Fan1
int     fan2status;
int     fan2hour;
int     fan2mintue;

//set variable Fan21
int     fan21status;
int     fan21hour;
int     fan21mintue;

//set variable Fan21
int     fan22status;
int     fan22hour;
int     fan22mintue;



int timePumpOn = 10; // Thời gian bật bơm nước
// Biến cho timer
long sampleTimingSeconds = 1; // ==> Thời gian đọc cảm biến (s)
long startTiming = 0;
long elapsedTime = 0;
WiFiClient client;

// ThingSpeak Settings
const int channelID = 884467; //
String writeAPIKey = "UK592M4GI5783Y1D"; // write API key for your ThingSpeak Channel
const char* server = "api.thingspeak.com";
const int postingInterval = 2 * 1000; // post data every 2 seconds


DHT dht(DHTPIN, DHTTYPE);


strDateTime dateTime;                                                     

void setup() {
  int cnt = 0;
 Serial.begin(115200);
  /* Set ESP8266 to WiFi Station mode */
  WiFi.mode(WIFI_STA);
  /* Wait for SmartConfig packet from mobile */ 
  while (WiFi.status() != WL_CONNECTED) 
  {
    Serial.print(".");
    delay(100);
     if(cnt++ >= 10){
 WiFi.beginSmartConfig();
 while(1){
 delay(1000);
 //Kiểm tra kết nối thành công in thông báo
 if(WiFi.smartConfigDone()){
 Serial.println("SmartConfig Success");
 break;
 }
 }
     }
      
  Firebase.setInt("S1_Auto",0); 
  Firebase.setInt("S2_Lamp",0);  
  Firebase.setInt("S3_Fan",0); 
  Firebase.setInt("S4_Pump",0);
  Firebase.setInt("S5_Mis",0);
  Firebase.setInt("S6_Fan2",0);
  Firebase.setInt("S7_Alarm",0);
  
  Firebase.setInt("Smart_Farm1/Auto/Temp_high",0);
  Firebase.setInt("Smart_Farm1/Auto/Temp_low",0);
  Firebase.setInt("Smart_Farm1/Auto/Hum",0);
  Firebase.setInt("Smart_Farm1/Auto/Soil",0);
//Set Alarm:
  Firebase.setInt("Smart_Farm1/Alarm/Status",0);
// Set Alarm: Pump1  
  Firebase.setInt("Smart_Farm1/Alarm/Pump/AmPump1/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Pump/AmPump1/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Pump/AmPump1/Minute",0);

  // Set Alarm: Pump2  
  Firebase.setInt("Smart_Farm1/Alarm/Pump/AmPump2/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Pump/AmPump2/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Pump/AmPump2/Minute",0);

  // Set Alarm: Lamp1  
  Firebase.setInt("Smart_Farm1/Alarm/Lamp/AmLamp1/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Lamp/AmLamp1/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Lamp/AmLamp1/Minute",0);
   // Set Alarm: Lamp2  
  Firebase.setInt("Smart_Farm1/Alarm/Lamp/AmLamp2/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Lamp/AmLamp2/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Lamp/AmLamp2/Minute",0);
  // Set Alarm: Mis1  
  Firebase.setInt("Smart_Farm1/Alarm/Mis/AmMis1/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Mis/AmMis1/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Mis/AmMis1/Minute",0);
  // Set Alarm: Mis2  
  Firebase.setInt("Smart_Farm1/Alarm/Mis/AmMis2/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Mis/AmMis2/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Mis/AmMis2/Minute",0);

  // Set Alarm: Fan1  
  Firebase.setInt("Smart_Farm1/Alarm/Fan/AmFan1/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan/AmFan1/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan/AmFan1/Minute",0);
  // Set Alarm: Fan2 
  Firebase.setInt("Smart_Farm1/Alarm/Fan/AmFan2/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan/AmFan2/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan/AmFan2/Minute",0);
  // Set Alarm: Fan21  
  Firebase.setInt("Smart_Farm1/Alarm/Fan2/AmFan21/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan2/AmFan21/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan2/AmFan21/Minute",0);
  // Set Alarm: Fan22 
  Firebase.setInt("Smart_Farm1/Alarm/Fan2/AmFan22/Status",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan2/AmFan22/Hour",0);
  Firebase.setInt("Smart_Farm1/Alarm/Fan2/AmFan22/Minute",0);


  
  
 Serial.println("");
 Serial.println("");
 
 WiFi.printDiag(Serial);
 
 // Khởi tạo server UDP tại lắng nghe tại cổng 49999
 Udp.begin(49999);
 Serial.println("Server started");

// In địa chỉ IP ESP8266
 Serial.println(WiFi.localIP());
  }
  Serial.println("WiFi Connected.");
  Serial.print("IP Address: ");
  Serial.println(WiFi.localIP());                                            //print local IP address


 pinMode(Lam_Sensor, INPUT); 
 pinMode(DHTPIN , INPUT);   

 pinMode(PUMPW_PIN, OUTPUT);
 pinMode(Lamp, OUTPUT);
 pinMode(Fan,OUTPUT);
 pinMode(Mis,OUTPUT);
 pinMode(Fan2,OUTPUT);
 
 digitalWrite(PUMPW_PIN,LOW);
 digitalWrite(Lamp,LOW);
 digitalWrite(Fan,LOW);
 digitalWrite(Mis,LOW);
 Firebase.begin(FIREBASE_HOST, FIREBASE_AUTH);                              // connect to firebase
  dht.begin();                                                               //Start reading dht sensor
  startTiming = millis();
 
}
int doamdat()
{
  int i = 0;
  int anaValue = 0;
  for (i = 0; i < 10; i++)  //
  {
    anaValue += analogRead(Soil_Sensor); //Đọc giá trị cảm biến ánh sáng
    delay(50);   // Đợi đọc giá trị ADC
  }
  anaValue = anaValue / (i);
  anaValue = map(anaValue, 1023, 0, 0, 100); //sáng ítc:0%  ==> sáng nhiều: 100%
  return anaValue;
}
void hand()

{
val=Firebase.getString("S1_Manual").toInt(); 
 if (val==0)
 {
  val1=Firebase.getString("S2_Lamp").toInt();                                        //Reading the value of the varialble Status from the firebase
  if(val1==1)                                                             // If, the Status is 1, turn on the Relay3
     {
      digitalWrite(Lamp,HIGH);
      Serial.println("light 3 ON");
      Firebase.setString("Smart_Farm1/Status/Lamp","1");
    }
 else if(val1==0)                                                      // If, the Status is 0, turn Off the Relay3
    {                                      
      digitalWrite(Lamp,LOW);
      Serial.println("light 3 OFF");
      Firebase.setString("Smart_Farm1/Status/Lamp","0");
    }

val2=Firebase.getString("S4_Pump").toInt();                                        //Reading the value of the varialble Status from the firebase
  
  if(val2==1)                                                             // If, the Status is 1, turn on the Relay4
     {
      digitalWrite(PUMPW_PIN,HIGH);
      Serial.println("light 4 ON");
      Firebase.setString("Smart_Farm1/Status/Fan","1");
    }
    else if(val2==0)                                                      // If, the Status is 0, turn Off the Relay4
    {                                      
      digitalWrite(PUMPW_PIN,LOW);
      Serial.println("light 4 OFF");
      Firebase.setString("Smart_Farm1/Status/Fan","0");
    }        
  
val3=Firebase.getString("S3_Fan").toInt();                                        //Reading the value of the varialble Status from the firebase
  
  if(val3==1)                                                             // If, the Status is 1, turn on the Relay3
     {
      digitalWrite(Fan,HIGH);
      Serial.println("light 3 ON");
      Firebase.setString("Smart_Farm1/Status/Pump","1");
    }
    else if(val3==0)                                                      // If, the Status is 0, turn Off the Relay3
    {                                      
      digitalWrite(Fan,LOW);
      Serial.println("light 3 OFF");
      Firebase.setString("Smart_Farm1/Status/Pump","0");
    }

   val4=Firebase.getString("S5_Mis").toInt();                                        //Reading the value of the varialble Status from the firebase
  
  if(val4==1)                                                             // If, the Status is 1, turn on the Relay4
     {
      digitalWrite(Mis,HIGH);
      Serial.println("light 4 ON");
      Firebase.setString("Smart_Farm1/Status/Mis","1");
    }
    else if(val4==0)                                                      // If, the Status is 0, turn Off the Relay4
    {                                      
      digitalWrite(Mis,LOW);
      Serial.println("light 4 OFF");
      Firebase.setString("Smart_Farm1/Status/Mis","0");
    }
    val5=Firebase.getString("S6_Fan2").toInt();
    if(val5==1)                                                             // If, the Status is 1, turn on the Relay4
     {
      digitalWrite(Fan2,HIGH);
      Serial.println("light 4 ON");
      Firebase.setString("Smart_Farm1/Status/Fan2","1");
    }
    else if(val5==0)                                                      // If, the Status is 0, turn Off the Relay4
    {                                      
      digitalWrite(Fan2,LOW);
      Serial.println("light 4 OFF");
      Firebase.setString("Smart_Farm1/Status/Fan2","0");
        
    }
 else if(val==1)
 {
  Serial.println("CHE DO BANG TAY:OFF");
  }
  }
}
 void automation()
 
 {
  float h = dht.readHumidity();                                              // Reading temperature or humidity takes about 250 milliseconds!
  int t = dht.readTemperature();                                           // Read temperature as Celsius (the default)
  float d = doamdat();
  
  valauto=Firebase.getString("S1_Mode").toInt();
 
  soilhum_low =  Firebase.getString("Smart_Farm1/Auto/Soil").toInt();
  temp_high =  Firebase.getString("Smart_Farm1/Auto/Temp_high").toInt();
  temp_low =  Firebase.getString("Smart_Farm1/Auto/Temp_low").toInt();
  hum_low =  Firebase.getString("Smart_Farm1/Auto/Hum").toInt();
  
  if(valauto==1)
  {
        if ( d< soilhum_low)
          {
         digitalWrite(PUMPW_PIN,HIGH);
         Firebase.setString("Smart_Farm1/Status/Pump","1");
        // turnPumpOn();
          } 
       else {
        digitalWrite(PUMPW_PIN,LOW);
        Firebase.setString("Smart_Farm1/Status/Pump","0");
          }
          if ( h< hum_low)
          {
         digitalWrite(Mis,HIGH);
         Firebase.setString("Smart_Farm1/Status/Mis","1");
          } 
       else {
        digitalWrite(Mis,LOW);
        Firebase.setString("Smart_Farm1/Status/Mis","0");
          }
          if ( t< temp_low)
          {
         digitalWrite(Lamp,HIGH);
         Firebase.setString("Smart_Farm1/Status/Lamp","1");
          } 
       else {
        digitalWrite(Lamp,LOW);
        Firebase.setString("Smart_Farm1/Status/Lamp","0");
          }
          if ( t> temp_high)
          {
         digitalWrite(Fan,HIGH);
         Firebase.setString("Smart_Farm1/Status/Fan","1");
          } 
       else {
        digitalWrite(Fan,LOW);
        Firebase.setString("Smart_Farm1/Status/Fan","0");
          }
    
  } 
    else if (valauto==0)
    {
     Serial.println("CHE DO AUTO :off");
     }
  }
void readsensor(void){
  float h = dht.readHumidity();                                              // Reading temperature or humidity takes about 250 milliseconds!
  int t = dht.readTemperature();                                           // Read temperature as Celsius (the default)
  float d = doamdat();
  
  if (digitalRead(Lam_Sensor) == 1)
  {
  Serial.println("Toi");
  Firebase.setString("Smart_Farm1/sensor/light", "Trời tối"); 
//  Firebase.pushString("/anhsangluu", "Trời tối");
  }
  else {
    Serial.println("Sang");
    Firebase.setString("Smart_Farm1/sensor/light", "Trời sáng");
 //   Firebase.pushString("/anhsangluu", "Trời sáng"); 
  }
  
//  Serial.print("Humidity: ");  Serial.print(h);
  String fireHumid = String(h);                                                 //convert integer humidity to string humidity 
 // Serial.print("%  Temperature: ");  Serial.print(t);  Serial.println("°C ");
  String fireTemp = String(t) ;                                                     //convert integer temperature to string temperature
  
  String doamdat = String(d) ;   
 // delay(5000);

 Firebase.setString("Smart_Farm1/sensor/dht/hum", fireHumid);                                  //setup path and send readings
 Firebase.setString("Smart_Farm1/sensor/dht/temp", fireTemp);                                //setup path and send readings
 Firebase.setString("Smart_Farm1/sensor/soil_hum", doamdat); 
  
  }
  
void loop() {
   Udp.parsePacket();
 //In IP của ESP8266
 while(Udp.available()){
 Serial.println(Udp.remoteIP());
 Udp.flush(); // xóa vùng đệm 
 delay(5);
 }
  
//  delay(postingInterval);
  
  float h = dht.readHumidity();                                              // Reading temperature or humidity takes about 250 milliseconds!
  float t = dht.readTemperature();                                           // Read temperature as Celsius (the default)
  float d = doamdat();

  
    realtime();  
    alarmclock();
    readsensor();
    hand();
    automation ();
    
  if (client.connect(server, 80)) {
                // Construct API request body
                String body = "field1=" + String(t, 1) + "&field2=" + String(h, 1) + "&field3=" + String(d, 1);


                client.print("POST /update HTTP/1.1\n");
                client.print("Host: api.thingspeak.com\n");
                client.print("Connection: close\n");
                client.print("X-THINGSPEAKAPIKEY: " + writeAPIKey + "\n");
                client.print("Content-Type: application/x-www-form-urlencoded\n");
                client.print("Content-Length: ");
                client.print(body.length());
                client.print("\n\n");
                client.print(body);
                client.print("\n\n");
                Serial.printf("Nhiet do %s - Do am %s\r\n", String(t, 1).c_str(), String(h, 1).c_str());
        }
       
}
void alarmclock()
{
  //Realtime O'clock
  minuterealtime = Firebase.getString("Smart_Farm1/Realtime/Phut").toInt();
  Serial.println(minuterealtime);
  hourrealtime = Firebase.getString("Smart_Farm1/Realtime/Hour").toInt();
  Serial.println(hourrealtime);

  // Alarm Button
  alarm = Firebase.getString("Smart_Farm1/Alarm/Status").toInt();
  Serial.println(alarm);
  //Pump Time 1
  pump1status = Firebase.getString("Smart_Farm1/Alarm/Pump/AmPump1/Status").toInt();
  Serial.println(pump1status);
  pump1hour =  Firebase.getString("Smart_Farm1/Alarm/Pump/AmPump1/Hour").toInt();
  Serial.println(pump1hour);
  pump1mintue = Firebase.getString("Smart_Farm1/Alarm/Pump/AmPump1/Minute").toInt();
  Serial.println(pump1mintue);
  //Pump Time 2
  pump2status = Firebase.getString("Smart_Farm1/Alarm/Pump/AmPump2/Status").toInt();
  Serial.println(pump2status);
  pump2hour =  Firebase.getString("Smart_Farm1/Alarm/Pump/AmPump2/Hour").toInt();
  Serial.println(pump2hour);
  pump2mintue = Firebase.getString("Smart_Farm1/Alarm/Pump/AmPump2/Minute").toInt();
  Serial.println(pump2mintue);

  //Lamp Time 1
  lamp1status = Firebase.getString("Smart_Farm1/Alarm/Lamp/AmLamp1/Status").toInt();
  Serial.println(lamp1status);
  lamp1hour =  Firebase.getString("Smart_Farm1/Alarm/Lamp/AmLamp1/Hour").toInt();
  Serial.println(lamp1hour);
  lamp1mintue = Firebase.getString("Smart_Farm1/Alarm/Lamp/AmLamp1/Minute").toInt();
  Serial.println(lamp1mintue);
  //Lamp Time 2
  lamp2status = Firebase.getString("Smart_Farm1/Alarm/Lamp/AmLamp2/Status").toInt();
  Serial.println(lamp2status);
  lamp2hour =  Firebase.getString("Smart_Farm1/Alarm/Lamp/AmLamp2/Hour").toInt();
  Serial.println(lamp2hour);
  lamp2mintue = Firebase.getString("Smart_Farm1/Alarm/Lamp/AmLamp2/Minute").toInt();
  Serial.println(lamp2mintue);

  //Mis Time 1
  mis1status = Firebase.getString("Smart_Farm1/Alarm/Mis/AmMis1/Status").toInt();
  Serial.println(mis1status);
  mis1hour =  Firebase.getString("Smart_Farm1/Alarm/Mis/AmMis1/Hour").toInt();
  Serial.println(mis1hour);
  mis1mintue = Firebase.getString("Smart_Farm1/Alarm/Mis/AmMis1/Minute").toInt();
  Serial.println(mis1mintue);
  //Mis Time 2
  mis2status = Firebase.getString("Smart_Farm1/Alarm/Mis/AmMis2/Status").toInt();
  Serial.println(mis2status);
  mis2hour =  Firebase.getString("Smart_Farm1/Alarm/Mis/AmMis2/Hour").toInt();
  Serial.println(mis2hour);
  mis2mintue = Firebase.getString("Smart_Farm1/Alarm/Mis/AmMis2/Minute").toInt();
  Serial.println(mis2mintue);

  //Fan Time 1
  fan1status = Firebase.getString("Smart_Farm1/Alarm/Fan/AmFan1/Status").toInt();
  Serial.println(fan1status);
  fan1hour =  Firebase.getString("Smart_Farm1/Alarm/Fan/AmFan1/Hour").toInt();
  Serial.println(fan1hour);
  fan1mintue = Firebase.getString("Smart_Farm1/Alarm/Fan/AmFan1/Minute").toInt();
  Serial.println(fan1mintue);
  //Mis Time 2
  fan2status = Firebase.getString("Smart_Farm1/Alarm/Fan/AmFan2/Status").toInt();
  Serial.println(fan2status);
  fan2hour =  Firebase.getString("Smart_Farm1/Alarm/Fan/AmFan2/Hour").toInt();
  Serial.println(fan2hour);
  fan2mintue = Firebase.getString("Smart_Farm1/Alarm/Fan/AmFan2/Minute").toInt();
  Serial.println(fan2mintue);

  
  //Fan2 Time 1
  fan21status = Firebase.getString("Smart_Farm1/Alarm/Fan2/AmFan21/Status").toInt();
  Serial.println(fan21status);
  fan21hour =  Firebase.getString("Smart_Farm1/Alarm/Fan2/AmFan21/Hour").toInt();
  Serial.println(fan21hour);
  fan21mintue = Firebase.getString("Smart_Farm1/Alarm/Fan2/AmFan21/Minute").toInt();
  Serial.println(fan21mintue);
  //Mis Time 2
  fan22status = Firebase.getString("Smart_Farm1/Alarm/Fan2/AmFan22/Status").toInt();
  Serial.println(fan22status);
  fan22hour =  Firebase.getString("Smart_Farm1/Alarm/Fan2/AmFan22/Hour").toInt();
  Serial.println(fan22hour);
  fan22mintue = Firebase.getString("Smart_Farm1/Alarm/Fan2/AmFan22/Minute").toInt();
  Serial.println(fan22mintue);
 







   
  if(alarm ==1)               // Start Button Alarm
    {
      ///////////////////////////////////PUMP////////////////////////////////////////
      //Pump Time 1
      if(pump1status==1)
        {if( pump1mintue == minuterealtime && pump1hour == hourrealtime )
         {
          digitalWrite(PUMPW_PIN,HIGH);
         Firebase.setString("Smart_Farm1/Status/Pump","1");
          } 
       else{
        digitalWrite(PUMPW_PIN,LOW);
        Firebase.setString("Smart_Farm1/Status/Pump","0");
          }
        }
        
        //Pump Time 2

        if(pump2status==1)
        {if( pump2mintue == minuterealtime && pump2hour == hourrealtime )
         {
          digitalWrite(PUMPW_PIN,HIGH);
         Firebase.setString("Smart_Farm1/Status/Pump","1");
          } 
       else{
        digitalWrite(PUMPW_PIN,LOW);
        Firebase.setString("Smart_Farm1/Status/Pump","0");
          }
        }


  /////////////////////////////////////LAMP///////////////////////////////////////////////////      
        //Lamp Time 1

        if(lamp1status==1)
        {if( lamp1mintue == minuterealtime && lamp1hour == hourrealtime )
         {
          digitalWrite(Lamp,HIGH);
         Firebase.setString("Smart_Farm1/Status/Lamp","1");
          } 
       else{
        digitalWrite(Lamp,LOW);
        Firebase.setString("Smart_Farm1/Status/Lamp","0");
          }
        }

        //Lamp Time 2

        if(lamp2status==1)
        {if( lamp2mintue == minuterealtime && lamp2hour == hourrealtime )
         {
          digitalWrite(Lamp,HIGH);
         Firebase.setString("Smart_Farm1/Status/Lamp","1");
          } 
       else{
        digitalWrite(Lamp,LOW);
        Firebase.setString("Smart_Farm1/Status/Lamp","0");
          }
        }
         /////////////////////////////////////MIS///////////////////////////////////////////////////      
        //Mis Time 1

        if(mis1status==1)
        {if( mis1mintue == minuterealtime && mis1hour == hourrealtime )
         {
          digitalWrite(Mis,HIGH);
         Firebase.setString("Smart_Farm1/Status/Mis","1");
          } 
       else{
        digitalWrite(Mis,LOW);
        Firebase.setString("Smart_Farm1/Status/Mis","0");
          }
        }

        //Mis Time 2

        if(mis2status==1)
        {if( mis2mintue == minuterealtime && mis2hour == hourrealtime )
         {
          digitalWrite(Mis,HIGH);
         Firebase.setString("Smart_Farm1/Status/Mis","1");
          } 
       else{
        digitalWrite(Mis,LOW);
        Firebase.setString("Smart_Farm1/Status/Mis","0");
          }
        }

        /////////////////////////////////////Fan///////////////////////////////////////////////////      
        //Mis Time 1

        if(fan1status==1)
        {if( fan1mintue == minuterealtime && fan1hour == hourrealtime )
         {
          digitalWrite(Fan,HIGH);
         Firebase.setString("Smart_Farm1/Status/Fan","1");
          } 
       else{
        digitalWrite(Fan,LOW);
        Firebase.setString("Smart_Farm1/Status/Fan","0");
          }
        }

        //fan Time 2

        if(fan2status==1)
        {if( fan2mintue == minuterealtime && fan2hour == hourrealtime )
         {
          digitalWrite(Fan,HIGH);
         Firebase.setString("Smart_Farm1/Status/Fan","1");
          } 
       else{
        digitalWrite(Fan,LOW);
        Firebase.setString("Smart_Farm1/Status/Fan","0");
          }
        }
       /////////////////////////////////////Fan2///////////////////////////////////////////////////      
        //Mis Time 1

        if(fan21status==1)
        {if( fan21mintue == minuterealtime && fan21hour == hourrealtime )
         {
          digitalWrite(Fan2,HIGH);
         Firebase.setString("Smart_Farm1/Status/Fan2","1");
          } 
       else{
        digitalWrite(Fan2,LOW);
        Firebase.setString("Smart_Farm1/Status/Fan2","0");
          }
        }

          //fan Time 2

        if(fan22status==1)
        {if( fan22mintue == minuterealtime && fan22hour == hourrealtime )
         {
          digitalWrite(Fan2,HIGH);
         Firebase.setString("Smart_Farm1/Status/Fan2","1");
          } 
       else{
        digitalWrite(Fan2,LOW);
        Firebase.setString("Smart_Farm1/Status/Fan2","0");
          }
        }
       
      

 }
    else if (alarm==0)
    {
    Serial.println("CHE DO Alarm :off");
    }  

}
void realtime()
{
      dateTime = NTPch.getNTPtime(7.0, 0);
      if(dateTime.valid){
    NTPch.printDateTime(dateTime);

    int actualHour = dateTime.hour;      // Gio
    String gio = String(actualHour);
    Firebase.setString("Smart_Farm1/Realtime/Gio", gio);
    int  actualMinute = dateTime.minute;  // Phut
    String phut = String(actualMinute);
    Firebase.setString("Smart_Farm1/Realtime/Phut", phut);
    int  actualsecond = dateTime.second;  // Giay
    Firebase.setInt("Smart_Farm1/Realtime/Giay", actualsecond);
    int  actualyear = dateTime.year;       // Nam
    Firebase.setInt("Smart_Farm1/Realtime/Nam", actualyear);
    int  actualMonth = dateTime.month;    // Thang
    Firebase.setInt("Smart_Farm1/Realtime/Thang", actualMonth);
    int  actualday =dateTime.day;         // Ngay
    Firebase.setInt("Smart_Farm1/Realtime/Ngay", actualday);
    int actualdayofWeek = dateTime.dayofWeek;
    Firebase.setInt("Smart_Farm1/Realtime/NgayTrongTuan", actualdayofWeek);
    
  } 
}
void turnPumpOn()
{
  digitalWrite(PUMPW_PIN, HIGH);
  delay (timePumpOn * 5000);
  digitalWrite(PUMPW_PIN, LOW);
}
