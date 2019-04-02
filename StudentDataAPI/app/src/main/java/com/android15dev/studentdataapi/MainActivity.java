package com.android15dev.studentdataapi;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private DatePickerDialog datePickerDialog;
    private TextView textViewDOB;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextCourse;
    private EditText editTextAddress;
    private EditText editTextPhone;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        Calendar cal = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, this,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        textViewDOB = findViewById(R.id.textViewDOB);
        textViewDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextCourse = findViewById(R.id.editTextCourse);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.buttonSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String dob = textViewDOB.getText().toString();
        String course = editTextCourse.getText().toString();
        String address = editTextAddress.getText().toString();
        String phone = editTextPhone.getText().toString();

        if (TextUtils.isEmpty(firstName)) {
            showToast("Please enter first name");
        } else if (checkNumberExist(firstName)) {
            showToast("Please enter valid first name");
        } else if (TextUtils.isEmpty(lastName)) {
            showToast("Please enter last name");
        } else if (checkNumberExist(lastName)) {
            showToast("Please enter valid last name");
        } else if (TextUtils.isEmpty(course)) {
            showToast("Please enter course");
        } else if (checkNumberExist(course)) {
            showToast("Please enter valid course");
        } else if (TextUtils.isEmpty(address)) {
            showToast("Please enter address");
        } else if (TextUtils.isEmpty(phone)) {
            showToast("Please enter phone number");
        } else if (dob.equals(getString(R.string.dob))) {
            showToast("Please select valid date of birth");
        } else {
            hideKeyboard();
            progressBar.setVisibility(View.VISIBLE);
            UserDetail userDetail = new UserDetail(firstName, lastName, dob, course, address, phone);
            RetrofitRequest.getInstance().register("application/json", userDetail)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressBar.setVisibility(View.GONE);
                            if (response.code() == 200) {
                                showToast("User registered Successfully");
                                clear();
                            } else {
                                showToast("Something went wrong. Please try again later");
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressBar.setVisibility(View.GONE);
                            t.printStackTrace();
                            showToast("Something went wrong. Please try again later");
                        }
                    });
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void clear() {
        editTextPhone.setText("");
        editTextAddress.setText("");
        editTextCourse.setText("");
        editTextLastName.setText("");
        editTextFirstName.setText("");
        textViewDOB.setText(R.string.dob);
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        textViewDOB.setText(sdf.format(cal.getTime()));
    }

    private boolean checkNumberExist(String data) {
        String regex = "(.)*(\\d)(.)*";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(data).matches();
    }
}
