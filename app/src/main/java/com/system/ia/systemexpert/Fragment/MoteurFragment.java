package com.system.ia.systemexpert.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.system.ia.systemexpert.Entity.ReponseSystem;
import com.system.ia.systemexpert.Entity.SystemEntity;
import com.system.ia.systemexpert.R;
import com.system.ia.systemexpert.System.TheResolver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoteurFragment extends Fragment {

    List<SystemEntity> systemEntities = new ArrayList<SystemEntity>();
    List<ReponseSystem> reponseSystems;
    RelativeLayout ll;
    TextView textView;
    int currentitem = 0;
    String[] propositions = new String[]{};
    String[] interproposition;
    String[] propositionsradio;
    RadioButton[] rb;
    RadioGroup rg;
    public MoteurFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_moteur, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ll = (RelativeLayout) view.findViewById(R.id.viewid);
        textView = (TextView) view.findViewById(R.id.textView);
        new ParseAsycn().execute();
    }



    public String OpenFile() {
        String json = null;
        try {

            InputStream is = getResources().openRawResource(R.raw.information);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }

    class ParseAsycn extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            String jsonString = OpenFile();
            JSONObject jsonObj = null;
            JSONObject jsonsystem = null;
            JSONArray jsonArrayresp = null;
            try {
                SystemEntity systemEntity = null;
                ReponseSystem reponseSystem = null;
                jsonObj = new JSONObject(jsonString);
                Iterator<String> iter = jsonObj.keys();
                while (iter.hasNext()) {
                    String key = iter.next();
                    jsonsystem = jsonObj.getJSONObject(key);
                    systemEntity = new SystemEntity();
                    systemEntity.setId(jsonsystem.getInt("id"));
                    systemEntity.setTitle(jsonsystem.getString("title"));
                    jsonArrayresp = jsonsystem.getJSONArray("reponse");
                    Log.d("id_resp", jsonArrayresp.length() + "");
                    reponseSystems = new ArrayList<ReponseSystem>();
                    for (int i = 0; i < jsonArrayresp.length(); i++) {
                        JSONObject jsonObject = (JSONObject) jsonArrayresp.get(i);
                        reponseSystem = new ReponseSystem();
                        reponseSystem.setText(jsonObject.getString("text"));
                        if (!jsonObject.getJSONArray("proposition").get(0).equals("")) {
                            String[] strings = new String[jsonObject.getJSONArray("proposition").length()];
                            Log.d("string array", jsonObject.getJSONArray("proposition").length() + "");
                            for (int j = 0; j < jsonObject.getJSONArray("proposition").length(); j++) {
                                Log.d("string array", jsonObject.getJSONArray("proposition").get(j).toString());
                                strings[j] = jsonObject.getJSONArray("proposition").get(j).toString();
                            }
                            reponseSystem.setProposition(strings);
                        } else {
                            reponseSystem.setProposition(null);
                        }
                        reponseSystems.add(reponseSystem);

                    }

                    systemEntity.setReponseSystemList(reponseSystems);
                    Log.d("id", jsonsystem.getInt("id") + "");
                    systemEntities.add(systemEntity);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            final RelativeLayout.LayoutParams pm = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            final RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            param.setMargins(10, 10, 10, 10);
            pm.addRule(RelativeLayout.BELOW, R.id.textView);
            pm.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
            Log.d("size list", systemEntities.size() + "");
            final List<ReponseSystem> rep = systemEntities.get(currentitem).getReponseSystemList();
            textView.setText(systemEntities.get(currentitem).getTitle());
            rb = new RadioButton[systemEntities.get(currentitem).getReponseSystemList().size()];
            rg = new RadioGroup(getActivity());
            rg.setId(102);
            rg.setLayoutParams(pm);
            final Button submit = new Button(getActivity());
            submit.setBackground(getResources().getDrawable(R.color.red));
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
            p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
            p.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
            submit.setLayoutParams(p);
            submit.setText(R.string.next);
            submit.setId(101);
            rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
            for (int i = 0; i < systemEntities.get(currentitem).getReponseSystemList().size(); i++) {
                rb[i] = new RadioButton(getActivity());
                rb[i].setTextColor(R.color.red);
                rb[i].setTextSize(25);
                rb[i].setLayoutParams(param);
                rb[i].setId(i);
                rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
                rb[i].setText(rep.get(i).getText());
            }
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    Log.d("id_chekced====>",checkedId+"");
                    Log.d("checked", rb[checkedId].getText() + "");
                    Log.d("value",rep.get(checkedId).getProposition()+"");
                    if(rep.get(checkedId).getProposition()!=null)
                        propositionsradio = rep.get(checkedId).getProposition();

                }
            });
            ll.addView(rg);//you add the whole RadioGroup to the layout
            ll.addView(submit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ll.removeView(rg);
                    currentitem++;
                    propositions = combine(propositions,propositionsradio);
                    Log.d("size tab combine",propositions.length+"");
                    if (currentitem > systemEntities.size()-1){
                        ll.removeView(submit);
                        textView.setText(R.string.result);
                        final Button result = new Button(getActivity());
                        final RelativeLayout.LayoutParams pr = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        pr.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                        pr.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        pr.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        result.setLayoutParams(pr);
                        result.setId(104);
                        result.setText(R.string.reload);
                        result.setBackground(getResources().getDrawable(R.color.red));

                        ll.addView(result);
                        RelativeLayout.LayoutParams txt = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        txt.addRule(RelativeLayout.BELOW, R.id.textView);
                        txt.addRule(RelativeLayout.CENTER_HORIZONTAL,RelativeLayout.TRUE);
                        txt.setMargins(25,25,25,25);
                        TheResolver theResolver = new TheResolver(propositions);
                        final TextView textresult = new TextView(getActivity());
                        textresult.setTextColor(R.color.red_dark);
                        textresult.setText(theResolver.getProp());
                        textresult.setTextSize(25);
                        textresult.setLayoutParams(txt);
                        ll.addView(textresult);
                        result.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ll.removeView(result);
                                ll.removeView(textresult);
                                new ParseAsycn().execute();
                            }
                        });
                    }else {
                        textView.setText(systemEntities.get(currentitem).getTitle());
                        Toast.makeText(getActivity(), currentitem + "", Toast.LENGTH_LONG).show();
                        final List<ReponseSystem> rep = systemEntities.get(currentitem).getReponseSystemList();
                        rb = new RadioButton[systemEntities.get(currentitem).getReponseSystemList().size()];
                        rg = new RadioGroup(getActivity());
                        rg.setId(102);
                        rg.setLayoutParams(pm);
                        Button submit = new Button(getActivity());
                        submit.setBackground(getResources().getDrawable(R.color.red));
                        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT);
                        p.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
                        p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
                        p.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        submit.setLayoutParams(p);
                        rg.setOrientation(RadioGroup.VERTICAL);//or RadioGroup.VERTICAL
                        for (int i = 0; i < systemEntities.get(currentitem).getReponseSystemList().size(); i++) {
                            rb[i] = new RadioButton(getActivity());
                            rb[i].setLayoutParams(param);
                            rb[i].setTextColor(R.color.red);
                            rb[i].setTextSize(25);
                            rb[i].setId(i);
                            rg.addView(rb[i]); //the RadioButtons are added to the radioGroup instead of the layout
                            rb[i].setText(rep.get(i).getText());
                        }

                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                Log.d("checked id", rg.getCheckedRadioButtonId() + "");
                                Log.d("checked", rb[checkedId].getText() + "");
                                Log.d("value", rep.get(checkedId).getProposition() + "");
                                if (rep.get(checkedId).getProposition() != null)
                                    propositionsradio = rep.get(checkedId).getProposition();

                            }
                        });
                        ll.addView(rg);//you add the whole RadioGroup to the layout
                    }
                }
            });
            super.onPostExecute(aVoid);
        }
    }

    public static String[] combine(String[] a, String[] b) {
        int length = a.length + b.length;
        String[] result = new String[length];
        System.arraycopy(a, 0, result, 0, a.length);
        System.arraycopy(b, 0, result, a.length, b.length);
        Log.d("array combine", result.length + "");
        return result;
    }
}
