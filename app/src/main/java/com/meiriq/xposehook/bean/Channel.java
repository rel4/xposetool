package com.meiriq.xposehook.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tian on 15-12-26.
 */
public class Channel implements Serializable{
    private String name;

    public Channel() {
    }

    public Channel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "name='" + name + '\'' +
                '}';
    }

    public static ArrayList<Channel> getDefaultChannels(){
        ArrayList<Channel> channels = new ArrayList<>();
        Channel channel1 = new Channel("果盟");
        Channel channel2 = new Channel("中亿");
        Channel channel3 = new Channel("麟点");
        Channel channel4 = new Channel("金袋");
        Channel channel5 = new Channel("中亿2");
        Channel channel6 = new Channel("椰果");
        Channel channel7 = new Channel("有盟");
        Channel channel8 = new Channel("test");
        channels.add(channel1);
        channels.add(channel2);
        channels.add(channel3);
        channels.add(channel4);
        channels.add(channel5);
        channels.add(channel6);
        channels.add(channel7);
        channels.add(channel8);
        return channels;
    }
}
