package algonquin.cst2335.myapplicatio116.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.myapplicatio116.data.MainViewModel;
import algonquin.cst2335.myapplicatio116.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;
    private MainViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        model = new ViewModelProvider(this).get(MainViewModel.class);
        model.editString.observe(this, s-> {
            variableBinding.mytext.setText("Your edit text has: " + s);
        });

        model.isSelected.observe(this, selected->{
            variableBinding.checkBox.setChecked(selected);
            variableBinding.radioButton.setChecked(selected);
            variableBinding.switch1.setChecked(selected);
            Toast toast = Toast.makeText(this, "The value is now: " + selected, Toast.LENGTH_LONG);
            toast.show();
        });

        //click listener for button to change textview
        variableBinding.mybutton.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.myedittext.getText().toString());
        });

        //onCheckedChangeListner to togggle choice
        variableBinding.checkBox.setOnCheckedChangeListener((btn, isChecked)->{
            model.isSelected.postValue(isChecked);
        });
        variableBinding.radioButton.setOnCheckedChangeListener((btn, isChecked)->{
            model.isSelected.postValue(isChecked);
        });
        variableBinding.switch1.setOnCheckedChangeListener((btn, isChecked)->{
            model.isSelected.postValue(isChecked);
        });

        //onClickListener to show image button size
        variableBinding.myimagebutton.setOnClickListener(click->{
            Toast toast = Toast.makeText(this, "The width =" + variableBinding.myimagebutton.getWidth()
                    + " and height = " + variableBinding.myimagebutton.getHeight()
                    , Toast.LENGTH_LONG);
            toast.show();
        });

    }

}