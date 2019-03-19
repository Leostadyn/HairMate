package com.example.stackfarm.myapplication.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.stackfarm.myapplication.R;

/**
 * Created by lan.zheng on 2016/8/1.
 */
public class BottomSelectorPopDialog extends PopupWindow {
    private TextView mActionOneTextView;
    private TextView mActionTwoTextView;
    private TextView mActionThreeTextView;
    private TextView mCancelTextView;
    private View mMenuView;
    public BottomSelectorPopDialog(Activity context, String[] nameList, View.OnClickListener itemsOnClick) {
        super(context);
//        super(context,R.style.transparentFrameWindowStyle);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.popup_dialog_publish, null);
        mActionOneTextView = (TextView) mMenuView.findViewById(R.id.tv_action_one);
        mActionTwoTextView = (TextView) mMenuView.findViewById(R.id.tv_action_two);
        mCancelTextView = (TextView)mMenuView.findViewById(R.id.tv_cancel_show) ;
 
        if(nameList.length == 3){  //想要更多的操作按钮，可以继续添加
            mActionThreeTextView = (TextView)mMenuView.findViewById((R.id.tv_action_three));
            mActionThreeTextView.setVisibility(View.VISIBLE);
            mActionThreeTextView.setText(nameList[2]);
            mActionThreeTextView.setOnClickListener(itemsOnClick);
        }
 
        mActionOneTextView.setText(nameList[0]);
        mActionTwoTextView.setText(nameList[1]);
        mActionOneTextView.setOnClickListener(itemsOnClick);
        mActionTwoTextView.setOnClickListener(itemsOnClick);
 
        //点击取消
        mCancelTextView.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setAnimationStyle(R.style.AnimBottom);
//        设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //实例化一个ColorDrawable颜色为全透明！！！为了和window底色融合，设置#00ffffff
//         ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.transparent));
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.colorAccent));
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
 
            public boolean onTouch(View v, MotionEvent event) {
 
                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y=(int) event.getY();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(y<height){
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}