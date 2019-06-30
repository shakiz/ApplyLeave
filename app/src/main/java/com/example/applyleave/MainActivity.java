package com.example.applyleave;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Spinner leaveTypeSpinner,time1Spinner, time2Spinner,supervisorSpinner,incChargeSpinner;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> leaveTypeDataArrayList,pickHourArrayList,superVisorArrayList,inChargeArrayList;
    private TextView startDatePickerTextView,endDatePickerTextView;

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
    }

    private void setDataPickHour() {
        pickHourArrayList.add("Select Hour");
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

    private void setAdapterSpinner(Spinner Spinner,ArrayList<String> dataArrayList) {
        arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,dataArrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner.setAdapter(arrayAdapter);
    }

    public void initialize(){
        leaveTypeSpinner=findViewById(R.id.leaveTypeSpinnerXML);
        startDatePickerTextView=findViewById(R.id.textViewForStartDateXML);
        time1Spinner=findViewById(R.id.spinnerForTime1XML);
        time2Spinner =findViewById(R.id.spinnerForTime2XML);
        supervisorSpinner=findViewById(R.id.supervisorSpinnerXML);
        incChargeSpinner=findViewById(R.id.inChargeSpinnerXML);
        endDatePickerTextView=findViewById(R.id.textViewForEndDateXML);
        leaveTypeDataArrayList=new ArrayList<>();
        pickHourArrayList=new ArrayList<>();
        superVisorArrayList=new ArrayList<>();
        inChargeArrayList=new ArrayList<>();
    }
}
