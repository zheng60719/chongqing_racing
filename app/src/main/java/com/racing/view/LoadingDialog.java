package com.racing.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.lottery.R;


/**
 * @author will on 2017/5/10 15:03
 * @email pengweiqiang64@163.com
 * @description 进度条
 * @Version
 */

public class LoadingDialog extends ProgressDialog
{
    public LoadingDialog(Context context)
    {
        super(context);
    }

    public LoadingDialog(Context context, int theme)
    {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        init(getContext());
    }

    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消
//        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setContentView(R.layout.view_progress);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    public void setTitle(String title){
        TextView txTitle = (TextView) findViewById(R.id.tv_load_dialog);
        txTitle.setText(title);
    }

    @Override
    public void show()
    {
        super.show();
    }
}
