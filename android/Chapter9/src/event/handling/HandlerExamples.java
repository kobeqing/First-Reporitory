package event.handling;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HandlerExamples extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_examples);
        
        Button button = (Button)findViewById(R.id.testButton);
        button.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.handler_examples, menu);
        return true;
    }


	@Override
	public void onClick(View arg0) {
		TextView text = (TextView)findViewById(R.id.testText);
		text.setText("BUTTON HAS BEEN CLICEKED. EVENT PROCESSED.");
		
	}
    
}
