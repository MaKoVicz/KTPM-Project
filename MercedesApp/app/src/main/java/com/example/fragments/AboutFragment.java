package com.example.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mercedesapp.R;

public class AboutFragment extends Fragment {
    private TextView introTextView1, introTextView2, introTextView3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);
        introTextView1 = (TextView) view.findViewById(R.id.introductionTextView1);
        introTextView2 = (TextView) view.findViewById(R.id.introductionTextView2);
        introTextView3 = (TextView) view.findViewById(R.id.introductionTextView3);
        setTextViewText();

        return view;
    }

    public void setTextViewText() {
        String text = "Tọa lạc trên khuôn viên với tổng diện tích gần 6000 mét vuông, " +
                "Vietnam Star Autohaus Hanoi có diện tích mái che 2500 mét vuông gồm phòng trưng bày, " +
                "phòng kinh doanh, phòng phụ tùng và dịch vụ nhà xưởng. " +
                "Ngoài ra chi nhánh Hà Nội còn có 1 city showroom nằm ngay chính " +
                "giữa trung tâm quận Hoàn Kiếm của thành phố Hà Nội.";

        introTextView1.setText(Html.fromHtml(text));

        text = "Vietnam Star Hanoi là Autohaus 300, đáp ứng mọi tiêu chuẩn quốc tế của một " +
                "Autohaus Mercedes-Benz về cả hai chức năng bán hàng và hậu mãi, " +
                "trong đó bao gồm việc tuân thủ các quy trình tiên tiến trong hoạt động kinh doanh, " +
                "cũng như phải đảm bảo quy trình và trang thiết bị hiện đại trong bảo hành, sửa chữa.";

        introTextView2.setText(text);

        text = "Bên cạnh đó đội ngũ nhân viên phải có trình độ chuyên môn cao và công ty có khả năng tài chính dồi dào, " +
                "nhằm đảm bảo việc kinh doanh được lâu dài. Từng yếu tố một phải đảm bảo theo đúng tiêu chuẩn của " +
                "Mercedes-Benz một cách chặt chẽ nhằm thể hiện đúng thương hiệu và chất lượng đẳng cấp Mercedes-Benz.";

        introTextView3.setText(text);
    }
}
