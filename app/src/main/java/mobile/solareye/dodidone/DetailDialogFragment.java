package mobile.solareye.dodidone;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import mobile.solareye.dodidone.data.EventModel;

/**
 * Created by Aleksander on 16.04.2015.
 */
public class DetailDialogFragment extends DialogFragment {

    EventModel event;
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy");

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        event = (EventModel) getArguments().getSerializable("Event");

        View v = inflater.inflate(R.layout.detail_fragment, null);

        TextView detail_tv = (TextView) v.findViewById(R.id.detail_tv);

        String name = event.getName();
        String details = event.getDetails();

        getDialog().setTitle(name);

        if (details != null)
            detail_tv.setText(details);

        return v;
    }

}
