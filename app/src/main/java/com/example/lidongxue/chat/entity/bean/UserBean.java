package com.example.lidongxue.chat.entity.bean;

import org.jivesoftware.smack.roster.packet.RosterPacket;

import java.util.List;

/**
 * Created by lidongxue on 17-10-17.
 */
//联系人实体类
public class UserBean {
    private String groupName;
    private List<UserBeanDetails> details;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<UserBeanDetails> getDetails() {
        return details;
    }

    public void setDetails(List<UserBeanDetails> details) {
        this.details = details;
    }

    public static class UserBeanDetails {
        private String userIp;
        private String pickName;
        private RosterPacket.ItemType type;
        private RosterPacket.ItemStatus status;

       /* public UserBeanDetails(String userIp, String pickName, RosterPacket.ItemType type, RosterPacket.ItemStatus status) {
            this.userIp = userIp;
            this.pickName = pickName;
            this.type = type;
            this.status = status;
        }*/

        public String getUserIp() {
            return userIp;
        }

        public void setUserIp(String userIp) {
            this.userIp = userIp;
        }

        public String getPickName() {
            return pickName;
        }

        public void setPickName(String pickName) {
            this.pickName = pickName;
        }

        public RosterPacket.ItemType getType() {
            return type;
        }

        public void setType(RosterPacket.ItemType type) {
            this.type = type;
        }

        public RosterPacket.ItemStatus getStatus() {
            return status;
        }

        public void setStatus(RosterPacket.ItemStatus status) {
            this.status = status;
        }
    }
}
