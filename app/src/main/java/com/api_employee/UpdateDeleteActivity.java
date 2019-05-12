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

public class UpdateDeleteActivity extends AppCompatActivity {
    private EditText etEmpId, etEmpName, etEmpSalary, etEmpAge;
    private Button btnSearch, btnUpdate, btnDelete;
    private final static String BaseURL= "http://dummy.restapiexample.com/api/v1/";
    EmployeeAPI employeeAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        etEmpId = findViewById(R.id.etEmpId);
        etEmpName = findViewById(R.id.etEmpName);
        etEmpSalary = findViewById(R.id.etEmpSalary);
        etEmpAge = findViewById(R.id.etEmpAge);
        btnSearch = findViewById(R.id.btnSearch);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEmployee();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEmployee();
            }
        });
    }
    private void createInstance(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL).addConverterFactory(GsonConverterFactory.create()).build();
        employeeAPI = retrofit.create(EmployeeAPI.class);
    }
    private void loadData(){
        createInstance();
        Call<Employee> listCall = employeeAPI.getEmployeeById(Integer.parseInt(etEmpId.getText().toString()));

        listCall.enqueue(new Callback<Employee>() {
            @Override
            public void onResponse(Call<Employee> call, Response<Employee> response) {
                etEmpName.setText(response.body().getEmployee_name());
                etEmpSalary.setText(Float.toString(response.body().getEmployee_salary()));
                etEmpAge.setText(response.body().getEmployee_age());
            }

            @Override
            public void onFailure(Call<Employee> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateEmployee(){
        createInstance();
        EmployeeCUD employeeCUD = new EmployeeCUD(etEmpName.getText().toString(),Float.parseFloat(etEmpSalary.getText().toString()),Integer.parseInt(etEmpAge.getText().toString()));
        Call<Void> voidCall = employeeAPI.updateEmployee(Integer.parseInt(etEmpId.getText().toString()),employeeCUD);

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateDeleteActivity.this,"Update successful",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this,"Error: "+t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void deleteEmployee(){
        createInstance();
        Call<Void> voidCall = employeeAPI.deleteEmployee(Integer.parseInt(etEmpId.getText().toString()));

        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(UpdateDeleteActivity.this,"Deleted successfully",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(UpdateDeleteActivity.this,"Error: "+t.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
