package algonquin.cst2335.myapplicatio116;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import algonquin.cst2335.myapplicatio116.data.ChatRoomViewModel;
import algonquin.cst2335.myapplicatio116.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.myapplicatio116.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ArrayList<String> messages;
    ChatRoomViewModel chatModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            chatModel.messages.postValue( messages = new ArrayList<String>());
        }


        binding.sendButton.setOnClickListener(click->{
            messages.add(binding.textInput.getText().toString());
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });
        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                String obj = messages.get(position);
                holder.messageText.setText(obj);
                holder.timeText.setText("");

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }
        });

        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

}