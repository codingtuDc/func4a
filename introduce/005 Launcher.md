# Launcher使用

在Activity中添加注解@Launcher
paramClasses中添加需要传递过来的参数的类类型
paramNames中添加需要传递过来的参数的名字
可以在此类中通过Pass获得传递过来的参数

```
@Launcher(paramClasses = User.class, paramNames = "user")
public class OneActivity extends CoreActivity {

    @FindView(R.id.tv)
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        User user = Pass.user(getIntent());
    }
}
```

在需要打开新的Activity的地方使用时

```
ActLuncher.oneActivity(getThis(),new User());
```

在