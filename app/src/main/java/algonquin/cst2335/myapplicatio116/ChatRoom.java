package algonquin.cst2335.myapplicatio116;

//import algonquin.cst2335.myapplicatio116.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.myapplicatio116.data.ChatRoomViewModel;
import algonquin.cst2335.myapplicatio116.databinding.ActivityChatRoomBinding;
import algonquin.cst2335.myapplicatio116.databinding.ReceiveMessageBinding;
import algonquin.cst2335.myapplicatio116.databinding.SentMessageBinding;

public class ChatRoom extends AppCompatActivity {
    ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter ;
    static ChatMessageDAO mDAO;
    ArrayList<ChatMessage> messages;
//    ChatRoomViewModel chatModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        //OPEN A DATABASE
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name")
                .fallbackToDestructiveMigration()
                .build();
        mDAO = db.cmDAO();

//        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
//        messages = chatModel.messages.getValue();
        if(messages == null)
        {
            //setValue and postValue
//            chatModel.messages.setValue( messages = new ArrayList<>());
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recyclerView.setAdapter( myAdapter =new RecyclerView.Adapter<MyRowHolder>() {
                    @NonNull
                    @Override
                    public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        if(viewType == 1){
                            ReceiveMessageBinding binding = ReceiveMessageBinding.inflate(getLayoutInflater());
                            return new MyRowHolder(binding.getRoot());
                        }else{
                            SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                            return new MyRowHolder(binding.getRoot());
                        }
                    }

                    @Override
                    public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                        ChatMessage obj = messages.get(position);
                        holder.messageText.setText(obj.getMessage());
                        holder.timeText.setText(obj.getTimeSent());
                    }
                    @Override
                    public int getItemCount() {
                        return messages.size();
                    }
                    @Override
                    public int getItemViewType(int position) {
                        if(messages.get(position).isSentButton() == true){
                            return 0;
                        }else{
                            return 1;
                        }
                    }
                }));//You can then load the RecyclerView
            });

        }



        binding.sendButton.setOnClickListener(click->{
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage m = new ChatMessage();
            m.setMessage(binding.textInput.getText().toString());
            m.setTimeSent(currentDateandTime);
            m.setSentButton(true);
            messages.add(m);

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });

        binding.receiveButton.setOnClickListener(click->{
            SimpleDateFormat sdf = new SimpleDateFormat("EE, dd-MM-yyyy hh-mm-ss a");
            String currentDateandTime = sdf.format(new Date());
            ChatMessage m = new ChatMessage();
            m.setMessage(binding.textInput.getText().toString());
            m.setTimeSent(currentDateandTime);
            m.setSentButton(false);
            messages.add(m);

            myAdapter.notifyItemInserted(messages.size()-1);
            binding.textInput.setText("");
        });


        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);

        super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.deleteIcon) {
                // 处理删除菜单项的点击事件
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete all messages?")
                        .setTitle("Questions: ")
                        .setNegativeButton("No", (dialog, cl)->{})
                        .setPositiveButton("Yes", (dialog, cl)->{
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() ->
                            {//delete from another thread
                                mDAO.deleteAll();
                                myAdapter.notifyDataSetChanged();
                            });
                        });
                builder.show();

                return true;
            } else if (item.getItemId() == R.id.aboutIcon) {
                // 处理关于菜单项的点击事件
                Toast.makeText(getApplicationContext(), "Version 1.0, created by Jiaying Qiu", Toast.LENGTH_LONG).show();

                return true;
            } else {
                return super.onOptionsItemSelected(item);
            }
        }

//        switch( item.getItemId() )
//        {
//            case R.id.deleteIcon:
//                //put your ChatMessage deletion code here. If you select this item, you should show the alert dialog
//                //asking if the user wants to delete this message.
//                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
//                builder.setMessage("Do you want to delete all messages?")
//                        .setTitle("Questions: ")
//                        .setNegativeButton("No", (dialog, cl)->{})
//                        .setPositiveButton("Yes", (dialog, cl)->{
//                            Executor thread = Executors.newSingleThreadExecutor();
//                            thread.execute(() ->
//                            {//delete from another thread
//                                mDAO.deleteAll();
//                                myAdapter.notifyDataSetChanged();
//                            });
//                        });
//
//                builder.show();
//                return true;
//
//            case R.id.aboutIcon:
//                Toast.makeText(getApplicationContext(), "Version 1.0, created by Jiaying Qiu", Toast.LENGTH_LONG).show();
//            return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }



    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView timeText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(click->{
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder( ChatRoom.this );
                builder.setMessage("Do you want to delete the message: " + messageText.getText())
                        .setTitle("Questions: ")
                        .setNegativeButton("No", (dialog, cl)->{})
                        .setPositiveButton("Yes", (dialog, cl)->{
                            ChatMessage removedMessage = messages.get(position);
                            Executor thread = Executors.newSingleThreadExecutor();
                            thread.execute(() ->
                                    {//delete from another thread
                                        mDAO.deleteMessage(removedMessage);
                                    });

                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);
                            Snackbar.make(messageText,"You deleted message #" + position, Snackbar.LENGTH_LONG)
                                    .setAction("Undo",clk->{
                                        messages.add(position,removedMessage);
                                        myAdapter.notifyItemInserted(position);
                                    }).show();
                }).create().show();

            });
            messageText = itemView.findViewById(R.id.message);
            timeText = itemView.findViewById(R.id.time);
        }
    }

}