package austin_d.a100daysofcodetracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Main_Activity extends AppCompatActivity
{
    TextView txtVDaysRemaining = null;
    TextView txtVstartingDate = null;
    TextView txtVendingDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txtVDaysRemaining = (TextView)findViewById(R.id.txtVDaysToGo);
        txtVstartingDate = (TextView)findViewById(R.id.txtVStartDate);
        txtVendingDate = (TextView)findViewById(R.id.txtVEndDate);

        Button btnChangeStartingDate = (Button)findViewById(R.id.btnChangeStartDate);
        btnChangeStartingDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                changeStartDate();
            }
        });

        setDates();
    }

    private void setDates()
    {
        String[] dates = getDates();

        txtVDaysRemaining.setText(subtractDate(dates));
        txtVstartingDate.setText(dates[0]);
        txtVendingDate.setText(dates[1]);
    }

    private String[] getDates()
    {
        try
        {
            String[] dates = new String[3];
            BufferedReader br = new BufferedReader(new InputStreamReader(this.openFileInput(FirstTimeSetup_Activity.FIRST_TIME_SETUP_FILE_LOCATION)));
            String line; int index = 0;
            while((line = br.readLine()) != null)
            {
                dates[index] = line;
                index++;
            }
            br.close();

            // Adds today's date to the array
            Date today = new Date();
            dates[2] = new SimpleDateFormat("MM/dd/yy", Locale.US).format(today);
            return dates;
        }
        catch(Exception ex)
        {
            txtVDaysRemaining.setText(ex.getMessage());
            return new String[0];
        }
    }

    // Returns the difference in days between two dates
    private String subtractDate(String[] dates)
    {
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy",Locale.US);
            Date today = sdf.parse(dates[2]);
            Date end = sdf.parse(dates[1]);

            long diff = end.getTime() - today.getTime(); // Returns time in milliseconds
            long daysLeft = ((((diff / 1000) / 60) / 60) / 24);

            return String.valueOf(daysLeft);
        }catch (Exception ex)
        {
            return ex.getMessage();
        }
    }

    private void changeStartDate()
    {
        try
        {
            File startDate = new File(Main_Activity.this.getFilesDir().getAbsolutePath(), FirstTimeSetup_Activity.FIRST_TIME_SETUP_FILE_LOCATION);

            if(startDate.delete())
            {
                startActivity(new Intent(Main_Activity.this, FirstTimeSetup_Activity.class));
            }
            else
            {
                Toast.makeText(Main_Activity.this, "Couldn't find starting date.\nTry clearing this app's data in the Application Manager", Toast.LENGTH_LONG).show();
            }
        } catch(Exception ex)
        {
            Toast.makeText(Main_Activity.this, "Couldn't find starting date.\nTry clearing this app's data in the Application Manager", Toast.LENGTH_LONG).show();
        }

    }
}
