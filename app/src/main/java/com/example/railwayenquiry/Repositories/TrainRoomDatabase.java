package com.example.railwayenquiry.Repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.railwayenquiry.Adapters.TrainItem;

import org.json.JSONArray;
import org.json.JSONObject;


@Database(entities = {TrainItem.class}, version = 1, exportSchema = false)
public abstract class TrainRoomDatabase extends RoomDatabase {
    public abstract TrainDao trainDao();

    private static TrainRoomDatabase INSTANCE;
    public static String jsonstring = "{\"debit\": 1, \"trains\": [{\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"15:45\", \"delaydep\": \"RT\", \"number\": \"47115\", \"actdep\": \"15:45\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"16:10\", \"delaydep\": \"RT\", \"number\": \"47116\", \"actdep\": \"16:10\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-GR INTERCITY EXP\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"16:25\", \"delaydep\": \"RT\", \"number\": \"11308\", \"actdep\": \"16:25\"}, {\"scharr\": \"SRC\", \"name\": \"CHENNAI EXP\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"16:50\", \"delaydep\": \"RT\", \"number\": \"12604\", \"actdep\": \"16:50\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"17:05\", \"delaydep\": \"RT\", \"number\": \"47117\", \"actdep\": \"17:05\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-VSKP GODAVARI EXP\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"17:15\", \"delaydep\": \"RT\", \"number\": \"12728\", \"actdep\": \"17:15\"}, {\"scharr\": \"SRC\", \"name\": \"PASS\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"17:20\", \"delaydep\": \"RT\", \"number\": \"57156\", \"actdep\": \"17:20\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"17:30\", \"delaydep\": \"RT\", \"number\": \"47118\", \"actdep\": \"17:30\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"18:15\", \"delaydep\": \"RT\", \"number\": \"47119\", \"actdep\": \"18:15\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-MAS CHARMINAR EXP\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"18:30\", \"delaydep\": \"RT\", \"number\": \"12760\", \"actdep\": \"18:30\"}, {\"scharr\": \"SRC\", \"name\": \"PUSHPULL\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"18:40\", \"delaydep\": \"RT\", \"number\": \"67266\", \"actdep\": \"18:40\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"18:57\", \"delaydep\": \"RT\", \"number\": \"47120\", \"actdep\": \"18:57\"}, {\"scharr\": \"SRC\", \"name\": \"HYB-LPI\", \"delayarr\": \"RT\", \"actarr\": \"SRC\", \"schdep\": \"19:40\", \"delaydep\": \"RT\", \"number\": \"47121\", \"actdep\": \"19:40\"}, {\"scharr\": \"15:35\", \"name\": \"FM-HYB\", \"delayarr\": \"RT\", \"actarr\": \"15:35\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47200\", \"actdep\": \"DSTN\"}, {\"scharr\": \"15:45\", \"name\": \"LPI-HYB\", \"delayarr\": \"RT\", \"actarr\": \"15:45\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47139\", \"actdep\": \"DSTN\"}, {\"scharr\": \"16:42\", \"name\": \"FM-HYB\", \"delayarr\": \"RT\", \"actarr\": \"16:42\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47201\", \"actdep\": \"DSTN\"}, {\"scharr\": \"17:15\", \"name\": \"LPI-HYB\", \"delayarr\": \"RT\", \"actarr\": \"17:15\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47140\", \"actdep\": \"DSTN\"}, {\"scharr\": \"17:55\", \"name\": \"HWH HYB EAST COASTEXPRESS\", \"delayarr\": \"RT\", \"actarr\": \"17:55\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"18645\", \"actdep\": \"DSTN\"}, {\"scharr\": \"18:05\", \"name\": \"LPI-HYB\", \"delayarr\": \"RT\", \"actarr\": \"18:05\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47141\", \"actdep\": \"DSTN\"}, {\"scharr\": \"18:05\", \"name\": \"PUSHPULL\", \"delayarr\": \"RT\", \"actarr\": \"18:05\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"67267\", \"actdep\": \"DSTN\"}, {\"scharr\": \"18:50\", \"name\": \"LPI-HYB\", \"delayarr\": \"RT\", \"actarr\": \"18:50\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47142\", \"actdep\": \"DSTN\"}, {\"scharr\": \"19:10\", \"name\": \"TDU-HYB PASSENGER\", \"delayarr\": \"RT\", \"actarr\": \"19:10\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"57518\", \"actdep\": \"DSTN\"}, {\"scharr\": \"19:35\", \"name\": \"LPI-HYB\", \"delayarr\": \"RT\", \"actarr\": \"19:35\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"47143\", \"actdep\": \"DSTN\"}, {\"scharr\": \"19:35\", \"name\": \"TELANGANA EXPRESS\", \"delayarr\": \"RT\", \"actarr\": \"19:35\", \"schdep\": \"DSTN\", \"delaydep\": \"RT\", \"number\": \"12724\", \"actdep\": \"DSTN\"}], \"total\": 24, \"response_code\": 200}";
    public static String jsonstring2 = "{\"response_code\": 200, \"debit\": 1, \"trains\": [{\"number\": \"07526\", \"from_station\": {\"code\": \"NBQ\", \"lng\": 90.5193288, \"lat\": 26.5110388, \"name\": \"NEW BONGAIGAON\"}, \"name\": \"NBQ - SGUJ DEMU SPL\", \"to_station\": {\"code\": \"SGUJ\", \"lng\": 88.39528609999999, \"lat\": 26.7271012, \"name\": \"SILIGURI JN\"}, \"rescheduled_time\": \"07:00\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"02:00\"}, {\"number\": \"11047\", \"from_station\": {\"code\": \"MRJ\", \"lng\": -73.6460781, \"lat\": 40.7555796, \"name\": \"MIRAJ JN\"}, \"name\": \"MRJ UBL EXP\", \"to_station\": {\"code\": \"UBL\", \"lng\": 75.1239547, \"lat\": 15.3647083, \"name\": \"HUBLI JN\"}, \"rescheduled_time\": \"01:40\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:30\"}, {\"number\": \"12715\", \"from_station\": {\"code\": \"NED\", \"lng\": 77.3209555, \"lat\": 19.1382514, \"name\": \"NANDED\"}, \"name\": \"NED-ASR SACHKHAND EXP\", \"to_station\": {\"code\": \"ASR\", \"lng\": 74.8722642, \"lat\": 31.6339793, \"name\": \"AMRITSAR JN\"}, \"rescheduled_time\": \"12:00\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"02:30\"}, {\"number\": \"18235\", \"from_station\": {\"code\": \"BPL\", \"lng\": 77.412615, \"lat\": 23.2599333, \"name\": \"BHOPAL  JN\"}, \"name\": \"BPL-BSP EXP CUM PASS\", \"to_station\": {\"code\": \"BSP\", \"lng\": 82.1391412, \"lat\": 22.0796251, \"name\": \"BILASPUR JN\"}, \"rescheduled_time\": \"11:45\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"03:45\"}, {\"number\": \"20652\", \"from_station\": {\"code\": \"TLGP\", \"lng\": 74.90041579999999, \"lat\": 14.2158245, \"name\": \"TALGUPPA\"}, \"name\": \"TLGP-SBC  SF EXPRESS\", \"to_station\": {\"code\": \"SBC\", \"lng\": 77.5695295, \"lat\": 12.9781291, \"name\": \"KSR BENGALURU\"}, \"rescheduled_time\": \"05:40\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:10\"}, {\"number\": \"22457\", \"from_station\": {\"code\": \"NED\", \"lng\": 77.3209555, \"lat\": 19.1382514, \"name\": \"NANDED\"}, \"name\": \"NED-NLDM SUPERFAST EXP\", \"to_station\": {\"code\": \"AADR\", \"lng\": 76.1104491, \"lat\": 31.6704872, \"name\": \"AMB ANDAURA\"}, \"rescheduled_time\": \"12:45\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:45\"}, {\"number\": \"51603\", \"from_station\": {\"code\": \"BINA\", \"lng\": -122.2659551, \"lat\": 37.5243899, \"name\": \"BINA JN\"}, \"name\": \"BINA-KTE PASSANGER\", \"to_station\": {\"code\": \"KTE\", \"lng\": 80.38938139999999, \"lat\": 23.8343441, \"name\": \"KATNI\"}, \"rescheduled_time\": \"14:30\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:00\"}, {\"number\": \"51675\", \"from_station\": {\"code\": \"KTE\", \"lng\": 80.38938139999999, \"lat\": 23.8343441, \"name\": \"KATNI\"}, \"name\": \"KTE-CPU PASS\", \"to_station\": {\"code\": \"CPU\", \"lng\": 83.02002449999999, \"lat\": 24.5232651, \"name\": \"CHOPAN\"}, \"rescheduled_time\": \"21:45\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"00:30\"}, {\"number\": \"57123\", \"from_station\": {\"code\": \"BDCR\", \"lng\": 80.8935925, \"lat\": 17.6687912, \"name\": \"BHADRACHALAM RD\"}, \"name\": \"BDCR-SRUR SINGARENI\", \"to_station\": {\"code\": \"SRUR\", \"lng\": 79.57116549999999, \"lat\": 19.4769446, \"name\": \"SIRPUR TOWN\"}, \"rescheduled_time\": \"06:45\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:00\"}, {\"number\": \"57135\", \"from_station\": {\"code\": \"AJNI\", \"lng\": 79.0825548, \"lat\": 21.127025, \"name\": \"AJNI\"}, \"name\": \"NGP-KZJ PASS\", \"to_station\": {\"code\": \"KZJ\", \"lng\": 79.5034501, \"lat\": 17.972366, \"name\": \"KAZIPET JN\"}, \"rescheduled_time\": \"22:10\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"00:50\"}, {\"number\": \"59342\", \"from_station\": {\"code\": \"BINA\", \"lng\": -122.2659551, \"lat\": 37.5243899, \"name\": \"BINA JN\"}, \"name\": \"BINA NAD PASS\", \"to_station\": {\"code\": \"NAD\", \"lng\": 75.41699179999999, \"lat\": 23.4454599, \"name\": \"NAGDA JN\"}, \"rescheduled_time\": \"05:30\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:30\"}, {\"number\": \"77204\", \"from_station\": {\"code\": \"NS\", \"lng\": 81.6966198, \"lat\": 16.4329833, \"name\": \"NARASAPUR\"}, \"name\": \"NS-BZA\", \"to_station\": {\"code\": \"BZA\", \"lng\": 80.6480153, \"lat\": 16.5061743, \"name\": \"VIJAYAWADA JN\"}, \"rescheduled_time\": \"15:45\", \"rescheduled_date\": \"01-02-2020\", \"time_diff\": \"01:00\"}]}";
    public static String[] contexts={"StationScheduleFragment2.class", "RPageFragment.class","CPageFragment.class"};

    public static TrainRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TrainRoomDatabase.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TrainRoomDatabase.class, "train_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };


    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TrainDao mDao;
        String train_name, train_no, arrival_time, departure_time;

        PopulateDbAsync(TrainRoomDatabase db) {
            mDao = db.trainDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                mDao.deleteAll();
                JSONObject js = new JSONObject(jsonstring);
                JSONArray jsonArray = new JSONArray();


                jsonArray = js.getJSONArray("trains");
                JSONObject temp = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {

                    temp = jsonArray.getJSONObject(i);
                    train_name = temp.getString("name");
                    train_no = temp.getString("number");
                    arrival_time = temp.getString("actarr");
                    departure_time = temp.getString("actdep");


                    TrainItem word = new TrainItem(train_name, train_no, arrival_time, departure_time);
                    mDao.insert(word);

                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private static RoomDatabase.Callback rRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new rPopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class rPopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TrainDao mDao;
        String train_name, train_no, rescheduled_time, rescheduled_date;

        rPopulateDbAsync(TrainRoomDatabase db) {
            mDao = db.trainDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                mDao.deleteAll();
                JSONObject js = new JSONObject(jsonstring2);
                JSONArray jsonArray = new JSONArray();


                jsonArray = js.getJSONArray("trains");
                JSONObject temp = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {

                    temp = jsonArray.getJSONObject(i);
                    train_name = temp.getString("name");
                    train_no = temp.getString("number");
                    rescheduled_time = temp.getString("rescheduled_time");
                    rescheduled_date= temp.getString("rescheduled_date");


                    TrainItem word = new TrainItem(train_name, train_no, rescheduled_time, rescheduled_date);
                    mDao.insert(word);

                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
    private static RoomDatabase.Callback cRoomDatabaseCallback =
            new RoomDatabase.Callback() {

                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new cPopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class cPopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TrainDao mDao;
        String train_name, train_no, time;

        cPopulateDbAsync(TrainRoomDatabase db) {
            mDao = db.trainDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            try {

                mDao.deleteAll();
                JSONObject js = new JSONObject(jsonstring2);
                JSONArray jsonArray = new JSONArray();


                jsonArray = js.getJSONArray("trains");
                JSONObject temp = new JSONObject();

                for (int i = 0; i < jsonArray.length(); i++) {

                    temp = jsonArray.getJSONObject(i);
                    train_name = temp.getString("name");
                    train_no = temp.getString("number");
                    time = temp.getString("start_time");


                    TrainItem word = new TrainItem(train_name, train_no, time,"");
                    mDao.insert(word);

                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
