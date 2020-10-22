package com.open.openglstudy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.open.openglstudy.adapter.HomeAdapter;
import com.open.openglstudy.bean.ActivityBean;
import com.open.openglstudy.opengl01.OpenGL01Activity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ActivityBean> list = new ArrayList<>();
    private RecyclerView recyclerview;

    HomeAdapter homeAdapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        //添加Android自带的分割线
        recyclerview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        list.add(new ActivityBean(0, "opengl环境搭建,绘制一种颜色", OpenGL01Activity.class));


        homeAdapter = new HomeAdapter(R.layout.home_item_view, list);
        recyclerview.setAdapter(homeAdapter);

        homeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(MainActivity.this, list.get(position).getClazz()));
            }
        });
    }
}