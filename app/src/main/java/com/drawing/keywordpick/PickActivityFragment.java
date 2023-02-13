package com.drawing.keywordpick;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class PickActivityFragment extends Fragment {
    private Button BtnList;
    private TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_pick, container, false);
        BtnList = (Button) view.findViewById(R.id.pick_btn);
        textInputLayout = view.findViewById(R.id.listInputLayout);
        autoCompleteTextView = view.findViewById(R.id.list_text);

        BtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "클릭!", Toast.LENGTH_SHORT).show();
                //TODO: 뽑기 기능 구현
                //기본 키워드 리스트 넣기
                //개수따라 뽑는 함수 구현
                //뽑는 애니메이션 효과
                //리스트 선택기능 추가
                //리스트는 db에서 가져올 것
                //TODO: db구현
                //기본 넣어놓고, 추가, 수정, 삭제 기능
                //목록 - 요소들 어떻게?
            }
        });

        String[] items = {"1번","2번","3번","4번"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.list_menu_item, R.id.tv_item_menu, items);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //text.setText((String)adapterView.getItemAtPosition(i));
            }
        });
        return view;
    }
}