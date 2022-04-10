package com.example.studious;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Main code for the application.
 *
 * @author Ben Allen
 * @author Devin Elenbaase
 * @author Bryan VanDyke
 * @version Release 1
 */

//FAKETODO: Make Calendar a seperate item that is the parent for reminders/alarms/todos.
public class MainActivity extends AppCompatActivity {
    /** Calendar Type **/
    public static final int TYPE_CALENDAR = 0;
    /** To Do Type **/
    public static final int TYPE_TODO = 1;
    /** Reminder Type **/
    public static final int TYPE_REMINDER = 2;
    /** Alarm Type **/
    public static final int TYPE_ALARM = 3;

    DatePicker picker;
    Button btnGet;
    TextView tvw;


    /*
        Three moving parts here:
        1) On disk storage in sqlite database. Manipulation of this is
            through the DatabaseHelper class.
        2) In-memory copy of data. This is stored in the ItemAdaptor class.
            In ArrayList<Items> called listItems.
        3) RecyclerView. This displays the data in the activity.

        dataBaseHelper.listItems() loads a copy of the data from the database.

        new ItemAdaptor(MainActivity.this, allItems) creates a new adapter
            and is passed the loaded data.

            It is responsible to provide recycler view data items the format
            defined int items_list.xml backed by ItemViewHolder class.

        dbAdapter.updateDataset(dataBaseHelper.listItems()); update the
            in memory list and notifies the adapter the data has changed.

        RecyclerView. responsible for displaying data as you scroll on screen.

     */


    /** helps manage database. */
    private DataBaseHelper dataBaseHelper;
    /** current data adapter for recyclerView */
    private ItemAdaptor dbAdapter;


    /**
     * Initialize main activity.
     *
     * @param savedInstanceState previous instance data.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* render to screen */
        // call first before findViewById or nothing to find
        setContentView(R.layout.activity_main);

        /* get id's of activity_main */

        /* view item for list items */
        RecyclerView recyclerView = findViewById(R.id.rv_items);

        /* action buttons */
        // Using expanding floating action button from:
        // https://github.com/nambicompany/expandable-fab
        // https://uxdesign.cc/how-to-use-the-expandable-floating-action-button-9c6fdedc4169
        FloatingActionButton fab_calendar = findViewById(R.id.efab_calendar);
        FloatingActionButton fab_reminder = findViewById(R.id.efab_reminder);
        FloatingActionButton fab_alarm = findViewById(R.id.efab_alarm);
        FloatingActionButton fab_todo = findViewById(R.id.efab_todo);

        tvw=(TextView)findViewById(R.id.textView1);
        picker=(DatePicker)findViewById(R.id.datePicker1);
        btnGet=(Button)findViewById(R.id.button1);
        btnGet.setOnClickListener(new View.OnClickListener() {

                                      @Override
                                      public void onClick(View v) {
                                          tvw.setText("Selected Date: " + picker.getDayOfMonth() + "/" + (picker.getMonth() + 1) + "/" + picker.getYear());
                                      }
                                  });



        /* RecyclerView needs a layout manager. */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);

        /* adapter layout doesn't change in size or width. optimize recycler. */
        recyclerView.hasFixedSize();

        /* manage database */
        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        /* get all items in database. */
        ArrayList<Items> allItems = dataBaseHelper.listItems();
        if (allItems.size() > 0)
        {
            recyclerView.setVisibility(View.VISIBLE);

            /* tell adapter to use allItems as datasource. */
            dbAdapter = new ItemAdaptor(MainActivity.this, allItems);

            /* user this adapter for recyclerView. */
            recyclerView.setAdapter(dbAdapter);

            /* allow RecyclerView item to swipe left or right */
            new ItemTouchHelper(
                    new ItemTouchHelper.SimpleCallback(
                            0,
                            /* set and item swipeable left or right. */
                            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
                    ) {
                        /* draggable callback. not used. */
                        @Override
                        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                            return false;
                        }

                        /* swipeable callback. when an item is swiped this is called. */
                        @Override
                        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                            /* When ItemAdapter binds the data I also set the (optional) Tag
                            to the Primary (ID) key of the database. That way we can
                            get the exact key needed to access the item in the db.
                             */
                            int id = (int) viewHolder.itemView.getTag();
                            /* delete using items primary key value. */
                            delete_item(id);
                        }
                    }).attachToRecyclerView(recyclerView);

        }
        else
        {
            /* Empty Database. */
            recyclerView.setVisibility(View.GONE);
            Toast.makeText(
                            MainActivity.this,
                            "There are no items in the database. Start adding now",
                            Toast.LENGTH_LONG)
                    .show();
        }

        /* set the listeners for the FAB buttons.
         * When a button is clicked this callback is called. */
        fab_calendar.setOnClickListener(view -> addTaskDialog(TYPE_CALENDAR, getString(R.string.typeCalendar)));
        fab_reminder.setOnClickListener(view -> addTaskDialog(TYPE_REMINDER, getString(R.string.typeReminder)));
        fab_alarm.setOnClickListener(view -> addTaskDialog(TYPE_ALARM, getString(R.string.typeAlarm)));
        fab_todo.setOnClickListener(view -> addTaskDialog(TYPE_TODO, getString(R.string.typeTodo)));
    }


    /**
     * Delete item from database.
     * Update internal data.
     * Refresh recycler view .
     *
     * @param id row id of item to delete.
     */
    private void delete_item(final int id) {
        /* delete that item from database. */
        dataBaseHelper.deleteItem(id);
        /* updateDataset() slaps the dbAdapter upside the head to tell
        it the data has changed. There's supposed to be a more elegant way to
        do this, but I haven't found it.

        listItems() is just fetching a fresh copy of the data, and
        updateDataset() will bind it to the adapter.
         */
        dbAdapter.updateDataset(dataBaseHelper.listItems());
    }

    /**
     * Add item dialog.
     * Add item to database.
     * Update internal data.
     * Refresh recycler view .
     *
     * @param type      item type
     * @param type_name title or description of item.
     */
    private void addTaskDialog(final int type, final String type_name) {
        LayoutInflater inflater = LayoutInflater.from(this);
        /* use add_item dialog */
        View subView = inflater.inflate(R.layout.add_item, null);

        /* get edit text field id */
        final EditText titleField = subView.findViewById(R.id.et_title);
        final EditText notesField = subView.findViewById(R.id.et_notes);
        final EditText startDateField = subView.findViewById(R.id.et_startdate);
        final EditText endDateField = subView.findViewById(R.id.et_enddate);

        /* use alert dialog */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        /* set title */
        builder.setTitle("Add " + type_name);
        /* user our add_item activity */
        builder.setView(subView);

        builder.create();
        /* set buttons and names */
        builder.setPositiveButton(getString(R.string.ButtonAdd) + " " + type_name,
                (dialog, which) -> {
                    /* read entered input */
                    final String title = titleField.getText().toString().trim();
                    final String notes = notesField.getText().toString().trim();
                    final String start = startDateField.getText().toString().trim();
                    final String end = endDateField.getText().toString().trim();
                    if (!TextUtils.isEmpty(title))
                    {
                        Items newItem = new Items(-1, title, type, notes, start, end);
                        /* add to database */
                        dataBaseHelper.addItem(newItem);
                        /* update dataset in adapter */
                        dbAdapter.updateDataset(dataBaseHelper.listItems());
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,
                                "Something went wrong. Check your input values",
                                Toast.LENGTH_LONG).show();
                    }
                });
        builder.setNegativeButton(getString(R.string.ButtonCancel),
                (dialog, which) -> Toast.makeText(
                        MainActivity.this,
                        "Task cancelled",
                        Toast.LENGTH_LONG).show());
        /* show our new fancy dialog. */
        builder.show();
    }

    /**
     * Clean up before application is closed.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null)
        {
            dataBaseHelper.close();
        }
    }


}
