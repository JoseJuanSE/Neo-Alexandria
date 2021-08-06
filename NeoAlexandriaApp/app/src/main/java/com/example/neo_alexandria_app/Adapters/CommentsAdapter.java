package com.example.neo_alexandria_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.neo_alexandria_app.DataModels.Comment;
import com.example.neo_alexandria_app.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "CommentsAdapter";

    Context context;
    List<Comment> comments;

    public CommentsAdapter(Context context, List<Comment> comments) {
        this.context = context;
        this.comments = comments;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        Comment comment = comments.get(position);
        ((ViewHolder) holder).bind(comment);
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAuthor;
        TextView tvTimeStamp;
        TextView tvContent;
        ImageView btnLike;
        ImageView ivAuthor;
        RelativeLayout relative;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvTimeStamp = itemView.findViewById(R.id.tvTimeStamp);
            tvContent = itemView.findViewById(R.id.tvContent);
            btnLike = itemView.findViewById(R.id.btnLike);
            ivAuthor = itemView.findViewById(R.id.ivAuthor);
            relative = itemView.findViewById(R.id.relative);
        }

        public void bind(Comment comment) {
            tvAuthor.setText(comment.getUserName());
            tvTimeStamp.setText(comment.getDate());
            tvContent.setText(comment.getContent());
            btnLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (comment.isLiked()) {
                        comment.setLiked();
                        btnLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ufi_heart));
                    } else {
                        comment.setLiked();
                        btnLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ufi_heart_active));
                    }
                }
            });
            if (!comment.getIvProfileURL().isEmpty()) {
                Glide.with(context).load(comment.getIvProfileURL()).into(ivAuthor);
            }
        }
    }
}
