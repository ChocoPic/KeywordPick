package com.drawing.keywordpick;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PickActivityFragment extends Fragment {

    private LinearLayout resultView;
    private DbHelper dbHelper;
    private int numTV = 0;
    private String title;
    private int num;
    private List<TextView> tvs;
    Button pickBtn;
    AutoCompleteTextView autoCompleteTextView;
    TextInputEditText numText;

    private InterstitialAd mInterstitialAd; //전면광고
    private int clickCount = 0; //n 번되면 전면 광고 표시
    private final int CLICKCOUNT = 3;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //전면 광고 로드
        loadAd();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_pick, container, false);

        //DB 세팅
        dbHelper = DbHelper.getInst(getContext());

        //뷰 세팅
        pickBtn = (Button) view.findViewById(R.id.pick_btn);
        autoCompleteTextView = (AutoCompleteTextView) view.findViewById(R.id.list_text);
        numText = view.findViewById(R.id.num_text);
        resultView = (LinearLayout) view.findViewById(R.id.resultLayout);

        //애니메이션 세팅
        Animation anim = AnimationUtils.loadAnimation(getContext(),R.anim.button_anim);
        anim.setAnimationListener(pick_aniListener);

        // 뽑기 목록 세팅
        dbHelper = DbHelper.getInst(getContext());

        // DB에서 가져와서 리사이클러뷰 리스트로 세팅
        ArrayList<MyData> dataList = dbHelper.getAllData();
        List<String> items = new ArrayList<>();
        for(int i=0; i<dataList.size(); i++){
            MyData temp_data = dataList.get(i);
            String [] temp_content = temp_data.content.split("\n");
            items.add(temp_data.title +" ("+ temp_content.length +")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.list_menu_item, R.id.tv_item_menu, items);
        autoCompleteTextView.setAdapter(adapter);

        tvs = new ArrayList<>();
        // 뽑기 버튼 클릭 이벤트
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //선택된 제목 리스트를 가져옵니다
                title = autoCompleteTextView.getText().toString().split(" ")[0];
                //입력된 숫자를 가져옵니다
                String n = numText.getText().toString();
                if(isNull(title) || isNull(n) || title.isEmpty() || n.isEmpty()){
                    Toast.makeText(getContext(), "빈칸이 있어요", Toast.LENGTH_SHORT).show();
                }else {
                    num = Integer.parseInt(n);
                    if (num < 1 || num > 10) {
                        Toast.makeText(getContext(), "1~10개까지 가능해요", Toast.LENGTH_SHORT).show();
                    } else {
                        //전면 광고 표시
                        clickCount++;
                        if (clickCount >= CLICKCOUNT && mInterstitialAd != null) {
                            mInterstitialAd.show(requireActivity());
                            clickCount = 0;
                        }

                        //뽑기 버튼에 애니메이션 효과
                        pickBtn.startAnimation(anim);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //DB 세팅
        dbHelper = DbHelper.getInst(getContext());

        //뷰 세팅
        pickBtn = (Button) getView().findViewById(R.id.pick_btn);
        autoCompleteTextView = (AutoCompleteTextView) getView().findViewById(R.id.list_text);
        numText = getView().findViewById(R.id.num_text);
        resultView = (LinearLayout) getView().findViewById(R.id.resultLayout);

        //애니메이션 세팅
        Animation anim = AnimationUtils.loadAnimation(getContext(),R.anim.button_anim);
        anim.setAnimationListener(pick_aniListener);

        // 뽑기 목록 세팅
        dbHelper = DbHelper.getInst(getContext());

        // DB에서 가져와서 리사이클러뷰 리스트로 세팅
        ArrayList<MyData> dataList = dbHelper.getAllData();
        List<String> items = new ArrayList<>();
        for(int i=0; i<dataList.size(); i++){
            MyData temp_data = dataList.get(i);
            String [] temp_content = temp_data.content.split("\n");
            items.add(temp_data.title +" ("+ temp_content.length +")");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.list_menu_item, R.id.tv_item_menu, items);
        autoCompleteTextView.setAdapter(adapter);

        tvs = new ArrayList<>();
        // 뽑기 버튼 클릭 이벤트
        pickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //선택된 제목 리스트를 가져옵니다
                title = autoCompleteTextView.getText().toString().split(" ")[0];
                //입력된 숫자를 가져옵니다
                String n = numText.getText().toString();
                if(isNull(title) || isNull(n) || title.isEmpty() || n.isEmpty()){
                    Toast.makeText(getContext(), "빈칸이 있어요", Toast.LENGTH_SHORT).show();
                }else {
                    num = Integer.parseInt(n);
                    if (num < 1 || num > 10) {
                        Toast.makeText(getContext(), "1~10개까지 가능해요", Toast.LENGTH_SHORT).show();
                    } else {
                        //전면 광고 표시
                        clickCount++;
                        if (clickCount == CLICKCOUNT) {
                            clickCount = 0;
                            if (mInterstitialAd != null) {
                                mInterstitialAd.show(requireActivity());
                                loadAd();   //광고 본 후 다시 로드
                            } else {
                                loadAd();   // 광고가 로드되지 않은 경우 다시 로드
                            }
                        }
                        //뽑기 버튼에 애니메이션 효과
                        pickBtn.startAnimation(anim);
                    }
                }
            }
        });
    }

    Animation.AnimationListener pick_aniListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            removeTextView();    //텍스트뷰 초기화
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            //중복없이 랜덤으로 뽑힌 리스트를 가져옵니다
            List<String> list = randomList(title, num);
            if (list.isEmpty()) {
                Toast.makeText(getContext(), "뽑을 후보가 너무 적어요", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < list.size(); i++) {
                    createTextView(list.get(i), i);
                }
            }
            //텍스트뷰 애니메이션 효과
            for(int k=0; k<tvs.size(); k++){
                tvs.get(k).setTranslationX(-700);
                tvs.get(k).animate()
                        .translationXBy(700)
                        .setDuration(300)
                        .setStartDelay(300L *k);
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };
    /** 뽑기 함수 **/
    private List<String> randomList(String title, int num){
        List<String> resultList = new ArrayList<>();
        String[] myTotalList;

        //선택된 뽑기 리스트를 DB에서 가져옵니다
        List<MyData> list = dbHelper.getData(title);    //해당 목록의 자르기 전 내용
        if(list.isEmpty()){
            Toast.makeText(getContext(), "목록을 가져오는 데 실패했습니다!", Toast.LENGTH_LONG).show();
            return resultList;
        }else{
            myTotalList = list.get(0).content.split("\n");    //해당 목록의 자른 후 내용
        }
        if(myTotalList.length<num){ //뽑는 개수가 옳지 않은 경우
            return resultList;
        }

        //랜덤으로 중복없게 개수만큼 하나씩 뽑아서 띄웁니다
        Random rand = new Random();
        int[] resultIntList = new int[10];        //최대 10개까지 뽑을 수 있음
        for(int i=0; i<num; i++){
            resultIntList[i] = rand.nextInt(myTotalList.length);
            for(int j=0; j<i; j++){ //중복제거
                if(resultIntList[i] == resultIntList[j]){
                    i--;
                }
            }
        }
        for(int i=0; i<num; i++){
            Log.d("뽑음",resultIntList[i]+"");
            resultList.add(myTotalList[resultIntList[i]]);
        }
        return resultList;
    }

    /** 텍스트뷰 생성 **/
    private void createTextView(String text, int id){
        TextView textView = new TextView(getContext());
        textView.setText(text);
        textView.setTextSize(20);
        textView.setId(id);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = 16;
        textView.setLayoutParams(params);
        tvs.add(textView);
        resultView.addView(textView);
        numTV++;
    }
    /** 텍스트뷰 제거 **/
    private void removeTextView(){
        int i = 0;
        if(numTV <= 0){
            return;
        }
        while (numTV!=0){
            TextView tv = resultView.findViewById(i);
            tvs.remove(tv);
            resultView.removeView(tv);
            i++;
            numTV--;
        }
    }
    private Boolean isNull(String text){
        if(text==null || text.isEmpty() || text.replace(" ", "").isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void loadAd() {
        InterstitialAd.load(
                requireActivity(),
                "ca-app-pub-9932148089014412/8125668266",
                new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;

                        mInterstitialAd.setFullScreenContentCallback(
                            new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    mInterstitialAd = null;
                                    loadAd(); // 다음 광고 미리 준비
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                    mInterstitialAd = null;
                                    loadAd();
                                }
                            }
                        );
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });

    }


}