package com.example.carlos.testandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InvestimentTab extends Fragment {

    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.investiment_fragment, container, false);

        try {
            CreateFields();
        }catch(JSONException ex){
            ex.printStackTrace();
        }

        return v;
    }

    private void CreateFields() throws JSONException {

        JSONObject json = AppController.getInstance().GetInvestJson();

        JSONObject jsonScreen = json.getJSONObject("screen");

        // title
        TextView title = v.findViewById(R.id.lblTitle);
        title.setText(jsonScreen.getString("title"));

        // fund name
        TextView fundName = v.findViewById(R.id.lblFundName);
        fundName.setText(jsonScreen.getString("fundName"));

        // what is
        TextView whatIs = v.findViewById(R.id.lblWhatIs);
        whatIs.setText(jsonScreen.getString("whatIs"));

        // definition
        TextView definition = v.findViewById(R.id.lblDefinition);
        definition.setText(jsonScreen.getString("definition"));

        // riskTitle
        TextView riskTitle = v.findViewById(R.id.lblRiskTitle);
        riskTitle.setText(jsonScreen.getString("riskTitle"));

        // infoTitle
        TextView infoTitle = v.findViewById(R.id.lblInfoTitle);
        infoTitle.setText(jsonScreen.getString("infoTitle"));

        // risk
        SetRiskBar(jsonScreen.getInt("risk") - 1);

        // moreInfo
        JSONObject jsonMoreInfo = jsonScreen.getJSONObject("moreInfo");
        JSONObject jsonMonth = jsonMoreInfo.getJSONObject("month");
        JSONObject jsonYear = jsonMoreInfo.getJSONObject("year");
        JSONObject json12Months = jsonMoreInfo.getJSONObject("12months");

        TextView fundInfo0 = v.findViewById(R.id.lblTblFund0);
        fundInfo0.setText(String.valueOf(jsonMonth.getDouble("fund")) + "%");
        TextView cdiInfo0 = v.findViewById(R.id.lblTblCDI0);
        cdiInfo0.setText(String.valueOf(jsonMonth.getDouble("CDI")) + "%");

        TextView fundInfo1 = v.findViewById(R.id.lblTblFund1);
        fundInfo1.setText(String.valueOf(jsonYear.getDouble("fund")) + "%");
        TextView cdiInfo1 = v.findViewById(R.id.lblTblCDI1);
        cdiInfo1.setText(String.valueOf(jsonYear.getDouble("CDI")) + "%");

        TextView fundInfo2 = v.findViewById(R.id.lblTblFund2);
        fundInfo2.setText(String.valueOf(json12Months.getDouble("fund")) + "%");
        TextView cdiInfo2 = v.findViewById(R.id.lblTblCDI2);
        cdiInfo2.setText(String.valueOf(json12Months.getDouble("CDI")) + "%");

        // info
        TableLayout infoTable = v.findViewById(R.id.tblInfo);

        JSONArray jsonInfo = jsonScreen.getJSONArray("info");

        for(int i = 0; i < jsonInfo.length(); i++)
        {
            TableRow row = new TableRow(getContext());

            JSONObject item = jsonInfo.getJSONObject(i);

            TextView nameView = new TextView(getContext());
            nameView.setText(item.getString("name"));
            nameView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            TextView dataView = new TextView(getContext());
            dataView.setText(item.getString("data"));
            dataView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);

            row.addView(nameView);
            row.addView(dataView);

            infoTable.addView(row);
        }

        // downInfo
        JSONArray jsonDownInfo = jsonScreen.getJSONArray("downInfo");

        for(int i = 0; i < jsonDownInfo.length(); i++)
        {
            TableRow row = new TableRow(getContext());

            JSONObject item = jsonDownInfo.getJSONObject(i);

            TextView nameView = new TextView(getContext());
            nameView.setText(item.getString("name"));
            nameView.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);

            LinearLayout newLL = new LinearLayout(getContext());

            ImageView dataView = new ImageView(getContext());
            dataView.setImageResource(R.drawable.ic_download);
            dataView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
            TextView downloadView = new TextView(getContext());
            downloadView.setText("Baixar");
            downloadView.setTextColor(Color.RED);

            newLL.addView(dataView);
            newLL.addView(downloadView);
            newLL.setGravity(Gravity.RIGHT);

            row.addView(nameView);
            row.addView(newLL);

            infoTable.addView(row);
        }
    }

    public void SetRiskBar(int risk)
    {
        LinearLayout lLayout1 = v.findViewById(R.id.riskBarArrow);
        LinearLayout lLayout2 = v.findViewById(R.id.riskBar);

        lLayout1.getChildAt(risk).setVisibility(View.VISIBLE);

        View colorView = lLayout2.getChildAt(risk);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)colorView.getLayoutParams();
        params.height = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 13, getResources().getDisplayMetrics());
        colorView.setLayoutParams(params);
    }
}
