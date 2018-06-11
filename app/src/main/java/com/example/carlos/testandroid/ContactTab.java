package com.example.carlos.testandroid;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;

public class ContactTab extends Fragment {

    private AwesomeValidation awesomeValidation;
    private View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.contact_fragment, container, false);

        try {
            CreateFields();
        }catch(JSONException ex){
            ex.printStackTrace();
        }

        return v;
    }

    private void CreateFields() throws JSONException {
        JSONObject json = AppController.getInstance().GetContactJson();

        JSONArray arr = json.getJSONArray("cells");

        LinearLayout mainLayout = v.findViewById(R.id.mainLayout);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,10,0,50);

        for (int i = 0; i < arr.length(); i++) {
            try {
                JSONObject currentObj = arr.getJSONObject(i);

                int type = currentObj.getInt("type");

                // text fields
                if(type == 1)
                {
                    LinearLayout fieldLL = new LinearLayout(getContext());
                    fieldLL.setOrientation(LinearLayout.VERTICAL);

                    TextView label = new TextView(getContext());
                    label.setText(currentObj.getString("message"));

                    final EditText txt = new EditText(getContext());
                    txt.setId(currentObj.getInt("id"));

                    switch (currentObj.getString("typefield"))
                    {
                        case "1":
                            txt.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            awesomeValidation.addValidation(txt, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", "Nome Errado" );
                            break;
                        case "3":
                            txt.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                            awesomeValidation.addValidation(txt, Patterns.EMAIL_ADDRESS, "Email Errado" );
                            break;
                        case "telnumber":
                            txt.setInputType(InputType.TYPE_CLASS_PHONE);
                            awesomeValidation.addValidation(txt, "^\\([0-9]{2}\\) [0-9]?[0-9]{4} [0-9]{4}$", "Telefone Errado" );
                            txt.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {

                                    txt.removeTextChangedListener(this);

                                    String strValue = String.valueOf(editable.toString());
                                    strValue = strValue.replaceAll("[^\\d]", "");

                                    editable.clear();
                                    editable.append(strValue.replaceFirst("(\\d{2})(\\d?)(\\d{4})(\\d{4})", "($1) $2$3 $4"));

                                    txt.addTextChangedListener(this);
                                }
                            });
                            break;
                        default:
                    }

                    fieldLL.addView(label);
                    fieldLL.addView(txt);
                    fieldLL.setLayoutParams(params);

                    mainLayout.addView(fieldLL);
                }
                // checkbox fields
                else if(type == 4)
                {
                    CheckBox chk = new CheckBox(getContext());
                    chk.setText(currentObj.getString("message"));
                    chk.setId(currentObj.getInt("id"));
                    chk.setLayoutParams(params);

                    mainLayout.addView(chk);
                }
                // button
                else if(type == 5)
                {
                    Button btn = new Button(getContext());
                    btn.setText(currentObj.getString("message"));
                    btn.setId(currentObj.getInt("id"));
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SubmitForm();
                        }
                    });
                    btn.setLayoutParams(params);
                    btn.setBackgroundResource(R.drawable.btn_red_rounded);
                    btn.setTextColor(Color.WHITE);

                    mainLayout.addView(btn);
                }

            } catch (JSONException e) {
            }
        }
    }

    private void SubmitForm()
    {
        if (awesomeValidation.validate()) {
            Intent intent = new Intent(getActivity(), MessageSent.class);
            startActivity(intent);
        }
    }
}
