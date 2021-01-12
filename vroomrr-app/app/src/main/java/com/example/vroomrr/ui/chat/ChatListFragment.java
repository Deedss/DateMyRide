package com.example.vroomrr.ui.chat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vroomrr.Car;
import com.example.vroomrr.Chat;
import com.example.vroomrr.Cryptography;
import com.example.vroomrr.R;
import com.example.vroomrr.ServerCallback;
import com.example.vroomrr.ServerConnection;
import com.example.vroomrr.User;
import com.example.vroomrr.ui.car.CarActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ChatListFragment extends Fragment implements ChatListViewAdapter.OnActionListener, ServerCallback {
    private View root;
    // Add RecyclerView member
    private RecyclerView recyclerView;
    private ArrayList<Chat> chats = new ArrayList<>();
    private ChatListViewAdapter adapter;

    private String currentUserID;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //TODO: Add actual logged in user here from main

        SharedPreferences SP = Cryptography.getEncryptedSharedPreferences(this.getActivity());
        SharedPreferences.Editor editor = SP.edit();
        editor.putString(String.valueOf(R.string.SessionId),"c396a4f5-3fa5-4e67-b6fa-75aa8cf23c27");
        editor.apply();
        currentUserID = "47b6b871-26ce-43f3-9dc0-17a6ccb0505a";

        //TODO: Remove this garbage

        ServerConnection.getChats(this, this.getActivity());

        root = inflater.inflate(R.layout.fragment_chat, container, false);

        // Build RecyclerView and set Adapter
        recyclerView = root.findViewById(R.id.chat_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));
        this.adapter = new ChatListViewAdapter(this.getContext(), this, chats);
        recyclerView.setAdapter(this.adapter);

        return root;
    }

    @Override
    public void openChat(int adapterPosition) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        intent.putExtra("chat", new Gson().toJson(chats.get(adapterPosition)));
        getActivity().startActivity(intent);
    }

    @Override
    public void completionHandler(String object, String url) {
        this.chats = new Gson().fromJson(object, new TypeToken<ArrayList<Chat>>(){}.getType());
        for(final Chat c : this.chats){
            User u = new User();
            if(c.getUserId1().equals(currentUserID)){
                u.setUserId(c.getUserId2());
            }else{
                    u.setUserId(c.getUserId1());
            }

            // Get default selected car for every chat user
            ServerConnection.getCars(u, new ServerCallback() {
                @Override
                public void completionHandler(String object, String url) {
                    ArrayList<Car> cars = new Gson().fromJson(object, new TypeToken<ArrayList<Car>>(){}.getType());
                    for(Car car : cars){
                        if(car.isSelected()){
                            c.setName(car.getBrand() + " " +car.getType() + " - " + car.getLicense_plate());
                            c.setDescription("Matched on " + c.getStart());
                            chats.set(chats.indexOf(c), c);
                            adapter.updateData(chats);
                        }
                    }
                }
            }, this.getActivity());
        }
        this.adapter.updateData(this.chats);
    }
}