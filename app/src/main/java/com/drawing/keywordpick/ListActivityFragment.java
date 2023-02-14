package com.drawing.keywordpick;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<MyData> dataList;
    private ArrayList<String> titleList;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DbHelper dbHelper;
    private Button button_add;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        // 리사이클러뷰 세팅
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(getContext());

        // DB세팅
        dbHelper = DbHelper.getInst(getContext());

        // DB에서 가져와서 리사이클러뷰 리스트로 세팅
        dataList = new ArrayList<>();
        dataList = dbHelper.getAllData();
        titleList = new ArrayList<>();
        for(int i=0; i<dataList.size(); i++){
            titleList.add(dataList.get(i).title);
        }
        recyclerAdapter = new RecyclerAdapter(titleList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        // 아이템 클릭 이벤트
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                clickItem(position,itemName);
            }
        });

        //버튼 세팅
        button_add = (Button) view.findViewById(R.id.button_add);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), EditActivity.class);
                intent.putExtra("title", "");  //제목전달
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 리사이클러뷰 세팅
        recyclerView = (RecyclerView) getView().findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        // DB세팅
        dbHelper = DbHelper.getInst(getContext());

        // DB에서 가져와서 리사이클러뷰 리스트로 세팅
        dataList = new ArrayList<>();
        dataList = dbHelper.getAllData();
        titleList = new ArrayList<>();
        for(int i=0; i<dataList.size(); i++){
            titleList.add(dataList.get(i).title);
        }
        recyclerAdapter = new RecyclerAdapter(titleList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        // 아이템 클릭 이벤트
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                clickItem(position,itemName);
            }
        });

    }

    // 특정 아이템 클릭시
    private void clickItem(int position, String itemName){
        //Toast.makeText(getContext(),position+itemName,Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), EditActivity.class);
        intent.putExtra("title", itemName);  //제목전달
        startActivity(intent);
    }

}