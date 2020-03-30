package com.larryhsiao.lhfaces;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.larryhsiao.lhfaces.config.SP;
import com.larryhsiao.lhfaces.config.SecondColor;
import com.silverhetch.aura.storage.SPCeres;
import com.silverhetch.aura.view.measures.DP;
import com.silverhetch.clotho.storage.Ceres;
import org.jraf.android.androidwearcolorpicker.ColorPickActivity;

public class ConfigActivity extends Activity {
    private static final int REQUEST_PICK_COLOR = 1001;
    private SecondColor secondColor;
    private ConfigAdapter adapter;
    private Ceres storage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.watchface_config);
        storage = new SPCeres(new SP(this).value());
        secondColor = new SecondColor(storage);
        RecyclerView list = findViewById(R.id.watchFaceConfig_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter = new ConfigAdapter());
    }

    public class ConfigAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int ITEM_TYPE_HEADER = 1001;
        private static final int ITEM_TYPE_SECOND_COLOR = 12002;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int type) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            switch (type) {
                case ITEM_TYPE_HEADER:
                    TextView header = ((TextView) inflater.inflate(
                        android.R.layout.simple_list_item_1,
                        parent,
                        false
                    ));
                    header.setGravity(Gravity.CENTER);
                    header.setPadding(0, 0, 0, 0);
                    return new RecyclerView.ViewHolder(header) {};
                default:
                    return new RecyclerView.ViewHolder(
                        inflater.inflate(
                            android.R.layout.simple_list_item_1,
                            parent,
                            false
                        )
                    ) {};
            }
        }

        @Override
        public void onBindViewHolder(
            @NonNull RecyclerView.ViewHolder holder, int position) {
            switch (getItemViewType(position)) {
                case ITEM_TYPE_HEADER:
                    ((TextView) holder.itemView).setText(
                        holder.itemView.getContext()
                            .getString(R.string.config));
                    break;
                case ITEM_TYPE_SECOND_COLOR:
                    TextView text = ((TextView) holder.itemView);
                    text.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_dot,
                        0, 0, 0
                    );
                    text.setCompoundDrawablePadding(
                        ((int) new DP(
                            text.getContext(),
                            3
                        ).px())
                    );

                    text.setCompoundDrawableTintList(
                        ColorStateList.valueOf(secondColor.color()));
                    text.setText(holder.itemView.getContext()
                        .getString(R.string.second_color));
                    text.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final Intent intent =
                                new ColorPickActivity.IntentBuilder()
                                    .oldColor(secondColor.color())
                                    .build(v.getContext());
                            startActivityForResult(intent, REQUEST_PICK_COLOR);
                        }
                    });
                    break;
                default:
                    break;
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            switch (position) {
                case 0:
                    return ITEM_TYPE_HEADER;
                case 1:
                    return ITEM_TYPE_SECOND_COLOR;
                default:
                    return super.getItemViewType(position);
            }
        }
    }

    @Override
    protected void onActivityResult(int request, int result, Intent data) {
        super.onActivityResult(request, result, data);
        if (request == REQUEST_PICK_COLOR && result == RESULT_OK) {
            secondColor.newColor(
                ColorPickActivity.Companion.getPickedColor(data)
            );
            adapter.notifyItemChanged(1);
        }
    }
}
