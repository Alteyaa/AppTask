package com.apptask;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apptask.model.Task;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    public List<Task> list;

    private ClickListener clickListener;

    public interface ClickListener{

        void onItemClick (int pos);
        void onItemLongClick(int pos);

    }

    public TaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View v = layoutInflater.inflate(R.layout.list_task, viewGroup, false);
        TaskViewHolder taskViewHolder = new TaskViewHolder(v);
        return taskViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
         taskViewHolder.onBind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {

        TextView mTextviewTitle;
        TextView mTextviewDescription;
        Task task;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextviewTitle = itemView.findViewById(R.id.view_holder_title);
            mTextviewDescription = itemView.findViewById(R.id.view_holder_description);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.onItemClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    clickListener.onItemLongClick(getAdapterPosition());
                    return true;
                }
            });

        }
            public void onBind(Task task){
                this.task= task;
                mTextviewTitle.setText(task.title);
                mTextviewDescription.setText(task.description);

        }
    }
}
