package com.api_employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import api.EmployeeAPI;
import model.Employee;
import model.EmployeeCUD;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterEmployee extends AppCompatActivity {
    private EditText etEmpName, etEmpAge, etEmpSalary;
    private Button btnRegister;
    private final static String BaseURL= "http://dummy.restapiexample.com/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        etEmpName = findViewById(R.id.etEmpName);
        etEmpAge = findViewById(R.id.etEmpAge);
        etEmpSalary = findViewById(R.id.etEmpSalary);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register(){
        String name = etEmpName.getText().toString();
        float salary = Float.parseFloat(etEmpSalary.getText().toString());
        int age = Integer.parseInt(etEmpAge.getText().toString());

        EmployeeCUD employee = new EmployeeCUD(name,salary,age);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL).addConverterFactory(GsonConverterFactory.create()).build();

        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<Void> voidCall = employeeAPI.registerEmployee(employee);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(RegisterEmployee.this,"Register Successful",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterEmployee.this,"Error: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
