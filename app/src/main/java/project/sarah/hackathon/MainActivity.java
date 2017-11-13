package project.sarah.hackathon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.csv.*;

public class MainActivity extends AppCompatActivity {

    Button btn_sub;
    EditText ed0,ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9;
    String st0,st1,st2,st3,st4,st5,st6,st7,st8,st9;

    double y_norm = 4265449.70874;
    double intercept_ = -0.000992267713707;
    double y_pred;
    List<Double> coef_=new ArrayList<Double>();
    List<Double> x_user=new ArrayList<Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed0=(EditText)findViewById(R.id.ed0);
        ed1=(EditText)findViewById(R.id.ed1);
        ed2=(EditText)findViewById(R.id.ed2);
        ed3=(EditText)findViewById(R.id.ed3);
        ed4=(EditText)findViewById(R.id.ed4);
        ed5=(EditText)findViewById(R.id.ed5);
        ed6=(EditText)findViewById(R.id.ed6);
        ed7=(EditText)findViewById(R.id.ed7);
        ed8=(EditText)findViewById(R.id.ed8);
        ed9=(EditText)findViewById(R.id.ed9);

        //testCSV();

        st0=ed0.getText().toString();
        st1=ed1.getText().toString();
        st2=ed2.getText().toString();
        st3=ed3.getText().toString();
        st4=ed4.getText().toString();
        st5=ed5.getText().toString();
        st6=ed6.getText().toString();
        st7=ed7.getText().toString();
        st8=ed8.getText().toString();
        st9=ed9.getText().toString(); // get user's input values

        x_user.add(Double.parseDouble(st0));
        x_user.add(Double.parseDouble(st1));
        x_user.add(Double.parseDouble(st2));
        x_user.add(Double.parseDouble(st3));
        x_user.add(Double.parseDouble(st4));
        x_user.add(Double.parseDouble(st5));
        x_user.add(Double.parseDouble(st6));
        x_user.add(Double.parseDouble(st7));
        x_user.add(Double.parseDouble(st8));
        x_user.add(Double.parseDouble(st9)); // put user's input values at x_user list

        readCSV();
        final Double final_price=getPrice();

        btn_sub=(Button)findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, ResultActivity.class);
                intent.putExtra("final",final_price);
                startActivity(intent);
            }
        });
    }

    //It's function which read test.csv's data and doing price prediction
    //It is just for testset.
    public void testCSV(){
        try{
            InputStreamReader ir2 = new InputStreamReader(getAssets().open("test.csv"));
            Reader in2 = ir2;
            CSVParser parser2 = new CSVParser( in2, CSVFormat.EXCEL );
            List<CSVRecord> list2 = parser2.getRecords();

            CSVRecord row=list2.get(1);
            ed0.setText(row.get(4));
            ed1.setText(row.get(34));
            ed2.setText(row.get(37));
            ed3.setText(row.get(38));
            ed4.setText(row.get(43));
            ed6.setText(row.get(44));
            ed5.setText(row.get(46));
            ed7.setText(row.get(62));
            ed8.setText(row.get(66));
            ed9.setText(row.get(67));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //reading coef_.csv file for calculation
    public void readCSV(){
        try {
            InputStreamReader ir = new InputStreamReader(getAssets().open("coef_.csv"));
            Reader in = ir;
            CSVParser parser = new CSVParser( in, CSVFormat.EXCEL );
            List<CSVRecord> list = parser.getRecords();

           for( CSVRecord row : list ) {
               for (String entry : row) {
                   Double temp_coef = Double.parseDouble(entry);
                   coef_.add(temp_coef);
               }
           }

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // ============================================================================= //
    // Linear Regression model for house price prediction                            //
    // Author: Team Vision                                                           //
    // Date: Nov-11-2017                                                             //
    // ============================================================================= //
    ///////this is the fucntion which does final calculation for get price prediction.
    /*
    Usage:

    y_pred = (x * coef_ + intercept_) * y_norm

    x           -- user input, 1x10 vector contains ['LotArea', 'BsmtFinSF1', 'BsmtUnfSF', 'TotalBsmtSF', '1stFlrSF', '2ndFlrSF', 'GrLivArea', 'GarageArea', 'WoodDeckSF', 'OpenPorchSF']
    coef_       -- coefficients of linear regression model, read from coef_.csv file
    intercept_  -- intercept of linear regression model, intercept_ = -0.000992267713707
    y_norm      -- norm of training y, y_norm = 4265449.70874
    y_pred      -- predicted house price
    */

    public double getPrice(){
        Double sum_temp=0.0;
        for(int i=0;i<x_user.size();i++){
            sum_temp+=x_user.get(i)*coef_.get(i);
        }

        y_pred=(sum_temp+intercept_)*y_norm;

        return y_pred;
    }

}
