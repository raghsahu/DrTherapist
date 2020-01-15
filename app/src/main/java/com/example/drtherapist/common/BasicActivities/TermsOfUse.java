package com.example.drtherapist.common.BasicActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.drtherapist.R;

public class TermsOfUse extends AppCompatActivity {
    ImageView iv_back;
    WebView webview;
    String Terms_Use="\n" +
            "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <title></title>\n" +
            "    <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css\">\n" +
            "\n" +
            "\n" +
            "</head>\n" +
            "<style>\n" +
            ".checked {\n" +
            "  color: orange;\n" +
            "}\n" +
            "</style>\n" +
            "<body>\n" +
            "\n" +
            "<main id=\"main-content\">\n" +
            "    <div id=\"content-wrap\">\n" +
            "        <div id=\"site-content\" class=\"site-content clearfix\">\n" +
            "            <div id=\"inner-content\" class=\"inner-content-wrap\">\n" +
            "                <div class=\"page-content\">\n" +
            "\n" +
            "                    <div class=\"wprt-container\">\n" +
            "                        <p style=\"font-size: 30px; color:#0b529a;margin-top: 11px;text-align: center;\">TERMS OF SERVICE AGREEMENT</p>\n" +
            "                        <div class=\"row\">\n" +
            "\n" +
            "                        <p style=\"font-size: 18px; font-family:Calibri;color: #251e1e;\">\n" +
            "                          PLEASE READ THE FOLLOWING TERMS OF SERVICE AGREEMENT CAREFULLY. BY ACCESSING OR USING OUR SITES AND OUR SERVICES, YOU HEREBY AGREE TO BE BOUND BY THE TERMS AND ALL TERMS INCORPORATED HEREIN BY REFERENCE. IT IS THE RESPONSIBILITY OF YOU, THE USER, CUSTOMER, OR PROSPECTIVE CUSTOMER TO READ THE TERMS AND CONDITIONS BEFORE PROCEEDING TO USE THIS SITE. IF YOU DO NOT EXPRESSLY AGREE TO ALL OF THE TERMS AND CONDITIONS, THEN PLEASE DO NOT ACCESS OR USE OUR SITES OR OUR SERVICES. THIS TERMS OF SERVICE AGREEMENT IS EFFECTIVE AS OF 06/01/2019.\n" +
            "\n" +
            "                        </p><br>\n" +
            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>ACCEPTANCE OF TERMS</b></p>\n" +
            "                        <p style=\"font-size: 18px; font-family: Times New Roman;color: #251e1e\">\n" +
            "                  The following Terms of Service Agreement (the \"TOS\") is a legally binding agreement that shall govern the relationship with our users and others which may interact or interface with The Missing Piece LLC, also known as Therapist Hire, located at 7036 Creekside Dr, Plainfield, Illinois 60586 and our subsidiaries and affiliates, in association with the use of theT herapist Hire website, which includes therapisthire.com, (the \"Site\") and its Services, which shall be defined below.\n" +
            "                        </p>\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>DESCRIPTION OF WEBSITE SERVICES OFFERED</b></p>\n" +
            "                        <p style=\"font-size: 18px; font-family:Times New Roman;color: #251e1e\">\n" +
            "                     The Site is a social networking website which has the following description: The Purpose of this website and App is to connect local therapist and therapeutic service providers with companies and family according to their availability and scheduling needs. Any and all visitors to our site, despite whether they are registered or not, shall be deemed as \"users\" of the herein contained Services provided for the purpose of this TOS. Once an individual register's for our Services, through the process of creating an account, the user shall then be considered a \"member.\" The user and/or member acknowledges and agrees that the Services provided and made available through our website and applications, which may include some mobile applications and that those applications may be made available on various social media networking sites and numerous other platforms and downloadable programs, are the sole property of The Missing Piece LLC. At its discretion, The Missing Piece LLC may offer additional website Services and/or products, or update, modify or revise any current content and Services, and this Agreement shall apply to any and all additional Services and/or products and any and all updated, modified or revised Services unless otherwise stipulated. The Missing Piece LLC does hereby reserve the right to cancel and cease offering any of the aforementioned Services and/or products. You, as the end user and/or member, acknowledge, accept and agree that The Missing Piece LLC shall not be held liable for any such updates, modifications, revisions, suspensions or discontinuance of any of our Services and/or products. Your continued use of the Services provided, after such posting of any updates, changes, and/or modifications shall constitute your acceptance of such updates, changes and/or modifications, and as such, frequent review of this Agreement and any and all applicable terms and policies should be made by you to ensure you are aware of all terms and policies currently in effect. Should you not agree to the updated, revised or modified terms, you must stop using the provided Services forthwith. Furthermore, the user and/or member understands, acknowledges and agrees that the Services offered shall be provided \"AS IS\" and as such The Missing Piece LLC shall not assume any responsibility or obligation for the timeliness, missed delivery, deletion and/or any failure to store user content, communication or personalization settings.\n" +
            "                        </p><br>\n" +
            "\n" +
            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>ACCEPTANCE OF TERMS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                           The following Terms of Service Agreement (the \"TOS\") is a legally binding agreement that shall govern the relationship with our users and others which may interact or interface with The Missing Piece LLC, also known as Therapist Hire, located at 7036 Creekside Dr, Plainfield, Illinois 60586 and our subsidiaries and affiliates, in association with the use of theT herapist Hire website, which includes therapisthire.com, (the \"Site\") and its Services, which shall be defined below.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                         <p style=\"font-size: 20px;color:#0b529a;\"><b>DESCRIPTION OF WEBSITE SERVICES OFFERED</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                           The Site is a social networking website which has the following description: The Purpose of this website and App is to connect local therapist and therapeutic service providers with companies and family according to their availability and scheduling needs. Any and all visitors to our site, despite whether they are registered or not, shall be deemed as \"users\" of the herein contained Services provided for the purpose of this TOS. Once an individual register's for our Services, through the process of creating an account, the user shall then be considered a \"member.\" The user and/or member acknowledges and agrees that the Services provided and made available through our website and applications, which may include some mobile applications and that those applications may be made available on various social media networking sites and numerous other platforms and downloadable programs, are the sole property of The Missing Piece LLC. At its discretion, The Missing Piece LLC may offer additional website Services and/or products, or update, modify or revise any current content and Services, and this Agreement shall apply to any and all additional Services and/or products and any and all updated, modified or revised Services unless otherwise stipulated. The Missing Piece LLC does hereby reserve the right to cancel and cease offering any of the aforementioned Services and/or products. You, as the end user and/or member, acknowledge, accept and agree that The Missing Piece LLC shall not be held liable for any such updates, modifications, revisions, suspensions or discontinuance of any of our Services and/or products. Your continued use of the Services provided, after such posting of any updates, changes, and/or modifications shall constitute your acceptance of such updates, changes and/or modifications, and as such, frequent review of this Agreement and any and all applicable terms and policies should be made by you to ensure you are aware of all terms and policies currently in effect. Should you not agree to the updated, revised or modified terms, you must stop using the provided Services forthwith. Furthermore, the user and/or member understands, acknowledges and agrees that the Services offered shall be provided \"AS IS\" and as such The Missing Piece LLC shall not assume any responsibility or obligation for the timeliness, missed delivery, deletion and/or any failure to store user content, communication or personalization settings.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                         <p style=\"font-size: 20px;color:#0b529a;\"><b>REGISTRATION</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                    To register and become a \"member\" of the Site, you must be at least 18 years of age to enter into and form a legally binding contract. In addition, you must be in good standing and not an individual that has been previously barred from receiving Therapist Hire's Services under the laws and statutes of the United States or other applicable jurisdiction. When you register, Therapist Hire may collect information such as your name, e-mail address, birth date, gender, mailing address, occupation, industry and personal interests. You can edit your account information at any time. Once you register with Therapist Hire and sign in to our Services, you are no longer anonymous to us. Furthermore, the registering party hereby acknowledges, understands and agrees to: a) furnish factual, correct, current and complete information with regards to yourself as may be requested by the data registration process, and b) maintain and promptly update your registration and profile information in an effort to maintain accuracy and completeness at all times. If anyone knowingly provides any information of a false, untrue, inaccurate or incomplete nature, The Missing Piece LLC will have sufficient grounds and rights to suspend or terminate the member in violation of this aspect of the Agreement, and as such refuse any and all current or future use of The Missing Piece LLC Services, or any portion thereof. It is The Missing Piece LLC's priority to ensure the safety and privacy of all its visitors, users and members, especially that of children. Therefore, it is for this reason that the parents of any child under the age of 13 that permit their child or children access to the Therapist Hire website platform Services must create a \"family\" account, which will certify that the individual creating the \"family\" account is of 18 years of age and as such, the parent or legal guardian of any child or children registered under the \"family\" account. As the creator of the \"family\" account, s/he is thereby granting permission for his/her child or children to access the various Services provided, including, but not limited to, message boards, email, and/or instant messaging. It is the parent's and/or legal guardian's responsibility to determine whether any of the Services and/or content provided are age-appropriate for his/her child.\n" +
            "                        </p><br>\n" +
            "\n" +


            "</main> \n" +
            "\n" +
            "</body>\n" +
            "</html>";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_use);

        iv_back=findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webview=(WebView)findViewById(R.id.webview);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webview.loadUrl("http://logicalsofttech.com/therapist/Welcome/Term_Of_Use");

        webview.loadData(Terms_Use, "text/html", null);
    }
}
