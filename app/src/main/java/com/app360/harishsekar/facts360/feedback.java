package com.app360.harishsekar.facts360;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * Created by harishsekar on 9/9/17.
 */

public class feedback extends Fragment {

    EditText email_subject,email_message;
    ImageButton email_send_Button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feedback_fragment,container,false);


        email_subject = view.findViewById(R.id.subject_ID);
        email_message = view.findViewById(R.id.message_ID);

        email_send_Button = view.findViewById(R.id.email_sendbutton_ID);

        email_send_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String toSubject = email_subject.getText().toString();
                String toMessage = email_message.getText().toString();

                Intent email =  new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL,new String[] { "the360@gmail.com" });
                email.putExtra(Intent.EXTRA_SUBJECT,toSubject);
                email.putExtra(Intent.EXTRA_TEXT,toMessage);

                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email,"Facts: 360  ~ Choose App to Send Feedback"));
            }
        });


        return view;
    }
}
