package com.example.drtherapist.common.BasicActivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.drtherapist.R;

public class PrivacyPolicy extends AppCompatActivity {
    ImageView iv_back;
    WebView webview;

    String Privacy_policy="\n" +
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
            "                        <p style=\"font-size: 30px; color:#0b529a;margin-top: 11px;text-align: center;\">PRIVACY POLICY</p>\n" +
            "                        <div class=\"row\">\n" +
            "\n" +
            "                        <p style=\"font-size: 18px; font-family:Calibri;color: #251e1e;\">\n" +
            "               Every member's registration data and various other personal information are strictly protected by the The Missing Piece LLC Online Privacy Policy (see the full Privacy Policy at) . As a member, you herein consent to the collection and use of the information provided, including the transfer of information within the United States and/or other countries for storage, processing or use by The Missing Piece LLC and/or our subsidiaries and affiliates. MEMBER ACCOUNT, USERNAME, PASSWORD AND SECURITY When you set up an account, you are the sole authorized user of your account. You shall be responsible for maintaining the secrecy and confidentiality of your password and for all activities that transpire on or within your account. It is your responsibility for any act or omission of any user(s) that access your account information that, if undertaken by you, would be deemed a violation of the TOS. It shall be your responsibility to notify The Missing Piece LLC immediately if you notice any unauthorized access or use of your account or password or any other breach of security. The Missing Piece LLC shall not be held liable for any loss and/or damage arising from any failure to comply with this term and/or condition of the TOS.\n" +
            "\n" +
            "                        </p><br>\n" +
            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>CONDUCT</b></p>\n" +
            "                        <p style=\"font-size: 18px; font-family: Times New Roman;color: #251e1e\">\n" +
             "                            As a user or member of the Site, you herein acknowledge, understand and agree that all information, text, software, data, photographs, music, video, messages, tags or any other content, whether it is publicly or privately posted and/or transmitted, is the expressed sole responsibility of the individual from whom the content originated. In short, this means that you are solely responsible for any and all content posted, uploaded, emailed, transmitted or otherwise made available by way of the Therapist Hire Services, and as such, we do not guarantee the accuracy, integrity or quality of such content. It is expressly understood that by use of our Services, you may be exposed to content including, but not limited to, any errors or omissions in any content posted, and/or any loss or damage of any kind incurred as a result of the use of any content posted, emailed, transmitted or otherwise made available by Therapist Hire. Furthermore, you herein agree not to make use of The Missing Piece LLC's Services for the purpose of: a) uploading, posting, emailing, transmitting, or otherwise making available any content that shall be deemed unlawful, harmful, threatening, abusive, harassing, tortious, defamatory, vulgar, obscene, libelous, or invasive of another's privacy or which is hateful, and/or racially, ethnically, or otherwise objectionable; b) causing harm to minors in any manner whatsoever; c) impersonating any individual or entity, including, but not limited to, any Therapist Hire officials, forum leaders, guides or hosts or falsely stating or otherwise misrepresenting any affiliation with an individual or entity; d) forging captions, headings or titles or otherwise offering any content that you personally have no right to pursuant to any law nor having any contractual or fiduciary relationship with; e) uploading, posting, emailing, transmitting or otherwise offering any such content that may infringe upon any patent, copyright, trademark, or any other proprietary or intellectual rights of any other party; f) uploading, posting, emailing, transmitting or otherwise offering any content that you do not personally have any right to offer pursuant to any law or in accordance with any contractual or fiduciary relationship; g) uploading, posting, emailing, transmitting, or otherwise offering any unsolicited or unauthorized advertising, promotional flyers, \"junk mail,\" \"spam,\" or any other form of solicitation, except in any such areas that may have been designated for such purpose; h) uploading, posting, emailing, transmitting, or otherwise offering any source that may contain a software virus or other computer code, any files and/or programs which have been designed to interfere, destroy and/or limit the operation of any computer software, hardware, or telecommunication equipment; i) disrupting the normal flow of communication, or otherwise acting in any manner that would negatively affect other users' ability to participate in any real time interactions; j) interfering with or disrupting any The Missing Piece LLC Services, servers and/or networks that may be connected or related to our website, including, but not limited to, the use of any device software and/or routine to bypass the robot exclusion headers; k) intentionally or unintentionally violating any local, state, federal, national or international law, including, but not limited to, rules, guidelines, and/or regulations decreed by the U.S. Securities and Exchange Commission, in addition to any rules of any nation or other securities exchange, that would include without limitation, the New York Stock Exchange, the American Stock Exchange, or the NASDAQ, and any regulations having the force of law; l) providing informational support or resources, concealing and/or disguising the character, location, and or source to any organization delegated by the United States government as a \"foreign terrorist organization\" in accordance to Section 219 of the Immigration Nationality Act; m) \"stalking\" or with the intent to otherwise harass another individual; and/or n) collecting or storing of any personal data relating to any other member or user in connection with the prohibited conduct and/or activities which have been set forth in the aforementioned paragraphs. The Missing Piece LLC herein reserves the right to pre-screen, refuse and/or delete any content currently available through our Services. In addition, we reserve the right to remove and/or delete any such content that would violate the TOS or which would otherwise be considered offensive to other visitors, users and/or members. The Missing Piece LLC herein reserves the right to access, preserve and/or disclose member account information and/or content if it is requested to do so by law or in good faith belief that any such action is deemed reasonably necessary for: a) compliance with any legal process; b) enforcement of the TOS; c) responding to any claim that therein contained content is in violation of the rights of any third party; d) responding to requests for customer service; or e) protecting the rights, property or the personal safety of The Missing Piece LLC, its visitors, users and members, including the general public. The Missing Piece LLC herein reserves the right to include the use of security components that may permit digital information or material to be protected, and that such use of information and/or material is subject to usage guidelines and regulations established by The Missing Piece LLC or any other content providers supplying content services to The Missing Piece LLC. You are hereby prohibited from making any attempt to override or circumvent any of the embedded usage rules in our Services. Furthermore, unauthorized reproduction, publication, distribution, or exhibition of any information or materials supplied by our Services, despite whether done so in whole or in part, is expressly prohibited.\n" +
            "                        </p>\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>INTERSTATE COMMUNICATION</b></p>\n" +
            "                        <p style=\"font-size: 18px; font-family:Times New Roman;color: #251e1e\">\n" +
            "                 Upon registration, you hereby acknowledge that by using therapisthire.com to send electronic communications, which would include, but are not limited to, email, searches, instant messages, uploading of files, photos and/or videos, you will be causing communications to be sent through our computer network. Therefore, through your use, and thus your agreement with this TOS, you are acknowledging that the use of this Service shall result in interstate transmissions. CAUTIONS FOR GLOBAL USE AND EXPORT AND IMPORT COMPLIANCE Due to the global nature of the internet, through the use of our network you hereby agree to comply with all local rules relating to online conduct and that which is considered acceptable Content. Uploading, posting and/or transferring of software, technology and other technical data may be subject to the export and import laws of the United States and possibly other countries. Through the use of our network, you thus agree to comply with all applicable export and import laws, statutes and regulations, including, but not limited to, the Export Administration Regulations (http://www.access.gpo.gov/bis/ear/ear_data.html), as well as the sanctions control program of the United States (http://www.treasury.gov/resourcecenter/ sanctions/Programs/Pages/Programs.aspx). Furthermore, you state and pledge that you: a) are not on the list of prohibited individuals which may be identified on any government export exclusion report (http://www.bis.doc.gov/complianceandenforcement/liststocheck.htm) nor a member of any other government which may be part of an export-prohibited country identified in applicable export and import laws and regulations; b) agree not to transfer any software, technology or any other technical data through the use of our network Services to any export-prohibited country; c) agree not to use our website network Services for any military, nuclear, missile, chemical or biological weaponry end uses that would be a violation of the U.S. export laws; and d) agree not to post, transfer nor upload any software, technology or any other technical data which would be in violation of the U.S. or other applicable export and/or import laws.\n" +
            "                        </p><br>\n" +
            "\n" +
            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>CONTENT PLACED OR MADE AVAILABLE FOR COMPANY SERVICES</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                       The Missing Piece LLC shall not lay claim to ownership of any content submitted by any visito,r member, or user, nor make such content available for inclusion on our website Services. Therefore, you hereby grant and allow for The Missing Piece LLC the below listed worldwide, royalty-free and non-exclusive licenses, as applicable: a) The content submitted or made available for inclusion on the publicly accessible areas of The Missing Piece LLC's sites, the license provided to permit to use, distribute, reproduce, modify, adapt, publicly perform and/or publicly display said Content on our network Services is for the sole purpose of providing and promoting the specific area to which this content was placed and/or made available for viewing. This license shall be available so long as you are a member of The Missing Piece LLC's sites, and shall terminate at such time when you elect to discontinue your membership. b) Photos, audio, video and/or graphics submitted or made available for inclusion on the publicly accessible areas of The Missing Piece LLC's sites, the license provided to permit to use, distribute, reproduce, modify, adapt, publicly perform and/or publicly display said Content on our network Services are for the sole purpose of providing and promoting the specific area in which this content was placed and/or made available for viewing. This license shall be available so long as you are a member of The Missing Piece LLC's sites and shall terminate at such time when you elect to discontinue your membership. c) For any other content submitted or made available for inclusion on the publicly accessible areas of The Missing Piece LLC's sites, the continuous, binding and completely sublicensable license which is meant to permit to use, distribute, reproduce, modify, adapt, publish, translate, publicly perform and/or publicly display said content, whether in whole or in part, and the incorporation of any such Content into other works in any arrangement or medium current used or later developed. Those areas which may be deemed \"publicly accessible\" areas of The Missing Piece LLC's sites are those such areas of our network properties which are meant to be available to the general public, and which would include message boards and groups that are openly available to both users and members. However, those areas which are not open to the public, and thus available to members only, would include our mail system and instant messaging. CONTRIBUTIONS TO COMPANY WEBSITE The Missing Piece LLC provides an area for our users and members to contribute feedback to our website. When you submit ideas, documents, suggestions and/or proposals (\"Contributions\") to our site, you acknowledge and agree that: a) your contributions do not contain any type of confidential or proprietary information; b) Therapist Hire shall not be liable or under any obligation to ensure or maintain confidentiality, expressed or implied, related to any Contributions; c) Therapist Hire shall be entitled to make use of and/or disclose any such Contributions in any such manner as they may see fit; d) the contributor's Contributions shall automatically become the sole property of Therapist Hire; and e) Therapist Hire is under no obligation to either compensate or provide any form of reimbursement in any manner or nature.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                         <p style=\"font-size: 20px;color:#0b529a;\"><b>INDEMNITY</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          All users and/or members herein agree to insure and hold The Missing Piece LLC, our subsidiaries, affiliates, agents, employees, officers, partners and/or licensors blameless or not liable for any claim or demand, which may include, but is not limited to, reasonable attorney fees made by any third party which may arise from any content a member or user of our site may submit, post, modify, transmit or otherwise make available through our Services, the use of Therapist Hire Services or your connection with these Services, your violations of the Terms of Service and/or your violation of any such rights of another person. COMMERCIAL REUSE OF SERVICES The member or user herein agrees not to replicate, duplicate, copy, trade, sell, resell nor exploit for any commercial reason any part, use of, or access to Therapist Hire's sites.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                         <p style=\"font-size: 20px;color:#0b529a;\"><b>USE AND STORAGE GENERAL PRACTICES</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                           You herein acknowledge that The Missing Piece LLC may set up any such practices and/or limits regarding the use of our Services, without limitation of the maximum number of days that any email, message posting or any other uploaded content shall be retained by The Missing Piece LLC, nor the maximum number of email messages that may be sent and/or received by any member, the maximum volume or size of any email message that may be sent from or may be received by an account on our Service, the maximum disk space allowable that shall be allocated o n The Missing Piece LLC's servers on the member's behalf, and/or the maximum number of times and/or duration that any member may access our Services in a given period of time. In addition, you also agree that The Missing Piece LLC has absolutely no responsibility or liability for the removal or failure to maintain storage of any messages and/or other communications or content maintained or transmitted by our Services. You also herein acknowledge that we reserve the right to delete or remove any account that is no longer active for an extended period of time. Furthermore, The Missing Piece LLC shall reserve the right to modify, alter and/or update these general practices and limits at our discretion. Any messenger service, which may include any web-based versions, shall allow you and the individuals with whom you communicate with the ability to save your conversations in your account located on The Missing Piece LLC's servers. In this manner, you will be able to access and search your message history from any computer with internet access. You also acknowledge that others have the option to use and save conversations with you in their own personal account on therapisthire.com. It is your agreement to this TOS which establishes your consent to allow The Missing Piece LLC to store any and all communications on its servers.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                         <p style=\"font-size: 20px;color:#0b529a;\"><b>MODIFICATIONS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                           The Missing Piece LLC shall reserve the right at any time it may deem fit, to modify, alter and or discontinue, whether temporarily or permanently, our service, or any part thereof, with or without prior notice. In addition, we shall not be held liable to you or to any third party for any such alteration, modification, suspension and/or discontinuance of our Services, or any part thereof.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                          <p style=\"font-size: 20px;color:#0b529a;\"><b> TERMINATION</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                            As a member of therapisthire.com, you may cancel or terminate your account, associated email address and/or access to our Services by submitting a cancellation or termination request to info@iamthemissingpiece.com. As a member, you agree that The Missing Piece LLC may, without any prior written notice, immediately suspend, terminate, discontinue and/or limit your account, any email associated with your account, and access to any of our Services. The cause for such termination, discontinuance, suspension and/or limitation of access shall include, but is not limited to: a) any breach or violation of our TOS or any other incorporated agreement, regulation and/or guideline; b) by way of requests from law enforcement or any other governmental agencies; c) the discontinuance, alteration and/or material modification to our Services, or any part thereof; d) unexpected technical or security issues and/or problems; e) any extended periods of inactivity; f) any engagement by you in any fraudulent or illegal activities; and/or g) the nonpayment of any associated fees that may be owed by you in connection with your therapisthire.com account Services. Furthermore, you herein agree that any and all terminations, suspensions, discontinuances, and or limitations of access for cause shall be made at our sole discretion and that we shall not be liable to you or any other third party with regards to the termination of your account, associated email address and/or access to any of our Services. The termination of your account with therapisthire.com shall include any and/or all of the following: a) the removal of any access to all or part of the Services offered within therapisthire.com; b) the deletion of your password and any and all related information, files, and any such content that may be associated with or inside your account, or any part thereof; and c) the barring of any further use of all or part of our Services.\n" +
            "                        </p><br>\n" +
            "\n" +
            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>ADVERTISERS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                           Any correspondence or business dealings with, or the participation in any promotions of, advertisers located on or through our Services, which may include the payment and/or delivery of such related goods and/or Services, and any such other term, condition, warranty and/or representation associated with such dealings, are and shall be solely between you and any such advertiser. Moreover, you herein agree that The Missing Piece LLC shall not be held responsible or liable for any loss or damage of any nature or manner incurred as a direct result of any such dealings or as a result of the presence of such advertisers on our website.\n" +
            "                        </p><br>\n" +
            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>LINKS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            Either The Missing Piece LLC or any third parties may provide links to other websites and/or resources. Thus, you acknowledge and agree that we are not responsible for the availability of any such external sites or resources, and as such, we do not endorse nor are we responsible or liable for any content, products, advertising or any other materials, on or available from such third party sites or resources. Furthermore, you acknowledge and agree that The Missing Piece LLC shall not be responsible or liable, directly or indirectly, for any such damage or loss which may be a result of, caused or allegedly to be caused by or in connection with the use of or the reliance on any such content, goods or Services made available on or through any such site or resource.\n" +
            "                        </p><br>\n" +

                   "\n" +
            //***********************************************
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>PROPRIETARY RIGHTS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            You do hereby acknowledge and agree that The Missing Piece LLC's Services and any essential software that may be used in connection with our Services (\"Software\") shall contain proprietary and confidential material that is protected by applicable intellectual property rights and other laws. Furthermore, you herein acknowledge and agree that any Content which may be contained in any advertisements or information presented by and through our Services or by advertisers is protected by copyrights, trademarks, patents or other proprietary rights and laws. Therefore, except for that which is expressly permitted by applicable law or as authorized by The Missing Piece LLC or such applicable licensor, you agree not to alter, modify, lease, rent, loan, sell, distribute, transmit, broadcast, publicly perform and/or created any plagiaristic works which are based on The Missing Piece LLC Services (e.g. Content or Software), in whole or part. The Missing Piece LLC herein has granted you personal, non-transferable and non-exclusive rights and/or license to make use of the object code or our Software on a single computer, as long as you do not, and shall not, allow any third party to duplicate, alter, modify, create or plagiarize work from, reverse engineer, reverse assemble or otherwise make an attempt to locate or discern any source code, sell, assign, sublicense, grant a security interest in and/or otherwise transfer any such right in the Software. Furthermore, you do herein agree not to alter or change the Software in any manner, nature or form, and as such, not to use any modified versions of the Software, including and without limitation, for the purpose of obtaining unauthorized access to our Services. Lastly, you also agree not to access or attempt to access our Services through any means other than through the interface which is provided by The Missing Piece LLC for use in accessing our Services.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>WARRANTY DISCLAIMERS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            YOU HEREIN EXPRESSLY ACKNOWLEDGE AND AGREE THAT: a) THE USE OF THE MISSING PIECE LLC SERVICES AND SOFTWARE ARE AT THE SOLE RISK BY YOU. OUR SERVICES AND SOFTWARE SHALL BE PROVIDED ON AN \"AS IS\" AND/OR \"AS AVAILABLE\" BASIS. THE MISSING PIECE LLC AND OUR SUBSIDIARIES, AFFILIATES, OFFICERS, EMPLOYEES, AGENTS, PARTNERS AND LICENSORS EXPRESSLY DISCLAIM ANY AND ALL WARRANTIES OF ANY KIND WHETHER EXPRESSED OR IMPLIED, INCLUDING, BUT NOT LIMITED TO ANY IMPLIED WARRANTIES OF TITLE, MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. b) THE MISSING PIECE LLC AND OUR SUBSIDIARIES, OFFICERS, EMPLOYEES, AGENTS, PARTNERS AND LICENSORS MAKE NO SUCH WARRANTIES THAT (i) THE MISSING PIECE LLC SERVICES OR SOFTWARE WILL MEET YOUR REQUIREMENTS; (ii) THE MISSING PIECE LLC SERVICES OR SOFTWARE SHALL BE UNINTERRUPTED, TIMELY, SECURE OR ERROR-FREE; (iii) THAT SUCH RESULTS WHICH MAY BE OBTAINED FROM THE USE OF THE THE MISSING PIECE LLC SERVICES OR SOFTWARE WILL BE ACCURATE OR RELIABLE; (iv) QUALITY OF ANY PRODUCTS, SERVICES, ANY INFORMATION OR OTHER MATERIAL WHICH MAY BE PURCHASED OR OBTAINED BY YOU THROUGH OUR SERVICES OR SOFTWARE WILL MEET YOUR EXPECTATIONS; AND (v) THAT ANY SUCH ERRORS CONTAINED IN THE SOFTWARE SHALL BE CORRECTED. c) ANY INFORMATION OR MATERIAL DOWNLOADED OR OTHERWISE OBTAINED BY WAY OF THE MISSING PIECE LLC SERVICES OR SOFTWARE SHALL BE ACCESSED BY YOUR SOLE DISCRETION AND SOLE RISK, AND AS SUCH YOU SHALL BE SOLELY RESPONSIBLE FOR AND HEREBY WAIVE ANY AND ALL CLAIMS AND CAUSES OF ACTION WITH RESPECT TO ANY DAMAGE TO YOUR COMPUTER AND/OR INTERNET ACCESS, DOWNLOADING AND/OR DISPLAYING, OR FOR ANY LOSS OF DATA THAT COULD RESULT FROM THE DOWNLOAD OF ANY SUCH INFORMATION OR MATERIAL. d) NO ADVICE AND/OR INFORMATION, DESPITE WHETHER WRITTEN OR ORAL, THAT MAY BE OBTAINED BY YOU FROM THE MISSING PIECE LLC OR BY WAY OF OR FROM OUR SERVICES OR SOFTWARE SHALL CREATE ANY WARRANTY NOT EXPRESSLY STATED IN THE TOS. e) A SMALL PERCENTAGE OF SOME USERS MAY EXPERIENCE SOME DEGREE OF EPILEPTIC SEIZURE WHEN EXPOSED TO CERTAIN LIGHT PATTERNS OR BACKGROUNDS THAT MAY BE CONTAINED ON A COMPUTER SCREEN OR WHILE USING OUR SERVICES. CERTAIN CONDITIONS MAY INDUCE A PREVIOUSLY UNKNOWN CONDITION OR UNDETECTED EPILEPTIC SYMPTOM IN USERS WHO HAVE SHOWN NO HISTORY OF ANY PRIOR SEIZURE OR EPILEPSY. SHOULD YOU, ANYONE YOU KNOW OR ANYONE IN YOUR FAMILY HAVE AN EPILEPTIC CONDITION, PLEASE CONSULT A PHYSICIAN IF YOU EXPERIENCE ANY OF THE FOLLOWING SYMPTOMS WHILE USING OUR SERVICES: DIZZINESS, ALTERED VISION, EYE OR MUSCLE TWITCHES, LOSS OF AWARENESS, DISORIENTATION, ANY INVOLUNTARY MOVEMENT, OR CONVULSIONS.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>LIMITATION OF LIABILITY</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            YOU EXPLICITLY ACKNOWLEDGE, UNDERSTAND AND AGREE THAT THE MISSING PIECE LLC AND OUR SUBSIDIARIES, AFFILIATES, OFFICERS, EMPLOYEES, AGENTS, PARTNERS AND LICENSORS SHALL NOT BE LIABLE TO YOU FOR ANY PUNITIVE, INDIRECT, INCIDENTAL, SPECIAL, CONSEQUENTIAL OR EXEMPLARY DAMAGES, INCLUDING, BUT NOT LIMITED TO, DAMAGES WHICH MAY BE RELATED TO THE LOSS OF ANY PROFITS, GOODWILL, USE, DATA AND/OR OTHER INTANGIBLE LOSSES, EVEN THOUGH WE MAY HAVE BEEN ADVISED OF SUCH POSSIBILITY THAT SAID DAMAGES.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>LINKS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            Either The Missing Piece LLC or any third parties may provide links to other websites and/or resources. Thus, you acknowledge and agree that we are not responsible for the availability of any such external sites or resources, and as such, we do not endorse nor are we responsible or liable for any content, products, advertising or any other materials, on or available from such third party sites or resources. Furthermore, you acknowledge and agree that The Missing Piece LLC shall not be responsible or liable, directly or indirectly, for any such damage or loss which may be a result of, caused or allegedly to be caused by or in connection with the use of or the reliance on any such content, goods or Services made available on or through any such site or resource.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>MAY OCCUR, AND RESULT FROM:</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            a) THE USE OR INABILITY TO USE OUR SERVICE; b) THE COST OF PROCURING SUBSTITUTE GOODS AND SERVICES; c) UNAUTHORIZED ACCESS TO OR THE ALTERATION OF YOUR TRANSMISSIONS AND/OR DATA; d) STATEMENTS OR CONDUCT OF ANY SUCH THIRD PARTY ON OUR SERVICE; e) AND ANY OTHER MATTER WHICH MAY BE RELATED TO OUR SERVICE.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>RELEASE</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            In the event you have a dispute, you agree to release The Missing Piece LLC (and its officers, directors, employees, agents, parent subsidiaries, affiliates, co-branders, partners and any other third parties) from claims, demands and damages (actual and consequential) of every kind and nature, known and unknown, suspected or unsuspected, disclosed and undisclosed, arising out of or in any way connected to such dispute.\n" +
            "                        </p><br>\n" +

            "\n" +

            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>SPECIAL ADMONITION RELATED TO FINANCIAL MATTERS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            Should you intend to create or to join any service, receive or request any such news, messages, alerts or other information from our Services concerning companies, stock quotes, investments or securities, please review the above Sections Warranty Disclaimers and Limitations of Liability again. In addition, for this particular type of information, the phrase \"Let the investor beware\" is appropriate. The Missing Piece LLC's content is provided primarily for informational purposes, and no content that shall be provided or included in our Services is intended for trading or investing purposes. The Missing Piece LLC and our licensors shall not be responsible or liable for the accuracy, usefulness or availability of any information transmitted and/or made available by way of our Services, and shall not be responsible or liable for any trading and/or investment decisions based on any such information.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>EXCLUSION AND LIMITATIONS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            THERE ARE SOME JURISDICTIONS WHICH DO NOT ALLOW THE EXCLUSION OF CERTAIN WARRANTIES OR THE LIMITATION OF EXCLUSION OF LIABILITY FOR INCIDENTAL OR CONSEQUENTIAL DAMAGES. THEREFORE, SOME OF THE ABOVE LIMITATIONS OF SECTIONS WARRANTY DISCLAIMERS AND LIMITATION OF LIABILITY MAY NOT APPLY TO YOU.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>THIRD PARTY BENEFICIARIES</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            You herein acknowledge, understand and agree, unless otherwise expressly provided in this TOS, that there shall be no third-party beneficiaries to this agreement. NOTICE The Missing Piece LLC may furnish you with notices, including those with regards to any changes to the TOS, including but not limited to email, regular mail, MMS or SMS, text messaging, postings on our website Services, or other reasonable means currently known or any which may be herein after developed. Any such notices may not be received if you violate any aspects of the TOS by accessing our Services in an unauthorized manner. Your acceptance of this TOS constitutes your agreement that you are deemed to have received any and all notices that would have been delivered had you accessed our Services in an authorized manner.\n" +
            "                        </p><br>\n" +

            "\n" +

            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>TRADEMARK INFORMATION</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            You herein acknowledge, understand and agree that all of the The Missing Piece LLC trademarks, copyright, trade name, service marks, and other The Missing Piece LLC logos and any brand features, and/or product and service names are trademarks and as such, are and shall remain the property of The Missing Piece LLC. You herein agree not to display and/or use in any manner the The Missing Piece LLC logo or marks without obtaining The Missing Piece LLC's prior written consent.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>COPYRIGHT OR INTELLECTUAL PROPERTY INFRINGEMENT CLAIMS NOTICE & PROCEDURES</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            The Missing Piece LLC will always respect the intellectual property of others, and we ask that all of our users do the same. With regards to appropriate circumstances and at its sole discretion, The Missing Piece LLC may disable and/or terminate the accounts of any user who violates our TOS and/or infringes the rights of others. If you feel that your work has been duplicated in such a way that would constitute copyright infringement, or if you believe your intellectual property rights have been otherwise violated, you should provide to us the following information: a) The electronic or the physical signature of the individual that is authorized on behalf of the owner of the copyright or other intellectual property interest; b) A description of the copyrighted work or other intellectual property that you believe has been infringed upon; c) A description of the location of the site which you allege has been infringing upon your work; d) Your physical address, telephone number, and email address; e) A statement, in which you state that the alleged and disputed use of your work is not authorized by the copyright owner, its agents or the law; f) And finally, a statement, made under penalty of perjury, that the aforementioned information in your notice is truthful and accurate, and that you are the copyright or intellectual property owner, representative or agent authorized to act on the copyright or intellectual property owner's behalf. The The Missing Piece LLC Agent for notice of claims of copyright or other intellectual property infringement can be contacted as follows: Mailing Address: The Missing Piece LLC Attn: Copyright Agent 7036 Creekside Dr Plainfield, Illinois 60586 Telephone: 7089647505 Email: info@iamthemissingpiece.com\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>CLOSED CAPTIONING</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            BE IT KNOWN, that The Missing Piece LLC complies with all applicable Federal Communications Commission rules and regulations regarding the closed captioning of video content. For more information, please visit our website at therapisthire.com.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>GENERAL INFORMATION ENTIRE AGREEMENT</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            This TOS constitutes the entire agreement between you and The Missing Piece LLC and shall govern the use of our Services, superseding any prior version of this TOS between you and us with respect to The Missing Piece LLC Services. You may also be subject to additional terms and conditions that may apply when you use or purchase certain other The Missing Piece LLC Services, affiliate Services, third-party content or third-party software.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>CHOICE OF LAW AND FORUM</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            It is at the mutual agreement of both you and The Missing Piece LLC with regard to the TOS that the relationship between the parties shall be governed by the laws of the state of without regard to its conflict of law provisions and that any and all claims, causes of action and/or disputes, arising out of or relating to the TOS, or the relationship between you and The Missing Piece LLC, shall be filed within the courts having jurisdiction within the County of , or the U.S. District Court located in said state. You and The Missing Piece LLC agree to submit to the jurisdiction of the courts as previously mentioned, and agree to waive any and all objections to the exercise of jurisdiction over the parties by such courts and to venue in such courts.\n" +
            "                        </p><br>\n" +

            "\n" +
             "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>WAIVER AND SEVERABILITY OF TERMS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            At any time, should The Missing Piece LLC fail to exercise or enforce any right or provision of the TOS, such failure shall not constitute a waiver of such right or provision. If any provision of this TOS is found by a court of competent jurisdiction to be invalid, the parties nevertheless agree that the court should endeavor to give effect to the parties' intentions as reflected in the provision, and the other provisions of the TOS remain in full force and effect. NO RIGHT OF SURVIVORSHIP NON-TRANSFERABILITY You acknowledge, understand and agree that your account is non-transferable and any rights to your ID and/or contents within your account shall terminate upon your death. Upon receipt of a copy of a death certificate, your account may be terminated and all contents therein permanently deleted.\n" +
            "                        </p><br>\n" +

            "\n" +
              "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>STATUTE OF LIMITATIONS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            You acknowledge, understand and agree that regardless of any statute or law to the contrary, any claim or action arising out of or related to the use of our Services or the TOS must be filed within year(s) after said claim or cause of action arose or shall be forever barred.\n" +
            "                        </p><br>\n" +

            "\n" +
            "                        <p style=\"font-size: 20px;color:#0b529a;\"><b>VIOLATIONS</b></p>\n" +
            "                        <p style=\"font-size: 18px;  font-family:Times New Roman;color: #251e1e\">\n" +
            "                          \n" +
            "                        \n" +
            "                            Please report any and all violations of this TOS to The Missing Piece LLC as follows: Mailing Address: The Missing Piece LLC 7036 Creekside Dr Plainfield, Illinois 60586 Telephone: 7089647505 Email: info@iamthemissingpiece.com\n" +
            "                        </p><br>\n" +

            "\n" +

            "</main> \n" +
            "\n" +
            "</body>\n" +
            "</html>";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

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
       // webview.loadUrl("http://logicalsofttech.com/therapist/Welcome/Privacy_Policy");

        webview.loadData(Privacy_policy, "text/html", null);
    }
}
