package core.view.layer;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import core.R;
import core.funcs.ViewFunc;

public class DialogLayer extends CenterLayer {

    TextView titleTv;
    TextView leftTv;
    TextView rightTv;

    public DialogLayer(Activity act) {
        super(act, R.layout.layer_dialog);
        titleTv = centerView.findViewById(R.id.titleTv);
        leftTv = centerView.findViewById(R.id.leftTv);
        rightTv = centerView.findViewById(R.id.rightTv);
    }

    public void setTitle(String title) {
        ViewFunc.setText(titleTv, title);
    }

    public void setLeftTv(String left) {
        ViewFunc.setText(leftTv, left);
    }

    public void setRightTv(String right) {
        ViewFunc.setText(rightTv, right);
    }


    public void setOnClick(final OnClick onClick) {
        setOnClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = v.getId();
                if (i == R.id.leftTv) {
                    onClick.onClickLeft();
                } else if (i == R.id.rightTv) {
                    onClick.onClickRight();
                }
            }
        }, R.id.leftTv, R.id.rightTv);
    }

    public static interface OnClick {
        public void onClickLeft();

        public void onClickRight();
    }
}
