package com.example.drtherapist.client.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.drtherapist.R;
import com.example.drtherapist.client.adapter.All_Dr_ListAdapter;
import com.example.drtherapist.client.adapter.SearchClientAdapter;
import com.example.drtherapist.client.model.AllTharapistModel;
import com.example.drtherapist.client.model.SearchClient;
import com.example.drtherapist.common.Interface.Config;
import com.example.drtherapist.common.Utils.GPSTracker;
import com.example.drtherapist.common.Utils.NetworkUtil;
import com.example.drtherapist.common.Utils.ToastClass;
import com.example.drtherapist.common.Utils.Utils;
import com.example.drtherapist.common.remote.API;
import com.example.drtherapist.therapist.model.CategoryListData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.drtherapist.common.Interface.Config.SearchTherapistByCat_id;

public class SearchActivityClient extends AppCompatActivity {
    String url, query;
    private RecyclerView recycler_view_search;
    private Context mContext;
    private List<SearchClient> searchClients;
    private SearchClientAdapter mAdapter;
    private LinearLayout ll_no_record;
    private EditText et_search;
    private TextView tv_find_client;
    Spinner serach_cate,serach_by;
    GPSTracker tracker;
    double longitude, latitude;
    ImageView iv_back;
    String JSON_URL = "http://logicalsofttech.com/therapist/index.php/Api/Search_Threpist";
    RecyclerView.LayoutManager mLayoutManager;
    static int flag = 0;
    private String cat_url;
    private List<CategoryListData> catagoryList=new ArrayList<>();
    HashMap<Integer,CategoryListData>CateHashMap=new HashMap<Integer,CategoryListData>();
     String Search_by_string;
     RelativeLayout rel_et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        mContext = this;
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent = getIntent();
//        query = intent.getStringExtra("SEARCH_QUERY");
//        Log.e("query", "" + query);

        tracker = new GPSTracker(SearchActivityClient.this);
        // if (tracker.canGetLocation()); {
        latitude = tracker.getLatitude();
        longitude = tracker.getLongitude();
        Log.e("current_lat ", " " + String.valueOf(latitude));
        Log.e("current_Lon ", " " + String.valueOf(longitude));
//            address = getAddress(latitude, longitude);
//            Log.e("Address ", " " + getAddress(latitude, longitude));
        // }

        initView();
    }

    private void initView() {
        searchClients = new ArrayList<>();

        try{
            if (searchClients!=null){
                searchClients.clear();
                mAdapter = new SearchClientAdapter(searchClients, mContext);
            }
        }catch(Exception e){
        }


        recycler_view_search = findViewById(R.id.recycler_view_search);
        ll_no_record = findViewById(R.id.ll_no_record);

        tv_find_client = (TextView) findViewById(R.id.tv_find_client);
        et_search = (EditText) findViewById(R.id.et_search);
        serach_cate =  findViewById(R.id.serach_cate);
        serach_by =  findViewById(R.id.serach_by);
        rel_et_search =  findViewById(R.id.rel_et_search);

        if (NetworkUtil.isNetworkConnected(SearchActivityClient.this)) {
            try {
                cat_url = API.BASE_URL + "getCategory";
                Log.e("Category  URL signup = ", cat_url);
                getcategoryList(cat_url);
            } catch (NullPointerException e) {
               // ToastClass.showToast(SearchActivityClient.this, getString(R.string.too_slow));
            }
        } else {
            ToastClass.showToast(SearchActivityClient.this, getString(R.string.no_internet_access));
        }



        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
               // searchClients.clear();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                filter(editable.toString());
            }
        });
//***************************************
        serach_by.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        Search_by_string=serach_by.getSelectedItem().toString();

                   if (Search_by_string.equalsIgnoreCase("search by category")){
                       serach_cate.setVisibility(View.VISIBLE);

                       try{
                           if (searchClients!=null){
                               searchClients.clear();
                               mAdapter = new SearchClientAdapter(searchClients, mContext);
                           }
                       }catch(Exception e){
                           Log.e("search_cate_error", e.toString());
                       }

                   }else{
                       serach_cate.setVisibility(View.GONE);
                   }

                   if (Search_by_string.equalsIgnoreCase("search by nearest")){
                       String url="http://logicalsofttech.com/therapist/index.php/Api/Search_doctorListByLat_Lon";

                       if (NetworkUtil.isNetworkConnected(SearchActivityClient.this)) {
                           try {
                               SearchNearestTherapist(url);
                           } catch (NullPointerException e) {
                               // ToastClass.showToast(SearchActivityClient.this, getString(R.string.too_slow));
                           }
                       } else {
                           ToastClass.showToast(SearchActivityClient.this, getString(R.string.no_internet_access));
                       }
                   }

                   //**********search by name***
                if (Search_by_string.equalsIgnoreCase("search by name or pincode")){
                    rel_et_search.setVisibility(View.VISIBLE);

                    try{
                        if (searchClients!=null){
                            searchClients.clear();
                            mAdapter = new SearchClientAdapter(searchClients, mContext);
                        }
                    }catch(Exception e){
                    }
                }else {
                    rel_et_search.setVisibility(View.GONE);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        //***************************search by category
        serach_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               String Search_by_cat_id=CateHashMap.get(position).id;
               // Toast.makeText(mContext, "cate"+CateHashMap.get(position).id, Toast.LENGTH_SHORT).show();

                try{
                            String SearchByCat_url= Config.Base_Url+SearchTherapistByCat_id;
                            if (searchClients!=null){
                                searchClients.clear();
                                mAdapter = new SearchClientAdapter(searchClients, mContext);
                                Search_Dr_by_Catg(SearchByCat_url,Search_by_cat_id);
                            }
                        }catch(Exception e){
                            Log.e("search_cate_error", e.toString());
                        }

//                for (int i = 0; i < CateHashMap.size(); i++)
//                {
//
//                    if (CateHashMap.get(i).name.equals(serach_cate.getItemAtPosition(position)))
//                    {
//                        String Search_by_cat_id =CateHashMap.get(i).id;
//                        Log.e("Search_cat_id", Search_by_cat_id);
//                         Toast.makeText(SearchActivityClient.this, "_Id"+Search_by_cat_id, Toast.LENGTH_SHORT).show();
//
//
//
//                    }
//
//                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
        //*************************************
        tv_find_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if (searchClients!=null){
                        searchClients.clear();
                        mAdapter = new SearchClientAdapter(searchClients, mContext);
                    }
                }catch(Exception e){
                }

                if (!et_search.getText().toString().isEmpty()){

                    if (NetworkUtil.isNetworkConnected(SearchActivityClient.this)) {
                        try {
                            getSearch(JSON_URL,et_search.getText().toString());
                        } catch (NullPointerException e) {
                            // ToastClass.showToast(SearchActivityClient.this, getString(R.string.too_slow));
                        }
                    } else {
                        ToastClass.showToast(SearchActivityClient.this, getString(R.string.no_internet_access));
                    }

                }else {
                    Toast.makeText(SearchActivityClient.this, "Please enter search", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }


    //***********search by therapist category id***************
    private void Search_Dr_by_Catg(String searchByCat_url, String search_by_cat_id) {
        Log.e("search_cat_by_url", searchByCat_url);

        AndroidNetworking.post(searchByCat_url)
                .addBodyParameter("cat_id",search_by_cat_id)

                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //  Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            Log.e("cat_idby_search", result);
                            //catagoryList.clear();
                            //catagoryList.add(new CategoryListData(context.getString(R.string.select_category)));

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray data = jsonObject.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject dataJSONObject = data.getJSONObject(i);

                                    SearchClient searchClient = new SearchClient();
                                    searchClient.dr_id=dataJSONObject.getString("dr_id");
                                    searchClient.dr_fname=dataJSONObject.getString("dr_fname");
                                    searchClient.experience=dataJSONObject.getString("experience");
                                    searchClient.dr_image=(dataJSONObject.getString("dr_image"));
                                    searchClient.fee=(dataJSONObject.getString("fee"));
                                    searchClient.dr_email=(dataJSONObject.getString("dr_email"));
                                    searchClient.cat_id=(dataJSONObject.getString("cat_id"));
                                    searchClient.dr_unique_key=(dataJSONObject.getString("dr_unique_key"));
                                    searchClient.rating=(dataJSONObject.getString("rating"));
                                    searchClient.specialization=(dataJSONObject.getString("specialization"));
                                    searchClient.age=(dataJSONObject.getString("age"));
                                    searchClient.location=(dataJSONObject.getString("location"));
                                    searchClient.gender=(dataJSONObject.getString("gender"));
                                    searchClient.gender=(dataJSONObject.getString("gender"));
                                    searchClient.fee=(dataJSONObject.getString("fee"));
                                    searchClient.bio=(dataJSONObject.getString("bio"));
                                    searchClient.resume=(dataJSONObject.getString("resume"));

                                    searchClients.add(searchClient);
                                }

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recycler_view_search.setLayoutManager(mLayoutManager);
                                recycler_view_search.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_search.setAdapter(mAdapter);


                            } else {
                                ToastClass.showToast(SearchActivityClient.this, message);

                                try{
                                    if (searchClients!=null){
                                        searchClients.clear();
                                        mAdapter.notifyDataSetChanged();
                                        mAdapter = new SearchClientAdapter(searchClients, mContext);
                                    }
                                }catch(Exception e){
                                    Log.e("search_e_error", e.toString());
                                }

                            }



                        } catch (JSONException e) {
                            e.printStackTrace();

                            try{
                                if (searchClients!=null){
                                    searchClients.clear();
                                    mAdapter = new SearchClientAdapter(searchClients, mContext);
                                }
                            }catch(Exception e1){
                                Log.e("search_cate_error", e1.toString());
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("cat_id_error = ", "" + error);
                    }
                });

    }
//*************************search therapist by nearest location
    private void SearchNearestTherapist(String url) {
        AndroidNetworking.post(url)
                .addBodyParameter("lat", String.valueOf(latitude))
                .addBodyParameter("lng", String.valueOf(longitude))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //  Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            //catagoryList.clear();
                            //catagoryList.add(new CategoryListData(context.getString(R.string.select_category)));

                            if (result.equalsIgnoreCase("true")) {

                                    JSONArray data = jsonObject.getJSONArray("data");

                                    for (int i = 0; i < data.length(); i++) {

                                        JSONObject dataJSONObject = data.getJSONObject(i);

                                        SearchClient searchClient = new SearchClient();
                                        searchClient.dr_id=dataJSONObject.getString("dr_id");
                                        searchClient.dr_fname=dataJSONObject.getString("dr_fname");
                                        searchClient.experience=dataJSONObject.getString("experience");
                                        searchClient.dr_image=(dataJSONObject.getString("dr_image"));
                                        searchClient.fee=(dataJSONObject.getString("fee"));
                                        searchClient.dr_email=(dataJSONObject.getString("dr_email"));
                                        searchClient.cat_id=(dataJSONObject.getString("cat_id"));
                                        searchClient.dr_unique_key=(dataJSONObject.getString("dr_unique_key"));
                                        searchClient.rating=(dataJSONObject.getString("rating"));
                                        searchClient.specialization=(dataJSONObject.getString("specialization"));
                                        searchClient.age=(dataJSONObject.getString("age"));
                                        searchClient.location=(dataJSONObject.getString("location"));
                                        searchClient.gender=(dataJSONObject.getString("gender"));
                                        searchClient.gender=(dataJSONObject.getString("gender"));
                                        searchClient.fee=(dataJSONObject.getString("fee"));
                                        searchClient.bio=(dataJSONObject.getString("bio"));
                                        searchClient.resume=(dataJSONObject.getString("resume"));

                                        searchClients.add(searchClient);
                                    }

                                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    recycler_view_search.setLayoutManager(mLayoutManager);
                                    recycler_view_search.setItemAnimator(new DefaultItemAnimator());
                                    recycler_view_search.setAdapter(mAdapter);


                            } else ToastClass.showToast(SearchActivityClient.this, message);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });


    }

    private void getcategoryList(String cat_url) {
        AndroidNetworking.get(cat_url)
                /*.addPathParameter("pageNumber", "0")
                .addQueryParameter("limit", "3")
                .addHeaders("token", "1234")*/
                .setTag("category list")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //  Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            //catagoryList.clear();
                            //catagoryList.add(new CategoryListData(context.getString(R.string.select_category)));

                            if (result.equalsIgnoreCase("true")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject job = jsonArray.getJSONObject(i);
                                    CategoryListData catData = new CategoryListData();
                                    catData.id = job.getString("cat_id");
                                    catData.name = job.getString("cate_name");
                                    catData.image = job.getString("cat_img");

                                    catagoryList.add(catData);
                                    CateHashMap.put(i, catData);
                                }
                            } else ToastClass.showToast(SearchActivityClient.this, message);

                            ArrayAdapter<CategoryListData> aa = new ArrayAdapter<CategoryListData>(SearchActivityClient.this, android.R.layout.simple_spinner_item, catagoryList) {
                                //ArrayAdapter aa = new ArrayAdapter(context, android.R.layout.simple_spinner_item, catagoryList) {
                                //set hint data in center_vertical on spinner
                                public View getView(int position, @Nullable View cView, @NonNull ViewGroup parent) {
                                    View view = super.getView(position, cView, parent);
                                    view.setPadding(0, view.getTop(), view.getRight(), view.getBottom());
                                    return view;
                                }
                            };
                            aa.setDropDownViewResource(R.layout.list_item_spinner);
                            //Setting the ArrayAdapter data on the Spinner
                            serach_cate.setAdapter(aa);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("error = ", "" + error);
                    }
                });

    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<SearchClient> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (SearchClient s : searchClients) {
            //if the existing elements contains the search input
            if (s.dr_fname.toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }
        }

        //calling a method of the adapter class and passing the filtered list
        mAdapter.filterList(filterdNames);
    }


    private void getSearch(String JSON_URL, String etSearch) {
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        Log.e("response", response + "");
//                        try {
//                            //getting the whole json object from the response
//                            JSONObject obj = new JSONObject(response);
//                            boolean result = obj.getBoolean("result");
//
//                            if (result == true) {
//
//                                JSONArray data = obj.getJSONArray("data");
//
//                                //now looping through all the elements of the json array
//                                for (int i = 0; i < data.length(); i++) {
//
//                                    JSONObject dataJSONObject = data.getJSONObject(i);
//
//
//                                    SearchClient searchClient = new SearchClient();
//                                    searchClient.setName(dataJSONObject.getString("cate_name"));
//                                    searchClient.setExperience(dataJSONObject.getString("experience"));
//                                    searchClient.setImage(dataJSONObject.getString("dr_image"));
//                                    searchClient.setFee(dataJSONObject.getString("fee"));
//
//                                    searchClients.add(searchClient);
//                                }
//
//                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
//                                recycler_view_search.setLayoutManager(mLayoutManager);
//                                recycler_view_search.setItemAnimator(new DefaultItemAnimator());
//                                recycler_view_search.setAdapter(mAdapter);
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        //displaying the error in toast if occurrs
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(stringRequest);

        AndroidNetworking.post(JSON_URL)
                .addBodyParameter("search", etSearch)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        //  Utils.dismissDialog();
                        try {
                            String message = jsonObject.getString("msg");
                            String result = jsonObject.getString("result");
                            //catagoryList.clear();
                            //catagoryList.add(new CategoryListData(context.getString(R.string.select_category)));

                            if (result.equalsIgnoreCase("true")) {

                                JSONArray data = jsonObject.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++) {

                                    JSONObject dataJSONObject = data.getJSONObject(i);

                                    SearchClient searchClient = new SearchClient();

                                    searchClient.dr_id=dataJSONObject.getString("dr_id");
                                    searchClient.dr_fname=dataJSONObject.getString("dr_fname");
                                    searchClient.experience=dataJSONObject.getString("experience");
                                    searchClient.dr_image=(dataJSONObject.getString("dr_image"));
                                    searchClient.fee=(dataJSONObject.getString("fee"));
                                    searchClient.dr_email=(dataJSONObject.getString("dr_email"));
                                    searchClient.cat_id=(dataJSONObject.getString("cat_id"));
                                    searchClient.dr_unique_key=(dataJSONObject.getString("dr_unique_key"));
                                    searchClient.rating=(dataJSONObject.getString("rating"));
                                    searchClient.specialization=(dataJSONObject.getString("specialization"));
                                    searchClient.age=(dataJSONObject.getString("age"));
                                    searchClient.location=(dataJSONObject.getString("location"));
                                    searchClient.gender=(dataJSONObject.getString("gender"));
                                    searchClient.gender=(dataJSONObject.getString("gender"));
                                    searchClient.fee=(dataJSONObject.getString("fee"));
                                    searchClient.bio=(dataJSONObject.getString("bio"));
                                    searchClient.resume=(dataJSONObject.getString("resume"));

                                    searchClients.add(searchClient);
                                }

                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                                recycler_view_search.setLayoutManager(mLayoutManager);
                                recycler_view_search.setItemAnimator(new DefaultItemAnimator());
                                recycler_view_search.setAdapter(mAdapter);


                            } else ToastClass.showToast(SearchActivityClient.this, message);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {

                        Log.e("nameByerror = ", "" + error);
                    }
                });



    }


}
