# OnResult4Activity

value参数表示由那个activity回调的

isDeal参数表示是否处理传递回来的参数

如果isDeal为false，表示不处理，则方法的参数必须为（int requestCode, int resultCode, Intent data）

如果isDeal为true(默认值),表示处理参数，方法的参数可以为任何类型，并且会从intent中取出相应的数据

```
    @OnResult4Activity(value = TwoActivity.class, isDeal = false)
    public void ccc(int requestCode, int resultCode, Intent data) {

    }

    @OnResult4Activity(TwoActivity.class)
    public void aaa(User user, Me me) {

    }
```