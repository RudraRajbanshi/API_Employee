package com.api_employee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import api.EmployeeAPI;
import model.Employee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView tvOutput;
    private final static String BaseURL= "http://dummy.restapiexample.com/api/v1/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvOutput = findViewById(R.id.tvOutput);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseURL).addConverterFactory(GsonConverterFactory.create()).build();
        EmployeeAPI employeeAPI = retrofit.create(EmployeeAPI.class);
        Call<List<Employee>> listCall = employeeAPI.getEmployee();

        listCall.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (!response.isSuccessful()){

                }
                List<Employee> employeeList = response.body();
                for (Employee employee : employeeList){
                    String content = "";
                    content += "id: " +employee.getId() + "\n";
                    content += "Name: " +employee.getEmployee_name() + "\n";
                    content += "Age: " +employee.getEmployee_age() + "\n";
                    content += "Salary: " +employee.getEmployee_salary() + "\n";
                    content += "----------------------" + "\n";

                    tvOutput.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                tvOutput.setText("Error: " + t.getMessage());

            }
        });
    }
}
