# ClickView使用

在Activity中添加点击方法，
用注解ClickView标注，ClickView中标注点击控件的id。
方法中如果需要回传参数，如果是回传点击的View，则参数为**View view**。
如果回传其他值，则需要通过注解ClickTag标注view tag的id。

```
    @ClickView({R.id.tv, R.id.tv1})
    public void clickTv1(View view, @ClickTag(R.id.tag_position) int position) {

    }

    @ClickView(R.id.tv2)
    public void clickTv2() {

    }
```

