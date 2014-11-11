package de.romschubser;

import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Container {
    private View view = null;
    private TextView txtHeader = null;
    private LinearLayout layoutContent = null;

    public Container(View view, int id) {
        this.view = view.findViewById(id);
        this.txtHeader = (TextView)this.view.findViewById(R.id.txtHeader);
        this.layoutContent = (LinearLayout)this.view.findViewById(R.id.layoutContent);
    }

    public void setHeader(String text) {
        this.txtHeader.setText(text);
    }

    public void addEntry(String title, String text) {
        TextView txtTitle = new TextView(this.view.getContext());
        txtTitle.setText(title+": ");
        txtTitle.setTypeface(txtTitle.getTypeface(), Typeface.BOLD);

        TextView txtContent = new TextView(this.view.getContext());
        txtContent.setText(text);

        GridLayout grid = new GridLayout(this.view.getContext());
        grid.setColumnCount(2);
        grid.setRowCount(1);
        grid.addView(txtTitle);
        grid.addView(txtContent);

        this.layoutContent.addView(grid);
    }

    public void addCheckbox(String title, String text) {
        CheckBox chkTitle = new CheckBox(this.view.getContext());
        chkTitle.setText(title+": ");
        chkTitle.setTypeface(chkTitle.getTypeface(), Typeface.BOLD);

        TextView txtContent = new TextView(this.view.getContext());
        txtContent.setText(text);

        GridLayout grid = new GridLayout(this.view.getContext());
        grid.setColumnCount(2);
        grid.setRowCount(1);
        grid.addView(chkTitle);
        grid.addView(txtContent);

        this.layoutContent.addView(grid);
    }

    public void addButton(String title) {
        Button button = new Button(this.view.getContext());
        button.setText(title);

        this.layoutContent.addView(button);
    }
}
