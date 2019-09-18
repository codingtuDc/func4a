package core.view.textwatcher;

public class DividerTextWatcher extends DefualtTextWatcher {

    //可以根据需求更改分段模式
    private int[] counts = new int[]{3, 4, 4};
    //
    private PhoneEditCallBack callBack;

    private int totalCount;
    private int[] ps;
    private boolean isDelete;
    private boolean isReset;

    public DividerTextWatcher(PhoneEditCallBack callBack) {
        ps = new int[counts.length - 1];
        ps[0] = totalCount = counts[0];
        for (int i = 1; i < counts.length; i++) {
            totalCount += counts[i] + 1;
            if (i < ps.length) {
                ps[i] = totalCount;
            }
        }
        this.callBack = callBack;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        super.beforeTextChanged(s, start, count, after);
        if (!isReset)
            isDelete = count == 1 && ' ' == s.toString().charAt(start);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        super.onTextChanged(s, start, before, count);

        String phone = s.toString();
        callBack.phoneLength(phone.length());

        if (!isReset) {
            if (isDelete) {
                phone = phone.substring(0, start - 1) + phone.substring(start);
                start--;
            }

            isReset = isDelete || phone.length() > totalCount;

            char subPhone;
            StringBuilder sb = new StringBuilder();
            int position = start;
            boolean isBlankIndex;
            boolean isBlank;
            int addCount;
            for (int i = 0; i < phone.length(); i++) {
                subPhone = phone.charAt(i);
                isBlankIndex = isBlankIndex(sb.length());
                isBlank = subPhone == ' ';
                addCount = (i >= start && i < (start + count)) ? 1 : 0;

                if (isBlankIndex) {
                    sb.append(' ');
                    position += addCount;
                }
                if (!isBlank) {
                    sb.append(subPhone);
                    position += addCount;
                }

                isReset = isReset || (isBlankIndex && !isBlank) || (!isBlankIndex && isBlank);

                if (sb.length() == totalCount) {
                    break;
                }
            }

            String newPhone = sb.toString().trim();

            callBack.phoneCanUse(newPhone.length() == totalCount);

            if (isReset = isReset || newPhone.length() != sb.length()) {
                callBack.resetPhone(newPhone, position);
            } else {
                callBack.phoneWatcherFinish();
            }
        } else {
            isReset = false;
        }
    }

    private boolean isBlankIndex(int position) {
        for (int i = 0; i < ps.length; i++) {
            if (position == ps[i]) {
                return true;
            }
        }
        return false;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public static interface PhoneEditCallBack {
        void phoneLength(int length);

        void phoneCanUse(boolean canUse);

        void resetPhone(String phone, int position);

        void phoneWatcherFinish();
    }
}