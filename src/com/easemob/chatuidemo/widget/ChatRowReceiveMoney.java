package com.easemob.chatuidemo.widget;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMMessage;
import com.easemob.chatuidemo.R;
import com.easemob.easeui.widget.chatrow.EaseChatRow;
import com.easemob.exceptions.EaseMobException;
import com.easemob.luckymoneysdk.constant.LMConstant;

public class ChatRowReceiveMoney extends EaseChatRow {

    private TextView mTvMessage;

    public ChatRowReceiveMoney(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflatView() {
        if (message.getBooleanAttribute(LMConstant.MESSAGE_ATTR_IS_OPEN_MONEY_MESSAGE, false)) {
            inflater.inflate(message.direct == EMMessage.Direct.RECEIVE ?
                    R.layout.ease_row_money_message : R.layout.ease_row_money_message, this);
        }
    }

    @Override
    protected void onFindViewById() {
        mTvMessage = (TextView) findViewById(R.id.ease_tv_money_msg);
    }

    @Override
    protected void onSetUpView() {
        try {
            String currentUser = EMChatManager.getInstance().getCurrentUser();
            String fromUser = message.getStringAttribute(LMConstant.EXTRA_LUCKY_MONEY_SENDER);//红包发送者
            String toUser = message.getStringAttribute(LMConstant.EXTRA_LUCKY_MONEY_RECEIVER);//红包接收者
            String senderId;
            if (message.direct == EMMessage.Direct.SEND) {
                if (message.getChatType().equals(EMMessage.ChatType.GroupChat)) {
                    senderId = message.getStringAttribute(LMConstant.EXTRA_LUCKY_MONEY_SENDER_ID);
                    if (senderId.equals(currentUser)) {
                        mTvMessage.setText("你领取了自己的红包");
                    } else {
                        mTvMessage.setText(String.format("你领取了%s的红包", fromUser));
                    }
                } else {
                    mTvMessage.setText(String.format("你领取了%s的红包", fromUser));
                }
            } else {
                mTvMessage.setText(String.format("%s领取了你的红包", toUser));
            }
        } catch (EaseMobException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onUpdateView() {

    }

    @Override
    protected void onBubbleClick() {
    }

}
