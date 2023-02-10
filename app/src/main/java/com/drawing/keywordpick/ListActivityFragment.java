package com.drawing.keywordpick;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListActivityFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<String> list;
    private RecyclerAdapter recyclerAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list, container, false);

        // 리사이클러뷰 세팅
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        linearLayoutManager = new LinearLayoutManager(getContext());

        list = new ArrayList<>();
        list.add("1번");
        list.add("2번");
        list.add("3번");
        recyclerAdapter = new RecyclerAdapter(list);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        // 아이템 클릭 이벤트
        recyclerAdapter.setOnItemClickListener(new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String itemName) {
                clickItem(position, itemName);
            }
        });

        return view;
    }

    // 특정 아이템 클릭시
    private void clickItem(int position, String itemName){
        Intent intent = new Intent(getContext(), EditActivity.class);
        //TODO: 키워드를 띄울 수 있게 아이템 정보나 저장된 데이터 등을 전달해준다. (수정할 수 있게)
        startActivity(intent);
    }

}