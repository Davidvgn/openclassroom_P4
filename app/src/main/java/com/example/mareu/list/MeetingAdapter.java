package com.example.mareu.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.request.RequestOptions;
import com.example.mareu.R;
import com.example.mareu.data.Meeting;
import com.example.mareu.data.MeetingRepository;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


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

        private final ImageView meetingRoomView;
        private final TextView meetingHourView;
        private final TextView meetingMinView;
        private final TextView meetingSubjectView;
        //        private final TextView meetingParticipantsView;
        private final ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            meetingRoomView = itemView.findViewById(R.id.meeting_item_iv_meetingRoom);
            meetingHourView = itemView.findViewById(R.id.meeting_item_tv_hour);
            meetingMinView = itemView.findViewById(R.id.meeting_item_tv_min);
            meetingSubjectView = itemView.findViewById(R.id.meeting_item_tv_subject);
//            meetingParticipantsView = itemView.findViewById(R.id.meeting_item_tv_emails);
            deleteImageView = itemView.findViewById(R.id.meeting_item_iv_delete);
        }

        public void bind(MeetingViewStateItem item, OnMeetingClickedListener listener) {
            Glide.with(meetingRoomView)
                    .load(item.getMeetingRoom())
                    .apply(RequestOptions.circleCropTransform())
                    .into(meetingRoomView);
            meetingHourView.setText(item.getHour());
            meetingMinView.setText(item.getMin());
            meetingSubjectView.setText(item.getMeetingSubject());
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

