package austin_d.a100daysofcodetracker;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class FirstTimeSetup_Activity extends AppCompatActivity
{
    public static final String FIRST_TIME_SETUP_FILE_LOCATION = "StartDate.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(firstTimeSetup())
        {
            setContentView(R.layout.first_time_setup);

            final EditText eTxtStartDate = (EditText)findViewById(R.id.eTxtStartDate);
            final Calendar cal = Calendar.getInstance();
            final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy", Locale.US);
            final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() // Necessary to have Date Picker when Edit Text is clicked on
            {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                {
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    eTxtStartDate.setText(sdf.format(cal.getTime()));
                }
            };

            eTxtStartDate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    new DatePickerDialog(FirstTimeSetup_Activity.this, date,  cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
                }
            });

            Button btnSubmitStartDate = (Button)findViewById(R.id.btnSubmitStartDate);
            btnSubmitStartDate.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    try
                    {
                        String sStartDate = eTxtStartDate.getText().toString();

                        if(!checkStartDate(sStartDate)) throw new DateException(DateException.dateFormatException());

                        Date dtStartDate = sdf.parse(sStartDate);

                        // Adds 100 days to the start date
                        cal.setTime(dtStartDate);
                        cal.add(Calendar.DAY_OF_YEAR, 100);

                        // Converts Date to proper formatting before it is written to a file.
                        String output = sdf.format(dtStartDate) + "\n" + sdf.format(cal.getTime());

                        FileOutputStream foStream = FirstTimeSetup_Activity.this.openFileOutput(FIRST_TIME_SETUP_FILE_LOCATION, Context.MODE_PRIVATE);
                        foStream.write(output.getBytes());
                        foStream.close();

                        startActivity(new Intent(FirstTimeSetup_Activity.this, Main_Activity.class));

                    }catch(DateException | ParseException | IOException ex)
                    {
                        Toast.makeText(FirstTimeSetup_Activity.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            startActivity(new Intent(FirstTimeSetup_Activity.this, Main_Activity.class));
        }
    }

    private boolean firstTimeSetup()
    {
        return !(this.getFileStreamPath(FIRST_TIME_SETUP_FILE_LOCATION).exists());
    }

    private boolean checkStartDate(String date)
    {
        // Checks the dates length to make sure it is == to 8
        if(date.length() == 8)
        {
            // Counts the '/'s in the date to make sure that there are only 2
            int counter = 0;
            for(int i = 0; i < date.length(); ++i)
            {
                if(date.charAt(i) == '/')
                {
                    counter++;
                }
            }

            if(counter == 2)
            {
                // Validates the month and day to make sure date is valid
                if(Integer.parseInt(date.substring(0,2)) < 13 && Integer.parseInt(date.substring(3,5)) < 32)
                {
                    return true;
                }
            }
            else return false;
        }

        return false;
    }
}
