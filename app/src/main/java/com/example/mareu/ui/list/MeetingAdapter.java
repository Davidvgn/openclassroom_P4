package com.example.mareu.ui.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;

@SuppressWarnings("unused")
public class MeetingAdapter extends ListAdapter<MeetingViewStateItem, MeetingAdapter.ViewHolder> {

    private final OnMeetingClickedListener listener;

    public MeetingAdapter(OnMeetingClickedListener listener) {
        super(new ViewHolder.ListMeetingItemCallBack());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position), listener);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView meetingRoomTextView;
        private final TextView meetingDayView;
        private final TextView meetingTimeView;
        private final TextView meetingSubjectView;
        private final TextView meetingParticipantsView;
        private final ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingRoomTextView = itemView.findViewById(R.id.meeting_item_tv_meetingRoom);
            meetingDayView = itemView.findViewById(R.id.meeting_item_tv_day);
            meetingTimeView = itemView.findViewById(R.id.meeting_item_tv_time);
            meetingSubjectView = itemView.findViewById(R.id.meeting_item_tv_subject);
            meetingParticipantsView = itemView.findViewById(R.id.meeting_item_tv_emails);
            deleteImageView = itemView.findViewById(R.id.meeting_item_iv_delete);
        }

        public void bind(MeetingViewStateItem item, OnMeetingClickedListener listener) {
            meetingRoomTextView.setText(item.getMeetingRoom());
            meetingDayView.setText(item.getDay());
            meetingTimeView.setText(item.getTime());
            meetingSubjectView.setText(item.getMeetingSubject());
            meetingParticipantsView.setText(item.getParticipants());
            deleteImageView.setOnClickListener(v -> listener.onDeleteMeetingClicked(item.getId()));
        }

        private static class ListMeetingItemCallBack extends DiffUtil.ItemCallback<MeetingViewStateItem> {
            @Override
            public boolean areItemsTheSame(@NonNull MeetingViewStateItem oldItem, @NonNull MeetingViewStateItem newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull MeetingViewStateItem oldItem, @NonNull MeetingViewStateItem newItem) {
                return oldItem.equals(newItem);
            }
        }
    }
}

