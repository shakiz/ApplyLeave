package com.example.applyleave.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applyleave.Utilities.DatabaseHelper;
import com.example.applyleave.Model.DataModel;
import com.example.applyleave.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Spinner leaveTypeSpinner,time1Spinner, time2Spinner,supervisorSpinner,incChargeSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private TextInputEditText reasonInputEditText;
    private ArrayList<String> leaveTypeDataArrayList,pickHourArrayList,superVisorArrayList,inChargeArrayList;
    private TextView startDatePickerTextView,endDatePickerTextView;
    private String startDateStr="",endDateStr="",leaveTypeStr="",timeFirstStr="",timeSecondStr="",supervisorStr="",inChargeStr="",                      reasonStr="",TAG="MainActivity";
    private int totalLeave=0;
    private FloatingActionButton submitButton;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        setDataLeaveType();
        setDataPickHour();
        setDataPickSupervisor();
        setDataLeaveInCharge();
        setAdapterSpinner(leaveTypeSpinner,leaveTypeDataArrayList);
        setAdapterSpinner(time1Spinner,pickHourArrayList);
        setAdapterSpinner(time2Spinner,pickHourArrayList);
        setAdapterSpinner(supervisorSpinner,superVisorArrayList);
        setAdapterSpinner(incChargeSpinner,inChargeArrayList);

        //Setting the on click listener for date picker,Which will be useful to pick a date and set the picked date to the textView
        startDatePickerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int calYear = calendar.get(Calendar.YEAR);
                int calMonth = calendar.get(Calendar.MONTH);
                final int calDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        startDateStr=getDay(year,month,dayOfMonth) + " - "+dayOfMonth+" "+getMonthName(year,month,dayOfMonth);
                        startDatePickerTextView.setText(startDateStr);
                        Log.v(TAG,startDateStr);
                    }
                },calYear,calMonth,calDay);
                datePickerDialog.show();
            }
        });
        endDatePickerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                int calYear=-calendar.get(Calendar.YEAR);
                int calMonth=calendar.get(Calendar.MONTH);
                int calDay=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        endDateStr = getDay(year,month,dayOfMonth) + " - "+dayOfMonth+" "+getMonthName(year,month,dayOfMonth);
                        endDatePickerTextView.setText(endDateStr);
                        Log.v(TAG,endDateStr);
                    }
                },calYear,calMonth,calDay);
                datePickerDialog.show();
            }
        });

        leaveTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                leaveTypeStr=parent.getItemAtPosition(position).toString();
                Log.v(TAG,leaveTypeStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        time1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeFirstStr=parent.getItemAtPosition(position).toString();
                Log.v(TAG,timeFirstStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        time2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                timeSecondStr=parent.getItemAtPosition(position).toString();
                Log.v(TAG,timeSecondStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        supervisorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                supervisorStr=parent.getItemAtPosition(position).toString();
                Log.v(TAG,supervisorStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        incChargeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inChargeStr=parent.getItemAtPosition(position).toString();
                Log.v(TAG,inChargeStr);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reasonStr=reasonInputEditText.getText().toString();
                if (reasonStr.isEmpty() || leaveTypeStr.isEmpty() || timeFirstStr.isEmpty() || timeSecondStr.isEmpty()
                    || startDateStr.isEmpty() || endDateStr.isEmpty() || supervisorStr.isEmpty() || inChargeStr.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Please check your data",Toast.LENGTH_SHORT).show();
                }
                else{
                    DataModel dataModel=new DataModel(2,supervisorStr,inChargeStr,reasonStr,leaveTypeStr,timeFirstStr,timeSecondStr,startDateStr,endDateStr);
                    if (databaseHelper.addRecord(dataModel)==true){
                        Toast.makeText(getApplicationContext(),"Record added successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,LeaveRecordActivity.class));
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Reacord not added",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    //This method returns only the user selected day
    public String getDay(int selectedYear,int selectedMonth,int selectedDay){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
        Date date = new Date(selectedYear, selectedMonth, selectedDay-1);
        Log.v(TAG,"Day : "+simpledateformat.format(date));
        return simpledateformat.format(date);
    }
    //This method returns only the user selected month
    public String getMonthName(int selectedYear,int selectedMonth,int selectedDay){
        SimpleDateFormat simpledateformat = new SimpleDateFormat("MMM");
        Date date=new Date(selectedYear,selectedMonth,selectedDay);
        Log.v(TAG,"Month : "+simpledateformat.format(date));
        return simpledateformat.format(date);
    }

    private void setDataPickHour() {
        pickHourArrayList.add("1st Half");
        pickHourArrayList.add("2nd Half");
    }

    private void setDataLeaveType() {
        leaveTypeDataArrayList.add("Select leave type");
        leaveTypeDataArrayList.add("Casual");
        leaveTypeDataArrayList.add("Sick");
        leaveTypeDataArrayList.add("Urgent");
    }

    private void setDataPickSupervisor() {
        superVisorArrayList.add("Select Supervisor");
        superVisorArrayList.add("David Beckham");
        superVisorArrayList.add("David Malan");
    }

    private void setDataLeaveInCharge() {
        inChargeArrayList.add("Select InCharge");
        inChargeArrayList.add("Mark Wood");
        inChargeArrayList.add("Chris Woakes");
    }

    private void setAdapterSpinner(Spinner spinner,ArrayList<String> dataArrayList) {
        arrayAdapter=new ArrayAdapter<>(this,R.layout.spinner_item,dataArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
    }

    public void initialize(){
        leaveTypeSpinner=findViewById(R.id.leaveTypeSpinnerXML);
        startDatePickerTextView=findViewById(R.id.textViewForStartDateXML);
        time1Spinner=findViewById(R.id.spinnerForTime1XML);
        time2Spinner =findViewById(R.id.spinnerForTime2XML);
        supervisorSpinner=findViewById(R.id.supervisorSpinnerXML);
        incChargeSpinner=findViewById(R.id.inChargeSpinnerXML);
        endDatePickerTextView=findViewById(R.id.textViewForEndDateXML);
        reasonInputEditText=findViewById(R.id.reasonTextInputXML);
        submitButton=findViewById(R.id.submitButtonXML);
        databaseHelper=new DatabaseHelper(getApplicationContext());
        leaveTypeDataArrayList=new ArrayList<>();
        pickHourArrayList=new ArrayList<>();
        superVisorArrayList=new ArrayList<>();
        inChargeArrayList=new ArrayList<>();
    }
}
