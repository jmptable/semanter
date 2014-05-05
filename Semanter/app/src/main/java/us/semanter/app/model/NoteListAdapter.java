package us.semanter.app.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import us.semanter.app.R;

public class NoteListAdapter extends BaseAdapter {
    private Context mContext;
    private int resource;

    private List<Note> notes;

    public NoteListAdapter(Context c, int resource, List<Note> notes) {
        mContext = c;
        this.resource = resource;

        this.notes = notes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup thumbnailView;

        Note note = (Note)getItem(position);

        // create
        LayoutInflater inflater;
        inflater = LayoutInflater.from(mContext);
        thumbnailView = (ViewGroup)inflater.inflate(resource, null);

        // adjust for GridView
        thumbnailView.setLayoutParams(new GridView.LayoutParams(85, 85));
        thumbnailView.setPadding(8, 8, 8, 8);

        // add data
        // TODO short date formatting
        DateFormat dateFormat = new SimpleDateFormat("E");

        ((TextView)thumbnailView.findViewById(R.id.thumbnail_note_date)).setText(dateFormat.format(note.getDate()));

        // TODO task numbers as enum
        ImageView progressImage = (ImageView)thumbnailView.findViewById(R.id.thumbnail_note_progress);
        switch(note.getResultCount()) {
            case 0:
                progressImage.setImageResource(R.drawable.ic_progress_1);
                break;
            case 1:
                progressImage.setImageResource(R.drawable.ic_progress_2);
                break;
            case 2:
                progressImage.setImageResource(R.drawable.ic_progress_3);
                break;
            case 3:
                progressImage.setImageResource(R.drawable.ic_progress_4);
                break;
            case 4:
                ((ViewSwitcher)thumbnailView.findViewById(R.id.thumbnail_note_switcher)).showNext();
                break;
        }

        return thumbnailView;
    }

    @Override
    public boolean  areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        if(position < notes.size()) {
            return notes.get(position).getResultCount() > 0;
        } else {
            return false;
        }
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        if(position < notes.size())
            return notes.get(position);
        else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
