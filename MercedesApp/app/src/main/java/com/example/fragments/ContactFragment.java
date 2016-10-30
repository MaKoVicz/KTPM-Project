package com.example.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mercedesapp.R;

public class ContactFragment extends Fragment {
    TextView addressTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        addressTextView = (TextView) view.findViewById(R.id.addressTextView);
        setTextViewText();

        return view;
    }

    public void setTextViewText() {
        String address = "<font color=black><b>Công ty TNHH Ô tô Ngôi Sao Việt Nam</b><font><br>\n" +
                "Nhà phân phối Mercedes-Benz lớn nhất Việt Nam<br>\n" +
                "<br>\n" +
                "<u>Tại Tp. Hồ Chí Minh</u> :<br>\n" +
                "Autohaus 1 : số 02 Trường Chinh, P.Tây Thạnh, Q.Tân Phú. <br>\n" +
                "Autohaus 2 : 811-813 Nguyễn Văn Linh, P.Tân Phong, Q.7. <br>\n" +
                "<br>\n" +
                "<u>Tại Hà Nội</u> :<br>\n" +
                "Autohaus : 368 Nguyễn Văn Linh, Q.Long Biên.<br>\n" +
                "City Showroom : số 02 Ngô Quyền, Q.Hoàn kiếm. <br>\n" +
                "<br>\n" +
                "<font color=black><b>Email : sales@mercedeshcm.vn<b><font><br>\n" +
                "<font color=black><b>Hotline P.KD : 0933 93 63 63<b><font>";

        addressTextView.setText(Html.fromHtml(address));
    }
}
