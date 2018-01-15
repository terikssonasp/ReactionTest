package com.example.terik.ik2018_inl2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    List<String> allNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        String url = "http://users.du.se/~h15aspto/IK2018Labb4/ReadTextFile.php";

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //requestQueue.getCache().clear();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {


            allNames = new ArrayList<>(); // create list with duplicates...



            for(int i = 0; i < response.length(); i++){

                try {
                    JSONObject tempObj = response.getJSONObject(i);
                    String namn = tempObj.getString("name");
                    double reaction = tempObj.getDouble("reaction");
                    allNames.add(namn);
                    //Toast.makeText(this, namn + " : " + reaction , Toast.LENGTH_SHORT).show();





                    //gör en hashmap som mappar String (name) och en ArrayList<Double> (reaction)

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            //hämta ut unika namn
            HashSet<String> uniqueNames = new HashSet<String>(allNames);

            //loopar igenom unika namn
            for(String value : uniqueNames){
                //skapar upp en temporär arraylista där reaktionstiderna skall sparas
                ArrayList<Double> tempList = new ArrayList<>();
               // Toast.makeText(this, value, Toast.LENGTH_SHORT).show();

                //loopar igenom responsen och hämtar ut jsonobjekten
                for(int i = 0; i < response.length(); i++){
                    JSONObject obj = null;
                    try {
                        obj = response.getJSONObject(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //om objektets namn är samma som value, läggs reaktionen in i arrayen
                    try {
                        if(value.equalsIgnoreCase(obj.getString("name"))){
                            tempList.add(obj.getDouble("reaction"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }



                //skapar upp en array med DataPoints som skall göras till en linje i diagrammet
                DataPoint[] dp = new DataPoint[tempList.size()];
                for(int i = 0; i < tempList.size();i++ ){
                    dp[i] = new DataPoint(i + 1, tempList.get(i));
                }

                //gör templist till en linje i diagrammet
                LineGraphSeries<DataPoint> line = new LineGraphSeries<DataPoint>(dp);

                //sätter färg på linje
                int col = generateRandomColor();
                line.setTitle(value);
                line.setColor(col);
                line.setDrawDataPoints(true);

                //lägger till linjen i diagrammet
                graph.addSeries(line);


                // Ändrar text på x- och y-axel
                graph.getGridLabelRenderer().setVerticalAxisTitle("Reaction");
                graph.getGridLabelRenderer().setHorizontalAxisTitle("Attempt");
                graph.getViewport().setXAxisBoundsManual(true);
                graph.getViewport().setMinX(1);
                graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                    @Override
                    public String formatLabel(double value, boolean isValueX) {
                        if (isValueX) {
                            // show x values as try
                            return "#" + super.formatLabel(value, isValueX);
                        } else {
                            // show seconds for y values
                            return super.formatLabel(value, isValueX) + " sec";
                        }
                    }
                });

                //sätter legen
                graph.getLegendRenderer().setVisible(true);
                graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
                graph.getLegendRenderer().setBackgroundColor(Color.LTGRAY);

                //sätter scroll
                graph.getViewport().setScrollable(true); // enables horizontal scrolling
                graph.getViewport().setScrollableY(true); // enables vertical scrolling
                //graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
                //graph.getViewport().setScalableY(true); // enables vertical zooming and scrolling


            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub

            }
        });

        requestQueue.add(jsonArrayRequest);




    }


    //hjälpmetod för att generera en random färg
    public int generateRandomColor(){
        Random rand = new Random();

        int color = Color.argb(255, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

        return color;
    }


}
